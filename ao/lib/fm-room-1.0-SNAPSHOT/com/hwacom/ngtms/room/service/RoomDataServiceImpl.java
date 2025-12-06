/*    */ package com.hwacom.ngtms.room.service;
/*    */ 
/*    */ import org.modelmapper.ModelMapper;
/*    */ import org.slf4j.Logger;
/*    */ import org.slf4j.LoggerFactory;
/*    */ import org.springframework.beans.factory.annotation.Autowired;
/*    */ import org.springframework.context.annotation.Profile;
/*    */ import org.springframework.stereotype.Service;
/*    */ 
/*    */ @Service
/*    */ @Profile({"dev"})
/*    */ public class RoomDataServiceImpl
/*    */   implements RoomDataService {
/* 14 */   private static final Logger logger = LoggerFactory.getLogger(RoomDataServiceImpl.class);
/*    */   
/*    */   @Autowired
/*    */   ModelMapper modelmapper;
/*    */   
/*    */   public String getRoomCctvUrl() {
/* 20 */     return null;
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\機房門禁10.121.41.38\ngtms\ao\lib.src\fm-room-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\room\service\RoomDataServiceImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */