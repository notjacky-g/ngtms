/*     */ package com.hwacom.ngtms.ao.am.view.rpt;
/*     */ 
/*     */ import com.google.gwt.core.client.GWT;
/*     */ import com.google.gwt.i18n.client.DateTimeFormat;
/*     */ import com.google.gwt.uibinder.client.UiBinder;
/*     */ import com.google.gwt.uibinder.client.UiField;
/*     */ import com.google.gwt.user.client.ui.Label;
/*     */ import com.google.gwt.user.client.ui.Widget;
/*     */ import com.hwacom.ngtms.ao.am.RptEP;
/*     */ import com.hwacom.ngtms.ao.am.presenter.rpt.RIPViewerByCardReaderLogPresenter;
/*     */ import com.hwacom.ngtms.ao.am.view.Messages;
/*     */ import com.hwacom.ngtms.ao.shared.dto.RoomNcuDeviceNameDTO;
/*     */ import com.hwacom.ngtms.c.shared.dto.DeviceConfigDTO;
/*     */ import com.hwacom.ngtms.cam.vo.IdNameNode;
/*     */ import com.hwacom.ngtms.common.am.view.DateRangePicker;
/*     */ import com.hwacom.ngtms.common.am.view.RIPViewer;
/*     */ import com.sencha.gxt.data.shared.LabelProvider;
/*     */ import com.sencha.gxt.data.shared.ListStore;
/*     */ import com.sencha.gxt.data.shared.ModelKeyProvider;
/*     */ import com.sencha.gxt.widget.core.client.Composite;
/*     */ import com.sencha.gxt.widget.core.client.Dialog;
/*     */ import com.sencha.gxt.widget.core.client.container.HorizontalLayoutContainer;
/*     */ import com.sencha.gxt.widget.core.client.form.ComboBox;
/*     */ import java.util.Date;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.function.BiConsumer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RIPViewerByCardReaderLog
/*     */   extends Composite
/*     */   implements RIPViewer
/*     */ {
/*  40 */   private static RIPViewerByCardReaderLogUiBinder uiBinder = (RIPViewerByCardReaderLogUiBinder)GWT.create(RIPViewerByCardReaderLogUiBinder.class);
/*     */ 
/*     */ 
/*     */   
/*  44 */   private static final Messages messages = (Messages)GWT.create(Messages.class);
/*     */   
/*     */   private static final String dateTimeFormat = "yyyy-MM-dd HH:mm:ss";
/*     */   
/*  48 */   private RIPViewerByCardReaderLogPresenter presenter = new RIPViewerByCardReaderLogPresenter(this);
/*     */   
/*     */   @UiField
/*     */   ComboBox<RoomNcuDeviceNameDTO> location;
/*     */   
/*     */   @UiField(provided = true)
/*     */   ListStore<RoomNcuDeviceNameDTO> locationStore;
/*     */   
/*     */   @UiField(provided = true)
/*     */   LabelProvider<RoomNcuDeviceNameDTO> locationProvider;
/*     */   
/*     */   @UiField
/*     */   ComboBox<String> status;
/*     */   @UiField(provided = true)
/*     */   ListStore<String> statusStore;
/*     */   @UiField(provided = true)
/*     */   LabelProvider<String> statusProvider;
/*     */   @UiField
/*     */   HorizontalLayoutContainer locationContainer;
/*     */   @UiField
/*     */   HorizontalLayoutContainer itemsContainer;
/*     */   @UiField
/*     */   HorizontalLayoutContainer dateRangePickerContainer;
/*     */   @UiField
/*     */   DateRangePicker dateRangePicker;
/*     */   private Dialog warningDialog;
/*     */   
/*     */   public RIPViewerByCardReaderLog() {
/*  76 */     this.locationStore = new ListStore(new ModelKeyProvider<RoomNcuDeviceNameDTO>()
/*     */         {
/*     */           
/*     */           public String getKey(RoomNcuDeviceNameDTO status)
/*     */           {
/*  81 */             return status.toString();
/*     */           }
/*     */         });
/*  84 */     this.locationProvider = new LabelProvider<RoomNcuDeviceNameDTO>()
/*     */       {
/*     */         public String getLabel(RoomNcuDeviceNameDTO status)
/*     */         {
/*  88 */           return status.getDisplayName();
/*     */         }
/*     */       };
/*     */     
/*  92 */     this.statusStore = new ListStore(new ModelKeyProvider<String>()
/*     */         {
/*     */           
/*     */           public String getKey(String status)
/*     */           {
/*  97 */             return status;
/*     */           }
/*     */         });
/*     */     
/* 101 */     this.statusProvider = new LabelProvider<String>()
/*     */       {
/*     */         public String getLabel(String status)
/*     */         {
/* 105 */           return status;
/*     */         }
/*     */       };
/*     */ 
/*     */     
/* 110 */     initWidget((Widget)uiBinder.createAndBindUi(this));
/*     */     
/* 112 */     Date now = new Date();
/* 113 */     this.dateRangePicker.setStartDate(now);
/* 114 */     this.dateRangePicker.setEndDate(now);
/* 115 */     this.dateRangePicker.setTimePickerIncrement(1);
/*     */     
/* 117 */     this.presenter.retrieveLocation();
/* 118 */     addEventHandlers();
/* 119 */     setAlarmItemCB();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Map<String, Object> getInputParameter() {
/* 129 */     Map<String, Object> map = new HashMap<>();
/* 130 */     Date startDateTime = this.dateRangePicker.getStartDate();
/* 131 */     Date endDateTime = this.dateRangePicker.getEndDate();
/*     */     
/* 133 */     map.put("startDateTime", DateTimeFormat.getFormat("yyyy-MM-dd HH:mm:ss").format(startDateTime));
/* 134 */     map.put("endDateTime", DateTimeFormat.getFormat("yyyy-MM-dd HH:mm:ss").format(endDateTime));
/* 135 */     map.put("location", ((RoomNcuDeviceNameDTO)this.location.getValue()).getDeviceName());
/* 136 */     map.put("status", this.status.getValue());
/* 137 */     map.put("userName", RptEP.getUserName());
/*     */     
/* 139 */     return map;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setInputParameter(Map<String, Object> inputParameter) {
/* 148 */     clearInputParameter();
/*     */ 
/*     */     
/* 151 */     IdNameNode idName = null;
/* 152 */     for (String key : inputParameter.get("devices")) {
/*     */       
/* 154 */       if (idName == null) {
/*     */         continue;
/*     */       }
/* 157 */       DeviceConfigDTO dto = new DeviceConfigDTO();
/* 158 */       dto.setDeviceName(idName.getDeviceName());
/* 159 */       dto.setDisplayName(idName.getName());
/* 160 */       dto.setMilepost(idName.getMilePost());
/* 161 */       dto.setDirection(idName.getDirection());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isValid() {
/* 171 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void clearInputParameter() {}
/*     */ 
/*     */   
/*     */   public void setHeading(String heading) {}
/*     */ 
/*     */   
/*     */   private void addEventHandlers() {
/* 183 */     this.dateRangePicker.addValueChangeCallback(new BiConsumer<Date, Date>()
/*     */         {
/*     */           public void accept(Date t, Date u) {}
/*     */         });
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
/*     */   private void initDialog() {
/* 199 */     if (this.warningDialog == null) {
/* 200 */       this.warningDialog = new Dialog();
/* 201 */       this.warningDialog.setWidth(210);
/* 202 */       this.warningDialog.setHeight(70);
/* 203 */       this.warningDialog.setModal(true);
/* 204 */       this.warningDialog.setResizable(false);
/* 205 */       this.warningDialog.setHeading("查詢條件不符");
/* 206 */       this.warningDialog.setHideOnButtonClick(true);
/*     */     } 
/*     */     
/* 209 */     Label label = new Label("日期範圍不得大於7天!\n請重新選取");
/* 210 */     this.warningDialog.add((Widget)label);
/* 211 */     this.warningDialog.show();
/*     */   }
/*     */   
/*     */   private void setAlarmItemCB() {
/* 215 */     String statusAll = "全部";
/* 216 */     this.statusStore.add(statusAll);
/* 217 */     String status1 = "刷進";
/* 218 */     this.statusStore.add(status1);
/* 219 */     String status2 = "刷出";
/* 220 */     this.statusStore.add(status2);
/* 221 */     String status3 = "遠端開門";
/* 222 */     this.statusStore.add(status3);
/* 223 */     this.status.setValue(this.statusStore.get(0));
/*     */   }
/*     */   
/*     */   public String getDeviceType() {
/* 227 */     return null;
/*     */   }
/*     */   
/*     */   public void fillLocation(List<RoomNcuDeviceNameDTO> list) {
/* 231 */     this.locationStore.clear();
/* 232 */     RoomNcuDeviceNameDTO all = new RoomNcuDeviceNameDTO();
/* 233 */     all.setDeviceName("全部");
/* 234 */     all.setDisplayName("全部");
/* 235 */     this.locationStore.add(all);
/* 236 */     this.locationStore.addAll(list);
/* 237 */     this.location.setValue(all);
/*     */   }
/*     */   
/*     */   public void setPresenter(RIPViewerByCardReaderLogPresenter presenter) {
/* 241 */     this.presenter = presenter;
/*     */   }
/*     */   
/*     */   static interface RIPViewerByCardReaderLogUiBinder extends UiBinder<Widget, RIPViewerByCardReaderLog> {}
/*     */ }


/* Location:              C:\User\\user\Desktop\lib\aoAdmin-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ao\am\view\rpt\RIPViewerByCardReaderLog.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */