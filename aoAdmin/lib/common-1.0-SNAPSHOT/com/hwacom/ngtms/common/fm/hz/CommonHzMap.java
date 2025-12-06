/*    */ package com.hwacom.ngtms.common.fm.hz;
/*    */ 
/*    */ import com.hwacom.ngtms.hcce.hz.HzMapEnum;
/*    */ import com.hwacom.ngtms.hcce.hz.HzMapIndex;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public enum CommonHzMap
/*    */   implements HzMapEnum
/*    */ {
/* 15 */   AccountUser("系統使用者", "userMapStore"),
/* 16 */   AccountUserForgotPwdToken("密碼重設(忘記密碼)的一次性符記", "userForgotPwdTokenMapStore"),
/* 17 */   AccountUserLockTime("使用者多次登入失敗後的鎖定時間"),
/* 18 */   AccountUserLoginFailureTimes("使用者登入失敗的次數"),
/* 19 */   AccountRole("系統角色", "roleMapStore"),
/* 20 */   AccountFunctionPermission("系統權限", "functionPermissionMapStore"),
/* 21 */   AccountUnit("系統單位", "unitMapStore"),
/*    */ 
/*    */   
/* 24 */   DeviceConfig("設備組態", "deviceConfigMapStore", 
/*    */ 
/*    */     
/* 27 */     HzMapIndex.parsingDeclare("deviceName, deviceType, enable")),
/*    */   
/* 29 */   UrlPatternRole("Url Pattern Role", "urlPatternRoleMapStore");
/*    */   
/*    */   private String mapStoreBeanName;
/*    */   private String description;
/*    */   private HzMapIndex[] hzMapIndexes;
/*    */   
/*    */   CommonHzMap(String description) {
/* 36 */     this.description = description;
/*    */   }
/*    */   
/*    */   CommonHzMap(String description, String mapStoreBeanName) {
/* 40 */     this.description = description;
/* 41 */     this.mapStoreBeanName = mapStoreBeanName;
/*    */   }
/*    */   
/*    */   CommonHzMap(String description, HzMapIndex[] hzMapIndexes) {
/* 45 */     this.description = description;
/* 46 */     this.hzMapIndexes = hzMapIndexes;
/*    */   }
/*    */   
/*    */   CommonHzMap(String description, String mapStoreBeanName, HzMapIndex[] hzMapIndexes) {
/* 50 */     this.description = description;
/* 51 */     this.mapStoreBeanName = mapStoreBeanName;
/* 52 */     this.hzMapIndexes = hzMapIndexes;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getDataStoreBeanName() {
/* 57 */     return this.mapStoreBeanName;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String toHzName() {
/* 63 */     return toString();
/*    */   }
/*    */ 
/*    */   
/*    */   public String getDescription() {
/* 68 */     return this.description;
/*    */   }
/*    */ 
/*    */   
/*    */   public HzMapIndex[] getHzMapIndexes() {
/* 73 */     return this.hzMapIndexes;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\lib\common-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\common\fm\hz\CommonHzMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */