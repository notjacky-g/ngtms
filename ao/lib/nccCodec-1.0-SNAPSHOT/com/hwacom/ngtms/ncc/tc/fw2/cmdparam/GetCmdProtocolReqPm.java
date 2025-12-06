/*    */ package com.hwacom.ngtms.ncc.tc.fw2.cmdparam;
/*    */ 
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.BytesUtility;
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParam;
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParams;
/*    */ import java.io.Externalizable;
/*    */ import java.io.IOException;
/*    */ import java.io.ObjectInput;
/*    */ import java.io.ObjectOutput;
/*    */ 
/*    */ @CommandParams(cmdName="getCmdProtocolReq")
/*    */ public class GetCmdProtocolReqPm
/*    */   implements Externalizable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   public static final int cmdId = 4;
/*    */   public static final String cmdName = "getCmdProtocolReq";
/*    */   @CommandParam(name="protocolCode")
/*    */   public int protocolCode;
/*    */   @CommandParam(name="attribute")
/*    */   public byte[] attribute;
/*    */   
/*    */   public void writeExternal(ObjectOutput out) throws IOException
/*    */   {
/* 25 */     out.writeShort(this.protocolCode);
/* 26 */     out.writeObject(this.attribute);
/*    */   }
/*    */   
/*    */   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*    */   {
/* 31 */     this.protocolCode = (in.readShort() & 0xFFFF);
/* 32 */     this.attribute = ((byte[])in.readObject());
/*    */   }
/*    */   
/*    */   public String toString() {
/* 36 */     StringBuilder sb = new StringBuilder();
/* 37 */     sb.append('[');
/* 38 */     sb.append("protocolCode: ").append(this.protocolCode).append(", ");
/* 39 */     sb.append("attribute: ")
/* 40 */       .append(BytesUtility.toHexString(this.attribute))
/* 41 */       .append(']');
/* 42 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\fw2\cmdparam\GetCmdProtocolReqPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */