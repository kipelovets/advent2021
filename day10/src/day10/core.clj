(ns day10.core
  (:require [clojure.string :as str]))

(defn parse-input [data]
  (map #(str/split % #"") (str/split-lines data)))

(def pairs {"(" ")"
            "[" "]"
            "{" "}"
            "<" ">"})

(defn in?
  [coll elm]
  (some #(= elm %) coll))

(def corrupted-literal "CORRUPTED")

(defn stack-reducer
  ([] "")
  ([acc] acc)
  ([acc next]
   (let [already-corrupted (str/starts-with? acc corrupted-literal)]
     (if already-corrupted
       acc
       (let [result (if (in? (keys pairs) next)
                      (str acc next)
                      (let [prev (last acc)
                            prev-match (pairs (str prev))]
                        (if (= prev-match next)
                          (subs acc 0 (dec (count acc)))
                          (str corrupted-literal next))))]
         result)))))

(defn corrupted-chunk [line]
  (let [stack (reduce stack-reducer line)]
    (if (str/starts-with? stack corrupted-literal)
      (str (first (subs stack (count corrupted-literal)))))))

(defn points [line]
  (let [chunk (corrupted-chunk line)
        scores {")" 3 "]" 57 "}" 1197 ">" 25137}]
    (if (= nil chunk)
      0
      (scores chunk))))

(defn all-points [lines]
  (reduce + (map points lines)))