/*    */ package com.hwacom.ngtms.ncc.tc.ct3.cmdparam.cms;
/*    */ 
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParam;
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParams;
/*    */ import java.io.Externalizable;
/*    */ import java.io.IOException;
/*    */ import java.io.ObjectInput;
/*    */ import java.io.ObjectOutput;
/*    */ import java.util.List;
/*    */ 
/*    */ @CommandParams(cmdName="setDefaultFullTextReq")
/*    */ public class SetDefaultFullTextReqPm
/*    */   implements Externalizable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   public static final int cmdId = 44817;
/*    */   public static final String cmdName = "setDefaultFullTextReq";
/*    */   @CommandParam(name="textId")
/*    */   public int textId;
/*    */   @CommandParam(name="show")
/*    */   public int show;
/*    */   @CommandParam(name="wordCodeList")
/*    */   public List<WordCodeListItem> wordCodeList;
/*    */   
/*    */   public static class WordCodeListItem
/*    */     implements Externalizable
/*    */   {
/*    */     private static final long serialVersionUID = 1L;
/*    */     @CommandParam(name="wordCode")
/*    */     public int wordCode;
/*    */     
/*    */     public void writeExternal(ObjectOutput out)
/*    */       throws IOException
/*    */     {
/* 35 */       out.writeShort(this.wordCode);
/*    */     }
/*    */     
/*    */     public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*    */     {
/* 40 */       this.wordCode = (in.readShort() & 0xFFFF);
/*    */     }
/*    */     
/*    */     public String toString() {
/* 44 */       StringBuilder sb = new StringBuilder();
/* 45 */       sb.append('[');
/* 46 */       sb.append("wordCode: ").append(this.wordCode).append(']');
/* 47 */       return sb.toString();
/*    */     }
/*    */   }
/*    */   
/*    */   public void writeExternal(ObjectOutput out) throws IOException
/*    */   {
/* 53 */     out.writeByte(this.textId);
/* 54 */     out.writeByte(this.show);
/* 55 */     out.writeObject(this.wordCodeList);
/*    */   }
/*    */   
/*    */   public void readExternal(ObjectInput in)
/*    */     throws IOException, ClassNotFoundException
/*    */   {
/* 61 */     this.textId = (in.readByte() & 0xFF);
/* 62 */     this.show = (in.readByte() & 0xFF);
/* 63 */     this.wordCodeList = ((List)in.readObject());
/*    */   }
/*    */   
/*    */   public String toString() {
/* 67 */     StringBuilder sb = new StringBuilder();
/* 68 */     sb.append('[');
/* 69 */     sb.append("textId: ").append(this.textId).append(", ");
/* 70 */     sb.append("show: ").append(this.show).append(", ");
/* 71 */     sb.append("wordCodeList: ").append(this.wordCodeList).append(']');
/* 72 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\ct3\cmdparam\cms\SetDefaultFullTextReqPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */