/*     */ package com.hwacom.ngtms.hcce.test;
/*     */ 
/*     */ import com.hazelcast.config.Config;
/*     */ import com.hazelcast.core.Hazelcast;
/*     */ import com.hazelcast.core.HazelcastInstance;
/*     */ import com.hwacom.ngtms.base.hazelcast.HzUtils;
/*     */ import com.hwacom.ngtms.base.hazelcast.HzUtilsSetter;
/*     */ import com.hwacom.ngtms.base.rmi.RmiUtils;
/*     */ import com.hwacom.ngtms.hcce.config.model.DynamicConfig;
/*     */ import com.hwacom.ngtms.hcce.config.repository.DynamicConfigRepository;
/*     */ import com.hwacom.ngtms.hcce.fme.controller.FmeController;
/*     */ import com.hwacom.ngtms.hcce.fme.controller.fm.FmeMainBase;
/*     */ import com.hwacom.ngtms.hcce.shared.HcceEnv;
/*     */ import com.hwacom.ngtms.hcce.test.frameworkcontext.FmTestConfig;
/*     */ import com.hwacom.ngtms.hcce.test.loader.Csv2Table;
/*     */ import com.hwacom.ngtms.hcce.test.loader.DbType;
/*     */ import java.util.concurrent.atomic.AtomicBoolean;
/*     */ import javax.annotation.Resource;
/*     */ import org.junit.AfterClass;
/*     */ import org.junit.Before;
/*     */ import org.junit.BeforeClass;
/*     */ import org.junit.runner.RunWith;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.core.env.Environment;
/*     */ import org.springframework.test.context.ContextConfiguration;
/*     */ import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
/*     */ import org.springframework.test.context.support.AnnotationConfigContextLoader;
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
/*     */ @RunWith(SpringJUnit4ClassRunner.class)
/*     */ @ContextConfiguration(classes = {FmTestConfig.class}, loader = AnnotationConfigContextLoader.class)
/*     */ public class FmTestCase
/*     */ {
/*     */   public enum DbName
/*     */   {
/*  72 */     OLDB,
/*  73 */     HDDB,
/*  74 */     DRBD;
/*     */   }
/*     */ 
/*     */   
/*     */   static {
/*  79 */     System.setProperty("hcce.config.file", "conf/test/fmTest.properties");
/*     */   }
/*     */   
/*  82 */   private static AtomicBoolean initialized = new AtomicBoolean();
/*     */   
/*     */   private static FmeController staticFmeController;
/*     */   
/*     */   @Autowired
/*     */   private Config hazelcastConfig;
/*     */   
/*     */   @Autowired
/*     */   private FmeController fmeController;
/*     */   
/*     */   @Autowired
/*     */   protected DynamicConfigRepository dynamicConfigRepository;
/*     */   
/*     */   @AfterClass
/*     */   public static void afterClass() {
/*  97 */     staticFmeController.stop();
/*  98 */     HzUtils.getHzInstance().shutdown(); } @Autowired
/*     */   protected HcceEnv hcceEnv; @Resource
/*     */   protected Environment environment; private static FmeInfo[] fmeInfos; @BeforeClass
/*     */   public static void beforeClass() {} @Before
/*     */   public void creetMockHcce() throws Exception {
/* 103 */     if (!initialized.getAndSet(true)) {
/* 104 */       importDataBeforeHcClusterStart();
/* 105 */       HazelcastInstance hzInstance = Hazelcast.newHazelcastInstance(this.hazelcastConfig);
/* 106 */       HzUtilsSetter.setHzInstance(hzInstance);
/* 107 */       importDataAfterHcClusterStarted();
/* 108 */       staticFmeController = this.fmeController;
/* 109 */       fmeInfos = getFmeInfos();
/* 110 */       if (fmeInfos != null) {
/* 111 */         for (FmeInfo fmeInfo : fmeInfos) {
/* 112 */           this.fmeController.addFme(fmeInfo
/* 113 */               .getFmeName(), fmeInfo.getFmClassName(), fmeInfo.getDescription());
/* 114 */           this.fmeController.startFme(fmeInfo.getFmeName());
/* 115 */           FmeMainBase fmeMainBase = this.fmeController.getFme(fmeInfo.getFmeName());
/* 116 */           while (fmeMainBase.getFmStartTime() == 0L) {
/* 117 */             Thread.sleep(1L);
/*     */           }
/*     */         } 
/* 120 */         Thread.sleep(1000L);
/*     */       } 
/* 122 */       importDataAfterFmInstanceStarted();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void restartFme(String fmeName) throws Exception {
/* 132 */     this.fmeController.removeFme(fmeName);
/* 133 */     for (FmeInfo fmeInfo : fmeInfos) {
/* 134 */       if (fmeInfo.getFmeName().equals(fmeName)) {
/* 135 */         this.fmeController.addFme(fmeInfo
/* 136 */             .getFmeName(), fmeInfo.getFmClassName(), fmeInfo.getDescription());
/* 137 */         this.fmeController.startFme(fmeInfo.getFmeName());
/* 138 */         FmeMainBase fmeMainBase = this.fmeController.getFme(fmeInfo.getFmeName());
/* 139 */         while (fmeMainBase.getFmStartTime() == 0L) {
/* 140 */           Thread.sleep(1L);
/*     */         }
/*     */         break;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void importDataBeforeHcClusterStart() throws Exception {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void importDataAfterHcClusterStarted() throws Exception {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void importDataAfterFmInstanceStarted() throws Exception {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected FmeInfo[] getFmeInfos() throws Exception {
/* 174 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected <T> T getFm(String fmeName) {
/* 184 */     return (T)this.fmeController.getFme(fmeName);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected <T> T getFmRemote(String serviceName, Class<T> serviceInterface) {
/* 194 */     return (T)RmiUtils.getRemoteClient("localhost", this.hcceEnv
/* 195 */         .getNodeName() + "/" + serviceName, serviceInterface);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void setDynamicConfig(String fmeName, String cfgName, String cfgValue) {
/* 206 */     this.dynamicConfigRepository.save(new DynamicConfig(
/* 207 */           HzUtils.getGroupName(), fmeName, cfgName, cfgValue));
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
/*     */   protected void loadData(DbName dbName, String... dataFileNames) throws Exception {
/*     */     String driverName, url, userName, password;
/* 223 */     switch (dbName) {
/*     */       case DRBD:
/* 225 */         driverName = this.environment.getProperty("drds.driverClassName");
/* 226 */         url = this.environment.getProperty("drds.url");
/* 227 */         userName = this.environment.getProperty("drds.username");
/* 228 */         password = this.environment.getProperty("drds.password");
/*     */         break;
/*     */       case HDDB:
/* 231 */         driverName = this.environment.getProperty("hdds.driverClassName");
/* 232 */         url = this.environment.getProperty("hdds.url");
/* 233 */         userName = this.environment.getProperty("hdds.username");
/* 234 */         password = this.environment.getProperty("hdds.password");
/*     */         break;
/*     */       case OLDB:
/* 237 */         driverName = this.environment.getProperty("olds.driverClassName");
/* 238 */         url = this.environment.getProperty("olds.url");
/* 239 */         userName = this.environment.getProperty("olds.username");
/* 240 */         password = this.environment.getProperty("olds.password");
/*     */         break;
/*     */       default:
/* 243 */         throw new RuntimeException("unkonwn DB name: " + dbName);
/*     */     } 
/*     */     
/* 246 */     DbType dbType = determineDbType(driverName);
/* 247 */     Csv2Table loadData2Table = new Csv2Table(dbType, url, userName, password);
/* 248 */     loadData2Table.loadData(true, dataFileNames);
/* 249 */     loadData2Table.close();
/*     */   }
/*     */   
/*     */   private DbType determineDbType(String driverClassName) {
/* 253 */     if (driverClassName.contains("mysql")) return DbType.MySQL; 
/* 254 */     if (driverClassName.contains("h2")) return DbType.H2; 
/* 255 */     return DbType.UnKnown;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\lib\hcce-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\hcce\test\FmTestCase.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */