/*
 * © HwaCom Systems Inc. 2018
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.common.am.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Widget;
import com.hwacom.ngtms.common.am.event.ReportViewerEvent;
import com.hwacom.ngtms.common.am.event.ReportViewerEvent.Action;
import com.hwacom.ngtms.hcce.am.factory.ClientFactory;
import com.sencha.gxt.data.shared.LabelProvider;
import com.sencha.gxt.data.shared.loader.PagingLoadConfig;
import com.sencha.gxt.data.shared.loader.PagingLoadResult;
import com.sencha.gxt.data.shared.loader.PagingLoader;
import com.sencha.gxt.widget.core.client.Composite;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.SimpleContainer;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.form.SimpleComboBox;
import com.sencha.gxt.widget.core.client.toolbar.PagingToolBar;
import com.sencha.gxt.widget.core.client.toolbar.PagingToolBar.PagingToolBarMessages;
import com.sencha.gxt.widget.core.client.toolbar.ToolBar;
import java.util.AbstractMap.SimpleEntry;
import java.util.Map.Entry;

public class ReportToolBar extends Composite {

  private static ReportToolBarUiBinder uiBinder = GWT.create(ReportToolBarUiBinder.class);

  interface ReportToolBarUiBinder extends UiBinder<Widget, ReportToolBar> {}

  private ClientFactory clientFactory = GWT.create(ClientFactory.class);

  private PagingLoader<PagingLoadConfig, PagingLoadResult<String>> pagingLoader;

  private PagingToolBar pagingToolBar;

  @UiField ToolBar toolBar;

  @UiField SimpleContainer pagingToolBarContainer;

  @UiField(provided = true)
  LabelProvider<Entry<String, Float>> labelProvider;

  @UiField SimpleComboBox<Entry<String, Float>> zoomRatio;

  @UiField TextButton exportCsv;

  @UiField TextButton exportPdf;

  @UiField TextButton exportXls;

  @UiField TextButton exportPng;

  @UiField TextButton exportJpeg;

  public ReportToolBar(PagingLoader<PagingLoadConfig, PagingLoadResult<String>> pagingLoader) {
    this.pagingLoader = pagingLoader;
    pagingLoader.addLoadHandler(event -> pagingToolBar.enable());
    labelProvider = item -> item.getKey();
    initWidget(uiBinder.createAndBindUi(this));
    initZoomRatio();
  }

  private void initZoomRatio() {
    Entry<String, Float> defaultZoomRatio = new SimpleEntry<>("150%", 1.5f);
    zoomRatio.add(new SimpleEntry<>("50%", 0.5f));
    zoomRatio.add(new SimpleEntry<>("75%", 0.75f));
    zoomRatio.add(new SimpleEntry<>("100%", 1.0f));
    zoomRatio.add(new SimpleEntry<>("125%", 1.25f));
    zoomRatio.add(defaultZoomRatio);
    zoomRatio.add(new SimpleEntry<>("175%", 1.75f));
    zoomRatio.add(new SimpleEntry<>("200%", 2.0f));
    zoomRatio.setValue(defaultZoomRatio);
  }

  private PagingToolBarMessages createPagingToolBarMessages() {
    return new PagingToolBarMessages() {
      @Override
      public String refreshText() {
        return pagingToolBar.getMessages().refreshText();
      }

      @Override
      public String prevText() {
        return pagingToolBar.getMessages().prevText();
      }

      @Override
      public String nextText() {
        return pagingToolBar.getMessages().nextText();
      }

      @Override
      public String lastText() {
        return pagingToolBar.getMessages().lastText();
      }

      @Override
      public String firstText() {
        return pagingToolBar.getMessages().firstText();
      }

      @Override
      public String emptyMessage() {
        return pagingToolBar.getMessages().emptyMessage();
      }

      @Override
      public String displayMessage(int start, int end, int total) {
        return "";
      }

      @Override
      public String beforePageText() {
        return "";
      }

      @Override
      public String afterPageText(int page) {
        return "/ " + page;
      }
    };
  }

  public float getZoomRatio() {
    return zoomRatio.getCurrentValue().getValue();
  }

  public void setExportPdf(Boolean isShow) {
    exportPdf.setVisible(isShow);
  }

  public void setExportXls(Boolean isShow) {
    exportXls.setVisible(isShow);
  }

  public void setExportPng(Boolean isShow) {
    exportPng.setVisible(isShow);
    exportPng.enable();
  }

  public void setExportJpeg(Boolean isShow) {
    exportJpeg.setVisible(isShow);
    exportJpeg.enable();
  }

  public void showExportPng(Boolean isShow) {
    if (isShow) {
      exportPng.enable();
    } else {
      exportPng.disable();
    }
  }

  public void showExportJpeg(Boolean isShow) {
    if (isShow) {
      exportJpeg.enable();
    } else {
      exportJpeg.disable();
    }
  }

  @UiHandler("preview")
  public void onPreview(SelectEvent event) {
    pagingLoader.setOffset(0);
    pagingLoader.load();
  }

  @UiHandler("exportCsv")
  public void onExportCsv(SelectEvent event) {
    clientFactory.getEventBus().fireEvent(new ReportViewerEvent(Action.EXPORT_CSV));
  }

  @UiHandler("exportPdf")
  public void onExportPdf(SelectEvent event) {
    clientFactory.getEventBus().fireEvent(new ReportViewerEvent(Action.EXPORT_PDF));
  }

  @UiHandler("exportXls")
  public void onExportXls(SelectEvent event) {
    clientFactory.getEventBus().fireEvent(new ReportViewerEvent(Action.EXPORT_XLS));
  }

  @UiHandler("exportPng")
  public void onExportPng(SelectEvent event) {
    clientFactory.getEventBus().fireEvent(new ReportViewerEvent(Action.EXPORT_PNG));
  }

  @UiHandler("exportJpeg")
  public void onExportJpeg(SelectEvent event) {
    clientFactory.getEventBus().fireEvent(new ReportViewerEvent(Action.EXPORT_JPEG));
  }

  @UiHandler("zoomRatio")
  public void onZoomRatioSelection(SelectionEvent<Entry<String, Float>> event) {
    pagingLoader.load();
  }

  @Override
  public void setEnabled(boolean enabled) {
    super.setEnabled(enabled);
    toolBar.setEnabled(enabled);
  }

  @Override
  public void disable() {
    super.disable();
    toolBar.disable();
  }

  @Override
  public void enable() {
    super.enable();
    toolBar.enable();
  }

  public void createPagingToolBar() {
    pagingToolBar = new PagingToolBar(1);
    pagingToolBar.setMessages(createPagingToolBarMessages());
    pagingToolBar.bind(pagingLoader);
    pagingToolBarContainer.setWidget(pagingToolBar);
    toolBar.forceLayout();
    pagingToolBar.disable();
    pagingLoader.setOffset(0);
  }
}
