package com.jawue.message;

public class GameFinishedMessage extends Message {
WinnerResult winnerResult;

  public GameFinishedMessage(WinnerResult winnerResult) {
    this.winnerResult = winnerResult;
  }
}
