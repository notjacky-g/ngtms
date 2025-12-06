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
/*    */ 
/*    */ @CommandParams(cmdName="getTtsDisplayPatternRsp")
/*    */ public class GetTtsDisplayPatternRspPm
/*    */   implements Externalizable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   public static final int cmdId = 24338;
/*    */   public static final String cmdName = "getTtsDisplayPatternRsp";
/*    */   @CommandParam(name="displayPosition")
/*    */   public int displayPosition;
/*    */   @CommandParam(name="frameNo")
/*    */   public int frameNo;
/*    */   @CommandParam(name="graphicPatternColor")
/*    */   public GraphicPatternColorPm graphicPatternColorPm;
/*    */   
/*    */   public void writeExternal(ObjectOutput out)
/*    */     throws IOException
/*    */   {
/* 29 */     out.writeByte(this.displayPosition);
/* 30 */     out.writeByte(this.frameNo);
/* 31 */     out.writeObject(this.graphicPatternColorPm);
/*    */   }
/*    */   
/*    */   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*    */   {
/* 36 */     this.displayPosition = (in.readByte() & 0xFF);
/* 37 */     this.frameNo = (in.readByte() & 0xFF);
/* 38 */     this.graphicPatternColorPm = ((GraphicPatternColorPm)in.readObject());
/*    */   }
/*    */   
/*    */   public String toString() {
/* 42 */     StringBuilder sb = new StringBuilder();
/* 43 */     sb.append('[');
/* 44 */     sb.append("displayPosition: ").append(this.displayPosition).append(", ");
/* 45 */     sb.append("frameNo: ").append(this.frameNo).append(", ");
/* 46 */     sb.append("graphicPatternColorPm: ").append(this.graphicPatternColorPm).append(']');
/* 47 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\fw2\cmdparam\tts\GetTtsDisplayPatternRspPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */