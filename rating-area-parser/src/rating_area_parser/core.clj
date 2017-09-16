(ns rating-area-parser.core
  (:require [net.cgrand.enlive-html :as html]
            [org.httpkit.client :as http])
  (:gen-class))

(defn get-abbr-url [abbr]
  (str "https://www.cms.gov/CCIIO/Programs-and-Initiatives/Health-Insurance-Market-Reforms/" abbr "-gra.html"))

(def states ["AK" "AL" "AR" "AZ" "CA" "CO" "CT" "DE" "FL"
             "GA" "HI" "IA" "ID" "IL" "IN" "KS" "KY" "LA"
             "MA" "MD" "ME" "MI" "MN" "MO" "MS" "MT" "NC"
             "ND" "NE" "NH" "NJ" "NM" "NV" "NY" "OH" "OK"
             "OR" "PA" "RI" "SC" "SD" "TN" "TX" "UT" "VA"
             "VT" "WA" "WI" "WV" "WY"])

(defn get-dom [state]
  (html/html-snippet
   (:body @(http/get (get-abbr-url state)))))

(defn get-rows [dom]
  (html/select dom [:div.list-bullet :tr :td :p]))

(defn get-main-content [state]
  (get-rows (get-dom state)))

(defn -main
  "My attempt at writing a rating-area parser in clojure"
  [& args]
  (let [rows (drop 3 (get-main-content (last states)))]
    (partition 3 (map #(apply str (:content %)) rows))))

