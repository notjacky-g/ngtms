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
/*     */ import java.net.URL;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Map.Entry;
/*     */ import java.util.Optional;
/*     */ import java.util.Set;
/*     */ import java.util.function.Consumer;
/*     */ import org.apache.http.Header;
/*     */ import org.apache.http.HttpEntity;
/*     */ import org.apache.http.NameValuePair;
/*     */ import org.apache.http.ParseException;
/*     */ import org.apache.http.StatusLine;
/*     */ import org.apache.http.client.config.RequestConfig;
/*     */ import org.apache.http.client.config.RequestConfig.Builder;
/*     */ import org.apache.http.client.entity.UrlEncodedFormEntity;
/*     */ import org.apache.http.client.methods.CloseableHttpResponse;
/*     */ import org.apache.http.client.methods.HttpGet;
/*     */ import org.apache.http.client.methods.HttpPost;
/*     */ import org.apache.http.client.utils.URIBuilder;
/*     */ import org.apache.http.entity.StringEntity;
/*     */ import org.apache.http.impl.client.CloseableHttpClient;
/*     */ import org.apache.http.message.BasicNameValuePair;
/*     */ import org.apache.http.util.EntityUtils;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.beans.factory.annotation.Qualifier;
/*     */ 
/*     */ 
/*     */ public abstract class BaseHttpClient
/*     */ {
/*  47 */   private static final Logger logger = LoggerFactory.getLogger(BaseHttpClient.class);
/*     */   
/*     */ 
/*     */ 
/*     */   @Autowired
/*     */   @Qualifier("requestConfig")
/*     */   private RequestConfig requestConfig;
/*     */   
/*     */ 
/*     */   @Autowired
/*     */   @Qualifier("proxyRequestConfig")
/*     */   private RequestConfig proxyRequestConfig;
/*     */   
/*     */ 
/*     */ 
/*     */   public String doGet(String url)
/*     */     throws Exception
/*     */   {
/*  65 */     CloseableHttpResponse response = getHttpClient().execute(getHttpGet(url));Throwable localThrowable4 = null;
/*     */     try { String str;
/*  67 */       if (response.getStatusLine().getStatusCode() == 200)
/*     */       {
/*  69 */         return EntityUtils.toString(response.getEntity(), "UTF-8");
/*     */       }
/*  71 */       return null;
/*     */     }
/*     */     catch (Throwable localThrowable5)
/*     */     {
/*  65 */       localThrowable4 = localThrowable5;throw localThrowable5;
/*     */ 
/*     */ 
/*     */     }
/*     */     finally
/*     */     {
/*     */ 
/*  72 */       if (response != null) if (localThrowable4 != null) try { response.close(); } catch (Throwable localThrowable3) { localThrowable4.addSuppressed(localThrowable3); } else response.close();
/*     */     }
/*     */   }
/*     */   
/*     */   public void doGetReturnContent(String url, Map<String, Object> map, Consumer<CloseableHttpResponse> consumer) throws Exception
/*     */   {
/*  78 */     HttpGet httpGet = getHttpGet(url);
/*  79 */     Iterator localIterator; if ((map != null) && (map.size() > 0)) {
/*  80 */       for (localIterator = map.entrySet().iterator(); localIterator.hasNext();) { en = (Map.Entry)localIterator.next();
/*  81 */         httpGet.addHeader((String)en.getKey(), en.getValue().toString());
/*     */       }
/*     */     }
/*     */     
/*  85 */     CloseableHttpResponse response = getHttpClient().execute(httpGet);Map.Entry<String, Object> en = null;
/*  86 */     try { consumer.accept(response);
/*     */     }
/*     */     catch (Throwable localThrowable1)
/*     */     {
/*  85 */       en = localThrowable1;throw localThrowable1;
/*     */     } finally {
/*  87 */       if (response != null) if (en != null) try { response.close(); } catch (Throwable localThrowable2) { en.addSuppressed(localThrowable2); } else response.close();
/*     */     }
/*     */   }
/*     */   
/*     */   private HttpGet getHttpGet(String url) {
/*  92 */     HttpGet httpGet = new HttpGet(url);
/*     */     
/*     */ 
/*  95 */     httpGet.setConfig(getRequestConfig(url));
/*  96 */     return httpGet;
/*     */   }
/*     */   
/*     */   private RequestConfig getRequestConfig(String url) {
/* 100 */     return UrlUtils.isInternalIp(url) ? this.requestConfig : this.proxyRequestConfig;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Optional<String> doHttpGet(String url, Map<String, String> headers)
/*     */     throws Exception
/*     */   {
/* 112 */     return doHttpGet(url, headers, true);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Optional<String> doHttpGet(String url, Map<String, String> headers, boolean timeoutConfig)
/*     */     throws Exception
/*     */   {
/* 125 */     HttpGet request = new HttpGet(url);
/* 126 */     if (timeoutConfig) {
/* 127 */       request.setConfig(getRequestConfig(url));
/*     */     }
/* 129 */     for (Map.Entry<String, String> entry : headers.entrySet()) {
/* 130 */       request.addHeader((String)entry.getKey(), (String)entry.getValue());
/*     */     }
/* 132 */     logger.debug("Before send request to '{}'", url);
/* 133 */     CloseableHttpResponse response = getHttpClient().execute(request);
/* 134 */     String headerValue = null;
/*     */     try {
/* 136 */       validateResponse(response);
/*     */     } catch (UnauthorizedException e) {
/* 138 */       headerValue = e.getWwwAuthenticateHeaderValue();
/*     */     }
/* 140 */     response.close();
/* 141 */     return Optional.ofNullable(headerValue);
/*     */   }
/*     */   
/*     */   private void validateResponse(CloseableHttpResponse response) throws UnauthorizedException {
/* 145 */     int responseCode = response.getStatusLine().getStatusCode();
/* 146 */     logger.debug("sendHttpResquest.getResponseCode: '{}'", Integer.valueOf(responseCode));
/* 147 */     if (responseCode == 401) {
/* 148 */       String headerName = "www-authenticate";
/* 149 */       Header[] headers = response.getHeaders(headerName);
/* 150 */       String headerValue = null;
/* 151 */       if ((headers != null) && (headers.length != 0)) {
/* 152 */         headerValue = headers[0].getValue();
/*     */       }
/* 154 */       logger.debug("Http resquest resend, header: '{}', value: '{}'.", headerName, headerValue);
/* 155 */       throw new UnauthorizedException(headerValue);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String doGet(String url, Map<String, Object> map)
/*     */     throws Exception
/*     */   {
/* 167 */     URIBuilder uriBuilder = new URIBuilder(url);
/*     */     
/* 169 */     if (map != null)
/*     */     {
/* 171 */       for (Map.Entry<String, Object> entry : map.entrySet()) {
/* 172 */         uriBuilder.setParameter((String)entry.getKey(), entry.getValue().toString());
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 177 */     return doGet(uriBuilder.build().toString());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public HttpResult doGetHttpResult(String url, Map<String, String> headerMap, Map<String, String> parameterMap, Optional<Integer> optConnectTimeoutMilliseconds)
/*     */     throws UnauthorizedException, Exception
/*     */   {
/* 186 */     URI uri = newUri(url, parameterMap);
/* 187 */     HttpGet httpGet = newHttpGet(uri, headerMap);
/* 188 */     if (optConnectTimeoutMilliseconds.isPresent()) {
/* 189 */       httpGet.setConfig(
/* 190 */         RequestConfig.custom().setConnectTimeout(((Integer)optConnectTimeoutMilliseconds.get()).intValue()).build());
/*     */     } else {
/* 192 */       httpGet.setConfig(getRequestConfig(url));
/*     */     }
/* 194 */     CloseableHttpResponse response = getHttpClient().execute(httpGet);
/*     */     try {
/* 196 */       return toHttpResult(response);
/*     */     }
/*     */     finally {
/* 199 */       EntityUtils.consume(response.getEntity());
/* 200 */       response.close();
/*     */     }
/*     */   }
/*     */   
/*     */   private URI newUri(String url, Map<String, String> parameterMap) throws URISyntaxException {
/* 205 */     URIBuilder uriBuilder = new URIBuilder(url);
/* 206 */     if (parameterMap != null) {
/* 207 */       for (Map.Entry<String, String> entry : parameterMap.entrySet()) {
/* 208 */         uriBuilder.addParameter((String)entry.getKey(), (String)entry.getValue());
/*     */       }
/*     */     }
/* 211 */     return uriBuilder.build();
/*     */   }
/*     */   
/*     */   private HttpGet newHttpGet(URI uri, Map<String, String> headerMap) {
/* 215 */     HttpGet request = new HttpGet(uri);
/* 216 */     for (Map.Entry<String, String> entry : headerMap.entrySet()) {
/* 217 */       request.addHeader((String)entry.getKey(), (String)entry.getValue());
/*     */     }
/* 219 */     return request;
/*     */   }
/*     */   
/*     */   private HttpResult toHttpResult(CloseableHttpResponse response) throws UnauthorizedException, ParseException, IOException
/*     */   {
/* 224 */     validateResponse(response);
/*     */     
/*     */ 
/* 227 */     return new HttpResult(Integer.valueOf(response.getStatusLine().getStatusCode()), EntityUtils.toString(response.getEntity(), "UTF-8"));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public HttpResult doGetHttpResult(String url, Map<String, Object> map)
/*     */     throws UnauthorizedException, Exception
/*     */   {
/* 240 */     return doGetHttpResult(url, map, Optional.empty());
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
/*     */   public HttpResult doGetHttpResult(String url, Map<String, Object> map, Optional<Integer> optConnectTimeoutMilliseconds)
/*     */     throws UnauthorizedException, Exception
/*     */   {
/* 254 */     Map<String, String> headerMap = new HashMap();
/* 255 */     for (Map.Entry<String, Object> entry : map.entrySet()) {
/* 256 */       headerMap.put(entry.getKey(), entry.getValue().toString());
/*     */     }
/* 258 */     return doGetHttpResult(url, headerMap, Collections.emptyMap(), optConnectTimeoutMilliseconds);
/*     */   }
/*     */   
/*     */   public void downloadFile(String url, Map<String, String> requestHeaders, String filePath) throws Exception
/*     */   {
/* 263 */     HttpGet httpGet = new HttpGet(url);
/* 264 */     httpGet.setConfig(getRequestConfig(url));
/* 265 */     for (Iterator localIterator = requestHeaders.entrySet().iterator(); localIterator.hasNext();) { entry = (Map.Entry)localIterator.next();
/* 266 */       httpGet.addHeader((String)entry.getKey(), (String)entry.getValue());
/*     */     }
/* 268 */     CloseableHttpResponse response = getHttpClient().execute(httpGet);Map.Entry<String, String> entry = null;
/* 269 */     try { int statusCode = response.getStatusLine().getStatusCode();
/* 270 */       logger.debug("Download file status code: '{}'.", Integer.valueOf(statusCode));
/* 271 */       if (statusCode == 200) {
/* 272 */         writeToFile(response.getEntity().getContent(), filePath);
/*     */       }
/*     */     }
/*     */     catch (Throwable localThrowable1)
/*     */     {
/* 268 */       entry = localThrowable1;throw localThrowable1;
/*     */ 
/*     */     }
/*     */     finally
/*     */     {
/*     */ 
/* 274 */       if (response != null) if (entry != null) try { response.close(); } catch (Throwable localThrowable2) { entry.addSuppressed(localThrowable2); } else response.close();
/*     */     }
/*     */   }
/*     */   
/* 278 */   private void writeToFile(InputStream source, String target) throws Exception { BufferedInputStream input = new BufferedInputStream(source);Throwable localThrowable6 = null;
/* 279 */     try { BufferedOutputStream output = new BufferedOutputStream(new FileOutputStream(new File(target)));Throwable localThrowable7 = null;
/*     */       try {
/*     */         int nextByte;
/* 282 */         while ((nextByte = input.read()) != -1) {
/* 283 */           output.write(nextByte);
/*     */         }
/*     */       }
/*     */       catch (Throwable localThrowable1)
/*     */       {
/* 278 */         localThrowable7 = localThrowable1;throw localThrowable1; } finally {} } catch (Throwable localThrowable4) { localThrowable6 = localThrowable4;throw localThrowable4;
/*     */ 
/*     */ 
/*     */     }
/*     */     finally
/*     */     {
/*     */ 
/* 285 */       if (input != null) { if (localThrowable6 != null) try { input.close(); } catch (Throwable localThrowable5) { localThrowable6.addSuppressed(localThrowable5); } else { input.close();
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public HttpResult doLineNotifyPost(LineNotifyParameter p)
/*     */     throws Exception
/*     */   {
/* 296 */     HttpPost request = new HttpPost("https://notify-api.line.me/api/notify");
/* 297 */     request.setConfig(this.proxyRequestConfig);
/* 298 */     request.addHeader("Authorization", "Bearer " + p.getToken());
/* 299 */     logger.debug("Line Notify Http POST request. token: '{}'", p.getToken());
/* 300 */     List<BasicNameValuePair> list = new ArrayList();
/* 301 */     list.add(new BasicNameValuePair("message", p.getMessage()));
/* 302 */     if ((p.getImageThumbnail() != null) && (p.getImageFullsize() != null)) {
/* 303 */       list.add(new BasicNameValuePair("imageThumbnail", p.getImageThumbnail().toString()));
/* 304 */       list.add(new BasicNameValuePair("imageFullsize", p.getImageFullsize().toString()));
/*     */     }
/* 306 */     list.add(new BasicNameValuePair("imageFile", p.getImageFile()));
/* 307 */     if ((p.getStickerPackageId() != null) && (p.getStickerId() != null)) {
/* 308 */       list.add(new BasicNameValuePair("stickerPackageId", p.getStickerPackageId().toString()));
/* 309 */       list.add(new BasicNameValuePair("stickerId", p.getStickerId().toString()));
/*     */     }
/* 311 */     list.add(new BasicNameValuePair("notificationDisabled", 
/* 312 */       String.valueOf(p.isNotificationDisabled())));
/* 313 */     request.setEntity(new UrlEncodedFormEntity(list, "UTF-8"));
/* 314 */     CloseableHttpResponse response = getHttpClient().execute(request);
/* 315 */     logger.debug("Line Notify Http POST request. Parameter: '{}'", p.toString());
/*     */     
/*     */ 
/* 318 */     return new HttpResult(Integer.valueOf(response.getStatusLine().getStatusCode()), EntityUtils.toString(response.getEntity(), "UTF-8"));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public HttpResult doPost(String url, Map<String, Object> map)
/*     */     throws Exception
/*     */   {
/* 330 */     return doPost(url, Collections.emptyMap(), map);
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
/*     */   public HttpResult doPost(String url, Map<String, String> headers, Map<String, Object> map)
/*     */     throws Exception
/*     */   {
/* 344 */     HttpEntity entity = null;
/*     */     
/* 346 */     if (map != null) {
/* 347 */       List<NameValuePair> list = new ArrayList();
/* 348 */       for (Map.Entry<String, Object> entry : map.entrySet()) {
/* 349 */         list.add(new BasicNameValuePair((String)entry.getKey(), entry.getValue().toString()));
/*     */       }
/*     */       
/* 352 */       entity = new UrlEncodedFormEntity(list, "UTF-8");
/*     */     }
/* 354 */     return doPost(url, headers, entity);
/*     */   }
/*     */   
/*     */   public HttpResult doPost(String url, Map<String, String> headers, HttpEntity entity)
/*     */     throws Exception
/*     */   {
/* 360 */     HttpPost httpPost = new HttpPost(url);
/*     */     
/* 362 */     httpPost.setConfig(getRequestConfig(url));
/*     */     
/* 364 */     for (Map.Entry<String, String> entry : headers.entrySet()) {
/* 365 */       httpPost.addHeader((String)entry.getKey(), (String)entry.getValue());
/*     */     }
/*     */     
/* 368 */     if (entity != null) {
/* 369 */       httpPost.setEntity(entity);
/*     */     }
/*     */     
/*     */ 
/* 373 */     CloseableHttpResponse response = getHttpClient().execute(httpPost);
/*     */     
/*     */ 
/* 376 */     return new HttpResult(Integer.valueOf(response.getStatusLine().getStatusCode()), EntityUtils.toString(response.getEntity(), "UTF-8"));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public HttpResult doPost(String url)
/*     */     throws Exception
/*     */   {
/* 387 */     return doPost(url, null);
/*     */   }
/*     */   
/*     */   public int postJson(String url, String json) throws Exception {
/* 391 */     HttpPost httpPost = new HttpPost(url);
/* 392 */     httpPost.setConfig(getRequestConfig(url));
/* 393 */     httpPost.setEntity(new StringEntity(json));
/* 394 */     httpPost.setHeader("Accept", "application/json");
/* 395 */     httpPost.setHeader("Content-type", "application/json");
/* 396 */     CloseableHttpResponse response = getHttpClient().execute(httpPost);Throwable localThrowable3 = null;
/* 397 */     try { return response.getStatusLine().getStatusCode();
/*     */     }
/*     */     catch (Throwable localThrowable4)
/*     */     {
/* 396 */       localThrowable3 = localThrowable4;throw localThrowable4;
/*     */     } finally {
/* 398 */       if (response != null) { if (localThrowable3 != null) try { response.close(); } catch (Throwable localThrowable2) { localThrowable3.addSuppressed(localThrowable2); } else { response.close();
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   protected abstract CloseableHttpClient getHttpClient();
/*     */   
/*     */   public static class HttpResult
/*     */   {
/*     */     private Integer code;
/*     */     private String body;
/*     */     
/*     */     public HttpResult() {}
/*     */     
/*     */     public HttpResult(Integer code, String body)
/*     */     {
/* 415 */       this.code = code;
/* 416 */       this.body = body;
/*     */     }
/*     */     
/*     */     public Integer getCode() {
/* 420 */       return this.code;
/*     */     }
/*     */     
/*     */     public void setCode(Integer code) {
/* 424 */       this.code = code;
/*     */     }
/*     */     
/*     */     public String getBody() {
/* 428 */       return this.body;
/*     */     }
/*     */     
/*     */     public void setBody(String body) {
/* 432 */       this.body = body;
/*     */     }
/*     */     
/*     */     public String toString()
/*     */     {
/* 437 */       return "HttpResult [code=" + this.code + ", body=" + this.body + "]";
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\base-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\base\httpclient\BaseHttpClient.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */