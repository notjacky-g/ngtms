/*    */ package com.hwacom.ngtms.ncc.tc.ct3.cmdparam.lcs;
/*    */ 
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.BytesUtility;
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParam;
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.GlobalParams;
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
/*    */ 
/*    */ 
/*    */ @GlobalParams(paramsName="hwStatus")
/*    */ public class HwStatusPm
/*    */   implements Externalizable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   @CommandParam(name="deviceFailure")
/*    */   public int deviceFailure;
/*    */   @CommandParam(name="doorOpen")
/*    */   public int doorOpen;
/*    */   @CommandParam(name="portableTest")
/*    */   public int portableTest;
/*    */   @CommandParam(name="panelOperate")
/*    */   public int panelOperate;
/*    */   @CommandParam(name="basicParam")
/*    */   public int basicParam;
/*    */   @CommandParam(name="selfRestart")
/*    */   public int selfRestart;
/*    */   @CommandParam(name="lightOff")
/*    */   public int lightOff;
/*    */   @CommandParam(name="didoFailure")
/*    */   public int didoFailure;
/*    */   @CommandParam(name="typeStatus")
/*    */   public byte[] typeStatus;
/*    */   
/*    */   public void writeExternal(ObjectOutput out)
/*    */     throws IOException
/*    */   {
/* 45 */     out.writeByte(this.deviceFailure);
/* 46 */     out.writeByte(this.doorOpen);
/* 47 */     out.writeByte(this.portableTest);
/* 48 */     out.writeByte(this.panelOperate);
/* 49 */     out.writeByte(this.basicParam);
/* 50 */     out.writeByte(this.selfRestart);
/* 51 */     out.writeByte(this.lightOff);
/* 52 */     out.writeByte(this.didoFailure);
/* 53 */     out.writeObject(this.typeStatus);
/*    */   }
/*    */   
/*    */   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*    */   {
/* 58 */     this.deviceFailure = (in.readByte() & 0xFFFF);
/* 59 */     this.doorOpen = (in.readByte() & 0xFFFF);
/* 60 */     this.portableTest = (in.readByte() & 0xFFFF);
/* 61 */     this.panelOperate = (in.readByte() & 0xFFFF);
/* 62 */     this.basicParam = (in.readByte() & 0xFFFF);
/* 63 */     this.selfRestart = (in.readByte() & 0xFFFF);
/* 64 */     this.lightOff = (in.readByte() & 0xFFFF);
/* 65 */     this.didoFailure = (in.readByte() & 0xFFFF);
/* 66 */     this.typeStatus = ((byte[])in.readObject());
/*    */   }
/*    */   
/*    */   public String toString() {
/* 70 */     StringBuilder sb = new StringBuilder();
/* 71 */     sb.append('[');
/* 72 */     sb.append("deviceFailure: ").append(this.deviceFailure).append(", ");
/* 73 */     sb.append("doorOpen: ").append(this.doorOpen).append(", ");
/* 74 */     sb.append("portableTest: ").append(this.portableTest).append(", ");
/* 75 */     sb.append("panelOperate: ").append(this.panelOperate).append(", ");
/* 76 */     sb.append("basicParam: ").append(this.basicParam).append(", ");
/* 77 */     sb.append("selfRestart: ").append(this.selfRestart).append(", ");
/* 78 */     sb.append("lightOff: ").append(this.lightOff).append(", ");
/* 79 */     sb.append("didoFailure: ").append(this.didoFailure).append(", ");
/* 80 */     sb.append("typeStatus: ")
/* 81 */       .append(BytesUtility.toHexString(this.typeStatus))
/* 82 */       .append(']');
/* 83 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\ct3\cmdparam\lcs\HwStatusPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */