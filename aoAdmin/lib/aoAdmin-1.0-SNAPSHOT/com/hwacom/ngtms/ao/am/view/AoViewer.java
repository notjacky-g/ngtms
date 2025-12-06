/*     */ package com.hwacom.ngtms.ao.am.view;
/*     */ 
/*     */ import com.google.gwt.core.client.GWT;
/*     */ import com.google.gwt.event.shared.EventHandler;
/*     */ import com.google.gwt.uibinder.client.UiBinder;
/*     */ import com.google.gwt.uibinder.client.UiField;
/*     */ import com.google.gwt.user.client.ui.Widget;
/*     */ import com.hwacom.ngtms.ao.am.event.AlarmMonitorEvent;
/*     */ import com.hwacom.ngtms.ao.am.event.AlarmMonitorGridByTypeEvent;
/*     */ import com.hwacom.ngtms.ao.am.event.RoomStatusMonitorEvent;
/*     */ import com.hwacom.ngtms.ao.am.images.room.roomMap.RoomMapImages;
/*     */ import com.hwacom.ngtms.ao.am.presenter.AoPresenter;
/*     */ import com.hwacom.ngtms.ao.am.presenter.RoomDeviceConfigSettingPresenter;
/*     */ import com.hwacom.ngtms.ao.shared.dto.AlarmMessageDTO;
/*     */ import com.hwacom.ngtms.cam.client.ui.AmTab;
/*     */ import com.hwacom.ngtms.cam.client.ui.AmTabPanel;
/*     */ import com.hwacom.ngtms.hcce.am.factory.ClientFactory;
/*     */ import com.hwacom.ngtms.room.am.event.RoomDeviceConfigEvent;
/*     */ import com.sencha.gxt.widget.core.client.Composite;
/*     */ import com.sencha.gxt.widget.core.client.TabItemConfig;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import org.vectomatic.dom.svg.ui.SVGResource;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AoViewer
/*     */   extends Composite
/*     */ {
/*  36 */   private static AoViewerUiBinder uiBinder = (AoViewerUiBinder)GWT.create(AoViewerUiBinder.class);
/*     */ 
/*     */ 
/*     */   
/*  40 */   private final ClientFactory clientFactory = (ClientFactory)GWT.create(ClientFactory.class);
/*     */   
/*  42 */   private AoPresenter presenter = new AoPresenter(this);
/*     */   
/*     */   @UiField(provided = true)
/*     */   AmTabPanel mainLayout;
/*     */   
/*     */   @UiField
/*     */   RoomDevicePositionEditViewer roomDevicePositionEditViewer;
/*     */   @UiField
/*     */   RoomDeviceMonitorViewer roomDeviceMonitorViewer;
/*     */   @UiField
/*     */   RoomAnalogRecordViewer roomAnalogRecordViewer;
/*     */   @UiField
/*     */   UserViewer userViewer;
/*     */   @UiField
/*     */   RoleFunctionPermissionViewer roleFunctionPermissionViewer;
/*     */   @UiField
/*     */   RoomStatusMonitorViewer roomStatusMonitorViewer;
/*     */   @UiField
/*     */   NcuCardControlViewer ncuCardControlViewer;
/*  61 */   RoomDeviceConfigSettingViewer roomDeviceConfigSettingViewer = new RoomDeviceConfigSettingViewer();
/*     */   
/*     */   public AoViewer() {
/*  64 */     this.mainLayout = new AmTabPanel()
/*     */       {
/*     */         public void openTab(AmTab tab, TabItemConfig config) {
/*  67 */           super.openTab(tab, config);
/*     */         }
/*     */       };
/*  70 */     initWidget((Widget)uiBinder.createAndBindUi(this));
/*     */     
/*  72 */     Map<String, SVGResource> svgResourceMap = new HashMap<>();
/*  73 */     svgResourceMap.put("caotunRoom", RoomMapImages.INSTANCE.caotunRoom());
/*  74 */     svgResourceMap.put("centralControlCenterRoom1F", RoomMapImages.INSTANCE
/*  75 */         .centralControlCenterRoom1F());
/*  76 */     svgResourceMap.put("centralControlCenterRoom3F", RoomMapImages.INSTANCE
/*  77 */         .centralControlCenterRoom3F());
/*  78 */     svgResourceMap.put("centralControlCenterRoom4F", RoomMapImages.INSTANCE
/*  79 */         .centralControlCenterRoom4F());
/*  80 */     svgResourceMap.put("centralControlCenterRoomB1", RoomMapImages.INSTANCE
/*  81 */         .centralControlCenterRoomB1());
/*  82 */     svgResourceMap.put("changhuaRoom1F", RoomMapImages.INSTANCE.changhuaRoom1F());
/*  83 */     svgResourceMap.put("changhuaRoom2F", RoomMapImages.INSTANCE.changhuaRoom2F());
/*  84 */     svgResourceMap.put("changhuaSystemRoom", RoomMapImages.INSTANCE.changhuaSystemRoom());
/*  85 */     svgResourceMap.put("dajiaRoom1F", RoomMapImages.INSTANCE.dajiaRoom1F());
/*  86 */     svgResourceMap.put("dajiaRoom2F", RoomMapImages.INSTANCE.dajiaRoom2F());
/*  87 */     svgResourceMap.put("dounanRoom1F", RoomMapImages.INSTANCE.dounanRoom1F());
/*  88 */     svgResourceMap.put("dounanRoom2F", RoomMapImages.INSTANCE.dounanRoom2F());
/*  89 */     svgResourceMap.put("dounanRoom4F", RoomMapImages.INSTANCE.dounanRoom4F());
/*  90 */     svgResourceMap.put("eastCaotunRoom", RoomMapImages.INSTANCE.eastCaotunRoom());
/*  91 */     svgResourceMap.put("fengyuanEastRoom3F", RoomMapImages.INSTANCE.fengyuanEastRoom3F());
/*  92 */     svgResourceMap.put("guoxingNO1EastExitRoom1F", RoomMapImages.INSTANCE
/*  93 */         .guoxingNO1EastExitRoom1F());
/*  94 */     svgResourceMap.put("guoxingNO1EastExitRoom2F", RoomMapImages.INSTANCE
/*  95 */         .guoxingNO1EastExitRoom2F());
/*  96 */     svgResourceMap.put("guoxingNO1Tunnel", RoomMapImages.INSTANCE.guoxingNO1Tunnel());
/*  97 */     svgResourceMap.put("guoxingNO1WestExitRoom1F", RoomMapImages.INSTANCE
/*  98 */         .guoxingNO1WestExitRoom1F());
/*  99 */     svgResourceMap.put("guoxingNO1WestExitRoom2F", RoomMapImages.INSTANCE
/* 100 */         .guoxingNO1WestExitRoom2F());
/* 101 */     svgResourceMap.put("guoxingNO1WestExitRoom3F", RoomMapImages.INSTANCE
/* 102 */         .guoxingNO1WestExitRoom3F());
/* 103 */     svgResourceMap.put("guoxingNO1WestExitRoomB1", RoomMapImages.INSTANCE
/* 104 */         .guoxingNO1WestExitRoomB1());
/* 105 */     svgResourceMap.put("guoxingNO2Tunnel", RoomMapImages.INSTANCE.guoxingNO2Tunnel());
/* 106 */     svgResourceMap.put("guoxingNO2WestExitRoom1F", RoomMapImages.INSTANCE
/* 107 */         .guoxingNO2WestExitRoom1F());
/* 108 */     svgResourceMap.put("guoxingNO2WestExitRoom2F", RoomMapImages.INSTANCE
/* 109 */         .guoxingNO2WestExitRoom2F());
/* 110 */     svgResourceMap.put("guoxingNO2WestExitRoom3F", RoomMapImages.INSTANCE
/* 111 */         .guoxingNO2WestExitRoom3F());
/* 112 */     svgResourceMap.put("houlongRoom1F", RoomMapImages.INSTANCE.houlongRoom1F());
/* 113 */     svgResourceMap.put("houlongRoom2F", RoomMapImages.INSTANCE.houlongRoom2F());
/* 114 */     svgResourceMap.put("hsihuRoom1F", RoomMapImages.INSTANCE.hsihuRoom1F());
/* 115 */     svgResourceMap.put("hsihuRoom2F", RoomMapImages.INSTANCE.hsihuRoom2F());
/* 116 */     svgResourceMap.put("linneiRoom1F", RoomMapImages.INSTANCE.linneiRoom1F());
/* 117 */     svgResourceMap.put("linneiRoom2F", RoomMapImages.INSTANCE.linneiRoom2F());
/* 118 */     svgResourceMap.put("miaoliRoom", RoomMapImages.INSTANCE.miaoliRoom());
/* 119 */     svgResourceMap.put("mingjianRoom1F", RoomMapImages.INSTANCE.mingjianRoom1F());
/* 120 */     svgResourceMap.put("mingjianRoom2F", RoomMapImages.INSTANCE.mingjianRoom2F());
/* 121 */     svgResourceMap.put("nantouRoom", RoomMapImages.INSTANCE.nantouRoom());
/* 122 */     svgResourceMap.put("puilEastExitRoom1F", RoomMapImages.INSTANCE.puilEastExitRoom1F());
/* 123 */     svgResourceMap.put("puilEastExitRoom2F", RoomMapImages.INSTANCE.puilEastExitRoom2F());
/* 124 */     svgResourceMap.put("puilEastExitRoom3F", RoomMapImages.INSTANCE.puilEastExitRoom3F());
/* 125 */     svgResourceMap.put("puilTunnel", RoomMapImages.INSTANCE.puilTunnel());
/* 126 */     svgResourceMap.put("shimizuRoom1F", RoomMapImages.INSTANCE.shimizuRoom1F());
/* 127 */     svgResourceMap.put("shimizuRoom2F", RoomMapImages.INSTANCE.shimizuRoom2F());
/* 128 */     svgResourceMap.put("taianRoom1F", RoomMapImages.INSTANCE.taianRoom1F());
/* 129 */     svgResourceMap.put("taianRoom2F", RoomMapImages.INSTANCE.taianRoom2F());
/* 130 */     svgResourceMap.put("taianRoom4F", RoomMapImages.INSTANCE.taianRoom4F());
/* 131 */     svgResourceMap.put("yuanlinRoom1F", RoomMapImages.INSTANCE.yuanlinRoom1F());
/* 132 */     svgResourceMap.put("yuanlinRoom2F", RoomMapImages.INSTANCE.yuanlinRoom2F());
/* 133 */     svgResourceMap.put("yuanlinRoom4F", RoomMapImages.INSTANCE.yuanlinRoom4F());
/* 134 */     svgResourceMap.put("zhonggangCreekRoom1F", RoomMapImages.INSTANCE.zhonggangCreekRoom1F());
/* 135 */     svgResourceMap.put("zhonggangCreekRoom2F", RoomMapImages.INSTANCE.zhonggangCreekRoom2F());
/* 136 */     svgResourceMap.put("zhonggangCreekRoom4F", RoomMapImages.INSTANCE.zhonggangCreekRoom4F());
/* 137 */     svgResourceMap.put("guoxingNO1AuxliaryRoom", RoomMapImages.INSTANCE.guoxingNO1AuxliaryRoom());
/* 138 */     svgResourceMap.put("puilMaintenanceRoom1F", RoomMapImages.INSTANCE.puilMaintenanceRoom1F());
/* 139 */     svgResourceMap.put("puilMaintenanceRoom2F", RoomMapImages.INSTANCE.puilMaintenanceRoom2F());
/* 140 */     this.roomDevicePositionEditViewer.setRoomBackground(svgResourceMap);
/* 141 */     this.roomDeviceMonitorViewer.setRoomBackground(svgResourceMap);
/*     */     
/* 143 */     RoomDeviceConfigSettingPresenter roomDeviceConfigSettingPresenter = new RoomDeviceConfigSettingPresenter(this.roomDeviceConfigSettingViewer);
/*     */     
/* 145 */     this.roomDeviceConfigSettingViewer.setPresenter(roomDeviceConfigSettingPresenter);
/* 146 */     this.roomDeviceConfigSettingViewer.setId("roomDeviceConfigSettingViewer");
/* 147 */     this.roomDeviceConfigSettingViewer.setTabTitle("監控點設定");
/*     */     
/* 149 */     this.clientFactory
/* 150 */       .getEventBus()
/* 151 */       .addHandler(RoomDeviceConfigEvent.TYPE, (EventHandler)new DefaultRoomDeviceConfigEventHandler());
/*     */     
/* 153 */     this.clientFactory
/* 154 */       .getEventBus()
/* 155 */       .addHandler(RoomStatusMonitorEvent.TYPE, (EventHandler)new DefaultRoomStatusMonitorEventHandler());
/*     */     
/* 157 */     this.clientFactory
/* 158 */       .getEventBus()
/* 159 */       .addHandler(AlarmMonitorEvent.TYPE, (EventHandler)new DefaultAlarmMonitorEventHandler());
/*     */     
/* 161 */     this.clientFactory
/* 162 */       .getEventBus()
/* 163 */       .addHandler(AlarmMonitorGridByTypeEvent.TYPE, (EventHandler)new DefaultAlarmMonitorGridByTypeEventHandler());
/*     */   }
/*     */ 
/*     */   
/*     */   public void setPresenter(AoPresenter presenter) {
/* 168 */     this.presenter = presenter;
/*     */   }
/*     */   
/*     */   public UserViewer getUserViewer() {
/* 172 */     return this.userViewer;
/*     */   }
/*     */   
/*     */   public RoleFunctionPermissionViewer getRoleFunctionPermissionViewer() {
/* 176 */     return this.roleFunctionPermissionViewer;
/*     */   }
/*     */   
/*     */   static interface AoViewerUiBinder
/*     */     extends UiBinder<Widget, AoViewer> {}
/*     */   
/*     */   class DefaultRoomDeviceConfigEventHandler
/*     */     implements RoomDeviceConfigEvent.RoomDeviceConfigEventHandler {
/*     */     public void onClick(RoomDeviceConfigEvent event) {
/* 185 */       RoomDeviceConfigSettingViewer viewer = (RoomDeviceConfigSettingViewer)AoViewer.this.mainLayout.findItem("roomDeviceConfigSettingViewer", true);
/* 186 */       if (viewer == null) {
/* 187 */         viewer = AoViewer.this.roomDeviceConfigSettingViewer;
/* 188 */         AoViewer.this.mainLayout.add((Widget)viewer, new TabItemConfig(viewer.getTabTitle(), viewer.isClosable()));
/*     */       } 
/* 190 */       AoViewer.this.mainLayout.setActive(viewer);
/* 191 */       AoViewer.this.roomDeviceConfigSettingViewer.receiveSettingEvent((String)event.getSource());
/*     */     }
/*     */ 
/*     */     
/*     */     public void onAnalogRecord(RoomDeviceConfigEvent event) {
/* 196 */       AoViewer.this.mainLayout.openTab(AoViewer.this.roomAnalogRecordViewer, new TabItemConfig(AoViewer.this.roomAnalogRecordViewer
/*     */ 
/*     */             
/* 199 */             .getTabTitle(), AoViewer.this.roomAnalogRecordViewer.isClosable()));
/* 200 */       AoViewer.this.roomAnalogRecordViewer.receiveEvent((String)event.getSource());
/*     */     }
/*     */   }
/*     */   
/*     */   class DefaultRoomStatusMonitorEventHandler
/*     */     implements RoomStatusMonitorEvent.RoomStatusMonitorEventHandler
/*     */   {
/*     */     public void onClick(RoomStatusMonitorEvent event) {
/* 208 */       AoViewer.this.mainLayout.openTab(AoViewer.this.roomDeviceMonitorViewer, new TabItemConfig(AoViewer.this.roomDeviceMonitorViewer
/*     */ 
/*     */             
/* 211 */             .getTabTitle(), AoViewer.this.roomDeviceMonitorViewer.isClosable()));
/* 212 */       AoViewer.this.roomDeviceMonitorViewer.receiveEvent((String)event.getSource());
/*     */     }
/*     */   }
/*     */   
/*     */   class DefaultAlarmMonitorEventHandler
/*     */     implements AlarmMonitorEvent.AlarmMonitorEventHandler
/*     */   {
/*     */     public void onClick(AlarmMonitorEvent event) {
/* 220 */       AlarmMessageDTO dto = (AlarmMessageDTO)event.getSource();
/* 221 */       AoViewer.this.presenter.confirmAlarm(dto);
/*     */     }
/*     */   }
/*     */   
/*     */   class DefaultAlarmMonitorGridByTypeEventHandler
/*     */     implements AlarmMonitorGridByTypeEvent.AlarmMonitorGridByTypeEventHandler
/*     */   {
/*     */     public void onAlert(AlarmMonitorGridByTypeEvent event) {
/* 229 */       AoViewer.this.roomStatusMonitorViewer.receiveEvent();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\lib\aoAdmin-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ao\am\view\AoViewer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */