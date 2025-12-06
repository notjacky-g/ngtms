/*    */ package com.hwacom.ngtms.ncc.tc.fw2.cmdparam;
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
/* 44 */     out.writeByte(this.deviceFailure);
/* 45 */     out.writeByte(this.doorOpen);
/* 46 */     out.writeByte(this.portableTest);
/* 47 */     out.writeByte(this.panelOperate);
/* 48 */     out.writeByte(this.basicParam);
/* 49 */     out.writeByte(this.selfRestart);
/* 50 */     out.writeByte(this.lightOff);
/* 51 */     out.writeByte(this.didoFailure);
/* 52 */     out.writeObject(this.typeStatus);
/*    */   }
/*    */   
/*    */   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*    */   {
/* 57 */     this.deviceFailure = (in.readByte() & 0xFFFF);
/* 58 */     this.doorOpen = (in.readByte() & 0xFFFF);
/* 59 */     this.portableTest = (in.readByte() & 0xFFFF);
/* 60 */     this.panelOperate = (in.readByte() & 0xFFFF);
/* 61 */     this.basicParam = (in.readByte() & 0xFFFF);
/* 62 */     this.selfRestart = (in.readByte() & 0xFFFF);
/* 63 */     this.lightOff = (in.readByte() & 0xFFFF);
/* 64 */     this.didoFailure = (in.readByte() & 0xFFFF);
/* 65 */     this.typeStatus = ((byte[])in.readObject());
/*    */   }
/*    */   
/*    */   public String toString() {
/* 69 */     StringBuilder sb = new StringBuilder();
/* 70 */     sb.append('[');
/* 71 */     sb.append("deviceFailure: ").append(this.deviceFailure).append(", ");
/* 72 */     sb.append("doorOpen: ").append(this.doorOpen).append(", ");
/* 73 */     sb.append("portableTest: ").append(this.portableTest).append(", ");
/* 74 */     sb.append("panelOperate: ").append(this.panelOperate).append(", ");
/* 75 */     sb.append("basicParam: ").append(this.basicParam).append(", ");
/* 76 */     sb.append("selfRestart: ").append(this.selfRestart).append(", ");
/* 77 */     sb.append("lightOff: ").append(this.lightOff).append(", ");
/* 78 */     sb.append("didoFailure: ").append(this.didoFailure).append(", ");
/* 79 */     sb.append("typeStatus: ")
/* 80 */       .append(BytesUtility.toHexString(this.typeStatus))
/* 81 */       .append(']');
/* 82 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\fw2\cmdparam\HwStatusPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */