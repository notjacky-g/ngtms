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
/*    */ @CommandParams(cmdName="getCmsFullColorGraphicRsp")
/*    */ public class GetCmsFullColorGraphicRspPm
/*    */   implements Externalizable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   public static final int cmdId = 24412;
/*    */   public static final String cmdName = "getCmsFullColorGraphicRsp";
/*    */   @CommandParam(name="gCodeList")
/*    */   public List<GCodeListItem> gCodeList;
/*    */   
/*    */   public static class GCodeListItem implements Externalizable
/*    */   {
/*    */     private static final long serialVersionUID = 1L;
/*    */     @CommandParam(name="gCodeId")
/*    */     public int gCodeId;
/*    */     @CommandParam(name="gCodeDescription")
/*    */     public byte[] gCodeDescription;
/*    */     
/*    */     public void writeExternal(ObjectOutput out) throws IOException
/*    */     {
/* 32 */       out.writeByte(this.gCodeId);
/* 33 */       out.writeObject(this.gCodeDescription);
/*    */     }
/*    */     
/*    */     public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*    */     {
/* 38 */       this.gCodeId = (in.readByte() & 0xFF);
/* 39 */       this.gCodeDescription = ((byte[])in.readObject());
/*    */     }
/*    */     
/*    */     public String toString() {
/* 43 */       StringBuilder sb = new StringBuilder();
/* 44 */       sb.append('[');
/* 45 */       sb.append("gCodeId: ").append(this.gCodeId).append(", ");
/* 46 */       sb.append("gCodeDescription: ")
/* 47 */         .append(BytesUtility.toHexString(this.gCodeDescription))
/* 48 */         .append(']');
/* 49 */       return sb.toString();
/*    */     }
/*    */   }
/*    */   
/*    */   public void writeExternal(ObjectOutput out) throws IOException
/*    */   {
/* 55 */     out.writeObject(this.gCodeList);
/*    */   }
/*    */   
/*    */   public void readExternal(ObjectInput in)
/*    */     throws IOException, ClassNotFoundException
/*    */   {
/* 61 */     this.gCodeList = ((List)in.readObject());
/*    */   }
/*    */   
/*    */   public String toString() {
/* 65 */     StringBuilder sb = new StringBuilder();
/* 66 */     sb.append('[');
/* 67 */     sb.append("gCodeList: ").append(this.gCodeList).append(']');
/* 68 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\fw2\cmdparam\cms\GetCmsFullColorGraphicRspPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */