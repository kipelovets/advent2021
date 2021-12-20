import * as fs from "fs";

const lines = fs.readFileSync("data/day3.txt", "utf8").split("\n");

const gammaStats = lines.reduce(
  (accum, curr) => {
    const digits = curr.split("");
    return accum.map((acc, ind) => {
      if (digits[ind] === "1") {
        return acc + 1;
      } else {
        return acc - 1;
      }
    });
  },
  [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]
);

const gammaDigits = gammaStats.map((x) => (x > 0 ? "1" : "0"));
const gamma = parseInt(gammaDigits.join(""), 2);
const epsilon = parseInt(
  gammaDigits.map((x) => (x == "0" ? "1" : "0")).join(""),
  2
);

console.log(`Day 3.1: ${gamma * epsilon}`);
