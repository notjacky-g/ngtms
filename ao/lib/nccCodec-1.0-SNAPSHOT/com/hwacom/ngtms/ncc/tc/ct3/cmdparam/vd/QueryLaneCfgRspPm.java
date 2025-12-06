/*    */ package com.hwacom.ngtms.ncc.tc.ct3.cmdparam.vd;
/*    */ 
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParam;
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParams;
/*    */ import java.io.Externalizable;
/*    */ import java.io.IOException;
/*    */ import java.io.ObjectInput;
/*    */ import java.io.ObjectOutput;
/*    */ 
/*    */ 
/*    */ @CommandParams(cmdName="queryLaneCfgRsp")
/*    */ public class QueryLaneCfgRspPm
/*    */   implements Externalizable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   public static final int cmdId = 28640;
/*    */   public static final String cmdName = "queryLaneCfgRsp";
/*    */   @CommandParam(name="laneCount")
/*    */   public int laneCount;
/*    */   @CommandParam(name="detectorMap")
/*    */   public int detectorMap;
/*    */   
/*    */   public void writeExternal(ObjectOutput out)
/*    */     throws IOException
/*    */   {
/* 26 */     out.writeByte(this.laneCount);
/* 27 */     out.writeByte(this.detectorMap);
/*    */   }
/*    */   
/*    */   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*    */   {
/* 32 */     this.laneCount = (in.readByte() & 0xFF);
/* 33 */     this.detectorMap = (in.readByte() & 0xFF);
/*    */   }
/*    */   
/*    */   public String toString() {
/* 37 */     StringBuilder sb = new StringBuilder();
/* 38 */     sb.append('[');
/* 39 */     sb.append("laneCount: ").append(this.laneCount).append(", ");
/* 40 */     sb.append("detectorMap: ").append(this.detectorMap).append(']');
/* 41 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\ct3\cmdparam\vd\QueryLaneCfgRspPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */