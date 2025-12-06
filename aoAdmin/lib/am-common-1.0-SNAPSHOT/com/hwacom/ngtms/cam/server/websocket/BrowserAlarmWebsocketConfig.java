/*
 * © HwaCom Systems Inc. 2015
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.cam.server.websocket;

import com.hwacom.ngtms.base.hazelcast.HazelcastClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

/** @author fenrir.cheng */
@Configuration
@EnableWebSocket
public class BrowserAlarmWebsocketConfig implements WebSocketConfigurer {

  @Autowired private HazelcastClient hazelcastClient;

  @Override
  public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
    registry
        .addHandler(new BrowserAlarmWebSocket(hazelcastClient), "/websocket/browserAlarm")
        .addInterceptors(
            new HttpSessionHandshakeInterceptor(BrowserAlarmWebSocket.ATTRIBUTE_NAMES));
  }
}
