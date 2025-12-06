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
/*    */ @CommandParams(cmdName="queryDefaultMsgRsp")
/*    */ public class QueryDefaultMsgRspPm
/*    */   implements Externalizable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   public static final int cmdId = 45026;
/*    */   public static final String cmdName = "queryDefaultMsgRsp";
/*    */   @CommandParam(name="msgId")
/*    */   public int msgId;
/*    */   @CommandParam(name="msgType")
/*    */   public int msgType;
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
/* 38 */     out.writeByte(this.msgId);
/* 39 */     out.writeByte(this.msgType);
/* 40 */     out.writeByte(this.totalPacket);
/* 41 */     out.writeByte(this.packetNo);
/* 42 */     out.writeShort(this.msgSize);
/* 43 */     out.writeObject(this.msgContent);
/*    */   }
/*    */   
/*    */   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*    */   {
/* 48 */     this.msgId = (in.readByte() & 0xFF);
/* 49 */     this.msgType = (in.readByte() & 0xFF);
/* 50 */     this.totalPacket = (in.readByte() & 0xFF);
/* 51 */     this.packetNo = (in.readByte() & 0xFF);
/* 52 */     this.msgSize = (in.readShort() & 0xFFFF);
/* 53 */     this.msgContent = ((byte[])in.readObject());
/*    */   }
/*    */   
/*    */   public String toString() {
/* 57 */     StringBuilder sb = new StringBuilder();
/* 58 */     sb.append('[');
/* 59 */     sb.append("msgId: ").append(this.msgId).append(", ");
/* 60 */     sb.append("msgType: ").append(this.msgType).append(", ");
/* 61 */     sb.append("totalPacket: ").append(this.totalPacket).append(", ");
/* 62 */     sb.append("packetNo: ").append(this.packetNo).append(", ");
/* 63 */     sb.append("msgSize: ").append(this.msgSize).append(", ");
/* 64 */     sb.append("msgContent: ")
/* 65 */       .append(BytesUtility.toHexString(this.msgContent))
/* 66 */       .append(']');
/* 67 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\ct3\cmdparam\cms\QueryDefaultMsgRspPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */