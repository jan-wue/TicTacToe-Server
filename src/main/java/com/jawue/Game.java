package com.jawue;

import com.jawue.shared.message.Message;
import com.jawue.shared.message.MoveResultMessage;
import com.jawue.shared.message.PlayerMoveMessage;
import com.jawue.shared.message.RequestMoveMessage;
import com.jawue.shared.Board;

public class Game extends Thread {
  PlayerConnection player1;
  PlayerConnection player2;
  Board board = new Board();


  public Game(PlayerConnection player1, PlayerConnection player2) {
    this.player1 = player1;
    this.player2 = player2;
  }

  public void run() {
    while (true) {
      player1.sendMessage(new RequestMoveMessage());
      PlayerMoveMessage playerMoveMessage = (PlayerMoveMessage) player1.receiveMessage();
      MoveResultMessage moveResultMessage = new MoveResultMessage(board, null);
      player1.sendMessage(moveResultMessage);
      player2.sendMessage(moveResultMessage);
      player2.sendMessage(new RequestMoveMessage());
      playerMoveMessage = (PlayerMoveMessage) player2.receiveMessage();
      moveResultMessage = new MoveResultMessage(board, null);
      player1.sendMessage(moveResultMessage);
      player2.sendMessage(moveResultMessage);


    }

  }
}
