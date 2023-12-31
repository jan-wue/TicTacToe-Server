package com.jawue;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jawue.shared.message.GameSymbol;
import com.jawue.shared.message.Message;
import io.javalin.websocket.WsConfig;
import io.javalin.websocket.WsContext;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class PlayerConnection {
  WsConfig ws;
  WsContext wsContext;
  BlockingQueue<Message> messages = new LinkedBlockingQueue<Message>();
  ObjectMapper mapper = new ObjectMapper();
private   GameSymbol playerSymbol;

  public PlayerConnection() {

  }


  public void setWs(WsConfig ws) {

    this.ws = ws;
  }

  public void setWsContext(WsContext wsContext) {
    this.wsContext = wsContext;
  }

  public PlayerConnection(WsConfig ws, WsContext wsContext) {
   this.ws = ws;
   this.wsContext = wsContext;
  }
  public void sendMessage(Message message) {
    try {
      mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

      String json = mapper.writeValueAsString(message);
      this.wsContext.send(json);
    } catch (Exception error) {
      System.err.println(error.getMessage());
    }
  }

  public  Message receiveMessage() {

    try {
      return messages.take();
    } catch (Exception error) {
      System.err.println(error.getMessage());
    }
    return null;
  }

  public void setPlayerSymbol(GameSymbol playerSymbol) {
    this.playerSymbol = playerSymbol;
  }

  public GameSymbol getPlayerSymbol() {
    return playerSymbol;
  }




}
