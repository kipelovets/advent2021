(ns day21.core-test
  (:require [clojure.test :refer :all]
            [day21.core :refer :all]))

(def example-input "Player 1 starting position: 4
Player 2 starting position: 8")

(def real-starting-pos (parse-input (slurp "input")))

(deftest test-parse-input
  (testing "parse-line"
    (is (=
         3
         (parse-line "Player 1 starting position: 3"))))


  (testing "example-input"
    (is (=
         [4 8]
         (parse-input example-input)))
    (is (=
         [3 7]
         real-starting-pos)))

  (testing "dice-rolls"
    (is (=
         [1 2 3 4 5]
         (take 5 (dice-rolls)))))

  (testing "play"
    (let [player-1 (play (dice-rolls) 0 4)
          player-2 (play (first player-1) 0 8)]

      (is (=
           [10 10]
           (rest player-1)))
      (is (=
           [3 3]
           (rest player-2)))))

  (testing "game"
    (is (=
         739785
         (game (dice-rolls) 4 8)))
    (is (=
         1006866
         (game (dice-rolls) (first real-starting-pos) (last real-starting-pos)))))

  (testing "winner"
    (is (=
         1
         (winner (dice-rolls) 4 8))))

;;   (testing "universe"
;;     (is (=
;;          [1 1 1 1 1]
;;          (take 5 (universe 0))))
;;     (is (=
;;          [2 2 2 1 1]
;;          (take 5 (universe 13))))
;;     (is (=
;;          [2 2 1 1 1]
;;          (take 5 (universe 12))))
;;     )

;;   (testing "multiverse-winners sample data"
;;     (is (=
;;          []
;;          (multiverse-winners 4 8))))
  )