/*     */ package com.hwacom.ngtms.c.fm.websocket;
/*     */ 
/*     */ import com.google.gson.Gson;
/*     */ import com.google.gson.GsonBuilder;
/*     */ import com.hazelcast.core.IMap;
/*     */ import com.hwacom.ngtms.base.hazelcast.HzUtils;
/*     */ import com.hwacom.ngtms.c.fm.hz.CommonFmHzMap;
/*     */ import com.hwacom.ngtms.c.fm.model.DeviceTcConfig;
/*     */ import com.hwacom.ngtms.c.shared.dto.OperationLogDTO;
/*     */ import com.hwacom.ngtms.common.websocket.AbstractServerEndpoint;
/*     */ import com.hwacom.ngtms.common.websocket.SpringConfigurator;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Arrays;
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import javax.websocket.CloseReason;
/*     */ import javax.websocket.EndpointConfig;
/*     */ import javax.websocket.OnClose;
/*     */ import javax.websocket.OnError;
/*     */ import javax.websocket.OnMessage;
/*     */ import javax.websocket.OnOpen;
/*     */ import javax.websocket.Session;
/*     */ import javax.websocket.server.ServerEndpoint;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
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
/*     */ @ServerEndpoint(value="/websocket/opLog", configurator=SpringConfigurator.class, decoders={CommonCt3TcConfigSettingEndPoint.CommonCt3DeviceTimeMessageDecoder.class})
/*     */ @Service
/*     */ public class OperationLogEndPoint
/*     */   extends AbstractServerEndpoint
/*     */ {
/*     */   public static final String NAME_OF_SESSION_ID = "SESSIONID";
/*  46 */   public static final List<String> ATTRIBUTE_NAMES = Arrays.asList(new String[] { "SESSIONID" });
/*     */   
/*  48 */   private Logger logger = LoggerFactory.getLogger(getClass());
/*     */   
/*     */ 
/*  51 */   private ConcurrentHashMap<String, Session> webSocketSessionMap = new ConcurrentHashMap();
/*     */   
/*     */ 
/*     */ 
/*  55 */   private ConcurrentHashMap<String, String> sessionSubSystemMap = new ConcurrentHashMap();
/*     */   
/*     */ 
/*  58 */   private SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss:SSS");
/*  59 */   private SimpleDateFormat sdFormatShort = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
/*     */   
/*     */   @OnOpen
/*     */   public void onOpen(Session session, EndpointConfig config) {
/*  63 */     init(session, config);
/*  64 */     this.webSocketSessionMap.put(session.getId(), session);
/*     */   }
/*     */   
/*     */   @OnMessage
/*     */   public void onMessage(Session session, String subSystem) {
/*  69 */     this.sessionSubSystemMap.put(session.getId(), subSystem.trim());
/*     */   }
/*     */   
/*     */   @OnClose
/*     */   public void onClose(Session session, CloseReason closeReason) {
/*  74 */     this.logger.debug("close session : {}", session.getId());
/*  75 */     this.webSocketSessionMap.remove(session.getId());
/*  76 */     this.sessionSubSystemMap.remove(session.getId());
/*     */   }
/*     */   
/*     */   @OnError
/*     */   public void onError(Session session, Throwable t) {}
/*     */   
/*     */   public void send(String subSystem, String message) {
/*  83 */     this.logger.debug("subSystem='{}',message='{}'", subSystem, message);
/*  84 */     for (String key : this.sessionSubSystemMap.keySet()) {
/*  85 */       String value = (String)this.sessionSubSystemMap.get(key);
/*  86 */       if (subSystem.equals(value)) {
/*  87 */         Session session = (Session)this.webSocketSessionMap.get(key);
/*  88 */         if (session != null) {
/*  89 */           synchronized (session) {
/*  90 */             send(message);
/*     */           }
/*     */         } else {
/*  93 */           this.logger.info("sessionId = {},But get WebSocketSession is null", key);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void sendOpLog(String userId, String cpeIp, String subSysName, String operationItem, String deviceName, String description, Date operationTime, String operationResult, String remark)
/*     */   {
/* 122 */     OperationLogDTO operationLog = new OperationLogDTO(userId, cpeIp, null, subSysName, operationItem, description, deviceName, this.sdFormat.format(operationTime), operationResult, remark, this.sdFormatShort.format(operationTime), operationTime.getTime());
/* 123 */     IMap<String, DeviceTcConfig> tcConfigMap = HzUtils.getMap(CommonFmHzMap.DeviceTcConfig);
/* 124 */     DeviceTcConfig tcConfig = (DeviceTcConfig)tcConfigMap.get(deviceName);
/* 125 */     if (tcConfig != null) {
/* 126 */       operationLog.setDisplayName(tcConfig.getDisplayName());
/*     */     }
/* 128 */     Gson gson = new GsonBuilder().disableHtmlEscaping().create();
/* 129 */     String jsoObject = gson.toJson(operationLog);
/* 130 */     this.logger.debug("sendOpLog = {} ", jsoObject);
/* 131 */     send(operationLog.getSubSysName(), jsoObject);
/*     */   }
/*     */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\commonFm-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\c\fm\websocket\OperationLogEndPoint.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */