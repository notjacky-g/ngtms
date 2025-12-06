/*    */ package com.hwacom.ngtms.ncc.tc.ct3.cmdparam.sig;
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
/*    */ @CommandParams(cmdName="setTimingPlanBasicReq")
/*    */ public class SetTimingPlanBasicReqPm
/*    */   implements Externalizable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   public static final int cmdId = 24340;
/*    */   public static final String cmdName = "setTimingPlanBasicReq";
/*    */   @CommandParam(name="planId")
/*    */   public int planId;
/*    */   @CommandParam(name="timingPlanBasicList")
/*    */   public List<TimingPlanBasicListItem> timingPlanBasicList;
/*    */   
/*    */   public static class TimingPlanBasicListItem
/*    */     implements Externalizable
/*    */   {
/*    */     private static final long serialVersionUID = 1L;
/*    */     @CommandParam(name="minGreen")
/*    */     public int minGreen;
/*    */     @CommandParam(name="maxGreen")
/*    */     public int maxGreen;
/*    */     @CommandParam(name="yellow")
/*    */     public int yellow;
/*    */     @CommandParam(name="allRed")
/*    */     public int allRed;
/*    */     @CommandParam(name="pedGreedFlash")
/*    */     public int pedGreedFlash;
/*    */     @CommandParam(name="pedRed")
/*    */     public int pedRed;
/*    */     
/*    */     public void writeExternal(ObjectOutput out)
/*    */       throws IOException
/*    */     {
/* 47 */       out.writeByte(this.minGreen);
/* 48 */       out.writeShort(this.maxGreen);
/* 49 */       out.writeByte(this.yellow);
/* 50 */       out.writeByte(this.allRed);
/* 51 */       out.writeByte(this.pedGreedFlash);
/* 52 */       out.writeByte(this.pedRed);
/*    */     }
/*    */     
/*    */     public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*    */     {
/* 57 */       this.minGreen = (in.readByte() & 0xFF);
/* 58 */       this.maxGreen = (in.readShort() & 0xFFFF);
/* 59 */       this.yellow = (in.readByte() & 0xFF);
/* 60 */       this.allRed = (in.readByte() & 0xFF);
/* 61 */       this.pedGreedFlash = (in.readByte() & 0xFF);
/* 62 */       this.pedRed = (in.readByte() & 0xFF);
/*    */     }
/*    */     
/*    */     public String toString() {
/* 66 */       StringBuilder sb = new StringBuilder();
/* 67 */       sb.append('[');
/* 68 */       sb.append("minGreen: ").append(this.minGreen).append(", ");
/* 69 */       sb.append("maxGreen: ").append(this.maxGreen).append(", ");
/* 70 */       sb.append("yellow: ").append(this.yellow).append(", ");
/* 71 */       sb.append("allRed: ").append(this.allRed).append(", ");
/* 72 */       sb.append("pedGreedFlash: ").append(this.pedGreedFlash).append(", ");
/* 73 */       sb.append("pedRed: ").append(this.pedRed).append(']');
/* 74 */       return sb.toString();
/*    */     }
/*    */   }
/*    */   
/*    */   public void writeExternal(ObjectOutput out) throws IOException
/*    */   {
/* 80 */     out.writeByte(this.planId);
/* 81 */     out.writeObject(this.timingPlanBasicList);
/*    */   }
/*    */   
/*    */   public void readExternal(ObjectInput in)
/*    */     throws IOException, ClassNotFoundException
/*    */   {
/* 87 */     this.planId = (in.readByte() & 0xFF);
/* 88 */     this.timingPlanBasicList = ((List)in.readObject());
/*    */   }
/*    */   
/*    */   public String toString() {
/* 92 */     StringBuilder sb = new StringBuilder();
/* 93 */     sb.append('[');
/* 94 */     sb.append("planId: ").append(this.planId).append(", ");
/* 95 */     sb.append("timingPlanBasicList: ").append(this.timingPlanBasicList).append(']');
/* 96 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\ct3\cmdparam\sig\SetTimingPlanBasicReqPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */