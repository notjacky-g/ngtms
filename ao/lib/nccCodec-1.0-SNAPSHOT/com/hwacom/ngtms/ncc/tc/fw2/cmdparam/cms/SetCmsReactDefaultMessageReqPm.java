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
/*    */ @CommandParams(cmdName="setCmsReactDefaultMessageReq")
/*    */ public class SetCmsReactDefaultMessageReqPm
/*    */   implements Externalizable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   public static final int cmdId = 84;
/*    */   public static final String cmdName = "setCmsReactDefaultMessageReq";
/*    */   @CommandParam(name="eventNo")
/*    */   public int eventNo;
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
/* 32 */     out.writeByte(this.eventNo);
/* 33 */     out.writeByte(this.dataType);
/* 34 */     out.writeObject(this.textContentPm);
/* 35 */     out.writeObject(this.graphicContentPm);
/*    */   }
/*    */   
/*    */   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*    */   {
/* 40 */     this.eventNo = (in.readByte() & 0xFF);
/* 41 */     this.dataType = (in.readByte() & 0xFF);
/* 42 */     this.textContentPm = ((TextContentPm)in.readObject());
/* 43 */     this.graphicContentPm = ((GraphicContentPm)in.readObject());
/*    */   }
/*    */   
/*    */   public String toString() {
/* 47 */     StringBuilder sb = new StringBuilder();
/* 48 */     sb.append('[');
/* 49 */     sb.append("eventNo: ").append(this.eventNo).append(", ");
/* 50 */     sb.append("dataType: ").append(this.dataType).append(", ");
/* 51 */     sb.append("textContentPm: ").append(this.textContentPm).append(", ");
/* 52 */     sb.append("graphicContentPm: ").append(this.graphicContentPm).append(']');
/* 53 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\fw2\cmdparam\cms\SetCmsReactDefaultMessageReqPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */