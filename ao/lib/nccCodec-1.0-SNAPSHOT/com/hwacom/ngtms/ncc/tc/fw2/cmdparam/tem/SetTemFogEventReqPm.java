/*    */ package com.hwacom.ngtms.ncc.tc.fw2.cmdparam.tem;
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
/*    */ @CommandParams(cmdName="setTemFogEventReq")
/*    */ public class SetTemFogEventReqPm
/*    */   implements Externalizable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   public static final int cmdId = 35;
/*    */   public static final String cmdName = "setTemFogEventReq";
/*    */   @CommandParam(name="direction")
/*    */   public int direction;
/*    */   @CommandParam(name="mileKilometer")
/*    */   public int mileKilometer;
/*    */   @CommandParam(name="mileMeter")
/*    */   public int mileMeter;
/*    */   @CommandParam(name="status")
/*    */   public int status;
/*    */   
/*    */   public void writeExternal(ObjectOutput out)
/*    */     throws IOException
/*    */   {
/* 31 */     out.writeByte(this.direction);
/* 32 */     out.writeShort(this.mileKilometer);
/* 33 */     out.writeShort(this.mileMeter);
/* 34 */     out.writeByte(this.status);
/*    */   }
/*    */   
/*    */   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*    */   {
/* 39 */     this.direction = (in.readByte() & 0xFF);
/* 40 */     this.mileKilometer = (in.readShort() & 0xFFFF);
/* 41 */     this.mileMeter = (in.readShort() & 0xFFFF);
/* 42 */     this.status = (in.readByte() & 0xFF);
/*    */   }
/*    */   
/*    */   public String toString() {
/* 46 */     StringBuilder sb = new StringBuilder();
/* 47 */     sb.append('[');
/* 48 */     sb.append("direction: ").append(this.direction).append(", ");
/* 49 */     sb.append("mileKilometer: ").append(this.mileKilometer).append(", ");
/* 50 */     sb.append("mileMeter: ").append(this.mileMeter).append(", ");
/* 51 */     sb.append("status: ").append(this.status).append(']');
/* 52 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\fw2\cmdparam\tem\SetTemFogEventReqPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */