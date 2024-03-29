(ns day9.core-test
  (:require [clojure.test :refer :all]
            [day9.core :refer :all]))

(def example-input "2199943210
3987894921
9856789892
8767896789
9899965678")

(deftest test-parse-input
  (testing "parse-input example-input"
    (let [parsed (parse-input example-input)]
      (is (=
           5
           (count parsed)))
      (is (=
           10
           (count (first parsed)))))))

(deftest test-get-height
  (testing "get-height example-input"
    (let [map (parse-input example-input)]
      (is (= max-height (get-height map [-1 -1])))
      (is (= max-height (get-height map [100 100])))
      (is (= max-height (get-height map [-1 0])))
      (is (= max-height (get-height map [0 -1])))
      (is (= 2 (get-height map [0 0])))
      (is (= 0 (get-height map [0 9])))
      (is (= 9 (get-height map [4 2]))))))

(deftest test-low-point?
  (testing "low-point? example input"
    (let [caves (parse-input example-input)]
      (is (= false (low-point? caves [0 0])))
      (is (= true (low-point? caves [0 1])))
      (is (= true (low-point? caves [0 9])))
      (is (= true (low-point? caves [2 2])))
      (is (= true (low-point? caves [4 6]))))))

(def example-caves (parse-input example-input))
(def real-caves (parse-input (slurp "input")))

(deftest test-low-points-coords
  (testing "low-points-coords example input"
    (let [points (low-points-coords example-caves)]
      (is (=
           [[0 1] [0 9] [2 2] [4 6]]
           points)))))

(deftest test-low-points
  (testing "low-points example input"
    (is (=
         [1 0 5 5]
         (low-points (parse-input example-input)))))
  (testing "low-points real input first line"
    (is (=
         [2 1 1 3 7 0 1 7 4 7 2 0 4]
         (low-points [(nth real-caves 0)])))))

(deftest test-risk-level
  (testing "risk-level example input"
    (is (=
         15
         (risk-level (parse-input example-input)))))

  ;; 1508 too high

  (testing "risk-level real input"
    (is (=
         478
         (risk-level (parse-input (slurp "input")))))))

(deftest test-in-flow-ads-coords
  (testing "in-flow-ads-coords"
    (is (=
         [[0 0]]
         (in-flow-adj-coords example-caves [0 1])))
    (is (=
         [[1 0]]
         (in-flow-adj-coords example-caves [0 0])))
    (is (=
         []
         (in-flow-adj-coords example-caves [1 0])))))

(deftest test-basin-coords
  (testing "basin-coords"
    (is (=
         [[0 1] [0 0] [1 0]]
         (basin-coords example-caves [0 1])))
    (is (=
         [[0 9] [0 8] [1 9] [0 7] [1 8] [2 9] [0 6] [0 5] [1 6]]
         (basin-coords example-caves [0 9])))))

(deftest test-largest-basins-risk
  (testing "largest-basins-risk example"
    (is (=
         1134
         (largest-basins-risk example-caves))))
  (testing "largest-basins-risk real input"
    (is (=
         1327014
         (largest-basins-risk real-caves))))
  )