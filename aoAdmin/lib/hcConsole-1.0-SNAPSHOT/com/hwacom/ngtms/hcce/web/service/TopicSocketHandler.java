/*     */ package com.hwacom.ngtms.hcce.web.service;
/*     */ 
/*     */ import com.hazelcast.core.ITopic;
/*     */ import com.hazelcast.core.Message;
/*     */ import com.hazelcast.core.MessageListener;
/*     */ import com.hwacom.ngtms.base.hazelcast.HzUtils;
/*     */ import java.io.IOException;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Date;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import org.apache.commons.lang.builder.ReflectionToStringBuilder;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ import org.springframework.web.socket.CloseStatus;
/*     */ import org.springframework.web.socket.TextMessage;
/*     */ import org.springframework.web.socket.WebSocketMessage;
/*     */ import org.springframework.web.socket.WebSocketSession;
/*     */ import org.springframework.web.socket.handler.TextWebSocketHandler;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TopicSocketHandler
/*     */   extends TextWebSocketHandler
/*     */ {
/*  30 */   private static Logger logger = LoggerFactory.getLogger(TopicSocketHandler.class);
/*     */   
/*  32 */   ConcurrentHashMap<WebSocketSession, TopicListener> sessionMap = new ConcurrentHashMap<>();
/*     */ 
/*     */ 
/*     */   
/*     */   public void afterConnectionEstablished(WebSocketSession session) {
/*  37 */     logger.debug("ConnectionEstablished: " + session);
/*     */   }
/*     */ 
/*     */   
/*     */   public void handleTextMessage(WebSocketSession session, TextMessage message) {
/*  42 */     logger.debug("TextMessage: " + session + ", message: " + message);
/*  43 */     if (this.sessionMap.get(session) == null) {
/*  44 */       String name = (String)message.getPayload();
/*  45 */       ITopic<Object> topic = HzUtils.getHzInstance().getTopic(name);
/*  46 */       new TopicListener(topic, this.sessionMap, session);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
/*  52 */     logger.debug("ConnectionClosed: " + session + ", status: " + status);
/*  53 */     TopicListener topicListener = this.sessionMap.get(session);
/*  54 */     if (topicListener != null) topicListener.connectionClosed(status);
/*     */   
/*     */   }
/*     */   
/*     */   public void handleTransportError(WebSocketSession session, Throwable exception) {
/*  59 */     logger.debug("TransportError: " + session + ", exception: " + exception);
/*  60 */     TopicListener topicListener = this.sessionMap.get(session);
/*  61 */     if (topicListener != null) topicListener.transportError(exception); 
/*     */   }
/*     */   
/*     */   static class TopicListener implements MessageListener<Object> {
/*  65 */     SimpleDateFormat sdFormat = new SimpleDateFormat("HH:mm:ss.SSS");
/*     */     
/*     */     ConcurrentHashMap<WebSocketSession, TopicListener> sessionMap;
/*     */     
/*     */     WebSocketSession session;
/*     */     
/*     */     ITopic<Object> topic;
/*     */     
/*     */     String registrationId;
/*     */     
/*     */     TopicListener(ITopic<Object> topic, ConcurrentHashMap<WebSocketSession, TopicListener> sessionMap, WebSocketSession session) {
/*  76 */       this.topic = topic;
/*  77 */       this.session = session;
/*  78 */       this.sessionMap = sessionMap;
/*  79 */       this.registrationId = topic.addMessageListener(this);
/*  80 */       sessionMap.put(session, this);
/*     */     }
/*     */     
/*     */     public void connectionClosed(CloseStatus status) {
/*  84 */       this.topic.removeMessageListener(this.registrationId);
/*  85 */       this.sessionMap.remove(this.session);
/*     */     }
/*     */     
/*     */     public void transportError(Throwable exception) {
/*  89 */       this.topic.removeMessageListener(this.registrationId);
/*     */       try {
/*  91 */         this.session.close();
/*  92 */       } catch (IOException iOException) {}
/*     */       
/*  94 */       this.sessionMap.remove(this.session);
/*     */     }
/*     */     
/*     */     public void onMessage(Message<Object> message) {
/*     */       String text;
/*  99 */       Object msgObj = message.getMessageObject();
/*     */       
/* 101 */       String src = message.getPublishingMember() + ":" + this.sdFormat.format(new Date(message.getPublishTime()));
/*     */       
/* 103 */       if (msgObj instanceof java.util.Map || msgObj instanceof java.util.Collection || msgObj instanceof java.util.Set)
/* 104 */       { text = msgObj.toString(); }
/* 105 */       else { text = ReflectionToStringBuilder.toString(msgObj, HzDumpStyle.getInstance(), true); }
/*     */        try {
/* 107 */         this.session.sendMessage((WebSocketMessage)new TextMessage(("SRC:" + src + ", MSG:" + text).getBytes("UTF-8")));
/* 108 */       } catch (IOException e) {
/*     */         try {
/* 110 */           this.session.close();
/* 111 */         } catch (IOException iOException) {}
/*     */         
/* 113 */         this.sessionMap.remove(this.session);
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\lib\hcConsole-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\hcce\web\service\TopicSocketHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */