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
/*    */ @CommandParams(cmdName="queryDisplayMsgRsp")
/*    */ public class QueryDisplayMsgRspPm
/*    */   implements Externalizable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   public static final int cmdId = 45027;
/*    */   public static final String cmdName = "queryDisplayMsgRsp";
/*    */   @CommandParam(name="msgId")
/*    */   public int msgId;
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
/* 35 */     out.writeByte(this.msgId);
/* 36 */     out.writeByte(this.totalPacket);
/* 37 */     out.writeByte(this.packetNo);
/* 38 */     out.writeShort(this.msgSize);
/* 39 */     out.writeObject(this.msgContent);
/*    */   }
/*    */   
/*    */   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*    */   {
/* 44 */     this.msgId = (in.readByte() & 0xFF);
/* 45 */     this.totalPacket = (in.readByte() & 0xFF);
/* 46 */     this.packetNo = (in.readByte() & 0xFF);
/* 47 */     this.msgSize = (in.readShort() & 0xFFFF);
/* 48 */     this.msgContent = ((byte[])in.readObject());
/*    */   }
/*    */   
/*    */   public String toString() {
/* 52 */     StringBuilder sb = new StringBuilder();
/* 53 */     sb.append('[');
/* 54 */     sb.append("msgId: ").append(this.msgId).append(", ");
/* 55 */     sb.append("totalPacket: ").append(this.totalPacket).append(", ");
/* 56 */     sb.append("packetNo: ").append(this.packetNo).append(", ");
/* 57 */     sb.append("msgSize: ").append(this.msgSize).append(", ");
/* 58 */     sb.append("msgContent: ")
/* 59 */       .append(BytesUtility.toHexString(this.msgContent))
/* 60 */       .append(']');
/* 61 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\ct3\cmdparam\cms\QueryDisplayMsgRspPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */