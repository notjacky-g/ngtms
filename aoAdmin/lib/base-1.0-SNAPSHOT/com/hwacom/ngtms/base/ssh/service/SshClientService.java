/*     */ package com.hwacom.ngtms.base.ssh.service;
/*     */ 
/*     */ import com.hwacom.ngtms.base.ssh.CmdExecBinaryResult;
/*     */ import com.hwacom.ngtms.base.ssh.CmdExecResult;
/*     */ import com.hwacom.ngtms.base.ssh.CmdRealTimeResponse;
/*     */ import com.hwacom.ngtms.base.ssh.SshClient;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.util.Random;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import java.util.concurrent.atomic.AtomicLong;
/*     */ import javax.annotation.PreDestroy;
/*     */ import org.springframework.scheduling.annotation.Scheduled;
/*     */ import org.springframework.stereotype.Service;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Service
/*     */ public class SshClientService
/*     */ {
/*  24 */   private ConcurrentHashMap<String, SshClientMeta> sshClientPool = new ConcurrentHashMap<>();
/*     */   
/*  26 */   private AtomicLong sshClientId = new AtomicLong();
/*  27 */   private Random random = new Random();
/*     */   
/*     */   static class SshClientMeta {
/*     */     long timestamp;
/*     */     SshClient sshClient;
/*     */   }
/*     */   
/*     */   @PreDestroy
/*     */   public void destroy() {
/*  36 */     for (SshClientMeta sshClientMeta : this.sshClientPool.values()) {
/*  37 */       sshClientMeta.sshClient.close();
/*     */     }
/*     */   }
/*     */   
/*     */   @Scheduled(cron = "30 0 * * * ?")
/*     */   public void cleanIdleClient() {
/*  43 */     long curTime = System.currentTimeMillis();
/*  44 */     for (String sshId : this.sshClientPool.keySet()) {
/*  45 */       SshClientMeta sshClientMeta = this.sshClientPool.get(sshId);
/*  46 */       if (curTime - sshClientMeta.timestamp > 3600000L) {
/*  47 */         this.sshClientPool.remove(sshId);
/*  48 */         sshClientMeta.sshClient.close();
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public String createSshClient(String host, String userName, String password) throws IOException {
/*  54 */     String sshId = this.sshClientId.incrementAndGet() + "-" + this.random.nextLong();
/*  55 */     SshClient sshClient = new SshClient();
/*  56 */     sshClient.connect(host, userName, password);
/*  57 */     SshClientMeta sshClientMeta = new SshClientMeta();
/*  58 */     sshClientMeta.sshClient = sshClient;
/*  59 */     sshClientMeta.timestamp = System.currentTimeMillis();
/*  60 */     this.sshClientPool.put(sshId, sshClientMeta);
/*  61 */     return sshId;
/*     */   }
/*     */ 
/*     */   
/*     */   public String createSshClient(String host, String userName, File privateKey, String privateKeyPassword) throws IOException {
/*  66 */     String sshId = this.sshClientId.incrementAndGet() + "-" + this.random.nextLong();
/*  67 */     SshClient sshClient = new SshClient();
/*  68 */     sshClient.connect(host, userName, privateKey, privateKeyPassword);
/*  69 */     SshClientMeta sshClientMeta = new SshClientMeta();
/*  70 */     sshClientMeta.sshClient = sshClient;
/*  71 */     sshClientMeta.timestamp = System.currentTimeMillis();
/*  72 */     this.sshClientPool.put(sshId, sshClientMeta);
/*  73 */     return sshId;
/*     */   }
/*     */   
/*     */   public CmdExecResult execCmd(String sshId, String command) throws IOException {
/*  77 */     SshClientMeta sshClientMeta = this.sshClientPool.get(sshId);
/*  78 */     if (sshClientMeta != null) {
/*  79 */       sshClientMeta.timestamp = System.currentTimeMillis();
/*  80 */       return sshClientMeta.sshClient.execCmd(command);
/*  81 */     }  throw new IOException("Unknown sshId: " + sshId);
/*     */   }
/*     */   
/*     */   public CmdExecBinaryResult execCmdBinary(String sshId, String command) throws IOException {
/*  85 */     SshClientMeta sshClientMeta = this.sshClientPool.get(sshId);
/*  86 */     if (sshClientMeta != null) {
/*  87 */       sshClientMeta.timestamp = System.currentTimeMillis();
/*  88 */       return sshClientMeta.sshClient.execCmdBinary(command);
/*  89 */     }  throw new IOException("Unknown sshId: " + sshId);
/*     */   }
/*     */ 
/*     */   
/*     */   public void execCmd(String sshId, String command, CmdRealTimeResponse cmdRealTimeResponse) throws IOException {
/*  94 */     SshClientMeta sshClientMeta = this.sshClientPool.get(sshId);
/*  95 */     if (sshClientMeta != null)
/*  96 */     { sshClientMeta.timestamp = System.currentTimeMillis();
/*  97 */       sshClientMeta.sshClient.execCmd(command, cmdRealTimeResponse); }
/*  98 */     else { throw new IOException("Unknown sshId: " + sshId); }
/*     */   
/*     */   }
/*     */   public void closeSshClient(String sshId) {
/* 102 */     SshClientMeta sshClientMeta = this.sshClientPool.remove(sshId);
/* 103 */     if (sshClientMeta != null)
/* 104 */       sshClientMeta.sshClient.close(); 
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\lib\base-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\base\ssh\service\SshClientService.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */