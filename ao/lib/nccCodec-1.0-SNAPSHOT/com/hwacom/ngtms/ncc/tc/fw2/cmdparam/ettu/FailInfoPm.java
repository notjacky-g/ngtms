/*     */ package com.hwacom.ngtms.ncc.tc.fw2.cmdparam.ettu;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @GlobalParams(paramsName="failInfo")
/*     */ public class FailInfoPm
/*     */   implements Externalizable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   @CommandParam(name="lineFail")
/*     */   public int lineFail;
/*     */   @CommandParam(name="plLightFail")
/*     */   public int plLightFail;
/*     */   @CommandParam(name="controlCircuitFail")
/*     */   public int controlCircuitFail;
/*     */   @CommandParam(name="acPowerFail")
/*     */   public int acPowerFail;
/*     */   @CommandParam(name="microphoneFail")
/*     */   public int microphoneFail;
/*     */   @CommandParam(name="batteryFail")
/*     */   public int batteryFail;
/*     */   @CommandParam(name="f1Spare7")
/*     */   public int f1Spare7;
/*     */   @CommandParam(name="f1Spare8")
/*     */   public int f1Spare8;
/*     */   @CommandParam(name="voltagePhase1Fail")
/*     */   public int voltagePhase1Fail;
/*     */   @CommandParam(name="voltagePhase2Fail")
/*     */   public int voltagePhase2Fail;
/*     */   @CommandParam(name="voltagePhase3Fail")
/*     */   public int voltagePhase3Fail;
/*     */   @CommandParam(name="speakerFail")
/*     */   public int speakerFail;
/*     */   @CommandParam(name="fuseFail")
/*     */   public int fuseFail;
/*     */   @CommandParam(name="dcPowerFail")
/*     */   public int dcPowerFail;
/*     */   @CommandParam(name="f2Spare7")
/*     */   public int f2Spare7;
/*     */   @CommandParam(name="f2Spare8")
/*     */   public int f2Spare8;
/*     */   @CommandParam(name="amplifierFail")
/*     */   public int amplifierFail;
/*     */   @CommandParam(name="onEooeCoolerActive")
/*     */   public int onEooeCoolerActive;
/*     */   @CommandParam(name="wirelessRepeaterAlarmRf1")
/*     */   public int wirelessRepeaterAlarmRf1;
/*     */   @CommandParam(name="wirelessRepeaterAlarmRf2")
/*     */   public int wirelessRepeaterAlarmRf2;
/*     */   @CommandParam(name="fiberInterfaceFail")
/*     */   public int fiberInterfaceFail;
/*     */   @CommandParam(name="onDoorOpen")
/*     */   public int onDoorOpen;
/*     */   @CommandParam(name="f3Spare7")
/*     */   public int f3Spare7;
/*     */   @CommandParam(name="f3Spare8")
/*     */   public int f3Spare8;
/*     */   
/*     */   public void writeExternal(ObjectOutput out)
/*     */     throws IOException
/*     */   {
/*  89 */     out.writeByte(this.lineFail);
/*  90 */     out.writeByte(this.plLightFail);
/*  91 */     out.writeByte(this.controlCircuitFail);
/*  92 */     out.writeByte(this.acPowerFail);
/*  93 */     out.writeByte(this.microphoneFail);
/*  94 */     out.writeByte(this.batteryFail);
/*  95 */     out.writeByte(this.f1Spare7);
/*  96 */     out.writeByte(this.f1Spare8);
/*  97 */     out.writeByte(this.voltagePhase1Fail);
/*  98 */     out.writeByte(this.voltagePhase2Fail);
/*  99 */     out.writeByte(this.voltagePhase3Fail);
/* 100 */     out.writeByte(this.speakerFail);
/* 101 */     out.writeByte(this.fuseFail);
/* 102 */     out.writeByte(this.dcPowerFail);
/* 103 */     out.writeByte(this.f2Spare7);
/* 104 */     out.writeByte(this.f2Spare8);
/* 105 */     out.writeByte(this.amplifierFail);
/* 106 */     out.writeByte(this.onEooeCoolerActive);
/* 107 */     out.writeByte(this.wirelessRepeaterAlarmRf1);
/* 108 */     out.writeByte(this.wirelessRepeaterAlarmRf2);
/* 109 */     out.writeByte(this.fiberInterfaceFail);
/* 110 */     out.writeByte(this.onDoorOpen);
/* 111 */     out.writeByte(this.f3Spare7);
/* 112 */     out.writeByte(this.f3Spare8);
/*     */   }
/*     */   
/*     */   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*     */   {
/* 117 */     this.lineFail = (in.readByte() & 0xFFFF);
/* 118 */     this.plLightFail = (in.readByte() & 0xFFFF);
/* 119 */     this.controlCircuitFail = (in.readByte() & 0xFFFF);
/* 120 */     this.acPowerFail = (in.readByte() & 0xFFFF);
/* 121 */     this.microphoneFail = (in.readByte() & 0xFFFF);
/* 122 */     this.batteryFail = (in.readByte() & 0xFFFF);
/* 123 */     this.f1Spare7 = (in.readByte() & 0xFFFF);
/* 124 */     this.f1Spare8 = (in.readByte() & 0xFFFF);
/* 125 */     this.voltagePhase1Fail = (in.readByte() & 0xFFFF);
/* 126 */     this.voltagePhase2Fail = (in.readByte() & 0xFFFF);
/* 127 */     this.voltagePhase3Fail = (in.readByte() & 0xFFFF);
/* 128 */     this.speakerFail = (in.readByte() & 0xFFFF);
/* 129 */     this.fuseFail = (in.readByte() & 0xFFFF);
/* 130 */     this.dcPowerFail = (in.readByte() & 0xFFFF);
/* 131 */     this.f2Spare7 = (in.readByte() & 0xFFFF);
/* 132 */     this.f2Spare8 = (in.readByte() & 0xFFFF);
/* 133 */     this.amplifierFail = (in.readByte() & 0xFFFF);
/* 134 */     this.onEooeCoolerActive = (in.readByte() & 0xFFFF);
/* 135 */     this.wirelessRepeaterAlarmRf1 = (in.readByte() & 0xFFFF);
/* 136 */     this.wirelessRepeaterAlarmRf2 = (in.readByte() & 0xFFFF);
/* 137 */     this.fiberInterfaceFail = (in.readByte() & 0xFFFF);
/* 138 */     this.onDoorOpen = (in.readByte() & 0xFFFF);
/* 139 */     this.f3Spare7 = (in.readByte() & 0xFFFF);
/* 140 */     this.f3Spare8 = (in.readByte() & 0xFFFF);
/*     */   }
/*     */   
/*     */   public String toString() {
/* 144 */     StringBuilder sb = new StringBuilder();
/* 145 */     sb.append('[');
/* 146 */     sb.append("lineFail: ").append(this.lineFail).append(", ");
/* 147 */     sb.append("plLightFail: ").append(this.plLightFail).append(", ");
/* 148 */     sb.append("controlCircuitFail: ").append(this.controlCircuitFail).append(", ");
/* 149 */     sb.append("acPowerFail: ").append(this.acPowerFail).append(", ");
/* 150 */     sb.append("microphoneFail: ").append(this.microphoneFail).append(", ");
/* 151 */     sb.append("batteryFail: ").append(this.batteryFail).append(", ");
/* 152 */     sb.append("f1Spare7: ").append(this.f1Spare7).append(", ");
/* 153 */     sb.append("f1Spare8: ").append(this.f1Spare8).append(", ");
/* 154 */     sb.append("voltagePhase1Fail: ").append(this.voltagePhase1Fail).append(", ");
/* 155 */     sb.append("voltagePhase2Fail: ").append(this.voltagePhase2Fail).append(", ");
/* 156 */     sb.append("voltagePhase3Fail: ").append(this.voltagePhase3Fail).append(", ");
/* 157 */     sb.append("speakerFail: ").append(this.speakerFail).append(", ");
/* 158 */     sb.append("fuseFail: ").append(this.fuseFail).append(", ");
/* 159 */     sb.append("dcPowerFail: ").append(this.dcPowerFail).append(", ");
/* 160 */     sb.append("f2Spare7: ").append(this.f2Spare7).append(", ");
/* 161 */     sb.append("f2Spare8: ").append(this.f2Spare8).append(", ");
/* 162 */     sb.append("amplifierFail: ").append(this.amplifierFail).append(", ");
/* 163 */     sb.append("onEooeCoolerActive: ").append(this.onEooeCoolerActive).append(", ");
/* 164 */     sb.append("wirelessRepeaterAlarmRf1: ").append(this.wirelessRepeaterAlarmRf1).append(", ");
/* 165 */     sb.append("wirelessRepeaterAlarmRf2: ").append(this.wirelessRepeaterAlarmRf2).append(", ");
/* 166 */     sb.append("fiberInterfaceFail: ").append(this.fiberInterfaceFail).append(", ");
/* 167 */     sb.append("onDoorOpen: ").append(this.onDoorOpen).append(", ");
/* 168 */     sb.append("f3Spare7: ").append(this.f3Spare7).append(", ");
/* 169 */     sb.append("f3Spare8: ").append(this.f3Spare8).append(']');
/* 170 */     return sb.toString();
/*     */   }
/*     */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\fw2\cmdparam\ettu\FailInfoPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */