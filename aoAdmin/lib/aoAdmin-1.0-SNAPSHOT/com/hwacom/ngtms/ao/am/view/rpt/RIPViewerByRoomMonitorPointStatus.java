/*     */ package com.hwacom.ngtms.ao.am.view.rpt;
/*     */ 
/*     */ import com.google.gwt.core.client.GWT;
/*     */ import com.google.gwt.uibinder.client.UiBinder;
/*     */ import com.google.gwt.uibinder.client.UiField;
/*     */ import com.google.gwt.user.client.ui.Widget;
/*     */ import com.hwacom.ngtms.ao.am.RptEP;
/*     */ import com.hwacom.ngtms.ao.am.presenter.rpt.RIPViewerByRoomMonitorPointStatusPresenter;
/*     */ import com.hwacom.ngtms.ao.am.view.Messages;
/*     */ import com.hwacom.ngtms.ao.shared.dto.PdLocationDTO;
/*     */ import com.hwacom.ngtms.c.shared.dto.DeviceTypeDTO;
/*     */ import com.hwacom.ngtms.common.am.view.RIPViewer;
/*     */ import com.sencha.gxt.data.shared.LabelProvider;
/*     */ import com.sencha.gxt.data.shared.ListStore;
/*     */ import com.sencha.gxt.data.shared.ModelKeyProvider;
/*     */ import com.sencha.gxt.widget.core.client.Composite;
/*     */ import com.sencha.gxt.widget.core.client.container.HorizontalLayoutContainer;
/*     */ import com.sencha.gxt.widget.core.client.form.ComboBox;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RIPViewerByRoomMonitorPointStatus
/*     */   extends Composite
/*     */   implements RIPViewer
/*     */ {
/*  33 */   private static RIPViewerByCardReaderLogUiBinder uiBinder = (RIPViewerByCardReaderLogUiBinder)GWT.create(RIPViewerByCardReaderLogUiBinder.class);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  38 */   private static final Messages messages = (Messages)GWT.create(Messages.class);
/*     */   
/*     */   private static final String dateTimeFormat = "yyyy-MM-dd HH:mm:ss";
/*     */   
/*  42 */   private RIPViewerByRoomMonitorPointStatusPresenter presenter = new RIPViewerByRoomMonitorPointStatusPresenter(this);
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
/*     */   ComboBox<String> signalType;
/*     */   @UiField(provided = true)
/*     */   ListStore<String> signalTypeStore;
/*     */   @UiField(provided = true)
/*     */   LabelProvider<String> signalTypeProvider;
/*     */   @UiField
/*     */   HorizontalLayoutContainer locationContainer;
/*     */   @UiField
/*     */   HorizontalLayoutContainer statusContainer;
/*     */   
/*     */   public RIPViewerByRoomMonitorPointStatus() {
/*  74 */     this.locationStore = new ListStore(new ModelKeyProvider<PdLocationDTO>()
/*     */         {
/*     */           
/*     */           public String getKey(PdLocationDTO location)
/*     */           {
/*  79 */             return location.getId().toString();
/*     */           }
/*     */         });
/*  82 */     this.locationProvider = new LabelProvider<PdLocationDTO>()
/*     */       {
/*     */         public String getLabel(PdLocationDTO signalType)
/*     */         {
/*  86 */           return signalType.getLocName();
/*     */         }
/*     */       };
/*     */     
/*  90 */     this.signalTypeStore = new ListStore(new ModelKeyProvider<String>()
/*     */         {
/*     */           
/*     */           public String getKey(String signalType)
/*     */           {
/*  95 */             return signalType;
/*     */           }
/*     */         });
/*     */     
/*  99 */     this.signalTypeProvider = new LabelProvider<String>()
/*     */       {
/*     */         public String getLabel(String signalType)
/*     */         {
/* 103 */           return signalType;
/*     */         }
/*     */       };
/*     */     
/* 107 */     this.deviceTypeStore = new ListStore(new ModelKeyProvider<DeviceTypeDTO>()
/*     */         {
/*     */           
/*     */           public String getKey(DeviceTypeDTO deviceType)
/*     */           {
/* 112 */             return deviceType.getId();
/*     */           }
/*     */         });
/*     */     
/* 116 */     this.deviceTypeProvider = new LabelProvider<DeviceTypeDTO>()
/*     */       {
/*     */         public String getLabel(DeviceTypeDTO deviceType)
/*     */         {
/* 120 */           return deviceType.getDescription();
/*     */         }
/*     */       };
/*     */ 
/*     */     
/* 125 */     initWidget((Widget)uiBinder.createAndBindUi(this));
/*     */     
/* 127 */     this.presenter.retrieveLocation();
/* 128 */     this.presenter.getDeviceTypeByCategory("ROOM");
/* 129 */     addEventHandlers();
/* 130 */     setSignalTypeCB();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Map<String, Object> getInputParameter() {
/* 140 */     Map<String, Object> map = new HashMap<>();
/*     */     
/* 142 */     map.put("location", ((PdLocationDTO)this.location.getValue()).getLocName());
/* 143 */     map.put("deviceType", ((DeviceTypeDTO)this.deviceType.getValue()).getId());
/* 144 */     map.put("signalType", this.signalType.getValue());
/* 145 */     map.put("userName", RptEP.getUserName());
/*     */     
/* 147 */     return map;
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
/* 159 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void clearInputParameter() {}
/*     */ 
/*     */   
/*     */   public void setHeading(String heading) {}
/*     */   
/*     */   public void setDeviceTypeCB(List<DeviceTypeDTO> deviceTypeList) {
/* 169 */     DeviceTypeDTO allDeviceType = new DeviceTypeDTO();
/* 170 */     allDeviceType.setId("全部");
/* 171 */     allDeviceType.setDescription("全部");
/* 172 */     this.deviceTypeStore.add(allDeviceType);
/* 173 */     this.deviceTypeStore.addAll(deviceTypeList);
/*     */     
/* 175 */     this.deviceType.setValue(this.deviceTypeStore.get(0));
/*     */   }
/*     */   
/*     */   private void addEventHandlers() {}
/*     */   
/*     */   private void setSignalTypeCB() {
/* 181 */     String signalTypeAll = "全部";
/* 182 */     this.signalTypeStore.add(signalTypeAll);
/* 183 */     String signalType1 = "AI(類比輸入)";
/* 184 */     this.signalTypeStore.add(signalType1);
/* 185 */     String signalType2 = "DI(數位輸入)";
/* 186 */     this.signalTypeStore.add(signalType2);
/* 187 */     String signalType3 = "DO(數位輸出)";
/* 188 */     this.signalTypeStore.add(signalType3);
/* 189 */     this.signalType.setValue(this.signalTypeStore.get(0));
/*     */   }
/*     */   
/*     */   public void fillLocation(List<PdLocationDTO> list) {
/* 193 */     this.locationStore.clear();
/* 194 */     PdLocationDTO all = new PdLocationDTO();
/* 195 */     all.setId(Integer.valueOf(-1));
/* 196 */     all.setLocName(messages.comboBox_all());
/* 197 */     this.locationStore.add(all);
/* 198 */     this.locationStore.addAll(list);
/* 199 */     this.location.setValue(all);
/*     */   }
/*     */   
/*     */   public void setPresenter(RIPViewerByRoomMonitorPointStatusPresenter presenter) {
/* 203 */     this.presenter = presenter;
/*     */   }
/*     */   
/*     */   static interface RIPViewerByCardReaderLogUiBinder extends UiBinder<Widget, RIPViewerByRoomMonitorPointStatus> {}
/*     */ }


/* Location:              C:\User\\user\Desktop\lib\aoAdmin-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ao\am\view\rpt\RIPViewerByRoomMonitorPointStatus.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */