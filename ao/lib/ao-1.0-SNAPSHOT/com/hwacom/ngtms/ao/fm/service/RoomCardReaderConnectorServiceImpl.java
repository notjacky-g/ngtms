/*     */ package com.hwacom.ngtms.ao.fm.service;
/*     */ 
/*     */ import com.hazelcast.core.IMap;
/*     */ import com.hwacom.ngtms.base.hazelcast.HzDistObjEnum;
/*     */ import com.hwacom.ngtms.base.hazelcast.HzUtils;
/*     */ import com.hwacom.ngtms.hcce.config.model.DynamicConfig;
/*     */ import com.hwacom.ngtms.hcce.config.model.DynamicConfigPk;
/*     */ import com.hwacom.ngtms.hcce.hz.HzMap;
/*     */ import com.hwacom.ngtms.room.service.RoomCardReaderConnectorService;
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
/*     */ @Profile({"ao"})
/*     */ public class RoomCardReaderConnectorServiceImpl implements RoomCardReaderConnectorService {
/*  29 */   private static final Logger logger = LoggerFactory.getLogger(RoomCardReaderConnectorServiceImpl.class);
/*     */ 
/*     */   
/*     */   public Boolean issueCard(String deviceName, String cardAba) {
/*  33 */     String api = getDynaConfig("RoomCardReaderAddDeleteCardApi").getValue();
/*  34 */     return creatContection(HttpMethod.POST, api, deviceName, cardAba);
/*     */   }
/*     */ 
/*     */   
/*     */   public Boolean deleteCard(String deviceName, String cardAba) {
/*  39 */     String api = getDynaConfig("RoomCardReaderAddDeleteCardApi").getValue();
/*  40 */     return creatContection(HttpMethod.DELETE, api, deviceName, cardAba);
/*     */   }
/*     */ 
/*     */   
/*     */   public Boolean openDoor(String deviceName, Integer mode) {
/*  45 */     String api = getDynaConfig("RoomOpenDoorApi").getValue();
/*  46 */     return openDoorConection(HttpMethod.PUT, api, deviceName, mode);
/*     */   }
/*     */ 
/*     */   
/*     */   public Boolean putOpenDoorPassword(String deviceName, Integer password) {
/*  51 */     String api = getDynaConfig("RoomOpenDoorPasswordApi").getValue();
/*  52 */     return openDoorPasswordConection(HttpMethod.PUT, api, deviceName, password.intValue());
/*     */   }
/*     */   
/*     */   public Integer getCardCount(String deviceName) {
/*  56 */     String api = getDynaConfig("RoomCardCountApi").getValue();
/*  57 */     return cardCountContection(HttpMethod.GET, api, deviceName);
/*     */   }
/*     */   
/*     */   private Boolean creatContection(HttpMethod method, String api, String deviceName, String aba) {
/*     */     try {
/*  62 */       logger.debug("creatContection add/delete card with spring RestTemplate.");
/*  63 */       logger.debug("method:'{}', api:'{}', deviceName:'{}', aba:'{}'", new Object[] { method, api, deviceName, aba });
/*     */       
/*  65 */       long startTime = System.currentTimeMillis();
/*     */       
/*  67 */       HttpComponentsClientHttpRequestFactory httpComponentsClientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory((HttpClient)HttpClients.createDefault());
/*     */       
/*  69 */       String ip = getDynaConfig("RoomCardReaderApiURL").getValue();
/*  70 */       HttpHeaders headers = new HttpHeaders();
/*  71 */       headers.setContentType(MediaType.APPLICATION_JSON);
/*     */       
/*  73 */       String url = ip + "/" + api + "/{deviceName}";
/*  74 */       logger.debug("url:'{}'", url);
/*  75 */       RestTemplate restTemplate = new RestTemplate((ClientHttpRequestFactory)httpComponentsClientHttpRequestFactory);
/*  76 */       HttpEntity<String> requestEntity = new HttpEntity(aba, (MultiValueMap)headers);
/*     */       
/*  78 */       ResponseEntity<String> result = restTemplate.exchange(url, method, requestEntity, String.class, new Object[] { deviceName });
/*  79 */       logger.debug("add/delete result.getStatusCodeValue():'{}'", Integer.valueOf(result.getStatusCodeValue()));
/*  80 */       logger.debug("cardReader createContection end ,{},time diff {}", 
/*     */           
/*  82 */           Long.valueOf(System.currentTimeMillis()), 
/*  83 */           Long.valueOf(System.currentTimeMillis() - startTime));
/*  84 */       logger.debug("cardReader card count:'{}'", getCardCount(deviceName));
/*  85 */       if (result.getStatusCodeValue() == 200 || result.getStatusCodeValue() == 204) {
/*  86 */         return Boolean.valueOf(true);
/*     */       }
/*     */       
/*  89 */       return Boolean.valueOf(false);
/*  90 */     } catch (Exception e) {
/*  91 */       logger.error("creatContectionTemplate failed.", e);
/*  92 */       return Boolean.valueOf(false);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private Integer cardCountContection(HttpMethod method, String api, String deviceName) {
/*     */     try {
/*  99 */       logger.debug("creatContection cardCount with spring RestTemplate.");
/* 100 */       logger.debug("method:'{}', api:'{}', deviceName:'{}'", new Object[] { method, api, deviceName });
/*     */       
/* 102 */       HttpComponentsClientHttpRequestFactory httpComponentsClientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory((HttpClient)HttpClients.createDefault());
/*     */       
/* 104 */       String ip = getDynaConfig("RoomCardReaderApiURL").getValue();
/* 105 */       HttpHeaders headers = new HttpHeaders();
/* 106 */       headers.setContentType(MediaType.APPLICATION_JSON);
/*     */       
/* 108 */       String url = ip + "/" + api + "/{deviceName}";
/* 109 */       logger.debug("url:'{}'", url);
/* 110 */       RestTemplate restTemplate = new RestTemplate((ClientHttpRequestFactory)httpComponentsClientHttpRequestFactory);
/* 111 */       HttpEntity<String> requestEntity = new HttpEntity((MultiValueMap)headers);
/*     */       
/* 113 */       ResponseEntity<Integer> result = restTemplate.exchange(url, method, requestEntity, Integer.class, new Object[] { deviceName });
/* 114 */       logger.debug("card count result.getStatusCodeValue():'{}'", Integer.valueOf(result.getStatusCodeValue()));
/* 115 */       if (result.getStatusCodeValue() == 200 || result.getStatusCodeValue() == 204) {
/* 116 */         return (Integer)result.getBody();
/*     */       }
/* 118 */       return null;
/* 119 */     } catch (Exception e) {
/* 120 */       logger.error("creatContectionTemplate cardCount failed.", e);
/* 121 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private Boolean openDoorConection(HttpMethod method, String api, String deviceName, Integer mode) {
/*     */     try {
/* 128 */       logger.debug("openDoorConection with spring RestTemplate.");
/* 129 */       logger.debug("method:'{}', api:'{}', deviceName:'{}'", new Object[] { method, api, deviceName });
/* 130 */       long startTime = System.currentTimeMillis();
/*     */       
/* 132 */       HttpComponentsClientHttpRequestFactory httpComponentsClientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory((HttpClient)HttpClients.createDefault());
/*     */       
/* 134 */       String ip = getDynaConfig("RoomCardReaderApiURL").getValue();
/* 135 */       HttpHeaders headers = new HttpHeaders();
/* 136 */       headers.setContentType(MediaType.APPLICATION_JSON);
/*     */       
/* 138 */       String url = ip + "/" + api + "/{deviceName}";
/* 139 */       logger.debug("url:'{}'", url);
/* 140 */       RestTemplate restTemplate = new RestTemplate((ClientHttpRequestFactory)httpComponentsClientHttpRequestFactory);
/* 141 */       HttpEntity<Integer> requestEntity = new HttpEntity(mode, (MultiValueMap)headers);
/*     */       
/* 143 */       ResponseEntity<Integer> result = restTemplate.exchange(url, method, requestEntity, Integer.class, new Object[] { deviceName });
/* 144 */       logger.debug("opendoor result.getStatusCodeValue():'{}'", Integer.valueOf(result.getStatusCodeValue()));
/* 145 */       logger.debug("openDoorConection end ,{},time diff {}", 
/*     */           
/* 147 */           Long.valueOf(System.currentTimeMillis()), 
/* 148 */           Long.valueOf(System.currentTimeMillis() - startTime));
/* 149 */       if (result.getStatusCodeValue() == 200 || result.getStatusCodeValue() == 204) {
/* 150 */         return Boolean.valueOf(true);
/*     */       }
/* 152 */       return Boolean.valueOf(false);
/* 153 */     } catch (Exception e) {
/* 154 */       logger.error("openDoorConection failed.", e);
/* 155 */       return Boolean.valueOf(false);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private Boolean openDoorPasswordConection(HttpMethod method, String api, String deviceName, int password) {
/*     */     try {
/* 162 */       logger.debug("openDoorPasswordConection with spring RestTemplate.");
/* 163 */       logger.debug("method:'{}', api:'{}', deviceName:'{}'", new Object[] { method, api, deviceName });
/* 164 */       long startTime = System.currentTimeMillis();
/*     */       
/* 166 */       HttpComponentsClientHttpRequestFactory httpComponentsClientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory((HttpClient)HttpClients.createDefault());
/*     */       
/* 168 */       String ip = getDynaConfig("RoomCardReaderApiURL").getValue();
/* 169 */       HttpHeaders headers = new HttpHeaders();
/* 170 */       headers.setContentType(MediaType.APPLICATION_JSON);
/*     */       
/* 172 */       String url = ip + "/" + api + "/{deviceName}";
/* 173 */       logger.debug("url:'{}'", url);
/* 174 */       RestTemplate restTemplate = new RestTemplate((ClientHttpRequestFactory)httpComponentsClientHttpRequestFactory);
/* 175 */       HttpEntity<Integer> requestEntity = new HttpEntity(Integer.valueOf(password), (MultiValueMap)headers);
/*     */       
/* 177 */       ResponseEntity<Integer> result = restTemplate.exchange(url, method, requestEntity, Integer.class, new Object[] { deviceName });
/* 178 */       logger.debug("opendoor password result.getStatusCodeValue():'{}'", 
/* 179 */           Integer.valueOf(result.getStatusCodeValue()));
/* 180 */       logger.debug("openDoorPasswordConection end ,{},time diff {}", 
/*     */           
/* 182 */           Long.valueOf(System.currentTimeMillis()), 
/* 183 */           Long.valueOf(System.currentTimeMillis() - startTime));
/* 184 */       if (result.getStatusCodeValue() == 200 || result.getStatusCodeValue() == 204) {
/* 185 */         return Boolean.valueOf(true);
/*     */       }
/* 187 */       return Boolean.valueOf(false);
/* 188 */     } catch (Exception e) {
/* 189 */       logger.error("openDoorConection failed.", e);
/* 190 */       return Boolean.valueOf(false);
/*     */     } 
/*     */   }
/*     */   
/*     */   private DynamicConfig getDynaConfig(String configName) {
/* 195 */     IMap<DynamicConfigPk, DynamicConfig> dynamicConfigMap = HzUtils.getMap((HzDistObjEnum)HzMap.DynamicConfig);
/*     */     
/* 197 */     DynamicConfig dynamicConfig = (DynamicConfig)dynamicConfigMap.get(new DynamicConfigPk("hc_primary_group", "RoomFm", configName));
/*     */     
/* 199 */     return dynamicConfig;
/*     */   }
/*     */ }


/* Location:              D:\TC\NJ\機房門禁10.121.41.38\ngtms\ao\lib.src\ao-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ao\fm\service\RoomCardReaderConnectorServiceImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */