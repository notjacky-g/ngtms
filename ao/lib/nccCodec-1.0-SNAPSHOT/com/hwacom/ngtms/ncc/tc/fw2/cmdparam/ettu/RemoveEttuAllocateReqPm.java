/*    */ package com.hwacom.ngtms.ncc.tc.fw2.cmdparam.ettu;
/*    */ 
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParam;
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParams;
/*    */ import java.io.Externalizable;
/*    */ import java.io.IOException;
/*    */ import java.io.ObjectInput;
/*    */ import java.io.ObjectOutput;
/*    */ 
/*    */ 
/*    */ @CommandParams(cmdName="removeEttuAllocateReq")
/*    */ public class RemoveEttuAllocateReqPm
/*    */   implements Externalizable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   public static final int cmdId = 10011;
/*    */   public static final String cmdName = "removeEttuAllocateReq";
/*    */   @CommandParam(name="phoneNumber")
/*    */   public PhoneNumberPm phoneNumberPm;
/*    */   @CommandParam(name="lrc")
/*    */   public int lrc;
/*    */   @CommandParam(name="endByte")
/*    */   public int endByte;
/*    */   
/*    */   public void writeExternal(ObjectOutput out)
/*    */     throws IOException
/*    */   {
/* 28 */     out.writeObject(this.phoneNumberPm);
/* 29 */     out.writeShort(this.lrc);
/* 30 */     out.writeByte(this.endByte);
/*    */   }
/*    */   
/*    */   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*    */   {
/* 35 */     this.phoneNumberPm = ((PhoneNumberPm)in.readObject());
/* 36 */     this.lrc = (in.readShort() & 0xFFFF);
/* 37 */     this.endByte = (in.readByte() & 0xFF);
/*    */   }
/*    */   
/*    */   public String toString() {
/* 41 */     StringBuilder sb = new StringBuilder();
/* 42 */     sb.append('[');
/* 43 */     sb.append("phoneNumberPm: ").append(this.phoneNumberPm).append(", ");
/* 44 */     sb.append("lrc: ").append(this.lrc).append(", ");
/* 45 */     sb.append("endByte: ").append(this.endByte).append(']');
/* 46 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\fw2\cmdparam\ettu\RemoveEttuAllocateReqPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */