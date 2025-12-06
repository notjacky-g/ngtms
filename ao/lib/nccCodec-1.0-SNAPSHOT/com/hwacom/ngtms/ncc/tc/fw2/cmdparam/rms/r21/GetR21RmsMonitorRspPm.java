/*     */ package com.hwacom.ngtms.ncc.tc.fw2.cmdparam.rms.r21;
/*     */ 
/*     */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParam;
/*     */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParams;
/*     */ import com.hwacom.ngtms.ncc.tc.fw2.cmdparam.HwStatusPm;
/*     */ import com.hwacom.ngtms.ncc.tc.fw2.cmdparam.OpModePm;
/*     */ import com.hwacom.ngtms.ncc.tc.fw2.cmdparam.OpStatusPm;
/*     */ import com.hwacom.ngtms.ncc.tc.fw2.cmdparam.rms.LampHwStatusPm;
/*     */ import java.io.Externalizable;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInput;
/*     */ import java.io.ObjectOutput;
/*     */ import java.util.List;
/*     */ 
/*     */ @CommandParams(cmdName="getR21RmsMonitorRsp")
/*     */ public class GetR21RmsMonitorRspPm
/*     */   implements Externalizable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   public static final int cmdId = 135;
/*     */   public static final String cmdName = "getR21RmsMonitorRsp";
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
/*     */   @CommandParam(name="lampHwStatusList")
/*     */   public List<LampHwStatusListItem> lampHwStatusList;
/*     */   @CommandParam(name="warnSetList")
/*     */   public List<WarnSetListItem> warnSetList;
/*     */   
/*     */   public static class LampHwStatusListItem
/*     */     implements Externalizable
/*     */   {
/*     */     private static final long serialVersionUID = 1L;
/*     */     @CommandParam(name="lampHwStatus")
/*     */     public LampHwStatusPm lampHwStatusPm;
/*     */     
/*     */     public void writeExternal(ObjectOutput out)
/*     */       throws IOException
/*     */     {
/*  51 */       out.writeObject(this.lampHwStatusPm);
/*     */     }
/*     */     
/*     */     public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*     */     {
/*  56 */       this.lampHwStatusPm = ((LampHwStatusPm)in.readObject());
/*     */     }
/*     */     
/*     */     public String toString() {
/*  60 */       StringBuilder sb = new StringBuilder();
/*  61 */       sb.append('[');
/*  62 */       sb.append("lampHwStatusPm: ").append(this.lampHwStatusPm).append(']');
/*  63 */       return sb.toString();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public static class WarnSetListItem
/*     */     implements Externalizable
/*     */   {
/*     */     private static final long serialVersionUID = 1L;
/*     */     
/*     */     @CommandParam(name="warnSet")
/*     */     public int warnSet;
/*     */     
/*     */     @CommandParam(name="bos1word1DriveErr")
/*     */     public int bos1word1DriveErr;
/*     */     
/*     */     @CommandParam(name="bos1word2DriveErr")
/*     */     public int bos1word2DriveErr;
/*     */     
/*     */     @CommandParam(name="bos1word3DriveErr")
/*     */     public int bos1word3DriveErr;
/*     */     
/*     */     @CommandParam(name="bos1word4DriveErr")
/*     */     public int bos1word4DriveErr;
/*     */     
/*     */     @CommandParam(name="bos1word5DriveErr")
/*     */     public int bos1word5DriveErr;
/*     */     
/*     */     @CommandParam(name="bos1word6DriveErr")
/*     */     public int bos1word6DriveErr;
/*     */     
/*     */     @CommandParam(name="bos1word7DriveErr")
/*     */     public int bos1word7DriveErr;
/*     */     
/*     */     @CommandParam(name="bos1word8DriveErr")
/*     */     public int bos1word8DriveErr;
/*     */     @CommandParam(name="warnMessageId")
/*     */     public int warnMessageId;
/*     */     
/*     */     public void writeExternal(ObjectOutput out)
/*     */       throws IOException
/*     */     {
/* 105 */       out.writeByte(this.warnSet);
/* 106 */       out.writeByte(this.bos1word1DriveErr);
/* 107 */       out.writeByte(this.bos1word2DriveErr);
/* 108 */       out.writeByte(this.bos1word3DriveErr);
/* 109 */       out.writeByte(this.bos1word4DriveErr);
/* 110 */       out.writeByte(this.bos1word5DriveErr);
/* 111 */       out.writeByte(this.bos1word6DriveErr);
/* 112 */       out.writeByte(this.bos1word7DriveErr);
/* 113 */       out.writeByte(this.bos1word8DriveErr);
/* 114 */       out.writeByte(this.warnMessageId);
/*     */     }
/*     */     
/*     */     public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*     */     {
/* 119 */       this.warnSet = (in.readByte() & 0xFF);
/* 120 */       this.bos1word1DriveErr = (in.readByte() & 0xFFFF);
/* 121 */       this.bos1word2DriveErr = (in.readByte() & 0xFFFF);
/* 122 */       this.bos1word3DriveErr = (in.readByte() & 0xFFFF);
/* 123 */       this.bos1word4DriveErr = (in.readByte() & 0xFFFF);
/* 124 */       this.bos1word5DriveErr = (in.readByte() & 0xFFFF);
/* 125 */       this.bos1word6DriveErr = (in.readByte() & 0xFFFF);
/* 126 */       this.bos1word7DriveErr = (in.readByte() & 0xFFFF);
/* 127 */       this.bos1word8DriveErr = (in.readByte() & 0xFFFF);
/* 128 */       this.warnMessageId = (in.readByte() & 0xFF);
/*     */     }
/*     */     
/*     */     public String toString() {
/* 132 */       StringBuilder sb = new StringBuilder();
/* 133 */       sb.append('[');
/* 134 */       sb.append("warnSet: ").append(this.warnSet).append(", ");
/* 135 */       sb.append("bos1word1DriveErr: ").append(this.bos1word1DriveErr).append(", ");
/* 136 */       sb.append("bos1word2DriveErr: ").append(this.bos1word2DriveErr).append(", ");
/* 137 */       sb.append("bos1word3DriveErr: ").append(this.bos1word3DriveErr).append(", ");
/* 138 */       sb.append("bos1word4DriveErr: ").append(this.bos1word4DriveErr).append(", ");
/* 139 */       sb.append("bos1word5DriveErr: ").append(this.bos1word5DriveErr).append(", ");
/* 140 */       sb.append("bos1word6DriveErr: ").append(this.bos1word6DriveErr).append(", ");
/* 141 */       sb.append("bos1word7DriveErr: ").append(this.bos1word7DriveErr).append(", ");
/* 142 */       sb.append("bos1word8DriveErr: ").append(this.bos1word8DriveErr).append(", ");
/* 143 */       sb.append("warnMessageId: ").append(this.warnMessageId).append(']');
/* 144 */       return sb.toString();
/*     */     }
/*     */   }
/*     */   
/*     */   public void writeExternal(ObjectOutput out) throws IOException
/*     */   {
/* 150 */     out.writeObject(this.hwStatusPm);
/* 151 */     out.writeByte(this.commState);
/* 152 */     out.writeObject(this.opStatusPm);
/* 153 */     out.writeObject(this.opModePm);
/* 154 */     out.writeByte(this.controlMode);
/* 155 */     out.writeByte(this.planNo);
/* 156 */     out.writeByte(this.rate);
/* 157 */     out.writeObject(this.lampHwStatusList);
/* 158 */     out.writeObject(this.warnSetList);
/*     */   }
/*     */   
/*     */   public void readExternal(ObjectInput in)
/*     */     throws IOException, ClassNotFoundException
/*     */   {
/* 164 */     this.hwStatusPm = ((HwStatusPm)in.readObject());
/* 165 */     this.commState = (in.readByte() & 0xFF);
/* 166 */     this.opStatusPm = ((OpStatusPm)in.readObject());
/* 167 */     this.opModePm = ((OpModePm)in.readObject());
/* 168 */     this.controlMode = (in.readByte() & 0xFF);
/* 169 */     this.planNo = (in.readByte() & 0xFF);
/* 170 */     this.rate = (in.readByte() & 0xFF);
/* 171 */     this.lampHwStatusList = ((List)in.readObject());
/* 172 */     this.warnSetList = ((List)in.readObject());
/*     */   }
/*     */   
/*     */   public String toString() {
/* 176 */     StringBuilder sb = new StringBuilder();
/* 177 */     sb.append('[');
/* 178 */     sb.append("hwStatusPm: ").append(this.hwStatusPm).append(", ");
/* 179 */     sb.append("commState: ").append(this.commState).append(", ");
/* 180 */     sb.append("opStatusPm: ").append(this.opStatusPm).append(", ");
/* 181 */     sb.append("opModePm: ").append(this.opModePm).append(", ");
/* 182 */     sb.append("controlMode: ").append(this.controlMode).append(", ");
/* 183 */     sb.append("planNo: ").append(this.planNo).append(", ");
/* 184 */     sb.append("rate: ").append(this.rate).append(", ");
/* 185 */     sb.append("lampHwStatusList: ").append(this.lampHwStatusList).append(", ");
/* 186 */     sb.append("warnSetList: ").append(this.warnSetList).append(']');
/* 187 */     return sb.toString();
/*     */   }
/*     */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\fw2\cmdparam\rms\r21\GetR21RmsMonitorRspPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */