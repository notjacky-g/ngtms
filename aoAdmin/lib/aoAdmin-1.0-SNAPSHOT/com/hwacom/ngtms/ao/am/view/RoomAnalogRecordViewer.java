/*     */ package com.hwacom.ngtms.ao.am.view;
/*     */ 
/*     */ import com.google.gwt.core.client.GWT;
/*     */ import com.google.gwt.event.logical.shared.SelectionEvent;
/*     */ import com.google.gwt.i18n.client.DateTimeFormat;
/*     */ import com.google.gwt.uibinder.client.UiBinder;
/*     */ import com.google.gwt.uibinder.client.UiField;
/*     */ import com.google.gwt.uibinder.client.UiHandler;
/*     */ import com.google.gwt.user.client.ui.Widget;
/*     */ import com.hwacom.ngtms.ao.am.presenter.RoomAnalogRecordPresenter;
/*     */ import com.hwacom.ngtms.ao.shared.dto.AnalogyDataDTO;
/*     */ import com.hwacom.ngtms.ao.shared.dto.RoomAnalogRecordDTO;
/*     */ import com.hwacom.ngtms.ao.shared.dto.RoomAnalogRecordQueryParamDTO;
/*     */ import com.hwacom.ngtms.ao.shared.dto.RoomAnalogTreeDTO;
/*     */ import com.hwacom.ngtms.cam.client.ui.AmTab;
/*     */ import com.hwacom.ngtms.cam.client.ui.dnd.AddType;
/*     */ import com.hwacom.ngtms.common.am.view.DateRangePicker;
/*     */ import com.sencha.gxt.chart.client.chart.Chart;
/*     */ import com.sencha.gxt.chart.client.chart.Legend;
/*     */ import com.sencha.gxt.chart.client.chart.axis.Axis;
/*     */ import com.sencha.gxt.chart.client.chart.axis.CategoryAxis;
/*     */ import com.sencha.gxt.chart.client.chart.axis.NumericAxis;
/*     */ import com.sencha.gxt.chart.client.chart.series.LineSeries;
/*     */ import com.sencha.gxt.chart.client.chart.series.Series;
/*     */ import com.sencha.gxt.chart.client.chart.series.SeriesLabelProvider;
/*     */ import com.sencha.gxt.chart.client.chart.series.SeriesToolTipConfig;
/*     */ import com.sencha.gxt.chart.client.draw.Color;
/*     */ import com.sencha.gxt.chart.client.draw.RGB;
/*     */ import com.sencha.gxt.chart.client.draw.sprite.TextSprite;
/*     */ import com.sencha.gxt.core.client.ValueProvider;
/*     */ import com.sencha.gxt.data.shared.LabelProvider;
/*     */ import com.sencha.gxt.data.shared.ListStore;
/*     */ import com.sencha.gxt.data.shared.ModelKeyProvider;
/*     */ import com.sencha.gxt.data.shared.PropertyAccess;
/*     */ import com.sencha.gxt.data.shared.TreeStore;
/*     */ import com.sencha.gxt.dnd.core.client.DND;
/*     */ import com.sencha.gxt.dnd.core.client.DndDragStartEvent;
/*     */ import com.sencha.gxt.dnd.core.client.DndDropEvent;
/*     */ import com.sencha.gxt.dnd.core.client.DropTarget;
/*     */ import com.sencha.gxt.dnd.core.client.GridDragSource;
/*     */ import com.sencha.gxt.dnd.core.client.TreeDragSource;
/*     */ import com.sencha.gxt.widget.core.client.ContentPanel;
/*     */ import com.sencha.gxt.widget.core.client.event.BeforeShowContextMenuEvent;
/*     */ import com.sencha.gxt.widget.core.client.event.SelectEvent;
/*     */ import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
/*     */ import com.sencha.gxt.widget.core.client.grid.ColumnModel;
/*     */ import com.sencha.gxt.widget.core.client.grid.Grid;
/*     */ import com.sencha.gxt.widget.core.client.info.Info;
/*     */ import com.sencha.gxt.widget.core.client.menu.Item;
/*     */ import com.sencha.gxt.widget.core.client.menu.Menu;
/*     */ import com.sencha.gxt.widget.core.client.menu.MenuItem;
/*     */ import com.sencha.gxt.widget.core.client.tree.Tree;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Date;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RoomAnalogRecordViewer
/*     */   extends AmTab
/*     */ {
/*  67 */   private static RoomAnalogRecordViewerUiBinder uiBinder = (RoomAnalogRecordViewerUiBinder)GWT.create(RoomAnalogRecordViewerUiBinder.class);
/*     */ 
/*     */ 
/*     */   
/*  71 */   private static final RoomAnalogTreeProperties props = (RoomAnalogTreeProperties)GWT.create(RoomAnalogTreeProperties.class);
/*     */ 
/*     */   
/*  74 */   private final RoomAnalogRecordPropertyAccess analogRecordProps = (RoomAnalogRecordPropertyAccess)GWT.create(RoomAnalogRecordPropertyAccess.class);
/*     */   
/*  76 */   private RoomAnalogRecordPresenter presenter = new RoomAnalogRecordPresenter(this);
/*     */   
/*  78 */   final long HOURS = 3600000L;
/*     */ 
/*     */   
/*  81 */   private DateTimeFormat dateFormatter = DateTimeFormat.getFormat("HH:mm");
/*     */ 
/*     */   
/*  84 */   private String[] colorArray = new String[] { "#0059b3", "#009900", "#b30000", "#5900b3", "#00aeb3", "#00b30c", "#b1b300", "#b36c00", "#9f00b3", "#b3005a" };
/*     */   
/*     */   @UiField
/*     */   Tree<RoomAnalogTreeDTO, String> tree;
/*     */   
/*     */   @UiField(provided = true)
/*     */   TreeStore<RoomAnalogTreeDTO> treeStore;
/*     */   
/*     */   @UiField(provided = true)
/*     */   ValueProvider<RoomAnalogTreeDTO, String> treeValueProvider;
/*     */   
/*     */   @UiField
/*     */   Grid<RoomAnalogTreeDTO> grid;
/*     */   
/*     */   @UiField(provided = true)
/*     */   ListStore<RoomAnalogTreeDTO> gridStore;
/*     */   
/*     */   @UiField(provided = true)
/*     */   ColumnModel<RoomAnalogTreeDTO> gridCm;
/*     */   
/*     */   @UiField
/*     */   DateRangePicker dateRangePicker;
/*     */   
/*     */   @UiField
/*     */   MenuItem menuItemDelete;
/*     */   @UiField
/*     */   Menu contextMenu;
/*     */   @UiField
/*     */   ContentPanel chartPanel;
/*     */   
/*     */   public RoomAnalogRecordViewer() {
/* 115 */     this.treeStore = new TreeStore(props.id());
/* 116 */     this.treeValueProvider = props.displayName();
/*     */     
/* 118 */     this.gridStore = new ListStore(props.id());
/* 119 */     this.gridCm = genColumnModel();
/*     */     
/* 121 */     initWidget((Widget)uiBinder.createAndBindUi(this));
/*     */     
/* 123 */     initDragAndDrop();
/*     */ 
/*     */     
/* 126 */     this.grid.setHideHeaders(true);
/*     */     
/* 128 */     this.contextMenu.add((Widget)this.menuItemDelete);
/* 129 */     this.grid.setContextMenu(this.contextMenu);
/*     */     
/* 131 */     Date now = new Date();
/* 132 */     Date twoHourAgo = new Date();
/* 133 */     twoHourAgo.setTime(now.getTime() - 7200000L);
/* 134 */     this.dateRangePicker.setStartDate(twoHourAgo);
/* 135 */     this.dateRangePicker.setEndDate(now);
/*     */   }
/*     */   
/*     */   public void initTree(List<RoomAnalogTreeDTO> dtos) {
/* 139 */     buildTree((RoomAnalogTreeDTO)null, dtos);
/* 140 */     this.tree.unmask();
/*     */   }
/*     */   
/*     */   private void buildTree(RoomAnalogTreeDTO parent, List<RoomAnalogTreeDTO> data) {
/* 144 */     for (RoomAnalogTreeDTO item : data) {
/* 145 */       if (parent == null) {
/* 146 */         this.treeStore.add(item);
/*     */       } else {
/* 148 */         this.treeStore.add(parent, item);
/*     */       } 
/* 150 */       if (item.getChildren() != null) {
/* 151 */         buildTree(item, item.getChildren());
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   @UiHandler({"search"})
/*     */   public void onSearchSelected(SelectEvent event) {
/* 158 */     if (checkField().booleanValue()) {
/* 159 */       RoomAnalogRecordQueryParamDTO dto = new RoomAnalogRecordQueryParamDTO();
/* 160 */       dto.setRoomAnalogList(this.gridStore.getAll());
/* 161 */       dto.setStartTime(this.dateRangePicker.getStartDate());
/* 162 */       dto.setEndTime(this.dateRangePicker.getEndDate());
/* 163 */       mask("資料查詢中");
/* 164 */       this.presenter.getRoomAnalogRecord(dto);
/*     */     } 
/*     */   }
/*     */   
/*     */   @UiHandler({"menuItemDelete"})
/*     */   void onMenuItemDeleteClicked(SelectionEvent<Item> event) {
/* 170 */     gridStoreRemove(this.grid.getSelectionModel().getSelectedItems());
/*     */   }
/*     */   
/*     */   @UiHandler({"grid"})
/*     */   void onBeforeShowViewerContextMenu(BeforeShowContextMenuEvent event) {
/* 175 */     if (this.grid.getSelectionModel().getSelection().size() > 0) {
/* 176 */       this.menuItemDelete.setEnabled(true);
/*     */     } else {
/* 178 */       this.menuItemDelete.setEnabled(false);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void receiveEvent(String deviceName) {
/* 183 */     List<RoomAnalogTreeDTO> treeDtos = this.treeStore.getAll();
/* 184 */     RoomAnalogTreeDTO selectTreeDto = null;
/* 185 */     for (RoomAnalogTreeDTO dto : treeDtos) {
/* 186 */       if (dto.getId().equals(deviceName)) {
/* 187 */         selectTreeDto = dto;
/*     */       }
/*     */     } 
/* 190 */     if (selectTreeDto != null) {
/* 191 */       RoomAnalogRecordQueryParamDTO dto = new RoomAnalogRecordQueryParamDTO();
/* 192 */       List<RoomAnalogTreeDTO> dtos = new ArrayList<>(Arrays.asList(new RoomAnalogTreeDTO[] { selectTreeDto }));
/* 193 */       dto.setRoomAnalogList(dtos);
/* 194 */       dto.setStartTime(this.dateRangePicker.getStartDate());
/* 195 */       dto.setEndTime(this.dateRangePicker.getEndDate());
/* 196 */       mask("資料查詢中");
/* 197 */       this.presenter.getRoomAnalogRecord(dto);
/*     */     } else {
/* 199 */       Info.display("查詢失敗", "所選設備不在清單內");
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setPresenter(RoomAnalogRecordPresenter presenter) {
/* 204 */     this.presenter = presenter;
/*     */   }
/*     */   
/*     */   public void fillChart(List<RoomAnalogRecordDTO> result) {
/* 208 */     GWT.log("fillChart.");
/* 209 */     unmask();
/* 210 */     Chart<RoomAnalogRecordDTO> chart = genChart(result);
/* 211 */     this.chartPanel.clear();
/* 212 */     this.chartPanel.add((Widget)chart);
/* 213 */     this.chartPanel.forceLayout();
/*     */   }
/*     */ 
/*     */   
/*     */   private Boolean checkField() {
/* 218 */     Date startTime = this.dateRangePicker.getStartDate();
/* 219 */     Date endTime = this.dateRangePicker.getEndDate();
/* 220 */     List<RoomAnalogTreeDTO> treeDtos = this.gridStore.getAll();
/* 221 */     if (treeDtos == null || treeDtos.size() == 0) {
/* 222 */       Info.display("查詢失敗", "請至少選取一個監控點位");
/* 223 */       return Boolean.valueOf(false);
/*     */     } 
/*     */     
/* 226 */     if (startTime.equals(endTime)) {
/* 227 */       Info.display("查詢失敗", "起訖時間不能相同");
/* 228 */       return Boolean.valueOf(false);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 236 */     return Boolean.valueOf(true);
/*     */   }
/*     */   
/*     */   private Chart<RoomAnalogRecordDTO> genChart(List<RoomAnalogRecordDTO> records) {
/* 240 */     ListStore<RoomAnalogRecordDTO> chartStore = new ListStore(new ModelKeyProvider<RoomAnalogRecordDTO>()
/*     */         {
/*     */           
/*     */           public String getKey(RoomAnalogRecordDTO item)
/*     */           {
/* 245 */             return item.getId();
/*     */           }
/*     */         });
/* 248 */     chartStore.addAll(records);
/*     */ 
/*     */     
/* 251 */     int max = 100;
/* 252 */     for (RoomAnalogRecordDTO trouble : records) {
/* 253 */       if (trouble.getPoint1Value().doubleValue() > 100.0D && trouble.getPoint1Value().doubleValue() > max) {
/* 254 */         max = trouble.getPoint1Value().intValue();
/*     */       }
/* 256 */       if (trouble.getPoint2Value().doubleValue() > 100.0D && trouble.getPoint2Value().doubleValue() > max) {
/* 257 */         max = trouble.getPoint2Value().intValue();
/*     */       }
/* 259 */       if (trouble.getPoint3Value().doubleValue() > 100.0D && trouble.getPoint3Value().doubleValue() > max) {
/* 260 */         max = trouble.getPoint3Value().intValue();
/*     */       }
/* 262 */       if (trouble.getPoint4Value().doubleValue() > 100.0D && trouble.getPoint4Value().doubleValue() > max) {
/* 263 */         max = trouble.getPoint4Value().intValue();
/*     */       }
/* 265 */       if (trouble.getPoint5Value().doubleValue() > 100.0D && trouble.getPoint5Value().doubleValue() > max) {
/* 266 */         max = trouble.getPoint5Value().intValue();
/*     */       }
/* 268 */       if (trouble.getPoint6Value().doubleValue() > 100.0D && trouble.getPoint6Value().doubleValue() > max) {
/* 269 */         max = trouble.getPoint6Value().intValue();
/*     */       }
/* 271 */       if (trouble.getPoint7Value().doubleValue() > 100.0D && trouble.getPoint7Value().doubleValue() > max) {
/* 272 */         max = trouble.getPoint7Value().intValue();
/*     */       }
/* 274 */       if (trouble.getPoint8Value().doubleValue() > 100.0D && trouble.getPoint8Value().doubleValue() > max) {
/* 275 */         max = trouble.getPoint8Value().intValue();
/*     */       }
/* 277 */       if (trouble.getPoint9Value().doubleValue() > 100.0D && trouble.getPoint9Value().doubleValue() > max) {
/* 278 */         max = trouble.getPoint9Value().intValue();
/*     */       }
/* 280 */       if (trouble.getPoint10Value().doubleValue() > 100.0D && trouble.getPoint10Value().doubleValue() > max) {
/* 281 */         max = trouble.getPoint10Value().intValue();
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 286 */     NumericAxis<RoomAnalogRecordDTO> axis = new NumericAxis();
/* 287 */     TextSprite titleY = new TextSprite("");
/* 288 */     titleY.setTextBaseline(TextSprite.TextBaseline.MIDDLE);
/* 289 */     axis.setPosition(Chart.Position.LEFT);
/* 290 */     axis.setTitleConfig(titleY);
/* 291 */     axis.addField(this.analogRecordProps.point1Value());
/* 292 */     axis.addField(this.analogRecordProps.point2Value());
/* 293 */     axis.addField(this.analogRecordProps.point3Value());
/* 294 */     axis.addField(this.analogRecordProps.point4Value());
/* 295 */     axis.addField(this.analogRecordProps.point5Value());
/* 296 */     axis.addField(this.analogRecordProps.point6Value());
/* 297 */     axis.addField(this.analogRecordProps.point7Value());
/* 298 */     axis.addField(this.analogRecordProps.point8Value());
/* 299 */     axis.addField(this.analogRecordProps.point9Value());
/* 300 */     axis.addField(this.analogRecordProps.point10Value());
/* 301 */     axis.setMinorTickSteps(5);
/* 302 */     axis.setDisplayGrid(true);
/* 303 */     axis.setMinimum(0.0D);
/* 304 */     axis.setMaximum(max);
/*     */ 
/*     */     
/* 307 */     CategoryAxis<RoomAnalogRecordDTO, String> catAxis = new CategoryAxis();
/*     */     
/* 309 */     catAxis.setPosition(Chart.Position.BOTTOM);
/* 310 */     catAxis.setField(new ValueProvider<RoomAnalogRecordDTO, String>()
/*     */         {
/*     */           
/*     */           public String getValue(RoomAnalogRecordDTO object)
/*     */           {
/* 315 */             return RoomAnalogRecordViewer.this.dateFormatter.format(object.getDataTime());
/*     */           }
/*     */ 
/*     */           
/*     */           public void setValue(RoomAnalogRecordDTO object, String value) {}
/*     */ 
/*     */           
/*     */           public String getPath() {
/* 323 */             return "timeInterval";
/*     */           }
/*     */         });
/* 326 */     catAxis.setLabelProvider(new LabelProvider<String>()
/*     */         {
/*     */           public String getLabel(String item)
/*     */           {
/* 330 */             return item;
/*     */           }
/*     */         });
/* 333 */     TextSprite catLabel = new TextSprite();
/* 334 */     TextSprite titleX = new TextSprite("時間");
/* 335 */     titleX.setTextBaseline(TextSprite.TextBaseline.MIDDLE);
/* 336 */     titleX.setX(0.0D);
/* 337 */     titleX.setY(20.0D);
/* 338 */     catLabel.setRotation(90.0D);
/*     */     
/* 340 */     catAxis.setLabelConfig(catLabel);
/* 341 */     catAxis.setTitleConfig(titleX);
/* 342 */     catAxis.setLabelOverlapHiding(true);
/* 343 */     catAxis.setDisplayGrid(true);
/*     */     
/* 345 */     Legend<RoomAnalogRecordDTO> legend = new Legend();
/* 346 */     legend.setItemHighlighting(true);
/* 347 */     legend.setItemHiding(true);
/* 348 */     legend.getBorderConfig().setStrokeWidth(1.0D);
/*     */     
/* 350 */     Chart<RoomAnalogRecordDTO> chart = new Chart();
/* 351 */     chart.setStore(chartStore);
/* 352 */     chart.setShadowChart(false);
/* 353 */     chart.addAxis((Axis)axis);
/* 354 */     chart.addAxis((Axis)catAxis);
/* 355 */     chart.setLegend(legend);
/* 356 */     chart.setDefaultInsets(30);
/*     */     
/* 358 */     for (LineSeries<RoomAnalogRecordDTO> series : genLineSeriesList(records)) {
/* 359 */       chart.addSeries((Series)series);
/*     */     }
/*     */     
/* 362 */     return chart;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private List<LineSeries<RoomAnalogRecordDTO>> genLineSeriesList(List<RoomAnalogRecordDTO> recordList) {
/* 368 */     List<LineSeries<RoomAnalogRecordDTO>> result = new ArrayList<>();
/* 369 */     Map<String, LineSeries<RoomAnalogRecordDTO>> map = new HashMap<>();
/*     */ 
/*     */     
/* 372 */     boolean isType1 = false;
/* 373 */     boolean isType2 = false;
/* 374 */     boolean isType3 = false;
/* 375 */     boolean isType4 = false;
/* 376 */     boolean isType5 = false;
/* 377 */     boolean isType6 = false;
/* 378 */     boolean isType7 = false;
/* 379 */     boolean isType8 = false;
/* 380 */     boolean isType9 = false;
/* 381 */     boolean isType10 = false;
/* 382 */     String desc1 = "";
/* 383 */     String desc2 = "";
/* 384 */     String desc3 = "";
/* 385 */     String desc4 = "";
/* 386 */     String desc5 = "";
/* 387 */     String desc6 = "";
/* 388 */     String desc7 = "";
/* 389 */     String desc8 = "";
/* 390 */     String desc9 = "";
/* 391 */     String desc10 = "";
/*     */     
/* 393 */     for (RoomAnalogRecordDTO record : recordList) {
/*     */       
/* 395 */       if (record.getPoint1Data() != null && !record.getPoint1Value().isNaN() && !isType1) {
/* 396 */         isType1 = true;
/* 397 */         desc1 = record.getPoint1Data().getLocationName();
/*     */       } 
/* 399 */       if (record.getPoint2Data() != null && !record.getPoint2Value().isNaN() && !isType2) {
/* 400 */         isType2 = true;
/* 401 */         desc2 = record.getPoint2Data().getLocationName();
/*     */       } 
/*     */       
/* 404 */       if (record.getPoint3Data() != null && !record.getPoint3Value().isNaN() && !isType3) {
/* 405 */         isType3 = true;
/* 406 */         desc3 = record.getPoint3Data().getLocationName();
/*     */       } 
/* 408 */       if (record.getPoint4Data() != null && !record.getPoint4Value().isNaN() && !isType4) {
/* 409 */         isType4 = true;
/* 410 */         desc4 = record.getPoint4Data().getLocationName();
/*     */       } 
/* 412 */       if (record.getPoint5Data() != null && !record.getPoint5Value().isNaN() && !isType5) {
/* 413 */         isType5 = true;
/* 414 */         desc5 = record.getPoint5Data().getLocationName();
/*     */       } 
/* 416 */       if (record.getPoint6Data() != null && !record.getPoint6Value().isNaN() && !isType6) {
/* 417 */         isType6 = true;
/* 418 */         desc6 = record.getPoint6Data().getLocationName();
/*     */       } 
/*     */       
/* 421 */       if (record.getPoint7Data() != null && !record.getPoint7Value().isNaN() && !isType7) {
/* 422 */         isType7 = true;
/* 423 */         desc7 = record.getPoint7Data().getLocationName();
/*     */       } 
/* 425 */       if (record.getPoint8Data() != null && !record.getPoint8Value().isNaN() && !isType8) {
/* 426 */         isType8 = true;
/* 427 */         desc8 = record.getPoint8Data().getLocationName();
/*     */       } 
/* 429 */       if (record.getPoint9Data() != null && !record.getPoint9Value().isNaN() && !isType9) {
/* 430 */         isType9 = true;
/* 431 */         desc9 = record.getPoint9Data().getLocationName();
/*     */       } 
/* 433 */       if (record.getPoint10Data() != null && !record.getPoint10Value().isNaN() && !isType10) {
/* 434 */         isType10 = true;
/* 435 */         desc10 = record.getPoint10Data().getLocationName();
/*     */       } 
/*     */     } 
/* 438 */     if (isType1) {
/* 439 */       map.put("data1", 
/*     */           
/* 441 */           genLineSeries(this.analogRecordProps
/* 442 */             .point1Value(), RoomAnalogRecordPropertyAccess.point1ValueLabel, desc1, this.colorArray[0]));
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 447 */     if (isType2) {
/* 448 */       map.put("data2", 
/*     */           
/* 450 */           genLineSeries(this.analogRecordProps
/* 451 */             .point2Value(), RoomAnalogRecordPropertyAccess.point2ValueLabel, desc2, this.colorArray[1]));
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 456 */     if (isType3) {
/* 457 */       map.put("data3", 
/*     */           
/* 459 */           genLineSeries(this.analogRecordProps
/* 460 */             .point3Value(), RoomAnalogRecordPropertyAccess.point3ValueLabel, desc3, this.colorArray[2]));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 466 */     if (isType4) {
/* 467 */       map.put("data4", 
/*     */           
/* 469 */           genLineSeries(this.analogRecordProps
/* 470 */             .point4Value(), RoomAnalogRecordPropertyAccess.point4ValueLabel, desc4, this.colorArray[3]));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 476 */     if (isType5) {
/* 477 */       map.put("data5", 
/*     */           
/* 479 */           genLineSeries(this.analogRecordProps
/* 480 */             .point5Value(), RoomAnalogRecordPropertyAccess.point5ValueLabel, desc5, this.colorArray[4]));
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 485 */     if (isType6) {
/* 486 */       map.put("data6", 
/*     */           
/* 488 */           genLineSeries(this.analogRecordProps
/* 489 */             .point6Value(), RoomAnalogRecordPropertyAccess.point6ValueLabel, desc6, this.colorArray[5]));
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 494 */     if (isType7) {
/* 495 */       map.put("data7", 
/*     */           
/* 497 */           genLineSeries(this.analogRecordProps
/* 498 */             .point7Value(), RoomAnalogRecordPropertyAccess.point7ValueLabel, desc7, this.colorArray[6]));
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 503 */     if (isType8) {
/* 504 */       map.put("data8", 
/*     */           
/* 506 */           genLineSeries(this.analogRecordProps
/* 507 */             .point8Value(), RoomAnalogRecordPropertyAccess.point8ValueLabel, desc8, this.colorArray[7]));
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 512 */     if (isType9) {
/* 513 */       map.put("data9", 
/*     */           
/* 515 */           genLineSeries(this.analogRecordProps
/* 516 */             .point9Value(), RoomAnalogRecordPropertyAccess.point9ValueLabel, desc9, this.colorArray[8]));
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 521 */     if (isType10) {
/* 522 */       map.put("data10", 
/*     */           
/* 524 */           genLineSeries(this.analogRecordProps
/* 525 */             .point10Value(), RoomAnalogRecordPropertyAccess.point10ValueLabel, desc10, this.colorArray[9]));
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 530 */     result.addAll(map.values());
/* 531 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private LineSeries<RoomAnalogRecordDTO> genLineSeries(ValueProvider<RoomAnalogRecordDTO, Double> valueProvider, SeriesLabelProvider<RoomAnalogRecordDTO> labelProvider, String legendTitle, String color) {
/* 539 */     SeriesToolTipConfig<RoomAnalogRecordDTO> tooltip = new SeriesToolTipConfig();
/*     */     
/* 541 */     tooltip.setLabelProvider(labelProvider);
/*     */     
/* 543 */     LineSeries<RoomAnalogRecordDTO> series = new LineSeries();
/* 544 */     series.setYAxisPosition(Chart.Position.LEFT);
/* 545 */     series.setYField(valueProvider);
/* 546 */     series.setStroke((Color)new RGB(color));
/* 547 */     series.setStrokeWidth(1.0D);
/* 548 */     series.setHighlighting(true);
/* 549 */     series.setLegendTitle(legendTitle);
/* 550 */     series.setSmooth(true);
/* 551 */     series.setToolTipConfig(tooltip);
/* 552 */     return series;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void initDragAndDrop() {
/* 558 */     TreeDragSource<RoomAnalogTreeDTO> treeDragSource = new TreeDragSource(this.tree);
/* 559 */     treeDragSource.setTreeSource(DND.TreeSource.BOTH);
/* 560 */     treeDragSource.setGroup(AddType.DeviceConfig.toString());
/* 561 */     treeDragSource.addDragStartHandler(new DndDragStartEvent.DndDragStartHandler()
/*     */         {
/*     */           public void onDragStart(DndDragStartEvent event)
/*     */           {
/* 565 */             GWT.log("treeDragSource onDragStart!=" + event.getData());
/* 566 */             List<RoomAnalogTreeDTO> result = new ArrayList<>();
/*     */             
/* 568 */             for (TreeStore.TreeNode<RoomAnalogTreeDTO> each : (Iterable<TreeStore.TreeNode<RoomAnalogTreeDTO>>)event.getData()) {
/* 569 */               result.add(each.getData());
/*     */             }
/* 571 */             event.setData(result);
/*     */           }
/*     */         });
/* 574 */     DropTarget treeDropTarget = new DropTarget((Widget)this.tree);
/* 575 */     treeDropTarget.setGroup("RemoveDevice");
/* 576 */     treeDropTarget.setOperation(DND.Operation.COPY);
/* 577 */     treeDropTarget.addDropHandler(new DndDropEvent.DndDropHandler()
/*     */         {
/*     */           public void onDrop(DndDropEvent event)
/*     */           {
/* 581 */             GWT.log("treeDropTarget onDrop!=" + event.getData());
/* 582 */             RoomAnalogRecordViewer.this.gridStoreRemove((List)event.getData());
/*     */           }
/*     */         });
/*     */ 
/*     */     
/* 587 */     GridDragSource<RoomAnalogTreeDTO> gridDragSource = new GridDragSource(this.grid);
/* 588 */     gridDragSource.setGroup("RemoveDevice");
/*     */     
/* 590 */     DropTarget gridDropTarget = new DropTarget((Widget)this.grid);
/* 591 */     gridDropTarget.setOperation(DND.Operation.COPY);
/* 592 */     gridDropTarget.setGroup(AddType.DeviceConfig.toString());
/* 593 */     gridDropTarget.addDropHandler(new DndDropEvent.DndDropHandler()
/*     */         {
/*     */           public void onDrop(DndDropEvent event)
/*     */           {
/* 597 */             GWT.log("gridDropTarget onDrop!=" + event.getData());
/* 598 */             RoomAnalogRecordViewer.this.gridStoreAdd((List)event.getData());
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   private ColumnModel<RoomAnalogTreeDTO> genColumnModel() {
/* 604 */     List<ColumnConfig<RoomAnalogTreeDTO, ?>> columnConfigList = new ArrayList<>();
/*     */     
/* 606 */     columnConfigList.add(new ColumnConfig(props.displayName(), 250, ""));
/* 607 */     return new ColumnModel(columnConfigList);
/*     */   }
/*     */   
/*     */   private void gridStoreAdd(List<RoomAnalogTreeDTO> dtos) {
/* 611 */     for (RoomAnalogTreeDTO dto : dtos) {
/* 612 */       if (dto.getType() != RoomAnalogTreeDTO.Type.ANALOG) {
/* 613 */         gridStoreAdd(dto.getChildren());
/*     */         continue;
/*     */       } 
/* 616 */       if (this.gridStore.findModelWithKey(dto.getId()) == null) {
/* 617 */         this.gridStore.add(dto);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private void gridStoreRemove(List<RoomAnalogTreeDTO> dtos) {
/* 623 */     for (RoomAnalogTreeDTO dto : dtos) {
/* 624 */       this.gridStore.remove(dto);
/*     */     }
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
/*     */   static interface RoomAnalogRecordViewerUiBinder
/*     */     extends UiBinder<Widget, RoomAnalogRecordViewer> {}
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
/*     */   static interface RoomAnalogTreeProperties
/*     */     extends PropertyAccess<RoomAnalogTreeDTO>
/*     */   {
/*     */     ModelKeyProvider<RoomAnalogTreeDTO> id();
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
/*     */     ValueProvider<RoomAnalogTreeDTO, String> displayName();
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
/*     */   static interface RoomAnalogRecordPropertyAccess
/*     */     extends PropertyAccess<RoomAnalogRecordDTO>
/*     */   {
/* 677 */     public static final SeriesLabelProvider<RoomAnalogRecordDTO> point1ValueLabel = new SeriesLabelProvider<RoomAnalogRecordDTO>()
/*     */       {
/*     */ 
/*     */         
/*     */         public String getLabel(RoomAnalogRecordDTO item, ValueProvider<? super RoomAnalogRecordDTO, ? extends Number> valueProvider)
/*     */         {
/* 683 */           return item.getPoint1Value().toString();
/*     */         }
/*     */       };
/* 686 */     public static final SeriesLabelProvider<RoomAnalogRecordDTO> point2ValueLabel = new SeriesLabelProvider<RoomAnalogRecordDTO>()
/*     */       {
/*     */ 
/*     */         
/*     */         public String getLabel(RoomAnalogRecordDTO item, ValueProvider<? super RoomAnalogRecordDTO, ? extends Number> valueProvider)
/*     */         {
/* 692 */           return item.getPoint2Value().toString();
/*     */         }
/*     */       };
/* 695 */     public static final SeriesLabelProvider<RoomAnalogRecordDTO> point3ValueLabel = new SeriesLabelProvider<RoomAnalogRecordDTO>()
/*     */       {
/*     */ 
/*     */         
/*     */         public String getLabel(RoomAnalogRecordDTO item, ValueProvider<? super RoomAnalogRecordDTO, ? extends Number> valueProvider)
/*     */         {
/* 701 */           return item.getPoint3Value().toString();
/*     */         }
/*     */       };
/* 704 */     public static final SeriesLabelProvider<RoomAnalogRecordDTO> point4ValueLabel = new SeriesLabelProvider<RoomAnalogRecordDTO>()
/*     */       {
/*     */ 
/*     */         
/*     */         public String getLabel(RoomAnalogRecordDTO item, ValueProvider<? super RoomAnalogRecordDTO, ? extends Number> valueProvider)
/*     */         {
/* 710 */           return item.getPoint4Value().toString();
/*     */         }
/*     */       };
/* 713 */     public static final SeriesLabelProvider<RoomAnalogRecordDTO> point5ValueLabel = new SeriesLabelProvider<RoomAnalogRecordDTO>()
/*     */       {
/*     */ 
/*     */         
/*     */         public String getLabel(RoomAnalogRecordDTO item, ValueProvider<? super RoomAnalogRecordDTO, ? extends Number> valueProvider)
/*     */         {
/* 719 */           return item.getPoint5Value().toString();
/*     */         }
/*     */       };
/* 722 */     public static final SeriesLabelProvider<RoomAnalogRecordDTO> point6ValueLabel = new SeriesLabelProvider<RoomAnalogRecordDTO>()
/*     */       {
/*     */ 
/*     */         
/*     */         public String getLabel(RoomAnalogRecordDTO item, ValueProvider<? super RoomAnalogRecordDTO, ? extends Number> valueProvider)
/*     */         {
/* 728 */           return item.getPoint6Value().toString();
/*     */         }
/*     */       };
/* 731 */     public static final SeriesLabelProvider<RoomAnalogRecordDTO> point7ValueLabel = new SeriesLabelProvider<RoomAnalogRecordDTO>()
/*     */       {
/*     */ 
/*     */         
/*     */         public String getLabel(RoomAnalogRecordDTO item, ValueProvider<? super RoomAnalogRecordDTO, ? extends Number> valueProvider)
/*     */         {
/* 737 */           return item.getPoint7Value().toString();
/*     */         }
/*     */       };
/* 740 */     public static final SeriesLabelProvider<RoomAnalogRecordDTO> point8ValueLabel = new SeriesLabelProvider<RoomAnalogRecordDTO>()
/*     */       {
/*     */ 
/*     */         
/*     */         public String getLabel(RoomAnalogRecordDTO item, ValueProvider<? super RoomAnalogRecordDTO, ? extends Number> valueProvider)
/*     */         {
/* 746 */           return item.getPoint8Value().toString();
/*     */         }
/*     */       };
/* 749 */     public static final SeriesLabelProvider<RoomAnalogRecordDTO> point9ValueLabel = new SeriesLabelProvider<RoomAnalogRecordDTO>()
/*     */       {
/*     */ 
/*     */         
/*     */         public String getLabel(RoomAnalogRecordDTO item, ValueProvider<? super RoomAnalogRecordDTO, ? extends Number> valueProvider)
/*     */         {
/* 755 */           return item.getPoint9Value().toString();
/*     */         }
/*     */       };
/* 758 */     public static final SeriesLabelProvider<RoomAnalogRecordDTO> point10ValueLabel = new SeriesLabelProvider<RoomAnalogRecordDTO>()
/*     */       {
/*     */ 
/*     */         
/*     */         public String getLabel(RoomAnalogRecordDTO item, ValueProvider<? super RoomAnalogRecordDTO, ? extends Number> valueProvider)
/*     */         {
/* 764 */           return item.getPoint10Value().toString();
/*     */         }
/*     */       };
/*     */     
/*     */     ModelKeyProvider<RoomAnalogRecordDTO> id();
/*     */     
/*     */     ValueProvider<RoomAnalogRecordDTO, Double> point1Value();
/*     */     
/*     */     ValueProvider<RoomAnalogRecordDTO, AnalogyDataDTO> point1Data();
/*     */     
/*     */     ValueProvider<RoomAnalogRecordDTO, Double> point2Value();
/*     */     
/*     */     ValueProvider<RoomAnalogRecordDTO, AnalogyDataDTO> point2Data();
/*     */     
/*     */     ValueProvider<RoomAnalogRecordDTO, Double> point3Value();
/*     */     
/*     */     ValueProvider<RoomAnalogRecordDTO, AnalogyDataDTO> point3Data();
/*     */     
/*     */     ValueProvider<RoomAnalogRecordDTO, Double> point4Value();
/*     */     
/*     */     ValueProvider<RoomAnalogRecordDTO, AnalogyDataDTO> point4Data();
/*     */     
/*     */     ValueProvider<RoomAnalogRecordDTO, Double> point5Value();
/*     */     
/*     */     ValueProvider<RoomAnalogRecordDTO, AnalogyDataDTO> point5Data();
/*     */     
/*     */     ValueProvider<RoomAnalogRecordDTO, Double> point6Value();
/*     */     
/*     */     ValueProvider<RoomAnalogRecordDTO, AnalogyDataDTO> point6Data();
/*     */     
/*     */     ValueProvider<RoomAnalogRecordDTO, Double> point7Value();
/*     */     
/*     */     ValueProvider<RoomAnalogRecordDTO, AnalogyDataDTO> point7Data();
/*     */     
/*     */     ValueProvider<RoomAnalogRecordDTO, Double> point8Value();
/*     */     
/*     */     ValueProvider<RoomAnalogRecordDTO, AnalogyDataDTO> point8Data();
/*     */     
/*     */     ValueProvider<RoomAnalogRecordDTO, Double> point9Value();
/*     */     
/*     */     ValueProvider<RoomAnalogRecordDTO, AnalogyDataDTO> point9Data();
/*     */     
/*     */     ValueProvider<RoomAnalogRecordDTO, Double> point10Value();
/*     */     
/*     */     ValueProvider<RoomAnalogRecordDTO, AnalogyDataDTO> point10Data();
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\lib\aoAdmin-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ao\am\view\RoomAnalogRecordViewer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */