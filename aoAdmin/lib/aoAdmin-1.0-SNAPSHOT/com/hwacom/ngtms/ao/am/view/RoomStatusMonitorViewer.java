/*     */ package com.hwacom.ngtms.ao.am.view;
/*     */ import com.google.gwt.core.client.GWT;
/*     */ import com.google.gwt.dom.client.Element;
/*     */ import com.google.gwt.dom.client.Node;
/*     */ import com.google.gwt.event.dom.client.MouseDownEvent;
/*     */ import com.google.gwt.event.dom.client.MouseDownHandler;
/*     */ import com.google.gwt.event.shared.GwtEvent;
/*     */ import com.google.gwt.media.client.Audio;
/*     */ import com.google.gwt.uibinder.client.UiBinder;
/*     */ import com.google.gwt.uibinder.client.UiField;
/*     */ import com.google.gwt.uibinder.client.UiHandler;
/*     */ import com.google.gwt.user.client.Timer;
/*     */ import com.google.gwt.user.client.ui.Widget;
/*     */ import com.hwacom.ngtms.ao.am.event.RoomStatusMonitorEvent;
/*     */ import com.hwacom.ngtms.ao.am.images.room.roomStatus.RoomStatusImages;
/*     */ import com.hwacom.ngtms.ao.am.presenter.RoomStatusMonitorPresenter;
/*     */ import com.hwacom.ngtms.ao.shared.dto.AlarmMessageDTO;
/*     */ import com.hwacom.ngtms.ao.shared.dto.RoomInfoDTO;
/*     */ import com.hwacom.ngtms.cam.client.ui.AmTab;
/*     */ import com.hwacom.ngtms.cam.client.ui.component.ScrollFloatContainer;
/*     */ import com.hwacom.ngtms.hcce.am.factory.ClientFactory;
/*     */ import com.sencha.gxt.core.client.dom.XElement;
/*     */ import com.sencha.gxt.core.client.util.Margins;
/*     */ import com.sencha.gxt.widget.core.client.ContentPanel;
/*     */ import com.sencha.gxt.widget.core.client.button.TextButton;
/*     */ import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
/*     */ import com.sencha.gxt.widget.core.client.event.SelectEvent;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.vectomatic.dom.svg.OMElement;
/*     */ import org.vectomatic.dom.svg.OMNode;
/*     */ import org.vectomatic.dom.svg.OMSVGDocument;
/*     */ import org.vectomatic.dom.svg.OMSVGSVGElement;
/*     */ import org.vectomatic.dom.svg.utils.OMSVGParser;
/*     */ 
/*     */ public class RoomStatusMonitorViewer extends AmTab {
/*  39 */   private static RoomStatusMonitorViewerUiBinder uiBinder = (RoomStatusMonitorViewerUiBinder)GWT.create(RoomStatusMonitorViewerUiBinder.class);
/*     */ 
/*     */ 
/*     */   
/*  43 */   private RoomStatusMonitorPresenter presenter = new RoomStatusMonitorPresenter(this);
/*     */   
/*  45 */   private final ClientFactory clientFactory = (ClientFactory)GWT.create(ClientFactory.class);
/*     */   
/*  47 */   private List<String> lineNames = new ArrayList<>();
/*     */   
/*  49 */   private List<RoomInfoDTO> roomList = new ArrayList<>();
/*     */   
/*  51 */   private Map<String, OMSVGSVGElement> roomStatus = new HashMap<>();
/*     */ 
/*     */   
/*  54 */   private String colorRed = "#FF3333";
/*     */   
/*  56 */   private String colorOragne = "#EA7500";
/*     */   
/*  58 */   private String colorGreen = "#2bb92b";
/*     */   
/*  60 */   private String colorYellow = "#d2d20e";
/*     */   
/*  62 */   private String colorGrey = "#D0D0D0";
/*     */   
/*  64 */   private String colorWhite = "#FFFFFF";
/*     */   
/*  66 */   private Timer roomStatusTimer = null;
/*     */   
/*  68 */   private Timer safeStatusTimer = null;
/*     */ 
/*     */   
/*  71 */   private final int SECOND = 60000;
/*     */   
/*  73 */   private final int SAFE_SECOND = 5000;
/*     */   
/*  75 */   private AlarmMonitorGrid.AlarmLogPropertyAccess alarmLogPropertyAccess = (AlarmMonitorGrid.AlarmLogPropertyAccess)GWT.create(AlarmMonitorGrid.AlarmLogPropertyAccess.class);
/*     */   
/*     */   @UiField
/*     */   VerticalLayoutContainer roomValue;
/*     */   @UiField
/*     */   AlarmMonitorGridByType alarmMonitorGrid;
/*     */   @UiField
/*     */   TextButton alarmVoicePlay;
/*     */   @UiField
/*     */   TextButton alarmVoiceStop;
/*  85 */   public Audio alert = Audio.createIfSupported();
/*     */   
/*     */   public RoomStatusMonitorViewer() {
/*  88 */     initWidget((Widget)uiBinder.createAndBindUi(this));
/*  89 */     this.presenter.getRoomLineData();
/*     */   }
/*     */   
/*     */   public void initLineData(List<String> response) {
/*  93 */     if (response == null) {
/*     */       return;
/*     */     }
/*  96 */     this.lineNames = response;
/*  97 */     this.presenter.getRoomInfo();
/*     */   }
/*     */   
/*     */   public void initRoomData(List<RoomInfoDTO> response) {
/* 101 */     if (response == null) {
/*     */       return;
/*     */     }
/* 104 */     this.roomList = response;
/* 105 */     addRoomData();
/*     */   }
/*     */   
/*     */   public void addRoomData() {
/* 109 */     for (String lineName : this.lineNames) {
/* 110 */       ContentPanel panel = new ContentPanel();
/* 111 */       ScrollFloatContainer overview = new ScrollFloatContainer();
/* 112 */       panel.setHeading(lineName);
/* 113 */       panel.setCollapsible(true);
/* 114 */       addRoomConfig(overview, panel.getHeading().asString());
/* 115 */       panel.add((Widget)overview);
/* 116 */       this.roomValue.add((Widget)panel);
/*     */     } 
/* 118 */     this.roomValue.forceLayout();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void addRoomConfig(ScrollFloatContainer overview, String lineName) {
/* 125 */     for (RoomInfoDTO dto : this.roomList) {
/* 126 */       if (dto.getLineName().equals(lineName)) {
/* 127 */         VerticalLayoutContainer statusSvgContainer = new VerticalLayoutContainer();
/* 128 */         OMSVGDocument doorDoc = OMSVGParser.currentDocument();
/* 129 */         OMSVGSVGElement doorSvg = doorDoc.createSVGSVGElement();
/* 130 */         Element doorDiv = null;
/* 131 */         XElement xElement = statusSvgContainer.getElement();
/* 132 */         OMSVGSVGElement svgElement = null;
/* 133 */         if (dto.getLifefacePanel() == 1) {
/* 134 */           svgElement = RoomStatusImages.INSTANCE.roomMonitorStatus().getSvg();
/*     */         } else {
/* 136 */           svgElement = RoomStatusImages.INSTANCE.roomMonitorStatusNoFace().getSvg();
/*     */         } 
/*     */         
/* 139 */         float width = svgElement.getWidth().getBaseVal().getValue();
/* 140 */         float height = svgElement.getHeight().getBaseVal().getValue();
/* 141 */         statusSvgContainer.setWidth((int)width);
/* 142 */         statusSvgContainer.setHeight((int)height);
/* 143 */         statusSvgContainer.getElement().setMargins(new Margins(2, 2, 2, 2));
/*     */         
/* 145 */         OMElement statusColorElement = svgElement.getElementById("statusColor");
/* 146 */         statusColorElement.getElement().getStyle().setProperty("fill", "#9fceef");
/*     */         
/* 148 */         svgElement.setId(dto.getId());
/*     */         
/* 150 */         String roomName = dto.getName();
/* 151 */         if (roomName != null) {
/* 152 */           if (roomName.contains("-")) {
/* 153 */             String[] nameValue = roomName.split("-");
/* 154 */             if (nameValue.length > 0 && !nameValue[0].isEmpty()) {
/* 155 */               OMElement roomName1Element = svgElement.getElementById("name1");
/* 156 */               roomName1Element.getElement().setInnerText(nameValue[0]);
/*     */             } 
/* 158 */             if (nameValue.length > 1 && !nameValue[1].isEmpty()) {
/* 159 */               OMElement roomName2Element = svgElement.getElementById("name2");
/* 160 */               roomName2Element.getElement().setInnerText(nameValue[1]);
/*     */             } 
/*     */           } else {
/* 163 */             OMElement roomName2Element = svgElement.getElementById("name3");
/* 164 */             roomName2Element.getElement().setInnerText(roomName);
/*     */           } 
/*     */         }
/* 167 */         if (dto.getLifefacePanel() == 1) {
/*     */           
/* 169 */           OMElement lifeFace1Element = svgElement.getElementById("lifeFace1");
/* 170 */           lifeFace1Element.getElement().setAttribute("id", dto.getId() + "_lifeFace1");
/* 171 */           lifeFace1Element.getElement().getStyle().setProperty("fill", this.colorWhite);
/*     */           
/* 173 */           OMElement lifeFace2Element = svgElement.getElementById("lifeFace2");
/* 174 */           lifeFace2Element.getElement().setAttribute("id", dto.getId() + "_lifeFace2");
/* 175 */           lifeFace2Element.getElement().getStyle().setProperty("fill", this.colorWhite);
/*     */           
/* 177 */           OMElement lifeFace3Element = svgElement.getElementById("lifeFace3");
/* 178 */           lifeFace3Element.getElement().setAttribute("id", dto.getId() + "_lifeFace3");
/* 179 */           lifeFace3Element.getElement().getStyle().setProperty("fill", this.colorWhite);
/*     */         } 
/*     */ 
/*     */         
/* 183 */         OMElement monitorElement = svgElement.getElementById("monitor");
/* 184 */         monitorElement.getElement().setAttribute("id", dto.getId() + "_monitor");
/* 185 */         monitorElement.getElement().getStyle().setProperty("fill", this.colorGrey);
/*     */         
/* 187 */         OMElement moName5Element = svgElement.getElementById("name5");
/* 188 */         moName5Element.getElement().setAttribute("id", dto.getId() + "_name5");
/* 189 */         moName5Element.getElement().setInnerText("");
/*     */ 
/*     */         
/* 192 */         OMElement alarmElement = svgElement.getElementById("alarm");
/* 193 */         alarmElement.getElement().setAttribute("id", dto.getId() + "_alarm");
/* 194 */         alarmElement.getElement().getStyle().setProperty("display", "none");
/*     */         
/* 196 */         if (dto.getId().equals("lifeServer")) {
/*     */           
/* 198 */           OMElement moStatusElement = svgElement.getElementById("moStatus");
/* 199 */           moStatusElement.getElement().setAttribute("id", dto.getId() + "_moStatus");
/* 200 */           moStatusElement.getElement().getStyle().setProperty("fill", this.colorGrey);
/*     */           
/* 202 */           OMElement moName4Element = svgElement.getElementById("name4");
/* 203 */           moName4Element.getElement().setAttribute("id", dto.getId() + "_name4");
/* 204 */           moName4Element.getElement().setInnerText("通訊");
/*     */         }
/*     */         else {
/*     */           
/* 208 */           OMElement moStatusElement = svgElement.getElementById("moStatus");
/* 209 */           moStatusElement.getElement().setAttribute("id", dto.getId() + "_moStatus");
/* 210 */           moStatusElement.getElement().getStyle().setProperty("fill", this.colorGrey);
/*     */           
/* 212 */           OMElement moName4Element = svgElement.getElementById("name4");
/* 213 */           moName4Element.getElement().setAttribute("id", dto.getId() + "_name4");
/* 214 */           moName4Element.getElement().setInnerText("環控");
/*     */           
/* 216 */           final OMSVGSVGElement endSvgElement = svgElement;
/* 217 */           doorSvg.addMouseDownHandler(new MouseDownHandler()
/*     */               {
/*     */                 
/*     */                 public void onMouseDown(MouseDownEvent event)
/*     */                 {
/* 222 */                   RoomStatusMonitorViewer.this.clientFactory
/* 223 */                     .getEventBus()
/* 224 */                     .fireEventFromSource((GwtEvent)new RoomStatusMonitorEvent(RoomStatusMonitorEvent.Action.CLICK), endSvgElement
/*     */                       
/* 226 */                       .getId());
/*     */                 }
/*     */               });
/*     */         } 
/* 230 */         doorSvg.appendChild((OMNode)svgElement);
/* 231 */         xElement.appendChild((Node)doorSvg.getElement());
/* 232 */         overview.add((Widget)statusSvgContainer);
/* 233 */         this.roomStatus.put(dto.getId(), doorSvg);
/*     */       } 
/*     */     } 
/* 236 */     overview.forceLayout();
/*     */   }
/*     */   
/*     */   public void refreshStatus(List<RoomInfoDTO> response) {
/* 240 */     if (response == null) {
/*     */       return;
/*     */     }
/*     */     
/* 244 */     for (RoomInfoDTO dto : response) {
/* 245 */       String roomId = dto.getId();
/* 246 */       if (this.roomStatus.containsKey(roomId)) {
/* 247 */         OMElement moStatusElement = ((OMSVGSVGElement)this.roomStatus.get(roomId)).getElementById(roomId + "_moStatus");
/* 248 */         if (dto.getStatus() == 0) {
/* 249 */           moStatusElement.getElement().getStyle().setProperty("fill", this.colorGreen);
/* 250 */         } else if (dto.getStatus() == 1) {
/* 251 */           moStatusElement.getElement().getStyle().setProperty("fill", this.colorRed);
/*     */         } else {
/* 253 */           moStatusElement.getElement().getStyle().setProperty("fill", this.colorGrey);
/*     */         } 
/* 255 */         if (roomId.equals("lifeServer")) {
/* 256 */           OMElement moName4Element = ((OMSVGSVGElement)this.roomStatus.get(roomId)).getElementById(roomId + "_name4");
/* 257 */           if (dto.getStatus() == 0) {
/* 258 */             moName4Element.getElement().setInnerText("通訊連線");
/* 259 */           } else if (dto.getStatus() == 1) {
/* 260 */             moName4Element.getElement().setInnerText("通訊離線");
/*     */           } 
/*     */         } else {
/* 263 */           OMElement moName4Element = ((OMSVGSVGElement)this.roomStatus.get(roomId)).getElementById(roomId + "_name4");
/* 264 */           if (dto.getStatus() == 0) {
/* 265 */             moName4Element.getElement().setInnerText("環控正常");
/* 266 */           } else if (dto.getStatus() == 1) {
/* 267 */             moName4Element.getElement().setInnerText("環控異常");
/*     */           } else {
/* 269 */             moName4Element.getElement().setInnerText("環控斷線");
/*     */           } 
/*     */         } 
/* 272 */         if (dto.getLifefacePanel() == 1) {
/* 273 */           OMElement lifeFace1Element = ((OMSVGSVGElement)this.roomStatus.get(roomId)).getElementById(roomId + "_lifeFace1");
/* 274 */           if (dto.getLifefaceStatus() == 0) {
/* 275 */             lifeFace1Element.getElement().getStyle().setProperty("fill", this.colorWhite);
/*     */           } else {
/* 277 */             lifeFace1Element.getElement().getStyle().setProperty("fill", this.colorRed);
/*     */           } 
/* 279 */           OMElement lifeFace2Element = ((OMSVGSVGElement)this.roomStatus.get(roomId)).getElementById(roomId + "_lifeFace2");
/* 280 */           if (dto.getLifefaceStatus() == 0) {
/* 281 */             lifeFace2Element.getElement().getStyle().setProperty("fill", this.colorWhite);
/*     */           } else {
/* 283 */             lifeFace2Element.getElement().getStyle().setProperty("fill", this.colorRed);
/*     */           } 
/* 285 */           OMElement lifeFace3Element = ((OMSVGSVGElement)this.roomStatus.get(roomId)).getElementById(roomId + "_lifeFace3");
/* 286 */           if (dto.getLifefaceStatus() == 0) {
/* 287 */             lifeFace3Element.getElement().getStyle().setProperty("fill", this.colorWhite); continue;
/*     */           } 
/* 289 */           lifeFace3Element.getElement().getStyle().setProperty("fill", this.colorRed);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void refreshSafeStatus(List<RoomInfoDTO> response) {
/* 297 */     if (response == null) {
/*     */       return;
/*     */     }
/* 300 */     for (RoomInfoDTO dto : response) {
/* 301 */       String roomId = dto.getId();
/* 302 */       if (this.roomStatus.containsKey(roomId)) {
/* 303 */         OMElement monitorElement = ((OMSVGSVGElement)this.roomStatus.get(roomId)).getElementById(roomId + "_monitor");
/* 304 */         if (dto.getSafeStatus() == 0) {
/* 305 */           monitorElement.getElement().getStyle().setProperty("fill", this.colorGreen);
/* 306 */         } else if (dto.getSafeStatus() == 1) {
/* 307 */           monitorElement.getElement().getStyle().setProperty("fill", this.colorYellow);
/* 308 */         } else if (dto.getSafeStatus() == 2) {
/* 309 */           monitorElement.getElement().getStyle().setProperty("fill", this.colorOragne);
/*     */         } else {
/* 311 */           monitorElement.getElement().getStyle().setProperty("fill", this.colorGrey);
/*     */         } 
/* 313 */         OMElement moName5Element = ((OMSVGSVGElement)this.roomStatus.get(roomId)).getElementById(roomId + "_name5");
/* 314 */         if (dto.getSafeStatus() == 0) {
/* 315 */           moName5Element.getElement().setInnerText("保全啟動");
/* 316 */         } else if (dto.getSafeStatus() == 1) {
/* 317 */           moName5Element.getElement().setInnerText("保全解除");
/* 318 */         } else if (dto.getSafeStatus() == 2) {
/* 319 */           moName5Element.getElement().setInnerText("非法入侵");
/*     */         } else {
/* 321 */           moName5Element.getElement().setInnerText("保全斷線");
/*     */         } 
/*     */         
/* 324 */         OMElement alarmElement = ((OMSVGSVGElement)this.roomStatus.get(roomId)).getElementById(roomId + "_alarm");
/* 325 */         if (dto.getAlarmStatus() == 1) {
/* 326 */           alarmElement.getElement().getStyle().setProperty("display", "inline"); continue;
/*     */         } 
/* 328 */         alarmElement.getElement().getStyle().setProperty("display", "none");
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void startRoomStatusTimer() {
/* 335 */     if (this.roomStatusTimer == null) {
/* 336 */       this.roomStatusTimer = new Timer()
/*     */         {
/*     */           public void run()
/*     */           {
/* 340 */             RoomStatusMonitorViewer.this.presenter.getRoomStatus();
/*     */           }
/*     */         };
/*     */     }
/* 344 */     this.roomStatusTimer.scheduleRepeating(60000);
/* 345 */     this.roomStatusTimer.run();
/*     */   }
/*     */   
/*     */   private void startSafeStatusTimer() {
/* 349 */     if (this.safeStatusTimer == null) {
/* 350 */       this.safeStatusTimer = new Timer()
/*     */         {
/*     */           public void run()
/*     */           {
/* 354 */             RoomStatusMonitorViewer.this.presenter.getSafeStatus();
/*     */           }
/*     */         };
/*     */     }
/* 358 */     this.safeStatusTimer.scheduleRepeating(5000);
/* 359 */     this.safeStatusTimer.run();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void onShow() {
/* 364 */     super.onShow();
/* 365 */     startRoomStatusTimer();
/* 366 */     startSafeStatusTimer();
/* 367 */     this.presenter.getAllNonRtnAlarms();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void onHide() {
/* 372 */     super.onHide();
/* 373 */     if (this.roomStatusTimer != null) {
/* 374 */       this.roomStatusTimer.cancel();
/* 375 */       this.roomStatusTimer = null;
/*     */     } 
/* 377 */     if (this.safeStatusTimer != null) {
/* 378 */       this.safeStatusTimer.cancel();
/* 379 */       this.safeStatusTimer = null;
/*     */     } 
/*     */   }
/*     */   
/*     */   public Audio getAlert() {
/* 384 */     return this.alert;
/*     */   }
/*     */ 
/*     */   
/*     */   @UiHandler({"alarmVoicePlay"})
/*     */   public void onAlarmVoicePlay(SelectEvent e) {
/* 390 */     this.alert.setSrc("js/alarmAudio.mp3");
/* 391 */     this.alert.play();
/* 392 */     this.alert.setLoop(true);
/* 393 */     this.alert.setMuted(false);
/*     */   }
/*     */ 
/*     */   
/*     */   @UiHandler({"alarmVoiceStop"})
/*     */   public void onAlarmVoiceStop(SelectEvent e) {
/* 399 */     if (this.alert != null) {
/* 400 */       this.alert.pause();
/* 401 */       this.alert.setLoop(false);
/* 402 */       this.alert.setMuted(true);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setAlarmMonitorGrid(List<AlarmMessageDTO> response) {
/* 407 */     if (response.size() > 0) {
/* 408 */       List<AlarmMessageDTO> typeAlarm = new ArrayList<>();
/* 409 */       for (AlarmMessageDTO dto : response) {
/*     */         
/* 411 */         if (dto.getDisplayName().contains("火警")) {
/* 412 */           typeAlarm.add(dto);
/*     */         }
/* 414 */         if (dto.getDisplayName().contains("發電機設備")) {
/* 415 */           typeAlarm.add(dto);
/*     */         }
/* 417 */         if (dto.getDisplayName().contains("三相主電源開關")) {
/* 418 */           typeAlarm.add(dto);
/*     */         }
/* 420 */         this.alarmMonitorGrid.setListStore(typeAlarm);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void receiveEvent() {
/* 426 */     this.alert.setSrc("js/alarmAudio.mp3");
/* 427 */     this.alert.play();
/* 428 */     this.alert.setLoop(true);
/* 429 */     this.alert.setMuted(false);
/*     */   }
/*     */   
/*     */   static interface RoomStatusMonitorViewerUiBinder extends UiBinder<Widget, RoomStatusMonitorViewer> {}
/*     */ }


/* Location:              C:\User\\user\Desktop\lib\aoAdmin-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ao\am\view\RoomStatusMonitorViewer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */