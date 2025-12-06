/*     */ package com.hwacom.ngtms.ao.am.view.rpt;
/*     */ 
/*     */ import com.google.gwt.core.client.GWT;
/*     */ import com.google.gwt.safehtml.shared.SafeHtmlUtils;
/*     */ import com.google.gwt.uibinder.client.UiBinder;
/*     */ import com.google.gwt.uibinder.client.UiField;
/*     */ import com.google.gwt.user.client.ui.Widget;
/*     */ import com.hwacom.ngtms.common.shared.dto.PreviewGridDataDTO;
/*     */ import com.hwacom.ngtms.common.shared.dto.PreviewGridHeaderDTO;
/*     */ import com.hwacom.ngtms.common.shared.dto.PreviewGridRowDTO;
/*     */ import com.sencha.gxt.core.client.ValueProvider;
/*     */ import com.sencha.gxt.data.shared.ListStore;
/*     */ import com.sencha.gxt.data.shared.ModelKeyProvider;
/*     */ import com.sencha.gxt.data.shared.PropertyAccess;
/*     */ import com.sencha.gxt.widget.core.client.Composite;
/*     */ import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
/*     */ import com.sencha.gxt.widget.core.client.grid.ColumnModel;
/*     */ import com.sencha.gxt.widget.core.client.grid.GridView;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
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
/*     */ 
/*     */ public class PreviewGridWithinData
/*     */   extends Composite
/*     */ {
/*  35 */   private static PreviewGridWithinDataUiBinder uiBinder = (PreviewGridWithinDataUiBinder)GWT.create(PreviewGridWithinDataUiBinder.class);
/*     */ 
/*     */ 
/*     */   
/*  39 */   private PreviewPropertyAccess propertyAccess = (PreviewPropertyAccess)GWT.create(PreviewPropertyAccess.class);
/*     */   
/*     */   @UiField(provided = true)
/*     */   ColumnModel<PreviewGridRowDTO> cm;
/*     */   
/*     */   @UiField(provided = true)
/*     */   ListStore<PreviewGridRowDTO> listStore;
/*     */   @UiField
/*     */   GridView<PreviewGridRowDTO> gridView;
/*     */   
/*     */   public PreviewGridWithinData(PreviewGridDataDTO previewGridData) {
/*  50 */     this.listStore = new ListStore(this.propertyAccess.id());
/*  51 */     initColumnModel(previewGridData.getHeaders());
/*  52 */     initWidget((Widget)uiBinder.createAndBindUi(this));
/*  53 */     this.listStore.addAll(previewGridData.getRows());
/*     */   }
/*     */   
/*     */   private void initColumnModel(List<PreviewGridHeaderDTO> headers) {
/*  57 */     List<ColumnConfig<PreviewGridRowDTO, ?>> configs = new ArrayList<>();
/*     */     
/*  59 */     for (PreviewGridHeaderDTO header : headers) {
/*  60 */       String columnId = header.getColumnId();
/*  61 */       ColumnConfig<PreviewGridRowDTO, ?> config = new ColumnConfig(new ColumnValueProvider(columnId));
/*     */       
/*  63 */       if (header.getHeader() != null && !header.getHeader().isEmpty()) {
/*  64 */         config.setHeader(SafeHtmlUtils.fromTrustedString(header.getHeader()));
/*     */       }
/*  66 */       if (header.getWidth() != null) {
/*  67 */         config.setWidth(header.getWidth().intValue());
/*     */       }
/*  69 */       config.setHideable(false);
/*  70 */       config.setMenuDisabled(true);
/*  71 */       configs.add(config);
/*     */     } 
/*  73 */     this.cm = new ColumnModel(configs);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   class ColumnValueProvider
/*     */     implements ValueProvider<PreviewGridRowDTO, Object>
/*     */   {
/*     */     private String columnId;
/*     */ 
/*     */     
/*     */     ColumnValueProvider(String columnId) {
/*  85 */       this.columnId = columnId;
/*     */     }
/*     */ 
/*     */     
/*     */     public Object getValue(PreviewGridRowDTO object) {
/*  90 */       Object value = object.getColumnValMap().get(this.columnId);
/*  91 */       if (value == null) {
/*  92 */         value = "";
/*     */       }
/*  94 */       return value;
/*     */     }
/*     */ 
/*     */     
/*     */     public void setValue(PreviewGridRowDTO object, Object value) {}
/*     */ 
/*     */     
/*     */     public String getPath() {
/* 102 */       return "";
/*     */     }
/*     */   }
/*     */   
/*     */   static interface PreviewPropertyAccess extends PropertyAccess<PreviewGridRowDTO> {
/*     */     ModelKeyProvider<PreviewGridRowDTO> id();
/*     */   }
/*     */   
/*     */   static interface PreviewGridWithinDataUiBinder extends UiBinder<Widget, PreviewGridWithinData> {}
/*     */ }


/* Location:              C:\User\\user\Desktop\lib\aoAdmin-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ao\am\view\rpt\PreviewGridWithinData.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */