/*     */ package com.hwacom.ngtms.ncc.tc.fw2.cmdparam.ettu;
/*     */ 
/*     */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.BytesUtility;
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
/*     */ @CommandParams(cmdName="getEttuFailStatisticsRsp")
/*     */ public class GetEttuFailStatisticsRspPm
/*     */   implements Externalizable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   public static final int cmdId = 10527;
/*     */   public static final String cmdName = "getEttuFailStatisticsRsp";
/*     */   @CommandParam(name="beginMonthString")
/*     */   public byte[] beginMonthString;
/*     */   @CommandParam(name="beginDayString")
/*     */   public byte[] beginDayString;
/*     */   @CommandParam(name="beginYearString")
/*     */   public byte[] beginYearString;
/*     */   @CommandParam(name="beginHourString")
/*     */   public byte[] beginHourString;
/*     */   @CommandParam(name="beginMinuteString")
/*     */   public byte[] beginMinuteString;
/*     */   @CommandParam(name="beginSecondString")
/*     */   public byte[] beginSecondString;
/*     */   @CommandParam(name="endMonthString")
/*     */   public byte[] endMonthString;
/*     */   @CommandParam(name="endDayString")
/*     */   public byte[] endDayString;
/*     */   @CommandParam(name="endYearString")
/*     */   public byte[] endYearString;
/*     */   @CommandParam(name="endHourString")
/*     */   public byte[] endHourString;
/*     */   @CommandParam(name="endMinuteString")
/*     */   public byte[] endMinuteString;
/*     */   @CommandParam(name="endSecondString")
/*     */   public byte[] endSecondString;
/*     */   @CommandParam(name="failData")
/*     */   public byte[] failData;
/*     */   @CommandParam(name="lrc")
/*     */   public int lrc;
/*     */   @CommandParam(name="endByte")
/*  60 */   public static final byte[] endByte = { -57 };
/*     */   
/*     */   public void writeExternal(ObjectOutput out) throws IOException
/*     */   {
/*  64 */     out.writeObject(this.beginMonthString);
/*  65 */     out.writeObject(this.beginDayString);
/*  66 */     out.writeObject(this.beginYearString);
/*  67 */     out.writeObject(this.beginHourString);
/*  68 */     out.writeObject(this.beginMinuteString);
/*  69 */     out.writeObject(this.beginSecondString);
/*  70 */     out.writeObject(this.endMonthString);
/*  71 */     out.writeObject(this.endDayString);
/*  72 */     out.writeObject(this.endYearString);
/*  73 */     out.writeObject(this.endHourString);
/*  74 */     out.writeObject(this.endMinuteString);
/*  75 */     out.writeObject(this.endSecondString);
/*  76 */     out.writeObject(this.failData);
/*  77 */     out.writeShort(this.lrc);
/*     */   }
/*     */   
/*     */   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*     */   {
/*  82 */     this.beginMonthString = ((byte[])in.readObject());
/*  83 */     this.beginDayString = ((byte[])in.readObject());
/*  84 */     this.beginYearString = ((byte[])in.readObject());
/*  85 */     this.beginHourString = ((byte[])in.readObject());
/*  86 */     this.beginMinuteString = ((byte[])in.readObject());
/*  87 */     this.beginSecondString = ((byte[])in.readObject());
/*  88 */     this.endMonthString = ((byte[])in.readObject());
/*  89 */     this.endDayString = ((byte[])in.readObject());
/*  90 */     this.endYearString = ((byte[])in.readObject());
/*  91 */     this.endHourString = ((byte[])in.readObject());
/*  92 */     this.endMinuteString = ((byte[])in.readObject());
/*  93 */     this.endSecondString = ((byte[])in.readObject());
/*  94 */     this.failData = ((byte[])in.readObject());
/*  95 */     this.lrc = (in.readShort() & 0xFFFF);
/*     */   }
/*     */   
/*     */   public String toString() {
/*  99 */     StringBuilder sb = new StringBuilder();
/* 100 */     sb.append('[');
/* 101 */     sb.append("beginMonthString: ")
/* 102 */       .append(BytesUtility.toHexString(this.beginMonthString))
/* 103 */       .append(", ");
/* 104 */     sb.append("beginDayString: ")
/* 105 */       .append(BytesUtility.toHexString(this.beginDayString))
/* 106 */       .append(", ");
/* 107 */     sb.append("beginYearString: ")
/* 108 */       .append(BytesUtility.toHexString(this.beginYearString))
/* 109 */       .append(", ");
/* 110 */     sb.append("beginHourString: ")
/* 111 */       .append(BytesUtility.toHexString(this.beginHourString))
/* 112 */       .append(", ");
/* 113 */     sb.append("beginMinuteString: ")
/* 114 */       .append(BytesUtility.toHexString(this.beginMinuteString))
/* 115 */       .append(", ");
/* 116 */     sb.append("beginSecondString: ")
/* 117 */       .append(BytesUtility.toHexString(this.beginSecondString))
/* 118 */       .append(", ");
/* 119 */     sb.append("endMonthString: ")
/* 120 */       .append(BytesUtility.toHexString(this.endMonthString))
/* 121 */       .append(", ");
/* 122 */     sb.append("endDayString: ")
/* 123 */       .append(BytesUtility.toHexString(this.endDayString))
/* 124 */       .append(", ");
/* 125 */     sb.append("endYearString: ")
/* 126 */       .append(BytesUtility.toHexString(this.endYearString))
/* 127 */       .append(", ");
/* 128 */     sb.append("endHourString: ")
/* 129 */       .append(BytesUtility.toHexString(this.endHourString))
/* 130 */       .append(", ");
/* 131 */     sb.append("endMinuteString: ")
/* 132 */       .append(BytesUtility.toHexString(this.endMinuteString))
/* 133 */       .append(", ");
/* 134 */     sb.append("endSecondString: ")
/* 135 */       .append(BytesUtility.toHexString(this.endSecondString))
/* 136 */       .append(", ");
/* 137 */     sb.append("failData: ")
/* 138 */       .append(BytesUtility.toHexString(this.failData))
/* 139 */       .append(", ");
/* 140 */     sb.append("lrc: ").append(this.lrc).append(", ");
/* 141 */     sb.append("endByte: ")
/* 142 */       .append(BytesUtility.toHexString(endByte))
/* 143 */       .append(']');
/* 144 */     return sb.toString();
/*     */   }
/*     */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\fw2\cmdparam\ettu\GetEttuFailStatisticsRspPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */