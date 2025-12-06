/*     */ package com.hwacom.ngtms.hcce.core.coordinator;
/*     */ 
/*     */ import com.hazelcast.config.Config;
/*     */ import com.hazelcast.config.GroupConfig;
/*     */ import com.hazelcast.core.Cluster;
/*     */ import com.hazelcast.core.HazelcastInstance;
/*     */ import com.hazelcast.core.Member;
/*     */ import com.hazelcast.core.MemberAttributeEvent;
/*     */ import com.hazelcast.core.MembershipEvent;
/*     */ import com.hazelcast.core.MembershipListener;
/*     */ import java.util.Iterator;
/*     */ import java.util.Set;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
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
/*     */ 
/*     */ 
/*     */ public class GroupLeadership
/*     */ {
/*  30 */   static final Logger logger = LoggerFactory.getLogger(GroupLeadership.class);
/*     */   private Cluster cluster;
/*     */   private LeaderCallback leaderCallback;
/*     */   private boolean leaderFlag;
/*     */   private String groupName;
/*  35 */   private Object syncObj = new Object();
/*     */   
/*     */   public GroupLeadership(HazelcastInstance hazelcastInstance) {
/*  38 */     this.cluster = hazelcastInstance.getCluster();
/*     */     try {
/*  40 */       this.groupName = hazelcastInstance.getConfig().getGroupConfig().getName();
/*     */     } catch (Exception ex) {
/*  42 */       this.groupName = "Unknown";
/*     */     }
/*     */   }
/*     */   
/*     */   public GroupLeadership(HazelcastInstance hazelcastInstance, LeaderCallback leaderCallback) {
/*  47 */     this(hazelcastInstance);
/*  48 */     setCallback(leaderCallback);
/*     */   }
/*     */   
/*     */   public void setCallback(LeaderCallback leaderCallback) {
/*  52 */     this.leaderCallback = leaderCallback;
/*  53 */     this.cluster.addMembershipListener(new MembershipListener()
/*     */     {
/*     */       public void memberAdded(MembershipEvent membersipEvent) {
/*  56 */         synchronized (GroupLeadership.this.syncObj) {
/*  57 */           if (GroupLeadership.this.leaderFlag) {
/*  58 */             GroupLeadership.this.leaderCallback.memberAdded(membersipEvent);
/*     */           }
/*  60 */           else if (GroupLeadership.this.isOldestMember()) {
/*  61 */             GroupLeadership.this.leaderFlag = true;
/*  62 */             GroupLeadership.logger.info("({}) becomes the leader of group: {}", membersipEvent
/*     */             
/*  64 */               .getMember(), 
/*  65 */               GroupLeadership.this.groupName);
/*  66 */             GroupLeadership.this.leaderCallback.onElectedLeader(membersipEvent.getMember());
/*     */           }
/*     */         }
/*     */       }
/*     */       
/*     */       public void memberRemoved(MembershipEvent membersipEvent)
/*     */       {
/*  73 */         synchronized (GroupLeadership.this.syncObj) {
/*  74 */           if (GroupLeadership.this.leaderFlag) {
/*  75 */             GroupLeadership.this.leaderCallback.memberRemoved(membersipEvent);
/*     */           }
/*  77 */           else if (GroupLeadership.this.isOldestMember()) {
/*  78 */             GroupLeadership.this.leaderFlag = true;
/*  79 */             GroupLeadership.logger.info("({}) becomes the leader of group: {}", membersipEvent
/*     */             
/*  81 */               .getMember(), 
/*  82 */               GroupLeadership.this.groupName);
/*  83 */             GroupLeadership.this.leaderCallback.onElectedLeader(membersipEvent.getMember());
/*     */           }
/*     */         }
/*     */       }
/*     */       
/*     */ 
/*     */       public void memberAttributeChanged(MemberAttributeEvent memberAttributeEvent)
/*     */       {
/*  91 */         GroupLeadership.logger.info(memberAttributeEvent.toString());
/*     */       }
/*     */     });
/*  94 */     synchronized (this.syncObj) {
/*  95 */       if ((!this.leaderFlag) && (isOldestMember())) {
/*  96 */         this.leaderFlag = true;
/*  97 */         Member member = this.cluster.getLocalMember();
/*  98 */         logger.info("({}) is the leader of group: {}", member, this.groupName);
/*  99 */         leaderCallback.onElectedLeader(member);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean isOldestMember() {
/* 105 */     Set<Member> memberSet = this.cluster.getMembers();
/* 106 */     Member oldestMember = (Member)memberSet.iterator().next();
/* 107 */     if (oldestMember.localMember()) return true;
/* 108 */     return false;
/*     */   }
/*     */   
/*     */   public boolean isLeader() {
/* 112 */     return this.leaderFlag;
/*     */   }
/*     */   
/*     */   public Set<Member> getMembers() {
/* 116 */     return this.cluster.getMembers();
/*     */   }
/*     */   
/*     */   public String getGroupName() {
/* 120 */     return this.groupName;
/*     */   }
/*     */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\hcce-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\hcce\core\coordinator\GroupLeadership.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */