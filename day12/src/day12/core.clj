(ns day12.core
  (:require [clojure.string :as str]))

(defn parse-input [data]
  (map (fn [line]
         (str/split line #"-"))
       (str/split-lines data)))

(defn in?
  [coll elm]
  (some #(= elm %) coll))

(defn neighbours [caves point]
  (let [links (filter #(in? % point)
                      caves)]
    (map (fn [[a b]]
           (if (= a
                  point)
             b a))
         links)))

(defn allowed-step? [path node]
  (or (not (in? path node))
      (every? #(Character/isUpperCase %) node)))

(defn paths
  ([caves start end] (paths caves start end [start]))
  ([caves start end path]
   (if (= end (last path))
     [path]
     (let [curr-node (last path)
           curr-neighbours (neighbours caves
                                       curr-node)
           next-nodes (filter #(allowed-step? path %)
                              curr-neighbours)]
       (if (empty? next-nodes)
         []
         (reduce
          concat
          (map #(paths caves
                       start end (conj path %))
               next-nodes)))))))