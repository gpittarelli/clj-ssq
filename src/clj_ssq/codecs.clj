(ns clj-ssq.codecs
  (:require [org.clojars.smee.binary.core :as b]))

(def ^:private ssq-string (b/c-string "UTF8"))

;; From https://gist.github.com/stathissideris/8801295
(defn- select-codec [decision-codec selector-fn]
  (reify org.clojars.smee.binary.core.BinaryIO
    (read-data  [_ big-in little-in]
      (let [decision-value (b/read-data decision-codec big-in little-in)
            codec (selector-fn decision-value)]
        (b/read-data codec big-in little-in)))
    (write-data [_ big-out little-out script])))

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
        :server-type :ubyte
        :environment :ubyte
        :visibility :ubyte
        :vac-enabled? :ubyte
        :version ssq-string
        :edf (b/bits edf-bits)))

      extended-codecs
      {:gameid? (b/ordered-map :gameid :ulong-le)
       :keywords? (b/ordered-map :keywords ssq-string)
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
