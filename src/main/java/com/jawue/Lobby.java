package com.jawue;

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
       player1.wsContext.send("you are connected bro");

       PlayerConnection player2 = boundedQueue.take();
       player2.wsContext.send("you are connected bro");
       System.out.println("works");
     } catch (Exception exception) {

     }
    }

  }
  public void addPlayerConnection(PlayerConnection playerConnection) {
    boundedQueue.add(playerConnection);
  }
}

