/*     */ package com.hwacom.ngtms.hcce.frameworkcontext;
/*     */ 
/*     */ import com.hazelcast.client.config.ClientConfig;
/*     */ import com.hazelcast.config.ClasspathXmlConfig;
/*     */ import com.hazelcast.config.Config;
/*     */ import com.hazelcast.config.FileSystemXmlConfig;
/*     */ import com.hazelcast.config.MapConfig;
/*     */ import com.hazelcast.config.QueueConfig;
/*     */ import com.hwacom.ngtms.base.hazelcast.HzUtilsSetter;
/*     */ import com.hwacom.ngtms.base.rmi.RmiUtilsSetter;
/*     */ import com.hwacom.ngtms.base.util.ResolveString;
/*     */ import com.hwacom.ngtms.hcce.shared.HcceEnv;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.IOException;
/*     */ import java.net.InetAddress;
/*     */ import java.net.UnknownHostException;
/*     */ import java.util.Arrays;
/*     */ import java.util.Optional;
/*     */ import javax.annotation.Resource;
/*     */ import org.apache.commons.codec.digest.DigestUtils;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ import org.springframework.beans.factory.annotation.Value;
/*     */ import org.springframework.context.annotation.Bean;
/*     */ import org.springframework.context.annotation.Configuration;
/*     */ import org.springframework.core.env.Environment;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Configuration
/*     */ public class HazelcastConfig
/*     */ {
/*  38 */   private static Logger logger = LoggerFactory.getLogger(HazelcastConfig.class);
/*  39 */   public static String HAZELCAST_CONFIG_PATH = "conf/hazelcast.xml";
/*     */ 
/*     */   
/*     */   static {
/*  43 */     System.setProperty("hazelcast.logging.type", "slf4j");
/*     */   }
/*     */ 
/*     */   
/*     */   @Value("${hz.md5.value}")
/*     */   private String HAZELCAST_CONFIG_MD5_VALUE;
/*     */   @Value("${hz.xmlconfig.enable:false}")
/*     */   private Boolean HAZELCAST_XML_CONFIG_ENABLE_VALUE;
/*     */   @Resource
/*     */   private Environment environment;
/*     */   
/*     */   @Bean
/*     */   public Config hazelcastConfig() throws IOException {
/*     */     ClasspathXmlConfig classpathXmlConfig;
/*  57 */     HcceEnv hcceEnv = getHcceEnv();
/*     */     
/*  59 */     Config config = null;
/*  60 */     String localMd5Value = null;
/*  61 */     if (this.HAZELCAST_XML_CONFIG_ENABLE_VALUE.booleanValue()) {
/*  62 */       HAZELCAST_CONFIG_PATH = this.environment.getProperty("hz.xmlconfig.file");
/*     */       
/*  64 */       localMd5Value = DigestUtils.md5Hex(new FileInputStream(HAZELCAST_CONFIG_PATH));
/*  65 */       FileSystemXmlConfig fileSystemXmlConfig = new FileSystemXmlConfig(HAZELCAST_CONFIG_PATH);
/*     */     }
/*     */     else {
/*     */       
/*  69 */       localMd5Value = DigestUtils.md5Hex(HazelcastConfig.class
/*  70 */           .getResourceAsStream("/" + HAZELCAST_CONFIG_PATH));
/*  71 */       classpathXmlConfig = new ClasspathXmlConfig(HAZELCAST_CONFIG_PATH);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  77 */     if (!this.HAZELCAST_CONFIG_MD5_VALUE.equals(localMd5Value)) {
/*  78 */       throw new IOException("The md5 hash value of " + HAZELCAST_CONFIG_PATH + " is not equal to " + localMd5Value);
/*     */     }
/*     */ 
/*     */     
/*  82 */     classpathXmlConfig
/*  83 */       .getMemberAttributeConfig()
/*  84 */       .setStringAttribute("groupName", hcceEnv.getCurrentGroupName());
/*  85 */     classpathXmlConfig
/*  86 */       .getMemberAttributeConfig()
/*  87 */       .setStringAttribute("nodeName", hcceEnv.getNodeName());
/*  88 */     classpathXmlConfig
/*  89 */       .getMemberAttributeConfig()
/*  90 */       .setBooleanAttribute("inPrimaryGroup", hcceEnv.isInPrimaryGroup());
/*  91 */     classpathXmlConfig.setInstanceName(hcceEnv.getNodeName());
/*  92 */     classpathXmlConfig
/*  93 */       .getGroupConfig()
/*  94 */       .setName(hcceEnv.getCurrentGroupName())
/*  95 */       .setPassword(this.environment.getRequiredProperty("hz.group.password"));
/*  96 */     classpathXmlConfig
/*  97 */       .getNetworkConfig()
/*  98 */       .setPort(((Integer)this.environment.getRequiredProperty("hz.network.port", Integer.class)).intValue());
/*     */     
/* 100 */     if (!StringUtils.isBlank(this.environment.getProperty("hz.binding.interface"))) {
/*     */       
/* 102 */       String[] addresses = this.environment.getRequiredProperty("hz.binding.interface").trim().split("\\s*,\\s*");
/* 103 */       classpathXmlConfig
/* 104 */         .getNetworkConfig()
/* 105 */         .getInterfaces()
/* 106 */         .setEnabled(true)
/* 107 */         .setInterfaces(Arrays.asList(addresses));
/*     */     } 
/* 109 */     if (((Boolean)this.environment.getRequiredProperty("hz.multicast.enabled", Boolean.class)).booleanValue()) {
/* 110 */       classpathXmlConfig
/* 111 */         .getNetworkConfig()
/* 112 */         .getJoin()
/* 113 */         .getMulticastConfig()
/* 114 */         .setEnabled(true)
/* 115 */         .setMulticastGroup(this.environment.getRequiredProperty("hz.multicast.group"))
/* 116 */         .setMulticastPort(((Integer)this.environment.getRequiredProperty("hz.multicast.port", Integer.class)).intValue());
/*     */       
/* 118 */       classpathXmlConfig.getNetworkConfig().getJoin().getTcpIpConfig().setEnabled(false);
/*     */     } else {
/* 120 */       classpathXmlConfig
/* 121 */         .getNetworkConfig()
/* 122 */         .getJoin()
/* 123 */         .getTcpIpConfig()
/* 124 */         .setEnabled(true)
/* 125 */         .setMembers(
/* 126 */           Arrays.asList(new String[] {
/*     */               
/* 128 */               ResolveString.resolve(this.environment.getRequiredProperty("hz.members"))
/*     */             }));
/*     */       
/* 131 */       classpathXmlConfig.getNetworkConfig().getJoin().getMulticastConfig().setEnabled(false);
/*     */     } 
/*     */     
/* 134 */     MapConfig mapConfig = classpathXmlConfig.getMapConfig("default");
/* 135 */     mapConfig.setBackupCount(((Integer)this.environment
/* 136 */         .getProperty("hz.imap.defaultBackupCount", Integer.class, Integer.valueOf(1))).intValue());
/* 137 */     mapConfig.setAsyncBackupCount(((Integer)this.environment
/* 138 */         .getProperty("hz.imap.defaultAsyncBackupCount", Integer.class, Integer.valueOf(0))).intValue());
/* 139 */     mapConfig.setReadBackupData(((Boolean)this.environment
/* 140 */         .getProperty("hz.imap.readBackupData", Boolean.class, Boolean.valueOf(false))).booleanValue());
/*     */     
/* 142 */     QueueConfig queueConfig = classpathXmlConfig.getQueueConfig("default");
/* 143 */     queueConfig.setBackupCount(((Integer)this.environment
/* 144 */         .getProperty("hz.iqueue.defaultBackupCount", Integer.class, Integer.valueOf(1))).intValue());
/* 145 */     queueConfig.setAsyncBackupCount(((Integer)this.environment
/* 146 */         .getProperty("hz.iqueue.defaultAsyncBackupCount", Integer.class, Integer.valueOf(0))).intValue());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 156 */     classpathXmlConfig
/* 157 */       .getManagementCenterConfig()
/* 158 */       .setUrl(
/* 159 */         ResolveString.resolve(this.environment
/* 160 */           .getProperty("hz.management.center.url", "http://localhost:8080/mancenter")));
/*     */ 
/*     */     
/* 163 */     logger.info("-------------Hazelcast Config-------------");
/* 164 */     logger.info("instanceName: {}", classpathXmlConfig.getInstanceName());
/* 165 */     logger.info("groupName: {}", classpathXmlConfig.getGroupConfig().getName());
/* 166 */     logger.info("groupMembers: {}", classpathXmlConfig
/* 167 */         .getNetworkConfig().getJoin().getTcpIpConfig().getMembers());
/* 168 */     logger.info("networkPort: {}", Integer.valueOf(classpathXmlConfig.getNetworkConfig().getPort()));
/* 169 */     logger.info("multicastEnable: {}", 
/*     */         
/* 171 */         Boolean.valueOf(classpathXmlConfig.getNetworkConfig().getJoin().getMulticastConfig().isEnabled()));
/* 172 */     logger.info("multicastGroup: {}", classpathXmlConfig
/*     */         
/* 174 */         .getNetworkConfig().getJoin().getMulticastConfig().getMulticastGroup());
/* 175 */     logger.info("multicastPort: {}", 
/*     */         
/* 177 */         Integer.valueOf(classpathXmlConfig.getNetworkConfig().getJoin().getMulticastConfig().getMulticastPort()));
/* 178 */     logger.info("managementCenterEnabled: {}", Boolean.valueOf(classpathXmlConfig.getManagementCenterConfig().isEnabled()));
/* 179 */     logger.info("managementCenterUrl: {}", classpathXmlConfig.getManagementCenterConfig().getUrl());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 188 */     logger.info("-----------------------------------------");
/*     */     
/* 190 */     return (Config)classpathXmlConfig;
/*     */   }
/*     */   
/*     */   @Bean
/*     */   public ClientConfig hazelcastClientConfig() throws IOException {
/* 195 */     ClientConfig config = new ClientConfig();
/* 196 */     config
/* 197 */       .getGroupConfig()
/* 198 */       .setName(hazelcastConfig().getGroupConfig().getName())
/* 199 */       .setPassword(hazelcastConfig().getGroupConfig().getPassword());
/*     */ 
/*     */ 
/*     */     
/* 203 */     String[] addresses = ResolveString.resolve(this.environment.getRequiredProperty("hz.members")).trim().split("\\s*,\\s*");
/* 204 */     config.getNetworkConfig().addAddress(addresses);
/*     */ 
/*     */     
/* 207 */     int connectionTimeout = ((Integer)this.environment.getRequiredProperty("hz.client.connection.timeout", Integer.class)).intValue();
/*     */     
/* 209 */     if (this.environment.getProperty("ngtms.product.mode") != null && "false"
/* 210 */       .equalsIgnoreCase(this.environment.getProperty("ngtms.product.mode")) && 
/* 211 */       connectionTimeout < 3000) connectionTimeout = 3000;
/*     */ 
/*     */     
/* 214 */     config.getNetworkConfig().setConnectionTimeout(connectionTimeout);
/* 215 */     config
/* 216 */       .getNetworkConfig()
/* 217 */       .setConnectionAttemptLimit(((Integer)this.environment
/* 218 */         .getRequiredProperty("hz.client.connection.attempt.limit", Integer.class)).intValue());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 224 */     config.setProperty("hazelcast.logging.type", "slf4j");
/*     */     
/* 226 */     return config;
/*     */   }
/*     */   
/*     */   @Bean
/*     */   public HcceEnv getHcceEnv() {
/* 231 */     HcceEnv hcceEnv = new HcceEnv();
/* 232 */     String nodeName = ResolveString.resolve(this.environment.getProperty("hcce.node.name"));
/* 233 */     hcceEnv.setNodeName(nodeName);
/*     */ 
/*     */     
/* 236 */     String primaryGroupName = ResolveString.resolve(this.environment.getProperty("hcce.primary.group.name"));
/* 237 */     hcceEnv.setPrimaryGroupName(primaryGroupName);
/*     */ 
/*     */     
/* 240 */     String backupGroupName = ResolveString.resolve(this.environment.getProperty("hcce.backup.group.name"));
/* 241 */     hcceEnv.setBackupGroupName(backupGroupName);
/*     */     
/* 243 */     Optional.<String>ofNullable(this.environment.getProperty("hcce.clientNode.group"))
/* 244 */       .ifPresent(value -> {
/*     */           String clientGroupName = ResolveString.resolve(value);
/*     */           
/*     */           paramHcceEnv.setClientGroupName(clientGroupName);
/*     */         });
/*     */     
/* 250 */     hcceEnv.setInPrimaryGroup(((Boolean)this.environment.getProperty("hcce.in.primary.group", Boolean.class)).booleanValue());
/* 251 */     hcceEnv.setCurrentGroupName(
/* 252 */         hcceEnv.isInPrimaryGroup() ? hcceEnv.getPrimaryGroupName() : hcceEnv.getBackupGroupName());
/*     */     
/* 254 */     hcceEnv.setRmiRegistryPort(((Integer)this.environment.getProperty("hcce.rmi.registry.port", Integer.class)).intValue());
/* 255 */     hcceEnv.setFmeManagementFirstDelay(((Integer)this.environment
/* 256 */         .getProperty("hcce.fme.management.first.delay", Integer.class)).intValue());
/* 257 */     hcceEnv.setFmeManagementInterval(((Integer)this.environment
/* 258 */         .getProperty("hcce.fme.management.interval", Integer.class)).intValue());
/*     */     try {
/* 260 */       hcceEnv.setLocalIpAddress(InetAddress.getLocalHost().getHostAddress());
/* 261 */     } catch (UnknownHostException e) {
/* 262 */       logger.warn("Failed to get local address", e);
/*     */     } 
/*     */ 
/*     */     
/* 266 */     HzUtilsSetter.setInstanceInfo(hcceEnv.getNodeName(), hcceEnv.getCurrentGroupName());
/* 267 */     RmiUtilsSetter.setRmiRegistryPort(hcceEnv.getRmiRegistryPort());
/* 268 */     return hcceEnv;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\lib\hcce-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\hcce\frameworkcontext\HazelcastConfig.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */