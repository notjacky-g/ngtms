/*    */ package com.hwacom.ngtms.ao.am.event;
/*    */ 
/*    */ import com.google.gwt.event.shared.EventHandler;
/*    */ import com.google.gwt.event.shared.GwtEvent;
/*    */ import com.google.web.bindery.event.shared.Event;
/*    */ 
/*    */ public class RoomStatusMonitorEvent
/*    */   extends GwtEvent<RoomStatusMonitorEvent.RoomStatusMonitorEventHandler> {
/*    */   private final Action action;
/*    */   
/*    */   public static interface RoomStatusMonitorEventHandler extends EventHandler {
/*    */     void onClick(RoomStatusMonitorEvent param1RoomStatusMonitorEvent);
/*    */   }
/*    */   
/* 15 */   public static final GwtEvent.Type<RoomStatusMonitorEventHandler> TYPE = new GwtEvent.Type();
/*    */   
/*    */   public enum Action
/*    */   {
/* 19 */     CLICK;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public RoomStatusMonitorEvent(Action action) {
/* 25 */     this.action = action;
/*    */   }
/*    */ 
/*    */   
/*    */   public GwtEvent.Type<RoomStatusMonitorEventHandler> getAssociatedType() {
/* 30 */     return TYPE;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void dispatch(RoomStatusMonitorEventHandler handler) {
/* 35 */     if (this.action == Action.CLICK)
/* 36 */       handler.onClick(this); 
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\lib\aoAdmin-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ao\am\event\RoomStatusMonitorEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */