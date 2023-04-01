package com.jawue.message;

import com.jawue.Board;

public class MoveResultMessage extends Message {
  Board board;
  String errorMessage;
  public MoveResultMessage(Board board) {
    this.board = board;
  }

  public MoveResultMessage(Board board, String errorMessage) {
    this.board = board;
    this.errorMessage = errorMessage;
  }
}
