/*    */ package com.hwacom.ngtms.ncc.tc.fw2.cmdparam.vd;
/*    */ 
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParam;
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParams;
/*    */ import com.hwacom.ngtms.ncc.tc.fw2.cmdparam.DayTimeNoSecPm;
/*    */ import java.io.Externalizable;
/*    */ import java.io.IOException;
/*    */ import java.io.ObjectInput;
/*    */ import java.io.ObjectOutput;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @CommandParams(cmdName="setSimDataActionReq")
/*    */ public class SetSimDataActionReqPm
/*    */   implements Externalizable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   public static final int cmdId = 25;
/*    */   public static final String cmdName = "setSimDataActionReq";
/*    */   @CommandParam(name="action")
/*    */   public int action;
/*    */   @CommandParam(name="dataType")
/*    */   public int dataType;
/*    */   @CommandParam(name="dataCount")
/*    */   public int dataCount;
/*    */   @CommandParam(name="laneCount")
/*    */   public int laneCount;
/*    */   @CommandParam(name="dayTimeNoSec")
/*    */   public DayTimeNoSecPm dayTimeNoSecPm;
/*    */   
/*    */   public void writeExternal(ObjectOutput out)
/*    */     throws IOException
/*    */   {
/* 35 */     out.writeByte(this.action);
/* 36 */     out.writeByte(this.dataType);
/* 37 */     out.writeShort(this.dataCount);
/* 38 */     out.writeByte(this.laneCount);
/* 39 */     out.writeObject(this.dayTimeNoSecPm);
/*    */   }
/*    */   
/*    */   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*    */   {
/* 44 */     this.action = (in.readByte() & 0xFF);
/* 45 */     this.dataType = (in.readByte() & 0xFF);
/* 46 */     this.dataCount = (in.readShort() & 0xFFFF);
/* 47 */     this.laneCount = (in.readByte() & 0xFF);
/* 48 */     this.dayTimeNoSecPm = ((DayTimeNoSecPm)in.readObject());
/*    */   }
/*    */   
/*    */   public String toString() {
/* 52 */     StringBuilder sb = new StringBuilder();
/* 53 */     sb.append('[');
/* 54 */     sb.append("action: ").append(this.action).append(", ");
/* 55 */     sb.append("dataType: ").append(this.dataType).append(", ");
/* 56 */     sb.append("dataCount: ").append(this.dataCount).append(", ");
/* 57 */     sb.append("laneCount: ").append(this.laneCount).append(", ");
/* 58 */     sb.append("dayTimeNoSecPm: ").append(this.dayTimeNoSecPm).append(']');
/* 59 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\fw2\cmdparam\vd\SetSimDataActionReqPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */