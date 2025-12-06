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
/*    */ @CommandParams(cmdName="setEttuSunshineTableReq")
/*    */ public class SetEttuSunshineTableReqPm
/*    */   implements Externalizable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   public static final int cmdId = 10017;
/*    */   public static final String cmdName = "setEttuSunshineTableReq";
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
/*    */   @CommandParam(name="endByte")
/*    */   public int endByte;
/*    */   
/*    */   public void writeExternal(ObjectOutput out)
/*    */     throws IOException
/*    */   {
/* 43 */     out.writeObject(this.monthString);
/* 44 */     out.writeObject(this.dayString);
/* 45 */     out.writeObject(this.sunriseHourString);
/* 46 */     out.writeObject(this.sunriseMinuteString);
/* 47 */     out.writeObject(this.sunsetHourString);
/* 48 */     out.writeObject(this.sunsetMinuteString);
/* 49 */     out.writeShort(this.lrc);
/* 50 */     out.writeByte(this.endByte);
/*    */   }
/*    */   
/*    */   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*    */   {
/* 55 */     this.monthString = ((byte[])in.readObject());
/* 56 */     this.dayString = ((byte[])in.readObject());
/* 57 */     this.sunriseHourString = ((byte[])in.readObject());
/* 58 */     this.sunriseMinuteString = ((byte[])in.readObject());
/* 59 */     this.sunsetHourString = ((byte[])in.readObject());
/* 60 */     this.sunsetMinuteString = ((byte[])in.readObject());
/* 61 */     this.lrc = (in.readShort() & 0xFFFF);
/* 62 */     this.endByte = (in.readByte() & 0xFF);
/*    */   }
/*    */   
/*    */   public String toString() {
/* 66 */     StringBuilder sb = new StringBuilder();
/* 67 */     sb.append('[');
/* 68 */     sb.append("monthString: ")
/* 69 */       .append(BytesUtility.toHexString(this.monthString))
/* 70 */       .append(", ");
/* 71 */     sb.append("dayString: ")
/* 72 */       .append(BytesUtility.toHexString(this.dayString))
/* 73 */       .append(", ");
/* 74 */     sb.append("sunriseHourString: ")
/* 75 */       .append(BytesUtility.toHexString(this.sunriseHourString))
/* 76 */       .append(", ");
/* 77 */     sb.append("sunriseMinuteString: ")
/* 78 */       .append(BytesUtility.toHexString(this.sunriseMinuteString))
/* 79 */       .append(", ");
/* 80 */     sb.append("sunsetHourString: ")
/* 81 */       .append(BytesUtility.toHexString(this.sunsetHourString))
/* 82 */       .append(", ");
/* 83 */     sb.append("sunsetMinuteString: ")
/* 84 */       .append(BytesUtility.toHexString(this.sunsetMinuteString))
/* 85 */       .append(", ");
/* 86 */     sb.append("lrc: ").append(this.lrc).append(", ");
/* 87 */     sb.append("endByte: ").append(this.endByte).append(']');
/* 88 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\fw2\cmdparam\ettu\SetEttuSunshineTableReqPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */