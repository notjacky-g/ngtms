/*
 * © HwaCom Systems Inc. 2015
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.cam.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsonUtils;
import com.google.gwt.editor.client.Editor.Path;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Widget;
import com.google.web.bindery.autobean.shared.AutoBean;
import com.google.web.bindery.autobean.shared.AutoBeanCodex;
import com.google.web.bindery.autobean.shared.AutoBeanFactory;
import com.hwacom.ngtms.c.shared.SubSystem;
import com.hwacom.ngtms.c.shared.dto.OperationLogDTO;
import com.hwacom.ngtms.cam.client.event.AmEventCenter;
import com.hwacom.ngtms.cam.client.event.TitleViewMaskEvent;
import com.hwacom.ngtms.cam.client.event.TitleViewMaskEvent.TitleViewMaskEventHandler;
import com.hwacom.ngtms.cam.view.Messages;
import com.hwacom.ngtms.cam.vo.WebSocketCloseReason;
import com.hwacom.ngtms.cam.websocket.OperationLogWebSocket;
import com.hwacom.ngtms.cam.websocket.OperationLogWebSocket.WebSocketHandler;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;
import com.sencha.gxt.data.shared.SortDir;
import com.sencha.gxt.data.shared.Store.StoreSortInfo;
import com.sencha.gxt.widget.core.client.Composite;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.grid.GridView;
import java.util.ArrayList;
import java.util.List;

public class OperationLogViewer extends Composite {

  private static OperationLogViewUiBinder uiBinder = GWT.create(OperationLogViewUiBinder.class);
  private static final Messages messages = GWT.create(Messages.class);
  private static final OperationLogProperties props = GWT.create(OperationLogProperties.class);
  private OperationLogWebSocket webSocket;
  private SubSystem subSystem;
  private Timer reconnectTimer;

  interface OperationLogViewUiBinder extends UiBinder<Widget, OperationLogViewer> {}

  OpLogAutoBeanFactory opLogAutoBeanFactory = GWT.create(OpLogAutoBeanFactory.class);

  interface OpLogAutoBeanFactory extends AutoBeanFactory {
    AutoBean<OperationLogDTOIF> createBean();
  }

  @UiField(provided = true)
  ColumnModel<OperationLogDTO> columnModel;

  @UiField(provided = true)
  ListStore<OperationLogDTO> listStore;

  @UiField GridView<OperationLogDTO> gridView;
  @UiField Grid<OperationLogDTO> grid;

  private boolean stopReConnectWebSocket = false;
  private boolean toShowGwtLog = true;

  public OperationLogViewer() {
    columnModel = initColumModel();
    listStore = initListStore();
    initWidget(uiBinder.createAndBindUi(this));
    listStore.addSortInfo(
        new StoreSortInfo<OperationLogDTO>(props.operationTimeLong(), SortDir.DESC));
    webSocket = new OperationLogWebSocket(new DefaultWebSocketHandler());
    webSocket.open();
    reconnectTimer =
        new Timer() {
          @Override
          public void run() {
            if (!stopReConnectWebSocket) webSocket.open();
          }
        };
    AmEventCenter.addTitleViewMaskEventHandler(
        new TitleViewMaskEventHandler() {

          @Override
          public void onMaskFired(TitleViewMaskEvent event) {
            if (toShowGwtLog) GWT.log("OperationLogView => Get The TitleViewMaskEvent!");
            stopReConnectWebSocket = true;
          }
        });
  }

  public void setSubSystem(SubSystem subSystem) {
    GWT.log("OperationLogView setSubSystem =>" + subSystem.toString());
    this.subSystem = subSystem;
  }

  private ColumnModel<OperationLogDTO> initColumModel() {
    List<ColumnConfig<OperationLogDTO, ?>> columns =
        new ArrayList<ColumnConfig<OperationLogDTO, ?>>();

    ColumnConfig<OperationLogDTO, String> operationTime =
        new ColumnConfig<OperationLogDTO, String>(props.operationTimeShort());
    operationTime.setWidth(170);
    operationTime.setHeader(messages.OperationLog_operationTime());
    columns.add(operationTime);

    ColumnConfig<OperationLogDTO, String> description =
        new ColumnConfig<OperationLogDTO, String>(
            props.description(), 260, messages.OperationLog_description());
    description.setCellClassName("EUDC");
    columns.add(description);

    ColumnConfig<OperationLogDTO, String> operationResult =
        new ColumnConfig<OperationLogDTO, String>(
            props.operationResult(), 100, messages.OperationLog_OperationResult());
    columns.add(operationResult);

    ColumnConfig<OperationLogDTO, String> deviceName =
        new ColumnConfig<OperationLogDTO, String>(
            props.displayName(), 180, messages.OperationLog_deviceName());
    columns.add(deviceName);

    ColumnConfig<OperationLogDTO, String> remark =
        new ColumnConfig<OperationLogDTO, String>(
            props.remark(), 300, messages.OperationLog_remark());
    columns.add(remark);

    ColumnModel<OperationLogDTO> columnModel = new ColumnModel<OperationLogDTO>(columns);
    return columnModel;
  }

  private ListStore<OperationLogDTO> initListStore() {
    ListStore<OperationLogDTO> listStore = new ListStore<OperationLogDTO>(props.key());
    return listStore;
  }

  class DefaultWebSocketHandler implements WebSocketHandler {

    @Override
    public void onOpen() {
      GWT.log("OperationLogView DefaultWebSocketHandler onOpen...");
      reconnectTimer.cancel();
      webSocket.send(subSystem.toString());
    }

    @Override
    public void onMessage(String message) {
      GWT.log("OperationLogView DefaultWebSocketHandler onMessage...");
      GWT.log("OperationLogView message =>" + message);
      String jsonStr = JsonUtils.escapeJsonForEval(message);
      //GWT.log("jsonStr =>" + jsonStr);
      OperationLogDTOIF opLogIf = deserializeFromJson(jsonStr);
      GWT.log("OperationLogView opLogIf.getSubSysName()=" + opLogIf.getSubSysName());
      OperationLogDTO opLog = new OperationLogDTO();
      opLog.setCpeIp(opLogIf.getCpeIp());
      opLog.setDescription(opLogIf.getDescription());
      opLog.setDeviceName(opLogIf.getDeviceName());
      opLog.setId(opLogIf.getId());
      opLog.setOperationItem(opLogIf.getOperationItem());
      opLog.setOperationResult(opLogIf.getOperationResult());
      opLog.setOperationTime(opLogIf.getOperationTime());
      opLog.setOperationTimeLong(opLogIf.getOperationTimeLong());
      opLog.setOperationTimeShort(opLogIf.getOperationTimeShort());
      opLog.setRemark(opLogIf.getRemark());
      opLog.setSchId(opLogIf.getSchId());
      opLog.setSubSysName(opLogIf.getSubSysName());
      opLog.setUserId(opLogIf.getUserId());
      opLog.setDisplayName(opLogIf.getDisplayName());
      GWT.log("OperationLogView opLog =>" + opLog);
      if ("SUCCESS".equals(opLog.getOperationResult())) {
        opLog.setOperationResult(messages.OperationLog_OperationResult_success());
      }
      if ("FAILURE".equals(opLog.getOperationResult())
          || "FAIL".equals(opLog.getOperationResult())) {
        opLog.setOperationResult(messages.OperationLog_OperationResult_failure());
      }
      if ("CANCEL".equals(opLog.getOperationResult())) {
        opLog.setOperationResult(messages.OperationLog_OperationResult_cancel());
      }
      if ("TIMEOUT".equals(opLog.getOperationResult())) {
        opLog.setOperationResult(messages.OperationLog_OperationResult_timeout());
      }
      listStore.add(opLog);
    }

    @Override
    public void onClose(int code, String reason) {
      GWT.log("OperationLogView DefaultWebSocketHandlerHandler onClose...");
      GWT.log("code=" + code + ";reason=" + reason);
      if (WebSocketCloseReason.FINISHED.getCode() == code
          || WebSocketCloseReason.FAILURE.getCode() == code) {
        //unmask();
      } else {
        reconnectTimer.scheduleRepeating(5000);
      }
    }
  }

  public interface OperationLogProperties extends PropertyAccess<OperationLogDTO> {
    @Path("operationTime")
    ModelKeyProvider<OperationLogDTO> key();

    ValueProvider<OperationLogDTO, Long> id();

    ValueProvider<OperationLogDTO, String> userId();

    ValueProvider<OperationLogDTO, String> cpeIp();

    ValueProvider<OperationLogDTO, String> schId();

    ValueProvider<OperationLogDTO, String> subSysName();

    ValueProvider<OperationLogDTO, String> operationItem();

    ValueProvider<OperationLogDTO, String> description();

    ValueProvider<OperationLogDTO, String> deviceName();

    ValueProvider<OperationLogDTO, String> displayName();

    ValueProvider<OperationLogDTO, String> operationTime();

    ValueProvider<OperationLogDTO, String> operationTimeShort();

    ValueProvider<OperationLogDTO, Long> operationTimeLong();

    ValueProvider<OperationLogDTO, String> operationResult();

    ValueProvider<OperationLogDTO, String> remark();
  }

  @Override
  protected void onUnload() {
    super.onUnload();
  }

  public OperationLogDTOIF deserializeFromJson(String json) {
    AutoBean<OperationLogDTOIF> bean =
        AutoBeanCodex.decode(opLogAutoBeanFactory, OperationLogDTOIF.class, json);
    return bean.as();
  }

  public interface OperationLogDTOIF {
    public String getUserId();

    public Long getOperationTimeLong();

    public void setUserId(String userId);

    public String getCpeIp();

    public void setCpeIp(String cpeIp);

    public String getSchId();

    public void setSchId(String schId);

    public String getSubSysName();

    public void setSubSysName(String subSysName);

    public String getOperationItem();

    public void setOperationItem(String operationItem);

    public String getDescription();

    public void setDescription(String description);

    public String getDeviceName();

    public void setDeviceName(String deviceName);

    public String getDisplayName();

    public void setDisplayName(String displayName);

    public String getOperationTime();

    public String getOperationTimeShort();

    public void setOperationTimeShort(String operationTimeShort);

    public String getOperationResult();

    public void setOperationResult(String operationResult);

    public String getRemark();

    public void setRemark(String remark);

    public Long getId();

    public void setId(Long id);
  }
}
