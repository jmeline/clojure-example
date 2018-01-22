(ns challenges.easy.21-nthElement)

;; #21
(defn my-solution
  "Pretty dumb solution but gets the job done"
  [lst index]
  (if (= 0 index)
    (first lst)
    (recur (rest lst) (dec index))))

(defn other-solution1
  [lst index]
  (first (drop index lst)))

(defn other-solution2
  [lst index]
  (->> lst (drop index)))

