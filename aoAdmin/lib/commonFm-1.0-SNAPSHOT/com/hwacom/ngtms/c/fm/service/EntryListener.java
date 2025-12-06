/*    */ package com.hwacom.ngtms.c.fm.service;
/*    */ 
/*    */ import com.hazelcast.core.EntryEvent;
/*    */ import com.hazelcast.core.IMap;
/*    */ import com.hazelcast.map.listener.EntryUpdatedListener;
/*    */ import com.hazelcast.map.listener.MapListener;
/*    */ import com.hazelcast.query.EntryObject;
/*    */ import com.hazelcast.query.Predicate;
/*    */ import com.hazelcast.query.PredicateBuilder;
/*    */ import com.hwacom.ngtms.hcce.hz.HzMapEnum;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import java.util.concurrent.ConcurrentHashMap;
/*    */ import java.util.function.Consumer;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class EntryListener<T>
/*    */ {
/* 21 */   private Map<String, Map<HzMapEnum, Map<RemoverKey, Remover>>> removerMap = new ConcurrentHashMap<>();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void clear() {
/* 35 */     this.removerMap
/* 36 */       .entrySet()
/* 37 */       .forEach(device -> ((Map)device.getValue()).entrySet().forEach(()));
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 47 */     this.removerMap.clear();
/*    */   }
/*    */ 
/*    */   
/*    */   protected void addEntryUpdatedListener(String deviceName, Consumer<T> consumer, HzMapEnum hzMap) {
/* 52 */     Map<HzMapEnum, Map<RemoverKey, Remover>> deviceRemoverMap = this.removerMap.get(deviceName);
/* 53 */     if (null == deviceRemoverMap) {
/* 54 */       deviceRemoverMap = new ConcurrentHashMap<>();
/* 55 */       this.removerMap.put(deviceName, deviceRemoverMap);
/*    */     } 
/*    */     
/* 58 */     Map<RemoverKey, Remover> iMapRemoverMap = deviceRemoverMap.get(hzMap);
/* 59 */     if (null == iMapRemoverMap) {
/* 60 */       iMapRemoverMap = new ConcurrentHashMap<>();
/* 61 */       deviceRemoverMap.put(hzMap, iMapRemoverMap);
/*    */     } 
/*    */     
/* 64 */     EntryUpdatedListener<String, ?> updatedListener = event -> paramConsumer.accept(retrieveMessage(paramString));
/*    */     
/* 66 */     IMap<String, ?> iMap = getIMap(hzMap);
/* 67 */     EntryObject eo = (new PredicateBuilder()).getEntryObject();
/* 68 */     PredicateBuilder predicate = eo.get("deviceName").equal(deviceName);
/*    */ 
/*    */     
/* 71 */     String listenerId = iMap.addEntryListener((MapListener)updatedListener, (Predicate)predicate, true);
/*    */     
/* 73 */     Remover updatedListenerRemover = iMapRemoverMap.put(RemoverKey.UPDATED, () -> paramIMap.removeEntryListener(paramString));
/* 74 */     removeListenerIfNotNull(updatedListenerRemover);
/*    */   } public abstract void registerListener(String paramString, Consumer<T> paramConsumer); public abstract void registerListener(List<String> paramList, Consumer<T> paramConsumer);
/*    */   public abstract T retrieveMessage(String paramString);
/*    */   private void removeListenerIfNotNull(Remover remover) {
/* 78 */     if (remover != null)
/* 79 */       remover.remove(); 
/*    */   } public abstract List<T> retrieveMessage(List<String> paramList);
/*    */   public abstract IMap<String, ?> getIMap(HzMapEnum paramHzMapEnum);
/*    */   private static interface Remover {
/*    */     void remove(); }
/* 84 */   private enum RemoverKey { ADDED,
/* 85 */     UPDATED,
/* 86 */     REMOVED; }
/*    */ 
/*    */ }


/* Location:              C:\User\\user\Desktop\lib\commonFm-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\c\fm\service\EntryListener.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */