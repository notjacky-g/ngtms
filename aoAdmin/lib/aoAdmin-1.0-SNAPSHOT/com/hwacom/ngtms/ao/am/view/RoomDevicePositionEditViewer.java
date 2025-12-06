/*     */ package com.hwacom.ngtms.ao.am.view;
/*     */ 
/*     */ import com.google.gwt.core.client.GWT;
/*     */ import com.google.gwt.dom.client.Element;
/*     */ import com.google.gwt.dom.client.Node;
/*     */ import com.google.gwt.event.dom.client.MouseDownEvent;
/*     */ import com.google.gwt.event.dom.client.MouseDownHandler;
/*     */ import com.google.gwt.event.dom.client.MouseEvent;
/*     */ import com.google.gwt.event.dom.client.MouseMoveEvent;
/*     */ import com.google.gwt.event.dom.client.MouseMoveHandler;
/*     */ import com.google.gwt.event.dom.client.MouseUpEvent;
/*     */ import com.google.gwt.event.dom.client.MouseUpHandler;
/*     */ import com.google.gwt.event.shared.EventHandler;
/*     */ import com.google.gwt.uibinder.client.UiBinder;
/*     */ import com.google.gwt.uibinder.client.UiField;
/*     */ import com.google.gwt.uibinder.client.UiHandler;
/*     */ import com.google.gwt.user.client.ui.Widget;
/*     */ import com.hwacom.ngtms.ao.am.images.room.roomDevice.RoomDeviceImages;
/*     */ import com.hwacom.ngtms.ao.am.presenter.RoomDevicePositionEditPresenter;
/*     */ import com.hwacom.ngtms.c.shared.dto.DeviceSvgPositionConfigDTO;
/*     */ import com.hwacom.ngtms.cam.client.ui.AmTab;
/*     */ import com.hwacom.ngtms.hcce.am.factory.ClientFactory;
/*     */ import com.hwacom.ngtms.room.shared.dto.RoomDeviceSubLocationConfigDTO;
/*     */ import com.sencha.gxt.core.client.Style;
/*     */ import com.sencha.gxt.core.client.ValueProvider;
/*     */ import com.sencha.gxt.data.shared.ListStore;
/*     */ import com.sencha.gxt.data.shared.ModelKeyProvider;
/*     */ import com.sencha.gxt.data.shared.PropertyAccess;
/*     */ import com.sencha.gxt.widget.core.client.ContentPanel;
/*     */ import com.sencha.gxt.widget.core.client.event.RowClickEvent;
/*     */ import com.sencha.gxt.widget.core.client.event.SelectEvent;
/*     */ import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
/*     */ import com.sencha.gxt.widget.core.client.grid.ColumnModel;
/*     */ import com.sencha.gxt.widget.core.client.grid.Grid;
/*     */ import java.util.AbstractMap;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.Comparator;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.vectomatic.dom.svg.OMElement;
/*     */ import org.vectomatic.dom.svg.OMNode;
/*     */ import org.vectomatic.dom.svg.OMSVGDocument;
/*     */ import org.vectomatic.dom.svg.OMSVGElement;
/*     */ import org.vectomatic.dom.svg.OMSVGMatrix;
/*     */ import org.vectomatic.dom.svg.OMSVGPoint;
/*     */ import org.vectomatic.dom.svg.OMSVGSVGElement;
/*     */ import org.vectomatic.dom.svg.ui.SVGResource;
/*     */ import org.vectomatic.dom.svg.utils.DOMHelper;
/*     */ import org.vectomatic.dom.svg.utils.OMSVGParser;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RoomDevicePositionEditViewer
/*     */   extends AmTab
/*     */ {
/*  59 */   private static RoomDevicePositionEditViewerUiBinder uiBinder = (RoomDevicePositionEditViewerUiBinder)GWT.create(RoomDevicePositionEditViewerUiBinder.class);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  64 */   private static GridProperties props = (GridProperties)GWT.create(GridProperties.class);
/*     */   
/*  66 */   private RoomDevicePositionEditPresenter presenter = new RoomDevicePositionEditPresenter(this);
/*     */   
/*  68 */   private final ClientFactory clientFactory = (ClientFactory)GWT.create(ClientFactory.class);
/*     */   
/*     */   @UiField(provided = true)
/*     */   ListStore<RoomDeviceSubLocationConfigDTO> store;
/*     */   
/*     */   @UiField(provided = true)
/*     */   ColumnModel<RoomDeviceSubLocationConfigDTO> cm;
/*     */   
/*     */   @UiField
/*     */   Grid<RoomDeviceSubLocationConfigDTO> grid;
/*     */   
/*     */   @UiField
/*     */   ContentPanel svgContainer;
/*     */   @UiField
/*     */   ContentPanel aboveSvgContainer;
/*  83 */   private Map<String, SVGResource> roomMap = new HashMap<>();
/*     */ 
/*     */   
/*  86 */   private OMSVGDocument doc = OMSVGParser.currentDocument();
/*     */   
/*  88 */   private OMSVGSVGElement svg = this.doc.createSVGSVGElement();
/*  89 */   private Element div = null;
/*     */ 
/*     */   
/*     */   private OMSVGPoint point;
/*     */   
/*     */   private boolean dragging;
/*     */   
/*  96 */   private List<AbstractMap.SimpleEntry<DeviceSvgPositionConfigDTO, OMSVGSVGElement>> deviceSvgList = new ArrayList<>();
/*     */ 
/*     */ 
/*     */   
/*     */   public RoomDevicePositionEditViewer() {
/* 101 */     this.store = new ListStore(props.id());
/* 102 */     this.cm = genColumnModel();
/* 103 */     initWidget((Widget)uiBinder.createAndBindUi(this));
/* 104 */     this.div = (Element)this.svgContainer.getElement();
/* 105 */     this.grid.setHideHeaders(true);
/* 106 */     this.grid.getSelectionModel().setSelectionMode(Style.SelectionMode.SINGLE);
/*     */   }
/*     */   
/*     */   @UiHandler({"grid"})
/*     */   public void rowClick(RowClickEvent event) {
/* 111 */     RoomDeviceSubLocationConfigDTO selected = (RoomDeviceSubLocationConfigDTO)this.grid.getSelectionModel().getSelectedItem();
/* 112 */     fillBackgroundSvg(selected.getBackgroundSvgId());
/*     */   }
/*     */   
/*     */   @UiHandler({"saveButton"})
/*     */   public void onSelectSaveButton(SelectEvent event) {
/* 117 */     List<DeviceSvgPositionConfigDTO> position = new ArrayList<>();
/* 118 */     for (AbstractMap.SimpleEntry<DeviceSvgPositionConfigDTO, OMSVGSVGElement> entry : this.deviceSvgList) {
/* 119 */       DeviceSvgPositionConfigDTO dto = entry.getKey();
/* 120 */       dto.setPositionX(Float.valueOf(((OMSVGSVGElement)entry.getValue()).getX().getBaseVal().getValue()));
/* 121 */       dto.setPositionY(Float.valueOf(((OMSVGSVGElement)entry.getValue()).getY().getBaseVal().getValue()));
/* 122 */       position.add(dto);
/*     */     } 
/* 124 */     this.presenter.saveRoomDevicePositionConfigs(position);
/*     */   }
/*     */   
/*     */   private ColumnModel<RoomDeviceSubLocationConfigDTO> genColumnModel() {
/* 128 */     List<ColumnConfig<RoomDeviceSubLocationConfigDTO, ?>> columnConfigList = new ArrayList<>();
/*     */     
/* 130 */     ColumnConfig<RoomDeviceSubLocationConfigDTO, String> locationName = new ColumnConfig(new ValueProvider<RoomDeviceSubLocationConfigDTO, String>()
/*     */         {
/*     */           
/*     */           public String getValue(RoomDeviceSubLocationConfigDTO dto)
/*     */           {
/* 135 */             if (dto.getSubLocation() != null && dto.getSubLocation() != "") {
/* 136 */               return dto.getLocationName() + "-" + dto.getSubLocation();
/*     */             }
/* 138 */             return dto.getLocationName();
/*     */           }
/*     */ 
/*     */ 
/*     */           
/*     */           public void setValue(RoomDeviceSubLocationConfigDTO object, String value) {}
/*     */ 
/*     */           
/*     */           public String getPath() {
/* 147 */             return "locationName";
/*     */           }
/*     */         },  280, "");
/*     */ 
/*     */     
/* 152 */     locationName.setMenuDisabled(true);
/* 153 */     columnConfigList.add(locationName);
/*     */     
/* 155 */     return new ColumnModel(columnConfigList);
/*     */   }
/*     */   
/*     */   public void fillGridData(List<RoomDeviceSubLocationConfigDTO> response) {
/* 159 */     GWT.log("roomDevicePosition fillGridData....");
/* 160 */     if (this.store.size() <= 0) {
/*     */ 
/*     */       
/* 163 */       Collections.sort(response, new Comparator<RoomDeviceSubLocationConfigDTO>()
/*     */           {
/*     */ 
/*     */             
/*     */             public int compare(RoomDeviceSubLocationConfigDTO o1, RoomDeviceSubLocationConfigDTO o2)
/*     */             {
/* 169 */               return o1.getLocationName().compareTo(o2.getLocationName());
/*     */             }
/*     */           });
/* 172 */       this.store.clear();
/* 173 */       this.store.addAll(response);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void fillBackgroundSvg(String svgName) {
/* 179 */     clearSVGComponent();
/* 180 */     SVGResource resource = getBackgroundSvgResource(svgName);
/* 181 */     if (resource == null) {
/* 182 */       GWT.log("Fill background svg failed, can not get resource, svgName=" + svgName);
/*     */       return;
/*     */     } 
/* 185 */     OMSVGSVGElement svgElement = resource.getSvg();
/*     */     
/* 187 */     this.svg.setViewBox(0.0F, 0.0F, svgElement
/*     */ 
/*     */         
/* 190 */         .getWidth().getBaseVal().getValue(), svgElement
/* 191 */         .getHeight().getBaseVal().getValue());
/* 192 */     this.svg.appendChild((OMNode)svgElement);
/* 193 */     this.div.appendChild((Node)this.svg.getElement());
/* 194 */     this.presenter.queryDevicePositionConfig(svgName);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void fillDeviceSvg(List<DeviceSvgPositionConfigDTO> positionConfigs) {
/* 203 */     for (DeviceSvgPositionConfigDTO config : positionConfigs) {
/* 204 */       SVGResource resource = getDeviceSvgResource(config.getDeviceType());
/* 205 */       if (resource == null) {
/* 206 */         GWT.log("Can not find svg image, deviceType=" + config.getDeviceType());
/*     */         continue;
/*     */       } 
/* 209 */       final OMSVGSVGElement svgElement = resource.getSvg();
/* 210 */       if (svgElement.getElementById("deviceName") != null) {
/* 211 */         OMElement tooltip = svgElement.getElementById("deviceName");
/* 212 */         tooltip.getElement().setInnerText(config.getDeviceName());
/*     */       } 
/*     */       
/* 215 */       if (svgElement.getElementById("displayName") != null) {
/* 216 */         OMElement displayNameElement = svgElement.getElementById("displayName");
/* 217 */         displayNameElement.getElement().setInnerText(config.getDisplayName() + " [狀態]");
/*     */       } 
/* 219 */       svgElement.getX().getBaseVal().setValue(config.getPositionX().floatValue());
/* 220 */       svgElement.getY().getBaseVal().setValue(config.getPositionY().floatValue());
/*     */       
/* 222 */       svgElement.addMouseDownHandler(new MouseDownHandler()
/*     */           {
/*     */             public void onMouseDown(MouseDownEvent event)
/*     */             {
/* 226 */               RoomDevicePositionEditViewer.this.dragging = true;
/* 227 */               RoomDevicePositionEditViewer.this.point = RoomDevicePositionEditViewer.this.getLocalCoordinates((MouseEvent<? extends EventHandler>)event, svgElement);
/* 228 */               DOMHelper.setCaptureElement((OMSVGElement)svgElement, null);
/* 229 */               svgElement.getStyle().setOpacity(0.5D);
/* 230 */               event.stopPropagation();
/* 231 */               event.preventDefault();
/*     */             }
/*     */           });
/* 234 */       svgElement.addMouseUpHandler(new MouseUpHandler()
/*     */           {
/*     */             public void onMouseUp(MouseUpEvent event)
/*     */             {
/* 238 */               RoomDevicePositionEditViewer.this.dragging = false;
/* 239 */               DOMHelper.releaseCaptureElement();
/* 240 */               svgElement.getStyle().setOpacity(1.0D);
/* 241 */               event.stopPropagation();
/* 242 */               event.preventDefault();
/*     */             }
/*     */           });
/* 245 */       svgElement.addMouseMoveHandler(new MouseMoveHandler()
/*     */           {
/*     */             public void onMouseMove(MouseMoveEvent event)
/*     */             {
/* 249 */               if (RoomDevicePositionEditViewer.this.dragging) {
/* 250 */                 OMSVGPoint p = RoomDevicePositionEditViewer.this.getLocalCoordinates((MouseEvent<? extends EventHandler>)event, svgElement);
/* 251 */                 float dx = p.getX() - RoomDevicePositionEditViewer.this.point.getX();
/* 252 */                 float dy = p.getY() - RoomDevicePositionEditViewer.this.point.getY();
/* 253 */                 float x = svgElement.getX().getBaseVal().getValue();
/* 254 */                 float y = svgElement.getY().getBaseVal().getValue();
/* 255 */                 svgElement.getX().getBaseVal().setValue(x + dx);
/* 256 */                 svgElement.getY().getBaseVal().setValue(y + dy);
/* 257 */                 RoomDevicePositionEditViewer.this.point = p;
/*     */               } 
/* 259 */               event.stopPropagation();
/* 260 */               event.preventDefault();
/*     */             }
/*     */           });
/* 263 */       this.svg.appendChild((OMNode)svgElement);
/* 264 */       this.deviceSvgList.add(new AbstractMap.SimpleEntry<>(config, svgElement));
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setRoomBackground(Map<String, SVGResource> svgResourceMap) {
/* 269 */     this.roomMap.putAll(svgResourceMap);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private SVGResource getBackgroundSvgResource(String svgName) {
/* 278 */     if (svgName == null) {
/* 279 */       return null;
/*     */     }
/* 281 */     return this.roomMap.get(svgName);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private SVGResource getDeviceSvgResource(String deviceType) {
/* 290 */     if (deviceType.equals("ROOM_DEVICE_GROUP"))
/* 291 */       return RoomDeviceImages.INSTANCE.group(); 
/* 292 */     if (deviceType.equals("cardReader")) {
/* 293 */       return RoomDeviceImages.INSTANCE.doorSingal();
/*     */     }
/* 295 */     return RoomDeviceImages.INSTANCE.inputSingal();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private OMSVGPoint getLocalCoordinates(MouseEvent<? extends EventHandler> e, OMSVGSVGElement svgElement) {
/* 302 */     OMSVGPoint p = this.svg.createSVGPoint(e.getClientX(), e.getClientY());
/* 303 */     OMSVGMatrix m = this.svg.getScreenCTM().inverse();
/* 304 */     return p.matrixTransform(m);
/*     */   }
/*     */ 
/*     */   
/*     */   private void clearSVGComponent() {
/* 309 */     this.div.removeAllChildren();
/* 310 */     this.svg = this.doc.createSVGSVGElement();
/* 311 */     this.deviceSvgList = new ArrayList<>();
/*     */   }
/*     */   
/*     */   static interface RoomDevicePositionEditViewerUiBinder extends UiBinder<Widget, RoomDevicePositionEditViewer> {}
/*     */   
/*     */   static interface GridProperties extends PropertyAccess<RoomDeviceSubLocationConfigDTO> {
/*     */     ModelKeyProvider<RoomDeviceSubLocationConfigDTO> id();
/*     */     
/*     */     ValueProvider<RoomDeviceSubLocationConfigDTO, String> locationName();
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\lib\aoAdmin-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ao\am\view\RoomDevicePositionEditViewer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */