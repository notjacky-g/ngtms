/*    */ package com.hwacom.ngtms.ncc.tc.fw2.cmdparam.qld;
/*    */ 
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParam;
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParams;
/*    */ import com.hwacom.ngtms.ncc.tc.fw2.cmdparam.DhmsPm;
/*    */ import com.hwacom.ngtms.ncc.tc.fw2.cmdparam.HwStatusPm;
/*    */ import java.io.Externalizable;
/*    */ import java.io.IOException;
/*    */ import java.io.ObjectInput;
/*    */ import java.io.ObjectOutput;
/*    */ 
/*    */ @CommandParams(cmdName="qldEventReport")
/*    */ public class QldEventReportPm
/*    */   implements Externalizable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   public static final int cmdId = 24323;
/*    */   public static final String cmdName = "qldEventReport";
/*    */   @CommandParam(name="hwStatus")
/*    */   public HwStatusPm hwStatusPm;
/*    */   @CommandParam(name="dhms")
/*    */   public DhmsPm dhmsPm;
/*    */   @CommandParam(name="congestionData")
/*    */   public int congestionData;
/*    */   
/*    */   public void writeExternal(ObjectOutput out)
/*    */     throws IOException
/*    */   {
/* 29 */     out.writeObject(this.hwStatusPm);
/* 30 */     out.writeObject(this.dhmsPm);
/* 31 */     out.writeByte(this.congestionData);
/*    */   }
/*    */   
/*    */   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*    */   {
/* 36 */     this.hwStatusPm = ((HwStatusPm)in.readObject());
/* 37 */     this.dhmsPm = ((DhmsPm)in.readObject());
/* 38 */     this.congestionData = (in.readByte() & 0xFF);
/*    */   }
/*    */   
/*    */   public String toString() {
/* 42 */     StringBuilder sb = new StringBuilder();
/* 43 */     sb.append('[');
/* 44 */     sb.append("hwStatusPm: ").append(this.hwStatusPm).append(", ");
/* 45 */     sb.append("dhmsPm: ").append(this.dhmsPm).append(", ");
/* 46 */     sb.append("congestionData: ").append(this.congestionData).append(']');
/* 47 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\fw2\cmdparam\qld\QldEventReportPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */