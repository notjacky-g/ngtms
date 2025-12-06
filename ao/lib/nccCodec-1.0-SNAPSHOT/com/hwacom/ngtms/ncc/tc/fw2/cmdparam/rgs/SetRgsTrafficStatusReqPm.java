/*    */ package com.hwacom.ngtms.ncc.tc.fw2.cmdparam.rgs;
/*    */ 
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParam;
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParams;
/*    */ import java.io.Externalizable;
/*    */ import java.io.IOException;
/*    */ import java.io.ObjectInput;
/*    */ import java.io.ObjectOutput;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ @CommandParams(cmdName="setRgsTrafficStatusReq")
/*    */ public class SetRgsTrafficStatusReqPm
/*    */   implements Externalizable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   public static final int cmdId = 147;
/*    */   public static final String cmdName = "setRgsTrafficStatusReq";
/*    */   @CommandParam(name="gCodeId")
/*    */   public int gCodeId;
/*    */   @CommandParam(name="setStatusList")
/*    */   public List<SetStatusListItem> setStatusList;
/*    */   
/*    */   public static class SetStatusListItem
/*    */     implements Externalizable
/*    */   {
/*    */     private static final long serialVersionUID = 1L;
/*    */     @CommandParam(name="sectionNo")
/*    */     public int sectionNo;
/*    */     @CommandParam(name="trafficStatus")
/*    */     public int trafficStatus;
/*    */     
/*    */     public void writeExternal(ObjectOutput out)
/*    */       throws IOException
/*    */     {
/* 36 */       out.writeByte(this.sectionNo);
/* 37 */       out.writeByte(this.trafficStatus);
/*    */     }
/*    */     
/*    */     public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*    */     {
/* 42 */       this.sectionNo = (in.readByte() & 0xFF);
/* 43 */       this.trafficStatus = (in.readByte() & 0xFF);
/*    */     }
/*    */     
/*    */     public String toString() {
/* 47 */       StringBuilder sb = new StringBuilder();
/* 48 */       sb.append('[');
/* 49 */       sb.append("sectionNo: ").append(this.sectionNo).append(", ");
/* 50 */       sb.append("trafficStatus: ").append(this.trafficStatus).append(']');
/* 51 */       return sb.toString();
/*    */     }
/*    */   }
/*    */   
/*    */   public void writeExternal(ObjectOutput out) throws IOException
/*    */   {
/* 57 */     out.writeByte(this.gCodeId);
/* 58 */     out.writeObject(this.setStatusList);
/*    */   }
/*    */   
/*    */   public void readExternal(ObjectInput in)
/*    */     throws IOException, ClassNotFoundException
/*    */   {
/* 64 */     this.gCodeId = (in.readByte() & 0xFF);
/* 65 */     this.setStatusList = ((List)in.readObject());
/*    */   }
/*    */   
/*    */   public String toString() {
/* 69 */     StringBuilder sb = new StringBuilder();
/* 70 */     sb.append('[');
/* 71 */     sb.append("gCodeId: ").append(this.gCodeId).append(", ");
/* 72 */     sb.append("setStatusList: ").append(this.setStatusList).append(']');
/* 73 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\fw2\cmdparam\rgs\SetRgsTrafficStatusReqPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */