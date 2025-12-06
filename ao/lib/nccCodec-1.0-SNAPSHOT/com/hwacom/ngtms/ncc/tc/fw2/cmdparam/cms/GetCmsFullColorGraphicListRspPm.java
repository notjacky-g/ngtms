/*    */ package com.hwacom.ngtms.ncc.tc.fw2.cmdparam.cms;
/*    */ 
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.BytesUtility;
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParam;
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParams;
/*    */ import java.io.Externalizable;
/*    */ import java.io.IOException;
/*    */ import java.io.ObjectInput;
/*    */ import java.io.ObjectOutput;
/*    */ import java.util.List;
/*    */ 
/*    */ @CommandParams(cmdName="getCmsFullColorGraphicListRsp")
/*    */ public class GetCmsFullColorGraphicListRspPm
/*    */   implements Externalizable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   public static final int cmdId = 24409;
/*    */   public static final String cmdName = "getCmsFullColorGraphicListRsp";
/*    */   @CommandParam(name="iconCodeList")
/*    */   public List<IconCodeListItem> iconCodeList;
/*    */   
/*    */   public static class IconCodeListItem implements Externalizable
/*    */   {
/*    */     private static final long serialVersionUID = 1L;
/*    */     @CommandParam(name="iconCodeId")
/*    */     public int iconCodeId;
/*    */     @CommandParam(name="iconCodeDescription")
/*    */     public byte[] iconCodeDescription;
/*    */     
/*    */     public void writeExternal(ObjectOutput out) throws IOException
/*    */     {
/* 32 */       out.writeByte(this.iconCodeId);
/* 33 */       out.writeObject(this.iconCodeDescription);
/*    */     }
/*    */     
/*    */     public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*    */     {
/* 38 */       this.iconCodeId = (in.readByte() & 0xFF);
/* 39 */       this.iconCodeDescription = ((byte[])in.readObject());
/*    */     }
/*    */     
/*    */     public String toString() {
/* 43 */       StringBuilder sb = new StringBuilder();
/* 44 */       sb.append('[');
/* 45 */       sb.append("iconCodeId: ").append(this.iconCodeId).append(", ");
/* 46 */       sb.append("iconCodeDescription: ")
/* 47 */         .append(BytesUtility.toHexString(this.iconCodeDescription))
/* 48 */         .append(']');
/* 49 */       return sb.toString();
/*    */     }
/*    */   }
/*    */   
/*    */   public void writeExternal(ObjectOutput out) throws IOException
/*    */   {
/* 55 */     out.writeObject(this.iconCodeList);
/*    */   }
/*    */   
/*    */   public void readExternal(ObjectInput in)
/*    */     throws IOException, ClassNotFoundException
/*    */   {
/* 61 */     this.iconCodeList = ((List)in.readObject());
/*    */   }
/*    */   
/*    */   public String toString() {
/* 65 */     StringBuilder sb = new StringBuilder();
/* 66 */     sb.append('[');
/* 67 */     sb.append("iconCodeList: ").append(this.iconCodeList).append(']');
/* 68 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\fw2\cmdparam\cms\GetCmsFullColorGraphicListRspPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */