/*     */ package com.hwacom.ngtms.ncc.tc.fw2.cmdparam.lcs;
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
/*     */ @CommandParams(cmdName="lcsSignConflictReport")
/*     */ public class LcsSignConflictReportPm
/*     */   implements Externalizable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   public static final int cmdId = 198;
/*     */   public static final String cmdName = "lcsSignConflictReport";
/*     */   @CommandParam(name="lane1OfGantry0")
/*     */   public int lane1OfGantry0;
/*     */   @CommandParam(name="lane2OfGantry0")
/*     */   public int lane2OfGantry0;
/*     */   @CommandParam(name="lane3OfGantry0")
/*     */   public int lane3OfGantry0;
/*     */   @CommandParam(name="lane4OfGantry0")
/*     */   public int lane4OfGantry0;
/*     */   @CommandParam(name="lane5OfGantry0")
/*     */   public int lane5OfGantry0;
/*     */   @CommandParam(name="lane6OfGantry0")
/*     */   public int lane6OfGantry0;
/*     */   @CommandParam(name="lane7OfGantry0")
/*     */   public int lane7OfGantry0;
/*     */   @CommandParam(name="lane8OfGantry0")
/*     */   public int lane8OfGantry0;
/*     */   @CommandParam(name="lane1OfGantry1")
/*     */   public int lane1OfGantry1;
/*     */   @CommandParam(name="lane2OfGantry1")
/*     */   public int lane2OfGantry1;
/*     */   @CommandParam(name="lane3OfGantry1")
/*     */   public int lane3OfGantry1;
/*     */   @CommandParam(name="lane4OfGantry1")
/*     */   public int lane4OfGantry1;
/*     */   @CommandParam(name="lane5OfGantry1")
/*     */   public int lane5OfGantry1;
/*     */   @CommandParam(name="lane6OfGantry1")
/*     */   public int lane6OfGantry1;
/*     */   @CommandParam(name="lane7OfGantry1")
/*     */   public int lane7OfGantry1;
/*     */   @CommandParam(name="lane8OfGantry1")
/*     */   public int lane8OfGantry1;
/*     */   
/*     */   public void writeExternal(ObjectOutput out)
/*     */     throws IOException
/*     */   {
/*  68 */     out.writeByte(this.lane1OfGantry0);
/*  69 */     out.writeByte(this.lane2OfGantry0);
/*  70 */     out.writeByte(this.lane3OfGantry0);
/*  71 */     out.writeByte(this.lane4OfGantry0);
/*  72 */     out.writeByte(this.lane5OfGantry0);
/*  73 */     out.writeByte(this.lane6OfGantry0);
/*  74 */     out.writeByte(this.lane7OfGantry0);
/*  75 */     out.writeByte(this.lane8OfGantry0);
/*  76 */     out.writeByte(this.lane1OfGantry1);
/*  77 */     out.writeByte(this.lane2OfGantry1);
/*  78 */     out.writeByte(this.lane3OfGantry1);
/*  79 */     out.writeByte(this.lane4OfGantry1);
/*  80 */     out.writeByte(this.lane5OfGantry1);
/*  81 */     out.writeByte(this.lane6OfGantry1);
/*  82 */     out.writeByte(this.lane7OfGantry1);
/*  83 */     out.writeByte(this.lane8OfGantry1);
/*     */   }
/*     */   
/*     */   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*     */   {
/*  88 */     this.lane1OfGantry0 = (in.readByte() & 0xFFFF);
/*  89 */     this.lane2OfGantry0 = (in.readByte() & 0xFFFF);
/*  90 */     this.lane3OfGantry0 = (in.readByte() & 0xFFFF);
/*  91 */     this.lane4OfGantry0 = (in.readByte() & 0xFFFF);
/*  92 */     this.lane5OfGantry0 = (in.readByte() & 0xFFFF);
/*  93 */     this.lane6OfGantry0 = (in.readByte() & 0xFFFF);
/*  94 */     this.lane7OfGantry0 = (in.readByte() & 0xFFFF);
/*  95 */     this.lane8OfGantry0 = (in.readByte() & 0xFFFF);
/*  96 */     this.lane1OfGantry1 = (in.readByte() & 0xFFFF);
/*  97 */     this.lane2OfGantry1 = (in.readByte() & 0xFFFF);
/*  98 */     this.lane3OfGantry1 = (in.readByte() & 0xFFFF);
/*  99 */     this.lane4OfGantry1 = (in.readByte() & 0xFFFF);
/* 100 */     this.lane5OfGantry1 = (in.readByte() & 0xFFFF);
/* 101 */     this.lane6OfGantry1 = (in.readByte() & 0xFFFF);
/* 102 */     this.lane7OfGantry1 = (in.readByte() & 0xFFFF);
/* 103 */     this.lane8OfGantry1 = (in.readByte() & 0xFFFF);
/*     */   }
/*     */   
/*     */   public String toString() {
/* 107 */     StringBuilder sb = new StringBuilder();
/* 108 */     sb.append('[');
/* 109 */     sb.append("lane1OfGantry0: ").append(this.lane1OfGantry0).append(", ");
/* 110 */     sb.append("lane2OfGantry0: ").append(this.lane2OfGantry0).append(", ");
/* 111 */     sb.append("lane3OfGantry0: ").append(this.lane3OfGantry0).append(", ");
/* 112 */     sb.append("lane4OfGantry0: ").append(this.lane4OfGantry0).append(", ");
/* 113 */     sb.append("lane5OfGantry0: ").append(this.lane5OfGantry0).append(", ");
/* 114 */     sb.append("lane6OfGantry0: ").append(this.lane6OfGantry0).append(", ");
/* 115 */     sb.append("lane7OfGantry0: ").append(this.lane7OfGantry0).append(", ");
/* 116 */     sb.append("lane8OfGantry0: ").append(this.lane8OfGantry0).append(", ");
/* 117 */     sb.append("lane1OfGantry1: ").append(this.lane1OfGantry1).append(", ");
/* 118 */     sb.append("lane2OfGantry1: ").append(this.lane2OfGantry1).append(", ");
/* 119 */     sb.append("lane3OfGantry1: ").append(this.lane3OfGantry1).append(", ");
/* 120 */     sb.append("lane4OfGantry1: ").append(this.lane4OfGantry1).append(", ");
/* 121 */     sb.append("lane5OfGantry1: ").append(this.lane5OfGantry1).append(", ");
/* 122 */     sb.append("lane6OfGantry1: ").append(this.lane6OfGantry1).append(", ");
/* 123 */     sb.append("lane7OfGantry1: ").append(this.lane7OfGantry1).append(", ");
/* 124 */     sb.append("lane8OfGantry1: ").append(this.lane8OfGantry1).append(']');
/* 125 */     return sb.toString();
/*     */   }
/*     */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\fw2\cmdparam\lcs\LcsSignConflictReportPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */