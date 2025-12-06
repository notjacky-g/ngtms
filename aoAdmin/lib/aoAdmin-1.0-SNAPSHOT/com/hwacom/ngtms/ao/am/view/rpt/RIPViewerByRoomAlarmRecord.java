/*     */ package com.hwacom.ngtms.ao.am.view.rpt;
/*     */ 
/*     */ import com.google.gwt.core.client.GWT;
/*     */ import com.google.gwt.i18n.client.DateTimeFormat;
/*     */ import com.google.gwt.uibinder.client.UiBinder;
/*     */ import com.google.gwt.uibinder.client.UiField;
/*     */ import com.google.gwt.user.client.ui.Label;
/*     */ import com.google.gwt.user.client.ui.Widget;
/*     */ import com.hwacom.ngtms.ao.am.RptEP;
/*     */ import com.hwacom.ngtms.ao.am.presenter.rpt.RIPViewerByRoomAlarmRecordPresenter;
/*     */ import com.hwacom.ngtms.ao.am.view.Messages;
/*     */ import com.hwacom.ngtms.ao.shared.dto.AlarmTypeDTO;
/*     */ import com.hwacom.ngtms.ao.shared.dto.PdLocationDTO;
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
/*     */ public class RIPViewerByRoomAlarmRecord
/*     */   extends Composite
/*     */   implements RIPViewer
/*     */ {
/*  39 */   private static RIPViewerByCardReaderLogUiBinder uiBinder = (RIPViewerByCardReaderLogUiBinder)GWT.create(RIPViewerByCardReaderLogUiBinder.class);
/*     */ 
/*     */ 
/*     */   
/*  43 */   private static final Messages messages = (Messages)GWT.create(Messages.class);
/*     */   
/*     */   private static final String dateTimeFormat = "yyyy-MM-dd HH:mm:ss";
/*     */   
/*  47 */   private RIPViewerByRoomAlarmRecordPresenter presenter = new RIPViewerByRoomAlarmRecordPresenter(this);
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
/*     */   ComboBox<AlarmTypeDTO> alarmType;
/*     */   
/*     */   @UiField(provided = true)
/*     */   ListStore<AlarmTypeDTO> alarmTypeStore;
/*     */   @UiField(provided = true)
/*     */   LabelProvider<AlarmTypeDTO> alarmTypeProvider;
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
/*     */   public RIPViewerByRoomAlarmRecord() {
/*  76 */     this.locationStore = new ListStore(new ModelKeyProvider<PdLocationDTO>()
/*     */         {
/*     */           
/*     */           public String getKey(PdLocationDTO location)
/*     */           {
/*  81 */             return location.getId().toString();
/*     */           }
/*     */         });
/*  84 */     this.locationProvider = new LabelProvider<PdLocationDTO>()
/*     */       {
/*     */         public String getLabel(PdLocationDTO status)
/*     */         {
/*  88 */           return status.getLocName();
/*     */         }
/*     */       };
/*     */     
/*  92 */     this.alarmTypeStore = new ListStore(new ModelKeyProvider<AlarmTypeDTO>()
/*     */         {
/*     */           
/*     */           public String getKey(AlarmTypeDTO alarmType)
/*     */           {
/*  97 */             return alarmType.getAlarmType().toString();
/*     */           }
/*     */         });
/*     */     
/* 101 */     this.alarmTypeProvider = new LabelProvider<AlarmTypeDTO>()
/*     */       {
/*     */         public String getLabel(AlarmTypeDTO alarmType)
/*     */         {
/* 105 */           return alarmType.getAlarmType().getName();
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
/* 118 */     this.presenter.getAllAlarmType();
/* 119 */     addEventHandlers();
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
/* 133 */     GWT.log("getInputParameter, startDateTime: " + startDateTime);
/* 134 */     map.put("startDateTime", DateTimeFormat.getFormat("yyyy-MM-dd HH:mm:ss").format(startDateTime));
/* 135 */     map.put("endDateTime", DateTimeFormat.getFormat("yyyy-MM-dd HH:mm:ss").format(endDateTime));
/* 136 */     map.put("location", ((PdLocationDTO)this.location.getValue()).getLocName());
/* 137 */     map.put("alarmType", ((AlarmTypeDTO)this.alarmType.getValue()).getAlarmType().name());
/* 138 */     map.put("userName", RptEP.getUserName());
/*     */     
/* 140 */     return map;
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
/* 152 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void clearInputParameter() {}
/*     */ 
/*     */   
/*     */   public void setHeading(String heading) {}
/*     */   
/*     */   private void addEventHandlers() {
/* 162 */     this.dateRangePicker.addValueChangeCallback(new BiConsumer<Date, Date>()
/*     */         {
/*     */           public void accept(Date t, Date u)
/*     */           {
/* 166 */             Long timeIntervalDay = Long.valueOf((u.getTime() - t.getTime()) / 86400000L);
/* 167 */             if (timeIntervalDay.longValue() > 7L) {
/* 168 */               Date now = new Date();
/* 169 */               RIPViewerByRoomAlarmRecord.this.dateRangePicker.setStartDate(now);
/* 170 */               RIPViewerByRoomAlarmRecord.this.dateRangePicker.setEndDate(now);
/* 171 */               RIPViewerByRoomAlarmRecord.this.initDialog();
/*     */             } 
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   private void initDialog() {
/* 178 */     if (this.warningDialog == null) {
/* 179 */       this.warningDialog = new Dialog();
/* 180 */       this.warningDialog.setWidth(210);
/* 181 */       this.warningDialog.setHeight(70);
/* 182 */       this.warningDialog.setModal(true);
/* 183 */       this.warningDialog.setResizable(false);
/* 184 */       this.warningDialog.setHeading("查詢條件不符");
/* 185 */       this.warningDialog.setHideOnButtonClick(true);
/*     */     } 
/*     */     
/* 188 */     Label label = new Label("日期範圍不得大於7天!\n請重新選取");
/* 189 */     this.warningDialog.add((Widget)label);
/* 190 */     this.warningDialog.show();
/*     */   }
/*     */   
/*     */   public void fillAlarmType(List<AlarmTypeDTO> list) {
/* 194 */     this.alarmTypeStore.addAll(list);
/* 195 */     this.alarmType.setValue(list.get(0));
/*     */   }
/*     */   
/*     */   public void fillLocation(List<PdLocationDTO> list) {
/* 199 */     this.locationStore.clear();
/* 200 */     PdLocationDTO all = new PdLocationDTO();
/* 201 */     all.setId(Integer.valueOf(-1));
/* 202 */     all.setLocName(messages.comboBox_all());
/* 203 */     this.locationStore.add(all);
/* 204 */     this.locationStore.addAll(list);
/* 205 */     this.location.setValue(all);
/*     */   }
/*     */   
/*     */   public void setPresenter(RIPViewerByRoomAlarmRecordPresenter presenter) {
/* 209 */     this.presenter = presenter;
/*     */   }
/*     */   
/*     */   static interface RIPViewerByCardReaderLogUiBinder extends UiBinder<Widget, RIPViewerByRoomAlarmRecord> {}
/*     */ }


/* Location:              C:\User\\user\Desktop\lib\aoAdmin-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ao\am\view\rpt\RIPViewerByRoomAlarmRecord.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */