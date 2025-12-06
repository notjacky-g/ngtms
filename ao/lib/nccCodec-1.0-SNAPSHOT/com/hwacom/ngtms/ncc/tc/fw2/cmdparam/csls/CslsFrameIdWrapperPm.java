/*    */ package com.hwacom.ngtms.ncc.tc.fw2.cmdparam.csls;
/*    */ 
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParam;
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.GlobalParams;
/*    */ import java.io.Externalizable;
/*    */ import java.io.IOException;
/*    */ import java.io.ObjectInput;
/*    */ import java.io.ObjectOutput;
/*    */ 
/*    */ 
/*    */ @GlobalParams(paramsName="cslsFrameIdWrapper")
/*    */ public class CslsFrameIdWrapperPm
/*    */   implements Externalizable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   @CommandParam(name="frameNo")
/*    */   public int frameNo;
/*    */   @CommandParam(name="frameId")
/*    */   public int frameId;
/*    */   
/*    */   public void writeExternal(ObjectOutput out)
/*    */     throws IOException
/*    */   {
/* 24 */     out.writeByte(this.frameNo);
/* 25 */     out.writeByte(this.frameId);
/*    */   }
/*    */   
/*    */   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*    */   {
/* 30 */     this.frameNo = (in.readByte() & 0xFF);
/* 31 */     this.frameId = (in.readByte() & 0xFF);
/*    */   }
/*    */   
/*    */   public String toString() {
/* 35 */     StringBuilder sb = new StringBuilder();
/* 36 */     sb.append('[');
/* 37 */     sb.append("frameNo: ").append(this.frameNo).append(", ");
/* 38 */     sb.append("frameId: ").append(this.frameId).append(']');
/* 39 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\fw2\cmdparam\csls\CslsFrameIdWrapperPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */