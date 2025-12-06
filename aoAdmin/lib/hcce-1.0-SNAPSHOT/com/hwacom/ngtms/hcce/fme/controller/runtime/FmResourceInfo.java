/*    */ package com.hwacom.ngtms.hcce.fme.controller.runtime;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ import java.util.Date;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class FmResourceInfo
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 1968283067415188927L;
/*    */   private Date time;
/*    */   private Map<String, Serializable> resourceMap;
/*    */   
/*    */   public String toString() {
/* 27 */     return "FmResources [time=" + this.time + ", resourceMap=" + this.resourceMap + "]";
/*    */   }
/*    */   
/*    */   public Map<String, Serializable> getResourceMap() {
/* 31 */     return this.resourceMap;
/*    */   }
/*    */   
/*    */   public void setResourceMap(Map<String, Serializable> resourceMap) {
/* 35 */     this.resourceMap = resourceMap;
/*    */   }
/*    */   
/*    */   public Date getTime() {
/* 39 */     return this.time;
/*    */   }
/*    */   
/*    */   public void setTime(Date time) {
/* 43 */     this.time = time;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\lib\hcce-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\hcce\fme\controller\runtime\FmResourceInfo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */