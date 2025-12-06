/*    */ package com.hwacom.ngtms.ncc.tc.fw2.cmdparam.vd;
/*    */ 
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParam;
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParams;
/*    */ import com.hwacom.ngtms.ncc.tc.fw2.cmdparam.DhmsPm;
/*    */ import com.hwacom.ngtms.ncc.tc.fw2.cmdparam.HwStatusPm;
/*    */ import java.io.Externalizable;
/*    */ import java.io.IOException;
/*    */ import java.io.ObjectInput;
/*    */ import java.io.ObjectOutput;
/*    */ 
/*    */ 
/*    */ 
/*    */ @CommandParams(cmdName="occupyReport")
/*    */ public class OccupyReportPm
/*    */   implements Externalizable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   public static final int cmdId = 26;
/*    */   public static final String cmdName = "occupyReport";
/*    */   @CommandParam(name="responseType")
/*    */   public int responseType;
/*    */   @CommandParam(name="hwStatus")
/*    */   public HwStatusPm hwStatusPm;
/*    */   @CommandParam(name="dhms")
/*    */   public DhmsPm dhmsPm;
/*    */   @CommandParam(name="laneId")
/*    */   public int laneId;
/*    */   @CommandParam(name="totalOccupyTime")
/*    */   public int totalOccupyTime;
/*    */   
/*    */   public void writeExternal(ObjectOutput out)
/*    */     throws IOException
/*    */   {
/* 35 */     out.writeByte(this.responseType);
/* 36 */     out.writeObject(this.hwStatusPm);
/* 37 */     out.writeObject(this.dhmsPm);
/* 38 */     out.writeByte(this.laneId);
/* 39 */     out.writeShort(this.totalOccupyTime);
/*    */   }
/*    */   
/*    */   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*    */   {
/* 44 */     this.responseType = (in.readByte() & 0xFF);
/* 45 */     this.hwStatusPm = ((HwStatusPm)in.readObject());
/* 46 */     this.dhmsPm = ((DhmsPm)in.readObject());
/* 47 */     this.laneId = (in.readByte() & 0xFF);
/* 48 */     this.totalOccupyTime = (in.readShort() & 0xFFFF);
/*    */   }
/*    */   
/*    */   public String toString() {
/* 52 */     StringBuilder sb = new StringBuilder();
/* 53 */     sb.append('[');
/* 54 */     sb.append("responseType: ").append(this.responseType).append(", ");
/* 55 */     sb.append("hwStatusPm: ").append(this.hwStatusPm).append(", ");
/* 56 */     sb.append("dhmsPm: ").append(this.dhmsPm).append(", ");
/* 57 */     sb.append("laneId: ").append(this.laneId).append(", ");
/* 58 */     sb.append("totalOccupyTime: ").append(this.totalOccupyTime).append(']');
/* 59 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\fw2\cmdparam\vd\OccupyReportPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */