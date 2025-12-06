/*    */ package com.hwacom.ngtms.hcce.frameworkcontext;
/*    */ 
/*    */ import com.hwacom.ngtms.base.rmi.RmiUtils;
/*    */ import com.hwacom.ngtms.hcce.core.message.CmInternal;
/*    */ import com.hwacom.ngtms.hcce.core.message.CmInternalImpl;
/*    */ import com.hwacom.ngtms.hcce.core.message.NmInternal;
/*    */ import com.hwacom.ngtms.hcce.core.message.NmInternalImpl;
/*    */ import com.hwacom.ngtms.hcce.hz.DistObjType;
/*    */ import com.hwacom.ngtms.hcce.hz.HcceHzPortable;
/*    */ import com.hwacom.ngtms.hcce.hz.HzDistObjRegister;
/*    */ import com.hwacom.ngtms.hcce.hz.HzIdGen;
/*    */ import com.hwacom.ngtms.hcce.hz.HzMap;
/*    */ import com.hwacom.ngtms.hcce.hz.HzPortableRegister;
/*    */ import com.hwacom.ngtms.hcce.hz.HzQueue;
/*    */ import com.hwacom.ngtms.hcce.hz.HzTopic;
/*    */ import com.hwacom.ngtms.hcce.remote.CmRemote;
/*    */ import com.hwacom.ngtms.hcce.remote.CmRemoteImpl;
/*    */ import com.hwacom.ngtms.hcce.shared.HcceEnv;
/*    */ import javax.annotation.PostConstruct;
/*    */ import org.springframework.beans.factory.annotation.Autowired;
/*    */ import org.springframework.beans.factory.annotation.Value;
/*    */ import org.springframework.context.annotation.Bean;
/*    */ import org.springframework.context.annotation.Configuration;
/*    */ import org.springframework.remoting.rmi.RmiServiceExporter;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Configuration
/*    */ public class HcceConfig
/*    */ {
/*    */   @Value("${hcce.rmi.registry.port:4320}")
/*    */   private int rmiRegistryPort;
/*    */   @Autowired
/*    */   private HzDistObjRegister hzDistObjRegister;
/*    */   @Autowired
/*    */   private HcceEnv hcceEnv;
/*    */   
/*    */   @PostConstruct
/*    */   public void init()
/*    */   {
/* 45 */     this.hzDistObjRegister.regHzDistObj(DistObjType.MAP, HzMap.class);
/* 46 */     this.hzDistObjRegister.regHzDistObj(DistObjType.QUEUE, HzQueue.class);
/* 47 */     this.hzDistObjRegister.regHzDistObj(DistObjType.TOPIC, HzTopic.class);
/* 48 */     this.hzDistObjRegister.regHzDistObj(DistObjType.ID_GENERATOR, HzIdGen.class);
/* 49 */     HzPortableRegister.registerPortable(HcceHzPortable.class);
/*    */   }
/*    */   
/*    */   @Bean
/*    */   public RmiServiceExporter nmInternalRemote(NmInternalImpl nmInternalImpl) {
/* 54 */     RmiServiceExporter export = new RmiServiceExporter();
/*    */     
/* 56 */     export.setServiceName(this.hcceEnv.getNodeName() + "/" + "nmInternal");
/* 57 */     export.setRegistryPort(RmiUtils.getRmiRegistryPort());
/* 58 */     export.setService(nmInternalImpl);
/* 59 */     export.setServiceInterface(NmInternal.class);
/* 60 */     return export;
/*    */   }
/*    */   
/*    */   @Bean
/*    */   public RmiServiceExporter cmInternalRemote(CmInternalImpl cmInternalImpl) {
/* 65 */     RmiServiceExporter export = new RmiServiceExporter();
/*    */     
/* 67 */     export.setServiceName(this.hcceEnv.getNodeName() + "/" + "cmInternal");
/*    */     
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/* 74 */     export.setRegistryPort(RmiUtils.getRmiRegistryPort());
/* 75 */     export.setService(cmInternalImpl);
/* 76 */     export.setServiceInterface(CmInternal.class);
/* 77 */     return export;
/*    */   }
/*    */   
/*    */   @Bean
/*    */   public RmiServiceExporter cmRemote(CmRemoteImpl cmRemoteImpl) {
/* 82 */     RmiServiceExporter export = new RmiServiceExporter();
/*    */     
/* 84 */     export.setServiceName(this.hcceEnv.getNodeName() + "/" + "remote/cm");
/* 85 */     export.setRegistryPort(RmiUtils.getRmiRegistryPort());
/* 86 */     export.setService(cmRemoteImpl);
/* 87 */     export.setServiceInterface(CmRemote.class);
/* 88 */     return export;
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\hcce-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\hcce\frameworkcontext\HcceConfig.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */