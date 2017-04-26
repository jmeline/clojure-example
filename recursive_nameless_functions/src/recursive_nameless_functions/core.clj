(ns recursive-nameless-functions.core
  (:gen-class))

(use 'recursive-nameless-functions.fractorial)

(defn log [msg func]
  (println (str msg (func))))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!")
  (log "(fractorial 19): " #(fractorial 19))
  (log "((fractorial-gen fractorial) 19): " #((fractorial-gen fractorial) 19))
  (log "((fractorial-weird fractorial-weird) 19): " #((fractorial-weird fractorial-weird) 19))
  (log "(fractorial-no-names 19): " #(fractorial-no-names 19)))

