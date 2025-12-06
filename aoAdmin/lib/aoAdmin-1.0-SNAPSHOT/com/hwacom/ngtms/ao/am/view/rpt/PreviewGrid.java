/*     */ package com.hwacom.ngtms.ao.am.view.rpt;
/*     */ 
/*     */ import com.google.gwt.core.client.GWT;
/*     */ import com.google.gwt.safehtml.shared.SafeHtmlUtils;
/*     */ import com.google.gwt.uibinder.client.UiBinder;
/*     */ import com.google.gwt.uibinder.client.UiField;
/*     */ import com.google.gwt.user.client.ui.Widget;
/*     */ import com.hwacom.ngtms.common.shared.dto.PreviewDataDTO;
/*     */ import com.hwacom.ngtms.common.shared.dto.PreviewHeaderDTO;
/*     */ import com.sencha.gxt.core.client.ValueProvider;
/*     */ import com.sencha.gxt.data.shared.ListStore;
/*     */ import com.sencha.gxt.data.shared.ModelKeyProvider;
/*     */ import com.sencha.gxt.data.shared.PropertyAccess;
/*     */ import com.sencha.gxt.widget.core.client.Composite;
/*     */ import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
/*     */ import com.sencha.gxt.widget.core.client.grid.ColumnModel;
/*     */ import com.sencha.gxt.widget.core.client.grid.Grid;
/*     */ import com.sencha.gxt.widget.core.client.grid.GridView;
/*     */ import java.util.ArrayList;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PreviewGrid
/*     */   extends Composite
/*     */ {
/*  34 */   private static PreviewGridUiBinder uiBinder = (PreviewGridUiBinder)GWT.create(PreviewGridUiBinder.class);
/*     */ 
/*     */ 
/*     */   
/*  38 */   private PreviewPropertyAccess propertyAccess = (PreviewPropertyAccess)GWT.create(PreviewPropertyAccess.class);
/*     */   
/*     */   @UiField(provided = true)
/*     */   ColumnModel<PreviewDataDTO> cm;
/*     */   
/*     */   @UiField(provided = true)
/*     */   ListStore<PreviewDataDTO> listStore;
/*     */   @UiField
/*     */   GridView<PreviewDataDTO> gridView;
/*     */   @UiField
/*     */   Grid<PreviewDataDTO> grid;
/*     */   
/*     */   public PreviewGrid(Map<String, PreviewHeaderDTO> headerMap) {
/*  51 */     this.listStore = new ListStore(this.propertyAccess.id());
/*  52 */     initColumnModel(headerMap);
/*  53 */     initWidget((Widget)uiBinder.createAndBindUi(this));
/*     */   }
/*     */   
/*     */   private void initColumnModel(Map<String, PreviewHeaderDTO> headerMap) {
/*  57 */     List<ColumnConfig<PreviewDataDTO, ?>> configs = new ArrayList<>();
/*     */ 
/*     */ 
/*     */     
/*  61 */     Map<String, ColumnConfig<PreviewDataDTO, String>> configMap = new LinkedHashMap<>();
/*     */     
/*  63 */     configMap.put("col1", new ColumnConfig(this.propertyAccess.col1()));
/*  64 */     configMap.put("col2", new ColumnConfig(this.propertyAccess.col2()));
/*  65 */     configMap.put("col3", new ColumnConfig(this.propertyAccess.col3()));
/*  66 */     configMap.put("col4", new ColumnConfig(this.propertyAccess.col4()));
/*  67 */     configMap.put("col5", new ColumnConfig(this.propertyAccess.col5()));
/*  68 */     configMap.put("col6", new ColumnConfig(this.propertyAccess.col6()));
/*  69 */     configMap.put("col7", new ColumnConfig(this.propertyAccess.col7()));
/*  70 */     configMap.put("col8", new ColumnConfig(this.propertyAccess.col8()));
/*  71 */     configMap.put("col9", new ColumnConfig(this.propertyAccess.col9()));
/*  72 */     configMap.put("col10", new ColumnConfig(this.propertyAccess.col10()));
/*  73 */     configMap.put("col11", new ColumnConfig(this.propertyAccess.col11()));
/*  74 */     configMap.put("col12", new ColumnConfig(this.propertyAccess.col12()));
/*  75 */     configMap.put("col13", new ColumnConfig(this.propertyAccess.col13()));
/*  76 */     configMap.put("col14", new ColumnConfig(this.propertyAccess.col14()));
/*  77 */     configMap.put("col15", new ColumnConfig(this.propertyAccess.col15()));
/*  78 */     configMap.put("col16", new ColumnConfig(this.propertyAccess.col16()));
/*  79 */     configMap.put("col17", new ColumnConfig(this.propertyAccess.col17()));
/*  80 */     configMap.put("col18", new ColumnConfig(this.propertyAccess.col18()));
/*  81 */     configMap.put("col19", new ColumnConfig(this.propertyAccess.col19()));
/*  82 */     configMap.put("col20", new ColumnConfig(this.propertyAccess.col20()));
/*  83 */     configMap.put("col21", new ColumnConfig(this.propertyAccess.col21()));
/*  84 */     configMap.put("col22", new ColumnConfig(this.propertyAccess.col22()));
/*  85 */     configMap.put("col23", new ColumnConfig(this.propertyAccess.col23()));
/*  86 */     configMap.put("col24", new ColumnConfig(this.propertyAccess.col24()));
/*  87 */     configMap.put("col25", new ColumnConfig(this.propertyAccess.col25()));
/*  88 */     configMap.put("col26", new ColumnConfig(this.propertyAccess.col26()));
/*  89 */     configMap.put("col27", new ColumnConfig(this.propertyAccess.col27()));
/*  90 */     configMap.put("col28", new ColumnConfig(this.propertyAccess.col28()));
/*  91 */     configMap.put("col29", new ColumnConfig(this.propertyAccess.col29()));
/*  92 */     configMap.put("col30", new ColumnConfig(this.propertyAccess.col30()));
/*     */     
/*  94 */     for (Map.Entry<String, ColumnConfig<PreviewDataDTO, String>> e : configMap.entrySet()) {
/*  95 */       PreviewHeaderDTO headerDto = headerMap.get(e.getKey());
/*  96 */       if (headerDto == null) {
/*     */         continue;
/*     */       }
/*     */       
/* 100 */       ColumnConfig<PreviewDataDTO, String> config = e.getValue();
/* 101 */       if (headerDto.getHeader() != null && !headerDto.getHeader().isEmpty()) {
/* 102 */         config.setHeader(SafeHtmlUtils.fromTrustedString(headerDto.getHeader()));
/*     */       }
/* 104 */       if (headerDto.getWidth() != null) {
/* 105 */         config.setWidth(headerDto.getWidth().intValue());
/*     */       }
/* 107 */       config.setHideable(false);
/* 108 */       config.setMenuDisabled(true);
/* 109 */       configs.add(config);
/*     */     } 
/* 111 */     this.cm = new ColumnModel(configs);
/*     */   }
/*     */   
/*     */   static interface PreviewPropertyAccess extends PropertyAccess<PreviewDataDTO> {
/*     */     ModelKeyProvider<PreviewDataDTO> id();
/*     */     
/*     */     ValueProvider<PreviewDataDTO, String> col1();
/*     */     
/*     */     ValueProvider<PreviewDataDTO, String> col2();
/*     */     
/*     */     ValueProvider<PreviewDataDTO, String> col3();
/*     */     
/*     */     ValueProvider<PreviewDataDTO, String> col4();
/*     */     
/*     */     ValueProvider<PreviewDataDTO, String> col5();
/*     */     
/*     */     ValueProvider<PreviewDataDTO, String> col6();
/*     */     
/*     */     ValueProvider<PreviewDataDTO, String> col7();
/*     */     
/*     */     ValueProvider<PreviewDataDTO, String> col8();
/*     */     
/*     */     ValueProvider<PreviewDataDTO, String> col9();
/*     */     
/*     */     ValueProvider<PreviewDataDTO, String> col10();
/*     */     
/*     */     ValueProvider<PreviewDataDTO, String> col11();
/*     */     
/*     */     ValueProvider<PreviewDataDTO, String> col12();
/*     */     
/*     */     ValueProvider<PreviewDataDTO, String> col13();
/*     */     
/*     */     ValueProvider<PreviewDataDTO, String> col14();
/*     */     
/*     */     ValueProvider<PreviewDataDTO, String> col15();
/*     */     
/*     */     ValueProvider<PreviewDataDTO, String> col16();
/*     */     
/*     */     ValueProvider<PreviewDataDTO, String> col17();
/*     */     
/*     */     ValueProvider<PreviewDataDTO, String> col18();
/*     */     
/*     */     ValueProvider<PreviewDataDTO, String> col19();
/*     */     
/*     */     ValueProvider<PreviewDataDTO, String> col20();
/*     */     
/*     */     ValueProvider<PreviewDataDTO, String> col21();
/*     */     
/*     */     ValueProvider<PreviewDataDTO, String> col22();
/*     */     
/*     */     ValueProvider<PreviewDataDTO, String> col23();
/*     */     
/*     */     ValueProvider<PreviewDataDTO, String> col24();
/*     */     
/*     */     ValueProvider<PreviewDataDTO, String> col25();
/*     */     
/*     */     ValueProvider<PreviewDataDTO, String> col26();
/*     */     
/*     */     ValueProvider<PreviewDataDTO, String> col27();
/*     */     
/*     */     ValueProvider<PreviewDataDTO, String> col28();
/*     */     
/*     */     ValueProvider<PreviewDataDTO, String> col29();
/*     */     
/*     */     ValueProvider<PreviewDataDTO, String> col30();
/*     */   }
/*     */   
/*     */   static interface PreviewGridUiBinder extends UiBinder<Widget, PreviewGrid> {}
/*     */ }


/* Location:              C:\User\\user\Desktop\lib\aoAdmin-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ao\am\view\rpt\PreviewGrid.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */