/*     */ package com.hwacom.ngtms.ncc.tc.fw2.cmdparam.lsd;
/*     */ 
/*     */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParam;
/*     */ import com.hwacom.ngtms.ncc.tc.fw2.cmdparam.HwStatusPm;
/*     */ import java.io.Externalizable;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInput;
/*     */ import java.io.ObjectOutput;
/*     */ import java.util.List;
/*     */ 
/*     */ @com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParams(cmdName="periodLsdDataReport")
/*     */ public class PeriodLsdDataReportPm implements Externalizable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   public static final int cmdId = 48;
/*     */   public static final String cmdName = "periodLsdDataReport";
/*     */   @CommandParam(name="responseType")
/*     */   public int responseType;
/*     */   @CommandParam(name="hwStatus")
/*     */   public HwStatusPm hwStatusPm;
/*     */   @CommandParam(name="dhm")
/*     */   public com.hwacom.ngtms.ncc.tc.fw2.cmdparam.DhmPm dhmPm;
/*     */   @CommandParam(name="sensorVariableList")
/*     */   public List<SensorVariableListItem> sensorVariableList;
/*     */   @CommandParam(name="accumulateVariableList")
/*     */   public List<AccumulateVariableListItem> accumulateVariableList;
/*     */   @CommandParam(name="dayVariable")
/*     */   public int dayVariable;
/*     */   
/*     */   public static class SensorVariableListItem implements Externalizable
/*     */   {
/*     */     private static final long serialVersionUID = 1L;
/*     */     @CommandParam(name="sensorVariable")
/*     */     public int sensorVariable;
/*     */     
/*     */     public void writeExternal(ObjectOutput out) throws IOException
/*     */     {
/*  38 */       out.writeShort(this.sensorVariable);
/*     */     }
/*     */     
/*     */     public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*     */     {
/*  43 */       this.sensorVariable = (in.readShort() & 0xFFFF);
/*     */     }
/*     */     
/*     */     public String toString() {
/*  47 */       StringBuilder sb = new StringBuilder();
/*  48 */       sb.append('[');
/*  49 */       sb.append("sensorVariable: ").append(this.sensorVariable).append(']');
/*  50 */       return sb.toString();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public static class AccumulateVariableListItem
/*     */     implements Externalizable
/*     */   {
/*     */     private static final long serialVersionUID = 1L;
/*     */     @CommandParam(name="accumulateVariable")
/*     */     public int accumulateVariable;
/*     */     
/*     */     public void writeExternal(ObjectOutput out)
/*     */       throws IOException
/*     */     {
/*  65 */       out.writeShort(this.accumulateVariable);
/*     */     }
/*     */     
/*     */     public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*     */     {
/*  70 */       this.accumulateVariable = (in.readShort() & 0xFFFF);
/*     */     }
/*     */     
/*     */     public String toString() {
/*  74 */       StringBuilder sb = new StringBuilder();
/*  75 */       sb.append('[');
/*  76 */       sb.append("accumulateVariable: ").append(this.accumulateVariable).append(']');
/*  77 */       return sb.toString();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void writeExternal(ObjectOutput out)
/*     */     throws IOException
/*     */   {
/*  86 */     out.writeByte(this.responseType);
/*  87 */     out.writeObject(this.hwStatusPm);
/*  88 */     out.writeObject(this.dhmPm);
/*  89 */     out.writeObject(this.sensorVariableList);
/*  90 */     out.writeObject(this.accumulateVariableList);
/*  91 */     out.writeShort(this.dayVariable);
/*     */   }
/*     */   
/*     */   public void readExternal(ObjectInput in)
/*     */     throws IOException, ClassNotFoundException
/*     */   {
/*  97 */     this.responseType = (in.readByte() & 0xFF);
/*  98 */     this.hwStatusPm = ((HwStatusPm)in.readObject());
/*  99 */     this.dhmPm = ((com.hwacom.ngtms.ncc.tc.fw2.cmdparam.DhmPm)in.readObject());
/* 100 */     this.sensorVariableList = ((List)in.readObject());
/* 101 */     this.accumulateVariableList = ((List)in.readObject());
/* 102 */     this.dayVariable = (in.readShort() & 0xFFFF);
/*     */   }
/*     */   
/*     */   public String toString() {
/* 106 */     StringBuilder sb = new StringBuilder();
/* 107 */     sb.append('[');
/* 108 */     sb.append("responseType: ").append(this.responseType).append(", ");
/* 109 */     sb.append("hwStatusPm: ").append(this.hwStatusPm).append(", ");
/* 110 */     sb.append("dhmPm: ").append(this.dhmPm).append(", ");
/* 111 */     sb.append("sensorVariableList: ").append(this.sensorVariableList).append(", ");
/* 112 */     sb.append("accumulateVariableList: ").append(this.accumulateVariableList).append(", ");
/* 113 */     sb.append("dayVariable: ").append(this.dayVariable).append(']');
/* 114 */     return sb.toString();
/*     */   }
/*     */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\fw2\cmdparam\lsd\PeriodLsdDataReportPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */