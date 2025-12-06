/*    */ package com.hwacom.ngtms.ncc.tc.ct3.cmdparam.cms;
/*    */ 
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParam;
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParams;
/*    */ import java.io.Externalizable;
/*    */ import java.io.IOException;
/*    */ import java.io.ObjectInput;
/*    */ import java.io.ObjectOutput;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @CommandParams(cmdName="setZonePatternReq")
/*    */ public class SetZonePatternReqPm
/*    */   implements Externalizable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   public static final int cmdId = 44852;
/*    */   public static final String cmdName = "setZonePatternReq";
/*    */   @CommandParam(name="patternId")
/*    */   public int patternId;
/*    */   @CommandParam(name="dataTypeX")
/*    */   public int dataTypeX;
/*    */   @CommandParam(name="dataTypeY")
/*    */   public int dataTypeY;
/*    */   @CommandParam(name="zoneList")
/*    */   public List<ZoneListItem> zoneList;
/*    */   
/*    */   public static class ZoneListItem
/*    */     implements Externalizable
/*    */   {
/*    */     private static final long serialVersionUID = 1L;
/*    */     @CommandParam(name="zoneNo")
/*    */     public int zoneNo;
/*    */     @CommandParam(name="loc")
/*    */     public int loc;
/*    */     @CommandParam(name="zoneSize")
/*    */     public int zoneSize;
/*    */     
/*    */     public void writeExternal(ObjectOutput out)
/*    */       throws IOException
/*    */     {
/* 44 */       out.writeByte(this.zoneNo);
/* 45 */       out.writeShort(this.loc);
/* 46 */       out.writeShort(this.zoneSize);
/*    */     }
/*    */     
/*    */     public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*    */     {
/* 51 */       this.zoneNo = (in.readByte() & 0xFF);
/* 52 */       this.loc = (in.readShort() & 0xFFFF);
/* 53 */       this.zoneSize = (in.readShort() & 0xFFFF);
/*    */     }
/*    */     
/*    */     public String toString() {
/* 57 */       StringBuilder sb = new StringBuilder();
/* 58 */       sb.append('[');
/* 59 */       sb.append("zoneNo: ").append(this.zoneNo).append(", ");
/* 60 */       sb.append("loc: ").append(this.loc).append(", ");
/* 61 */       sb.append("zoneSize: ").append(this.zoneSize).append(']');
/* 62 */       return sb.toString();
/*    */     }
/*    */   }
/*    */   
/*    */   public void writeExternal(ObjectOutput out) throws IOException
/*    */   {
/* 68 */     out.writeByte(this.patternId);
/* 69 */     out.writeByte(this.dataTypeX);
/* 70 */     out.writeByte(this.dataTypeY);
/* 71 */     out.writeObject(this.zoneList);
/*    */   }
/*    */   
/*    */   public void readExternal(ObjectInput in)
/*    */     throws IOException, ClassNotFoundException
/*    */   {
/* 77 */     this.patternId = (in.readByte() & 0xFF);
/* 78 */     this.dataTypeX = (in.readByte() & 0xFF);
/* 79 */     this.dataTypeY = (in.readByte() & 0xFF);
/* 80 */     this.zoneList = ((List)in.readObject());
/*    */   }
/*    */   
/*    */   public String toString() {
/* 84 */     StringBuilder sb = new StringBuilder();
/* 85 */     sb.append('[');
/* 86 */     sb.append("patternId: ").append(this.patternId).append(", ");
/* 87 */     sb.append("dataTypeX: ").append(this.dataTypeX).append(", ");
/* 88 */     sb.append("dataTypeY: ").append(this.dataTypeY).append(", ");
/* 89 */     sb.append("zoneList: ").append(this.zoneList).append(']');
/* 90 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\ct3\cmdparam\cms\SetZonePatternReqPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */