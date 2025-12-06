/*    */ package com.hwacom.ngtms.ncc.tc.fw2.cmdparam.rd;
/*    */ 
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.BytesUtility;
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParam;
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParams;
/*    */ import java.io.Externalizable;
/*    */ import java.io.IOException;
/*    */ import java.io.ObjectInput;
/*    */ import java.io.ObjectOutput;
/*    */ 
/*    */ @CommandParams(cmdName="setEvaluateParamTableReq")
/*    */ public class SetEvaluateParamTableReqPm
/*    */   implements Externalizable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   public static final int cmdId = 73;
/*    */   public static final String cmdName = "setEvaluateParamTableReq";
/*    */   @CommandParam(name="evaluateParamTable")
/*    */   public byte[] evaluateParamTable;
/*    */   @CommandParam(name="measureTime")
/*    */   public int measureTime;
/*    */   
/*    */   public void writeExternal(ObjectOutput out)
/*    */     throws IOException
/*    */   {
/* 26 */     out.writeObject(this.evaluateParamTable);
/* 27 */     out.writeByte(this.measureTime);
/*    */   }
/*    */   
/*    */   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*    */   {
/* 32 */     this.evaluateParamTable = ((byte[])in.readObject());
/* 33 */     this.measureTime = (in.readByte() & 0xFF);
/*    */   }
/*    */   
/*    */   public String toString() {
/* 37 */     StringBuilder sb = new StringBuilder();
/* 38 */     sb.append('[');
/* 39 */     sb.append("evaluateParamTable: ")
/* 40 */       .append(BytesUtility.toHexString(this.evaluateParamTable))
/* 41 */       .append(", ");
/* 42 */     sb.append("measureTime: ").append(this.measureTime).append(']');
/* 43 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\fw2\cmdparam\rd\SetEvaluateParamTableReqPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */