/*    */ package com.hwacom.ngtms.ncc.tc.fw2.cmdparam.rms.r21;
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
/*    */ @CommandParams(cmdName="rmsR21SignalChangeReport")
/*    */ public class RmsR21SignalChangeReportPm
/*    */   implements Externalizable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   public static final int cmdId = 134;
/*    */   public static final String cmdName = "rmsR21SignalChangeReport";
/*    */   @CommandParam(name="signal")
/*    */   public int signal;
/*    */   @CommandParam(name="signalSecond")
/*    */   public int signalSecond;
/*    */   
/*    */   public void writeExternal(ObjectOutput out)
/*    */     throws IOException
/*    */   {
/* 27 */     out.writeByte(this.signal);
/* 28 */     out.writeByte(this.signalSecond);
/*    */   }
/*    */   
/*    */   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*    */   {
/* 33 */     this.signal = (in.readByte() & 0xFF);
/* 34 */     this.signalSecond = (in.readByte() & 0xFF);
/*    */   }
/*    */   
/*    */   public String toString() {
/* 38 */     StringBuilder sb = new StringBuilder();
/* 39 */     sb.append('[');
/* 40 */     sb.append("signal: ").append(this.signal).append(", ");
/* 41 */     sb.append("signalSecond: ").append(this.signalSecond).append(']');
/* 42 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\fw2\cmdparam\rms\r21\RmsR21SignalChangeReportPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */