/*     */ package com.hwacom.ngtms.ncc.tc.fw2.cmdparam.rms;
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
/*     */ @CommandParams(cmdName="setExtRmsPlanDataReq")
/*     */ public class SetExtRmsPlanDataReqPm
/*     */   implements Externalizable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   public static final int cmdId = 36740;
/*     */   public static final String cmdName = "setExtRmsPlanDataReq";
/*     */   @CommandParam(name="tableId")
/*     */   public int tableId;
/*     */   @CommandParam(name="rateList")
/*     */   public List<RateListItem> rateList;
/*     */   
/*     */   public static class RateListItem
/*     */     implements Externalizable
/*     */   {
/*     */     private static final long serialVersionUID = 1L;
/*     */     @CommandParam(name="planNo")
/*     */     public int planNo;
/*     */     @CommandParam(name="rate")
/*     */     public int rate;
/*     */     @CommandParam(name="timeCycle")
/*     */     public int timeCycle;
/*     */     @CommandParam(name="greenTime")
/*     */     public int greenTime;
/*     */     @CommandParam(name="yellowTime")
/*     */     public int yellowTime;
/*     */     @CommandParam(name="redTime")
/*     */     public int redTime;
/*     */     @CommandParam(name="vehicleNo")
/*     */     public int vehicleNo;
/*     */     
/*     */     public void writeExternal(ObjectOutput out)
/*     */       throws IOException
/*     */     {
/*  50 */       out.writeByte(this.planNo);
/*  51 */       out.writeShort(this.rate);
/*  52 */       out.writeByte(this.timeCycle);
/*  53 */       out.writeByte(this.greenTime);
/*  54 */       out.writeByte(this.yellowTime);
/*  55 */       out.writeByte(this.redTime);
/*  56 */       out.writeByte(this.vehicleNo);
/*     */     }
/*     */     
/*     */     public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*     */     {
/*  61 */       this.planNo = (in.readByte() & 0xFF);
/*  62 */       this.rate = (in.readShort() & 0xFFFF);
/*  63 */       this.timeCycle = (in.readByte() & 0xFF);
/*  64 */       this.greenTime = (in.readByte() & 0xFF);
/*  65 */       this.yellowTime = (in.readByte() & 0xFF);
/*  66 */       this.redTime = (in.readByte() & 0xFF);
/*  67 */       this.vehicleNo = (in.readByte() & 0xFF);
/*     */     }
/*     */     
/*     */     public String toString() {
/*  71 */       StringBuilder sb = new StringBuilder();
/*  72 */       sb.append('[');
/*  73 */       sb.append("planNo: ").append(this.planNo).append(", ");
/*  74 */       sb.append("rate: ").append(this.rate).append(", ");
/*  75 */       sb.append("timeCycle: ").append(this.timeCycle).append(", ");
/*  76 */       sb.append("greenTime: ").append(this.greenTime).append(", ");
/*  77 */       sb.append("yellowTime: ").append(this.yellowTime).append(", ");
/*  78 */       sb.append("redTime: ").append(this.redTime).append(", ");
/*  79 */       sb.append("vehicleNo: ").append(this.vehicleNo).append(']');
/*  80 */       return sb.toString();
/*     */     }
/*     */   }
/*     */   
/*     */   public void writeExternal(ObjectOutput out) throws IOException
/*     */   {
/*  86 */     out.writeByte(this.tableId);
/*  87 */     out.writeObject(this.rateList);
/*     */   }
/*     */   
/*     */   public void readExternal(ObjectInput in)
/*     */     throws IOException, ClassNotFoundException
/*     */   {
/*  93 */     this.tableId = (in.readByte() & 0xFF);
/*  94 */     this.rateList = ((List)in.readObject());
/*     */   }
/*     */   
/*     */   public String toString() {
/*  98 */     StringBuilder sb = new StringBuilder();
/*  99 */     sb.append('[');
/* 100 */     sb.append("tableId: ").append(this.tableId).append(", ");
/* 101 */     sb.append("rateList: ").append(this.rateList).append(']');
/* 102 */     return sb.toString();
/*     */   }
/*     */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\fw2\cmdparam\rms\SetExtRmsPlanDataReqPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */