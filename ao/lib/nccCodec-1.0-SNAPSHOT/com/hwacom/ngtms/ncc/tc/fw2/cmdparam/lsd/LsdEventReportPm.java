/*    */ package com.hwacom.ngtms.ncc.tc.fw2.cmdparam.lsd;
/*    */ 
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParam;
/*    */ import com.hwacom.ngtms.ncc.tc.fw2.cmdparam.HwStatusPm;
/*    */ import java.io.IOException;
/*    */ import java.io.ObjectInput;
/*    */ import java.io.ObjectOutput;
/*    */ 
/*    */ @com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParams(cmdName="lsdEventReport")
/*    */ public class LsdEventReportPm implements java.io.Externalizable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   public static final int cmdId = 50;
/*    */   public static final String cmdName = "lsdEventReport";
/*    */   @CommandParam(name="responseType")
/*    */   public int responseType;
/*    */   @CommandParam(name="hwStatus")
/*    */   public HwStatusPm hwStatusPm;
/*    */   @CommandParam(name="mdhm")
/*    */   public com.hwacom.ngtms.ncc.tc.fw2.cmdparam.MdhmPm mdhmPm;
/*    */   @CommandParam(name="accumulateVariableList")
/*    */   public java.util.List<AccumulateVariableListItem> accumulateVariableList;
/*    */   @CommandParam(name="dayVariable")
/*    */   public int dayVariable;
/*    */   @CommandParam(name="monthVariable")
/*    */   public int monthVariable;
/*    */   @CommandParam(name="lsdDegree")
/*    */   public int lsdDegree;
/*    */   
/*    */   public static class AccumulateVariableListItem implements java.io.Externalizable
/*    */   {
/*    */     private static final long serialVersionUID = 1L;
/*    */     @CommandParam(name="accumulateVariable")
/*    */     public int accumulateVariable;
/*    */     
/*    */     public void writeExternal(ObjectOutput out) throws IOException
/*    */     {
/* 38 */       out.writeShort(this.accumulateVariable);
/*    */     }
/*    */     
/*    */     public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*    */     {
/* 43 */       this.accumulateVariable = (in.readShort() & 0xFFFF);
/*    */     }
/*    */     
/*    */     public String toString() {
/* 47 */       StringBuilder sb = new StringBuilder();
/* 48 */       sb.append('[');
/* 49 */       sb.append("accumulateVariable: ").append(this.accumulateVariable).append(']');
/* 50 */       return sb.toString();
/*    */     }
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public void writeExternal(ObjectOutput out)
/*    */     throws IOException
/*    */   {
/* 65 */     out.writeByte(this.responseType);
/* 66 */     out.writeObject(this.hwStatusPm);
/* 67 */     out.writeObject(this.mdhmPm);
/* 68 */     out.writeObject(this.accumulateVariableList);
/* 69 */     out.writeShort(this.dayVariable);
/* 70 */     out.writeShort(this.monthVariable);
/* 71 */     out.writeByte(this.lsdDegree);
/*    */   }
/*    */   
/*    */   public void readExternal(ObjectInput in)
/*    */     throws IOException, ClassNotFoundException
/*    */   {
/* 77 */     this.responseType = (in.readByte() & 0xFF);
/* 78 */     this.hwStatusPm = ((HwStatusPm)in.readObject());
/* 79 */     this.mdhmPm = ((com.hwacom.ngtms.ncc.tc.fw2.cmdparam.MdhmPm)in.readObject());
/* 80 */     this.accumulateVariableList = ((java.util.List)in.readObject());
/* 81 */     this.dayVariable = (in.readShort() & 0xFFFF);
/* 82 */     this.monthVariable = (in.readShort() & 0xFFFF);
/* 83 */     this.lsdDegree = (in.readByte() & 0xFF);
/*    */   }
/*    */   
/*    */   public String toString() {
/* 87 */     StringBuilder sb = new StringBuilder();
/* 88 */     sb.append('[');
/* 89 */     sb.append("responseType: ").append(this.responseType).append(", ");
/* 90 */     sb.append("hwStatusPm: ").append(this.hwStatusPm).append(", ");
/* 91 */     sb.append("mdhmPm: ").append(this.mdhmPm).append(", ");
/* 92 */     sb.append("accumulateVariableList: ").append(this.accumulateVariableList).append(", ");
/* 93 */     sb.append("dayVariable: ").append(this.dayVariable).append(", ");
/* 94 */     sb.append("monthVariable: ").append(this.monthVariable).append(", ");
/* 95 */     sb.append("lsdDegree: ").append(this.lsdDegree).append(']');
/* 96 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\fw2\cmdparam\lsd\LsdEventReportPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */