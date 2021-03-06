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

(def master-regions (vals codecs/msq-regions))

(defn- concat-input-streams [streams]
  "Given a seq of InputStreams, returns a stream of the concatenation
  of all of the contents of each stream in streams, in order. "
  (SequenceInputStream. (Collections/enumeration streams)))

(defn- socket-open? [^DatagramSocket s] (not (.isClosed s)))

(defn- datagram->stream [^DatagramPacket p]
  (ByteArrayInputStream. (.getData p) (.getOffset p) (.getLength p)))

(defn- make-request-fn [request-code
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

          send
          (fn [data challenge]
            (let [bao (ByteArrayOutputStream. 100)]
              (b/encode codecs/ssq-request-codec bao [data challenge])
              (let [packet (DatagramPacket. (.toByteArray bao)
                                            (.size bao)
                                            address)]
                (.send socket packet))))

          result-promise (promise)]

      (send request-code (when needs-challenge? :request))

      (future
        (Thread/sleep timeout)
        (.close socket)
        (deliver result-promise {:err :timeout}))

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
                    (contains? result :challenge)
                    (do (send request-code (:challenge result))
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


;;;; Master Server Queries

;; TODO: pull out the duplicated code from this and
;; make-request-fn (maybe into core.async channels + transducers?)
(defn master [host port region filter
              & {:keys [:timeout :socket-timeout]
                 :or {:timeout 3000 :socket-timeout 3000}}]
  (let [^InetSocketAddress address
        (InetSocketAddress. host port)

        ^DatagramSocket socket
        (doto (DatagramSocket.)
          (.setSoTimeout socket-timeout)
          (.connect address))

        send
        (fn [prev-server]
          (let [bao (ByteArrayOutputStream. 100)]
            (b/encode codecs/msq-query-codec bao
                      {:region-code region
                       :prev-server prev-server
                       :filter filter})
            (let [packet (DatagramPacket. (.toByteArray bao)
                                          (.size bao)
                                          address)]
              (.send socket packet))))

        result-promise (promise)]

    (send "0.0.0.0:0")

    (future
      (Thread/sleep timeout)
      (.close socket)
      (when-not (realized? result-promise)
        (deliver result-promise {:err :timeout})))

    (future
      (loop [all-servers #{}]
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
              (let [servers (->> recv-packet
                                 datagram->stream
                                 (b/decode codecs/msq-response-codec)
                                 (map (fn [{:keys [ip port]}]
                                        (str ip ":" port))))
                    last-server (last servers)
                    new-all-servers (into all-servers servers)]
                (if (= last-server "0.0.0.0:0")
                  (deliver result-promise (disj new-all-servers "0.0.0.0:0"))

                  ;; Piece segmented responses back together
                  (do (send last-server)
                      (recur new-all-servers)))))))))
    result-promise))
