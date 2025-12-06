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
/*    */ 
/*    */ @CommandParams(cmdName="setExtCmsDisplayMessageReq")
/*    */ public class SetExtCmsDisplayMessageReqPm
/*    */   implements Externalizable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   public static final int cmdId = 24407;
/*    */   public static final String cmdName = "setExtCmsDisplayMessageReq";
/*    */   @CommandParam(name="dataType")
/*    */   public int dataType;
/*    */   @CommandParam(name="iconCodeId")
/*    */   public int iconCodeId;
/*    */   @CommandParam(name="gCodeId")
/*    */   public int gCodeId;
/*    */   @CommandParam(name="textContent")
/*    */   public TextContentPm textContentPm;
/*    */   
/*    */   public void writeExternal(ObjectOutput out)
/*    */     throws IOException
/*    */   {
/* 32 */     out.writeByte(this.dataType);
/* 33 */     out.writeByte(this.iconCodeId);
/* 34 */     out.writeByte(this.gCodeId);
/* 35 */     out.writeObject(this.textContentPm);
/*    */   }
/*    */   
/*    */   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*    */   {
/* 40 */     this.dataType = (in.readByte() & 0xFF);
/* 41 */     this.iconCodeId = (in.readByte() & 0xFF);
/* 42 */     this.gCodeId = (in.readByte() & 0xFF);
/* 43 */     this.textContentPm = ((TextContentPm)in.readObject());
/*    */   }
/*    */   
/*    */   public String toString() {
/* 47 */     StringBuilder sb = new StringBuilder();
/* 48 */     sb.append('[');
/* 49 */     sb.append("dataType: ").append(this.dataType).append(", ");
/* 50 */     sb.append("iconCodeId: ").append(this.iconCodeId).append(", ");
/* 51 */     sb.append("gCodeId: ").append(this.gCodeId).append(", ");
/* 52 */     sb.append("textContentPm: ").append(this.textContentPm).append(']');
/* 53 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\fw2\cmdparam\cms\SetExtCmsDisplayMessageReqPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */