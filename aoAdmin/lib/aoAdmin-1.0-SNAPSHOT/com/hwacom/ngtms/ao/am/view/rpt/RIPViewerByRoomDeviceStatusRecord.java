/*     */ package com.hwacom.ngtms.ao.am.view.rpt;
/*     */ 
/*     */ import com.google.gwt.core.client.GWT;
/*     */ import com.google.gwt.i18n.client.DateTimeFormat;
/*     */ import com.google.gwt.uibinder.client.UiBinder;
/*     */ import com.google.gwt.uibinder.client.UiField;
/*     */ import com.google.gwt.user.client.ui.Label;
/*     */ import com.google.gwt.user.client.ui.Widget;
/*     */ import com.hwacom.ngtms.ao.am.RptEP;
/*     */ import com.hwacom.ngtms.ao.am.presenter.rpt.RIPViewerByRoomDeviceStatusRecordPresenter;
/*     */ import com.hwacom.ngtms.ao.am.view.Messages;
/*     */ import com.hwacom.ngtms.ao.shared.dto.PdLocationDTO;
/*     */ import com.hwacom.ngtms.c.shared.dto.DeviceTypeDTO;
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
/*     */ public class RIPViewerByRoomDeviceStatusRecord
/*     */   extends Composite
/*     */   implements RIPViewer
/*     */ {
/*  39 */   private static RIPViewerByCardReaderLogUiBinder uiBinder = (RIPViewerByCardReaderLogUiBinder)GWT.create(RIPViewerByCardReaderLogUiBinder.class);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  44 */   private static final Messages messages = (Messages)GWT.create(Messages.class);
/*     */   
/*     */   private static final String dateTimeFormat = "yyyy-MM-dd HH:mm:ss";
/*     */   
/*  48 */   private RIPViewerByRoomDeviceStatusRecordPresenter presenter = new RIPViewerByRoomDeviceStatusRecordPresenter(this);
/*     */   
/*     */   @UiField
/*     */   ComboBox<PdLocationDTO> location;
/*     */   
/*     */   @UiField(provided = true)
/*     */   ListStore<PdLocationDTO> locationStore;
/*     */   
/*     */   @UiField(provided = true)
/*     */   LabelProvider<PdLocationDTO> locationProvider;
/*     */   
/*     */   @UiField
/*     */   ComboBox<DeviceTypeDTO> deviceType;
/*     */   
/*     */   @UiField(provided = true)
/*     */   ListStore<DeviceTypeDTO> deviceTypeStore;
/*     */   
/*     */   @UiField(provided = true)
/*     */   LabelProvider<DeviceTypeDTO> deviceTypeProvider;
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
/*     */   HorizontalLayoutContainer statusContainer;
/*     */   @UiField
/*     */   HorizontalLayoutContainer dateRangePickerContainer;
/*     */   @UiField
/*     */   DateRangePicker dateRangePicker;
/*     */   private Dialog warningDialog;
/*     */   
/*     */   public RIPViewerByRoomDeviceStatusRecord() {
/*  85 */     this.locationStore = new ListStore(new ModelKeyProvider<PdLocationDTO>()
/*     */         {
/*     */           
/*     */           public String getKey(PdLocationDTO location)
/*     */           {
/*  90 */             return location.getId().toString();
/*     */           }
/*     */         });
/*  93 */     this.locationProvider = new LabelProvider<PdLocationDTO>()
/*     */       {
/*     */         public String getLabel(PdLocationDTO status)
/*     */         {
/*  97 */           return status.getLocName();
/*     */         }
/*     */       };
/*     */     
/* 101 */     this.statusStore = new ListStore(new ModelKeyProvider<String>()
/*     */         {
/*     */           
/*     */           public String getKey(String status)
/*     */           {
/* 106 */             return status;
/*     */           }
/*     */         });
/*     */     
/* 110 */     this.statusProvider = new LabelProvider<String>()
/*     */       {
/*     */         public String getLabel(String status)
/*     */         {
/* 114 */           return status;
/*     */         }
/*     */       };
/*     */     
/* 118 */     this.deviceTypeStore = new ListStore(new ModelKeyProvider<DeviceTypeDTO>()
/*     */         {
/*     */           
/*     */           public String getKey(DeviceTypeDTO deviceType)
/*     */           {
/* 123 */             return deviceType.getId();
/*     */           }
/*     */         });
/*     */     
/* 127 */     this.deviceTypeProvider = new LabelProvider<DeviceTypeDTO>()
/*     */       {
/*     */         public String getLabel(DeviceTypeDTO deviceType)
/*     */         {
/* 131 */           return deviceType.getDescription();
/*     */         }
/*     */       };
/*     */ 
/*     */     
/* 136 */     initWidget((Widget)uiBinder.createAndBindUi(this));
/*     */     
/* 138 */     Date now = new Date();
/* 139 */     this.dateRangePicker.setStartDate(now);
/* 140 */     this.dateRangePicker.setEndDate(now);
/* 141 */     this.dateRangePicker.setTimePickerIncrement(1);
/*     */     
/* 143 */     this.presenter.retrieveLocation();
/* 144 */     this.presenter.getDeviceTypeByCategory("ROOM");
/* 145 */     addEventHandlers();
/* 146 */     setStatusCB();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Map<String, Object> getInputParameter() {
/* 156 */     Map<String, Object> map = new HashMap<>();
/* 157 */     Date startDateTime = this.dateRangePicker.getStartDate();
/* 158 */     Date endDateTime = this.dateRangePicker.getEndDate();
/*     */     
/* 160 */     map.put("startDateTime", DateTimeFormat.getFormat("yyyy-MM-dd HH:mm:ss").format(startDateTime));
/* 161 */     map.put("endDateTime", DateTimeFormat.getFormat("yyyy-MM-dd HH:mm:ss").format(endDateTime));
/* 162 */     map.put("location", ((PdLocationDTO)this.location.getValue()).getLocName());
/* 163 */     map.put("deviceType", ((DeviceTypeDTO)this.deviceType.getValue()).getId());
/* 164 */     map.put("status", this.status.getValue());
/* 165 */     map.put("userName", RptEP.getUserName());
/*     */     
/* 167 */     return map;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setInputParameter(Map<String, Object> inputParameter) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isValid() {
/* 179 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void clearInputParameter() {}
/*     */ 
/*     */   
/*     */   public void setHeading(String heading) {}
/*     */   
/*     */   private void addEventHandlers() {
/* 189 */     this.dateRangePicker.addValueChangeCallback(new BiConsumer<Date, Date>()
/*     */         {
/*     */           public void accept(Date t, Date u)
/*     */           {
/* 193 */             Long timeIntervalDay = Long.valueOf((u.getTime() - t.getTime()) / 86400000L);
/* 194 */             if (timeIntervalDay.longValue() > 7L) {
/* 195 */               Date now = new Date();
/* 196 */               RIPViewerByRoomDeviceStatusRecord.this.dateRangePicker.setStartDate(now);
/* 197 */               RIPViewerByRoomDeviceStatusRecord.this.dateRangePicker.setEndDate(now);
/* 198 */               RIPViewerByRoomDeviceStatusRecord.this.initDialog();
/*     */             } 
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   private void initDialog() {
/* 205 */     if (this.warningDialog == null) {
/* 206 */       this.warningDialog = new Dialog();
/* 207 */       this.warningDialog.setWidth(210);
/* 208 */       this.warningDialog.setHeight(70);
/* 209 */       this.warningDialog.setModal(true);
/* 210 */       this.warningDialog.setResizable(false);
/* 211 */       this.warningDialog.setHeading("查詢條件不符");
/* 212 */       this.warningDialog.setHideOnButtonClick(true);
/*     */     } 
/*     */     
/* 215 */     Label label = new Label("日期範圍不得大於7天!\n請重新選取");
/* 216 */     this.warningDialog.add((Widget)label);
/* 217 */     this.warningDialog.show();
/*     */   }
/*     */   
/*     */   private void setStatusCB() {
/* 221 */     String statusAll = "全部";
/* 222 */     this.statusStore.add(statusAll);
/* 223 */     String status1 = "連線";
/* 224 */     this.statusStore.add(status1);
/* 225 */     String status2 = "斷線";
/* 226 */     this.statusStore.add(status2);
/* 227 */     this.status.setValue(this.statusStore.get(0));
/*     */   }
/*     */   
/*     */   public void setDeviceTypeCB(List<DeviceTypeDTO> deviceTypeList) {
/* 231 */     DeviceTypeDTO allDeviceType = new DeviceTypeDTO();
/* 232 */     allDeviceType.setId("全部");
/* 233 */     allDeviceType.setDescription("全部");
/* 234 */     this.deviceTypeStore.add(allDeviceType);
/* 235 */     this.deviceTypeStore.addAll(deviceTypeList);
/*     */     
/* 237 */     this.deviceType.setValue(this.deviceTypeStore.get(0));
/*     */   }
/*     */   
/*     */   public void fillLocation(List<PdLocationDTO> list) {
/* 241 */     this.locationStore.clear();
/* 242 */     PdLocationDTO all = new PdLocationDTO();
/* 243 */     all.setId(Integer.valueOf(-1));
/* 244 */     all.setLocName(messages.comboBox_all());
/* 245 */     this.locationStore.add(all);
/* 246 */     this.locationStore.addAll(list);
/* 247 */     this.location.setValue(all);
/*     */   }
/*     */   
/*     */   public void setPresenter(RIPViewerByRoomDeviceStatusRecordPresenter presenter) {
/* 251 */     this.presenter = presenter;
/*     */   }
/*     */   
/*     */   static interface RIPViewerByCardReaderLogUiBinder extends UiBinder<Widget, RIPViewerByRoomDeviceStatusRecord> {}
/*     */ }


/* Location:              C:\User\\user\Desktop\lib\aoAdmin-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ao\am\view\rpt\RIPViewerByRoomDeviceStatusRecord.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */