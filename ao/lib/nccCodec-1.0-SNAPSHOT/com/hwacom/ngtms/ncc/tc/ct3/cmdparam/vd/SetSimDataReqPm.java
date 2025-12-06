/*    */ package com.hwacom.ngtms.ncc.tc.ct3.cmdparam.vd;
/*    */ 
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParam;
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParams;
/*    */ import java.io.Externalizable;
/*    */ import java.io.IOException;
/*    */ import java.io.ObjectInput;
/*    */ import java.io.ObjectOutput;
/*    */ import java.util.List;
/*    */ 
/*    */ @CommandParams(cmdName="setSimDataReq")
/*    */ public class SetSimDataReqPm
/*    */   implements Externalizable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   public static final int cmdId = 28432;
/*    */   public static final String cmdName = "setSimDataReq";
/*    */   @CommandParam(name="dataCount")
/*    */   public int dataCount;
/*    */   @CommandParam(name="dataSeqNo")
/*    */   public int dataSeqNo;
/*    */   @CommandParam(name="laneDataList")
/*    */   public List<LaneDataListItem> laneDataList;
/*    */   
/*    */   public static class LaneDataListItem
/*    */     implements Externalizable
/*    */   {
/*    */     private static final long serialVersionUID = 1L;
/*    */     @CommandParam(name="laneDataItem")
/*    */     public LaneDataItemPm laneDataItemPm;
/*    */     
/*    */     public void writeExternal(ObjectOutput out)
/*    */       throws IOException
/*    */     {
/* 35 */       out.writeObject(this.laneDataItemPm);
/*    */     }
/*    */     
/*    */     public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*    */     {
/* 40 */       this.laneDataItemPm = ((LaneDataItemPm)in.readObject());
/*    */     }
/*    */     
/*    */     public String toString() {
/* 44 */       StringBuilder sb = new StringBuilder();
/* 45 */       sb.append('[');
/* 46 */       sb.append("laneDataItemPm: ").append(this.laneDataItemPm).append(']');
/* 47 */       return sb.toString();
/*    */     }
/*    */   }
/*    */   
/*    */   public void writeExternal(ObjectOutput out) throws IOException
/*    */   {
/* 53 */     out.writeShort(this.dataCount);
/* 54 */     out.writeShort(this.dataSeqNo);
/* 55 */     out.writeObject(this.laneDataList);
/*    */   }
/*    */   
/*    */   public void readExternal(ObjectInput in)
/*    */     throws IOException, ClassNotFoundException
/*    */   {
/* 61 */     this.dataCount = (in.readShort() & 0xFFFF);
/* 62 */     this.dataSeqNo = (in.readShort() & 0xFFFF);
/* 63 */     this.laneDataList = ((List)in.readObject());
/*    */   }
/*    */   
/*    */   public String toString() {
/* 67 */     StringBuilder sb = new StringBuilder();
/* 68 */     sb.append('[');
/* 69 */     sb.append("dataCount: ").append(this.dataCount).append(", ");
/* 70 */     sb.append("dataSeqNo: ").append(this.dataSeqNo).append(", ");
/* 71 */     sb.append("laneDataList: ").append(this.laneDataList).append(']');
/* 72 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\ct3\cmdparam\vd\SetSimDataReqPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */