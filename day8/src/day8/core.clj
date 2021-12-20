(ns day8.core
  (:require [clojure.string :as s]
            [clojure.set :as set]))

(def digit-segments {0 "abcefg"
                     1 "cf"
                     2 "acdeg"
                     3 "acdfg"
                     4 "bcdf"
                     5 "abdfg"
                     6 "abdefg"
                     7 "acf"
                     8 "abcdefg"
                     9 "abcdfg"})

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

(defn single-segment-stats [char line]
  (let [digits (doall (s/join "" (take 10 line)))]
    (count (re-seq (re-pattern char) digits))))

(def all-segments ["a" "b" "c" "d" "e" "f" "g"])

(defn segment-stats [line]
  (map #(single-segment-stats % line) all-segments))

(defn index-of [e coll]
  (first (keep-indexed #(if (= e %2) %1) coll)))

(defn find-of-length [len col]
  (first (filter #(= len (count %)) col)))

(defn clean-vals [s col]
  (s/replace s (re-pattern (str "[" (s/join "" (vals col)) "]")) ""))

(defn detect-mixed-segments [line]
  (let [signal-pattern (take 10 line)
        stats (segment-stats line)
        result {"b" (get all-segments (index-of 6 stats))
                "e" (get all-segments (index-of 4 stats))
                "f" (get all-segments (index-of 9 stats))}
        pattern-for-one (find-of-length 2 signal-pattern)
        pattern-for-four (find-of-length 4 signal-pattern)
        result (assoc result
                      "c" (clean-vals pattern-for-one result))
        result (assoc result
                      "d" (clean-vals pattern-for-four result))
        result (assoc result
                      "a" (clean-vals (find-of-length 3 signal-pattern) result))
        result (assoc result
                      "g" (clean-vals (find-of-length 7 signal-pattern) result))]
    result))

(defn replace-with-map [s col]
  (let [inv-map (set/map-invert col)]
    (s/join "" (sort (map #(get inv-map (str %)) s)))))

(defn decode-output-digits [line]
  (let [segments-map (detect-mixed-segments line)
        output-digits (take-last 4 line)
        unmixed-segments (map #(replace-with-map % segments-map) output-digits)
        inv-segments (set/map-invert digit-segments)
        digits (map #(get inv-segments %) unmixed-segments)]
    (Integer/parseInt (s/join "" (map str digits)))))