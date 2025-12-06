/*    */ package com.hwacom.ngtms.ncc.tc.fw2.cmdparam.vi;
/*    */ 
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.BytesUtility;
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParam;
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParams;
/*    */ import java.io.Externalizable;
/*    */ import java.io.IOException;
/*    */ import java.io.ObjectInput;
/*    */ import java.io.ObjectOutput;
/*    */ 
/*    */ @CommandParams(cmdName="setSmoothParamTableReq")
/*    */ public class SetSmoothParamTableReqPm
/*    */   implements Externalizable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   public static final int cmdId = 34;
/*    */   public static final String cmdName = "setSmoothParamTableReq";
/*    */   @CommandParam(name="smoothParamTable")
/*    */   public byte[] smoothParamTable;
/*    */   
/*    */   public void writeExternal(ObjectOutput out) throws IOException
/*    */   {
/* 23 */     out.writeObject(this.smoothParamTable);
/*    */   }
/*    */   
/*    */   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*    */   {
/* 28 */     this.smoothParamTable = ((byte[])in.readObject());
/*    */   }
/*    */   
/*    */   public String toString() {
/* 32 */     StringBuilder sb = new StringBuilder();
/* 33 */     sb.append('[');
/* 34 */     sb.append("smoothParamTable: ")
/* 35 */       .append(BytesUtility.toHexString(this.smoothParamTable))
/* 36 */       .append(']');
/* 37 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\fw2\cmdparam\vi\SetSmoothParamTableReqPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */