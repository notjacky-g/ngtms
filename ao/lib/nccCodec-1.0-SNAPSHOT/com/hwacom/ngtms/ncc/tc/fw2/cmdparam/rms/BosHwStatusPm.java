/*     */ package com.hwacom.ngtms.ncc.tc.fw2.cmdparam.rms;
/*     */ 
/*     */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParam;
/*     */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.GlobalParams;
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
/*     */ @GlobalParams(paramsName="bosHwStatus")
/*     */ public class BosHwStatusPm
/*     */   implements Externalizable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   @CommandParam(name="bos1word1DriveErr")
/*     */   public int bos1word1DriveErr;
/*     */   @CommandParam(name="bos1word2DriveErr")
/*     */   public int bos1word2DriveErr;
/*     */   @CommandParam(name="bos1word3DriveErr")
/*     */   public int bos1word3DriveErr;
/*     */   @CommandParam(name="bos1word4DriveErr")
/*     */   public int bos1word4DriveErr;
/*     */   @CommandParam(name="bos1word5DriveErr")
/*     */   public int bos1word5DriveErr;
/*     */   @CommandParam(name="bos1word6DriveErr")
/*     */   public int bos1word6DriveErr;
/*     */   @CommandParam(name="bos1word7DriveErr")
/*     */   public int bos1word7DriveErr;
/*     */   @CommandParam(name="bos1word8DriveErr")
/*     */   public int bos1word8DriveErr;
/*     */   @CommandParam(name="bos2word1DriveErr")
/*     */   public int bos2word1DriveErr;
/*     */   @CommandParam(name="bos2word2DriveErr")
/*     */   public int bos2word2DriveErr;
/*     */   @CommandParam(name="bos2word3DriveErr")
/*     */   public int bos2word3DriveErr;
/*     */   @CommandParam(name="bos2word4DriveErr")
/*     */   public int bos2word4DriveErr;
/*     */   @CommandParam(name="bos2word5DriveErr")
/*     */   public int bos2word5DriveErr;
/*     */   @CommandParam(name="bos2word6DriveErr")
/*     */   public int bos2word6DriveErr;
/*     */   @CommandParam(name="bos2word7DriveErr")
/*     */   public int bos2word7DriveErr;
/*     */   @CommandParam(name="bos2word8DriveErr")
/*     */   public int bos2word8DriveErr;
/*     */   
/*     */   public void writeExternal(ObjectOutput out)
/*     */     throws IOException
/*     */   {
/*  66 */     out.writeByte(this.bos1word1DriveErr);
/*  67 */     out.writeByte(this.bos1word2DriveErr);
/*  68 */     out.writeByte(this.bos1word3DriveErr);
/*  69 */     out.writeByte(this.bos1word4DriveErr);
/*  70 */     out.writeByte(this.bos1word5DriveErr);
/*  71 */     out.writeByte(this.bos1word6DriveErr);
/*  72 */     out.writeByte(this.bos1word7DriveErr);
/*  73 */     out.writeByte(this.bos1word8DriveErr);
/*  74 */     out.writeByte(this.bos2word1DriveErr);
/*  75 */     out.writeByte(this.bos2word2DriveErr);
/*  76 */     out.writeByte(this.bos2word3DriveErr);
/*  77 */     out.writeByte(this.bos2word4DriveErr);
/*  78 */     out.writeByte(this.bos2word5DriveErr);
/*  79 */     out.writeByte(this.bos2word6DriveErr);
/*  80 */     out.writeByte(this.bos2word7DriveErr);
/*  81 */     out.writeByte(this.bos2word8DriveErr);
/*     */   }
/*     */   
/*     */   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*     */   {
/*  86 */     this.bos1word1DriveErr = (in.readByte() & 0xFFFF);
/*  87 */     this.bos1word2DriveErr = (in.readByte() & 0xFFFF);
/*  88 */     this.bos1word3DriveErr = (in.readByte() & 0xFFFF);
/*  89 */     this.bos1word4DriveErr = (in.readByte() & 0xFFFF);
/*  90 */     this.bos1word5DriveErr = (in.readByte() & 0xFFFF);
/*  91 */     this.bos1word6DriveErr = (in.readByte() & 0xFFFF);
/*  92 */     this.bos1word7DriveErr = (in.readByte() & 0xFFFF);
/*  93 */     this.bos1word8DriveErr = (in.readByte() & 0xFFFF);
/*  94 */     this.bos2word1DriveErr = (in.readByte() & 0xFFFF);
/*  95 */     this.bos2word2DriveErr = (in.readByte() & 0xFFFF);
/*  96 */     this.bos2word3DriveErr = (in.readByte() & 0xFFFF);
/*  97 */     this.bos2word4DriveErr = (in.readByte() & 0xFFFF);
/*  98 */     this.bos2word5DriveErr = (in.readByte() & 0xFFFF);
/*  99 */     this.bos2word6DriveErr = (in.readByte() & 0xFFFF);
/* 100 */     this.bos2word7DriveErr = (in.readByte() & 0xFFFF);
/* 101 */     this.bos2word8DriveErr = (in.readByte() & 0xFFFF);
/*     */   }
/*     */   
/*     */   public String toString() {
/* 105 */     StringBuilder sb = new StringBuilder();
/* 106 */     sb.append('[');
/* 107 */     sb.append("bos1word1DriveErr: ").append(this.bos1word1DriveErr).append(", ");
/* 108 */     sb.append("bos1word2DriveErr: ").append(this.bos1word2DriveErr).append(", ");
/* 109 */     sb.append("bos1word3DriveErr: ").append(this.bos1word3DriveErr).append(", ");
/* 110 */     sb.append("bos1word4DriveErr: ").append(this.bos1word4DriveErr).append(", ");
/* 111 */     sb.append("bos1word5DriveErr: ").append(this.bos1word5DriveErr).append(", ");
/* 112 */     sb.append("bos1word6DriveErr: ").append(this.bos1word6DriveErr).append(", ");
/* 113 */     sb.append("bos1word7DriveErr: ").append(this.bos1word7DriveErr).append(", ");
/* 114 */     sb.append("bos1word8DriveErr: ").append(this.bos1word8DriveErr).append(", ");
/* 115 */     sb.append("bos2word1DriveErr: ").append(this.bos2word1DriveErr).append(", ");
/* 116 */     sb.append("bos2word2DriveErr: ").append(this.bos2word2DriveErr).append(", ");
/* 117 */     sb.append("bos2word3DriveErr: ").append(this.bos2word3DriveErr).append(", ");
/* 118 */     sb.append("bos2word4DriveErr: ").append(this.bos2word4DriveErr).append(", ");
/* 119 */     sb.append("bos2word5DriveErr: ").append(this.bos2word5DriveErr).append(", ");
/* 120 */     sb.append("bos2word6DriveErr: ").append(this.bos2word6DriveErr).append(", ");
/* 121 */     sb.append("bos2word7DriveErr: ").append(this.bos2word7DriveErr).append(", ");
/* 122 */     sb.append("bos2word8DriveErr: ").append(this.bos2word8DriveErr).append(']');
/* 123 */     return sb.toString();
/*     */   }
/*     */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\fw2\cmdparam\rms\BosHwStatusPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */