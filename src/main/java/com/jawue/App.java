package com.jawue;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jawue.shared.message.Message;
import io.javalin.Javalin;
import java.time.Duration;

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
          //keep connection open, idle timeout
        ctx.session.setIdleTimeout(Duration.ZERO);
                  PlayerConnection playerConnection = new PlayerConnection();
                  ctx.attribute("playerConnection", playerConnection );
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
 
