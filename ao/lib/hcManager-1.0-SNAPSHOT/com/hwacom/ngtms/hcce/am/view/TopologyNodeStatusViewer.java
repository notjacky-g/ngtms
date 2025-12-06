/*
 * © HwaCom Systems Inc. 2013
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.hcce.am.view;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.DateCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor.Path;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.safecss.shared.SafeStyles;
import com.google.gwt.safecss.shared.SafeStylesUtils;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Widget;
import com.hwacom.ngtms.hcce.am.event.MaskRootEvent;
import com.hwacom.ngtms.hcce.am.event.MaskRootEvent.MaskRootEventHandler;
import com.hwacom.ngtms.hcce.am.event.SystemEnvEvent;
import com.hwacom.ngtms.hcce.am.event.SystemEnvEvent.SystemEnvEventHandler;
import com.hwacom.ngtms.hcce.am.factory.ClientFactory;
import com.hwacom.ngtms.hcce.am.presenter.TopologyNodeStatusPresenter;
import com.hwacom.ngtms.hcce.shared.dto.SystemEnvDTO;
import com.hwacom.ngtms.hcce.shared.dto.TopologyGroup;
import com.hwacom.ngtms.hcce.shared.dto.TopologyNodeStatusDTO;
import com.sencha.gxt.cell.core.client.TextButtonCell;
import com.sencha.gxt.core.client.Style.SelectionMode;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.core.client.resources.CommonStyles;
import com.sencha.gxt.data.shared.LabelProvider;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;
import com.sencha.gxt.widget.core.client.Composite;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.grid.GridView;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TopologyNodeStatusViewer extends Composite {

  private static TopologyNodeStatusViewerUiBinder uiBinder =
      GWT.create(TopologyNodeStatusViewerUiBinder.class);

  interface TopologyNodeStatusViewerUiBinder extends UiBinder<Widget, TopologyNodeStatusViewer> {}

  private static final TopologyNodeStatusProperties props =
      GWT.create(TopologyNodeStatusProperties.class);
  private static final Messages messages = GWT.create(Messages.class);
  public static final int FETCH_TOPOLOGY_NODE_STATUS_TIME = 10000;
  private TopologyNodeStatusPresenter presenter;
  private Timer fetchTopologyNodeStatusTimer;
  private Timer operationTimer;
  private TopologyGroup topologyGroup;
  private final ClientFactory clientFactory = GWT.create(ClientFactory.class);
  private final HandlerRegistration defaulHcceEnvEventHandlerRegistration;
  private final HandlerRegistration eventHandlerRegistration;

  @UiField(provided = true)
  ColumnModel<TopologyNodeStatusDTO> cm;

  @UiField(provided = true)
  ListStore<TopologyNodeStatusDTO> store;

  @UiField GridView<TopologyNodeStatusDTO> view;
  @UiField Grid<TopologyNodeStatusDTO> grid;

  public TopologyNodeStatusViewer() {
    store = new ListStore<TopologyNodeStatusDTO>(props.key());
    initColumnModel();
    initWidget(uiBinder.createAndBindUi(this));
    grid.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    fetchTopologyNodeStatusTimer =
        new Timer() {
          public void run() {
            presenter.init(topologyGroup);
          }
        };
    presenter = new TopologyNodeStatusPresenter(this);
    defaulHcceEnvEventHandlerRegistration =
        clientFactory
            .getEventBus()
            .addHandler(SystemEnvEvent.TYPE, new DefaulHcceEnvEventHandler());
    eventHandlerRegistration =
        clientFactory
            .getEventBus()
            .addHandler(
                MaskRootEvent.TYPE,
                new MaskRootEventHandler() {

                  @Override
                  public void onMask(MaskRootEvent event) {
                    GWT.log("FmViewerImpl Get the MaskRootEvent TO_MASK action");
                    if (fetchTopologyNodeStatusTimer != null) fetchTopologyNodeStatusTimer.cancel();
                  }
                });
  }

  private void initColumnModel() {
    List<ColumnConfig<TopologyNodeStatusDTO, ?>> columnConfigs =
        new ArrayList<ColumnConfig<TopologyNodeStatusDTO, ?>>();

    SafeStyles btnPaddingStyle = SafeStylesUtils.fromTrustedString("padding: 1px 3px 0;");

    ColumnConfig<TopologyNodeStatusDTO, String> operationColumn =
        new ColumnConfig<TopologyNodeStatusDTO, String>(
            props.operation(), 70, messages.topologyNodeStatus_operation_node());
    operationColumn.setColumnTextClassName(CommonStyles.get().inlineBlock());
    operationColumn.setColumnTextStyle(btnPaddingStyle);
    TextButtonCell button = new TextButtonCell();
    button.addSelectHandler(
        new SelectHandler() {
          @Override
          public void onSelect(SelectEvent event) {
            int row = event.getContext().getIndex();
            TopologyNodeStatusDTO p = store.get(row);
            // Info.display("Event", "The " + p.getNodeName()+
            // " was clicked.");
            if (p.isCoordinator()) {
              new MessageTimerMask(
                  TopologyNodeStatusViewer.this, messages.message_coordinatorCanNotStop(), 3000);
              return;
            }
            if (!p.isRegistered()
                && (p.getLastRegIpAddress() == null || p.getLastRegIpAddress().length() == 0)) {
              new MessageTimerMask(
                  TopologyNodeStatusViewer.this,
                  messages.topologyNodeStatus_unRegisteredMsg(),
                  3000);
              return;
            }
            if (p.isRegistered()) {
              presenter.stopHcNode(topologyGroup, p.getNodeName());
            } else {
              presenter.startHcNode(topologyGroup, p.getNodeName());
            }
            // new MessageTimerMask(TopologyNodeStatusViewerImpl.this,
            // messages.message_waiting(), 15000);
            TopologyNodeStatusViewer.this.mask(messages.message_waiting());
            operationTimer =
                new Timer() {
                  public void run() {
                    TopologyNodeStatusViewer.this.unmask();
                  }
                };
            operationTimer.schedule(13000);
          }
        });
    operationColumn.setCell(button);
    columnConfigs.add(operationColumn);

    ColumnConfig<TopologyNodeStatusDTO, String> operationMapColumn =
        new ColumnConfig<TopologyNodeStatusDTO, String>(
            props.mapOperation(), 70, messages.topologyNodeStatus_operation_map());
    operationMapColumn.setColumnTextClassName(CommonStyles.get().inlineBlock());
    operationMapColumn.setColumnTextStyle(btnPaddingStyle);
    TextButtonCell mapButton = new TextButtonCell();
    mapButton.addSelectHandler(
        new SelectHandler() {
          @Override
          public void onSelect(SelectEvent event) {
            int row = event.getContext().getIndex();
            TopologyNodeStatusDTO p = store.get(row);
            if (p.isCoordinator()) {
              new MessageTimerMask(
                  TopologyNodeStatusViewer.this, messages.message_coordinatorCanNotRemove(), 3000);
              return;
            }
            presenter.removeHcNode(topologyGroup, p.getNodeName());
          }
        });
    operationMapColumn.setCell(mapButton);
    columnConfigs.add(operationMapColumn);

    ColumnConfig<TopologyNodeStatusDTO, String> nodeNameConfig =
        new ColumnConfig<TopologyNodeStatusDTO, String>(props.nodeName());
    nodeNameConfig.setWidth(100);
    nodeNameConfig.setHeader(messages.topologyNodeStatus_nodeName());
    nodeNameConfig.setHideable(false);
    nodeNameConfig.setMenuDisabled(true);
    nodeNameConfig.setSortable(false);
    columnConfigs.add(nodeNameConfig);

    ColumnConfig<TopologyNodeStatusDTO, Boolean> registeredConfig =
        new ColumnConfig<TopologyNodeStatusDTO, Boolean>(props.registered());
    registeredConfig.setWidth(60);
    registeredConfig.setHeader(messages.topologyNodeStatus_registered());
    registeredConfig.setHideable(false);
    registeredConfig.setMenuDisabled(true);
    registeredConfig.setSortable(false);
    registeredConfig.setCell(
        new AbstractCell<Boolean>() {
          @Override
          public void render(
              com.google.gwt.cell.client.Cell.Context context, Boolean value, SafeHtmlBuilder sb) {
            String style = "style='color: " + (value ? "green" : "red") + "'";
            sb.appendHtmlConstant(
                "<span "
                    + style
                    + ">"
                    + (value ? messages.message_yes() : messages.message_no())
                    + "</span>");
          }
        });
    columnConfigs.add(registeredConfig);

    ColumnConfig<TopologyNodeStatusDTO, Boolean> coordinatorConfig =
        new ColumnConfig<TopologyNodeStatusDTO, Boolean>(props.coordinator());
    coordinatorConfig.setWidth(60);
    coordinatorConfig.setHeader(messages.topologyNodeStatus_coordinator());
    coordinatorConfig.setHideable(false);
    coordinatorConfig.setMenuDisabled(true);
    coordinatorConfig.setSortable(false);
    coordinatorConfig.setCell(
        new AbstractCell<Boolean>() {
          @Override
          public void render(
              com.google.gwt.cell.client.Cell.Context context, Boolean value, SafeHtmlBuilder sb) {
            String style = "style='color: " + (value ? "green" : "red") + "'";
            sb.appendHtmlConstant(
                "<span " + style + ">" + (value ? messages.message_yes() : "") + "</span>");
          }
        });
    columnConfigs.add(coordinatorConfig);

    ColumnConfig<TopologyNodeStatusDTO, String> lastRegIpAddressConfig =
        new ColumnConfig<TopologyNodeStatusDTO, String>(props.lastRegIpAddress());
    lastRegIpAddressConfig.setWidth(120);
    lastRegIpAddressConfig.setHeader(messages.topologyNodeStatus_lastRegIpAddress());
    lastRegIpAddressConfig.setHideable(false);
    lastRegIpAddressConfig.setMenuDisabled(true);
    lastRegIpAddressConfig.setSortable(false);
    columnConfigs.add(lastRegIpAddressConfig);

    ColumnConfig<TopologyNodeStatusDTO, String> serviceTimeConfig =
        new ColumnConfig<TopologyNodeStatusDTO, String>(props.serviceTime());
    serviceTimeConfig.setWidth(100);
    serviceTimeConfig.setHeader(messages.topologyNodeStatus_serviceTime());
    serviceTimeConfig.setHideable(false);
    serviceTimeConfig.setMenuDisabled(true);
    serviceTimeConfig.setSortable(false);
    columnConfigs.add(serviceTimeConfig);

    ColumnConfig<TopologyNodeStatusDTO, Date> registerTimeConfig =
        new ColumnConfig<TopologyNodeStatusDTO, Date>(props.registerTime());
    registerTimeConfig.setWidth(170);
    registerTimeConfig.setHeader(messages.topologyNodeStatus_registerTime());
    registerTimeConfig.setHideable(false);
    registerTimeConfig.setMenuDisabled(true);
    registerTimeConfig.setSortable(false);
    registerTimeConfig.setCell(new DateCell(DateTimeFormat.getFormat("yyyy/MM/dd HH:mm:ss")));
    columnConfigs.add(registerTimeConfig);

    cm = new ColumnModel<TopologyNodeStatusDTO>(columnConfigs);
  }

  interface TopologyNodeStatusProperties extends PropertyAccess<TopologyNodeStatusDTO> {
    @Path("key")
    ModelKeyProvider<TopologyNodeStatusDTO> key();

    @Path("nodeName")
    LabelProvider<TopologyNodeStatusDTO> nameLabel();

    ValueProvider<TopologyNodeStatusDTO, String> groupName();

    ValueProvider<TopologyNodeStatusDTO, String> nodeName();

    ValueProvider<TopologyNodeStatusDTO, Boolean> coordinator();

    ValueProvider<TopologyNodeStatusDTO, Boolean> registered();

    ValueProvider<TopologyNodeStatusDTO, Date> registerTime();

    ValueProvider<TopologyNodeStatusDTO, Date> unRegisterTime();

    ValueProvider<TopologyNodeStatusDTO, String> serviceTime();

    ValueProvider<TopologyNodeStatusDTO, String> lastRegIpAddress();

    ValueProvider<TopologyNodeStatusDTO, String> operation();

    ValueProvider<TopologyNodeStatusDTO, String> mapOperation();
  }

  @Override
  protected void onUnload() {
    if (fetchTopologyNodeStatusTimer != null) {
      fetchTopologyNodeStatusTimer.cancel();
      fetchTopologyNodeStatusTimer = null;
    }
    defaulHcceEnvEventHandlerRegistration.removeHandler();
    if (eventHandlerRegistration != null) eventHandlerRegistration.removeHandler();
    super.onUnload();
  }

  public void setPresenter(TopologyNodeStatusPresenter presenter) {
    this.presenter = presenter;
  }

  public void init(List<TopologyNodeStatusDTO> topologyConfigs) {
    store.clear();
    for (TopologyNodeStatusDTO each : topologyConfigs) {
      if (each.getOperation() != null
          && (each.getOperation().equals("Stop") || each.getOperation().equals("Start"))) {
        each.setMapOperation("remove");
      } else {
        each.setMapOperation("");
      }
      store.add(each);
    }
  }

  public void setTopologyGroup(TopologyGroup topologyGroup) {
    this.topologyGroup = topologyGroup;
  }

  public TopologyGroup getTopologyGroup() {
    return this.topologyGroup;
  }

  class DefaulHcceEnvEventHandler implements SystemEnvEventHandler {

    @Override
    public void onUpdateEnvData(SystemEnvEvent event) {}

    @Override
    public void onInitEnvData(SystemEnvEvent event) {
      GWT.log(
          "TopologyNodeStatusViewer=> this view topologyGroup : "
              + topologyGroup
              + " ; event.getTopologyGroup :"
              + event.getTopologyGroup());
      SystemEnvDTO systemEnvVo = (SystemEnvDTO) event.getSource();
      GWT.log("TopologyNodeStatusViewer=> systemEnvVo : " + systemEnvVo);
      if (systemEnvVo != null) {
        if (event.getTopologyGroup() == topologyGroup) {
          GWT.log("TopologyNodeStatusViewer=> presenter.init topologyGroup : " + topologyGroup);
          presenter.init(topologyGroup);
          fetchTopologyNodeStatusTimer.scheduleRepeating(FETCH_TOPOLOGY_NODE_STATUS_TIME);
        }
      }
    }
  }
}
