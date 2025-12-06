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
/*    */ @CommandParams(cmdName="getVdDataRsp")
/*    */ public class GetVdDataRspPm
/*    */   implements Externalizable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   public static final int cmdId = 21;
/*    */   public static final String cmdName = "getVdDataRsp";
/*    */   @CommandParam(name="hwStatus")
/*    */   public HwStatusPm hwStatusPm;
/*    */   @CommandParam(name="dhm")
/*    */   public DhmPm dhmPm;
/*    */   @CommandParam(name="vdDataList")
/*    */   public List<VdDataListItem> vdDataList;
/*    */   
/*    */   public static class VdDataListItem implements Externalizable
/*    */   {
/*    */     private static final long serialVersionUID = 1L;
/*    */     @CommandParam(name="laneDataItem")
/*    */     public LaneDataItemPm laneDataItemPm;
/*    */     
/*    */     public void writeExternal(ObjectOutput out) throws IOException
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
/* 53 */     out.writeObject(this.hwStatusPm);
/* 54 */     out.writeObject(this.dhmPm);
/* 55 */     out.writeObject(this.vdDataList);
/*    */   }
/*    */   
/*    */   public void readExternal(ObjectInput in)
/*    */     throws IOException, ClassNotFoundException
/*    */   {
/* 61 */     this.hwStatusPm = ((HwStatusPm)in.readObject());
/* 62 */     this.dhmPm = ((DhmPm)in.readObject());
/* 63 */     this.vdDataList = ((List)in.readObject());
/*    */   }
/*    */   
/*    */   public String toString() {
/* 67 */     StringBuilder sb = new StringBuilder();
/* 68 */     sb.append('[');
/* 69 */     sb.append("hwStatusPm: ").append(this.hwStatusPm).append(", ");
/* 70 */     sb.append("dhmPm: ").append(this.dhmPm).append(", ");
/* 71 */     sb.append("vdDataList: ").append(this.vdDataList).append(']');
/* 72 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\fw2\cmdparam\vd\GetVdDataRspPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */