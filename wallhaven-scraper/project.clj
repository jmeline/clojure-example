(defproject wallhaven-scraper "0.1.0-SNAPSHOT"
  :description "Scraping the website wallhaven.cc for wallpapers"
  :url "http://example.com/FIXME"
  :license {:name "MIT"
            :url ""}
  :dependencies [[org.clojure/clojure "1.10.0"]
                 [enlive "1.1.6"]
                 [http-kit "2.3.0"]]
  :main ^:skip-aot wallhaven-scraper.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
