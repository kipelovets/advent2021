(ns day7.core-test
  (:require [clojure.test :refer :all]
            [day7.core :refer :all]))

(def sample-input "16,1,2,0,4,2,7,1,2,14")

(deftest test-input
  (testing "reading file"
    (let [parsed (parse-input (slurp "input"))]
      (is (= 1000 (count parsed)))
      (is (= [1101 1 29 67 1102] (take 5 parsed))))))

(deftest sample-fuel
  (testing "sample fuel matches webpage"
    (is (= 37 (fuel-to-move 2 (parse-input sample-input))))
    (is (= 41 (fuel-to-move 1 (parse-input sample-input))))
    (is (= 39 (fuel-to-move 3 (parse-input sample-input))))
    (is (= 71 (fuel-to-move 10 (parse-input sample-input))))
    (is (= 45 (fuel-to-move 5 (parse-input sample-input))))))

(deftest test-mean
  (testing "test mean"
    (is (= 2 (mean [1 2 3])))
    (is (= 2 (mean (parse-input sample-input))))))

(deftest actual-problem-1
  (testing "actual problem with real input"
    (is (= 328187 (let [vals (parse-input (slurp "input"))]
                    (fuel-to-move (mean vals) vals))))))

(deftest sample-fuel-2
  (testing "sample fuel part 2"
    (is (= 168 (fuel-to-move-2 5 (parse-input sample-input))))
    (is (= 206 (fuel-to-move-2 2 (parse-input sample-input))))))

(deftest part-2-example
  (testing "part 2 example"
    (is (= 168 (solution-part-2 (parse-input sample-input))))))

(deftest part-2-example
  (testing "part 2 input"
    (is (= 91257582 (let [input (parse-input (slurp "input"))]
                      (solution-part-2 input))))))

;; 100567317 too high
;; 91258780 too high