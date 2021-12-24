(ns day11.core-test
  (:require [clojure.test :refer :all]
            [day11.core :refer :all]))

(def example-data-string "11111
19991
19191
19991
11111")

(deftest test-parse-input
  (testing "example data"
    (is (=
         [[1 1 1 1 1]
          [1 9 9 9 1]
          [1 9 1 9 1]
          [1 9 9 9 1]
          [1 1 1 1 1]]
         (parse-input example-data-string)))))

(def example-data (parse-input example-data-string))

(deftest test-find-2d-coords
  (testing "example data"
    (is (=
         #{[1 1] [1 2] [1 3] [2 1] [2 3] [3 1] [3 2] [3 3]}
         (find-2d-coords example-data #(>= % 9))))))

(deftest test-map-2d
  (testing "test data"
    (is (=
         [[-1 1 1 1 1]
          [1 9 9 9 1]
          [1 9 1 9 1]
          [1 9 9 9 1]
          [1 1 1 1 1]]
         (map-2d example-data (fn [x y val] (if (= [0 0] [x y])
                                              -1
                                              val)))))))

(deftest test-adj-coords
  (testing "test data"
    (is (=
         [[-1 0] [0 -1] [1 0] [0 1] [1 1] [-1 -1] [1 -1] [-1 1]]
         (adj-coords [0 0])))))

(def step0 (parse-input "5483143223
2745854711
5264556173
6141336146
6357385478
4167524645
2176841721
6882881134
4846848554
5283751526"))
(def step1 (parse-input "6594254334
3856965822
6375667284
7252447257
7468496589
5278635756
3287952832
7993992245
5957959665
6394862637"))
(def step2 (parse-input "8807476555
5089087054
8597889608
8485769600
8700908800
6600088989
6800005943
0000007456
9000000876
8700006848"))

(deftest test-simulate-step
  (testing "example data"
    (is (=
         [9 [[3 4 5 4 3]
             [4 0 0 0 4]
             [5 0 0 0 5]
             [4 0 0 0 4]
             [3 4 5 4 3]]]
         (simulate-step example-data)))



    (is (=
         [0 step1]
         (simulate-step step0)))

    (is (=
         [35 step2]
         (simulate-step step1)))))


(deftest test-simulate-steps
  (testing "example data"
    (is (=
         204
         (first (simulate-steps step0 10))))
    (is (=
         1656
         (first (simulate-steps step0 100)))))

  (testing "solution part 1"
    (is (=
         1675
         (first (simulate-steps (parse-input (slurp "input")) 100))))))