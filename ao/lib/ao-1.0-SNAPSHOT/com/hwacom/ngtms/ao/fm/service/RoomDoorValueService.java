/*    */ package com.hwacom.ngtms.ao.fm.service;
/*    */ 
/*    */ import com.hazelcast.core.IMap;
/*    */ import com.hwacom.ngtms.base.hazelcast.HzDistObjEnum;
/*    */ import com.hwacom.ngtms.base.hazelcast.HzUtils;
/*    */ import com.hwacom.ngtms.hcce.config.model.DynamicConfig;
/*    */ import com.hwacom.ngtms.hcce.config.model.DynamicConfigPk;
/*    */ import com.hwacom.ngtms.hcce.hz.HzMap;
/*    */ import com.hwacom.ngtms.hcce.shared.HcceEnv;
/*    */ import com.hwacom.ngtms.rtu.fm.hz.RtuHzMap;
/*    */ import org.slf4j.Logger;
/*    */ import org.slf4j.LoggerFactory;
/*    */ import org.springframework.beans.factory.annotation.Autowired;
/*    */ import org.springframework.stereotype.Service;
/*    */ 
/*    */ @Service
/*    */ public class RoomDoorValueService
/*    */ {
/* 19 */   private static final Logger logger = LoggerFactory.getLogger(RoomDoorValueService.class);
/*    */   
/* 21 */   private static final String[] DOOR_POSITION = new String[] { "NCU_2-DI-2", "NCU_22-DI-2", "NCU_22-DI-3", "NCU_22-DI-4", "NCU_22-DI-5", "NCU_22-DI-6" };
/*    */ 
/*    */   
/*    */   @Autowired
/*    */   private HcceEnv hcceEnv;
/*    */   
/*    */   @Autowired
/*    */   HunDureNcuServiceImpl hunDureNCUServiceImpl;
/*    */ 
/*    */   
/*    */   public void process() {
/* 32 */     IMap<String, Integer> dataMap = HzUtils.getMap((HzDistObjEnum)RtuHzMap.ModbusData);
/* 33 */     String[] deviceArr = DOOR_POSITION;
/* 34 */     DynamicConfig dyConfig = getDynamicConfig("AoCommonFm", "doorPosition");
/* 35 */     if (dyConfig != null && dyConfig.getValue() != null) {
/* 36 */       deviceArr = dyConfig.getValue().split(",");
/*    */     }
/* 38 */     for (String deviceOne : deviceArr) {
/*    */       try {
/* 40 */         String[] formatValue = deviceOne.split("-");
/* 41 */         String cardReaderId = getCardReaderID(formatValue[2]);
/* 42 */         Integer dataValue = Integer.valueOf(-1);
/* 43 */         if (cardReaderId != null) {
/* 44 */           boolean result = this.hunDureNCUServiceImpl.readDoor(formatValue[0], cardReaderId).booleanValue();
/* 45 */           dataValue = Integer.valueOf(result ? 0 : 1);
/*    */         } 
/* 47 */         dataMap.put(deviceOne, dataValue);
/* 48 */       } catch (Exception e) {
/* 49 */         logger.error("RoomDoorValueService error.", e);
/*    */       } 
/*    */     } 
/*    */   }
/*    */   
/*    */   private String getCardReaderID(String value) {
/* 55 */     String result = null;
/* 56 */     switch (value) {
/*    */       case "2":
/* 58 */         result = "36";
/*    */         break;
/*    */       case "3":
/* 61 */         result = "37";
/*    */         break;
/*    */       case "4":
/* 64 */         result = "38";
/*    */         break;
/*    */       case "5":
/* 67 */         result = "39";
/*    */         break;
/*    */       case "6":
/* 70 */         result = "40";
/*    */         break;
/*    */     } 
/*    */ 
/*    */     
/* 75 */     return result;
/*    */   }
/*    */   
/*    */   private DynamicConfig getDynamicConfig(String fmeName, String key) {
/* 79 */     if (fmeName == null || key == null) {
/* 80 */       logger.warn("Can not get DynamicConfig, fmeName or key is null.");
/*    */     }
/* 82 */     IMap<DynamicConfigPk, DynamicConfig> dynamicConfigMap = HzUtils.getMap((HzDistObjEnum)HzMap.DynamicConfig);
/*    */     
/* 84 */     DynamicConfig dynamicConfig = (DynamicConfig)dynamicConfigMap.get(new DynamicConfigPk(this.hcceEnv.getCurrentGroupName(), fmeName, key));
/* 85 */     return dynamicConfig;
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\機房門禁10.121.41.38\ngtms\ao\lib.src\ao-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ao\fm\service\RoomDoorValueService.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */