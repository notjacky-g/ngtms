/*
 * © HwaCom Systems Inc. 2015
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.cam.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Widget;
import com.hwacom.ngtms.c.shared.dto.DeviceConfigDTO;
import com.hwacom.ngtms.c.shared.dto.DeviceInfoDTO;
import com.hwacom.ngtms.cam.client.event.AmEventCenter;
import com.hwacom.ngtms.cam.client.event.DeviceConfigDeselectEvent;
import com.hwacom.ngtms.cam.client.event.DeviceConfigDeselectHandler;
import com.hwacom.ngtms.cam.client.ui.dnd.RemoveDeviceUtil;
import com.hwacom.ngtms.cam.factory.DeviceInfoFactory;
import com.hwacom.ngtms.cam.util.DeviceInfoUtil;
import com.hwacom.ngtms.cam.util.DeviceInfoUtil.ColumnGroup;
import com.hwacom.ngtms.cam.view.Messages;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.core.client.util.DelayedTask;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.TreeStore;
import com.sencha.gxt.widget.core.client.event.CheckChangedEvent;
import com.sencha.gxt.widget.core.client.event.CheckChangedEvent.CheckChangedHandler;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.tree.Tree;
import com.sencha.gxt.widget.core.client.tree.Tree.CheckState;
import java.util.ArrayList;
import java.util.List;

/**
 * 提供共用的「設備組態查詢」widget。 各 device 無法共用的部份，透過 {@link DeviceInfoFactory} 實做 deferred binding 來達成。
 *
 * <p>目前假設：
 *
 * <ul>
 *   <li>grid 的 column 依序為 DeviceConfig、MfccConfig、SpecifyConfig，不會穿插出現 （{@link
 *       #setColumnConfigHidden(int, int, boolean)}）。
 *   <li>必存在欄位（即使欄位快選全部取消選擇仍然會存在）一定在最左邊、 搭配上一條假設，就一定是 DeviceConfig 的欄位。 （{@link
 *       #STATIC_COLUMN_LENGTH}）
 *
 * @param <SC> 子系統的 config DTO class，例如 {@link
 *     com.hwacom.ngtms.dgs.weather.am.shared.model.RdConfigDTO}。
 * @see DeviceInfoFactory
 * @author monty.pan
 */
public class DeviceInfoViewer<SC> extends AmTab {
  private final Messages messages = GWT.create(Messages.class);
  private static final int STATIC_COLUMN_LENGTH = 1;
  private static DeviceInfoViewUiBinder uiBinder = GWT.create(DeviceInfoViewUiBinder.class);

  interface DeviceInfoViewUiBinder extends UiBinder<Widget, DeviceInfoViewer<?>> {}

  @UiField(provided = true)
  ListStore<DeviceInfoDTO<SC>> store;

  @UiField(provided = true)
  ColumnModel<DeviceInfoDTO<SC>> cm;

  @UiField Grid<DeviceInfoDTO<SC>> grid;
  @UiField Tree<TreeData, String> tree;

  @UiField(provided = true)
  TreeStore<TreeData> treeStore;

  @UiField(provided = true)
  ValueProvider<TreeData, String> valueProvider;

  private DeviceInfoFactory<SC> factory = GWT.create(DeviceInfoFactory.class);

  public DeviceInfoViewer() {
    setTabTitle(messages.deviceInfoView_name());
    store = new ListStore<>(factory.getModelKeyProvider());
    cm = new ColumnModel<>(factory.getColumnConfigList());

    treeStore =
        new TreeStore<TreeData>(
            new ModelKeyProvider<TreeData>() {
              @Override
              public String getKey(TreeData item) {
                return item.getId();
              }
            });
    valueProvider =
        new ValueProvider<TreeData, String>() {
          @Override
          public String getValue(TreeData object) {
            return object.getName();
          }

          @Override
          public void setValue(DeviceInfoViewer<SC>.TreeData object, String value) {}

          @Override
          public String getPath() {
            return "treeInfo";
          }
        };

    initWidget(uiBinder.createAndBindUi(this));
    initTreeData();

    //drag source 的處理
    RemoveDeviceUtil.buildHasDeviceGrid(grid);

    //收到 event 實際移除 DeviceConfig
    AmEventCenter.addDeviceConfigDeselectHandler(
        new DeviceConfigDeselectHandler() {
          @Override
          public void onDeviceConfigDeselect(DeviceConfigDeselectEvent event) {
            for (DeviceConfigDTO dc : event.getData()) {
              store.remove(store.findModelWithKey(dc.getDeviceName()));
            }
          }
        });

    final DelayedTask task =
        new DelayedTask() {
          @Override
          public void onExecute() {
            for (TreeData eachParent : treeStore.getRootItems()) {
              for (TreeData each : treeStore.getChildren(eachParent)) {
                cm.setHidden(Integer.valueOf(each.getId()), !tree.isChecked(each));
              }
            }
          }
        };
    tree.addCheckChangedHandler(
        new CheckChangedHandler<TreeData>() {
          @Override
          public void onCheckChanged(CheckChangedEvent<TreeData> event) {
            task.delay(100);
          }
        });
    checkAll();
  }

  public void addData(List<DeviceInfoDTO<SC>> dataList) {
    //不能直接 store.allAll()，得判斷是否重複
    for (DeviceInfoDTO<SC> data : dataList) {
      if (store.findModel(data) == null) {
        store.add(data);
      }
    }
  }

  //建立樹狀資料
  //TODO 避免影響其他已建立系統,先以此方式產生, 後面考慮在DeviceInfiUtil內建立
  private void initTreeData() {
    for (ColumnGroup cg : ColumnGroup.values()) {
      if (cg == ColumnGroup.BASIC_INFO) {
        int count = DeviceInfoUtil.getDeviceConfigColumnLength();
        String basicIndex = "0_" + count;
        TreeData td = new TreeData(basicIndex, cg.getName());
        treeStore.add(td);
        for (int i = STATIC_COLUMN_LENGTH; i < count; i++) {
          treeStore.add(
              td, new TreeData(String.valueOf(i), cm.getColumn(i).getHeader().asString()));
        }
      } else if (cg == ColumnGroup.NETWORK_STATUS) {
        int count =
            DeviceInfoUtil.getDeviceConfigColumnLength()
                + DeviceInfoUtil.getMfccConfigColumnLength();
        String basicIndex = DeviceInfoUtil.getDeviceConfigColumnLength() + "_" + count;
        TreeData td = new TreeData(basicIndex, cg.getName());
        treeStore.add(td);
        for (int i = DeviceInfoUtil.getDeviceConfigColumnLength(); i < count; i++) {
          treeStore.add(
              td, new TreeData(String.valueOf(i), cm.getColumn(i).getHeader().asString()));
        }
      } else if (cg == ColumnGroup.DEVICE_STATUS) {
        if (cm.getColumns().size()
            > (DeviceInfoUtil.getDeviceConfigColumnLength()
                + DeviceInfoUtil.getMfccConfigColumnLength())) {
          int count =
              DeviceInfoUtil.getDeviceConfigColumnLength()
                  + DeviceInfoUtil.getMfccConfigColumnLength();
          String basicIndex = count + "_" + cm.getColumns().size();
          TreeData td = new TreeData(basicIndex, cg.getName());
          treeStore.add(td);
          for (int i = count; i < cm.getColumns().size(); i++) {
            treeStore.add(
                td, new TreeData(String.valueOf(i), cm.getColumn(i).getHeader().asString()));
          }
        }
      }
    }
  }

  @UiHandler("expandAll")
  public void onExpandAll(SelectEvent event) {
    tree.expandAll();
  }

  @UiHandler("collapseAll")
  public void onCollapseAll(SelectEvent event) {
    tree.collapseAll();
  }

  @UiHandler("export")
  public void onExportData(SelectEvent event) {
    if (grid.getStore().size() == 0) {
      return;
    }
    List<List<String>> csvData = new ArrayList<List<String>>();
    // generate header
    List<String> header = new ArrayList<String>();
    for (ColumnConfig<DeviceInfoDTO<SC>, ?> config : cm.getColumns()) {
      header.add(config.getHeader().asString());
    }
    csvData.add(header);

    // generate data
    List<DeviceInfoDTO<SC>> infoData = grid.getStore().getAll();
    for (DeviceInfoDTO<SC> item : infoData) {
      List<String> csvDatum = new ArrayList<String>();
      for (ColumnConfig<DeviceInfoDTO<SC>, ?> config : cm.getColumns()) {
        csvDatum.add(
            config.getValueProvider().getValue(item) == null
                ? ""
                : String.valueOf(config.getValueProvider().getValue(item)));
      }

      csvData.add(csvDatum);
    }
    ExportCsvFile.exportAsCsv("DeviceInfo.csv", csvData);
  }

  private void checkAll() {
    for (TreeData each : treeStore.getRootItems()) {
      tree.setChecked(each, CheckState.CHECKED);
    }
  }

  private class TreeData {
    private String id;
    private String name;

    public TreeData(String id, String name) {
      this.id = id;
      this.name = name;
    }

    public String getId() {
      return id;
    }

    public String getName() {
      return name;
    }
  }
}
