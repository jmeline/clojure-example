(ns rating-area-parser.core-test
  (:require [clojure.test :refer :all]
            [rating-area-parser.core :refer :all]))

(deftest rating-area-parser-testing
  (testing "Gets correct URL"
    (let [result "https://www.cms.gov/CCIIO/Programs-and-Initiatives/Health-Insurance-Market-Reforms/WA-gra.html"]
    (is (= (get-url "WA") result))))

  (testing "county-name-format"
    (is (= (county-name-format "Rating Area 1" "Cache" []) ["Rating Area 1" "Cache" " "]))
    (is (= (county-name-format "Rating Area 2" "Clark") ["Rating Area 2" "Clark" " "])))

  (testing "three-digit-zip-format"
    (is (= (three-digit-zip-format "Rating Area 2" "995" []) ["Rating Area 2" " " "995"]))
    (is (= (three-digit-zip-format "Rating Area 3" "445") ["Rating Area 3" " " "445"])))

  (testing "sanitize-and-normalize fn"
    (is (= ["Rating Area 3" " " "445"]
           (sanitize-and-normalize-data ["Rating Area 3 " " " "" "445"]))))

  (testing "build-xml-data-tag"
    (is (= {:tag :Cell :content [{:tag :Data :attrs {"ss:Type" "String"} :content ["Rating Area 1"]}]}
           (build-xml-data-tag "Rating Area 1")))
    (is (= {:tag :Cell :content [{:tag :Data :attrs {"ss:Type" "Number"} :content ["995"]}]}
           (build-xml-data-tag "995"))))

  (testing "build-xml-cell"
    (let [test-list '("Rating Area 1" "Boundary" " ")]
      (is (= {:tag :Row :content [{:tag :Cell :content [{:tag :Data :attrs {"ss:Type" "String"} :content ["Rating Area 1"]}]}
                                   {:tag :Cell :content [{:tag :Data :attrs {"ss:Type" "String"} :content ["Boundary"]}]}
                                   {:tag :Cell :content [{:tag :Data :attrs {"ss:Type" "String"} :content [" "]}]}]}
             (build-xml-cell test-list))))))

  (comment
    testing "build-xml-worksheet"
    (is (= {:tag :Worksheet :attrs {"ss:Name" "ID"}
            :content [{:tag :Table :content [{:tag :Row :content [{:tag :Cell :content (build-xml-cell state)}]}]}]})))


