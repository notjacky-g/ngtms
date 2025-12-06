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
/*    */ @CommandParams(cmdName="setLoopTestReq")
/*    */ public class SetLoopTestReqPm implements Externalizable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   public static final int cmdId = 5;
/*    */   public static final String cmdName = "setLoopTestReq";
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
/* 28 */       out.writeByte(this.message);
/*    */     }
/*    */     
/*    */     public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*    */     {
/* 33 */       this.message = (in.readByte() & 0xFF);
/*    */     }
/*    */     
/*    */     public String toString() {
/* 37 */       StringBuilder sb = new StringBuilder();
/* 38 */       sb.append('[');
/* 39 */       sb.append("message: ").append(this.message).append(']');
/* 40 */       return sb.toString();
/*    */     }
/*    */   }
/*    */   
/*    */   public void writeExternal(ObjectOutput out) throws IOException
/*    */   {
/* 46 */     out.writeObject(this.list);
/*    */   }
/*    */   
/*    */   public void readExternal(ObjectInput in)
/*    */     throws IOException, ClassNotFoundException
/*    */   {
/* 52 */     this.list = ((List)in.readObject());
/*    */   }
/*    */   
/*    */   public String toString() {
/* 56 */     StringBuilder sb = new StringBuilder();
/* 57 */     sb.append('[');
/* 58 */     sb.append("list: ").append(this.list).append(']');
/* 59 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\tc\fw2\cmdparam\SetLoopTestReqPm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */