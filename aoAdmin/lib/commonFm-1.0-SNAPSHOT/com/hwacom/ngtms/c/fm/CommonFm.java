/*     */ package com.hwacom.ngtms.c.fm;
/*     */ 
/*     */ import com.hwacom.ngtms.c.fm.service.RptEnvVar;
/*     */ import com.hwacom.ngtms.common.fm.service.AccountService;
/*     */ import com.hwacom.ngtms.hcce.config.model.DynamicConfig;
/*     */ import com.hwacom.ngtms.hcce.core.exception.FmException;
/*     */ import com.hwacom.ngtms.hcce.fme.controller.fm.FmeMainBase;
/*     */ import com.hwacom.ngtms.hcce.shared.DynamicConfigDeclare;
/*     */ import org.apache.commons.beanutils.BeanUtils;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
/*     */ import org.springframework.context.ApplicationContext;
/*     */ import org.springframework.context.ApplicationContextAware;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CommonFm
/*     */   extends FmeMainBase
/*     */   implements ApplicationContextAware
/*     */ {
/*  27 */   private static Logger logger = LoggerFactory.getLogger(CommonFm.class);
/*     */   
/*     */   static {
/*  30 */     setDynamicConfigDeclares(new DynamicConfigDeclare[] { new DynamicConfigDeclare("RptAbsoluteResourcePath", "/temp"), new DynamicConfigDeclare("RptResourceBaseUri", "http://emm.cfreeway.nat.gov.tw/images/"), new DynamicConfigDeclare("RptServiceUsingRemotePolicy", 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/*  36 */             Boolean.toString(false)), new DynamicConfigDeclare("RptExportFileDirectory", "/shared/rpt/"), new DynamicConfigDeclare("RptBulkProcessServiceUri", "rmi://rpt.freeway.intra:5000/RptBulkProcessRemote") });
/*     */   }
/*     */ 
/*     */   
/*     */   @Autowired
/*     */   private ApplicationContext applicationContext;
/*     */   
/*     */   @Autowired
/*     */   private RptEnvVar rptEnvVar;
/*     */   
/*     */   @Autowired
/*     */   private AccountService accountService;
/*     */   private transient AutowireCapableBeanFactory beanFactory;
/*     */   
/*     */   public CommonFm(String name, String description) {
/*  51 */     super(name, description);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setApplicationContext(ApplicationContext context) {
/*  56 */     this.beanFactory = context.getAutowireCapableBeanFactory();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void init() throws FmException {
/*  66 */     addDyanConfigListener(new String[] { "RptServiceUsingRemotePolicy", "RptExportFileDirectory", "RptAbsoluteResourcePath", "RptResourceBaseUri", "RptBulkProcessServiceUri" });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/*  74 */       this.accountService.init();
/*  75 */     } catch (Exception e) {
/*  76 */       logger.error("AccountService initinalize failed", e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void runFm() {
/*     */     try {
/*  84 */       RptEnvVar var = new RptEnvVar();
/*  85 */       DynamicConfig v = getDynaConfig("RptServiceUsingRemotePolicy");
/*  86 */       var.setUsingRptServiceRemotePolicy((v == null) ? new Boolean(false) : new Boolean(v
/*  87 */             .getValue()));
/*  88 */       var.setExportFileDirectory(
/*  89 */           getDynaConfig("RptExportFileDirectory").getValue());
/*  90 */       var.setAbsoluteResourcePath(
/*  91 */           getDynaConfig("RptAbsoluteResourcePath").getValue());
/*  92 */       var.setResourceBaseUri(getDynaConfig("RptResourceBaseUri").getValue());
/*  93 */       var.setBulkProcessServiceUri(
/*  94 */           getDynaConfig("RptBulkProcessServiceUri").getValue());
/*  95 */       setRptEnvVar(var);
/*  96 */     } catch (Exception e) {
/*  97 */       logger.error("Failed to set rptEnvVar", e);
/*     */     } 
/*     */ 
/*     */     
/* 101 */     while (checkFmKeepRunning()) {
/*     */       try {
/* 103 */         Thread.sleep(5000L);
/* 104 */       } catch (InterruptedException ex) {}
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 115 */     logger.info("{} stopped", getFmeName());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void startTesting() throws FmException {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isAllowConcurrentExecution() {
/* 134 */     return Boolean.FALSE.booleanValue();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void onDynaConfigRemoved(String name, DynamicConfig dynamicConfig) {
/* 139 */     logger.info("Dynamic Config removed, name:'{}'", name);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void onDynaConfigUpdated(String name, DynamicConfig dynamicConfig) {
/* 144 */     logger.info("Dynamic Config update, name:'{}', value:'{}'", name, dynamicConfig.getValue());
/* 145 */     if ("RptServiceUsingRemotePolicy".equals(name)) {
/* 146 */       this.rptEnvVar.setUsingRptServiceRemotePolicy(new Boolean(dynamicConfig.getValue()));
/* 147 */     } else if ("RptExportFileDirectory".equals(name)) {
/* 148 */       this.rptEnvVar.setExportFileDirectory(dynamicConfig.getValue());
/* 149 */     } else if ("RptAbsoluteResourcePath".equals(name)) {
/* 150 */       this.rptEnvVar.setAbsoluteResourcePath(dynamicConfig.getValue());
/* 151 */     } else if ("RptResourceBaseUri".equals(name)) {
/* 152 */       this.rptEnvVar.setResourceBaseUri(dynamicConfig.getValue());
/* 153 */     } else if ("RptBulkProcessServiceUri".equals(name)) {
/* 154 */       this.rptEnvVar.setBulkProcessServiceUri(dynamicConfig.getValue());
/*     */     } 
/*     */     
/* 157 */     setRptEnvVar(this.rptEnvVar);
/*     */   }
/*     */   
/*     */   public void setRptEnvVar(RptEnvVar rptEnvVar) {
/* 161 */     if (!this.applicationContext.containsBean("rptEnvVar")) {
/* 162 */       this.beanFactory.autowireBean(rptEnvVar);
/*     */     } else {
/* 164 */       RptEnvVar obj = (RptEnvVar)this.applicationContext.getBean("rptEnvVar");
/*     */       try {
/* 166 */         BeanUtils.copyProperties(obj, rptEnvVar);
/* 167 */         logger.debug(obj.toString());
/* 168 */       } catch (Exception e) {
/* 169 */         logger.error("RptEnvVar copyFields error", e);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\lib\commonFm-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\c\fm\CommonFm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */