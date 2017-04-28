(ns recursive-nameless-functions.fractorial_test
  (:require [clojure.test :refer :all]
            [recursive-nameless-functions.fractorial :refer :all]))

(def fractorial-result-1 3628800)
(def fractorial-result-2 120)
(deftest regular-fractorial-function
  (testing "Tests regular recursive function"
    (is (= (fractorial 10) fractorial-result-1))
    (is (= (fractorial 5) fractorial-result-2)))
  (testing "Tests fractorial-gen"
    (is (= ((fractorial-gen fractorial) 10) fractorial-result-1))
    (is (= ((fractorial-gen fractorial) 5) fractorial-result-2)))
  (testing "Tests fractorial-weird"
    (is (= ((fractorial-weird fractorial-weird) 10) fractorial-result-1))
    (is (= ((fractorial-weird fractorial-weird) 5) fractorial-result-2)))
  (testing "Tests fractorial-no-names"
    (is (= (fractorial-no-names 10) fractorial-result-1))
    (is (= (fractorial-no-names 5) fractorial-result-2)))
  (testing "Test equality of all functions"
    (is (= (fractorial 10) 
           ((fractorial-gen fractorial) 10) 
           ((fractorial-weird fractorial-weird) 10)
           (fractorial-no-names 10)
           fractorial-result-1))))
