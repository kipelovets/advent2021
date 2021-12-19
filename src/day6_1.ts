import * as fs from "fs";
import * as path from "path";
import * as os from "os";

const rows = fs
  .readFileSync("data/day6.txt", "utf8")
  .split("\n")
  .map((s) => s.trim());

const counters = rows[0].split(",").map((s) => parseInt(s));

type Flock = Record<number, number>;
let flock: Flock = {};

counters.forEach((c) => (flock[c] = (flock[c] || 0) + 1));

function print(f: Flock) {
  for (let i in f) {
    console.log(`${i}: ${f[i]}`);
  }
}
print(flock);

for (let day = 0; day < 256; day++) {
  const newFlock: Flock = {};
  for (let i in flock) {
    const count = parseInt(i);
    if (count === 0) {
      newFlock[8] = flock[i];
      newFlock[6] = (newFlock[6] || 0) + flock[i];
    } else {
      newFlock[count - 1] = (newFlock[count - 1] || 0) + flock[i];
    }
    
  }
  flock = newFlock;
  // console.log(`Day ${day + 1}`);
  // print(flock);
}

const count = (Object.keys(flock) as string[]).reduce(
  (accum, key) => accum + flock[parseInt(key)],
  0
);

console.log(`${path.basename(__filename)}: ${count}`);
