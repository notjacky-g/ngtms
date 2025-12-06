/*    */ package com.hwacom.ngtms.ncc.tc.fw2.cmdparam;
/*    */ 
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParam;
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParams;
/*    */ import java.io.Externalizable;
/*    */ import java.io.IOException;
/*    */ import java.io.ObjectInput;
/*    */ import java.io.ObjectOutput;
/*    */ 
/*    */ @CommandParams(cmdName="setLocalTestOperationReq")
/*    */ public class SetLocalTestOperationReqPm
/*    */   implements Externalizable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   public static final int cmdId = 3855;
/*    */   public static final String cmdName = "setLocalTestOperationReq";
/*    */   @CommandParam(name="testItem")
/*    */   public int testItem;
/*    */   @CommandParam(name="testMode")
/*    */   public int testMode;
/*    */   
/*    */   public void writeExternal(ObjectOutput out)
/*    */     throws IOException
/*    */   {
/* 25 */     out.writeByte(this.testItem);
/* 26 */     out.writeByte(this.testMode);
/*    */   }
/*    */   
/*    */   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*    */   {
/* 31 */     this.testItem = (in.readByte() & 0xFF);
/* 32 */     this.testMode = (in.readByte() & 0xFF);
/*    */   }
/*    */   
/*    */   public String toString() {
/* 36 */     StringBuilder sb = new StringBuilder();
/* 37 */     sb.append('[');
/* 38 */     sb.append("testItem: ").append(this.testItem).append(", ");
/* 39 */     sb.append("testMode: ").append(this.testMode).append(']');
/* 40 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\fw2\cmdparam\SetLocalTestOperationReqPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */