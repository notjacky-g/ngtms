/*    */ package com.hwacom.ngtms.ncc.tc.ct3.cmdparam.etag;
/*    */ 
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParam;
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.GlobalParams;
/*    */ import java.io.Externalizable;
/*    */ import java.io.IOException;
/*    */ import java.io.ObjectInput;
/*    */ import java.io.ObjectOutput;
/*    */ import java.nio.charset.Charset;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @GlobalParams(paramsName="vehicle")
/*    */ public class VehiclePm
/*    */   implements Externalizable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   @CommandParam(name="vehicleId")
/*    */   public String vehicleId;
/*    */   @CommandParam(name="year")
/*    */   public int year;
/*    */   @CommandParam(name="month")
/*    */   public int month;
/*    */   @CommandParam(name="day")
/*    */   public int day;
/*    */   @CommandParam(name="hour")
/*    */   public int hour;
/*    */   @CommandParam(name="min")
/*    */   public int min;
/*    */   @CommandParam(name="sec")
/*    */   public int sec;
/*    */   
/*    */   public void writeExternal(ObjectOutput out)
/*    */     throws IOException
/*    */   {
/* 42 */     byte[] bytes = this.vehicleId == null ? new byte[0] : this.vehicleId.getBytes(Charset.forName("UTF-8"));
/* 43 */     out.writeInt(bytes.length);
/* 44 */     out.write(bytes);
/* 45 */     out.writeByte(this.year);
/* 46 */     out.writeByte(this.month);
/* 47 */     out.writeByte(this.day);
/* 48 */     out.writeByte(this.hour);
/* 49 */     out.writeByte(this.min);
/* 50 */     out.writeByte(this.sec);
/*    */   }
/*    */   
/*    */   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*    */   {
/* 55 */     int len = in.readInt();
/* 56 */     byte[] bytes = new byte[len];
/* 57 */     in.read(bytes);
/* 58 */     this.vehicleId = new String(bytes, Charset.forName("UTF-8"));
/* 59 */     this.year = (in.readByte() & 0xFF);
/* 60 */     this.month = (in.readByte() & 0xFF);
/* 61 */     this.day = (in.readByte() & 0xFF);
/* 62 */     this.hour = (in.readByte() & 0xFF);
/* 63 */     this.min = (in.readByte() & 0xFF);
/* 64 */     this.sec = (in.readByte() & 0xFF);
/*    */   }
/*    */   
/*    */   public String toString() {
/* 68 */     StringBuilder sb = new StringBuilder();
/* 69 */     sb.append('[');
/* 70 */     sb.append("vehicleId: ").append(this.vehicleId).append(", ");
/* 71 */     sb.append("year: ").append(this.year).append(", ");
/* 72 */     sb.append("month: ").append(this.month).append(", ");
/* 73 */     sb.append("day: ").append(this.day).append(", ");
/* 74 */     sb.append("hour: ").append(this.hour).append(", ");
/* 75 */     sb.append("min: ").append(this.min).append(", ");
/* 76 */     sb.append("sec: ").append(this.sec).append(']');
/* 77 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\ct3\cmdparam\etag\VehiclePm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */