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
    (is (= 26397 (all-corrupted-points example-nav)))
    (is (= 271245 (all-corrupted-points real-nav))))

  (testing "incomplete"
    (is (=
         [288957 5566 0 1480781 0 0 995444 0 0 294]
         (map #(incomplete-points %) example-nav))))

  (testing "all-incomplete-points"
    (is (=
         288957
         (all-incomplete-points example-nav)))
    (is (=
         1685293086
         (all-incomplete-points real-nav)))))