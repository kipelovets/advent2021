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
  ([node-filter caves start end] (paths node-filter caves start end [start]))
  ([node-filter caves start end path]
   (if (= end (last path))
     [path]
     (let [curr-node (last path)
           curr-neighbours (neighbours caves
                                       curr-node)
           next-nodes (filter #(node-filter path %)
                              curr-neighbours)]
       (if (empty? next-nodes)
         []
         (reduce concat
                 (map #(paths node-filter
                              caves
                              start
                              end
                              (conj path %))
                      next-nodes)))))))

(defn no-duplicate-small-caves? [path]
  (let [small-caves (filter (fn [node]
                              (every? #(Character/isLowerCase %)
                                      node))
                            path)]
    (= (count small-caves)
       (count (distinct small-caves)))))

(defn allowed-step-2? [path node]
  (let [is-big-cave (every? #(Character/isUpperCase %) node)]
    (and (not (= "start" node))
         (or (not (in? path node))
             is-big-cave
             (and (= 1
                     (count (filter #(= node %)
                                    path)))
                  (no-duplicate-small-caves? path))))))
