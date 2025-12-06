/*     */ package com.hwacom.ngtms.common.websocket;
/*     */ 
/*     */ import com.hwacom.ngtms.frameworkcontext.WebSocketBeanProvider;
/*     */ import java.util.Arrays;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import javax.servlet.http.HttpSession;
/*     */ import javax.websocket.HandshakeResponse;
/*     */ import javax.websocket.server.HandshakeRequest;
/*     */ import javax.websocket.server.ServerEndpointConfig;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ import org.springframework.context.ApplicationContext;
/*     */ import org.springframework.core.annotation.AnnotationUtils;
/*     */ import org.springframework.stereotype.Component;
/*     */ import org.springframework.util.ClassUtils;
/*     */ import org.springframework.util.ObjectUtils;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SpringConfigurator
/*     */   extends ServerEndpointConfig.Configurator
/*     */ {
/*  39 */   private static final Logger logger = LoggerFactory.getLogger(SpringConfigurator.class);
/*     */   
/*  41 */   private static final Map<String, Map<Class<?>, String>> cache = new ConcurrentHashMap<>();
/*     */   
/*  43 */   private static final String NO_VALUE = ObjectUtils.identityToString(new Object());
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <T> T getEndpointInstance(Class<T> endpointClass) throws InstantiationException {
/*  49 */     ApplicationContext wac = WebSocketBeanProvider.getApplicationContext();
/*     */     
/*  51 */     if (wac == null) {
/*  52 */       String message = "Failed to find com.hwacom.ngtms.frameworkcontext.WebSocketBeanProvider. Was WebSocketBeanProvider as a Spring bean?";
/*     */       
/*  54 */       logger.error(message);
/*  55 */       throw new IllegalStateException(message);
/*     */     } 
/*     */     
/*  58 */     String beanName = ClassUtils.getShortNameAsProperty(endpointClass);
/*  59 */     if (wac.containsBean(beanName)) {
/*  60 */       T endpoint = (T)wac.getBean(beanName, endpointClass);
/*  61 */       if (logger.isTraceEnabled()) {
/*  62 */         logger.trace("Using @ServerEndpoint singleton " + endpoint);
/*     */       }
/*  64 */       return endpoint;
/*     */     } 
/*     */     
/*  67 */     Component annot = (Component)AnnotationUtils.findAnnotation(endpointClass, Component.class);
/*  68 */     if (annot != null && wac.containsBean(annot.value())) {
/*  69 */       T endpoint = (T)wac.getBean(annot.value(), endpointClass);
/*  70 */       if (logger.isTraceEnabled()) {
/*  71 */         logger.trace("Using @ServerEndpoint singleton " + endpoint);
/*     */       }
/*  73 */       return endpoint;
/*     */     } 
/*     */     
/*  76 */     beanName = getBeanNameByType(wac, endpointClass);
/*  77 */     if (beanName != null) {
/*  78 */       return (T)wac.getBean(beanName);
/*     */     }
/*     */     
/*  81 */     if (logger.isTraceEnabled()) {
/*  82 */       logger.trace("Creating new @ServerEndpoint instance of type " + endpointClass);
/*     */     }
/*  84 */     return (T)wac.getAutowireCapableBeanFactory().createBean(endpointClass);
/*     */   }
/*     */ 
/*     */   
/*     */   private String getBeanNameByType(ApplicationContext wac, Class<?> endpointClass) {
/*  89 */     String wacId = wac.getId();
/*     */     
/*  91 */     Map<Class<?>, String> beanNamesByType = cache.get(wacId);
/*  92 */     if (beanNamesByType == null) {
/*  93 */       beanNamesByType = new ConcurrentHashMap<>();
/*  94 */       cache.put(wacId, beanNamesByType);
/*     */     } 
/*     */     
/*  97 */     if (!beanNamesByType.containsKey(endpointClass)) {
/*  98 */       String[] names = wac.getBeanNamesForType(endpointClass);
/*  99 */       if (names.length == 1) {
/* 100 */         beanNamesByType.put(endpointClass, names[0]);
/*     */       } else {
/* 102 */         beanNamesByType.put(endpointClass, NO_VALUE);
/* 103 */         if (names.length > 1) {
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 108 */           String message = "Found multiple @ServerEndpoint's of type " + endpointClass + ", names=" + Arrays.toString((Object[])names);
/* 109 */           logger.error(message);
/* 110 */           throw new IllegalStateException(message);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 115 */     String beanName = beanNamesByType.get(endpointClass);
/* 116 */     return NO_VALUE.equals(beanName) ? null : beanName;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void modifyHandshake(ServerEndpointConfig sec, HandshakeRequest request, HandshakeResponse response) {
/* 122 */     super.modifyHandshake(sec, request, response);
/* 123 */     sec.getUserProperties().put(HttpSession.class.getName(), request.getHttpSession());
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\lib\common-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\common\websocket\SpringConfigurator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */