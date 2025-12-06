/*    */ package com.hwacom.ngtms.ncc.tc.fw2.cmdparam.lsd;
/*    */ 
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParam;
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParams;
/*    */ import java.io.Externalizable;
/*    */ import java.io.IOException;
/*    */ import java.io.ObjectInput;
/*    */ import java.io.ObjectOutput;
/*    */ import java.util.List;
/*    */ 
/*    */ @CommandParams(cmdName="setSimulateDataReq")
/*    */ public class SetSimulateDataReqPm
/*    */   implements Externalizable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   public static final int cmdId = 51;
/*    */   public static final String cmdName = "setSimulateDataReq";
/*    */   @CommandParam(name="dataCount")
/*    */   public int dataCount;
/*    */   @CommandParam(name="dataSno")
/*    */   public int dataSno;
/*    */   @CommandParam(name="sensorVariableList")
/*    */   public List<SensorVariableListItem> sensorVariableList;
/*    */   
/*    */   public static class SensorVariableListItem
/*    */     implements Externalizable
/*    */   {
/*    */     private static final long serialVersionUID = 1L;
/*    */     @CommandParam(name="sensorVariable")
/*    */     public int sensorVariable;
/*    */     
/*    */     public void writeExternal(ObjectOutput out)
/*    */       throws IOException
/*    */     {
/* 35 */       out.writeShort(this.sensorVariable);
/*    */     }
/*    */     
/*    */     public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*    */     {
/* 40 */       this.sensorVariable = (in.readShort() & 0xFFFF);
/*    */     }
/*    */     
/*    */     public String toString() {
/* 44 */       StringBuilder sb = new StringBuilder();
/* 45 */       sb.append('[');
/* 46 */       sb.append("sensorVariable: ").append(this.sensorVariable).append(']');
/* 47 */       return sb.toString();
/*    */     }
/*    */   }
/*    */   
/*    */   public void writeExternal(ObjectOutput out) throws IOException
/*    */   {
/* 53 */     out.writeShort(this.dataCount);
/* 54 */     out.writeShort(this.dataSno);
/* 55 */     out.writeObject(this.sensorVariableList);
/*    */   }
/*    */   
/*    */   public void readExternal(ObjectInput in)
/*    */     throws IOException, ClassNotFoundException
/*    */   {
/* 61 */     this.dataCount = (in.readShort() & 0xFFFF);
/* 62 */     this.dataSno = (in.readShort() & 0xFFFF);
/* 63 */     this.sensorVariableList = ((List)in.readObject());
/*    */   }
/*    */   
/*    */   public String toString() {
/* 67 */     StringBuilder sb = new StringBuilder();
/* 68 */     sb.append('[');
/* 69 */     sb.append("dataCount: ").append(this.dataCount).append(", ");
/* 70 */     sb.append("dataSno: ").append(this.dataSno).append(", ");
/* 71 */     sb.append("sensorVariableList: ").append(this.sensorVariableList).append(']');
/* 72 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\fw2\cmdparam\lsd\SetSimulateDataReqPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */