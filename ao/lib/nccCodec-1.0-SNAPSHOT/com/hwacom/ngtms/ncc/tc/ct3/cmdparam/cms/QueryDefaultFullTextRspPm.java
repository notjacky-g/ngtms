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
/*    */ @CommandParams(cmdName="queryDefaultFullTextRsp")
/*    */ public class QueryDefaultFullTextRspPm
/*    */   implements Externalizable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   public static final int cmdId = 44993;
/*    */   public static final String cmdName = "queryDefaultFullTextRsp";
/*    */   @CommandParam(name="textId")
/*    */   public int textId;
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
/*    */     public void writeExternal(ObjectOutput out) throws IOException
/*    */     {
/* 32 */       out.writeShort(this.wordCode);
/*    */     }
/*    */     
/*    */     public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*    */     {
/* 37 */       this.wordCode = (in.readShort() & 0xFFFF);
/*    */     }
/*    */     
/*    */     public String toString() {
/* 41 */       StringBuilder sb = new StringBuilder();
/* 42 */       sb.append('[');
/* 43 */       sb.append("wordCode: ").append(this.wordCode).append(']');
/* 44 */       return sb.toString();
/*    */     }
/*    */   }
/*    */   
/*    */   public void writeExternal(ObjectOutput out) throws IOException
/*    */   {
/* 50 */     out.writeByte(this.textId);
/* 51 */     out.writeObject(this.wordCodeList);
/*    */   }
/*    */   
/*    */   public void readExternal(ObjectInput in)
/*    */     throws IOException, ClassNotFoundException
/*    */   {
/* 57 */     this.textId = (in.readByte() & 0xFF);
/* 58 */     this.wordCodeList = ((List)in.readObject());
/*    */   }
/*    */   
/*    */   public String toString() {
/* 62 */     StringBuilder sb = new StringBuilder();
/* 63 */     sb.append('[');
/* 64 */     sb.append("textId: ").append(this.textId).append(", ");
/* 65 */     sb.append("wordCodeList: ").append(this.wordCodeList).append(']');
/* 66 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\ct3\cmdparam\cms\QueryDefaultFullTextRspPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */