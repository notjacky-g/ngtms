/*     */ package com.hwacom.ngtms.ao.am;
/*     */ 
/*     */ import com.google.gwt.core.client.GWT;
/*     */ import com.google.gwt.event.shared.HandlerRegistration;
/*     */ import com.google.gwt.user.client.Window;
/*     */ import com.hwacom.ngtms.ao.am.restygwt.AdRestService;
/*     */ import com.hwacom.ngtms.ao.am.restygwt.AoAlarmViewerRestService;
/*     */ import com.hwacom.ngtms.ao.am.restygwt.AoNcuViewerRestService;
/*     */ import com.hwacom.ngtms.ao.am.restygwt.AoViewerRestService;
/*     */ import com.hwacom.ngtms.ao.am.view.AoViewer;
/*     */ import com.hwacom.ngtms.ao.am.view.Messages;
/*     */ import com.hwacom.ngtms.ao.am.view.UserViewer;
/*     */ import com.hwacom.ngtms.cam.client.HomeEP;
/*     */ import com.hwacom.ngtms.common.am.AccountEP;
/*     */ import com.hwacom.ngtms.common.am.NotificationCallback;
/*     */ import com.hwacom.ngtms.common.am.restygwt.AccountRestService;
/*     */ import com.hwacom.ngtms.hcce.am.factory.ClientFactory;
/*     */ import com.hwacom.ngtms.pd.am.PdEP;
/*     */ import com.hwacom.ngtms.pd.am.restygwt.PdCommonRestService;
/*     */ import com.hwacom.ngtms.room.am.RoomEP;
/*     */ import com.hwacom.ngtms.room.am.restygwt.RoomCommonRestService;
/*     */ import com.sencha.gxt.widget.core.client.Composite;
/*     */ import com.sencha.gxt.widget.core.client.Dialog;
/*     */ import com.sencha.gxt.widget.core.client.box.ConfirmMessageBox;
/*     */ import com.sencha.gxt.widget.core.client.button.TextButton;
/*     */ import com.sencha.gxt.widget.core.client.event.DialogHideEvent;
/*     */ import com.sencha.gxt.widget.core.client.event.SelectEvent;
/*     */ import java.util.AbstractMap;
/*     */ import java.util.Arrays;
/*     */ import java.util.function.Consumer;
/*     */ import org.fusesource.restygwt.client.Method;
/*     */ import org.fusesource.restygwt.client.MethodCallback;
/*     */ import org.fusesource.restygwt.client.RestService;
/*     */ import org.fusesource.restygwt.client.RestServiceProxy;
/*     */ 
/*     */ public class AoEP extends AoEntryPoint {
/*  37 */   public static Messages messages = (Messages)GWT.create(Messages.class);
/*     */   
/*  39 */   public static final AoViewerRestService aoService = (AoViewerRestService)GWT.create(AoViewerRestService.class);
/*     */   
/*  41 */   public static final AoNcuViewerRestService ncuService = (AoNcuViewerRestService)GWT.create(AoNcuViewerRestService.class);
/*     */ 
/*     */   
/*  44 */   public static final AoAlarmViewerRestService aoAlarmService = (AoAlarmViewerRestService)GWT.create(AoAlarmViewerRestService.class);
/*     */   
/*  46 */   public static AdRestService adService = (AdRestService)GWT.create(AdRestService.class);
/*     */   
/*  48 */   public static AccountRestService accountService = (AccountRestService)GWT.create(AccountRestService.class);
/*     */   
/*  50 */   private ClientFactory clientFactory = (ClientFactory)GWT.create(ClientFactory.class);
/*     */   
/*     */   private HandlerRegistration handlerRegistration;
/*     */   
/*     */   public AoEP() {
/*  55 */     super(messages
/*  56 */         .windowTitle(), 
/*  57 */         Arrays.asList((AbstractMap.SimpleEntry<String, RestService>[])new AbstractMap.SimpleEntry[] { new AbstractMap.SimpleEntry<>("AoCommon", aoService), new AbstractMap.SimpleEntry<>("AoCommon", adService), new AbstractMap.SimpleEntry<>("AoCommon", ncuService), new AbstractMap.SimpleEntry<>("AoCommon", aoAlarmService), new AbstractMap.SimpleEntry<>("Common", accountService), new AbstractMap.SimpleEntry<>("Pd", PdEP.pdCommonService), new AbstractMap.SimpleEntry<>("Room", RoomEP.commonService) }), new RestService[] { (RestService)HomeEP.camService });
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
/*     */   protected void serviceReady(RestService service, String fmeAddress, int port) {
/*  70 */     if (aoService == service) {
/*  71 */       webSocketIp = fmeAddress;
/*  72 */       webSocketPort = port;
/*     */     } 
/*  74 */     super.serviceReady(service, fmeAddress, port);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void allServicesReady() {
/*  79 */     ((RestServiceProxy)AccountEP.accountService)
/*  80 */       .setResource(((RestServiceProxy)accountService).getResource());
/*  81 */     AoViewer viewer = new AoViewer();
/*  82 */     customizeAccountViewer(viewer);
/*  83 */     init((Composite)viewer);
/*     */   }
/*     */   
/*     */   private void customizeAccountViewer(AoViewer viewer) {
/*  87 */     UserViewer userViewer = viewer.getUserViewer();
/*  88 */     TextButton button = new TextButton("AD同步");
/*  89 */     button.addSelectHandler(selectEvent -> {
/*     */           ConfirmMessageBox confirmMessageBox = new ConfirmMessageBox("AD同步", "確定同步？");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           confirmMessageBox.addDialogHideHandler(());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           confirmMessageBox.show();
/*     */         });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 112 */     userViewer.addButtonInGridContentPanel(button);
/* 113 */     viewer.getRoleFunctionPermissionViewer().hideRolePanel();
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\lib\aoAdmin-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ao\am\AoEP.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */