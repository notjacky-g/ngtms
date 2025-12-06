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
/*    */ @CommandParams(cmdName="setRmsSignalReq")
/*    */ public class SetRmsSignalReqPm
/*    */   implements Externalizable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   public static final int cmdId = 129;
/*    */   public static final String cmdName = "setRmsSignalReq";
/*    */   @CommandParam(name="signal")
/*    */   public int signal;
/*    */   @CommandParam(name="alarm")
/*    */   public int alarm;
/*    */   
/*    */   public void writeExternal(ObjectOutput out)
/*    */     throws IOException
/*    */   {
/* 26 */     out.writeByte(this.signal);
/* 27 */     out.writeByte(this.alarm);
/*    */   }
/*    */   
/*    */   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*    */   {
/* 32 */     this.signal = (in.readByte() & 0xFF);
/* 33 */     this.alarm = (in.readByte() & 0xFF);
/*    */   }
/*    */   
/*    */   public String toString() {
/* 37 */     StringBuilder sb = new StringBuilder();
/* 38 */     sb.append('[');
/* 39 */     sb.append("signal: ").append(this.signal).append(", ");
/* 40 */     sb.append("alarm: ").append(this.alarm).append(']');
/* 41 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\fw2\cmdparam\rms\SetRmsSignalReqPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */