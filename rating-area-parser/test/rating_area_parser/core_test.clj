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
  (testing "determine-type"
    (is (= "Number" (determine-type "018"))
        (= "String" (determine-type "abc")))
    (is (= "String" (determine-type "Rating Area 1"))))

  (testing "build-xml-data-tag"
    (is (= {:tag :Cell :content [{:tag :Data :attrs {"ss:Type" "String"}}]}
           (build-xml-data-tag nil)))
    (is (= {:tag :Cell :content [{:tag :Data :attrs {"ss:Type" "String"} :content ["Rating Area 1"]}]}
           (build-xml-data-tag "Rating Area 1")))
    (is (= {:tag :Cell :content [{:tag :Data :attrs {"ss:Type" "Number"} :content ["995"]}]}
           (build-xml-data-tag "995"))))

  (testing "build-xml-row"
    (let [test-list (lazy-seq '("Rating Area 1" "Boundary" "996" " "))]
      (is (= {:tag :Row :content [{:tag :Cell :content [{:tag :Data :attrs {"ss:Type" "String"} :content ["Rating Area 1"]}]}
                                  {:tag :Cell :content [{:tag :Data :attrs {"ss:Type" "String"} :content ["Boundary"]}]}
                                  {:tag :Cell :content [{:tag :Data :attrs {"ss:Type" "Number"} :content ["996"]}]}
                                  {:tag :Cell :content [{:tag :Data :attrs {"ss:Type" "String"} :content [" "]}]}]}
             (build-xml-row test-list)))))

  (testing "build-xml-worsheet"
    (let [test-data {:WA '(("Rating Area 1" "King" " ")
                           ("Rating Area 2" "Clallam" " ")
                           ("Rating Area 2" "Cowlitz" " "))}
          test-options {:Options '(("Effective Year" "2018"))}]
      (= {:tag :Worksheet :attrs {"ss:Name" "Options"}
          :content [{:tag :Table :content [{:tag :Row :content [{:tag :Cell :content [(build-xml-data-tag "Effective Year")
                                                                                      (build-xml-data-tag "2018")]}]}]}]}
         (build-xml-worksheet (ffirst test-options) (second (first test-options))))
      (is (= {:tag :Worksheet :attrs {"ss:Name" "WA"}
              :content [{:tag :Table :content [{:tag :Row :content [{:tag :Cell :content [{:tag :Data :attrs {"ss:Type" "String"} :content ["Rating Area 1"]}]}
                                                                    {:tag :Cell :content [{:tag :Data :attrs {"ss:Type" "String"} :content ["King"]}]}
                                                                    {:tag :Cell :content [{:tag :Data :attrs {"ss:Type" "String"} :content [" "]}]}]}
                                               {:tag :Row :content [{:tag :Cell :content [{:tag :Data :attrs {"ss:Type" "String"} :content ["Rating Area 2"]}]}
                                                                    {:tag :Cell :content [{:tag :Data :attrs {"ss:Type" "String"} :content ["Clallam"]}]}
                                                                    {:tag :Cell :content [{:tag :Data :attrs {"ss:Type" "String"} :content [" "]}]}]}
                                               {:tag :Row :content [{:tag :Cell :content [{:tag :Data :attrs {"ss:Type" "String"} :content ["Rating Area 2"]}]}
                                                                    {:tag :Cell :content [{:tag :Data :attrs {"ss:Type" "String"} :content ["Cowlitz"]}]}
                                                                    {:tag :Cell :content [{:tag :Data :attrs {"ss:Type" "String"} :content [" "]}]}]}]}]}
             (build-xml-worksheet (ffirst test-data) (second (first test-data))))))))

  (comment
    testing "build-xml-worksheet"
    (is (= {:tag :Worksheet :attrs {"ss:Name" "ID"}
            :content [{:tag :Table :content [{:tag :Row :content [{:tag :Cell :content (build-row state)}]}]}]})))

