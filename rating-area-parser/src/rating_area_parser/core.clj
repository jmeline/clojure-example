(ns rating-area-parser.core
  (:require [net.cgrand.enlive-html :as html]
            [org.httpkit.client :as http])
  (:gen-class))

(defn get-url [abbr]
  (str "https://www.cms.gov/CCIIO/Programs-and-Initiatives/Health-Insurance-Market-Reforms/" abbr "-gra.html"))

(def states ["AK" "AL" "AR" "AZ" "CA" "CO" "CT" "DE" "FL"
             "GA" "HI" "IA" "ID" "IL" "IN" "KS" "KY" "LA"
             "MA" "MD" "ME" "MI" "MN" "MO" "MS" "MT" "NC"
             "ND" "NE" "NH" "NJ" "NM" "NV" "NY" "OH" "OK"
             "OR" "PA" "RI" "SC" "SD" "TN" "TX" "UT" "VA"
             "VT" "WA" "WI" "WV" "WY"])

(defn get-state-specific-dom [state]
  (html/html-snippet
   (:body @(http/get (get-url state)))))

(defn get-table-rows [dom]
  (html/select dom [:div.list-bullet :tr :td :p]))

(defn get-table-rows-from-dom [state]
  (-> state 
      (get-state-specific-dom)
      (get-table-rows)))

(defn partition-rating-areas [state]
  (->> state
       (get-table-rows-from-dom)
       (drop 3)
       (map #(apply str (:content %)))
       (partition 3)))

(defn -main [& args]
  "My attempt at writing a rating-area parser in clojure"
  (partition-rating-areas (last states)))
