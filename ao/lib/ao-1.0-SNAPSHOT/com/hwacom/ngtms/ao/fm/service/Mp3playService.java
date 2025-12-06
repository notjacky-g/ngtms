/*    */ package com.hwacom.ngtms.ao.fm.service;
/*    */ 
/*    */ import java.io.BufferedInputStream;
/*    */ import java.io.FileInputStream;
/*    */ import javazoom.jl.player.Player;
/*    */ import org.slf4j.Logger;
/*    */ import org.slf4j.LoggerFactory;
/*    */ import org.springframework.beans.factory.annotation.Value;
/*    */ import org.springframework.stereotype.Service;
/*    */ 
/*    */ @Service
/*    */ public class Mp3playService
/*    */   extends Thread
/*    */ {
/* 15 */   private static final Logger logger = LoggerFactory.getLogger(Mp3playService.class);
/*    */   
/*    */   @Value("${mp3.file:D://HWA/Ao-ngtms/ao/src/main/resources/conf/alarmAudio.mp3}")
/*    */   private String mp3File;
/*    */   
/*    */   private boolean loop;
/*    */   
/*    */   private Player prehravac;
/*    */   
/*    */   public void run() {
/*    */     try {
/* 26 */       this.loop = true;
/*    */       do {
/* 28 */         FileInputStream fileInputStream = new FileInputStream(this.mp3File);
/* 29 */         BufferedInputStream buff = new BufferedInputStream(fileInputStream);
/* 30 */         this.prehravac = new Player(buff);
/* 31 */         this.prehravac.play();
/* 32 */       } while (this.loop);
/* 33 */     } catch (Exception ioe) {
/* 34 */       logger.error("Mp3playService run Faild", ioe);
/*    */     } 
/*    */   }
/*    */   
/*    */   public void close() {
/* 39 */     this.loop = false;
/* 40 */     this.prehravac.close();
/* 41 */     interrupt();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\機房門禁10.121.41.38\ngtms\ao\lib.src\ao-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ao\fm\service\Mp3playService.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */