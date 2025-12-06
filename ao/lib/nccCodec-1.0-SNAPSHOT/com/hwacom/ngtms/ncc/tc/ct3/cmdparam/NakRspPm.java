/*    */ package com.hwacom.ngtms.ncc.tc.ct3.cmdparam;
/*    */ 
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParam;
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParams;
/*    */ import java.io.Externalizable;
/*    */ import java.io.IOException;
/*    */ import java.io.ObjectInput;
/*    */ import java.io.ObjectOutput;
/*    */ 
/*    */ 
/*    */ @CommandParams(cmdName="nakRsp")
/*    */ public class NakRspPm
/*    */   implements Externalizable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   public static final int cmdId = 3969;
/*    */   public static final String cmdName = "nakRsp";
/*    */   @CommandParam(name="commandId")
/*    */   public int commandId;
/*    */   @CommandParam(name="errorCode")
/*    */   public int errorCode;
/*    */   @CommandParam(name="parameterNumber")
/*    */   public int parameterNumber;
/*    */   
/*    */   public void writeExternal(ObjectOutput out)
/*    */     throws IOException
/*    */   {
/* 28 */     out.writeShort(this.commandId);
/* 29 */     out.writeByte(this.errorCode);
/* 30 */     out.writeByte(this.parameterNumber);
/*    */   }
/*    */   
/*    */   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*    */   {
/* 35 */     this.commandId = (in.readShort() & 0xFFFF);
/* 36 */     this.errorCode = (in.readByte() & 0xFF);
/* 37 */     this.parameterNumber = (in.readByte() & 0xFF);
/*    */   }
/*    */   
/*    */   public String toString() {
/* 41 */     StringBuilder sb = new StringBuilder();
/* 42 */     sb.append('[');
/* 43 */     sb.append("commandId: ").append(this.commandId).append(", ");
/* 44 */     sb.append("errorCode: ").append(this.errorCode).append(", ");
/* 45 */     sb.append("parameterNumber: ").append(this.parameterNumber).append(']');
/* 46 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\ct3\cmdparam\NakRspPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */