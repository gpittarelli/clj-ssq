(ns clj-ssq.core
  (:require [clj-ssq.codecs :as codecs]
            [clojure.java.io :as io]
            [clojure.core.async :as async]
            [org.clojars.smee.binary.core :as b])
  (:import [java.net Socket DatagramSocket DatagramPacket
            SocketException InetAddress
            InetSocketAddress SocketTimeoutException]
           [java.util Collections]
           [java.io BufferedReader BufferedWriter ByteArrayInputStream
            ByteArrayOutputStream SequenceInputStream]))

(defn- concat-input-streams [streams]
  "Given a seq of InputStreams, returns a stream of the concatenation
  of all of the contents of each stream in streams, in order. "
  (SequenceInputStream. (Collections/enumeration streams)))

(defn- socket-open? [^DatagramSocket s] (not (.isClosed s)))

(defn- datagram->stream [^DatagramPacket p]
  (ByteArrayInputStream. (.getData p) (.getOffset p) (.getLength p)))

(defn request [message
               ^InetSocketAddress address
               & {:keys [:timeout :needs-challenge?]
                  :or {:timeout 3000 :needs-challenge? false}}]
  "Send out an SSQ request to the given address, returning a promise
  that the decoded response will be delivered to (or
  {:err :timeout} if no response is receive in time)."
  (let [^DatagramSocket socket (doto (DatagramSocket.)
                                 (.connect address)
                                 (.setSoTimeout timeout))
        result-promise (promise)]

    (async/go-loop [parts {}]
      (let [recv-max-len 2048
            recv-buf (byte-array recv-max-len)
            recv-packet (DatagramPacket. recv-buf recv-max-len)]
        (when (socket-open? socket)
          (when (try (.receive socket recv-packet)
                     true
                     (catch SocketTimeoutException err
                       (deliver result-promise {:err :timeout})
                       (.close socket)
                       false))
            (let [stream (datagram->stream recv-packet)
                  result (b/decode codecs/framing-codec stream)]
              (cond
                (= (:type result) :segment)
                (let [{:keys [num total]} result
                      all-parts (assoc parts num stream)]
                  (if (= (count (keys all-parts)) total)
                    (let [chunks-in-order (map all-parts (range total))
                          full-message (concat-input-streams chunks-in-order)]

                      ;; TODO: nice way of moving this skip to the
                      ;; codec namespace
                      (.skip full-message 4)

                      (deliver result-promise
                               (b/decode codecs/ssq-codec full-message)))
                    (if (:compressed? result)
                      (deliver result-promise {:err :compression-unsupported})
                      (recur all-parts))))

                (contains? result :challenge)
                (let [bao (ByteArrayOutputStream.)
                      challenge-encoded
                      (b/encode codecs/challenge-codec bao result)
                      msg (byte-array (concat [0xFF 0xFF 0xFF 0xFF]
                                              message (.toByteArray bao)))]
                  (.send socket (DatagramPacket. msg (count msg) address))
                  (recur {}))

                :else
                (deliver result-promise result)))))))

    ;; TODO: nice way of moving this bit to the codec namespace
    (let [msg (byte-array (concat [0xFF 0xFF 0xFF 0xFF]
                                  message
                                  (when needs-challenge?
                                    [0xFF 0xFF 0xFF 0xFF])))]
      (.send socket (DatagramPacket. msg (count msg) address)))

    result-promise))

(defn info
  "Requests information from a Source server. Takes the same arguments
  as `request`, except does not accept a `message` parameter."
  ([& args]
   (apply request
          (.getBytes "TSource Engine Query\u0000")
          args)))

(defn players
  "Requests player list from a Source server. Takes the same arguments
  as `request`, except does not accept a `message` parameter."
  ([& args]
   (apply request
          (.getBytes "U")
          (concat args [:needs-challenge? true]))))

(defn rules
  "Requests rules list from a Source server. Takes the same arguments
  as `request`, except does not accept a `message` parameter."
  ([& args]
   (apply request
          (.getBytes "V")
          (concat args [:needs-challenge? true]))))
