/*    */ package com.hwacom.ngtms.ncc.tc.fw2.cmdparam.vd;
/*    */ 
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParam;
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParams;
/*    */ import java.io.Externalizable;
/*    */ import java.io.IOException;
/*    */ import java.io.ObjectInput;
/*    */ import java.io.ObjectOutput;
/*    */ import java.util.List;
/*    */ 
/*    */ @CommandParams(cmdName="setOccupyCfgReq")
/*    */ public class SetOccupyCfgReqPm
/*    */   implements Externalizable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   public static final int cmdId = 26;
/*    */   public static final String cmdName = "setOccupyCfgReq";
/*    */   @CommandParam(name="occupyCfgList")
/*    */   public List<OccupyCfgListItem> occupyCfgList;
/*    */   
/*    */   public static class OccupyCfgListItem
/*    */     implements Externalizable
/*    */   {
/*    */     private static final long serialVersionUID = 1L;
/*    */     @CommandParam(name="laneId")
/*    */     public int laneId;
/*    */     @CommandParam(name="occupyTime")
/*    */     public int occupyTime;
/*    */     
/*    */     public void writeExternal(ObjectOutput out) throws IOException
/*    */     {
/* 32 */       out.writeByte(this.laneId);
/* 33 */       out.writeByte(this.occupyTime);
/*    */     }
/*    */     
/*    */     public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*    */     {
/* 38 */       this.laneId = (in.readByte() & 0xFF);
/* 39 */       this.occupyTime = (in.readByte() & 0xFF);
/*    */     }
/*    */     
/*    */     public String toString() {
/* 43 */       StringBuilder sb = new StringBuilder();
/* 44 */       sb.append('[');
/* 45 */       sb.append("laneId: ").append(this.laneId).append(", ");
/* 46 */       sb.append("occupyTime: ").append(this.occupyTime).append(']');
/* 47 */       return sb.toString();
/*    */     }
/*    */   }
/*    */   
/*    */   public void writeExternal(ObjectOutput out) throws IOException
/*    */   {
/* 53 */     out.writeObject(this.occupyCfgList);
/*    */   }
/*    */   
/*    */   public void readExternal(ObjectInput in)
/*    */     throws IOException, ClassNotFoundException
/*    */   {
/* 59 */     this.occupyCfgList = ((List)in.readObject());
/*    */   }
/*    */   
/*    */   public String toString() {
/* 63 */     StringBuilder sb = new StringBuilder();
/* 64 */     sb.append('[');
/* 65 */     sb.append("occupyCfgList: ").append(this.occupyCfgList).append(']');
/* 66 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\fw2\cmdparam\vd\SetOccupyCfgReqPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */