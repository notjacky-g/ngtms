/*     */ package com.hwacom.ngtms.ncc.tc.fw2.cmdparam.rms;
/*     */ 
/*     */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParam;
/*     */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParams;
/*     */ import com.hwacom.ngtms.ncc.tc.fw2.cmdparam.HwStatusPm;
/*     */ import com.hwacom.ngtms.ncc.tc.fw2.cmdparam.OpModePm;
/*     */ import com.hwacom.ngtms.ncc.tc.fw2.cmdparam.OpStatusPm;
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
/*     */ @CommandParams(cmdName="getRmsMonitorRsp")
/*     */ public class GetRmsMonitorRspPm
/*     */   implements Externalizable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   public static final int cmdId = 135;
/*     */   public static final String cmdName = "getRmsMonitorRsp";
/*     */   @CommandParam(name="hwStatus")
/*     */   public HwStatusPm hwStatusPm;
/*     */   @CommandParam(name="commState")
/*     */   public int commState;
/*     */   @CommandParam(name="opStatus")
/*     */   public OpStatusPm opStatusPm;
/*     */   @CommandParam(name="opMode")
/*     */   public OpModePm opModePm;
/*     */   @CommandParam(name="controlMode")
/*     */   public int controlMode;
/*     */   @CommandParam(name="planNo")
/*     */   public int planNo;
/*     */   @CommandParam(name="rate")
/*     */   public int rate;
/*     */   @CommandParam(name="rmsHwStatusList")
/*     */   public List<RmsHwStatusListItem> rmsHwStatusList;
/*     */   
/*     */   public static class RmsHwStatusListItem
/*     */     implements Externalizable
/*     */   {
/*     */     private static final long serialVersionUID = 1L;
/*     */     @CommandParam(name="lampHwStatus")
/*     */     public LampHwStatusPm lampHwStatusPm;
/*     */     @CommandParam(name="bosHwStatus")
/*     */     public BosHwStatusPm bosHwStatusPm;
/*     */     @CommandParam(name="warnMessageId")
/*     */     public int warnMessageId;
/*     */     
/*     */     public void writeExternal(ObjectOutput out)
/*     */       throws IOException
/*     */     {
/*  56 */       out.writeObject(this.lampHwStatusPm);
/*  57 */       out.writeObject(this.bosHwStatusPm);
/*  58 */       out.writeByte(this.warnMessageId);
/*     */     }
/*     */     
/*     */     public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*     */     {
/*  63 */       this.lampHwStatusPm = ((LampHwStatusPm)in.readObject());
/*  64 */       this.bosHwStatusPm = ((BosHwStatusPm)in.readObject());
/*  65 */       this.warnMessageId = (in.readByte() & 0xFF);
/*     */     }
/*     */     
/*     */     public String toString() {
/*  69 */       StringBuilder sb = new StringBuilder();
/*  70 */       sb.append('[');
/*  71 */       sb.append("lampHwStatusPm: ").append(this.lampHwStatusPm).append(", ");
/*  72 */       sb.append("bosHwStatusPm: ").append(this.bosHwStatusPm).append(", ");
/*  73 */       sb.append("warnMessageId: ").append(this.warnMessageId).append(']');
/*  74 */       return sb.toString();
/*     */     }
/*     */   }
/*     */   
/*     */   public void writeExternal(ObjectOutput out) throws IOException
/*     */   {
/*  80 */     out.writeObject(this.hwStatusPm);
/*  81 */     out.writeByte(this.commState);
/*  82 */     out.writeObject(this.opStatusPm);
/*  83 */     out.writeObject(this.opModePm);
/*  84 */     out.writeByte(this.controlMode);
/*  85 */     out.writeByte(this.planNo);
/*  86 */     out.writeByte(this.rate);
/*  87 */     out.writeObject(this.rmsHwStatusList);
/*     */   }
/*     */   
/*     */   public void readExternal(ObjectInput in)
/*     */     throws IOException, ClassNotFoundException
/*     */   {
/*  93 */     this.hwStatusPm = ((HwStatusPm)in.readObject());
/*  94 */     this.commState = (in.readByte() & 0xFF);
/*  95 */     this.opStatusPm = ((OpStatusPm)in.readObject());
/*  96 */     this.opModePm = ((OpModePm)in.readObject());
/*  97 */     this.controlMode = (in.readByte() & 0xFF);
/*  98 */     this.planNo = (in.readByte() & 0xFF);
/*  99 */     this.rate = (in.readByte() & 0xFF);
/* 100 */     this.rmsHwStatusList = ((List)in.readObject());
/*     */   }
/*     */   
/*     */   public String toString() {
/* 104 */     StringBuilder sb = new StringBuilder();
/* 105 */     sb.append('[');
/* 106 */     sb.append("hwStatusPm: ").append(this.hwStatusPm).append(", ");
/* 107 */     sb.append("commState: ").append(this.commState).append(", ");
/* 108 */     sb.append("opStatusPm: ").append(this.opStatusPm).append(", ");
/* 109 */     sb.append("opModePm: ").append(this.opModePm).append(", ");
/* 110 */     sb.append("controlMode: ").append(this.controlMode).append(", ");
/* 111 */     sb.append("planNo: ").append(this.planNo).append(", ");
/* 112 */     sb.append("rate: ").append(this.rate).append(", ");
/* 113 */     sb.append("rmsHwStatusList: ").append(this.rmsHwStatusList).append(']');
/* 114 */     return sb.toString();
/*     */   }
/*     */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\fw2\cmdparam\rms\GetRmsMonitorRspPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */