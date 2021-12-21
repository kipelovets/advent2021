(ns day10.core-test
  (:require [clojure.test :refer :all]
            [day10.core :refer :all]))

(def example-input "[({(<(())[]>[[{[]{<()<>>
[(()[<>])]({[<{<<[]>>(
{([(<{}[<>[]}>{[]{[(<()>
(((({<>}<{<{<>}{[]{[]{}
[[<[([]))<([[{}[[()]]]
[{[{({}]{}}([{[{{{}}([]
{<[[]]>}<{[{[{[]{()[[[]
[<(<(<(<{}))><([]([]()
<{([([[(<>()){}]>(<<{{
<{([{{}}[<[[[<>{}]]]>[]]")

(def example-nav (parse-input example-input))
(def real-nav (parse-input (slurp "input")))

(deftest all-tests
  (testing "parse-input example input"
    (let [nav example-nav]
      (is (=
           10
           (count nav)))
      (is (=
           24
           (count (nth nav 0))))))

  (testing "stack-reducer"
    (is (= "" (stack-reducer "[" "]")))
    (is (= "[<" (stack-reducer "[" "<")))
    (is (= "CORRUPTED>" (stack-reducer "[" ">"))))

  (testing "corrupted?"
    (is (= nil (corrupted-chunk [])))
    (is (= nil (corrupted-chunk ["(" ")"])))
    (is (= nil (corrupted-chunk ["(" "["])))
    (is (= nil (corrupted-chunk ["(" "[" "]" "<"])))
    (is (= "]" (corrupted-chunk ["(" "]"]))))

  (testing "corrupted in example"
    (let [line (nth example-nav 2)]
      (is (=
           "}"
           (corrupted-chunk line))))

    (is (=
         [nil nil "}" nil ")" "]" nil ")" ">" nil]
         (map corrupted-chunk example-nav))))

  (testing "points"
    (is (= 26397 (all-points example-nav)))
    (is (= 271245 (all-points real-nav)))))