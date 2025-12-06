/*    */ package com.hwacom.ngtms.ncc.tc.ct3.cmdparam.cms;
/*    */ 
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParam;
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParams;
/*    */ import java.io.Externalizable;
/*    */ import java.io.IOException;
/*    */ import java.io.ObjectInput;
/*    */ import java.io.ObjectOutput;
/*    */ 
/*    */ @CommandParams(cmdName="queryOffLineDisplayModeRsp")
/*    */ public class QueryOffLineDisplayModeRspPm
/*    */   implements Externalizable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   public static final int cmdId = 45025;
/*    */   public static final String cmdName = "queryOffLineDisplayModeRsp";
/*    */   @CommandParam(name="offLineDisplayMode")
/*    */   public int offLineDisplayMode;
/*    */   
/*    */   public void writeExternal(ObjectOutput out)
/*    */     throws IOException
/*    */   {
/* 23 */     out.writeByte(this.offLineDisplayMode);
/*    */   }
/*    */   
/*    */   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*    */   {
/* 28 */     this.offLineDisplayMode = (in.readByte() & 0xFF);
/*    */   }
/*    */   
/*    */   public String toString() {
/* 32 */     StringBuilder sb = new StringBuilder();
/* 33 */     sb.append('[');
/* 34 */     sb.append("offLineDisplayMode: ").append(this.offLineDisplayMode).append(']');
/* 35 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\ct3\cmdparam\cms\QueryOffLineDisplayModeRspPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */