/*     */ package com.hwacom.ngtms.ncc.tc.ct3.cmdparam.sig;
/*     */ 
/*     */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParam;
/*     */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParams;
/*     */ import java.io.Externalizable;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInput;
/*     */ import java.io.ObjectOutput;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @CommandParams(cmdName="setReverseLaneSpecialDayReq")
/*     */ public class SetReverseLaneSpecialDayReqPm
/*     */   implements Externalizable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   public static final int cmdId = 24338;
/*     */   public static final String cmdName = "setReverseLaneSpecialDayReq";
/*     */   @CommandParam(name="inDirect")
/*     */   public int inDirect;
/*     */   @CommandParam(name="inStartHour")
/*     */   public int inStartHour;
/*     */   @CommandParam(name="inStartMin")
/*     */   public int inStartMin;
/*     */   @CommandParam(name="inEndHour")
/*     */   public int inEndHour;
/*     */   @CommandParam(name="inEndMin")
/*     */   public int inEndMin;
/*     */   @CommandParam(name="outDirect")
/*     */   public int outDirect;
/*     */   @CommandParam(name="outStartHour")
/*     */   public int outStartHour;
/*     */   @CommandParam(name="outStartMin")
/*     */   public int outStartMin;
/*     */   @CommandParam(name="outEndHour")
/*     */   public int outEndHour;
/*     */   @CommandParam(name="outEndMin")
/*     */   public int outEndMin;
/*     */   @CommandParam(name="clearTime")
/*     */   public int clearTime;
/*     */   @CommandParam(name="flashGreen")
/*     */   public int flashGreen;
/*     */   @CommandParam(name="greenTime")
/*     */   public int greenTime;
/*     */   @CommandParam(name="reverseTimeType")
/*     */   public int reverseTimeType;
/*     */   @CommandParam(name="startYear")
/*     */   public int startYear;
/*     */   @CommandParam(name="startMonth")
/*     */   public int startMonth;
/*     */   @CommandParam(name="startDay")
/*     */   public int startDay;
/*     */   @CommandParam(name="endYear")
/*     */   public int endYear;
/*     */   @CommandParam(name="endMonth")
/*     */   public int endMonth;
/*     */   @CommandParam(name="endDay")
/*     */   public int endDay;
/*     */   
/*     */   public void writeExternal(ObjectOutput out)
/*     */     throws IOException
/*     */   {
/*  80 */     out.writeByte(this.inDirect);
/*  81 */     out.writeByte(this.inStartHour);
/*  82 */     out.writeByte(this.inStartMin);
/*  83 */     out.writeByte(this.inEndHour);
/*  84 */     out.writeByte(this.inEndMin);
/*  85 */     out.writeByte(this.outDirect);
/*  86 */     out.writeByte(this.outStartHour);
/*  87 */     out.writeByte(this.outStartMin);
/*  88 */     out.writeByte(this.outEndHour);
/*  89 */     out.writeByte(this.outEndMin);
/*  90 */     out.writeByte(this.clearTime);
/*  91 */     out.writeByte(this.flashGreen);
/*  92 */     out.writeByte(this.greenTime);
/*  93 */     out.writeByte(this.reverseTimeType);
/*  94 */     out.writeByte(this.startYear);
/*  95 */     out.writeByte(this.startMonth);
/*  96 */     out.writeByte(this.startDay);
/*  97 */     out.writeByte(this.endYear);
/*  98 */     out.writeByte(this.endMonth);
/*  99 */     out.writeByte(this.endDay);
/*     */   }
/*     */   
/*     */   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*     */   {
/* 104 */     this.inDirect = (in.readByte() & 0xFF);
/* 105 */     this.inStartHour = (in.readByte() & 0xFF);
/* 106 */     this.inStartMin = (in.readByte() & 0xFF);
/* 107 */     this.inEndHour = (in.readByte() & 0xFF);
/* 108 */     this.inEndMin = (in.readByte() & 0xFF);
/* 109 */     this.outDirect = (in.readByte() & 0xFF);
/* 110 */     this.outStartHour = (in.readByte() & 0xFF);
/* 111 */     this.outStartMin = (in.readByte() & 0xFF);
/* 112 */     this.outEndHour = (in.readByte() & 0xFF);
/* 113 */     this.outEndMin = (in.readByte() & 0xFF);
/* 114 */     this.clearTime = (in.readByte() & 0xFF);
/* 115 */     this.flashGreen = (in.readByte() & 0xFF);
/* 116 */     this.greenTime = (in.readByte() & 0xFF);
/* 117 */     this.reverseTimeType = (in.readByte() & 0xFF);
/* 118 */     this.startYear = (in.readByte() & 0xFF);
/* 119 */     this.startMonth = (in.readByte() & 0xFF);
/* 120 */     this.startDay = (in.readByte() & 0xFF);
/* 121 */     this.endYear = (in.readByte() & 0xFF);
/* 122 */     this.endMonth = (in.readByte() & 0xFF);
/* 123 */     this.endDay = (in.readByte() & 0xFF);
/*     */   }
/*     */   
/*     */   public String toString() {
/* 127 */     StringBuilder sb = new StringBuilder();
/* 128 */     sb.append('[');
/* 129 */     sb.append("inDirect: ").append(this.inDirect).append(", ");
/* 130 */     sb.append("inStartHour: ").append(this.inStartHour).append(", ");
/* 131 */     sb.append("inStartMin: ").append(this.inStartMin).append(", ");
/* 132 */     sb.append("inEndHour: ").append(this.inEndHour).append(", ");
/* 133 */     sb.append("inEndMin: ").append(this.inEndMin).append(", ");
/* 134 */     sb.append("outDirect: ").append(this.outDirect).append(", ");
/* 135 */     sb.append("outStartHour: ").append(this.outStartHour).append(", ");
/* 136 */     sb.append("outStartMin: ").append(this.outStartMin).append(", ");
/* 137 */     sb.append("outEndHour: ").append(this.outEndHour).append(", ");
/* 138 */     sb.append("outEndMin: ").append(this.outEndMin).append(", ");
/* 139 */     sb.append("clearTime: ").append(this.clearTime).append(", ");
/* 140 */     sb.append("flashGreen: ").append(this.flashGreen).append(", ");
/* 141 */     sb.append("greenTime: ").append(this.greenTime).append(", ");
/* 142 */     sb.append("reverseTimeType: ").append(this.reverseTimeType).append(", ");
/* 143 */     sb.append("startYear: ").append(this.startYear).append(", ");
/* 144 */     sb.append("startMonth: ").append(this.startMonth).append(", ");
/* 145 */     sb.append("startDay: ").append(this.startDay).append(", ");
/* 146 */     sb.append("endYear: ").append(this.endYear).append(", ");
/* 147 */     sb.append("endMonth: ").append(this.endMonth).append(", ");
/* 148 */     sb.append("endDay: ").append(this.endDay).append(']');
/* 149 */     return sb.toString();
/*     */   }
/*     */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\ct3\cmdparam\sig\SetReverseLaneSpecialDayReqPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */