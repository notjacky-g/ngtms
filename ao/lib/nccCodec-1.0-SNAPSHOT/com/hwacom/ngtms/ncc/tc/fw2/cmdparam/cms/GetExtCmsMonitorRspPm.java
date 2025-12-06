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
/*    */ 
/*    */ @CommandParams(cmdName="getExtCmsMonitorRsp")
/*    */ public class GetExtCmsMonitorRspPm
/*    */   implements Externalizable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   public static final int cmdId = 24405;
/*    */   public static final String cmdName = "getExtCmsMonitorRsp";
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
/*    */   @CommandParam(name="iconCodeId")
/*    */   public int iconCodeId;
/*    */   @CommandParam(name="gCodeId")
/*    */   public int gCodeId;
/*    */   @CommandParam(name="textContent")
/*    */   public TextContentPm textContentPm;
/*    */   
/*    */   public void writeExternal(ObjectOutput out)
/*    */     throws IOException
/*    */   {
/* 44 */     out.writeObject(this.hwStatusPm);
/* 45 */     out.writeByte(this.commState);
/* 46 */     out.writeObject(this.opStatusPm);
/* 47 */     out.writeObject(this.opModePm);
/* 48 */     out.writeByte(this.dataType);
/* 49 */     out.writeByte(this.iconCodeId);
/* 50 */     out.writeByte(this.gCodeId);
/* 51 */     out.writeObject(this.textContentPm);
/*    */   }
/*    */   
/*    */   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*    */   {
/* 56 */     this.hwStatusPm = ((HwStatusPm)in.readObject());
/* 57 */     this.commState = (in.readByte() & 0xFF);
/* 58 */     this.opStatusPm = ((OpStatusPm)in.readObject());
/* 59 */     this.opModePm = ((OpModePm)in.readObject());
/* 60 */     this.dataType = (in.readByte() & 0xFF);
/* 61 */     this.iconCodeId = (in.readByte() & 0xFF);
/* 62 */     this.gCodeId = (in.readByte() & 0xFF);
/* 63 */     this.textContentPm = ((TextContentPm)in.readObject());
/*    */   }
/*    */   
/*    */   public String toString() {
/* 67 */     StringBuilder sb = new StringBuilder();
/* 68 */     sb.append('[');
/* 69 */     sb.append("hwStatusPm: ").append(this.hwStatusPm).append(", ");
/* 70 */     sb.append("commState: ").append(this.commState).append(", ");
/* 71 */     sb.append("opStatusPm: ").append(this.opStatusPm).append(", ");
/* 72 */     sb.append("opModePm: ").append(this.opModePm).append(", ");
/* 73 */     sb.append("dataType: ").append(this.dataType).append(", ");
/* 74 */     sb.append("iconCodeId: ").append(this.iconCodeId).append(", ");
/* 75 */     sb.append("gCodeId: ").append(this.gCodeId).append(", ");
/* 76 */     sb.append("textContentPm: ").append(this.textContentPm).append(']');
/* 77 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\fw2\cmdparam\cms\GetExtCmsMonitorRspPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */