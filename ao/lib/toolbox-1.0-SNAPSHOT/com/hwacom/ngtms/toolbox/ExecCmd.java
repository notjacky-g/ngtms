/*    */ package com.hwacom.ngtms.toolbox;
/*    */ 
/*    */ import java.io.IOException;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ExecCmd
/*    */ {
/*    */   public static void enableInterface(String interfaceName)
/*    */     throws IOException
/*    */   {
/* 14 */     if (OSValidator.isWindows()) {
/* 15 */       Runtime.getRuntime().exec("netsh interface set interface " + interfaceName + " ENABLE");
/* 16 */     } else if (OSValidator.isUnix()) {
/* 17 */       Runtime.getRuntime().exec("ifconfig " + interfaceName + " up");
/*    */     }
/*    */   }
/*    */   
/*    */   public static void disableInterface(String interfaceName) throws IOException {
/* 22 */     if (OSValidator.isWindows()) {
/* 23 */       Runtime.getRuntime().exec("netsh interface set interface " + interfaceName + " DISABLED");
/* 24 */     } else if (OSValidator.isUnix()) {
/* 25 */       Runtime.getRuntime().exec("ifconfig " + interfaceName + " down");
/*    */     }
/*    */   }
/*    */   
/*    */   public static void main(String[] args) throws IOException {
/* 30 */     disableInterface("區域連線");
/* 31 */     enableInterface("區域連線");
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\toolbox-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\toolbox\ExecCmd.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */