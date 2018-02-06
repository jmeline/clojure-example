(ns challenges.easy.38-maximum-value-test
  (:require [clojure.test :refer :all]
            [challenges.easy.38-maximum-value :refer :all]))

(deftest maximum-value
  (is (= (my-solution 1 8 3 4) 8))
  (is (= (my-solution 45 67 11) 67))
  (is (= (my-solution 30 20) 30)))
