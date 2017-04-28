(ns recursive-nameless-functions.fib_test
  (:require [clojure.test :refer :all]
            [recursive-nameless-functions.fib :refer :all]))

(def expected '(1 1 2 3 5 8 13 21 34))

(defn sample [f]
  (map f (range 1 10)))

(deftest fibbonacci-functions
  (are [f] (= expected (sample f))
       fib
       (fib-gen fib)
       (fib-weird fib-weird)
       fib-nameless))
