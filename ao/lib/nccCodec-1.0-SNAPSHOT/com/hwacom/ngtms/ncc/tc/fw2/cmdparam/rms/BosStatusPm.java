/*    */ package com.hwacom.ngtms.ncc.tc.fw2.cmdparam.rms;
/*    */ 
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParam;
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.GlobalParams;
/*    */ import java.io.Externalizable;
/*    */ import java.io.IOException;
/*    */ import java.io.ObjectInput;
/*    */ import java.io.ObjectOutput;
/*    */ 
/*    */ 
/*    */ 
/*    */ @GlobalParams(paramsName="bosStatus")
/*    */ public class BosStatusPm
/*    */   implements Externalizable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   @CommandParam(name="warnSet")
/*    */   public int warnSet;
/*    */   @CommandParam(name="warnStatus")
/*    */   public int warnStatus;
/*    */   @CommandParam(name="warnMessageId")
/*    */   public int warnMessageId;
/*    */   
/*    */   public void writeExternal(ObjectOutput out)
/*    */     throws IOException
/*    */   {
/* 27 */     out.writeByte(this.warnSet);
/* 28 */     out.writeByte(this.warnStatus);
/* 29 */     out.writeByte(this.warnMessageId);
/*    */   }
/*    */   
/*    */   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*    */   {
/* 34 */     this.warnSet = (in.readByte() & 0xFF);
/* 35 */     this.warnStatus = (in.readByte() & 0xFF);
/* 36 */     this.warnMessageId = (in.readByte() & 0xFF);
/*    */   }
/*    */   
/*    */   public String toString() {
/* 40 */     StringBuilder sb = new StringBuilder();
/* 41 */     sb.append('[');
/* 42 */     sb.append("warnSet: ").append(this.warnSet).append(", ");
/* 43 */     sb.append("warnStatus: ").append(this.warnStatus).append(", ");
/* 44 */     sb.append("warnMessageId: ").append(this.warnMessageId).append(']');
/* 45 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\fw2\cmdparam\rms\BosStatusPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */