package com.jawue;

import io.javalin.websocket.WsConfig;
import io.javalin.websocket.WsConnectHandler;
import io.javalin.websocket.WsContext;

public class PlayerConnection {
  WsConfig ws;
  WsContext wsContext;
  public PlayerConnection(WsConfig ws, WsContext wsContext) {
   this.ws = ws;
   this.wsContext = wsContext;
  }
}
