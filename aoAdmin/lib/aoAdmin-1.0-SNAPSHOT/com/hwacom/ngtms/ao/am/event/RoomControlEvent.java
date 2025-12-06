/*    */ package com.hwacom.ngtms.ao.am.event;
/*    */ 
/*    */ import com.google.gwt.event.shared.EventHandler;
/*    */ import com.google.gwt.event.shared.GwtEvent;
/*    */ import com.google.web.bindery.event.shared.Event;
/*    */ 
/*    */ public class RoomControlEvent
/*    */   extends GwtEvent<RoomControlEvent.RoomControlEventHandler> {
/*    */   private final Action action;
/*    */   
/*    */   public static interface RoomControlEventHandler extends EventHandler {
/*    */     void onGet(RoomControlEvent param1RoomControlEvent);
/*    */   }
/*    */   
/* 15 */   public static final GwtEvent.Type<RoomControlEventHandler> TYPE = new GwtEvent.Type();
/*    */   
/*    */   public enum Action
/*    */   {
/* 19 */     GET;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public RoomControlEvent(Action action) {
/* 25 */     this.action = action;
/*    */   }
/*    */ 
/*    */   
/*    */   public GwtEvent.Type<RoomControlEventHandler> getAssociatedType() {
/* 30 */     return TYPE;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void dispatch(RoomControlEventHandler handler) {
/* 35 */     if (this.action == Action.GET)
/* 36 */       handler.onGet(this); 
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\lib\aoAdmin-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ao\am\event\RoomControlEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */