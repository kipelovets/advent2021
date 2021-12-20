import * as fs from "fs";

const lines = fs
  .readFileSync("data/day3.txt", "utf8")
  .split("\n")
  .map((s) => s.trim());

const mostCommonBit = (
  lns: string[],
  ind: number,
  preferOnes: boolean
): string => {
  const sum = lns.reduce(
    (accum, curr) => accum + (curr[ind] === "1" ? 1 : -1),
    0
  );

  if (preferOnes) {
    return sum >= 0 ? "1" : "0";
  } else {
    return sum >= 0 ? "0" : "1";
  }
};

const oxygen = parseInt(
  lines[0].split("").reduce((accum, _, ind) => {
    if (accum.length <= 1) {
      return accum;
    }
    const bit = mostCommonBit(accum, ind, true);
    const result = accum.filter((l) => l[ind] === bit);
    return result;
  }, lines)[0],
  2
);

const scrubber = parseInt(
  lines[0].split("").reduce((accum, _, ind) => {
    if (accum.length <= 1) {
      return accum;
    }
    const bit = mostCommonBit(accum, ind, false);
    const result = accum.filter((l) => l[ind] === bit);
    return result;
  }, lines)[0],
  2
);

console.log(`Day 3.2: ${oxygen * scrubber}`);
