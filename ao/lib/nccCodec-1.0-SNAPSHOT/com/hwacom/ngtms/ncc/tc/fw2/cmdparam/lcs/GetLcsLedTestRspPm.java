/*    */ package com.hwacom.ngtms.ncc.tc.fw2.cmdparam.lcs;
/*    */ 
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.BytesUtility;
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParam;
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParams;
/*    */ import com.hwacom.ngtms.ncc.tc.fw2.cmdparam.HwStatusPm;
/*    */ import java.io.Externalizable;
/*    */ import java.io.IOException;
/*    */ import java.io.ObjectInput;
/*    */ import java.io.ObjectOutput;
/*    */ import java.util.List;
/*    */ 
/*    */ @CommandParams(cmdName="getLcsLedTestRsp")
/*    */ public class GetLcsLedTestRspPm
/*    */   implements Externalizable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   public static final int cmdId = 197;
/*    */   public static final String cmdName = "getLcsLedTestRsp";
/*    */   @CommandParam(name="hwStatus")
/*    */   public HwStatusPm hwStatusPm;
/*    */   @CommandParam(name="signStatusList")
/*    */   public List<SignStatusListItem> signStatusList;
/*    */   
/*    */   public static class SignStatusListItem implements Externalizable
/*    */   {
/*    */     private static final long serialVersionUID = 1L;
/*    */     @CommandParam(name="signNo")
/*    */     public int signNo;
/*    */     @CommandParam(name="ledStatus")
/*    */     public byte[] ledStatus;
/*    */     
/*    */     public void writeExternal(ObjectOutput out) throws IOException
/*    */     {
/* 35 */       out.writeByte(this.signNo);
/* 36 */       out.writeObject(this.ledStatus);
/*    */     }
/*    */     
/*    */     public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*    */     {
/* 41 */       this.signNo = (in.readByte() & 0xFF);
/* 42 */       this.ledStatus = ((byte[])in.readObject());
/*    */     }
/*    */     
/*    */     public String toString() {
/* 46 */       StringBuilder sb = new StringBuilder();
/* 47 */       sb.append('[');
/* 48 */       sb.append("signNo: ").append(this.signNo).append(", ");
/* 49 */       sb.append("ledStatus: ")
/* 50 */         .append(BytesUtility.toHexString(this.ledStatus))
/* 51 */         .append(']');
/* 52 */       return sb.toString();
/*    */     }
/*    */   }
/*    */   
/*    */   public void writeExternal(ObjectOutput out) throws IOException
/*    */   {
/* 58 */     out.writeObject(this.hwStatusPm);
/* 59 */     out.writeObject(this.signStatusList);
/*    */   }
/*    */   
/*    */   public void readExternal(ObjectInput in)
/*    */     throws IOException, ClassNotFoundException
/*    */   {
/* 65 */     this.hwStatusPm = ((HwStatusPm)in.readObject());
/* 66 */     this.signStatusList = ((List)in.readObject());
/*    */   }
/*    */   
/*    */   public String toString() {
/* 70 */     StringBuilder sb = new StringBuilder();
/* 71 */     sb.append('[');
/* 72 */     sb.append("hwStatusPm: ").append(this.hwStatusPm).append(", ");
/* 73 */     sb.append("signStatusList: ").append(this.signStatusList).append(']');
/* 74 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\fw2\cmdparam\lcs\GetLcsLedTestRspPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */