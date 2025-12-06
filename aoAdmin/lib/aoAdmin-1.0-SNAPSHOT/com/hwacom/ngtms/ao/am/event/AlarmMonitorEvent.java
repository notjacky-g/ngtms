/*    */ package com.hwacom.ngtms.ao.am.event;
/*    */ import com.google.gwt.event.shared.EventHandler;
/*    */ import com.google.gwt.event.shared.GwtEvent;
/*    */ import com.google.web.bindery.event.shared.Event;
/*    */ 
/*    */ public class AlarmMonitorEvent extends GwtEvent<AlarmMonitorEvent.AlarmMonitorEventHandler> {
/*    */   private final Action action;
/*  8 */   public static final GwtEvent.Type<AlarmMonitorEventHandler> TYPE = new GwtEvent.Type();
/*    */   public static interface AlarmMonitorEventHandler extends EventHandler {
/*    */     void onClick(AlarmMonitorEvent param1AlarmMonitorEvent); }
/*    */   
/* 12 */   public enum Action { CLICK; }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public AlarmMonitorEvent(Action action) {
/* 18 */     this.action = action;
/*    */   }
/*    */ 
/*    */   
/*    */   public GwtEvent.Type<AlarmMonitorEventHandler> getAssociatedType() {
/* 23 */     return TYPE;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void dispatch(AlarmMonitorEventHandler handler) {
/* 28 */     if (this.action == Action.CLICK)
/* 29 */       handler.onClick(this); 
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\lib\aoAdmin-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ao\am\event\AlarmMonitorEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */