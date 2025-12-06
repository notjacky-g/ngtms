/*     */ package com.hwacom.ngtms.room.service;
/*     */ 
/*     */ import com.hazelcast.core.IMap;
/*     */ import com.hwacom.ngtms.base.hazelcast.HzDistObjEnum;
/*     */ import com.hwacom.ngtms.base.hazelcast.HzUtils;
/*     */ import com.hwacom.ngtms.hcce.config.model.DynamicConfig;
/*     */ import com.hwacom.ngtms.hcce.config.model.DynamicConfigPk;
/*     */ import com.hwacom.ngtms.hcce.hz.HzMap;
/*     */ import org.apache.http.client.HttpClient;
/*     */ import org.apache.http.impl.client.HttpClients;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ import org.springframework.context.annotation.Profile;
/*     */ import org.springframework.http.HttpEntity;
/*     */ import org.springframework.http.HttpHeaders;
/*     */ import org.springframework.http.HttpMethod;
/*     */ import org.springframework.http.MediaType;
/*     */ import org.springframework.http.ResponseEntity;
/*     */ import org.springframework.http.client.ClientHttpRequestFactory;
/*     */ import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
/*     */ import org.springframework.stereotype.Service;
/*     */ import org.springframework.util.MultiValueMap;
/*     */ import org.springframework.web.client.RestTemplate;
/*     */ 
/*     */ @Service
/*     */ @Profile({"dev"})
/*     */ public class RoomCardReaderConnectorServiceImpl implements RoomCardReaderConnectorService {
/*  28 */   private static final Logger logger = LoggerFactory.getLogger(RoomCardReaderConnectorServiceImpl.class);
/*     */ 
/*     */   
/*     */   public Boolean issueCard(String deviceName, String cardAba) {
/*  32 */     String api = getDynaConfig("RoomCardReaderAddDeleteCardApi").getValue();
/*  33 */     return creatContection(HttpMethod.POST, api, deviceName, cardAba);
/*     */   }
/*     */ 
/*     */   
/*     */   public Boolean deleteCard(String deviceName, String cardAba) {
/*  38 */     String api = getDynaConfig("RoomCardReaderAddDeleteCardApi").getValue();
/*  39 */     return creatContection(HttpMethod.DELETE, api, deviceName, cardAba);
/*     */   }
/*     */ 
/*     */   
/*     */   public Boolean openDoor(String deviceName, Integer mode) {
/*  44 */     String api = getDynaConfig("RoomOpenDoorApi").getValue();
/*  45 */     return openDoorConection(HttpMethod.PUT, api, deviceName, mode);
/*     */   }
/*     */ 
/*     */   
/*     */   public Boolean putOpenDoorPassword(String deviceName, Integer password) {
/*  50 */     String api = getDynaConfig("RoomOpenDoorPasswordApi").getValue();
/*  51 */     return openDoorPasswordConection(HttpMethod.PUT, api, deviceName, password.intValue());
/*     */   }
/*     */   
/*     */   public Integer getCardCount(String deviceName) {
/*  55 */     String api = getDynaConfig("RoomCardCountApi").getValue();
/*  56 */     return cardCountContection(HttpMethod.GET, api, deviceName);
/*     */   }
/*     */   
/*     */   private Boolean creatContection(HttpMethod method, String api, String deviceName, String aba) {
/*     */     try {
/*  61 */       logger.debug("creatContection add/delete card with spring RestTemplate.");
/*  62 */       logger.debug("method:'{}', api:'{}', deviceName:'{}', aba:'{}'", new Object[] { method, api, deviceName, aba });
/*     */       
/*  64 */       long startTime = System.currentTimeMillis();
/*     */       
/*  66 */       HttpComponentsClientHttpRequestFactory httpComponentsClientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory((HttpClient)HttpClients.createDefault());
/*     */       
/*  68 */       String ip = getDynaConfig("RoomCardReaderApiURL").getValue();
/*  69 */       HttpHeaders headers = new HttpHeaders();
/*  70 */       headers.setContentType(MediaType.APPLICATION_JSON);
/*     */       
/*  72 */       String url = ip + "/" + api + "/" + "{deviceName}";
/*  73 */       logger.debug("url:'{}'", url);
/*  74 */       RestTemplate restTemplate = new RestTemplate((ClientHttpRequestFactory)httpComponentsClientHttpRequestFactory);
/*  75 */       HttpEntity<String> requestEntity = new HttpEntity(aba, (MultiValueMap)headers);
/*     */       
/*  77 */       ResponseEntity<String> result = restTemplate.exchange(url, method, requestEntity, String.class, new Object[] { deviceName });
/*  78 */       logger.debug("add/delete result.getStatusCodeValue():'{}'", Integer.valueOf(result.getStatusCodeValue()));
/*  79 */       logger.debug("cardReader createContection end ,{},time diff {}", 
/*     */           
/*  81 */           Long.valueOf(System.currentTimeMillis()), 
/*  82 */           Long.valueOf(System.currentTimeMillis() - startTime));
/*  83 */       logger.debug("cardReader card count:'{}'", getCardCount(deviceName));
/*  84 */       if (result.getStatusCodeValue() == 200 || result.getStatusCodeValue() == 204) {
/*  85 */         return Boolean.valueOf(true);
/*     */       }
/*     */       
/*  88 */       return Boolean.valueOf(false);
/*  89 */     } catch (Exception e) {
/*  90 */       logger.error("creatContectionTemplate failed.", e);
/*  91 */       return Boolean.valueOf(false);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private Integer cardCountContection(HttpMethod method, String api, String deviceName) {
/*     */     try {
/*  98 */       logger.debug("creatContection cardCount with spring RestTemplate.");
/*  99 */       logger.debug("method:'{}', api:'{}', deviceName:'{}'", new Object[] { method, api, deviceName });
/*     */       
/* 101 */       HttpComponentsClientHttpRequestFactory httpComponentsClientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory((HttpClient)HttpClients.createDefault());
/*     */       
/* 103 */       String ip = getDynaConfig("RoomCardReaderApiURL").getValue();
/* 104 */       HttpHeaders headers = new HttpHeaders();
/* 105 */       headers.setContentType(MediaType.APPLICATION_JSON);
/*     */       
/* 107 */       String url = ip + "/" + api + "/" + "{deviceName}";
/* 108 */       logger.debug("url:'{}'", url);
/* 109 */       RestTemplate restTemplate = new RestTemplate((ClientHttpRequestFactory)httpComponentsClientHttpRequestFactory);
/* 110 */       HttpEntity<String> requestEntity = new HttpEntity((MultiValueMap)headers);
/*     */       
/* 112 */       ResponseEntity<Integer> result = restTemplate.exchange(url, method, requestEntity, Integer.class, new Object[] { deviceName });
/* 113 */       logger.debug("card count result.getStatusCodeValue():'{}'", Integer.valueOf(result.getStatusCodeValue()));
/* 114 */       if (result.getStatusCodeValue() == 200 || result.getStatusCodeValue() == 204) {
/* 115 */         return (Integer)result.getBody();
/*     */       }
/* 117 */       return null;
/* 118 */     } catch (Exception e) {
/* 119 */       logger.error("creatContectionTemplate cardCount failed.", e);
/* 120 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private Boolean openDoorConection(HttpMethod method, String api, String deviceName, Integer mode) {
/*     */     try {
/* 127 */       logger.debug("openDoorConection with spring RestTemplate.");
/* 128 */       logger.debug("method:'{}', api:'{}', deviceName:'{}'", new Object[] { method, api, deviceName });
/* 129 */       long startTime = System.currentTimeMillis();
/*     */       
/* 131 */       HttpComponentsClientHttpRequestFactory httpComponentsClientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory((HttpClient)HttpClients.createDefault());
/*     */       
/* 133 */       String ip = getDynaConfig("RoomCardReaderApiURL").getValue();
/* 134 */       HttpHeaders headers = new HttpHeaders();
/* 135 */       headers.setContentType(MediaType.APPLICATION_JSON);
/*     */       
/* 137 */       String url = ip + "/" + api + "/" + "{deviceName}";
/* 138 */       logger.debug("url:'{}'", url);
/* 139 */       RestTemplate restTemplate = new RestTemplate((ClientHttpRequestFactory)httpComponentsClientHttpRequestFactory);
/* 140 */       HttpEntity<Integer> requestEntity = new HttpEntity(mode, (MultiValueMap)headers);
/*     */       
/* 142 */       ResponseEntity<Integer> result = restTemplate.exchange(url, method, requestEntity, Integer.class, new Object[] { deviceName });
/* 143 */       logger.debug("opendoor result.getStatusCodeValue():'{}'", Integer.valueOf(result.getStatusCodeValue()));
/* 144 */       logger.debug("openDoorConection end ,{},time diff {}", 
/*     */           
/* 146 */           Long.valueOf(System.currentTimeMillis()), 
/* 147 */           Long.valueOf(System.currentTimeMillis() - startTime));
/* 148 */       if (result.getStatusCodeValue() == 200 || result.getStatusCodeValue() == 204) {
/* 149 */         return Boolean.valueOf(true);
/*     */       }
/* 151 */       return Boolean.valueOf(false);
/* 152 */     } catch (Exception e) {
/* 153 */       logger.error("openDoorConection failed.", e);
/* 154 */       return Boolean.valueOf(false);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private Boolean openDoorPasswordConection(HttpMethod method, String api, String deviceName, int password) {
/*     */     try {
/* 161 */       logger.debug("openDoorConection with spring RestTemplate.");
/* 162 */       logger.debug("method:'{}', api:'{}', deviceName:'{}'", new Object[] { method, api, deviceName });
/* 163 */       long startTime = System.currentTimeMillis();
/*     */       
/* 165 */       HttpComponentsClientHttpRequestFactory httpComponentsClientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory((HttpClient)HttpClients.createDefault());
/*     */       
/* 167 */       String ip = getDynaConfig("RoomCardReaderApiURL").getValue();
/* 168 */       HttpHeaders headers = new HttpHeaders();
/* 169 */       headers.setContentType(MediaType.APPLICATION_JSON);
/*     */       
/* 171 */       String url = ip + "/" + api + "/" + "{deviceName}";
/* 172 */       logger.debug("url:'{}'", url);
/* 173 */       RestTemplate restTemplate = new RestTemplate((ClientHttpRequestFactory)httpComponentsClientHttpRequestFactory);
/* 174 */       HttpEntity<Integer> requestEntity = new HttpEntity(Integer.valueOf(password), (MultiValueMap)headers);
/*     */       
/* 176 */       ResponseEntity<Integer> result = restTemplate.exchange(url, method, requestEntity, Integer.class, new Object[] { deviceName });
/* 177 */       logger.debug("opendoor password result.getStatusCodeValue():'{}'", 
/* 178 */           Integer.valueOf(result.getStatusCodeValue()));
/* 179 */       logger.debug("openDoorPasswordConection end ,{},time diff {}", 
/*     */           
/* 181 */           Long.valueOf(System.currentTimeMillis()), 
/* 182 */           Long.valueOf(System.currentTimeMillis() - startTime));
/* 183 */       if (result.getStatusCodeValue() == 200 || result.getStatusCodeValue() == 204) {
/* 184 */         return Boolean.valueOf(true);
/*     */       }
/* 186 */       return Boolean.valueOf(false);
/* 187 */     } catch (Exception e) {
/* 188 */       logger.error("openDoorConection failed.", e);
/* 189 */       return Boolean.valueOf(false);
/*     */     } 
/*     */   }
/*     */   
/*     */   private DynamicConfig getDynaConfig(String configName) {
/* 194 */     IMap<DynamicConfigPk, DynamicConfig> dynamicConfigMap = HzUtils.getMap((HzDistObjEnum)HzMap.DynamicConfig);
/*     */     
/* 196 */     DynamicConfig dynamicConfig = (DynamicConfig)dynamicConfigMap.get(new DynamicConfigPk("hc_primary_group", "RoomFm", configName));
/*     */     
/* 198 */     return dynamicConfig;
/*     */   }
/*     */ }


/* Location:              D:\TC\NJ\機房門禁10.121.41.38\ngtms\ao\lib.src\fm-room-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\room\service\RoomCardReaderConnectorServiceImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */