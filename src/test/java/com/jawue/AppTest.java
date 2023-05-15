package com.jawue;

import com.jawue.shared.Board;
import com.jawue.shared.PlayerMove;
import com.jawue.shared.message.GameSymbol;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest
        extends TestCase {
  /**
   * Create the test case
   *
   * @param testName name of the test case
   */
  public AppTest(String testName) {
    super(testName);
  }

  /**
   * @return the suite of tests being tested
   */
  public static Test suite() {

    return new TestSuite(AppTest.class);
  }

  /**
   * Rigourous Test :-)
   */
  public void testNewBoardShouldNotBeFull() {
    Board board = new Board();
    assertFalse(board.isBoardFull());
  }

  public void testFullBoardShouldBeFull() {
    Board board = new Board();
    for(int i = 0; i < board.getLength(); i++) {
      for(int j = 0; j < board.getLength(); j++) {
        PlayerMove playerMove = new PlayerMove(i, j);
        board.fill(playerMove, GameSymbol.O);
      }
    }
   assertTrue(board.isBoardFull());
  }
}
