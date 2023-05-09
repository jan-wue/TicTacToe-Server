package com.jawue;

import com.jawue.shared.PlayerMove;
import com.jawue.shared.WinnerResult;
import com.jawue.shared.message.*;

import java.util.Random;

public class Game extends Thread {
  PlayerConnection player1;
  PlayerConnection player2;


  public Game(PlayerConnection player1, PlayerConnection player2) {
    this.player1 = player1;
    this.player2 = player2;
  }

  public void run() {
    boolean applicationRuns = false;
      Board board = new Board();
      board.initialize();
      setPlayerSymbolsRandomly();
      boolean gameIsNotFinished = true;
      while (gameIsNotFinished) {
        requestAndValidatesMove(board, player1, player2);
        if(isThereAWinner(board, player1.getPlayerSymbol())) {
          GameFinishedMessage winMessage = new GameFinishedMessage("Congratulation you have won");
          GameFinishedMessage loseMessage= new GameFinishedMessage("Sorry bro you have lost");
          player1.sendMessage(winMessage);
          player2.sendMessage(loseMessage);
        }
        if(board.isBoardFull()) {
          GameFinishedMessage drawMessage = new GameFinishedMessage("game endet in Draw");
          break;
        }
        requestAndValidatesMove(board, player2, player1);
        if(isThereAWinner(board, player2.getPlayerSymbol())) {
          GameFinishedMessage winMessage = new GameFinishedMessage("Congratulation you have won");
          GameFinishedMessage loseMessage= new GameFinishedMessage("Sorry bro you have lost");
          player2.sendMessage(winMessage);
          player1.sendMessage(loseMessage);
        }


      }
  }
  /** Sends a requestMove to the player, gets the playermove and validates the move if it is a valid move it will send the result to both players, if not it will keep promting the player for a valid move. Meanwhile waiting for the players move it will send a waitforotherplayer message to the other player.
   **/

  /**
   * @param board
   * @param player
   */
  public void requestAndValidatesMove(Board board, PlayerConnection currentPlayer, PlayerConnection otherPlayer) {

    boolean isMoveValid = false;
    PlayerMove playerMove =  null;
    WaitForOtherPlayerMessage waitMessage = new WaitForOtherPlayerMessage("Please wait for your turn bro");
    otherPlayer.sendMessage(waitMessage);
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
    board.fill(playerMove, currentPlayer);
    MoveResultMessage moveResultMessage = new MoveResultMessage(board, null);
    currentPlayer.sendMessage(moveResultMessage);
    otherPlayer.sendMessage(moveResultMessage);


  }


  public void setPlayerSymbolsRandomly() {
    Random random = new Random();
    int randomNumber = random.nextInt(1);
    if(randomNumber == 1) {
      player1.setPlayerSymbol(GameSymbol.X);
      player2.setPlayerSymbol(GameSymbol.O);
    } else {
      player1.setPlayerSymbol(GameSymbol.O);
      player2.setPlayerSymbol(GameSymbol.X);
    }

  }
  public boolean getRowWinner(Board board, Integer rowIndex, GameSymbol playerSymbol) {

    String x0 = board.board[rowIndex][0];
    String x1 = board.board[rowIndex][1];
    String x2 = board.board[rowIndex][2];

    return x0.equals(playerSymbol.toString()) && x1.equals(playerSymbol.toString()) && x2.equals(playerSymbol.toString());
  }

  public boolean getColumnWinner(Board board, Integer columnIndex, GameSymbol gameSymbol) {

    String x0 = board.board[0][columnIndex];
    String x1 = board.board[1][columnIndex];
    String x2 = board.board[2][columnIndex];

    return x0.equals(gameSymbol.toString()) && x1.equals(gameSymbol.toString()) && x2.equals(gameSymbol.toString());
  }

  public boolean getDiagonalLeftWinner(Board board, GameSymbol playerSymbol) {

    String x0 = board.board[0][0];
    String x1 = board.board[1][1];
    String x2 = board.board[2][2];

    return x0.equals(playerSymbol.toString()) && x1.equals(playerSymbol.toString()) && x2.equals(playerSymbol.toString());
  }

  public boolean getDiagonalRightWinner(Board board, GameSymbol gameSymbol) {

    String x0 = board.board[0][2];
    String x1 = board.board[1][1];
    String x2 = board.board[2][0];

    return x0.equals(gameSymbol.toString()) && x1.equals(gameSymbol.toString()) && x2.equals(gameSymbol.toString());
  }



  public boolean isThereAWinner(Board board, GameSymbol playerSymbol) {

    if (getDiagonalRightWinner(board, playerSymbol)) {
      return true;
    }

    if (getDiagonalLeftWinner(board, playerSymbol)) {
      return true;
    }

    for (int i = 0; i < 3; i++) {

      if (getRowWinner(board, i, playerSymbol)) {
        return true;
      }
    }

    for (int i = 0; i < 3; i++) {

      if (getColumnWinner(board, i, playerSymbol)) {

        return true;
      }
    }

    return false;
  }

}
