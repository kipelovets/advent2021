import * as fs from "fs";

const lines = fs.readFileSync("data/day1.txt", "utf8").split("\n");
const n: number[] = lines.map((s) => Number.parseInt(s));

const result = n.reduce((prev, _, ind) => {
  if (ind < 3) {
    return prev;
  }

  if (n[ind] + n[ind - 1] + n[ind - 2] > n[ind - 1] + n[ind - 2] + n[ind - 3]) {
    return prev + 1;
  }

  return prev;
}, 0);

console.log(`Day 1.2: ${result}`);
