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
/*    */ @CommandParams(cmdName="setActuateControlConfigReq")
/*    */ public class SetActuateControlConfigReqPm
/*    */   implements Externalizable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   public static final int cmdId = 24345;
/*    */   public static final String cmdName = "setActuateControlConfigReq";
/*    */   @CommandParam(name="subPhaseId")
/*    */   public int subPhaseId;
/*    */   @CommandParam(name="actuateType")
/*    */   public ActuateTypePm actuateTypePm;
/*    */   @CommandParam(name="timeExtend")
/*    */   public int timeExtend;
/*    */   @CommandParam(name="actuateData1")
/*    */   public int actuateData1;
/*    */   @CommandParam(name="actuateData2")
/*    */   public int actuateData2;
/*    */   
/*    */   public void writeExternal(ObjectOutput out)
/*    */     throws IOException
/*    */   {
/* 35 */     out.writeByte(this.subPhaseId);
/* 36 */     out.writeObject(this.actuateTypePm);
/* 37 */     out.writeByte(this.timeExtend);
/* 38 */     out.writeByte(this.actuateData1);
/* 39 */     out.writeByte(this.actuateData2);
/*    */   }
/*    */   
/*    */   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*    */   {
/* 44 */     this.subPhaseId = (in.readByte() & 0xFF);
/* 45 */     this.actuateTypePm = ((ActuateTypePm)in.readObject());
/* 46 */     this.timeExtend = (in.readByte() & 0xFF);
/* 47 */     this.actuateData1 = (in.readByte() & 0xFF);
/* 48 */     this.actuateData2 = (in.readByte() & 0xFF);
/*    */   }
/*    */   
/*    */   public String toString() {
/* 52 */     StringBuilder sb = new StringBuilder();
/* 53 */     sb.append('[');
/* 54 */     sb.append("subPhaseId: ").append(this.subPhaseId).append(", ");
/* 55 */     sb.append("actuateTypePm: ").append(this.actuateTypePm).append(", ");
/* 56 */     sb.append("timeExtend: ").append(this.timeExtend).append(", ");
/* 57 */     sb.append("actuateData1: ").append(this.actuateData1).append(", ");
/* 58 */     sb.append("actuateData2: ").append(this.actuateData2).append(']');
/* 59 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\ct3\cmdparam\sig\SetActuateControlConfigReqPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */