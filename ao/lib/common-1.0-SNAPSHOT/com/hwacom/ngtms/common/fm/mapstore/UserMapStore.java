/*    */ package com.hwacom.ngtms.common.fm.mapstore;
/*    */ 
/*    */ import com.hwacom.ngtms.base.hazelcast.JPAMapStore;
/*    */ import com.hwacom.ngtms.common.fm.model.User;
/*    */ import com.hwacom.ngtms.common.fm.repository.UserRepository;
/*    */ import java.util.Collection;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import java.util.Set;
/*    */ import javax.annotation.PostConstruct;
/*    */ import org.springframework.beans.factory.annotation.Autowired;
/*    */ import org.springframework.stereotype.Component;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Component
/*    */ public class UserMapStore
/*    */   extends JPAMapStore<User, String>
/*    */ {
/*    */   @Autowired
/*    */   private UserRepository repository;
/*    */   
/*    */   @PostConstruct
/*    */   public void setCrudRepository()
/*    */   {
/* 27 */     setCrudRepository(this.repository);
/*    */   }
/*    */   
/*    */   public Map<String, User> loadAll(Collection<String> collection)
/*    */   {
/* 32 */     Map<String, User> map = new HashMap();
/* 33 */     for (User user : this.repository.findAllById(collection)) {
/* 34 */       map.put(user.getLogin(), user);
/*    */     }
/* 36 */     return map;
/*    */   }
/*    */   
/*    */   public Set<String> loadAllKeys()
/*    */   {
/* 41 */     return this.repository.findAllKeys();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\common-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\common\fm\mapstore\UserMapStore.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */