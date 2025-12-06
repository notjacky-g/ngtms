/*     */ package com.hwacom.ngtms.hcce.util;
/*     */ 
/*     */ import com.hazelcast.core.Cluster;
/*     */ import com.hazelcast.core.HazelcastInstance;
/*     */ import com.hazelcast.core.IMap;
/*     */ import com.hazelcast.core.Member;
/*     */ import com.hwacom.ngtms.base.hazelcast.HzUtils;
/*     */ import com.hwacom.ngtms.base.rmi.RmiUtils;
/*     */ import com.hwacom.ngtms.hcce.config.model.DynamicConfig;
/*     */ import com.hwacom.ngtms.hcce.config.model.DynamicConfigPk;
/*     */ import com.hwacom.ngtms.hcce.core.message.CmInternal;
/*     */ import com.hwacom.ngtms.hcce.core.message.NmInternal;
/*     */ import com.hwacom.ngtms.hcce.fme.controller.fm.FmeMainBase;
/*     */ import com.hwacom.ngtms.hcce.hz.HzMap;
/*     */ import com.hwacom.ngtms.hcce.remote.CmRemote;
/*     */ import com.hwacom.ngtms.hcce.shared.FmeExeStatus;
/*     */ import com.hwacom.ngtms.hcce.shared.TopologyInfo;
/*     */ import com.hwacom.ngtms.hcce.shared.TopologyNode;
/*     */ import com.hwacom.ngtms.hcce.topology.model.TopologyNodeCfg;
/*     */ import java.net.InetAddress;
/*     */ import java.net.InetSocketAddress;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ 
/*     */ public class HcceUtils
/*     */ {
/*     */   public static NmInternal getNmRmiClient(String nodeName, String ipAddress)
/*     */   {
/*  30 */     return (NmInternal)RmiUtils.getRemoteClient(ipAddress, nodeName + "/" + "nmInternal", NmInternal.class);
/*     */   }
/*     */   
/*     */   public static CmInternal getCmRmiClient()
/*     */   {
/*  35 */     Member member = (Member)HzUtils.getMembers().iterator().next();
/*  36 */     return (CmInternal)RmiUtils.getRemoteClient(member
/*  37 */       .getSocketAddress().getAddress().getHostAddress(), member
/*  38 */       .getStringAttribute("nodeName") + "/" + "cmInternal", CmInternal.class);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static CmRemote getCmRemote(HazelcastInstance hzInstance)
/*     */   {
/*  49 */     Member coordinator = (Member)hzInstance.getCluster().getMembers().iterator().next();
/*  50 */     String cmIpAddress = coordinator.getSocketAddress().getAddress().getHostAddress();
/*  51 */     return (CmRemote)RmiUtils.getRemoteClient(cmIpAddress, coordinator
/*     */     
/*  53 */       .getStringAttribute("nodeName") + "/" + "remote/cm", CmRemote.class);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static CmRemote getCmRemote()
/*     */   {
/*  62 */     Member coordinator = HzUtils.getCoordinator();
/*  63 */     String cmIpAddress = coordinator.getSocketAddress().getAddress().getHostAddress();
/*  64 */     return (CmRemote)RmiUtils.getRemoteClient(cmIpAddress, 
/*  65 */       HzUtils.getInstanceName() + "/" + "remote/cm", CmRemote.class);
/*     */   }
/*     */   
/*     */   public static CmRemote getCoordinatorCmRemote() {
/*  69 */     Member coordinator = HzUtils.getCoordinator();
/*  70 */     String cmIpAddress = coordinator.getSocketAddress().getAddress().getHostAddress();
/*  71 */     IMap<String, TopologyInfo> topologyInfoMap = HzUtils.getMap(HzMap.TopologyInfo);
/*  72 */     TopologyInfo topologyInfo = (TopologyInfo)topologyInfoMap.get(HzUtils.getGroupName());
/*  73 */     for (TopologyNode topologyNode : topologyInfo.getNodeMap().values()) {
/*  74 */       if (java.util.Objects.equals(cmIpAddress, topologyNode.getLastRegIpAddress())) {
/*  75 */         return (CmRemote)RmiUtils.getRemoteClient(cmIpAddress, topologyNode
/*     */         
/*  77 */           .getNodeCfg().getNodeName() + "/" + "remote/cm", CmRemote.class);
/*     */       }
/*     */     }
/*     */     
/*  81 */     throw new RuntimeException("Can't get corresponding node with ip: '" + cmIpAddress + "'");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static <T> T getRemote(HazelcastInstance hzInstance, String fmeName, Class<T> serviceInterface)
/*     */   {
/*  94 */     IMap<String, FmeExeStatus> fmeExeStatusMap = hzInstance.getMap(HzMap.FmeExeStatus.toHzName());
/*  95 */     for (FmeExeStatus fmeStatus : fmeExeStatusMap.values()) {
/*  96 */       if (fmeStatus.getFmeName().equals(fmeName)) {
/*  97 */         return (T)RmiUtils.getRemoteClient(fmeStatus
/*  98 */           .getAssignedMemberIpAddress(), fmeStatus
/*  99 */           .getAssignedNodeName() + "/" + fmeStatus.getFmeName(), serviceInterface);
/*     */       }
/*     */     }
/*     */     
/* 103 */     return null;
/*     */   }
/*     */   
/*     */   public static <T> T getRemote(HazelcastInstance hzInstance, String fmeName, String serviceName, Class<T> serviceInterface)
/*     */   {
/* 108 */     IMap<String, FmeExeStatus> fmeExeStatusMap = hzInstance.getMap(HzMap.FmeExeStatus.toHzName());
/* 109 */     for (FmeExeStatus fmeStatus : fmeExeStatusMap.values()) {
/* 110 */       if (fmeStatus.getFmeName().equals(fmeName)) {
/* 111 */         return (T)RmiUtils.getRemoteClient(fmeStatus
/* 112 */           .getAssignedMemberIpAddress(), fmeStatus
/* 113 */           .getAssignedNodeName() + "/" + fmeStatus.getFmeName() + "/" + serviceName, serviceInterface);
/*     */       }
/*     */     }
/*     */     
/* 117 */     return null;
/*     */   }
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
/*     */   public static <T> T getRemote(HazelcastInstance hzInstance, Class<? extends FmeMainBase> fmeClass, Class<T> serviceInterface)
/*     */   {
/* 131 */     IMap<String, FmeExeStatus> fmeExeStatusMap = hzInstance.getMap(HzMap.FmeExeStatus.toHzName());
/* 132 */     for (FmeExeStatus fmeStatus : fmeExeStatusMap.values()) {
/* 133 */       if (fmeStatus.getFmeClassName().equals(fmeClass.getName())) {
/* 134 */         return (T)RmiUtils.getRemoteClient(fmeStatus
/* 135 */           .getAssignedMemberIpAddress(), fmeStatus
/* 136 */           .getAssignedNodeName() + "/" + fmeStatus.getFmeName(), serviceInterface);
/*     */       }
/*     */     }
/*     */     
/* 140 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static <T> T getRemote(String fmeName, Class<T> serviceInterface)
/*     */   {
/* 151 */     IMap<String, FmeExeStatus> fmeExeStatusMap = HzUtils.getMap(HzMap.FmeExeStatus);
/* 152 */     for (FmeExeStatus fmeStatus : fmeExeStatusMap.values()) {
/* 153 */       if (fmeStatus.getFmeName().equals(fmeName)) {
/* 154 */         return (T)RmiUtils.getRemoteClient(fmeStatus
/* 155 */           .getAssignedMemberIpAddress(), fmeStatus
/* 156 */           .getAssignedNodeName() + "/" + fmeStatus.getFmeName(), serviceInterface);
/*     */       }
/*     */     }
/*     */     
/* 160 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static DynamicConfig getDynaConfig(String fmeName, String configName)
/*     */   {
/* 171 */     IMap<DynamicConfigPk, DynamicConfig> dynamicConfigMap = HzUtils.getMap(HzMap.DynamicConfig);
/* 172 */     return (DynamicConfig)dynamicConfigMap.get(new DynamicConfigPk(HzUtils.getGroupName(), fmeName, configName));
/*     */   }
/*     */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\hcce-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\hcce\util\HcceUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */