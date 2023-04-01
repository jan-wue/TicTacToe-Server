package com.jawue;

import com.jawue.shared.message.ConnectMessage;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Lobby extends Thread{
  BlockingQueue<PlayerConnection> boundedQueue = new LinkedBlockingQueue<>();

  @Override
  public void run() {
    super.run();
    System.out.println("Thread started lobby runs");
    while(true) {
     try {
       PlayerConnection player1 = boundedQueue.take();
       player1.sendMessage(new ConnectMessage());

       PlayerConnection player2 = boundedQueue.take();

       player2.sendMessage(new ConnectMessage());
       Game game = new Game(player1, player2);
       game.start();
     } catch (Exception exception) {

     }
    }

  }
  public void addPlayerConnection(PlayerConnection playerConnection) {
    boundedQueue.add(playerConnection);
  }
}

