/*    */ package com.hwacom.ngtms.ncc.tc.ct3.cmdparam.etag;
/*    */ 
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParam;
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParams;
/*    */ import java.io.Externalizable;
/*    */ import java.io.IOException;
/*    */ import java.io.ObjectInput;
/*    */ import java.io.ObjectOutput;
/*    */ 
/*    */ @CommandParams(cmdName="queryTotalVehicleNoReportCycleRsp")
/*    */ public class QueryTotalVehicleNoReportCycleRspPm
/*    */   implements Externalizable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   public static final int cmdId = 58563;
/*    */   public static final String cmdName = "queryTotalVehicleNoReportCycleRsp";
/*    */   @CommandParam(name="vehicleNoCycle")
/*    */   public int vehicleNoCycle;
/*    */   
/*    */   public void writeExternal(ObjectOutput out)
/*    */     throws IOException
/*    */   {
/* 23 */     out.writeByte(this.vehicleNoCycle);
/*    */   }
/*    */   
/*    */   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*    */   {
/* 28 */     this.vehicleNoCycle = (in.readByte() & 0xFF);
/*    */   }
/*    */   
/*    */   public String toString() {
/* 32 */     StringBuilder sb = new StringBuilder();
/* 33 */     sb.append('[');
/* 34 */     sb.append("vehicleNoCycle: ").append(this.vehicleNoCycle).append(']');
/* 35 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\ct3\cmdparam\etag\QueryTotalVehicleNoReportCycleRspPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */