/*    */ package com.hwacom.ngtms.ncc.tc.fw2.cmdparam.vd;
/*    */ 
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParam;
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.GlobalParams;
/*    */ import java.io.Externalizable;
/*    */ import java.io.IOException;
/*    */ import java.io.ObjectInput;
/*    */ import java.io.ObjectOutput;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @GlobalParams(paramsName="laneDataItem")
/*    */ public class LaneDataItemPm
/*    */   implements Externalizable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   @CommandParam(name="smallVolume")
/*    */   public int smallVolume;
/*    */   @CommandParam(name="smallSpeed")
/*    */   public int smallSpeed;
/*    */   @CommandParam(name="smallLength")
/*    */   public int smallLength;
/*    */   @CommandParam(name="bigVolume")
/*    */   public int bigVolume;
/*    */   @CommandParam(name="bigSpeed")
/*    */   public int bigSpeed;
/*    */   @CommandParam(name="bigLength")
/*    */   public int bigLength;
/*    */   @CommandParam(name="connectVolume")
/*    */   public int connectVolume;
/*    */   @CommandParam(name="connectSpeed")
/*    */   public int connectSpeed;
/*    */   @CommandParam(name="connectLength")
/*    */   public int connectLength;
/*    */   @CommandParam(name="avgInterval")
/*    */   public int avgInterval;
/*    */   @CommandParam(name="avgOccupancy")
/*    */   public int avgOccupancy;
/*    */   
/*    */   public void writeExternal(ObjectOutput out)
/*    */     throws IOException
/*    */   {
/* 51 */     out.writeByte(this.smallVolume);
/* 52 */     out.writeByte(this.smallSpeed);
/* 53 */     out.writeByte(this.smallLength);
/* 54 */     out.writeByte(this.bigVolume);
/* 55 */     out.writeByte(this.bigSpeed);
/* 56 */     out.writeByte(this.bigLength);
/* 57 */     out.writeByte(this.connectVolume);
/* 58 */     out.writeByte(this.connectSpeed);
/* 59 */     out.writeByte(this.connectLength);
/* 60 */     out.writeShort(this.avgInterval);
/* 61 */     out.writeByte(this.avgOccupancy);
/*    */   }
/*    */   
/*    */   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*    */   {
/* 66 */     this.smallVolume = (in.readByte() & 0xFF);
/* 67 */     this.smallSpeed = (in.readByte() & 0xFF);
/* 68 */     this.smallLength = (in.readByte() & 0xFF);
/* 69 */     this.bigVolume = (in.readByte() & 0xFF);
/* 70 */     this.bigSpeed = (in.readByte() & 0xFF);
/* 71 */     this.bigLength = (in.readByte() & 0xFF);
/* 72 */     this.connectVolume = (in.readByte() & 0xFF);
/* 73 */     this.connectSpeed = (in.readByte() & 0xFF);
/* 74 */     this.connectLength = (in.readByte() & 0xFF);
/* 75 */     this.avgInterval = (in.readShort() & 0xFFFF);
/* 76 */     this.avgOccupancy = (in.readByte() & 0xFF);
/*    */   }
/*    */   
/*    */   public String toString() {
/* 80 */     StringBuilder sb = new StringBuilder();
/* 81 */     sb.append('[');
/* 82 */     sb.append("smallVolume: ").append(this.smallVolume).append(", ");
/* 83 */     sb.append("smallSpeed: ").append(this.smallSpeed).append(", ");
/* 84 */     sb.append("smallLength: ").append(this.smallLength).append(", ");
/* 85 */     sb.append("bigVolume: ").append(this.bigVolume).append(", ");
/* 86 */     sb.append("bigSpeed: ").append(this.bigSpeed).append(", ");
/* 87 */     sb.append("bigLength: ").append(this.bigLength).append(", ");
/* 88 */     sb.append("connectVolume: ").append(this.connectVolume).append(", ");
/* 89 */     sb.append("connectSpeed: ").append(this.connectSpeed).append(", ");
/* 90 */     sb.append("connectLength: ").append(this.connectLength).append(", ");
/* 91 */     sb.append("avgInterval: ").append(this.avgInterval).append(", ");
/* 92 */     sb.append("avgOccupancy: ").append(this.avgOccupancy).append(']');
/* 93 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\fw2\cmdparam\vd\LaneDataItemPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */