/*    */ package com.hwacom.ngtms.ncc.tc.fw2.cmdparam.ettu;
/*    */ 
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParam;
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.GlobalParams;
/*    */ import java.io.Externalizable;
/*    */ import java.io.IOException;
/*    */ import java.io.ObjectInput;
/*    */ import java.io.ObjectOutput;
/*    */ 
/*    */ 
/*    */ @GlobalParams(paramsName="allocate")
/*    */ public class AllocatePm
/*    */   implements Externalizable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   @CommandParam(name="a1")
/*    */   public int a1;
/*    */   @CommandParam(name="u1")
/*    */   public int u1;
/*    */   @CommandParam(name="u2")
/*    */   public int u2;
/*    */   
/*    */   public void writeExternal(ObjectOutput out)
/*    */     throws IOException
/*    */   {
/* 26 */     out.writeByte(this.a1);
/* 27 */     out.writeByte(this.u1);
/* 28 */     out.writeByte(this.u2);
/*    */   }
/*    */   
/*    */   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*    */   {
/* 33 */     this.a1 = (in.readByte() & 0xFF);
/* 34 */     this.u1 = (in.readByte() & 0xFF);
/* 35 */     this.u2 = (in.readByte() & 0xFF);
/*    */   }
/*    */   
/*    */   public String toString() {
/* 39 */     StringBuilder sb = new StringBuilder();
/* 40 */     sb.append('[');
/* 41 */     sb.append("a1: ").append(this.a1).append(", ");
/* 42 */     sb.append("u1: ").append(this.u1).append(", ");
/* 43 */     sb.append("u2: ").append(this.u2).append(']');
/* 44 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\fw2\cmdparam\ettu\AllocatePm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */