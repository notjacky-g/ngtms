/*    */ package com.hwacom.ngtms.ncc.tc.fw2.cmdparam.ettu;
/*    */ 
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.BytesUtility;
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParam;
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.GlobalParams;
/*    */ import java.io.Externalizable;
/*    */ import java.io.IOException;
/*    */ import java.io.ObjectInput;
/*    */ import java.io.ObjectOutput;
/*    */ 
/*    */ @GlobalParams(paramsName="area")
/*    */ public class AreaPm implements Externalizable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   @CommandParam(name="areaString")
/*    */   public byte[] areaString;
/*    */   
/*    */   public void writeExternal(ObjectOutput out) throws IOException
/*    */   {
/* 20 */     out.writeObject(this.areaString);
/*    */   }
/*    */   
/*    */   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*    */   {
/* 25 */     this.areaString = ((byte[])in.readObject());
/*    */   }
/*    */   
/*    */   public String toString() {
/* 29 */     StringBuilder sb = new StringBuilder();
/* 30 */     sb.append('[');
/* 31 */     sb.append("areaString: ")
/* 32 */       .append(BytesUtility.toHexString(this.areaString))
/* 33 */       .append(']');
/* 34 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\fw2\cmdparam\ettu\AreaPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */