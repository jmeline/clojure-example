(ns cryptopals.core
  (:gen-class))

(defn exp [x n]
  (loop [acc 1 n n]
    (if (zero? n)
      acc
      (recur (* x acc) (dec n)))))

(defn dec->hex-conversion [x]
  (condp #(= %1 %2) x
    10 "A"
    11 "B"
    12 "C"
    13 "D"
    14 "E"
    15 "F"
    x))

(defn hex->dec-conversion [x]
  (condp #(= %1 %2) x
    "A" "10"
    "B" "11"
    "C" "12"
    "D" "13"
    "E" "14"
    "F" "15"
    x))

(defn parse-int [x]
  (Integer. (re-find #"\d+" x)))

(defn parse-values [value]
  (->>
   (reduce #(conj %1 (-> %2 str hex->dec-conversion)) '() value)
   (map parse-int)))

(defn dec->hex [value]
  (loop [x value
         hex '()]
    (if (zero? x)
      (clojure.string/join hex)
      (let [rem (mod x 16)
            hex-value (dec->hex-conversion rem)]
        (recur (quot x 16)
               (conj hex hex-value))))))

(defn hex->dec [value]
  (loop [x 0
         result 0
         hex-values (parse-values value)]
    (if (empty? hex-values)
      result
      (recur (inc x)
             (+ result (* (first hex-values) (exp 16 x)))
             (rest hex-values)))))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))


