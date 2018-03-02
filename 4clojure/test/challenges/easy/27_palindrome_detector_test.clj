(ns challenges.easy.27-palindrome-detector-test
  (:require  [clojure.test :refer :all]))

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
