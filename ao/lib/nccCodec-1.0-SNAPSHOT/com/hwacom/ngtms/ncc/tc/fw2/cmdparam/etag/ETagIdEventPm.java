/*    */ package com.hwacom.ngtms.ncc.tc.fw2.cmdparam.etag;
/*    */ 
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.BytesUtility;
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParam;
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParams;
/*    */ import com.hwacom.ngtms.ncc.tc.fw2.cmdparam.DhmPm;
/*    */ import com.hwacom.ngtms.ncc.tc.fw2.cmdparam.HwStatusPm;
/*    */ import java.io.Externalizable;
/*    */ import java.io.IOException;
/*    */ import java.io.ObjectInput;
/*    */ import java.io.ObjectOutput;
/*    */ 
/*    */ 
/*    */ 
/*    */ @CommandParams(cmdName="eTagIdEvent")
/*    */ public class ETagIdEventPm
/*    */   implements Externalizable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   public static final int cmdId = 16132;
/*    */   public static final String cmdName = "eTagIdEvent";
/*    */   @CommandParam(name="hwStatus")
/*    */   public HwStatusPm hwStatusPm;
/*    */   @CommandParam(name="dhm")
/*    */   public DhmPm dhmPm;
/*    */   @CommandParam(name="laneId")
/*    */   public int laneId;
/*    */   @CommandParam(name="carType")
/*    */   public int carType;
/*    */   @CommandParam(name="second")
/*    */   public int second;
/*    */   @CommandParam(name="tagId")
/*    */   public byte[] tagId;
/*    */   
/*    */   public void writeExternal(ObjectOutput out)
/*    */     throws IOException
/*    */   {
/* 38 */     out.writeObject(this.hwStatusPm);
/* 39 */     out.writeObject(this.dhmPm);
/* 40 */     out.writeByte(this.laneId);
/* 41 */     out.writeByte(this.carType);
/* 42 */     out.writeByte(this.second);
/* 43 */     out.writeObject(this.tagId);
/*    */   }
/*    */   
/*    */   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*    */   {
/* 48 */     this.hwStatusPm = ((HwStatusPm)in.readObject());
/* 49 */     this.dhmPm = ((DhmPm)in.readObject());
/* 50 */     this.laneId = (in.readByte() & 0xFF);
/* 51 */     this.carType = (in.readByte() & 0xFF);
/* 52 */     this.second = (in.readByte() & 0xFF);
/* 53 */     this.tagId = ((byte[])in.readObject());
/*    */   }
/*    */   
/*    */   public String toString() {
/* 57 */     StringBuilder sb = new StringBuilder();
/* 58 */     sb.append('[');
/* 59 */     sb.append("hwStatusPm: ").append(this.hwStatusPm).append(", ");
/* 60 */     sb.append("dhmPm: ").append(this.dhmPm).append(", ");
/* 61 */     sb.append("laneId: ").append(this.laneId).append(", ");
/* 62 */     sb.append("carType: ").append(this.carType).append(", ");
/* 63 */     sb.append("second: ").append(this.second).append(", ");
/* 64 */     sb.append("tagId: ")
/* 65 */       .append(BytesUtility.toHexString(this.tagId))
/* 66 */       .append(']');
/* 67 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\fw2\cmdparam\etag\ETagIdEventPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */