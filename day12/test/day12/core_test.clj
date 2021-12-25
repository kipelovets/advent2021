(ns day12.core-test
  (:require [clojure.test :refer :all]
            [day12.core :refer :all]))

(def example-data-string "start-A
start-b
A-c
A-b
b-d
A-end
b-end")

(deftest test-parse-input
  (testing "example data"
    (is (=
         [["start" "A"] ["start" "b"] ["A" "c"] ["A" "b"] ["b" "d"] ["A" "end"] ["b" "end"]]
         (parse-input example-data-string)))))

(def example-data
  (parse-input example-data-string))

(def example-data-2
  (parse-input "dc-end
HN-start
start-kj
dc-start
dc-HN
LN-dc
HN-end
kj-sa
kj-HN
kj-dc"))

(def example-data-3
  (parse-input "fs-end
he-DX
fs-he
start-DX
pj-DX
end-zg
zg-sl
zg-pj
pj-he
RW-he
fs-DX
pj-RW
zg-RW
start-pj
he-WI
zg-he
pj-fs
start-RW"))

(deftest test-neighbours
  (testing "example data"
    (is (=
         ["A" "b"]
         (neighbours example-data
                     "start")))
    (is (=
         ["start" "c" "b" "end"]
         (neighbours example-data
                     "A")))))

(deftest test-allowed-step
  (testing "test data"
    (is (allowed-step? ["start"] "A"))
    (is (allowed-step? ["start" "A"] "b"))
    (is (allowed-step? ["start" "A" "b"] "A"))
    (is (not (allowed-step? ["start" "b" "A"] "b")))))

(def real-input (parse-input (slurp "input")))

(deftest test-paths
  (testing "example data"
    (is (=
         10
         (count (paths allowed-step? example-data "start" "end")))))
  (testing "example data 2"
    (is (=
         19
         (count (paths allowed-step? example-data-2 "start" "end")))))
  (testing "example data 3"
    (is (=
         226
         (count (paths allowed-step? example-data-3 "start" "end")))))
  (testing "solution part 1"
    (is (=
         5252
         (count (paths allowed-step? real-input "start" "end"))))))

(deftest test-no-duplicate-small-caves
  (testing "test data"
    (is (no-duplicate-small-caves? ["start"]))
    (is (not (no-duplicate-small-caves? ["a" "a"])))))

(deftest test-alowed-step-2
  (testing "test data"
    (is (allowed-step-2? ["start"] "A"))
    (is (not (allowed-step-2? ["start" "A" "a" "a"] "a")))
    (is (allowed-step-2? ["start" "a" "a"] "b"))
    (is (not (allowed-step-2? ["start" "a" "a" "b"] "b")))))

(deftest test-paths-part-2
  (testing "example-data"
    (is (=
         36
         (count (paths allowed-step-2? example-data "start" "end")))))
  (testing "example-data-2"
    (is (=
         103
         (count (paths allowed-step-2? example-data-2 "start" "end")))))
  (testing "example-data-3"
    (is (=
         3509
         (count (paths allowed-step-2? example-data-3 "start" "end")))))
  (testing "solution part 2"
    (is (=
         147784
         (count (paths allowed-step-2? real-input "start" "end"))))))
