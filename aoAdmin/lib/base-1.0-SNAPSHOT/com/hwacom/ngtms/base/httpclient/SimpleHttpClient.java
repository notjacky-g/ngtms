/*     */ package com.hwacom.ngtms.base.httpclient;
/*     */ 
/*     */ import com.hwacom.ngtms.base.util.UrlUtils;
/*     */ import java.io.BufferedInputStream;
/*     */ import java.io.BufferedOutputStream;
/*     */ import java.io.File;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.net.URI;
/*     */ import java.net.URISyntaxException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Optional;
/*     */ import java.util.concurrent.Executors;
/*     */ import java.util.concurrent.ScheduledExecutorService;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import javax.annotation.PostConstruct;
/*     */ import javax.annotation.PreDestroy;
/*     */ import org.apache.http.Header;
/*     */ import org.apache.http.HttpEntity;
/*     */ import org.apache.http.NameValuePair;
/*     */ import org.apache.http.ParseException;
/*     */ import org.apache.http.client.config.RequestConfig;
/*     */ import org.apache.http.client.entity.UrlEncodedFormEntity;
/*     */ import org.apache.http.client.methods.CloseableHttpResponse;
/*     */ import org.apache.http.client.methods.HttpGet;
/*     */ import org.apache.http.client.methods.HttpPost;
/*     */ import org.apache.http.client.methods.HttpUriRequest;
/*     */ import org.apache.http.client.utils.URIBuilder;
/*     */ import org.apache.http.entity.StringEntity;
/*     */ import org.apache.http.impl.client.CloseableHttpClient;
/*     */ import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
/*     */ import org.apache.http.message.BasicNameValuePair;
/*     */ import org.apache.http.util.EntityUtils;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.beans.factory.annotation.Qualifier;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SimpleHttpClient
/*     */ {
/*  51 */   private Logger logger = LoggerFactory.getLogger(SimpleHttpClient.class);
/*     */   
/*     */   @Autowired
/*     */   private CloseableHttpClient httpClient;
/*     */   
/*     */   @Autowired
/*     */   @Qualifier("requestConfig")
/*     */   private RequestConfig requestConfig;
/*     */   
/*     */   @Autowired
/*     */   @Qualifier("proxyRequestConfig")
/*     */   private RequestConfig proxyRequestConfig;
/*     */   @Autowired
/*     */   private PoolingHttpClientConnectionManager httpClientConnectionManager;
/*     */   private ScheduledExecutorService scheduledExecutorService;
/*     */   
/*     */   @PostConstruct
/*     */   public void init() {
/*  69 */     this.scheduledExecutorService = Executors.newScheduledThreadPool(1);
/*  70 */     this.scheduledExecutorService.scheduleAtFixedRate(() -> {
/*     */           try {
/*     */             this.httpClientConnectionManager.closeIdleConnections(30L, TimeUnit.SECONDS);
/*     */             
/*     */             this.logger.debug("Close idle connections.");
/*     */             this.httpClientConnectionManager.closeExpiredConnections();
/*     */             this.logger.debug("Close expired connections.");
/*  77 */           } catch (Exception e) {
/*     */             this.logger.debug("Close inactive connection failed!", e);
/*     */           } 
/*     */         }10L, 10L, TimeUnit.MINUTES);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @PreDestroy
/*     */   public void preDestroy() {
/*  88 */     this.scheduledExecutorService.shutdownNow();
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
/*     */   public String doGet(String url) throws Exception {
/* 100 */     HttpGet httpGet = new HttpGet(url);
/*     */ 
/*     */     
/* 103 */     httpGet.setConfig(getRequestConfig(url));
/*     */ 
/*     */     
/* 106 */     try (CloseableHttpResponse response = this.httpClient.execute((HttpUriRequest)httpGet)) {
/*     */       
/* 108 */       if (response.getStatusLine().getStatusCode() == 200)
/*     */       {
/* 110 */         return EntityUtils.toString(response.getEntity(), "UTF-8");
/*     */       }
/* 112 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   private RequestConfig getRequestConfig(String url) {
/* 117 */     return UrlUtils.isInternalIp(url) ? this.requestConfig : this.proxyRequestConfig;
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
/*     */   public Optional<String> doHttpGet(String url, Map<String, String> headers) throws Exception {
/* 129 */     return doHttpGet(url, headers, true);
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
/*     */   public Optional<String> doHttpGet(String url, Map<String, String> headers, boolean timeoutConfig) throws Exception {
/* 142 */     HttpGet request = new HttpGet(url);
/* 143 */     if (timeoutConfig) {
/* 144 */       request.setConfig(getRequestConfig(url));
/*     */     }
/* 146 */     for (Map.Entry<String, String> entry : headers.entrySet()) {
/* 147 */       request.addHeader(entry.getKey(), entry.getValue());
/*     */     }
/* 149 */     this.logger.debug("Before send request to '{}'", url);
/* 150 */     CloseableHttpResponse response = this.httpClient.execute((HttpUriRequest)request);
/* 151 */     String headerValue = null;
/*     */     try {
/* 153 */       validateResponse(response);
/* 154 */     } catch (UnauthorizedException e) {
/* 155 */       headerValue = e.getWwwAuthenticateHeaderValue();
/*     */     } 
/* 157 */     response.close();
/* 158 */     return Optional.ofNullable(headerValue);
/*     */   }
/*     */   
/*     */   private void validateResponse(CloseableHttpResponse response) throws UnauthorizedException {
/* 162 */     int responseCode = response.getStatusLine().getStatusCode();
/* 163 */     this.logger.debug("sendHttpResquest.getResponseCode: '{}'", Integer.valueOf(responseCode));
/* 164 */     if (responseCode == 401) {
/* 165 */       String headerName = "www-authenticate";
/* 166 */       Header[] headers = response.getHeaders(headerName);
/* 167 */       String headerValue = null;
/* 168 */       if (headers != null && headers.length != 0) {
/* 169 */         headerValue = headers[0].getValue();
/*     */       }
/* 171 */       this.logger.debug("Http resquest resend, header: '{}', value: '{}'.", headerName, headerValue);
/* 172 */       throw new UnauthorizedException(headerValue);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String doGet(String url, Map<String, Object> map) throws Exception {
/* 184 */     URIBuilder uriBuilder = new URIBuilder(url);
/*     */     
/* 186 */     if (map != null)
/*     */     {
/* 188 */       for (Map.Entry<String, Object> entry : map.entrySet()) {
/* 189 */         uriBuilder.setParameter(entry.getKey(), entry.getValue().toString());
/*     */       }
/*     */     }
/*     */ 
/*     */     
/* 194 */     return doGet(uriBuilder.build().toString());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public HttpResult doGetHttpResult(String url, Map<String, String> headerMap, Map<String, String> parameterMap, Optional<Integer> optConnectTimeoutMilliseconds) throws UnauthorizedException, Exception {
/* 203 */     URI uri = newUri(url, parameterMap);
/* 204 */     HttpGet httpGet = newHttpGet(uri, headerMap);
/* 205 */     if (optConnectTimeoutMilliseconds.isPresent()) {
/* 206 */       httpGet.setConfig(
/* 207 */           RequestConfig.custom().setConnectTimeout(((Integer)optConnectTimeoutMilliseconds.get()).intValue()).build());
/*     */     } else {
/* 209 */       httpGet.setConfig(getRequestConfig(url));
/*     */     } 
/* 211 */     CloseableHttpResponse response = this.httpClient.execute((HttpUriRequest)httpGet);
/*     */     try {
/* 213 */       return toHttpResult(response);
/*     */     } finally {
/* 215 */       response.close();
/*     */     } 
/*     */   }
/*     */   
/*     */   private URI newUri(String url, Map<String, String> parameterMap) throws URISyntaxException {
/* 220 */     URIBuilder uriBuilder = new URIBuilder(url);
/* 221 */     if (parameterMap != null) {
/* 222 */       for (Map.Entry<String, String> entry : parameterMap.entrySet()) {
/* 223 */         uriBuilder.addParameter(entry.getKey(), entry.getValue());
/*     */       }
/*     */     }
/* 226 */     return uriBuilder.build();
/*     */   }
/*     */   
/*     */   private HttpGet newHttpGet(URI uri, Map<String, String> headerMap) {
/* 230 */     HttpGet request = new HttpGet(uri);
/* 231 */     for (Map.Entry<String, String> entry : headerMap.entrySet()) {
/* 232 */       request.addHeader(entry.getKey(), entry.getValue());
/*     */     }
/* 234 */     return request;
/*     */   }
/*     */ 
/*     */   
/*     */   private HttpResult toHttpResult(CloseableHttpResponse response) throws UnauthorizedException, ParseException, IOException {
/* 239 */     validateResponse(response);
/* 240 */     return new HttpResult(
/* 241 */         Integer.valueOf(response.getStatusLine().getStatusCode()), 
/* 242 */         EntityUtils.toString(response.getEntity(), "UTF-8"));
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
/*     */   public HttpResult doGetHttpResult(String url, Map<String, Object> map) throws UnauthorizedException, Exception {
/* 255 */     Map<String, String> headerMap = new HashMap<>();
/* 256 */     for (Map.Entry<String, Object> entry : map.entrySet()) {
/* 257 */       headerMap.put(entry.getKey(), entry.getValue().toString());
/*     */     }
/* 259 */     return doGetHttpResult(url, headerMap, Collections.emptyMap(), Optional.empty());
/*     */   }
/*     */ 
/*     */   
/*     */   public void downloadFile(String url, Map<String, String> requestHeaders, String filePath) throws Exception {
/* 264 */     HttpGet httpGet = new HttpGet(url);
/* 265 */     httpGet.setConfig(getRequestConfig(url));
/* 266 */     for (Map.Entry<String, String> entry : requestHeaders.entrySet()) {
/* 267 */       httpGet.addHeader(entry.getKey(), entry.getValue());
/*     */     }
/* 269 */     try (CloseableHttpResponse response = this.httpClient.execute((HttpUriRequest)httpGet)) {
/* 270 */       int statusCode = response.getStatusLine().getStatusCode();
/* 271 */       this.logger.debug("Download file status code: '{}'.", Integer.valueOf(statusCode));
/* 272 */       if (statusCode == 200) {
/* 273 */         writeToFile(response.getEntity().getContent(), filePath);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private void writeToFile(InputStream source, String target) throws Exception {
/* 279 */     try(BufferedInputStream input = new BufferedInputStream(source); 
/* 280 */         BufferedOutputStream output = new BufferedOutputStream(new FileOutputStream(new File(target)))) {
/*     */       int nextByte;
/*     */       
/* 283 */       while ((nextByte = input.read()) != -1) {
/* 284 */         output.write(nextByte);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public HttpResult doLineNotifyPost(LineNotifyParameter p) throws Exception {
/* 297 */     HttpPost request = new HttpPost("https://notify-api.line.me/api/notify");
/* 298 */     request.setConfig(this.proxyRequestConfig);
/* 299 */     request.addHeader("Authorization", "Bearer " + p.getToken());
/* 300 */     this.logger.debug("Line Notify Http POST request. token: '{}'", p.getToken());
/* 301 */     List<BasicNameValuePair> list = new ArrayList<>();
/* 302 */     list.add(new BasicNameValuePair("message", p.getMessage()));
/* 303 */     if (p.getImageThumbnail() != null && p.getImageFullsize() != null) {
/* 304 */       list.add(new BasicNameValuePair("imageThumbnail", p.getImageThumbnail().toString()));
/* 305 */       list.add(new BasicNameValuePair("imageFullsize", p.getImageFullsize().toString()));
/*     */     } 
/* 307 */     list.add(new BasicNameValuePair("imageFile", p.getImageFile()));
/* 308 */     if (p.getStickerPackageId() != null && p.getStickerId() != null) {
/* 309 */       list.add(new BasicNameValuePair("stickerPackageId", p.getStickerPackageId().toString()));
/* 310 */       list.add(new BasicNameValuePair("stickerId", p.getStickerId().toString()));
/*     */     } 
/* 312 */     list.add(new BasicNameValuePair("notificationDisabled", 
/* 313 */           String.valueOf(p.isNotificationDisabled())));
/* 314 */     request.setEntity((HttpEntity)new UrlEncodedFormEntity(list, "UTF-8"));
/* 315 */     CloseableHttpResponse response = this.httpClient.execute((HttpUriRequest)request);
/* 316 */     this.logger.debug("Line Notify Http POST request. Parameter: '{}'", p.toString());
/* 317 */     return new HttpResult(
/* 318 */         Integer.valueOf(response.getStatusLine().getStatusCode()), 
/* 319 */         EntityUtils.toString(response.getEntity(), "UTF-8"));
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
/*     */   public HttpResult doPost(String url, Map<String, Object> map) throws Exception {
/* 331 */     return doPost(url, Collections.emptyMap(), map);
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
/*     */ 
/*     */   
/*     */   public HttpResult doPost(String url, Map<String, String> headers, Map<String, Object> map) throws Exception {
/* 346 */     HttpPost httpPost = new HttpPost(url);
/*     */     
/* 348 */     httpPost.setConfig(getRequestConfig(url));
/*     */     
/* 350 */     for (Map.Entry<String, String> entry : headers.entrySet()) {
/* 351 */       httpPost.addHeader(entry.getKey(), entry.getValue());
/*     */     }
/*     */ 
/*     */     
/* 355 */     if (map != null) {
/* 356 */       List<NameValuePair> list = new ArrayList<>();
/* 357 */       for (Map.Entry<String, Object> entry : map.entrySet()) {
/* 358 */         list.add(new BasicNameValuePair(entry.getKey(), entry.getValue().toString()));
/*     */       }
/*     */       
/* 361 */       UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(list, "UTF-8");
/*     */ 
/*     */       
/* 364 */       httpPost.setEntity((HttpEntity)urlEncodedFormEntity);
/*     */     } 
/*     */ 
/*     */     
/* 368 */     CloseableHttpResponse response = this.httpClient.execute((HttpUriRequest)httpPost);
/* 369 */     return new HttpResult(
/* 370 */         Integer.valueOf(response.getStatusLine().getStatusCode()), 
/* 371 */         EntityUtils.toString(response.getEntity(), "UTF-8"));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public HttpResult doPost(String url) throws Exception {
/* 382 */     return doPost(url, null);
/*     */   }
/*     */   
/*     */   public int postJson(String url, String json) throws Exception {
/* 386 */     HttpPost httpPost = new HttpPost(url);
/* 387 */     httpPost.setConfig(getRequestConfig(url));
/* 388 */     httpPost.setEntity((HttpEntity)new StringEntity(json));
/* 389 */     httpPost.setHeader("Accept", "application/json");
/* 390 */     httpPost.setHeader("Content-type", "application/json");
/* 391 */     try (CloseableHttpResponse response = this.httpClient.execute((HttpUriRequest)httpPost)) {
/* 392 */       return response.getStatusLine().getStatusCode();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static class HttpResult
/*     */   {
/*     */     private Integer code;
/*     */     
/*     */     private String body;
/*     */ 
/*     */     
/*     */     public HttpResult() {}
/*     */ 
/*     */     
/*     */     public HttpResult(Integer code, String body) {
/* 408 */       this.code = code;
/* 409 */       this.body = body;
/*     */     }
/*     */     
/*     */     public Integer getCode() {
/* 413 */       return this.code;
/*     */     }
/*     */     
/*     */     public void setCode(Integer code) {
/* 417 */       this.code = code;
/*     */     }
/*     */     
/*     */     public String getBody() {
/* 421 */       return this.body;
/*     */     }
/*     */     
/*     */     public void setBody(String body) {
/* 425 */       this.body = body;
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 430 */       return "HttpResult [code=" + this.code + ", body=" + this.body + "]";
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\lib\base-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\base\httpclient\SimpleHttpClient.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */