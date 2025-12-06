/*    */ package com.hwacom.ngtms.ao.am;
/*    */ 
/*    */ import com.google.gwt.core.client.GWT;
/*    */ import com.hwacom.ngtms.ao.am.view.Messages;
/*    */ import com.hwacom.ngtms.cam.restygwt.CommonRestService;
/*    */ import com.hwacom.ngtms.common.am.AmEntryPoint;
/*    */ import java.util.AbstractMap;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Arrays;
/*    */ import java.util.List;
/*    */ import org.fusesource.restygwt.client.RestService;
/*    */ 
/*    */ public abstract class AoEntryPoint
/*    */   extends AmEntryPoint {
/* 15 */   public static final Messages messages = (Messages)GWT.create(Messages.class);
/*    */   
/* 17 */   public static CommonRestService camService = (CommonRestService)GWT.create(CommonRestService.class);
/*    */   
/*    */   public AoEntryPoint(String windowTitle, RestService... services) {
/* 20 */     super(windowTitle, addAdditionalServices(services));
/*    */   }
/*    */   
/*    */   private static RestService[] addAdditionalServices(RestService[] services) {
/* 24 */     List<RestService> list = new ArrayList<>(Arrays.asList(services));
/* 25 */     list.add(camService);
/* 26 */     return list.<RestService>toArray(new RestService[0]);
/*    */   }
/*    */ 
/*    */   
/*    */   public AoEntryPoint(String windowTitle, List<AbstractMap.SimpleEntry<String, RestService>> dedicatedServices) {
/* 31 */     super(windowTitle, dedicatedServices, new RestService[] { (RestService)camService });
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public AoEntryPoint(String windowTitle, List<AbstractMap.SimpleEntry<String, RestService>> dedicatedServices, RestService... services) {
/* 38 */     super(windowTitle, dedicatedServices, addAdditionalServices(services));
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\lib\aoAdmin-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ao\am\AoEntryPoint.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */