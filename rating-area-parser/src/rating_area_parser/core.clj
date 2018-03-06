(ns rating-area-parser.core
  (:require [net.cgrand.enlive-html :as html]
            [org.httpkit.client :as http]
            [clojure.xml :as xml])
  (:gen-class))

;; Web Scraping
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
  [lst format-fn]
  (loop [values lst
         result []]
    (if (empty? values)
      result
      (recur (drop 2 values) (format-fn (first values) (second values) result)))))

(defn get-table-rows-from-dom
  "Collect html <tr> into a lazy sequence from the DOM"
  [state]
  (-> state
      (get-state-specific-dom)
      (get-table-rows)))

(defn pull-content-from-cms-website-by-state 
  "Build a list of all the rating areas for the state being pulled
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
          (let [normalize-fn (partial normalize-data lst)]
            (if (number? (read-string (second lst)))
              (normalize-fn three-digit-zip-format)
              (normalize-fn county-name-format)))))))

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
   (fn [list-of-maps state]
     (println "state: " state)
     (conj list-of-maps
           (assoc {}
                  :state state
                  :rating-areas (partition-rating-areas state)))) [] states))

;; XML Generation
(defn determine-type [value]
  (println "'" value "', " (type value))
  (cond
    (clojure.string/blank? value) "String"
    (number? (read-string value)) "Number"
    :else "String"))

(defn build-xml-data-tag
  [value]
  (-> {:tag :Data :attrs {"ss:Type" (determine-type value)}}
      (cond-> (not (empty? value)) (assoc :content [value]))
      (#(identity {:tag :Cell :content [%]}))))

(defn build-xml-cell
  [collection]
  (-> (into [] (mapv #(build-xml-data-tag %) collection))
      (#(identity {:tag :Row :content %}))))

(defn build-xml-collection
  [collection]
  (map #(build-xml-cell %) collection))

(defn build-worksheet
  [name content]
  {:tag :Worksheet :attrs {"ss:Name" name}
   :content [{:tag :Table :content [{:tag :Row :content [{:tag :Cell :content content}]}]}]} )

(defn build-xml-header
  [worksheets]
  {:tag :WorkBook :attrs {:xmlns "urn:schemas-microsoft-com:office:spreadsheet"
                          :xmlns:o "urn:schemas-microsoft-com:office:office"
                          :xmlns:x "urn:schemas-microsoft-com:office:excel"
                          :xmlns:ss "urn:schemas-microsoft-com:office:spreadsheet"
                          :xmlns:html "http://www.w3.org/TR/REC-html40"}
   :content worksheets})

(defn build-xml-worksheet
  [values-list options]
  (build-xml-header
   (conj (build-worksheet "Options" (map #(build-xml-data-tag %) ["Effective Year" (:year options)]))
         (reduce
          (fn [result map]
            (println (:state map) " = " (:rating-areas map))
            (conj build-worksheet
                  (name (:state map))
                  (build-xml-collection (:rating-areas map))) result) [] values-list))))

(defn write-to-file
  [data]
  (with-open [out-file (clojure.java.io/writer "RatingAreas.xml" :encoding "UTF-8")]
    (xml/emit data out-file)))

(defn -main
  "My attempt at writing a rating-area parser in clojure"
  [& args]
  (let [currentYear "2018"]
    (write-to-file
     (build-xml-worksheet (build-all-state-maps) {:year currentYear}))))

;;(def ft (future (partition-rating-areas (first states))))
