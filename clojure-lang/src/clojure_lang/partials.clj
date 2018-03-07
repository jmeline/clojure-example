(ns clojure-lang.partials)

;; define my own partial
;; Returns a function provided N-1 args that asks for more arguments.
(defn my-partial
  [partialized-fn & args]
  (fn [& more-args]
    (apply partialized-fn
           (concat args more-args))))
