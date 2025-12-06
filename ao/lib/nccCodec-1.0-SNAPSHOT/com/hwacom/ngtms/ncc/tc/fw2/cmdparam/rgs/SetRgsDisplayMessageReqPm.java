/*    */ package com.hwacom.ngtms.ncc.tc.fw2.cmdparam.rgs;
/*    */ 
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParam;
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParams;
/*    */ import java.io.Externalizable;
/*    */ import java.io.IOException;
/*    */ import java.io.ObjectInput;
/*    */ import java.io.ObjectOutput;
/*    */ 
/*    */ 
/*    */ 
/*    */ @CommandParams(cmdName="setRgsDisplayMessageReq")
/*    */ public class SetRgsDisplayMessageReqPm
/*    */   implements Externalizable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   public static final int cmdId = 149;
/*    */   public static final String cmdName = "setRgsDisplayMessageReq";
/*    */   @CommandParam(name="gCodeId")
/*    */   public int gCodeId;
/*    */   @CommandParam(name="rgsTextContent")
/*    */   public RgsTextContentPm rgsTextContentPm;
/*    */   
/*    */   public void writeExternal(ObjectOutput out)
/*    */     throws IOException
/*    */   {
/* 27 */     out.writeByte(this.gCodeId);
/* 28 */     out.writeObject(this.rgsTextContentPm);
/*    */   }
/*    */   
/*    */   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*    */   {
/* 33 */     this.gCodeId = (in.readByte() & 0xFF);
/* 34 */     this.rgsTextContentPm = ((RgsTextContentPm)in.readObject());
/*    */   }
/*    */   
/*    */   public String toString() {
/* 38 */     StringBuilder sb = new StringBuilder();
/* 39 */     sb.append('[');
/* 40 */     sb.append("gCodeId: ").append(this.gCodeId).append(", ");
/* 41 */     sb.append("rgsTextContentPm: ").append(this.rgsTextContentPm).append(']');
/* 42 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\fw2\cmdparam\rgs\SetRgsDisplayMessageReqPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */