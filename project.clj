(defproject clj-ssq "0.3.0"
  :description "Source Server Query protocol implementation"
  :url "https://github.com/gpittarelli/clj-ssq"
  :license {:name "MIT License"
            :url "http://www.opensource.org/licenses/mit-license.php"}
  :dependencies [[org.clojure/core.async "0.1.346.0-17112a-alpha"]
                 [smee/binary "0.5.1"]]
  :profiles {:dev {:dependencies [[org.clojure/clojure "1.7.0"]]
                   :global-vars {*warn-on-reflection* true}}}
  :signing {:gpg-key "8D1FE35F"})
