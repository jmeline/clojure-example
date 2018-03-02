(ns challenges.easy.24-sum-it-all-up-test
  (:require  [clojure.test :refer :all]))

;; #24
(deftest sum-it-all-up
  (letfn [(solution1 [x] (apply + x))
          (solution2 [x] (reduce + x))
          (test-cases [f]
            (=
             (= (f [1 2 3]) 6)
             (= (f (list 0 -2 5 5)) 8)
             (= (f #{4 2 1}) 7)
             (= (f '(0 0 -1)) -1)
             (= (f '(1 10 3)) 14)))]
    (is (= (test-cases solution1)
           (test-cases solution2)))))
