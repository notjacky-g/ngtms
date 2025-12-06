/*     */ package com.hwacom.ngtms.common.security.josso;
/*     */ 
/*     */ import java.net.URL;
/*     */ import java.util.Map;
/*     */ import javax.xml.ws.BindingProvider;
/*     */ import org.josso.gateway.identity.service.SSOIdentityManagerService;
/*     */ import org.josso.gateway.identity.service.SSOIdentityProviderService;
/*     */ import org.josso.gateway.jaxws.JAXWSWebserviceGatewayServiceLocator;
/*     */ import org.josso.gateway.jaxws.identity.service.WebserviceSSOIdentityManager;
/*     */ import org.josso.gateway.jaxws.identity.service.WebserviceSSOIdentityProvider;
/*     */ import org.josso.gateway.jaxws.session.service.WebserviceSSOSessionManager;
/*     */ import org.josso.gateway.session.service.SSOSessionManagerService;
/*     */ import org.josso.gateway.ws._1_2.wsdl.SSOIdentityManager;
/*     */ import org.josso.gateway.ws._1_2.wsdl.SSOIdentityManagerWS;
/*     */ import org.josso.gateway.ws._1_2.wsdl.SSOIdentityProvider;
/*     */ import org.josso.gateway.ws._1_2.wsdl.SSOIdentityProviderWS;
/*     */ import org.josso.gateway.ws._1_2.wsdl.SSOSessionManager;
/*     */ import org.josso.gateway.ws._1_2.wsdl.SSOSessionManagerWS;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class JossoJAXWSWebserviceGatewayServiceLocator
/*     */   extends JAXWSWebserviceGatewayServiceLocator
/*     */ {
/*     */   private static final long serialVersionUID = -6807003030373677499L;
/*  34 */   private static final Logger logger = LoggerFactory.getLogger(JossoJAXWSWebserviceGatewayServiceLocator.class);
/*     */   
/*     */   private String identityManagerWsdlLocation;
/*     */   
/*     */   private String identityProviderWsdlLocation;
/*     */   
/*     */   private String sessionManagerWsdlLocation;
/*     */   
/*     */   public SSOSessionManagerService getSSOSessionManager() throws Exception {
/*  43 */     SSOSessionManager port = (new SSOSessionManagerWS(new URL(this.sessionManagerWsdlLocation))).getSSOSessionManagerSoap();
/*     */     
/*  45 */     String smEndpoint = getSSOSessionManagerEndpoint();
/*  46 */     logger.debug("Using SSOSessionManager endpoint '" + smEndpoint + "'");
/*  47 */     setEndpointAddress(port, smEndpoint);
/*     */     
/*  49 */     WebserviceSSOSessionManager wsm = new WebserviceSSOSessionManager(port);
/*  50 */     return (SSOSessionManagerService)wsm;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public SSOIdentityManagerService getSSOIdentityManager() throws Exception {
/*  56 */     SSOIdentityManager port = (new SSOIdentityManagerWS(new URL(this.identityManagerWsdlLocation))).getSSOIdentityManagerSoap();
/*     */     
/*  58 */     String imEndpoint = getSSOIdentityManagerEndpoint();
/*  59 */     logger.debug("Using SSOIdentityManager endpoint '" + imEndpoint + "'");
/*  60 */     setEndpointAddress(port, imEndpoint);
/*     */     
/*  62 */     WebserviceSSOIdentityManager wim = new WebserviceSSOIdentityManager(port);
/*  63 */     return (SSOIdentityManagerService)wim;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SSOIdentityProviderService getSSOIdentityProvider() throws Exception {
/*  70 */     SSOIdentityProvider port = (new SSOIdentityProviderWS(new URL(this.identityProviderWsdlLocation))).getSSOIdentityProviderSoap();
/*     */     
/*  72 */     String ipEndpoint = getSSOIdentityProviderEndpoint();
/*  73 */     logger.debug("Using SSOIdentityProvider endpoint '" + ipEndpoint + "'");
/*  74 */     setEndpointAddress(port, ipEndpoint);
/*     */     
/*  76 */     WebserviceSSOIdentityProvider wip = new WebserviceSSOIdentityProvider(port);
/*  77 */     return (SSOIdentityProviderService)wip;
/*     */   }
/*     */ 
/*     */   
/*     */   private void setEndpointAddress(Object port, String newAddress) {
/*  82 */     assert port instanceof BindingProvider : "Doesn't appear to be a valid port";
/*  83 */     assert newAddress != null : "Doesn't appear to be a valid address";
/*     */     
/*  85 */     BindingProvider bp = (BindingProvider)port;
/*     */     
/*  87 */     Map<String, Object> context = bp.getRequestContext();
/*  88 */     context.put("javax.xml.ws.service.endpoint.address", newAddress);
/*     */   }
/*     */   
/*     */   public void setIdentityManagerWsdlLocation(String identityManagerWsdlLocation) {
/*  92 */     this.identityManagerWsdlLocation = identityManagerWsdlLocation;
/*     */   }
/*     */   
/*     */   public void setIdentityProviderWsdlLocation(String identityProviderWsdlLocation) {
/*  96 */     this.identityProviderWsdlLocation = identityProviderWsdlLocation;
/*     */   }
/*     */   
/*     */   public void setSessionManagerWsdlLocation(String sessionManagerWsdlLocation) {
/* 100 */     this.sessionManagerWsdlLocation = sessionManagerWsdlLocation;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\lib\common-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\common\security\josso\JossoJAXWSWebserviceGatewayServiceLocator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */