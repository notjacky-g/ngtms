/*    */ package com.hwacom.ngtms.ao.am.view.rpt;
/*    */ 
/*    */ import com.google.gwt.core.client.GWT;
/*    */ import com.google.gwt.uibinder.client.UiBinder;
/*    */ import com.google.gwt.uibinder.client.UiField;
/*    */ import com.google.gwt.user.client.ui.HTML;
/*    */ import com.google.gwt.user.client.ui.Widget;
/*    */ import com.sencha.gxt.data.client.loader.RpcProxy;
/*    */ import com.sencha.gxt.data.shared.loader.DataProxy;
/*    */ import com.sencha.gxt.data.shared.loader.LoadEvent;
/*    */ import com.sencha.gxt.data.shared.loader.LoadHandler;
/*    */ import com.sencha.gxt.data.shared.loader.PagingLoadConfig;
/*    */ import com.sencha.gxt.data.shared.loader.PagingLoadResult;
/*    */ import com.sencha.gxt.data.shared.loader.PagingLoader;
/*    */ import com.sencha.gxt.widget.core.client.Composite;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class HtmlPreviewer
/*    */   extends Composite
/*    */ {
/* 25 */   private static HtmlPreviewerUiBinder uiBinder = (HtmlPreviewerUiBinder)GWT.create(HtmlPreviewerUiBinder.class);
/*    */   
/*    */   private PagingLoader<PagingLoadConfig, PagingLoadResult<String>> pagingLoader;
/*    */   
/*    */   @UiField
/*    */   ReportPreviewToolBar previewToolBar;
/*    */   
/*    */   @UiField
/*    */   HTML previewContent;
/*    */   
/*    */   public HtmlPreviewer() {
/* 36 */     initWidget((Widget)uiBinder.createAndBindUi(this));
/*    */   }
/*    */   
/*    */   public void initPagingLoader(RpcProxy<PagingLoadConfig, PagingLoadResult<String>> rpcProxy) {
/* 40 */     this.pagingLoader = new PagingLoader((DataProxy)rpcProxy);
/* 41 */     this.pagingLoader.addLoadHandler(new LoadHandler<PagingLoadConfig, PagingLoadResult<String>>()
/*    */         {
/*    */           public void onLoad(LoadEvent<PagingLoadConfig, PagingLoadResult<String>> event)
/*    */           {
/* 45 */             HtmlPreviewer.this.previewContent.setHTML(((PagingLoadResult)event.getLoadResult()).getData().get(0));
/*    */           }
/*    */         });
/*    */     
/* 49 */     this.previewToolBar.pagingToolBar.bind(this.pagingLoader);
/* 50 */     this.previewToolBar.zoomLevel.setValue(this.previewToolBar.getDefaultZoomRatio());
/* 51 */     this.previewToolBar.pagingToolBar.clear();
/* 52 */     this.previewToolBar.pagingToolBar.enable();
/* 53 */     this.previewContent.setHTML("");
/*    */     
/* 55 */     this.pagingLoader.setOffset(0);
/* 56 */     this.pagingLoader.load();
/*    */   }
/*    */   
/*    */   public void reload() {
/* 60 */     this.pagingLoader.setOffset(0);
/* 61 */     this.pagingLoader.load();
/*    */   }
/*    */   static interface HtmlPreviewerUiBinder extends UiBinder<Widget, HtmlPreviewer> {}
/*    */   public float getRoomRatio() {
/* 65 */     return this.previewToolBar.getRoomRatio();
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\lib\aoAdmin-1.0-SNAPSHOT.jar!\com\hwacom\ngtms\ao\am\view\rpt\HtmlPreviewer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */