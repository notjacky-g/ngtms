/*
 * © HwaCom Systems Inc. 2015
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.cam.server.websocket;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hazelcast.core.ITopic;
import com.hazelcast.core.Message;
import com.hazelcast.core.MessageListener;
import com.hwacom.ngtms.c.fm.hz.CommonFmHzTopic;
import com.hwacom.ngtms.c.shared.BrowserAlarm;
import com.hwacom.ngtms.cam.vo.BrowserAlarmVO;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

public class BrowserAlarmWebSocket extends TextWebSocketHandler
    implements MessageListener<BrowserAlarm> {
  public static final String NAME_OF_SESSION_ID = "SESSIONID";
  public static final List<String> ATTRIBUTE_NAMES = Arrays.asList(NAME_OF_SESSION_ID);
  private Logger logger = LoggerFactory.getLogger(getClass());
  // 記錄 webSocketSessionId 與 webSocketSession 配對
  private ConcurrentHashMap<String, WebSocketSession> webSocketSessionMap =
      new ConcurrentHashMap<String, WebSocketSession>();
  // 記錄 SessionId 與 CommandId 配對
  private ConcurrentHashMap<String, String> sessionCommandMap =
      new ConcurrentHashMap<String, String>();

  private com.hwacom.ngtms.base.hazelcast.HazelcastClient hazelcastClient;
  private String registrationId;

  public BrowserAlarmWebSocket(com.hwacom.ngtms.base.hazelcast.HazelcastClient hazelcastClient) {
    this.hazelcastClient = hazelcastClient;
  }

  @PreDestroy
  public void drstroy() {
    if (registrationId != null) {
      ITopic<BrowserAlarm> topic = hazelcastClient.getITopic(CommonFmHzTopic.AlarmOfBrowser);
      topic.removeMessageListener(registrationId);
    }
  }

  @PostConstruct
  public void initHazelcast() {
    if (hazelcastClient != null) {
      ITopic<BrowserAlarm> topic = hazelcastClient.getITopic(CommonFmHzTopic.AlarmOfBrowser);
      registrationId = topic.addMessageListener(this);
    } else {
      logger.debug("hazelcastClient =" + hazelcastClient);
    }
  }

  @Override
  public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
    logger.debug("session=" + session.getId());
    String commandId = message.getPayload().trim();
    logger.debug("commandId =" + commandId);
    sessionCommandMap.put(session.getId(), commandId);
  }

  public void send(String commandId, String message) {
    logger.debug("commandId={},message={}", commandId, message);
    for (String key : sessionCommandMap.keySet()) {
      String value = sessionCommandMap.get(key);
      if (commandId.equals(value)) {
        WebSocketSession session = webSocketSessionMap.get(key);
        if (session != null) {
          try {
            synchronized (session) {
              session.sendMessage(new TextMessage(message));
            }
          } catch (IOException e) {
            logger.error(e.getMessage(), e);
          }
        } else {
          logger.info("sessionId = {},But get WebSocketSession is null", key);
        }
      }
    }
  }

  public void sendToAllSession(String message) {
    logger.debug("message={}", message);
    webSocketSessionMap
        .values()
        .forEach(
            session -> {
              try {
                synchronized (session) {
                  session.sendMessage(new TextMessage(message));
                }
              } catch (IOException e) {
                logger.error(e.getMessage(), e);
              }
            });
  }

  public void sendBrowserAlarm(Long id, String content) {
    BrowserAlarmVO dto = new BrowserAlarmVO(id, content);
    Gson gson = new GsonBuilder().disableHtmlEscaping().create();
    String jsoObject = gson.toJson(dto);
    logger.debug("sendBrowserAlarm = {} ", jsoObject);
    sendToAllSession(jsoObject);
  }

  public Set<String> getCommandSet() {
    return new HashSet<String>(sessionCommandMap.values());
  }

  public String getRemoteAddress(String sessionId) {
    WebSocketSession session = webSocketSessionMap.get(sessionId);
    return session == null ? null : session.getRemoteAddress().getAddress().getHostAddress();
  }

  @Override
  public void afterConnectionEstablished(WebSocketSession session) throws Exception {
    // String httpSessionId =
    // session.getAttributes().get(NAME_OF_SESSION_ID).toString();
    logger.debug("create session : {}", session.getId());
    logger.debug("session.getRemoteAddress()=" + session.getRemoteAddress());
    webSocketSessionMap.put(session.getId(), session);
  }

  @Override
  public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
    logger.debug("close session : {}", session.getId());
    webSocketSessionMap.remove(session.getId());
    sessionCommandMap.remove(session.getId());
  }

  @Override
  public void onMessage(Message<BrowserAlarm> message) {
    logger.debug("Get AlarmOfBrowser Topic onMessage...");
    try {
      BrowserAlarm event = (BrowserAlarm) message.getMessageObject();
      logger.debug("Get AlarmOfBrowser Topic Message received event = " + event);
      if (event != null) {
        sendBrowserAlarm(event.getPublishDate().getTime(), event.getContent());
      }
    } catch (Exception e) {
      logger.error("Get onMessage Error : ", e);
    }
  }
}
