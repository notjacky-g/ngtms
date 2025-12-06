/*    */ package com.hwacom.ngtms.ncc.tc.ct3.cmdparam.cms;
/*    */ 
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.BytesUtility;
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParam;
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParams;
/*    */ import java.io.Externalizable;
/*    */ import java.io.IOException;
/*    */ import java.io.ObjectInput;
/*    */ import java.io.ObjectOutput;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @CommandParams(cmdName="setDefaultMsgReq")
/*    */ public class SetDefaultMsgReqPm
/*    */   implements Externalizable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   public static final int cmdId = 44850;
/*    */   public static final String cmdName = "setDefaultMsgReq";
/*    */   @CommandParam(name="msgId")
/*    */   public int msgId;
/*    */   @CommandParam(name="msgType")
/*    */   public int msgType;
/*    */   @CommandParam(name="actionType")
/*    */   public int actionType;
/*    */   @CommandParam(name="totalPacket")
/*    */   public int totalPacket;
/*    */   @CommandParam(name="packetNo")
/*    */   public int packetNo;
/*    */   @CommandParam(name="msgSize")
/*    */   public int msgSize;
/*    */   @CommandParam(name="msgContent")
/*    */   public byte[] msgContent;
/*    */   
/*    */   public void writeExternal(ObjectOutput out)
/*    */     throws IOException
/*    */   {
/* 41 */     out.writeByte(this.msgId);
/* 42 */     out.writeByte(this.msgType);
/* 43 */     out.writeByte(this.actionType);
/* 44 */     out.writeByte(this.totalPacket);
/* 45 */     out.writeByte(this.packetNo);
/* 46 */     out.writeShort(this.msgSize);
/* 47 */     out.writeObject(this.msgContent);
/*    */   }
/*    */   
/*    */   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*    */   {
/* 52 */     this.msgId = (in.readByte() & 0xFF);
/* 53 */     this.msgType = (in.readByte() & 0xFF);
/* 54 */     this.actionType = (in.readByte() & 0xFF);
/* 55 */     this.totalPacket = (in.readByte() & 0xFF);
/* 56 */     this.packetNo = (in.readByte() & 0xFF);
/* 57 */     this.msgSize = (in.readShort() & 0xFFFF);
/* 58 */     this.msgContent = ((byte[])in.readObject());
/*    */   }
/*    */   
/*    */   public String toString() {
/* 62 */     StringBuilder sb = new StringBuilder();
/* 63 */     sb.append('[');
/* 64 */     sb.append("msgId: ").append(this.msgId).append(", ");
/* 65 */     sb.append("msgType: ").append(this.msgType).append(", ");
/* 66 */     sb.append("actionType: ").append(this.actionType).append(", ");
/* 67 */     sb.append("totalPacket: ").append(this.totalPacket).append(", ");
/* 68 */     sb.append("packetNo: ").append(this.packetNo).append(", ");
/* 69 */     sb.append("msgSize: ").append(this.msgSize).append(", ");
/* 70 */     sb.append("msgContent: ")
/* 71 */       .append(BytesUtility.toHexString(this.msgContent))
/* 72 */       .append(']');
/* 73 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\ct3\cmdparam\cms\SetDefaultMsgReqPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */