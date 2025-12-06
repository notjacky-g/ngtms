/*    */ package com.hwacom.ngtms.ncc.tc.ct3.cmdparam.vd;
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
/*    */ @GlobalParams(paramsName="laneDataItem")
/*    */ public class LaneDataItemPm
/*    */   implements Externalizable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   @CommandParam(name="bigVolume")
/*    */   public int bigVolume;
/*    */   @CommandParam(name="bigSpeed")
/*    */   public int bigSpeed;
/*    */   @CommandParam(name="carVolume")
/*    */   public int carVolume;
/*    */   @CommandParam(name="carSpeed")
/*    */   public int carSpeed;
/*    */   @CommandParam(name="motorVolume")
/*    */   public int motorVolume;
/*    */   @CommandParam(name="motorSpeed")
/*    */   public int motorSpeed;
/*    */   @CommandParam(name="avgSpeed")
/*    */   public int avgSpeed;
/*    */   @CommandParam(name="laneOccupy")
/*    */   public int laneOccupy;
/*    */   @CommandParam(name="avgInt")
/*    */   public int avgInt;
/*    */   
/*    */   public void writeExternal(ObjectOutput out)
/*    */     throws IOException
/*    */   {
/* 45 */     out.writeByte(this.bigVolume);
/* 46 */     out.writeByte(this.bigSpeed);
/* 47 */     out.writeByte(this.carVolume);
/* 48 */     out.writeByte(this.carSpeed);
/* 49 */     out.writeByte(this.motorVolume);
/* 50 */     out.writeByte(this.motorSpeed);
/* 51 */     out.writeByte(this.avgSpeed);
/* 52 */     out.writeByte(this.laneOccupy);
/* 53 */     out.writeByte(this.avgInt);
/*    */   }
/*    */   
/*    */   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*    */   {
/* 58 */     this.bigVolume = (in.readByte() & 0xFF);
/* 59 */     this.bigSpeed = (in.readByte() & 0xFF);
/* 60 */     this.carVolume = (in.readByte() & 0xFF);
/* 61 */     this.carSpeed = (in.readByte() & 0xFF);
/* 62 */     this.motorVolume = (in.readByte() & 0xFF);
/* 63 */     this.motorSpeed = (in.readByte() & 0xFF);
/* 64 */     this.avgSpeed = (in.readByte() & 0xFF);
/* 65 */     this.laneOccupy = (in.readByte() & 0xFF);
/* 66 */     this.avgInt = (in.readByte() & 0xFF);
/*    */   }
/*    */   
/*    */   public String toString() {
/* 70 */     StringBuilder sb = new StringBuilder();
/* 71 */     sb.append('[');
/* 72 */     sb.append("bigVolume: ").append(this.bigVolume).append(", ");
/* 73 */     sb.append("bigSpeed: ").append(this.bigSpeed).append(", ");
/* 74 */     sb.append("carVolume: ").append(this.carVolume).append(", ");
/* 75 */     sb.append("carSpeed: ").append(this.carSpeed).append(", ");
/* 76 */     sb.append("motorVolume: ").append(this.motorVolume).append(", ");
/* 77 */     sb.append("motorSpeed: ").append(this.motorSpeed).append(", ");
/* 78 */     sb.append("avgSpeed: ").append(this.avgSpeed).append(", ");
/* 79 */     sb.append("laneOccupy: ").append(this.laneOccupy).append(", ");
/* 80 */     sb.append("avgInt: ").append(this.avgInt).append(']');
/* 81 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\ct3\cmdparam\vd\LaneDataItemPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */