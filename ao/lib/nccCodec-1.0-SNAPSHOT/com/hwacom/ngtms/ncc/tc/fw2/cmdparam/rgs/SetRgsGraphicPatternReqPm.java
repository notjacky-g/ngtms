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
/*    */ 
/*    */ @CommandParams(cmdName="setRgsGraphicPatternReq")
/*    */ public class SetRgsGraphicPatternReqPm
/*    */   implements Externalizable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   public static final int cmdId = 151;
/*    */   public static final String cmdName = "setRgsGraphicPatternReq";
/*    */   @CommandParam(name="mode")
/*    */   public int mode;
/*    */   @CommandParam(name="gCodeId")
/*    */   public int gCodeId;
/*    */   @CommandParam(name="frameNo")
/*    */   public int frameNo;
/*    */   @CommandParam(name="rgsGraphicPatternColorWithDesc")
/*    */   public RgsGraphicPatternColorWithDescPm rgsGraphicPatternColorWithDescPm;
/*    */   
/*    */   public void writeExternal(ObjectOutput out)
/*    */     throws IOException
/*    */   {
/* 33 */     out.writeByte(this.mode);
/* 34 */     out.writeByte(this.gCodeId);
/* 35 */     out.writeByte(this.frameNo);
/* 36 */     out.writeObject(this.rgsGraphicPatternColorWithDescPm);
/*    */   }
/*    */   
/*    */   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*    */   {
/* 41 */     this.mode = (in.readByte() & 0xFF);
/* 42 */     this.gCodeId = (in.readByte() & 0xFF);
/* 43 */     this.frameNo = (in.readByte() & 0xFF);
/* 44 */     this.rgsGraphicPatternColorWithDescPm = ((RgsGraphicPatternColorWithDescPm)in.readObject());
/*    */   }
/*    */   
/*    */   public String toString() {
/* 48 */     StringBuilder sb = new StringBuilder();
/* 49 */     sb.append('[');
/* 50 */     sb.append("mode: ").append(this.mode).append(", ");
/* 51 */     sb.append("gCodeId: ").append(this.gCodeId).append(", ");
/* 52 */     sb.append("frameNo: ").append(this.frameNo).append(", ");
/* 53 */     sb.append("rgsGraphicPatternColorWithDescPm: ")
/* 54 */       .append(this.rgsGraphicPatternColorWithDescPm)
/* 55 */       .append(']');
/* 56 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\fw2\cmdparam\rgs\SetRgsGraphicPatternReqPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */