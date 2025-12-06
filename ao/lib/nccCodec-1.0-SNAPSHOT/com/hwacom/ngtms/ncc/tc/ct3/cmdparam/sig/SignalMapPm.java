/*    */ package com.hwacom.ngtms.ncc.tc.ct3.cmdparam.sig;
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
/*    */ @GlobalParams(paramsName="signalMap")
/*    */ public class SignalMapPm
/*    */   implements Externalizable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   @CommandParam(name="north")
/*    */   public int north;
/*    */   @CommandParam(name="eastNorth")
/*    */   public int eastNorth;
/*    */   @CommandParam(name="east")
/*    */   public int east;
/*    */   @CommandParam(name="eastSouth")
/*    */   public int eastSouth;
/*    */   @CommandParam(name="south")
/*    */   public int south;
/*    */   @CommandParam(name="westSouth")
/*    */   public int westSouth;
/*    */   @CommandParam(name="west")
/*    */   public int west;
/*    */   @CommandParam(name="westNorth")
/*    */   public int westNorth;
/*    */   
/*    */   public void writeExternal(ObjectOutput out)
/*    */     throws IOException
/*    */   {
/* 42 */     out.writeByte(this.north);
/* 43 */     out.writeByte(this.eastNorth);
/* 44 */     out.writeByte(this.east);
/* 45 */     out.writeByte(this.eastSouth);
/* 46 */     out.writeByte(this.south);
/* 47 */     out.writeByte(this.westSouth);
/* 48 */     out.writeByte(this.west);
/* 49 */     out.writeByte(this.westNorth);
/*    */   }
/*    */   
/*    */   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*    */   {
/* 54 */     this.north = (in.readByte() & 0xFFFF);
/* 55 */     this.eastNorth = (in.readByte() & 0xFFFF);
/* 56 */     this.east = (in.readByte() & 0xFFFF);
/* 57 */     this.eastSouth = (in.readByte() & 0xFFFF);
/* 58 */     this.south = (in.readByte() & 0xFFFF);
/* 59 */     this.westSouth = (in.readByte() & 0xFFFF);
/* 60 */     this.west = (in.readByte() & 0xFFFF);
/* 61 */     this.westNorth = (in.readByte() & 0xFFFF);
/*    */   }
/*    */   
/*    */   public String toString() {
/* 65 */     StringBuilder sb = new StringBuilder();
/* 66 */     sb.append('[');
/* 67 */     sb.append("north: ").append(this.north).append(", ");
/* 68 */     sb.append("eastNorth: ").append(this.eastNorth).append(", ");
/* 69 */     sb.append("east: ").append(this.east).append(", ");
/* 70 */     sb.append("eastSouth: ").append(this.eastSouth).append(", ");
/* 71 */     sb.append("south: ").append(this.south).append(", ");
/* 72 */     sb.append("westSouth: ").append(this.westSouth).append(", ");
/* 73 */     sb.append("west: ").append(this.west).append(", ");
/* 74 */     sb.append("westNorth: ").append(this.westNorth).append(']');
/* 75 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\ct3\cmdparam\sig\SignalMapPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */