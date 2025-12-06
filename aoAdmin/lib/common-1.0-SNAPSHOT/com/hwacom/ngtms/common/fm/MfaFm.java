/*    */ package com.hwacom.ngtms.common.fm;
/*    */ 
/*    */ import com.hazelcast.core.EntryEvent;
/*    */ import com.hazelcast.core.IMap;
/*    */ import com.hazelcast.map.listener.EntryAddedListener;
/*    */ import com.hazelcast.map.listener.EntryUpdatedListener;
/*    */ import com.hazelcast.map.listener.MapListener;
/*    */ import com.hwacom.ngtms.base.hazelcast.HzDistObjEnum;
/*    */ import com.hwacom.ngtms.base.hazelcast.HzUtils;
/*    */ import com.hwacom.ngtms.common.fm.hz.CommonHzMap;
/*    */ import com.hwacom.ngtms.common.fm.model.User;
/*    */ import com.hwacom.ngtms.common.fm.service.MfaService;
/*    */ import com.hwacom.ngtms.hcce.core.exception.FmException;
/*    */ import com.hwacom.ngtms.hcce.fme.controller.fm.FmeMainBase;
/*    */ import java.util.Objects;
/*    */ import java.util.concurrent.ExecutorService;
/*    */ import java.util.concurrent.Executors;
/*    */ import org.slf4j.Logger;
/*    */ import org.slf4j.LoggerFactory;
/*    */ import org.springframework.beans.factory.annotation.Autowired;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MfaFm
/*    */   extends FmeMainBase
/*    */ {
/* 27 */   private static final Logger logger = LoggerFactory.getLogger(MfaFm.class);
/*    */   
/*    */   @Autowired
/*    */   private MfaService mfaService;
/*    */   private ExecutorService threadPool;
/*    */   
/*    */   public MfaFm(String fmeName, String description) {
/* 34 */     super(fmeName, description);
/* 35 */     this.threadPool = Executors.newFixedThreadPool(1);
/*    */   }
/*    */ 
/*    */   
/*    */   public void init() throws FmException {
/* 40 */     logger.info("{} init", getFmeName());
/*    */   }
/*    */ 
/*    */   
/*    */   protected void runFm() throws Exception {
/* 45 */     logger.info("{} started", getFmeName());
/* 46 */     Runnable listenerRemover = null;
/*    */     try {
/* 48 */       listenerRemover = registerEntryListeners();
/* 49 */       this.threadPool.execute(() -> this.mfaService.sendSecretForExistUser());
/* 50 */       keepRunning();
/* 51 */     } catch (Exception e) {
/* 52 */       logger.error("scheduleJob Error", e);
/*    */     } finally {
/* 54 */       logger.info("{} stopped", getFmeName());
/* 55 */       if (listenerRemover != null) {
/* 56 */         listenerRemover.run();
/*    */       }
/* 58 */       if (this.threadPool != null) {
/* 59 */         this.threadPool.shutdownNow();
/*    */       }
/*    */     } 
/*    */   }
/*    */   
/*    */   private Runnable registerEntryListeners() {
/* 65 */     IMap<String, User> userMap = HzUtils.getMap((HzDistObjEnum)CommonHzMap.AccountUser);
/* 66 */     EntryAddedListener<String, User> entryAddedListener = event -> this.threadPool.execute(());
/*    */ 
/*    */ 
/*    */     
/* 70 */     EntryUpdatedListener<String, User> entryUpdatedListener = event -> {
/*    */         User user = (User)event.getValue();
/*    */         
/*    */         User oldUser = (User)event.getOldValue();
/*    */         if (!Objects.equals(user.getMail(), oldUser.getMail())) {
/*    */           this.mfaService.generateSecretIfAbsentAndSendEmail(user.getLogin());
/*    */         }
/*    */       };
/* 78 */     String addedListenerId = userMap.addEntryListener((MapListener)entryAddedListener, true);
/* 79 */     String updatedListenerId = userMap.addEntryListener((MapListener)entryUpdatedListener, true);
/* 80 */     return () -> {
/*    */         paramIMap.removeEntryListener(paramString1);
/*    */         paramIMap.removeEntryListener(paramString2);
/*    */       };
/*    */   }
/*    */   
/*    */   private void keepRunning() {
/* 87 */     while (checkFmKeepRunning()) {
/*    */       try {
/* 89 */         Thread.sleep(5000L);
/* 90 */       } catch (InterruptedException interruptedException) {}
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isAllowConcurrentExecution() {
/* 97 */     return false;
/*    */   }
/*    */   
/*    */   public void startTesting() throws FmException {}
/*    */ }


/* Location:              C:\User\\user\Desktop\lib\common-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\common\fm\MfaFm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */