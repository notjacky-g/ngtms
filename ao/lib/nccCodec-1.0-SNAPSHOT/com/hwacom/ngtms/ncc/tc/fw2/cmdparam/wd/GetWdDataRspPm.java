/*    */ package com.hwacom.ngtms.ncc.tc.fw2.cmdparam.wd;
/*    */ 
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParam;
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParams;
/*    */ import com.hwacom.ngtms.ncc.tc.fw2.cmdparam.DhmPm;
/*    */ import com.hwacom.ngtms.ncc.tc.fw2.cmdparam.HwStatusPm;
/*    */ import java.io.Externalizable;
/*    */ import java.io.IOException;
/*    */ import java.io.ObjectInput;
/*    */ import java.io.ObjectOutput;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @CommandParams(cmdName="getWdDataRsp")
/*    */ public class GetWdDataRspPm
/*    */   implements Externalizable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   public static final int cmdId = 45;
/*    */   public static final String cmdName = "getWdDataRsp";
/*    */   @CommandParam(name="hwStatus")
/*    */   public HwStatusPm hwStatusPm;
/*    */   @CommandParam(name="dhm")
/*    */   public DhmPm dhmPm;
/*    */   @CommandParam(name="avgWindSpeed")
/*    */   public int avgWindSpeed;
/*    */   @CommandParam(name="avgWindDirection")
/*    */   public int avgWindDirection;
/*    */   @CommandParam(name="maxWindSpeed")
/*    */   public int maxWindSpeed;
/*    */   @CommandParam(name="maxWindDirection")
/*    */   public int maxWindDirection;
/*    */   @CommandParam(name="amDegree")
/*    */   public int amDegree;
/*    */   
/*    */   public void writeExternal(ObjectOutput out)
/*    */     throws IOException
/*    */   {
/* 41 */     out.writeObject(this.hwStatusPm);
/* 42 */     out.writeObject(this.dhmPm);
/* 43 */     out.writeByte(this.avgWindSpeed);
/* 44 */     out.writeByte(this.avgWindDirection);
/* 45 */     out.writeByte(this.maxWindSpeed);
/* 46 */     out.writeByte(this.maxWindDirection);
/* 47 */     out.writeByte(this.amDegree);
/*    */   }
/*    */   
/*    */   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*    */   {
/* 52 */     this.hwStatusPm = ((HwStatusPm)in.readObject());
/* 53 */     this.dhmPm = ((DhmPm)in.readObject());
/* 54 */     this.avgWindSpeed = (in.readByte() & 0xFF);
/* 55 */     this.avgWindDirection = (in.readByte() & 0xFF);
/* 56 */     this.maxWindSpeed = (in.readByte() & 0xFF);
/* 57 */     this.maxWindDirection = (in.readByte() & 0xFF);
/* 58 */     this.amDegree = (in.readByte() & 0xFF);
/*    */   }
/*    */   
/*    */   public String toString() {
/* 62 */     StringBuilder sb = new StringBuilder();
/* 63 */     sb.append('[');
/* 64 */     sb.append("hwStatusPm: ").append(this.hwStatusPm).append(", ");
/* 65 */     sb.append("dhmPm: ").append(this.dhmPm).append(", ");
/* 66 */     sb.append("avgWindSpeed: ").append(this.avgWindSpeed).append(", ");
/* 67 */     sb.append("avgWindDirection: ").append(this.avgWindDirection).append(", ");
/* 68 */     sb.append("maxWindSpeed: ").append(this.maxWindSpeed).append(", ");
/* 69 */     sb.append("maxWindDirection: ").append(this.maxWindDirection).append(", ");
/* 70 */     sb.append("amDegree: ").append(this.amDegree).append(']');
/* 71 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\fw2\cmdparam\wd\GetWdDataRspPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */