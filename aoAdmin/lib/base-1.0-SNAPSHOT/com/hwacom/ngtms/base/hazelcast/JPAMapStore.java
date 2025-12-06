/*    */ package com.hwacom.ngtms.base.hazelcast;
/*    */ 
/*    */ import com.hazelcast.core.MapStore;
/*    */ import java.io.Serializable;
/*    */ import java.util.Collection;
/*    */ import java.util.Map;
/*    */ import java.util.Set;
/*    */ import org.slf4j.Logger;
/*    */ import org.slf4j.LoggerFactory;
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
/*    */ 
/*    */ public abstract class JPAMapStore<T, ID extends Serializable>
/*    */   implements MapStore<ID, T>
/*    */ {
/* 26 */   private static Logger logger = LoggerFactory.getLogger(JPAMapStore.class);
/*    */   private CrudRepository<T, ID> crudRepository;
/*    */   
/*    */   public CrudRepository<T, ID> getCrudRepository() {
/* 30 */     return this.crudRepository;
/*    */   }
/*    */   
/*    */   public void setCrudRepository(CrudRepository<T, ID> crudRepository) {
/* 34 */     this.crudRepository = crudRepository;
/*    */   }
/*    */   
/*    */   public void store(ID key, T value) {
/* 38 */     this.crudRepository.save(value);
/*    */   }
/*    */   
/*    */   public void storeAll(Map<ID, T> map) {
/* 42 */     this.crudRepository.saveAll(map.values());
/*    */   }
/*    */   
/*    */   public void delete(ID key) {
/* 46 */     this.crudRepository.deleteById(key);
/*    */   }
/*    */   
/*    */   public T load(ID key) {
/* 50 */     if (key instanceof String) {
/* 51 */       String k = (String)key;
/* 52 */       if (k != null && k.length() > 0 && 
/* 53 */         k.charAt(k.length() - 1) == ' ') {
/* 54 */         logger.warn("key : '{}' contains whitespace. MapStore prohibit a key end with whitespace\n", k);
/*    */         
/* 56 */         StackTraceElement[] ste = Thread.currentThread().getStackTrace();
/* 57 */         for (StackTraceElement e : ste) {
/* 58 */           logger.debug(e.toString());
/*    */         }
/* 60 */         return null;
/*    */       } 
/*    */     } 
/*    */     
/* 64 */     return this.crudRepository.findById(key).orElse(null);
/*    */   }
/*    */ 
/*    */   
/*    */   public void deleteAll(Collection<ID> keys) {
/* 69 */     for (Serializable serializable : keys)
/* 70 */       this.crudRepository.deleteById(serializable); 
/*    */   }
/*    */   
/*    */   public abstract Map<ID, T> loadAll(Collection<ID> paramCollection);
/*    */   
/*    */   public abstract Set<ID> loadAllKeys();
/*    */ }


/* Location:              C:\User\\user\Desktop\lib\base-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\base\hazelcast\JPAMapStore.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */