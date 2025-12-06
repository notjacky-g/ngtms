/*    */ package com.hwacom.ngtms.ncc.tc.fw2.cmdparam.wis;
/*    */ 
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.BytesUtility;
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParam;
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.GlobalParams;
/*    */ import java.io.Externalizable;
/*    */ import java.io.IOException;
/*    */ import java.io.ObjectInput;
/*    */ import java.io.ObjectOutput;
/*    */ 
/*    */ @GlobalParams(paramsName="wisGraphicContentWithDesc")
/*    */ public class WisGraphicContentWithDescPm
/*    */   implements Externalizable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   @CommandParam(name="gCodeId")
/*    */   public int gCodeId;
/*    */   @CommandParam(name="gDescription")
/*    */   public byte[] gDescription;
/*    */   
/*    */   public void writeExternal(ObjectOutput out)
/*    */     throws IOException
/*    */   {
/* 24 */     out.writeByte(this.gCodeId);
/* 25 */     out.writeObject(this.gDescription);
/*    */   }
/*    */   
/*    */   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*    */   {
/* 30 */     this.gCodeId = (in.readByte() & 0xFF);
/* 31 */     this.gDescription = ((byte[])in.readObject());
/*    */   }
/*    */   
/*    */   public String toString() {
/* 35 */     StringBuilder sb = new StringBuilder();
/* 36 */     sb.append('[');
/* 37 */     sb.append("gCodeId: ").append(this.gCodeId).append(", ");
/* 38 */     sb.append("gDescription: ")
/* 39 */       .append(BytesUtility.toHexString(this.gDescription))
/* 40 */       .append(']');
/* 41 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\fw2\cmdparam\wis\WisGraphicContentWithDescPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */