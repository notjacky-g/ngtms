/*    */ package com.hwacom.ngtms.ncc.tc.ct3.cmdparam.vd;
/*    */ 
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParam;
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.GlobalParams;
/*    */ import java.io.Externalizable;
/*    */ import java.io.IOException;
/*    */ import java.io.ObjectInput;
/*    */ import java.io.ObjectOutput;
/*    */ 
/*    */ 
/*    */ @GlobalParams(paramsName="laneOccupyDataItem")
/*    */ public class LaneOccupyDataItemPm
/*    */   implements Externalizable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   @CommandParam(name="laneId")
/*    */   public int laneId;
/*    */   @CommandParam(name="occupyTime")
/*    */   public int occupyTime;
/*    */   
/*    */   public void writeExternal(ObjectOutput out)
/*    */     throws IOException
/*    */   {
/* 24 */     out.writeByte(this.laneId);
/* 25 */     out.writeByte(this.occupyTime);
/*    */   }
/*    */   
/*    */   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*    */   {
/* 30 */     this.laneId = (in.readByte() & 0xFF);
/* 31 */     this.occupyTime = (in.readByte() & 0xFF);
/*    */   }
/*    */   
/*    */   public String toString() {
/* 35 */     StringBuilder sb = new StringBuilder();
/* 36 */     sb.append('[');
/* 37 */     sb.append("laneId: ").append(this.laneId).append(", ");
/* 38 */     sb.append("occupyTime: ").append(this.occupyTime).append(']');
/* 39 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\ct3\cmdparam\vd\LaneOccupyDataItemPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */