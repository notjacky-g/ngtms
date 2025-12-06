/*     */ package com.hwacom.ngtms.ncc.tc.fw2.cmdparam.vd.r21;
/*     */ 
/*     */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParam;
/*     */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParams;
/*     */ import com.hwacom.ngtms.ncc.tc.fw2.cmdparam.DhmsPm;
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
/*     */ @CommandParams(cmdName="r21SpotSpeedDataReport")
/*     */ public class R21SpotSpeedDataReportPm
/*     */   implements Externalizable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   public static final int cmdId = 24;
/*     */   public static final String cmdName = "r21SpotSpeedDataReport";
/*     */   @CommandParam(name="responseType")
/*     */   public int responseType;
/*     */   @CommandParam(name="hwStatus")
/*     */   public HwStatusPm hwStatusPm;
/*     */   @CommandParam(name="laneId")
/*     */   public int laneId;
/*     */   @CommandParam(name="spotSpeedDataList")
/*     */   public List<SpotSpeedDataListItem> spotSpeedDataList;
/*     */   
/*     */   public static class SpotSpeedDataListItem
/*     */     implements Externalizable
/*     */   {
/*     */     private static final long serialVersionUID = 1L;
/*     */     @CommandParam(name="dhms")
/*     */     public DhmsPm dhmsPm;
/*     */     @CommandParam(name="carSpeed")
/*     */     public int carSpeed;
/*     */     @CommandParam(name="carLength")
/*     */     public int carLength;
/*     */     @CommandParam(name="carInterval")
/*     */     public int carInterval;
/*     */     @CommandParam(name="carClass")
/*     */     public int carClass;
/*     */     
/*     */     public void writeExternal(ObjectOutput out)
/*     */       throws IOException
/*     */     {
/*  51 */       out.writeObject(this.dhmsPm);
/*  52 */       out.writeByte(this.carSpeed);
/*  53 */       out.writeByte(this.carLength);
/*  54 */       out.writeShort(this.carInterval);
/*  55 */       out.writeByte(this.carClass);
/*     */     }
/*     */     
/*     */     public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*     */     {
/*  60 */       this.dhmsPm = ((DhmsPm)in.readObject());
/*  61 */       this.carSpeed = (in.readByte() & 0xFF);
/*  62 */       this.carLength = (in.readByte() & 0xFF);
/*  63 */       this.carInterval = (in.readShort() & 0xFFFF);
/*  64 */       this.carClass = (in.readByte() & 0xFF);
/*     */     }
/*     */     
/*     */     public String toString() {
/*  68 */       StringBuilder sb = new StringBuilder();
/*  69 */       sb.append('[');
/*  70 */       sb.append("dhmsPm: ").append(this.dhmsPm).append(", ");
/*  71 */       sb.append("carSpeed: ").append(this.carSpeed).append(", ");
/*  72 */       sb.append("carLength: ").append(this.carLength).append(", ");
/*  73 */       sb.append("carInterval: ").append(this.carInterval).append(", ");
/*  74 */       sb.append("carClass: ").append(this.carClass).append(']');
/*  75 */       return sb.toString();
/*     */     }
/*     */   }
/*     */   
/*     */   public void writeExternal(ObjectOutput out) throws IOException
/*     */   {
/*  81 */     out.writeByte(this.responseType);
/*  82 */     out.writeObject(this.hwStatusPm);
/*  83 */     out.writeByte(this.laneId);
/*  84 */     out.writeObject(this.spotSpeedDataList);
/*     */   }
/*     */   
/*     */   public void readExternal(ObjectInput in)
/*     */     throws IOException, ClassNotFoundException
/*     */   {
/*  90 */     this.responseType = (in.readByte() & 0xFF);
/*  91 */     this.hwStatusPm = ((HwStatusPm)in.readObject());
/*  92 */     this.laneId = (in.readByte() & 0xFF);
/*  93 */     this.spotSpeedDataList = ((List)in.readObject());
/*     */   }
/*     */   
/*     */   public String toString() {
/*  97 */     StringBuilder sb = new StringBuilder();
/*  98 */     sb.append('[');
/*  99 */     sb.append("responseType: ").append(this.responseType).append(", ");
/* 100 */     sb.append("hwStatusPm: ").append(this.hwStatusPm).append(", ");
/* 101 */     sb.append("laneId: ").append(this.laneId).append(", ");
/* 102 */     sb.append("spotSpeedDataList: ").append(this.spotSpeedDataList).append(']');
/* 103 */     return sb.toString();
/*     */   }
/*     */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\fw2\cmdparam\vd\r21\R21SpotSpeedDataReportPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */