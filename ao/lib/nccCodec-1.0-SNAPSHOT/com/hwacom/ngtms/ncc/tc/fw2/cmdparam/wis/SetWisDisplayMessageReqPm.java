/*    */ package com.hwacom.ngtms.ncc.tc.fw2.cmdparam.wis;
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
/*    */ @CommandParams(cmdName="setWisDisplayMessageReq")
/*    */ public class SetWisDisplayMessageReqPm
/*    */   implements Externalizable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   public static final int cmdId = 57303;
/*    */   public static final String cmdName = "setWisDisplayMessageReq";
/*    */   @CommandParam(name="dataType")
/*    */   public int dataType;
/*    */   @CommandParam(name="wisTextContent")
/*    */   public WisTextContentPm wisTextContentPm;
/*    */   @CommandParam(name="wisGraphicContent")
/*    */   public WisGraphicContentPm wisGraphicContentPm;
/*    */   
/*    */   public void writeExternal(ObjectOutput out)
/*    */     throws IOException
/*    */   {
/* 29 */     out.writeByte(this.dataType);
/* 30 */     out.writeObject(this.wisTextContentPm);
/* 31 */     out.writeObject(this.wisGraphicContentPm);
/*    */   }
/*    */   
/*    */   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*    */   {
/* 36 */     this.dataType = (in.readByte() & 0xFF);
/* 37 */     this.wisTextContentPm = ((WisTextContentPm)in.readObject());
/* 38 */     this.wisGraphicContentPm = ((WisGraphicContentPm)in.readObject());
/*    */   }
/*    */   
/*    */   public String toString() {
/* 42 */     StringBuilder sb = new StringBuilder();
/* 43 */     sb.append('[');
/* 44 */     sb.append("dataType: ").append(this.dataType).append(", ");
/* 45 */     sb.append("wisTextContentPm: ").append(this.wisTextContentPm).append(", ");
/* 46 */     sb.append("wisGraphicContentPm: ").append(this.wisGraphicContentPm).append(']');
/* 47 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\fw2\cmdparam\wis\SetWisDisplayMessageReqPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */