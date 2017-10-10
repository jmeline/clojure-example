(defproject rating-area-parser "0.0.1"
  :description "A Simple application that parses rating areas from cms"
  :url "http://example.com/FIXME"
  :license {:name "MIT"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 ;[org.clojure/clojure "1.9.0-beta2"]
                 [enlive "1.1.6"]
                 [http-kit "2.2.0"]
                 [org.clojure/data.xml "0.0.8"]]
  :main ^:skip-aot rating-area-parser.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
