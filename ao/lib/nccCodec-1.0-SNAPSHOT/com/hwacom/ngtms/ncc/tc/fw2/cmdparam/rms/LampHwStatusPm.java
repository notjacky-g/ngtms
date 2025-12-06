/*    */ package com.hwacom.ngtms.ncc.tc.fw2.cmdparam.rms;
/*    */ 
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParam;
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.GlobalParams;
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
/*    */ 
/*    */ 
/*    */ @GlobalParams(paramsName="lampHwStatus")
/*    */ public class LampHwStatusPm
/*    */   implements Externalizable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   @CommandParam(name="greenLightErr")
/*    */   public int greenLightErr;
/*    */   @CommandParam(name="yellowLightErr")
/*    */   public int yellowLightErr;
/*    */   @CommandParam(name="redLightErr")
/*    */   public int redLightErr;
/*    */   @CommandParam(name="policeLightErr")
/*    */   public int policeLightErr;
/*    */   @CommandParam(name="verticalGreenLightErr")
/*    */   public int verticalGreenLightErr;
/*    */   @CommandParam(name="verticalYellowLightErr")
/*    */   public int verticalYellowLightErr;
/*    */   @CommandParam(name="verticalRedLightErr")
/*    */   public int verticalRedLightErr;
/*    */   @CommandParam(name="countDownLightErr")
/*    */   public int countDownLightErr;
/*    */   
/*    */   public void writeExternal(ObjectOutput out)
/*    */     throws IOException
/*    */   {
/* 42 */     out.writeByte(this.greenLightErr);
/* 43 */     out.writeByte(this.yellowLightErr);
/* 44 */     out.writeByte(this.redLightErr);
/* 45 */     out.writeByte(this.policeLightErr);
/* 46 */     out.writeByte(this.verticalGreenLightErr);
/* 47 */     out.writeByte(this.verticalYellowLightErr);
/* 48 */     out.writeByte(this.verticalRedLightErr);
/* 49 */     out.writeByte(this.countDownLightErr);
/*    */   }
/*    */   
/*    */   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*    */   {
/* 54 */     this.greenLightErr = (in.readByte() & 0xFFFF);
/* 55 */     this.yellowLightErr = (in.readByte() & 0xFFFF);
/* 56 */     this.redLightErr = (in.readByte() & 0xFFFF);
/* 57 */     this.policeLightErr = (in.readByte() & 0xFFFF);
/* 58 */     this.verticalGreenLightErr = (in.readByte() & 0xFFFF);
/* 59 */     this.verticalYellowLightErr = (in.readByte() & 0xFFFF);
/* 60 */     this.verticalRedLightErr = (in.readByte() & 0xFFFF);
/* 61 */     this.countDownLightErr = (in.readByte() & 0xFFFF);
/*    */   }
/*    */   
/*    */   public String toString() {
/* 65 */     StringBuilder sb = new StringBuilder();
/* 66 */     sb.append('[');
/* 67 */     sb.append("greenLightErr: ").append(this.greenLightErr).append(", ");
/* 68 */     sb.append("yellowLightErr: ").append(this.yellowLightErr).append(", ");
/* 69 */     sb.append("redLightErr: ").append(this.redLightErr).append(", ");
/* 70 */     sb.append("policeLightErr: ").append(this.policeLightErr).append(", ");
/* 71 */     sb.append("verticalGreenLightErr: ").append(this.verticalGreenLightErr).append(", ");
/* 72 */     sb.append("verticalYellowLightErr: ").append(this.verticalYellowLightErr).append(", ");
/* 73 */     sb.append("verticalRedLightErr: ").append(this.verticalRedLightErr).append(", ");
/* 74 */     sb.append("countDownLightErr: ").append(this.countDownLightErr).append(']');
/* 75 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\fw2\cmdparam\rms\LampHwStatusPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */