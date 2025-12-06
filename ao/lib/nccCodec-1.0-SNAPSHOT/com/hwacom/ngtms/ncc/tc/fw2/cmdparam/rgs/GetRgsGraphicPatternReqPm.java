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
/*    */ 
/*    */ 
/*    */ @CommandParams(cmdName="getRgsGraphicPatternReq")
/*    */ public class GetRgsGraphicPatternReqPm
/*    */   implements Externalizable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   public static final int cmdId = 152;
/*    */   public static final String cmdName = "getRgsGraphicPatternReq";
/*    */   @CommandParam(name="mode")
/*    */   public int mode;
/*    */   @CommandParam(name="gCodeId")
/*    */   public int gCodeId;
/*    */   @CommandParam(name="rgsFrameIdWrapper")
/*    */   public RgsFrameIdWrapperPm rgsFrameIdWrapperPm;
/*    */   
/*    */   public void writeExternal(ObjectOutput out)
/*    */     throws IOException
/*    */   {
/* 30 */     out.writeByte(this.mode);
/* 31 */     out.writeByte(this.gCodeId);
/* 32 */     out.writeObject(this.rgsFrameIdWrapperPm);
/*    */   }
/*    */   
/*    */   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*    */   {
/* 37 */     this.mode = (in.readByte() & 0xFF);
/* 38 */     this.gCodeId = (in.readByte() & 0xFF);
/* 39 */     this.rgsFrameIdWrapperPm = ((RgsFrameIdWrapperPm)in.readObject());
/*    */   }
/*    */   
/*    */   public String toString() {
/* 43 */     StringBuilder sb = new StringBuilder();
/* 44 */     sb.append('[');
/* 45 */     sb.append("mode: ").append(this.mode).append(", ");
/* 46 */     sb.append("gCodeId: ").append(this.gCodeId).append(", ");
/* 47 */     sb.append("rgsFrameIdWrapperPm: ").append(this.rgsFrameIdWrapperPm).append(']');
/* 48 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\fw2\cmdparam\rgs\GetRgsGraphicPatternReqPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */