/*    */ package com.hwacom.ngtms.ncc.tc.ct3.cmdparam;
/*    */ 
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParam;
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParams;
/*    */ import java.io.Externalizable;
/*    */ import java.io.IOException;
/*    */ import java.io.ObjectInput;
/*    */ import java.io.ObjectOutput;
/*    */ 
/*    */ @CommandParams(cmdName="queryLockDbRsp")
/*    */ public class QueryLockDbRspPm
/*    */   implements Externalizable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   public static final int cmdId = 4038;
/*    */   public static final String cmdName = "queryLockDbRsp";
/*    */   @CommandParam(name="lockDb")
/*    */   public int lockDb;
/*    */   
/*    */   public void writeExternal(ObjectOutput out) throws IOException
/*    */   {
/* 22 */     out.writeByte(this.lockDb);
/*    */   }
/*    */   
/*    */   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*    */   {
/* 27 */     this.lockDb = (in.readByte() & 0xFF);
/*    */   }
/*    */   
/*    */   public String toString() {
/* 31 */     StringBuilder sb = new StringBuilder();
/* 32 */     sb.append('[');
/* 33 */     sb.append("lockDb: ").append(this.lockDb).append(']');
/* 34 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\ct3\cmdparam\QueryLockDbRspPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */