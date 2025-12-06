/*    */ package com.hwacom.ngtms.ncc.tc.fw2.cmdparam.tem;
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
/*    */ @CommandParams(cmdName="temPowerStatusReport")
/*    */ public class TemPowerStatusReportPm
/*    */   implements Externalizable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   public static final int cmdId = 19;
/*    */   public static final String cmdName = "temPowerStatusReport";
/*    */   @CommandParam(name="dayTime")
/*    */   public DayTimePm dayTimePm;
/*    */   @CommandParam(name="tunnel")
/*    */   public int tunnel;
/*    */   @CommandParam(name="place")
/*    */   public int place;
/*    */   @CommandParam(name="status")
/*    */   public int status;
/*    */   
/*    */   public void writeExternal(ObjectOutput out)
/*    */     throws IOException
/*    */   {
/* 31 */     out.writeObject(this.dayTimePm);
/* 32 */     out.writeByte(this.tunnel);
/* 33 */     out.writeByte(this.place);
/* 34 */     out.writeByte(this.status);
/*    */   }
/*    */   
/*    */   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*    */   {
/* 39 */     this.dayTimePm = ((DayTimePm)in.readObject());
/* 40 */     this.tunnel = (in.readByte() & 0xFF);
/* 41 */     this.place = (in.readByte() & 0xFF);
/* 42 */     this.status = (in.readByte() & 0xFF);
/*    */   }
/*    */   
/*    */   public String toString() {
/* 46 */     StringBuilder sb = new StringBuilder();
/* 47 */     sb.append('[');
/* 48 */     sb.append("dayTimePm: ").append(this.dayTimePm).append(", ");
/* 49 */     sb.append("tunnel: ").append(this.tunnel).append(", ");
/* 50 */     sb.append("place: ").append(this.place).append(", ");
/* 51 */     sb.append("status: ").append(this.status).append(']');
/* 52 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\fw2\cmdparam\tem\TemPowerStatusReportPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */