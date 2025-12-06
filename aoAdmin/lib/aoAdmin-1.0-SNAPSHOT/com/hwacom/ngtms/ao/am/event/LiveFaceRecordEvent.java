/*    */ package com.hwacom.ngtms.ao.am.event;
/*    */ 
/*    */ import com.google.gwt.event.shared.EventHandler;
/*    */ import com.google.gwt.event.shared.GwtEvent;
/*    */ import com.google.web.bindery.event.shared.Event;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class LiveFaceRecordEvent
/*    */   extends GwtEvent<LiveFaceRecordEvent.LiveFaceRecordEventHandler>
/*    */ {
/*    */   private final Action action;
/* 15 */   public static final GwtEvent.Type<LiveFaceRecordEventHandler> TYPE = new GwtEvent.Type();
/*    */   public static interface LiveFaceRecordEventHandler extends EventHandler {
/*    */     void onLock(LiveFaceRecordEvent param1LiveFaceRecordEvent);
/*    */     void onUnlock(LiveFaceRecordEvent param1LiveFaceRecordEvent); }
/* 19 */   public enum Action { LOCK,
/* 20 */     UNLOCK; }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public LiveFaceRecordEvent(Action action) {
/* 26 */     this.action = action;
/*    */   }
/*    */ 
/*    */   
/*    */   public GwtEvent.Type<LiveFaceRecordEventHandler> getAssociatedType() {
/* 31 */     return TYPE;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void dispatch(LiveFaceRecordEventHandler handler) {
/* 36 */     if (this.action == Action.LOCK) {
/* 37 */       handler.onLock(this);
/* 38 */     } else if (this.action == Action.UNLOCK) {
/* 39 */       handler.onUnlock(this);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\lib\aoAdmin-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ao\am\event\LiveFaceRecordEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */