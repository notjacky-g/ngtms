/*    */ package com.hwacom.ngtms.ncc.tc.fw2.cmdparam.ettu;
/*    */ 
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.BytesUtility;
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParam;
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParams;
/*    */ import java.io.Externalizable;
/*    */ import java.io.IOException;
/*    */ import java.io.ObjectInput;
/*    */ import java.io.ObjectOutput;
/*    */ 
/*    */ 
/*    */ 
/*    */ @CommandParams(cmdName="getEttuSunshineTableReq")
/*    */ public class GetEttuSunshineTableReqPm
/*    */   implements Externalizable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   public static final int cmdId = 10018;
/*    */   public static final String cmdName = "getEttuSunshineTableReq";
/*    */   @CommandParam(name="monthString")
/*    */   public byte[] monthString;
/*    */   @CommandParam(name="dayString")
/*    */   public byte[] dayString;
/*    */   @CommandParam(name="lrc")
/*    */   public int lrc;
/*    */   @CommandParam(name="endByty")
/* 27 */   public static final byte[] endByty = { -57 };
/*    */   
/*    */   public void writeExternal(ObjectOutput out) throws IOException
/*    */   {
/* 31 */     out.writeObject(this.monthString);
/* 32 */     out.writeObject(this.dayString);
/* 33 */     out.writeShort(this.lrc);
/*    */   }
/*    */   
/*    */   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*    */   {
/* 38 */     this.monthString = ((byte[])in.readObject());
/* 39 */     this.dayString = ((byte[])in.readObject());
/* 40 */     this.lrc = (in.readShort() & 0xFFFF);
/*    */   }
/*    */   
/*    */   public String toString() {
/* 44 */     StringBuilder sb = new StringBuilder();
/* 45 */     sb.append('[');
/* 46 */     sb.append("monthString: ")
/* 47 */       .append(BytesUtility.toHexString(this.monthString))
/* 48 */       .append(", ");
/* 49 */     sb.append("dayString: ")
/* 50 */       .append(BytesUtility.toHexString(this.dayString))
/* 51 */       .append(", ");
/* 52 */     sb.append("lrc: ").append(this.lrc).append(", ");
/* 53 */     sb.append("endByty: ")
/* 54 */       .append(BytesUtility.toHexString(endByty))
/* 55 */       .append(']');
/* 56 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\fw2\cmdparam\ettu\GetEttuSunshineTableReqPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */