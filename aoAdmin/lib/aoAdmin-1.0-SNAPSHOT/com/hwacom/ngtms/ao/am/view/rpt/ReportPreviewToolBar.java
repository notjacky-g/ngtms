/*     */ package com.hwacom.ngtms.ao.am.view.rpt;
/*     */ 
/*     */ import com.google.gwt.core.client.GWT;
/*     */ import com.google.gwt.editor.client.Editor.Path;
/*     */ import com.google.gwt.event.logical.shared.HasSelectionHandlers;
/*     */ import com.google.gwt.event.logical.shared.SelectionEvent;
/*     */ import com.google.gwt.event.shared.GwtEvent;
/*     */ import com.google.gwt.uibinder.client.UiBinder;
/*     */ import com.google.gwt.uibinder.client.UiField;
/*     */ import com.google.gwt.uibinder.client.UiHandler;
/*     */ import com.google.gwt.user.client.ui.Widget;
/*     */ import com.hwacom.ngtms.ao.am.event.ReportGenerationEvent;
/*     */ import com.hwacom.ngtms.hcce.am.factory.ClientFactory;
/*     */ import com.sencha.gxt.data.shared.LabelProvider;
/*     */ import com.sencha.gxt.data.shared.ListStore;
/*     */ import com.sencha.gxt.data.shared.ModelKeyProvider;
/*     */ import com.sencha.gxt.data.shared.PropertyAccess;
/*     */ import com.sencha.gxt.widget.core.client.Composite;
/*     */ import com.sencha.gxt.widget.core.client.button.TextButton;
/*     */ import com.sencha.gxt.widget.core.client.event.SelectEvent;
/*     */ import com.sencha.gxt.widget.core.client.form.ComboBox;
/*     */ import com.sencha.gxt.widget.core.client.toolbar.PagingToolBar;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ReportPreviewToolBar
/*     */   extends Composite
/*     */ {
/*  32 */   private static ReportPreviewToolBarUiBinder uiBinder = (ReportPreviewToolBarUiBinder)GWT.create(ReportPreviewToolBarUiBinder.class);
/*     */   
/*     */   @UiField
/*     */   PagingToolBar pagingToolBar;
/*     */   
/*     */   @UiField
/*     */   TextButton decrement;
/*     */   
/*     */   @UiField
/*     */   TextButton increment;
/*     */   
/*     */   @UiField
/*     */   ComboBox<ZoomRatio> zoomLevel;
/*     */   
/*     */   @UiField(provided = true)
/*     */   ListStore<ZoomRatio> store;
/*     */   @UiField(provided = true)
/*     */   LabelProvider<ZoomRatio> labelProvider;
/*  50 */   private final ClientFactory clientFactory = (ClientFactory)GWT.create(ClientFactory.class);
/*     */   
/*  52 */   private final ZoomRatioPropertyAccess propertyAccess = (ZoomRatioPropertyAccess)GWT.create(ZoomRatioPropertyAccess.class);
/*     */   
/*     */   private ZoomRatio defaultRatio;
/*     */   
/*     */   public ReportPreviewToolBar() {
/*  57 */     this.store = new ListStore(this.propertyAccess.id());
/*  58 */     this.labelProvider = this.propertyAccess.label();
/*  59 */     initWidget((Widget)uiBinder.createAndBindUi(this));
/*     */     
/*  61 */     this.defaultRatio = new ZoomRatio("100%", 1.0F);
/*  62 */     this.store.add(new ZoomRatio("50%", 0.5F));
/*  63 */     this.store.add(this.defaultRatio);
/*  64 */     this.store.add(new ZoomRatio("200%", 2.0F));
/*  65 */     this.store.add(new ZoomRatio("300%", 3.0F));
/*  66 */     this.zoomLevel.setValue(this.defaultRatio);
/*     */     
/*  68 */     addEventHandlers();
/*     */   }
/*     */   
/*     */   private void addEventHandlers() {}
/*     */   
/*     */   @UiHandler({"zoomLevel"})
/*     */   public void onSelectZoomLevel(SelectionEvent<ZoomRatio> event) {
/*  75 */     int currentIndex = this.store.getAll().indexOf(event.getSelectedItem());
/*  76 */     if (currentIndex == 0) {
/*  77 */       this.decrement.disable();
/*     */     } else {
/*  79 */       this.decrement.enable();
/*     */     } 
/*     */     
/*  82 */     if (currentIndex == this.store.size() - 1) {
/*  83 */       this.increment.disable();
/*     */     } else {
/*  85 */       this.increment.enable();
/*     */     } 
/*     */     
/*  88 */     this.clientFactory
/*  89 */       .getEventBus()
/*  90 */       .fireEvent((GwtEvent)new ReportGenerationEvent(ReportGenerationEvent.Action.REPORT_PREVIEW_TOOL_BAR_ZOOM));
/*     */   }
/*     */   
/*     */   @UiHandler({"decrement"})
/*     */   public void decrease(SelectEvent event) {
/*  95 */     int currentIndex = this.store.getAll().indexOf(this.zoomLevel.getValue());
/*  96 */     ZoomRatio ratio = (ZoomRatio)this.store.get(currentIndex - 1);
/*  97 */     this.zoomLevel.setValue(ratio);
/*  98 */     SelectionEvent.fire((HasSelectionHandlers)this.zoomLevel, ratio);
/*     */   }
/*     */   
/*     */   @UiHandler({"increment"})
/*     */   public void increase(SelectEvent event) {
/* 103 */     int currentIndex = this.store.getAll().indexOf(this.zoomLevel.getValue());
/* 104 */     ZoomRatio ratio = (ZoomRatio)this.store.get(currentIndex + 1);
/* 105 */     this.zoomLevel.setValue(ratio);
/* 106 */     SelectionEvent.fire((HasSelectionHandlers)this.zoomLevel, ratio);
/*     */   }
/*     */   
/*     */   public float getRoomRatio() {
/* 110 */     return ((ZoomRatio)this.zoomLevel.getCurrentValue()).getRatio();
/*     */   }
/*     */   
/*     */   public ZoomRatio getDefaultZoomRatio() {
/* 114 */     return this.defaultRatio;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public class ZoomRatio
/*     */   {
/*     */     private String label;
/*     */ 
/*     */     
/*     */     private float ratio;
/*     */ 
/*     */ 
/*     */     
/*     */     public ZoomRatio() {}
/*     */ 
/*     */ 
/*     */     
/*     */     public ZoomRatio(String label, float ratio) {
/* 133 */       this.label = label;
/* 134 */       this.ratio = ratio;
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 139 */       int hash = 0;
/* 140 */       hash += (this.label != null) ? this.label.hashCode() : 0;
/* 141 */       return hash;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object obj) {
/* 146 */       if (!(obj instanceof ZoomRatio)) {
/* 147 */         return false;
/*     */       }
/* 149 */       ZoomRatio other = (ZoomRatio)obj;
/* 150 */       if ((this.label == null && other.label != null) || (this.label != null && 
/* 151 */         !this.label.equals(other.label))) {
/* 152 */         return false;
/*     */       }
/* 154 */       return true;
/*     */     }
/*     */     
/*     */     public String getLabel() {
/* 158 */       return this.label;
/*     */     }
/*     */     
/*     */     public void setLabel(String label) {
/* 162 */       this.label = label;
/*     */     }
/*     */     
/*     */     public float getRatio() {
/* 166 */       return this.ratio;
/*     */     }
/*     */     
/*     */     public void setRatio(float ratio) {
/* 170 */       this.ratio = ratio;
/*     */     }
/*     */   }
/*     */   
/*     */   static interface ZoomRatioPropertyAccess extends PropertyAccess<ZoomRatio> {
/*     */     @Path("label")
/*     */     ModelKeyProvider<ReportPreviewToolBar.ZoomRatio> id();
/*     */     
/*     */     LabelProvider<ReportPreviewToolBar.ZoomRatio> label();
/*     */   }
/*     */   
/*     */   static interface ReportPreviewToolBarUiBinder extends UiBinder<Widget, ReportPreviewToolBar> {}
/*     */ }


/* Location:              C:\User\\user\Desktop\lib\aoAdmin-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ao\am\view\rpt\ReportPreviewToolBar.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */