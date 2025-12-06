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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @CommandParams(cmdName="getEttuSunshineTableRsp")
/*    */ public class GetEttuSunshineTableRspPm
/*    */   implements Externalizable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   public static final int cmdId = 10530;
/*    */   public static final String cmdName = "getEttuSunshineTableRsp";
/*    */   @CommandParam(name="monthString")
/*    */   public byte[] monthString;
/*    */   @CommandParam(name="dayString")
/*    */   public byte[] dayString;
/*    */   @CommandParam(name="sunriseHourString")
/*    */   public byte[] sunriseHourString;
/*    */   @CommandParam(name="sunriseMinuteString")
/*    */   public byte[] sunriseMinuteString;
/*    */   @CommandParam(name="sunsetHourString")
/*    */   public byte[] sunsetHourString;
/*    */   @CommandParam(name="sunsetMinuteString")
/*    */   public byte[] sunsetMinuteString;
/*    */   @CommandParam(name="lrc")
/*    */   public int lrc;
/*    */   @CommandParam(name="endByty")
/* 39 */   public static final byte[] endByty = { -57 };
/*    */   
/*    */   public void writeExternal(ObjectOutput out) throws IOException
/*    */   {
/* 43 */     out.writeObject(this.monthString);
/* 44 */     out.writeObject(this.dayString);
/* 45 */     out.writeObject(this.sunriseHourString);
/* 46 */     out.writeObject(this.sunriseMinuteString);
/* 47 */     out.writeObject(this.sunsetHourString);
/* 48 */     out.writeObject(this.sunsetMinuteString);
/* 49 */     out.writeShort(this.lrc);
/*    */   }
/*    */   
/*    */   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*    */   {
/* 54 */     this.monthString = ((byte[])in.readObject());
/* 55 */     this.dayString = ((byte[])in.readObject());
/* 56 */     this.sunriseHourString = ((byte[])in.readObject());
/* 57 */     this.sunriseMinuteString = ((byte[])in.readObject());
/* 58 */     this.sunsetHourString = ((byte[])in.readObject());
/* 59 */     this.sunsetMinuteString = ((byte[])in.readObject());
/* 60 */     this.lrc = (in.readShort() & 0xFFFF);
/*    */   }
/*    */   
/*    */   public String toString() {
/* 64 */     StringBuilder sb = new StringBuilder();
/* 65 */     sb.append('[');
/* 66 */     sb.append("monthString: ")
/* 67 */       .append(BytesUtility.toHexString(this.monthString))
/* 68 */       .append(", ");
/* 69 */     sb.append("dayString: ")
/* 70 */       .append(BytesUtility.toHexString(this.dayString))
/* 71 */       .append(", ");
/* 72 */     sb.append("sunriseHourString: ")
/* 73 */       .append(BytesUtility.toHexString(this.sunriseHourString))
/* 74 */       .append(", ");
/* 75 */     sb.append("sunriseMinuteString: ")
/* 76 */       .append(BytesUtility.toHexString(this.sunriseMinuteString))
/* 77 */       .append(", ");
/* 78 */     sb.append("sunsetHourString: ")
/* 79 */       .append(BytesUtility.toHexString(this.sunsetHourString))
/* 80 */       .append(", ");
/* 81 */     sb.append("sunsetMinuteString: ")
/* 82 */       .append(BytesUtility.toHexString(this.sunsetMinuteString))
/* 83 */       .append(", ");
/* 84 */     sb.append("lrc: ").append(this.lrc).append(", ");
/* 85 */     sb.append("endByty: ")
/* 86 */       .append(BytesUtility.toHexString(endByty))
/* 87 */       .append(']');
/* 88 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\fw2\cmdparam\ettu\GetEttuSunshineTableRspPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */