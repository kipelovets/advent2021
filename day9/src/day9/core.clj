(ns day9.core
  (:require [clojure.string :as str]))

(defn parse-input [input]
  (let [lines (str/split-lines input)
        rows (map #(str/split % #"") lines)]
    (map (fn [row] (map #(Integer/parseInt %) row)) rows)))

(def max-height 99)

(defn get-height [caves [x y]]
  (if (or (< x 0)
          (< y 0)
          (>= x (count caves))
          (>= y (count (nth caves 0))))
    max-height
    (nth (nth caves x) y)))

(defn low-point? [caves [x y]]
  (let [height (get-height caves [x y])
        adj-heights [(get-height caves [(dec x) y])
                     (get-height caves [x (dec y)])
                     (get-height caves [(inc x) y])
                     (get-height caves [x (inc y)])]
        adj-lower (filter #(<= % height) adj-heights)]
    (= 0 (count adj-lower))))

(defn low-points [caves]
  (let [coordsx (range (count caves))
        coordsy (range (count (nth caves 0)))]
    (flatten
     (map
      (fn [x] (map (fn [y] (nth (nth caves x) y))
                   (filter
                    (fn [y] (low-point? caves [x y]))
                    coordsy)))
      coordsx))))

(defn risk-level [caves]
  (let [points (low-points caves)]
    (+
     (count points)
     (reduce + points))))