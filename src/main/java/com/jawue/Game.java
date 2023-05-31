package com.jawue;

import com.jawue.shared.Board;
import com.jawue.shared.PlayerMove;
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
    boolean playersWantAnotherGame = false;
    boolean isApplicationRunning = true;
    while (isApplicationRunning) {
      Board board = new Board();
      board.initialize();
      setPlayerSymbolsRandomly();
      PlayerConnection currentPlayer = player1;
      PlayerConnection otherPlayer = player2;
      boolean isGameRunning = true;
      currentPlayer.sendMessage(new GameStartsMessage("Game starts"));
      otherPlayer.sendMessage(new GameStartsMessage("Game starts"));
      while (isGameRunning) {
        requestAndValidatesMove(board, currentPlayer, otherPlayer);
        if (isThereAWinner(board, currentPlayer.getPlayerSymbol()) || board.isBoardFull()) {
          if (board.isBoardFull()) {
            GameResultMessage drawMessage = new GameResultMessage("game ends in Draw");
            currentPlayer.sendMessage(drawMessage);
            otherPlayer.sendMessage(drawMessage);
          } else {
            GameResultMessage winMessage = new GameResultMessage("Congratulation you have won");
            GameResultMessage loseMessage = new GameResultMessage("Sorry bro you have lost");
            currentPlayer.sendMessage(winMessage);
            otherPlayer.sendMessage(loseMessage);
            isGameRunning = false;

          }
          PlayAgainMessage playAgainMessage = new PlayAgainMessage("Would you like to play Again if yes press y else n ");
          currentPlayer.sendMessage(playAgainMessage);
          otherPlayer.sendMessage(playAgainMessage);
          if (!doBothPLayerWantToKeepPlaying()) {
            isApplicationRunning = false;
            GameFinishedMessage message = new GameFinishedMessage("Thanks for playing TicTacToe :D");
            currentPlayer.sendMessage(message);
            otherPlayer.sendMessage(message);
          } else {
            NewGameStartsMessage message = new NewGameStartsMessage("Both players want to keep playing");
            currentPlayer.sendMessage(message);
            otherPlayer.sendMessage(message);
          }

        }


        if (currentPlayer == player1) {
          currentPlayer = player2;
          otherPlayer = player1;
        } else {
          currentPlayer = player1;
          otherPlayer = player2;
        }
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
    PlayerMove playerMove = null;
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
    board.fill(playerMove, currentPlayer.getPlayerSymbol());
    MoveResultMessage moveResultMessage = new MoveResultMessage(board, null);
    currentPlayer.sendMessage(moveResultMessage);
    otherPlayer.sendMessage(moveResultMessage);


  }

  public boolean doBothPLayerWantToKeepPlaying() {
    PlayAgainMessage player1Message = (PlayAgainMessage) player1.receiveMessage();
    PlayAgainMessage player2Message = (PlayAgainMessage) player2.receiveMessage();
    Boolean player1Answer = player1Message.getPlayerAnswer();
    Boolean player2Answer = player2Message.getPlayerAnswer();
    if (player1Answer && player2Answer) {
      return true;
    }
    return false;
  }


  public void setPlayerSymbolsRandomly() {
    Random random = new Random();
    int randomNumber = random.nextInt(2);
    if (randomNumber == 1) {
      player1.setPlayerSymbol(GameSymbol.X);
      player2.setPlayerSymbol(GameSymbol.O);
    } else {
      player1.setPlayerSymbol(GameSymbol.O);
      player2.setPlayerSymbol(GameSymbol.X);
    }

  }

  public boolean getRowWinner(Board board, Integer rowIndex, GameSymbol playerSymbol) {

    String x0 = board.getField(rowIndex, 0);
    String x1 = board.getField(rowIndex, 1);
    String x2 = board.getField(rowIndex, 2);

    return x0.equals(playerSymbol.toString()) && x1.equals(playerSymbol.toString()) && x2.equals(playerSymbol.toString());
  }

  public boolean getColumnWinner(Board board, Integer columnIndex, GameSymbol gameSymbol) {

    String x0 = board.getField(0, columnIndex);
    String x1 = board.getField(1, columnIndex);
    String x2 = board.getField(2, columnIndex);

    return x0.equals(gameSymbol.toString()) && x1.equals(gameSymbol.toString()) && x2.equals(gameSymbol.toString());
  }

  public boolean getDiagonalLeftWinner(Board board, GameSymbol playerSymbol) {

    String x0 = board.getField(0, 0);
    String x1 = board.getField(1, 1);
    String x2 = board.getField(2, 2);

    return x0.equals(playerSymbol.toString()) && x1.equals(playerSymbol.toString()) && x2.equals(playerSymbol.toString());
  }

  public boolean getDiagonalRightWinner(Board board, GameSymbol gameSymbol) {

    String x0 = board.getField(0, 2);
    String x1 = board.getField(1, 1);
    String x2 = board.getField(2, 0);

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
