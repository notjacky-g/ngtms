/*    */ package com.hwacom.ngtms.ncc.tc.ct3.cmdparam.cms;
/*    */ 
/*    */ import java.io.ObjectOutput;
/*    */ 
/*    */ @com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParams(cmdName="set3ColorTextColorParamReq")
/*    */ public class Set3ColorTextColorParamReqPm implements java.io.Externalizable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   public static final int cmdId = 44818;
/*    */   public static final String cmdName = "set3ColorTextColorParamReq";
/*    */   @com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParam(name="textId")
/*    */   public int textId;
/*    */   @com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParam(name="textColorList")
/*    */   public java.util.List<TextColorListItem> textColorList;
/*    */   @com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParam(name="vBound")
/*    */   public int vBound;
/*    */   @com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParam(name="hBound")
/*    */   public int hBound;
/*    */   @com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParam(name="vSpace")
/*    */   public int vSpace;
/*    */   @com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParam(name="hSpace")
/*    */   public int hSpace;
/*    */   
/*    */   public static class TextColorListItem implements java.io.Externalizable
/*    */   {
/*    */     private static final long serialVersionUID = 1L;
/*    */     @com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParam(name="textColor")
/*    */     public TextColorPm textColorPm;
/*    */     
/*    */     public void writeExternal(ObjectOutput out) throws java.io.IOException
/*    */     {
/* 32 */       out.writeObject(this.textColorPm);
/*    */     }
/*    */     
/*    */     public void readExternal(java.io.ObjectInput in) throws java.io.IOException, ClassNotFoundException
/*    */     {
/* 37 */       this.textColorPm = ((TextColorPm)in.readObject());
/*    */     }
/*    */     
/*    */     public String toString() {
/* 41 */       StringBuilder sb = new StringBuilder();
/* 42 */       sb.append('[');
/* 43 */       sb.append("textColorPm: ").append(this.textColorPm).append(']');
/* 44 */       return sb.toString();
/*    */     }
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public void writeExternal(ObjectOutput out)
/*    */     throws java.io.IOException
/*    */   {
/* 62 */     out.writeByte(this.textId);
/* 63 */     out.writeObject(this.textColorList);
/* 64 */     out.writeByte(this.vBound);
/* 65 */     out.writeByte(this.hBound);
/* 66 */     out.writeShort(this.vSpace);
/* 67 */     out.writeShort(this.hSpace);
/*    */   }
/*    */   
/*    */   public void readExternal(java.io.ObjectInput in)
/*    */     throws java.io.IOException, ClassNotFoundException
/*    */   {
/* 73 */     this.textId = (in.readByte() & 0xFF);
/* 74 */     this.textColorList = ((java.util.List)in.readObject());
/* 75 */     this.vBound = (in.readByte() & 0xFF);
/* 76 */     this.hBound = (in.readByte() & 0xFF);
/* 77 */     this.vSpace = (in.readShort() & 0xFFFF);
/* 78 */     this.hSpace = (in.readShort() & 0xFFFF);
/*    */   }
/*    */   
/*    */   public String toString() {
/* 82 */     StringBuilder sb = new StringBuilder();
/* 83 */     sb.append('[');
/* 84 */     sb.append("textId: ").append(this.textId).append(", ");
/* 85 */     sb.append("textColorList: ").append(this.textColorList).append(", ");
/* 86 */     sb.append("vBound: ").append(this.vBound).append(", ");
/* 87 */     sb.append("hBound: ").append(this.hBound).append(", ");
/* 88 */     sb.append("vSpace: ").append(this.vSpace).append(", ");
/* 89 */     sb.append("hSpace: ").append(this.hSpace).append(']');
/* 90 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\ct3\cmdparam\cms\Set3ColorTextColorParamReqPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */