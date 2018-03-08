(ns clojure-lang.partial-test
  (:require  [clojure.test :refer :all]
             [clojure-lang.partials :as p]))

(def add20 (p/my-partial + 20))

(deftest partial-add20
  (are [x y] (= x y)
    (add20 3) 23
    (add20 4) 24
    (add20) 20))

(deftest partial-customfn
  (defn sampleFn [x y z]
    (assoc {} :x x :y y :z z))
  (def oneArg (p/my-partial sampleFn 5))
  (def twoArgs (p/my-partial sampleFn 2 5))
  (def threeArgs (p/my-partial sampleFn 2 5 3))
  (are [m v] (= m v)
    (twoArgs 9) {:x 2 :y 5 :z 9}
    (oneArg 2 3) {:x 5 :y 2 :z 3}
    (threeArgs) {:x 2 :y 5 :z 3}))

(defn dyingMessage [message]
  (clojure.string/join
   (map-indexed
    (fn [idx val]
      (if (= (mod idx 2) 0)
        (clojure.string/upper-case val)
        (clojure.string/lower-case val))) message)))

(defn lousy-logger
  [log-level message]
  (condp = log-level
    :warn (clojure.string/lower-case message)
    :emergency (clojure.string/upper-case message)
    :dying (dyingMessage message)))

(deftest partial-logger-warn
  (def warn (p/my-partial lousy-logger :warn))
  (is (= (warn "Red light ahead") "red light ahead")))

(deftest partial-logger-emergency
  (def emergency (p/my-partial lousy-logger :emergency))
  (is (= (emergency "Red light ahead") "RED LIGHT AHEAD")))

(deftest partial-logger-dying
  (def emergency (p/my-partial lousy-logger :dying))
  (is (= (emergency "Red light ahead") "ReD LiGhT AhEaD")))
