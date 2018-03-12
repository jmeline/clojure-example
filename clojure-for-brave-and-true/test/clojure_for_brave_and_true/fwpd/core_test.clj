(ns clojure-for-brave-and-true.fwpd.core-test
  (:require [clojure-for-brave-and-true.fwpd.core :as sut]
            [clojure.test :refer :all]))

(deftest fwpd-tests
  (testing "convert"
    (is (= (sut/convert :name "jacob") "jacob"))
    (is (= (sut/convert :glitter-index "10") 10)))
  (testing "build-name-and-glitter-vector"
    (is (= (sut/build-name-and-glitter-vector ["Edward Cullen" "10"])
           '([:name "Edward Cullen"] [:glitter-index "10"]))))
  (testing "convert-key-list-into-map"
    (is (= (sut/convert-key-list-into-map '([:name "Frodo Baggins"] [:glitter-index "50"]))
           {:name "Frodo Baggins" :glitter-index 50}))
    (is (= (sut/convert-key-list-into-map '([:name "Edward Cullen"] [:glitter-index "15"]))
           {:name "Edward Cullen" :glitter-index 15})))
  (testing "filtering based on glitter index"
    (def sampleData
      '({:name "Edward Cullen", :glitter-index 10}
        {:name "Bella Swan", :glitter-index 0}
        {:name "Charlie Swan", :glitter-index 0}
        {:name "Jacob Black", :glitter-index 3}
        {:name "Carlisle Cullen", :glitter-index 6}))
    (is (= (sut/filter-glitter-index 3 sampleData)
           '({:name "Edward Cullen", :glitter-index 10}
             {:name "Jacob Black", :glitter-index 3}
             {:name "Carlisle Cullen", :glitter-index 6})))
    (is (= (sut/filter-glitter-index 7 sampleData)
           '({:name "Edward Cullen", :glitter-index 10})))
    (is (= (sut/filter-glitter-index 11 sampleData) '()))))
