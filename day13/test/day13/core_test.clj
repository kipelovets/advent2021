(ns day13.core-test
  (:require [clojure.test :refer :all]
            [day13.core :refer :all]))

(def example-data "6,10
0,14
9,10
0,3
10,4
4,11
6,0
6,12
4,1
0,13
10,12
3,4
3,0
8,4
1,10
2,14
8,10
9,0

fold along y=7
fold along x=5")

(deftest test-parse-input
  (testing "example input"
    (is (=
         [[[6 10]
           [0 14]
           [9 10]
           [0 3]
           [10 4]
           [4 11]
           [6 0]
           [6 12]
           [4 1]
           [0 13]
           [10 12]
           [3 4]
           [3 0]
           [8 4]
           [1 10]
           [2 14]
           [8 10]
           [9 0]]
          [["y" 7] ["x" 5]]]
         (parse-input example-data)))))

(def example-input
  (parse-input example-data))

(def example-points
  (first example-input))

(deftest test-print-paper
  (testing "example data"
    (is (=
         "...#..#..#.
....#......
...........
#..........
...#....#.#
...........
...........
...........
...........
...........
.#....#.##.
....#......
......#...#
#..........
#.#........"
         (print-paper example-points)))))

(def real-input (parse-input (slurp "input")))
(def points (first real-input))
(def folds (last real-input))

(deftest test-fold
  (testing "example data"
    (let [folded (fold example-points ["y" 7])]
      (is (=
           "#.##..#..#.
#...#......
......#...#
#...#......
.#.#..#.###"
           (print-paper folded)))
      (is (=
           17
           (count folded)))
      (is (=
           "#####
#...#
#...#
#...#
#####"
           (print-paper (fold folded ["x" 5]))))))

  (testing "real input - solution part 1"
    (is (=
         693
         (count (fold points (first folds))))))

  (testing "real input - solution part 2"
    (is (=
         "#..#..##..#....####.###...##..####.#..#
#..#.#..#.#.......#.#..#.#..#....#.#..#
#..#.#....#......#..#..#.#..#...#..#..#
#..#.#....#.....#...###..####..#...#..#
#..#.#..#.#....#....#.#..#..#.#....#..#
.##...##..####.####.#..#.#..#.####..##."
         (print-paper (reduce (fn [acc el]
                                (fold acc el))
                              (cons points folds)))))))

