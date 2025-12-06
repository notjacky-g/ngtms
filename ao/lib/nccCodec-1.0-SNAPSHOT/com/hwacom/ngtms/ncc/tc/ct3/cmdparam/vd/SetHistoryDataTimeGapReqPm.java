/*    */ package com.hwacom.ngtms.ncc.tc.ct3.cmdparam.vd;
/*    */ 
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParam;
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParams;
/*    */ import java.io.Externalizable;
/*    */ import java.io.IOException;
/*    */ import java.io.ObjectInput;
/*    */ import java.io.ObjectOutput;
/*    */ 
/*    */ @CommandParams(cmdName="setHistoryDataTimeGapReq")
/*    */ public class SetHistoryDataTimeGapReqPm
/*    */   implements Externalizable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   public static final int cmdId = 28436;
/*    */   public static final String cmdName = "setHistoryDataTimeGapReq";
/*    */   @CommandParam(name="timeGap")
/*    */   public int timeGap;
/*    */   
/*    */   public void writeExternal(ObjectOutput out)
/*    */     throws IOException
/*    */   {
/* 23 */     out.writeByte(this.timeGap);
/*    */   }
/*    */   
/*    */   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*    */   {
/* 28 */     this.timeGap = (in.readByte() & 0xFF);
/*    */   }
/*    */   
/*    */   public String toString() {
/* 32 */     StringBuilder sb = new StringBuilder();
/* 33 */     sb.append('[');
/* 34 */     sb.append("timeGap: ").append(this.timeGap).append(']');
/* 35 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\ct3\cmdparam\vd\SetHistoryDataTimeGapReqPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */