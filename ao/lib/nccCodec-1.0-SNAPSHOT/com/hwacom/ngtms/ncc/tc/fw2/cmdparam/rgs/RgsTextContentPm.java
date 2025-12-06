/*    */ package com.hwacom.ngtms.ncc.tc.fw2.cmdparam.rgs;
/*    */ 
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParam;
/*    */ import java.io.IOException;
/*    */ import java.io.ObjectInput;
/*    */ import java.io.ObjectOutput;
/*    */ 
/*    */ @com.hwacom.ngtms.cmdprot.cmdfmtdef.GlobalParams(paramsName="rgsTextContent")
/*    */ public class RgsTextContentPm implements java.io.Externalizable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   @CommandParam(name="messageLength")
/*    */   public int messageLength;
/*    */   @CommandParam(name="verList")
/*    */   public java.util.List<VerListItem> verList;
/*    */   @CommandParam(name="horSpace")
/*    */   public int horSpace;
/*    */   @CommandParam(name="message")
/*    */   public byte[] message;
/*    */   @CommandParam(name="color")
/*    */   public byte[] color;
/*    */   
/*    */   public static class VerListItem implements java.io.Externalizable
/*    */   {
/*    */     private static final long serialVersionUID = 1L;
/*    */     @CommandParam(name="verSpace")
/*    */     public int verSpace;
/*    */     
/*    */     public void writeExternal(ObjectOutput out) throws IOException
/*    */     {
/* 31 */       out.writeByte(this.verSpace);
/*    */     }
/*    */     
/*    */     public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*    */     {
/* 36 */       this.verSpace = (in.readByte() & 0xFF);
/*    */     }
/*    */     
/*    */     public String toString() {
/* 40 */       StringBuilder sb = new StringBuilder();
/* 41 */       sb.append('[');
/* 42 */       sb.append("verSpace: ").append(this.verSpace).append(']');
/* 43 */       return sb.toString();
/*    */     }
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public void writeExternal(ObjectOutput out)
/*    */     throws IOException
/*    */   {
/* 58 */     out.writeByte(this.messageLength);
/* 59 */     out.writeObject(this.verList);
/* 60 */     out.writeShort(this.horSpace);
/* 61 */     out.writeObject(this.message);
/* 62 */     out.writeObject(this.color);
/*    */   }
/*    */   
/*    */   public void readExternal(ObjectInput in)
/*    */     throws IOException, ClassNotFoundException
/*    */   {
/* 68 */     this.messageLength = (in.readByte() & 0xFF);
/* 69 */     this.verList = ((java.util.List)in.readObject());
/* 70 */     this.horSpace = (in.readShort() & 0xFFFF);
/* 71 */     this.message = ((byte[])in.readObject());
/* 72 */     this.color = ((byte[])in.readObject());
/*    */   }
/*    */   
/*    */   public String toString() {
/* 76 */     StringBuilder sb = new StringBuilder();
/* 77 */     sb.append('[');
/* 78 */     sb.append("messageLength: ").append(this.messageLength).append(", ");
/* 79 */     sb.append("verList: ").append(this.verList).append(", ");
/* 80 */     sb.append("horSpace: ").append(this.horSpace).append(", ");
/* 81 */     sb.append("message: ")
/* 82 */       .append(com.hwacom.ngtms.cmdprot.cmdfmtdef.BytesUtility.toHexString(this.message))
/* 83 */       .append(", ");
/* 84 */     sb.append("color: ")
/* 85 */       .append(com.hwacom.ngtms.cmdprot.cmdfmtdef.BytesUtility.toHexString(this.color))
/* 86 */       .append(']');
/* 87 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\fw2\cmdparam\rgs\RgsTextContentPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */