(ns challenges.easy.21-nthElement-test
  (:require [challenges.easy.21-nthElement :refer :all]
            [clojure.test :refer :all]))

(defn validate
  [numbers index expected]
  (apply = (conj (map #(% numbers index) [my-solution other-solution1 other-solution1]) expected)))
(deftest nth-element
  (is (= (validate '(4 5 6 7) 2 6)
         (validate [:a :b :c] 0 :a)
         (validate [1 2 3 4] 1 2)
         (validate '([1 2] [3 4] [5 6] [6 7]) 2 [5 6]))))

