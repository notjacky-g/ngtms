package com.hwacom.ngtms.pd.am.view;

import com.google.gwt.core.client.GWT;
import com.hwacom.ngtms.hcce.am.factory.ClientFactory;
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

public class PdUnsetLoopDeviceConfigGrid extends TreeGrid<LoopDeviceConfigDTO> {

  private static final PdUnsetLoopDeviceConfigProperties props =
      GWT.create(PdUnsetLoopDeviceConfigProperties.class);

  static TreeStore<LoopDeviceConfigDTO> treeStore =
      new TreeStore<>(PdUnsetLoopDeviceConfigProperties.deviceName);

  private static Messages messages = GWT.create(Messages.class);

  private ClientFactory clientFactory = GWT.create(ClientFactory.class);

  private static ColumnConfig<LoopDeviceConfigDTO, String> deviceNameCol;

  private static ColumnModel<LoopDeviceConfigDTO> genColumnModel() {
    ArrayList<ColumnConfig<LoopDeviceConfigDTO, ?>> columnConfigList = new ArrayList<>();

    deviceNameCol =
        new ColumnConfig<LoopDeviceConfigDTO, String>(
            new ValueProvider<LoopDeviceConfigDTO, String>() {
              @Override
              public String getValue(LoopDeviceConfigDTO item) {
                return item.getDisplayName() == null ? item.getDeviceName() : item.getDisplayName();
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

    return new ColumnModel<>(columnConfigList);
  }

  public void setGridStore(List<LoopDeviceConfigDTO> result) {
    treeStore.clear();
    buildTree(null, result);
  }

  private void buildTree(LoopDeviceConfigDTO parent, List<LoopDeviceConfigDTO> data) {
    for (LoopDeviceConfigDTO item : data) {
      if (parent == null) {
        treeStore.add(item);
      } else {
        treeStore.add(parent, item);
      }
      if (item.getChildren() != null) {
        buildTree(item, item.getChildren());
      }
    }
  }

  public PdUnsetLoopDeviceConfigGrid() {
    super(treeStore, genColumnModel(), deviceNameCol);
    TreeGridView<LoopDeviceConfigDTO> gridView = new TreeGridView<LoopDeviceConfigDTO>();
    gridView.setAutoFill(false);
    setView(gridView);
    //this.expandAll();
    setHideHeaders(true);
    getSelectionModel().setSelectionMode(SelectionMode.MULTI);
  }

  interface PdUnsetLoopDeviceConfigProperties extends PropertyAccess<LoopDeviceConfigDTO> {
    ModelKeyProvider<LoopDeviceConfigDTO> deviceName =
        new ModelKeyProvider<LoopDeviceConfigDTO>() {

          @Override
          public String getKey(LoopDeviceConfigDTO item) {
            return item.getDeviceName() + "_" + item.getDeviceName();
          }
        };;

    ValueProvider<LoopDeviceConfigDTO, String> displayName();
  }
}
