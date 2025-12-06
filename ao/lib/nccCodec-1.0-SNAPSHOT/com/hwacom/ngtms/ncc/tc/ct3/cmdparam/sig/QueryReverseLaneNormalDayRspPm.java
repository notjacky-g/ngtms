/*     */ package com.hwacom.ngtms.ncc.tc.ct3.cmdparam.sig;
/*     */ 
/*     */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParam;
/*     */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParams;
/*     */ import java.io.Externalizable;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInput;
/*     */ import java.io.ObjectOutput;
/*     */ import java.util.List;
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
/*     */ @CommandParams(cmdName="queryReverseLaneNormalDayRsp")
/*     */ public class QueryReverseLaneNormalDayRspPm
/*     */   implements Externalizable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   public static final int cmdId = 24513;
/*     */   public static final String cmdName = "queryReverseLaneNormalDayRsp";
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
/*     */   @CommandParam(name="weekDayList")
/*     */   public List<WeekDayListItem> weekDayList;
/*     */   
/*     */   public static class WeekDayListItem
/*     */     implements Externalizable
/*     */   {
/*     */     private static final long serialVersionUID = 1L;
/*     */     @CommandParam(name="weekDay")
/*     */     public int weekDay;
/*     */     
/*     */     public void writeExternal(ObjectOutput out)
/*     */       throws IOException
/*     */     {
/*  71 */       out.writeByte(this.weekDay);
/*     */     }
/*     */     
/*     */     public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*     */     {
/*  76 */       this.weekDay = (in.readByte() & 0xFF);
/*     */     }
/*     */     
/*     */     public String toString() {
/*  80 */       StringBuilder sb = new StringBuilder();
/*  81 */       sb.append('[');
/*  82 */       sb.append("weekDay: ").append(this.weekDay).append(']');
/*  83 */       return sb.toString();
/*     */     }
/*     */   }
/*     */   
/*     */   public void writeExternal(ObjectOutput out) throws IOException
/*     */   {
/*  89 */     out.writeByte(this.inDirect);
/*  90 */     out.writeByte(this.inStartHour);
/*  91 */     out.writeByte(this.inStartMin);
/*  92 */     out.writeByte(this.inEndHour);
/*  93 */     out.writeByte(this.inEndMin);
/*  94 */     out.writeByte(this.outDirect);
/*  95 */     out.writeByte(this.outStartHour);
/*  96 */     out.writeByte(this.outStartMin);
/*  97 */     out.writeByte(this.outEndHour);
/*  98 */     out.writeByte(this.outEndMin);
/*  99 */     out.writeByte(this.clearTime);
/* 100 */     out.writeByte(this.flashGreen);
/* 101 */     out.writeByte(this.greenTime);
/* 102 */     out.writeByte(this.reverseTimeType);
/* 103 */     out.writeObject(this.weekDayList);
/*     */   }
/*     */   
/*     */   public void readExternal(ObjectInput in)
/*     */     throws IOException, ClassNotFoundException
/*     */   {
/* 109 */     this.inDirect = (in.readByte() & 0xFF);
/* 110 */     this.inStartHour = (in.readByte() & 0xFF);
/* 111 */     this.inStartMin = (in.readByte() & 0xFF);
/* 112 */     this.inEndHour = (in.readByte() & 0xFF);
/* 113 */     this.inEndMin = (in.readByte() & 0xFF);
/* 114 */     this.outDirect = (in.readByte() & 0xFF);
/* 115 */     this.outStartHour = (in.readByte() & 0xFF);
/* 116 */     this.outStartMin = (in.readByte() & 0xFF);
/* 117 */     this.outEndHour = (in.readByte() & 0xFF);
/* 118 */     this.outEndMin = (in.readByte() & 0xFF);
/* 119 */     this.clearTime = (in.readByte() & 0xFF);
/* 120 */     this.flashGreen = (in.readByte() & 0xFF);
/* 121 */     this.greenTime = (in.readByte() & 0xFF);
/* 122 */     this.reverseTimeType = (in.readByte() & 0xFF);
/* 123 */     this.weekDayList = ((List)in.readObject());
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
/* 143 */     sb.append("weekDayList: ").append(this.weekDayList).append(']');
/* 144 */     return sb.toString();
/*     */   }
/*     */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\ct3\cmdparam\sig\QueryReverseLaneNormalDayRspPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */