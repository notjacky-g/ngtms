/*    */ package com.hwacom.ngtms.ncc.tc.ct3.cmdparam.cms;
/*    */ 
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParam;
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.GlobalParams;
/*    */ import java.io.Externalizable;
/*    */ import java.io.IOException;
/*    */ import java.io.ObjectInput;
/*    */ import java.io.ObjectOutput;
/*    */ import java.util.List;
/*    */ 
/*    */ @GlobalParams(paramsName="displayShowPatternZone")
/*    */ public class DisplayShowPatternZonePm
/*    */   implements Externalizable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   @CommandParam(name="zoneNo")
/*    */   public int zoneNo;
/*    */   @CommandParam(name="displayList")
/*    */   public List<DisplayListItem> displayList;
/*    */   
/*    */   public static class DisplayListItem
/*    */     implements Externalizable
/*    */   {
/*    */     private static final long serialVersionUID = 1L;
/*    */     @CommandParam(name="displayTime")
/*    */     public int displayTime;
/*    */     @CommandParam(name="msgId")
/*    */     public int msgId;
/*    */     
/*    */     public void writeExternal(ObjectOutput out)
/*    */       throws IOException
/*    */     {
/* 33 */       out.writeByte(this.displayTime);
/* 34 */       out.writeByte(this.msgId);
/*    */     }
/*    */     
/*    */     public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*    */     {
/* 39 */       this.displayTime = (in.readByte() & 0xFF);
/* 40 */       this.msgId = (in.readByte() & 0xFF);
/*    */     }
/*    */     
/*    */     public String toString() {
/* 44 */       StringBuilder sb = new StringBuilder();
/* 45 */       sb.append('[');
/* 46 */       sb.append("displayTime: ").append(this.displayTime).append(", ");
/* 47 */       sb.append("msgId: ").append(this.msgId).append(']');
/* 48 */       return sb.toString();
/*    */     }
/*    */   }
/*    */   
/*    */   public void writeExternal(ObjectOutput out) throws IOException
/*    */   {
/* 54 */     out.writeByte(this.zoneNo);
/* 55 */     out.writeObject(this.displayList);
/*    */   }
/*    */   
/*    */   public void readExternal(ObjectInput in)
/*    */     throws IOException, ClassNotFoundException
/*    */   {
/* 61 */     this.zoneNo = (in.readByte() & 0xFF);
/* 62 */     this.displayList = ((List)in.readObject());
/*    */   }
/*    */   
/*    */   public String toString() {
/* 66 */     StringBuilder sb = new StringBuilder();
/* 67 */     sb.append('[');
/* 68 */     sb.append("zoneNo: ").append(this.zoneNo).append(", ");
/* 69 */     sb.append("displayList: ").append(this.displayList).append(']');
/* 70 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\ct3\cmdparam\cms\DisplayShowPatternZonePm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */