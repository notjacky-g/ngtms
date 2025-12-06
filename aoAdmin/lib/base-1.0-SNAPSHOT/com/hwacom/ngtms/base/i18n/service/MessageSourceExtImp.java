/*     */ package com.hwacom.ngtms.base.i18n.service;
/*     */ 
/*     */ import com.hwacom.ngtms.base.i18n.shared.MessageSourceExt;
/*     */ import com.hwacom.ngtms.base.i18n.shared.MessageType;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Enumeration;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import java.util.Properties;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import javax.annotation.PostConstruct;
/*     */ import org.springframework.beans.BeansException;
/*     */ import org.springframework.context.ApplicationContext;
/*     */ import org.springframework.context.ApplicationContextAware;
/*     */ import org.springframework.context.support.ReloadableResourceBundleMessageSource;
/*     */ import org.springframework.core.io.Resource;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MessageSourceExtImp
/*     */   extends ReloadableResourceBundleMessageSource
/*     */   implements MessageSourceExt, ApplicationContextAware
/*     */ {
/*     */   private static final String ABBR = ".abbr.";
/*     */   private static final String FULL = ".full.";
/*     */   private ApplicationContext applicationContext;
/*     */   private String basenamesRegex;
/*     */   
/*     */   @PostConstruct
/*     */   public void init() throws IOException {
/*     */     String baseDirectory;
/*  43 */     boolean inClassPath = false;
/*  44 */     String locationPattern = this.basenamesRegex + "/**/*.properties";
/*  45 */     if (this.basenamesRegex.startsWith("classpath*:"))
/*  46 */     { baseDirectory = this.basenamesRegex.substring(11).trim();
/*  47 */       inClassPath = true; }
/*  48 */     else if (this.basenamesRegex.startsWith("classpath:"))
/*  49 */     { baseDirectory = this.basenamesRegex.substring(10).trim();
/*  50 */       inClassPath = true; }
/*  51 */     else { baseDirectory = this.basenamesRegex; }
/*  52 */      Resource[] resources = this.applicationContext.getResources(locationPattern);
/*  53 */     String patternStr = ".*(" + baseDirectory + "/.*?)(_.*)?\\.properties";
/*  54 */     Pattern pattern = Pattern.compile(patternStr);
/*  55 */     HashSet<String> baseNameSet = new HashSet<>();
/*  56 */     for (Resource r : resources) {
/*  57 */       String path = r.getURL().getPath();
/*  58 */       Matcher matcher = pattern.matcher(path);
/*  59 */       if (matcher.find()) {
/*  60 */         baseNameSet.add(inClassPath ? ("classpath:" + matcher.group(1)) : matcher.group(1));
/*     */       }
/*     */     } 
/*  63 */     String[] baseNames = new String[baseNameSet.size()];
/*  64 */     baseNameSet.toArray((Object[])baseNames);
/*  65 */     if (this.logger.isDebugEnabled()) this.logger.debug("base names: " + Arrays.toString((Object[])baseNames)); 
/*  66 */     setBasenames(baseNames);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getMessage(String code) {
/*  71 */     return getMessage(code, null, null);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getMessage(String code, Locale locale) {
/*  76 */     return getMessage(code, null, locale);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getMessage(String code, Object... args) {
/*  81 */     return getMessage(code, args, null);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getMessage(String code, Locale locale, Object... args) {
/*  86 */     return getMessage(code, args, locale);
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String> getKeysOfSamePrefix(String prefix) {
/*  91 */     return getKeysOfSamePrefix(prefix, Locale.getDefault());
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String> getKeysOfSamePrefix(String prefix, Locale locale) {
/*  96 */     ReloadableResourceBundleMessageSource.PropertiesHolder propertiesHolder = getMergedProperties(locale);
/*  97 */     Properties properties = propertiesHolder.getProperties();
/*  98 */     List<String> names = new ArrayList<>();
/*  99 */     for (Enumeration<?> e = properties.keys(); e.hasMoreElements(); ) {
/* 100 */       String key = (String)e.nextElement();
/* 101 */       if (!key.startsWith(prefix))
/* 102 */         continue;  names.add(key.substring(prefix.length()));
/*     */     } 
/* 104 */     return names;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String> getValuesOfSamePrefix(String prefix) {
/* 109 */     return getValuesOfSamePrefix(prefix, Locale.getDefault());
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String> getValuesOfSamePrefix(String prefix, Locale locale) {
/* 114 */     ReloadableResourceBundleMessageSource.PropertiesHolder propertiesHolder = getMergedProperties(locale);
/* 115 */     Properties properties = propertiesHolder.getProperties();
/* 116 */     List<String> values = new ArrayList<>();
/* 117 */     for (Enumeration<?> e = properties.keys(); e.hasMoreElements(); ) {
/* 118 */       String key = (String)e.nextElement();
/* 119 */       if (!key.startsWith(prefix))
/* 120 */         continue;  values.add(getMessage(key, (Object[])null, locale));
/*     */     } 
/* 122 */     return values;
/*     */   }
/*     */ 
/*     */   
/*     */   public <E extends Enum<E> & MessageType> String getEnumAbbrMessage(E messageType) {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: aload_1
/*     */     //   2: ldc '.abbr.'
/*     */     //   4: invokevirtual getEnumMessage : (Ljava/lang/Enum;Ljava/lang/String;)Ljava/lang/String;
/*     */     //   7: areturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #127	-> 0
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   0	8	0	this	Lcom/hwacom/ngtms/base/i18n/service/MessageSourceExtImp;
/*     */     //   0	8	1	messageType	Ljava/lang/Enum;
/*     */     // Local variable type table:
/*     */     //   start	length	slot	name	signature
/*     */     //   0	8	1	messageType	TE;
/*     */   }
/*     */ 
/*     */   
/*     */   public <E extends Enum<E> & MessageType> String getEnumFullMessage(E messageType) {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: aload_1
/*     */     //   2: ldc '.full.'
/*     */     //   4: invokevirtual getEnumMessage : (Ljava/lang/Enum;Ljava/lang/String;)Ljava/lang/String;
/*     */     //   7: areturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #132	-> 0
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   0	8	0	this	Lcom/hwacom/ngtms/base/i18n/service/MessageSourceExtImp;
/*     */     //   0	8	1	messageType	Ljava/lang/Enum;
/*     */     // Local variable type table:
/*     */     //   start	length	slot	name	signature
/*     */     //   0	8	1	messageType	TE;
/*     */   }
/*     */ 
/*     */   
/*     */   public <E extends Enum<E> & MessageType> String getEnumMessage(E messageType, String middle) {
/* 137 */     return getMessage(((MessageType)messageType).getMessageKeyPrefix() + middle + messageType.toString());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public <E extends Enum<E> & MessageType> Map<E, String> getEnumAbbrMessages(Class<E> messageType) {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: aload_1
/*     */     //   2: ldc '.abbr.'
/*     */     //   4: invokevirtual getEnumMessages : (Ljava/lang/Class;Ljava/lang/String;)Ljava/util/Map;
/*     */     //   7: areturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #143	-> 0
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   0	8	0	this	Lcom/hwacom/ngtms/base/i18n/service/MessageSourceExtImp;
/*     */     //   0	8	1	messageType	Ljava/lang/Class;
/*     */     // Local variable type table:
/*     */     //   start	length	slot	name	signature
/*     */     //   0	8	1	messageType	Ljava/lang/Class<TE;>;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public <E extends Enum<E> & MessageType> Map<E, String> getEnumFullMessages(Class<E> messageType) {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: aload_1
/*     */     //   2: ldc '.full.'
/*     */     //   4: invokevirtual getEnumMessages : (Ljava/lang/Class;Ljava/lang/String;)Ljava/util/Map;
/*     */     //   7: areturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #149	-> 0
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   0	8	0	this	Lcom/hwacom/ngtms/base/i18n/service/MessageSourceExtImp;
/*     */     //   0	8	1	messageType	Ljava/lang/Class;
/*     */     // Local variable type table:
/*     */     //   start	length	slot	name	signature
/*     */     //   0	8	1	messageType	Ljava/lang/Class<TE;>;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public <E extends Enum<E> & MessageType> Map<E, String> getEnumMessages(Class<E> messageType, String middle) {
/* 155 */     Enum[] arrayOfEnum = (Enum[])messageType.getEnumConstants();
/* 156 */     HashMap<E, String> messageMap = new HashMap<>();
/* 157 */     for (Enum enum_ : arrayOfEnum) {
/* 158 */       messageMap.put((E)enum_, getMessage(((MessageType)enum_).getMessageKeyPrefix() + middle + enum_.toString()));
/*     */     }
/* 160 */     return messageMap;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
/* 165 */     this.applicationContext = applicationContext;
/*     */   }
/*     */   
/*     */   public ApplicationContext getApplicationContext() {
/* 169 */     return this.applicationContext;
/*     */   }
/*     */   
/*     */   public String getBasenamesRegex() {
/* 173 */     return this.basenamesRegex;
/*     */   }
/*     */   
/*     */   public void setBasenamesRegex(String basenamesRegex) throws IOException {
/* 177 */     this.basenamesRegex = basenamesRegex;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\lib\base-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\base\i18n\service\MessageSourceExtImp.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */