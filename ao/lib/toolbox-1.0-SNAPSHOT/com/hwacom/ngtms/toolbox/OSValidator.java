/*    */ package com.hwacom.ngtms.toolbox;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class OSValidator
/*    */ {
/*    */   public static boolean isWindows()
/*    */   {
/* 11 */     String os = System.getProperty("os.name").toLowerCase();
/* 12 */     return os.indexOf("win") >= 0;
/*    */   }
/*    */   
/*    */   public static boolean isMac() {
/* 16 */     String os = System.getProperty("os.name").toLowerCase();
/* 17 */     return os.indexOf("mac") >= 0;
/*    */   }
/*    */   
/*    */   public static boolean isUnix() {
/* 21 */     String os = System.getProperty("os.name").toLowerCase();
/* 22 */     return (os.indexOf("nix") >= 0) || (os.indexOf("nux") >= 0);
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\toolbox-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\toolbox\OSValidator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */