/*    */ package com.hwacom.ngtms.ncc.tc.fw2.cmdparam;
/*    */ 
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CmdBindingBase;
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParam;
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParams;
/*    */ import java.io.Externalizable;
/*    */ import java.io.IOException;
/*    */ import java.io.ObjectInput;
/*    */ import java.io.ObjectOutput;
/*    */ 
/*    */ @CommandParams(cmdName="getCmdProtocolRsp")
/*    */ public class GetCmdProtocolRspPm
/*    */   extends CmdBindingBase
/*    */   implements Externalizable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   public static final int cmdId = 4;
/*    */   public static final String cmdName = "getCmdProtocolRsp";
/*    */   @CommandParam(name="hwStatus")
/*    */   public HwStatusPm hwStatusPm;
/*    */   @CommandParam(name="protocolLength")
/*    */   public int protocolLength;
/*    */   @CommandParam(cmdRef=true)
/*    */   public Object cmdParamRef;
/*    */   
/*    */   public void writeExternal(ObjectOutput out)
/*    */     throws IOException
/*    */   {
/* 29 */     out.writeObject(this.hwStatusPm);
/* 30 */     out.writeShort(this.protocolLength);
/* 31 */     out.writeObject(this.cmdParamRef);
/*    */   }
/*    */   
/*    */   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*    */   {
/* 36 */     this.hwStatusPm = ((HwStatusPm)in.readObject());
/* 37 */     this.protocolLength = (in.readShort() & 0xFFFF);
/* 38 */     this.cmdParamRef = in.readObject();
/*    */   }
/*    */   
/*    */   public String toString() {
/* 42 */     StringBuilder sb = new StringBuilder();
/* 43 */     sb.append('[');
/* 44 */     sb.append("hwStatusPm: ").append(this.hwStatusPm).append(", ");
/* 45 */     sb.append("protocolLength: ").append(this.protocolLength).append(", ");
/* 46 */     sb.append("cmdParamRef: ").append(this.cmdParamRef).append(']');
/* 47 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\fw2\cmdparam\GetCmdProtocolRspPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */