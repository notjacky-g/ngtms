/*    */ package com.hwacom.ngtms.ccs.restful;
/*    */ 
/*    */ import com.hwacom.ngtms.c.restful.BaseRestful;
/*    */ import com.hwacom.ngtms.c.shared.SubSystem;
/*    */ import com.hwacom.ngtms.hcce.config.model.DynamicConfig;
/*    */ import com.hwacom.ngtms.hcce.util.HcceUtils;
/*    */ import java.util.Collections;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import javax.annotation.PostConstruct;
/*    */ import org.slf4j.Logger;
/*    */ import org.slf4j.LoggerFactory;
/*    */ import org.springframework.web.bind.annotation.CrossOrigin;
/*    */ import org.springframework.web.bind.annotation.RequestMapping;
/*    */ import org.springframework.web.bind.annotation.RestController;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @CrossOrigin
/*    */ @RestController
/*    */ @RequestMapping({"/api/ccs/common"})
/*    */ public class CcsCommonRestServiceImpl
/*    */   extends BaseRestful
/*    */ {
/* 30 */   private static final Logger logger = LoggerFactory.getLogger(CcsCommonRestServiceImpl.class);
/*    */   
/*    */   @PostConstruct
/*    */   public void init() {
/* 34 */     setSubSystem(SubSystem.CCS);
/*    */   }
/*    */   
/*    */   @RequestMapping(value={"/url"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
/*    */   public Map<String, String> retrieveUrl() {
/*    */     try {
/* 40 */       logger.debug("Retrieve url.");
/* 41 */       Map<String, String> result = new HashMap();
/*    */       
/*    */ 
/*    */ 
/* 45 */       DynamicConfig vedioDynamicConfig = HcceUtils.getDynaConfig("CcsFm", "VideoUrl");
/* 46 */       result.put("VideoUrl", vedioDynamicConfig.getValue());
/*    */       
/*    */ 
/*    */ 
/* 50 */       DynamicConfig replayDynamicConfig = HcceUtils.getDynaConfig("CcsFm", "ReplayUrl");
/* 51 */       result.put("ReplayUrl", replayDynamicConfig.getValue());
/*    */       
/*    */ 
/*    */ 
/* 55 */       DynamicConfig screenDynamicConfig = HcceUtils.getDynaConfig("CcsFm", "ScreenUrl");
/* 56 */       result.put("ScreenUrl", screenDynamicConfig.getValue());
/*    */       
/*    */ 
/*    */ 
/* 60 */       DynamicConfig presetDynamicConfig = HcceUtils.getDynaConfig("CcsFm", "PresetUrl");
/* 61 */       result.put("PresetUrl", presetDynamicConfig.getValue());
/*    */       
/* 63 */       return result;
/*    */     } catch (RuntimeException ex) {
/* 65 */       logger.warn("Retrieve url failed.", ex); }
/* 66 */     return Collections.emptyMap();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\fm-ccs-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ccs\restful\CcsCommonRestServiceImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */