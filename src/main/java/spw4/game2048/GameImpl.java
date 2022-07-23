package spw4.game2048;

import java.util.*;

public class GameImpl implements Game {

  private final static int SIZE = 4;
  private final static int WINNING_VALUE = 2048;

  private int score;
  private int moves;
  private int[][] board;
  private final Random random;

  public GameImpl() {
    this(new Random());
  }

  public GameImpl(Random random) {
    this.random = random;
    board = new int[SIZE][SIZE];
  }

  public int getMoves() {
    return moves;
  }

  public int getScore() {
    return score;
  }

  public int getValueAt(int x, int y) {
    return board[x][y];
  }

  public boolean isOver() {
    if (isWon())
      return true;

    for (int i = 0; i < SIZE; i++) {
      for (int j = 0; j < SIZE; j++) {
        if (board[i][j] == 0) {
          return false;
        }
      }
    }
    return true;
  }

  public boolean isWon() {
    for (int row = 0; row < SIZE; row++) {
      for (int col = 0; col < SIZE; col++) {
        if (board[row][col] == WINNING_VALUE)
          return true;
      }
    }
    return false;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("Moves: ").append(moves).append("    Score: ").append(score).append("\n");
    for (int i = 0; i < SIZE; i++) {
      for (int j = 0; j < SIZE; j++) {
        int val = getValueAt(i, j);
        if (val == 0)
          sb.append(padRight(".", 6));
        else
          sb.append(padRight(String.valueOf(val), 6));
      }
      sb.append("\n");
    }
    return sb.toString();
  }

  public static String padRight(String val, int n) {
    return String.format("%-" + n + "s", val);
  }

  public void initialize() {
    moves = 0;
    score = 0;

    for (int i = 0; i < SIZE; i++) {
      Arrays.fill(board[i], 0);
    }

    board[random.nextInt(SIZE)][random.nextInt(SIZE)] = 2;
    board[random.nextInt(SIZE)][random.nextInt(SIZE)] = 2;
  }


  public void initialize(int[][] board) {
    moves = 0;
    score = 0;
    this.board = board;
  }

  public void move(Direction direction) {
    moves++;
    shiftAllRows(direction);
    if (!isOver())
      placeNewTile();
  }

  private void placeNewTile() {
    while (true) {
      int nX = random.nextInt(SIZE);
      int nY = random.nextInt(SIZE);
      if (board[nX][nY] == 0) {
        if (random.nextInt(10) < 9) {
          board[nX][nY] = 2;
        } else {
          board[nX][nY] = 4;
        }
        return;
      }
    }
  }

  private void shiftAllRows(Direction direction) {
    switch (direction) {
      case up -> rotateCounterClockwise(3);
      case down -> rotateCounterClockwise(1);
      case left -> rotateCounterClockwise(2);
    }
    for (int i = 0; i < SIZE; i++) {
      shiftRowToRight(i);
    }
    switch (direction) {
      case up -> rotateCounterClockwise(1);
      case down -> rotateCounterClockwise(3);
      case left -> rotateCounterClockwise(2);
    }
  }

  private void shiftRowToRight(int rowNumber) {
    int mergedCnt = 0;
    for (int i = SIZE - 2; i >= 0; i--) {
      for (int col = (SIZE - 2 - mergedCnt); col >= 0; col--) {
        int act = board[rowNumber][col];
        if (act != 0) {
          if (act == board[rowNumber][col + 1]) {
            board[rowNumber][col + 1] *= 2;
            score += board[rowNumber][col + 1];
            board[rowNumber][col] = 0;
            mergedCnt++;
          } else if (board[rowNumber][col + 1] == 0) {
            board[rowNumber][col + 1] = act;
            board[rowNumber][col] = 0;
          }
        }
      }
    }
  }

  private void rotateCounterClockwise(int count) {
    for (int i = 0; i < count; i++) {
      transposeBoard();
      reverseRows();
    }
  }

  private void transposeBoard() {
    int[][] transposed = new int[SIZE][SIZE];
    for (int row = 0; row < SIZE; row++) {
      for (int col = 0; col < SIZE; col++) {
        transposed[col][row] = board[row][col];
      }
    }
    board = transposed;
  }

  private void reverseRows() {
    int[][] reversed = new int[SIZE][SIZE];
    for (int row = 0; row < SIZE; row++) {
      for (int col = 0; col < SIZE; col++) {
        reversed[col][row] = board[GameImpl.SIZE - 1 - col][row];
      }
    }
    board = reversed;
  }
}
