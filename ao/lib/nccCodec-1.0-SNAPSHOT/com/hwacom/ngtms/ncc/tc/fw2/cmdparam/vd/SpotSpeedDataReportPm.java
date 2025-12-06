/*    */ package com.hwacom.ngtms.ncc.tc.fw2.cmdparam.vd;
/*    */ 
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParam;
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParams;
/*    */ import com.hwacom.ngtms.ncc.tc.fw2.cmdparam.DhmsPm;
/*    */ import com.hwacom.ngtms.ncc.tc.fw2.cmdparam.HwStatusPm;
/*    */ import java.io.Externalizable;
/*    */ import java.io.IOException;
/*    */ import java.io.ObjectInput;
/*    */ import java.io.ObjectOutput;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ @CommandParams(cmdName="spotSpeedDataReport")
/*    */ public class SpotSpeedDataReportPm
/*    */   implements Externalizable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   public static final int cmdId = 24;
/*    */   public static final String cmdName = "spotSpeedDataReport";
/*    */   @CommandParam(name="responseType")
/*    */   public int responseType;
/*    */   @CommandParam(name="hwStatus")
/*    */   public HwStatusPm hwStatusPm;
/*    */   @CommandParam(name="laneId")
/*    */   public int laneId;
/*    */   @CommandParam(name="spotSpeedDataList")
/*    */   public List<SpotSpeedDataListItem> spotSpeedDataList;
/*    */   
/*    */   public static class SpotSpeedDataListItem
/*    */     implements Externalizable
/*    */   {
/*    */     private static final long serialVersionUID = 1L;
/*    */     @CommandParam(name="dhms")
/*    */     public DhmsPm dhmsPm;
/*    */     @CommandParam(name="carSpeed")
/*    */     public int carSpeed;
/*    */     @CommandParam(name="carLength")
/*    */     public int carLength;
/*    */     @CommandParam(name="carInterval")
/*    */     public int carInterval;
/*    */     
/*    */     public void writeExternal(ObjectOutput out)
/*    */       throws IOException
/*    */     {
/* 47 */       out.writeObject(this.dhmsPm);
/* 48 */       out.writeByte(this.carSpeed);
/* 49 */       out.writeByte(this.carLength);
/* 50 */       out.writeShort(this.carInterval);
/*    */     }
/*    */     
/*    */     public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*    */     {
/* 55 */       this.dhmsPm = ((DhmsPm)in.readObject());
/* 56 */       this.carSpeed = (in.readByte() & 0xFF);
/* 57 */       this.carLength = (in.readByte() & 0xFF);
/* 58 */       this.carInterval = (in.readShort() & 0xFFFF);
/*    */     }
/*    */     
/*    */     public String toString() {
/* 62 */       StringBuilder sb = new StringBuilder();
/* 63 */       sb.append('[');
/* 64 */       sb.append("dhmsPm: ").append(this.dhmsPm).append(", ");
/* 65 */       sb.append("carSpeed: ").append(this.carSpeed).append(", ");
/* 66 */       sb.append("carLength: ").append(this.carLength).append(", ");
/* 67 */       sb.append("carInterval: ").append(this.carInterval).append(']');
/* 68 */       return sb.toString();
/*    */     }
/*    */   }
/*    */   
/*    */   public void writeExternal(ObjectOutput out) throws IOException
/*    */   {
/* 74 */     out.writeByte(this.responseType);
/* 75 */     out.writeObject(this.hwStatusPm);
/* 76 */     out.writeByte(this.laneId);
/* 77 */     out.writeObject(this.spotSpeedDataList);
/*    */   }
/*    */   
/*    */   public void readExternal(ObjectInput in)
/*    */     throws IOException, ClassNotFoundException
/*    */   {
/* 83 */     this.responseType = (in.readByte() & 0xFF);
/* 84 */     this.hwStatusPm = ((HwStatusPm)in.readObject());
/* 85 */     this.laneId = (in.readByte() & 0xFF);
/* 86 */     this.spotSpeedDataList = ((List)in.readObject());
/*    */   }
/*    */   
/*    */   public String toString() {
/* 90 */     StringBuilder sb = new StringBuilder();
/* 91 */     sb.append('[');
/* 92 */     sb.append("responseType: ").append(this.responseType).append(", ");
/* 93 */     sb.append("hwStatusPm: ").append(this.hwStatusPm).append(", ");
/* 94 */     sb.append("laneId: ").append(this.laneId).append(", ");
/* 95 */     sb.append("spotSpeedDataList: ").append(this.spotSpeedDataList).append(']');
/* 96 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\fw2\cmdparam\vd\SpotSpeedDataReportPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */