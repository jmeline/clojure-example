(ns challenges.easy.22-count-a-sequence)

;; #22
(defn solution1
  [x]
  (loop [items x cnt 0]
    (if (empty? items)
      cnt
      (recur (rest items) (inc cnt)))))

(defn solution2
  [x]
  (reduce (fn [c _] (inc c)) 0 x))

