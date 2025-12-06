/*    */ package com.hwacom.ngtms.ncc.remote;
/*    */ 
/*    */ import java.io.Externalizable;
/*    */ import java.io.IOException;
/*    */ import java.io.ObjectInput;
/*    */ import java.io.ObjectOutput;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TcMessage
/*    */   implements Externalizable
/*    */ {
/*    */   private byte[] data;
/*    */   private Object cmdBindingObj;
/*    */   
/*    */   public byte[] getData()
/*    */   {
/* 19 */     return this.data;
/*    */   }
/*    */   
/*    */   public void setData(byte[] data) {
/* 23 */     this.data = data;
/*    */   }
/*    */   
/*    */   public Object getCmdBindingObj() {
/* 27 */     return this.cmdBindingObj;
/*    */   }
/*    */   
/*    */   public void setCmdBindingObj(Object cmdBindingObj) {
/* 31 */     this.cmdBindingObj = cmdBindingObj;
/*    */   }
/*    */   
/*    */   public void writeExternal(ObjectOutput out) throws IOException
/*    */   {
/* 36 */     out.writeInt(this.data.length);
/* 37 */     out.write(this.data);
/* 38 */     out.writeObject(this.cmdBindingObj);
/*    */   }
/*    */   
/*    */   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
/*    */   {
/* 43 */     this.data = new byte[in.readInt()];
/* 44 */     in.readFully(this.data);
/* 45 */     this.cmdBindingObj = in.readObject();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\remote\TcMessage.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */