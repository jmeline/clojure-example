(ns challenges.easy.38-maximum-value)

;; #38
;; maximum value

;; write a function which takes a variable number o parameters and returns the maximum value

(defn my-solution [& xs]
  (last (sort xs)))



