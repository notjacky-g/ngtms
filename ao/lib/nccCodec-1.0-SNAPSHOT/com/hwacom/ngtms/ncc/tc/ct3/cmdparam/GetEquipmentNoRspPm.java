/*    */ package com.hwacom.ngtms.ncc.tc.ct3.cmdparam;
/*    */ 
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParam;
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParams;
/*    */ import java.io.Externalizable;
/*    */ import java.io.IOException;
/*    */ import java.io.ObjectInput;
/*    */ import java.io.ObjectOutput;
/*    */ import java.util.List;
/*    */ 
/*    */ @CommandParams(cmdName="getEquipmentNoRsp")
/*    */ public class GetEquipmentNoRspPm
/*    */   implements Externalizable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   public static final int cmdId = 4032;
/*    */   public static final String cmdName = "getEquipmentNoRsp";
/*    */   @CommandParam(name="equipmentNo")
/*    */   public int equipmentNo;
/*    */   @CommandParam(name="equipmentDataList")
/*    */   public List<EquipmentDataListItem> equipmentDataList;
/*    */   
/*    */   public static class EquipmentDataListItem
/*    */     implements Externalizable
/*    */   {
/*    */     private static final long serialVersionUID = 1L;
/*    */     @CommandParam(name="subEquipmentNo")
/*    */     public int subEquipmentNo;
/*    */     @CommandParam(name="equipmentId")
/*    */     public int equipmentId;
/*    */     
/*    */     public void writeExternal(ObjectOutput out) throws IOException
/*    */     {
/* 34 */       out.writeByte(this.subEquipmentNo);
/* 35 */       out.writeShort(this.equipmentId);
/*    */     }
/*    */     
/*    */     public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*    */     {
/* 40 */       this.subEquipmentNo = (in.readByte() & 0xFF);
/* 41 */       this.equipmentId = (in.readShort() & 0xFFFF);
/*    */     }
/*    */     
/*    */     public String toString() {
/* 45 */       StringBuilder sb = new StringBuilder();
/* 46 */       sb.append('[');
/* 47 */       sb.append("subEquipmentNo: ").append(this.subEquipmentNo).append(", ");
/* 48 */       sb.append("equipmentId: ").append(this.equipmentId).append(']');
/* 49 */       return sb.toString();
/*    */     }
/*    */   }
/*    */   
/*    */   public void writeExternal(ObjectOutput out) throws IOException
/*    */   {
/* 55 */     out.writeByte(this.equipmentNo);
/* 56 */     out.writeObject(this.equipmentDataList);
/*    */   }
/*    */   
/*    */   public void readExternal(ObjectInput in)
/*    */     throws IOException, ClassNotFoundException
/*    */   {
/* 62 */     this.equipmentNo = (in.readByte() & 0xFF);
/* 63 */     this.equipmentDataList = ((List)in.readObject());
/*    */   }
/*    */   
/*    */   public String toString() {
/* 67 */     StringBuilder sb = new StringBuilder();
/* 68 */     sb.append('[');
/* 69 */     sb.append("equipmentNo: ").append(this.equipmentNo).append(", ");
/* 70 */     sb.append("equipmentDataList: ").append(this.equipmentDataList).append(']');
/* 71 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\ct3\cmdparam\GetEquipmentNoRspPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */