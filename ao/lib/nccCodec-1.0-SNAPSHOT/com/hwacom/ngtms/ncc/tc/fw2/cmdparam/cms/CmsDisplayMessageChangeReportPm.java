/*    */ package com.hwacom.ngtms.ncc.tc.fw2.cmdparam.cms;
/*    */ 
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParam;
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParams;
/*    */ import com.hwacom.ngtms.ncc.tc.fw2.cmdparam.HwStatusPm;
/*    */ import com.hwacom.ngtms.ncc.tc.fw2.cmdparam.OpModePm;
/*    */ import com.hwacom.ngtms.ncc.tc.fw2.cmdparam.OpStatusPm;
/*    */ import java.io.Externalizable;
/*    */ import java.io.IOException;
/*    */ import java.io.ObjectInput;
/*    */ import java.io.ObjectOutput;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @CommandParams(cmdName="cmsDisplayMessageChangeReport")
/*    */ public class CmsDisplayMessageChangeReportPm
/*    */   implements Externalizable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   public static final int cmdId = 90;
/*    */   public static final String cmdName = "cmsDisplayMessageChangeReport";
/*    */   @CommandParam(name="hwStatus")
/*    */   public HwStatusPm hwStatusPm;
/*    */   @CommandParam(name="commState")
/*    */   public int commState;
/*    */   @CommandParam(name="opStatus")
/*    */   public OpStatusPm opStatusPm;
/*    */   @CommandParam(name="opMode")
/*    */   public OpModePm opModePm;
/*    */   @CommandParam(name="dataType")
/*    */   public int dataType;
/*    */   @CommandParam(name="textContent")
/*    */   public TextContentPm textContentPm;
/*    */   @CommandParam(name="graphicContentWithDesc")
/*    */   public GraphicContentWithDescPm graphicContentWithDescPm;
/*    */   
/*    */   public void writeExternal(ObjectOutput out)
/*    */     throws IOException
/*    */   {
/* 41 */     out.writeObject(this.hwStatusPm);
/* 42 */     out.writeByte(this.commState);
/* 43 */     out.writeObject(this.opStatusPm);
/* 44 */     out.writeObject(this.opModePm);
/* 45 */     out.writeByte(this.dataType);
/* 46 */     out.writeObject(this.textContentPm);
/* 47 */     out.writeObject(this.graphicContentWithDescPm);
/*    */   }
/*    */   
/*    */   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*    */   {
/* 52 */     this.hwStatusPm = ((HwStatusPm)in.readObject());
/* 53 */     this.commState = (in.readByte() & 0xFF);
/* 54 */     this.opStatusPm = ((OpStatusPm)in.readObject());
/* 55 */     this.opModePm = ((OpModePm)in.readObject());
/* 56 */     this.dataType = (in.readByte() & 0xFF);
/* 57 */     this.textContentPm = ((TextContentPm)in.readObject());
/* 58 */     this.graphicContentWithDescPm = ((GraphicContentWithDescPm)in.readObject());
/*    */   }
/*    */   
/*    */   public String toString() {
/* 62 */     StringBuilder sb = new StringBuilder();
/* 63 */     sb.append('[');
/* 64 */     sb.append("hwStatusPm: ").append(this.hwStatusPm).append(", ");
/* 65 */     sb.append("commState: ").append(this.commState).append(", ");
/* 66 */     sb.append("opStatusPm: ").append(this.opStatusPm).append(", ");
/* 67 */     sb.append("opModePm: ").append(this.opModePm).append(", ");
/* 68 */     sb.append("dataType: ").append(this.dataType).append(", ");
/* 69 */     sb.append("textContentPm: ").append(this.textContentPm).append(", ");
/* 70 */     sb.append("graphicContentWithDescPm: ").append(this.graphicContentWithDescPm).append(']');
/* 71 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\fw2\cmdparam\cms\CmsDisplayMessageChangeReportPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */