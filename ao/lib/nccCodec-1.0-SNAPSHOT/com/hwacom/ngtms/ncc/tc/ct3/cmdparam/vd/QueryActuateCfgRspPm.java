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
/*    */ 
/*    */ @CommandParams(cmdName="queryActuateCfgRsp")
/*    */ public class QueryActuateCfgRspPm
/*    */   implements Externalizable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   public static final int cmdId = 28614;
/*    */   public static final String cmdName = "queryActuateCfgRsp";
/*    */   @CommandParam(name="laneId")
/*    */   public int laneId;
/*    */   @CommandParam(name="actuateType")
/*    */   public ActuateTypePm actuateTypePm;
/*    */   @CommandParam(name="occupyTime")
/*    */   public int occupyTime;
/*    */   
/*    */   public void writeExternal(ObjectOutput out)
/*    */     throws IOException
/*    */   {
/* 29 */     out.writeByte(this.laneId);
/* 30 */     out.writeObject(this.actuateTypePm);
/* 31 */     out.writeByte(this.occupyTime);
/*    */   }
/*    */   
/*    */   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*    */   {
/* 36 */     this.laneId = (in.readByte() & 0xFF);
/* 37 */     this.actuateTypePm = ((ActuateTypePm)in.readObject());
/* 38 */     this.occupyTime = (in.readByte() & 0xFF);
/*    */   }
/*    */   
/*    */   public String toString() {
/* 42 */     StringBuilder sb = new StringBuilder();
/* 43 */     sb.append('[');
/* 44 */     sb.append("laneId: ").append(this.laneId).append(", ");
/* 45 */     sb.append("actuateTypePm: ").append(this.actuateTypePm).append(", ");
/* 46 */     sb.append("occupyTime: ").append(this.occupyTime).append(']');
/* 47 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\ct3\cmdparam\vd\QueryActuateCfgRspPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */