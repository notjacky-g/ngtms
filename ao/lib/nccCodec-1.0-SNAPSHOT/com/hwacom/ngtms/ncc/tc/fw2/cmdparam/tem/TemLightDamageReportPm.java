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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @CommandParams(cmdName="temLightDamageReport")
/*    */ public class TemLightDamageReportPm
/*    */   implements Externalizable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   public static final int cmdId = 18;
/*    */   public static final String cmdName = "temLightDamageReport";
/*    */   @CommandParam(name="dayTime")
/*    */   public DayTimePm dayTimePm;
/*    */   @CommandParam(name="tunnel")
/*    */   public int tunnel;
/*    */   @CommandParam(name="place")
/*    */   public int place;
/*    */   @CommandParam(name="div")
/*    */   public int div;
/*    */   @CommandParam(name="startMileKilometer")
/*    */   public int startMileKilometer;
/*    */   @CommandParam(name="startMileMeter")
/*    */   public int startMileMeter;
/*    */   @CommandParam(name="stopMileKilometer")
/*    */   public int stopMileKilometer;
/*    */   @CommandParam(name="stopMileMeter")
/*    */   public int stopMileMeter;
/*    */   @CommandParam(name="required")
/*    */   public int required;
/*    */   @CommandParam(name="damaged")
/*    */   public int damaged;
/*    */   
/*    */   public void writeExternal(ObjectOutput out)
/*    */     throws IOException
/*    */   {
/* 49 */     out.writeObject(this.dayTimePm);
/* 50 */     out.writeByte(this.tunnel);
/* 51 */     out.writeByte(this.place);
/* 52 */     out.writeByte(this.div);
/* 53 */     out.writeShort(this.startMileKilometer);
/* 54 */     out.writeShort(this.startMileMeter);
/* 55 */     out.writeShort(this.stopMileKilometer);
/* 56 */     out.writeShort(this.stopMileMeter);
/* 57 */     out.writeShort(this.required);
/* 58 */     out.writeShort(this.damaged);
/*    */   }
/*    */   
/*    */   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*    */   {
/* 63 */     this.dayTimePm = ((DayTimePm)in.readObject());
/* 64 */     this.tunnel = (in.readByte() & 0xFF);
/* 65 */     this.place = (in.readByte() & 0xFF);
/* 66 */     this.div = (in.readByte() & 0xFF);
/* 67 */     this.startMileKilometer = (in.readShort() & 0xFFFF);
/* 68 */     this.startMileMeter = (in.readShort() & 0xFFFF);
/* 69 */     this.stopMileKilometer = (in.readShort() & 0xFFFF);
/* 70 */     this.stopMileMeter = (in.readShort() & 0xFFFF);
/* 71 */     this.required = (in.readShort() & 0xFFFF);
/* 72 */     this.damaged = (in.readShort() & 0xFFFF);
/*    */   }
/*    */   
/*    */   public String toString() {
/* 76 */     StringBuilder sb = new StringBuilder();
/* 77 */     sb.append('[');
/* 78 */     sb.append("dayTimePm: ").append(this.dayTimePm).append(", ");
/* 79 */     sb.append("tunnel: ").append(this.tunnel).append(", ");
/* 80 */     sb.append("place: ").append(this.place).append(", ");
/* 81 */     sb.append("div: ").append(this.div).append(", ");
/* 82 */     sb.append("startMileKilometer: ").append(this.startMileKilometer).append(", ");
/* 83 */     sb.append("startMileMeter: ").append(this.startMileMeter).append(", ");
/* 84 */     sb.append("stopMileKilometer: ").append(this.stopMileKilometer).append(", ");
/* 85 */     sb.append("stopMileMeter: ").append(this.stopMileMeter).append(", ");
/* 86 */     sb.append("required: ").append(this.required).append(", ");
/* 87 */     sb.append("damaged: ").append(this.damaged).append(']');
/* 88 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\fw2\cmdparam\tem\TemLightDamageReportPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */