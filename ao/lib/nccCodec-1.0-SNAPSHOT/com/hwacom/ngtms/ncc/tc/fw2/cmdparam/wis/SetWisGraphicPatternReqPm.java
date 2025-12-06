/*    */ package com.hwacom.ngtms.ncc.tc.fw2.cmdparam.wis;
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
/*    */ @CommandParams(cmdName="setWisGraphicPatternReq")
/*    */ public class SetWisGraphicPatternReqPm
/*    */   implements Externalizable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   public static final int cmdId = 57297;
/*    */   public static final String cmdName = "setWisGraphicPatternReq";
/*    */   @CommandParam(name="gCodeId")
/*    */   public int gCodeId;
/*    */   @CommandParam(name="frameNo")
/*    */   public int frameNo;
/*    */   @CommandParam(name="wisGraphicPatternColorWithDesc")
/*    */   public WisGraphicPatternColorWithDescPm wisGraphicPatternColorWithDescPm;
/*    */   
/*    */   public void writeExternal(ObjectOutput out)
/*    */     throws IOException
/*    */   {
/* 29 */     out.writeByte(this.gCodeId);
/* 30 */     out.writeByte(this.frameNo);
/* 31 */     out.writeObject(this.wisGraphicPatternColorWithDescPm);
/*    */   }
/*    */   
/*    */   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*    */   {
/* 36 */     this.gCodeId = (in.readByte() & 0xFF);
/* 37 */     this.frameNo = (in.readByte() & 0xFF);
/* 38 */     this.wisGraphicPatternColorWithDescPm = ((WisGraphicPatternColorWithDescPm)in.readObject());
/*    */   }
/*    */   
/*    */   public String toString() {
/* 42 */     StringBuilder sb = new StringBuilder();
/* 43 */     sb.append('[');
/* 44 */     sb.append("gCodeId: ").append(this.gCodeId).append(", ");
/* 45 */     sb.append("frameNo: ").append(this.frameNo).append(", ");
/* 46 */     sb.append("wisGraphicPatternColorWithDescPm: ")
/* 47 */       .append(this.wisGraphicPatternColorWithDescPm)
/* 48 */       .append(']');
/* 49 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\fw2\cmdparam\wis\SetWisGraphicPatternReqPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */