(ns day14.core-test
  (:require [clojure.test :refer :all]
            [day14.core :refer :all]))

(def example-data "NNCB

CH -> B
HH -> N
CB -> H
NH -> C
HB -> C
HC -> B
HN -> C
NN -> C
BH -> H
NC -> B
NB -> B
BN -> B
BB -> N
BC -> B
CC -> N
CN -> C")

(deftest test-parse-input
  (testing "example data"
    (is (=
         ["NNCB"
          [["CH" "B"]
           ["HH" "N"]
           ["CB" "H"]
           ["NH" "C"]
           ["HB" "C"]
           ["HC" "B"]
           ["HN" "C"]
           ["NN" "C"]
           ["BH" "H"]
           ["NC" "B"]
           ["NB" "B"]
           ["BN" "B"]
           ["BB" "N"]
           ["BC" "B"]
           ["CC" "N"]
           ["CN" "C"]]]
         (parse-input example-data)))))

(def example-input (parse-input example-data))
(def example-poly (first example-input))
(def example-rules (last example-input))

(deftest test-apply-rules-pair
  (testing "example data"
    (is (=
         "NCN"
         (apply-rules-pair "NN" example-rules)))))

(deftest test-apply-rules
  (testing "example data"
    (is (=
         "NCNBCHB"
         (apply apply-rules example-input)))

    (is (=
         "NBBNBNBBCCNBCNCCNBBNBBNBBBNBBNBBCBHCBHHNHCBBCBHCB"
         (apply-rules-count example-poly example-rules 4)))

    (let [result (apply-rules-count example-poly example-rules 10)]
      (is (=
           3073
           (count result))))))

(def real-input (parse-input (slurp "input")))
(def real-poly (first real-input))
(def real-rules (last real-input))

(deftest test-answer
  (testing "test data"
    (is (=
         2
         (answer "NNNA"))))

  (testing "example data"
    (let [result (apply-rules-count example-poly example-rules 10)]
      (is (=
           1588
           (answer result)))))
  
  (testing "solution part 1"
    (is (=
         2768
         (answer (apply-rules-count real-poly real-rules 10)))))
  
  )