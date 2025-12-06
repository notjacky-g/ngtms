/*     */ package com.hwacom.ngtms.base.hazelcast;
/*     */ 
/*     */ import com.hazelcast.core.Cluster;
/*     */ import com.hazelcast.core.HazelcastInstance;
/*     */ import com.hazelcast.core.ILock;
/*     */ import com.hazelcast.core.IMap;
/*     */ import com.hazelcast.core.IQueue;
/*     */ import com.hazelcast.core.ITopic;
/*     */ import com.hazelcast.core.IdGenerator;
/*     */ import com.hazelcast.core.Member;
/*     */ import com.hazelcast.core.TransactionalMap;
/*     */ import com.hazelcast.core.TransactionalQueue;
/*     */ import com.hazelcast.transaction.TransactionContext;
/*     */ import java.net.InetAddress;
/*     */ import java.net.InetSocketAddress;
/*     */ import java.util.Iterator;
/*     */ import java.util.Set;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class HzUtils
/*     */ {
/*  31 */   private static HazelcastInstance hzInstance = null;
/*     */   
/*     */ 
/*     */   private static String instanceName;
/*     */   
/*     */   private static String groupName;
/*     */   
/*     */ 
/*     */   public static HazelcastInstance getHzInstance()
/*     */   {
/*  41 */     return hzInstance;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected static void setHzInstance(HazelcastInstance hazelcastInstance)
/*     */   {
/*  50 */     hzInstance = hazelcastInstance;
/*     */   }
/*     */   
/*     */   protected static void setInstanceInfo(String instanceName, String groupName) {
/*  54 */     instanceName = instanceName;
/*  55 */     groupName = groupName;
/*     */   }
/*     */   
/*     */   public static String getInstanceName() {
/*  59 */     return instanceName;
/*     */   }
/*     */   
/*     */   public static String getGroupName() {
/*  63 */     return groupName;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static Member getCoordinator()
/*     */   {
/*  72 */     return (Member)getMembers().iterator().next();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static Set<Member> getMembers()
/*     */   {
/*  81 */     return hzInstance.getCluster().getMembers();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static Member getLocalMember()
/*     */   {
/*  90 */     return hzInstance.getCluster().getLocalMember();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static String getIpAddress(Member m)
/*     */   {
/* 100 */     if (m == null) {
/* 101 */       return null;
/*     */     }
/* 103 */     return m.getSocketAddress().getAddress().getHostAddress();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static String getLocalMemberIpAddress()
/*     */   {
/* 112 */     return getIpAddress(getHzInstance().getCluster().getLocalMember());
/*     */   }
/*     */   
/*     */   public static Member findMemberByUuid(String uuid) {
/* 116 */     Set<Member> members = getMembers();
/* 117 */     for (Member m : members) {
/* 118 */       if (m.getUuid().equals(uuid)) return m;
/*     */     }
/* 120 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static <K, V> IMap<K, V> getMap(HzDistObjEnum hzMap)
/*     */   {
/* 129 */     return hzInstance.getMap(hzMap.toHzName());
/*     */   }
/*     */   
/*     */   public static <T> TransactionalMap<String, T> getMap(TransactionContext context, HzDistObjEnum hzMap)
/*     */   {
/* 134 */     return context.getMap(hzMap.toHzName());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static <E> IQueue<E> getQueue(HzDistObjEnum hzQueue)
/*     */   {
/* 143 */     return hzInstance.getQueue(hzQueue.toHzName());
/*     */   }
/*     */   
/*     */   public static <E> TransactionalQueue<E> getQueue(TransactionContext context, HzDistObjEnum hzQueue)
/*     */   {
/* 148 */     return context.getQueue(hzQueue.toHzName());
/*     */   }
/*     */   
/*     */   public static ILock getLock(HzDistObjEnum hzLock) {
/* 152 */     return hzInstance.getLock(hzLock.toHzName());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static IdGenerator getIdGenerator(HzDistObjEnum hzIdGen)
/*     */   {
/* 162 */     return hzInstance.getIdGenerator(hzIdGen.toHzName());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static <E> ITopic<E> getTopic(HzDistObjEnum hzTopic)
/*     */   {
/* 171 */     return hzInstance.getTopic(hzTopic.toHzName());
/*     */   }
/*     */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\base-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\base\hazelcast\HzUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */