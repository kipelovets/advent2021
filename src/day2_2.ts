import * as fs from "fs";

const lines = fs.readFileSync("data/day2.txt", "utf8").split("\n");
const n: [string, number][] = lines.map((s) => {
  const [dest, num] = s.split(" ");

  return [dest, Number.parseInt(num)];
});

const result = n.reduce(
  (accum, curr) => {
    const [dir, x] = curr;
    const [aim, pos, depth] = accum;

    switch (true) {
      case dir === "forward":
        return [aim, pos + x, depth + aim * x];
      case dir === "up":
        return [aim - x, pos, depth];
      case dir === "down":
        return [aim + x, pos, depth];
      default:
        console.log(`Unknown command ${dir}`);
    }

    return [aim, pos, depth];
  },
  [0, 0, 0]
);
const [_, pos, depth] = result;

console.log(`Day 2.2: ${pos * depth}`);
