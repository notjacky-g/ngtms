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
/*    */ @CommandParams(cmdName="actuateReport")
/*    */ public class ActuateReportPm
/*    */   implements Externalizable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   public static final int cmdId = 28422;
/*    */   public static final String cmdName = "actuateReport";
/*    */   @CommandParam(name="actuateType")
/*    */   public ActuateTypePm actuateTypePm;
/*    */   @CommandParam(name="laneId")
/*    */   public int laneId;
/*    */   
/*    */   public void writeExternal(ObjectOutput out)
/*    */     throws IOException
/*    */   {
/* 26 */     out.writeObject(this.actuateTypePm);
/* 27 */     out.writeByte(this.laneId);
/*    */   }
/*    */   
/*    */   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*    */   {
/* 32 */     this.actuateTypePm = ((ActuateTypePm)in.readObject());
/* 33 */     this.laneId = (in.readByte() & 0xFF);
/*    */   }
/*    */   
/*    */   public String toString() {
/* 37 */     StringBuilder sb = new StringBuilder();
/* 38 */     sb.append('[');
/* 39 */     sb.append("actuateTypePm: ").append(this.actuateTypePm).append(", ");
/* 40 */     sb.append("laneId: ").append(this.laneId).append(']');
/* 41 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\ct3\cmdparam\vd\ActuateReportPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */