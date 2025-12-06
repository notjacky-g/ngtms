/*    */ package com.hwacom.ngtms.ncc.tc.ct3.cmdparam;
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
/*    */ @CommandParams(cmdName="getVerRsp")
/*    */ public class GetVerRspPm
/*    */   implements Externalizable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   public static final int cmdId = 4035;
/*    */   public static final String cmdName = "getVerRsp";
/*    */   @CommandParam(name="year")
/*    */   public int year;
/*    */   @CommandParam(name="month")
/*    */   public int month;
/*    */   @CommandParam(name="day")
/*    */   public int day;
/*    */   @CommandParam(name="companyId")
/*    */   public int companyId;
/*    */   @CommandParam(name="version")
/*    */   public int version;
/*    */   @CommandParam(name="commandSet")
/*    */   public int commandSet;
/*    */   
/*    */   public void writeExternal(ObjectOutput out)
/*    */     throws IOException
/*    */   {
/* 37 */     out.writeByte(this.year);
/* 38 */     out.writeByte(this.month);
/* 39 */     out.writeByte(this.day);
/* 40 */     out.writeByte(this.companyId);
/* 41 */     out.writeByte(this.version);
/* 42 */     out.writeByte(this.commandSet);
/*    */   }
/*    */   
/*    */   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*    */   {
/* 47 */     this.year = (in.readByte() & 0xFF);
/* 48 */     this.month = (in.readByte() & 0xFF);
/* 49 */     this.day = (in.readByte() & 0xFF);
/* 50 */     this.companyId = (in.readByte() & 0xFF);
/* 51 */     this.version = (in.readByte() & 0xFF);
/* 52 */     this.commandSet = (in.readByte() & 0xFF);
/*    */   }
/*    */   
/*    */   public String toString() {
/* 56 */     StringBuilder sb = new StringBuilder();
/* 57 */     sb.append('[');
/* 58 */     sb.append("year: ").append(this.year).append(", ");
/* 59 */     sb.append("month: ").append(this.month).append(", ");
/* 60 */     sb.append("day: ").append(this.day).append(", ");
/* 61 */     sb.append("companyId: ").append(this.companyId).append(", ");
/* 62 */     sb.append("version: ").append(this.version).append(", ");
/* 63 */     sb.append("commandSet: ").append(this.commandSet).append(']');
/* 64 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\ct3\cmdparam\GetVerRspPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */