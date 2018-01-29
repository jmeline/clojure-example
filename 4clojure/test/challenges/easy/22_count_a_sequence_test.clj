(ns challenges.easy.22-count-a-sequence-test
  (:require  [clojure.test :refer :all]
             [challenges.easy.22-count-a-sequence :refer :all]))

(deftest count-a-sequence
  (letfn [(test-cases [f]
            (=
             (= (f '(1 2 3 3 1)) 5)
             (= (f "Hello World") 11)
             (= (f [[1 2] [3 4] [5 6]]) 3)
             (= (f '(13)) 1)
             (= (f '(:a :b :c)) 3)))]
    (is (= (test-cases solution1)
           (test-cases solution2)
           true))))

