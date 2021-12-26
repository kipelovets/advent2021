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
      pair
      (str (first pair)
           (last rule)
           (last pair)))))

(defn apply-rules [poly rules]
  (apply str (for [ind (range (count poly))
                   :let [first (subs poly ind (inc ind))
                         second (subs poly (inc ind) (min (inc (inc ind)) (count poly)))
                         pair (str first second)]]
               (let [updated (apply-rules-pair pair rules)]
                 (subs updated 0 (min 2 (count pair)))))))

(defn apply-rules-count [poly rules steps]
  (loop [step 0
         p poly]
    (if (= step steps)
      p
      (recur (inc step)
             (apply-rules p rules)))))

(defn answer [poly]
  (let [stats (loop [res {}
                     remaining poly]
                (if (= "" remaining)
                  res
                  (let [ch (str (first remaining))]
                    (recur (assoc res ch (inc (or (get res ch) 0)))
                           (apply str (drop 1 remaining))))))
        sorted-stats (sort #(> (last %1) (last %2)) stats)
        [_ max-count] (first sorted-stats)
        [_ min-count] (last sorted-stats)]
    (- max-count min-count)))