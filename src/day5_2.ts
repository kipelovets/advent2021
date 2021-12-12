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

const lines: Line[] = rows
  .map((row) => {
    const [from, to] = row.split(" -> ");

    return {
      from: parsePoint(from),
      to: parsePoint(to),
    };
  })
  .filter(
    (l) =>
      l.from.x === l.to.x ||
      l.from.y === l.to.y ||
      Math.abs(l.to.y - l.from.y) === Math.abs(l.to.x - l.from.x)
  );

const matrix: number[][] = [];
for (let i = 0; i < 1000; i++) {
  matrix.push(Array(1000).fill(0));
}

function next(current: Point, to: Point): Point {
  return {
    x: to.x === current.x ? to.x : current.x + (to.x - current.x > 0 ? 1 : -1),
    y: to.y === current.y ? to.y : current.y + (to.y - current.y > 0 ? 1 : -1),
  };
}

lines.forEach((line) => {
  // console.log(`${line.from.x}, ${line.from.y} -> ${line.to.x}, ${line.to.y}`);
  for (let point = { ...line.from }; ; point = next(point, line.to)) {
    matrix[point.x][point.y]++;
    // console.log(`  ${point.x}, ${point.y}`);
    if (point.x === line.to.x && point.y === line.to.y) {
      break;
    }
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

// 7269 too low
