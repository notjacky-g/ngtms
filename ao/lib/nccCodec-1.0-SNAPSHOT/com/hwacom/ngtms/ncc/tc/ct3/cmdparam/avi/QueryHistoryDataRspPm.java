/*    */ package com.hwacom.ngtms.ncc.tc.ct3.cmdparam.avi;
/*    */ 
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParam;
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParams;
/*    */ import java.io.Externalizable;
/*    */ import java.io.IOException;
/*    */ import java.io.ObjectInput;
/*    */ import java.io.ObjectOutput;
/*    */ import java.util.List;
/*    */ 
/*    */ @CommandParams(cmdName="queryHistoryDataRsp")
/*    */ public class QueryHistoryDataRspPm
/*    */   implements Externalizable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   public static final int cmdId = 58561;
/*    */   public static final String cmdName = "queryHistoryDataRsp";
/*    */   @CommandParam(name="vehicleNo")
/*    */   public int vehicleNo;
/*    */   @CommandParam(name="vehicleLen")
/*    */   public int vehicleLen;
/*    */   @CommandParam(name="vehicleList")
/*    */   public List<VehicleListItem> vehicleList;
/*    */   
/*    */   public static class VehicleListItem
/*    */     implements Externalizable
/*    */   {
/*    */     private static final long serialVersionUID = 1L;
/*    */     @CommandParam(name="vehicle")
/*    */     public VehiclePm vehiclePm;
/*    */     
/*    */     public void writeExternal(ObjectOutput out)
/*    */       throws IOException
/*    */     {
/* 35 */       out.writeObject(this.vehiclePm);
/*    */     }
/*    */     
/*    */     public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*    */     {
/* 40 */       this.vehiclePm = ((VehiclePm)in.readObject());
/*    */     }
/*    */     
/*    */     public String toString() {
/* 44 */       StringBuilder sb = new StringBuilder();
/* 45 */       sb.append('[');
/* 46 */       sb.append("vehiclePm: ").append(this.vehiclePm).append(']');
/* 47 */       return sb.toString();
/*    */     }
/*    */   }
/*    */   
/*    */   public void writeExternal(ObjectOutput out) throws IOException
/*    */   {
/* 53 */     out.writeObject(this.vehicleList);
/*    */   }
/*    */   
/*    */   public void readExternal(ObjectInput in)
/*    */     throws IOException, ClassNotFoundException
/*    */   {
/* 59 */     this.vehicleList = ((List)in.readObject());
/*    */   }
/*    */   
/*    */   public String toString() {
/* 63 */     StringBuilder sb = new StringBuilder();
/* 64 */     sb.append('[');
/* 65 */     sb.append("vehicleList: ").append(this.vehicleList).append(']');
/* 66 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\ct3\cmdparam\avi\QueryHistoryDataRspPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */