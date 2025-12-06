/*    */ package com.hwacom.ngtms.common.fm.service;
/*    */ 
/*    */ import com.hwacom.ngtms.base.hazelcast.HzDistObjEnum;
/*    */ import com.hwacom.ngtms.base.hazelcast.HzUtils;
/*    */ import com.hwacom.ngtms.common.fm.hz.CommonHzMap;
/*    */ import com.hwacom.ngtms.common.fm.model.DeviceConfig;
/*    */ import com.hwacom.ngtms.common.util.IMapLocker;
/*    */ import org.modelmapper.ModelMapper;
/*    */ import org.slf4j.Logger;
/*    */ import org.slf4j.LoggerFactory;
/*    */ import org.springframework.beans.factory.annotation.Autowired;
/*    */ import org.springframework.stereotype.Service;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Service
/*    */ public class DeviceConfigService
/*    */ {
/* 23 */   private static final Logger logger = LoggerFactory.getLogger(DeviceConfigService.class);
/*    */   
/*    */   @Autowired
/*    */   private ModelMapper modelMapper;
/*    */   
/*    */   public <T extends DeviceConfig, D extends com.hwacom.ngtms.common.shared.dto.DeviceConfigDTO> void saveDeviceConfig(D dto, Class<T> t) {
/* 29 */     if (dto.getProject() == null) {
/* 30 */       dto.setProject("");
/*    */     }
/* 32 */     DeviceConfig deviceConfig = (DeviceConfig)this.modelMapper.map(dto, t);
/* 33 */     IMapLocker.addOrUpdateMapping(
/* 34 */         HzUtils.getMap((HzDistObjEnum)CommonHzMap.DeviceConfig), deviceConfig.getDeviceName(), deviceConfig);
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\lib\common-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\common\fm\service\DeviceConfigService.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */