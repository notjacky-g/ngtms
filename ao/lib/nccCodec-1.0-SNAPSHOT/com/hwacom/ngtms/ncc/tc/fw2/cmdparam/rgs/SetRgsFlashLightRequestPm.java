/*    */ package com.hwacom.ngtms.ncc.tc.fw2.cmdparam.rgs;
/*    */ 
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParam;
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParams;
/*    */ import java.io.Externalizable;
/*    */ import java.io.IOException;
/*    */ import java.io.ObjectInput;
/*    */ import java.io.ObjectOutput;
/*    */ 
/*    */ 
/*    */ @CommandParams(cmdName="setRgsFlashLightRequest")
/*    */ public class SetRgsFlashLightRequestPm
/*    */   implements Externalizable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   public static final int cmdId = 158;
/*    */   public static final String cmdName = "setRgsFlashLightRequest";
/*    */   @CommandParam(name="action")
/*    */   public int action;
/*    */   
/*    */   public void writeExternal(ObjectOutput out)
/*    */     throws IOException
/*    */   {
/* 24 */     out.writeByte(this.action);
/*    */   }
/*    */   
/*    */   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*    */   {
/* 29 */     this.action = (in.readByte() & 0xFF);
/*    */   }
/*    */   
/*    */   public String toString() {
/* 33 */     StringBuilder sb = new StringBuilder();
/* 34 */     sb.append('[');
/* 35 */     sb.append("action: ").append(this.action).append(']');
/* 36 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\fw2\cmdparam\rgs\SetRgsFlashLightRequestPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */