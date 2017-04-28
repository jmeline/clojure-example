(ns recursive-nameless-functions.fractorial)

(defn fractorial [n]
  (if (zero? n)
    1
    (* n (fractorial (dec n)))))

(defn fractorial-gen [func]
  (fn [n]
    (if (zero? n)
      1
      (* n (func (dec n))))))

(defn fractorial-weird [func]
  (fn [n]
    (if (zero? n)
      1
      (* n ((func func) (dec n))))))

(def fractorial-no-names
  ((fn [func]
     (fn [n]
       (if (zero? n)
         1
         (* n ((func func) (dec n))))))
   (fn [func]
     (fn [n]
       (if (zero? n)
         1
         (* n ((func func) (dec n))))))))
