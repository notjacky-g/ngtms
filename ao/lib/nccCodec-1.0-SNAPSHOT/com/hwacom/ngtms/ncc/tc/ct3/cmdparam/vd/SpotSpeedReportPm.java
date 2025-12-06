/*    */ package com.hwacom.ngtms.ncc.tc.ct3.cmdparam.vd;
/*    */ 
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParam;
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParams;
/*    */ import java.io.Externalizable;
/*    */ import java.io.IOException;
/*    */ import java.io.ObjectInput;
/*    */ import java.io.ObjectOutput;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ @CommandParams(cmdName="spotSpeedReport")
/*    */ public class SpotSpeedReportPm
/*    */   implements Externalizable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   public static final int cmdId = 28418;
/*    */   public static final String cmdName = "spotSpeedReport";
/*    */   @CommandParam(name="hour")
/*    */   public int hour;
/*    */   @CommandParam(name="min")
/*    */   public int min;
/*    */   @CommandParam(name="sec")
/*    */   public int sec;
/*    */   @CommandParam(name="detectData")
/*    */   public List<DetectDataItem> detectData;
/*    */   
/*    */   public static class DetectDataItem
/*    */     implements Externalizable
/*    */   {
/*    */     private static final long serialVersionUID = 1L;
/*    */     @CommandParam(name="vehType")
/*    */     public int vehType;
/*    */     @CommandParam(name="vehSpeed")
/*    */     public int vehSpeed;
/*    */     
/*    */     public void writeExternal(ObjectOutput out)
/*    */       throws IOException
/*    */     {
/* 41 */       out.writeShort(this.vehType);
/* 42 */       out.writeByte(this.vehSpeed);
/*    */     }
/*    */     
/*    */     public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*    */     {
/* 47 */       this.vehType = (in.readShort() & 0xFFFF);
/* 48 */       this.vehSpeed = (in.readByte() & 0xFF);
/*    */     }
/*    */     
/*    */     public String toString() {
/* 52 */       StringBuilder sb = new StringBuilder();
/* 53 */       sb.append('[');
/* 54 */       sb.append("vehType: ").append(this.vehType).append(", ");
/* 55 */       sb.append("vehSpeed: ").append(this.vehSpeed).append(']');
/* 56 */       return sb.toString();
/*    */     }
/*    */   }
/*    */   
/*    */   public void writeExternal(ObjectOutput out) throws IOException
/*    */   {
/* 62 */     out.writeByte(this.hour);
/* 63 */     out.writeByte(this.min);
/* 64 */     out.writeByte(this.sec);
/* 65 */     out.writeObject(this.detectData);
/*    */   }
/*    */   
/*    */   public void readExternal(ObjectInput in)
/*    */     throws IOException, ClassNotFoundException
/*    */   {
/* 71 */     this.hour = (in.readByte() & 0xFF);
/* 72 */     this.min = (in.readByte() & 0xFF);
/* 73 */     this.sec = (in.readByte() & 0xFF);
/* 74 */     this.detectData = ((List)in.readObject());
/*    */   }
/*    */   
/*    */   public String toString() {
/* 78 */     StringBuilder sb = new StringBuilder();
/* 79 */     sb.append('[');
/* 80 */     sb.append("hour: ").append(this.hour).append(", ");
/* 81 */     sb.append("min: ").append(this.min).append(", ");
/* 82 */     sb.append("sec: ").append(this.sec).append(", ");
/* 83 */     sb.append("detectData: ").append(this.detectData).append(']');
/* 84 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\ct3\cmdparam\vd\SpotSpeedReportPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */