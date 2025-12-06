/*     */ package com.hwacom.ngtms.node.performance.fm.task;
/*     */ 
/*     */ import com.hazelcast.core.IMap;
/*     */ import com.hwacom.ngtms.base.hazelcast.HzUtils;
/*     */ import com.hwacom.ngtms.base.nodeperf.NodePerformance;
/*     */ import com.hwacom.ngtms.base.ssh.CmdExecBinaryResult;
/*     */ import com.hwacom.ngtms.hcce.fme.controller.fm.JobBase;
/*     */ import com.hwacom.ngtms.hcce.remote.CmRemoteImpl;
/*     */ import com.hwacom.ngtms.node.performance.fm.hz.NodePerformanceHzMap;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.util.Arrays;
/*     */ import javax.annotation.Resource;
/*     */ import org.quartz.JobDataMap;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.core.env.Environment;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class NodePerformanceClientTask
/*     */   extends JobBase
/*     */ {
/*  28 */   private static final Logger logger = LoggerFactory.getLogger(NodePerformanceClientTask.class);
/*     */   
/*     */   @Resource
/*     */   private Environment environment;
/*     */   @Autowired
/*     */   private CmRemoteImpl remote;
/*  34 */   private String[] servers = null;
/*     */   
/*     */   protected void init(JobDataMap arg0)
/*     */   {
/*  38 */     if (this.servers == null) readServers();
/*     */   }
/*     */   
/*     */   protected void process()
/*     */   {
/*  43 */     if (this.servers == null) return;
/*  44 */     for (String server : this.servers) {
/*     */       try {
/*  46 */         readServerPerformance(server);
/*     */       } catch (Exception ex) {
/*  48 */         logger.warn("get {} performace failed!", server, ex);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private void readServers() {
/*  54 */     String serversProperty = this.environment.getProperty("ssh.server.names");
/*  55 */     if (serversProperty == null) {
/*  56 */       logger.warn("ssh.server.names not found!");
/*  57 */       return;
/*     */     }
/*  59 */     this.servers = serversProperty.split(",");
/*     */   }
/*     */   
/*     */   private void readServerPerformance(String server) {
/*  63 */     long t1 = System.currentTimeMillis();
/*  64 */     ObjectInputStream ois = null;
/*  65 */     String sshId = null;
/*     */     try {
/*  67 */       sshId = this.remote.createSshClient(server);
/*     */       
/*  69 */       CmdExecBinaryResult cmdExecBinaryResult = this.remote.execCmdBinary(sshId, "~/nodePerf/nodePerf.sh");
/*  70 */       if ((cmdExecBinaryResult.getStdOut() != null) && 
/*  71 */         (cmdExecBinaryResult.getStdOut().length > 0)) {
/*  72 */         ois = new ObjectInputStream(new ByteArrayInputStream(cmdExecBinaryResult.getStdOut()));
/*     */         
/*  74 */         NodePerformance nodePerformance = (NodePerformance)ois.readObject();
/*  75 */         nodePerformance.setGroupName(server);
/*  76 */         nodePerformance.setNodeName("Performace");
/*     */         try {
/*  78 */           HzUtils.getMap(NodePerformanceHzMap.NodePerformanceLog).put(server, nodePerformance);
/*     */         } catch (Exception ex) {
/*  80 */           logger.info("Failed to put performance information of server: " + server, ex);
/*     */         }
/*     */       }
/*  83 */       else if (cmdExecBinaryResult.getStdErr() != null) {
/*  84 */         logger.error("Failed to execute '~/nodePerf/nodePerf.sh' on " + server + ", StdErr: " + 
/*     */         
/*     */ 
/*     */ 
/*  88 */           Arrays.toString(cmdExecBinaryResult.getStdErr()));
/*     */       }
/*     */     }
/*     */     catch (Exception ex) {
/*  92 */       logger.error("Failed to get performance of " + server, ex);
/*     */     } finally {
/*  94 */       if (ois != null)
/*     */         try {
/*  96 */           ois.close();
/*     */         } catch (IOException e) {
/*  98 */           logger.error("ois.close()", e);
/*     */         }
/* 100 */       if (sshId != null) this.remote.closeSshClient(sshId);
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nodePerformance-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\node\performance\fm\task\NodePerformanceClientTask.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */