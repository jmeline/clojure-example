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

(defn filter-glitter-index-by-name
  ;; 1. Turn the result of your glitter filter into a list of names.
  [minimum-glitter records]
  (map :name (filter-glitter-index minimum-glitter records)))

(defn append
  ;; 2. Write a function, append, which will append a new suspect to your list of suspects.
  [new-suspect suspects]
  (cons new-suspect suspects))

(defn validate
  ;; 3. Write a function, validate, which will check that :name and :glitter-index
  ;; are present when you append. The validate function should accept two arguments:
  ;; a map of keywords to validating functions, similar to conversions, and the record
  ;; to be validated.
  [validations record]
  (let [validations-results
        (reduce
         (fn [acc [key val :as all]]
           (assoc acc key
                  (if-let [validation-fn (key validations)]
                    ((key validations) val)
                    true))) {} record)]
    (assoc record :validations validations-results)))

(defn map->csv
  ;; 4. Write a function that will take your list of maps and convert it back to a CSV string.
  ;; You’ll need to use the clojure.string/join function.
  [records]
  (let [to-list
        (fn [record]
          (clojure.string/join "," (reduce
                                    (fn [acc [key val]]
                                      (conj acc (name key) val)) [] record)))]
    (map to-list records)))
