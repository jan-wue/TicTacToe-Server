package com.jawue;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jawue.message.Message;
import com.jawue.message.PlayerMoveMessage;
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
              ws.onConnect(ctx -> {
                System.out.println("Connected");
              PlayerConnection playerConnection = new PlayerConnection(ws, ctx);
              lobby.addPlayerConnection(playerConnection);
              }
              );
              ws.onMessage(ctx -> {
                ObjectMapper mapper = new ObjectMapper();
                Message message = mapper.readValue(ctx.message(), Message.class);
                System.out.println(message.getClass() + ctx.message());
              });
            }) 
            .start(7070);


  }

}
 
