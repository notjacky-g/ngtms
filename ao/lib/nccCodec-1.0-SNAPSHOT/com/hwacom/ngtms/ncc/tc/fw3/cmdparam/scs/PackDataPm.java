/*    */ package com.hwacom.ngtms.ncc.tc.fw3.cmdparam.scs;
/*    */ 
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.BytesUtility;
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParam;
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.GlobalParams;
/*    */ import java.io.Externalizable;
/*    */ import java.io.IOException;
/*    */ import java.io.ObjectInput;
/*    */ import java.io.ObjectOutput;
/*    */ 
/*    */ @GlobalParams(paramsName="packData")
/*    */ public class PackDataPm
/*    */   implements Externalizable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   @CommandParam(name="packIdx")
/*    */   public int packIdx;
/*    */   @CommandParam(name="pattern")
/*    */   public byte[] pattern;
/*    */   
/*    */   public void writeExternal(ObjectOutput out)
/*    */     throws IOException
/*    */   {
/* 24 */     out.writeByte(this.packIdx);
/* 25 */     out.writeObject(this.pattern);
/*    */   }
/*    */   
/*    */   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*    */   {
/* 30 */     this.packIdx = (in.readByte() & 0xFF);
/* 31 */     this.pattern = ((byte[])in.readObject());
/*    */   }
/*    */   
/*    */   public String toString() {
/* 35 */     StringBuilder sb = new StringBuilder();
/* 36 */     sb.append('[');
/* 37 */     sb.append("packIdx: ").append(this.packIdx).append(", ");
/* 38 */     sb.append("pattern: ")
/* 39 */       .append(BytesUtility.toHexString(this.pattern))
/* 40 */       .append(']');
/* 41 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\fw3\cmdparam\scs\PackDataPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */