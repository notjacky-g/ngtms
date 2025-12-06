/*    */ package com.hwacom.ngtms.ncc.tc.fw2.cmdparam.csls;
/*    */ 
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParam;
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParams;
/*    */ import java.io.Externalizable;
/*    */ import java.io.IOException;
/*    */ import java.io.ObjectInput;
/*    */ import java.io.ObjectOutput;
/*    */ 
/*    */ 
/*    */ @CommandParams(cmdName="getCslsDisplayPatternRsp")
/*    */ public class GetCslsDisplayPatternRspPm
/*    */   implements Externalizable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   public static final int cmdId = 182;
/*    */   public static final String cmdName = "getCslsDisplayPatternRsp";
/*    */   @CommandParam(name="frameNo")
/*    */   public int frameNo;
/*    */   @CommandParam(name="cslsGraphicPatternColor")
/*    */   public CslsGraphicPatternColorPm cslsGraphicPatternColorPm;
/*    */   
/*    */   public void writeExternal(ObjectOutput out)
/*    */     throws IOException
/*    */   {
/* 26 */     out.writeByte(this.frameNo);
/* 27 */     out.writeObject(this.cslsGraphicPatternColorPm);
/*    */   }
/*    */   
/*    */   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*    */   {
/* 32 */     this.frameNo = (in.readByte() & 0xFF);
/* 33 */     this.cslsGraphicPatternColorPm = ((CslsGraphicPatternColorPm)in.readObject());
/*    */   }
/*    */   
/*    */   public String toString() {
/* 37 */     StringBuilder sb = new StringBuilder();
/* 38 */     sb.append('[');
/* 39 */     sb.append("frameNo: ").append(this.frameNo).append(", ");
/* 40 */     sb.append("cslsGraphicPatternColorPm: ").append(this.cslsGraphicPatternColorPm).append(']');
/* 41 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\fw2\cmdparam\csls\GetCslsDisplayPatternRspPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */