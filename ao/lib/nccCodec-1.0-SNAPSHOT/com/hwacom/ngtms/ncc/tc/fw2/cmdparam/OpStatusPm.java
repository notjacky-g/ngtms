/*    */ package com.hwacom.ngtms.ncc.tc.fw2.cmdparam;
/*    */ 
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParam;
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.GlobalParams;
/*    */ import java.io.Externalizable;
/*    */ import java.io.IOException;
/*    */ import java.io.ObjectInput;
/*    */ import java.io.ObjectOutput;
/*    */ 
/*    */ @GlobalParams(paramsName="opStatus")
/*    */ public class OpStatusPm
/*    */   implements Externalizable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   @CommandParam(name="lowNibble")
/*    */   public int lowNibble;
/*    */   @CommandParam(name="hiNibble")
/*    */   public int hiNibble;
/*    */   
/*    */   public void writeExternal(ObjectOutput out)
/*    */     throws IOException
/*    */   {
/* 23 */     out.writeByte(this.lowNibble);
/* 24 */     out.writeByte(this.hiNibble);
/*    */   }
/*    */   
/*    */   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*    */   {
/* 29 */     this.lowNibble = (in.readByte() & 0xFFFF);
/* 30 */     this.hiNibble = (in.readByte() & 0xFFFF);
/*    */   }
/*    */   
/*    */   public String toString() {
/* 34 */     StringBuilder sb = new StringBuilder();
/* 35 */     sb.append('[');
/* 36 */     sb.append("lowNibble: ").append(this.lowNibble).append(", ");
/* 37 */     sb.append("hiNibble: ").append(this.hiNibble).append(']');
/* 38 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\fw2\cmdparam\OpStatusPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */