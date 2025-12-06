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
/*    */ 
/*    */ @CommandParams(cmdName="tcDataChangeReport")
/*    */ public class TcDataChangeReportPm
/*    */   implements Externalizable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   public static final int cmdId = 24330;
/*    */   public static final String cmdName = "tcDataChangeReport";
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
/*    */   @CommandParam(name="subDbId")
/*    */   public int subDbId;
/*    */   
/*    */   public void writeExternal(ObjectOutput out)
/*    */     throws IOException
/*    */   {
/* 47 */     out.writeByte(this.phaseOrder);
/* 48 */     out.writeByte(this.timeSegment);
/* 49 */     out.writeByte(this.basicParameter);
/* 50 */     out.writeByte(this.timimngPlan);
/* 51 */     out.writeByte(this.vipParameter);
/* 52 */     out.writeByte(this.reverseLaneControl);
/* 53 */     out.writeByte(this.dimControl);
/* 54 */     out.writeByte(this.actuatedControl);
/* 55 */     out.writeByte(this.subDbId);
/*    */   }
/*    */   
/*    */   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*    */   {
/* 60 */     this.phaseOrder = (in.readByte() & 0xFFFF);
/* 61 */     this.timeSegment = (in.readByte() & 0xFFFF);
/* 62 */     this.basicParameter = (in.readByte() & 0xFFFF);
/* 63 */     this.timimngPlan = (in.readByte() & 0xFFFF);
/* 64 */     this.vipParameter = (in.readByte() & 0xFFFF);
/* 65 */     this.reverseLaneControl = (in.readByte() & 0xFFFF);
/* 66 */     this.dimControl = (in.readByte() & 0xFFFF);
/* 67 */     this.actuatedControl = (in.readByte() & 0xFFFF);
/* 68 */     this.subDbId = (in.readByte() & 0xFF);
/*    */   }
/*    */   
/*    */   public String toString() {
/* 72 */     StringBuilder sb = new StringBuilder();
/* 73 */     sb.append('[');
/* 74 */     sb.append("phaseOrder: ").append(this.phaseOrder).append(", ");
/* 75 */     sb.append("timeSegment: ").append(this.timeSegment).append(", ");
/* 76 */     sb.append("basicParameter: ").append(this.basicParameter).append(", ");
/* 77 */     sb.append("timimngPlan: ").append(this.timimngPlan).append(", ");
/* 78 */     sb.append("vipParameter: ").append(this.vipParameter).append(", ");
/* 79 */     sb.append("reverseLaneControl: ").append(this.reverseLaneControl).append(", ");
/* 80 */     sb.append("dimControl: ").append(this.dimControl).append(", ");
/* 81 */     sb.append("actuatedControl: ").append(this.actuatedControl).append(", ");
/* 82 */     sb.append("subDbId: ").append(this.subDbId).append(']');
/* 83 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\ct3\cmdparam\sig\TcDataChangeReportPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */