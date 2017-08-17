(ns challenges.core
  (:gen-class))

(declare cool)
(declare let-and-locals)

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!" (first args))
  (cool)
  (let-and-locals))

(defn cool [] (str "Im cool!"))
(defn let-and-locals
  []
  ;; When you want some lexically-scoped named values to use in a section of your code,
  ;; you can use the let expression:
  (let [width     10
        height    20
        thickness 2]
    (println "Hello from inside the `let`.")
    (* width height thickness));; => 40

  ;; These local names are symbols that refer directly to the values you set them to.
  ;; You can re-set the symbols in the binding vector multiple times
  ;; (building it up into the value you need), if you find it useful:

  (let [x 2 x (* x x) x (inc x)] (println "local names: " x))) ;; => 5


(defn data-structures
  []
  (println "list: "     (list 1 2 3))                 ;; => '(1 2 3)
  (println "vector: "   (vector 1 2 3))               ;; => [1 2 3]
  (println "hash-map: " (hash-map :a 1 :b 2))         ;; => {:a 1 :b 2}
  (println "hash-set "  (hash-set :a :c :b :b :a))    ;; => #{:a :b :c}

  (def my-vec [1 2 3])
  (set my-vec)          ;; => #{1 2 3}
  (def my-map {:a 1 :b 2})
  (vec my-map)          ;; => [[:a 1] [:b 2]]
  (flatten (vec my-map)) ;; => (:a 1 :b 2)
)

;; (
;;   (def games [:chess :checkers :backgammon :cards])
;;   (let [game-a (games 0)
;;         game-b (games 1)
;;         game-c (games 2)
;;         game-d (games 3)]
;;     ;...
;;     )
;;
;;   ;; This can be simplified using the syntactic sugar called destructuring
;;   (let [[game-a game-b game-c game-d] games]
;;     ;...
;;     )
;;   )
