/*    */ package com.hwacom.ngtms.pd.fm.mapstore;
/*    */ 
/*    */ import com.hwacom.ngtms.base.hazelcast.JPAMapStore;
/*    */ import com.hwacom.ngtms.pd.fm.model.PdStatus;
/*    */ import com.hwacom.ngtms.pd.fm.repository.PdStatusRepository;
/*    */ import java.util.Collection;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import java.util.Set;
/*    */ import javax.annotation.PostConstruct;
/*    */ import org.springframework.beans.factory.annotation.Autowired;
/*    */ import org.springframework.stereotype.Service;
/*    */ 
/*    */ 
/*    */ 
/*    */ @Service
/*    */ public class PdStatusMapStore
/*    */   extends JPAMapStore<PdStatus, String>
/*    */ {
/*    */   @Autowired
/*    */   PdStatusRepository repository;
/*    */   
/*    */   @PostConstruct
/*    */   public void setCrudRepository()
/*    */   {
/* 26 */     setCrudRepository(this.repository);
/*    */   }
/*    */   
/*    */   public Map<String, PdStatus> loadAll(Collection<String> collection)
/*    */   {
/* 31 */     Map<String, PdStatus> map = new HashMap();
/*    */     
/* 33 */     for (PdStatus config : this.repository.findAllById(collection)) {
/* 34 */       map.put(config.getDeviceName(), config);
/*    */     }
/* 36 */     return map;
/*    */   }
/*    */   
/*    */   public Set<String> loadAllKeys()
/*    */   {
/* 41 */     return this.repository.findAllKeys();
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\fm-pd-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\pd\fm\mapstore\PdStatusMapStore.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */