/*    */ package com.hwacom.ngtms.ao.am.images.room.roomStatus;
/*    */ 
/*    */ import com.google.gwt.core.client.GWT;
/*    */ import com.google.gwt.resources.client.ClientBundle;
/*    */ import org.vectomatic.dom.svg.ui.SVGResource;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public interface RoomStatusImages
/*    */   extends ClientBundle
/*    */ {
/* 15 */   public static final RoomStatusImages INSTANCE = (RoomStatusImages)GWT.create(RoomStatusImages.class);
/*    */   
/*    */   SVGResource roomDoorStatus();
/*    */   
/*    */   SVGResource roomCarReaderStatus();
/*    */   
/*    */   SVGResource roomMonitorStatus();
/*    */   
/*    */   SVGResource roomMonitorStatusNoFace();
/*    */ }


/* Location:              C:\User\\user\Desktop\lib\aoAdmin-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ao\am\images\room\roomStatus\RoomStatusImages.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */