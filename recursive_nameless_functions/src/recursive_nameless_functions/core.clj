(ns recursive-nameless-functions.core
  (:require [recursive-nameless-functions.fib :as f])
  (:gen-class))

(use 'recursive-nameless-functions.fractorial)

(defn sample [f]
  (map f (range 1 10)))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!" )
  (println "normal fib:"  (sample f/fib))
  (println "fib-gen :"    (sample (f/fib-gen f/fib)))
  (println "fib-weird :"  (sample (f/fib-weird f/fib-weird)))
  (println "fib-nameless :"  (sample f/fib-nameless )))



