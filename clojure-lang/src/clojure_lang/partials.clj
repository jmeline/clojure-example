(ns clojure-lang.partials)

;; define my own partial
(defn my-partial
  [partialized-fn & args]
  (fn [& more-args]
    (apply partialized-fn (into args more-args))))
