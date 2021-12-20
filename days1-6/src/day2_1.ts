import * as fs from "fs";

const lines = fs.readFileSync("data/day2.txt", "utf8").split("\n");
const n: [string, number][] = lines.map((s) => {
  const [dest, num] = s.split(" ");

  return [dest, Number.parseInt(num)];
});

const result = n.reduce(
  (accum, curr) => {
    const [dir, num] = curr;
    const [pos, depth] = accum;
    switch (true) {
      case dir === "forward":
        return [pos + num, depth];
      case dir === "up":
        return [pos, depth - num];
      case dir === "down":
        return [pos, depth + num];
      default:
        console.log(`Unknown command ${dir}`);
    }

    return [pos, depth];
  },
  [0, 0]
);
const [pos, depth] = result;

console.log(`Day 2.1: ${pos * depth}`);
