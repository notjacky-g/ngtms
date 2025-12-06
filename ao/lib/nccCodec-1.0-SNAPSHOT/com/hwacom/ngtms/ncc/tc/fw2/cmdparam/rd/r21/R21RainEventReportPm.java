/*    */ package com.hwacom.ngtms.ncc.tc.fw2.cmdparam.rd.r21;
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
/*    */ @CommandParams(cmdName="r21RainEventReport")
/*    */ public class R21RainEventReportPm
/*    */   implements Externalizable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   public static final int cmdId = 74;
/*    */   public static final String cmdName = "r21RainEventReport";
/*    */   @CommandParam(name="responseType")
/*    */   public int responseType;
/*    */   @CommandParam(name="hwStatus")
/*    */   public HwStatusPm hwStatusPm;
/*    */   @CommandParam(name="dhm")
/*    */   public DhmPm dhmPm;
/*    */   @CommandParam(name="currentPluviometric")
/*    */   public int currentPluviometric;
/*    */   @CommandParam(name="rdDegree")
/*    */   public int rdDegree;
/*    */   
/*    */   public void writeExternal(ObjectOutput out)
/*    */     throws IOException
/*    */   {
/* 36 */     out.writeByte(this.responseType);
/* 37 */     out.writeObject(this.hwStatusPm);
/* 38 */     out.writeObject(this.dhmPm);
/* 39 */     out.writeShort(this.currentPluviometric);
/* 40 */     out.writeByte(this.rdDegree);
/*    */   }
/*    */   
/*    */   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*    */   {
/* 45 */     this.responseType = (in.readByte() & 0xFF);
/* 46 */     this.hwStatusPm = ((HwStatusPm)in.readObject());
/* 47 */     this.dhmPm = ((DhmPm)in.readObject());
/* 48 */     this.currentPluviometric = (in.readShort() & 0xFFFF);
/* 49 */     this.rdDegree = (in.readByte() & 0xFF);
/*    */   }
/*    */   
/*    */   public String toString() {
/* 53 */     StringBuilder sb = new StringBuilder();
/* 54 */     sb.append('[');
/* 55 */     sb.append("responseType: ").append(this.responseType).append(", ");
/* 56 */     sb.append("hwStatusPm: ").append(this.hwStatusPm).append(", ");
/* 57 */     sb.append("dhmPm: ").append(this.dhmPm).append(", ");
/* 58 */     sb.append("currentPluviometric: ").append(this.currentPluviometric).append(", ");
/* 59 */     sb.append("rdDegree: ").append(this.rdDegree).append(']');
/* 60 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\fw2\cmdparam\rd\r21\R21RainEventReportPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */