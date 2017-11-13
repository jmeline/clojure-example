(ns beachbody-21-day-fix-container-plans.core
  (:gen-class))

(def weight 188)
(defn calorie-baseline [x] (* 11 x))
(defn maintenance-calories [x] (+ 400 x))
(defn calorie-target [x] (- x 750))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]

  (let [cb (calorie-baseline weight)
       mc (maintenance-calories cb)
       ct (calorie-target mc)]

  (println "Weight: " weight)
  (println "Calorie baseline: " cb)
  (println "Maintenance Calories: " mc)
  (println "Calorie target: " ct)))
