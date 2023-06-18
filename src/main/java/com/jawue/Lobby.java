package com.jawue;

import com.jawue.shared.message.ConnectMessage;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Lobby extends Thread{
  BlockingQueue<PlayerConnection> boundedQueue = new LinkedBlockingQueue<>();
  List<PlayerConnection> players = new ArrayList<>();
  @Override
  public void run() {
    super.run();
    System.out.println("Thread started lobby runs");
    while(true) {
     try {

       if(players.size() != 2) {
         PlayerConnection player = boundedQueue.take();
         player.sendMessage(new ConnectMessage());
         players.add(player);

       } else {
         Game game = new Game(players.get(0), players.get(1));
         game.start();
         players.clear();
       }
     } catch (Exception exception) {

     }
    }

  }
  public void addPlayerConnection(PlayerConnection playerConnection) {
    boundedQueue.add(playerConnection);
  }

  public void removePlayerConnection(PlayerConnection playerConnection) {
    boundedQueue.remove(playerConnection);
    players.remove(playerConnection);
  }

}

