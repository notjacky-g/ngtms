/*    */ package com.hwacom.ngtms.ao.restful.ws;
/*    */ 
/*    */ import com.google.gson.Gson;
/*    */ import com.google.gson.GsonBuilder;
/*    */ import com.google.gson.JsonElement;
/*    */ import com.google.gson.JsonPrimitive;
/*    */ import com.google.gson.JsonSerializationContext;
/*    */ import com.google.gson.JsonSerializer;
/*    */ import com.hwacom.ngtms.ao.fm.service.AoAlarmService;
/*    */ import com.hwacom.ngtms.ao.shared.dto.AlarmMessageDTO;
/*    */ import com.hwacom.ngtms.common.websocket.AbstractServerEndpoint;
/*    */ import com.hwacom.ngtms.common.websocket.SpringConfigurator;
/*    */ import java.io.StringWriter;
/*    */ import java.lang.reflect.Type;
/*    */ import java.util.Date;
/*    */ import javax.json.Json;
/*    */ import javax.json.JsonObjectBuilder;
/*    */ import javax.websocket.CloseReason;
/*    */ import javax.websocket.OnClose;
/*    */ import javax.websocket.OnError;
/*    */ import javax.websocket.OnOpen;
/*    */ import javax.websocket.Session;
/*    */ import javax.websocket.server.ServerEndpoint;
/*    */ import org.slf4j.Logger;
/*    */ import org.slf4j.LoggerFactory;
/*    */ import org.springframework.beans.factory.annotation.Autowired;
/*    */ import org.springframework.context.annotation.Scope;
/*    */ import org.springframework.stereotype.Service;
/*    */ 
/*    */ @ServerEndpoint(value = "/websocket/ao/alarm", configurator = SpringConfigurator.class)
/*    */ @Service
/*    */ @Scope("prototype")
/*    */ public class AlarmEndpoint
/*    */   extends AbstractServerEndpoint {
/* 35 */   private Logger logger = LoggerFactory.getLogger(AlarmEndpoint.class);
/*    */   
/*    */   private Gson gson;
/*    */   @Autowired
/*    */   AoAlarmService registor;
/*    */   
/*    */   public AlarmEndpoint() {
/* 42 */     GsonBuilder gsonBuilder = new GsonBuilder();
/* 43 */     gsonBuilder.registerTypeAdapter(Date.class, new JsonSerializer<Date>()
/*    */         {
/*    */           
/*    */           public JsonElement serialize(Date src, Type typeOfSrc, JsonSerializationContext context)
/*    */           {
/* 48 */             return (JsonElement)new JsonPrimitive(Long.valueOf(src.getTime()));
/*    */           }
/*    */         });
/* 51 */     this.gson = gsonBuilder.create();
/*    */   }
/*    */   
/*    */   @OnOpen
/*    */   public void onOpen(Session session) {
/* 56 */     setSession(session);
/* 57 */     this.logger.info("Websocket Session:'{}' Started...", session.getId());
/* 58 */     this.registor.registerAlarmMessageListener(o -> {
/*    */           if (o instanceof AlarmMessageDTO) {
/*    */             send((AlarmMessageDTO)o);
/*    */           } else if (o instanceof String) {
/*    */             JsonObjectBuilder builder = Json.createObjectBuilder();
/*    */             builder.add("remove", (String)o);
/*    */             StringWriter writer = new StringWriter();
/*    */             Json.createWriter(writer).writeObject(builder.build());
/*    */             send(writer.toString());
/*    */           } 
/*    */         });
/*    */   }
/*    */ 
/*    */   
/*    */   @OnClose
/*    */   public void onClose(Session session, CloseReason closeReason) {
/* 74 */     this.logger.info("Websocket Session:'{}' Ended..., reason:'{}'", session.getId(), closeReason);
/* 75 */     this.registor.clear();
/*    */   }
/*    */   
/*    */   @OnError
/*    */   public void onError(Session session, Throwable t) {
/* 80 */     this.logger.warn("Websocket Session:'{}' Error!", session.getId(), t);
/* 81 */     this.registor.clear();
/*    */   }
/*    */   
/*    */   protected void send(AlarmMessageDTO dto) {
/*    */     try {
/* 86 */       if (dto != null) {
/* 87 */         send(this.gson.toJson(dto));
/*    */       }
/* 89 */     } catch (Exception e) {
/* 90 */       this.logger.error("send Error..., reason:'{}'", e);
/* 91 */       this.registor.clear();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\機房門禁10.121.41.38\ngtms\ao\lib.src\ao-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ao\restful\ws\AlarmEndpoint.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */