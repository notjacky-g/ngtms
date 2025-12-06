/*     */ package com.hwacom.ngtms.frameworkcontext;
/*     */ 
/*     */ import com.hwacom.ngtms.base.httpclient.HttpIdleConnectionEvictor;
/*     */ import com.hwacom.ngtms.base.httpclient.SimpleHttpClient;
/*     */ import com.hwacom.ngtms.base.sms.SmsClient;
/*     */ import javax.annotation.Resource;
/*     */ import org.apache.http.HttpHost;
/*     */ import org.apache.http.client.config.RequestConfig;
/*     */ import org.apache.http.conn.HttpClientConnectionManager;
/*     */ import org.apache.http.impl.client.CloseableHttpClient;
/*     */ import org.apache.http.impl.client.HttpClientBuilder;
/*     */ import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
/*     */ import org.springframework.beans.factory.annotation.Qualifier;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Configuration
/*     */ public class HttpClientConfig
/*     */ {
/*     */   @Value("${http.client.maxTotal:100}")
/*     */   private Integer maxTotal;
/*     */   @Value("${http.client.defaultMaxPerRoute:20}")
/*     */   private Integer defaultMaxPerRoute;
/*     */   @Value("${http.client.connectTimeout:1000}")
/*     */   private Integer connectTimeout;
/*     */   @Value("${http.client.connectionRequestTimeout:500}")
/*     */   private Integer connectionRequestTimeout;
/*     */   @Value("${http.client.socketTimeout:10000}")
/*     */   private Integer socketTimeout;
/*     */   @Value("${http.client.staleConnectionCheckEnabled:true}")
/*     */   private boolean staleConnectionCheckEnabled;
/*     */   @Resource
/*     */   private Environment environment;
/*     */   
/*     */   @Bean(name = {"httpClientConnectionManager"})
/*     */   public PoolingHttpClientConnectionManager httpClientConnectionManager() {
/*  52 */     PoolingHttpClientConnectionManager httpClientConnectionManager = new PoolingHttpClientConnectionManager();
/*     */ 
/*     */     
/*  55 */     httpClientConnectionManager.setMaxTotal(this.maxTotal.intValue());
/*     */     
/*  57 */     httpClientConnectionManager.setDefaultMaxPerRoute(this.defaultMaxPerRoute.intValue());
/*  58 */     return httpClientConnectionManager;
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
/*     */   @Bean(name = {"httpClientBuilder"})
/*     */   public HttpClientBuilder httpClientBuilder(@Qualifier("httpClientConnectionManager") PoolingHttpClientConnectionManager httpClientConnectionManager) {
/*  73 */     HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
/*     */     
/*  75 */     httpClientBuilder.setConnectionManager((HttpClientConnectionManager)httpClientConnectionManager);
/*     */     
/*  77 */     return httpClientBuilder;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Bean(destroyMethod = "close")
/*     */   public CloseableHttpClient httpclient(@Qualifier("httpClientBuilder") HttpClientBuilder httpClientBuilder) {
/*  89 */     return httpClientBuilder.build();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Bean(name = {"builder"})
/*     */   public RequestConfig.Builder builder() {
/* 100 */     return newBuilder();
/*     */   }
/*     */   
/*     */   private RequestConfig.Builder newBuilder() {
/* 104 */     RequestConfig.Builder builder = RequestConfig.custom();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 109 */     return builder.setConnectTimeout(this.connectTimeout.intValue()).setConnectionRequestTimeout(this.connectionRequestTimeout.intValue()).setSocketTimeout(this.socketTimeout.intValue()).setStaleConnectionCheckEnabled(this.staleConnectionCheckEnabled);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Bean({"requestConfig"})
/*     */   public RequestConfig requestConfig() {
/* 120 */     return newBuilder().build();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Bean({"proxyRequestConfig"})
/*     */   public RequestConfig proxyRequestConfig(@Qualifier("builder") RequestConfig.Builder builder) {
/* 131 */     HttpHost proxy = null;
/* 132 */     String proxyIp = this.environment.getProperty("http.proxy.ip");
/* 133 */     if (proxyIp != null && proxyIp.length() > 0) {
/* 134 */       int port = ((Integer)this.environment.getProperty("http.proxy.port", Integer.class, Integer.valueOf(3128))).intValue();
/* 135 */       proxy = new HttpHost(proxyIp, port);
/*     */     } 
/* 137 */     if (proxy != null) {
/* 138 */       return builder.setProxy(proxy).build();
/*     */     }
/*     */     
/* 141 */     return builder.build();
/*     */   }
/*     */   
/*     */   @Bean
/*     */   public HttpIdleConnectionEvictor httpIdleConnectionEvictor() {
/* 146 */     return new HttpIdleConnectionEvictor();
/*     */   }
/*     */   
/*     */   @Bean
/*     */   public SimpleHttpClient simpleHttpClient() {
/* 151 */     return new SimpleHttpClient();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Bean(destroyMethod = "destroy")
/*     */   public SmsClient smsClient() {
/* 161 */     SmsClient smsClient = new SmsClient(this.environment.getProperty("sms.account", "10371"), this.environment.getProperty("sms.password", "10371"));
/* 162 */     smsClient.setSmsEnabled(((Boolean)this.environment.getProperty("sms.enabled", Boolean.class, Boolean.valueOf(false))).booleanValue());
/*     */     
/* 164 */     boolean enableExternalSms = ((Boolean)this.environment.getProperty("sms.external.enabled", Boolean.class, Boolean.valueOf(false))).booleanValue();
/* 165 */     smsClient.setEnableExternalSms(enableExternalSms);
/* 166 */     if (enableExternalSms) {
/* 167 */       smsClient.setExternalSmsUrl((String)this.environment.getProperty("sms.external.url", String.class));
/*     */     }
/* 169 */     return smsClient;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\lib\base-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\frameworkcontext\HttpClientConfig.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */