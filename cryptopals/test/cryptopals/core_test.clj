(ns cryptopals.core-test
  (:require [clojure.test :refer :all]
            [cryptopals.core :refer :all]))

(deftest hex-functions
  (testing "decimal -> hex conversion"
    (is (= (dec->hex 921) "399"))
    (is (= (dec->hex 100) "64"))
    (is (= (dec->hex 188) "BC"))
    (is (= (dec->hex 590) "24E"))
    (is (= (dec->hex 1128) "468"))
    (is (= (dec->hex 1) "1"))
    (is (= (dec->hex 16) "10"))
    (is (= (dec->hex 175) "AF"))
    (is (= (dec->hex 256) "100"))
    (is (= (dec->hex 105166) "19ACE"))
    (is (= (dec->hex 16777215) "FFFFFF"))
    (is (= (dec->hex 12) "C")))

  (testing "hex -> decimal conversion"
    (is (= (hex->dec "1A") 26)))

  (testing "dec->hex-conversion"
    (is (= (dec->hex-conversion 10) "A"))
    (is (= (dec->hex-conversion 11) "B"))
    (is (= (dec->hex-conversion 12) "C"))
    (is (= (dec->hex-conversion 13) "D"))
    (is (= (dec->hex-conversion 14) "E"))
    (is (= (dec->hex-conversion 15) "F")))

  (testing "hex->dec-conversion"
    (is (= (hex->dec-conversion "A") 10))
    (is (= (hex->dec-conversion "B") 11))
    (is (= (hex->dec-conversion "C") 12))
    (is (= (hex->dec-conversion "D") 13))
    (is (= (hex->dec-conversion "E") 14))
    (is (= (hex->dec-conversion "F") 15)))

  (testing "exp"
    (is (= (exp 5 2) 25))
    (is (= (exp 5 0) 1)))
  )
