/*    */ package com.hwacom.ngtms.ao.fm.service;
/*    */ 
/*    */ import com.hazelcast.core.IMap;
/*    */ import com.hazelcast.query.Predicate;
/*    */ import com.hazelcast.query.PredicateBuilder;
/*    */ import com.hwacom.ngtms.base.hazelcast.HzDistObjEnum;
/*    */ import com.hwacom.ngtms.base.hazelcast.HzUtils;
/*    */ import com.hwacom.ngtms.c.fm.hz.CommonFmHzMap;
/*    */ import com.hwacom.ngtms.c.fm.model.DeviceTcConfig;
/*    */ import org.slf4j.Logger;
/*    */ import org.slf4j.LoggerFactory;
/*    */ import org.springframework.beans.factory.annotation.Autowired;
/*    */ import org.springframework.stereotype.Service;
/*    */ 
/*    */ @Service
/*    */ public class CardReaderSynchronizeTimeService {
/* 17 */   private static final Logger logger = LoggerFactory.getLogger(CardReaderSynchronizeTimeService.class);
/*    */   @Autowired
/*    */   HunDureNcuServiceImpl hunDureNCUServiceImpl;
/*    */   
/*    */   public void process() {
/* 22 */     logger.debug("Start Synchronize CardReader Time.");
/*    */     try {
/* 24 */       IMap<String, DeviceTcConfig> configMap = HzUtils.getMap((HzDistObjEnum)CommonFmHzMap.DeviceTcConfig);
/* 25 */       PredicateBuilder pb = (new PredicateBuilder()).getEntryObject().get("deviceType").equal("NCU");
/* 26 */       for (DeviceTcConfig config : configMap.values((Predicate)pb)) {
/*    */         try {
/* 28 */           Boolean result = this.hunDureNCUServiceImpl.synchronizeNCUTime(config.getDeviceName());
/* 29 */           if (result.booleanValue()) {
/* 30 */             logger.debug("Synchronize Ncu = '{}'", config.getDeviceName()); continue;
/*    */           } 
/* 32 */           logger.error("Synchronize failed NCU = '{}'", config.getDeviceName());
/*    */         }
/* 34 */         catch (Exception e) {
/* 35 */           logger.error("Synchronize CardReader failed DeviceName = '{}'", e, config
/* 36 */               .getDeviceName());
/*    */         } 
/*    */       } 
/* 39 */     } catch (Exception e) {
/* 40 */       logger.error("Set Synchronize CardReader Time failed.", e);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\機房門禁10.121.41.38\ngtms\ao\lib.src\ao-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ao\fm\service\CardReaderSynchronizeTimeService.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */