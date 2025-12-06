/*    */ package com.hwacom.ngtms.ncc.tc.fw2.cmdparam.rms;
/*    */ 
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParam;
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParams;
/*    */ import java.io.Externalizable;
/*    */ import java.io.IOException;
/*    */ import java.io.ObjectInput;
/*    */ import java.io.ObjectOutput;
/*    */ 
/*    */ 
/*    */ 
/*    */ @CommandParams(cmdName="setRmsDeviceConfigReq")
/*    */ public class SetRmsDeviceConfigReqPm
/*    */   implements Externalizable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   public static final int cmdId = 36610;
/*    */   public static final String cmdName = "setRmsDeviceConfigReq";
/*    */   @CommandParam(name="signalNo")
/*    */   public int signalNo;
/*    */   @CommandParam(name="bos1No")
/*    */   public int bos1No;
/*    */   @CommandParam(name="bos2No")
/*    */   public int bos2No;
/*    */   
/*    */   public void writeExternal(ObjectOutput out)
/*    */     throws IOException
/*    */   {
/* 29 */     out.writeByte(this.signalNo);
/* 30 */     out.writeByte(this.bos1No);
/* 31 */     out.writeByte(this.bos2No);
/*    */   }
/*    */   
/*    */   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*    */   {
/* 36 */     this.signalNo = (in.readByte() & 0xFF);
/* 37 */     this.bos1No = (in.readByte() & 0xFF);
/* 38 */     this.bos2No = (in.readByte() & 0xFF);
/*    */   }
/*    */   
/*    */   public String toString() {
/* 42 */     StringBuilder sb = new StringBuilder();
/* 43 */     sb.append('[');
/* 44 */     sb.append("signalNo: ").append(this.signalNo).append(", ");
/* 45 */     sb.append("bos1No: ").append(this.bos1No).append(", ");
/* 46 */     sb.append("bos2No: ").append(this.bos2No).append(']');
/* 47 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\fw2\cmdparam\rms\SetRmsDeviceConfigReqPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */