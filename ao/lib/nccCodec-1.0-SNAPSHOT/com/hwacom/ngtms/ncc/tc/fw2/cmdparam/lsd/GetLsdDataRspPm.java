/*    */ package com.hwacom.ngtms.ncc.tc.fw2.cmdparam.lsd;
/*    */ 
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParam;
/*    */ import com.hwacom.ngtms.ncc.tc.fw2.cmdparam.HwStatusPm;
/*    */ import com.hwacom.ngtms.ncc.tc.fw2.cmdparam.MdhmPm;
/*    */ import java.io.Externalizable;
/*    */ import java.io.IOException;
/*    */ import java.io.ObjectInput;
/*    */ import java.io.ObjectOutput;
/*    */ 
/*    */ @com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParams(cmdName="getLsdDataRsp")
/*    */ public class GetLsdDataRspPm implements Externalizable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   public static final int cmdId = 53;
/*    */   public static final String cmdName = "getLsdDataRsp";
/*    */   @CommandParam(name="hwStatus")
/*    */   public HwStatusPm hwStatusPm;
/*    */   @CommandParam(name="mdhm")
/*    */   public MdhmPm mdhmPm;
/*    */   @CommandParam(name="variableList")
/*    */   public java.util.List<VariableListItem> variableList;
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
/* 38 */       out.writeShort(this.sensorVariable);
/* 39 */       out.writeShort(this.accumulateVariable);
/*    */     }
/*    */     
/*    */     public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*    */     {
/* 44 */       this.sensorVariable = (in.readShort() & 0xFFFF);
/* 45 */       this.accumulateVariable = (in.readShort() & 0xFFFF);
/*    */     }
/*    */     
/*    */     public String toString() {
/* 49 */       StringBuilder sb = new StringBuilder();
/* 50 */       sb.append('[');
/* 51 */       sb.append("sensorVariable: ").append(this.sensorVariable).append(", ");
/* 52 */       sb.append("accumulateVariable: ").append(this.accumulateVariable).append(']');
/* 53 */       return sb.toString();
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
/* 65 */     out.writeObject(this.hwStatusPm);
/* 66 */     out.writeObject(this.mdhmPm);
/* 67 */     out.writeObject(this.variableList);
/* 68 */     out.writeShort(this.dayVariable);
/* 69 */     out.writeShort(this.monthVariable);
/*    */   }
/*    */   
/*    */   public void readExternal(ObjectInput in)
/*    */     throws IOException, ClassNotFoundException
/*    */   {
/* 75 */     this.hwStatusPm = ((HwStatusPm)in.readObject());
/* 76 */     this.mdhmPm = ((MdhmPm)in.readObject());
/* 77 */     this.variableList = ((java.util.List)in.readObject());
/* 78 */     this.dayVariable = (in.readShort() & 0xFFFF);
/* 79 */     this.monthVariable = (in.readShort() & 0xFFFF);
/*    */   }
/*    */   
/*    */   public String toString() {
/* 83 */     StringBuilder sb = new StringBuilder();
/* 84 */     sb.append('[');
/* 85 */     sb.append("hwStatusPm: ").append(this.hwStatusPm).append(", ");
/* 86 */     sb.append("mdhmPm: ").append(this.mdhmPm).append(", ");
/* 87 */     sb.append("variableList: ").append(this.variableList).append(", ");
/* 88 */     sb.append("dayVariable: ").append(this.dayVariable).append(", ");
/* 89 */     sb.append("monthVariable: ").append(this.monthVariable).append(']');
/* 90 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\fw2\cmdparam\lsd\GetLsdDataRspPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */