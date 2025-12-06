/*    */ package com.hwacom.ngtms.ncc.tc.fw2.cmdparam.ettu;
/*    */ 
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.BytesUtility;
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParam;
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.GlobalParams;
/*    */ import java.io.Externalizable;
/*    */ import java.io.IOException;
/*    */ import java.io.ObjectInput;
/*    */ import java.io.ObjectOutput;
/*    */ 
/*    */ @GlobalParams(paramsName="phoneNumber")
/*    */ public class PhoneNumberPm implements Externalizable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   @CommandParam(name="numberString")
/*    */   public byte[] numberString;
/*    */   
/*    */   public void writeExternal(ObjectOutput out) throws IOException
/*    */   {
/* 20 */     out.writeObject(this.numberString);
/*    */   }
/*    */   
/*    */   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*    */   {
/* 25 */     this.numberString = ((byte[])in.readObject());
/*    */   }
/*    */   
/*    */   public String toString() {
/* 29 */     StringBuilder sb = new StringBuilder();
/* 30 */     sb.append('[');
/* 31 */     sb.append("numberString: ")
/* 32 */       .append(BytesUtility.toHexString(this.numberString))
/* 33 */       .append(']');
/* 34 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\fw2\cmdparam\ettu\PhoneNumberPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */