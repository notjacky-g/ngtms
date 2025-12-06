/*    */ package com.hwacom.ngtms.c.ncc;
/*    */ 
/*    */ import java.io.Externalizable;
/*    */ import java.io.IOException;
/*    */ import java.io.ObjectInput;
/*    */ import java.io.ObjectOutput;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TcReportWrapper
/*    */   implements Externalizable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   private String deviceName;
/*    */   private long receiveTime;
/*    */   private Object cmdBindingObj;
/*    */   
/*    */   public TcReportWrapper() {}
/*    */   
/*    */   public TcReportWrapper(String deviceName, long receiveTime, Object cmdBindingObj) {
/* 24 */     this.deviceName = deviceName;
/* 25 */     this.receiveTime = receiveTime;
/* 26 */     this.cmdBindingObj = cmdBindingObj;
/*    */   }
/*    */   
/*    */   public String getDeviceName() {
/* 30 */     return this.deviceName;
/*    */   }
/*    */   
/*    */   public void setDeviceName(String deviceName) {
/* 34 */     this.deviceName = deviceName;
/*    */   }
/*    */   
/*    */   public long getReceiveTime() {
/* 38 */     return this.receiveTime;
/*    */   }
/*    */   
/*    */   public void setReceiveTime(long receiveTime) {
/* 42 */     this.receiveTime = receiveTime;
/*    */   }
/*    */   
/*    */   public Object getCmdBindingObj() {
/* 46 */     return this.cmdBindingObj;
/*    */   }
/*    */   
/*    */   public void setCmdBindingObj(Object cmdBindingObj) {
/* 50 */     this.cmdBindingObj = cmdBindingObj;
/*    */   }
/*    */ 
/*    */   
/*    */   public void writeExternal(ObjectOutput out) throws IOException {
/* 55 */     out.writeUTF(this.deviceName);
/* 56 */     out.writeLong(this.receiveTime);
/* 57 */     out.writeObject(this.cmdBindingObj);
/*    */   }
/*    */ 
/*    */   
/*    */   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
/* 62 */     this.deviceName = in.readUTF();
/* 63 */     this.receiveTime = in.readLong();
/* 64 */     this.cmdBindingObj = in.readObject();
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\lib\commonFm-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\c\ncc\TcReportWrapper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */