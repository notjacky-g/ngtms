/*    */ package com.hwacom.ngtms.ao.am.event;
/*    */ 
/*    */ import com.google.gwt.event.shared.EventHandler;
/*    */ import com.google.gwt.event.shared.GwtEvent;
/*    */ import com.google.web.bindery.event.shared.Event;
/*    */ 
/*    */ public class RoomConfigDataEvent
/*    */   extends GwtEvent<RoomConfigDataEvent.RoomConfigDataEventHandler> {
/*    */   private final Action action;
/*    */   
/*    */   public static interface RoomConfigDataEventHandler extends EventHandler {
/*    */     void onGet(RoomConfigDataEvent param1RoomConfigDataEvent);
/*    */   }
/*    */   
/* 15 */   public static final GwtEvent.Type<RoomConfigDataEventHandler> TYPE = new GwtEvent.Type();
/*    */   
/*    */   public enum Action
/*    */   {
/* 19 */     GET;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public RoomConfigDataEvent(Action action) {
/* 25 */     this.action = action;
/*    */   }
/*    */ 
/*    */   
/*    */   public GwtEvent.Type<RoomConfigDataEventHandler> getAssociatedType() {
/* 30 */     return TYPE;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void dispatch(RoomConfigDataEventHandler handler) {
/* 35 */     if (this.action == Action.GET)
/* 36 */       handler.onGet(this); 
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\lib\aoAdmin-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ao\am\event\RoomConfigDataEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */