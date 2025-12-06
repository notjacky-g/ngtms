/*     */ package com.hwacom.ngtms.ncc.tc.fw2.cmdparam.tts;
/*     */ 
/*     */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParam;
/*     */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParams;
/*     */ import com.hwacom.ngtms.ncc.tc.fw2.cmdparam.HwStatusPm;
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
/*     */ @CommandParams(cmdName="getTtsLedTestRsp")
/*     */ public class GetTtsLedTestRspPm
/*     */   implements Externalizable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   public static final int cmdId = 24342;
/*     */   public static final String cmdName = "getTtsLedTestRsp";
/*     */   @CommandParam(name="displayPosition")
/*     */   public int displayPosition;
/*     */   @CommandParam(name="hwStatus")
/*     */   public HwStatusPm hwStatusPm;
/*     */   @CommandParam(name="light1")
/*     */   public int light1;
/*     */   @CommandParam(name="light2")
/*     */   public int light2;
/*     */   @CommandParam(name="light3")
/*     */   public int light3;
/*     */   @CommandParam(name="light4")
/*     */   public int light4;
/*     */   @CommandParam(name="light5")
/*     */   public int light5;
/*     */   @CommandParam(name="light6")
/*     */   public int light6;
/*     */   @CommandParam(name="light7")
/*     */   public int light7;
/*     */   @CommandParam(name="light8")
/*     */   public int light8;
/*     */   @CommandParam(name="badLedList")
/*     */   public List<BadLedListItem> badLedList;
/*     */   
/*     */   public static class BadLedListItem
/*     */     implements Externalizable
/*     */   {
/*     */     private static final long serialVersionUID = 1L;
/*     */     @CommandParam(name="ledX")
/*     */     public int ledX;
/*     */     @CommandParam(name="ledY")
/*     */     public int ledY;
/*     */     
/*     */     public void writeExternal(ObjectOutput out)
/*     */       throws IOException
/*     */     {
/*  62 */       out.writeByte(this.ledX);
/*  63 */       out.writeByte(this.ledY);
/*     */     }
/*     */     
/*     */     public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*     */     {
/*  68 */       this.ledX = (in.readByte() & 0xFF);
/*  69 */       this.ledY = (in.readByte() & 0xFF);
/*     */     }
/*     */     
/*     */     public String toString() {
/*  73 */       StringBuilder sb = new StringBuilder();
/*  74 */       sb.append('[');
/*  75 */       sb.append("ledX: ").append(this.ledX).append(", ");
/*  76 */       sb.append("ledY: ").append(this.ledY).append(']');
/*  77 */       return sb.toString();
/*     */     }
/*     */   }
/*     */   
/*     */   public void writeExternal(ObjectOutput out) throws IOException
/*     */   {
/*  83 */     out.writeByte(this.displayPosition);
/*  84 */     out.writeObject(this.hwStatusPm);
/*  85 */     out.writeByte(this.light1);
/*  86 */     out.writeByte(this.light2);
/*  87 */     out.writeByte(this.light3);
/*  88 */     out.writeByte(this.light4);
/*  89 */     out.writeByte(this.light5);
/*  90 */     out.writeByte(this.light6);
/*  91 */     out.writeByte(this.light7);
/*  92 */     out.writeByte(this.light8);
/*  93 */     out.writeObject(this.badLedList);
/*     */   }
/*     */   
/*     */   public void readExternal(ObjectInput in)
/*     */     throws IOException, ClassNotFoundException
/*     */   {
/*  99 */     this.displayPosition = (in.readByte() & 0xFF);
/* 100 */     this.hwStatusPm = ((HwStatusPm)in.readObject());
/* 101 */     this.light1 = (in.readByte() & 0xFFFF);
/* 102 */     this.light2 = (in.readByte() & 0xFFFF);
/* 103 */     this.light3 = (in.readByte() & 0xFFFF);
/* 104 */     this.light4 = (in.readByte() & 0xFFFF);
/* 105 */     this.light5 = (in.readByte() & 0xFFFF);
/* 106 */     this.light6 = (in.readByte() & 0xFFFF);
/* 107 */     this.light7 = (in.readByte() & 0xFFFF);
/* 108 */     this.light8 = (in.readByte() & 0xFFFF);
/* 109 */     this.badLedList = ((List)in.readObject());
/*     */   }
/*     */   
/*     */   public String toString() {
/* 113 */     StringBuilder sb = new StringBuilder();
/* 114 */     sb.append('[');
/* 115 */     sb.append("displayPosition: ").append(this.displayPosition).append(", ");
/* 116 */     sb.append("hwStatusPm: ").append(this.hwStatusPm).append(", ");
/* 117 */     sb.append("light1: ").append(this.light1).append(", ");
/* 118 */     sb.append("light2: ").append(this.light2).append(", ");
/* 119 */     sb.append("light3: ").append(this.light3).append(", ");
/* 120 */     sb.append("light4: ").append(this.light4).append(", ");
/* 121 */     sb.append("light5: ").append(this.light5).append(", ");
/* 122 */     sb.append("light6: ").append(this.light6).append(", ");
/* 123 */     sb.append("light7: ").append(this.light7).append(", ");
/* 124 */     sb.append("light8: ").append(this.light8).append(", ");
/* 125 */     sb.append("badLedList: ").append(this.badLedList).append(']');
/* 126 */     return sb.toString();
/*     */   }
/*     */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\fw2\cmdparam\tts\GetTtsLedTestRspPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */