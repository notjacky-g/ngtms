/*     */ package com.hwacom.ngtms.base.sms;
/*     */ 
/*     */ import com.google.common.util.concurrent.ThreadFactoryBuilder;
/*     */ import com.google.gson.Gson;
/*     */ import com.google.gson.GsonBuilder;
/*     */ import com.google.gson.JsonDeserializationContext;
/*     */ import com.google.gson.JsonElement;
/*     */ import com.google.gson.JsonParseException;
/*     */ import com.google.gson.JsonPrimitive;
/*     */ import com.google.gson.JsonSerializationContext;
/*     */ import com.google.gson.reflect.TypeToken;
/*     */ import com.hwacom.ngtms.base.sms.shared.QuerySmsCallback;
/*     */ import com.hwacom.ngtms.base.sms.shared.SendSmsCallback;
/*     */ import com.hwacom.ngtms.base.sms.shared.SmsQueryResponse;
/*     */ import com.hwacom.ngtms.base.sms.shared.SmsSendRequest;
/*     */ import com.hwacom.ngtms.base.sms.shared.SmsSendResponse;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.OutputStream;
/*     */ import java.lang.reflect.Type;
/*     */ import java.net.HttpURLConnection;
/*     */ import java.net.URL;
/*     */ import java.text.ParseException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Date;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.ExecutorService;
/*     */ import java.util.concurrent.Executors;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import org.apache.http.HttpEntity;
/*     */ import org.apache.http.NameValuePair;
/*     */ import org.apache.http.client.config.RequestConfig;
/*     */ import org.apache.http.client.entity.UrlEncodedFormEntity;
/*     */ import org.apache.http.client.methods.CloseableHttpResponse;
/*     */ import org.apache.http.client.methods.HttpPost;
/*     */ import org.apache.http.client.methods.HttpUriRequest;
/*     */ import org.apache.http.impl.client.CloseableHttpClient;
/*     */ import org.apache.http.message.BasicNameValuePair;
/*     */ import org.apache.http.util.EntityUtils;
/*     */ import org.joda.time.format.DateTimeFormat;
/*     */ import org.joda.time.format.DateTimeFormatter;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SmsClient
/*     */ {
/*  63 */   private static Logger logger = LoggerFactory.getLogger(SmsClient.class);
/*     */   private static final String SubmitUrl = "https://imsp.emome.net:4443/imsp/sms/servlet/SubmitSM";
/*     */   private static final String QueryUrl = "https://imsp.emome.net:4443/imsp/sms/servlet/QuerySM";
/*  66 */   private static final Pattern bodyPattern = Pattern.compile("(?s)<body>\\s*(.*?)\\s*</body>");
/*  67 */   private static final Pattern resultPattern = Pattern.compile("<br>|\\|");
/*  68 */   private static final DateTimeFormatter dateFormat = DateTimeFormat.forPattern("yyyyMMddHHmmss");
/*     */   
/*     */   private String smsAccount;
/*     */   
/*     */   private String smsPassword;
/*     */   
/*     */   private boolean smsEnabled;
/*     */   private ExecutorService executorService;
/*     */   private String externalSmsUrl;
/*     */   private boolean enableExternalSms;
/*     */   @Autowired
/*     */   private CloseableHttpClient httpClient;
/*     */   @Autowired
/*     */   @Qualifier("proxyRequestConfig")
/*     */   private RequestConfig requestConfig;
/*     */   
/*     */   public SmsClient(String smsAccount, String smsPassword) {
/*  85 */     this.smsAccount = smsAccount;
/*  86 */     this.smsPassword = smsPassword;
/*  87 */     this
/*  88 */       .executorService = Executors.newCachedThreadPool((new ThreadFactoryBuilder())
/*  89 */         .setNameFormat("SmsClient").build());
/*     */   }
/*     */   
/*     */   public void destroy() {
/*  93 */     this.executorService.shutdownNow();
/*     */   }
/*     */   
/*     */   public boolean isSmsEnabled() {
/*  97 */     return this.smsEnabled;
/*     */   }
/*     */   
/*     */   public void setSmsEnabled(boolean smsEnabled) {
/* 101 */     this.smsEnabled = smsEnabled;
/*     */   }
/*     */   
/*     */   public String getExternalSmsUrl() {
/* 105 */     return this.externalSmsUrl;
/*     */   }
/*     */   
/*     */   public void setExternalSmsUrl(String externalSmsUrl) {
/* 109 */     this.externalSmsUrl = externalSmsUrl;
/*     */   }
/*     */   
/*     */   public boolean isEnableExternalSms() {
/* 113 */     return this.enableExternalSms;
/*     */   }
/*     */   
/*     */   public void setEnableExternalSms(boolean enableExternalSms) {
/* 117 */     this.enableExternalSms = enableExternalSms;
/*     */   }
/*     */   
/*     */   private Map<String, List<SmsSendResponse>> postExternalUrl(String msg, String... to) {
/* 121 */     Map<String, List<SmsSendResponse>> resultMap = new HashMap<>();
/* 122 */     HttpURLConnection conn = null;
/*     */     try {
/* 124 */       URL url = new URL(this.externalSmsUrl);
/* 125 */       conn = (HttpURLConnection)url.openConnection();
/* 126 */       conn.setDoOutput(true);
/* 127 */       conn.setRequestMethod("POST");
/* 128 */       conn.setRequestProperty("Content-Type", "application/json");
/*     */       
/* 130 */       SmsSendRequest smsSendRequest = new SmsSendRequest();
/* 131 */       StringBuilder sb = new StringBuilder();
/* 132 */       if (to != null && to.length > 0) {
/* 133 */         for (int i = 0; i < to.length; i++) {
/* 134 */           if (i == 0) {
/* 135 */             sb.append(to[i]);
/*     */           } else {
/* 137 */             sb.append(";" + to[i]);
/*     */           } 
/*     */         } 
/*     */       }
/* 141 */       smsSendRequest.setToAddrs(sb.toString());
/* 142 */       smsSendRequest.setMessages(msg);
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
/* 154 */       Gson gson = (new GsonBuilder()).registerTypeAdapter(Date.class, (json, typeOfT, context) -> new Date(json.getAsJsonPrimitive().getAsLong())).registerTypeAdapter(Date.class, (date, type, jsonSerializationContext) -> new JsonPrimitive(Long.valueOf(date.getTime()))).create();
/* 155 */       OutputStream os = conn.getOutputStream();
/* 156 */       os.write(gson.toJson(smsSendRequest).getBytes());
/* 157 */       os.flush();
/*     */       
/* 159 */       if (conn.getResponseCode() == 200) {
/* 160 */         Type mapType = (new TypeToken<Map<String, List<SmsSendResponse>>>() {  }).getType();
/* 161 */         resultMap = (Map<String, List<SmsSendResponse>>)gson.fromJson(new InputStreamReader(conn.getInputStream()), mapType);
/*     */       } 
/* 163 */       conn.disconnect();
/* 164 */     } catch (Exception e) {
/* 165 */       e.printStackTrace();
/*     */     } finally {
/* 167 */       if (conn != null) {
/* 168 */         conn.disconnect();
/*     */       }
/*     */     } 
/* 171 */     return resultMap;
/*     */   }
/*     */   
/*     */   public void sendSmsAsync(final SendSmsCallback sendSmsCallback, final String msg, String... to) {
/* 175 */     this.executorService.submit(new Runnable()
/*     */         {
/*     */           public void run()
/*     */           {
/* 179 */             Map<String, List<SmsSendResponse>> resultMap = SmsClient.this.sendSms(msg, to);
/* 180 */             sendSmsCallback.onSendResponse(resultMap);
/*     */           }
/*     */         });
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Map<String, List<SmsSendResponse>> sendSms(String msg, String... to) {
/* 201 */     if (this.enableExternalSms) {
/* 202 */       return postExternalUrl(msg, to);
/*     */     }
/* 204 */     List<String> msgSegmentList = new ArrayList<>();
/* 205 */     List<String[]> toSegmentList = (List)new ArrayList<>();
/* 206 */     if (containsHanScript(msg))
/* 207 */     { if (msg.length() > 70)
/* 208 */       { int j = 0;
/* 209 */         StringBuilder sb = new StringBuilder();
/* 210 */         for (int i = 0; i < msg.length(); i++) {
/* 211 */           sb.append(msg.charAt(i));
/* 212 */           j++;
/* 213 */           if (j >= 70) {
/* 214 */             msgSegmentList.add(sb.toString());
/* 215 */             sb.setLength(0);
/* 216 */             j = 0;
/*     */           } 
/*     */         } 
/* 219 */         if (j > 0) msgSegmentList.add(sb.toString());  }
/* 220 */       else { msgSegmentList.add(msg); }
/*     */        }
/* 222 */     else if (msg.length() > 160)
/* 223 */     { int j = 0;
/* 224 */       StringBuilder sb = new StringBuilder();
/* 225 */       for (int i = 0; i < msg.length(); i++) {
/* 226 */         sb.append(msg.charAt(i));
/* 227 */         j++;
/* 228 */         if (j >= 160) {
/* 229 */           msgSegmentList.add(sb.toString());
/* 230 */           sb.setLength(0);
/* 231 */           j = 0;
/*     */         } 
/*     */       } 
/* 234 */       if (j > 0) msgSegmentList.add(sb.toString());  }
/* 235 */     else { msgSegmentList.add(msg); }
/*     */ 
/*     */     
/* 238 */     if (to.length > 20)
/* 239 */     { int j = 0;
/* 240 */       List<String> list = new ArrayList<>();
/*     */       
/* 242 */       for (int i = 0; i < to.length; i++) {
/* 243 */         list.add(to[i]);
/* 244 */         j++;
/* 245 */         if (j >= 20) {
/* 246 */           String[] toSeg = new String[list.size()];
/* 247 */           list.toArray(toSeg);
/* 248 */           toSegmentList.add(toSeg);
/* 249 */           list.clear();
/* 250 */           j = 0;
/*     */         } 
/*     */       } 
/* 253 */       if (j > 0) {
/* 254 */         String[] toSeg = new String[list.size()];
/* 255 */         list.toArray(toSeg);
/* 256 */         toSegmentList.add(toSeg);
/*     */       }  }
/* 258 */     else { toSegmentList.add(to); }
/*     */     
/* 260 */     logger.info("Send SMS :{} divided into {} segments TO {} divided into {} groups", new Object[] { msgSegmentList, 
/*     */ 
/*     */           
/* 263 */           Integer.valueOf(msgSegmentList.size()), 
/* 264 */           Arrays.deepToString(toSegmentList.toArray((Object[])new String[toSegmentList.size()][])), 
/* 265 */           Integer.valueOf(toSegmentList.size()) });
/*     */     
/* 267 */     Map<String, List<SmsSendResponse>> resultMap = new HashMap<>();
/* 268 */     for (String[] toSeg : toSegmentList) {
/* 269 */       for (String msgSeg : msgSegmentList) {
/* 270 */         List<SmsSendResponse> smsSendResponseList = sendSmsSegment(msgSeg, toSeg);
/* 271 */         for (SmsSendResponse smsSendResponse : smsSendResponseList) {
/* 272 */           List<SmsSendResponse> list = resultMap.get(smsSendResponse.getToAddr());
/* 273 */           if (list == null) {
/* 274 */             list = new ArrayList<>();
/* 275 */             resultMap.put(smsSendResponse.getToAddr(), list);
/*     */           } 
/* 277 */           list.add(smsSendResponse);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 285 */     return resultMap;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private List<SmsSendResponse> sendSmsSegment(String msg, String... to) {
/* 296 */     List<SmsSendResponse> smsSendResponseList = new ArrayList<>();
/* 297 */     if (!this.smsEnabled) {
/* 298 */       for (int j = 0; j < to.length; j++) {
/* 299 */         smsSendResponseList.add(new SmsSendResponse(to[j], "-1", "", "SmsClient disabled", new Date()));
/*     */       }
/*     */       
/* 302 */       return smsSendResponseList;
/*     */     } 
/* 304 */     StringBuilder sb = new StringBuilder();
/* 305 */     for (int i = 0; i < to.length; i++) {
/* 306 */       String number = to[i];
/* 307 */       if (number != null && !number.equals("")) {
/*     */ 
/*     */         
/* 310 */         number = number.replaceAll("-", "");
/* 311 */         if (number.startsWith("8869") && number.length() == 12) {
/* 312 */           if (i > 0) sb.append(','); 
/* 313 */           sb.append(number);
/* 314 */         } else if (number.startsWith("+8869") && number.length() == 13) {
/* 315 */           if (i > 0) sb.append(','); 
/* 316 */           sb.append(number);
/* 317 */         } else if (number.startsWith("09") && number.length() == 10) {
/* 318 */           if (i > 0) sb.append(','); 
/* 319 */           sb.append(number);
/*     */         } 
/*     */       } 
/*     */     } 
/* 323 */     List<NameValuePair> urlParameters = new ArrayList<>();
/* 324 */     urlParameters.add(new BasicNameValuePair("account", this.smsAccount));
/* 325 */     urlParameters.add(new BasicNameValuePair("password", this.smsPassword));
/* 326 */     urlParameters.add(new BasicNameValuePair("from_addr_type", "0"));
/* 327 */     urlParameters.add(new BasicNameValuePair("from_addr", ""));
/* 328 */     urlParameters.add(new BasicNameValuePair("to_addr_type", "0"));
/* 329 */     urlParameters.add(new BasicNameValuePair("to_addr", sb.toString()));
/* 330 */     urlParameters.add(new BasicNameValuePair("msg_expire_time", "0"));
/* 331 */     urlParameters.add(new BasicNameValuePair("msg_type", "0"));
/* 332 */     urlParameters.add(new BasicNameValuePair("msg", msg));
/*     */     
/* 334 */     HttpPost httpPost = new HttpPost("https://imsp.emome.net:4443/imsp/sms/servlet/SubmitSM");
/* 335 */     httpPost.setConfig(this.requestConfig);
/*     */     
/* 337 */     CloseableHttpResponse httpResponse = null;
/*     */     try {
/* 339 */       httpPost.setEntity((HttpEntity)new UrlEncodedFormEntity(urlParameters, "BIG5"));
/*     */       try {
/* 341 */         httpResponse = this.httpClient.execute((HttpUriRequest)httpPost);
/* 342 */       } catch (Exception e) {
/* 343 */         logger.error("httpClient.execute failed.", e.getMessage());
/*     */       } 
/* 345 */       if (httpResponse == null || 502 == httpResponse
/* 346 */         .getStatusLine().getStatusCode()) {
/* 347 */         httpResponse = this.httpClient.execute((HttpUriRequest)httpPost);
/* 348 */         if (200 == httpResponse.getStatusLine().getStatusCode()) {
/* 349 */           logger.debug("try 1 execute ok");
/* 350 */           processResponse(smsSendResponseList, httpResponse);
/*     */         } else {
/* 352 */           throw new Exception("status code: " + httpResponse.getStatusLine().getStatusCode());
/*     */         } 
/* 354 */       } else if (200 == httpResponse.getStatusLine().getStatusCode()) {
/* 355 */         logger.debug("first execute ok");
/* 356 */         processResponse(smsSendResponseList, httpResponse);
/*     */       } else {
/* 358 */         throw new Exception("status code: " + httpResponse.getStatusLine().getStatusCode());
/*     */       } 
/* 360 */     } catch (Exception ex) {
/* 361 */       logger.error("Failed to send SMS, to: {}", sb.toString(), ex);
/* 362 */       smsSendResponseList.clear();
/* 363 */       String description = ex.getMessage();
/* 364 */       if (description == null) {
/* 365 */         Throwable th = ex.getCause();
/* 366 */         if (th != null) description = th.getMessage(); 
/*     */       } 
/* 368 */       for (int j = 0; j < to.length; j++) {
/* 369 */         smsSendResponseList.add(new SmsSendResponse(to[j], "-1", "", description, new Date()));
/*     */       }
/*     */     } finally {
/* 372 */       if (httpResponse != null) {
/*     */         try {
/* 374 */           EntityUtils.consume(httpResponse.getEntity());
/* 375 */         } catch (Exception e) {
/* 376 */           logger.error("Failed to consume Entity", e);
/*     */         } 
/*     */         try {
/* 379 */           httpResponse.close();
/* 380 */         } catch (IOException e) {
/* 381 */           logger.error("Failed to close httpResponse", e);
/*     */         } 
/*     */       } 
/*     */     } 
/* 385 */     return smsSendResponseList;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void processResponse(List<SmsSendResponse> smsSendResponseList, CloseableHttpResponse httpResponse) throws ParseException, IOException {
/* 392 */     String response = EntityUtils.toString(httpResponse.getEntity());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 398 */     Matcher matcher = bodyPattern.matcher(response);
/* 399 */     if (matcher.find()) {
/* 400 */       String result = matcher.group(1);
/* 401 */       String[] parts = resultPattern.split(result);
/* 402 */       for (int i = 0; i < parts.length / 4; i++)
/*     */       {
/* 404 */         smsSendResponseList.add(new SmsSendResponse(parts[i * 4], parts[i * 4 + 1], parts[i * 4 + 2], parts[i * 4 + 3], new Date()));
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void queryStatusAsync(final QuerySmsCallback querySmsCallback, final String messageId) {
/* 412 */     this.executorService.submit(new Runnable()
/*     */         {
/*     */           public void run()
/*     */           {
/* 416 */             List<SmsQueryResponse> smsQueryResponseList = SmsClient.this.queryStatus(messageId);
/* 417 */             querySmsCallback.onQueryResponse(smsQueryResponseList);
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<SmsQueryResponse> queryStatus(String messageId) {
/* 430 */     List<SmsQueryResponse> smsQueryResponseList = new ArrayList<>();
/* 431 */     if (!this.smsEnabled) {
/* 432 */       smsQueryResponseList.add(new SmsQueryResponse(null, "-1", null, "SmsClient disabled"));
/* 433 */       return smsQueryResponseList;
/*     */     } 
/* 435 */     List<NameValuePair> urlParameters = new ArrayList<>();
/* 436 */     urlParameters.add(new BasicNameValuePair("account", this.smsAccount));
/* 437 */     urlParameters.add(new BasicNameValuePair("password", this.smsPassword));
/* 438 */     urlParameters.add(new BasicNameValuePair("messageid", messageId));
/*     */     
/* 440 */     HttpPost httpPost = new HttpPost("https://imsp.emome.net:4443/imsp/sms/servlet/QuerySM");
/* 441 */     httpPost.setConfig(this.requestConfig);
/*     */     
/* 443 */     CloseableHttpResponse httpResponse = null;
/*     */     try {
/* 445 */       httpPost.setEntity((HttpEntity)new UrlEncodedFormEntity(urlParameters, "BIG5"));
/* 446 */       httpResponse = this.httpClient.execute((HttpUriRequest)httpPost);
/* 447 */       if (200 == httpResponse.getStatusLine().getStatusCode())
/* 448 */       { String response = EntityUtils.toString(httpResponse.getEntity());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 454 */         Matcher matcher = bodyPattern.matcher(response);
/* 455 */         if (matcher.find()) {
/* 456 */           String result = matcher.group(1);
/* 457 */           String[] parts = resultPattern.split(result);
/* 458 */           for (int i = 0; i < parts.length / 4; i++) {
/* 459 */             smsQueryResponseList.add(new SmsQueryResponse(parts[i * 4], parts[i * 4 + 1], dateFormat
/*     */ 
/*     */ 
/*     */                   
/* 463 */                   .parseDateTime(parts[i * 4 + 2]).toDate(), parts[i * 4 + 3]));
/*     */           }
/*     */         }  }
/*     */       else
/* 467 */       { throw new Exception("status code: " + httpResponse.getStatusLine().getStatusCode()); } 
/* 468 */     } catch (Exception ex) {
/* 469 */       logger.error("Failed to query status, messageId: {}", messageId, ex);
/* 470 */       smsQueryResponseList.clear();
/* 471 */       String description = ex.getMessage();
/* 472 */       if (description == null) {
/* 473 */         Throwable th = ex.getCause();
/* 474 */         if (th != null) description = th.getMessage(); 
/*     */       } 
/* 476 */       smsQueryResponseList.add(new SmsQueryResponse(null, "-1", null, description));
/*     */     } finally {
/* 478 */       if (httpResponse != null) {
/*     */         try {
/* 480 */           EntityUtils.consume(httpResponse.getEntity());
/* 481 */         } catch (Exception e) {
/* 482 */           logger.error("Failed to consume Entity", e);
/*     */         } 
/*     */         try {
/* 485 */           httpResponse.close();
/* 486 */         } catch (IOException e) {
/* 487 */           logger.error("Failed to close httpResponse", e);
/*     */         } 
/*     */       } 
/*     */     } 
/* 491 */     return smsQueryResponseList;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean containsHanScript(String s) {
/* 496 */     return s.codePoints().anyMatch(codepoint -> (Character.UnicodeScript.of(codepoint) == Character.UnicodeScript.HAN));
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\lib\base-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\base\sms\SmsClient.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */