(ns wallhaven-scraper.core
  (:require [clj-http.client :as client]))

(def wallhaven-latest "https://alpha.wallhaven.cc/latest")

(defn get-site [site]
  (:body (client/get site)))

(defn -main
  "Wallhaven scraper, eventually it'll happen..."
  [& args]
  (println "this is what I got back!"
           (get-site wallhaven-latest)))
