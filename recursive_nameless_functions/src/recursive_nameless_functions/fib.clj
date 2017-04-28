(ns recursive-nameless-functions.fib)

(defn fib [n]
  (cond
    (= 0 n) 0
    (= 1 n) 1
    :else (+ (fib (- n 1)) (fib (- n 2)))))

(defn fib-gen [f]
  (fn [n]
    (cond
      (= 0 n) 0
      (= 1 n) 1
      :else (+
              (f (- n 1))
              (f (- n 2))))))

(defn fib-weird [f]
  (fn [n]
    (cond
      (= 0 n) 0
      (= 1 n) 1
      :else (+
              ((f f) (- n 1))
              ((f f) (- n 2))))))

(def fib-nameless
  ((fn [f]
     (fn [n]
       (cond
         (= 0 n) 0
         (= 1 n) 1
         :else (+
                ((f f) (- n 1))
                ((f f) (- n 2))))))
   (fn [f]
     (fn [n]
       (cond
         (= 0 n) 0
         (= 1 n) 1
         :else (+
                ((f f) (- n 1))
                ((f f) (- n 2))))))))
