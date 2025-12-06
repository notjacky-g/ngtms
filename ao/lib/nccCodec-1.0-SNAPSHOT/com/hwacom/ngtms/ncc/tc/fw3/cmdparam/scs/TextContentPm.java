/*    */ package com.hwacom.ngtms.ncc.tc.fw3.cmdparam.scs;
/*    */ 
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParam;
/*    */ import java.io.ObjectInput;
/*    */ import java.io.ObjectOutput;
/*    */ 
/*    */ @com.hwacom.ngtms.cmdprot.cmdfmtdef.GlobalParams(paramsName="textContent")
/*    */ public class TextContentPm implements java.io.Externalizable
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
/*    */     public void writeExternal(ObjectOutput out) throws java.io.IOException
/*    */     {
/* 30 */       out.writeByte(this.verSpace);
/*    */     }
/*    */     
/*    */     public void readExternal(ObjectInput in) throws java.io.IOException, ClassNotFoundException
/*    */     {
/* 35 */       this.verSpace = (in.readByte() & 0xFF);
/*    */     }
/*    */     
/*    */     public String toString() {
/* 39 */       StringBuilder sb = new StringBuilder();
/* 40 */       sb.append('[');
/* 41 */       sb.append("verSpace: ").append(this.verSpace).append(']');
/* 42 */       return sb.toString();
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
/*    */     throws java.io.IOException
/*    */   {
/* 57 */     out.writeByte(this.messageLength);
/* 58 */     out.writeObject(this.verList);
/* 59 */     out.writeShort(this.horSpace);
/* 60 */     out.writeObject(this.message);
/* 61 */     out.writeObject(this.color);
/*    */   }
/*    */   
/*    */   public void readExternal(ObjectInput in)
/*    */     throws java.io.IOException, ClassNotFoundException
/*    */   {
/* 67 */     this.messageLength = (in.readByte() & 0xFF);
/* 68 */     this.verList = ((java.util.List)in.readObject());
/* 69 */     this.horSpace = (in.readShort() & 0xFFFF);
/* 70 */     this.message = ((byte[])in.readObject());
/* 71 */     this.color = ((byte[])in.readObject());
/*    */   }
/*    */   
/*    */   public String toString() {
/* 75 */     StringBuilder sb = new StringBuilder();
/* 76 */     sb.append('[');
/* 77 */     sb.append("messageLength: ").append(this.messageLength).append(", ");
/* 78 */     sb.append("verList: ").append(this.verList).append(", ");
/* 79 */     sb.append("horSpace: ").append(this.horSpace).append(", ");
/* 80 */     sb.append("message: ")
/* 81 */       .append(com.hwacom.ngtms.cmdprot.cmdfmtdef.BytesUtility.toHexString(this.message))
/* 82 */       .append(", ");
/* 83 */     sb.append("color: ")
/* 84 */       .append(com.hwacom.ngtms.cmdprot.cmdfmtdef.BytesUtility.toHexString(this.color))
/* 85 */       .append(']');
/* 86 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\fw3\cmdparam\scs\TextContentPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */