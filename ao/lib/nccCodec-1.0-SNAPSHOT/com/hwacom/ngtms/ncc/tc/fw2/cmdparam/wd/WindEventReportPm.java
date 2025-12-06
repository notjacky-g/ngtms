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
/*    */ 
/*    */ @CommandParams(cmdName="windEventReport")
/*    */ public class WindEventReportPm
/*    */   implements Externalizable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   public static final int cmdId = 42;
/*    */   public static final String cmdName = "windEventReport";
/*    */   @CommandParam(name="responseType")
/*    */   public int responseType;
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
/* 44 */     out.writeByte(this.responseType);
/* 45 */     out.writeObject(this.hwStatusPm);
/* 46 */     out.writeObject(this.dhmPm);
/* 47 */     out.writeByte(this.avgWindSpeed);
/* 48 */     out.writeByte(this.avgWindDirection);
/* 49 */     out.writeByte(this.maxWindSpeed);
/* 50 */     out.writeByte(this.maxWindDirection);
/* 51 */     out.writeByte(this.amDegree);
/*    */   }
/*    */   
/*    */   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*    */   {
/* 56 */     this.responseType = (in.readByte() & 0xFF);
/* 57 */     this.hwStatusPm = ((HwStatusPm)in.readObject());
/* 58 */     this.dhmPm = ((DhmPm)in.readObject());
/* 59 */     this.avgWindSpeed = (in.readByte() & 0xFF);
/* 60 */     this.avgWindDirection = (in.readByte() & 0xFF);
/* 61 */     this.maxWindSpeed = (in.readByte() & 0xFF);
/* 62 */     this.maxWindDirection = (in.readByte() & 0xFF);
/* 63 */     this.amDegree = (in.readByte() & 0xFF);
/*    */   }
/*    */   
/*    */   public String toString() {
/* 67 */     StringBuilder sb = new StringBuilder();
/* 68 */     sb.append('[');
/* 69 */     sb.append("responseType: ").append(this.responseType).append(", ");
/* 70 */     sb.append("hwStatusPm: ").append(this.hwStatusPm).append(", ");
/* 71 */     sb.append("dhmPm: ").append(this.dhmPm).append(", ");
/* 72 */     sb.append("avgWindSpeed: ").append(this.avgWindSpeed).append(", ");
/* 73 */     sb.append("avgWindDirection: ").append(this.avgWindDirection).append(", ");
/* 74 */     sb.append("maxWindSpeed: ").append(this.maxWindSpeed).append(", ");
/* 75 */     sb.append("maxWindDirection: ").append(this.maxWindDirection).append(", ");
/* 76 */     sb.append("amDegree: ").append(this.amDegree).append(']');
/* 77 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\fw2\cmdparam\wd\WindEventReportPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */