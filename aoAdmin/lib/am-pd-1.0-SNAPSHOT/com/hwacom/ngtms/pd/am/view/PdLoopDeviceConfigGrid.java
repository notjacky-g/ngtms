package com.hwacom.ngtms.pd.am.view;

import com.google.gwt.core.client.GWT;
import com.hwacom.ngtms.pd.shared.dto.LoopDeviceConfigDTO;
import com.sencha.gxt.core.client.Style.SelectionMode;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;
import com.sencha.gxt.data.shared.TreeStore;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;
import com.sencha.gxt.widget.core.client.treegrid.TreeGrid;
import com.sencha.gxt.widget.core.client.treegrid.TreeGridView;
import java.util.ArrayList;
import java.util.List;

public class PdLoopDeviceConfigGrid extends TreeGrid<LoopDeviceConfigDTO> {

  private static final PdLoopDeviceConfigProperties props =
      GWT.create(PdLoopDeviceConfigProperties.class);

  static TreeStore<LoopDeviceConfigDTO> treeStore =
      new TreeStore<>(PdLoopDeviceConfigProperties.deviceName);

  private static Messages messages = GWT.create(Messages.class);

  private static ColumnConfig<LoopDeviceConfigDTO, String> deviceNameCol;

  private static ColumnModel<LoopDeviceConfigDTO> genColumnModel() {
    ArrayList<ColumnConfig<LoopDeviceConfigDTO, ?>> columnConfigList = new ArrayList<>();
    deviceNameCol =
        new ColumnConfig<LoopDeviceConfigDTO, String>(
            new ValueProvider<LoopDeviceConfigDTO, String>() {
              @Override
              public String getValue(LoopDeviceConfigDTO item) {
                return item.getDisplayName();
              }

              @Override
              public void setValue(LoopDeviceConfigDTO object, String value) {}

              @Override
              public String getPath() {
                return "deviceName";
              }
            },
            150,
            messages.column_loopDeviceConfig_deviceName());
    columnConfigList.add(deviceNameCol);

    columnConfigList.add(
        new ColumnConfig<>(
            new ValueProvider<LoopDeviceConfigDTO, String>() {
              @Override
              public String getValue(LoopDeviceConfigDTO dto) {
                if (dto.getDiameter() != null) {
                  return String.valueOf(dto.getDiameter());
                } else {
                  return "";
                }
              }

              @Override
              public void setValue(LoopDeviceConfigDTO object, String value) {}

              @Override
              public String getPath() {
                return "diameter";
              }
            },
            100,
            messages.column_loopDeviceConfig_diameter()));

    columnConfigList.add(
        new ColumnConfig<>(
            new ValueProvider<LoopDeviceConfigDTO, String>() {
              @Override
              public String getValue(LoopDeviceConfigDTO dto) {
                return dto.getMemo() == null ? "" : dto.getMemo();
              }

              @Override
              public void setValue(LoopDeviceConfigDTO object, String value) {}

              @Override
              public String getPath() {
                return "memo";
              }
            },
            100,
            messages.column_loopDeviceConfig_memo()));

    return new ColumnModel<>(columnConfigList);
  }

  public void setGridStore(List<LoopDeviceConfigDTO> result) {
    treeStore.clear();
    for (LoopDeviceConfigDTO data : result) {
      treeStore.add(data);
      for (LoopDeviceConfigDTO loopData : data.getChildren()) {
        treeStore.add(data, loopData);
        if (loopData.getChildren() != null) {
          for (LoopDeviceConfigDTO deviceData : loopData.getChildren()) {
            treeStore.add(loopData, deviceData);
          }
        }
      }
    }
  }

  public PdLoopDeviceConfigGrid() {
    super(treeStore, genColumnModel(), deviceNameCol);
    TreeGridView<LoopDeviceConfigDTO> gridView = new TreeGridView<LoopDeviceConfigDTO>();
    gridView.setAutoFill(true);
    setView(gridView);
    this.expandAll();
    getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
  }

  interface PdLoopDeviceConfigProperties extends PropertyAccess<LoopDeviceConfigDTO> {
    ModelKeyProvider<LoopDeviceConfigDTO> deviceName =
        new ModelKeyProvider<LoopDeviceConfigDTO>() {

          @Override
          public String getKey(LoopDeviceConfigDTO item) {
            return item.getDeviceName() + "_" + item.getDeviceName();
          }
        };

    ValueProvider<LoopDeviceConfigDTO, String> displayName();

    ValueProvider<LoopDeviceConfigDTO, Integer> diameter();

    ValueProvider<LoopDeviceConfigDTO, String> memo();
  }
}
