package com.jawue;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jawue.shared.message.Message;
import com.jawue.shared.PlayerMove;
import com.jawue.shared.message.PlayerMoveMessage;
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
                  PlayerConnection playerConnection = new PlayerConnection();
                  ctx.attribute("playerConnection", playerConnection );
                System.out.println("Connected");
                  playerConnection.setWs(ws);
                  playerConnection.setWsContext(ctx);
              lobby.addPlayerConnection(playerConnection);
              }
              );
              ws.onMessage(ctx -> {
                ObjectMapper mapper = new ObjectMapper();
                String messageStr = ctx.message();
                Message message = mapper.readValue(messageStr, Message.class);
                PlayerConnection playerConnection = (PlayerConnection) ctx.attribute("playerConnection");
                playerConnection.messages.add(message);
                System.out.println(message.getClass() + ctx.message());

              });
            }) 
            .start(7070);


  }

}
 
