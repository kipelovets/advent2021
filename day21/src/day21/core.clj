(ns day21.core
  (:require [clojure.string :as str]))

(defn parse-line [line]
  (let [pattern #"Player \d starting position: (\d+)"
        matches (re-seq pattern line)]
    (Integer/parseInt (last (first matches)))))

(defn parse-input [data]
  (map parse-line (str/split-lines data)))

(defn dice-rolls []
  (map #(inc (rem % 100)) (range)))

(defn move-position [pos moves]
  (let [new-pos-uncut (mod (+ pos moves) 10)
        new-pos (if (= new-pos-uncut 0) 10 new-pos-uncut)]
    new-pos))

(defn play [rolls score pos]
  (let [moves (reduce + (take 3 rolls))
        rest-rolls (drop 3 rolls)
        new-pos (move-position pos moves)
        new-score (+ score new-pos)]
    [rest-rolls new-score new-pos]))

(def winning-score 1000)

(defn- winning-data [round rolls win-score [[score pos] other-player]]
  (let [[rest-rolls new-score new-pos] (play rolls score pos)
        [score-2] other-player
        new-round (+ 3 round)]
    (if (>= new-score win-score)
      [score-2 new-round new-score]
      (winning-data new-round rest-rolls win-score [other-player [new-score new-pos]]))))

(defn game [rolls pos1 pos2]
  (let [[loser-score rolls-count] (winning-data 0 rolls winning-score [[0 pos1] [0 pos2]])]
    (* loser-score rolls-count)))

(defn throw-possibilities []
  (let [sides (range 1 4)
        moves (flatten (map (fn [a]
                              (map (fn [b]
                                     (map (fn [c]
                                            (+ a b c))
                                          sides))
                                   sides))
                            sides))
        moves-counts (reduce (fn [acc el]
                               (assoc acc el (inc (or (get acc el) 0))))
                             (cons {} moves))]
    moves-counts))

(defn sum-maps [a b]
  (reduce (fn [acc [key val]]
            (assoc acc key (+ val (or (get acc key) 0))))
          (cons a b)))

(def all-throws (throw-possibilities))

(def count-win
  (memoize
   (fn [pos score pos2 score2]
     (if (>= score2 21)
       [0 1]
       (reduce #(mapv + %1 %2)
               (for [[moves universes] all-throws
                     :let [new-pos (move-position pos moves)
                           new-score (+ score new-pos)]]
                 (reverse (map #(* universes %) (count-win pos2 score2 new-pos new-score)))))))))