(ns challenges.4clojure.4clojure_easy
  (:require [clojure.test :refer :all]))

;; #21
(deftest nth-element
  (defn func1 [lst index]
    (if (= 0 index) (first lst) (recur (rest lst) (dec index))))
  (defn func2 [lst index] (first (drop index lst)))
  (defn func3 [lst index] (->> lst (drop index)))
  (defn validate [numbers index expected]
    (apply =
           (conj (map #(% numbers index) [func1 func2 func3]) expected)))
  (is (= (validate '(4 5 6 7) 2 6)
         (validate [:a :b :c] 0 :a)
         (validate [1 2 3 4] 1 2)
         (validate '([1 2] [3 4] [5 6] [6 7]) 2 [5 6]))))

;; #22
(deftest count-a-sequence
  (defn solution1 [x]
    (loop [items x cnt 0]
      (if (empty? items)
        cnt
        (recur (rest items) (inc cnt)))))
  (defn solution2 [x] (reduce (fn [c _] (inc c)) 0 x))
  (defn test-cases [f]
    (=
      (= (f '(1 2 3 3 1)) 5)
      (= (f "Hello World") 11)
      (= (f [[1 2] [3 4] [5 6]]) 3)
      (= (f '(13)) 5)
      (= (f '(:a :b :c)) 3)))

  (is (= (test-cases solution1)
         (test-cases solution2))))
;; #24
(deftest sum-it-all-up
  (defn solution1 [x] (apply + x))
  (defn solution2 [x] (reduce + x))
  (defn test-cases [f]
    (=
      (= (f [1 2 3]) 6)
      (= (f (list 0 -2 5 5)) 8)
      (= (f #{4 2 1}) 7)
      (= (f '(0 0 -1)) -1)
      (= (f '(1 10 3)) 14)))
  (is (= (test-cases solution1)
         (test-cases solution2))))
