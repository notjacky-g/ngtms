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
/*    */ @CommandParams(cmdName="setLoopTestRsp")
/*    */ public class SetLoopTestRspPm
/*    */   implements Externalizable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   public static final int cmdId = 5;
/*    */   public static final String cmdName = "setLoopTestRsp";
/*    */   @CommandParam(name="hwStatus")
/*    */   public HwStatusPm hwStatusPm;
/*    */   @CommandParam(name="list")
/*    */   public List<ListItem> list;
/*    */   
/*    */   public static class ListItem implements Externalizable
/*    */   {
/*    */     private static final long serialVersionUID = 1L;
/*    */     @CommandParam(name="message")
/*    */     public int message;
/*    */     
/*    */     public void writeExternal(ObjectOutput out) throws IOException
/*    */     {
/* 31 */       out.writeByte(this.message);
/*    */     }
/*    */     
/*    */     public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*    */     {
/* 36 */       this.message = (in.readByte() & 0xFF);
/*    */     }
/*    */     
/*    */     public String toString() {
/* 40 */       StringBuilder sb = new StringBuilder();
/* 41 */       sb.append('[');
/* 42 */       sb.append("message: ").append(this.message).append(']');
/* 43 */       return sb.toString();
/*    */     }
/*    */   }
/*    */   
/*    */   public void writeExternal(ObjectOutput out) throws IOException
/*    */   {
/* 49 */     out.writeObject(this.hwStatusPm);
/* 50 */     out.writeObject(this.list);
/*    */   }
/*    */   
/*    */   public void readExternal(ObjectInput in)
/*    */     throws IOException, ClassNotFoundException
/*    */   {
/* 56 */     this.hwStatusPm = ((HwStatusPm)in.readObject());
/* 57 */     this.list = ((List)in.readObject());
/*    */   }
/*    */   
/*    */   public String toString() {
/* 61 */     StringBuilder sb = new StringBuilder();
/* 62 */     sb.append('[');
/* 63 */     sb.append("hwStatusPm: ").append(this.hwStatusPm).append(", ");
/* 64 */     sb.append("list: ").append(this.list).append(']');
/* 65 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\fw2\cmdparam\SetLoopTestRspPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */