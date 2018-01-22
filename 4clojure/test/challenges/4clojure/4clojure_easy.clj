(ns challenges.4clojure.4clojure_easy
  (:require [clojure.test :refer :all]))


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

;; #25
(deftest find-the-odd-numbers
  (are [x y] (= (filter odd? x) y)
       #{1 2 3 4 5} '(1 3 5)
       [4 2 1 6] '(1)
       [2 2 4 6] '()
       [1 1 1 3] '(1 1 1 3)))

;; #27
(deftest palindrome-detector
  (letfn [(my-solution [lst]
            (if (not (seq lst))
              true
              (if (not= (first lst) (last lst))
                false
                (recur (drop 1 (drop-last lst))))))
          (simple-solution [lst] (= (reverse lst) (seq lst)))]
  (are [f y] (every? f (map #(% y) [my-solution simple-solution]))
       false? '(1 2 3 4 5)
       true? "racecar"
       true? [:foo :bar :foo]
       true? '(1 1 3 3 1 1)
       false? '(:a :b :c))))
