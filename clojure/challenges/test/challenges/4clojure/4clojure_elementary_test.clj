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
  (is (list :a :b :c) '(:a :b :c)))

;; #5
(deftest lists-conj
  (are [x] (= [1 2 3 4] x)
       (conj '(2 3 4) 1)
       (conj '(3 4) 2 1)))

;; #6
(deftest intro-to-vectors
  (are [x] (= [:a :b :c] x)
       (list :a :b :c)
       (vec '(:a :b :c))
       (vector :a :b :c)))

;; #7
(deftest vectors-conj
  (are [x] (= [1 2 3 4] x)
       (conj [1 2 3] 4)
       (conj [1 2] 3 4)))
;; #8
(deftest sets
  (are [x] (= #{:a :b :c :d} x)
       (set '(:a :a :b :c :c :c :d :d))
       (clojure.set/union #{:a :b :c} #{:b :c :d})))

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
  (are [x] (= x 3)
       (first '(3 2 1))
       (second [2 3 4])
       (last (list 1 2 3))))

;; #13
(deftest sequences-rest
  (is (= [20 30 40] (rest [10 20 30 40]))))

;; #14
(deftest functions-intro
  (are [x] (= 8 x)
       ((fn add-five [x] (+ x 5)) 3)
       ((fn [x] (+ x 5)) 3)
       (#(+ % 5) 3)
       ((partial + 5) 3)))

;; #15
(deftest double-down
  (let [f (fn [x] (* x 2))]
    (are [x y] (= (f x) y)
         2 4
         3 6
         11 22
         7 14)))

;; #16
(deftest hello-world
  (let [f #(str "Hello, " % "!")]
    (are [x y] (= x y)
         (f "Dave") "Hello, Dave!"
         (f "Jenn") "Hello, Jenn!"
         (f "Rhea") "Hello, Rhea!")))

;; #17
(deftest sequences-map
  (is (= [6 7 8] (map #(+ % 5) '(1 2 3)))))

;; #18
(deftest sequences-filter
  (is (= [6 7] (filter #(> % 5) '(3 4 5 6 7)))))

;; #35
(deftest local-bindings
  (are [x] (= 7 x)
       (let [x 5] (+ 2 x))
       (let [x 3 y 10] (- y x))
       (let [x 21] (let [y 3] (/ x y)))))

;; #36
(deftest let-it-be
  (are [x y] (= x y)
       10 (let [x 7 y 3] (+ x y))
       4  (let [y 3 z 1] (+ y z))
       1  (let [z 1] z)))

;; #37
(deftest regular-expressions
  (is (= "ABC" (apply str (re-seq #"[A-Z]+" "bA1B3Ce ")))))

;; #57
(deftest simple-recursion
  (is
    (= ((fn foo [x]
          (when (> x 0)
            (conj (foo (dec x)) x)))
        5)
       [5 4 3 2 1])))

;; #64
(deftest intro-to-reduce
  (are [x y] (= x y)
       15 (reduce + [1 2 3 4 5])
       0  (reduce + [])
       6  (reduce + 1 [2 3])))

;; #68
(deftest recurring-theme
  (is (=
        (loop [x 5 result []]
          (if (> x 0)
            (recur (dec x) (conj result (+ 2 x)))
            result))
        [7 6 5 4 3])))

;; #71
(deftest rearranging-code->
  (are [x] (= x 5)
       (last (sort (rest (reverse [2 5 4 1 3 6]))))
       (-> [2 5 4 1 3 6]
           (reverse)
           (rest)
           (sort)
           (last))))

;; #72
(deftest rearranging-code->>
  (are [x] (= 11 x)
       (reduce + (map inc (take 3 (drop 2 [2 5 4 1 3 6]))))
       (->>
         [2 5 4 1 3 6]
         (drop 2)
         (take 3)
         (map inc)
         (reduce +))))

;; #134
;; return true iff contains key and value is nil
(deftest a-nil-key
  (let [f #(not (%2 %1 true))]
    (are [r x] (r (f x {:a nil :b 2}))
         true? :a
         false? :b
         false? :c)))

;; #145
(deftest for-the-win
  (are [x] (= x '(1 5 9 13 17 21 25 29 33 37))
       (for [x (range 40) :when (= 1 (rem x 4))] x)
       (for [x (iterate #(+ 4 %) 0)
             :let [z (inc x)]
             :while (< z 40)] z)
       (for [[x y] (partition 2 (range 20))] (+ x y))))

;; 156
(deftest map-defaults
  (let [f (fn [x y] (into {} (for [value y] [value x])))]
    (are [x y] (= x y)
         (f 0 [:a :b :c]) {:a 0 :b 0 :c 0}
         (f "x" [1 2 3]) {1 "x" 2 "x" 3 "x"}
         (f [:a :b] [:foo :bar]) {:foo [:a :b] :bar [:a :b]})))

;; #161
(deftest subset-and-superset
  (are [x] (= true x)
       (clojure.set/subset? #{1} #{1 2})
       (clojure.set/subset? #{1 2} #{1 2})
       (clojure.set/superset? #{1 2} #{2})
       (clojure.set/superset? #{1 2} #{1 2})))

;; #162
(deftest logical-falsity-and-truth
  (are [x] (= x 1)
       (if-not false 1 0)
       (if-not nil 1 0)
       (if true 1 0)
       (if [] 1 0)
       (if [0] 1 0)
       (if 0 1 0)
       (if 1 1 0)))
