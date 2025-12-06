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
/*    */ @GlobalParams(paramsName="actuateType")
/*    */ public class ActuateTypePm
/*    */   implements Externalizable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   @CommandParam(name="bus")
/*    */   public int bus;
/*    */   @CommandParam(name="turnLeft")
/*    */   public int turnLeft;
/*    */   @CommandParam(name="ramp")
/*    */   public int ramp;
/*    */   @CommandParam(name="railway")
/*    */   public int railway;
/*    */   @CommandParam(name="pedestrian")
/*    */   public int pedestrian;
/*    */   @CommandParam(name="all")
/*    */   public int all;
/*    */   @CommandParam(name="brake")
/*    */   public int brake;
/*    */   @CommandParam(name="reserved")
/*    */   public int reserved;
/*    */   
/*    */   public void writeExternal(ObjectOutput out)
/*    */     throws IOException
/*    */   {
/* 42 */     out.writeByte(this.bus);
/* 43 */     out.writeByte(this.turnLeft);
/* 44 */     out.writeByte(this.ramp);
/* 45 */     out.writeByte(this.railway);
/* 46 */     out.writeByte(this.pedestrian);
/* 47 */     out.writeByte(this.all);
/* 48 */     out.writeByte(this.brake);
/* 49 */     out.writeByte(this.reserved);
/*    */   }
/*    */   
/*    */   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*    */   {
/* 54 */     this.bus = (in.readByte() & 0xFFFF);
/* 55 */     this.turnLeft = (in.readByte() & 0xFFFF);
/* 56 */     this.ramp = (in.readByte() & 0xFFFF);
/* 57 */     this.railway = (in.readByte() & 0xFFFF);
/* 58 */     this.pedestrian = (in.readByte() & 0xFFFF);
/* 59 */     this.all = (in.readByte() & 0xFFFF);
/* 60 */     this.brake = (in.readByte() & 0xFFFF);
/* 61 */     this.reserved = (in.readByte() & 0xFFFF);
/*    */   }
/*    */   
/*    */   public String toString() {
/* 65 */     StringBuilder sb = new StringBuilder();
/* 66 */     sb.append('[');
/* 67 */     sb.append("bus: ").append(this.bus).append(", ");
/* 68 */     sb.append("turnLeft: ").append(this.turnLeft).append(", ");
/* 69 */     sb.append("ramp: ").append(this.ramp).append(", ");
/* 70 */     sb.append("railway: ").append(this.railway).append(", ");
/* 71 */     sb.append("pedestrian: ").append(this.pedestrian).append(", ");
/* 72 */     sb.append("all: ").append(this.all).append(", ");
/* 73 */     sb.append("brake: ").append(this.brake).append(", ");
/* 74 */     sb.append("reserved: ").append(this.reserved).append(']');
/* 75 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\ct3\cmdparam\sig\ActuateTypePm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */