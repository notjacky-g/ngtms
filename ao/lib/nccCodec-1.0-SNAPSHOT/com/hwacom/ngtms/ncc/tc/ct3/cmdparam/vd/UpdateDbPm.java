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
/*    */ @GlobalParams(paramsName="updateDb")
/*    */ public class UpdateDbPm
/*    */   implements Externalizable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   @CommandParam(name="timeDate")
/*    */   public int timeDate;
/*    */   @CommandParam(name="laneCountDirection")
/*    */   public int laneCountDirection;
/*    */   @CommandParam(name="realTimeLaneCfg")
/*    */   public int realTimeLaneCfg;
/*    */   @CommandParam(name="actuateCfg")
/*    */   public int actuateCfg;
/*    */   
/*    */   public void writeExternal(ObjectOutput out)
/*    */     throws IOException
/*    */   {
/* 30 */     out.writeByte(this.timeDate);
/* 31 */     out.writeByte(this.laneCountDirection);
/* 32 */     out.writeByte(this.realTimeLaneCfg);
/* 33 */     out.writeByte(this.actuateCfg);
/*    */   }
/*    */   
/*    */   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*    */   {
/* 38 */     this.timeDate = (in.readByte() & 0xFFFF);
/* 39 */     this.laneCountDirection = (in.readByte() & 0xFFFF);
/* 40 */     this.realTimeLaneCfg = (in.readByte() & 0xFFFF);
/* 41 */     this.actuateCfg = (in.readByte() & 0xFFFF);
/*    */   }
/*    */   
/*    */   public String toString() {
/* 45 */     StringBuilder sb = new StringBuilder();
/* 46 */     sb.append('[');
/* 47 */     sb.append("timeDate: ").append(this.timeDate).append(", ");
/* 48 */     sb.append("laneCountDirection: ").append(this.laneCountDirection).append(", ");
/* 49 */     sb.append("realTimeLaneCfg: ").append(this.realTimeLaneCfg).append(", ");
/* 50 */     sb.append("actuateCfg: ").append(this.actuateCfg).append(']');
/* 51 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\ct3\cmdparam\vd\UpdateDbPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */