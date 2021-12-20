import * as fs from "fs";
import * as path from "path";

const rows = fs
  .readFileSync("data/day5.txt", "utf8")
  .split("\n")
  .map((s) => s.trim());

interface Point {
  x: number;
  y: number;
}

interface Line {
  from: Point;
  to: Point;
}

function parsePoint(s: string): Point {
  const [x, y] = s.split(",");
  return {
    x: parseInt(x),
    y: parseInt(y),
  };
}

const lines = rows
  .map((row) => {
    const [from, to] = row.split(" -> ");

    return {
      from: parsePoint(from),
      to: parsePoint(to),
    };
  })
  .filter((l) => l.from.x === l.to.x || l.from.y === l.to.y);

const matrix: number[][] = [];
for (let i = 0; i < 1000; i++) {
  matrix.push(Array(1000).fill(0));
}

lines.forEach((line) => {
  const length = line.to.x - line.from.x + (line.to.y - line.from.y);
  const inc = length > 0 ? 1 : -1;
  for (let i = 0; i !== length + inc; i += inc) {
    const point: Point = {
      x: line.from.x + (line.to.x != line.from.x ? i : 0),
      y: line.from.y + (line.to.y != line.from.y ? i : 0),
    };
    matrix[point.x][point.y]++;
  }
});

let count = 0;
for (let i = 0; i < 1000; i++) {
  for (let j = 0; j < 1000; j++) {
    if (matrix[i][j] > 1) {
      count++;
    }
  }
}

console.log(`${path.basename(__filename)}: ${count}`);

// 988 to low
