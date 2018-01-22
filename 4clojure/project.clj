(defproject challenges "0.1.0-SNAPSHOT"
  :description "Clojure challenges from across the web"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.9.0"]]
  :main ^:skip-aot challenges.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
