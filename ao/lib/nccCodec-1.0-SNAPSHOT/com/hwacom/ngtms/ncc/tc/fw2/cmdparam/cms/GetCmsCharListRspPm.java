/*    */ package com.hwacom.ngtms.ncc.tc.fw2.cmdparam.cms;
/*    */ 
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParam;
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParams;
/*    */ import java.io.Externalizable;
/*    */ import java.io.IOException;
/*    */ import java.io.ObjectInput;
/*    */ import java.io.ObjectOutput;
/*    */ import java.util.List;
/*    */ 
/*    */ @CommandParams(cmdName="getCmsCharListRsp")
/*    */ public class GetCmsCharListRspPm
/*    */   implements Externalizable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   public static final int cmdId = 88;
/*    */   public static final String cmdName = "getCmsCharListRsp";
/*    */   @CommandParam(name="charList")
/*    */   public List<CharListItem> charList;
/*    */   
/*    */   public static class CharListItem implements Externalizable
/*    */   {
/*    */     private static final long serialVersionUID = 1L;
/*    */     @CommandParam(name="codeId")
/*    */     public int codeId;
/*    */     
/*    */     public void writeExternal(ObjectOutput out) throws IOException
/*    */     {
/* 29 */       out.writeShort(this.codeId);
/*    */     }
/*    */     
/*    */     public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*    */     {
/* 34 */       this.codeId = (in.readShort() & 0xFFFF);
/*    */     }
/*    */     
/*    */     public String toString() {
/* 38 */       StringBuilder sb = new StringBuilder();
/* 39 */       sb.append('[');
/* 40 */       sb.append("codeId: ").append(this.codeId).append(']');
/* 41 */       return sb.toString();
/*    */     }
/*    */   }
/*    */   
/*    */   public void writeExternal(ObjectOutput out) throws IOException
/*    */   {
/* 47 */     out.writeObject(this.charList);
/*    */   }
/*    */   
/*    */   public void readExternal(ObjectInput in)
/*    */     throws IOException, ClassNotFoundException
/*    */   {
/* 53 */     this.charList = ((List)in.readObject());
/*    */   }
/*    */   
/*    */   public String toString() {
/* 57 */     StringBuilder sb = new StringBuilder();
/* 58 */     sb.append('[');
/* 59 */     sb.append("charList: ").append(this.charList).append(']');
/* 60 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\fw2\cmdparam\cms\GetCmsCharListRspPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */