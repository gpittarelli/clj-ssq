(ns clj-ssq.core
  (:require [clj-ssq.codecs :as codecs]
            [clojure.java.io :as io]
            [org.clojars.smee.binary.core :as b])
  (:import [java.net Socket DatagramSocket DatagramPacket
            SocketException InetAddress
            InetSocketAddress SocketTimeoutException
            PortUnreachableException]
           [java.util Collections]
           [java.io BufferedReader BufferedWriter ByteArrayInputStream
            ByteArrayOutputStream SequenceInputStream IOException]))

(defn- concat-input-streams [streams]
  "Given a seq of InputStreams, returns a stream of the concatenation
  of all of the contents of each stream in streams, in order. "
  (SequenceInputStream. (Collections/enumeration streams)))

(defn- socket-open? [^DatagramSocket s] (not (.isClosed s)))

(defn- datagram->stream [^DatagramPacket p]
  (ByteArrayInputStream. (.getData p) (.getOffset p) (.getLength p)))

(defn- make-request-fn [request-message
                        & {:keys [:needs-challenge?]
                           :or {:needs-challenge? false}}]
  "Creates a function that sends out an SSQ request to a given
  address, and returns a promise that the decoded response will be
  delivered to (or {:err ...} if no response is received
  before :timeout milliseconds). Note that some queries may still take
  longer than :timeout milliseconds, because the answer may be sent in
  multiple segments and "
  (fn [host port & {:keys [:timeout :socket-timeout]
                    :or {:timeout 3000 :socket-timeout 3000}}]
    (let [^InetSocketAddress address
          (InetSocketAddress. host port)

          ^DatagramSocket socket
          (doto (DatagramSocket.)
            (.setSoTimeout socket-timeout)
            (.connect address))

          result-promise (promise)]

      ;; TODO: nice way of moving this bit to the codec namespace
      (let [msg (byte-array (concat [0xFF 0xFF 0xFF 0xFF]
                                    request-message
                                    (when needs-challenge?
                                      [0xFF 0xFF 0xFF 0xFF])))]
        (.send socket (DatagramPacket. msg (count msg) address)))

      (future
        (Thread/sleep timeout)
        (.close socket)
        (when-not (realized? result-promise)
          (deliver result-promise {:err :timeout})))

      (future
        (loop [parts {}]
          (let [recv-max-len 2048
                recv-buf (byte-array recv-max-len)
                recv-packet (DatagramPacket. recv-buf recv-max-len)

                handle-err (fn [code exception]
                             (.close socket)
                             (deliver result-promise
                                      {:err code
                                       :exception exception})
                             false)]
            (when (and (not (realized? result-promise))
                       (socket-open? socket))
              (when (try
                      (.receive socket recv-packet)
                      true

                      (catch SocketTimeoutException err
                        (handle-err :socket-timeout err))
                      (catch PortUnreachableException err
                        (handle-err :port-unreachable err))
                      (catch IOException err
                        (handle-err :io-exception err)))
                (let [stream (datagram->stream recv-packet)
                      result (b/decode codecs/framing-codec stream)]
                  (cond
                    ;; Piece segmented responses back together
                    (= (:type result) :segment)
                    (let [{:keys [num total]} result
                          all-parts (assoc parts num stream)]
                      (if (= (count (keys all-parts)) total)
                        (->> (range total)
                             (map all-parts)
                             concat-input-streams
                             (b/decode codecs/framing-codec)
                             (deliver result-promise))
                        (if (:compressed? result)
                          (deliver result-promise
                                   {:err :compression-unsupported})
                          (recur all-parts))))

                    ;; If given a challenge, re-issue with the request
                    ;; with the challenge token
                    ;; TODO: move the binary stuff here into the codecs code
                    (contains? result :challenge)
                    (let [bao (ByteArrayOutputStream.)

                          challenge-encoded
                          (b/encode codecs/challenge-codec bao result)

                          msg (byte-array
                               (concat [0xFF 0xFF 0xFF 0xFF]
                                       request-message (.toByteArray bao)))]
                      (.send socket (DatagramPacket. msg (count msg) address))
                      (recur {}))

                    ;; otherwise we got the actual data back
                    :else
                    (deliver result-promise result))))))))

      result-promise)))

(def info
  "Requests information from a Source server. Takes host, port, and
  optional :timeout/:socket-timeout parameters and returns a promise
  to which the result will be delivered, or an error object."
  (make-request-fn (.getBytes "TSource Engine Query\u0000")))

(def players
  "Requests player list from a Source server. Takes host, port, and
  optional :timeout/:socket-timeout parameters and returns a promise
  to which the result will be delivered, or an error object."
  (make-request-fn (.getBytes "U") :needs-challenge? true))

(def rules
  "Requests rules list from a Source server. Takes host, port, and
  optional :timeout/:socket-timeout parameters and returns a promise
  to which the result will be delivered, or an error object."
  (make-request-fn (.getBytes "V") :needs-challenge? true))
