/*    */ package com.hwacom.ngtms.ncc.tc.fw2.cmdparam;
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
/*    */ @CommandParams(cmdName="getDevcieStatusRsp")
/*    */ public class GetDevcieStatusRspPm
/*    */   implements Externalizable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   public static final int cmdId = 11;
/*    */   public static final String cmdName = "getDevcieStatusRsp";
/*    */   @CommandParam(name="hwStatus")
/*    */   public HwStatusPm hwStatusPm;
/*    */   @CommandParam(name="commState")
/*    */   public int commState;
/*    */   @CommandParam(name="opStatus")
/*    */   public OpStatusPm opStatusPm;
/*    */   @CommandParam(name="opMode")
/*    */   public OpModePm opModePm;
/*    */   
/*    */   public void writeExternal(ObjectOutput out)
/*    */     throws IOException
/*    */   {
/* 31 */     out.writeObject(this.hwStatusPm);
/* 32 */     out.writeByte(this.commState);
/* 33 */     out.writeObject(this.opStatusPm);
/* 34 */     out.writeObject(this.opModePm);
/*    */   }
/*    */   
/*    */   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*    */   {
/* 39 */     this.hwStatusPm = ((HwStatusPm)in.readObject());
/* 40 */     this.commState = (in.readByte() & 0xFF);
/* 41 */     this.opStatusPm = ((OpStatusPm)in.readObject());
/* 42 */     this.opModePm = ((OpModePm)in.readObject());
/*    */   }
/*    */   
/*    */   public String toString() {
/* 46 */     StringBuilder sb = new StringBuilder();
/* 47 */     sb.append('[');
/* 48 */     sb.append("hwStatusPm: ").append(this.hwStatusPm).append(", ");
/* 49 */     sb.append("commState: ").append(this.commState).append(", ");
/* 50 */     sb.append("opStatusPm: ").append(this.opStatusPm).append(", ");
/* 51 */     sb.append("opModePm: ").append(this.opModePm).append(']');
/* 52 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\fw2\cmdparam\GetDevcieStatusRspPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */