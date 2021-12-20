(ns day7.core
  (:require [clojure.string :as s]))

(defn parse-input [input]
  (let [vals (s/split input #",")
        vals (remove #(= "" %) vals)
        vals (map #(Integer/parseInt %) vals)]
    vals))

(defn fuel-to-move [where input]
  (let [vals (map #(Math/abs (- where %)) input)]
    (reduce + vals)))

(defn mean [coll]
  (nth (sort coll) (int (/ (count coll) 2))))

(defn fuel-to-move-2 [where input]
  (let [transform #(let
                    [diff (Math/abs (- where %))]
                     (/ (* diff (+ diff 1)) 2))
        vals      (map transform input)]
    (reduce + vals)))

(defn solution-part-2 [input]
  (apply min (map #(fuel-to-move-2 % input) (range (apply min input) (apply max input)))))