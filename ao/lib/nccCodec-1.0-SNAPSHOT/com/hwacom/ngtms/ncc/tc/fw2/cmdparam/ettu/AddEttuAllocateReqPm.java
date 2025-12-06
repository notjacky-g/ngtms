/*    */ package com.hwacom.ngtms.ncc.tc.fw2.cmdparam.ettu;
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
/*    */ 
/*    */ 
/*    */ @CommandParams(cmdName="addEttuAllocateReq")
/*    */ public class AddEttuAllocateReqPm
/*    */   implements Externalizable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   public static final int cmdId = 10010;
/*    */   public static final String cmdName = "addEttuAllocateReq";
/*    */   @CommandParam(name="phoneNumber")
/*    */   public PhoneNumberPm phoneNumberPm;
/*    */   @CommandParam(name="segment")
/*    */   public SegmentPm segmentPm;
/*    */   @CommandParam(name="area")
/*    */   public AreaPm areaPm;
/*    */   @CommandParam(name="allocate")
/*    */   public AllocatePm allocatePm;
/*    */   @CommandParam(name="lrc")
/*    */   public int lrc;
/*    */   @CommandParam(name="endByte")
/*    */   public int endByte;
/*    */   
/*    */   public void writeExternal(ObjectOutput out)
/*    */     throws IOException
/*    */   {
/* 37 */     out.writeObject(this.phoneNumberPm);
/* 38 */     out.writeObject(this.segmentPm);
/* 39 */     out.writeObject(this.areaPm);
/* 40 */     out.writeObject(this.allocatePm);
/* 41 */     out.writeShort(this.lrc);
/* 42 */     out.writeByte(this.endByte);
/*    */   }
/*    */   
/*    */   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*    */   {
/* 47 */     this.phoneNumberPm = ((PhoneNumberPm)in.readObject());
/* 48 */     this.segmentPm = ((SegmentPm)in.readObject());
/* 49 */     this.areaPm = ((AreaPm)in.readObject());
/* 50 */     this.allocatePm = ((AllocatePm)in.readObject());
/* 51 */     this.lrc = (in.readShort() & 0xFFFF);
/* 52 */     this.endByte = (in.readByte() & 0xFF);
/*    */   }
/*    */   
/*    */   public String toString() {
/* 56 */     StringBuilder sb = new StringBuilder();
/* 57 */     sb.append('[');
/* 58 */     sb.append("phoneNumberPm: ").append(this.phoneNumberPm).append(", ");
/* 59 */     sb.append("segmentPm: ").append(this.segmentPm).append(", ");
/* 60 */     sb.append("areaPm: ").append(this.areaPm).append(", ");
/* 61 */     sb.append("allocatePm: ").append(this.allocatePm).append(", ");
/* 62 */     sb.append("lrc: ").append(this.lrc).append(", ");
/* 63 */     sb.append("endByte: ").append(this.endByte).append(']');
/* 64 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\fw2\cmdparam\ettu\AddEttuAllocateReqPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */