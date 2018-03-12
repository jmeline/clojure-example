(ns clojure-for-brave-and-true.fwpd.core)

(def filename "suspects.csv")

(def vamp-keys
  [:name
   :glitter-index])

(defn str->int
  [str]
  (Integer. str))

(def conversions
  {:name identity
   :glitter-index str->int})

(defn convert
  [vamp-key value]
  ((vamp-key conversions) value))

(defn parse
  "conver a csv into rows of columns"
  [string]
  (map #(clojure.string/split % #",")
       (clojure.string/split string #"\n")))

(defn build-name-and-glitter-vector
  "Builds list of vectors for each vamp-key i.e.
  ([:name \"Edward Cullen\"] [:glitter-index \"3\"])"
  [row]
  (map vector vamp-keys row))

(defn convert-key-list-into-map
  [row]
  (reduce
   (fn [row-map [vamp-key value]]
     (assoc row-map vamp-key (convert vamp-key value))) {} row))

(defn mapify
  "returns a seq of maps like
  {:name \"Edward Cullen\" :glitter-index 10}"
  [rows]
  (map
   (fn [unmapped-row]
     (->>
      unmapped-row
      (build-name-and-glitter-vector)
      (convert-key-list-into-map)))
   rows))

(defn filter-glitter-index
  [minimum-glitter records]
  (filter #(>= (:glitter-index %) minimum-glitter) records))
