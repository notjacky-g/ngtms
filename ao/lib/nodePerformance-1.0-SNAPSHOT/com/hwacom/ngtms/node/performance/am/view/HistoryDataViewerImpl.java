/*
 * © HwaCom Systems Inc. 2013
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.node.performance.am.view;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.DateCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor.Path;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.safecss.shared.SafeStyles;
import com.google.gwt.safecss.shared.SafeStylesUtils;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Widget;
import com.hwacom.ngtms.hcce.am.view.DateTimeField;
import com.hwacom.ngtms.hcce.am.view.DateTimeField.DateTimeValidException;
import com.hwacom.ngtms.hcce.am.view.DateTimeUtil;
import com.hwacom.ngtms.hcce.shared.dto.NodeDTO;
import com.hwacom.ngtms.hcce.shared.dto.TopologyGroup;
import com.hwacom.ngtms.node.performance.am.presenter.HistoryDataPresenter;
import com.hwacom.ngtms.node.performance.shared.dto.NodePerformanceLogDTO;
import com.sencha.gxt.core.client.Style.SelectionMode;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.LabelProvider;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;
import com.sencha.gxt.data.shared.loader.LoadResultListStoreBinding;
import com.sencha.gxt.data.shared.loader.PagingLoadConfig;
import com.sencha.gxt.data.shared.loader.PagingLoadResult;
import com.sencha.gxt.data.shared.loader.PagingLoader;
import com.sencha.gxt.widget.core.client.Composite;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.form.SimpleComboBox;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.grid.GridView;
import com.sencha.gxt.widget.core.client.info.Info;
import com.sencha.gxt.widget.core.client.toolbar.PagingToolBar;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HistoryDataViewerImpl extends Composite implements HistoryDataViewer {

  private static HistoryDataViewerImplUiBinder uiBinder =
      GWT.create(HistoryDataViewerImplUiBinder.class);

  interface HistoryDataViewerImplUiBinder extends UiBinder<Widget, HistoryDataViewerImpl> {}

  private static final NodePerformanceLogProperties props =
      GWT.create(NodePerformanceLogProperties.class);

  private static final Messages messages = GWT.create(Messages.class);

  private final PagingLoader<PagingLoadConfig, PagingLoadResult<NodePerformanceLogDTO>> loader;

  private Date startDate = null;

  private Date endDate = null;

  private HistoryDataPresenter presenter;

  @UiField(provided = true)
  ColumnModel<NodePerformanceLogDTO> cm;

  @UiField(provided = true)
  ListStore<NodePerformanceLogDTO> store;

  @UiField GridView<NodePerformanceLogDTO> view;

  @UiField Grid<NodePerformanceLogDTO> grid;

  @UiField DateTimeField startTime;

  @UiField DateTimeField endTime;

  @UiField PagingToolBar pageToolBar;

  @UiField(provided = true)
  SimpleComboBox<SimpleEntry<TopologyGroup, String>> groupNameCombo =
      new SimpleComboBox<SimpleEntry<TopologyGroup, String>>(
          new LabelProvider<SimpleEntry<TopologyGroup, String>>() {
            @Override
            public String getLabel(SimpleEntry<TopologyGroup, String> item) {
              return item.getValue();
            }
          });

  @UiField(provided = true)
  SimpleComboBox<NodeDTO> nodeNameCombo =
      new SimpleComboBox<>(
          new LabelProvider<NodeDTO>() {
            @Override
            public String getLabel(NodeDTO item) {
              return item.getName();
            }
          });

  public HistoryDataViewerImpl() {
    store = new ListStore<NodePerformanceLogDTO>(props.key());
    initColumnModel();
    presenter = new HistoryDataPresenter(this);
    loader =
        new PagingLoader<PagingLoadConfig, PagingLoadResult<NodePerformanceLogDTO>>(
            presenter.getProxy());
    loader.addLoadHandler(
        new LoadResultListStoreBinding<
            PagingLoadConfig, NodePerformanceLogDTO, PagingLoadResult<NodePerformanceLogDTO>>(
            store));

    initWidget(uiBinder.createAndBindUi(this));
    grid.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    grid.setLoader(loader);
    pageToolBar.bind(loader);
    // 初始化時間
    Date currentDate = new Date();
    startTime.setValue(DateTimeUtil.dayStart(currentDate));
    endTime.setValue(DateTimeUtil.dayEnd(currentDate));
    groupNameCombo.add(new SimpleEntry<>(TopologyGroup.HC_PRIMARY_GROUP, "主系統"));
    groupNameCombo.add(new SimpleEntry<>(TopologyGroup.HC_BACKUP_GROUP, "備援系統"));
    groupNameCombo.setValue(groupNameCombo.getStore().get(0));
    SelectionEvent.<SimpleEntry<TopologyGroup, String>>fire(
        groupNameCombo, groupNameCombo.getValue());
    Info.display("init :", groupNameCombo.getValue().getKey().toString());
  }

  private void initColumnModel() {
    SafeStyles fieldPaddingStyle = SafeStylesUtils.fromTrustedString("padding: 2px 3px; 2px 3px;");

    List<ColumnConfig<NodePerformanceLogDTO, ?>> columnConfigs =
        new ArrayList<ColumnConfig<NodePerformanceLogDTO, ?>>();

    ColumnConfig<NodePerformanceLogDTO, Date> recordTime =
        new ColumnConfig<NodePerformanceLogDTO, Date>(props.recordTime());
    recordTime.setWidth(170);
    recordTime.setHeader(messages.nodePerformanceLog_recordTime());
    recordTime.setCell(new DateCell(DateTimeFormat.getFormat("yyyy/MM/dd HH:mm:ss")));
    columnConfigs.add(recordTime);

    ColumnConfig<NodePerformanceLogDTO, String> groupNameConfig =
        new ColumnConfig<NodePerformanceLogDTO, String>(props.groupName());
    groupNameConfig.setWidth(150);
    groupNameConfig.setHeader(messages.nodePerformanceLog_groupName());
    columnConfigs.add(groupNameConfig);

    ColumnConfig<NodePerformanceLogDTO, String> nodeNameConfig =
        new ColumnConfig<NodePerformanceLogDTO, String>(props.nodeName());
    nodeNameConfig.setWidth(95);
    nodeNameConfig.setHeader(messages.nodePerformanceLog_nodeName());
    columnConfigs.add(nodeNameConfig);

    ColumnConfig<NodePerformanceLogDTO, Double> cpuLoadAverage =
        new ColumnConfig<NodePerformanceLogDTO, Double>(props.cpuLoadAverageShow());
    cpuLoadAverage.setWidth(100);
    cpuLoadAverage.setHeader(messages.nodePerformanceLog_cpuLoadAverage());
    cpuLoadAverage.setColumnTextStyle(fieldPaddingStyle);
    // final ProgressBarCell cpuLoadAverageProgress = new ProgressBarCell() {
    // @Override
    // public boolean handlesSelection() {
    // return true;
    // }
    // };
    // cpuLoadAverageProgress.setProgressText("{0}%");
    // cpuLoadAverageProgress.setWidth(95);
    // cpuLoadAverage.setCell(cpuLoadAverageProgress);
    cpuLoadAverage.setCell(
        new AbstractCell<Double>() {
          @Override
          public void render(
              com.google.gwt.cell.client.Cell.Context context, Double value, SafeHtmlBuilder sb) {
            String msg = "";
            msg = NumberFormat.getFormat("0.00").format(value * 100);
            sb.appendHtmlConstant("<span>" + msg + "</span>");
          }
        });
    columnConfigs.add(cpuLoadAverage);

    ColumnConfig<NodePerformanceLogDTO, String> memUsedPercent =
        new ColumnConfig<>(NodePerformanceLogProperties.memUsedPercent);
    memUsedPercent.setWidth(100);
    memUsedPercent.setHeader(messages.nodePerformanceLog_memUsedPercent());
    memUsedPercent.setColumnTextStyle(fieldPaddingStyle);
    // final ProgressBarCell memUsedPercentProgress = new ProgressBarCell() {
    // @Override
    // public boolean handlesSelection() {
    // return true;
    // }
    // };
    // memUsedPercentProgress.setProgressText("{0}%");
    // memUsedPercentProgress.setWidth(95);
    // memUsedPercent.setCell(memUsedPercentProgress);
    columnConfigs.add(memUsedPercent);

    ColumnConfig<NodePerformanceLogDTO, String> diskUsageJson =
        new ColumnConfig<>(props.diskUsageJson());
    diskUsageJson.setWidth(600);
    diskUsageJson.setHeader(messages.nodePerformanceLog_diskUsage());
    columnConfigs.add(diskUsageJson);

    cm = new ColumnModel<NodePerformanceLogDTO>(columnConfigs);
  }

  @UiHandler("groupNameCombo")
  public void onSelectGroupNameCombo(SelectionEvent<SimpleEntry<TopologyGroup, String>> event) {
    nodeNameCombo.getStore().clear();
    presenter.fetchNodeData(event.getSelectedItem().getKey());
  }

  @UiHandler("queryButton")
  void selectQuery(SelectEvent se) {
    try {
      startDate = startTime.getValue();
    } catch (DateTimeValidException e) {
      Info.display(e.getTitle(), e.getErrorString());
    }

    try {
      endDate = endTime.getValue();
    } catch (DateTimeValidException e) {
      Info.display(e.getTitle(), e.getErrorString());
    }

    if (startDate == null || endDate == null) {
      return;
    }

    if (startDate.getTime() > endDate.getTime()) {
      Info.display("訊息", "開始時間必需小於結束時間");
      return;
    }

    loader.load();
  }

  interface NodePerformanceLogProperties extends PropertyAccess<NodePerformanceLogDTO> {
    @Path("id")
    ModelKeyProvider<NodePerformanceLogDTO> key();

    @Path("nodeName")
    LabelProvider<NodePerformanceLogDTO> name();

    ValueProvider<NodePerformanceLogDTO, String> groupName();

    ValueProvider<NodePerformanceLogDTO, String> nodeName();

    ValueProvider<NodePerformanceLogDTO, Double> cpuLoadAverage();

    ValueProvider<NodePerformanceLogDTO, Double> cpuLoadAverageShow();

    ValueProvider<NodePerformanceLogDTO, String> memUsedPercent =
        new ValueProvider<NodePerformanceLogDTO, String>() {
          @Override
          public String getValue(NodePerformanceLogDTO object) {
            return NumberFormat.getFormat("0.00").format(object.getMemUsedPercent());
          }

          @Override
          public void setValue(NodePerformanceLogDTO object, String value) {}

          @Override
          public String getPath() {
            return "memUsedPercent";
          }
        };

    ValueProvider<NodePerformanceLogDTO, String> diskUsageJson();

    ValueProvider<NodePerformanceLogDTO, Date> recordTime();
  }

  @Override
  protected void onUnload() {
    super.onUnload();
  }

  @Override
  public void setPresenter(HistoryDataPresenter presenter) {
    this.presenter = presenter;
  }

  @Override
  public Date getStartDate() {
    return new Date(this.startDate.getTime());
  }

  @Override
  public Date getEndDate() {
    return new Date(this.endDate.getTime());
  }

  @Override
  public String getGroupName() {
    return groupNameCombo.getValue().getKey().toString();
  }

  @Override
  public String getNodeName() {
    return nodeNameCombo.getValue().getName();
  }

  @Override
  public void updateNodeNames(List<NodeDTO> result) {
    if (!result.isEmpty()) {
      nodeNameCombo.getStore().addAll(result);
      nodeNameCombo.setValue(result.get(0));
    }
  }
}
