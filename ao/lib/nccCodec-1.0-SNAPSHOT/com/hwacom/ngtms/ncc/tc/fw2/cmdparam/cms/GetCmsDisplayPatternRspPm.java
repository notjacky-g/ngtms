/*    */ package com.hwacom.ngtms.ncc.tc.fw2.cmdparam.cms;
/*    */ 
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParam;
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParams;
/*    */ import java.io.Externalizable;
/*    */ import java.io.IOException;
/*    */ import java.io.ObjectInput;
/*    */ import java.io.ObjectOutput;
/*    */ 
/*    */ 
/*    */ @CommandParams(cmdName="getCmsDisplayPatternRsp")
/*    */ public class GetCmsDisplayPatternRspPm
/*    */   implements Externalizable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   public static final int cmdId = 82;
/*    */   public static final String cmdName = "getCmsDisplayPatternRsp";
/*    */   @CommandParam(name="frameNo")
/*    */   public int frameNo;
/*    */   @CommandParam(name="graphicPatternColor")
/*    */   public GraphicPatternColorPm graphicPatternColorPm;
/*    */   
/*    */   public void writeExternal(ObjectOutput out)
/*    */     throws IOException
/*    */   {
/* 26 */     out.writeByte(this.frameNo);
/* 27 */     out.writeObject(this.graphicPatternColorPm);
/*    */   }
/*    */   
/*    */   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*    */   {
/* 32 */     this.frameNo = (in.readByte() & 0xFF);
/* 33 */     this.graphicPatternColorPm = ((GraphicPatternColorPm)in.readObject());
/*    */   }
/*    */   
/*    */   public String toString() {
/* 37 */     StringBuilder sb = new StringBuilder();
/* 38 */     sb.append('[');
/* 39 */     sb.append("frameNo: ").append(this.frameNo).append(", ");
/* 40 */     sb.append("graphicPatternColorPm: ").append(this.graphicPatternColorPm).append(']');
/* 41 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\fw2\cmdparam\cms\GetCmsDisplayPatternRspPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */