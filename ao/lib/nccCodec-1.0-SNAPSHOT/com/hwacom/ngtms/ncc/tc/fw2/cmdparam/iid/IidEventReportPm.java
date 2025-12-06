/*    */ package com.hwacom.ngtms.ncc.tc.fw2.cmdparam.iid;
/*    */ 
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParam;
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParams;
/*    */ import com.hwacom.ngtms.ncc.tc.fw2.cmdparam.DayTimePm;
/*    */ import com.hwacom.ngtms.ncc.tc.fw2.cmdparam.HwStatusPm;
/*    */ import java.io.Externalizable;
/*    */ import java.io.IOException;
/*    */ import java.io.ObjectInput;
/*    */ import java.io.ObjectOutput;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ @CommandParams(cmdName="iidEventReport")
/*    */ public class IidEventReportPm
/*    */   implements Externalizable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   public static final int cmdId = 22;
/*    */   public static final String cmdName = "iidEventReport";
/*    */   @CommandParam(name="camId")
/*    */   public int camId;
/*    */   @CommandParam(name="hwStatus")
/*    */   public HwStatusPm hwStatusPm;
/*    */   @CommandParam(name="dayTime")
/*    */   public DayTimePm dayTimePm;
/*    */   @CommandParam(name="eventId")
/*    */   public int eventId;
/*    */   @CommandParam(name="actionType")
/*    */   public int actionType;
/*    */   @CommandParam(name="laneIdList")
/*    */   public List<LaneIdListItem> laneIdList;
/*    */   
/*    */   public static class LaneIdListItem
/*    */     implements Externalizable
/*    */   {
/*    */     private static final long serialVersionUID = 1L;
/*    */     @CommandParam(name="laneId")
/*    */     public int laneId;
/*    */     
/*    */     public void writeExternal(ObjectOutput out)
/*    */       throws IOException
/*    */     {
/* 44 */       out.writeByte(this.laneId);
/*    */     }
/*    */     
/*    */     public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*    */     {
/* 49 */       this.laneId = (in.readByte() & 0xFF);
/*    */     }
/*    */     
/*    */     public String toString() {
/* 53 */       StringBuilder sb = new StringBuilder();
/* 54 */       sb.append('[');
/* 55 */       sb.append("laneId: ").append(this.laneId).append(']');
/* 56 */       return sb.toString();
/*    */     }
/*    */   }
/*    */   
/*    */   public void writeExternal(ObjectOutput out) throws IOException
/*    */   {
/* 62 */     out.writeByte(this.camId);
/* 63 */     out.writeObject(this.hwStatusPm);
/* 64 */     out.writeObject(this.dayTimePm);
/* 65 */     out.writeByte(this.eventId);
/* 66 */     out.writeByte(this.actionType);
/* 67 */     out.writeObject(this.laneIdList);
/*    */   }
/*    */   
/*    */   public void readExternal(ObjectInput in)
/*    */     throws IOException, ClassNotFoundException
/*    */   {
/* 73 */     this.camId = (in.readByte() & 0xFF);
/* 74 */     this.hwStatusPm = ((HwStatusPm)in.readObject());
/* 75 */     this.dayTimePm = ((DayTimePm)in.readObject());
/* 76 */     this.eventId = (in.readByte() & 0xFF);
/* 77 */     this.actionType = (in.readByte() & 0xFF);
/* 78 */     this.laneIdList = ((List)in.readObject());
/*    */   }
/*    */   
/*    */   public String toString() {
/* 82 */     StringBuilder sb = new StringBuilder();
/* 83 */     sb.append('[');
/* 84 */     sb.append("camId: ").append(this.camId).append(", ");
/* 85 */     sb.append("hwStatusPm: ").append(this.hwStatusPm).append(", ");
/* 86 */     sb.append("dayTimePm: ").append(this.dayTimePm).append(", ");
/* 87 */     sb.append("eventId: ").append(this.eventId).append(", ");
/* 88 */     sb.append("actionType: ").append(this.actionType).append(", ");
/* 89 */     sb.append("laneIdList: ").append(this.laneIdList).append(']');
/* 90 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\fw2\cmdparam\iid\IidEventReportPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */