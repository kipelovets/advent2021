import * as fs from "fs";

const lines = fs.readFileSync("data/day1.txt", "utf8").split("\n");

const result = lines.reduce((prev, curr, ind) => {
  if (ind > 0 && Number.parseInt(curr) > Number.parseInt(lines[ind - 1])) {
    return prev + 1;
  }

  return prev;
}, 0);

console.log(`Day 1.1: ${result}`);