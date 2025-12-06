/*    */ package com.hwacom.ngtms.ncc.tc.ct3.cmdparam.cms;
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
/*    */ @CommandParams(cmdName="queryDefaultMsgReq")
/*    */ public class QueryDefaultMsgReqPm
/*    */   implements Externalizable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   public static final int cmdId = 44898;
/*    */   public static final String cmdName = "queryDefaultMsgReq";
/*    */   @CommandParam(name="msgId")
/*    */   public int msgId;
/*    */   @CommandParam(name="totalPacket")
/*    */   public int totalPacket;
/*    */   @CommandParam(name="packetNo")
/*    */   public int packetNo;
/*    */   
/*    */   public void writeExternal(ObjectOutput out)
/*    */     throws IOException
/*    */   {
/* 29 */     out.writeByte(this.msgId);
/* 30 */     out.writeByte(this.totalPacket);
/* 31 */     out.writeByte(this.packetNo);
/*    */   }
/*    */   
/*    */   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*    */   {
/* 36 */     this.msgId = (in.readByte() & 0xFF);
/* 37 */     this.totalPacket = (in.readByte() & 0xFF);
/* 38 */     this.packetNo = (in.readByte() & 0xFF);
/*    */   }
/*    */   
/*    */   public String toString() {
/* 42 */     StringBuilder sb = new StringBuilder();
/* 43 */     sb.append('[');
/* 44 */     sb.append("msgId: ").append(this.msgId).append(", ");
/* 45 */     sb.append("totalPacket: ").append(this.totalPacket).append(", ");
/* 46 */     sb.append("packetNo: ").append(this.packetNo).append(']');
/* 47 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\ct3\cmdparam\cms\QueryDefaultMsgReqPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */