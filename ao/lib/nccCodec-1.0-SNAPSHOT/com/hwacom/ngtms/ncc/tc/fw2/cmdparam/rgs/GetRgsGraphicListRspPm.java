/*    */ package com.hwacom.ngtms.ncc.tc.fw2.cmdparam.rgs;
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
/*    */ @CommandParams(cmdName="getRgsGraphicListRsp")
/*    */ public class GetRgsGraphicListRspPm
/*    */   implements Externalizable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   public static final int cmdId = 153;
/*    */   public static final String cmdName = "getRgsGraphicListRsp";
/*    */   @CommandParam(name="mode")
/*    */   public int mode;
/*    */   @CommandParam(name="gCodeList")
/*    */   public List<GCodeListItem> gCodeList;
/*    */   
/*    */   public static class GCodeListItem
/*    */     implements Externalizable
/*    */   {
/*    */     private static final long serialVersionUID = 1L;
/*    */     @CommandParam(name="gCodeId")
/*    */     public int gCodeId;
/*    */     @CommandParam(name="gDescription")
/*    */     public byte[] gDescription;
/*    */     
/*    */     public void writeExternal(ObjectOutput out)
/*    */       throws IOException
/*    */     {
/* 36 */       out.writeByte(this.gCodeId);
/* 37 */       out.writeObject(this.gDescription);
/*    */     }
/*    */     
/*    */     public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*    */     {
/* 42 */       this.gCodeId = (in.readByte() & 0xFF);
/* 43 */       this.gDescription = ((byte[])in.readObject());
/*    */     }
/*    */     
/*    */     public String toString() {
/* 47 */       StringBuilder sb = new StringBuilder();
/* 48 */       sb.append('[');
/* 49 */       sb.append("gCodeId: ").append(this.gCodeId).append(", ");
/* 50 */       sb.append("gDescription: ")
/* 51 */         .append(BytesUtility.toHexString(this.gDescription))
/* 52 */         .append(']');
/* 53 */       return sb.toString();
/*    */     }
/*    */   }
/*    */   
/*    */   public void writeExternal(ObjectOutput out) throws IOException
/*    */   {
/* 59 */     out.writeByte(this.mode);
/* 60 */     out.writeObject(this.gCodeList);
/*    */   }
/*    */   
/*    */   public void readExternal(ObjectInput in)
/*    */     throws IOException, ClassNotFoundException
/*    */   {
/* 66 */     this.mode = (in.readByte() & 0xFF);
/* 67 */     this.gCodeList = ((List)in.readObject());
/*    */   }
/*    */   
/*    */   public String toString() {
/* 71 */     StringBuilder sb = new StringBuilder();
/* 72 */     sb.append('[');
/* 73 */     sb.append("mode: ").append(this.mode).append(", ");
/* 74 */     sb.append("gCodeList: ").append(this.gCodeList).append(']');
/* 75 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\fw2\cmdparam\rgs\GetRgsGraphicListRspPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */