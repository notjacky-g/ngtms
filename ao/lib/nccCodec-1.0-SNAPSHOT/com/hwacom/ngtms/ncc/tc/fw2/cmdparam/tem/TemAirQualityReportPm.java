/*     */ package com.hwacom.ngtms.ncc.tc.fw2.cmdparam.tem;
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
/*     */ @CommandParams(cmdName="temAirQualityReport")
/*     */ public class TemAirQualityReportPm
/*     */   implements Externalizable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   public static final int cmdId = 17;
/*     */   public static final String cmdName = "temAirQualityReport";
/*     */   @CommandParam(name="dayTime")
/*     */   public DayTimePm dayTimePm;
/*     */   @CommandParam(name="tunnel")
/*     */   public int tunnel;
/*     */   @CommandParam(name="place")
/*     */   public int place;
/*     */   @CommandParam(name="mileKilometer")
/*     */   public int mileKilometer;
/*     */   @CommandParam(name="mileMeter")
/*     */   public int mileMeter;
/*     */   @CommandParam(name="type")
/*     */   public int type;
/*     */   @CommandParam(name="density1")
/*     */   public int density1;
/*     */   @CommandParam(name="level1")
/*     */   public int level1;
/*     */   @CommandParam(name="odd1")
/*     */   public int odd1;
/*     */   @CommandParam(name="density2")
/*     */   public int density2;
/*     */   @CommandParam(name="level2")
/*     */   public int level2;
/*     */   @CommandParam(name="odd2")
/*     */   public int odd2;
/*     */   @CommandParam(name="density3")
/*     */   public int density3;
/*     */   @CommandParam(name="level3")
/*     */   public int level3;
/*     */   @CommandParam(name="odd3")
/*     */   public int odd3;
/*     */   @CommandParam(name="density4")
/*     */   public int density4;
/*     */   @CommandParam(name="level4")
/*     */   public int level4;
/*     */   @CommandParam(name="odd4")
/*     */   public int odd4;
/*     */   @CommandParam(name="density5")
/*     */   public int density5;
/*     */   @CommandParam(name="level5")
/*     */   public int level5;
/*     */   @CommandParam(name="odd5")
/*     */   public int odd5;
/*     */   
/*     */   public void writeExternal(ObjectOutput out)
/*     */     throws IOException
/*     */   {
/*  82 */     out.writeObject(this.dayTimePm);
/*  83 */     out.writeByte(this.tunnel);
/*  84 */     out.writeByte(this.place);
/*  85 */     out.writeShort(this.mileKilometer);
/*  86 */     out.writeShort(this.mileMeter);
/*  87 */     out.writeByte(this.type);
/*  88 */     out.writeShort(this.density1);
/*  89 */     out.writeByte(this.level1);
/*  90 */     out.writeByte(this.odd1);
/*  91 */     out.writeShort(this.density2);
/*  92 */     out.writeByte(this.level2);
/*  93 */     out.writeByte(this.odd2);
/*  94 */     out.writeShort(this.density3);
/*  95 */     out.writeByte(this.level3);
/*  96 */     out.writeByte(this.odd3);
/*  97 */     out.writeShort(this.density4);
/*  98 */     out.writeByte(this.level4);
/*  99 */     out.writeByte(this.odd4);
/* 100 */     out.writeShort(this.density5);
/* 101 */     out.writeByte(this.level5);
/* 102 */     out.writeByte(this.odd5);
/*     */   }
/*     */   
/*     */   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*     */   {
/* 107 */     this.dayTimePm = ((DayTimePm)in.readObject());
/* 108 */     this.tunnel = (in.readByte() & 0xFF);
/* 109 */     this.place = (in.readByte() & 0xFF);
/* 110 */     this.mileKilometer = (in.readShort() & 0xFFFF);
/* 111 */     this.mileMeter = (in.readShort() & 0xFFFF);
/* 112 */     this.type = (in.readByte() & 0xFF);
/* 113 */     this.density1 = (in.readShort() & 0xFFFF);
/* 114 */     this.level1 = (in.readByte() & 0xFF);
/* 115 */     this.odd1 = (in.readByte() & 0xFF);
/* 116 */     this.density2 = (in.readShort() & 0xFFFF);
/* 117 */     this.level2 = (in.readByte() & 0xFF);
/* 118 */     this.odd2 = (in.readByte() & 0xFF);
/* 119 */     this.density3 = (in.readShort() & 0xFFFF);
/* 120 */     this.level3 = (in.readByte() & 0xFF);
/* 121 */     this.odd3 = (in.readByte() & 0xFF);
/* 122 */     this.density4 = (in.readShort() & 0xFFFF);
/* 123 */     this.level4 = (in.readByte() & 0xFF);
/* 124 */     this.odd4 = (in.readByte() & 0xFF);
/* 125 */     this.density5 = (in.readShort() & 0xFFFF);
/* 126 */     this.level5 = (in.readByte() & 0xFF);
/* 127 */     this.odd5 = (in.readByte() & 0xFF);
/*     */   }
/*     */   
/*     */   public String toString() {
/* 131 */     StringBuilder sb = new StringBuilder();
/* 132 */     sb.append('[');
/* 133 */     sb.append("dayTimePm: ").append(this.dayTimePm).append(", ");
/* 134 */     sb.append("tunnel: ").append(this.tunnel).append(", ");
/* 135 */     sb.append("place: ").append(this.place).append(", ");
/* 136 */     sb.append("mileKilometer: ").append(this.mileKilometer).append(", ");
/* 137 */     sb.append("mileMeter: ").append(this.mileMeter).append(", ");
/* 138 */     sb.append("type: ").append(this.type).append(", ");
/* 139 */     sb.append("density1: ").append(this.density1).append(", ");
/* 140 */     sb.append("level1: ").append(this.level1).append(", ");
/* 141 */     sb.append("odd1: ").append(this.odd1).append(", ");
/* 142 */     sb.append("density2: ").append(this.density2).append(", ");
/* 143 */     sb.append("level2: ").append(this.level2).append(", ");
/* 144 */     sb.append("odd2: ").append(this.odd2).append(", ");
/* 145 */     sb.append("density3: ").append(this.density3).append(", ");
/* 146 */     sb.append("level3: ").append(this.level3).append(", ");
/* 147 */     sb.append("odd3: ").append(this.odd3).append(", ");
/* 148 */     sb.append("density4: ").append(this.density4).append(", ");
/* 149 */     sb.append("level4: ").append(this.level4).append(", ");
/* 150 */     sb.append("odd4: ").append(this.odd4).append(", ");
/* 151 */     sb.append("density5: ").append(this.density5).append(", ");
/* 152 */     sb.append("level5: ").append(this.level5).append(", ");
/* 153 */     sb.append("odd5: ").append(this.odd5).append(']');
/* 154 */     return sb.toString();
/*     */   }
/*     */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\fw2\cmdparam\tem\TemAirQualityReportPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */