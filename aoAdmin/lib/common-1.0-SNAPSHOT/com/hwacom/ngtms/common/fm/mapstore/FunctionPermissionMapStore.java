/*    */ package com.hwacom.ngtms.common.fm.mapstore;
/*    */ 
/*    */ import com.hwacom.ngtms.base.hazelcast.JPAMapStore;
/*    */ import com.hwacom.ngtms.common.fm.model.FunctionPermission;
/*    */ import com.hwacom.ngtms.common.fm.repository.FunctionPermissionRepository;
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
/*    */ public class FunctionPermissionMapStore
/*    */   extends JPAMapStore<FunctionPermission, String>
/*    */ {
/*    */   @Autowired
/*    */   private FunctionPermissionRepository repository;
/*    */   
/*    */   @PostConstruct
/*    */   public void setCrudRepository() {
/* 27 */     setCrudRepository((CrudRepository)this.repository);
/*    */   }
/*    */ 
/*    */   
/*    */   public Map<String, FunctionPermission> loadAll(Collection<String> collection) {
/* 32 */     Map<String, FunctionPermission> map = new HashMap<>();
/* 33 */     for (FunctionPermission each : this.repository.findAllById(collection)) {
/* 34 */       map.put(each.getId(), each);
/*    */     }
/* 36 */     return map;
/*    */   }
/*    */ 
/*    */   
/*    */   public Set<String> loadAllKeys() {
/* 41 */     return this.repository.findAllKeys();
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\lib\common-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\common\fm\mapstore\FunctionPermissionMapStore.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */