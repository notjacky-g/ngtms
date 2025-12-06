/*    */ package com.hwacom.ngtms.hcce.web.vo;
/*    */ 
/*    */ 
/*    */ public class MemberInfo
/*    */ {
/*    */   private String uuid;
/*    */   
/*    */   private boolean coordinator;
/*    */   
/*    */   private boolean localMember;
/*    */   
/*    */   private String address;
/*    */   
/*    */   private String attributes;
/*    */   
/*    */   public String getUuid()
/*    */   {
/* 18 */     return this.uuid;
/*    */   }
/*    */   
/*    */   public void setUuid(String uuid) {
/* 22 */     this.uuid = uuid;
/*    */   }
/*    */   
/*    */   public boolean isCoordinator() {
/* 26 */     return this.coordinator;
/*    */   }
/*    */   
/*    */   public void setCoordinator(boolean coordinator) {
/* 30 */     this.coordinator = coordinator;
/*    */   }
/*    */   
/*    */   public boolean isLocalMember() {
/* 34 */     return this.localMember;
/*    */   }
/*    */   
/*    */   public void setLocalMember(boolean localMember) {
/* 38 */     this.localMember = localMember;
/*    */   }
/*    */   
/*    */   public String getAddress() {
/* 42 */     return this.address;
/*    */   }
/*    */   
/*    */   public void setAddress(String address) {
/* 46 */     this.address = address;
/*    */   }
/*    */   
/*    */   public String getAttributes() {
/* 50 */     return this.attributes;
/*    */   }
/*    */   
/*    */   public void setAttributes(String attributes) {
/* 54 */     this.attributes = attributes;
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\hcConsole-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\hcce\web\vo\MemberInfo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */