/*    */ package com.hwacom.ngtms.hcce.web.controller;
/*    */ 
/*    */ import com.hwacom.ngtms.hcce.core.NodeManager;
/*    */ import com.hwacom.ngtms.hcce.shared.HcceEnv;
/*    */ import com.hwacom.ngtms.hcce.web.vo.HcNodeInfo;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.text.SimpleDateFormat;
/*    */ import java.util.Date;
/*    */ import java.util.jar.Attributes;
/*    */ import java.util.jar.Manifest;
/*    */ import javax.servlet.ServletContext;
/*    */ import org.slf4j.Logger;
/*    */ import org.slf4j.LoggerFactory;
/*    */ import org.springframework.beans.factory.annotation.Autowired;
/*    */ import org.springframework.stereotype.Controller;
/*    */ import org.springframework.ui.Model;
/*    */ import org.springframework.web.bind.annotation.RequestMapping;
/*    */ import org.springframework.web.bind.annotation.RequestMethod;
/*    */ import org.springframework.web.servlet.ModelAndView;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Controller
/*    */ @RequestMapping({"/hcConsole"})
/*    */ public class HcConsoleViewController
/*    */ {
/* 33 */   private static Logger logger = LoggerFactory.getLogger(HcConsoleViewController.class);
/* 34 */   private SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSS");
/*    */   
/*    */   @Autowired
/*    */   private HcceEnv hcceEnv;
/*    */   
/*    */   private HcNodeInfo hcNodeInfo;
/*    */ 
/*    */   
/*    */   @RequestMapping
/*    */   public String hcConsole(Model model) {
/* 44 */     model.addAttribute("hcceEnv", this.hcceEnv);
/* 45 */     return "hcConsole";
/*    */   }
/*    */   @Autowired
/*    */   private ServletContext servletContext; @Autowired
/*    */   private NodeManager nodeManager;
/*    */   
/*    */   @RequestMapping(value = {"/tabsContent"}, method = {RequestMethod.GET})
/*    */   public ModelAndView tabsContent() {
/* 53 */     ModelAndView model = new ModelAndView();
/* 54 */     model.addObject("hcNodeInfo", getHcNodeInfo());
/* 55 */     model.setViewName("tabsContent");
/* 56 */     return model;
/*    */   }
/*    */   
/*    */   private HcNodeInfo getHcNodeInfo() {
/* 60 */     if (this.hcNodeInfo != null) return this.hcNodeInfo; 
/* 61 */     HcNodeInfo hcNodeInfo = new HcNodeInfo();
/* 62 */     hcNodeInfo.setVersion("Unknown");
/* 63 */     hcNodeInfo.setIssueDate("Unknown");
/*    */     
/* 65 */     InputStream inputStream = this.servletContext.getResourceAsStream("/META-INF/MANIFEST.MF");
/*    */     try {
/* 67 */       Manifest manifest = new Manifest(inputStream);
/* 68 */       Attributes mainAttrs = manifest.getMainAttributes();
/* 69 */       String implementationBuild = mainAttrs.getValue("Implementation-Build");
/* 70 */       if (implementationBuild != null) {
/* 71 */         int index = implementationBuild.indexOf('_');
/* 72 */         if (index >= 0) {
/* 73 */           hcNodeInfo.setRevision(implementationBuild.substring(0, index));
/* 74 */           hcNodeInfo.setIssueDate(implementationBuild.substring(index + 1));
/*    */         } 
/*    */       } 
/*    */       
/* 78 */       String implementationTitle = mainAttrs.getValue("Implementation-Title");
/* 79 */       if (implementationTitle != null && implementationTitle.length() > 0)
/* 80 */       { hcNodeInfo.setTitle(implementationTitle); }
/* 81 */       else { hcNodeInfo.setTitle("Unkonwn"); }
/*    */       
/* 83 */       String implementationVersion = mainAttrs.getValue("Implementation-Version");
/* 84 */       if (implementationVersion != null && implementationVersion.length() > 0)
/* 85 */       { hcNodeInfo.setVersion(implementationVersion); }
/* 86 */       else { hcNodeInfo.setVersion("Unknown"); } 
/* 87 */     } catch (Exception exception) {
/*    */     
/*    */     } finally {
/* 90 */       if (inputStream != null) {
/*    */         try {
/* 92 */           inputStream.close();
/* 93 */         } catch (IOException iOException) {}
/*    */       }
/*    */     } 
/*    */     
/* 97 */     hcNodeInfo.setStartTime(new Date(this.nodeManager.getStartTime()));
/* 98 */     this.hcNodeInfo = hcNodeInfo;
/* 99 */     return hcNodeInfo;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\lib\hcConsole-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\hcce\web\controller\HcConsoleViewController.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */