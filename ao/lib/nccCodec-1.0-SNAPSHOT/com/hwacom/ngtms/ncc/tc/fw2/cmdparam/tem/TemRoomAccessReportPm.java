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
/*    */ 
/*    */ @CommandParams(cmdName="temRoomAccessReport")
/*    */ public class TemRoomAccessReportPm
/*    */   implements Externalizable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   public static final int cmdId = 20;
/*    */   public static final String cmdName = "temRoomAccessReport";
/*    */   @CommandParam(name="dayTime")
/*    */   public DayTimePm dayTimePm;
/*    */   @CommandParam(name="tunnel")
/*    */   public int tunnel;
/*    */   @CommandParam(name="place")
/*    */   public int place;
/*    */   @CommandParam(name="cardReader")
/*    */   public int cardReader;
/*    */   @CommandParam(name="status")
/*    */   public int status;
/*    */   
/*    */   public void writeExternal(ObjectOutput out)
/*    */     throws IOException
/*    */   {
/* 34 */     out.writeObject(this.dayTimePm);
/* 35 */     out.writeByte(this.tunnel);
/* 36 */     out.writeByte(this.place);
/* 37 */     out.writeByte(this.cardReader);
/* 38 */     out.writeByte(this.status);
/*    */   }
/*    */   
/*    */   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*    */   {
/* 43 */     this.dayTimePm = ((DayTimePm)in.readObject());
/* 44 */     this.tunnel = (in.readByte() & 0xFF);
/* 45 */     this.place = (in.readByte() & 0xFF);
/* 46 */     this.cardReader = (in.readByte() & 0xFF);
/* 47 */     this.status = (in.readByte() & 0xFF);
/*    */   }
/*    */   
/*    */   public String toString() {
/* 51 */     StringBuilder sb = new StringBuilder();
/* 52 */     sb.append('[');
/* 53 */     sb.append("dayTimePm: ").append(this.dayTimePm).append(", ");
/* 54 */     sb.append("tunnel: ").append(this.tunnel).append(", ");
/* 55 */     sb.append("place: ").append(this.place).append(", ");
/* 56 */     sb.append("cardReader: ").append(this.cardReader).append(", ");
/* 57 */     sb.append("status: ").append(this.status).append(']');
/* 58 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\fw2\cmdparam\tem\TemRoomAccessReportPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */