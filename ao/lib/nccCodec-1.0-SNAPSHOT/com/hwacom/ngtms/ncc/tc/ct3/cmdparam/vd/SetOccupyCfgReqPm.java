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
/*    */ @CommandParams(cmdName="setOccupyCfgReq")
/*    */ public class SetOccupyCfgReqPm
/*    */   implements Externalizable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   public static final int cmdId = 28439;
/*    */   public static final String cmdName = "setOccupyCfgReq";
/*    */   @CommandParam(name="laneOccupyDataList")
/*    */   public List<LaneOccupyDataListItem> laneOccupyDataList;
/*    */   
/*    */   public static class LaneOccupyDataListItem implements Externalizable
/*    */   {
/*    */     private static final long serialVersionUID = 1L;
/*    */     @CommandParam(name="laneOccupyDataItem")
/*    */     public LaneOccupyDataItemPm laneOccupyDataItemPm;
/*    */     
/*    */     public void writeExternal(ObjectOutput out) throws IOException
/*    */     {
/* 29 */       out.writeObject(this.laneOccupyDataItemPm);
/*    */     }
/*    */     
/*    */     public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*    */     {
/* 34 */       this.laneOccupyDataItemPm = ((LaneOccupyDataItemPm)in.readObject());
/*    */     }
/*    */     
/*    */     public String toString() {
/* 38 */       StringBuilder sb = new StringBuilder();
/* 39 */       sb.append('[');
/* 40 */       sb.append("laneOccupyDataItemPm: ").append(this.laneOccupyDataItemPm).append(']');
/* 41 */       return sb.toString();
/*    */     }
/*    */   }
/*    */   
/*    */   public void writeExternal(ObjectOutput out) throws IOException
/*    */   {
/* 47 */     out.writeObject(this.laneOccupyDataList);
/*    */   }
/*    */   
/*    */   public void readExternal(ObjectInput in)
/*    */     throws IOException, ClassNotFoundException
/*    */   {
/* 53 */     this.laneOccupyDataList = ((List)in.readObject());
/*    */   }
/*    */   
/*    */   public String toString() {
/* 57 */     StringBuilder sb = new StringBuilder();
/* 58 */     sb.append('[');
/* 59 */     sb.append("laneOccupyDataList: ").append(this.laneOccupyDataList).append(']');
/* 60 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\ct3\cmdparam\vd\SetOccupyCfgReqPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */