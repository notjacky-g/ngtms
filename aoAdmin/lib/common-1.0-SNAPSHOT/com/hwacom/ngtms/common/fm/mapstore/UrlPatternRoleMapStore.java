/*    */ package com.hwacom.ngtms.common.fm.mapstore;
/*    */ 
/*    */ import com.hwacom.ngtms.base.hazelcast.JPAMapStore;
/*    */ import com.hwacom.ngtms.common.fm.model.UrlPatternRole;
/*    */ import com.hwacom.ngtms.common.fm.repository.UrlPatternRoleRepository;
/*    */ import java.util.Collection;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import java.util.Set;
/*    */ import javax.annotation.PostConstruct;
/*    */ import org.springframework.beans.factory.annotation.Autowired;
/*    */ import org.springframework.data.repository.CrudRepository;
/*    */ import org.springframework.stereotype.Component;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Component
/*    */ public class UrlPatternRoleMapStore
/*    */   extends JPAMapStore<UrlPatternRole, Long>
/*    */ {
/*    */   @Autowired
/*    */   private UrlPatternRoleRepository repository;
/*    */   
/*    */   @PostConstruct
/*    */   public void setCrudRepository() {
/* 27 */     setCrudRepository((CrudRepository)this.repository);
/*    */   }
/*    */ 
/*    */   
/*    */   public Map<Long, UrlPatternRole> loadAll(Collection<Long> collection) {
/* 32 */     Map<Long, UrlPatternRole> map = new HashMap<>();
/* 33 */     for (UrlPatternRole u : this.repository.findAllById(collection)) {
/* 34 */       map.put(u.getId(), u);
/*    */     }
/* 36 */     return map;
/*    */   }
/*    */ 
/*    */   
/*    */   public Set<Long> loadAllKeys() {
/* 41 */     return this.repository.findAllKeys();
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\lib\common-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\common\fm\mapstore\UrlPatternRoleMapStore.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */