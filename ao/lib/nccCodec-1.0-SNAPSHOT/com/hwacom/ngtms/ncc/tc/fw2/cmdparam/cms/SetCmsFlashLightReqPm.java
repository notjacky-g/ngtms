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
/*    */ @CommandParams(cmdName="setCmsFlashLightReq")
/*    */ public class SetCmsFlashLightReqPm
/*    */   implements Externalizable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   public static final int cmdId = 24321;
/*    */   public static final String cmdName = "setCmsFlashLightReq";
/*    */   @CommandParam(name="color")
/*    */   public int color;
/*    */   @CommandParam(name="flash")
/*    */   public int flash;
/*    */   
/*    */   public void writeExternal(ObjectOutput out)
/*    */     throws IOException
/*    */   {
/* 26 */     out.writeByte(this.color);
/* 27 */     out.writeByte(this.flash);
/*    */   }
/*    */   
/*    */   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*    */   {
/* 32 */     this.color = (in.readByte() & 0xFF);
/* 33 */     this.flash = (in.readByte() & 0xFF);
/*    */   }
/*    */   
/*    */   public String toString() {
/* 37 */     StringBuilder sb = new StringBuilder();
/* 38 */     sb.append('[');
/* 39 */     sb.append("color: ").append(this.color).append(", ");
/* 40 */     sb.append("flash: ").append(this.flash).append(']');
/* 41 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\fw2\cmdparam\cms\SetCmsFlashLightReqPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */