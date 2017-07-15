(ns challenges.4clojure.4clojure_elementary_test
  (:require [clojure.test :refer :all]
            [clojure.set :refer :all]))


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
;; #8
(deftest sets
  (is (=
       #{:a :b :c :d}
       (set '(:a :a :b :c :c :c :d :d))
       (clojure.set/union #{:a :b :c} #{:b :c :d}))))

;; #9
(deftest sets-conj
  (is (=
       #{1 2 3 4}
       (conj #{1 4 3} 2))))

;; #10
(deftest intro-to-maps
  (is (=
       ((hash-map :a 10 :b 20 :c 30) :b)
       (:b {:a 10 :b 20 :c 30}))))

;; #11
(deftest maps-conj
  (is (=
       {:a 1 :b 2 :c 3}
       (conj {:a 1} {:b 2} [:c 3]))))

;; #12
(deftest intro-to-sequences
  (is (=
       (first '(3 2 1))
       (second [2 3 4])
       (last (list 1 2 3))
       3)))

;; #13
(deftest sequences-rest
  (is (=
       [20 30 40]
       (rest [10 20 30 40]))))

;; #14
(deftest functions-intro
  (is (=
       8
       ((fn add-five [x] (+ x 5)) 3)
       ((fn [x] (+ x 5)) 3)
       (#(+ % 5) 3)
       ((partial + 5) 3))))

;; #15
(deftest double-down
  (let [f (fn [x] (* x 2))]
    (is (=
         (= (f 2) 4)
         (= (f 3) 6)
         (= (f 11) 22)
         (= (f 7) 14)))))

;; #16
(deftest hello-world
  (let [f #(str "Hello, " % "!")]
   (is (=
        (= (f "Dave") "Hello, Dave!")
        (= (f "Jenn") "Hello, Jenn!")
        (= (f "Rhea") "Hello, Rhea!")))))

;; #17
(deftest sequences-map
 (is (=
        [6 7 8]
        (map #(+ % 5) '(1 2 3)))))

;; #18
(deftest sequences-filter
 (is (=
        [6 7]
        (filter #(> % 5) '(3 4 5 6 7)))))

;; #35
(deftest local-bindings
 (is (=
      (= (let [x 5] (+ 2 x)) 7)
      (= (let [x 3 y 10] (- y x)) 7)
      (= (let [x 21] (let [y 3] (/ x y))) 7))))

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
