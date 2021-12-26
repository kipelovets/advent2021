(ns day14.core
  (:require [clojure.string :as str]))

(defn parse-rule [line]
  (let [parts (str/split line #" -> ")]
    parts))

(defn parse-input [data]
  (let [lines (str/split-lines data)
        polymer (first lines)
        rules (subvec lines 2)]
    [polymer
     (map parse-rule rules)]))

(defn apply-rules-pair [pair rules]
  (let [rule (first (filter #(= pair (first %)) rules))]
    (if (nil? rule)
      [pair]
      [(str (first pair)
            (last rule))
       (str (last rule)
            (last pair))])))

(defn stat-get [stats key]
  (or (get stats key) 0))

(defn stat-add [stats key value]
  (assoc stats
         key
         (+ value
            (stat-get stats key))))

(defn extract-pairs [poly]
  (let [pairs (for [ind (range (count poly))
                    :let [first (subs poly ind (inc ind))
                          second (subs poly (inc ind) (min (inc (inc ind)) (count poly)))
                          pair (str first second)]]
                pair)
        pairs (filter #(= 2 (count %)) pairs)]
    (reduce (fn [acc elem]
              (stat-add acc elem 1))
            (cons {} pairs))))

(defn apply-rules [stats rules]
  (loop [ind 0
         res {}]
    (if (>= ind (count (keys stats)))
      res
      (let [pair (nth (keys stats) ind)
            count (get stats pair)
            new-pairs (apply-rules-pair pair rules)
            prev-counts (map (partial get res) new-pairs)
            new-counts (map #(+ (or % 0) count) prev-counts)
            new-res (apply assoc res (flatten (seq (zipmap new-pairs new-counts))))]
        (recur (inc ind) new-res)))))

(defn apply-rules-count [stats rules steps]
  (loop [step 0
         p stats]
    (if (= step steps)
      p
      (recur (inc step)
             (apply-rules p rules)))))

(defn answer [stats last-char]
  (let [first-chars-stats (loop [ind 0
                                 res {}]
                            (if (>= ind (count (keys stats)))
                              res
                              (let [key (nth (keys stats) ind)
                                    count (get stats key)
                                    first-char (str (first key))
                                    existing-count (or (get res first-char) 0)
                                    new-count (+ count existing-count)]
                                (recur (inc ind) (assoc res first-char new-count)))))
        char-stats (assoc first-chars-stats
                          last-char
                          (+ (or (get first-chars-stats last-char) 0)
                             1))
        sorted-stats (sort #(> (last %1) (last %2)) char-stats)
        [_ max-count] (first sorted-stats)
        [_ min-count] (last sorted-stats)]
    (- max-count min-count)))

(defn solution [[poly rules] steps]
  (answer (apply-rules-count (extract-pairs poly)
                             rules
                             steps)
          (str (last poly))))