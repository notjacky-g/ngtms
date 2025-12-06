/*    */ package com.hwacom.ngtms.ncc.tc.ct3.cmdparam.sig;
/*    */ 
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParam;
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParams;
/*    */ import java.io.Externalizable;
/*    */ import java.io.IOException;
/*    */ import java.io.ObjectInput;
/*    */ import java.io.ObjectOutput;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @CommandParams(cmdName="phaseReport")
/*    */ public class PhaseReportPm
/*    */   implements Externalizable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   public static final int cmdId = 24323;
/*    */   public static final String cmdName = "phaseReport";
/*    */   @CommandParam(name="phaseOrder")
/*    */   public int phaseOrder;
/*    */   @CommandParam(name="signalMap")
/*    */   public SignalMapPm signalMapPm;
/*    */   @CommandParam(name="signalCount")
/*    */   public int signalCount;
/*    */   @CommandParam(name="subPhaseId")
/*    */   public int subPhaseId;
/*    */   @CommandParam(name="stepId")
/*    */   public int stepId;
/*    */   @CommandParam(name="stepSec")
/*    */   public int stepSec;
/*    */   @CommandParam(name="signalStatusList")
/*    */   public List<SignalStatusListItem> signalStatusList;
/*    */   
/*    */   public static class SignalStatusListItem
/*    */     implements Externalizable
/*    */   {
/*    */     private static final long serialVersionUID = 1L;
/*    */     @CommandParam(name="signalStatus")
/*    */     public SignalStatusPm signalStatusPm;
/*    */     
/*    */     public void writeExternal(ObjectOutput out)
/*    */       throws IOException
/*    */     {
/* 47 */       out.writeObject(this.signalStatusPm);
/*    */     }
/*    */     
/*    */     public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*    */     {
/* 52 */       this.signalStatusPm = ((SignalStatusPm)in.readObject());
/*    */     }
/*    */     
/*    */     public String toString() {
/* 56 */       StringBuilder sb = new StringBuilder();
/* 57 */       sb.append('[');
/* 58 */       sb.append("signalStatusPm: ").append(this.signalStatusPm).append(']');
/* 59 */       return sb.toString();
/*    */     }
/*    */   }
/*    */   
/*    */   public void writeExternal(ObjectOutput out) throws IOException
/*    */   {
/* 65 */     out.writeByte(this.phaseOrder);
/* 66 */     out.writeObject(this.signalMapPm);
/* 67 */     out.writeByte(this.signalCount);
/* 68 */     out.writeByte(this.subPhaseId);
/* 69 */     out.writeByte(this.stepId);
/* 70 */     out.writeShort(this.stepSec);
/* 71 */     out.writeObject(this.signalStatusList);
/*    */   }
/*    */   
/*    */   public void readExternal(ObjectInput in)
/*    */     throws IOException, ClassNotFoundException
/*    */   {
/* 77 */     this.phaseOrder = (in.readByte() & 0xFF);
/* 78 */     this.signalMapPm = ((SignalMapPm)in.readObject());
/* 79 */     this.signalCount = (in.readByte() & 0xFF);
/* 80 */     this.subPhaseId = (in.readByte() & 0xFF);
/* 81 */     this.stepId = (in.readByte() & 0xFF);
/* 82 */     this.stepSec = (in.readShort() & 0xFFFF);
/* 83 */     this.signalStatusList = ((List)in.readObject());
/*    */   }
/*    */   
/*    */   public String toString() {
/* 87 */     StringBuilder sb = new StringBuilder();
/* 88 */     sb.append('[');
/* 89 */     sb.append("phaseOrder: ").append(this.phaseOrder).append(", ");
/* 90 */     sb.append("signalMapPm: ").append(this.signalMapPm).append(", ");
/* 91 */     sb.append("signalCount: ").append(this.signalCount).append(", ");
/* 92 */     sb.append("subPhaseId: ").append(this.subPhaseId).append(", ");
/* 93 */     sb.append("stepId: ").append(this.stepId).append(", ");
/* 94 */     sb.append("stepSec: ").append(this.stepSec).append(", ");
/* 95 */     sb.append("signalStatusList: ").append(this.signalStatusList).append(']');
/* 96 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\ct3\cmdparam\sig\PhaseReportPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */