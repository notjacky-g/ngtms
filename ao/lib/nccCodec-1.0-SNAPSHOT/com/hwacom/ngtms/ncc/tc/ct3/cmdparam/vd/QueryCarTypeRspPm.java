/*    */ package com.hwacom.ngtms.ncc.tc.ct3.cmdparam.vd;
/*    */ 
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParam;
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParams;
/*    */ import java.io.Externalizable;
/*    */ import java.io.IOException;
/*    */ import java.io.ObjectInput;
/*    */ import java.io.ObjectOutput;
/*    */ 
/*    */ 
/*    */ @CommandParams(cmdName="queryCarTypeRsp")
/*    */ public class QueryCarTypeRspPm
/*    */   implements Externalizable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   public static final int cmdId = 28641;
/*    */   public static final String cmdName = "queryCarTypeRsp";
/*    */   @CommandParam(name="bigCarLength")
/*    */   public int bigCarLength;
/*    */   @CommandParam(name="carLength")
/*    */   public int carLength;
/*    */   
/*    */   public void writeExternal(ObjectOutput out)
/*    */     throws IOException
/*    */   {
/* 26 */     out.writeShort(this.bigCarLength);
/* 27 */     out.writeShort(this.carLength);
/*    */   }
/*    */   
/*    */   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*    */   {
/* 32 */     this.bigCarLength = (in.readShort() & 0xFFFF);
/* 33 */     this.carLength = (in.readShort() & 0xFFFF);
/*    */   }
/*    */   
/*    */   public String toString() {
/* 37 */     StringBuilder sb = new StringBuilder();
/* 38 */     sb.append('[');
/* 39 */     sb.append("bigCarLength: ").append(this.bigCarLength).append(", ");
/* 40 */     sb.append("carLength: ").append(this.carLength).append(']');
/* 41 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\ct3\cmdparam\vd\QueryCarTypeRspPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */