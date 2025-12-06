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
/*    */ @CommandParams(cmdName="controlStrategyReport")
/*    */ public class ControlStrategyReportPm
/*    */   implements Externalizable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   public static final int cmdId = 24320;
/*    */   public static final String cmdName = "controlStrategyReport";
/*    */   @CommandParam(name="controlStrategy")
/*    */   public ControlStrategyPm controlStrategyPm;
/*    */   @CommandParam(name="beginEnd")
/*    */   public int beginEnd;
/*    */   
/*    */   public void writeExternal(ObjectOutput out)
/*    */     throws IOException
/*    */   {
/* 26 */     out.writeObject(this.controlStrategyPm);
/* 27 */     out.writeByte(this.beginEnd);
/*    */   }
/*    */   
/*    */   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*    */   {
/* 32 */     this.controlStrategyPm = ((ControlStrategyPm)in.readObject());
/* 33 */     this.beginEnd = (in.readByte() & 0xFF);
/*    */   }
/*    */   
/*    */   public String toString() {
/* 37 */     StringBuilder sb = new StringBuilder();
/* 38 */     sb.append('[');
/* 39 */     sb.append("controlStrategyPm: ").append(this.controlStrategyPm).append(", ");
/* 40 */     sb.append("beginEnd: ").append(this.beginEnd).append(']');
/* 41 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\ct3\cmdparam\sig\ControlStrategyReportPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */