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
/*    */ @CommandParams(cmdName="setCmsDisplayMessageReq")
/*    */ public class SetCmsDisplayMessageReqPm
/*    */   implements Externalizable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   public static final int cmdId = 87;
/*    */   public static final String cmdName = "setCmsDisplayMessageReq";
/*    */   @CommandParam(name="dataType")
/*    */   public int dataType;
/*    */   @CommandParam(name="textContent")
/*    */   public TextContentPm textContentPm;
/*    */   @CommandParam(name="graphicContent")
/*    */   public GraphicContentPm graphicContentPm;
/*    */   
/*    */   public void writeExternal(ObjectOutput out)
/*    */     throws IOException
/*    */   {
/* 29 */     out.writeByte(this.dataType);
/* 30 */     out.writeObject(this.textContentPm);
/* 31 */     out.writeObject(this.graphicContentPm);
/*    */   }
/*    */   
/*    */   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*    */   {
/* 36 */     this.dataType = (in.readByte() & 0xFF);
/* 37 */     this.textContentPm = ((TextContentPm)in.readObject());
/* 38 */     this.graphicContentPm = ((GraphicContentPm)in.readObject());
/*    */   }
/*    */   
/*    */   public String toString() {
/* 42 */     StringBuilder sb = new StringBuilder();
/* 43 */     sb.append('[');
/* 44 */     sb.append("dataType: ").append(this.dataType).append(", ");
/* 45 */     sb.append("textContentPm: ").append(this.textContentPm).append(", ");
/* 46 */     sb.append("graphicContentPm: ").append(this.graphicContentPm).append(']');
/* 47 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\fw2\cmdparam\cms\SetCmsDisplayMessageReqPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */