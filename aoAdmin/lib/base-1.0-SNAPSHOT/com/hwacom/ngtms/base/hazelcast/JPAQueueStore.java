/*    */ package com.hwacom.ngtms.base.hazelcast;
/*    */ 
/*    */ import com.hazelcast.core.QueueStore;
/*    */ import java.util.Collection;
/*    */ import java.util.Map;
/*    */ import java.util.Set;
/*    */ import org.springframework.data.repository.CrudRepository;
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
/*    */ public abstract class JPAQueueStore<T>
/*    */   implements QueueStore<T>
/*    */ {
/*    */   private CrudRepository<T, Long> crudRepository;
/*    */   
/*    */   public CrudRepository<T, Long> getCrudRepository() {
/* 25 */     return this.crudRepository;
/*    */   }
/*    */   
/*    */   public void setCrudRepository(CrudRepository<T, Long> crudRepository) {
/* 29 */     this.crudRepository = crudRepository;
/*    */   }
/*    */ 
/*    */   
/*    */   public void store(Long key, T value) {
/* 34 */     this.crudRepository.save(value);
/*    */   }
/*    */ 
/*    */   
/*    */   public void storeAll(Map<Long, T> map) {
/* 39 */     this.crudRepository.saveAll(map.values());
/*    */   }
/*    */ 
/*    */   
/*    */   public void delete(Long key) {
/* 44 */     this.crudRepository.deleteById(key);
/*    */   }
/*    */ 
/*    */   
/*    */   public void deleteAll(Collection<Long> keys) {
/* 49 */     for (Long key : keys) {
/* 50 */       this.crudRepository.deleteById(key);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public T load(Long key) {
/* 56 */     return this.crudRepository.findById(key).orElse(null);
/*    */   }
/*    */   
/*    */   public abstract Map<Long, T> loadAll(Collection<Long> paramCollection);
/*    */   
/*    */   public abstract Set<Long> loadAllKeys();
/*    */ }


/* Location:              C:\User\\user\Desktop\lib\base-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\base\hazelcast\JPAQueueStore.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */