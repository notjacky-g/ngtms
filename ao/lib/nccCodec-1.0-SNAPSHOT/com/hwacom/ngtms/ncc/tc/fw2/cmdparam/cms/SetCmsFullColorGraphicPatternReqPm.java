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
/*    */ 
/*    */ @CommandParams(cmdName="setCmsFullColorGraphicPatternReq")
/*    */ public class SetCmsFullColorGraphicPatternReqPm
/*    */   implements Externalizable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   public static final int cmdId = 24401;
/*    */   public static final String cmdName = "setCmsFullColorGraphicPatternReq";
/*    */   @CommandParam(name="gCodeId")
/*    */   public int gCodeId;
/*    */   @CommandParam(name="frameNo")
/*    */   public int frameNo;
/*    */   @CommandParam(name="graphicPatternColorWithDesc")
/*    */   public GraphicPatternColorWithDescPm graphicPatternColorWithDescPm;
/*    */   
/*    */   public void writeExternal(ObjectOutput out)
/*    */     throws IOException
/*    */   {
/* 29 */     out.writeByte(this.gCodeId);
/* 30 */     out.writeByte(this.frameNo);
/* 31 */     out.writeObject(this.graphicPatternColorWithDescPm);
/*    */   }
/*    */   
/*    */   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*    */   {
/* 36 */     this.gCodeId = (in.readByte() & 0xFF);
/* 37 */     this.frameNo = (in.readByte() & 0xFF);
/* 38 */     this.graphicPatternColorWithDescPm = ((GraphicPatternColorWithDescPm)in.readObject());
/*    */   }
/*    */   
/*    */   public String toString() {
/* 42 */     StringBuilder sb = new StringBuilder();
/* 43 */     sb.append('[');
/* 44 */     sb.append("gCodeId: ").append(this.gCodeId).append(", ");
/* 45 */     sb.append("frameNo: ").append(this.frameNo).append(", ");
/* 46 */     sb.append("graphicPatternColorWithDescPm: ").append(this.graphicPatternColorWithDescPm).append(']');
/* 47 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\fw2\cmdparam\cms\SetCmsFullColorGraphicPatternReqPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */