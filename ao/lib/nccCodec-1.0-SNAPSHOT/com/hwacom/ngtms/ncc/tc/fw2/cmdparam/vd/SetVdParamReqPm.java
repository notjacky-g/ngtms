/*     */ package com.hwacom.ngtms.ncc.tc.fw2.cmdparam.vd;
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
/*     */ @CommandParams(cmdName="setVdParamReq")
/*     */ public class SetVdParamReqPm
/*     */   implements Externalizable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   public static final int cmdId = 22;
/*     */   public static final String cmdName = "setVdParamReq";
/*     */   @CommandParam(name="length")
/*     */   public int length;
/*     */   @CommandParam(name="loopDistance1")
/*     */   public int loopDistance1;
/*     */   @CommandParam(name="loopDistance2")
/*     */   public int loopDistance2;
/*     */   @CommandParam(name="loopDistance3")
/*     */   public int loopDistance3;
/*     */   @CommandParam(name="loopDistance4")
/*     */   public int loopDistance4;
/*     */   @CommandParam(name="loopDistance5")
/*     */   public int loopDistance5;
/*     */   @CommandParam(name="loopDistance6")
/*     */   public int loopDistance6;
/*     */   @CommandParam(name="loop1")
/*     */   public int loop1;
/*     */   @CommandParam(name="loop2")
/*     */   public int loop2;
/*     */   @CommandParam(name="loop3")
/*     */   public int loop3;
/*     */   @CommandParam(name="loop4")
/*     */   public int loop4;
/*     */   @CommandParam(name="loop5")
/*     */   public int loop5;
/*     */   @CommandParam(name="loop6")
/*     */   public int loop6;
/*     */   @CommandParam(name="smallCarMaxLength")
/*     */   public int smallCarMaxLength;
/*     */   @CommandParam(name="bigCarMaxLength")
/*     */   public int bigCarMaxLength;
/*     */   @CommandParam(name="carMinLength")
/*     */   public int carMinLength;
/*     */   @CommandParam(name="carMaxLength")
/*     */   public int carMaxLength;
/*     */   @CommandParam(name="carMaxSpeed")
/*     */   public int carMaxSpeed;
/*     */   @CommandParam(name="laneCount")
/*     */   public int laneCount;
/*     */   @CommandParam(name="delayConst")
/*     */   public int delayConst;
/*     */   
/*     */   public void writeExternal(ObjectOutput out)
/*     */     throws IOException
/*     */   {
/*  80 */     out.writeByte(this.length);
/*  81 */     out.writeShort(this.loopDistance1);
/*  82 */     out.writeShort(this.loopDistance2);
/*  83 */     out.writeShort(this.loopDistance3);
/*  84 */     out.writeShort(this.loopDistance4);
/*  85 */     out.writeShort(this.loopDistance5);
/*  86 */     out.writeShort(this.loopDistance6);
/*  87 */     out.writeShort(this.loop1);
/*  88 */     out.writeShort(this.loop2);
/*  89 */     out.writeShort(this.loop3);
/*  90 */     out.writeShort(this.loop4);
/*  91 */     out.writeShort(this.loop5);
/*  92 */     out.writeShort(this.loop6);
/*  93 */     out.writeShort(this.smallCarMaxLength);
/*  94 */     out.writeShort(this.bigCarMaxLength);
/*  95 */     out.writeShort(this.carMinLength);
/*  96 */     out.writeShort(this.carMaxLength);
/*  97 */     out.writeShort(this.carMaxSpeed);
/*  98 */     out.writeByte(this.laneCount);
/*  99 */     out.writeByte(this.delayConst);
/*     */   }
/*     */   
/*     */   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*     */   {
/* 104 */     this.length = (in.readByte() & 0xFF);
/* 105 */     this.loopDistance1 = (in.readShort() & 0xFFFF);
/* 106 */     this.loopDistance2 = (in.readShort() & 0xFFFF);
/* 107 */     this.loopDistance3 = (in.readShort() & 0xFFFF);
/* 108 */     this.loopDistance4 = (in.readShort() & 0xFFFF);
/* 109 */     this.loopDistance5 = (in.readShort() & 0xFFFF);
/* 110 */     this.loopDistance6 = (in.readShort() & 0xFFFF);
/* 111 */     this.loop1 = (in.readShort() & 0xFFFF);
/* 112 */     this.loop2 = (in.readShort() & 0xFFFF);
/* 113 */     this.loop3 = (in.readShort() & 0xFFFF);
/* 114 */     this.loop4 = (in.readShort() & 0xFFFF);
/* 115 */     this.loop5 = (in.readShort() & 0xFFFF);
/* 116 */     this.loop6 = (in.readShort() & 0xFFFF);
/* 117 */     this.smallCarMaxLength = (in.readShort() & 0xFFFF);
/* 118 */     this.bigCarMaxLength = (in.readShort() & 0xFFFF);
/* 119 */     this.carMinLength = (in.readShort() & 0xFFFF);
/* 120 */     this.carMaxLength = (in.readShort() & 0xFFFF);
/* 121 */     this.carMaxSpeed = (in.readShort() & 0xFFFF);
/* 122 */     this.laneCount = (in.readByte() & 0xFF);
/* 123 */     this.delayConst = (in.readByte() & 0xFF);
/*     */   }
/*     */   
/*     */   public String toString() {
/* 127 */     StringBuilder sb = new StringBuilder();
/* 128 */     sb.append('[');
/* 129 */     sb.append("length: ").append(this.length).append(", ");
/* 130 */     sb.append("loopDistance1: ").append(this.loopDistance1).append(", ");
/* 131 */     sb.append("loopDistance2: ").append(this.loopDistance2).append(", ");
/* 132 */     sb.append("loopDistance3: ").append(this.loopDistance3).append(", ");
/* 133 */     sb.append("loopDistance4: ").append(this.loopDistance4).append(", ");
/* 134 */     sb.append("loopDistance5: ").append(this.loopDistance5).append(", ");
/* 135 */     sb.append("loopDistance6: ").append(this.loopDistance6).append(", ");
/* 136 */     sb.append("loop1: ").append(this.loop1).append(", ");
/* 137 */     sb.append("loop2: ").append(this.loop2).append(", ");
/* 138 */     sb.append("loop3: ").append(this.loop3).append(", ");
/* 139 */     sb.append("loop4: ").append(this.loop4).append(", ");
/* 140 */     sb.append("loop5: ").append(this.loop5).append(", ");
/* 141 */     sb.append("loop6: ").append(this.loop6).append(", ");
/* 142 */     sb.append("smallCarMaxLength: ").append(this.smallCarMaxLength).append(", ");
/* 143 */     sb.append("bigCarMaxLength: ").append(this.bigCarMaxLength).append(", ");
/* 144 */     sb.append("carMinLength: ").append(this.carMinLength).append(", ");
/* 145 */     sb.append("carMaxLength: ").append(this.carMaxLength).append(", ");
/* 146 */     sb.append("carMaxSpeed: ").append(this.carMaxSpeed).append(", ");
/* 147 */     sb.append("laneCount: ").append(this.laneCount).append(", ");
/* 148 */     sb.append("delayConst: ").append(this.delayConst).append(']');
/* 149 */     return sb.toString();
/*     */   }
/*     */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\fw2\cmdparam\vd\SetVdParamReqPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */