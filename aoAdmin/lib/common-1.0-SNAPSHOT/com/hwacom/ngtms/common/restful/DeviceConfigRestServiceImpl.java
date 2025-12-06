/*    */ package com.hwacom.ngtms.common.restful;
/*    */ 
/*    */ import com.hwacom.ngtms.common.fm.model.DeviceConfig;
/*    */ import com.hwacom.ngtms.common.fm.service.DeviceConfigService;
/*    */ import com.hwacom.ngtms.common.shared.dto.DeviceConfigDTO;
/*    */ import org.slf4j.Logger;
/*    */ import org.slf4j.LoggerFactory;
/*    */ import org.springframework.beans.factory.annotation.Autowired;
/*    */ import org.springframework.web.bind.annotation.CrossOrigin;
/*    */ import org.springframework.web.bind.annotation.RequestBody;
/*    */ import org.springframework.web.bind.annotation.RequestMapping;
/*    */ import org.springframework.web.bind.annotation.RequestMethod;
/*    */ import org.springframework.web.bind.annotation.RestController;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @CrossOrigin
/*    */ @RestController
/*    */ @RequestMapping({"/api/deviceConfig"})
/*    */ public class DeviceConfigRestServiceImpl
/*    */ {
/* 26 */   private static final Logger logger = LoggerFactory.getLogger(DeviceConfigRestServiceImpl.class);
/*    */   @Autowired
/*    */   private DeviceConfigService deviceConfigService;
/*    */   
/*    */   @RequestMapping(value = {"/saveDeviceConfig"}, method = {RequestMethod.POST})
/*    */   public void saveDeviceConfig(@RequestBody DeviceConfigDTO dto) {
/*    */     try {
/* 33 */       this.deviceConfigService.saveDeviceConfig(dto, DeviceConfig.class);
/* 34 */     } catch (RuntimeException ex) {
/* 35 */       logger.warn("Add device config failed!", ex);
/* 36 */       throw ex;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\lib\common-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\common\restful\DeviceConfigRestServiceImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */