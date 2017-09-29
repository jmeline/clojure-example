(ns rating-area-parser.core
  (:require [net.cgrand.enlive-html :as html]
            [org.httpkit.client :as http]
            [clojure.xml :as xml])
  (:gen-class))

(defn get-url [abbr]
  (str "https://www.cms.gov/CCIIO/Programs-and-Initiatives/Health-Insurance-Market-Reforms/" abbr "-gra.html"))

(def states ["AK" "AL" "AR" "AZ" "CA" "CO" "CT" "DE" "FL"
             "GA" "HI" "IA" "ID" "IL" "IN" "KS" "KY" "LA"
             "MA" "MD" "ME" "MI" "MN" "MO" "MS" "MT" "NC"
             "ND" "NE" "NH" "NJ" "NM" "NV" "NY" "OH" "OK"
             "OR" "PA" "RI" "SC" "SD" "TN" "TX" "UT" "VA"
             "VT" "WA" "WI" "WV" "WY" "AS" "GU" "MP" "PR"])

(defn get-state-specific-dom [state]
  (html/html-snippet
   (:body @(http/get (get-url state)))))

(defn get-table-rows [dom]
  (html/select dom [:div.list-bullet :tr :td :p]))

(defn county-name-format
  ([first-value second-value result]
   (conj result first-value second-value " "))
  ([first-value second-value]
   (county-name-format first-value second-value [])))

(defn three-digit-zip-format
  ([first-value second-value result]
   (conj result first-value " " second-value))
  ([first-value second-value]
   (three-digit-zip-format first-value second-value [])))

(defn normalize-data
  "restructure the data into a better format depending on if it
   uses the 3 digit zip format or if it uses the county name."
  [lst result-modification-fn]
  (loop [values lst result []]
    (if (empty? values)
      result
      (recur (drop 2 values)
             (result-modification-fn (first values) (second values) result)))))

(defn get-table-rows-from-dom
  "Collect html <tr> into a lazy sequence from the DOM"
  [state]
  (-> state
      (get-state-specific-dom)
      (get-table-rows)))

(defn pull-content-from-cms-website-by-state 
  "Build a crud list of all the rating areas for the state being pulled
   from the website"
  [state]
  (->> state
       (get-table-rows-from-dom)
       (drop 3) ;; ignore the table header rows
       (map #(apply str (:content %)))))

(defn sanitize-and-normalize-data 
  "CMS website uses inconsistent formatting and frankly pulling data from
   each page can be a headache. Remove the weird characters"
  [lst]
  (->> lst
       (map #(clojure.string/replace % #" " ""))
       (filter #(when (not (empty? %)) %))
       ((fn [lst]
          (if (number? (read-string (second lst)))
            (normalize-data lst three-digit-zip-format)
            (normalize-data lst county-name-format))))))

(defn partition-rating-areas
  "Obtains data from site and partitions the normalized data
   into groups of 3"
  [state]
  (->> state
       (pull-content-from-cms-website-by-state)
       (sanitize-and-normalize-data)
       (partition 3)))

(defn build-all-state-maps
  "Builds a single map of key State and value List of rating areas"
  []
  (reduce
   (fn [new-map state]
     (assoc new-map (keyword state) (partition-rating-areas state))) {} states))

(defn build-xml-data-tag
  ([value]
   (-> {:tag :Data}
       (assoc :attrs (if (number? (read-string value))
                       {"ss:Type" "Number"}
                       {"ss:Type" "String"}))
       (cond-> (not (empty? value)) (assoc :content [value])))))

(defn build-xml-cell
  [state]
  (into [] (map #(build-xml-data-tag %) (state r))))

(defn build-xml-worksheet
  [state]
  {:tag :Worksheet :attrs {"ss:Name" (name state)}
   :content [{:tag :Table :content [{:tag :Row :content [{:tag :Cell :content (build-xml-cell state)}]}]}]})

(defn testheader []
  (xml/emit-element
   {:tag :WorkBook :attrs {:xmlns "urn:schemas-microsoft-com:office:spreadsheet"
                           :xmlns:o "urn:schemas-microsoft-com:office:office"
                           :xmlns:x "urn:schemas-microsoft-com:office:excel"
                           :xmlns:ss "urn:schemas-microsoft-com:office:spreadsheet"
                           :xmlns:html "http://www.w3.org/TR/REC-html40"}
    :content [
              {:tag :Worksheet :attrs {"ss:Name" "Options"}
               :content [{:tag :Table :content [{:tag :Row :content [{:tag :Cell :content [
                                                                                           (build-xml-data-tag "Effective Year")
                                                                                           (build-xml-data-tag "2014" "Number")
                                                                                           ]}]}]}]}
              {:tag :Worksheet :attrs {"ss:Name" "AL"}
               :content [{:tag :Table :content [{:tag :Row :content [{:tag :Cell :content (into []
                                                                                                 (map #(build-xml-data-tag %)
                                                                                                      (first (:ID r))))
                                                                                           }]}]}]}
              (build-xml-worksheet :ID)
              ]}))
(defn -main
  "My attempt at writing a rating-area parser in clojure"
  [& args]
  (build-all-state-maps))

;;(def ft (future (partition-rating-areas (first states))))
