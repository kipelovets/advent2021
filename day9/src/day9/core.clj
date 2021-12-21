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

(defn adj-coords [x y]
  [[(dec x) y]
   [x (dec y)]
   [(inc x) y]
   [x (inc y)]])

(defn low-point? [caves [x y]]
  (let [height (get-height caves [x y])
        adj-heights (map #(get-height caves %) (adj-coords x y))
        adj-lower (filter #(<= % height) adj-heights)]
    (= 0 (count adj-lower))))

(defn low-points-coords [caves]
  (let [coordsx (range (count caves))
        coordsy (range (count (nth caves 0)))
        coords-vec (map
                    (fn [x] (map (fn [y] [x y])
                                 (filter
                                  (fn [y] (low-point? caves [x y]))
                                  coordsy)))
                    coordsx)]
    (reduce concat coords-vec)))

(defn low-points [caves]
  (let [coords (low-points-coords caves)]
    (map (fn [[x y]] (nth (nth caves x) y)) coords)))

(defn risk-level [caves]
  (let [points (low-points caves)]
    (+
     (count points)
     (reduce + points))))

(defn in-flow-adj-coords [caves [x y]]
  (let [height (get-height caves [x y])
        coords (adj-coords x y)]
    (filter
     #(let [h (get-height caves %)]
        (and (>= h height) (< h 9)))
     coords)))

(defn- expand-basin [caves basin]
  (let [next-points (reduce concat (map #(in-flow-adj-coords caves %) basin))
        next-basin (distinct (concat basin next-points))]
    (if (>
         (count next-basin)
         (count basin))
      (expand-basin caves next-basin)
      basin)))

(defn basin-coords [caves low-point]
  (expand-basin caves [low-point]))

(defn largest-basins-risk [caves]
  (let [points (low-points-coords caves)
        basins (map #(basin-coords caves %) points)
        basin-sizes (map count basins)
        largest-basins (take 3 (sort > basin-sizes))]
    (reduce * largest-basins)))