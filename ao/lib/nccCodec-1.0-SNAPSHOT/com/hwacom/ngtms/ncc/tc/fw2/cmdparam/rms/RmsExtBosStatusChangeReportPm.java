/*    */ package com.hwacom.ngtms.ncc.tc.fw2.cmdparam.rms;
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
/*    */ @CommandParams(cmdName="rmsExtBosStatusChangeReport")
/*    */ public class RmsExtBosStatusChangeReportPm
/*    */   implements Externalizable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   public static final int cmdId = 174;
/*    */   public static final String cmdName = "rmsExtBosStatusChangeReport";
/*    */   @CommandParam(name="bosId")
/*    */   public int bosId;
/*    */   @CommandParam(name="warnStatus")
/*    */   public int warnStatus;
/*    */   @CommandParam(name="warnMessageId")
/*    */   public int warnMessageId;
/*    */   @CommandParam(name="message")
/*    */   public byte[] message;
/*    */   
/*    */   public void writeExternal(ObjectOutput out)
/*    */     throws IOException
/*    */   {
/* 32 */     out.writeByte(this.bosId);
/* 33 */     out.writeByte(this.warnStatus);
/* 34 */     out.writeByte(this.warnMessageId);
/* 35 */     out.writeObject(this.message);
/*    */   }
/*    */   
/*    */   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*    */   {
/* 40 */     this.bosId = (in.readByte() & 0xFF);
/* 41 */     this.warnStatus = (in.readByte() & 0xFF);
/* 42 */     this.warnMessageId = (in.readByte() & 0xFF);
/* 43 */     this.message = ((byte[])in.readObject());
/*    */   }
/*    */   
/*    */   public String toString() {
/* 47 */     StringBuilder sb = new StringBuilder();
/* 48 */     sb.append('[');
/* 49 */     sb.append("bosId: ").append(this.bosId).append(", ");
/* 50 */     sb.append("warnStatus: ").append(this.warnStatus).append(", ");
/* 51 */     sb.append("warnMessageId: ").append(this.warnMessageId).append(", ");
/* 52 */     sb.append("message: ")
/* 53 */       .append(BytesUtility.toHexString(this.message))
/* 54 */       .append(']');
/* 55 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\fw2\cmdparam\rms\RmsExtBosStatusChangeReportPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */