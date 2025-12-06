/*    */ package com.hwacom.ngtms.ncc.tc.fw2.cmdparam.tts;
/*    */ 
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParam;
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParams;
/*    */ import java.io.Externalizable;
/*    */ import java.io.IOException;
/*    */ import java.io.ObjectInput;
/*    */ import java.io.ObjectOutput;
/*    */ 
/*    */ 
/*    */ @CommandParams(cmdName="getTtsDisplayPatternReq")
/*    */ public class GetTtsDisplayPatternReqPm
/*    */   implements Externalizable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   public static final int cmdId = 24338;
/*    */   public static final String cmdName = "getTtsDisplayPatternReq";
/*    */   @CommandParam(name="displayPosition")
/*    */   public int displayPosition;
/*    */   @CommandParam(name="frameIdWrapper")
/*    */   public FrameIdWrapperPm frameIdWrapperPm;
/*    */   
/*    */   public void writeExternal(ObjectOutput out)
/*    */     throws IOException
/*    */   {
/* 26 */     out.writeByte(this.displayPosition);
/* 27 */     out.writeObject(this.frameIdWrapperPm);
/*    */   }
/*    */   
/*    */   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*    */   {
/* 32 */     this.displayPosition = (in.readByte() & 0xFF);
/* 33 */     this.frameIdWrapperPm = ((FrameIdWrapperPm)in.readObject());
/*    */   }
/*    */   
/*    */   public String toString() {
/* 37 */     StringBuilder sb = new StringBuilder();
/* 38 */     sb.append('[');
/* 39 */     sb.append("displayPosition: ").append(this.displayPosition).append(", ");
/* 40 */     sb.append("frameIdWrapperPm: ").append(this.frameIdWrapperPm).append(']');
/* 41 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\fw2\cmdparam\tts\GetTtsDisplayPatternReqPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */