/*    */ package com.hwacom.ngtms.hcce.am.server;
/*    */ 
/*    */ import com.hazelcast.core.IMap;
/*    */ import com.hwacom.ngtms.base.hazelcast.HazelcastClient;
/*    */ import com.hwacom.ngtms.base.hazelcast.HzDistObjEnum;
/*    */ import com.hwacom.ngtms.hcce.hz.HzMap;
/*    */ import com.hwacom.ngtms.hcce.shared.ClientHeartbeat;
/*    */ import java.net.InetAddress;
/*    */ import java.net.UnknownHostException;
/*    */ import java.util.Date;
/*    */ import java.util.Optional;
/*    */ import org.slf4j.Logger;
/*    */ import org.slf4j.LoggerFactory;
/*    */ import org.springframework.beans.factory.annotation.Autowired;
/*    */ import org.springframework.beans.factory.annotation.Value;
/*    */ import org.springframework.scheduling.annotation.Scheduled;
/*    */ import org.springframework.stereotype.Service;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Service
/*    */ public class ClientHeartbeatTask
/*    */ {
/* 27 */   private static final Logger logger = LoggerFactory.getLogger(ClientHeartbeatTask.class);
/*    */   
/*    */   @Value("${hcce.clientNode.heartbeat:false}")
/*    */   private boolean heartbeat;
/*    */   
/*    */   @Value("${hcce.clientNode.ip:#{null}}")
/*    */   private String ip;
/*    */   
/*    */   @Value("${hcce.clientNode.service:#{null}}")
/*    */   private String service;
/*    */   
/*    */   @Value("${hcce.clientNode.group:hc_client_group}")
/*    */   private String hcClientGroup;
/*    */   
/*    */   @Value("${hcce.clientNode.name:#{null}}")
/*    */   private String nodeName;
/*    */   @Autowired
/*    */   private HazelcastClient hazelcastClient;
/*    */   
/*    */   @Scheduled(cron = "${hcce.clientNode.heartbeat.cron:15/30 * * * * ?}")
/*    */   public void execute() {
/* 48 */     if (this.heartbeat) {
/* 49 */       logger.debug("Client heartbeat.");
/* 50 */       ClientHeartbeat heartbeat = new ClientHeartbeat();
/* 51 */       heartbeat.setIp(
/* 52 */           Optional.<String>ofNullable(this.ip)
/* 53 */           .orElseGet(() -> {
/*    */               
/*    */               try {
/*    */                 return InetAddress.getLocalHost().getHostAddress();
/* 57 */               } catch (UnknownHostException e) {
/*    */                 return null;
/*    */               } 
/*    */             }));
/* 61 */       heartbeat.setService(this.service);
/* 62 */       heartbeat.setGroupName(this.hcClientGroup);
/* 63 */       heartbeat.setNodeName(this.nodeName);
/* 64 */       heartbeat.setTime(new Date());
/* 65 */       IMap<String, ClientHeartbeat> heartbeatMap = this.hazelcastClient.getIMap((HzDistObjEnum)HzMap.ClientHeartbeat);
/* 66 */       heartbeatMap.putIfAbsent(heartbeat.getId(), heartbeat);
/* 67 */       heartbeatMap.merge(heartbeat.getId(), heartbeat, (h1, h2) -> paramClientHeartbeat1);
/* 68 */       logger.debug("Put heartbeat to ClientHeartbeat map success.");
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\lib\hcManager-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\hcce\am\server\ClientHeartbeatTask.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */