/*    */ package com.hwacom.ngtms.ncc.tc.fw3.cmdparam.scs;
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
/*    */ @CommandParams(cmdName="setScsGraphicReq")
/*    */ public class SetScsGraphicReqPm
/*    */   implements Externalizable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   public static final int cmdId = 53226;
/*    */   public static final String cmdName = "setScsGraphicReq";
/*    */   @CommandParam(name="pictureLevel")
/*    */   public int pictureLevel;
/*    */   @CommandParam(name="width")
/*    */   public int width;
/*    */   @CommandParam(name="height")
/*    */   public int height;
/*    */   @CommandParam(name="packNum")
/*    */   public int packNum;
/*    */   @CommandParam(name="packData")
/*    */   public PackDataPm packDataPm;
/*    */   
/*    */   public void writeExternal(ObjectOutput out)
/*    */     throws IOException
/*    */   {
/* 35 */     out.writeByte(this.pictureLevel);
/* 36 */     out.writeShort(this.width);
/* 37 */     out.writeShort(this.height);
/* 38 */     out.writeByte(this.packNum);
/* 39 */     out.writeObject(this.packDataPm);
/*    */   }
/*    */   
/*    */   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*    */   {
/* 44 */     this.pictureLevel = (in.readByte() & 0xFF);
/* 45 */     this.width = (in.readShort() & 0xFFFF);
/* 46 */     this.height = (in.readShort() & 0xFFFF);
/* 47 */     this.packNum = (in.readByte() & 0xFF);
/* 48 */     this.packDataPm = ((PackDataPm)in.readObject());
/*    */   }
/*    */   
/*    */   public String toString() {
/* 52 */     StringBuilder sb = new StringBuilder();
/* 53 */     sb.append('[');
/* 54 */     sb.append("pictureLevel: ").append(this.pictureLevel).append(", ");
/* 55 */     sb.append("width: ").append(this.width).append(", ");
/* 56 */     sb.append("height: ").append(this.height).append(", ");
/* 57 */     sb.append("packNum: ").append(this.packNum).append(", ");
/* 58 */     sb.append("packDataPm: ").append(this.packDataPm).append(']');
/* 59 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\fw3\cmdparam\scs\SetScsGraphicReqPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */