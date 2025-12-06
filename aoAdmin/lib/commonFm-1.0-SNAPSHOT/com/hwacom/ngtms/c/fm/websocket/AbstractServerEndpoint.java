/*     */ package com.hwacom.ngtms.c.fm.websocket;
/*     */ 
/*     */ import com.hwacom.ngtms.base.oplog.shared.OperationItem;
/*     */ import com.hwacom.ngtms.c.fm.service.TcResponseLogQueueService;
/*     */ import com.hwacom.ngtms.c.shared.SubSystem;
/*     */ import com.hwacom.ngtms.c.shared.TcOplogResult;
/*     */ import com.hwacom.ngtms.common.websocket.AbstractServerEndpoint;
/*     */ import com.hwacom.ngtms.ncc.remote.TcResponse;
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ import javax.servlet.http.HttpSession;
/*     */ import javax.websocket.EndpointConfig;
/*     */ import javax.websocket.Session;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.security.core.context.SecurityContext;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class AbstractServerEndpoint
/*     */   extends AbstractServerEndpoint
/*     */ {
/*  30 */   private static final Logger logger = LoggerFactory.getLogger(AbstractServerEndpoint.class);
/*     */   
/*     */   @Autowired
/*     */   private TcResponseLogQueueService tcResponseLogQueueService;
/*     */   @Autowired
/*     */   private OperationLogEndPoint operationLogEndpoint;
/*     */   
/*     */   protected void setSession(Session session) {
/*  38 */     this.session = session;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private OperationItem operationItem;
/*     */ 
/*     */ 
/*     */   
/*     */   private TcOplogResult tcOplogResult;
/*     */ 
/*     */ 
/*     */   
/*     */   protected void init(Session session, EndpointConfig config, OperationItem operationItem, SubSystem subSystem) {
/*  53 */     setSession(session);
/*     */     
/*  55 */     HttpSession httpSession = (HttpSession)config.getUserProperties().get(HttpSession.class.getName());
/*  56 */     if (httpSession != null) {
/*     */ 
/*     */       
/*  59 */       SecurityContext securityContext = (SecurityContext)httpSession.getAttribute("SPRING_SECURITY_CONTEXT");
/*     */       
/*  61 */       if (securityContext != null) {
/*  62 */         this.userId = securityContext.getAuthentication().getName();
/*     */       }
/*  64 */       this.ip = (String)httpSession.getAttribute("REMOTE_ADDR");
/*     */     } 
/*  66 */     this.operationItem = operationItem;
/*  67 */     this.tcOplogResult = new TcOplogResult(this.ip, this.userId, operationItem, subSystem);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void setSubSystem(SubSystem subSystem) {
/*  76 */     this.tcOplogResult = new TcOplogResult(this.ip, this.userId, this.operationItem, subSystem);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void offerTcResponseLog(String deviceName, TcResponse tcResponse, String remark, String logMessage) {
/*  82 */     logger.debug("Offer tcResponse log to Queue: deviceName='{}', logMessage='{}'", deviceName, logMessage);
/*     */     
/*  84 */     this.tcOplogResult.setDeviceName(deviceName);
/*  85 */     this.tcOplogResult.setTcResponse(tcResponse);
/*  86 */     this.tcOplogResult.setRemark(remark);
/*  87 */     this.tcOplogResult.setLogMessage(logMessage);
/*  88 */     this.tcResponseLogQueueService.offerQueue(this.tcOplogResult);
/*  89 */     this.operationLogEndpoint.sendOpLog(this.userId, this.ip, this.tcOplogResult
/*     */ 
/*     */         
/*  92 */         .getSubSystem().name(), this.operationItem
/*  93 */         .name(), deviceName, logMessage, new Date(), tcResponse
/*     */ 
/*     */ 
/*     */         
/*  97 */         .getResult().name(), remark);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void offerMessageLog(String deviceName, Boolean result, String remark, String logMessage) {
/* 104 */     logger.debug("Offer message log to Queue: deviceName='{}', remark='{}', logMessage='{}'", new Object[] { deviceName, remark, logMessage });
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 109 */     TcResponse response = new TcResponse();
/* 110 */     response.setResult(result.booleanValue() ? TcResponse.Result.SUCCESS : TcResponse.Result.FAIL);
/* 111 */     this.tcOplogResult.setDeviceName(deviceName);
/* 112 */     this.tcOplogResult.setTcResponse(response);
/* 113 */     this.tcOplogResult.setRemark(remark);
/* 114 */     this.tcOplogResult.setLogMessage(logMessage);
/* 115 */     this.tcResponseLogQueueService.offerQueue(this.tcOplogResult);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void offerExceptionLog(List<String> deviceNames, RuntimeException e, String logMessage) {
/* 121 */     logger.debug("Offer exception log to Queue: deviceNames='{}', exception='{}', logMessage='{}'", new Object[] { deviceNames, e
/*     */ 
/*     */           
/* 124 */           .getMessage(), logMessage });
/*     */     
/* 126 */     deviceNames.forEach(deviceName -> offerMessageLog(deviceName, Boolean.valueOf(false), paramRuntimeException.getMessage(), paramString1));
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\lib\commonFm-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\c\fm\websocket\AbstractServerEndpoint.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */