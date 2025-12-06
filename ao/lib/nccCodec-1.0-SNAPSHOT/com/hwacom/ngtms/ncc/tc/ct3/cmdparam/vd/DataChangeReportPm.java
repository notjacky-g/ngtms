/*    */ package com.hwacom.ngtms.ncc.tc.ct3.cmdparam.vd;
/*    */ 
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParam;
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParams;
/*    */ import java.io.Externalizable;
/*    */ import java.io.IOException;
/*    */ import java.io.ObjectInput;
/*    */ import java.io.ObjectOutput;
/*    */ 
/*    */ 
/*    */ @CommandParams(cmdName="dataChangeReport")
/*    */ public class DataChangeReportPm
/*    */   implements Externalizable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   public static final int cmdId = 28426;
/*    */   public static final String cmdName = "dataChangeReport";
/*    */   @CommandParam(name="updateDb")
/*    */   public UpdateDbPm updateDbPm;
/*    */   @CommandParam(name="subDbId")
/*    */   public int subDbId;
/*    */   
/*    */   public void writeExternal(ObjectOutput out)
/*    */     throws IOException
/*    */   {
/* 26 */     out.writeObject(this.updateDbPm);
/* 27 */     out.writeByte(this.subDbId);
/*    */   }
/*    */   
/*    */   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*    */   {
/* 32 */     this.updateDbPm = ((UpdateDbPm)in.readObject());
/* 33 */     this.subDbId = (in.readByte() & 0xFF);
/*    */   }
/*    */   
/*    */   public String toString() {
/* 37 */     StringBuilder sb = new StringBuilder();
/* 38 */     sb.append('[');
/* 39 */     sb.append("updateDbPm: ").append(this.updateDbPm).append(", ");
/* 40 */     sb.append("subDbId: ").append(this.subDbId).append(']');
/* 41 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\ct3\cmdparam\vd\DataChangeReportPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */