/*    */ package com.hwacom.ngtms.ncc.tc.fw2.cmdparam.lcs;
/*    */ 
/*    */ import java.io.Externalizable;
/*    */ import java.io.IOException;
/*    */ import java.io.ObjectInput;
/*    */ import java.io.ObjectOutput;
/*    */ 
/*    */ @com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParams(cmdName="getLcsMonitorReq")
/*    */ public class GetLcsMonitorReqPm implements Externalizable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   public static final int cmdId = 196;
/*    */   public static final String cmdName = "getLcsMonitorReq";
/*    */   
/*    */   public void writeExternal(ObjectOutput out) throws IOException
/*    */   {}
/*    */   
/*    */   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*    */   {}
/*    */   
/*    */   public String toString()
/*    */   {
/* 23 */     return "[]";
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\fw2\cmdparam\lcs\GetLcsMonitorReqPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */