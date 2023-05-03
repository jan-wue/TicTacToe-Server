package com.jawue.shared;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.jawue.shared.message.MoveResultMessage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@JsonSerialize
public class Board {
  String[][] board = new String[3][3];
  private final String emptyFieldSymbol = "-";

  @Override
  public String toString() {
    return Arrays.deepToString(board);
  }



  public void print() {
    this.initialize();
    System.out.println("       |     |");
    for (int i = 0; i < 3; i++) {

      String newLine = "";

      System.out.print(i + " ");

      for (int j = 0; j < 3; j++) {
        newLine = newLine + "  " + (this.board[i][j]);

        if (j != 2) {

          newLine = newLine + "  |";
        }
      }
      System.out.println(newLine);

      if (i != 2) {

        System.out.println("  -----|-----|-----    ");
      }
    }

    System.out.println("       |     |");
    System.out.println("\n   " + " A " + "    B " + "    C  ");
  }

  public void initialize() {

    for(int i = 0; i < this.board.length; i++) {
      for(int j = 0; j < this.board[i].length; j++) {
        if(board[i][j] == null) {
          board[i][j] = emptyFieldSymbol;
        }
      }
    }
  }

  public void fill(PlayerMove move) {
    int rowIndex =  Character.getNumericValue(move.getRow());
    int columnIndex = move.getColumn() - 'A';
    this.board[rowIndex][columnIndex] = "X";
  }
  public MoveResultMessage validateMove(PlayerMove playerMove) {
    final List<Character> VALIDROWCHARS = Collections.unmodifiableList(new ArrayList<Character>(Arrays.asList('0', '1', '2')));
    final List<Character> VALIDCOLUMNCHARS = Collections.unmodifiableList(new ArrayList<Character>(Arrays.asList('A', 'B', 'C')));

    if(!VALIDROWCHARS.contains(playerMove.getRow())) {
      MoveResultMessage errorMessage = new MoveResultMessage();
      errorMessage.setErrorMessage("Invalid Input !!!  please try again, type 2 Characters, one for the Row and one for the Column");
      return errorMessage;
    }
    if(!VALIDCOLUMNCHARS.contains(playerMove.getColumn())) {
      MoveResultMessage errorMessage = new MoveResultMessage();
      errorMessage.setErrorMessage("Invalid Input !!!  please try again, type 2 Characters, one for the Row and one for the Column");
      return errorMessage;
    }
    int rowIndex = Character.getNumericValue(playerMove.getRow());
    int columnIndex = playerMove.getColumn() - 'A';
    if(this.board[rowIndex][columnIndex] == emptyFieldSymbol  ) {
      MoveResultMessage errorMessage = new MoveResultMessage();
      errorMessage.setErrorMessage("your field is already filled, please choose an empty field");
      return errorMessage;
    }
    
  }
}
