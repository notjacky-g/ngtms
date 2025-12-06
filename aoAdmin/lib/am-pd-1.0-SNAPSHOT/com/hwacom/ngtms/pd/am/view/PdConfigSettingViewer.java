package com.hwacom.ngtms.pd.am.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Widget;
import com.hwacom.ngtms.c.shared.Direction;
import com.hwacom.ngtms.c.shared.dto.RoadLineDTO;
import com.hwacom.ngtms.c.shared.dto.RoadSectionDTO;
import com.hwacom.ngtms.cam.client.ui.AmTab;
import com.hwacom.ngtms.hcce.am.factory.ClientFactory;
import com.hwacom.ngtms.pd.am.event.PdConfigSettingViewerEvent;
import com.hwacom.ngtms.pd.am.presenter.PdConfigSettingPresenter;
import com.hwacom.ngtms.pd.am.util.StringConverter;
import com.hwacom.ngtms.pd.shared.dto.DirectionDTO;
import com.hwacom.ngtms.pd.shared.dto.LocationDTO;
import com.hwacom.ngtms.pd.shared.dto.PdConfigDTO;
import com.sencha.gxt.core.client.Style.SelectionMode;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.LabelProvider;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;
import com.sencha.gxt.widget.core.client.Dialog;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.event.RowClickEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.form.ComboBox;
import com.sencha.gxt.widget.core.client.form.DoubleField;
import com.sencha.gxt.widget.core.client.form.IntegerField;
import com.sencha.gxt.widget.core.client.form.SimpleComboBox;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.info.Info;
import java.util.ArrayList;
import java.util.List;

public class PdConfigSettingViewer extends AmTab {

  private static PdConfigSettingViewerUiBinder uiBinder =
      GWT.create(PdConfigSettingViewerUiBinder.class);

  interface PdConfigSettingViewerUiBinder extends UiBinder<Widget, PdConfigSettingViewer> {}

  private final PdConfigPropertyAccess propertyAccess = GWT.create(PdConfigPropertyAccess.class);

  private static Messages messages = GWT.create(Messages.class);

  private PdConfigSettingPresenter presenter = new PdConfigSettingPresenter(this);

  private ClientFactory clientFactory = GWT.create(ClientFactory.class);

  @UiField Grid<PdConfigDTO> grid;

  @UiField(provided = true)
  ListStore<PdConfigDTO> listStore;

  @UiField(provided = true)
  ColumnModel<PdConfigDTO> columnModel;

  @UiField ComboBox<DirectionDTO> direction;

  @UiField(provided = true)
  ListStore<DirectionDTO> directionStore;

  @UiField(provided = true)
  LabelProvider<DirectionDTO> directionProvider;

  @UiField ComboBox<LocationDTO> location;

  @UiField(provided = true)
  ListStore<LocationDTO> locationStore;

  @UiField(provided = true)
  LabelProvider<LocationDTO> locationProvider;

  @UiField ComboBox<RoadLineDTO> line;

  @UiField(provided = true)
  ListStore<RoadLineDTO> lineStore;

  @UiField(provided = true)
  LabelProvider<RoadLineDTO> lineProvider;

  @UiField ComboBox<RoadSectionDTO> section;

  @UiField(provided = true)
  ListStore<RoadSectionDTO> sectionStore;

  @UiField(provided = true)
  LabelProvider<RoadSectionDTO> sectionProvider;

  @UiField TextField deviceName;

  @UiField TextField displayName;

  @UiField IntegerField kilometer;

  @UiField IntegerField meter;

  @UiField TextField meterNo;

  @UiField TextField area;

  @UiField TextField phone;

  @UiField TextButton save;

  @UiField TextButton update;

  @UiField TextButton delete;

  @UiField TextField ip;

  @UiField IntegerField port;

  @UiField DoubleField longitude;

  @UiField DoubleField latitude;

  @UiField IntegerField loopNo;

  @UiField TextField memo;

  @UiField(provided = true)
  SimpleComboBox<String> enable;

  private Dialog confirmDialog;
  private Boolean check = false;

  public PdConfigSettingViewer() {
    initComboBox();
    directionStore =
        new ListStore<>(
            new ModelKeyProvider<DirectionDTO>() {
              @Override
              public String getKey(DirectionDTO item) {
                return item.toString();
              }
            });
    directionProvider =
        new LabelProvider<DirectionDTO>() {
          @Override
          public String getLabel(DirectionDTO item) {
            return StringConverter.getFormattedDirection(item.getDirection());
          }
        };

    locationStore =
        new ListStore<>(
            new ModelKeyProvider<LocationDTO>() {
              @Override
              public String getKey(LocationDTO item) {
                return item.toString();
              }
            });
    locationProvider =
        new LabelProvider<LocationDTO>() {
          @Override
          public String getLabel(LocationDTO item) {
            return item.getLocName();
          }
        };

    lineStore =
        new ListStore<>(
            new ModelKeyProvider<RoadLineDTO>() {
              @Override
              public String getKey(RoadLineDTO item) {
                return item.toString();
              }
            });

    lineProvider =
        new LabelProvider<RoadLineDTO>() {
          @Override
          public String getLabel(RoadLineDTO item) {
            return item.getLineName();
          }
        };

    sectionStore =
        new ListStore<>(
            new ModelKeyProvider<RoadSectionDTO>() {
              @Override
              public String getKey(RoadSectionDTO item) {
                return item.toString();
              }
            });

    sectionProvider =
        new LabelProvider<RoadSectionDTO>() {
          @Override
          public String getLabel(RoadSectionDTO item) {
            return item.getSectionName();
          }
        };

    listStore = new ListStore<PdConfigDTO>(propertyAccess.deviceName());
    initColumnModel();
    initWidget(uiBinder.createAndBindUi(this));
    setDirectionData();
    grid.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

    mask(messages.pdConfigSettingViewer_info_adjusting());

    enable.add(messages.pdConfigSettingViewer_enable_true());
    enable.add(messages.pdConfigSettingViewer_enable_false());
    presenter.retrievePdConfigDTO();
    presenter.retrieveLocation();
    presenter.retrieveRoadLine();
    presenter.retrieveSection();
  }

  private void initColumnModel() {
    List<ColumnConfig<PdConfigDTO, ?>> columnConfigs =
        new ArrayList<ColumnConfig<PdConfigDTO, ?>>();

    ColumnConfig<PdConfigDTO, String> displayName =
        new ColumnConfig<>(
            new ValueProvider<PdConfigDTO, String>() {
              @Override
              public String getValue(PdConfigDTO dto) {
                return dto.getDisplayName();
              }

              @Override
              public void setValue(PdConfigDTO object, String value) {}

              @Override
              public String getPath() {
                return "displayName";
              }
            },
            100,
            messages.column_pdConfig_displayName());
    columnConfigs.add(displayName);

    ColumnConfig<PdConfigDTO, String> location =
        new ColumnConfig<>(
            new ValueProvider<PdConfigDTO, String>() {
              @Override
              public String getValue(PdConfigDTO dto) {
                return dto.getLocation();
              }

              @Override
              public void setValue(PdConfigDTO object, String value) {}

              @Override
              public String getPath() {
                return "location";
              }
            },
            100,
            messages.column_pdConfig_locationNo());
    columnConfigs.add(location);

    ColumnConfig<PdConfigDTO, String> lineName =
        new ColumnConfig<>(
            new ValueProvider<PdConfigDTO, String>() {
              @Override
              public String getValue(PdConfigDTO dto) {
                return dto.getLineName();
              }

              @Override
              public void setValue(PdConfigDTO object, String value) {}

              @Override
              public String getPath() {
                return "lineName";
              }
            },
            80,
            messages.column_pdConfig_lineId());
    columnConfigs.add(lineName);

    ColumnConfig<PdConfigDTO, String> direction =
        new ColumnConfig<>(
            new ValueProvider<PdConfigDTO, String>() {
              @Override
              public String getValue(PdConfigDTO dto) {
                return StringConverter.getFormattedDirection(dto.getDirection());
              }

              @Override
              public void setValue(PdConfigDTO object, String value) {}

              @Override
              public String getPath() {
                return "direction";
              }
            },
            60,
            messages.column_pdConfig_direction());
    columnConfigs.add(direction);

    ColumnConfig<PdConfigDTO, String> milepost =
        new ColumnConfig<>(
            new ValueProvider<PdConfigDTO, String>() {
              @Override
              public String getValue(PdConfigDTO dto) {
                return StringConverter.getFormattedMileage(dto.getMilepost());
              }

              @Override
              public void setValue(PdConfigDTO object, String value) {}

              @Override
              public String getPath() {
                return "milepost";
              }
            },
            60,
            messages.column_pdConfig_milepost());
    columnConfigs.add(milepost);

    ColumnConfig<PdConfigDTO, String> sectionName =
        new ColumnConfig<>(
            new ValueProvider<PdConfigDTO, String>() {
              @Override
              public String getValue(PdConfigDTO dto) {
                return dto.getSectionName();
              }

              @Override
              public void setValue(PdConfigDTO object, String value) {}

              @Override
              public String getPath() {
                return "sectionName";
              }
            },
            80,
            messages.column_pdConfig_section());
    columnConfigs.add(sectionName);

    ColumnConfig<PdConfigDTO, String> meterNo =
        new ColumnConfig<>(
            new ValueProvider<PdConfigDTO, String>() {
              @Override
              public String getValue(PdConfigDTO dto) {
                return dto.getMeterNo();
              }

              @Override
              public void setValue(PdConfigDTO object, String value) {}

              @Override
              public String getPath() {
                return "meterNo";
              }
            },
            100,
            messages.column_pdConfig_meterNo());
    columnConfigs.add(meterNo);

    ColumnConfig<PdConfigDTO, String> area =
        new ColumnConfig<>(
            new ValueProvider<PdConfigDTO, String>() {
              @Override
              public String getValue(PdConfigDTO dto) {
                return dto.getArea();
              }

              @Override
              public void setValue(PdConfigDTO object, String value) {}

              @Override
              public String getPath() {
                return "area";
              }
            },
            80,
            messages.column_pdConfig_area());
    columnConfigs.add(area);

    ColumnConfig<PdConfigDTO, String> phone =
        new ColumnConfig<>(
            new ValueProvider<PdConfigDTO, String>() {
              @Override
              public String getValue(PdConfigDTO dto) {
                return dto.getPhone();
              }

              @Override
              public void setValue(PdConfigDTO object, String value) {}

              @Override
              public String getPath() {
                return "phone";
              }
            },
            100,
            messages.column_pdConfig_phone());
    columnConfigs.add(phone);

    ColumnConfig<PdConfigDTO, String> ip =
        new ColumnConfig<>(
            new ValueProvider<PdConfigDTO, String>() {
              @Override
              public String getValue(PdConfigDTO dto) {
                return dto.getIp();
              }

              @Override
              public void setValue(PdConfigDTO object, String value) {}

              @Override
              public String getPath() {
                return "ip";
              }
            },
            100,
            messages.column_pdConfig_ip());
    columnConfigs.add(ip);

    ColumnConfig<PdConfigDTO, Integer> port =
        new ColumnConfig<>(
            new ValueProvider<PdConfigDTO, Integer>() {
              @Override
              public Integer getValue(PdConfigDTO dto) {
                return dto.getPort();
              }

              @Override
              public void setValue(PdConfigDTO object, Integer value) {}

              @Override
              public String getPath() {
                return "port";
              }
            },
            80,
            messages.column_pdConfig_port());
    columnConfigs.add(port);

    ColumnConfig<PdConfigDTO, Double> longitude =
        new ColumnConfig<>(
            new ValueProvider<PdConfigDTO, Double>() {
              @Override
              public Double getValue(PdConfigDTO dto) {
                return dto.getLongitude();
              }

              @Override
              public void setValue(PdConfigDTO object, Double value) {}

              @Override
              public String getPath() {
                return "longitude";
              }
            },
            120,
            messages.column_pdConfig_longitude());
    columnConfigs.add(longitude);

    ColumnConfig<PdConfigDTO, Double> latitude =
        new ColumnConfig<>(
            new ValueProvider<PdConfigDTO, Double>() {
              @Override
              public Double getValue(PdConfigDTO dto) {
                return dto.getLatitude();
              }

              @Override
              public void setValue(PdConfigDTO object, Double value) {}

              @Override
              public String getPath() {
                return "latitude";
              }
            },
            120,
            "緯度");
    columnConfigs.add(latitude);

    ColumnConfig<PdConfigDTO, Integer> loopNo =
        new ColumnConfig<>(
            new ValueProvider<PdConfigDTO, Integer>() {
              @Override
              public Integer getValue(PdConfigDTO dto) {
                return dto.getLoopNo();
              }

              @Override
              public void setValue(PdConfigDTO object, Integer value) {}

              @Override
              public String getPath() {
                return "loopNo";
              }
            },
            70,
            messages.column_pdConfig_loopNo());
    columnConfigs.add(loopNo);

    ColumnConfig<PdConfigDTO, String> memo =
        new ColumnConfig<>(
            new ValueProvider<PdConfigDTO, String>() {
              @Override
              public String getValue(PdConfigDTO dto) {
                return dto.getIp();
              }

              @Override
              public void setValue(PdConfigDTO object, String value) {}

              @Override
              public String getPath() {
                return "memo";
              }
            },
            60,
            messages.column_pdConfig_memo());
    columnConfigs.add(memo);

    ColumnConfig<PdConfigDTO, String> enable =
        new ColumnConfig<>(
            new ValueProvider<PdConfigDTO, String>() {
              @Override
              public String getValue(PdConfigDTO dto) {
                return dto.getEnable()
                    ? messages.pdConfigSettingViewer_enable_true()
                    : messages.pdConfigSettingViewer_enable_false();
              }

              @Override
              public void setValue(PdConfigDTO object, String value) {}

              @Override
              public String getPath() {
                return "enable";
              }
            },
            60,
            messages.column_pdConfig_enable());
    columnConfigs.add(enable);

    columnModel = new ColumnModel<>(columnConfigs);
  }

  @UiHandler("grid")
  public void rowClick(RowClickEvent event) {
    PdConfigDTO dto = grid.getSelectionModel().getSelectedItem();
    deviceName.setValue(dto.getDeviceName());
    displayName.setValue(dto.getDisplayName());

    if (dto.getLineId() != null) {
      for (RoadLineDTO each : lineStore.getAll()) {
        if (each.getLineId() == dto.getLineId()) {
          line.setValue(each);
        }
      }
    }

    if (dto.getLocation() != null) {
      for (LocationDTO each : locationStore.getAll()) {
        if (each.getLocName().equals(dto.getLocation())) {
          location.setValue(each);
          break;
        }
      }
    }

    if (dto.getDirection() != null) {
      for (DirectionDTO each : directionStore.getAll()) {
        if (each.getDirection() == dto.getDirection()) {
          direction.setValue(each);
          break;
        }
      }
    }

    if (dto.getSectionId() != null) {
      for (RoadSectionDTO each : sectionStore.getAll()) {
        if (each.getSectionId() == dto.getSectionId()) {
          section.setValue(each);
          break;
        }
      }
    }

    enable.setValue(dto.getEnable() ? enable.getStore().get(0) : enable.getStore().get(1));
    if (dto.getMilepost() != null) {
      kilometer.setValue(dto.getMilepost() / 1000);
      meter.setValue(dto.getMilepost() % 1000);
    }
    meterNo.setValue(dto.getMeterNo() != null ? dto.getMeterNo() : null);
    area.setValue(dto.getArea() != null ? dto.getArea() : null);
    phone.setValue(dto.getPhone() != null ? dto.getPhone() : null);
    ip.setValue(dto.getIp() != null ? dto.getIp() : null);
    port.setValue(dto.getPort() != null ? dto.getPort() : null);
    longitude.setValue(dto.getLongitude() != null ? dto.getLongitude() : null);
    latitude.setValue(dto.getLatitude() != null ? dto.getLatitude() : null);
    loopNo.setValue(dto.getLoopNo() != null ? dto.getLoopNo() : null);
    memo.setValue(dto.getMemo() != null ? dto.getMemo() : null);
  }

  @UiHandler("save")
  public void onSave(SelectEvent se) {
    if (!checkField()) {
      Info.display(
          messages.pdConfigSettingViewer_info_fieldEmpty(),
          messages.pdConfigSettingViewer_info_inputAgain());
      return;
    }
    PdConfigDTO dto = getPdConfigData();
    mask(messages.pdConfigSettingViewer_info_adjusting());
    presenter.checkPdConfig(deviceName.getValue(), "save", dto);
    cleanField();
  }

  @UiHandler("update")
  public void onUpdate(SelectEvent se) {
    if (!checkField()) {
      Info.display(
          messages.pdConfigSettingViewer_info_fieldEmpty(),
          messages.pdConfigSettingViewer_info_inputAgain());
      return;
    }
    PdConfigDTO dto = getPdConfigData();
    mask(messages.pdConfigSettingViewer_info_adjusting());
    presenter.checkPdConfig(deviceName.getValue(), "update", dto);
    cleanField();
  }

  @UiHandler("delete")
  public void onDelete(SelectEvent se) {
    if (deviceName.getCurrentValue() == null) {
      Info.display(
          messages.pdConfigSettingViewer_info_deviceNameEmpty(),
          messages.pdConfigSettingViewer_info_selectDeviceAgain());
      return;
    }
    if (listStore.findModelWithKey(deviceName.getCurrentValue()) == null) {
      Info.display("無此設備", messages.pdConfigSettingViewer_info_selectDeviceAgain());
      return;
    }
    mask(messages.pdConfigSettingViewer_info_adjusting());
    presenter.checkRemovedPdConfig(deviceName.getValue());
  }

  public void setDirectionData() {
    directionStore.clear();
    DirectionDTO directionN = new DirectionDTO();
    directionN.setId(messages.direction_north());
    directionN.setDirection(Direction.N);
    directionStore.add(directionN);
    DirectionDTO directionS = new DirectionDTO();
    directionS.setId(messages.direction_south());
    directionS.setDirection(Direction.S);
    directionStore.add(directionS);
    DirectionDTO directionW = new DirectionDTO();
    directionW.setId(messages.direction_west());
    directionW.setDirection(Direction.N);
    directionStore.add(directionW);
    DirectionDTO directionE = new DirectionDTO();
    directionE.setId(messages.direction_east());
    directionE.setDirection(Direction.N);
    directionStore.add(directionE);
  }

  private Boolean checkField() {
    if (deviceName.getCurrentValue() == null
        || displayName.getCurrentValue() == null
        || location.getCurrentValue() == null
        || line.getCurrentValue() == null
        || direction.getCurrentValue() == null
        || kilometer.getCurrentValue() == null
        || meter.getCurrentValue() == null
        || section.getCurrentValue() == null
        || ip.getCurrentValue() == null
        || port.getCurrentValue() == null
        || enable.getCurrentValue() == null) {
      return false;
    } else {
      return true;
    }
  }

  private void initComboBox() {
    enable =
        new SimpleComboBox<String>(
            new LabelProvider<String>() {
              @Override
              public String getLabel(String item) {
                return item;
              }
            });
  }

  public void fillRoadLine(List<RoadLineDTO> list) {
    lineStore.clear();
    lineStore.addAll(list);
  }

  public void fillLocation(List<LocationDTO> list) {
    locationStore.clear();
    locationStore.addAll(list);
  }

  public void fillSection(List<RoadSectionDTO> list) {
    sectionStore.clear();
    sectionStore.addAll(list);
  }

  public void refillGrid() {
    listStore.clear();
    presenter.retrievePdConfigDTO();
  }

  public void checkData(Boolean result) {
    unmask();
    check = result;
  }

  public void showExistInfo() {
    unmask();
    Info.display(
        messages.pdConfigSettingViewer_info_deviceExist(),
        messages.pdConfigSettingViewer_info_inputAgain());
  }

  public void confirmDialog(String deviceName) {
    if (confirmDialog == null) {
      initConfirmDialog(deviceName);
    }
    confirmDialog.show();
  }

  private void initConfirmDialog(String deviceName) {
    confirmDialog = new Dialog();
    confirmDialog.setHeading(messages.pdConfigSettingViewer_confirmDeleteDevice());
    confirmDialog.setModal(true);
    confirmDialog.setWidth(260);
    confirmDialog.setHeight(100);
    TextButton confirmButton = new TextButton();
    confirmButton.setText(messages.pdConfigSettingViewer_confirm());
    confirmButton.addSelectHandler(
        new SelectHandler() {
          @Override
          public void onSelect(SelectEvent event) {
            presenter.deletePdConfig(deviceName);
            cleanField();
            confirmDialog.hide();
          }
        });
    TextButton cancelButton = new TextButton();
    cancelButton.setText(messages.pdConfigSettingViewer_cancel());
    cancelButton.addSelectHandler(
        new SelectHandler() {
          @Override
          public void onSelect(SelectEvent event) {
            unmask();
            confirmDialog.hide();
          }
        });
    confirmDialog.getButtonBar().clear();
    confirmDialog.getButtonBar().add(confirmButton);
    confirmDialog.getButtonBar().add(cancelButton);
  }

  public PdConfigDTO getPdConfigData() {
    PdConfigDTO dto = new PdConfigDTO();
    dto.setDeviceName(deviceName.getValue());
    dto.setDisplayName(displayName.getValue());
    dto.setLocationNo(String.valueOf(location.getCurrentValue().getId()));
    dto.setLocation(location.getCurrentValue().getLocName());
    dto.setLineId(line.getCurrentValue().getLineId());
    dto.setLineName(line.getCurrentValue().getLineName());
    dto.setDirection(direction.getCurrentValue().getDirection());
    dto.setMilepost(kilometer.getCurrentValue() * 1000 + meter.getCurrentValue());
    dto.setSectionId(section.getCurrentValue().getSectionId());
    dto.setSectionName(section.getCurrentValue().getSectionName());
    dto.setMeterNo(meterNo.getValue());
    dto.setArea(area.getValue());
    dto.setPhone(phone.getValue());
    dto.setIp(ip.getValue());
    dto.setPort(port.getValue());
    dto.setLongitude(longitude.getValue());
    dto.setLatitude(latitude.getValue());
    dto.setLoopNo(loopNo.getValue());
    dto.setMemo(memo.getValue());
    dto.setEnable(
        enable.getCurrentValue() == messages.pdConfigSettingViewer_enable_true() ? true : false);
    return dto;
  }

  public void addPdConfig(List<PdConfigDTO> list) {
    unmask();
    listStore.addAll(list);
  }

  public void cleanField() {
    deviceName.clear();
    displayName.clear();
    kilometer.clear();
    meterNo.clear();
    area.clear();
    phone.clear();
    ip.clear();
    port.clear();
    longitude.clear();
    latitude.clear();
    loopNo.clear();
    memo.clear();
    direction.clear();
    location.clear();
    line.clear();
    section.clear();
    enable.clear();
    meter.clear();
  }

  public void updatePdConfig() {
    clientFactory
        .getEventBus()
        .fireEvent(new PdConfigSettingViewerEvent(PdConfigSettingViewerEvent.Action.UPDATE));
  }

  interface PdConfigPropertyAccess extends PropertyAccess<PdConfigDTO> {
    ModelKeyProvider<PdConfigDTO> deviceName();

    ValueProvider<PdConfigDTO, String> displayName();

    ValueProvider<PdConfigDTO, String> location();

    ValueProvider<PdConfigDTO, String> lineName();

    ValueProvider<PdConfigDTO, Direction> direction();

    ValueProvider<PdConfigDTO, Integer> milepost();

    ValueProvider<PdConfigDTO, String> sectionName();

    ValueProvider<PdConfigDTO, String> meterNo();

    ValueProvider<PdConfigDTO, String> area();

    ValueProvider<PdConfigDTO, String> phone();

    ValueProvider<PdConfigDTO, String> ip();

    ValueProvider<PdConfigDTO, Integer> port();

    ValueProvider<PdConfigDTO, Double> longitude();

    ValueProvider<PdConfigDTO, Double> latitude();

    ValueProvider<PdConfigDTO, Integer> loopNo();

    ValueProvider<PdConfigDTO, String> memo();
  }
}
