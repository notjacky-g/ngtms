/*    */ package com.hwacom.ngtms.ncc.tc.fw2.cmdparam.vd;
/*    */ 
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParam;
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParams;
/*    */ import java.io.Externalizable;
/*    */ import java.io.IOException;
/*    */ import java.io.ObjectInput;
/*    */ import java.io.ObjectOutput;
/*    */ 
/*    */ 
/*    */ @CommandParams(cmdName="setCarTypeCfgReq")
/*    */ public class SetCarTypeCfgReqPm
/*    */   implements Externalizable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   public static final int cmdId = 18;
/*    */   public static final String cmdName = "setCarTypeCfgReq";
/*    */   @CommandParam(name="smallCarMaxLength")
/*    */   public int smallCarMaxLength;
/*    */   @CommandParam(name="bigCarMaxLength")
/*    */   public int bigCarMaxLength;
/*    */   
/*    */   public void writeExternal(ObjectOutput out)
/*    */     throws IOException
/*    */   {
/* 26 */     out.writeByte(this.smallCarMaxLength);
/* 27 */     out.writeByte(this.bigCarMaxLength);
/*    */   }
/*    */   
/*    */   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*    */   {
/* 32 */     this.smallCarMaxLength = (in.readByte() & 0xFF);
/* 33 */     this.bigCarMaxLength = (in.readByte() & 0xFF);
/*    */   }
/*    */   
/*    */   public String toString() {
/* 37 */     StringBuilder sb = new StringBuilder();
/* 38 */     sb.append('[');
/* 39 */     sb.append("smallCarMaxLength: ").append(this.smallCarMaxLength).append(", ");
/* 40 */     sb.append("bigCarMaxLength: ").append(this.bigCarMaxLength).append(']');
/* 41 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\fw2\cmdparam\vd\SetCarTypeCfgReqPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */