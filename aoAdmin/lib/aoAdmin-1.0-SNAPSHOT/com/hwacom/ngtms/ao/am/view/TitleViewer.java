/*     */ package com.hwacom.ngtms.ao.am.view;
/*     */ 
/*     */ import com.google.gwt.core.client.GWT;
/*     */ import com.google.gwt.event.dom.client.ClickEvent;
/*     */ import com.google.gwt.event.shared.EventHandler;
/*     */ import com.google.gwt.event.shared.HandlerRegistration;
/*     */ import com.google.gwt.i18n.client.DateTimeFormat;
/*     */ import com.google.gwt.resources.client.ClientBundle;
/*     */ import com.google.gwt.resources.client.CssResource;
/*     */ import com.google.gwt.resources.client.ImageResource;
/*     */ import com.google.gwt.uibinder.client.UiBinder;
/*     */ import com.google.gwt.uibinder.client.UiField;
/*     */ import com.google.gwt.uibinder.client.UiHandler;
/*     */ import com.google.gwt.user.client.Timer;
/*     */ import com.google.gwt.user.client.Window;
/*     */ import com.google.gwt.user.client.ui.Image;
/*     */ import com.google.gwt.user.client.ui.Label;
/*     */ import com.google.gwt.user.client.ui.Widget;
/*     */ import com.hwacom.ngtms.cam.client.HomeEP;
/*     */ import com.hwacom.ngtms.cam.client.event.TitleViewMaskEvent;
/*     */ import com.hwacom.ngtms.common.am.view.UserBasicInfoWindow;
/*     */ import com.hwacom.ngtms.common.shared.dto.UserDTO;
/*     */ import com.hwacom.ngtms.hcce.am.factory.ClientFactory;
/*     */ import com.sencha.gxt.widget.core.client.Composite;
/*     */ import java.util.Date;
/*     */ import java.util.logging.Logger;
/*     */ import org.fusesource.restygwt.client.Method;
/*     */ import org.fusesource.restygwt.client.MethodCallback;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TitleViewer
/*     */   extends Composite
/*     */ {
/*  38 */   private static Logger logger = Logger.getLogger("TitleView");
/*     */   
/*  40 */   private static TitleViewUiBinder uiBinder = (TitleViewUiBinder)GWT.create(TitleViewUiBinder.class);
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
/*  72 */   private final TitleResources titleResources = (TitleResources)GWT.create(TitleResources.class);
/*     */   
/*  74 */   private ClientFactory clientFactory = (ClientFactory)GWT.create(ClientFactory.class);
/*     */   
/*     */   private HandlerRegistration userImageHandlerRegistration;
/*     */   
/*     */   private Timer clockTimer;
/*     */   
/*  80 */   private DateTimeFormat dateForamt = DateTimeFormat.getFormat("yyyy/MM/dd");
/*     */   
/*  82 */   private DateTimeFormat timeForamt = DateTimeFormat.getFormat("HH:mm");
/*     */   @UiField
/*     */   Style style;
/*     */   @UiField
/*     */   Label dateLabel;
/*     */   @UiField
/*     */   Label timeLabel;
/*     */   @UiField
/*     */   Label userName;
/*     */   @UiField
/*     */   Image userImage;
/*     */   
/*     */   public TitleViewer() {
/*  95 */     initWidget((Widget)uiBinder.createAndBindUi(this));
/*  96 */     this.titleResources.title().ensureInjected();
/*  97 */     createClockTimer();
/*  98 */     init();
/*  99 */     addEventHandlers();
/* 100 */     this.clientFactory
/* 101 */       .getEventBus()
/* 102 */       .addHandler(TitleViewMaskEvent.TYPE, (EventHandler)new TitleViewMaskEvent.TitleViewMaskEventHandler()
/*     */         {
/*     */           
/*     */           public void onMaskFired(TitleViewMaskEvent event)
/*     */           {
/* 107 */             TitleViewer.this.stopClockTimer();
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   private void addEventHandlers() {
/* 113 */     this
/* 114 */       .userImageHandlerRegistration = this.userImage.addClickHandler(event -> {
/*     */           UserBasicInfoWindow window = new UserBasicInfoWindow();
/*     */           window.show();
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   private void createClockTimer() {
/* 122 */     this.clockTimer = new Timer()
/*     */       {
/*     */         public void run()
/*     */         {
/* 126 */           TitleViewer.this.showClock();
/*     */         }
/*     */       };
/* 129 */     this.clockTimer.scheduleRepeating(60000);
/* 130 */     this.clockTimer.run();
/*     */   }
/*     */   
/*     */   private void stopClockTimer() {
/* 134 */     if (this.clockTimer != null && this.clockTimer.isRunning()) {
/* 135 */       this.clockTimer.cancel();
/*     */     }
/*     */   }
/*     */   
/*     */   private void showClock() {
/* 140 */     Date now = new Date();
/* 141 */     this.dateLabel.setText(this.dateForamt.format(now));
/* 142 */     this.timeLabel.setText(this.timeForamt.format(now));
/*     */   }
/*     */   
/*     */   private void init() {
/* 146 */     logger.info("TitleView init ... ");
/* 147 */     HomeEP.camService.getUserDTO(new MethodCallback<UserDTO>()
/*     */         {
/*     */           public void onSuccess(Method method, UserDTO result)
/*     */           {
/* 151 */             TitleViewer.logger.info("TitleView getUserDTO onSuccess result = ");
/* 152 */             if (result != null && result.getName() != null) {
/* 153 */               TitleViewer.logger.info("UserDTO name =>" + result.getName());
/* 154 */               TitleViewer.this.userName.setText(result.getName());
/*     */             } else {
/* 156 */               TitleViewer.logger.info("TitleView getUserDTO userName is null! ");
/* 157 */               TitleViewer.this.userName.setText("");
/*     */             } 
/*     */           }
/*     */ 
/*     */           
/*     */           public void onFailure(Method method, Throwable caught) {
/* 163 */             TitleViewer.logger.info("TitleView loginService.getUserDTO failed." + caught);
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   public void disableUserImageClick() {
/* 169 */     this.userImage.removeStyleName(this.style.overCursor());
/* 170 */     this.userImageHandlerRegistration.removeHandler();
/*     */   }
/*     */   
/*     */   @UiHandler({"loginOutImage"})
/*     */   public void loginOutImage(ClickEvent event) {
/* 175 */     Window.Location.assign(GWT.getHostPageBaseURL() + "logoutAo.do");
/*     */   }
/*     */ 
/*     */   
/*     */   protected void onUnload() {
/* 180 */     stopClockTimer();
/* 181 */     super.onUnload();
/*     */   }
/*     */   
/*     */   static interface TitleViewUiBinder extends UiBinder<Widget, TitleViewer> {}
/*     */   
/*     */   static interface Style extends CssResource {
/*     */     String overCursor();
/*     */   }
/*     */   
/*     */   static interface TitleStyle extends CssResource {
/*     */     String titleLeft();
/*     */     
/*     */     String titleCenter();
/*     */     
/*     */     String titleRight();
/*     */     
/*     */     String titleColor();
/*     */     
/*     */     String itemStyle();
/*     */     
/*     */     String userText();
/*     */   }
/*     */   
/*     */   static interface TitleResources extends ClientBundle {
/*     */     TitleViewer.TitleStyle title();
/*     */     
/*     */     ImageResource headerLeft();
/*     */     
/*     */     ImageResource headerCenter();
/*     */     
/*     */     ImageResource headerRight();
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\lib\aoAdmin-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ao\am\view\TitleViewer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */