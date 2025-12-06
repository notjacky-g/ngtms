/*     */ package com.hwacom.ngtms.ao.fm.service;
/*     */ 
/*     */ import com.google.gson.Gson;
/*     */ import com.hwacom.ngtms.ao.shared.dto.LifeFaceDTO;
/*     */ import com.hwacom.ngtms.ao.shared.dto.LifeFaceSessionDTO;
/*     */ import com.hwacom.ngtms.ao.shared.dto.UserParamDTO;
/*     */ import com.hwacom.ngtms.c.fm.model.DeviceTcStatus;
/*     */ import com.hwacom.ngtms.c.fm.service.OpLogger;
/*     */ import com.neovisionaries.ws.client.WebSocket;
/*     */ import com.neovisionaries.ws.client.WebSocketAdapter;
/*     */ import com.neovisionaries.ws.client.WebSocketFactory;
/*     */ import com.neovisionaries.ws.client.WebSocketFrame;
/*     */ import com.neovisionaries.ws.client.WebSocketListener;
/*     */ import java.io.BufferedOutputStream;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.BufferedWriter;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.OutputStream;
/*     */ import java.io.OutputStreamWriter;
/*     */ import java.net.HttpURLConnection;
/*     */ import java.net.URI;
/*     */ import java.net.URISyntaxException;
/*     */ import java.net.URL;
/*     */ import java.security.SecureRandom;
/*     */ import java.security.cert.X509Certificate;
/*     */ import java.util.Date;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javax.annotation.PostConstruct;
/*     */ import javax.net.ssl.HostnameVerifier;
/*     */ import javax.net.ssl.HttpsURLConnection;
/*     */ import javax.net.ssl.SSLContext;
/*     */ import javax.net.ssl.SSLSession;
/*     */ import javax.net.ssl.TrustManager;
/*     */ import javax.net.ssl.X509TrustManager;
/*     */ import org.json.JSONObject;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.core.env.Environment;
/*     */ import org.springframework.http.HttpMethod;
/*     */ import org.springframework.stereotype.Service;
/*     */ 
/*     */ 
/*     */ @Service
/*     */ public class LiveFaceService
/*     */ {
/*     */   @Autowired
/*     */   Environment env;
/*  52 */   private static final Logger logger = LoggerFactory.getLogger(LiveFaceService.class); @Autowired
/*     */   OpLogger opLogger; @Autowired
/*  54 */   LifeFaceLockCardService lifeFaceLockCardService; public Map<String, DeviceTcStatus> statusMap = new HashMap<>();
/*     */   
/*     */   @PostConstruct
/*     */   public void lifeFacePostConstruct() throws IOException {
/*  58 */     logger.debug("LiveFace Start.");
/*  59 */     getLoginInfo();
/*     */   }
/*     */   
/*     */   public void getLoginInfo() {
/*  63 */     int count = 0;
/*  64 */     String ip = (String)this.env.getProperty("liveface.ip", String.class);
/*  65 */     String login = (String)this.env.getProperty("liveface.login", String.class);
/*  66 */     String match = (String)this.env.getProperty("liveface.match", String.class);
/*  67 */     String notMatch = (String)this.env.getProperty("liveface.notMatch", String.class);
/*     */     try {
/*  69 */       (new LiveFaceService()).getClass(); HttpsURLConnection.setDefaultHostnameVerifier(new NullHostNameVerifier());
/*     */       
/*  71 */       SSLContext sc = SSLContext.getInstance("TLS");
/*  72 */       sc.init(null, this.trustAllCerts, new SecureRandom());
/*  73 */       HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
/*  74 */       String url = "https://" + ip + "/" + login;
/*  75 */       LifeFaceSessionDTO result = new LifeFaceSessionDTO();
/*  76 */       HttpURLConnection connection = null;
/*  77 */       URL ur = new URL(url);
/*  78 */       connection = (HttpURLConnection)ur.openConnection();
/*  79 */       connection.setRequestMethod(HttpMethod.POST.name());
/*  80 */       connection.setRequestProperty("Content-Type", "application/json; charset=utf-8");
/*  81 */       connection.setConnectTimeout(10000);
/*  82 */       connection.setDoInput(true);
/*  83 */       connection.setDoOutput(true);
/*  84 */       connection.connect();
/*     */       
/*  86 */       OutputStream out = null;
/*  87 */       Gson gson = new Gson();
/*  88 */       out = new BufferedOutputStream(connection.getOutputStream());
/*     */       
/*  90 */       BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out, "UTF-8"));
/*  91 */       UserParamDTO param = new UserParamDTO();
/*  92 */       String username = (String)this.env.getProperty("liveface.username", String.class);
/*  93 */       String password = (String)this.env.getProperty("liveface.password", String.class);
/*  94 */       param.setUsername(username);
/*  95 */       param.setPassword(password);
/*  96 */       writer.write(gson.toJson(param));
/*  97 */       writer.flush();
/*  98 */       writer.close();
/*  99 */       out.close();
/* 100 */       int responseCode = connection.getResponseCode();
/* 101 */       if (responseCode == 200) {
/* 102 */         logger.debug("LiveFace ResponseCode OK.");
/* 103 */         BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
/* 104 */         StringBuilder sb = new StringBuilder();
/*     */         String line;
/* 106 */         while ((line = br.readLine()) != null) {
/* 107 */           sb.append(line + "\n");
/*     */         }
/* 109 */         br.close();
/* 110 */         JSONObject loginObj = new JSONObject(sb.toString());
/* 111 */         result.setServerTime(loginObj.get("serverTime").toString());
/* 112 */         result.setSessionId(loginObj.get("sessionId").toString());
/* 113 */         result.setIp(ip);
/*     */         
/* 115 */         result.setIdentifyTrue(match);
/*     */         
/* 117 */         result.setIdentifyFalse(notMatch);
/* 118 */         logger.debug("LiveFace Start SessionId='{}'.", result.getSessionId());
/*     */         
/* 120 */         if (result != null && result.getSessionId() != null) {
/* 121 */           lifeFaceWsConnect(result);
/*     */         }
/*     */       } else {
/* 124 */         count++;
/* 125 */         connection.disconnect();
/* 126 */         logger.debug("LiveFace ResponseCode Failed count ='{}'", Integer.valueOf(count));
/* 127 */         if (count > 3) {
/* 128 */           Thread.sleep(60000L);
/* 129 */           getLoginInfo();
/*     */         } else {
/* 131 */           getLoginInfo();
/*     */         } 
/*     */       } 
/* 134 */     } catch (Exception e) {
/* 135 */       logger.error("LiveFace trustAllCerts failed", e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void lifeFaceWsConnect(LifeFaceSessionDTO dto) {
/*     */     try {
/* 141 */       SSLContext context = SSLContext.getInstance("TLS");
/* 142 */       context.init(null, this.trustAllCerts, new SecureRandom());
/*     */       
/*     */       try {
/* 145 */         URI endpointURIString = new URI("wss://" + dto.getIp() + ":443/" + dto.getIdentifyTrue() + dto.getSessionId());
/* 146 */         logger.debug("LiveFace Match = '{}'", endpointURIString.toString());
/* 147 */         WebSocketFactory ws1 = new WebSocketFactory();
/* 148 */         ws1.setVerifyHostname(false);
/* 149 */         ws1.setSSLContext(context);
/* 150 */         ws1.createSocket(endpointURIString, 10000)
/* 151 */           .addListener((WebSocketListener)new WebSocketAdapter()
/*     */             {
/*     */ 
/*     */               
/*     */               public void onConnected(WebSocket websocket, Map<String, List<String>> headers) throws Exception
/*     */               {
/* 157 */                 LiveFaceService.logger.debug("LiveFace Match Connect");
/* 158 */                 if (LiveFaceService.this.statusMap.containsKey("lifeServer")) {
/* 159 */                   DeviceTcStatus status = LiveFaceService.this.statusMap.get("lifeServer");
/* 160 */                   status.setLastUpdateStatus(status.getCommStatus());
/* 161 */                   status.setLastUpdateTime(status.getTimestamp());
/* 162 */                   status.setTimestamp(new Date());
/* 163 */                   status.setCommStatus(Integer.valueOf(0));
/* 164 */                   LiveFaceService.this.statusMap.put("lifeServer", status);
/*     */                 } else {
/* 166 */                   DeviceTcStatus newStatus = new DeviceTcStatus();
/* 167 */                   newStatus.setId("lifeServer");
/* 168 */                   newStatus.setTimestamp(new Date());
/* 169 */                   newStatus.setDeviceType("LIFEFACE");
/* 170 */                   newStatus.setCommStatus(Integer.valueOf(0));
/* 171 */                   LiveFaceService.this.statusMap.put("lifeServer", newStatus);
/*     */                 } 
/*     */               }
/*     */ 
/*     */               
/*     */               public void onTextMessage(WebSocket websocket, String message) {
/* 177 */                 JSONObject resultObj = new JSONObject(message.toString());
/* 178 */                 if (resultObj.has("objectId")) {
/* 179 */                   LifeFaceDTO dto = new LifeFaceDTO();
/* 180 */                   if (resultObj.get("cameraName") != null) {
/* 181 */                     dto.setCameraName(resultObj.get("cameraName").toString());
/*     */                   }
/* 183 */                   if (resultObj.get("personCard") != null) {
/* 184 */                     dto.setCardNumber(resultObj.get("personCard").toString());
/*     */                   }
/* 186 */                   if (resultObj.get("isMatch") != null) {
/* 187 */                     dto.setIsMatch(resultObj.get("isMatch").toString());
/*     */                   }
/* 189 */                   if (resultObj.get("date") != null) {
/* 190 */                     dto.setDate(resultObj.get("date").toString());
/*     */                   }
/* 192 */                   if (dto.getCameraName() != null) {
/* 193 */                     LiveFaceService.this.lifeFaceLockCardService.processLiveFaceMatchMessage(dto);
/*     */                   }
/*     */                 } 
/*     */               }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */               
/*     */               public void onDisconnected(WebSocket websocket, WebSocketFrame serverCloseFrame, WebSocketFrame clientCloseFrame, boolean closedByServer) throws Exception {
/* 205 */                 LiveFaceService.logger.error("LiveFace Match DisConnect");
/* 206 */                 if (LiveFaceService.this.statusMap.containsKey("lifeServer")) {
/* 207 */                   DeviceTcStatus status = LiveFaceService.this.statusMap.get("lifeServer");
/* 208 */                   status.setLastUpdateStatus(status.getCommStatus());
/* 209 */                   status.setLastUpdateTime(status.getTimestamp());
/* 210 */                   status.setTimestamp(new Date());
/* 211 */                   status.setCommStatus(Integer.valueOf(1));
/* 212 */                   LiveFaceService.this.statusMap.put("lifeServer", status);
/*     */                 } else {
/* 214 */                   DeviceTcStatus newStatus = new DeviceTcStatus();
/* 215 */                   newStatus.setId("lifeServer");
/* 216 */                   newStatus.setTimestamp(new Date());
/* 217 */                   newStatus.setDeviceType("LIFEFACE");
/* 218 */                   newStatus.setCommStatus(Integer.valueOf(1));
/* 219 */                   LiveFaceService.this.statusMap.put("lifeServer", newStatus);
/*     */                 } 
/* 221 */                 LiveFaceService.this.getLoginInfo();
/*     */               }
/* 224 */             }).connect();
/* 225 */       } catch (URISyntaxException e) {
/* 226 */         logger.error("LiveFaceClientMatch Conncet Failed.", e);
/*     */       } 
/*     */ 
/*     */       
/*     */       try {
/* 231 */         URI endpointURIString = new URI("wss://" + dto.getIp() + ":443/" + dto.getIdentifyFalse() + dto.getSessionId());
/* 232 */         logger.debug("LiveFace NotMatch = '{}'", endpointURIString.toString());
/* 233 */         WebSocketFactory ws2 = new WebSocketFactory();
/* 234 */         ws2.setVerifyHostname(false);
/* 235 */         ws2.setSSLContext(context);
/* 236 */         ws2.createSocket(endpointURIString, 10000)
/* 237 */           .addListener((WebSocketListener)new WebSocketAdapter()
/*     */             {
/*     */               
/*     */               public void onConnected(WebSocket websocket, Map<String, List<String>> headers) throws Exception
/*     */               {
/* 242 */                 LiveFaceService.logger.debug("LiveFace NotMatch Connect");
/*     */               }
/*     */ 
/*     */               
/*     */               public void onTextMessage(WebSocket websocket, String message) {
/* 247 */                 JSONObject resultObj = new JSONObject(message.toString());
/* 248 */                 if (resultObj.has("objectId")) {
/* 249 */                   LifeFaceDTO dto = new LifeFaceDTO();
/* 250 */                   if (resultObj.get("cameraName") != null) {
/* 251 */                     dto.setCameraName(resultObj.get("cameraName").toString());
/*     */                   }
/* 253 */                   if (resultObj.get("isMatch") != null) {
/* 254 */                     dto.setIsMatch(resultObj.get("isMatch").toString());
/*     */                   }
/* 256 */                   if (resultObj.get("date") != null) {
/* 257 */                     dto.setDate(resultObj.get("date").toString());
/*     */                   }
/* 259 */                   if (dto.getCameraName() != null) {
/* 260 */                     LiveFaceService.this.lifeFaceLockCardService.processLiveFaceNotMatchMessage(dto);
/*     */                   }
/*     */                 } 
/*     */               }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */               
/*     */               public void onDisconnected(WebSocket websocket, WebSocketFrame serverCloseFrame, WebSocketFrame clientCloseFrame, boolean closedByServer) throws Exception {
/* 272 */                 LiveFaceService.logger.error("LiveFace NotMatch DisConnect");
/* 273 */                 LiveFaceService.this.getLoginInfo();
/*     */               }
/* 276 */             }).connect();
/* 277 */       } catch (URISyntaxException e) {
/* 278 */         logger.error("LiveFaceClientNotMatch Conncet Failed.", e);
/*     */       } 
/* 280 */     } catch (Exception e) {
/* 281 */       logger.error("LifeFaceWsConnect Context failed.", e);
/*     */     } 
/*     */   }
/*     */   
/* 285 */   TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager()
/*     */       {
/*     */         public void checkClientTrusted(X509Certificate[] chain, String authType) {}
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         public void checkServerTrusted(X509Certificate[] chain, String authType) {}
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         public X509Certificate[] getAcceptedIssuers() {
/* 298 */           return new X509Certificate[0];
/*     */         }
/*     */       } };
/*     */ 
/*     */   
/*     */   public class NullHostNameVerifier
/*     */     implements HostnameVerifier
/*     */   {
/*     */     public boolean verify(String arg0, SSLSession arg1) {
/* 307 */       return true;
/*     */     }
/*     */   }
/*     */   
/*     */   public Map<String, DeviceTcStatus> getLifeServerStatus() {
/* 312 */     return this.statusMap;
/*     */   }
/*     */ }


/* Location:              D:\TC\NJ\機房門禁10.121.41.38\ngtms\ao\lib.src\ao-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ao\fm\service\LiveFaceService.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */