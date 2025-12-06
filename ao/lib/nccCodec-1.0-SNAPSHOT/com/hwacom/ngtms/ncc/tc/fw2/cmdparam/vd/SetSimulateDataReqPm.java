/*    */ package com.hwacom.ngtms.ncc.tc.fw2.cmdparam.vd;
/*    */ 
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParam;
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParams;
/*    */ import java.io.Externalizable;
/*    */ import java.io.IOException;
/*    */ import java.io.ObjectInput;
/*    */ import java.io.ObjectOutput;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ @CommandParams(cmdName="setSimulateDataReq")
/*    */ public class SetSimulateDataReqPm
/*    */   implements Externalizable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   public static final int cmdId = 19;
/*    */   public static final String cmdName = "setSimulateDataReq";
/*    */   @CommandParam(name="dataType")
/*    */   public int dataType;
/*    */   @CommandParam(name="dataCount")
/*    */   public int dataCount;
/*    */   @CommandParam(name="dataSeqNo")
/*    */   public int dataSeqNo;
/*    */   @CommandParam(name="simulateDataList")
/*    */   public List<SimulateDataListItem> simulateDataList;
/*    */   
/*    */   public static class SimulateDataListItem
/*    */     implements Externalizable
/*    */   {
/*    */     private static final long serialVersionUID = 1L;
/*    */     @CommandParam(name="laneDataItem")
/*    */     public LaneDataItemPm laneDataItemPm;
/*    */     
/*    */     public void writeExternal(ObjectOutput out)
/*    */       throws IOException
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
/* 56 */     out.writeByte(this.dataType);
/* 57 */     out.writeShort(this.dataCount);
/* 58 */     out.writeShort(this.dataSeqNo);
/* 59 */     out.writeObject(this.simulateDataList);
/*    */   }
/*    */   
/*    */   public void readExternal(ObjectInput in)
/*    */     throws IOException, ClassNotFoundException
/*    */   {
/* 65 */     this.dataType = (in.readByte() & 0xFF);
/* 66 */     this.dataCount = (in.readShort() & 0xFFFF);
/* 67 */     this.dataSeqNo = (in.readShort() & 0xFFFF);
/* 68 */     this.simulateDataList = ((List)in.readObject());
/*    */   }
/*    */   
/*    */   public String toString() {
/* 72 */     StringBuilder sb = new StringBuilder();
/* 73 */     sb.append('[');
/* 74 */     sb.append("dataType: ").append(this.dataType).append(", ");
/* 75 */     sb.append("dataCount: ").append(this.dataCount).append(", ");
/* 76 */     sb.append("dataSeqNo: ").append(this.dataSeqNo).append(", ");
/* 77 */     sb.append("simulateDataList: ").append(this.simulateDataList).append(']');
/* 78 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\fw2\cmdparam\vd\SetSimulateDataReqPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */