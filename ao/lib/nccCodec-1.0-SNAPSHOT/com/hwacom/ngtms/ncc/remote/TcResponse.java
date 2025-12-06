/*     */ package com.hwacom.ngtms.ncc.remote;
/*     */ 
/*     */ import java.io.ObjectInput;
/*     */ import java.io.ObjectOutput;
/*     */ 
/*     */ public class TcResponse implements java.io.Externalizable, Cloneable
/*     */ {
/*     */   private Result result;
/*     */   private int cmdIndex;
/*     */   private ErrorCode errorCode;
/*     */   private String errorReason;
/*     */   private Object cmdBindingObj;
/*     */   
/*     */   public static enum Result
/*     */   {
/*  16 */     SUCCESS, 
/*  17 */     FAIL, 
/*  18 */     TIMEOUT;
/*     */     
/*     */     private Result() {} }
/*     */   
/*  22 */   public static enum ErrorCode { NONE, 
/*  23 */     DISCONNECT, 
/*  24 */     NO_TC_ADAPTOR, 
/*  25 */     NAK, 
/*  26 */     EXCEPTION, 
/*  27 */     TIMEOUT;
/*     */     
/*     */ 
/*     */ 
/*     */     private ErrorCode() {}
/*     */   }
/*     */   
/*     */ 
/*     */   public TcResponse clone()
/*     */     throws CloneNotSupportedException
/*     */   {
/*  38 */     return (TcResponse)super.clone();
/*     */   }
/*     */   
/*     */   public Result getResult() {
/*  42 */     return this.result;
/*     */   }
/*     */   
/*     */   public void setResult(Result result) {
/*  46 */     this.result = result;
/*     */   }
/*     */   
/*     */   public int getCmdIndex() {
/*  50 */     return this.cmdIndex;
/*     */   }
/*     */   
/*     */   public void setCmdIndex(int cmdIndex) {
/*  54 */     this.cmdIndex = cmdIndex;
/*     */   }
/*     */   
/*     */   public ErrorCode getErrorCode() {
/*  58 */     return this.errorCode;
/*     */   }
/*     */   
/*     */   public void setErrorCode(ErrorCode errorCode) {
/*  62 */     this.errorCode = errorCode;
/*     */   }
/*     */   
/*     */   public String getErrorReason() {
/*  66 */     return this.errorReason;
/*     */   }
/*     */   
/*     */   public void setErrorReason(String errorReason) {
/*  70 */     this.errorReason = errorReason;
/*     */   }
/*     */   
/*     */   public Object getCmdBindingObj() {
/*  74 */     return this.cmdBindingObj;
/*     */   }
/*     */   
/*     */   public void setCmdBindingObj(Object cmdBindingObj) {
/*  78 */     this.cmdBindingObj = cmdBindingObj;
/*     */   }
/*     */   
/*     */   public void writeExternal(ObjectOutput out) throws java.io.IOException
/*     */   {
/*  83 */     out.writeByte(this.result.ordinal());
/*  84 */     out.writeShort(this.cmdIndex);
/*  85 */     if (this.errorCode == null) this.errorCode = ErrorCode.NONE;
/*  86 */     out.writeByte(this.errorCode.ordinal());
/*  87 */     out.writeObject(this.errorReason);
/*  88 */     out.writeObject(this.cmdBindingObj);
/*     */   }
/*     */   
/*     */   public void readExternal(ObjectInput in) throws java.io.IOException, ClassNotFoundException
/*     */   {
/*  93 */     this.result = Result.values()[in.read()];
/*  94 */     this.cmdIndex = in.readShort();
/*  95 */     this.errorCode = ErrorCode.values()[in.read()];
/*  96 */     this.errorReason = ((String)in.readObject());
/*  97 */     this.cmdBindingObj = in.readObject();
/*     */   }
/*     */   
/*     */   public String toString()
/*     */   {
/* 102 */     return "TcResponse [result=" + this.result + ", cmdIndex=" + this.cmdIndex + ", errorCode=" + this.errorCode + ", errorReason=" + this.errorReason + ", cmdBindingObj=" + this.cmdBindingObj + "]";
/*     */   }
/*     */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\nccCodec-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ncc\remote\TcResponse.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */