(ns clojure-for-brave-and-true.fwpd.core-test
  (:require [clojure-for-brave-and-true.fwpd.core :as sut]
            [clojure.test :refer :all]))

(def sampleData
  '({:name "Edward Cullen", :glitter-index 10}
    {:name "Bella Swan", :glitter-index 0}
    {:name "Charlie Swan", :glitter-index 0}
    {:name "Jacob Black", :glitter-index 3}
    {:name "Carlisle Cullen", :glitter-index 6}))

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
    (is (= (sut/filter-glitter-index 3 sampleData)
           '({:name "Edward Cullen" :glitter-index 10}
             {:name "Jacob Black" :glitter-index 3}
             {:name "Carlisle Cullen" :glitter-index 6})))
    (is (= (sut/filter-glitter-index 7 sampleData)
           '({:name "Edward Cullen" :glitter-index 10})))
    (is (= (sut/filter-glitter-index 11 sampleData) '())))
  (testing "additional exercises"
    (testing "filter-glitter-index-by-name"
      (is (= (sut/filter-glitter-index-by-name 3 sampleData)
             '("Edward Cullen" "Jacob Black" "Carlisle Cullen")))
      (is (= (sut/filter-glitter-index-by-name 10 sampleData)
             '("Edward Cullen"))))
    (testing "append"
      (let [updatedSampleData
            (sut/append {:name "Voldermort" :glitter-index 11} sampleData)]
        (is (= (first (sut/filter-glitter-index 11 updatedSampleData))
               {:name "Voldermort" :glitter-index 11}))))
    (testing "validate"
      (let [validations {:name (fn [x] (> (count x) 0))
                         :glitter-index (fn [x] (> x 0))}]
        (is (= (sut/validate validations (first sampleData))
               {:name "Edward Cullen" :glitter-index 10 :validations {:name true :glitter-index true}}))))
    (testing "map->csv"
      (is (= (sut/map->csv sampleData))))))
