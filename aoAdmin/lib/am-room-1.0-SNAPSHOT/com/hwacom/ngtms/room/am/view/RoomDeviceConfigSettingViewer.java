package com.hwacom.ngtms.room.am.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Widget;
import com.hwacom.ngtms.c.shared.dto.DeviceTypeDTO;
import com.hwacom.ngtms.cam.client.ui.AmTab;
import com.hwacom.ngtms.room.am.presenter.RoomDeviceConfigSettingPresenter;
import com.hwacom.ngtms.room.am.util.StringConverter;
import com.hwacom.ngtms.room.shared.SignalType;
import com.hwacom.ngtms.room.shared.dto.RoomDeviceConfigDTO;
import com.hwacom.ngtms.room.shared.dto.RoomDeviceSubLocationConfigDTO;
import com.sencha.gxt.core.client.Style.SelectionMode;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.LabelProvider;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;
import com.sencha.gxt.widget.core.client.event.RowClickEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.form.ComboBox;
import com.sencha.gxt.widget.core.client.form.IntegerField;
import com.sencha.gxt.widget.core.client.form.SimpleComboBox;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.grid.GridView;
import com.sencha.gxt.widget.core.client.info.Info;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class RoomDeviceConfigSettingViewer extends AmTab {

  private static RoomDeviceConfigSettingViewerUiBinder uiBinder =
      GWT.create(RoomDeviceConfigSettingViewerUiBinder.class);

  interface RoomDeviceConfigSettingViewerUiBinder
      extends UiBinder<Widget, RoomDeviceConfigSettingViewer> {}

  private static GridProperties props = GWT.create(GridProperties.class);

  private final RoomDeviceConfigPropertyAccess propertyAccess =
      GWT.create(RoomDeviceConfigPropertyAccess.class);

  private RoomDeviceConfigSettingPresenter presenter = new RoomDeviceConfigSettingPresenter(this);

  @UiField(provided = true)
  ListStore<RoomDeviceSubLocationConfigDTO> store;

  @UiField(provided = true)
  ColumnModel<RoomDeviceSubLocationConfigDTO> cm;

  @UiField GridView<RoomDeviceSubLocationConfigDTO> view;

  @UiField Grid<RoomDeviceSubLocationConfigDTO> grid;

  @UiField(provided = true)
  ListStore<RoomDeviceConfigDTO> deviceStore;

  @UiField(provided = true)
  ColumnModel<RoomDeviceConfigDTO> column;

  @UiField GridView<RoomDeviceConfigDTO> gridView;

  @UiField Grid<RoomDeviceConfigDTO> deviceGrid;

  @UiField TextField displayName;

  @UiField TextField unit;

  @UiField IntegerField upperLimit;

  @UiField IntegerField lowerLimit;

  @UiField(provided = true)
  SimpleComboBox<String> enable;

  @UiField(provided = true)
  ListStore<DeviceTypeDTO> typeStore;

  @UiField(provided = true)
  LabelProvider<DeviceTypeDTO> typeProvider;

  @UiField ComboBox<DeviceTypeDTO> type;

  private String settedDeviceName = new String();

  public RoomDeviceConfigSettingViewer() {
    store = new ListStore<>(props.id());
    cm = genColumnModel();
    deviceStore = new ListStore<RoomDeviceConfigDTO>(propertyAccess.deviceName());
    initColumnModel();

    typeStore =
        new ListStore<>(
            new ModelKeyProvider<DeviceTypeDTO>() {
              @Override
              public String getKey(DeviceTypeDTO item) {
                return item.getId();
              }
            });

    typeProvider =
        new LabelProvider<DeviceTypeDTO>() {
          @Override
          public String getLabel(DeviceTypeDTO item) {
            return item.getDescription();
          }
        };

    enable =
        new SimpleComboBox<String>(
            new LabelProvider<String>() {
              @Override
              public String getLabel(String item) {
                return item;
              }
            });

    initWidget(uiBinder.createAndBindUi(this));

    enable.add("是");
    enable.add("否");
    grid.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    deviceGrid.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    presenter.initGridData();
    presenter.fiilTypeCB();
  }

  private void initColumnModel() {
    List<ColumnConfig<RoomDeviceConfigDTO, ?>> columnConfigList =
        new ArrayList<ColumnConfig<RoomDeviceConfigDTO, ?>>();

    ColumnConfig<RoomDeviceConfigDTO, String> displayName =
        new ColumnConfig<>(
            new ValueProvider<RoomDeviceConfigDTO, String>() {
              @Override
              public String getValue(RoomDeviceConfigDTO dto) {
                return dto.getDisplayName();
              }

              @Override
              public void setValue(RoomDeviceConfigDTO object, String value) {}

              @Override
              public String getPath() {
                return "displayName";
              }
            },
            70,
            "監視點名稱");
    displayName.setMenuDisabled(true);
    columnConfigList.add(displayName);

    ColumnConfig<RoomDeviceConfigDTO, String> typeDiscription =
        new ColumnConfig<>(
            new ValueProvider<RoomDeviceConfigDTO, String>() {
              @Override
              public String getValue(RoomDeviceConfigDTO dto) {
                return dto.getTypeDiscription();
              }

              @Override
              public void setValue(RoomDeviceConfigDTO object, String value) {}

              @Override
              public String getPath() {
                return "typeDiscription";
              }
            },
            70,
            "監視點設備類別");
    typeDiscription.setMenuDisabled(true);
    columnConfigList.add(typeDiscription);

    ColumnConfig<RoomDeviceConfigDTO, String> signalType =
        new ColumnConfig<>(
            new ValueProvider<RoomDeviceConfigDTO, String>() {
              @Override
              public String getValue(RoomDeviceConfigDTO dto) {
                return StringConverter.getFormattedSignalType(dto.getSignalType());
              }

              @Override
              public void setValue(RoomDeviceConfigDTO object, String value) {}

              @Override
              public String getPath() {
                return "signalType";
              }
            },
            70,
            "訊號種類");
    signalType.setMenuDisabled(true);
    columnConfigList.add(signalType);

    ColumnConfig<RoomDeviceConfigDTO, String> unit =
        new ColumnConfig<>(
            new ValueProvider<RoomDeviceConfigDTO, String>() {
              @Override
              public String getValue(RoomDeviceConfigDTO dto) {
                return dto.getUnit();
              }

              @Override
              public void setValue(RoomDeviceConfigDTO object, String value) {}

              @Override
              public String getPath() {
                return "unit";
              }
            },
            70,
            "單位");
    unit.setMenuDisabled(true);
    columnConfigList.add(unit);

    ColumnConfig<RoomDeviceConfigDTO, String> upperLimit =
        new ColumnConfig<>(
            new ValueProvider<RoomDeviceConfigDTO, String>() {
              @Override
              public String getValue(RoomDeviceConfigDTO dto) {
                if (dto.getUpperLimit() != 0) {
                  return String.valueOf(dto.getUpperLimit());
                } else {
                  return "";
                }
              }

              @Override
              public void setValue(RoomDeviceConfigDTO object, String value) {}

              @Override
              public String getPath() {
                return "upperLimit";
              }
            },
            70,
            "上限值");
    upperLimit.setMenuDisabled(true);
    columnConfigList.add(upperLimit);

    ColumnConfig<RoomDeviceConfigDTO, String> lowerLimit =
        new ColumnConfig<>(
            new ValueProvider<RoomDeviceConfigDTO, String>() {
              @Override
              public String getValue(RoomDeviceConfigDTO dto) {
                if (dto.getLowerLimit() != 0) {
                  return String.valueOf(dto.getLowerLimit());
                } else {
                  return "";
                }
              }

              @Override
              public void setValue(RoomDeviceConfigDTO object, String value) {}

              @Override
              public String getPath() {
                return "lowerLimit";
              }
            },
            70,
            "下限值");
    lowerLimit.setMenuDisabled(true);
    columnConfigList.add(lowerLimit);

    ColumnConfig<RoomDeviceConfigDTO, String> alarmCheck =
        new ColumnConfig<>(
            new ValueProvider<RoomDeviceConfigDTO, String>() {
              @Override
              public String getValue(RoomDeviceConfigDTO dto) {
                return dto.getAlarmCheck() == true ? "是" : "否";
              }

              @Override
              public void setValue(RoomDeviceConfigDTO object, String value) {}

              @Override
              public String getPath() {
                return "alarmCheck";
              }
            },
            70,
            "是否發出警報");
    alarmCheck.setMenuDisabled(true);
    columnConfigList.add(alarmCheck);

    column = new ColumnModel<>(columnConfigList);
  }

  private ColumnModel<RoomDeviceSubLocationConfigDTO> genColumnModel() {
    List<ColumnConfig<RoomDeviceSubLocationConfigDTO, ?>> columnConfigList = new ArrayList<>();

    ColumnConfig<RoomDeviceSubLocationConfigDTO, String> locationName =
        new ColumnConfig<>(
            new ValueProvider<RoomDeviceSubLocationConfigDTO, String>() {
              @Override
              public String getValue(RoomDeviceSubLocationConfigDTO dto) {
                return dto.getLocationName() + "-" + dto.getSubLocation();
              }

              @Override
              public void setValue(RoomDeviceSubLocationConfigDTO object, String value) {}

              @Override
              public String getPath() {
                return "locationName";
              }
            },
            100,
            "");
    locationName.setMenuDisabled(true);
    columnConfigList.add(locationName);

    return new ColumnModel<>(columnConfigList);
  }

  @UiHandler("grid")
  public void onGridRowClick(RowClickEvent event) {
    RoomDeviceSubLocationConfigDTO selectedDto = grid.getSelectionModel().getSelectedItem();
    presenter.fiilDeviceGridData(selectedDto.getLocationName(), selectedDto.getSubLocation());
  }

  @UiHandler("deviceGrid")
  public void onRowClick(RowClickEvent event) {
    RoomDeviceConfigDTO selectedDevice = deviceGrid.getSelectionModel().getSelectedItem();
    displayName.setValue(selectedDevice.getDisplayName());
    enable.setValue(selectedDevice.getAlarmCheck() ? "是" : "否");
    type.setValue(typeStore.findModelWithKey(selectedDevice.getType()));
    if (selectedDevice.getSignalType() == SignalType.DIGITAL_IN) {
      unit.disable();
      unit.clear();
      upperLimit.disable();
      upperLimit.clear();
      lowerLimit.disable();
      lowerLimit.clear();
    } else if (selectedDevice.getSignalType() == SignalType.ANALOG_IN) {
      unit.enable();
      unit.setAllowBlank(false);
      unit.setValue(selectedDevice.getUnit());
      upperLimit.enable();
      upperLimit.setAllowBlank(false);
      upperLimit.setValue(selectedDevice.getUpperLimit());
      lowerLimit.enable();
      lowerLimit.setAllowBlank(false);
      lowerLimit.setValue(selectedDevice.getLowerLimit());
    }
  }

  @UiHandler("saveButton")
  public void onSelectSaveButton(SelectEvent event) {
    RoomDeviceConfigDTO selectedDevice = deviceGrid.getSelectionModel().getSelectedItem();
    SignalType deviceSignalType = selectedDevice.getSignalType();
    if (checkField(deviceSignalType)) {
      Info.display("儲存失敗", "尚有欄位為空");
      return;
    }
    selectedDevice.setDisplayName(displayName.getCurrentValue());
    selectedDevice.setType(type.getCurrentValue().getId());
    selectedDevice.setAlarmCheck(enable.getCurrentValue() == "是" ? true : false);
    if (deviceSignalType == SignalType.ANALOG_IN) {
      selectedDevice.setUpperLimit(upperLimit.getCurrentValue());
      selectedDevice.setLowerLimit(lowerLimit.getCurrentValue());
      selectedDevice.setUnit(unit.getCurrentValue());
    }
    presenter.updateRoomDeviceConfig(selectedDevice);
  }

  public Boolean checkField(SignalType signalType) {
    Boolean check = false;
    if (displayName == null || type.getValue() == null) {
      check = true;
    } else {
      if (signalType == SignalType.ANALOG_IN) {
        if (unit == null || upperLimit == null || lowerLimit == null) {
          check = true;
        }
      }
    }
    return check;
  }

  public void updateConfig(Boolean result) {
    if (result) {
      Info.display("儲存成功", "");
    } else {
      Info.display("儲存失敗", "");
    }
    RoomDeviceSubLocationConfigDTO selected = grid.getSelectionModel().getSelectedItem();
    presenter.fiilDeviceGridData(selected.getLocationName(), selected.getSubLocation());
  }

  public void fillGridData(List<RoomDeviceSubLocationConfigDTO> response) {
    Collections.sort(
        response,
        new Comparator<RoomDeviceSubLocationConfigDTO>() {
          @Override
          public int compare(RoomDeviceSubLocationConfigDTO o1, RoomDeviceSubLocationConfigDTO o2) {
            return o1.getLocationName().compareTo(o2.getLocationName());
          }
        });
    store.clear();
    store.addAll(response);
  }

  public void fillDeviceGrid(List<RoomDeviceConfigDTO> list) {
    deviceStore.clear();
    for (RoomDeviceConfigDTO each : list) {
      deviceStore.add(each);
    }
    if (!settedDeviceName.equals("")) {
      deviceGrid.getSelectionModel().select(true, deviceStore.findModelWithKey(settedDeviceName));
      RoomDeviceConfigDTO selectedDevice = deviceGrid.getSelectionModel().getSelectedItem();
      displayName.setValue(selectedDevice.getDisplayName());
      enable.setValue(selectedDevice.getAlarmCheck() ? "是" : "否");
      type.setValue(typeStore.findModelWithKey(selectedDevice.getType()));
      if (selectedDevice.getSignalType() == SignalType.DIGITAL_IN) {
        unit.disable();
        unit.clear();
        upperLimit.disable();
        upperLimit.clear();
        lowerLimit.disable();
        lowerLimit.clear();
      } else if (selectedDevice.getSignalType() == SignalType.ANALOG_IN) {
        unit.enable();
        unit.setAllowBlank(false);
        unit.setValue(selectedDevice.getUnit());
        upperLimit.enable();
        upperLimit.setAllowBlank(false);
        upperLimit.setValue(selectedDevice.getUpperLimit());
        lowerLimit.enable();
        lowerLimit.setAllowBlank(false);
        lowerLimit.setValue(selectedDevice.getLowerLimit());
      }
      settedDeviceName = new String();
    }
  }

  public void fillTypeCB(List<DeviceTypeDTO> list) {
    typeStore.addAll(list);
  }

  public void receiveSettingEvent(String deviceName) {
    settedDeviceName = deviceName;
    presenter.findSettedDevcieLocation(settedDeviceName);
  }

  public void setSettedDeviceLocation(String id) {
    grid.getSelectionModel().select(true, store.findModelWithKey(id));
    RoomDeviceSubLocationConfigDTO selectedDto = grid.getSelectionModel().getSelectedItem();
    presenter.fiilDeviceGridData(selectedDto.getLocationName(), selectedDto.getSubLocation());
  }

  public void setPresenter(RoomDeviceConfigSettingPresenter presenter) {
    this.presenter = presenter;
  }

  interface GridProperties extends PropertyAccess<RoomDeviceSubLocationConfigDTO> {
    ModelKeyProvider<RoomDeviceSubLocationConfigDTO> id();

    ValueProvider<RoomDeviceSubLocationConfigDTO, String> locationName();
  }

  interface RoomDeviceConfigPropertyAccess extends PropertyAccess<RoomDeviceConfigDTO> {
    ModelKeyProvider<RoomDeviceConfigDTO> deviceName();

    ValueProvider<RoomDeviceConfigDTO, String> displayName();

    ValueProvider<RoomDeviceConfigDTO, SignalType> signalType();

    ValueProvider<RoomDeviceConfigDTO, String> typeDiscription();

    ValueProvider<RoomDeviceConfigDTO, String> unit();

    ValueProvider<RoomDeviceConfigDTO, Integer> upperLimit();

    ValueProvider<RoomDeviceConfigDTO, Integer> lowerLimit();

    ValueProvider<RoomDeviceConfigDTO, Boolean> alarmCheck();
  }
}
