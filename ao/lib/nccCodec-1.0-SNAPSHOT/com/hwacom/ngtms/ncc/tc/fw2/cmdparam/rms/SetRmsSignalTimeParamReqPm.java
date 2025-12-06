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
/*    */ 
/*    */ 
/*    */ 
/*    */ @CommandParams(cmdName="setRmsSignalTimeParamReq")
/*    */ public class SetRmsSignalTimeParamReqPm
/*    */   implements Externalizable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   public static final int cmdId = 162;
/*    */   public static final String cmdName = "setRmsSignalTimeParamReq";
/*    */   @CommandParam(name="greenFlashTime")
/*    */   public int greenFlashTime;
/*    */   @CommandParam(name="yellowFlashTime")
/*    */   public int yellowFlashTime;
/*    */   @CommandParam(name="redFlashTime")
/*    */   public int redFlashTime;
/*    */   @CommandParam(name="greenMinTime")
/*    */   public int greenMinTime;
/*    */   @CommandParam(name="yellowMinTime")
/*    */   public int yellowMinTime;
/*    */   @CommandParam(name="redMinTime")
/*    */   public int redMinTime;
/*    */   
/*    */   public void writeExternal(ObjectOutput out)
/*    */     throws IOException
/*    */   {
/* 38 */     out.writeByte(this.greenFlashTime);
/* 39 */     out.writeByte(this.yellowFlashTime);
/* 40 */     out.writeByte(this.redFlashTime);
/* 41 */     out.writeByte(this.greenMinTime);
/* 42 */     out.writeByte(this.yellowMinTime);
/* 43 */     out.writeByte(this.redMinTime);
/*    */   }
/*    */   
/*    */   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*    */   {
/* 48 */     this.greenFlashTime = (in.readByte() & 0xFF);
/* 49 */     this.yellowFlashTime = (in.readByte() & 0xFF);
/* 50 */     this.redFlashTime = (in.readByte() & 0xFF);
/* 51 */     this.greenMinTime = (in.readByte() & 0xFF);
/* 52 */     this.yellowMinTime = (in.readByte() & 0xFF);
/* 53 */     this.redMinTime = (in.readByte() & 0xFF);
/*    */   }
/*    */   
/*    */   public String toString() {
/* 57 */     StringBuilder sb = new StringBuilder();
/* 58 */     sb.append('[');
/* 59 */     sb.append("greenFlashTime: ").append(this.greenFlashTime).append(", ");
/* 60 */     sb.append("yellowFlashTime: ").append(this.yellowFlashTime).append(", ");
/* 61 */     sb.append("redFlashTime: ").append(this.redFlashTime).append(", ");
/* 62 */     sb.append("greenMinTime: ").append(this.greenMinTime).append(", ");
/* 63 */     sb.append("yellowMinTime: ").append(this.yellowMinTime).append(", ");
/* 64 */     sb.append("redMinTime: ").append(this.redMinTime).append(']');
/* 65 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\fw2\cmdparam\rms\SetRmsSignalTimeParamReqPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */