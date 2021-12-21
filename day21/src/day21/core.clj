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

(defn play [rolls score pos]
  (let [moves (reduce + (take 3 rolls))
        rest-rolls (drop 3 rolls)
        new-pos-uncut (rem (+ pos moves) 10)
        new-pos (if (= new-pos-uncut 0) 10 new-pos-uncut)
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

(defn winner-from-winning-data [_ rolls-count]
  (let [rounds-played (/ rolls-count 3)]
    (if (= 1 (rem rounds-played 2))
      1
      2)))

(defn winner [rolls pos1 pos2]
  (let [[_ rolls-count] (winning-data 0 rolls winning-score [[0 pos1] [0 pos2]])]
    (winner-from-winning-data _ rolls-count)))

(defn to-ternary [acc num]
  (if (= 0 num)
    acc
    (to-ternary (cons (rem num 3) acc) (int (/ num 3)))))

(defn universe [num]
  (concat (map inc (to-ternary [] num))
          (repeat 1)))

(defn universe-winner [seed pos1 pos2]
  (let [rolls (universe seed)
        entropy (count (to-ternary [] seed))
        [_ rolls-count] (winning-data 0 rolls 21 [[0 pos1] [0 pos2]])
        winner (winner-from-winning-data _ rolls-count)]
    [winner entropy rolls-count]))

(defn multiverse-winners [pos1 pos2]
  (let [wins (take-while
              (fn [[_ entropy rolls-count]] (< (inc entropy) rolls-count))
              (map #(universe-winner % pos1 pos2) (range)))
        wins1 (count (filter (fn [[winner]] (= 1 winner)) wins))
        wins2 (- (count wins) wins1)]
    (println (last wins))
    [wins1 wins2]))