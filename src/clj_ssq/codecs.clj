(ns clj-ssq.codecs
  (:require [org.clojars.smee.binary.core :as b]
            [clojure.string :as str]
            [clojure.set :refer [map-invert]])
  (:import [java.io ByteArrayInputStream]))

(def ^:private ssq-string (b/c-string "UTF8"))

;; From https://gist.github.com/stathissideris/8801295
(defn- select-codec [decision-codec selector-fn]
  (reify org.clojars.smee.binary.core.BinaryIO
    (read-data  [_ big-in little-in]
      (let [decision-value (b/read-data decision-codec big-in little-in)
            codec (selector-fn decision-value)]
        (b/read-data codec big-in little-in)))
    (write-data [_ big-out little-out value])))
;; (end previous attribution)

(defn- optional-byte
  "Returns a codec which will apply the given codec only if data is
  still available in the input stream, else it will return the default
  value.

  Due to limitations with the binary library, we must do a hack with a
  temporary input stream. (since mark/reset/available are not properly
  delegated, we can only test if new data is present with .read (and
  assume the underlying data stream is properly marked as ended, such
  as with ByteArrayInputStreams)) To simplify this hack, the nested
  codec must only read a single byte."
  [codec default-value]
  (reify org.clojars.smee.binary.core.BinaryIO
    (read-data [_ big-in little-in]
      (let [maybe-byte (.read big-in)]
        (if (= -1 maybe-byte)
          default-value
          (let [byte-stream (ByteArrayInputStream. (byte-array [maybe-byte]))]
            (b/decode codec byte-stream)))))
    (write-data [_ big-out little-out value]
      (b/write-data codec big-out little-out
                    (if (nil? value) default-value value)))))

(defn- optional-challenge []
  (reify org.clojars.smee.binary.core.BinaryIO
    (read-data [_ big-in little-in]
      (let [maybe-challenge-bytes (take 4 (repeatedly #(.read big-in)))]
        (when (every? #(not= % 255) maybe-challenge-bytes)
          (->> maybe-challenge-bytes
               byte-array
               ByteArrayInputStream.
               (b/decode :uint-le)))))
    (write-data [_ big-out little-out challenge]
      (cond
        (= challenge :request)
        (b/write-data :uint-le big-out little-out 0xffffffff)

        (not (nil? challenge))
        (b/write-data :uint-le big-out little-out challenge)))))

(defn- map-codec
  "Returns a codec that reads the nested codec and looks up the
  returned value in the given map to determine the resulting
  value (and does the reverse for encoding). The map must be 1-1 (all
  keys are unique and all values are unique)."
  [codec dict]
  (assert (every? #(= % 1) (vals (frequencies (vals dict))))
          "map-codec must be given a map with unique values.")
  (let [dict-inverse (map-invert dict)]
    (b/compile-codec
     codec
     #(dict-inverse %1)
     #(dict %1))))

(def ubyte->char
  (b/compile-codec :ubyte int char))

(def challenge-codec
  (b/compile-codec
   (b/ordered-map :challenge :uint-le)))

(declare info-codec)
(declare players-codec)
(declare rules-codec)

(def ssq-codec
  (b/compile-codec
   (select-codec
    (b/ordered-map :opcode :byte)
    (fn [{:keys [opcode]}]
      (case opcode
        0x49 info-codec
        0x44 players-codec
        0x45 rules-codec
        0x41 challenge-codec
        (b/ordered-map))))))

(def ssq-request-codec
  (b/compile-codec
   [(b/constant (b/blob) (byte-array [0xFF 0xFF 0xFF 0xFF]))
    (b/blob)
    (optional-challenge)]
   (fn [[opcode challenge]] [nil opcode challenge])
   identity))

(def msq-regions
  {0x00 :us-east
   0x01 :us-west
   0x02 :south-america
   0x03 :europe
   0x04 :asia
   0x05 :australia
   0x06 :middle-east
   0x07 :africa
   0xFF :other})

(def msq-query-codec
  (b/compile-codec
   [(b/constant :ubyte (int \1))
    (b/ordered-map :region-code (map-codec :ubyte msq-regions)
                   :prev-server ssq-string
                   :filter ssq-string)]
   (fn [data] [nil data])
   identity))

(def ip-codec
  (b/compile-codec
   [:ubyte :ubyte :ubyte :ubyte]
   (fn [ip-str] (map #(Integer. %) (str/split ip-str #"\.")))
   (fn [octets] (->> octets
                     (map str)
                     (str/join ".")))))

(def msq-response-codec
  (b/compile-codec
   [(byte-array [0xFF 0xFF 0xFF 0xFF 0x66 0x0A])
    (b/repeated (b/ordered-map :ip ip-codec :port :ushort))]
   identity
   second))

(def ^:private segment-codec
  (b/compile-codec
   (b/ordered-map :id :uint-le
                  :total :ubyte
                  :num :ubyte
                  :size :ushort-le)
   #(dissoc % :type :compressed?)
   #(assoc % :type :segment :compressed (bit-and (:id %) 0x80000000))))

(def framing-codec
  (select-codec (b/ordered-map :header :int-le)
                (fn [{:keys [header] :as a}]
                  (case header
                    -1 ssq-codec
                    -2 segment-codec))))

(let [edf-bits
      [:gameid? nil nil nil :steamid? :keywords? :sourcetv? :port?]

      ;; serialization order of the extended data
      edf-data-order
      [:port? :steamid? :sourcetv? :keywords? :gameid?]

      body
      (b/compile-codec
       (b/ordered-map
        :protocol :ubyte
        :name ssq-string
        :map ssq-string
        :folder ssq-string
        :game ssq-string
        :id :ushort-le
        :players :ubyte
        :max-players :ubyte
        :bots :ubyte
        :server-type (map-codec ubyte->char
                                {\d :dedicated
                                 \l :non-dedicated
                                 \p :stv-relay})
        :environment (map-codec ubyte->char
                                {\l :linux
                                 \w :windows
                                 \m :mac
                                 \o :old-mac})
        :password? (map-codec :ubyte {0 false 1 true})
        :vac-enabled? (map-codec :ubyte {0 false 1 true})
        :version ssq-string
        :edf (optional-byte (b/bits edf-bits) #{})))

      ssq-keywords
      (b/compile-codec
       ssq-string
       (fn [keywords] (str/join "," keywords))
       (fn [csv] (set (str/split csv #","))))

      extended-codecs
      {:gameid? (b/ordered-map :gameid :ulong-le)
       :keywords? (b/ordered-map :keywords ssq-keywords)
       :sourcetv? (b/ordered-map :sourcetv-port :ushort-le
                                 :sourcetv-url ssq-string)
       :port? (b/ordered-map :port :ushort-le)
       :steamid? (b/ordered-map :steamid :ulong-le)}]
  (def info-codec
    (reify org.clojars.smee.binary.core.BinaryIO
      (read-data [_ big-in little-in]
        (let [data (b/read-data body big-in little-in)
              extended-data
              (->> data
                   :edf
                   (#(filter % edf-data-order))
                   (map extended-codecs)
                   (map #(b/read-data % big-in little-in)))]
          (apply merge data extended-data)))

      (write-data [_ big-out little-out val]
        (b/write-data body big-out little-out val)))))

(let [player-codec
      (b/ordered-map
       :index :ubyte
       :name ssq-string
       :score :int-le
       :duration :float-le)]
  (def players-codec
    (b/compile-codec
     (b/repeated player-codec :prefix :ubyte))))

(let [name-value-codec (b/ordered-map :name ssq-string :value ssq-string)]
  (def rules-codec
    (b/compile-codec
     (b/repeated name-value-codec :prefix :ushort-le)
     #(apply map (fn [name val] {:name name :value val}) (juxt keys vals) %)
     #(into {} (map (juxt :name :value) %)))))
