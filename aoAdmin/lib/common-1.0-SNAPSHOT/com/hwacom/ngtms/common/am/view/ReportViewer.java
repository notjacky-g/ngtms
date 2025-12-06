/*
 * © HwaCom Systems Inc. 2018
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.common.am.view;

import com.google.gwt.core.client.Callback;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiConstructor;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.hwacom.ngtms.common.am.event.ReportViewerEvent;
import com.hwacom.ngtms.common.am.event.ReportViewerEvent.Action;
import com.hwacom.ngtms.common.am.event.ReportViewerEvent.ReportViewerEventHandler;
import com.hwacom.ngtms.common.am.presenter.ReportPresenter;
import com.hwacom.ngtms.common.am.util.DownloadUtil;
import com.hwacom.ngtms.common.shared.ReportFormat;
import com.hwacom.ngtms.common.shared.dto.ExportDTO;
import com.hwacom.ngtms.common.shared.dto.PreviewReportDTO;
import com.hwacom.ngtms.common.shared.dto.ReportTreeDTO;
import com.hwacom.ngtms.hcce.am.factory.ClientFactory;
import com.sencha.gxt.core.client.Style.SelectionMode;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;
import com.sencha.gxt.data.shared.TreeStore;
import com.sencha.gxt.data.shared.loader.DataProxy;
import com.sencha.gxt.data.shared.loader.PagingLoadConfig;
import com.sencha.gxt.data.shared.loader.PagingLoadResult;
import com.sencha.gxt.data.shared.loader.PagingLoadResultBean;
import com.sencha.gxt.data.shared.loader.PagingLoader;
import com.sencha.gxt.widget.core.client.Composite;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer.BorderLayoutData;
import com.sencha.gxt.widget.core.client.container.SimpleContainer;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.tree.Tree;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ReportViewer extends Composite {

  private static ReportViewerUiBinder uiBinder = GWT.create(ReportViewerUiBinder.class);

  interface ReportViewerUiBinder extends UiBinder<Widget, ReportViewer> {}

  private ReportTreePropertyAccess propertyAccess = GWT.create(ReportTreePropertyAccess.class);

  private ClientFactory clientFactory = GWT.create(ClientFactory.class);

  private RIPViewerInitializer initializer = GWT.create(RIPViewerInitializer.class);

  private RIPViewer ripViewer;

  private ReportPresenter presenter;

  private List<String> pngList = new ArrayList<>();

  @UiField BorderLayoutContainer container;

  @UiField BorderLayoutData southLayoutData;

  @UiField Tree<ReportTreeDTO, String> tree;

  @UiField(provided = true)
  TreeStore<ReportTreeDTO> treeStore;

  @UiField(provided = true)
  ValueProvider<ReportTreeDTO, String> treeValueProvider;

  @UiField SimpleContainer inputParameterContainer;

  @UiField(provided = true)
  ReportToolBar toolBar;

  @UiField HTML previewContent;

  @UiConstructor
  public ReportViewer(String module) {
    treeStore = new TreeStore<ReportTreeDTO>(propertyAccess.id());
    treeValueProvider = propertyAccess.name();

    toolBar = new ReportToolBar(new PagingLoader<>(createDataProxy()));
    initWidget(uiBinder.createAndBindUi(this));
    tree.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    tree.getSelectionModel()
        .addSelectionHandler(
            event -> {
              ReportTreeDTO dto = event.getSelectedItem();
              if (tree.isLeaf(dto)) {
                clientFactory
                    .getEventBus()
                    .fireEventFromSource(new ReportViewerEvent(Action.SELECT_REPORT), dto);
              }
            });
    setExportPdf(false);
    setExportXls(false);
    clientFactory
        .getEventBus()
        .addHandler(ReportViewerEvent.TYPE, new DefaultReportViewerEventHandler());

    presenter = new ReportPresenter(this, module);
  }

  @Override
  protected void onResize(int width, int height) {
    super.onResize(width, height);
    if (width < 1600 && width > 600) {
      southLayoutData.setSize(200);
    } else if (width <= 600) {
      southLayoutData.setSize(100);
    } else {
      southLayoutData.setSize(700);
    }
  }

  public void setTitleViewer(IsWidget widget, int height) {
    container.setNorthWidget(widget, new BorderLayoutData(height));
  }

  public void initTreeData(List<ReportTreeDTO> list) {
    for (ReportTreeDTO dto : list) {
      treeStore.add(dto);
      addTreeData(dto, dto.getChildren());
    }
    tree.expandAll();
  }

  private void addTreeData(ReportTreeDTO parent, List<ReportTreeDTO> children) {
    for (ReportTreeDTO dto : children) {
      treeStore.add(parent, dto);
      addTreeData(dto, dto.getChildren());
    }
  }

  public void setExportPdf(Boolean isShow) {
    toolBar.setExportPdf(isShow);
  }

  public void setExportXls(Boolean isShow) {
    toolBar.setExportXls(isShow);
  }

  public void setExportPng(Boolean isShow) {
    toolBar.setExportPng(isShow);
  }

  public void setExportJpeg(Boolean isShow) {
    toolBar.setExportJpeg(isShow);
  }

  private DataProxy<PagingLoadConfig, PagingLoadResult<String>> createDataProxy() {
    return new DataProxy<PagingLoadConfig, PagingLoadResult<String>>() {
      @Override
      public void load(
          PagingLoadConfig loadConfig, Callback<PagingLoadResult<String>, Throwable> callback) {
        presenter.previewReport(
            getPreviewReportDTO(loadConfig),
            (reportId, previewReportOutputDTO) -> {
              if (!reportId.equals(tree.getSelectionModel().getSelectedItem().getId())) {
                callback.onFailure(new RuntimeException());
              } else {
                previewContent.setHTML(previewReportOutputDTO.getReport());
                PagingLoadResultBean<String> bean = new PagingLoadResultBean<>();
                bean.setOffset(loadConfig.getOffset());
                bean.setTotalLength(previewReportOutputDTO.getTotalPage());
                callback.onSuccess(bean);
              }
            },
            callback);
      }
    };
  }

  private PreviewReportDTO getPreviewReportDTO(PagingLoadConfig loadConfig) {
    PreviewReportDTO dto = new PreviewReportDTO();
    dto.setReportId(tree.getSelectionModel().getSelectedItem().getId());
    dto.setInputParameters(ripViewer.getInputParameter());
    dto.setPageIndex(loadConfig.getOffset());
    dto.setZoomRatio(toolBar.getZoomRatio());
    return dto;
  }

  @UiHandler("allExpand")
  public void onClickAllExpand(SelectEvent se) {
    tree.expandAll();
  }

  @UiHandler("allCollapse")
  public void onClickAllCollapse(SelectEvent se) {
    tree.collapseAll();
  }

  public void downloadCsv(String csv) {
    DownloadUtil.downloadCsv(tree.getSelectionModel().getSelectedItem().getName() + ".csv", csv);
  }

  public void downloadFile(String file) {
    GWT.log("downloadFile:" + file);
    if (file != null) {
      Window.open(file, "_blank", null);
    }
  }

  public void downloadFiles(List<String> files) {
    for (String file : files) {
      GWT.log("downloadFile:" + file);
      if (file != null) {
        Window.open(file, "_blank", null);
      }
    }
  }

  interface ReportTreePropertyAccess extends PropertyAccess<ReportTreeDTO> {
    ModelKeyProvider<ReportTreeDTO> id();

    ValueProvider<ReportTreeDTO, String> name();
  }

  class DefaultReportViewerEventHandler implements ReportViewerEventHandler {

    @Override
    public void onSelectReport(ReportViewerEvent event) {
      previewContent.setText("");
      ReportTreeDTO dto = (ReportTreeDTO) event.getSource();
      ripViewer = initializer.init(dto.getRipViewerClass());
      ripViewer.setHeading(dto.getName());
      inputParameterContainer.setWidget(ripViewer);
      toolBar.enable();
      toolBar.createPagingToolBar();
      if (dto.getChartSize() != null && dto.getChartSize() > 0) {
        toolBar.showExportPng(true);
        toolBar.showExportJpeg(true);
      } else {
        toolBar.showExportPng(false);
        toolBar.showExportJpeg(false);
      }
      container.forceLayout();
    }

    @Override
    public void onExportCsv(ReportViewerEvent event) {
      ExportDTO dto = new ExportDTO();
      dto.setReportId(tree.getSelectionModel().getSelectedItem().getId());
      Map<String, Object> map = ripViewer.getInputParameter();
      map.put("explorerHeader", true);
      dto.setInputParameters(map);
      dto.setReportFormat(ReportFormat.CSV);
      presenter.exportCsv(dto);
    }

    @Override
    public void onExportPdf(ReportViewerEvent event) {
      ExportDTO dto = new ExportDTO();
      dto.setReportId(tree.getSelectionModel().getSelectedItem().getId());
      dto.setInputParameters(ripViewer.getInputParameter());
      dto.setReportFormat(ReportFormat.PDF);
      presenter.exportFile(dto);
    }

    @Override
    public void onExportXls(ReportViewerEvent event) {
      ExportDTO dto = new ExportDTO();
      dto.setReportId(tree.getSelectionModel().getSelectedItem().getId());
      Map<String, Object> map = ripViewer.getInputParameter();
      map.put("explorerHeader", true);
      dto.setInputParameters(map);
      dto.setReportFormat(ReportFormat.XLS);
      presenter.exportFile(dto);
    }

    @Override
    public void onExportPng(ReportViewerEvent event) {
      ExportDTO dto = new ExportDTO();
      pngList.clear();
      dto.setReportId(tree.getSelectionModel().getSelectedItem().getId());
      Map<String, Object> map = ripViewer.getInputParameter();
      dto.setInputParameters(map);
      dto.setReportFormat(ReportFormat.PNG);
      Integer pages = tree.getSelectionModel().getSelectedItem().getChartSize();
      dto.setChartPage(0);
      presenter.exportPng(dto, pages, pngList);
    }

    @Override
    public void onExportJpeg(ReportViewerEvent event) {
      ExportDTO dto = new ExportDTO();
      pngList.clear();
      dto.setReportId(tree.getSelectionModel().getSelectedItem().getId());
      Map<String, Object> map = ripViewer.getInputParameter();
      dto.setInputParameters(map);
      dto.setReportFormat(ReportFormat.JPEG);
      Integer pages = tree.getSelectionModel().getSelectedItem().getChartSize();
      dto.setChartPage(0);
      presenter.exportPng(dto, pages, pngList);
    }
  }
}
