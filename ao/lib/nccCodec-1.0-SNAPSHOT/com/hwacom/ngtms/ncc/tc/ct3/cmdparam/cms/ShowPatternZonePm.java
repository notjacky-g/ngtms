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
/*    */ 
/*    */ 
/*    */ 
/*    */ @GlobalParams(paramsName="showPatternZone")
/*    */ public class ShowPatternZonePm
/*    */   implements Externalizable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   @CommandParam(name="zoneNo")
/*    */   public int zoneNo;
/*    */   @CommandParam(name="show")
/*    */   public int show;
/*    */   @CommandParam(name="fitSize")
/*    */   public int fitSize;
/*    */   @CommandParam(name="fontType")
/*    */   public int fontType;
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
/* 42 */       out.writeByte(this.displayTime);
/* 43 */       out.writeByte(this.msgId);
/*    */     }
/*    */     
/*    */     public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*    */     {
/* 48 */       this.displayTime = (in.readByte() & 0xFF);
/* 49 */       this.msgId = (in.readByte() & 0xFF);
/*    */     }
/*    */     
/*    */     public String toString() {
/* 53 */       StringBuilder sb = new StringBuilder();
/* 54 */       sb.append('[');
/* 55 */       sb.append("displayTime: ").append(this.displayTime).append(", ");
/* 56 */       sb.append("msgId: ").append(this.msgId).append(']');
/* 57 */       return sb.toString();
/*    */     }
/*    */   }
/*    */   
/*    */   public void writeExternal(ObjectOutput out) throws IOException
/*    */   {
/* 63 */     out.writeByte(this.zoneNo);
/* 64 */     out.writeByte(this.show);
/* 65 */     out.writeByte(this.fitSize);
/* 66 */     out.writeByte(this.fontType);
/* 67 */     out.writeObject(this.displayList);
/*    */   }
/*    */   
/*    */   public void readExternal(ObjectInput in)
/*    */     throws IOException, ClassNotFoundException
/*    */   {
/* 73 */     this.zoneNo = (in.readByte() & 0xFF);
/* 74 */     this.show = (in.readByte() & 0xFF);
/* 75 */     this.fitSize = (in.readByte() & 0xFF);
/* 76 */     this.fontType = (in.readByte() & 0xFF);
/* 77 */     this.displayList = ((List)in.readObject());
/*    */   }
/*    */   
/*    */   public String toString() {
/* 81 */     StringBuilder sb = new StringBuilder();
/* 82 */     sb.append('[');
/* 83 */     sb.append("zoneNo: ").append(this.zoneNo).append(", ");
/* 84 */     sb.append("show: ").append(this.show).append(", ");
/* 85 */     sb.append("fitSize: ").append(this.fitSize).append(", ");
/* 86 */     sb.append("fontType: ").append(this.fontType).append(", ");
/* 87 */     sb.append("displayList: ").append(this.displayList).append(']');
/* 88 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\ct3\cmdparam\cms\ShowPatternZonePm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */