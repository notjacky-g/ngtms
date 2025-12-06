/*    */ package com.hwacom.ngtms.ncc.tc.fw2.cmdparam.vi;
/*    */ 
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
/*    */ @CommandParams(cmdName="getViDataRsp")
/*    */ public class GetViDataRspPm
/*    */   implements Externalizable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   public static final int cmdId = 37;
/*    */   public static final String cmdName = "getViDataRsp";
/*    */   @CommandParam(name="hwStatus")
/*    */   public HwStatusPm hwStatusPm;
/*    */   @CommandParam(name="dhm")
/*    */   public DhmPm dhmPm;
/*    */   @CommandParam(name="viDistance")
/*    */   public int viDistance;
/*    */   @CommandParam(name="viDegree")
/*    */   public int viDegree;
/*    */   
/*    */   public void writeExternal(ObjectOutput out)
/*    */     throws IOException
/*    */   {
/* 32 */     out.writeObject(this.hwStatusPm);
/* 33 */     out.writeObject(this.dhmPm);
/* 34 */     out.writeShort(this.viDistance);
/* 35 */     out.writeByte(this.viDegree);
/*    */   }
/*    */   
/*    */   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*    */   {
/* 40 */     this.hwStatusPm = ((HwStatusPm)in.readObject());
/* 41 */     this.dhmPm = ((DhmPm)in.readObject());
/* 42 */     this.viDistance = (in.readShort() & 0xFFFF);
/* 43 */     this.viDegree = (in.readByte() & 0xFF);
/*    */   }
/*    */   
/*    */   public String toString() {
/* 47 */     StringBuilder sb = new StringBuilder();
/* 48 */     sb.append('[');
/* 49 */     sb.append("hwStatusPm: ").append(this.hwStatusPm).append(", ");
/* 50 */     sb.append("dhmPm: ").append(this.dhmPm).append(", ");
/* 51 */     sb.append("viDistance: ").append(this.viDistance).append(", ");
/* 52 */     sb.append("viDegree: ").append(this.viDegree).append(']');
/* 53 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\fw2\cmdparam\vi\GetViDataRspPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */