/*    */ package com.hwacom.ngtms.ncc.tc.fw2.cmdparam.vd;
/*    */ 
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParam;
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParams;
/*    */ import com.hwacom.ngtms.ncc.tc.fw2.cmdparam.DhmPm;
/*    */ import com.hwacom.ngtms.ncc.tc.fw2.cmdparam.HwStatusPm;
/*    */ import java.io.Externalizable;
/*    */ import java.io.IOException;
/*    */ import java.io.ObjectInput;
/*    */ import java.io.ObjectOutput;
/*    */ import java.util.List;
/*    */ 
/*    */ @CommandParams(cmdName="periodVdDataReport")
/*    */ public class PeriodVdDataReportPm
/*    */   implements Externalizable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   public static final int cmdId = 16;
/*    */   public static final String cmdName = "periodVdDataReport";
/*    */   @CommandParam(name="responseType")
/*    */   public int responseType;
/*    */   @CommandParam(name="hwStatus")
/*    */   public HwStatusPm hwStatusPm;
/*    */   @CommandParam(name="dhm")
/*    */   public DhmPm dhmPm;
/*    */   @CommandParam(name="periodVdDataList")
/*    */   public List<PeriodVdDataListItem> periodVdDataList;
/*    */   
/*    */   public static class PeriodVdDataListItem
/*    */     implements Externalizable
/*    */   {
/*    */     private static final long serialVersionUID = 1L;
/*    */     @CommandParam(name="laneDataItem")
/*    */     public LaneDataItemPm laneDataItemPm;
/*    */     
/*    */     public void writeExternal(ObjectOutput out) throws IOException
/*    */     {
/* 38 */       out.writeObject(this.laneDataItemPm);
/*    */     }
/*    */     
/*    */     public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*    */     {
/* 43 */       this.laneDataItemPm = ((LaneDataItemPm)in.readObject());
/*    */     }
/*    */     
/*    */     public String toString() {
/* 47 */       StringBuilder sb = new StringBuilder();
/* 48 */       sb.append('[');
/* 49 */       sb.append("laneDataItemPm: ").append(this.laneDataItemPm).append(']');
/* 50 */       return sb.toString();
/*    */     }
/*    */   }
/*    */   
/*    */   public void writeExternal(ObjectOutput out) throws IOException
/*    */   {
/* 56 */     out.writeByte(this.responseType);
/* 57 */     out.writeObject(this.hwStatusPm);
/* 58 */     out.writeObject(this.dhmPm);
/* 59 */     out.writeObject(this.periodVdDataList);
/*    */   }
/*    */   
/*    */   public void readExternal(ObjectInput in)
/*    */     throws IOException, ClassNotFoundException
/*    */   {
/* 65 */     this.responseType = (in.readByte() & 0xFF);
/* 66 */     this.hwStatusPm = ((HwStatusPm)in.readObject());
/* 67 */     this.dhmPm = ((DhmPm)in.readObject());
/* 68 */     this.periodVdDataList = ((List)in.readObject());
/*    */   }
/*    */   
/*    */   public String toString() {
/* 72 */     StringBuilder sb = new StringBuilder();
/* 73 */     sb.append('[');
/* 74 */     sb.append("responseType: ").append(this.responseType).append(", ");
/* 75 */     sb.append("hwStatusPm: ").append(this.hwStatusPm).append(", ");
/* 76 */     sb.append("dhmPm: ").append(this.dhmPm).append(", ");
/* 77 */     sb.append("periodVdDataList: ").append(this.periodVdDataList).append(']');
/* 78 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\fw2\cmdparam\vd\PeriodVdDataReportPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */