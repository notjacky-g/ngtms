/*    */ package com.hwacom.ngtms.ncc.tc.ct3.cmdparam.sig;
/*    */ 
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParam;
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParams;
/*    */ import java.io.Externalizable;
/*    */ import java.io.IOException;
/*    */ import java.io.ObjectInput;
/*    */ import java.io.ObjectOutput;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @CommandParams(cmdName="requestDataResendReport")
/*    */ public class RequestDataResendReportPm
/*    */   implements Externalizable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   public static final int cmdId = 24331;
/*    */   public static final String cmdName = "requestDataResendReport";
/*    */   @CommandParam(name="phaseOrder")
/*    */   public int phaseOrder;
/*    */   @CommandParam(name="timeSegment")
/*    */   public int timeSegment;
/*    */   @CommandParam(name="basicParameter")
/*    */   public int basicParameter;
/*    */   @CommandParam(name="timimngPlan")
/*    */   public int timimngPlan;
/*    */   @CommandParam(name="vipParameter")
/*    */   public int vipParameter;
/*    */   @CommandParam(name="reverseLaneControl")
/*    */   public int reverseLaneControl;
/*    */   @CommandParam(name="dimControl")
/*    */   public int dimControl;
/*    */   @CommandParam(name="actuatedControl")
/*    */   public int actuatedControl;
/*    */   
/*    */   public void writeExternal(ObjectOutput out)
/*    */     throws IOException
/*    */   {
/* 44 */     out.writeByte(this.phaseOrder);
/* 45 */     out.writeByte(this.timeSegment);
/* 46 */     out.writeByte(this.basicParameter);
/* 47 */     out.writeByte(this.timimngPlan);
/* 48 */     out.writeByte(this.vipParameter);
/* 49 */     out.writeByte(this.reverseLaneControl);
/* 50 */     out.writeByte(this.dimControl);
/* 51 */     out.writeByte(this.actuatedControl);
/*    */   }
/*    */   
/*    */   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*    */   {
/* 56 */     this.phaseOrder = (in.readByte() & 0xFFFF);
/* 57 */     this.timeSegment = (in.readByte() & 0xFFFF);
/* 58 */     this.basicParameter = (in.readByte() & 0xFFFF);
/* 59 */     this.timimngPlan = (in.readByte() & 0xFFFF);
/* 60 */     this.vipParameter = (in.readByte() & 0xFFFF);
/* 61 */     this.reverseLaneControl = (in.readByte() & 0xFFFF);
/* 62 */     this.dimControl = (in.readByte() & 0xFFFF);
/* 63 */     this.actuatedControl = (in.readByte() & 0xFFFF);
/*    */   }
/*    */   
/*    */   public String toString() {
/* 67 */     StringBuilder sb = new StringBuilder();
/* 68 */     sb.append('[');
/* 69 */     sb.append("phaseOrder: ").append(this.phaseOrder).append(", ");
/* 70 */     sb.append("timeSegment: ").append(this.timeSegment).append(", ");
/* 71 */     sb.append("basicParameter: ").append(this.basicParameter).append(", ");
/* 72 */     sb.append("timimngPlan: ").append(this.timimngPlan).append(", ");
/* 73 */     sb.append("vipParameter: ").append(this.vipParameter).append(", ");
/* 74 */     sb.append("reverseLaneControl: ").append(this.reverseLaneControl).append(", ");
/* 75 */     sb.append("dimControl: ").append(this.dimControl).append(", ");
/* 76 */     sb.append("actuatedControl: ").append(this.actuatedControl).append(']');
/* 77 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\ct3\cmdparam\sig\RequestDataResendReportPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */