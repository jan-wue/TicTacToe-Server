package com.jawue.shared.message;

import com.jawue.shared.Answer;

public class PlayAgainMessage extends Message {
 private String message;
 private Boolean playerAnswer;

  public PlayAgainMessage() {
  }

  public PlayAgainMessage(String message) {
    this.message = message;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public Boolean getPlayerAnswer() {
    return playerAnswer;
  }

  public void setPlayerAnswer(Boolean playerAnswer) {
    this.playerAnswer = playerAnswer;
  }
}
