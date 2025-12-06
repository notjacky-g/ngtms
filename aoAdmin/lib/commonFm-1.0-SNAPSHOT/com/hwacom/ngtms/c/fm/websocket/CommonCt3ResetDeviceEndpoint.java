/*     */ package com.hwacom.ngtms.c.fm.websocket;
/*     */ 
/*     */ import com.hwacom.ngtms.base.oplog.shared.OperationItem;
/*     */ import com.hwacom.ngtms.c.fm.service.CommonCt3TcService;
/*     */ import com.hwacom.ngtms.c.shared.DeviceListAndTypeMessage;
/*     */ import com.hwacom.ngtms.c.shared.SubSystem;
/*     */ import com.hwacom.ngtms.c.shared.WebSocketCloseReason;
/*     */ import com.hwacom.ngtms.common.websocket.SpringConfigurator;
/*     */ import com.hwacom.ngtms.ncc.remote.TcResponse;
/*     */ import java.io.IOException;
/*     */ import java.io.StringReader;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import java.util.function.BiConsumer;
/*     */ import javax.json.Json;
/*     */ import javax.json.JsonArray;
/*     */ import javax.json.JsonException;
/*     */ import javax.json.JsonObject;
/*     */ import javax.json.JsonString;
/*     */ import javax.websocket.CloseReason;
/*     */ import javax.websocket.DecodeException;
/*     */ import javax.websocket.Decoder;
/*     */ import javax.websocket.EndpointConfig;
/*     */ import javax.websocket.OnClose;
/*     */ import javax.websocket.OnError;
/*     */ import javax.websocket.OnMessage;
/*     */ import javax.websocket.OnOpen;
/*     */ import javax.websocket.Session;
/*     */ import javax.websocket.server.ServerEndpoint;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.stereotype.Service;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @ServerEndpoint(value = "/websocket/ct3/common/ct3ResetDevice", configurator = SpringConfigurator.class, decoders = {CommonCt3ResetDeviceEndpoint.MessageDecoder.class})
/*     */ @Service
/*     */ public class CommonCt3ResetDeviceEndpoint
/*     */   extends AbstractServerEndpoint
/*     */ {
/*  60 */   private static final Logger logger = LoggerFactory.getLogger(CommonCt3ResetDeviceEndpoint.class);
/*     */   @Autowired
/*     */   private CommonCt3TcService commonTcService;
/*     */   
/*     */   @OnOpen
/*     */   public void start(Session session, EndpointConfig config) {
/*  66 */     init(session, config, OperationItem.SET, (SubSystem)null);
/*  67 */     logger.info("Common reset device query Websocket Session:'{}' Started...", session.getId());
/*     */   }
/*     */   
/*     */   @OnMessage
/*     */   public void query(DeviceListAndTypeMessage message, Session session) {
/*  72 */     String deviceType = message.getDeviceType();
/*  73 */     setSubSystem(SubSystem.valueOf(deviceType));
/*  74 */     List<String> deviceNames = message.getDeviceNames();
/*     */     try {
/*  76 */       this.commonTcService.setCt3ResetDevice(deviceNames, new Callback(deviceNames, "common.tc.reset"));
/*  77 */     } catch (RuntimeException e) {
/*  78 */       logger.warn("Reset device failed! deviceNames:{}", deviceNames, e);
/*  79 */       offerExceptionLog(deviceNames, e, "common.tc.reset");
/*  80 */       close(WebSocketCloseReason.FAILURE.getCode());
/*     */     } 
/*  82 */     logger.debug("Websocket Session:'{}' query:'{}'", session.getId(), message.getDeviceNames());
/*     */   }
/*     */   
/*     */   private void close(int closeCode) {
/*     */     try {
/*  87 */       this.session.close(new CloseReason(() -> paramInt, ""));
/*  88 */     } catch (IOException e) {
/*  89 */       logger.warn("Close websocket Session:'{}' failed!", this.session.getId(), e);
/*     */     } 
/*     */   }
/*     */   
/*     */   @OnClose
/*     */   public void end(Session session, CloseReason closeReason) {
/*  95 */     logger.info("Websocket Session:'{}' Ended..., reason:'{}'", session.getId(), closeReason);
/*     */   }
/*     */   
/*     */   @OnError
/*     */   public void error(Session session, Throwable t) {
/* 100 */     logger.warn("Websocket Session:'{}' Error!", session.getId(), t);
/*     */   }
/*     */   
/*     */   private class Callback
/*     */     implements BiConsumer<String, TcResponse>
/*     */   {
/*     */     private int count;
/*     */     private String messageId;
/*     */     
/*     */     public Callback(List<String> deviceNames, String messageId) {
/* 110 */       this.count = deviceNames.size();
/* 111 */       this.messageId = messageId;
/*     */     }
/*     */ 
/*     */     
/*     */     public void accept(String deviceName, TcResponse response) {
/* 116 */       CommonCt3ResetDeviceEndpoint.this.offerTcResponseLog(deviceName, response, (String)null, this.messageId);
/* 117 */       this.count--;
/* 118 */       if (this.count == 0) {
/* 119 */         CommonCt3ResetDeviceEndpoint.this.close(WebSocketCloseReason.FINISHED.getCode());
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static class MessageDecoder
/*     */     implements Decoder.Text<DeviceListAndTypeMessage>
/*     */   {
/*     */     public void init(EndpointConfig config) {}
/*     */ 
/*     */     
/*     */     public void destroy() {}
/*     */     
/*     */     public DeviceListAndTypeMessage decode(String s) throws DecodeException {
/* 134 */       DeviceListAndTypeMessage.DeviceListAndTypeMessageImpl msg = new DeviceListAndTypeMessage.DeviceListAndTypeMessageImpl();
/* 135 */       JsonObject jsonObject = Json.createReader(new StringReader(s)).readObject();
/* 136 */       JsonArray deviceNameArray = jsonObject.getJsonArray("deviceNames");
/* 137 */       msg.setDeviceNames(new ArrayList<>(jsonArrayToCollection(deviceNameArray)));
/* 138 */       msg.setDeviceType(jsonObject.getString("deviceType"));
/* 139 */       return (DeviceListAndTypeMessage)msg;
/*     */     }
/*     */     
/*     */     private Collection<String> jsonArrayToCollection(JsonArray jsonArray) {
/* 143 */       if (jsonArray == null) {
/* 144 */         return Collections.emptySet();
/*     */       }
/*     */       
/* 147 */       Set<String> result = new HashSet<>();
/* 148 */       for (int i = 0; i < jsonArray.size(); i++) {
/* 149 */         JsonString jsonString = jsonArray.getJsonString(i);
/* 150 */         result.add(jsonString.getString());
/*     */       } 
/*     */       
/* 153 */       return result;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean willDecode(String s) {
/*     */       try {
/* 159 */         Json.createReader(new StringReader(s)).readObject();
/* 160 */         return true;
/* 161 */       } catch (JsonException ex) {
/* 162 */         CommonCt3ResetDeviceEndpoint.logger.warn("DeviceListAndTypeMessage format Error, will not be decoded! '{}'", s);
/* 163 */         return false;
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\lib\commonFm-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\c\fm\websocket\CommonCt3ResetDeviceEndpoint.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */