/*    */ package com.hwacom.ngtms.ncc.tc.ct3.cmdparam.vd;
/*    */ 
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParam;
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParams;
/*    */ import com.hwacom.ngtms.ncc.tc.ct3.cmdparam.HmPm;
/*    */ import java.io.Externalizable;
/*    */ import java.io.IOException;
/*    */ import java.io.ObjectInput;
/*    */ import java.io.ObjectOutput;
/*    */ 
/*    */ 
/*    */ @CommandParams(cmdName="setReportActionReq")
/*    */ public class SetReportActionReqPm
/*    */   implements Externalizable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   public static final int cmdId = 28433;
/*    */   public static final String cmdName = "setReportActionReq";
/*    */   @CommandParam(name="dataCount")
/*    */   public int dataCount;
/*    */   @CommandParam(name="dataSeqNo")
/*    */   public int dataSeqNo;
/*    */   @CommandParam(name="hm")
/*    */   public HmPm hmPm;
/*    */   
/*    */   public void writeExternal(ObjectOutput out)
/*    */     throws IOException
/*    */   {
/* 29 */     out.writeByte(this.dataCount);
/* 30 */     out.writeShort(this.dataSeqNo);
/* 31 */     out.writeObject(this.hmPm);
/*    */   }
/*    */   
/*    */   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*    */   {
/* 36 */     this.dataCount = (in.readByte() & 0xFF);
/* 37 */     this.dataSeqNo = (in.readShort() & 0xFFFF);
/* 38 */     this.hmPm = ((HmPm)in.readObject());
/*    */   }
/*    */   
/*    */   public String toString() {
/* 42 */     StringBuilder sb = new StringBuilder();
/* 43 */     sb.append('[');
/* 44 */     sb.append("dataCount: ").append(this.dataCount).append(", ");
/* 45 */     sb.append("dataSeqNo: ").append(this.dataSeqNo).append(", ");
/* 46 */     sb.append("hmPm: ").append(this.hmPm).append(']');
/* 47 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\ct3\cmdparam\vd\SetReportActionReqPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */