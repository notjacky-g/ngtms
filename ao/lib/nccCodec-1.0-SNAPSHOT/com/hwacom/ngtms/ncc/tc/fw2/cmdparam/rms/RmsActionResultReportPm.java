/*     */ package com.hwacom.ngtms.ncc.tc.fw2.cmdparam.rms;
/*     */ 
/*     */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParam;
/*     */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParams;
/*     */ import com.hwacom.ngtms.ncc.tc.fw2.cmdparam.HwStatusPm;
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
/*     */ @CommandParams(cmdName="rmsActionResultReport")
/*     */ public class RmsActionResultReportPm
/*     */   implements Externalizable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   public static final int cmdId = 170;
/*     */   public static final String cmdName = "rmsActionResultReport";
/*     */   @CommandParam(name="hwStatus")
/*     */   public HwStatusPm hwStatusPm;
/*     */   @CommandParam(name="openClose")
/*     */   public int openClose;
/*     */   @CommandParam(name="status")
/*     */   public int status;
/*     */   @CommandParam(name="lampSet1DriveErr")
/*     */   public int lampSet1DriveErr;
/*     */   @CommandParam(name="lampSet2DriveErr")
/*     */   public int lampSet2DriveErr;
/*     */   @CommandParam(name="lampSet3DriveErr")
/*     */   public int lampSet3DriveErr;
/*     */   @CommandParam(name="lampSet4DriveErr")
/*     */   public int lampSet4DriveErr;
/*     */   @CommandParam(name="lampSet1DisplayErr")
/*     */   public int lampSet1DisplayErr;
/*     */   @CommandParam(name="lampSet2DisplayErr")
/*     */   public int lampSet2DisplayErr;
/*     */   @CommandParam(name="lampSet3DisplayErr")
/*     */   public int lampSet3DisplayErr;
/*     */   @CommandParam(name="lampSet4DisplayErr")
/*     */   public int lampSet4DisplayErr;
/*     */   @CommandParam(name="boss1DriveErr")
/*     */   public int boss1DriveErr;
/*     */   @CommandParam(name="boss2DriveErr")
/*     */   public int boss2DriveErr;
/*     */   @CommandParam(name="boss3DriveErr")
/*     */   public int boss3DriveErr;
/*     */   @CommandParam(name="boss4DriveErr")
/*     */   public int boss4DriveErr;
/*     */   @CommandParam(name="boss1DisplayErr")
/*     */   public int boss1DisplayErr;
/*     */   @CommandParam(name="boss2DisplayErr")
/*     */   public int boss2DisplayErr;
/*     */   @CommandParam(name="boss3DisplayErr")
/*     */   public int boss3DisplayErr;
/*     */   @CommandParam(name="boss4DisplayErr")
/*     */   public int boss4DisplayErr;
/*     */   
/*     */   public void writeExternal(ObjectOutput out)
/*     */     throws IOException
/*     */   {
/*  77 */     out.writeObject(this.hwStatusPm);
/*  78 */     out.writeByte(this.openClose);
/*  79 */     out.writeByte(this.status);
/*  80 */     out.writeByte(this.lampSet1DriveErr);
/*  81 */     out.writeByte(this.lampSet2DriveErr);
/*  82 */     out.writeByte(this.lampSet3DriveErr);
/*  83 */     out.writeByte(this.lampSet4DriveErr);
/*  84 */     out.writeByte(this.lampSet1DisplayErr);
/*  85 */     out.writeByte(this.lampSet2DisplayErr);
/*  86 */     out.writeByte(this.lampSet3DisplayErr);
/*  87 */     out.writeByte(this.lampSet4DisplayErr);
/*  88 */     out.writeByte(this.boss1DriveErr);
/*  89 */     out.writeByte(this.boss2DriveErr);
/*  90 */     out.writeByte(this.boss3DriveErr);
/*  91 */     out.writeByte(this.boss4DriveErr);
/*  92 */     out.writeByte(this.boss1DisplayErr);
/*  93 */     out.writeByte(this.boss2DisplayErr);
/*  94 */     out.writeByte(this.boss3DisplayErr);
/*  95 */     out.writeByte(this.boss4DisplayErr);
/*     */   }
/*     */   
/*     */   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*     */   {
/* 100 */     this.hwStatusPm = ((HwStatusPm)in.readObject());
/* 101 */     this.openClose = (in.readByte() & 0xFF);
/* 102 */     this.status = (in.readByte() & 0xFF);
/* 103 */     this.lampSet1DriveErr = (in.readByte() & 0xFFFF);
/* 104 */     this.lampSet2DriveErr = (in.readByte() & 0xFFFF);
/* 105 */     this.lampSet3DriveErr = (in.readByte() & 0xFFFF);
/* 106 */     this.lampSet4DriveErr = (in.readByte() & 0xFFFF);
/* 107 */     this.lampSet1DisplayErr = (in.readByte() & 0xFFFF);
/* 108 */     this.lampSet2DisplayErr = (in.readByte() & 0xFFFF);
/* 109 */     this.lampSet3DisplayErr = (in.readByte() & 0xFFFF);
/* 110 */     this.lampSet4DisplayErr = (in.readByte() & 0xFFFF);
/* 111 */     this.boss1DriveErr = (in.readByte() & 0xFFFF);
/* 112 */     this.boss2DriveErr = (in.readByte() & 0xFFFF);
/* 113 */     this.boss3DriveErr = (in.readByte() & 0xFFFF);
/* 114 */     this.boss4DriveErr = (in.readByte() & 0xFFFF);
/* 115 */     this.boss1DisplayErr = (in.readByte() & 0xFFFF);
/* 116 */     this.boss2DisplayErr = (in.readByte() & 0xFFFF);
/* 117 */     this.boss3DisplayErr = (in.readByte() & 0xFFFF);
/* 118 */     this.boss4DisplayErr = (in.readByte() & 0xFFFF);
/*     */   }
/*     */   
/*     */   public String toString() {
/* 122 */     StringBuilder sb = new StringBuilder();
/* 123 */     sb.append('[');
/* 124 */     sb.append("hwStatusPm: ").append(this.hwStatusPm).append(", ");
/* 125 */     sb.append("openClose: ").append(this.openClose).append(", ");
/* 126 */     sb.append("status: ").append(this.status).append(", ");
/* 127 */     sb.append("lampSet1DriveErr: ").append(this.lampSet1DriveErr).append(", ");
/* 128 */     sb.append("lampSet2DriveErr: ").append(this.lampSet2DriveErr).append(", ");
/* 129 */     sb.append("lampSet3DriveErr: ").append(this.lampSet3DriveErr).append(", ");
/* 130 */     sb.append("lampSet4DriveErr: ").append(this.lampSet4DriveErr).append(", ");
/* 131 */     sb.append("lampSet1DisplayErr: ").append(this.lampSet1DisplayErr).append(", ");
/* 132 */     sb.append("lampSet2DisplayErr: ").append(this.lampSet2DisplayErr).append(", ");
/* 133 */     sb.append("lampSet3DisplayErr: ").append(this.lampSet3DisplayErr).append(", ");
/* 134 */     sb.append("lampSet4DisplayErr: ").append(this.lampSet4DisplayErr).append(", ");
/* 135 */     sb.append("boss1DriveErr: ").append(this.boss1DriveErr).append(", ");
/* 136 */     sb.append("boss2DriveErr: ").append(this.boss2DriveErr).append(", ");
/* 137 */     sb.append("boss3DriveErr: ").append(this.boss3DriveErr).append(", ");
/* 138 */     sb.append("boss4DriveErr: ").append(this.boss4DriveErr).append(", ");
/* 139 */     sb.append("boss1DisplayErr: ").append(this.boss1DisplayErr).append(", ");
/* 140 */     sb.append("boss2DisplayErr: ").append(this.boss2DisplayErr).append(", ");
/* 141 */     sb.append("boss3DisplayErr: ").append(this.boss3DisplayErr).append(", ");
/* 142 */     sb.append("boss4DisplayErr: ").append(this.boss4DisplayErr).append(']');
/* 143 */     return sb.toString();
/*     */   }
/*     */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\fw2\cmdparam\rms\RmsActionResultReportPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */