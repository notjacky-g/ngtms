/*    */ package com.hwacom.ngtms.ncc.tc.fw2.cmdparam.rms;
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
/*    */ @CommandParams(cmdName="setRmsVdqOccupyAndRateReq")
/*    */ public class SetRmsVdqOccupyAndRateReqPm
/*    */   implements Externalizable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   public static final int cmdId = 138;
/*    */   public static final String cmdName = "setRmsVdqOccupyAndRateReq";
/*    */   @CommandParam(name="recordList")
/*    */   public List<RecordListItem> recordList;
/*    */   
/*    */   public static class RecordListItem
/*    */     implements Externalizable
/*    */   {
/*    */     private static final long serialVersionUID = 1L;
/*    */     @CommandParam(name="planNo")
/*    */     public int planNo;
/*    */     @CommandParam(name="rate")
/*    */     public int rate;
/*    */     @CommandParam(name="minOccupy")
/*    */     public int minOccupy;
/*    */     @CommandParam(name="maxOccupy")
/*    */     public int maxOccupy;
/*    */     
/*    */     public void writeExternal(ObjectOutput out)
/*    */       throws IOException
/*    */     {
/* 38 */       out.writeByte(this.planNo);
/* 39 */       out.writeByte(this.rate);
/* 40 */       out.writeByte(this.minOccupy);
/* 41 */       out.writeByte(this.maxOccupy);
/*    */     }
/*    */     
/*    */     public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*    */     {
/* 46 */       this.planNo = (in.readByte() & 0xFF);
/* 47 */       this.rate = (in.readByte() & 0xFF);
/* 48 */       this.minOccupy = (in.readByte() & 0xFF);
/* 49 */       this.maxOccupy = (in.readByte() & 0xFF);
/*    */     }
/*    */     
/*    */     public String toString() {
/* 53 */       StringBuilder sb = new StringBuilder();
/* 54 */       sb.append('[');
/* 55 */       sb.append("planNo: ").append(this.planNo).append(", ");
/* 56 */       sb.append("rate: ").append(this.rate).append(", ");
/* 57 */       sb.append("minOccupy: ").append(this.minOccupy).append(", ");
/* 58 */       sb.append("maxOccupy: ").append(this.maxOccupy).append(']');
/* 59 */       return sb.toString();
/*    */     }
/*    */   }
/*    */   
/*    */   public void writeExternal(ObjectOutput out) throws IOException
/*    */   {
/* 65 */     out.writeObject(this.recordList);
/*    */   }
/*    */   
/*    */   public void readExternal(ObjectInput in)
/*    */     throws IOException, ClassNotFoundException
/*    */   {
/* 71 */     this.recordList = ((List)in.readObject());
/*    */   }
/*    */   
/*    */   public String toString() {
/* 75 */     StringBuilder sb = new StringBuilder();
/* 76 */     sb.append('[');
/* 77 */     sb.append("recordList: ").append(this.recordList).append(']');
/* 78 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\fw2\cmdparam\rms\SetRmsVdqOccupyAndRateReqPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */