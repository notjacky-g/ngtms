/*    */ package com.hwacom.ngtms.ncc.tc.ct3.cmdparam.sig;
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
/*    */ @CommandParams(cmdName="querySecretControlRsp")
/*    */ public class QuerySecretControlRspPm
/*    */   implements Externalizable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   public static final int cmdId = 24526;
/*    */   public static final String cmdName = "querySecretControlRsp";
/*    */   @CommandParam(name="inDirect")
/*    */   public int inDirect;
/*    */   @CommandParam(name="outDirect")
/*    */   public int outDirect;
/*    */   @CommandParam(name="inStartHour")
/*    */   public int inStartHour;
/*    */   @CommandParam(name="inStartMin")
/*    */   public int inStartMin;
/*    */   @CommandParam(name="inEndHour")
/*    */   public int inEndHour;
/*    */   @CommandParam(name="inEndMin")
/*    */   public int inEndMin;
/*    */   
/*    */   public void writeExternal(ObjectOutput out)
/*    */     throws IOException
/*    */   {
/* 38 */     out.writeByte(this.inDirect);
/* 39 */     out.writeByte(this.outDirect);
/* 40 */     out.writeByte(this.inStartHour);
/* 41 */     out.writeByte(this.inStartMin);
/* 42 */     out.writeByte(this.inEndHour);
/* 43 */     out.writeByte(this.inEndMin);
/*    */   }
/*    */   
/*    */   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*    */   {
/* 48 */     this.inDirect = (in.readByte() & 0xFF);
/* 49 */     this.outDirect = (in.readByte() & 0xFF);
/* 50 */     this.inStartHour = (in.readByte() & 0xFF);
/* 51 */     this.inStartMin = (in.readByte() & 0xFF);
/* 52 */     this.inEndHour = (in.readByte() & 0xFF);
/* 53 */     this.inEndMin = (in.readByte() & 0xFF);
/*    */   }
/*    */   
/*    */   public String toString() {
/* 57 */     StringBuilder sb = new StringBuilder();
/* 58 */     sb.append('[');
/* 59 */     sb.append("inDirect: ").append(this.inDirect).append(", ");
/* 60 */     sb.append("outDirect: ").append(this.outDirect).append(", ");
/* 61 */     sb.append("inStartHour: ").append(this.inStartHour).append(", ");
/* 62 */     sb.append("inStartMin: ").append(this.inStartMin).append(", ");
/* 63 */     sb.append("inEndHour: ").append(this.inEndHour).append(", ");
/* 64 */     sb.append("inEndMin: ").append(this.inEndMin).append(']');
/* 65 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\ct3\cmdparam\sig\QuerySecretControlRspPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */