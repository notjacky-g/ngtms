/*    */ package com.hwacom.ngtms.ncc.tc.fw2.cmdparam.rms;
/*    */ 
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.BytesUtility;
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParam;
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParams;
/*    */ import java.io.Externalizable;
/*    */ import java.io.IOException;
/*    */ import java.io.ObjectInput;
/*    */ import java.io.ObjectOutput;
/*    */ import java.util.List;
/*    */ 
/*    */ @CommandParams(cmdName="setRmsWarnMessageReq")
/*    */ public class SetRmsWarnMessageReqPm
/*    */   implements Externalizable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   public static final int cmdId = 140;
/*    */   public static final String cmdName = "setRmsWarnMessageReq";
/*    */   @CommandParam(name="messageList")
/*    */   public List<MessageListItem> messageList;
/*    */   
/*    */   public static class MessageListItem implements Externalizable
/*    */   {
/*    */     private static final long serialVersionUID = 1L;
/*    */     @CommandParam(name="warnMessageId")
/*    */     public int warnMessageId;
/*    */     @CommandParam(name="warnMessage")
/*    */     public byte[] warnMessage;
/*    */     
/*    */     public void writeExternal(ObjectOutput out) throws IOException
/*    */     {
/* 32 */       out.writeByte(this.warnMessageId);
/* 33 */       out.writeObject(this.warnMessage);
/*    */     }
/*    */     
/*    */     public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*    */     {
/* 38 */       this.warnMessageId = (in.readByte() & 0xFF);
/* 39 */       this.warnMessage = ((byte[])in.readObject());
/*    */     }
/*    */     
/*    */     public String toString() {
/* 43 */       StringBuilder sb = new StringBuilder();
/* 44 */       sb.append('[');
/* 45 */       sb.append("warnMessageId: ").append(this.warnMessageId).append(", ");
/* 46 */       sb.append("warnMessage: ")
/* 47 */         .append(BytesUtility.toHexString(this.warnMessage))
/* 48 */         .append(']');
/* 49 */       return sb.toString();
/*    */     }
/*    */   }
/*    */   
/*    */   public void writeExternal(ObjectOutput out) throws IOException
/*    */   {
/* 55 */     out.writeObject(this.messageList);
/*    */   }
/*    */   
/*    */   public void readExternal(ObjectInput in)
/*    */     throws IOException, ClassNotFoundException
/*    */   {
/* 61 */     this.messageList = ((List)in.readObject());
/*    */   }
/*    */   
/*    */   public String toString() {
/* 65 */     StringBuilder sb = new StringBuilder();
/* 66 */     sb.append('[');
/* 67 */     sb.append("messageList: ").append(this.messageList).append(']');
/* 68 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\fw2\cmdparam\rms\SetRmsWarnMessageReqPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */