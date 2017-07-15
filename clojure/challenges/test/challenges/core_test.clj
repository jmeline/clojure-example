(ns challenges.core-test
  (:require [clojure.test :refer :all]
            [challenges.core :refer :all]))

(deftest addition
  (is (= 4 (+ 2 2)))
  (is (= 9 (+ 3 6))))

(deftest subtraction
  (is (= 0 (- 2 2)))
  (is (= 9 (- 13 4))))

(deftest multiplication
  (are [x y] (= x y)
        4 (* 2 2)
        100 (* 10 10)))

(deftest arithmetic
  (addition)
  (subtraction)
  (multiplication))

(deftest a-test
  (testing "FIXME, I fail."
    (is (cool) "Im cool!")))
