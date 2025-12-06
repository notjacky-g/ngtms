/*    */ package com.hwacom.ngtms.ncc.tc.fw2.cmdparam.wd;
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
/*    */ @CommandParams(cmdName="setSimDataActionReq")
/*    */ public class SetSimDataActionReqPm
/*    */   implements Externalizable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   public static final int cmdId = 46;
/*    */   public static final String cmdName = "setSimDataActionReq";
/*    */   @CommandParam(name="action")
/*    */   public int action;
/*    */   @CommandParam(name="dataCount")
/*    */   public int dataCount;
/*    */   @CommandParam(name="dayTimeNoSec")
/*    */   public DayTimeNoSecPm dayTimeNoSecPm;
/*    */   
/*    */   public void writeExternal(ObjectOutput out)
/*    */     throws IOException
/*    */   {
/* 29 */     out.writeByte(this.action);
/* 30 */     out.writeShort(this.dataCount);
/* 31 */     out.writeObject(this.dayTimeNoSecPm);
/*    */   }
/*    */   
/*    */   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*    */   {
/* 36 */     this.action = (in.readByte() & 0xFF);
/* 37 */     this.dataCount = (in.readShort() & 0xFFFF);
/* 38 */     this.dayTimeNoSecPm = ((DayTimeNoSecPm)in.readObject());
/*    */   }
/*    */   
/*    */   public String toString() {
/* 42 */     StringBuilder sb = new StringBuilder();
/* 43 */     sb.append('[');
/* 44 */     sb.append("action: ").append(this.action).append(", ");
/* 45 */     sb.append("dataCount: ").append(this.dataCount).append(", ");
/* 46 */     sb.append("dayTimeNoSecPm: ").append(this.dayTimeNoSecPm).append(']');
/* 47 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\fw2\cmdparam\wd\SetSimDataActionReqPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */