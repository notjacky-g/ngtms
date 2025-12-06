/*
 * © HwaCom Systems Inc. 2015
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.cam.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor.Path;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Widget;
import com.hwacom.ngtms.c.shared.dto.AmParametersDTO;
import com.hwacom.ngtms.c.shared.dto.DeviceConfigDTO;
import com.hwacom.ngtms.c.shared.dto.DeviceGroupDTO;
import com.hwacom.ngtms.c.shared.dto.DeviceGroupDeviceConfigDTO;
import com.hwacom.ngtms.cam.client.HomeEP;
import com.hwacom.ngtms.cam.client.event.AmEventCenter;
import com.hwacom.ngtms.cam.client.event.DataFetchEvent;
import com.hwacom.ngtms.cam.client.event.DataFetchHandler;
import com.hwacom.ngtms.cam.client.event.GroupOperationEvent;
import com.hwacom.ngtms.cam.client.event.GroupOperationEvent.GroupOperationType;
import com.hwacom.ngtms.cam.client.ui.dnd.AddType;
import com.hwacom.ngtms.cam.client.ui.dnd.RemoveDeviceUtil;
import com.hwacom.ngtms.cam.util.DataCenter;
import com.hwacom.ngtms.cam.view.Messages;
import com.hwacom.ngtms.cam.vo.DeviceDataType;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.LabelProvider;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;
import com.sencha.gxt.dnd.core.client.DND.Operation;
import com.sencha.gxt.dnd.core.client.DndDropEvent;
import com.sencha.gxt.dnd.core.client.DndDropEvent.DndDropHandler;
import com.sencha.gxt.dnd.core.client.DropTarget;
import com.sencha.gxt.dnd.core.client.GridDragSource;
import com.sencha.gxt.widget.core.client.Dialog.PredefinedButton;
import com.sencha.gxt.widget.core.client.box.AlertMessageBox;
import com.sencha.gxt.widget.core.client.box.PromptMessageBox;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.event.DialogHideEvent;
import com.sencha.gxt.widget.core.client.event.DialogHideEvent.DialogHideHandler;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.form.ComboBox;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.info.Info;
import java.util.ArrayList;
import java.util.HashSet;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;

/**
 * 「操作群組設定」的共用 component。
 *
 * <p>雖然操作群組設定的 DnD 行為一樣是以 {@link RoadTreeViewer} 作為來源 / 目的地， 但是跟其他頁籤不同的是：它的 drop（新增 device）跟
 * drag（移除 device）行為都不能與其他頁籤連動。 也就是說，操作群組設定中新增 / 移除 deviceA，其他頁籤（例如設備組態查詢）不會更著新增 / 移除 deviceA。
 *
 * @author monty.pan
 */
public class CustomGroupViewer extends AmTab {
  private static CustomGroupViewUiBinder uiBinder = GWT.create(CustomGroupViewUiBinder.class);

  interface CustomGroupViewUiBinder extends UiBinder<Widget, CustomGroupViewer> {}

  public static final String DND_GROUP = "CustomGroupRemoveDevice";
  private static final GroupProperties groupProps = GWT.create(GroupProperties.class);
  private static final DeviceProperties deviceProps = GWT.create(DeviceProperties.class);
  private final Messages messages = GWT.create(Messages.class);

  @UiField(provided = true)
  ComboBox<DeviceGroupDTO> groupList;
  /** 不能用 {@link RemoveDeviceUtil#GROUP} DnD group，所以不能用 DeviceNameView */
  @UiField(provided = true)
  Grid<DeviceConfigDTO> deviceGrid;

  @UiField TextButton updateBtn;
  @UiField TextButton deleteBtn;

  public CustomGroupViewer() {
    this.setTabTitle(messages.groupView_name());

    buildDeviceGrid();

    groupList = new ComboBox<>(new ListStore<>(groupProps.groupId()), groupProps.groupName());

    initWidget(uiBinder.createAndBindUi(this));

    buildDropTarget();

    //要求 DataCenter.fetchDeviceGroup() 的行為是在 RoadTreeView 當中作
    AmEventCenter.addDataFetchHandler(
        new DataFetchHandler() {
          @Override
          public void onDataFetchEvent(DataFetchEvent event) {
            if (event.getType() == DeviceDataType.DeviceGroup) {
              DeviceGroupDTO nowValue = groupList.getValue(); //保留現有選擇
              groupList.getStore().clear();
              for (DeviceGroupDTO dg : DataCenter.getDeviceGroupList()) {
                groupList.getStore().add(dg);
              }

              if (nowValue == null) {
                refreshView(null);
                return;
              }

              //新的 dataset 當中有已選取的 group
              //因為新增 group 的時候，client side 的 instance 跟 server 回傳的 instance 並不一樣
              //所以這邊一致地改用 name 來判斷
              for (DeviceGroupDTO dg : DataCenter.getDeviceGroupList()) {
                if (dg.getGroupName().equals(nowValue.getGroupName())) {
                  groupList.setValue(dg); //蓋過去
                  refreshView(dg);
                  return;
                }
              }

              //會跑到這裡來是因為 nowValue 被其他 client 給移除掉了
              //所以除了 refreshView(null) 之外，groupList 也要清空
              groupList.setValue(null);
              refreshView(null);
            }
          }
        });
  }

  @UiHandler("groupList")
  void selectGroup(SelectionEvent<DeviceGroupDTO> se) {
    groupList.setValue(se.getSelectedItem());
    deviceGrid.getStore().clear();
    refreshView(se.getSelectedItem());
  }

  @UiHandler("updateBtn")
  void selectUpdate(SelectEvent se) {
    mask();

    DeviceGroupDTO dg = groupList.getValue();
    //懶得作 diff，直接清空重建 DeviceGroupDeviceConfig
    HashSet<DeviceGroupDeviceConfigDTO> dgdcSet = new HashSet<>();
    for (DeviceConfigDTO device : deviceGrid.getStore().getAll()) {
      DeviceGroupDeviceConfigDTO newDgdc = new DeviceGroupDeviceConfigDTO();
      newDgdc.setGroupId(dg.getGroupId());
      newDgdc.setDeviceName(device.getDeviceName());
      dgdcSet.add(newDgdc);
    }
    dg.setDevices(dgdcSet);
    AmParametersDTO params = new AmParametersDTO();
    params.setDeviceGroupDTO(dg);
    HomeEP.camService.updateDeviceGroup(
        params,
        new MethodCallback<Boolean>() {
          @Override
          public void onSuccess(Method method, Boolean result) {
            unmask();
            if (result) {
              Info.display("", messages.message_updateFinish());
            }
            AmEventCenter.fireEvent(
                new GroupOperationEvent(GroupOperationType.UPDATE, groupList.getValue()));
          }

          @Override
          public void onFailure(Method method, Throwable caught) {
            GWT.log("CustomGroupView.updateDeviceGroup failed.", caught);
          }
        });
  }

  @UiHandler("newBtn")
  void selectNew(SelectEvent se) {
    final PromptMessageBox box =
        new PromptMessageBox(messages.message_newGroup(), messages.message_groupName());
    box.addDialogHideHandler(
        new DialogHideHandler() {
          @Override
          public void onDialogHide(DialogHideEvent event) {
            if (event.getHideButton() == PredefinedButton.OK) {
              if (box.getValue() != null && box.getValue().trim().length() != 0) {
                newGroup(box.getValue());
              } else {
                new AlertMessageBox(messages.message_error(), messages.message_enterGroupName())
                    .show();
              }
            }
          }
        });
    box.show();
  }

  @UiHandler("deleteBtn")
  void selectDelete(SelectEvent se) {
    mask(messages.message_updating());
    HomeEP.camService.removeDeviceGroup(
        groupList.getValue().getGroupId(),
        new MethodCallback<Boolean>() {
          @Override
          public void onSuccess(Method method, Boolean result) {
            unmask();
            if (result) {
              AmEventCenter.fireEvent(
                  new GroupOperationEvent(GroupOperationType.DELETE, groupList.getValue()));
              Info.display("", messages.message_updateSuccessed());
              groupList.setValue(null);
              refreshView(null);
            } else {
              Info.display("", messages.message_updateFailed());
            }
          }

          @Override
          public void onFailure(Method method, Throwable caught) {
            GWT.log("CustomGroupView.removeDeviceGroup failed.", caught);
          }
        });
  }

  /**
   * 以指定的 {@link DeviceGroupDTO} 更新畫面
   *
   * @param dg 如果是 null 就只會把 grid 清空
   */
  //SelectionEvent 觸發時 groupList.getValue() 還是舊的值，所以只能靠傳入 DeviceGroup 來解決
  private void refreshView(DeviceGroupDTO dg) {
    deviceGrid.getStore().clear();

    if (dg == null) {
      deleteBtn.setEnabled(false);
      return;
    }

    deleteBtn.setEnabled(true);
    updateBtn.setEnabled(false);
    ArrayList<DeviceConfigDTO> deviceList = new ArrayList<>();
    for (DeviceGroupDeviceConfigDTO dgdc : dg.getDevices()) {
      deviceList.add(DataCenter.getDeviceConfig(dgdc.getDeviceName()));
    }
    deviceGrid.getStore().addAll(deviceList);
  }

  private void newGroup(String name) {
    final DeviceGroupDTO newOne = new DeviceGroupDTO();
    newOne.setGroupName(name);
    //這裡不考慮 DataCenter 根本沒指定 deviceType
    newOne.setGroupType(DataCenter.getDeviceType().get(0)); //FIXME 多個 DeviceType 的處理

    //FM 會重設 DeviceGroupDeviceConfig 的 groupID
    for (DeviceConfigDTO device : deviceGrid.getStore().getAll()) {
      DeviceGroupDeviceConfigDTO dgdc = new DeviceGroupDeviceConfigDTO();
      dgdc.setDeviceName(device.getDeviceName());
      newOne.getDevices().add(dgdc);
    }
    AmParametersDTO params = new AmParametersDTO();
    params.setDeviceGroupDTO(newOne);
    HomeEP.camService.createDeviceGroup(
        params,
        new MethodCallback<Boolean>() {
          @Override
          public void onSuccess(Method method, Boolean result) {
            groupList.setValue(newOne);
            // 這裡 newOne 的 groupId 依然是 null
            // 這邊設定是為了在 onDataFetch() 的時候有值可以判斷 / 重新 assign，讓使用者不用重新指定剛剛新增的
            // group
            AmEventCenter.fireEvent(new GroupOperationEvent(GroupOperationType.UPDATE, newOne));
          }

          @Override
          public void onFailure(Method method, Throwable caught) {
            GWT.log("CustomGroupView.createDeviceGroup failed.", caught);
          }
        });
  }

  /** 建立已選取設備的 grid、以及移除 device 的邏輯 */
  private void buildDeviceGrid() {
    ArrayList<ColumnConfig<DeviceConfigDTO, ?>> columnConfigList = new ArrayList<>();
    columnConfigList.add(
        new ColumnConfig<>(deviceProps.displayName(), 100, messages.grid_header_name()));

    deviceGrid = new Grid<>(new ListStore<>(deviceProps.id()), new ColumnModel<>(columnConfigList));

    GridDragSource<DeviceConfigDTO> dragSource = new GridDragSource<>(deviceGrid);
    dragSource.setGroup(DND_GROUP);
    dragSource.addDropHandler(
        new DndDropHandler() {
          @Override
          public void onDrop(DndDropEvent event) {
            updateBtn.setEnabled(true);
          }
        });
  }

  /** 主要內容是新增 device 的處理 */
  private void buildDropTarget() {
    DropTarget dropTarget = new DropTarget(this);
    dropTarget.setOperation(Operation.COPY);
    dropTarget.setGroup(AddType.DeviceConfig.toString());
    dropTarget.addDropHandler(
        new DndDropHandler() {
          @SuppressWarnings("unchecked")
          @Override
          public void onDrop(DndDropEvent event) {
            ArrayList<DeviceConfigDTO> newList = new ArrayList<>();
            for (DeviceConfigDTO device : (ArrayList<DeviceConfigDTO>) event.getData()) {
              //排除重複
              if (deviceGrid.getStore().findModel(device) == null) {
                newList.add(device);
              }
            }
            deviceGrid.getStore().addAll(newList);
            if (newList.size() != 0 && groupList.getValue() != null) {
              updateBtn.setEnabled(true);
            }
          }
        });
  }

  interface GroupProperties extends PropertyAccess<DeviceGroupDTO> {
    ModelKeyProvider<DeviceGroupDTO> groupId();

    LabelProvider<DeviceGroupDTO> groupName();
  }

  interface DeviceProperties extends PropertyAccess<DeviceConfigDTO> {
    @Path("deviceName")
    ModelKeyProvider<DeviceConfigDTO> id();

    ValueProvider<DeviceConfigDTO, String> deviceName();

    ValueProvider<DeviceConfigDTO, String> displayName();
  }
}
