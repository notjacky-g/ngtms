/*    */ package com.hwacom.ngtms.ncc.tc.fw2.cmdparam.etag;
/*    */ 
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParam;
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParams;
/*    */ import com.hwacom.ngtms.ncc.tc.fw2.cmdparam.DayTimeNoSecPm;
/*    */ import com.hwacom.ngtms.ncc.tc.fw2.cmdparam.HwStatusPm;
/*    */ import java.io.Externalizable;
/*    */ import java.io.IOException;
/*    */ import java.io.ObjectInput;
/*    */ import java.io.ObjectOutput;
/*    */ 
/*    */ 
/*    */ @CommandParams(cmdName="getEtagDataCntRsp")
/*    */ public class GetEtagDataCntRspPm
/*    */   implements Externalizable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   public static final int cmdId = 16129;
/*    */   public static final String cmdName = "getEtagDataCntRsp";
/*    */   @CommandParam(name="responseType")
/*    */   public int responseType;
/*    */   @CommandParam(name="hwStatus")
/*    */   public HwStatusPm hwStatusPm;
/*    */   @CommandParam(name="dataCount")
/*    */   public int dataCount;
/*    */   @CommandParam(name="dayTimeNoSec")
/*    */   public DayTimeNoSecPm dayTimeNoSecPm;
/*    */   
/*    */   public void writeExternal(ObjectOutput out)
/*    */     throws IOException
/*    */   {
/* 32 */     out.writeByte(this.responseType);
/* 33 */     out.writeObject(this.hwStatusPm);
/* 34 */     out.writeShort(this.dataCount);
/* 35 */     out.writeObject(this.dayTimeNoSecPm);
/*    */   }
/*    */   
/*    */   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*    */   {
/* 40 */     this.responseType = (in.readByte() & 0xFF);
/* 41 */     this.hwStatusPm = ((HwStatusPm)in.readObject());
/* 42 */     this.dataCount = (in.readShort() & 0xFFFF);
/* 43 */     this.dayTimeNoSecPm = ((DayTimeNoSecPm)in.readObject());
/*    */   }
/*    */   
/*    */   public String toString() {
/* 47 */     StringBuilder sb = new StringBuilder();
/* 48 */     sb.append('[');
/* 49 */     sb.append("responseType: ").append(this.responseType).append(", ");
/* 50 */     sb.append("hwStatusPm: ").append(this.hwStatusPm).append(", ");
/* 51 */     sb.append("dataCount: ").append(this.dataCount).append(", ");
/* 52 */     sb.append("dayTimeNoSecPm: ").append(this.dayTimeNoSecPm).append(']');
/* 53 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\fw2\cmdparam\etag\GetEtagDataCntRspPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */