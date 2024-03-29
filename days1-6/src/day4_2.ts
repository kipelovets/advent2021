import * as fs from "fs";

const lines = fs
  .readFileSync("data/day4.txt", "utf8")
  .split("\n")
  .map((s) => s.trim());

const moves: number[] = lines
  .shift()!
  .split(",")
  .map((s) => parseInt(s));

lines.shift();

interface Cell {
  num: number;
  marked: boolean;
}

function mark(board: Cell[][], num: number): Cell[][] {
  return board.map((line) =>
    line.map((c) => ({ ...c, marked: c.marked || c.num === num }))
  );
}

function isComplete(board: Cell[][]): boolean {
  const isCompleteLine = (line: Cell[]): boolean => {
    return line.filter((c) => c.marked).length === line.length;
  };

  for (let line of board) {
    if (isCompleteLine(line)) {
      return true;
    }
  }

  const rows = board[0].map((_, ind) => board.map((line) => line[ind]));
  for (let line of rows) {
    if (isCompleteLine(line)) {
      return true;
    }
  }

  return false;
}

function calculateScore(board: Cell[][], lastNumber: number): number {
  return (
    lastNumber *
    board
      .map((line) =>
        line.reduce((accum, cell) => accum + (cell.marked ? 0 : cell.num), 0)
      )
      .reduce((accum, curr) => accum + curr, 0)
  );
}

function printBoard(board: Cell[][]): void {
  console.log(
    board
      .map((line) =>
        line.map((c) => `${c.num}${c.marked ? "*" : ""}`).join(" ")
      )
      .join("\n")
  );
}

let boards: Cell[][][] = [];
let currentBoard: Cell[][] = [];
while (lines.length > 0) {
  const line = lines.shift()!;
  if (line === "") {
    boards.push(currentBoard);
    currentBoard = [];
    continue;
  }

  currentBoard.push(
    line
      .split(" ")
      .filter((s) => s.length > 0)
      .map((s) => ({ num: parseInt(s), marked: false }))
  );
}

for (let move of moves) {
  const wonBefore = boards.map((board) => isComplete(board));
  boards = boards.map((board) => mark(board, move));

  const won = boards.map((board) => isComplete(board));
  if (won.filter((w) => !!w).length === boards.length) {
    const firstNewWinner = won.findIndex((val, ind) => val !== wonBefore[ind]);
    if (firstNewWinner === -1) {
      throw new Error("Logic error");
    }
    console.log(`Day 4.2: ${calculateScore(boards[firstNewWinner], move)}`);
    break;
  }
}
