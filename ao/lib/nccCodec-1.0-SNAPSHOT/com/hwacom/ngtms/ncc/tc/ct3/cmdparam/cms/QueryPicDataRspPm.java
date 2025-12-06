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
/*    */ @CommandParams(cmdName="queryPicDataRsp")
/*    */ public class QueryPicDataRspPm
/*    */   implements Externalizable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   public static final int cmdId = 45016;
/*    */   public static final String cmdName = "queryPicDataRsp";
/*    */   @CommandParam(name="patternCode")
/*    */   public int patternCode;
/*    */   @CommandParam(name="picType")
/*    */   public int picType;
/*    */   @CommandParam(name="dataLength")
/*    */   public int dataLength;
/*    */   @CommandParam(name="picData")
/*    */   public byte[] picData;
/*    */   
/*    */   public void writeExternal(ObjectOutput out)
/*    */     throws IOException
/*    */   {
/* 32 */     out.writeShort(this.patternCode);
/* 33 */     out.writeByte(this.picType);
/* 34 */     out.writeShort(this.dataLength);
/* 35 */     out.writeObject(this.picData);
/*    */   }
/*    */   
/*    */   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*    */   {
/* 40 */     this.patternCode = (in.readShort() & 0xFFFF);
/* 41 */     this.picType = (in.readByte() & 0xFF);
/* 42 */     this.dataLength = (in.readShort() & 0xFFFF);
/* 43 */     this.picData = ((byte[])in.readObject());
/*    */   }
/*    */   
/*    */   public String toString() {
/* 47 */     StringBuilder sb = new StringBuilder();
/* 48 */     sb.append('[');
/* 49 */     sb.append("patternCode: ").append(this.patternCode).append(", ");
/* 50 */     sb.append("picType: ").append(this.picType).append(", ");
/* 51 */     sb.append("dataLength: ").append(this.dataLength).append(", ");
/* 52 */     sb.append("picData: ")
/* 53 */       .append(BytesUtility.toHexString(this.picData))
/* 54 */       .append(']');
/* 55 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\ct3\cmdparam\cms\QueryPicDataRspPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */