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
/*    */ @GlobalParams(paramsName="laneMap")
/*    */ public class LaneMapPm
/*    */   implements Externalizable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   @CommandParam(name="lane1")
/*    */   public int lane1;
/*    */   @CommandParam(name="lane2")
/*    */   public int lane2;
/*    */   @CommandParam(name="lane3")
/*    */   public int lane3;
/*    */   @CommandParam(name="lane4")
/*    */   public int lane4;
/*    */   @CommandParam(name="lane5")
/*    */   public int lane5;
/*    */   @CommandParam(name="lane6")
/*    */   public int lane6;
/*    */   @CommandParam(name="lane7")
/*    */   public int lane7;
/*    */   @CommandParam(name="lane8")
/*    */   public int lane8;
/*    */   
/*    */   public void writeExternal(ObjectOutput out)
/*    */     throws IOException
/*    */   {
/* 42 */     out.writeByte(this.lane1);
/* 43 */     out.writeByte(this.lane2);
/* 44 */     out.writeByte(this.lane3);
/* 45 */     out.writeByte(this.lane4);
/* 46 */     out.writeByte(this.lane5);
/* 47 */     out.writeByte(this.lane6);
/* 48 */     out.writeByte(this.lane7);
/* 49 */     out.writeByte(this.lane8);
/*    */   }
/*    */   
/*    */   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*    */   {
/* 54 */     this.lane1 = (in.readByte() & 0xFFFF);
/* 55 */     this.lane2 = (in.readByte() & 0xFFFF);
/* 56 */     this.lane3 = (in.readByte() & 0xFFFF);
/* 57 */     this.lane4 = (in.readByte() & 0xFFFF);
/* 58 */     this.lane5 = (in.readByte() & 0xFFFF);
/* 59 */     this.lane6 = (in.readByte() & 0xFFFF);
/* 60 */     this.lane7 = (in.readByte() & 0xFFFF);
/* 61 */     this.lane8 = (in.readByte() & 0xFFFF);
/*    */   }
/*    */   
/*    */   public String toString() {
/* 65 */     StringBuilder sb = new StringBuilder();
/* 66 */     sb.append('[');
/* 67 */     sb.append("lane1: ").append(this.lane1).append(", ");
/* 68 */     sb.append("lane2: ").append(this.lane2).append(", ");
/* 69 */     sb.append("lane3: ").append(this.lane3).append(", ");
/* 70 */     sb.append("lane4: ").append(this.lane4).append(", ");
/* 71 */     sb.append("lane5: ").append(this.lane5).append(", ");
/* 72 */     sb.append("lane6: ").append(this.lane6).append(", ");
/* 73 */     sb.append("lane7: ").append(this.lane7).append(", ");
/* 74 */     sb.append("lane8: ").append(this.lane8).append(']');
/* 75 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\fw2\cmdparam\vd\LaneMapPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */