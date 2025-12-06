/*     */ package com.hwacom.ngtms.frameworkcontext;
/*     */ 
/*     */ import com.hwacom.ngtms.base.httpclient.HttpIdleConnectionEvictor;
/*     */ import com.hwacom.ngtms.base.httpclient.SimpleHttpClient;
/*     */ import com.hwacom.ngtms.base.httpclient.TrustSelfHttpClient;
/*     */ import com.hwacom.ngtms.base.sms.SmsClient;
/*     */ import javax.annotation.Resource;
/*     */ import org.apache.http.HttpHost;
/*     */ import org.apache.http.client.config.RequestConfig;
/*     */ import org.apache.http.client.config.RequestConfig.Builder;
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
/*     */   @Bean(name={"httpClientConnectionManager"})
/*     */   public PoolingHttpClientConnectionManager httpClientConnectionManager()
/*     */   {
/*  53 */     PoolingHttpClientConnectionManager httpClientConnectionManager = new PoolingHttpClientConnectionManager();
/*     */     
/*     */ 
/*  56 */     httpClientConnectionManager.setMaxTotal(this.maxTotal.intValue());
/*     */     
/*  58 */     httpClientConnectionManager.setDefaultMaxPerRoute(this.defaultMaxPerRoute.intValue());
/*  59 */     return httpClientConnectionManager;
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
/*     */   @Bean(name={"httpClientBuilder"})
/*     */   public HttpClientBuilder httpClientBuilder(@Qualifier("httpClientConnectionManager") PoolingHttpClientConnectionManager httpClientConnectionManager)
/*     */   {
/*  74 */     HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
/*     */     
/*  76 */     httpClientBuilder.setConnectionManager(httpClientConnectionManager);
/*     */     
/*  78 */     return httpClientBuilder;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @Bean(destroyMethod="close")
/*     */   public CloseableHttpClient httpclient(@Qualifier("httpClientBuilder") HttpClientBuilder httpClientBuilder)
/*     */   {
/*  90 */     return httpClientBuilder.build();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @Bean(name={"builder"})
/*     */   public RequestConfig.Builder builder()
/*     */   {
/* 101 */     return newBuilder();
/*     */   }
/*     */   
/*     */   private RequestConfig.Builder newBuilder() {
/* 105 */     RequestConfig.Builder builder = RequestConfig.custom();
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 110 */     return builder.setConnectTimeout(this.connectTimeout.intValue()).setConnectionRequestTimeout(this.connectionRequestTimeout.intValue()).setSocketTimeout(this.socketTimeout.intValue()).setStaleConnectionCheckEnabled(this.staleConnectionCheckEnabled);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @Bean({"requestConfig"})
/*     */   public RequestConfig requestConfig()
/*     */   {
/* 121 */     return newBuilder().build();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @Bean({"proxyRequestConfig"})
/*     */   public RequestConfig proxyRequestConfig(@Qualifier("builder") RequestConfig.Builder builder)
/*     */   {
/* 132 */     HttpHost proxy = null;
/* 133 */     String proxyIp = this.environment.getProperty("http.proxy.ip");
/* 134 */     if ((proxyIp != null) && (proxyIp.length() > 0)) {
/* 135 */       int port = ((Integer)this.environment.getProperty("http.proxy.port", Integer.class, Integer.valueOf(3128))).intValue();
/* 136 */       proxy = new HttpHost(proxyIp, port);
/*     */     }
/* 138 */     if (proxy != null) {
/* 139 */       return builder.setProxy(proxy).build();
/*     */     }
/*     */     
/* 142 */     return builder.build();
/*     */   }
/*     */   
/*     */   @Bean
/*     */   public HttpIdleConnectionEvictor httpIdleConnectionEvictor() {
/* 147 */     return new HttpIdleConnectionEvictor();
/*     */   }
/*     */   
/*     */   @Bean
/*     */   public SimpleHttpClient simpleHttpClient() {
/* 152 */     return new SimpleHttpClient();
/*     */   }
/*     */   
/*     */   @Bean
/*     */   public TrustSelfHttpClient trustSelfHttpClient() {
/* 157 */     return new TrustSelfHttpClient();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @Bean(destroyMethod="destroy")
/*     */   public SmsClient smsClient()
/*     */   {
/* 167 */     SmsClient smsClient = new SmsClient(this.environment.getProperty("sms.account", "10371"), this.environment.getProperty("sms.password", "10371"));
/* 168 */     smsClient.setSmsEnabled(((Boolean)this.environment.getProperty("sms.enabled", Boolean.class, Boolean.valueOf(false))).booleanValue());
/*     */     
/* 170 */     boolean enableExternalSms = ((Boolean)this.environment.getProperty("sms.external.enabled", Boolean.class, Boolean.valueOf(false))).booleanValue();
/* 171 */     smsClient.setEnableExternalSms(enableExternalSms);
/* 172 */     if (enableExternalSms) {
/* 173 */       smsClient.setExternalSmsUrl((String)this.environment.getProperty("sms.external.url", String.class));
/*     */     }
/* 175 */     return smsClient;
/*     */   }
/*     */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\base-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\frameworkcontext\HttpClientConfig.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */