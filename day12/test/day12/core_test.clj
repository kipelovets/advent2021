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

(deftest test-paths
  (testing "example data"
    (is (=
         10
         (count (paths example-data "start" "end")))))
  (testing "example data 2"
    (is (=
         19
         (count (paths example-data-2 "start" "end")))))
  (testing "example data 3"
    (is (=
         226
         (count (paths example-data-3 "start" "end")))))
  (testing "solution part 1"
    (is (=
         5252
         (count (paths (parse-input (slurp "input")) "start" "end")))))
  )

