(ns day11.core
  (:require [clojure.string :as str]
            [clojure.data :as data]
            [clojure.set :as set]))

(defn parse-input [raw-string]
  (map (fn [raw-line]
         (map #(Integer/parseInt %) (str/split raw-line #"")))
       (str/split-lines raw-string)))

(defn increase-energy [cavern]
  (map (fn [row] (map #(inc %) row)) cavern))

(defn find-2d-coords [col pred]
  (let [coordsx (range (count col))
        coordsy (range (count (nth col 0)))
        coords-vec (map
                    (fn [x] (map (fn [y] [x y])
                                 (filter
                                  (fn [y] (pred (nth (nth col x) y)))
                                  coordsy)))
                    coordsx)]
    (set (reduce concat coords-vec))))

(defn map-2d [col pred-coords]
  (let [coordsx (range (count col))
        coordsy (range (count (nth col 0)))]
    (map (fn [x] (map (fn [y] (pred-coords x y (nth (nth col x) y)))
                      coordsy))
         coordsx)))

(defn in?
  [coll elm]
  (some #(= elm %) coll))

(defn adj-coords [[x y]]
  [[(dec x) y]
   [x (dec y)]
   [(inc x) y]
   [x (inc y)]
   [(inc x) (inc y)]
   [(dec x) (dec y)]
   [(inc x) (dec y)]
   [(dec x) (inc y)]])

(defn count-in [col a]
  (count (filter #(= a %) col)))

(defn flash
  ([cavern] (flash cavern #{}))
  ([cavern flashed]
   (let [could-flash (find-2d-coords cavern #(> % 9))
         [should-flash] (data/diff could-flash flashed)]
     (if (= 0 (count should-flash))
       [cavern flashed]
       (let [should-flash-adj (reduce concat (map adj-coords should-flash))
             cavern-after-flash (map-2d cavern (fn [x y val] (+ val (count-in should-flash-adj [x y]))))]
         (flash cavern-after-flash (set/union flashed should-flash)))))))

(defn simulate-step [cavern]
  (let [cavern-increased (increase-energy cavern)
        [cavern-flashed flashed-coords] (flash cavern-increased)
        new-cavern (map-2d cavern-flashed #(if (in? flashed-coords [%1 %2])
                                             0
                                             %3))
        flashes (count flashed-coords)]
    [flashes new-cavern]))

(defn simulate-steps [initial-cavern steps]
  (loop [cavern initial-cavern
         step 0
         flashed 0]
    (if (= steps step)
      [flashed cavern]
      (let [[new-flashed new-cavern] (simulate-step cavern)]
        (recur new-cavern (inc step) (+ flashed new-flashed))))))

(defn step-when-all-flash [initial-cavern]
  (let [all-count (* (count initial-cavern) (count (nth initial-cavern 0)))]
    (loop [cavern initial-cavern
           step 1]
      (let [[new-flashed new-cavern] (simulate-step cavern)]
        (if (= new-flashed all-count)
          step
          (recur new-cavern (inc step)))))))