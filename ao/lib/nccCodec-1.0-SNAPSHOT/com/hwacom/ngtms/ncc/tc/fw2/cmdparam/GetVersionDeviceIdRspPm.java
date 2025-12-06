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
/*    */ 
/*    */ 
/*    */ 
/*    */ @CommandParams(cmdName="getVersionDeviceIdRsp")
/*    */ public class GetVersionDeviceIdRspPm
/*    */   implements Externalizable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   public static final int cmdId = 14;
/*    */   public static final String cmdName = "getVersionDeviceIdRsp";
/*    */   @CommandParam(name="hwStatus")
/*    */   public HwStatusPm hwStatusPm;
/*    */   @CommandParam(name="day")
/*    */   public DayPm dayPm;
/*    */   @CommandParam(name="description")
/*    */   public byte[] description;
/*    */   @CommandParam(name="deviceType")
/*    */   public int deviceType;
/*    */   @CommandParam(name="versionNo")
/*    */   public int versionNo;
/*    */   @CommandParam(name="address")
/*    */   public int address;
/*    */   
/*    */   public void writeExternal(ObjectOutput out)
/*    */     throws IOException
/*    */   {
/* 37 */     out.writeObject(this.hwStatusPm);
/* 38 */     out.writeObject(this.dayPm);
/* 39 */     out.writeObject(this.description);
/* 40 */     out.writeByte(this.deviceType);
/* 41 */     out.writeByte(this.versionNo);
/* 42 */     out.writeShort(this.address);
/*    */   }
/*    */   
/*    */   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*    */   {
/* 47 */     this.hwStatusPm = ((HwStatusPm)in.readObject());
/* 48 */     this.dayPm = ((DayPm)in.readObject());
/* 49 */     this.description = ((byte[])in.readObject());
/* 50 */     this.deviceType = (in.readByte() & 0xFF);
/* 51 */     this.versionNo = (in.readByte() & 0xFF);
/* 52 */     this.address = (in.readShort() & 0xFFFF);
/*    */   }
/*    */   
/*    */   public String toString() {
/* 56 */     StringBuilder sb = new StringBuilder();
/* 57 */     sb.append('[');
/* 58 */     sb.append("hwStatusPm: ").append(this.hwStatusPm).append(", ");
/* 59 */     sb.append("dayPm: ").append(this.dayPm).append(", ");
/* 60 */     sb.append("description: ")
/* 61 */       .append(BytesUtility.toHexString(this.description))
/* 62 */       .append(", ");
/* 63 */     sb.append("deviceType: ").append(this.deviceType).append(", ");
/* 64 */     sb.append("versionNo: ").append(this.versionNo).append(", ");
/* 65 */     sb.append("address: ").append(this.address).append(']');
/* 66 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\fw2\cmdparam\GetVersionDeviceIdRspPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */