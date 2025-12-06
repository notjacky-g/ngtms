/*    */ package com.hwacom.ngtms.ncc.tc.fw2.cmdparam;
/*    */ 
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.BytesUtility;
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParam;
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParams;
/*    */ import java.io.Externalizable;
/*    */ import java.io.IOException;
/*    */ import java.io.ObjectInput;
/*    */ import java.io.ObjectOutput;
/*    */ 
/*    */ @CommandParams(cmdName="requestDbReport")
/*    */ public class RequestDbReportPm
/*    */   implements Externalizable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   public static final int cmdId = 13;
/*    */   public static final String cmdName = "requestDbReport";
/*    */   @CommandParam(name="hwStatus")
/*    */   public HwStatusPm hwStatusPm;
/*    */   @CommandParam(name="protocolCode")
/*    */   public byte[] protocolCode;
/*    */   
/*    */   public void writeExternal(ObjectOutput out) throws IOException
/*    */   {
/* 25 */     out.writeObject(this.hwStatusPm);
/* 26 */     out.writeObject(this.protocolCode);
/*    */   }
/*    */   
/*    */   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*    */   {
/* 31 */     this.hwStatusPm = ((HwStatusPm)in.readObject());
/* 32 */     this.protocolCode = ((byte[])in.readObject());
/*    */   }
/*    */   
/*    */   public String toString() {
/* 36 */     StringBuilder sb = new StringBuilder();
/* 37 */     sb.append('[');
/* 38 */     sb.append("hwStatusPm: ").append(this.hwStatusPm).append(", ");
/* 39 */     sb.append("protocolCode: ")
/* 40 */       .append(BytesUtility.toHexString(this.protocolCode))
/* 41 */       .append(']');
/* 42 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\fw2\cmdparam\RequestDbReportPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */