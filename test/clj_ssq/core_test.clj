(ns clj-ssq.core-test
  (:require [clojure.test :refer :all]
            [clj-ssq.core :refer :all]
            [clj-ssq.codecs :as codecs]
            [clj-ssq.core-test-data :refer :all]
            [org.clojars.smee.binary.core :as b])
  (:import [java.net DatagramSocket DatagramPacket]
           [java.io ByteArrayInputStream ByteArrayOutputStream]))

(def ^:dynamic *test-server* "127.0.0.1")
(def ^:dynamic *test-port* -1)

(defn dummy-ssq-server-fixture [run-tests]
  (with-open [server-socket (DatagramSocket. 9876)]
    (let [recv-buf (byte-array 1000)
          recv-packet (DatagramPacket. recv-buf 1000)]
      (future
        (while (not (.isClosed server-socket))
          (when (try
                  (.receive server-socket recv-packet)
                  true
                  (catch Exception err
                    ;; (println "Test server error" err)
                    false))
            (let [query (take (.getLength recv-packet) recv-buf)
                  response
                  (cond ;; note: case doesn't work nicely with byte
                        ;; arrays, have to use cond
                    (= query (seq info-query))
                    info-response

                    (= query (seq player-challenge-query))
                    player-challenge-response
                    (= query (seq player-query))
                    player-response

                    (= query (seq rules-challenge-query))
                    rules-challenge-response
                    (= query (seq rules-query))
                    rules-response

                    :else (byte-array [1 1 1]))

                  responses
                  (if (sequential? response)
                    response
                    [response])]
              (doseq [datagram responses]
                (.send
                 server-socket
                 (DatagramPacket. datagram (count datagram)
                                  (.getSocketAddress recv-packet)))))))))

    (binding [*test-port* (.getLocalPort server-socket)]
      (run-tests))))

(use-fixtures :once dummy-ssq-server-fixture)

(deftest query-test
  (is (= @(info *test-server* *test-port*) info-response-value))
  (is (= @(rules *test-server* *test-port*) rules-response-value))
  (is (= @(players *test-server* *test-port*) player-response-value)))

(deftest msq-decode-test
  (let [bai (ByteArrayInputStream. msq-response)]
    (is (= (b/decode codecs/msq-response-codec bai)
           msq-value))))
