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
/*    */ @CommandParams(cmdName="setTimingPlanReq")
/*    */ public class SetTimingPlanReqPm implements Externalizable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   public static final int cmdId = 24341;
/*    */   public static final String cmdName = "setTimingPlanReq";
/*    */   @CommandParam(name="planId")
/*    */   public int planId;
/*    */   @CommandParam(name="direct")
/*    */   public int direct;
/*    */   @CommandParam(name="phaseOrder")
/*    */   public int phaseOrder;
/*    */   @CommandParam(name="timingPlanList")
/*    */   public List<TimingPlanListItem> timingPlanList;
/*    */   @CommandParam(name="cycleTime")
/*    */   public int cycleTime;
/*    */   @CommandParam(name="offset")
/*    */   public int offset;
/*    */   
/*    */   public static class TimingPlanListItem implements Externalizable
/*    */   {
/*    */     private static final long serialVersionUID = 1L;
/*    */     @CommandParam(name="green")
/*    */     public int green;
/*    */     
/*    */     public void writeExternal(ObjectOutput out) throws IOException
/*    */     {
/* 38 */       out.writeShort(this.green);
/*    */     }
/*    */     
/*    */     public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*    */     {
/* 43 */       this.green = (in.readShort() & 0xFFFF);
/*    */     }
/*    */     
/*    */     public String toString() {
/* 47 */       StringBuilder sb = new StringBuilder();
/* 48 */       sb.append('[');
/* 49 */       sb.append("green: ").append(this.green).append(']');
/* 50 */       return sb.toString();
/*    */     }
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public void writeExternal(ObjectOutput out)
/*    */     throws IOException
/*    */   {
/* 62 */     out.writeByte(this.planId);
/* 63 */     out.writeByte(this.direct);
/* 64 */     out.writeByte(this.phaseOrder);
/* 65 */     out.writeObject(this.timingPlanList);
/* 66 */     out.writeShort(this.cycleTime);
/* 67 */     out.writeShort(this.offset);
/*    */   }
/*    */   
/*    */   public void readExternal(ObjectInput in)
/*    */     throws IOException, ClassNotFoundException
/*    */   {
/* 73 */     this.planId = (in.readByte() & 0xFF);
/* 74 */     this.direct = (in.readByte() & 0xFF);
/* 75 */     this.phaseOrder = (in.readByte() & 0xFF);
/* 76 */     this.timingPlanList = ((List)in.readObject());
/* 77 */     this.cycleTime = (in.readShort() & 0xFFFF);
/* 78 */     this.offset = (in.readShort() & 0xFFFF);
/*    */   }
/*    */   
/*    */   public String toString() {
/* 82 */     StringBuilder sb = new StringBuilder();
/* 83 */     sb.append('[');
/* 84 */     sb.append("planId: ").append(this.planId).append(", ");
/* 85 */     sb.append("direct: ").append(this.direct).append(", ");
/* 86 */     sb.append("phaseOrder: ").append(this.phaseOrder).append(", ");
/* 87 */     sb.append("timingPlanList: ").append(this.timingPlanList).append(", ");
/* 88 */     sb.append("cycleTime: ").append(this.cycleTime).append(", ");
/* 89 */     sb.append("offset: ").append(this.offset).append(']');
/* 90 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\ct3\cmdparam\sig\SetTimingPlanReqPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */