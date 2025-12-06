/*    */ package com.hwacom.ngtms.ncc.tc.fw2.cmdparam.rms.r21;
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
/*    */ @CommandParams(cmdName="setR21RmsCapacityDecreaseReq")
/*    */ public class SetR21RmsCapacityDecreaseReqPm
/*    */   implements Externalizable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   public static final int cmdId = 171;
/*    */   public static final String cmdName = "setR21RmsCapacityDecreaseReq";
/*    */   @CommandParam(name="capacityDecrease")
/*    */   public int capacityDecrease;
/*    */   @CommandParam(name="entryPercentage")
/*    */   public int entryPercentage;
/*    */   
/*    */   public void writeExternal(ObjectOutput out)
/*    */     throws IOException
/*    */   {
/* 27 */     out.writeByte(this.capacityDecrease);
/* 28 */     out.writeByte(this.entryPercentage);
/*    */   }
/*    */   
/*    */   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*    */   {
/* 33 */     this.capacityDecrease = (in.readByte() & 0xFF);
/* 34 */     this.entryPercentage = (in.readByte() & 0xFF);
/*    */   }
/*    */   
/*    */   public String toString() {
/* 38 */     StringBuilder sb = new StringBuilder();
/* 39 */     sb.append('[');
/* 40 */     sb.append("capacityDecrease: ").append(this.capacityDecrease).append(", ");
/* 41 */     sb.append("entryPercentage: ").append(this.entryPercentage).append(']');
/* 42 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\fw2\cmdparam\rms\r21\SetR21RmsCapacityDecreaseReqPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */