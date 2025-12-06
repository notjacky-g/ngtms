/*    */ package com.hwacom.ngtms.ao.am;
/*    */ 
/*    */ import com.google.gwt.core.shared.GWT;
/*    */ import com.hwacom.ngtms.ao.am.restygwt.AoNcuViewerRestService;
/*    */ import com.hwacom.ngtms.ao.am.restygwt.AoRptViewerRestService;
/*    */ import com.hwacom.ngtms.ao.am.view.Messages;
/*    */ import com.hwacom.ngtms.ao.am.view.rpt.RptViewer;
/*    */ import com.hwacom.ngtms.cam.client.HomeEP;
/*    */ import com.hwacom.ngtms.common.am.ReportEP;
/*    */ import com.sencha.gxt.widget.core.client.Composite;
/*    */ import java.util.AbstractMap;
/*    */ import java.util.Arrays;
/*    */ import org.fusesource.restygwt.client.RestService;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class RptEP
/*    */   extends AoEntryPoint
/*    */ {
/* 23 */   public static final Messages messages = (Messages)GWT.create(Messages.class);
/*    */ 
/*    */   
/* 26 */   public static final AoRptViewerRestService aoRptService = (AoRptViewerRestService)GWT.create(AoRptViewerRestService.class);
/*    */   
/* 28 */   public static final AoNcuViewerRestService ncuService = (AoNcuViewerRestService)GWT.create(AoNcuViewerRestService.class);
/*    */   
/*    */   public RptEP() {
/* 31 */     super(messages
/* 32 */         .rpt_title(), 
/* 33 */         Arrays.asList((AbstractMap.SimpleEntry<String, RestService>[])new AbstractMap.SimpleEntry[] { new AbstractMap.SimpleEntry<>("AoCommon", aoRptService), new AbstractMap.SimpleEntry<>("AoCommon", ncuService) }), new RestService[] { (RestService)ReportEP.service, (RestService)HomeEP.camService });
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void serviceReady(RestService service, String fmeAddress, int port) {
/* 41 */     if (aoRptService == service) {
/* 42 */       webSocketIp = fmeAddress;
/* 43 */       webSocketPort = port;
/*    */     } 
/* 45 */     super.serviceReady(service, fmeAddress, port);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void allServicesReady() {
/* 50 */     init((Composite)new RptViewer());
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\lib\aoAdmin-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ao\am\RptEP.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */