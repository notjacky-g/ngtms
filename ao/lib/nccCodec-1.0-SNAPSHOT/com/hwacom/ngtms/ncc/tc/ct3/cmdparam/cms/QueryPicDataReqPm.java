/*    */ package com.hwacom.ngtms.ncc.tc.ct3.cmdparam.cms;
/*    */ 
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParam;
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParams;
/*    */ import java.io.Externalizable;
/*    */ import java.io.IOException;
/*    */ import java.io.ObjectInput;
/*    */ import java.io.ObjectOutput;
/*    */ 
/*    */ @CommandParams(cmdName="queryPicDataReq")
/*    */ public class QueryPicDataReqPm
/*    */   implements Externalizable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   public static final int cmdId = 44888;
/*    */   public static final String cmdName = "queryPicDataReq";
/*    */   @CommandParam(name="patternCode")
/*    */   public int patternCode;
/*    */   
/*    */   public void writeExternal(ObjectOutput out)
/*    */     throws IOException
/*    */   {
/* 23 */     out.writeShort(this.patternCode);
/*    */   }
/*    */   
/*    */   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*    */   {
/* 28 */     this.patternCode = (in.readShort() & 0xFFFF);
/*    */   }
/*    */   
/*    */   public String toString() {
/* 32 */     StringBuilder sb = new StringBuilder();
/* 33 */     sb.append('[');
/* 34 */     sb.append("patternCode: ").append(this.patternCode).append(']');
/* 35 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\ct3\cmdparam\cms\QueryPicDataReqPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */