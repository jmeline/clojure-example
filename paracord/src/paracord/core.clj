(ns paracord.core
  (:gen-class))

(def PI 3.1415927)

(defn calculate [wrist-size bracelet-thickness]
  "The length of the bracelet =
    Wrist size
    + (3.14 * thickness of the bracelet)
    + (1/16 to 1/4)"
  (+ wrist-size (* PI bracelet-thickness)))

(defn -main
  "I calculate the length of the bracelet/how much cord i'll need"
  [& args]
  (println "Size: "(calculate
                     (read-string (first args))
                     (read-string (second args)))))

