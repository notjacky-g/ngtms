/*    */ package com.hwacom.ngtms.ncc.tc.ct3.cmdparam.sig;
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
/*    */ @GlobalParams(paramsName="signalStatus")
/*    */ public class SignalStatusPm
/*    */   implements Externalizable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   @CommandParam(name="red")
/*    */   public int red;
/*    */   @CommandParam(name="yellow")
/*    */   public int yellow;
/*    */   @CommandParam(name="circleGreen")
/*    */   public int circleGreen;
/*    */   @CommandParam(name="leftGreen")
/*    */   public int leftGreen;
/*    */   @CommandParam(name="straightGreen")
/*    */   public int straightGreen;
/*    */   @CommandParam(name="rightGreen")
/*    */   public int rightGreen;
/*    */   @CommandParam(name="pedGreen")
/*    */   public int pedGreen;
/*    */   @CommandParam(name="pedRed")
/*    */   public int pedRed;
/*    */   
/*    */   public void writeExternal(ObjectOutput out)
/*    */     throws IOException
/*    */   {
/* 42 */     out.writeByte(this.red);
/* 43 */     out.writeByte(this.yellow);
/* 44 */     out.writeByte(this.circleGreen);
/* 45 */     out.writeByte(this.leftGreen);
/* 46 */     out.writeByte(this.straightGreen);
/* 47 */     out.writeByte(this.rightGreen);
/* 48 */     out.writeByte(this.pedGreen);
/* 49 */     out.writeByte(this.pedRed);
/*    */   }
/*    */   
/*    */   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*    */   {
/* 54 */     this.red = (in.readByte() & 0xFFFF);
/* 55 */     this.yellow = (in.readByte() & 0xFFFF);
/* 56 */     this.circleGreen = (in.readByte() & 0xFFFF);
/* 57 */     this.leftGreen = (in.readByte() & 0xFFFF);
/* 58 */     this.straightGreen = (in.readByte() & 0xFFFF);
/* 59 */     this.rightGreen = (in.readByte() & 0xFFFF);
/* 60 */     this.pedGreen = (in.readByte() & 0xFFFF);
/* 61 */     this.pedRed = (in.readByte() & 0xFFFF);
/*    */   }
/*    */   
/*    */   public String toString() {
/* 65 */     StringBuilder sb = new StringBuilder();
/* 66 */     sb.append('[');
/* 67 */     sb.append("red: ").append(this.red).append(", ");
/* 68 */     sb.append("yellow: ").append(this.yellow).append(", ");
/* 69 */     sb.append("circleGreen: ").append(this.circleGreen).append(", ");
/* 70 */     sb.append("leftGreen: ").append(this.leftGreen).append(", ");
/* 71 */     sb.append("straightGreen: ").append(this.straightGreen).append(", ");
/* 72 */     sb.append("rightGreen: ").append(this.rightGreen).append(", ");
/* 73 */     sb.append("pedGreen: ").append(this.pedGreen).append(", ");
/* 74 */     sb.append("pedRed: ").append(this.pedRed).append(']');
/* 75 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\ct3\cmdparam\sig\SignalStatusPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */