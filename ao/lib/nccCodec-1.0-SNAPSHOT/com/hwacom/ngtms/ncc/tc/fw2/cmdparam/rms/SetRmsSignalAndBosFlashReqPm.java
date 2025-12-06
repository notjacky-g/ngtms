/*    */ package com.hwacom.ngtms.ncc.tc.fw2.cmdparam.rms;
/*    */ 
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParam;
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParams;
/*    */ import java.io.Externalizable;
/*    */ import java.io.IOException;
/*    */ import java.io.ObjectInput;
/*    */ import java.io.ObjectOutput;
/*    */ 
/*    */ 
/*    */ @CommandParams(cmdName="setRmsSignalAndBosFlashReq")
/*    */ public class SetRmsSignalAndBosFlashReqPm
/*    */   implements Externalizable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   public static final int cmdId = 168;
/*    */   public static final String cmdName = "setRmsSignalAndBosFlashReq";
/*    */   @CommandParam(name="signalDisplay")
/*    */   public int signalDisplay;
/*    */   @CommandParam(name="bosDisplay")
/*    */   public int bosDisplay;
/*    */   
/*    */   public void writeExternal(ObjectOutput out)
/*    */     throws IOException
/*    */   {
/* 26 */     out.writeByte(this.signalDisplay);
/* 27 */     out.writeByte(this.bosDisplay);
/*    */   }
/*    */   
/*    */   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*    */   {
/* 32 */     this.signalDisplay = (in.readByte() & 0xFF);
/* 33 */     this.bosDisplay = (in.readByte() & 0xFF);
/*    */   }
/*    */   
/*    */   public String toString() {
/* 37 */     StringBuilder sb = new StringBuilder();
/* 38 */     sb.append('[');
/* 39 */     sb.append("signalDisplay: ").append(this.signalDisplay).append(", ");
/* 40 */     sb.append("bosDisplay: ").append(this.bosDisplay).append(']');
/* 41 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\fw2\cmdparam\rms\SetRmsSignalAndBosFlashReqPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */