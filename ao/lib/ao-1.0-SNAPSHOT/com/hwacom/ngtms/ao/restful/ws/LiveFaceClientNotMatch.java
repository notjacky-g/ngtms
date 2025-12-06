/*     */ package com.hwacom.ngtms.ao.restful.ws;
/*     */ 
/*     */ import com.hwacom.ngtms.ao.fm.service.LifeFaceLockCardService;
/*     */ import com.hwacom.ngtms.ao.fm.service.LiveFaceService;
/*     */ import com.hwacom.ngtms.ao.shared.dto.LifeFaceDTO;
/*     */ import com.hwacom.ngtms.ao.shared.dto.LifeFaceSessionDTO;
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
/*     */ public class LiveFaceClientNotMatch
/*     */ {
/*  29 */   private static final Logger logger = LoggerFactory.getLogger(LiveFaceClientNotMatch.class);
/*     */   
/*     */   private static LiveFaceService liveFaceService;
/*     */   
/*     */   private static LifeFaceLockCardService lifeFaceLockCardService;
/*     */   
/*     */   @Autowired
/*     */   public void setLiveFaceService(LiveFaceService liveFaceService) {
/*  37 */     LiveFaceClientNotMatch.liveFaceService = liveFaceService;
/*     */   }
/*     */   
/*     */   @Autowired
/*     */   public void setLifeFaceLockCardService(LifeFaceLockCardService lifeFaceLockCardService) {
/*  42 */     LiveFaceClientNotMatch.lifeFaceLockCardService = lifeFaceLockCardService;
/*     */   }
/*     */   
/*  45 */   Session userSession = null;
/*     */   
/*     */   @OnOpen
/*     */   public void onOpen(Session userSession) {
/*  49 */     logger.info("LiveFaceClientNotMatch userSessionId:'{}' Started...", userSession.getId());
/*     */   }
/*     */   
/*     */   @OnMessage
/*     */   public void onMessage(Session userSession, String message) throws ParseException {
/*  54 */     logger.debug("LiveFaceClientNotMatch userSessionId:'{}' message:'{}'", userSession
/*  55 */         .getId(), message);
/*  56 */     JSONObject resultObj = new JSONObject(message.toString());
/*  57 */     if (resultObj.has("objectId")) {
/*  58 */       LifeFaceDTO dto = new LifeFaceDTO();
/*  59 */       if (resultObj.get("cameraName") != null) {
/*  60 */         dto.setCameraName(resultObj.get("cameraName").toString());
/*     */       }
/*  62 */       if (resultObj.get("isMatch") != null) {
/*  63 */         dto.setIsMatch(resultObj.get("isMatch").toString());
/*     */       }
/*  65 */       if (resultObj.get("date") != null) {
/*  66 */         dto.setDate(resultObj.get("date").toString());
/*     */       }
/*  68 */       if (dto.getCameraName() != null) {
/*  69 */         lifeFaceLockCardService.processLiveFaceNotMatchMessage(dto);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @OnClose
/*     */   public void onClose(Session userSession, CloseReason closeReason) throws IOException, DeploymentException, InterruptedException {
/*  77 */     logger.info("LiveFaceClientNotMatch userSessionId:'{}' Ended..., reason:'{}'", userSession
/*     */         
/*  79 */         .getId(), closeReason);
/*     */ 
/*     */     
/*  82 */     Thread.sleep(3000L);
/*     */     
/*  84 */     LifeFaceSessionDTO sessionDTO = new LifeFaceSessionDTO();
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
/*     */   @OnError
/*     */   public void onError(Session userSession, Throwable t) {
/* 106 */     logger.warn("LiveFace userSessionId:'{}' Error!", userSession.getId(), t);
/*     */   }
/*     */ }


/* Location:              D:\TC\NJ\機房門禁10.121.41.38\ngtms\ao\lib.src\ao-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ao\restful\ws\LiveFaceClientNotMatch.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */