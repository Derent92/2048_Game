package spw4.game2048;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GameImplTest {

  GameImpl gameInstance;

  @BeforeEach
  void setUp() {
    gameInstance = new GameImpl(new RandomStub(List.of(0, 0, 0, 3, 1, 2, 0, 0, 0, 1, 0, 2, 0, 3, 1, 0, 1, 1, 1, 2, 1, 3)));
    gameInstance.initialize();
  }

  @AfterEach
  void tearDown() {
    gameInstance = null;
  }

  @Test
  void gameConstructed() {
    assertNotNull(gameInstance);
  }

  @Test
  void newGame_IsNotOver() {
    assertFalse(gameInstance.isOver());
  }

  @Test
  void newGame_IsNotWon() {
    assertFalse(gameInstance.isWon());
  }

  @Test
  void newGame_getScore() {
    assertEquals(0, gameInstance.getScore());
  }

  @Test
  void newGame_MovesEqualsZero() {
    assertEquals(0, gameInstance.getMoves());
  }

  @Test
  void makeOneMove_MovesEqualsOne() {
    gameInstance.move(Direction.down);
    assertEquals(1, gameInstance.getMoves());
  }

  @Test
  void addTilesWithValueTwo() {
    gameInstance.move(Direction.right);
    assertEquals(4, gameInstance.getScore());
  }

  @Test
  void addRowWithTilesOfValueTwoOnly_ShiftRight() {
    int[][] board = new int[][]{{2, 2, 2, 2}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}};
    gameInstance.initialize(board);

    gameInstance.move(Direction.right);

    assertEquals(8, gameInstance.getScore());
  }

  @Test
  void addRowWithTilesOfValueTwoOnly_ShiftLeft() {
    int[][] board = new int[][]{{4, 4, 2, 2}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}};
    gameInstance.initialize(board);

    gameInstance.move(Direction.left);

    assertEquals(12, gameInstance.getScore());
  }

  @Test
  void addRowWithTilesOfValueTwoOnly_ShiftUp() {
    int[][] board = new int[][]{{4, 0, 0, 0}, {4, 0, 0, 0}, {2, 0, 0, 0}, {2, 0, 0, 0}};
    gameInstance.initialize(board);

    gameInstance.move(Direction.up);

    assertEquals(12, gameInstance.getScore());
  }

  @Test
  void addRowWithTilesOfValueTwoOnly_ShiftDown() {
    int[][] board = new int[][]{{4, 0, 0, 0}, {4, 0, 0, 0}, {2, 0, 0, 0}, {2, 0, 0, 0}};
    gameInstance.initialize(board);

    gameInstance.move(Direction.up);

    assertEquals(12, gameInstance.getScore());
  }

  @Test
  void getValueAt_ReturnsTwo() {
    int[][] board = new int[][]{{2, 2, 2, 2}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}};
    gameInstance.initialize(board);
    assertEquals(2, gameInstance.getValueAt(0, 0));
  }

  @Test
  void testIsWon() {
    int[][] board = new int[][]{{512, 512, 512, 512}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}};
    gameInstance.initialize(board);

    gameInstance.move(Direction.left);
    gameInstance.move(Direction.left);

    assertTrue(gameInstance.isWon());
  }

  @Test
  void newTileAfterMove_IsCreated() {
    int[][] board = new int[][]{{2, 2, 2, 2}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}};
    gameInstance.initialize(board);

    gameInstance.move(Direction.down);

    assertNotEquals(0, gameInstance.getValueAt(1, 2));
  }

  @Test
  void gameIsOver_PlayerLost() {
    int[][] board = new int[][]{{2, 4, 2, 4}, {4, 2, 4, 2}, {2, 4, 2, 4}, {4, 2, 4, 2}};
    gameInstance.initialize(board);

    assertTrue(gameInstance.isOver());
  }

  @Test
  void toString_NotEmptyString() {
    int[][] board = new int[][]{{2, 2, 2, 2}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}};
    gameInstance.initialize(board);

    assertNotEquals("", gameInstance.toString());
  }
}