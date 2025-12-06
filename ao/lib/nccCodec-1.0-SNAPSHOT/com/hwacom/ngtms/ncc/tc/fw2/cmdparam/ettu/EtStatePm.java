/*    */ package com.hwacom.ngtms.ncc.tc.fw2.cmdparam.ettu;
/*    */ 
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParam;
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.GlobalParams;
/*    */ import java.io.Externalizable;
/*    */ import java.io.IOException;
/*    */ import java.io.ObjectInput;
/*    */ import java.io.ObjectOutput;
/*    */ 
/*    */ @GlobalParams(paramsName="etState")
/*    */ public class EtStatePm
/*    */   implements Externalizable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   @CommandParam(name="x1")
/*    */   public int x1;
/*    */   @CommandParam(name="x2")
/*    */   public int x2;
/*    */   
/*    */   public void writeExternal(ObjectOutput out)
/*    */     throws IOException
/*    */   {
/* 23 */     out.writeByte(this.x1);
/* 24 */     out.writeByte(this.x2);
/*    */   }
/*    */   
/*    */   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*    */   {
/* 29 */     this.x1 = (in.readByte() & 0xFF);
/* 30 */     this.x2 = (in.readByte() & 0xFF);
/*    */   }
/*    */   
/*    */   public String toString() {
/* 34 */     StringBuilder sb = new StringBuilder();
/* 35 */     sb.append('[');
/* 36 */     sb.append("x1: ").append(this.x1).append(", ");
/* 37 */     sb.append("x2: ").append(this.x2).append(']');
/* 38 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\fw2\cmdparam\ettu\EtStatePm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */