/*     */ package com.hwacom.ngtms.base.httpclient;
/*     */ 
/*     */ import java.net.URL;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class LineNotifyParameter
/*     */ {
/*     */   private String token;
/*     */   private String message;
/*     */   private URL imageThumbnail;
/*     */   private URL imageFullsize;
/*     */   private String imageFile;
/*     */   private Integer stickerPackageId;
/*     */   private Integer stickerId;
/*     */   private boolean notificationDisabled;
/*     */   
/*     */   public LineNotifyParameter(String token) {
/*  30 */     this(token, " ");
/*     */   }
/*     */   
/*     */   public LineNotifyParameter(String token, String message) {
/*  34 */     if (token == null || message == null) {
/*  35 */       throw new NullPointerException();
/*     */     }
/*  37 */     this.token = token;
/*  38 */     this.message = message;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getToken() {
/*  43 */     return this.token;
/*     */   }
/*     */   
/*     */   public String getMessage() {
/*  47 */     return this.message;
/*     */   }
/*     */   
/*     */   public URL getImageThumbnail() {
/*  51 */     return this.imageThumbnail;
/*     */   }
/*     */   
/*     */   public void setImageThumbnail(URL imageThumbnail) {
/*  55 */     this.imageThumbnail = imageThumbnail;
/*     */   }
/*     */   
/*     */   public URL getImageFullsize() {
/*  59 */     return this.imageFullsize;
/*     */   }
/*     */   
/*     */   public void setImageFullsize(URL imageFullsize) {
/*  63 */     this.imageFullsize = imageFullsize;
/*     */   }
/*     */   
/*     */   public String getImageFile() {
/*  67 */     return this.imageFile;
/*     */   }
/*     */   
/*     */   public void setImageFile(String imageFile) {
/*  71 */     this.imageFile = imageFile;
/*     */   }
/*     */   
/*     */   public Integer getStickerPackageId() {
/*  75 */     return this.stickerPackageId;
/*     */   }
/*     */   
/*     */   public void setStickerPackageId(Integer stickerPackageId) {
/*  79 */     this.stickerPackageId = stickerPackageId;
/*     */   }
/*     */   
/*     */   public Integer getStickerId() {
/*  83 */     return this.stickerId;
/*     */   }
/*     */   
/*     */   public void setStickerId(Integer stickerId) {
/*  87 */     this.stickerId = stickerId;
/*     */   }
/*     */   
/*     */   public boolean isNotificationDisabled() {
/*  91 */     return this.notificationDisabled;
/*     */   }
/*     */   
/*     */   public void setNotificationDisabled(boolean notificationDisabled) {
/*  95 */     this.notificationDisabled = notificationDisabled;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 100 */     return "LineNotifyParameter [token=" + this.token + ", message=" + this.message + ", imageThumbnail=" + this.imageThumbnail + ", imageFullsize=" + this.imageFullsize + ", imageFile=" + this.imageFile + ", stickerPackageId=" + this.stickerPackageId + ", stickerId=" + this.stickerId + ", notificationDisabled=" + this.notificationDisabled + "]";
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\lib\base-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\base\httpclient\LineNotifyParameter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */