/*    */ package com.hwacom.ngtms.ncc.tc.fw2.cmdparam.lsd;
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
/*    */   public static final int cmdId = 49;
/*    */   public static final String cmdName = "setEvaluateParamTableReq";
/*    */   @CommandParam(name="type")
/*    */   public int type;
/*    */   @CommandParam(name="evaluateParamTable")
/*    */   public byte[] evaluateParamTable;
/*    */   
/*    */   public void writeExternal(ObjectOutput out)
/*    */     throws IOException
/*    */   {
/* 26 */     out.writeByte(this.type);
/* 27 */     out.writeObject(this.evaluateParamTable);
/*    */   }
/*    */   
/*    */   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*    */   {
/* 32 */     this.type = (in.readByte() & 0xFF);
/* 33 */     this.evaluateParamTable = ((byte[])in.readObject());
/*    */   }
/*    */   
/*    */   public String toString() {
/* 37 */     StringBuilder sb = new StringBuilder();
/* 38 */     sb.append('[');
/* 39 */     sb.append("type: ").append(this.type).append(", ");
/* 40 */     sb.append("evaluateParamTable: ")
/* 41 */       .append(BytesUtility.toHexString(this.evaluateParamTable))
/* 42 */       .append(']');
/* 43 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\fw2\cmdparam\lsd\SetEvaluateParamTableReqPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */