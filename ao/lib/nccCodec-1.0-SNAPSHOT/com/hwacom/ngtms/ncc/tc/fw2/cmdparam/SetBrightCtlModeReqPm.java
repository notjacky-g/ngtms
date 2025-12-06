/*    */ package com.hwacom.ngtms.ncc.tc.fw2.cmdparam;
/*    */ 
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParam;
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParams;
/*    */ import java.io.Externalizable;
/*    */ import java.io.IOException;
/*    */ import java.io.ObjectInput;
/*    */ import java.io.ObjectOutput;
/*    */ 
/*    */ @CommandParams(cmdName="setBrightCtlModeReq")
/*    */ public class SetBrightCtlModeReqPm
/*    */   implements Externalizable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   public static final int cmdId = 10;
/*    */   public static final String cmdName = "setBrightCtlModeReq";
/*    */   @CommandParam(name="brightCtrlMode")
/*    */   public int brightCtrlMode;
/*    */   
/*    */   public void writeExternal(ObjectOutput out) throws IOException
/*    */   {
/* 22 */     out.writeByte(this.brightCtrlMode);
/*    */   }
/*    */   
/*    */   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*    */   {
/* 27 */     this.brightCtrlMode = (in.readByte() & 0xFF);
/*    */   }
/*    */   
/*    */   public String toString() {
/* 31 */     StringBuilder sb = new StringBuilder();
/* 32 */     sb.append('[');
/* 33 */     sb.append("brightCtrlMode: ").append(this.brightCtrlMode).append(']');
/* 34 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\fw2\cmdparam\SetBrightCtlModeReqPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */