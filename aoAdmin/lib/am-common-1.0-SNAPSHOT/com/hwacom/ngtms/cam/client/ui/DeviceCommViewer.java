/*
 * © HwaCom Systems Inc. 2015
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.cam.client.ui;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.HasSelectionHandlers;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Widget;
import com.hwacom.ngtms.c.shared.HasDeviceConfig;
import com.hwacom.ngtms.c.shared.dto.DeviceConfigDTO;
import com.hwacom.ngtms.cam.client.event.AmEventCenter;
import com.hwacom.ngtms.cam.client.event.DeviceConfigDeselectEvent;
import com.hwacom.ngtms.cam.client.event.DeviceConfigDeselectHandler;
import com.hwacom.ngtms.cam.client.ui.dnd.RemoveDeviceUtil;
import com.hwacom.ngtms.cam.view.Messages;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.grid.GridView;
import com.sencha.gxt.widget.core.client.tips.QuickTip;
import java.util.ArrayList;
import java.util.List;

/**
 * 提供一個包含 {@link Grid} 的 {@link ContentPanel}， Grid 的內容為 {@link DeviceConfigDTO#getDeviceName()}
 * 與通訊回傳結果。 當滑鼠移到 Grid 上頭，無論停在「名稱」或是「通訊結果」欄位，都用 tooltip 的方式顯示「名稱 : 通訊結果」。
 *
 * @author monty.pan
 */
public class DeviceCommViewer extends ContentPanel
    implements HasSelectionHandlers<DeviceConfigDTO> {
  private static DeviceCommViewUiBinder uiBinder = GWT.create(DeviceCommViewUiBinder.class);

  interface DeviceCommViewUiBinder extends UiBinder<Widget, DeviceCommViewer> {}

  private final Messages messages = GWT.create(Messages.class);

  @UiField(provided = true)
  ListStore<Data> store;

  @UiField(provided = true)
  ColumnModel<Data> cm;

  @UiField GridView<Data> view;
  @UiField Grid<Data> grid;

  private ArrayList<DeviceConfigDTO> allDevice = new ArrayList<>();

  public DeviceCommViewer() {
    /*
     * 目前只找得到 QuickTip 的方式讓 Grid 的 cell（還找不到 row 的方法）顯示 tooltip，
     * QuickTip 顯示 tooltip 的方法是去認 HTML attribute（qtip 等），所以只好寫成這個樣子。
     * Refactory 找尋替代 QuickTip / 其他的寫法
     */
    ArrayList<ColumnConfig<Data, ?>> columnConfigList = new ArrayList<>();
    ColumnConfig<Data, Data> nameColumn =
        new ColumnConfig<>(new DataVP(), 100, messages.grid_header_name());
    nameColumn.setCell(
        new AbstractCell<DeviceCommViewer.Data>() {
          @Override
          public void render(Context context, Data value, SafeHtmlBuilder sb) {
            sb.appendHtmlConstant(
                "<span qtip='"
                    + value.getTipString()
                    + "'>"
                    + value.getDeviceConfig().getDisplayName()
                    + "</span>");
          }
        });
    columnConfigList.add(nameColumn);

    ColumnConfig<Data, Data> resultColumn =
        new ColumnConfig<>(new DataVP(), 100, messages.grid_header_connectResult());
    resultColumn.setCell(
        new AbstractCell<DeviceCommViewer.Data>() {
          @Override
          public void render(Context context, Data value, SafeHtmlBuilder sb) {
            sb.appendHtmlConstant(
                "<span qtip='" + value.getTipString() + "'>" + value.getResult() + "</span>");
          }
        });
    columnConfigList.add(resultColumn);

    cm = new ColumnModel<>(columnConfigList);
    store =
        new ListStore<>(
            new ModelKeyProvider<Data>() {
              @Override
              public String getKey(Data item) {
                return item.getDeviceConfig().getDeviceName();
              }
            });

    setHeading(messages.header_selectedDevice());
    this.add(uiBinder.createAndBindUi(this));

    new QuickTip(grid);

    //drag source 的處理
    RemoveDeviceUtil.buildHasDeviceGrid(grid);

    //收到 event 實際移除 DeviceConfig
    AmEventCenter.addDeviceConfigDeselectHandler(
        new DeviceConfigDeselectHandler() {
          @Override
          public void onDeviceConfigDeselect(DeviceConfigDeselectEvent event) {
            removeDevices(event.getData());
          }
        });

    grid.getSelectionModel()
        .addSelectionHandler(
            new SelectionHandler<Data>() {
              @Override
              public void onSelection(SelectionEvent<Data> event) {
                SelectionEvent.<DeviceConfigDTO>fire(
                    DeviceCommViewer.this, event.getSelectedItem().getDeviceConfig());
              }
            });
  }

  public void removeDevices(List<DeviceConfigDTO> data) {
    for (DeviceConfigDTO dc : data) {
      store.remove(store.findModelWithKey(dc.getDeviceName()));
      allDevice.remove(dc);
    }
  }

  public void addDevices(List<DeviceConfigDTO> dcList) {
    for (DeviceConfigDTO dc : dcList) {
      Data data = store.findModelWithKey(dc.getDeviceName());
      if (data == null) {
        data = new Data(dc);
        store.add(data);
        allDevice.add(dc);
      }
    }
  }

  public DeviceConfigDTO getSelectedDevice() {
    return grid.getSelectionModel().getSelectedItem() == null
        ? null
        : grid.getSelectionModel().getSelectedItem().getDeviceConfig();
  }

  public ArrayList<DeviceConfigDTO> getDeviceList() {
    return allDevice;
  }

  public void setCommResult(String deviceName, String result) {
    Data data = store.findModelWithKey(deviceName);
    if (data != null) {
      data.setResult(result);
    }
    view.refresh(false);
  }

  public void clearCommResult() {
    for (Data d : store.getAll()) {
      d.setResult("");
    }
    view.refresh(false);
  }

  @Override
  public HandlerRegistration addSelectionHandler(SelectionHandler<DeviceConfigDTO> handler) {
    return super.addHandler(handler, SelectionEvent.getType());
  }

  private class Data implements HasDeviceConfig {
    private DeviceConfigDTO config;
    private String result;

    public Data(DeviceConfigDTO dc) {
      config = dc;
    }

    public String getTipString() {
      return config.getDeviceName() + " : " + getResult();
    }

    public String getResult() {
      return result == null ? "" : result;
    }

    public void setResult(String result) {
      this.result = result;
    }

    @Override
    public DeviceConfigDTO getDeviceConfig() {
      return config;
    }

    @Override
    public void setDeviceConfig(DeviceConfigDTO deviceConfig) {
      config = deviceConfig;
    }
  }

  private class DataVP implements ValueProvider<Data, Data> {
    @Override
    public Data getValue(Data object) {
      return object;
    }

    @Override
    public void setValue(Data object, Data value) {}

    @Override
    public String getPath() {
      return null;
    }
  }
}
