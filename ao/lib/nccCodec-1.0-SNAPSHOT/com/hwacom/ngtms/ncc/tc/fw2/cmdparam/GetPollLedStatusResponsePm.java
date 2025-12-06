/*    */ package com.hwacom.ngtms.ncc.tc.fw2.cmdparam;
/*    */ 
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParam;
/*    */ import com.hwacom.ngtms.cmdprot.cmdfmtdef.CommandParams;
/*    */ import java.io.Externalizable;
/*    */ import java.io.IOException;
/*    */ import java.io.ObjectInput;
/*    */ import java.io.ObjectOutput;
/*    */ import java.util.List;
/*    */ 
/*    */ @CommandParams(cmdName="getPollLedStatusResponse")
/*    */ public class GetPollLedStatusResponsePm
/*    */   implements Externalizable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   public static final int cmdId = 3876;
/*    */   public static final String cmdName = "getPollLedStatusResponse";
/*    */   @CommandParam(name="badLedList")
/*    */   public List<BadLedListItem> badLedList;
/*    */   
/*    */   public static class BadLedListItem implements Externalizable
/*    */   {
/*    */     private static final long serialVersionUID = 1L;
/*    */     @CommandParam(name="ledX")
/*    */     public int ledX;
/*    */     @CommandParam(name="ledY")
/*    */     public int ledY;
/*    */     
/*    */     public void writeExternal(ObjectOutput out) throws IOException
/*    */     {
/* 31 */       out.writeByte(this.ledX);
/* 32 */       out.writeByte(this.ledY);
/*    */     }
/*    */     
/*    */     public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*    */     {
/* 37 */       this.ledX = (in.readByte() & 0xFF);
/* 38 */       this.ledY = (in.readByte() & 0xFF);
/*    */     }
/*    */     
/*    */     public String toString() {
/* 42 */       StringBuilder sb = new StringBuilder();
/* 43 */       sb.append('[');
/* 44 */       sb.append("ledX: ").append(this.ledX).append(", ");
/* 45 */       sb.append("ledY: ").append(this.ledY).append(']');
/* 46 */       return sb.toString();
/*    */     }
/*    */   }
/*    */   
/*    */   public void writeExternal(ObjectOutput out) throws IOException
/*    */   {
/* 52 */     out.writeObject(this.badLedList);
/*    */   }
/*    */   
/*    */   public void readExternal(ObjectInput in)
/*    */     throws IOException, ClassNotFoundException
/*    */   {
/* 58 */     this.badLedList = ((List)in.readObject());
/*    */   }
/*    */   
/*    */   public String toString() {
/* 62 */     StringBuilder sb = new StringBuilder();
/* 63 */     sb.append('[');
/* 64 */     sb.append("badLedList: ").append(this.badLedList).append(']');
/* 65 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\fw2\cmdparam\GetPollLedStatusResponsePm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */