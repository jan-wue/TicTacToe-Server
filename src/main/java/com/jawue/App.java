package com.jawue;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jawue.shared.message.Message;
import io.javalin.Javalin;

/**
 * Hello world!
 *
 */
public class App {

  public static void main(String[] args) {
    Lobby lobby = new Lobby();
    lobby.start();
        var app = Javalin.create(/*config*/)
            .get("/", ctx -> ctx.result("Hello World"))
	    .ws("/websocket", ws -> {
         PlayerConnection playerConnection = new PlayerConnection();
        ws.onConnect(ctx -> {
                System.out.println("Connected");
                  playerConnection.setWs(ws);
                  playerConnection.setWsContext(ctx);
              lobby.addPlayerConnection(playerConnection);
              }
              );
              ws.onMessage(ctx -> {
                ObjectMapper mapper = new ObjectMapper();
                String messageStr = ctx.message();
                System.out.println(messageStr);
                Message message = mapper.readValue(messageStr, Message.class);
                playerConnection.messages.add(message);
                System.out.println(message.getClass() + ctx.message());

              });
            }) 
            .start(7070);


  }

}
 
