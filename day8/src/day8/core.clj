(ns day8.core
  (:require [clojure.string :as s]))

(defn parse-line [line]
  (let [vals (s/split line #" ")]
    (remove #(= "|" %) vals)))

(defn parse-input [input]
  (let [lines (s/split-lines input)
        lines (remove #(= "" %) lines)]
    (map parse-line lines)))

(defn unique-count-line [line]
  (let [outputs (take-last 4 line)
        lengths (map count outputs)
        unique-lengths (filter #(contains? #{2 3 4 7} %) lengths)]
    (count unique-lengths)))

(defn unique-count [input]
  (let [counts (map unique-count-line input)]
    (reduce + counts)))