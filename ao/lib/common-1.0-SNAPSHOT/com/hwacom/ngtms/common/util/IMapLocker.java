/*    */ package com.hwacom.ngtms.common.util;
/*    */ 
/*    */ import com.hazelcast.core.IMap;
/*    */ import com.hwacom.ngtms.base.oplog.service.OperationEntityListener;
/*    */ import java.util.Optional;
/*    */ import java.util.concurrent.TimeUnit;
/*    */ import org.slf4j.Logger;
/*    */ import org.slf4j.LoggerFactory;
/*    */ 
/*    */ public class IMapLocker
/*    */ {
/* 12 */   private static Logger logger = LoggerFactory.getLogger(IMapLocker.class);
/*    */   
/* 14 */   private static OperationEntityListener listener = new OperationEntityListener();
/*    */   
/*    */   public static <K, V> void addOrUpdateMapping(IMap<K, V> imap, K key, V value) {
/* 17 */     if (imap.get(key) == null) {
/* 18 */       listener.prePersist(value);
/* 19 */       imap.put(key, value);
/* 20 */       listener.postPersist(value);
/*    */     } else {
/* 22 */       updateMapping(imap, key, value);
/*    */     }
/*    */   }
/*    */   
/*    */   private static <K, V> void updateMapping(IMap<K, V> imap, K key, V value) {
/*    */     try {
/* 28 */       if (imap.tryLock(key, 1L, TimeUnit.SECONDS)) {
/*    */         try {
/* 30 */           V v = imap.put(key, value);
/* 31 */           listener.preUpdate(v);
/* 32 */           listener.PostUpdate(value);
/*    */         } finally {
/*    */           try {
/* 35 */             imap.unlock(key);
/*    */           } catch (Exception e) {
/* 37 */             logger.warn("Unlock map failed! key: '{}'", key, e);
/*    */           }
/*    */         }
/*    */       }
/* 41 */       throw new RuntimeException("Can't acquire lock for the specified key " + key);
/*    */     }
/*    */     catch (InterruptedException ex) {
/* 44 */       throw new RuntimeException(ex);
/*    */     }
/*    */   }
/*    */   
/*    */   public static <K, V> Optional<V> removeMapping(IMap<K, V> map, K k) {
/*    */     try {
/* 50 */       if (map.tryLock(k, 1L, TimeUnit.SECONDS)) {
/*    */         try {
/* 52 */           V v = map.remove(k);
/* 53 */           listener.preRemove(v);
/* 54 */           listener.postRemove(v);
/* 55 */           return Optional.ofNullable(v);
/*    */         } finally {
/*    */           try {
/* 58 */             map.unlock(k);
/*    */           } catch (Exception e) {
/* 60 */             logger.warn("Unlock map failed! key: '{}'", k, e);
/*    */           }
/*    */         }
/*    */       }
/* 64 */       return Optional.empty();
/*    */     } catch (InterruptedException ex) {
/* 66 */       throw new RuntimeException(ex);
/*    */     }
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\common-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\common\util\IMapLocker.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */