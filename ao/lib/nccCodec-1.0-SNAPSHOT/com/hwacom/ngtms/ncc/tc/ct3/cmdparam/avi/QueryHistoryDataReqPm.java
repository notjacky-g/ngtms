/*    */ package com.hwacom.ngtms.ncc.tc.ct3.cmdparam.avi;
/*    */ 
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParam;
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParams;
/*    */ import java.io.Externalizable;
/*    */ import java.io.IOException;
/*    */ import java.io.ObjectInput;
/*    */ import java.io.ObjectOutput;
/*    */ 
/*    */ 
/*    */ @CommandParams(cmdName="queryHistoryDataReq")
/*    */ public class QueryHistoryDataReqPm
/*    */   implements Externalizable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   public static final int cmdId = 58433;
/*    */   public static final String cmdName = "queryHistoryDataReq";
/*    */   @CommandParam(name="startTime")
/*    */   public StartTimePm startTimePm;
/*    */   @CommandParam(name="endTime")
/*    */   public EndTimePm endTimePm;
/*    */   
/*    */   public void writeExternal(ObjectOutput out)
/*    */     throws IOException
/*    */   {
/* 26 */     out.writeObject(this.startTimePm);
/* 27 */     out.writeObject(this.endTimePm);
/*    */   }
/*    */   
/*    */   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*    */   {
/* 32 */     this.startTimePm = ((StartTimePm)in.readObject());
/* 33 */     this.endTimePm = ((EndTimePm)in.readObject());
/*    */   }
/*    */   
/*    */   public String toString() {
/* 37 */     StringBuilder sb = new StringBuilder();
/* 38 */     sb.append('[');
/* 39 */     sb.append("startTimePm: ").append(this.startTimePm).append(", ");
/* 40 */     sb.append("endTimePm: ").append(this.endTimePm).append(']');
/* 41 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\ct3\cmdparam\avi\QueryHistoryDataReqPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */