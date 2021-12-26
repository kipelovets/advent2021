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
         ["NC" "CN"]
         (apply-rules-pair "NN" example-rules)))))

(deftest test-apply-rules
  (testing "example data"
    (let [result (apply-rules-count (extract-pairs example-poly) example-rules 10)]
      (is (=
           1588
           (answer result "B"))))))

(deftest test-solution-part1
  (testing "example data"
    (is (=
         1588
         (solution example-input 10)))))

(def real-input (parse-input (slurp "input")))
(def real-poly (first real-input))
(def real-rules (last real-input))

(deftest test-real-input
  (testing "extracted data"
    (is (=
         "OFSNKKHCBSNKBKFFCVNB"
         (first real-input)))
    (is (=
         100
         (count (last real-input))))))

(deftest test-answer
  (testing "test data"
    (is (=
         1
         (answer (extract-pairs "NNCB") "B"))))

  (testing "part 2 example data"
    (is (=
         2188189693529
         (solution example-input 40)))))

(deftest test-part1
  (testing "solution part 1"
    (is (=
         2768
         (solution real-input 10)))))

(deftest test-part2
  (testing "solution part 2"
    (is (=
         2914365137499
         (solution real-input 40)))))

(deftest test-extract-pairs
  (testing "test data"
    (is (=
         {"CB" 1, "NC" 1, "NN" 1}
         (extract-pairs "NNCB"))))
  (testing "example data"
    (is (=
         {"BK" 1
          "KK" 1
          "KF" 1
          "KB" 1
          "CV" 1
          "FF" 1
          "NB" 1
          "VN" 1
          "FC" 1
          "KH" 1
          "CB" 1
          "FS" 1
          "SN" 2
          "HC" 1
          "NK" 2
          "BS" 1
          "OF" 1}
         (extract-pairs real-poly)))))