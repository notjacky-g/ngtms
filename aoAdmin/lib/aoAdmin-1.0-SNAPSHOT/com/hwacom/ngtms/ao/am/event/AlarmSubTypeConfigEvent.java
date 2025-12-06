/*    */ package com.hwacom.ngtms.ao.am.event;
/*    */ 
/*    */ import com.google.gwt.event.shared.EventHandler;
/*    */ import com.google.gwt.event.shared.GwtEvent;
/*    */ import com.google.web.bindery.event.shared.Event;
/*    */ 
/*    */ public class AlarmSubTypeConfigEvent
/*    */   extends GwtEvent<AlarmSubTypeConfigEvent.AlarmSubTypeConfigEventHandler> {
/*    */   private final Action action;
/*    */   
/*    */   public static interface AlarmSubTypeConfigEventHandler extends EventHandler {
/*    */     void onConfigChange(AlarmSubTypeConfigEvent param1AlarmSubTypeConfigEvent);
/*    */   }
/*    */   
/* 15 */   public static final GwtEvent.Type<AlarmSubTypeConfigEventHandler> TYPE = new GwtEvent.Type();
/*    */   
/*    */   public enum Action
/*    */   {
/* 19 */     CONFIG_CHANGE;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public AlarmSubTypeConfigEvent(Action action) {
/* 25 */     this.action = action;
/*    */   }
/*    */ 
/*    */   
/*    */   public GwtEvent.Type<AlarmSubTypeConfigEventHandler> getAssociatedType() {
/* 30 */     return TYPE;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void dispatch(AlarmSubTypeConfigEventHandler handler) {
/* 35 */     if (this.action == Action.CONFIG_CHANGE)
/* 36 */       handler.onConfigChange(this); 
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\lib\aoAdmin-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ao\am\event\AlarmSubTypeConfigEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */