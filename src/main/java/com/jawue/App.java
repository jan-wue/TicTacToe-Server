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
    String html = """
            <!DOCTYPE html>
            <html lang="en">
              <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <meta http-equiv="X-UA-Compatible" content="ie=edge">
                <title>TicTacToe</title>
                <link rel="stylesheet" href="style.css">
              </head>
              <body>
               <h1>TicTacToe</h1>
              </body>
            </html>
            """;
    Lobby lobby = new Lobby();
    lobby.start();
        var app = Javalin.create(/*config*/)
            .get("/", ctx -> ctx.html(html))
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
 
