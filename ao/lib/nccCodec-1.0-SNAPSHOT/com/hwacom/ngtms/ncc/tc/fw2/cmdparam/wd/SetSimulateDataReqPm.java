/*    */ package com.hwacom.ngtms.ncc.tc.fw2.cmdparam.wd;
/*    */ 
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParam;
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParams;
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
/*    */ @CommandParams(cmdName="setSimulateDataReq")
/*    */ public class SetSimulateDataReqPm
/*    */   implements Externalizable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   public static final int cmdId = 43;
/*    */   public static final String cmdName = "setSimulateDataReq";
/*    */   @CommandParam(name="dataCount")
/*    */   public int dataCount;
/*    */   @CommandParam(name="dataSno")
/*    */   public int dataSno;
/*    */   @CommandParam(name="avgWindSpeed")
/*    */   public int avgWindSpeed;
/*    */   @CommandParam(name="avgWindDirection")
/*    */   public int avgWindDirection;
/*    */   @CommandParam(name="maxWindSpeed")
/*    */   public int maxWindSpeed;
/*    */   @CommandParam(name="maxWindDirection")
/*    */   public int maxWindDirection;
/*    */   
/*    */   public void writeExternal(ObjectOutput out)
/*    */     throws IOException
/*    */   {
/* 38 */     out.writeShort(this.dataCount);
/* 39 */     out.writeShort(this.dataSno);
/* 40 */     out.writeByte(this.avgWindSpeed);
/* 41 */     out.writeByte(this.avgWindDirection);
/* 42 */     out.writeByte(this.maxWindSpeed);
/* 43 */     out.writeByte(this.maxWindDirection);
/*    */   }
/*    */   
/*    */   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*    */   {
/* 48 */     this.dataCount = (in.readShort() & 0xFFFF);
/* 49 */     this.dataSno = (in.readShort() & 0xFFFF);
/* 50 */     this.avgWindSpeed = (in.readByte() & 0xFF);
/* 51 */     this.avgWindDirection = (in.readByte() & 0xFF);
/* 52 */     this.maxWindSpeed = (in.readByte() & 0xFF);
/* 53 */     this.maxWindDirection = (in.readByte() & 0xFF);
/*    */   }
/*    */   
/*    */   public String toString() {
/* 57 */     StringBuilder sb = new StringBuilder();
/* 58 */     sb.append('[');
/* 59 */     sb.append("dataCount: ").append(this.dataCount).append(", ");
/* 60 */     sb.append("dataSno: ").append(this.dataSno).append(", ");
/* 61 */     sb.append("avgWindSpeed: ").append(this.avgWindSpeed).append(", ");
/* 62 */     sb.append("avgWindDirection: ").append(this.avgWindDirection).append(", ");
/* 63 */     sb.append("maxWindSpeed: ").append(this.maxWindSpeed).append(", ");
/* 64 */     sb.append("maxWindDirection: ").append(this.maxWindDirection).append(']');
/* 65 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\fw2\cmdparam\wd\SetSimulateDataReqPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */