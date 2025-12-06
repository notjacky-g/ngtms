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
/*    */ @CommandParams(cmdName="setShowPatternReq")
/*    */ public class SetShowPatternReqPm
/*    */   implements Externalizable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   public static final int cmdId = 44853;
/*    */   public static final String cmdName = "setShowPatternReq";
/*    */   @CommandParam(name="patternId")
/*    */   public int patternId;
/*    */   @CommandParam(name="zoneList")
/*    */   public List<ZoneListItem> zoneList;
/*    */   
/*    */   public static class ZoneListItem
/*    */     implements Externalizable
/*    */   {
/*    */     private static final long serialVersionUID = 1L;
/*    */     @CommandParam(name="showPatternZone")
/*    */     public ShowPatternZonePm showPatternZonePm;
/*    */     
/*    */     public void writeExternal(ObjectOutput out) throws IOException
/*    */     {
/* 32 */       out.writeObject(this.showPatternZonePm);
/*    */     }
/*    */     
/*    */     public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*    */     {
/* 37 */       this.showPatternZonePm = ((ShowPatternZonePm)in.readObject());
/*    */     }
/*    */     
/*    */     public String toString() {
/* 41 */       StringBuilder sb = new StringBuilder();
/* 42 */       sb.append('[');
/* 43 */       sb.append("showPatternZonePm: ").append(this.showPatternZonePm).append(']');
/* 44 */       return sb.toString();
/*    */     }
/*    */   }
/*    */   
/*    */   public void writeExternal(ObjectOutput out) throws IOException
/*    */   {
/* 50 */     out.writeByte(this.patternId);
/* 51 */     out.writeObject(this.zoneList);
/*    */   }
/*    */   
/*    */   public void readExternal(ObjectInput in)
/*    */     throws IOException, ClassNotFoundException
/*    */   {
/* 57 */     this.patternId = (in.readByte() & 0xFF);
/* 58 */     this.zoneList = ((List)in.readObject());
/*    */   }
/*    */   
/*    */   public String toString() {
/* 62 */     StringBuilder sb = new StringBuilder();
/* 63 */     sb.append('[');
/* 64 */     sb.append("patternId: ").append(this.patternId).append(", ");
/* 65 */     sb.append("zoneList: ").append(this.zoneList).append(']');
/* 66 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\ct3\cmdparam\cms\SetShowPatternReqPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */