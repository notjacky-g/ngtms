/*    */ package com.hwacom.ngtms.ncc.tc.fw3.cmdparam.scs;
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
/*    */ @GlobalParams(paramsName="planNoParam")
/*    */ public class PlanNoParamPm
/*    */   implements Externalizable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   @CommandParam(name="board1")
/*    */   public int board1;
/*    */   @CommandParam(name="board2")
/*    */   public int board2;
/*    */   @CommandParam(name="board3")
/*    */   public int board3;
/*    */   @CommandParam(name="signStatus")
/*    */   public int signStatus;
/*    */   
/*    */   public void writeExternal(ObjectOutput out)
/*    */     throws IOException
/*    */   {
/* 30 */     out.writeByte(this.board1);
/* 31 */     out.writeByte(this.board2);
/* 32 */     out.writeByte(this.board3);
/* 33 */     out.writeByte(this.signStatus);
/*    */   }
/*    */   
/*    */   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*    */   {
/* 38 */     this.board1 = (in.readByte() & 0xFFFF);
/* 39 */     this.board2 = (in.readByte() & 0xFFFF);
/* 40 */     this.board3 = (in.readByte() & 0xFFFF);
/* 41 */     this.signStatus = (in.readByte() & 0xFFFF);
/*    */   }
/*    */   
/*    */   public String toString() {
/* 45 */     StringBuilder sb = new StringBuilder();
/* 46 */     sb.append('[');
/* 47 */     sb.append("board1: ").append(this.board1).append(", ");
/* 48 */     sb.append("board2: ").append(this.board2).append(", ");
/* 49 */     sb.append("board3: ").append(this.board3).append(", ");
/* 50 */     sb.append("signStatus: ").append(this.signStatus).append(']');
/* 51 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\fw3\cmdparam\scs\PlanNoParamPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */