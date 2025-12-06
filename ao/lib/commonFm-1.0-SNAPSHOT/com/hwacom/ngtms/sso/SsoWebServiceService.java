/*    */ package com.hwacom.ngtms.sso;
/*    */ 
/*    */ import java.net.MalformedURLException;
/*    */ import java.net.URL;
/*    */ import java.util.logging.Level;
/*    */ import java.util.logging.Logger;
/*    */ import javax.xml.namespace.QName;
/*    */ import javax.xml.ws.Service;
/*    */ import javax.xml.ws.WebEndpoint;
/*    */ import javax.xml.ws.WebServiceClient;
/*    */ import javax.xml.ws.WebServiceFeature;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @WebServiceClient(name="SsoWebServiceService", wsdlLocation="file:/data/workspace/NGTMS-tc-1.0/fm/commonFm/src/wsdl/SsoWebServiceApiForDataSync.wsdl", targetNamespace="http://sso.ngtms.hwacom.com/")
/*    */ public class SsoWebServiceService
/*    */   extends Service
/*    */ {
/*    */   public static final URL WSDL_LOCATION;
/* 24 */   public static final QName SERVICE = new QName("http://sso.ngtms.hwacom.com/", "SsoWebServiceService");
/* 25 */   public static final QName SsoWebServicePort = new QName("http://sso.ngtms.hwacom.com/", "SsoWebServicePort");
/*    */   
/* 27 */   static { URL url = null;
/*    */     try {
/* 29 */       url = new URL("file:/data/workspace/NGTMS-tc-1.0/fm/commonFm/src/wsdl/SsoWebServiceApiForDataSync.wsdl");
/*    */     }
/*    */     catch (MalformedURLException e) {
/* 32 */       Logger.getLogger(SsoWebServiceService.class.getName()).log(Level.INFO, "Can not initialize the default wsdl from {0}", "file:/data/workspace/NGTMS-tc-1.0/fm/commonFm/src/wsdl/SsoWebServiceApiForDataSync.wsdl");
/*    */     }
/*    */     
/* 35 */     WSDL_LOCATION = url;
/*    */   }
/*    */   
/*    */   public SsoWebServiceService(URL wsdlLocation) {
/* 39 */     super(wsdlLocation, SERVICE);
/*    */   }
/*    */   
/*    */   public SsoWebServiceService(URL wsdlLocation, QName serviceName) {
/* 43 */     super(wsdlLocation, serviceName);
/*    */   }
/*    */   
/*    */   public SsoWebServiceService() {
/* 47 */     super(WSDL_LOCATION, SERVICE);
/*    */   }
/*    */   
/*    */   public SsoWebServiceService(WebServiceFeature... features) {
/* 51 */     super(WSDL_LOCATION, SERVICE, features);
/*    */   }
/*    */   
/*    */   public SsoWebServiceService(URL wsdlLocation, WebServiceFeature... features) {
/* 55 */     super(wsdlLocation, SERVICE, features);
/*    */   }
/*    */   
/*    */   public SsoWebServiceService(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
/* 59 */     super(wsdlLocation, serviceName, features);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   @WebEndpoint(name="SsoWebServicePort")
/*    */   public SsoWebService getSsoWebServicePort()
/*    */   {
/* 72 */     return (SsoWebService)super.getPort(SsoWebServicePort, SsoWebService.class);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   @WebEndpoint(name="SsoWebServicePort")
/*    */   public SsoWebService getSsoWebServicePort(WebServiceFeature... features)
/*    */   {
/* 84 */     return (SsoWebService)super.getPort(SsoWebServicePort, SsoWebService.class, features);
/*    */   }
/*    */ }


/* Location:              D:\TC\NJ\10.121.41.38\ngtms\ao\lib.src\commonFm-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\sso\SsoWebServiceService.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */