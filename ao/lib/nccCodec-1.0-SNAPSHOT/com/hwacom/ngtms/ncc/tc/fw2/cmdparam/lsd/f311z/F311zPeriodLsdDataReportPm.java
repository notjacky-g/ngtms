/*    */ package com.hwacom.ngtms.ncc.tc.fw2.cmdparam.lsd.f311z;
/*    */ 
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParam;
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParams;
/*    */ import com.hwacom.ngtms.ncc.tc.fw2.cmdparam.DhmPm;
/*    */ import com.hwacom.ngtms.ncc.tc.fw2.cmdparam.HwStatusPm;
/*    */ import java.io.Externalizable;
/*    */ import java.io.IOException;
/*    */ import java.io.ObjectInput;
/*    */ import java.io.ObjectOutput;
/*    */ import java.util.List;
/*    */ 
/*    */ @CommandParams(cmdName="f311zPeriodLsdDataReport")
/*    */ public class F311zPeriodLsdDataReportPm implements Externalizable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   public static final int cmdId = 48;
/*    */   public static final String cmdName = "f311zPeriodLsdDataReport";
/*    */   @CommandParam(name="responseType")
/*    */   public int responseType;
/*    */   @CommandParam(name="hwStatus")
/*    */   public HwStatusPm hwStatusPm;
/*    */   @CommandParam(name="dhm")
/*    */   public DhmPm dhmPm;
/*    */   @CommandParam(name="variableList")
/*    */   public List<VariableListItem> variableList;
/*    */   @CommandParam(name="dayVariable")
/*    */   public int dayVariable;
/*    */   @CommandParam(name="monthVariable")
/*    */   public int monthVariable;
/*    */   
/*    */   public static class VariableListItem implements Externalizable
/*    */   {
/*    */     private static final long serialVersionUID = 1L;
/*    */     @CommandParam(name="sensorVariable")
/*    */     public int sensorVariable;
/*    */     @CommandParam(name="accumulateVariable")
/*    */     public int accumulateVariable;
/*    */     
/*    */     public void writeExternal(ObjectOutput out) throws IOException
/*    */     {
/* 42 */       out.writeShort(this.sensorVariable);
/* 43 */       out.writeShort(this.accumulateVariable);
/*    */     }
/*    */     
/*    */     public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*    */     {
/* 48 */       this.sensorVariable = (in.readShort() & 0xFFFF);
/* 49 */       this.accumulateVariable = (in.readShort() & 0xFFFF);
/*    */     }
/*    */     
/*    */     public String toString() {
/* 53 */       StringBuilder sb = new StringBuilder();
/* 54 */       sb.append('[');
/* 55 */       sb.append("sensorVariable: ").append(this.sensorVariable).append(", ");
/* 56 */       sb.append("accumulateVariable: ").append(this.accumulateVariable).append(']');
/* 57 */       return sb.toString();
/*    */     }
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public void writeExternal(ObjectOutput out)
/*    */     throws IOException
/*    */   {
/* 69 */     out.writeByte(this.responseType);
/* 70 */     out.writeObject(this.hwStatusPm);
/* 71 */     out.writeObject(this.dhmPm);
/* 72 */     out.writeObject(this.variableList);
/* 73 */     out.writeShort(this.dayVariable);
/* 74 */     out.writeShort(this.monthVariable);
/*    */   }
/*    */   
/*    */   public void readExternal(ObjectInput in)
/*    */     throws IOException, ClassNotFoundException
/*    */   {
/* 80 */     this.responseType = (in.readByte() & 0xFF);
/* 81 */     this.hwStatusPm = ((HwStatusPm)in.readObject());
/* 82 */     this.dhmPm = ((DhmPm)in.readObject());
/* 83 */     this.variableList = ((List)in.readObject());
/* 84 */     this.dayVariable = (in.readShort() & 0xFFFF);
/* 85 */     this.monthVariable = (in.readShort() & 0xFFFF);
/*    */   }
/*    */   
/*    */   public String toString() {
/* 89 */     StringBuilder sb = new StringBuilder();
/* 90 */     sb.append('[');
/* 91 */     sb.append("responseType: ").append(this.responseType).append(", ");
/* 92 */     sb.append("hwStatusPm: ").append(this.hwStatusPm).append(", ");
/* 93 */     sb.append("dhmPm: ").append(this.dhmPm).append(", ");
/* 94 */     sb.append("variableList: ").append(this.variableList).append(", ");
/* 95 */     sb.append("dayVariable: ").append(this.dayVariable).append(", ");
/* 96 */     sb.append("monthVariable: ").append(this.monthVariable).append(']');
/* 97 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\fw2\cmdparam\lsd\f311z\F311zPeriodLsdDataReportPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */