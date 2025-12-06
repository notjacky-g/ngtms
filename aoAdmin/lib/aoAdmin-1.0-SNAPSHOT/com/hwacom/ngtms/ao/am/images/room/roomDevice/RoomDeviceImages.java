/*    */ package com.hwacom.ngtms.ao.am.images.room.roomDevice;
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
/*    */ public interface RoomDeviceImages
/*    */   extends ClientBundle
/*    */ {
/* 15 */   public static final RoomDeviceImages INSTANCE = (RoomDeviceImages)GWT.create(RoomDeviceImages.class);
/*    */   
/*    */   SVGResource inputSingal();
/*    */   
/*    */   SVGResource outputSingal();
/*    */   
/*    */   SVGResource doorSingal();
/*    */   
/*    */   SVGResource group();
/*    */ }


/* Location:              C:\User\\user\Desktop\lib\aoAdmin-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ao\am\images\room\roomDevice\RoomDeviceImages.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */