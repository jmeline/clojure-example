(ns clojure-lang.partial-test
  (:require  [clojure.test :as t]
             [clojure-lang.partial :as p]))

(t/deftest
  (def add20 (p/my-partial + 20))
  (t/is (= (add20 3) 23)))
