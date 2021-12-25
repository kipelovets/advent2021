(ns day13.core
  (:require [clojure.string :as str]))

(defn in?
  [coll elm]
  (some #(= elm %) coll))


(defn- parse-points [lines]
  (map (fn [line]
         (map #(Integer/parseInt %)
              (str/split line #",")))
       lines))

(defn- parse-folds [lines]
  (let [res (map (fn [line]
                   (let [[[_ axis val]] (re-seq #"fold along (.)=(.+)"
                                                line)]
                     [axis (Integer/parseInt val)]))
                 lines)]
    res))

(defn parse-input [data]
  (let [lines (str/split-lines data)
        [points folds] (split-with #(not (= "" %)) lines)
        folds (drop 1 folds)]
    [(parse-points points)
     (parse-folds folds)]))

(defn print-paper [points]
  (let [xmax (inc (apply max (map last points)))
        ymax (inc (apply max (map first points)))
        flat-paper (for [x (range xmax)
                         y (range ymax)]
                     (if (in? points [y x])
                       "#"
                       "."))
        paper (partition (/ (count flat-paper) xmax)
                         flat-paper)
        lines (map #(str/join "" %) paper)]
    (str/join "\n" lines)))

(defn fold-point [[x y] [axis fold-line]]
  [(if (and (= axis "x")
            (> x fold-line))
     (- (* 2 fold-line)
        x)
     x)
   (if (and (= axis "y")
            (> y fold-line))
     (- (* 2 fold-line)
        y)
     y)])

(defn fold [points fold]
  (distinct (map #(fold-point % fold)
                 points)))