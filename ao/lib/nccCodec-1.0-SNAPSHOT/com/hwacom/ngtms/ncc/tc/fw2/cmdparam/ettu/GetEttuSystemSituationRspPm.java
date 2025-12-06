/*    */ package com.hwacom.ngtms.ncc.tc.fw2.cmdparam.ettu;
/*    */ 
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.BytesUtility;
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
/*    */ 
/*    */ @CommandParams(cmdName="getEttuSystemSituationRsp")
/*    */ public class GetEttuSystemSituationRspPm
/*    */   implements Externalizable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   public static final int cmdId = 10528;
/*    */   public static final String cmdName = "getEttuSystemSituationRsp";
/*    */   @CommandParam(name="taskNumberString")
/*    */   public byte[] taskNumberString;
/*    */   @CommandParam(name="taskType")
/*    */   public int taskType;
/*    */   @CommandParam(name="date")
/*    */   public DatePm datePm;
/*    */   @CommandParam(name="time")
/*    */   public TimePm timePm;
/*    */   @CommandParam(name="segment")
/*    */   public SegmentPm segmentPm;
/*    */   @CommandParam(name="area")
/*    */   public AreaPm areaPm;
/*    */   @CommandParam(name="percentString")
/*    */   public byte[] percentString;
/*    */   @CommandParam(name="lrc")
/*    */   public int lrc;
/*    */   @CommandParam(name="endByte")
/*    */   public int endByte;
/*    */   
/*    */   public void writeExternal(ObjectOutput out)
/*    */     throws IOException
/*    */   {
/* 46 */     out.writeObject(this.taskNumberString);
/* 47 */     out.writeByte(this.taskType);
/* 48 */     out.writeObject(this.datePm);
/* 49 */     out.writeObject(this.timePm);
/* 50 */     out.writeObject(this.segmentPm);
/* 51 */     out.writeObject(this.areaPm);
/* 52 */     out.writeObject(this.percentString);
/* 53 */     out.writeShort(this.lrc);
/* 54 */     out.writeByte(this.endByte);
/*    */   }
/*    */   
/*    */   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*    */   {
/* 59 */     this.taskNumberString = ((byte[])in.readObject());
/* 60 */     this.taskType = (in.readByte() & 0xFF);
/* 61 */     this.datePm = ((DatePm)in.readObject());
/* 62 */     this.timePm = ((TimePm)in.readObject());
/* 63 */     this.segmentPm = ((SegmentPm)in.readObject());
/* 64 */     this.areaPm = ((AreaPm)in.readObject());
/* 65 */     this.percentString = ((byte[])in.readObject());
/* 66 */     this.lrc = (in.readShort() & 0xFFFF);
/* 67 */     this.endByte = (in.readByte() & 0xFF);
/*    */   }
/*    */   
/*    */   public String toString() {
/* 71 */     StringBuilder sb = new StringBuilder();
/* 72 */     sb.append('[');
/* 73 */     sb.append("taskNumberString: ")
/* 74 */       .append(BytesUtility.toHexString(this.taskNumberString))
/* 75 */       .append(", ");
/* 76 */     sb.append("taskType: ").append(this.taskType).append(", ");
/* 77 */     sb.append("datePm: ").append(this.datePm).append(", ");
/* 78 */     sb.append("timePm: ").append(this.timePm).append(", ");
/* 79 */     sb.append("segmentPm: ").append(this.segmentPm).append(", ");
/* 80 */     sb.append("areaPm: ").append(this.areaPm).append(", ");
/* 81 */     sb.append("percentString: ")
/* 82 */       .append(BytesUtility.toHexString(this.percentString))
/* 83 */       .append(", ");
/* 84 */     sb.append("lrc: ").append(this.lrc).append(", ");
/* 85 */     sb.append("endByte: ").append(this.endByte).append(']');
/* 86 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\fw2\cmdparam\ettu\GetEttuSystemSituationRspPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */