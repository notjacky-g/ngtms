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
/*    */ @CommandParams(cmdName="getCmsGraphicListRsp")
/*    */ public class GetCmsGraphicListRspPm
/*    */   implements Externalizable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   public static final int cmdId = 89;
/*    */   public static final String cmdName = "getCmsGraphicListRsp";
/*    */   @CommandParam(name="graphicList")
/*    */   public List<GraphicListItem> graphicList;
/*    */   
/*    */   public static class GraphicListItem implements Externalizable
/*    */   {
/*    */     private static final long serialVersionUID = 1L;
/*    */     @CommandParam(name="gCodeId")
/*    */     public int gCodeId;
/*    */     
/*    */     public void writeExternal(ObjectOutput out) throws IOException
/*    */     {
/* 29 */       out.writeByte(this.gCodeId);
/*    */     }
/*    */     
/*    */     public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*    */     {
/* 34 */       this.gCodeId = (in.readByte() & 0xFF);
/*    */     }
/*    */     
/*    */     public String toString() {
/* 38 */       StringBuilder sb = new StringBuilder();
/* 39 */       sb.append('[');
/* 40 */       sb.append("gCodeId: ").append(this.gCodeId).append(']');
/* 41 */       return sb.toString();
/*    */     }
/*    */   }
/*    */   
/*    */   public void writeExternal(ObjectOutput out) throws IOException
/*    */   {
/* 47 */     out.writeObject(this.graphicList);
/*    */   }
/*    */   
/*    */   public void readExternal(ObjectInput in)
/*    */     throws IOException, ClassNotFoundException
/*    */   {
/* 53 */     this.graphicList = ((List)in.readObject());
/*    */   }
/*    */   
/*    */   public String toString() {
/* 57 */     StringBuilder sb = new StringBuilder();
/* 58 */     sb.append('[');
/* 59 */     sb.append("graphicList: ").append(this.graphicList).append(']');
/* 60 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\fw2\cmdparam\cms\GetCmsGraphicListRspPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */