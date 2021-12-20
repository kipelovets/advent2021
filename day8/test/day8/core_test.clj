(ns day8.core-test
  (:require [clojure.test :refer :all]
            [day8.core :refer :all]))

(def sample-input "acedgfb cdfbe gcdfa fbcad dab cefabd cdfgeb eafb cagedb ab | cdfeb fcadb cdfeb cdbaf")
(def sample-input-2 "be cfbegad cbdgef fgaecd cgeb fdcge agebfd fecdb fabcd edb | fdgacbe cefdb cefbgd gcbe
edbfga begcd cbg gc gcadebf fbgde acbgfd abcde gfcbed gfec | fcgedb cgb dgebacf gc
fgaebd cg bdaec gdafb agbcfd gdcbef bgcad gfac gcb cdgabef | cg cg fdcagb cbg
fbegcd cbd adcefb dageb afcb bc aefdc ecdab fgdeca fcdbega | efabcd cedba gadfec cb
aecbfdg fbg gf bafeg dbefa fcge gcbea fcaegb dgceab fcbdga | gecf egdcabf bgf bfgea
fgeab ca afcebg bdacfeg cfaedg gcfdb baec bfadeg bafgc acf | gebdcfa ecba ca fadegcb
dbcfg fgd bdegcaf fgec aegbdf ecdfab fbedc dacgb gdcebf gf | cefg dcbef fcge gbcadfe
bdfegc cbegaf gecbf dfcage bdacg ed bedf ced adcbefg gebcd | ed bcgafe cdgba cbgef
egadfb cdbfeg cegd fecab cgb gbdefca cg fgcdab egfdb bfceg | gbdfcae bgc cg cgb
gcafb gcf dcaebfg ecagb gf abcdeg gaef cafbge fdbac fegbdc | fgae cfgab fg bagce")

(def sample-input-3 "be cfbegad cbdgef fgaecd cgeb fdcge agebfd fecdb fabcd edb | fdgacbe cefdb cefbgd gcbe")

(deftest test-input
  (testing "reading sample 1"
    (let [parsed (parse-input sample-input)]
      (is (= 1 (count parsed)))
      (is (= 14 (count (first parsed))))
      (is (= ["acedgfb" "cdfbe" "gcdfa" "fbcad" "dab" "cefabd" "cdfgeb" "eafb" "cagedb" "ab" "cdfeb" "fcadb" "cdfeb" "cdbaf"] (first parsed)))))
  (testing "reading sample 2"
    (let [parsed (parse-input sample-input-2)]
      (is (= 10 (count parsed)))
      (is (= 14 (count (first parsed))))
      (is (= "be" (first (first parsed)))))))

;;   0:      1:      2:      3:      4:
;;  aaaa    ....    aaaa    aaaa    ....
;; b    c  .    c  .    c  .    c  b    c
;; b    c  .    c  .    c  .    c  b    c
;;  ....    ....    dddd    dddd    dddd
;; e    f  .    f  e    .  .    f  .    f
;; e    f  .    f  e    .  .    f  .    f
;;  gggg    ....    gggg    gggg    ....

;;   5:      6:      7:      8:      9:
;;  aaaa    aaaa    aaaa    aaaa    aaaa
;; b    .  b    .  .    c  b    c  b    c
;; b    .  b    .  .    c  b    c  b    c
;;  dddd    dddd    ....    dddd    dddd
;; .    f  e    f  .    f  e    f  .    f
;; .    f  e    f  .    f  e    f  .    f
;;  gggg    gggg    ....    gggg    gggg

;; 0 abcefg
;; 1 cf
;; 2 acdeg
;; 3 acdfg
;; 4 bcdf
;; 5 abdfg
;; 6 abdefg
;; 7 acf
;; 8 abcdefg
;; 9 abcdfg

;; 2 chars: 1
;; 3 chars: 7
;; 4 chars: 4
;; 5 chars: 2, 3, 5
;; 6 chars: 0, 6, 9
;; 7 chars: 8

(deftest test-sample-line
  (testing "sample line"
    (let [line (first (parse-input sample-input))]
      (is (= 0 (unique-count-line line))))

    (let [line (first (parse-input sample-input-3))]
      (is (= 2 (unique-count-line line))))))

(deftest test-sample
  (testing "sample full"
    (is (= 0 (unique-count (parse-input sample-input))))
    (is (= 26 (unique-count (parse-input sample-input-2))))
    (is (= 2 (unique-count (parse-input sample-input-3))))))

(deftest test-part-1
  (testing "part-1"
    (is (= 409 (unique-count (parse-input (slurp "input")))))))
