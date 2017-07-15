(ns challenges.4clojure.4clojure_elementary_test
  (:require [clojure.test :refer :all]))

;; #1
(deftest nothing-but-the-truth
  (is (= true true)))

;; #2
(deftest simple-math
  (is (= (- 10 (* 2 3)) 4)))

;; #3
(deftest intro-to-strings
  (is (= (.toUpperCase "hello world") "HELLO WORLD")))

;; #4
(deftest intro-to-lists
  (is (list :a :b :c)
      '(:a :b :c)))

;; #5
(deftest lists-conj
  (is (=
       [1 2 3 4]
       (conj '(2 3 4) 1)
       (conj '(3 4) 2 1))))

;; #6
(deftest intro-to-vectors
  (is (=
       [:a :b :c]
       (list :a :b :c)
       (vec '(:a :b :c))
       (vector :a :b :c))))

;; #7
(deftest vectors-conj
  (is (=
        [1 2 3 4]
        (conj [1 2 3] 4)
        (conj [1 2] 3 4))))

;; #72
(deftest rearranging-code->>
  (is (= (reduce + (map inc (take 3 (drop 2 [2 5 4 1 3 6]))))
         (->>
           [2 5 4 1 3 6]
           (drop 2)
           (take 3)
           (map inc)
           (reduce +))
         11)))
