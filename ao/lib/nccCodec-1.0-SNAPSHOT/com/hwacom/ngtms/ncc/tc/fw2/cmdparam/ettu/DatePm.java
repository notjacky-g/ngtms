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
/*    */ @GlobalParams(paramsName="date")
/*    */ public class DatePm
/*    */   implements Externalizable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   @CommandParam(name="monthString")
/*    */   public byte[] monthString;
/*    */   @CommandParam(name="dayString")
/*    */   public byte[] dayString;
/*    */   @CommandParam(name="yearString")
/*    */   public byte[] yearString;
/*    */   
/*    */   public void writeExternal(ObjectOutput out)
/*    */     throws IOException
/*    */   {
/* 26 */     out.writeObject(this.monthString);
/* 27 */     out.writeObject(this.dayString);
/* 28 */     out.writeObject(this.yearString);
/*    */   }
/*    */   
/*    */   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*    */   {
/* 33 */     this.monthString = ((byte[])in.readObject());
/* 34 */     this.dayString = ((byte[])in.readObject());
/* 35 */     this.yearString = ((byte[])in.readObject());
/*    */   }
/*    */   
/*    */   public String toString() {
/* 39 */     StringBuilder sb = new StringBuilder();
/* 40 */     sb.append('[');
/* 41 */     sb.append("monthString: ")
/* 42 */       .append(BytesUtility.toHexString(this.monthString))
/* 43 */       .append(", ");
/* 44 */     sb.append("dayString: ")
/* 45 */       .append(BytesUtility.toHexString(this.dayString))
/* 46 */       .append(", ");
/* 47 */     sb.append("yearString: ")
/* 48 */       .append(BytesUtility.toHexString(this.yearString))
/* 49 */       .append(']');
/* 50 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\fw2\cmdparam\ettu\DatePm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */