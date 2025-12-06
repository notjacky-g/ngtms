/*     */ package com.hwacom.ngtms.ncc.tc.fw2.cmdparam.rms;
/*     */ 
/*     */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParam;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInput;
/*     */ import java.io.ObjectOutput;
/*     */ import java.util.List;
/*     */ 
/*     */ @com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParams(cmdName="getExtRmsMonitorRsp")
/*     */ public class GetExtRmsMonitorRspPm implements java.io.Externalizable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   public static final int cmdId = 36743;
/*     */   public static final String cmdName = "getExtRmsMonitorRsp";
/*     */   @CommandParam(name="hwStatus")
/*     */   public com.hwacom.ngtms.ncc.tc.fw2.cmdparam.HwStatusPm hwStatusPm;
/*     */   @CommandParam(name="commState")
/*     */   public int commState;
/*     */   @CommandParam(name="opStatus")
/*     */   public com.hwacom.ngtms.ncc.tc.fw2.cmdparam.OpStatusPm opStatusPm;
/*     */   @CommandParam(name="opMode")
/*     */   public com.hwacom.ngtms.ncc.tc.fw2.cmdparam.OpModePm opModePm;
/*     */   @CommandParam(name="controlMode")
/*     */   public int controlMode;
/*     */   @CommandParam(name="planNo")
/*     */   public int planNo;
/*     */   @CommandParam(name="rate")
/*     */   public int rate;
/*     */   @CommandParam(name="vdqDetectionMode")
/*     */   public int vdqDetectionMode;
/*     */   @CommandParam(name="vdrDetectionMode")
/*     */   public int vdrDetectionMode;
/*     */   @CommandParam(name="lampHwStatusList")
/*     */   public List<LampHwStatusListItem> lampHwStatusList;
/*     */   @CommandParam(name="bosStatusList")
/*     */   public List<BosStatusListItem> bosStatusList;
/*     */   @CommandParam(name="laneCount")
/*     */   public int laneCount;
/*     */   @CommandParam(name="rampMode")
/*     */   public int rampMode;
/*     */   @CommandParam(name="percent1")
/*     */   public int percent1;
/*     */   @CommandParam(name="percent2")
/*     */   public int percent2;
/*     */   @CommandParam(name="ltrAlarm")
/*     */   public int ltrAlarm;
/*     */   
/*     */   public static class LampHwStatusListItem implements java.io.Externalizable
/*     */   {
/*     */     private static final long serialVersionUID = 1L;
/*     */     @CommandParam(name="lampHwStatus")
/*     */     public LampHwStatusPm lampHwStatusPm;
/*     */     
/*     */     public void writeExternal(ObjectOutput out) throws IOException
/*     */     {
/*  56 */       out.writeObject(this.lampHwStatusPm);
/*     */     }
/*     */     
/*     */     public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*     */     {
/*  61 */       this.lampHwStatusPm = ((LampHwStatusPm)in.readObject());
/*     */     }
/*     */     
/*     */     public String toString() {
/*  65 */       StringBuilder sb = new StringBuilder();
/*  66 */       sb.append('[');
/*  67 */       sb.append("lampHwStatusPm: ").append(this.lampHwStatusPm).append(']');
/*  68 */       return sb.toString();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public static class BosStatusListItem
/*     */     implements java.io.Externalizable
/*     */   {
/*     */     private static final long serialVersionUID = 1L;
/*     */     @CommandParam(name="bosStatus")
/*     */     public BosStatusPm bosStatusPm;
/*     */     
/*     */     public void writeExternal(ObjectOutput out)
/*     */       throws IOException
/*     */     {
/*  83 */       out.writeObject(this.bosStatusPm);
/*     */     }
/*     */     
/*     */     public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*     */     {
/*  88 */       this.bosStatusPm = ((BosStatusPm)in.readObject());
/*     */     }
/*     */     
/*     */     public String toString() {
/*  92 */       StringBuilder sb = new StringBuilder();
/*  93 */       sb.append('[');
/*  94 */       sb.append("bosStatusPm: ").append(this.bosStatusPm).append(']');
/*  95 */       return sb.toString();
/*     */     }
/*     */   }
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
/*     */   public void writeExternal(ObjectOutput out)
/*     */     throws IOException
/*     */   {
/* 116 */     out.writeObject(this.hwStatusPm);
/* 117 */     out.writeByte(this.commState);
/* 118 */     out.writeObject(this.opStatusPm);
/* 119 */     out.writeObject(this.opModePm);
/* 120 */     out.writeByte(this.controlMode);
/* 121 */     out.writeByte(this.planNo);
/* 122 */     out.writeShort(this.rate);
/* 123 */     out.writeByte(this.vdqDetectionMode);
/* 124 */     out.writeByte(this.vdrDetectionMode);
/* 125 */     out.writeObject(this.lampHwStatusList);
/* 126 */     out.writeObject(this.bosStatusList);
/* 127 */     out.writeByte(this.laneCount);
/* 128 */     out.writeByte(this.rampMode);
/* 129 */     out.writeByte(this.percent1);
/* 130 */     out.writeByte(this.percent2);
/* 131 */     out.writeByte(this.ltrAlarm);
/*     */   }
/*     */   
/*     */   public void readExternal(ObjectInput in)
/*     */     throws IOException, ClassNotFoundException
/*     */   {
/* 137 */     this.hwStatusPm = ((com.hwacom.ngtms.ncc.tc.fw2.cmdparam.HwStatusPm)in.readObject());
/* 138 */     this.commState = (in.readByte() & 0xFF);
/* 139 */     this.opStatusPm = ((com.hwacom.ngtms.ncc.tc.fw2.cmdparam.OpStatusPm)in.readObject());
/* 140 */     this.opModePm = ((com.hwacom.ngtms.ncc.tc.fw2.cmdparam.OpModePm)in.readObject());
/* 141 */     this.controlMode = (in.readByte() & 0xFF);
/* 142 */     this.planNo = (in.readByte() & 0xFF);
/* 143 */     this.rate = (in.readShort() & 0xFFFF);
/* 144 */     this.vdqDetectionMode = (in.readByte() & 0xFF);
/* 145 */     this.vdrDetectionMode = (in.readByte() & 0xFF);
/* 146 */     this.lampHwStatusList = ((List)in.readObject());
/* 147 */     this.bosStatusList = ((List)in.readObject());
/* 148 */     this.laneCount = (in.readByte() & 0xFF);
/* 149 */     this.rampMode = (in.readByte() & 0xFF);
/* 150 */     this.percent1 = (in.readByte() & 0xFF);
/* 151 */     this.percent2 = (in.readByte() & 0xFF);
/* 152 */     this.ltrAlarm = (in.readByte() & 0xFF);
/*     */   }
/*     */   
/*     */   public String toString() {
/* 156 */     StringBuilder sb = new StringBuilder();
/* 157 */     sb.append('[');
/* 158 */     sb.append("hwStatusPm: ").append(this.hwStatusPm).append(", ");
/* 159 */     sb.append("commState: ").append(this.commState).append(", ");
/* 160 */     sb.append("opStatusPm: ").append(this.opStatusPm).append(", ");
/* 161 */     sb.append("opModePm: ").append(this.opModePm).append(", ");
/* 162 */     sb.append("controlMode: ").append(this.controlMode).append(", ");
/* 163 */     sb.append("planNo: ").append(this.planNo).append(", ");
/* 164 */     sb.append("rate: ").append(this.rate).append(", ");
/* 165 */     sb.append("vdqDetectionMode: ").append(this.vdqDetectionMode).append(", ");
/* 166 */     sb.append("vdrDetectionMode: ").append(this.vdrDetectionMode).append(", ");
/* 167 */     sb.append("lampHwStatusList: ").append(this.lampHwStatusList).append(", ");
/* 168 */     sb.append("bosStatusList: ").append(this.bosStatusList).append(", ");
/* 169 */     sb.append("laneCount: ").append(this.laneCount).append(", ");
/* 170 */     sb.append("rampMode: ").append(this.rampMode).append(", ");
/* 171 */     sb.append("percent1: ").append(this.percent1).append(", ");
/* 172 */     sb.append("percent2: ").append(this.percent2).append(", ");
/* 173 */     sb.append("ltrAlarm: ").append(this.ltrAlarm).append(']');
/* 174 */     return sb.toString();
/*     */   }
/*     */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\fw2\cmdparam\rms\GetExtRmsMonitorRspPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */