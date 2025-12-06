/*     */ package com.hwacom.ngtms.ncc.tc.fw2.cmdparam.tem;
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
/*     */ @CommandParams(cmdName="setTemVdTrafficDataReq")
/*     */ public class SetTemVdTrafficDataReqPm
/*     */   implements Externalizable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   public static final int cmdId = 33;
/*     */   public static final String cmdName = "setTemVdTrafficDataReq";
/*     */   @CommandParam(name="direction")
/*     */   public int direction;
/*     */   @CommandParam(name="mileKilometer")
/*     */   public int mileKilometer;
/*     */   @CommandParam(name="mileMeter")
/*     */   public int mileMeter;
/*     */   @CommandParam(name="laneCount")
/*     */   public int laneCount;
/*     */   @CommandParam(name="odd")
/*     */   public int odd;
/*     */   @CommandParam(name="trafficDataList")
/*     */   public List<TrafficDataListItem> trafficDataList;
/*     */   
/*     */   public static class TrafficDataListItem
/*     */     implements Externalizable
/*     */   {
/*     */     private static final long serialVersionUID = 1L;
/*     */     @CommandParam(name="loopId")
/*     */     public int loopId;
/*     */     @CommandParam(name="dataOdd")
/*     */     public int dataOdd;
/*     */     @CommandParam(name="volumeSmall")
/*     */     public int volumeSmall;
/*     */     @CommandParam(name="volumeBig")
/*     */     public int volumeBig;
/*     */     @CommandParam(name="volumeConnect")
/*     */     public int volumeConnect;
/*     */     @CommandParam(name="speedSmall")
/*     */     public int speedSmall;
/*     */     @CommandParam(name="speedBig")
/*     */     public int speedBig;
/*     */     @CommandParam(name="spseedConnect")
/*     */     public int spseedConnect;
/*     */     @CommandParam(name="occupancy")
/*     */     public int occupancy;
/*     */     
/*     */     public void writeExternal(ObjectOutput out)
/*     */       throws IOException
/*     */     {
/*  67 */       out.writeByte(this.loopId);
/*  68 */       out.writeByte(this.dataOdd);
/*  69 */       out.writeShort(this.volumeSmall);
/*  70 */       out.writeShort(this.volumeBig);
/*  71 */       out.writeShort(this.volumeConnect);
/*  72 */       out.writeByte(this.speedSmall);
/*  73 */       out.writeByte(this.speedBig);
/*  74 */       out.writeByte(this.spseedConnect);
/*  75 */       out.writeByte(this.occupancy);
/*     */     }
/*     */     
/*     */     public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*     */     {
/*  80 */       this.loopId = (in.readByte() & 0xFF);
/*  81 */       this.dataOdd = (in.readByte() & 0xFF);
/*  82 */       this.volumeSmall = (in.readShort() & 0xFFFF);
/*  83 */       this.volumeBig = (in.readShort() & 0xFFFF);
/*  84 */       this.volumeConnect = (in.readShort() & 0xFFFF);
/*  85 */       this.speedSmall = (in.readByte() & 0xFF);
/*  86 */       this.speedBig = (in.readByte() & 0xFF);
/*  87 */       this.spseedConnect = (in.readByte() & 0xFF);
/*  88 */       this.occupancy = (in.readByte() & 0xFF);
/*     */     }
/*     */     
/*     */     public String toString() {
/*  92 */       StringBuilder sb = new StringBuilder();
/*  93 */       sb.append('[');
/*  94 */       sb.append("loopId: ").append(this.loopId).append(", ");
/*  95 */       sb.append("dataOdd: ").append(this.dataOdd).append(", ");
/*  96 */       sb.append("volumeSmall: ").append(this.volumeSmall).append(", ");
/*  97 */       sb.append("volumeBig: ").append(this.volumeBig).append(", ");
/*  98 */       sb.append("volumeConnect: ").append(this.volumeConnect).append(", ");
/*  99 */       sb.append("speedSmall: ").append(this.speedSmall).append(", ");
/* 100 */       sb.append("speedBig: ").append(this.speedBig).append(", ");
/* 101 */       sb.append("spseedConnect: ").append(this.spseedConnect).append(", ");
/* 102 */       sb.append("occupancy: ").append(this.occupancy).append(']');
/* 103 */       return sb.toString();
/*     */     }
/*     */   }
/*     */   
/*     */   public void writeExternal(ObjectOutput out) throws IOException
/*     */   {
/* 109 */     out.writeByte(this.direction);
/* 110 */     out.writeShort(this.mileKilometer);
/* 111 */     out.writeShort(this.mileMeter);
/* 112 */     out.writeByte(this.laneCount);
/* 113 */     out.writeByte(this.odd);
/* 114 */     out.writeObject(this.trafficDataList);
/*     */   }
/*     */   
/*     */   public void readExternal(ObjectInput in)
/*     */     throws IOException, ClassNotFoundException
/*     */   {
/* 120 */     this.direction = (in.readByte() & 0xFF);
/* 121 */     this.mileKilometer = (in.readShort() & 0xFFFF);
/* 122 */     this.mileMeter = (in.readShort() & 0xFFFF);
/* 123 */     this.laneCount = (in.readByte() & 0xFF);
/* 124 */     this.odd = (in.readByte() & 0xFF);
/* 125 */     this.trafficDataList = ((List)in.readObject());
/*     */   }
/*     */   
/*     */   public String toString() {
/* 129 */     StringBuilder sb = new StringBuilder();
/* 130 */     sb.append('[');
/* 131 */     sb.append("direction: ").append(this.direction).append(", ");
/* 132 */     sb.append("mileKilometer: ").append(this.mileKilometer).append(", ");
/* 133 */     sb.append("mileMeter: ").append(this.mileMeter).append(", ");
/* 134 */     sb.append("laneCount: ").append(this.laneCount).append(", ");
/* 135 */     sb.append("odd: ").append(this.odd).append(", ");
/* 136 */     sb.append("trafficDataList: ").append(this.trafficDataList).append(']');
/* 137 */     return sb.toString();
/*     */   }
/*     */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\fw2\cmdparam\tem\SetTemVdTrafficDataReqPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */