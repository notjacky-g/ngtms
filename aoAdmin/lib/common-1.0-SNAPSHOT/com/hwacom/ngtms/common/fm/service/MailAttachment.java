/*    */ package com.hwacom.ngtms.common.fm.service;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MailAttachment
/*    */ {
/*    */   private String attachmentFilename;
/*    */   private byte[] bytes;
/*    */   private String contentType;
/*    */   
/*    */   public MailAttachment() {}
/*    */   
/*    */   public MailAttachment(String attachmentFilename, byte[] bytes, String contentType) {
/* 20 */     this.attachmentFilename = attachmentFilename;
/* 21 */     this.bytes = bytes;
/* 22 */     this.contentType = contentType;
/*    */   }
/*    */   
/*    */   public String getAttachmentFilename() {
/* 26 */     return this.attachmentFilename;
/*    */   }
/*    */   
/*    */   public void setAttachmentFilename(String attachmentFilename) {
/* 30 */     this.attachmentFilename = attachmentFilename;
/*    */   }
/*    */   
/*    */   public byte[] getBytes() {
/* 34 */     return this.bytes;
/*    */   }
/*    */   
/*    */   public void setBytes(byte[] bytes) {
/* 38 */     this.bytes = bytes;
/*    */   }
/*    */   
/*    */   public String getContentType() {
/* 42 */     return this.contentType;
/*    */   }
/*    */   
/*    */   public void setContentType(String contentType) {
/* 46 */     this.contentType = contentType;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\lib\common-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\common\fm\service\MailAttachment.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */