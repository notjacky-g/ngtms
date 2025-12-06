/*    */ package com.hwacom.ngtms.ncc.tc.fw2.cmdparam.qld;
/*    */ 
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParam;
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParams;
/*    */ import com.hwacom.ngtms.ncc.tc.fw2.cmdparam.DhmPm;
/*    */ import com.hwacom.ngtms.ncc.tc.fw2.cmdparam.HwStatusPm;
/*    */ import java.io.Externalizable;
/*    */ import java.io.IOException;
/*    */ import java.io.ObjectInput;
/*    */ import java.io.ObjectOutput;
/*    */ 
/*    */ @CommandParams(cmdName="getQldDataRsp")
/*    */ public class GetQldDataRspPm
/*    */   implements Externalizable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   public static final int cmdId = 24322;
/*    */   public static final String cmdName = "getQldDataRsp";
/*    */   @CommandParam(name="hwStatus")
/*    */   public HwStatusPm hwStatusPm;
/*    */   @CommandParam(name="dhm")
/*    */   public DhmPm dhmPm;
/*    */   @CommandParam(name="occData")
/*    */   public int occData;
/*    */   
/*    */   public void writeExternal(ObjectOutput out)
/*    */     throws IOException
/*    */   {
/* 29 */     out.writeObject(this.hwStatusPm);
/* 30 */     out.writeObject(this.dhmPm);
/* 31 */     out.writeByte(this.occData);
/*    */   }
/*    */   
/*    */   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*    */   {
/* 36 */     this.hwStatusPm = ((HwStatusPm)in.readObject());
/* 37 */     this.dhmPm = ((DhmPm)in.readObject());
/* 38 */     this.occData = (in.readByte() & 0xFF);
/*    */   }
/*    */   
/*    */   public String toString() {
/* 42 */     StringBuilder sb = new StringBuilder();
/* 43 */     sb.append('[');
/* 44 */     sb.append("hwStatusPm: ").append(this.hwStatusPm).append(", ");
/* 45 */     sb.append("dhmPm: ").append(this.dhmPm).append(", ");
/* 46 */     sb.append("occData: ").append(this.occData).append(']');
/* 47 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\fw2\cmdparam\qld\GetQldDataRspPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */