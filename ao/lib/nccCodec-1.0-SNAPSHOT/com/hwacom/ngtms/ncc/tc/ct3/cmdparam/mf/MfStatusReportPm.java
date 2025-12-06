/*    */ package com.hwacom.ngtms.ncc.tc.ct3.cmdparam.mf;
/*    */ 
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParam;
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParams;
/*    */ import java.io.Externalizable;
/*    */ import java.io.IOException;
/*    */ import java.io.ObjectInput;
/*    */ import java.io.ObjectOutput;
/*    */ import java.util.List;
/*    */ 
/*    */ @CommandParams(cmdName="mfStatusReport")
/*    */ public class MfStatusReportPm
/*    */   implements Externalizable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   public static final int cmdId = 28499;
/*    */   public static final String cmdName = "mfStatusReport";
/*    */   @CommandParam(name="mfStatusReportList")
/*    */   public List<MfStatusReportListItem> mfStatusReportList;
/*    */   
/*    */   public static class MfStatusReportListItem
/*    */     implements Externalizable
/*    */   {
/*    */     private static final long serialVersionUID = 1L;
/*    */     @CommandParam(name="mfStatus")
/*    */     public int mfStatus;
/*    */     @CommandParam(name="barrierStatus")
/*    */     public int barrierStatus;
/*    */     @CommandParam(name="tempStatus")
/*    */     public int tempStatus;
/*    */     
/*    */     public void writeExternal(ObjectOutput out)
/*    */       throws IOException
/*    */     {
/* 35 */       out.writeByte(this.mfStatus);
/* 36 */       out.writeByte(this.barrierStatus);
/* 37 */       out.writeByte(this.tempStatus);
/*    */     }
/*    */     
/*    */     public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*    */     {
/* 42 */       this.mfStatus = (in.readByte() & 0xFF);
/* 43 */       this.barrierStatus = (in.readByte() & 0xFF);
/* 44 */       this.tempStatus = (in.readByte() & 0xFF);
/*    */     }
/*    */     
/*    */     public String toString() {
/* 48 */       StringBuilder sb = new StringBuilder();
/* 49 */       sb.append('[');
/* 50 */       sb.append("mfStatus: ").append(this.mfStatus).append(", ");
/* 51 */       sb.append("barrierStatus: ").append(this.barrierStatus).append(", ");
/* 52 */       sb.append("tempStatus: ").append(this.tempStatus).append(']');
/* 53 */       return sb.toString();
/*    */     }
/*    */   }
/*    */   
/*    */   public void writeExternal(ObjectOutput out) throws IOException
/*    */   {
/* 59 */     out.writeObject(this.mfStatusReportList);
/*    */   }
/*    */   
/*    */   public void readExternal(ObjectInput in)
/*    */     throws IOException, ClassNotFoundException
/*    */   {
/* 65 */     this.mfStatusReportList = ((List)in.readObject());
/*    */   }
/*    */   
/*    */   public String toString() {
/* 69 */     StringBuilder sb = new StringBuilder();
/* 70 */     sb.append('[');
/* 71 */     sb.append("mfStatusReportList: ").append(this.mfStatusReportList).append(']');
/* 72 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\ct3\cmdparam\mf\MfStatusReportPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */