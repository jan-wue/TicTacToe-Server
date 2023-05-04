package com.jawue;

import com.jawue.shared.PlayerMove;
import com.jawue.shared.message.MoveResultMessage;
import com.jawue.shared.message.PlayerMoveMessage;
import com.jawue.shared.message.RequestMoveMessage;
import com.jawue.shared.Board;

public class Game extends Thread {
  PlayerConnection player1;
  PlayerConnection player2;


  public Game(PlayerConnection player1, PlayerConnection player2) {
    this.player1 = player1;
    this.player2 = player2;
  }

  public void run() {

    Board board = new Board();
    boolean gameIsNotFinished = true;
    while(gameIsNotFinished) {
      requestAndValidatesMove(board, player1, player2);
      requestAndValidatesMove(board, player2, player1);

    }

  }
  /** Sends a requestMove to the player, gets the playermove and validates the move if it is a valid move it will send the result to both players, if not it will keep promting the player for a valid move.
   **/

  /**
   * @param board
   * @param player
   */
  public void requestAndValidatesMove(Board board, PlayerConnection currentPlayer, PlayerConnection otherPlayer) {

    boolean isMoveValid = false;
    PlayerMove playerMove =  null;
    while (!isMoveValid) {

      currentPlayer.sendMessage(new RequestMoveMessage());
      PlayerMoveMessage playerMoveMessage = (PlayerMoveMessage) currentPlayer.receiveMessage();
       playerMove = playerMoveMessage.getPlayerMove();
      if (!board.isMoveValid(playerMove)) {

       MoveResultMessage moveResultMessage = new MoveResultMessage(board, "Move is not valid please type again");
        currentPlayer.sendMessage(moveResultMessage);
        continue;
      }
      if (board.isFieldOccupied(playerMove)) {
        MoveResultMessage moveResultMessage = new MoveResultMessage(board, "Field is already played, choose another field");
        currentPlayer.sendMessage(moveResultMessage);
        continue;
      }

      isMoveValid = true;

    }
    board.fill(playerMove);
    MoveResultMessage moveResultMessage = new MoveResultMessage(board, null);
    currentPlayer.sendMessage(moveResultMessage);
    otherPlayer.sendMessage(moveResultMessage);


  }




}
