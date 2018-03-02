(ns challenges.easy.25-find-the-odd-numbers-test
  (:require  [clojure.test :refer :all]))

;; #25
(deftest find-the-odd-numbers
  (are [x y] (= (filter odd? x) y)
    #{1 2 3 4 5} '(1 3 5)
    [4 2 1 6] '(1)
    [2 2 4 6] '()
    [1 1 1 3] '(1 1 1 3)))
