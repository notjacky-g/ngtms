/*    */ package com.hwacom.ngtms.ncc.tc.ct3.cmdparam.cms;
/*    */ 
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParam;
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParams;
/*    */ import java.io.Externalizable;
/*    */ import java.io.IOException;
/*    */ import java.io.ObjectInput;
/*    */ import java.io.ObjectOutput;
/*    */ 
/*    */ 
/*    */ @CommandParams(cmdName="queryShowPatternReq")
/*    */ public class QueryShowPatternReqPm
/*    */   implements Externalizable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   public static final int cmdId = 44901;
/*    */   public static final String cmdName = "queryShowPatternReq";
/*    */   @CommandParam(name="patternId")
/*    */   public int patternId;
/*    */   @CommandParam(name="zoneNo")
/*    */   public int zoneNo;
/*    */   
/*    */   public void writeExternal(ObjectOutput out)
/*    */     throws IOException
/*    */   {
/* 26 */     out.writeByte(this.patternId);
/* 27 */     out.writeByte(this.zoneNo);
/*    */   }
/*    */   
/*    */   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*    */   {
/* 32 */     this.patternId = (in.readByte() & 0xFF);
/* 33 */     this.zoneNo = (in.readByte() & 0xFF);
/*    */   }
/*    */   
/*    */   public String toString() {
/* 37 */     StringBuilder sb = new StringBuilder();
/* 38 */     sb.append('[');
/* 39 */     sb.append("patternId: ").append(this.patternId).append(", ");
/* 40 */     sb.append("zoneNo: ").append(this.zoneNo).append(']');
/* 41 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\ct3\cmdparam\cms\QueryShowPatternReqPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */