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
/*    */ 
/*    */ 
/*    */ 
/*    */ @CommandParams(cmdName="setRmsPlanDataReq")
/*    */ public class SetRmsPlanDataReqPm
/*    */   implements Externalizable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   public static final int cmdId = 132;
/*    */   public static final String cmdName = "setRmsPlanDataReq";
/*    */   @CommandParam(name="rateList")
/*    */   public List<RateListItem> rateList;
/*    */   
/*    */   public static class RateListItem
/*    */     implements Externalizable
/*    */   {
/*    */     private static final long serialVersionUID = 1L;
/*    */     @CommandParam(name="planNo")
/*    */     public int planNo;
/*    */     @CommandParam(name="rate")
/*    */     public int rate;
/*    */     @CommandParam(name="timeCycle")
/*    */     public int timeCycle;
/*    */     @CommandParam(name="greenTime")
/*    */     public int greenTime;
/*    */     @CommandParam(name="yellowTime")
/*    */     public int yellowTime;
/*    */     @CommandParam(name="redTime")
/*    */     public int redTime;
/*    */     @CommandParam(name="vehicleNo")
/*    */     public int vehicleNo;
/*    */     
/*    */     public void writeExternal(ObjectOutput out)
/*    */       throws IOException
/*    */     {
/* 47 */       out.writeByte(this.planNo);
/* 48 */       out.writeByte(this.rate);
/* 49 */       out.writeByte(this.timeCycle);
/* 50 */       out.writeByte(this.greenTime);
/* 51 */       out.writeByte(this.yellowTime);
/* 52 */       out.writeByte(this.redTime);
/* 53 */       out.writeByte(this.vehicleNo);
/*    */     }
/*    */     
/*    */     public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*    */     {
/* 58 */       this.planNo = (in.readByte() & 0xFF);
/* 59 */       this.rate = (in.readByte() & 0xFF);
/* 60 */       this.timeCycle = (in.readByte() & 0xFF);
/* 61 */       this.greenTime = (in.readByte() & 0xFF);
/* 62 */       this.yellowTime = (in.readByte() & 0xFF);
/* 63 */       this.redTime = (in.readByte() & 0xFF);
/* 64 */       this.vehicleNo = (in.readByte() & 0xFF);
/*    */     }
/*    */     
/*    */     public String toString() {
/* 68 */       StringBuilder sb = new StringBuilder();
/* 69 */       sb.append('[');
/* 70 */       sb.append("planNo: ").append(this.planNo).append(", ");
/* 71 */       sb.append("rate: ").append(this.rate).append(", ");
/* 72 */       sb.append("timeCycle: ").append(this.timeCycle).append(", ");
/* 73 */       sb.append("greenTime: ").append(this.greenTime).append(", ");
/* 74 */       sb.append("yellowTime: ").append(this.yellowTime).append(", ");
/* 75 */       sb.append("redTime: ").append(this.redTime).append(", ");
/* 76 */       sb.append("vehicleNo: ").append(this.vehicleNo).append(']');
/* 77 */       return sb.toString();
/*    */     }
/*    */   }
/*    */   
/*    */   public void writeExternal(ObjectOutput out) throws IOException
/*    */   {
/* 83 */     out.writeObject(this.rateList);
/*    */   }
/*    */   
/*    */   public void readExternal(ObjectInput in)
/*    */     throws IOException, ClassNotFoundException
/*    */   {
/* 89 */     this.rateList = ((List)in.readObject());
/*    */   }
/*    */   
/*    */   public String toString() {
/* 93 */     StringBuilder sb = new StringBuilder();
/* 94 */     sb.append('[');
/* 95 */     sb.append("rateList: ").append(this.rateList).append(']');
/* 96 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\fw2\cmdparam\rms\SetRmsPlanDataReqPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */