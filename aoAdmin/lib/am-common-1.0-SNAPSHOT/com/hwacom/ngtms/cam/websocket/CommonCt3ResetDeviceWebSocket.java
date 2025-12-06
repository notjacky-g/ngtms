/*
 * © HwaCom Systems Inc. 2021
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.cam.websocket;

import com.google.gwt.user.client.Window.Location;
import com.google.gwt.core.client.JavaScriptObject;

public class CommonCt3ResetDeviceWebSocket {

  private String uri;

  private CommonCt3ResetDeviceWebSocket.WebSocketHandler handler;

  private JavaScriptObject wsObject;

  public CommonCt3ResetDeviceWebSocket(CommonCt3ResetDeviceWebSocket.WebSocketHandler handler) {
    this.handler = handler;
    String protocol = Location.getProtocol().equals("https:") ? "wss:" : "ws:";
    this.uri = protocol + "//" + com.hwacom.ngtms.common.am.AmEntryPoint.webSocketIp + ":" + com.hwacom.ngtms.common.am.AmEntryPoint.webSocketPort + "/websocket/ct3/common/ct3ResetDevice";
  }

  public native void send(String message) /*-{
    var ws = this.@com.hwacom.ngtms.cam.websocket.CommonCt3ResetDeviceWebSocket::wsObject;
    ws.send(message);
  }-*/;

  public native void close() /*-{
    var ws = this.@com.hwacom.ngtms.cam.websocket.CommonCt3ResetDeviceWebSocket::wsObject;
    ws.close();
  }-*/;

  /**
   * http://www.w3.org/TR/2011/WD-websockets-20110419/
   * 
   * CONNECTING (numeric value 0) The connection has not yet been established.
   * OPEN (numeric value 1) The WebSocket connection is established and communication is possible.
   * CLOSING (numeric value 2) The connection is going through the closing handshake.
   * CLOSED (numeric value 3) The connection has been closed or could not be opened.
   * 
   * @return
   */
  public native boolean isClosed() /*-{
    var ws = this.@com.hwacom.ngtms.cam.websocket.CommonCt3ResetDeviceWebSocket::wsObject;
    return ws.readyState == 3;
  }-*/;

  public native void open() /*-{
    var websocket = new $wnd.WebSocket(this.@com.hwacom.ngtms.cam.websocket.CommonCt3ResetDeviceWebSocket::uri);
    var handler = this.@com.hwacom.ngtms.cam.websocket.CommonCt3ResetDeviceWebSocket::handler;
    websocket.onopen = function() {
      handler.@com.hwacom.ngtms.cam.websocket.CommonCt3ResetDeviceWebSocket.WebSocketHandler::onOpen()();
    }
    websocket.onmessage = function(event) {
      handler.@com.hwacom.ngtms.cam.websocket.CommonCt3ResetDeviceWebSocket.WebSocketHandler::onMessage(Ljava/lang/String;)(event.data);
    }
    websocket.onclose = function(event) {
      handler.@com.hwacom.ngtms.cam.websocket.CommonCt3ResetDeviceWebSocket.WebSocketHandler::onClose(ILjava/lang/String;)(event.code, event.reason);
    }
    this.@com.hwacom.ngtms.cam.websocket.CommonCt3ResetDeviceWebSocket::wsObject = websocket;
  }-*/;

  public interface WebSocketHandler {

    void onOpen();

    void onMessage(String message);

    void onClose(int code, String reason);
  }
}