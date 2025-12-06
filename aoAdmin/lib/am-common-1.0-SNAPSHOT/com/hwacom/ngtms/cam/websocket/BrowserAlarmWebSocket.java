/*
 * © HwaCom Systems Inc. 2015
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.cam.websocket;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window.Location;
import java.util.logging.Logger;

public class BrowserAlarmWebSocket {

  private String uri;

  private BrowserAlarmWebSocket.WebSocketHandler handler;
  private static Logger logger = Logger.getLogger("BrowserAlarmWebSocket");

  public BrowserAlarmWebSocket(BrowserAlarmWebSocket.WebSocketHandler handler) {
    this.handler = handler;
    logger.info("Location.getProtocol()=" + Location.getProtocol());
    if (Location.getProtocol().indexOf("https") > -1) {
      this.uri =
          GWT.getHostPageBaseURL().replace(Location.getProtocol(), "wss:")
              + "websocket/browserAlarm";
    } else {
      this.uri =
          GWT.getHostPageBaseURL().replace(Location.getProtocol(), "ws:")
              + "websocket/browserAlarm";
    }
    logger.info("uri=" + uri);
  }

  public native void send(String message) /*-{
        $wnd.BrowserAlarmWebSocket.send(message);
    }-*/;

  public native void close() /*-{
        $wnd.BrowserAlarmWebSocket.close();
    }-*/;

  /**
   * http://www.w3.org/TR/2011/WD-websockets-20110419/
   *
   * <p>CONNECTING (numeric value 0) The connection has not yet been established. OPEN (numeric
   * value 1) The WebSocket connection is established and communication is possible. CLOSING
   * (numeric value 2) The connection is going through the closing handshake. CLOSED (numeric value
   * 3) The connection has been closed or could not be opened.
   *
   * @return
   */
  public native boolean isClosed() /*-{
        return $wnd.BrowserAlarmWebSocket.readyState == 3;
    }-*/;

  public native void open() /*-{
        var websocket = new $wnd.WebSocket(this.@com.hwacom.ngtms.cam.websocket.BrowserAlarmWebSocket::uri);
        var handler = this.@com.hwacom.ngtms.cam.websocket.BrowserAlarmWebSocket::handler;
        websocket.onopen = function() {
            handler.@com.hwacom.ngtms.cam.websocket.BrowserAlarmWebSocket.WebSocketHandler::onOpen()();
        }
        websocket.onmessage = function(event) {
            handler.@com.hwacom.ngtms.cam.websocket.BrowserAlarmWebSocket.WebSocketHandler::onMessage(Ljava/lang/String;)(event.data);
        }
        websocket.onclose = function(event) {
            handler.@com.hwacom.ngtms.cam.websocket.BrowserAlarmWebSocket.WebSocketHandler::onClose(ILjava/lang/String;)(event.code, event.reason);
        }
        $wnd.BrowserAlarmWebSocket = websocket;
    }-*/;

  public interface WebSocketHandler {

    void onOpen();

    void onMessage(String message);

    void onClose(int code, String reason);
  }
}
