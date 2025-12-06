/*    */ package com.hwacom.ngtms.ao.am.event;
/*    */ 
/*    */ import com.google.gwt.event.shared.EventHandler;
/*    */ import com.google.gwt.event.shared.GwtEvent;
/*    */ import com.google.web.bindery.event.shared.Event;
/*    */ 
/*    */ public class AlarmMonitorGridByTypeEvent
/*    */   extends GwtEvent<AlarmMonitorGridByTypeEvent.AlarmMonitorGridByTypeEventHandler> {
/*    */   private final Action action;
/*    */   
/*    */   public static interface AlarmMonitorGridByTypeEventHandler extends EventHandler {
/*    */     void onAlert(AlarmMonitorGridByTypeEvent param1AlarmMonitorGridByTypeEvent);
/*    */   }
/*    */   
/* 15 */   public static final GwtEvent.Type<AlarmMonitorGridByTypeEventHandler> TYPE = new GwtEvent.Type();
/*    */   
/*    */   public enum Action
/*    */   {
/* 19 */     ALERT;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public AlarmMonitorGridByTypeEvent(Action action) {
/* 25 */     this.action = action;
/*    */   }
/*    */ 
/*    */   
/*    */   public GwtEvent.Type<AlarmMonitorGridByTypeEventHandler> getAssociatedType() {
/* 30 */     return TYPE;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void dispatch(AlarmMonitorGridByTypeEventHandler handler) {
/* 35 */     if (this.action == Action.ALERT)
/* 36 */       handler.onAlert(this); 
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\lib\aoAdmin-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ao\am\event\AlarmMonitorGridByTypeEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */