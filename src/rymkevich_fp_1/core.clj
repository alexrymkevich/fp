(ns rymkevich-fp-1.core)

(def Ra 3)
(def Rb (* 1.5 Ra))

(defn sqr [x]
  (* x x))

(def alfa (/ 4 (sqr Ra)))
(def betta (/ 4 (sqr Rb)))


(defn calc-potential [distance]
    (Math/exp (- (* alfa distance))))

(defn calc-potential-with-betta [distance]
  (Math/exp (- (* betta distance))))



; work with file
(defn read-coord-from-line [str]
  (vector
   (hash-map :coord
             (map read-string
                  (butlast
                   (map clojure.string/trim
                        (clojure.string/split str #",")))))

      ))


(defn read-coordinates-from-file [fileName]
  (mapcat read-coord-from-line
          (line-seq
           (clojure.java.io/reader fileName)
       )))


;function distance
(defn euclidean-distance
  [c1 c2]
  ( reduce + (map sqr  (map - c1 c2) )))

(defn hamming-distance
  [c1 c2]
  (count (filter true? (map not= c1 c2))))




(defn calc-potentials [points method]
  (map #(calc-point-potential %1 points method) points))


(defn revise-potential [point kernel method]
  (assoc point :dist
       (- (:dist point))
       (* (:dist kernel))
       (calc-potential-with-betta
        (method (:coord point) (:coord kernel))))
       )


(defn revise-point-potentials [points kernel method]
  (reverse
     (sort-by :dist
              (map #(revise-potential %1 kernel method) points))
       ))


(defn calc-min-dist [point points method]
  (apply min (map #(method (:coord point) (:coord %1)) points)))


;algorithm
(defn process [points method]
  (let [initPotentials (reverse (sort-by :dist (calc-potentials points method)))
        firstKernel (first initPotentials)]
      (loop [kernels [firstKernel]
             elements (rest initPotentials)]
        (let [revisedPoints (revise-point-potentials elements (first kernels) method)
              nextKernel (first revisedPoints)]
            (cond
              (< (:dist nextKernel) (* 0.15 (:dist firstKernel)))
              (reverse (sort-by :dist kernels))
              (> (:dist nextKernel) (* 0.5 (:dist firstKernel))) (recur (cons nextKernel kernels) (rest revisedPoints))
              (>= (+ (/ (calc-min-dist nextKernel kernels method) Ra) (/ (:dist nextKernel) (:dist firstKernel))) 1) (recur (cons nextKernel kernels) (rest revisedPoints))
              :else (recur kernels (cons (assoc nextKernel :dist 0) (rest revisedPoints))))))))


; MAIN FUNCTION
(defn -main [fileName distanceMethod]
  (let [points (read-coordinates-from-file fileName)
        distance (if (= distanceMethod "h") hamming-distance euclidean-distance)]
 (process points distance)))


; FOR DEBUG

(-main "c:/Users/Alex/Downloads/clojure-1.6.0/rymkevich_fp_1/src/rymkevich_fp_1/glass.txt" "e")

; (read-coordinates-from-file "c:/Users/Alex/Downloads/clojure-1.6.0/rymkevich_fp_1/src/rymkevich_fp_1/butterfly.txt")


;(defn clusterEstimation
; ([]  (println "Incorrect start params"))
; ([points ]  (println "Incorrect start params"))
;  ([points  distanceMethod]
;    println (process points distanceMethod)))

