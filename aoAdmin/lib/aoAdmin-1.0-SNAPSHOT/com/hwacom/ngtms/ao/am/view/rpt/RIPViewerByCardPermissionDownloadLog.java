/*     */ package com.hwacom.ngtms.ao.am.view.rpt;
/*     */ 
/*     */ import com.google.gwt.core.client.GWT;
/*     */ import com.google.gwt.i18n.client.DateTimeFormat;
/*     */ import com.google.gwt.uibinder.client.UiBinder;
/*     */ import com.google.gwt.uibinder.client.UiField;
/*     */ import com.google.gwt.user.client.ui.Label;
/*     */ import com.google.gwt.user.client.ui.Widget;
/*     */ import com.hwacom.ngtms.ao.am.RptEP;
/*     */ import com.hwacom.ngtms.ao.am.presenter.rpt.RIPViewerByCardPermissionDownloadLogPresenter;
/*     */ import com.hwacom.ngtms.ao.am.view.Messages;
/*     */ import com.hwacom.ngtms.ao.shared.dto.RoomNcuDeviceNameDTO;
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
/*     */ public class RIPViewerByCardPermissionDownloadLog
/*     */   extends Composite
/*     */   implements RIPViewer
/*     */ {
/*  38 */   private static RIPViewerByCardReaderLogUiBinder uiBinder = (RIPViewerByCardReaderLogUiBinder)GWT.create(RIPViewerByCardReaderLogUiBinder.class);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  43 */   private static final Messages messages = (Messages)GWT.create(Messages.class);
/*     */   
/*     */   private static final String dateTimeFormat = "yyyy-MM-dd HH:mm:ss";
/*     */   
/*  47 */   private RIPViewerByCardPermissionDownloadLogPresenter presenter = new RIPViewerByCardPermissionDownloadLogPresenter(this);
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
/*     */   
/*     */   @UiField(provided = true)
/*     */   ListStore<String> statusStore;
/*     */   
/*     */   @UiField(provided = true)
/*     */   LabelProvider<String> statusProvider;
/*     */   
/*     */   @UiField
/*     */   ComboBox<String> result;
/*     */   @UiField(provided = true)
/*     */   ListStore<String> resultStore;
/*     */   @UiField(provided = true)
/*     */   LabelProvider<String> resultProvider;
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
/*     */   public RIPViewerByCardPermissionDownloadLog() {
/*  84 */     this.locationStore = new ListStore(new ModelKeyProvider<RoomNcuDeviceNameDTO>()
/*     */         {
/*     */           
/*     */           public String getKey(RoomNcuDeviceNameDTO status)
/*     */           {
/*  89 */             return status.toString();
/*     */           }
/*     */         });
/*  92 */     this.locationProvider = new LabelProvider<RoomNcuDeviceNameDTO>()
/*     */       {
/*     */         public String getLabel(RoomNcuDeviceNameDTO status)
/*     */         {
/*  96 */           return status.getDisplayName();
/*     */         }
/*     */       };
/*     */     
/* 100 */     this.statusStore = new ListStore(new ModelKeyProvider<String>()
/*     */         {
/*     */           
/*     */           public String getKey(String status)
/*     */           {
/* 105 */             return status;
/*     */           }
/*     */         });
/*     */     
/* 109 */     this.statusProvider = new LabelProvider<String>()
/*     */       {
/*     */         public String getLabel(String status)
/*     */         {
/* 113 */           return status;
/*     */         }
/*     */       };
/*     */     
/* 117 */     this.resultStore = new ListStore(new ModelKeyProvider<String>()
/*     */         {
/*     */           
/*     */           public String getKey(String result)
/*     */           {
/* 122 */             return result;
/*     */           }
/*     */         });
/*     */     
/* 126 */     this.resultProvider = new LabelProvider<String>()
/*     */       {
/*     */         public String getLabel(String result)
/*     */         {
/* 130 */           return result;
/*     */         }
/*     */       };
/*     */ 
/*     */     
/* 135 */     initWidget((Widget)uiBinder.createAndBindUi(this));
/*     */     
/* 137 */     Date now = new Date();
/* 138 */     this.dateRangePicker.setStartDate(now);
/* 139 */     this.dateRangePicker.setEndDate(now);
/* 140 */     this.dateRangePicker.setTimePickerIncrement(1);
/*     */     
/* 142 */     this.presenter.retrieveLocation();
/* 143 */     addEventHandlers();
/* 144 */     setStatusCB();
/* 145 */     setResultCB();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Map<String, Object> getInputParameter() {
/* 155 */     Map<String, Object> map = new HashMap<>();
/* 156 */     Date startDateTime = this.dateRangePicker.getStartDate();
/* 157 */     Date endDateTime = this.dateRangePicker.getEndDate();
/*     */     
/* 159 */     map.put("startDateTime", DateTimeFormat.getFormat("yyyy-MM-dd HH:mm:ss").format(startDateTime));
/* 160 */     map.put("endDateTime", DateTimeFormat.getFormat("yyyy-MM-dd HH:mm:ss").format(endDateTime));
/* 161 */     map.put("location", ((RoomNcuDeviceNameDTO)this.location.getValue()).getDeviceName());
/* 162 */     map.put("status", this.status.getValue());
/* 163 */     map.put("result", this.result.getValue());
/* 164 */     map.put("userName", RptEP.getUserName());
/*     */     
/* 166 */     return map;
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
/* 178 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void clearInputParameter() {}
/*     */ 
/*     */   
/*     */   public void setHeading(String heading) {}
/*     */   
/*     */   private void addEventHandlers() {
/* 188 */     this.dateRangePicker.addValueChangeCallback(new BiConsumer<Date, Date>()
/*     */         {
/*     */           public void accept(Date t, Date u)
/*     */           {
/* 192 */             Long timeIntervalDay = Long.valueOf((u.getTime() - t.getTime()) / 86400000L);
/* 193 */             if (timeIntervalDay.longValue() > 7L) {
/* 194 */               Date now = new Date();
/* 195 */               RIPViewerByCardPermissionDownloadLog.this.dateRangePicker.setStartDate(now);
/* 196 */               RIPViewerByCardPermissionDownloadLog.this.dateRangePicker.setEndDate(now);
/* 197 */               RIPViewerByCardPermissionDownloadLog.this.initDialog();
/*     */             } 
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   private void initDialog() {
/* 204 */     if (this.warningDialog == null) {
/* 205 */       this.warningDialog = new Dialog();
/* 206 */       this.warningDialog.setWidth(210);
/* 207 */       this.warningDialog.setHeight(70);
/* 208 */       this.warningDialog.setModal(true);
/* 209 */       this.warningDialog.setResizable(false);
/* 210 */       this.warningDialog.setHeading("查詢條件不符");
/* 211 */       this.warningDialog.setHideOnButtonClick(true);
/*     */     } 
/*     */     
/* 214 */     Label label = new Label("日期範圍不得大於7天!\n請重新選取");
/* 215 */     this.warningDialog.add((Widget)label);
/* 216 */     this.warningDialog.show();
/*     */   }
/*     */   
/*     */   private void setStatusCB() {
/* 220 */     String statusAll = "全部";
/* 221 */     this.statusStore.add(statusAll);
/* 222 */     String status1 = "權限新增";
/* 223 */     this.statusStore.add(status1);
/* 224 */     String status2 = "權限刪除";
/* 225 */     this.statusStore.add(status2);
/* 226 */     this.status.setValue(this.statusStore.get(0));
/*     */   }
/*     */   
/*     */   private void setResultCB() {
/* 230 */     String resultAll = "全部";
/* 231 */     this.resultStore.add(resultAll);
/* 232 */     String resultSuccess = "成功";
/* 233 */     this.resultStore.add(resultSuccess);
/* 234 */     String resultFail = "失敗";
/* 235 */     this.resultStore.add(resultFail);
/* 236 */     this.result.setValue(this.resultStore.get(0));
/*     */   }
/*     */   
/*     */   public String getDeviceType() {
/* 240 */     return null;
/*     */   }
/*     */   
/*     */   public void fillLocation(List<RoomNcuDeviceNameDTO> list) {
/* 244 */     this.locationStore.clear();
/* 245 */     RoomNcuDeviceNameDTO all = new RoomNcuDeviceNameDTO();
/* 246 */     all.setDeviceName("全部");
/* 247 */     all.setDisplayName("全部");
/* 248 */     this.locationStore.add(all);
/* 249 */     this.locationStore.addAll(list);
/* 250 */     this.location.setValue(all);
/*     */   }
/*     */   
/*     */   public void setPresenter(RIPViewerByCardPermissionDownloadLogPresenter presenter) {
/* 254 */     this.presenter = presenter;
/*     */   }
/*     */   
/*     */   static interface RIPViewerByCardReaderLogUiBinder extends UiBinder<Widget, RIPViewerByCardPermissionDownloadLog> {}
/*     */ }


/* Location:              C:\User\\user\Desktop\lib\aoAdmin-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ao\am\view\rpt\RIPViewerByCardPermissionDownloadLog.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */