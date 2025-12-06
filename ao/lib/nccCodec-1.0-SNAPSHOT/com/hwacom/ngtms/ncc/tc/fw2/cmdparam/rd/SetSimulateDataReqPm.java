/*    */ package com.hwacom.ngtms.ncc.tc.fw2.cmdparam.rd;
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
/*    */ @CommandParams(cmdName="setSimulateDataReq")
/*    */ public class SetSimulateDataReqPm
/*    */   implements Externalizable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   public static final int cmdId = 75;
/*    */   public static final String cmdName = "setSimulateDataReq";
/*    */   @CommandParam(name="dataCount")
/*    */   public int dataCount;
/*    */   @CommandParam(name="dataSno")
/*    */   public int dataSno;
/*    */   @CommandParam(name="currentPluviometric")
/*    */   public int currentPluviometric;
/*    */   
/*    */   public void writeExternal(ObjectOutput out)
/*    */     throws IOException
/*    */   {
/* 29 */     out.writeShort(this.dataCount);
/* 30 */     out.writeShort(this.dataSno);
/* 31 */     out.writeByte(this.currentPluviometric);
/*    */   }
/*    */   
/*    */   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*    */   {
/* 36 */     this.dataCount = (in.readShort() & 0xFFFF);
/* 37 */     this.dataSno = (in.readShort() & 0xFFFF);
/* 38 */     this.currentPluviometric = (in.readByte() & 0xFF);
/*    */   }
/*    */   
/*    */   public String toString() {
/* 42 */     StringBuilder sb = new StringBuilder();
/* 43 */     sb.append('[');
/* 44 */     sb.append("dataCount: ").append(this.dataCount).append(", ");
/* 45 */     sb.append("dataSno: ").append(this.dataSno).append(", ");
/* 46 */     sb.append("currentPluviometric: ").append(this.currentPluviometric).append(']');
/* 47 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\fw2\cmdparam\rd\SetSimulateDataReqPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */