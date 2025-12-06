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
/*    */ @GlobalParams(paramsName="time")
/*    */ public class TimePm
/*    */   implements Externalizable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   @CommandParam(name="hourString")
/*    */   public byte[] hourString;
/*    */   @CommandParam(name="minuteString")
/*    */   public byte[] minuteString;
/*    */   @CommandParam(name="secondString")
/*    */   public byte[] secondString;
/*    */   
/*    */   public void writeExternal(ObjectOutput out)
/*    */     throws IOException
/*    */   {
/* 26 */     out.writeObject(this.hourString);
/* 27 */     out.writeObject(this.minuteString);
/* 28 */     out.writeObject(this.secondString);
/*    */   }
/*    */   
/*    */   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*    */   {
/* 33 */     this.hourString = ((byte[])in.readObject());
/* 34 */     this.minuteString = ((byte[])in.readObject());
/* 35 */     this.secondString = ((byte[])in.readObject());
/*    */   }
/*    */   
/*    */   public String toString() {
/* 39 */     StringBuilder sb = new StringBuilder();
/* 40 */     sb.append('[');
/* 41 */     sb.append("hourString: ")
/* 42 */       .append(BytesUtility.toHexString(this.hourString))
/* 43 */       .append(", ");
/* 44 */     sb.append("minuteString: ")
/* 45 */       .append(BytesUtility.toHexString(this.minuteString))
/* 46 */       .append(", ");
/* 47 */     sb.append("secondString: ")
/* 48 */       .append(BytesUtility.toHexString(this.secondString))
/* 49 */       .append(']');
/* 50 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\fw2\cmdparam\ettu\TimePm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */