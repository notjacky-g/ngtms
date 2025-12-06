/*     */ package com.hwacom.ngtms.ao.restful.ws;
/*     */ 
/*     */ import com.hwacom.ngtms.ao.fm.service.LifeFaceLockCardService;
/*     */ import com.hwacom.ngtms.ao.fm.service.LiveFaceService;
/*     */ import com.hwacom.ngtms.ao.shared.dto.LifeFaceDTO;
/*     */ import java.io.IOException;
/*     */ import java.text.ParseException;
/*     */ import javax.websocket.ClientEndpoint;
/*     */ import javax.websocket.CloseReason;
/*     */ import javax.websocket.DeploymentException;
/*     */ import javax.websocket.OnClose;
/*     */ import javax.websocket.OnError;
/*     */ import javax.websocket.OnMessage;
/*     */ import javax.websocket.OnOpen;
/*     */ import javax.websocket.Session;
/*     */ import org.json.JSONObject;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.context.annotation.Scope;
/*     */ import org.springframework.stereotype.Service;
/*     */ 
/*     */ @ClientEndpoint
/*     */ @Service
/*     */ @Scope("prototype")
/*     */ public class LiveFaceClientMatch
/*     */ {
/*  28 */   private static final Logger logger = LoggerFactory.getLogger(LiveFaceClientMatch.class);
/*     */   
/*     */   private static LiveFaceService liveFaceService;
/*     */   
/*     */   private static LifeFaceLockCardService lifeFaceLockCardService;
/*     */   
/*     */   @Autowired
/*     */   public void setLiveFaceService(LiveFaceService liveFaceService) {
/*  36 */     LiveFaceClientMatch.liveFaceService = liveFaceService;
/*     */   }
/*     */   
/*     */   @Autowired
/*     */   public void setLifeFaceLockCardService(LifeFaceLockCardService lifeFaceLockCardService) {
/*  41 */     LiveFaceClientMatch.lifeFaceLockCardService = lifeFaceLockCardService;
/*     */   }
/*     */   
/*  44 */   Session userSession = null;
/*     */   
/*     */   @OnOpen
/*     */   public void onOpen(Session userSession) {
/*  48 */     logger.debug("LiveFaceClientMatch userSessionId:'{}' Started...", userSession.getId());
/*     */   }
/*     */   
/*     */   @OnMessage
/*     */   public void onMessage(Session userSession, String message) throws ParseException {
/*  53 */     logger.debug("LiveFaceClientMatch userSessionId:'{}' message:'{}'", userSession
/*  54 */         .getId(), message);
/*  55 */     JSONObject resultObj = new JSONObject(message.toString());
/*  56 */     if (resultObj.has("objectId")) {
/*  57 */       LifeFaceDTO dto = new LifeFaceDTO();
/*  58 */       if (resultObj.get("cameraName") != null) {
/*  59 */         dto.setCameraName(resultObj.get("cameraName").toString());
/*     */       }
/*  61 */       if (resultObj.get("personCard") != null) {
/*  62 */         dto.setCardNumber(resultObj.get("personCard").toString());
/*     */       }
/*  64 */       if (resultObj.get("isMatch") != null) {
/*  65 */         dto.setIsMatch(resultObj.get("isMatch").toString());
/*     */       }
/*  67 */       if (resultObj.get("date") != null) {
/*  68 */         dto.setDate(resultObj.get("date").toString());
/*     */       }
/*  70 */       if (dto.getCameraName() != null) {
/*  71 */         lifeFaceLockCardService.processLiveFaceMatchMessage(dto);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @OnClose
/*     */   public void onClose(Session userSession, CloseReason closeReason) throws IOException, DeploymentException, InterruptedException {
/*  79 */     logger.debug("LiveFaceClientMatch userSessionId:'{}' Ended..., reason:'{}'", userSession
/*     */         
/*  81 */         .getId(), closeReason);
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
/*     */   
/*     */   @OnError
/*     */   public void onError(Session userSession, Throwable t) {
/* 108 */     logger.error("LiveFace userSessionId:'{}' Error!", userSession.getId(), t);
/*     */   }
/*     */ }


/* Location:              D:\TC\NJ\機房門禁10.121.41.38\ngtms\ao\lib.src\ao-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ao\restful\ws\LiveFaceClientMatch.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */