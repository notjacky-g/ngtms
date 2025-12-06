/*
 * © HwaCom Systems Inc. 2015
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.cam.view.road;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Widget;
import com.hwacom.ngtms.c.shared.AreaType;
import com.hwacom.ngtms.c.shared.Direction;
import com.hwacom.ngtms.c.shared.DivisionType;
import com.hwacom.ngtms.c.shared.RampType;
import com.hwacom.ngtms.c.shared.dto.DeviceConfigDTO;
import com.hwacom.ngtms.c.shared.dto.DeviceTypeDTO;
import com.hwacom.ngtms.c.shared.dto.RampVdConfigDTO;
import com.hwacom.ngtms.c.shared.dto.RampVdTypeDTO;
import com.hwacom.ngtms.c.shared.dto.RingRoadConfigDTO;
import com.hwacom.ngtms.c.shared.dto.RingRoadVdDTO;
import com.hwacom.ngtms.c.shared.dto.RoadDivisionDTO;
import com.hwacom.ngtms.c.shared.dto.RoadLineDTO;
import com.hwacom.ngtms.cam.event.road.RoadConfigTreeFireSelectedEvent;
import com.hwacom.ngtms.cam.event.road.RoadConfigTreeFireSelectedEvent.RoadConfigTreeFireSelectedEventHandler;
import com.hwacom.ngtms.cam.event.road.RoadDivisionUpdatedEvent;
import com.hwacom.ngtms.cam.presenter.road.SystemInterchangePresenter;
import com.hwacom.ngtms.cam.util.CommonStringConverter;
import com.hwacom.ngtms.cam.view.Messages;
import com.hwacom.ngtms.cam.vo.RampVdConfigProperties;
import com.hwacom.ngtms.cam.vo.RingRoadConfigProperties;
import com.hwacom.ngtms.cam.vo.RoadDivisionProperties;
import com.hwacom.ngtms.cam.vo.RoadLineProperties;
import com.hwacom.ngtms.hcce.am.factory.ClientFactory;
import com.sencha.gxt.data.shared.LabelProvider;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.SortDir;
import com.sencha.gxt.data.shared.Store;
import com.sencha.gxt.data.shared.Store.StoreFilter;
import com.sencha.gxt.data.shared.Store.StoreSortInfo;
import com.sencha.gxt.widget.core.client.Composite;
import com.sencha.gxt.widget.core.client.Dialog.PredefinedButton;
import com.sencha.gxt.widget.core.client.box.ConfirmMessageBox;
import com.sencha.gxt.widget.core.client.event.DialogHideEvent;
import com.sencha.gxt.widget.core.client.event.DialogHideEvent.DialogHideHandler;
import com.sencha.gxt.widget.core.client.event.RowClickEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.form.IntegerField;
import com.sencha.gxt.widget.core.client.form.SimpleComboBox;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.grid.GridView;
import com.sencha.gxt.widget.core.client.info.Info;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SystemInterchangeViewer extends Composite {

  private static SystemInterchangeViewerUiBinder uiBinder =
      GWT.create(SystemInterchangeViewerUiBinder.class);

  interface SystemInterchangeViewerUiBinder extends UiBinder<Widget, SystemInterchangeViewer> {}

  private static final RoadDivisionProperties props = GWT.create(RoadDivisionProperties.class);
  private static final RoadLineProperties roadLineProps = GWT.create(RoadLineProperties.class);
  private static final RampVdConfigProperties rampVdConfigProps =
      GWT.create(RampVdConfigProperties.class);
  private static final RingRoadConfigProperties ringRoadConfigProps =
      GWT.create(RingRoadConfigProperties.class);
  private final ClientFactory clientFactory = GWT.create(ClientFactory.class);
  private static final Messages messages = GWT.create(Messages.class);
  private SystemInterchangePresenter presenter;
  private final HandlerRegistration roadConfigTreeFireSelectedEventRegistration;
  private static final DivisionType DIVISION_TYPE = DivisionType.C;
  private RoadDivisionRingComboFilter roadDivisionRingComboFilter =
      new RoadDivisionRingComboFilter();
  private DirectionFilter directionRingfilter = new DirectionFilter();
  private DirectionFilter startDirectionRingfilter = new DirectionFilter();

  @SuppressWarnings("unused")
  private List<RoadLineDTO> roadLineList;

  private List<RoadDivisionDTO> roadDivisionList = new ArrayList<>();
  private KeyValue yesKeyValue = new KeyValue("true", messages.yes());
  private KeyValue noKeyValue = new KeyValue("false", messages.no());
  private KeyValue zeroKeyValue = new KeyValue("0", messages.no());
  private KeyValue oneKeyValue = new KeyValue("1", messages.yes());
  private RoadDivisionFilter roadDivisionFilter = new RoadDivisionFilter();
  private RoadDivisionDTO nowRoadDivision;
  private RampVdConfigDTO nowRampVdConfig;
  private RingRoadConfigDTO nowRingRoadConfig;
  private static List<DeviceConfigDTO> deviceConfigDTOList;

  @UiField(provided = true)
  ColumnModel<RoadDivisionDTO> cm;

  @UiField(provided = true)
  ListStore<RoadDivisionDTO> store;

  @UiField GridView<RoadDivisionDTO> view;
  @UiField Grid<RoadDivisionDTO> grid;
  @UiField TextField divisionId;
  @UiField TextField divisionName;
  @UiField IntegerField mileage;

  @UiField(provided = true)
  SimpleComboBox<RoadLineDTO> lineNameCombo =
      new SimpleComboBox<RoadLineDTO>(
          new LabelProvider<RoadLineDTO>() {
            @Override
            public String getLabel(RoadLineDTO item) {
              return item.getLineName();
            }
          });

  @UiField(provided = true)
  SimpleComboBox<KeyValue> boundaryCombo =
      new SimpleComboBox<KeyValue>(
          new LabelProvider<KeyValue>() {
            @Override
            public String getLabel(KeyValue item) {
              return item.getValue();
            }
          });

  @UiField(provided = true)
  SimpleComboBox<KeyValue> travelTimeVisibleCombo =
      new SimpleComboBox<KeyValue>(
          new LabelProvider<KeyValue>() {
            @Override
            public String getLabel(KeyValue item) {
              return item.getValue();
            }
          });

  @UiField(provided = true)
  ColumnModel<RampVdConfigDTO> rampCm;

  @UiField(provided = true)
  ListStore<RampVdConfigDTO> rampStore;

  @UiField GridView<RampVdConfigDTO> rampView;
  @UiField Grid<RampVdConfigDTO> rampGrid;

  @UiField(provided = true)
  SimpleComboBox<RampType> rampTypeCombo =
      new SimpleComboBox<RampType>(
          new LabelProvider<RampType>() {
            @Override
            public String getLabel(RampType item) {
              return CommonStringConverter.getRampTypeName(item);
            }
          });

  @UiField(provided = true)
  SimpleComboBox<Direction> directionCombo =
      new SimpleComboBox<Direction>(
          new LabelProvider<Direction>() {
            @Override
            public String getLabel(Direction item) {
              return CommonStringConverter.getDirectionName(item);
            }
          });

  @UiField(provided = true)
  SimpleComboBox<RampVdTypeDTO> rampVdTypeCombo =
      new SimpleComboBox<RampVdTypeDTO>(
          new LabelProvider<RampVdTypeDTO>() {
            @Override
            public String getLabel(RampVdTypeDTO item) {
              return item.getName();
            }
          });

  @UiField(provided = true)
  SimpleComboBox<KeyValue> exceptionCombo =
      new SimpleComboBox<KeyValue>(
          new LabelProvider<KeyValue>() {
            @Override
            public String getLabel(KeyValue item) {
              return item.getValue();
            }
          });

  @UiField TextField rampTypeDesc;
  @UiField TextField vd1;
  @UiField TextField vd2;
  @UiField TextField vd3;

  @UiField(provided = true)
  ColumnModel<RingRoadConfigDTO> ringCm;

  @UiField(provided = true)
  ListStore<RingRoadConfigDTO> ringStore;

  @UiField GridView<RingRoadConfigDTO> ringView;
  @UiField Grid<RingRoadConfigDTO> ringGrid;
  @UiField TextField startDescription;
  @UiField TextField endDescription;
  @UiField TextField exitVd;
  @UiField IntegerField ringLength;
  @UiField IntegerField speedLimit;
  @UiField IntegerField freeTravelTime;
  @UiField TextField etagSectionId;
  @UiField TextField ringRoadVd1;
  @UiField TextField ringRoadVd2;
  @UiField TextField ringRoadVd3;
  @UiField TextField ringRoadVd4;
  @UiField TextField ringRoadVd5;
  @UiField TextField ringRoadVd6;
  @UiField IntegerField ringRoadVd1Weigth;
  @UiField IntegerField ringRoadVd2Weigth;
  @UiField IntegerField ringRoadVd3Weigth;
  @UiField IntegerField ringRoadVd4Weigth;
  @UiField IntegerField ringRoadVd5Weigth;
  @UiField IntegerField ringRoadVd6Weigth;

  @UiField(provided = true)
  SimpleComboBox<Direction> startDirectionRingCombo =
      new SimpleComboBox<Direction>(
          new LabelProvider<Direction>() {
            @Override
            public String getLabel(Direction item) {
              return CommonStringConverter.getDirectionName(item);
            }
          });

  @UiField(provided = true)
  SimpleComboBox<RoadLineDTO> lineNameRingCombo =
      new SimpleComboBox<RoadLineDTO>(
          new LabelProvider<RoadLineDTO>() {

            @Override
            public String getLabel(RoadLineDTO item) {
              return item.getLineName();
            }
          });

  @UiField(provided = true)
  SimpleComboBox<RoadDivisionDTO> roadDivisionRingCombo =
      new SimpleComboBox<RoadDivisionDTO>(
          new LabelProvider<RoadDivisionDTO>() {
            @Override
            public String getLabel(RoadDivisionDTO item) {
              return item.getDivisionName();
            }
          });

  @UiField(provided = true)
  SimpleComboBox<Direction> directionRingCombo =
      new SimpleComboBox<Direction>(
          new LabelProvider<Direction>() {
            @Override
            public String getLabel(Direction item) {
              return CommonStringConverter.getDirectionName(item);
            }
          });

  public SystemInterchangeViewer() {
    store = new ListStore<RoadDivisionDTO>(props.key());
    rampStore = new ListStore<RampVdConfigDTO>(rampVdConfigProps.key());
    ringStore = new ListStore<RingRoadConfigDTO>(ringRoadConfigProps.key());
    initColumnModel();
    initRampColumnModel();
    initRingColumnModel();
    initWidget(uiBinder.createAndBindUi(this));
    presenter = new SystemInterchangePresenter(this);
    store.addSortInfo(new StoreSortInfo<RoadDivisionDTO>(props.mileage(), SortDir.ASC));
    store.addFilter(roadDivisionFilter);
    roadDivisionFilter.setShowAll(true);
    store.setEnableFilters(true);
    rampStore.addSortInfo(new StoreSortInfo<RampVdConfigDTO>(rampVdConfigProps.id(), SortDir.ASC));
    ringStore.addSortInfo(
        new StoreSortInfo<RingRoadConfigDTO>(ringRoadConfigProps.id(), SortDir.ASC));
    initComboBox();
    roadConfigTreeFireSelectedEventRegistration =
        clientFactory
            .getEventBus()
            .addHandler(
                RoadConfigTreeFireSelectedEvent.TYPE,
                new RoadConfigTreeFireSelectedEventHandlerImpl());
    roadDivisionRingCombo.getStore().addFilter(roadDivisionRingComboFilter);
    roadDivisionRingCombo.getStore().setEnableFilters(true);
    roadDivisionRingCombo
        .getStore()
        .addSortInfo(new StoreSortInfo<RoadDivisionDTO>(props.mileage(), SortDir.ASC));
    directionRingCombo.getStore().addFilter(directionRingfilter);
    directionRingCombo.getStore().setEnableFilters(true);
    startDirectionRingCombo.getStore().addFilter(startDirectionRingfilter);
    startDirectionRingCombo.getStore().setEnableFilters(true);
    fetchVdDeviceConfig();
  }

  private void fetchVdDeviceConfig() {
    List<DeviceTypeDTO> deviceTypes = new ArrayList<>();
    DeviceTypeDTO dto = new DeviceTypeDTO();
    dto.setId("VD");
    dto.setCategory("DGS");
    dto.setDescription("車輛偵測器 ");
    deviceTypes.add(dto);
    presenter.getDeviceConfig(deviceTypes);
  }

  public String getDisplayName(String deviceName) {
    if (deviceConfigDTOList != null) {
      for (DeviceConfigDTO dc : deviceConfigDTOList) {
        if (dc.getDeviceName().equals(deviceName)) {
          return dc.getDisplayName();
        }
      }
    }
    return null;
  }

  public String getDeviceNameByDisplayName(String displayName) {
    if (deviceConfigDTOList != null) {
      for (DeviceConfigDTO dc : deviceConfigDTOList) {
        if (dc.getDisplayName().equals(displayName)) {
          return dc.getDeviceName();
        }
      }
    }
    return null;
  }

  private void initComboBox() {
    lineNameCombo
        .getStore()
        .addSortInfo(new StoreSortInfo<RoadLineDTO>(roadLineProps.lineId(), SortDir.ASC));
    lineNameRingCombo
        .getStore()
        .addSortInfo(new StoreSortInfo<RoadLineDTO>(roadLineProps.lineId(), SortDir.ASC));
    boundaryCombo.getStore().add(yesKeyValue);
    boundaryCombo.getStore().add(noKeyValue);
    boundaryCombo.setValue(yesKeyValue);
    travelTimeVisibleCombo.getStore().add(yesKeyValue);
    travelTimeVisibleCombo.getStore().add(noKeyValue);
    travelTimeVisibleCombo.setValue(yesKeyValue);

    rampTypeCombo.add(Arrays.asList(RampType.values()));
    rampTypeCombo.setValue(RampType.O);
    directionCombo.add(Arrays.asList(Direction.values()));
    directionCombo.setValue(Direction.N);
    exceptionCombo.getStore().add(zeroKeyValue);
    exceptionCombo.getStore().add(oneKeyValue);
    exceptionCombo.setValue(zeroKeyValue);

    startDirectionRingCombo.add(Arrays.asList(Direction.values()));
    startDirectionRingCombo.setValue(Direction.N);
    directionRingCombo.add(Arrays.asList(Direction.values()));
    directionRingCombo.setValue(Direction.N);

    lineNameRingCombo.addSelectionHandler(
        new SelectionHandler<RoadLineDTO>() {

          @Override
          public void onSelection(SelectionEvent<RoadLineDTO> event) {
            GWT.log("on lineNameRingCombo Selection =>" + event.getSelectedItem().toString());
            roadDivisionRingCombo.getStore().getFilters().clear();
            roadDivisionRingComboFilter.setCondition(event.getSelectedItem().getLineId());
            roadDivisionRingCombo.getStore().addFilter(roadDivisionRingComboFilter);
            roadDivisionRingCombo.getStore().setEnableFilters(true);
            if (roadDivisionRingCombo.getStore().size() > 0) {
              roadDivisionRingCombo.setValue(roadDivisionRingCombo.getStore().get(0));
            }
            directionRingCombo.getStore().getFilters().clear();
            directionRingfilter.setCondition(event.getSelectedItem().getDirection().toString());
            directionRingCombo.getStore().addFilter(directionRingfilter);
            directionRingCombo.getStore().setEnableFilters(true);
            if (directionRingCombo.getStore().size() > 0) {
              directionRingCombo.setValue(directionRingCombo.getStore().get(0));
            }
          }
        });
  }

  public void initRoadLineData(List<RoadLineDTO> roadLineList) {
    this.roadLineList = roadLineList;
    if (roadLineList != null) {
      lineNameCombo.getStore().clear();
      lineNameCombo.getStore().addAll(roadLineList);
      if (lineNameCombo.getStore().size() > 0) {
        lineNameCombo.setValue(lineNameCombo.getStore().get(0));
      }
      lineNameRingCombo.getStore().clear();
      lineNameRingCombo.getStore().addAll(roadLineList);
      if (lineNameRingCombo.getStore().size() > 0) {
        lineNameRingCombo.setValue(lineNameRingCombo.getStore().get(0));
      }
    }
  }

  public void initRoadDivisionData(List<RoadDivisionDTO> roadDivisionList) {
    unmask();
    this.roadDivisionList.clear();
    for (RoadDivisionDTO dto : roadDivisionList) {
      if (DIVISION_TYPE == dto.getDivisionType()) {
        this.roadDivisionList.add(dto);
      }
    }
    store.clear();
    store.addAll(this.roadDivisionList);
    roadDivisionRingCombo.getStore().clear();
    if (this.roadDivisionList.size() > 0) {
      roadDivisionRingCombo.getStore().addAll(this.roadDivisionList);
      roadDivisionRingCombo.getStore().getFilters().clear();
      GWT.log("lineNameRingCombo.getStore().size()=" + lineNameRingCombo.getStore().size());
      if (lineNameRingCombo.getStore().size() > 0) {
        roadDivisionRingComboFilter.setCondition(lineNameRingCombo.getStore().get(0).getLineId());
        roadDivisionRingCombo.getStore().addFilter(roadDivisionRingComboFilter);
        roadDivisionRingCombo.getStore().setEnableFilters(true);
      }
      if (roadDivisionRingCombo.getStore().size() > 0) {
        roadDivisionRingCombo.setValue(roadDivisionRingCombo.getStore().get(0));
      }

      roadDivisionRingCombo.setValue(roadDivisionRingCombo.getStore().get(0));
    }
  }

  @UiHandler("grid")
  public void rowClick(RowClickEvent event) {
    RoadDivisionDTO dto = store.get(event.getRowIndex());
    if (dto != null) {
      GWT.log(dto.toString());
      fillFields(dto);
      mask(messages.waiting());
      presenter.getRampVdConfigs(dto.getDivisionId());
      presenter.getRingRoadConfigs(dto.getDivisionId());
    }
  }

  private void fillGrid(RoadLineDTO roadLineDto) {
    GWT.log("SystemInterchangeViewer fillGrid roadLineDto = " + roadLineDto);
    store.getFilters().clear();
    if (roadLineDto == null) return;
    if ("root".equals(roadLineDto.getLineId())) {
      roadDivisionFilter.setShowAll(true);
    } else {
      roadDivisionFilter.setShowAll(false);
      roadDivisionFilter.setCondition(roadLineDto.getLineId());
    }
    store.addFilter(roadDivisionFilter);
    store.setEnableFilters(true);
    if (store.size() > 0) {
      grid.getSelectionModel().select(store.get(0), false);
      fillFields(store.get(0));
      presenter.getRampVdConfigs(store.get(0).getDivisionId());
      presenter.getRingRoadConfigs(store.get(0).getDivisionId());
      startDirectionRingCombo.getStore().getFilters().clear();
      startDirectionRingfilter.setCondition(roadLineDto.getDirection().toString());
      startDirectionRingCombo.getStore().addFilter(startDirectionRingfilter);
      startDirectionRingCombo.getStore().setEnableFilters(true);
      if (startDirectionRingCombo.getStore().size() > 0) {
        startDirectionRingCombo.setValue(startDirectionRingCombo.getStore().get(0));
      }

    } else {
      emptyFields();
      emptyRampFields();
      emptyRingFields();
    }
  }

  @UiHandler("saveButton")
  public void saveButton(SelectEvent event) {
    GWT.log("saveButton Click!");
    if (nowRoadDivision == null) {
      Info.display(messages.info(), messages.info_noSelectedOneItem());
      return;
    }
    if (!divisionId.validate()) {
      Info.display(
          messages.info(), messages.roadDivision_divisionId() + " " + messages.info_notEmpty());
      return;
    }
    if (!divisionName.validate()) {
      Info.display(
          messages.info(), messages.roadDivision_divisionName() + " " + messages.info_notEmpty());
      return;
    }
    final ConfirmMessageBox box =
        new ConfirmMessageBox(messages.message(), messages.message_itemSaveConfirm());
    box.addDialogHideHandler(
        new DialogHideHandler() {
          @Override
          public void onDialogHide(DialogHideEvent event) {
            if (event.getHideButton() == PredefinedButton.YES) {
              RoadDivisionDTO storeDto = store.findModelWithKey(divisionId.getValue().trim());
              if (storeDto != null) {
                RoadDivisionDTO dto = fieldsToDto();
                GWT.log("Save dto = " + dto);
                presenter.saveItem(dto);
              } else {
                Info.display(messages.message(), messages.message_itemNoExisted());
              }
            }
          }
        });
    box.show();
  }

  @UiHandler("addButton")
  public void addButton(SelectEvent event) {
    GWT.log("addButton Click!");
    if (!divisionId.validate()) {
      Info.display(messages.info(), messages.roadLine_lineId() + " " + messages.info_notEmpty());
      return;
    }
    if (!divisionName.validate()) {
      Info.display(messages.info(), messages.roadLine_lineName() + " " + messages.info_notEmpty());
      return;
    }
    final ConfirmMessageBox box =
        new ConfirmMessageBox(messages.message(), messages.message_itemAddConfirm());
    box.addDialogHideHandler(
        new DialogHideHandler() {
          @Override
          public void onDialogHide(DialogHideEvent event) {
            if (event.getHideButton() == PredefinedButton.YES) {
              RoadDivisionDTO storeDto = store.findModelWithKey(divisionId.getValue().trim());
              if (storeDto == null) {
                RoadDivisionDTO dto = fieldsToDto();
                GWT.log("Add dto = " + dto);
                presenter.addItem(dto);
              } else {
                Info.display(messages.message(), messages.message_itemExisted());
              }
            }
          }
        });
    box.show();
  }

  @UiHandler("deleteButton")
  public void deleteButton(SelectEvent event) {
    GWT.log("deleteButton Click!");
    if (!divisionId.validate()) {
      Info.display(messages.info(), messages.roadLine_lineId() + " " + messages.info_notEmpty());
      return;
    }
    if (!divisionName.validate()) {
      Info.display(messages.info(), messages.roadLine_lineName() + " " + messages.info_notEmpty());
      return;
    }
    final ConfirmMessageBox box =
        new ConfirmMessageBox(messages.message(), messages.message_itemDeleteConfirm());
    box.addDialogHideHandler(
        new DialogHideHandler() {
          @Override
          public void onDialogHide(DialogHideEvent event) {
            if (event.getHideButton() == PredefinedButton.YES) {
              RoadDivisionDTO storeDto = store.findModelWithKey(divisionId.getValue().trim());
              if (storeDto != null) {
                RoadDivisionDTO dto = fieldsToDto();
                GWT.log("Delete dto = " + dto);
                presenter.removeItem(storeDto.getDivisionId());
              } else {
                Info.display(messages.message(), messages.message_itemNoExisted());
              }
            }
          }
        });
    box.show();
  }

  private void emptyFields() {
    divisionId.setValue(null);
    divisionName.setValue(null);
    mileage.setValue(null);
    if (lineNameCombo.getStore().size() > 0) {
      lineNameCombo.setValue(lineNameCombo.getStore().get(0));
    } else {
      lineNameCombo.setValue(null);
    }
    boundaryCombo.setValue(yesKeyValue);
    travelTimeVisibleCombo.setValue(yesKeyValue);
  }

  private void fillFields(RoadDivisionDTO dto) {
    nowRoadDivision = dto;
    divisionId.setValue(dto.getDivisionId());
    divisionName.setValue(dto.getDivisionName());
    mileage.setValue(dto.getMileage());
    RoadLineDTO roadLine = lineNameCombo.getStore().findModelWithKey(dto.getLineId());
    lineNameCombo.setValue(roadLine);
    if (dto.getBoundary()) boundaryCombo.setValue(yesKeyValue);
    else boundaryCombo.setValue(noKeyValue);
    if (dto.getTravelTimeVisible()) travelTimeVisibleCombo.setValue(yesKeyValue);
    else travelTimeVisibleCombo.setValue(noKeyValue);
  }

  private RoadDivisionDTO fieldsToDto() {
    RoadDivisionDTO dto = new RoadDivisionDTO();
    dto.setDivisionId(divisionId.getValue());
    dto.setDivisionName(divisionName.getValue());
    dto.setMileage(mileage.getValue());
    if (nowRoadDivision != null && nowRoadDivision.getAreaType() != null) {
      dto.setAreaType(nowRoadDivision.getAreaType());
    } else {
      // 若原先沒有 areaType 只好填入預設值
      dto.setAreaType(AreaType.C);
    }
    dto.setDivisionType(DIVISION_TYPE);
    dto.setLineId(lineNameCombo.getValue().getLineId());
    dto.setBoundary(Boolean.valueOf(boundaryCombo.getValue().getKey()));
    dto.setTravelTimeVisible(Boolean.valueOf(travelTimeVisibleCombo.getValue().getKey()));
    return dto;
  }

  @UiHandler("rampGrid")
  public void rampGridRowClick(RowClickEvent event) {
    RampVdConfigDTO dto = rampStore.get(event.getRowIndex());
    if (dto != null) {
      GWT.log(dto.toString());
      fillRampFields(dto);
      nowRampVdConfig = dto;
    }
  }

  @UiHandler("saveRampButton")
  public void saveRampButton(SelectEvent event) {
    GWT.log("saveRampButton Click!");
    if (nowRoadDivision == null) {
      Info.display(messages.info(), messages.info_noSelectedOneSystemInterchange());
      return;
    }
    if (nowRampVdConfig == null) {
      Info.display(messages.info(), messages.info_noSelectedOneRamp());
      return;
    }
    if (rampTypeCombo.getValue() == null) {
      Info.display(
          messages.info(), messages.rampVdConfig_rampType() + " " + messages.info_notEmpty());
      return;
    }
    if (directionCombo.getValue() == null) {
      Info.display(
          messages.info(), messages.rampVdConfig_direction() + " " + messages.info_notEmpty());
      return;
    }
    if (rampVdTypeCombo.getValue() == null) {
      Info.display(
          messages.info(), messages.rampVdConfig_rampVdType() + " " + messages.info_notEmpty());
      return;
    }
    if (exceptionCombo.getValue() == null) {
      Info.display(
          messages.info(), messages.rampVdConfig_exception() + " " + messages.info_notEmpty());
      return;
    }
    final ConfirmMessageBox box =
        new ConfirmMessageBox(messages.message(), messages.message_itemSaveConfirm());
    box.addDialogHideHandler(
        new DialogHideHandler() {
          @Override
          public void onDialogHide(DialogHideEvent event) {
            if (event.getHideButton() == PredefinedButton.YES) {
              RampVdConfigDTO storeDto = rampStore.findModelWithKey("" + nowRampVdConfig.getId());
              if (storeDto != null) {
                RampVdConfigDTO dto = rampFieldsToRampDto();
                GWT.log("Save dto = " + dto);
                presenter.saveRampItem(dto);
              } else {
                Info.display(messages.message(), messages.message_itemNoExisted());
              }
            }
          }
        });
    box.show();
  }

  @UiHandler("addRampButton")
  public void addRampButton(SelectEvent event) {
    GWT.log("addRampButton Click!");
    if (nowRoadDivision == null) {
      Info.display(messages.info(), messages.info_noSelectedOneSystemInterchange());
      return;
    }
    if (rampTypeCombo.getValue() == null) {
      Info.display(
          messages.info(), messages.rampVdConfig_rampType() + " " + messages.info_notEmpty());
      return;
    }
    if (directionCombo.getValue() == null) {
      Info.display(
          messages.info(), messages.rampVdConfig_direction() + " " + messages.info_notEmpty());
      return;
    }
    if (rampVdTypeCombo.getValue() == null) {
      Info.display(
          messages.info(), messages.rampVdConfig_rampVdType() + " " + messages.info_notEmpty());
      return;
    }
    if (exceptionCombo.getValue() == null) {
      Info.display(
          messages.info(), messages.rampVdConfig_exception() + " " + messages.info_notEmpty());
      return;
    }
    final ConfirmMessageBox box =
        new ConfirmMessageBox(messages.message(), messages.message_itemAddConfirm());
    box.addDialogHideHandler(
        new DialogHideHandler() {
          @Override
          public void onDialogHide(DialogHideEvent event) {
            if (event.getHideButton() == PredefinedButton.YES) {
              // RampVdConfigDTO storeDto =
              // rampStore.findModelWithKey(""+nowRampVdConfig.getId());
              // if (storeDto == null) {
              RampVdConfigDTO dto = rampFieldsToRampDto();
              GWT.log("Add dto = " + dto);
              presenter.addRampItem(dto);
              // } else {
              // Info.display(messages.message(),
              // messages.message_itemExisted());
              // }
            }
          }
        });
    box.show();
  }

  @UiHandler("deleteRampButton")
  public void deleteRampButton(SelectEvent event) {
    GWT.log("deleteRampButton Click!");
    if (nowRampVdConfig == null) {
      Info.display(messages.info(), messages.info_noSelectedOneSystemInterchange());
      return;
    }
    final ConfirmMessageBox box =
        new ConfirmMessageBox(messages.message(), messages.message_itemDeleteConfirm());
    box.addDialogHideHandler(
        new DialogHideHandler() {
          @Override
          public void onDialogHide(DialogHideEvent event) {
            if (event.getHideButton() == PredefinedButton.YES) {
              RampVdConfigDTO storeDto = rampStore.findModelWithKey("" + nowRampVdConfig.getId());
              if (storeDto != null) {
                RampVdConfigDTO dto = rampFieldsToRampDto();
                GWT.log("Delete dto = " + dto);
                presenter.removeRampItem(dto.getId());
              } else {
                Info.display(messages.message(), messages.message_itemNoExisted());
              }
            }
          }
        });
    box.show();
  }

  private void emptyRampFields() {
    if (rampTypeCombo.getStore().size() > 0) {
      rampTypeCombo.setValue(rampTypeCombo.getStore().get(0));
    } else {
      rampTypeCombo.setValue(null);
    }
    directionCombo.setValue(Direction.N);
    if (rampVdTypeCombo.getStore().size() > 0) {
      rampVdTypeCombo.setValue(rampVdTypeCombo.getStore().get(0));
    } else {
      rampVdTypeCombo.setValue(null);
    }
    exceptionCombo.setValue(zeroKeyValue);
    rampTypeDesc.setValue(null);
    vd1.setValue(null);
    vd2.setValue(null);
    vd3.setValue(null);
  }

  private void fillRampFields(RampVdConfigDTO dto) {
    rampTypeCombo.setValue(RampType.valueOf(dto.getRampTypeStr()));
    directionCombo.setValue(Direction.valueOf(dto.getDirectionStr()));
    RampVdTypeDTO rampVdType =
        rampVdTypeCombo.getStore().findModelWithKey("" + dto.getRampVdType());
    rampVdTypeCombo.setValue(rampVdType);
    if (dto.getException().intValue() == 1) exceptionCombo.setValue(oneKeyValue);
    else exceptionCombo.setValue(zeroKeyValue);
    rampTypeDesc.setValue(dto.getRampTypeDesc());
    vd1.setValue(dto.getVd1());
    vd2.setValue(dto.getVd2());
    vd3.setValue(dto.getVd3());
  }

  private RampVdConfigDTO rampFieldsToRampDto() {
    RampVdConfigDTO dto = new RampVdConfigDTO();
    if (nowRampVdConfig != null) dto.setId(nowRampVdConfig.getId());
    dto.setDivisionId(nowRoadDivision.getDivisionId());
    dto.setDirectionStr(directionCombo.getValue().toString());
    dto.setRampTypeStr(rampTypeCombo.getValue().toString());
    dto.setRampVdType(rampVdTypeCombo.getValue().getId());
    dto.setRampTypeDesc(rampTypeDesc.getValue());
    dto.setException(Integer.parseInt(exceptionCombo.getValue().getKey()));
    dto.setVd1(vd1.getValue());
    dto.setVd2(vd2.getValue());
    dto.setVd3(vd3.getValue());
    return dto;
  }

  @UiHandler("ringGrid")
  public void ringGridRowClick(RowClickEvent event) {
    RingRoadConfigDTO dto = ringStore.get(event.getRowIndex());
    if (dto != null) {
      GWT.log(dto.toString());
      nowRingRoadConfig = dto;
      fillRingFields(dto);
    }
  }

  public boolean checkRingVd() {
    boolean result = false;
    int vd1Weight = 0;
    int vd2Weight = 0;
    int vd3Weight = 0;
    int vd4Weight = 0;
    int vd5Weight = 0;
    int vd6Weight = 0;
    vd1Weight = ringRoadVd1Weigth.getValue() != null ? ringRoadVd1Weigth.getValue() : 0;
    vd2Weight = ringRoadVd2Weigth.getValue() != null ? ringRoadVd2Weigth.getValue() : 0;
    vd3Weight = ringRoadVd3Weigth.getValue() != null ? ringRoadVd3Weigth.getValue() : 0;
    vd4Weight = ringRoadVd4Weigth.getValue() != null ? ringRoadVd4Weigth.getValue() : 0;
    vd5Weight = ringRoadVd5Weigth.getValue() != null ? ringRoadVd5Weigth.getValue() : 0;
    vd6Weight = ringRoadVd6Weigth.getValue() != null ? ringRoadVd6Weigth.getValue() : 0;
    int totalVdWeight = vd1Weight + vd2Weight + vd3Weight + vd4Weight + vd5Weight + vd6Weight;
    if (totalVdWeight == 100) {
      result = true;
    }
    return result;
  }

  @UiHandler("saveRingButton")
  public void saveRingButton(SelectEvent event) {
    GWT.log("saveRingButton Click!");
    if (!checkRingVd()) {
      Info.display(messages.info(), "環道 VD 合計不是 100，請檢查!");
      return;
    }
    if (nowRoadDivision == null) {
      Info.display(messages.info(), messages.info_noSelectedOneSystemInterchange());
      return;
    }
    if (nowRingRoadConfig == null) {
      Info.display(messages.info(), messages.info_noSelectedOneRing());
      return;
    }
    if (startDescription.getValue() == null) {
      Info.display(
          messages.info(),
          messages.ringRoadConfig_startDescription() + " " + messages.info_notEmpty());
      return;
    }
    if (endDescription.getValue() == null) {
      Info.display(
          messages.info(),
          messages.ringRoadConfig_endDescription() + " " + messages.info_notEmpty());
      return;
    }
    if (lineNameRingCombo.getValue() == null) {
      Info.display(
          messages.info(), messages.ringRoadConfig_endLineId() + " " + messages.info_notEmpty());
      return;
    }
    if (startDirectionRingCombo.getValue() == null) {
      Info.display(
          messages.info(),
          messages.ringRoadConfig_startDirection() + " " + messages.info_notEmpty());
      return;
    }
    if (directionRingCombo.getValue() == null) {
      Info.display(
          messages.info(), messages.ringRoadConfig_endDirection() + " " + messages.info_notEmpty());
      return;
    }
    if (roadDivisionRingCombo.getValue() == null) {
      Info.display(
          messages.info(),
          messages.ringRoadConfig_endDivisionId() + " " + messages.info_notEmpty());
      return;
    }
    if (ringLength.getValue() == null) {
      Info.display(
          messages.info(), messages.ringRoadConfig_length() + " " + messages.info_notEmpty());
      return;
    }
    if (speedLimit.getValue() == null) {
      Info.display(
          messages.info(), messages.ringRoadConfig_speedLimit() + " " + messages.info_notEmpty());
      return;
    }
    if (freeTravelTime.getValue() == null) {
      Info.display(
          messages.info(),
          messages.ringRoadConfig_freeTravelTime() + " " + messages.info_notEmpty());
      return;
    }
    if (exitVd.getValue() != null && !exitVd.getValue().isEmpty()) {
      String deviceName = this.getDeviceNameByDisplayName(exitVd.getValue());
      if (deviceName == null || deviceName.isEmpty()) {
        Info.display(
            messages.info(),
            messages.ringRoadConfig_exitVd() + " " + exitVd.getValue() + " 不存在，請檢查!");
        return;
      }
    }
    if (ringRoadVd1.getValue() != null && !ringRoadVd1.getValue().isEmpty()) {
      String deviceName = this.getDeviceNameByDisplayName(ringRoadVd1.getValue());
      if (deviceName == null || deviceName.isEmpty()) {
        Info.display(
            messages.info(),
            messages.ringRoadConfig_ringRoadVd1() + " " + ringRoadVd1.getValue() + " 不存在，請檢查!");
        return;
      }
    }
    if (ringRoadVd2.getValue() != null && !ringRoadVd2.getValue().isEmpty()) {
      String deviceName = this.getDeviceNameByDisplayName(ringRoadVd2.getValue());
      if (deviceName == null || deviceName.isEmpty()) {
        Info.display(
            messages.info(),
            messages.ringRoadConfig_ringRoadVd2() + " " + ringRoadVd2.getValue() + " 不存在，請檢查!");
        return;
      }
    }
    if (ringRoadVd3.getValue() != null && !ringRoadVd3.getValue().isEmpty()) {
      String deviceName = this.getDeviceNameByDisplayName(ringRoadVd3.getValue());
      if (deviceName == null || deviceName.isEmpty()) {
        Info.display(
            messages.info(),
            messages.ringRoadConfig_ringRoadVd3() + " " + ringRoadVd3.getValue() + " 不存在，請檢查!");
        return;
      }
    }
    if (ringRoadVd4.getValue() != null && !ringRoadVd4.getValue().isEmpty()) {
      String deviceName = this.getDeviceNameByDisplayName(ringRoadVd4.getValue());
      if (deviceName == null || deviceName.isEmpty()) {
        Info.display(
            messages.info(),
            messages.ringRoadConfig_ringRoadVd4() + " " + ringRoadVd4.getValue() + " 不存在，請檢查!");
        return;
      }
    }
    if (ringRoadVd5.getValue() != null && !ringRoadVd5.getValue().isEmpty()) {
      String deviceName = this.getDeviceNameByDisplayName(ringRoadVd5.getValue());
      if (deviceName == null || deviceName.isEmpty()) {
        Info.display(
            messages.info(),
            messages.ringRoadConfig_ringRoadVd5() + " " + ringRoadVd5.getValue() + " 不存在，請檢查!");
        return;
      }
    }
    if (ringRoadVd6.getValue() != null && !ringRoadVd6.getValue().isEmpty()) {
      String deviceName = this.getDeviceNameByDisplayName(ringRoadVd6.getValue());
      if (deviceName == null || deviceName.isEmpty()) {
        Info.display(
            messages.info(),
            messages.ringRoadConfig_ringRoadVd6() + " " + ringRoadVd6.getValue() + " 不存在，請檢查!");
        return;
      }
    }

    int number1 = 0;
    int number2 = 0;
    int number3 = 0;
    int number4 = 0;
    int number5 = 0;
    int number6 = 0;
    if (ringRoadVd1Weigth.getValue() != null) {
      number1 = ringRoadVd1Weigth.getValue();
    }
    if (ringRoadVd2Weigth.getValue() != null) {
      number2 = ringRoadVd2Weigth.getValue();
    }
    if (ringRoadVd3Weigth.getValue() != null) {
      number3 = ringRoadVd3Weigth.getValue();
    }
    if (ringRoadVd4Weigth.getValue() != null) {
      number4 = ringRoadVd4Weigth.getValue();
    }
    if (ringRoadVd5Weigth.getValue() != null) {
      number5 = ringRoadVd5Weigth.getValue();
    }
    if (ringRoadVd6Weigth.getValue() != null) {
      number6 = ringRoadVd6Weigth.getValue();
    }
    if ((number1 + number2 + number3 + number4 + number5 + number6) != 100) {
      Info.display(messages.info(), "權重累加後需為 100");
      return;
    }

    final ConfirmMessageBox box =
        new ConfirmMessageBox(messages.message(), messages.message_itemSaveConfirm());
    box.addDialogHideHandler(
        new DialogHideHandler() {
          @Override
          public void onDialogHide(DialogHideEvent event) {
            if (event.getHideButton() == PredefinedButton.YES) {
              RingRoadConfigDTO storeDto =
                  ringStore.findModelWithKey("" + nowRingRoadConfig.getId());
              if (storeDto != null) {
                RingRoadConfigDTO dto = ringFieldsToRingDto();
                GWT.log("Save dto = " + dto);
                presenter.saveRingItem(dto);
              } else {
                Info.display(messages.message(), messages.message_itemNoExisted());
              }
            }
          }
        });
    box.show();
  }

  @UiHandler("addRingButton")
  public void addRingButton(SelectEvent event) {
    GWT.log("addRingButton Click!");
    if (nowRoadDivision == null) {
      Info.display(messages.info(), messages.info_noSelectedOneSystemInterchange());
      return;
    }
    if (startDescription.getValue() == null) {
      Info.display(
          messages.info(),
          messages.ringRoadConfig_startDescription() + " " + messages.info_notEmpty());
      return;
    }
    if (endDescription.getValue() == null) {
      Info.display(
          messages.info(),
          messages.ringRoadConfig_endDescription() + " " + messages.info_notEmpty());
      return;
    }
    if (lineNameRingCombo.getValue() == null) {
      Info.display(
          messages.info(), messages.ringRoadConfig_endLineId() + " " + messages.info_notEmpty());
      return;
    }
    if (startDirectionRingCombo.getValue() == null) {
      Info.display(
          messages.info(),
          messages.ringRoadConfig_startDirection() + " " + messages.info_notEmpty());
      return;
    }
    if (directionRingCombo.getValue() == null) {
      Info.display(
          messages.info(), messages.ringRoadConfig_endDirection() + " " + messages.info_notEmpty());
      return;
    }
    if (roadDivisionRingCombo.getValue() == null) {
      Info.display(
          messages.info(),
          messages.ringRoadConfig_endDivisionId() + " " + messages.info_notEmpty());
      return;
    }
    if (ringLength.getValue() == null) {
      Info.display(
          messages.info(), messages.ringRoadConfig_length() + " " + messages.info_notEmpty());
      return;
    }
    if (speedLimit.getValue() == null) {
      Info.display(
          messages.info(), messages.ringRoadConfig_speedLimit() + " " + messages.info_notEmpty());
      return;
    }
    if (freeTravelTime.getValue() == null) {
      Info.display(
          messages.info(),
          messages.ringRoadConfig_freeTravelTime() + " " + messages.info_notEmpty());
      return;
    }
    if (exitVd.getValue() != null && !exitVd.getValue().isEmpty()) {
      String deviceName = this.getDeviceNameByDisplayName(exitVd.getValue());
      if (deviceName == null || deviceName.isEmpty()) {
        Info.display(
            messages.info(),
            messages.ringRoadConfig_exitVd() + " " + exitVd.getValue() + " 不存在，請檢查!");
        return;
      }
    }
    if (ringRoadVd1.getValue() != null && !ringRoadVd1.getValue().isEmpty()) {
      String deviceName = this.getDeviceNameByDisplayName(ringRoadVd1.getValue());
      if (deviceName == null || deviceName.isEmpty()) {
        Info.display(
            messages.info(),
            messages.ringRoadConfig_ringRoadVd1() + " " + ringRoadVd1.getValue() + " 不存在，請檢查!");
        return;
      }
    }
    if (ringRoadVd2.getValue() != null && !ringRoadVd2.getValue().isEmpty()) {
      String deviceName = this.getDeviceNameByDisplayName(ringRoadVd2.getValue());
      if (deviceName == null || deviceName.isEmpty()) {
        Info.display(
            messages.info(),
            messages.ringRoadConfig_ringRoadVd2() + " " + ringRoadVd2.getValue() + " 不存在，請檢查!");
        return;
      }
    }
    if (ringRoadVd3.getValue() != null && !ringRoadVd3.getValue().isEmpty()) {
      String deviceName = this.getDeviceNameByDisplayName(ringRoadVd3.getValue());
      if (deviceName == null || deviceName.isEmpty()) {
        Info.display(
            messages.info(),
            messages.ringRoadConfig_ringRoadVd3() + " " + ringRoadVd3.getValue() + " 不存在，請檢查!");
        return;
      }
    }
    if (ringRoadVd4.getValue() != null && !ringRoadVd4.getValue().isEmpty()) {
      String deviceName = this.getDeviceNameByDisplayName(ringRoadVd4.getValue());
      if (deviceName == null || deviceName.isEmpty()) {
        Info.display(
            messages.info(),
            messages.ringRoadConfig_ringRoadVd4() + " " + ringRoadVd4.getValue() + " 不存在，請檢查!");
        return;
      }
    }
    if (ringRoadVd5.getValue() != null && !ringRoadVd5.getValue().isEmpty()) {
      String deviceName = this.getDeviceNameByDisplayName(ringRoadVd5.getValue());
      if (deviceName == null || deviceName.isEmpty()) {
        Info.display(
            messages.info(),
            messages.ringRoadConfig_ringRoadVd5() + " " + ringRoadVd5.getValue() + " 不存在，請檢查!");
        return;
      }
    }
    if (ringRoadVd6.getValue() != null && !ringRoadVd6.getValue().isEmpty()) {
      String deviceName = this.getDeviceNameByDisplayName(ringRoadVd6.getValue());
      if (deviceName == null || deviceName.isEmpty()) {
        Info.display(
            messages.info(),
            messages.ringRoadConfig_ringRoadVd6() + " " + ringRoadVd6.getValue() + " 不存在，請檢查!");
        return;
      }
    }
    int number1 = 0;
    int number2 = 0;
    int number3 = 0;
    int number4 = 0;
    int number5 = 0;
    int number6 = 0;
    if (ringRoadVd1Weigth.getValue() != null) {
      number1 = ringRoadVd1Weigth.getValue();
    }
    if (ringRoadVd2Weigth.getValue() != null) {
      number2 = ringRoadVd2Weigth.getValue();
    }
    if (ringRoadVd3Weigth.getValue() != null) {
      number3 = ringRoadVd3Weigth.getValue();
    }
    if (ringRoadVd4Weigth.getValue() != null) {
      number4 = ringRoadVd4Weigth.getValue();
    }
    if (ringRoadVd5Weigth.getValue() != null) {
      number5 = ringRoadVd5Weigth.getValue();
    }
    if (ringRoadVd6Weigth.getValue() != null) {
      number6 = ringRoadVd6Weigth.getValue();
    }
    if ((number1 + number2 + number3 + number4 + number5 + number6) != 100) {
      Info.display(messages.info(), "權重累加後需為 100");
      return;
    }
    final ConfirmMessageBox box =
        new ConfirmMessageBox(messages.message(), messages.message_itemAddConfirm());
    box.addDialogHideHandler(
        new DialogHideHandler() {
          @Override
          public void onDialogHide(DialogHideEvent event) {
            if (event.getHideButton() == PredefinedButton.YES) {
              RingRoadConfigDTO dto = ringFieldsToRingDto();
              GWT.log("Add dto = " + dto);
              presenter.addRingItem(dto);
            }
          }
        });
    box.show();
  }

  @UiHandler("deleteRingButton")
  public void deleteRingButton(SelectEvent event) {
    GWT.log("deleteRingButton Click!");
    if (nowRingRoadConfig == null) {
      Info.display(messages.info(), messages.info_noSelectedOneSystemInterchange());
      return;
    }
    final ConfirmMessageBox box =
        new ConfirmMessageBox(messages.message(), messages.message_itemDeleteConfirm());
    box.addDialogHideHandler(
        new DialogHideHandler() {
          @Override
          public void onDialogHide(DialogHideEvent event) {
            if (event.getHideButton() == PredefinedButton.YES) {
              RingRoadConfigDTO storeDto =
                  ringStore.findModelWithKey("" + nowRingRoadConfig.getId());
              if (storeDto != null) {
                RingRoadConfigDTO dto = ringFieldsToRingDto();
                GWT.log("Delete dto = " + dto);
                presenter.removeRingItem(dto.getId());
              } else {
                Info.display(messages.message(), messages.message_itemNoExisted());
              }
            }
          }
        });
    box.show();
  }

  private void emptyRingFields() {
    if (lineNameRingCombo.getStore().size() > 0) {
      lineNameRingCombo.setValue(lineNameRingCombo.getStore().get(0));
    } else {
      lineNameRingCombo.setValue(null);
    }
    startDirectionRingCombo.setValue(Direction.N);
    directionRingCombo.setValue(Direction.N);
    if (roadDivisionRingCombo.getStore().size() > 0) {
      roadDivisionRingCombo.setValue(roadDivisionRingCombo.getStore().get(0));
    } else {
      roadDivisionRingCombo.setValue(null);
    }
    startDescription.setValue(null);
    endDescription.setValue(null);
    exitVd.setValue(null);
    ringLength.setValue(null);
    speedLimit.setValue(null);
    freeTravelTime.setValue(null);
    etagSectionId.setValue(null);
    emptyRingVdFields();
  }

  private void emptyRingVdFields() {
    ringRoadVd1.setValue(null);
    ringRoadVd2.setValue(null);
    ringRoadVd3.setValue(null);
    ringRoadVd4.setValue(null);
    ringRoadVd5.setValue(null);
    ringRoadVd6.setValue(null);
    ringRoadVd1Weigth.setValue(null);
    ringRoadVd2Weigth.setValue(null);
    ringRoadVd3Weigth.setValue(null);
    ringRoadVd4Weigth.setValue(null);
    ringRoadVd5Weigth.setValue(null);
    ringRoadVd6Weigth.setValue(null);
  }

  private void fillRingFields(RingRoadConfigDTO dto) {
    directionCombo.setValue(dto.getEndDirection());

    RoadLineDTO roadLine = lineNameCombo.getStore().findModelWithKey(dto.getEndLineId());
    lineNameRingCombo.setValue(roadLine);
    roadDivisionRingCombo.getStore().getFilters().clear();
    roadDivisionRingComboFilter.setCondition(dto.getEndLineId());
    roadDivisionRingCombo.getStore().addFilter(roadDivisionRingComboFilter);
    roadDivisionRingCombo.getStore().setEnableFilters(true);
    GWT.log("dto.getEndDivisionId()=" + dto.getEndDivisionId());
    RoadDivisionDTO endRoadDivision = getRoadDivisionById(dto.getEndDivisionId());
    GWT.log("endRoadDivision=" + endRoadDivision);
    roadDivisionRingCombo.setValue(endRoadDivision);

    startDirectionRingCombo.setValue(dto.getStartDirection());
    directionRingCombo.setValue(dto.getEndDirection());
    startDescription.setValue(dto.getStartDescription());
    endDescription.setValue(dto.getEndDescription());
    String displayName = getDisplayName(dto.getExitVd());
    GWT.log("dto.getExitVd() : " + dto.getExitVd());
    GWT.log("displayName : " + displayName);
    if (displayName != null && !displayName.isEmpty()) {
      exitVd.setValue(displayName);
    }
    ringLength.setValue(dto.getLength());
    speedLimit.setValue(dto.getSpeedLimit());
    freeTravelTime.setValue(dto.getFreeTravelTime());
    etagSectionId.setValue(dto.getEtagSectionId());
    emptyRingVdFields();
    List<RingRoadVdDTO> ringRoadVds = dto.getRingRoadVds();
    if (ringRoadVds != null && ringRoadVds.size() > 0) {
      for (int i = 0; i < ringRoadVds.size(); i++) {
        RingRoadVdDTO ringRoadVdDto = ringRoadVds.get(i);
        if (i == 0) {
          ringRoadVd1.setValue(getDisplayName(ringRoadVdDto.getVd()));
          ringRoadVd1Weigth.setValue(ringRoadVdDto.getWeigth());
        }
        if (i == 1) {
          ringRoadVd2.setValue(getDisplayName(ringRoadVdDto.getVd()));
          ringRoadVd2Weigth.setValue(ringRoadVdDto.getWeigth());
        }
        if (i == 2) {
          ringRoadVd3.setValue(getDisplayName(ringRoadVdDto.getVd()));
          ringRoadVd3Weigth.setValue(ringRoadVdDto.getWeigth());
        }
        if (i == 3) {
          ringRoadVd4.setValue(getDisplayName(ringRoadVdDto.getVd()));
          ringRoadVd4Weigth.setValue(ringRoadVdDto.getWeigth());
        }
        if (i == 4) {
          ringRoadVd5.setValue(getDisplayName(ringRoadVdDto.getVd()));
          ringRoadVd5Weigth.setValue(ringRoadVdDto.getWeigth());
        }
        if (i == 5) {
          ringRoadVd6.setValue(getDisplayName(ringRoadVdDto.getVd()));
          ringRoadVd6Weigth.setValue(ringRoadVdDto.getWeigth());
        }
      }
    }
  }

  private RingRoadConfigDTO ringFieldsToRingDto() {
    RingRoadConfigDTO dto = new RingRoadConfigDTO();
    dto.setStartDivisionId(nowRoadDivision.getDivisionId());
    dto.setStartLineId(nowRoadDivision.getLineId());
    dto.setStartDirection(startDirectionRingCombo.getValue());
    dto.setStartMileage(nowRoadDivision.getMileage());
    dto.setStartDescription(startDescription.getValue());
    dto.setEndDescription(endDescription.getValue());
    dto.setEndDirection(directionRingCombo.getValue());
    dto.setEndDivisionId(roadDivisionRingCombo.getValue().getDivisionId());
    dto.setExitVd(this.getDeviceNameByDisplayName(exitVd.getValue()));
    dto.setLength(ringLength.getValue());
    dto.setSpeedLimit(speedLimit.getValue());
    dto.setFreeTravelTime(freeTravelTime.getValue());
    dto.setEtagSectionId(etagSectionId.getValue());
    RoadDivisionDTO roadDivisionDTO =
        getRoadDivisionByDivisionId(roadDivisionRingCombo.getValue().getDivisionId());
    if (roadDivisionDTO != null) {
      dto.setEndMileage(roadDivisionDTO.getMileage());
      dto.setEndLineId(roadDivisionDTO.getLineId());
    }

    if (nowRingRoadConfig != null) {
      dto.setId(nowRingRoadConfig.getId());
    } else {
      dto.setId(
          dto.getStartDivisionId()
              + "-"
              + dto.getStartDirection()
              + "-"
              + dto.getEndDivisionId()
              + "-"
              + dto.getEndDirection());
    }
    List<RingRoadVdDTO> ringRoadVdDtos = new ArrayList<>();
    if (ringRoadVd1.getValue() != null
        && ringRoadVd1.getValue().length() > 0
        && ringRoadVd1Weigth != null) {
      RingRoadVdDTO ringRoadVdDto = new RingRoadVdDTO();
      ringRoadVdDto.setVd(this.getDeviceNameByDisplayName(ringRoadVd1.getValue()));
      ringRoadVdDto.setWeigth(ringRoadVd1Weigth.getValue());
      ringRoadVdDto.setId(dto.getId() + "-" + ringRoadVdDto.getVd());
      ringRoadVdDtos.add(ringRoadVdDto);
    }
    if (ringRoadVd2.getValue() != null
        && ringRoadVd2.getValue().length() > 0
        && ringRoadVd2Weigth != null) {
      RingRoadVdDTO ringRoadVdDto = new RingRoadVdDTO();
      ringRoadVdDto.setVd(this.getDeviceNameByDisplayName(ringRoadVd2.getValue()));
      ringRoadVdDto.setWeigth(ringRoadVd2Weigth.getValue());
      ringRoadVdDto.setId(dto.getId() + "-" + ringRoadVdDto.getVd());
      ringRoadVdDtos.add(ringRoadVdDto);
    }
    if (ringRoadVd3.getValue() != null
        && ringRoadVd3.getValue().length() > 0
        && ringRoadVd3Weigth != null) {
      RingRoadVdDTO ringRoadVdDto = new RingRoadVdDTO();
      ringRoadVdDto.setVd(this.getDeviceNameByDisplayName(ringRoadVd3.getValue()));
      ringRoadVdDto.setWeigth(ringRoadVd3Weigth.getValue());
      ringRoadVdDto.setId(dto.getId() + "-" + ringRoadVdDto.getVd());
      ringRoadVdDtos.add(ringRoadVdDto);
    }
    if (ringRoadVd4.getValue() != null
        && ringRoadVd4.getValue().length() > 0
        && ringRoadVd4Weigth != null) {
      RingRoadVdDTO ringRoadVdDto = new RingRoadVdDTO();
      ringRoadVdDto.setVd(this.getDeviceNameByDisplayName(ringRoadVd4.getValue()));
      ringRoadVdDto.setWeigth(ringRoadVd4Weigth.getValue());
      ringRoadVdDto.setId(dto.getId() + "-" + ringRoadVdDto.getVd());
      ringRoadVdDtos.add(ringRoadVdDto);
    }
    if (ringRoadVd5.getValue() != null
        && ringRoadVd5.getValue().length() > 0
        && ringRoadVd5Weigth != null) {
      RingRoadVdDTO ringRoadVdDto = new RingRoadVdDTO();
      ringRoadVdDto.setVd(this.getDeviceNameByDisplayName(ringRoadVd5.getValue()));
      ringRoadVdDto.setWeigth(ringRoadVd5Weigth.getValue());
      ringRoadVdDto.setId(dto.getId() + "-" + ringRoadVdDto.getVd());
      ringRoadVdDtos.add(ringRoadVdDto);
    }
    if (ringRoadVd6.getValue() != null
        && ringRoadVd6.getValue().length() > 0
        && ringRoadVd6Weigth != null) {
      RingRoadVdDTO ringRoadVdDto = new RingRoadVdDTO();
      ringRoadVdDto.setVd(this.getDeviceNameByDisplayName(ringRoadVd6.getValue()));
      ringRoadVdDto.setWeigth(ringRoadVd6Weigth.getValue());
      ringRoadVdDto.setId(dto.getId() + "-" + ringRoadVdDto.getVd());
      ringRoadVdDtos.add(ringRoadVdDto);
    }
    dto.setRingRoadVds(ringRoadVdDtos);
    return dto;
  }

  private RoadDivisionDTO getRoadDivisionByDivisionId(String divisionId) {
    for (RoadDivisionDTO dto : roadDivisionList) {
      if (divisionId.equals(dto.getDivisionId())) {
        return dto;
      }
    }
    return null;
  }

  private void initColumnModel() {
    List<ColumnConfig<RoadDivisionDTO, ?>> columnConfigs =
        new ArrayList<ColumnConfig<RoadDivisionDTO, ?>>();

    ColumnConfig<RoadDivisionDTO, String> divisionId =
        new ColumnConfig<RoadDivisionDTO, String>(props.divisionId());
    divisionId.setWidth(80);
    divisionId.setHeader(messages.roadDivision_divisionId());
    divisionId.setHideable(true);
    divisionId.setMenuDisabled(false);
    divisionId.setSortable(true);
    columnConfigs.add(divisionId);

    ColumnConfig<RoadDivisionDTO, String> divisionName =
        new ColumnConfig<RoadDivisionDTO, String>(props.divisionName());
    divisionName.setWidth(160);
    divisionName.setHeader(messages.roadDivision_divisionName());
    divisionName.setHideable(true);
    divisionName.setMenuDisabled(false);
    divisionName.setSortable(true);
    columnConfigs.add(divisionName);

    ColumnConfig<RoadDivisionDTO, Integer> mileage =
        new ColumnConfig<RoadDivisionDTO, Integer>(props.mileage());
    mileage.setWidth(120);
    mileage.setHeader(messages.roadDivision_mileage());
    mileage.setHideable(true);
    mileage.setMenuDisabled(false);
    mileage.setSortable(true);
    columnConfigs.add(mileage);

    ColumnConfig<RoadDivisionDTO, String> lineName =
        new ColumnConfig<RoadDivisionDTO, String>(props.lineName());
    lineName.setWidth(100);
    lineName.setHeader(messages.roadDivision_lineName());
    lineName.setHideable(true);
    lineName.setMenuDisabled(false);
    lineName.setSortable(true);
    columnConfigs.add(lineName);

    ColumnConfig<RoadDivisionDTO, Boolean> boundary =
        new ColumnConfig<RoadDivisionDTO, Boolean>(props.boundary());
    boundary.setWidth(100);
    boundary.setHeader(messages.roadDivision_boundary());
    boundary.setHideable(true);
    boundary.setMenuDisabled(false);
    boundary.setSortable(true);
    boundary.setCell(
        new AbstractCell<Boolean>() {
          @Override
          public void render(
              com.google.gwt.cell.client.Cell.Context context, Boolean value, SafeHtmlBuilder sb) {
            String msg = "";
            if (value != null && value) msg = "<font color=blue>" + messages.yes() + "</font>";
            else msg = messages.no();
            sb.appendHtmlConstant("<span>" + msg + "</span>");
          }
        });
    columnConfigs.add(boundary);

    ColumnConfig<RoadDivisionDTO, Boolean> travelTimeVisible =
        new ColumnConfig<RoadDivisionDTO, Boolean>(props.travelTimeVisible());
    travelTimeVisible.setWidth(150);
    travelTimeVisible.setHeader(messages.roadDivision_travelTimeVisible());
    travelTimeVisible.setHideable(true);
    travelTimeVisible.setMenuDisabled(false);
    travelTimeVisible.setSortable(true);
    travelTimeVisible.setCell(
        new AbstractCell<Boolean>() {
          @Override
          public void render(
              com.google.gwt.cell.client.Cell.Context context, Boolean value, SafeHtmlBuilder sb) {
            String msg = "";
            if (value != null && value) msg = "<font color=blue>" + messages.yes() + "</font>";
            else msg = messages.no();
            sb.appendHtmlConstant("<span>" + msg + "</span>");
          }
        });
    columnConfigs.add(travelTimeVisible);

    cm = new ColumnModel<RoadDivisionDTO>(columnConfigs);
  }

  private void initRingColumnModel() {
    List<ColumnConfig<RingRoadConfigDTO, ?>> columnConfigs =
        new ArrayList<ColumnConfig<RingRoadConfigDTO, ?>>();

    ColumnConfig<RingRoadConfigDTO, String> startDescription =
        new ColumnConfig<RingRoadConfigDTO, String>(ringRoadConfigProps.startDescription());
    startDescription.setWidth(100);
    startDescription.setHeader(messages.ringRoadConfig_startDescription());
    startDescription.setHideable(true);
    startDescription.setMenuDisabled(false);
    startDescription.setSortable(true);
    columnConfigs.add(startDescription);

    ColumnConfig<RingRoadConfigDTO, String> endDivisionName =
        new ColumnConfig<RingRoadConfigDTO, String>(ringRoadConfigProps.endDivisionName());
    endDivisionName.setWidth(100);
    endDivisionName.setHeader(messages.ringRoadConfig_endDivisionId());
    endDivisionName.setHideable(true);
    endDivisionName.setMenuDisabled(false);
    endDivisionName.setSortable(true);
    columnConfigs.add(endDivisionName);

    ColumnConfig<RingRoadConfigDTO, String> endLineName =
        new ColumnConfig<RingRoadConfigDTO, String>(ringRoadConfigProps.endLineName());
    endLineName.setWidth(100);
    endLineName.setHeader(messages.ringRoadConfig_endLineId());
    endLineName.setHideable(true);
    endLineName.setMenuDisabled(false);
    endLineName.setSortable(true);
    columnConfigs.add(endLineName);

    ColumnConfig<RingRoadConfigDTO, String> endDirection =
        new ColumnConfig<RingRoadConfigDTO, String>(ringRoadConfigProps.endDirectionStr());
    endDirection.setWidth(100);
    endDirection.setHeader(messages.ringRoadConfig_endDirection());
    endDirection.setHideable(true);
    endDirection.setMenuDisabled(false);
    endDirection.setSortable(true);
    endDirection.setCell(
        new AbstractCell<String>() {
          @Override
          public void render(
              com.google.gwt.cell.client.Cell.Context context, String value, SafeHtmlBuilder sb) {
            String msg = CommonStringConverter.getDirectionName(Direction.valueOf(value));
            sb.appendHtmlConstant("<span>" + msg + "</span>");
          }
        });
    columnConfigs.add(endDirection);

    ColumnConfig<RingRoadConfigDTO, Integer> endMileage =
        new ColumnConfig<RingRoadConfigDTO, Integer>(ringRoadConfigProps.endMileage());
    endMileage.setWidth(100);
    endMileage.setHeader(messages.ringRoadConfig_endMileage());
    endMileage.setHideable(true);
    endMileage.setMenuDisabled(false);
    endMileage.setSortable(true);
    columnConfigs.add(endMileage);

    ColumnConfig<RingRoadConfigDTO, String> endDescription =
        new ColumnConfig<RingRoadConfigDTO, String>(ringRoadConfigProps.endDescription());
    endDescription.setWidth(100);
    endDescription.setHeader(messages.ringRoadConfig_endDescription());
    endDescription.setHideable(true);
    endDescription.setMenuDisabled(false);
    endDescription.setSortable(true);
    columnConfigs.add(endDescription);

    ColumnConfig<RingRoadConfigDTO, String> exitVd =
        new ColumnConfig<RingRoadConfigDTO, String>(ringRoadConfigProps.exitVdName());
    exitVd.setWidth(160);
    exitVd.setHeader(messages.ringRoadConfig_exitVd());
    exitVd.setHideable(true);
    exitVd.setMenuDisabled(false);
    exitVd.setSortable(true);
    columnConfigs.add(exitVd);

    ColumnConfig<RingRoadConfigDTO, Integer> length =
        new ColumnConfig<RingRoadConfigDTO, Integer>(ringRoadConfigProps.length());
    length.setWidth(100);
    length.setHeader(messages.ringRoadConfig_length());
    length.setHideable(true);
    length.setMenuDisabled(false);
    length.setSortable(true);
    columnConfigs.add(length);

    ColumnConfig<RingRoadConfigDTO, Integer> speedLimit =
        new ColumnConfig<RingRoadConfigDTO, Integer>(ringRoadConfigProps.speedLimit());
    speedLimit.setWidth(100);
    speedLimit.setHeader(messages.ringRoadConfig_speedLimit());
    speedLimit.setHideable(true);
    speedLimit.setMenuDisabled(false);
    speedLimit.setSortable(true);
    columnConfigs.add(speedLimit);

    ColumnConfig<RingRoadConfigDTO, Integer> freeTravelTime =
        new ColumnConfig<RingRoadConfigDTO, Integer>(ringRoadConfigProps.freeTravelTime());
    freeTravelTime.setWidth(100);
    freeTravelTime.setHeader(messages.ringRoadConfig_freeTravelTime());
    freeTravelTime.setHideable(true);
    freeTravelTime.setMenuDisabled(false);
    freeTravelTime.setSortable(true);
    columnConfigs.add(freeTravelTime);

    ColumnConfig<RingRoadConfigDTO, String> etagSectionId =
        new ColumnConfig<RingRoadConfigDTO, String>(ringRoadConfigProps.etagSectionId());
    etagSectionId.setWidth(100);
    etagSectionId.setHeader(messages.ringRoadConfig_etagSectionId());
    etagSectionId.setHideable(true);
    etagSectionId.setMenuDisabled(false);
    etagSectionId.setSortable(true);
    columnConfigs.add(etagSectionId);

    ColumnConfig<RingRoadConfigDTO, String> ringRoadVds =
        new ColumnConfig<RingRoadConfigDTO, String>(ringRoadConfigProps.ringRoadVdStr());
    ringRoadVds.setWidth(500);
    ringRoadVds.setHeader(messages.ringRoadConfig_RingRoadVds());
    ringRoadVds.setHideable(true);
    ringRoadVds.setMenuDisabled(false);
    ringRoadVds.setSortable(true);
    columnConfigs.add(ringRoadVds);

    ringCm = new ColumnModel<RingRoadConfigDTO>(columnConfigs);
  }

  private void initRampColumnModel() {
    List<ColumnConfig<RampVdConfigDTO, ?>> columnConfigs =
        new ArrayList<ColumnConfig<RampVdConfigDTO, ?>>();

    ColumnConfig<RampVdConfigDTO, String> direction =
        new ColumnConfig<RampVdConfigDTO, String>(rampVdConfigProps.directionStr());
    direction.setWidth(100);
    direction.setHeader(messages.rampVdConfig_direction());
    direction.setHideable(true);
    direction.setMenuDisabled(false);
    direction.setSortable(true);
    direction.setCell(
        new AbstractCell<String>() {
          @Override
          public void render(
              com.google.gwt.cell.client.Cell.Context context, String value, SafeHtmlBuilder sb) {
            String msg = "";
            msg = CommonStringConverter.getDirectionName(Direction.valueOf(value));
            sb.appendHtmlConstant("<span>" + msg + "</span>");
          }
        });
    columnConfigs.add(direction);

    ColumnConfig<RampVdConfigDTO, String> rampType =
        new ColumnConfig<RampVdConfigDTO, String>(rampVdConfigProps.rampTypeStr());
    rampType.setWidth(100);
    rampType.setHeader(messages.rampVdConfig_rampType());
    rampType.setHideable(true);
    rampType.setMenuDisabled(false);
    rampType.setSortable(true);
    rampType.setCell(
        new AbstractCell<String>() {
          @Override
          public void render(
              com.google.gwt.cell.client.Cell.Context context, String value, SafeHtmlBuilder sb) {
            String msg = "";
            msg = CommonStringConverter.getRampTypeName(RampType.valueOf(value));
            sb.appendHtmlConstant("<span>" + msg + "</span>");
          }
        });
    columnConfigs.add(rampType);

    ColumnConfig<RampVdConfigDTO, Integer> exception =
        new ColumnConfig<RampVdConfigDTO, Integer>(rampVdConfigProps.exception());
    exception.setWidth(100);
    exception.setHeader(messages.rampVdConfig_exception());
    exception.setHideable(true);
    exception.setMenuDisabled(false);
    exception.setSortable(true);
    exception.setCell(
        new AbstractCell<Integer>() {
          @Override
          public void render(
              com.google.gwt.cell.client.Cell.Context context, Integer value, SafeHtmlBuilder sb) {
            String msg = "";
            if (value != null && value.intValue() == 1)
              msg = "<font color=blue>" + messages.yes() + "</font>";
            else msg = messages.no();
            sb.appendHtmlConstant("<span>" + msg + "</span>");
          }
        });
    columnConfigs.add(exception);

    ColumnConfig<RampVdConfigDTO, String> vd1 =
        new ColumnConfig<RampVdConfigDTO, String>(rampVdConfigProps.vd1());
    vd1.setWidth(100);
    vd1.setHeader(messages.rampVdConfig_vd1());
    vd1.setHideable(true);
    vd1.setMenuDisabled(false);
    vd1.setSortable(true);
    columnConfigs.add(vd1);

    ColumnConfig<RampVdConfigDTO, String> vd2 =
        new ColumnConfig<RampVdConfigDTO, String>(rampVdConfigProps.vd2());
    vd2.setWidth(100);
    vd2.setHeader(messages.rampVdConfig_vd2());
    vd2.setHideable(true);
    vd2.setMenuDisabled(false);
    vd2.setSortable(true);
    columnConfigs.add(vd2);

    ColumnConfig<RampVdConfigDTO, String> vd3 =
        new ColumnConfig<RampVdConfigDTO, String>(rampVdConfigProps.vd3());
    vd3.setWidth(100);
    vd3.setHeader(messages.rampVdConfig_vd3());
    vd3.setHideable(true);
    vd3.setMenuDisabled(false);
    vd3.setSortable(true);
    columnConfigs.add(vd3);

    ColumnConfig<RampVdConfigDTO, Integer> id =
        new ColumnConfig<RampVdConfigDTO, Integer>(rampVdConfigProps.id());
    id.setWidth(80);
    id.setHeader(messages.rampVdConfig_id());
    id.setHideable(true);
    id.setMenuDisabled(false);
    id.setSortable(true);
    columnConfigs.add(id);

    ColumnConfig<RampVdConfigDTO, String> divisionId =
        new ColumnConfig<RampVdConfigDTO, String>(rampVdConfigProps.divisionName());
    divisionId.setWidth(80);
    divisionId.setHeader(messages.rampVdConfig_divisionId());
    divisionId.setHideable(true);
    divisionId.setMenuDisabled(false);
    divisionId.setSortable(true);
    columnConfigs.add(divisionId);

    rampCm = new ColumnModel<RampVdConfigDTO>(columnConfigs);
  }

  private RoadDivisionDTO getRoadDivisionById(String roadDivisionId) {
    for (RoadDivisionDTO tempDto : this.roadDivisionList) {
      if (roadDivisionId.equals(tempDto.getDivisionId())) {
        return tempDto;
      }
    }
    return null;
  }

  class RoadConfigTreeFireSelectedEventHandlerImpl
      implements RoadConfigTreeFireSelectedEventHandler {

    @Override
    public void selectOneItem(RoadConfigTreeFireSelectedEvent event) {
      GWT.log(
          "SystemInterchangeViewer RoadConfigTreeFireSelectedEventHandlerImpl selectOneItem start...");
      RoadLineDTO dto = (RoadLineDTO) event.getSource();
      fillGrid(dto);
    }
  }

  class RoadDivisionFilter implements StoreFilter<RoadDivisionDTO> {

    private String lineId = null;
    private boolean toShowAll = false;

    public void setCondition(String lineId) {
      this.lineId = lineId;
    }

    public void setShowAll(boolean toShowAll) {
      this.toShowAll = toShowAll;
    }

    @Override
    public boolean select(
        Store<RoadDivisionDTO> store, RoadDivisionDTO parent, RoadDivisionDTO item) {
      if (!toShowAll) {
        if (lineId.equals(item.getLineId())) return true;
        return false;
      } else return true;
    }
  }

  class DirectionFilter implements StoreFilter<Direction> {
    private String roadLineDirection = null;

    public void setCondition(String roadLineDirection) {
      this.roadLineDirection = roadLineDirection;
    }

    @Override
    public boolean select(Store<Direction> store, Direction parent, Direction item) {
      if ("N".equals(roadLineDirection)) {
        switch (item) {
          case E:
            return false;
          case EW:
            return false;
          case N:
            return true;
          case NS:
            return false;
          case S:
            return true;
          case W:
            return false;
          default:
            return false;
        }
      }
      if ("E".equals(roadLineDirection)) {
        switch (item) {
          case E:
            return true;
          case EW:
            return false;
          case N:
            return false;
          case NS:
            return false;
          case S:
            return false;
          case W:
            return true;
          default:
            return false;
        }
      }
      return false;
    }
  }

  class KeyValue {

    private String key;
    private String value;

    public KeyValue(String key, String value) {
      this.key = key;
      this.value = value;
    }
    /** @return the key */
    public String getKey() {
      return key;
    }
    /** @param key the key to set */
    public void setKey(String key) {
      this.key = key;
    }
    /** @return the value */
    public String getValue() {
      return value;
    }
    /** @param value the value to set */
    public void setValue(String value) {
      this.value = value;
    }
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
      return "KeyValue [key=" + key + ", value=" + value + "]";
    }
  }

  class RoadDivisionRingComboFilter implements StoreFilter<RoadDivisionDTO> {
    private String lineId = null;

    public void setCondition(String lineId) {
      this.lineId = lineId;
    }

    @Override
    public boolean select(
        Store<RoadDivisionDTO> store, RoadDivisionDTO parent, RoadDivisionDTO item) {
      if (lineId.equals(item.getLineId())) return true;
      else return false;
    }
  }

  @Override
  protected void onUnload() {
    roadConfigTreeFireSelectedEventRegistration.removeHandler();
    super.onUnload();
  }

  public void addItemResult(Boolean result) {
    if (result) {
      Info.display(messages.info(), messages.info_addSuccessfully());
      clientFactory
          .getEventBus()
          .fireEvent(
              new RoadDivisionUpdatedEvent(RoadDivisionUpdatedEvent.Action.ROADDIVISION_UPDATED));
    } else Info.display(messages.info(), messages.info_addFail());
  }

  public void removeItemResult(Boolean result) {
    if (result) {
      Info.display(messages.info(), messages.info_removeSuccessfully());
      emptyFields();
      clientFactory
          .getEventBus()
          .fireEvent(
              new RoadDivisionUpdatedEvent(RoadDivisionUpdatedEvent.Action.ROADDIVISION_UPDATED));
    } else Info.display(messages.info(), messages.info_removeFail());
  }

  public void saveItemResult(Boolean result) {
    if (result) {
      Info.display(messages.info(), messages.info_saveSuccessfully());
      clientFactory
          .getEventBus()
          .fireEvent(
              new RoadDivisionUpdatedEvent(RoadDivisionUpdatedEvent.Action.ROADDIVISION_UPDATED));
    } else Info.display(messages.info(), messages.info_saveFail());
  }

  @UiHandler("refreshButton")
  public void refreshButton(SelectEvent event) {
    GWT.log("refreshButton start...");
    clientFactory
        .getEventBus()
        .fireEvent(
            new RoadDivisionUpdatedEvent(RoadDivisionUpdatedEvent.Action.ROADDIVISION_UPDATED));
    mask(messages.waiting());
  }

  public void getRampVdConfigsResult(List<RampVdConfigDTO> rampVdConfigs) {
    unmask();
    rampStore.clear();
    if (rampVdConfigs != null) {
      rampStore.addAll(rampVdConfigs);
    } else {
      GWT.log("rampVdConfigs is null!");
    }
  }

  public void getRampVdTypesResult(List<RampVdTypeDTO> rampVdTypes) {
    if (rampVdTypes != null && rampVdTypes.size() != 0) {
      GWT.log("rampVdTypes.size()=" + rampVdTypes.size());
      rampVdTypeCombo.getStore().clear();
      rampVdTypeCombo.getStore().addAll(rampVdTypes);
      rampVdTypeCombo.setValue(rampVdTypeCombo.getStore().get(0));
    } else {
      GWT.log("rampVdTypes == null or rampVdTypes.size = 0");
    }
  }

  public void addRampItemResult(Boolean result) {
    if (result) {
      Info.display(messages.info(), messages.info_addSuccessfully());
      if (nowRoadDivision != null) presenter.getRampVdConfigs(nowRoadDivision.getDivisionId());
    } else Info.display(messages.info(), messages.info_addFail());
  }

  public void removeRampItemResult(Boolean result) {
    if (result) {
      Info.display(messages.info(), messages.info_removeSuccessfully());
      emptyRampFields();
      if (nowRoadDivision != null) presenter.getRampVdConfigs(nowRoadDivision.getDivisionId());
    } else Info.display(messages.info(), messages.info_removeFail());
  }

  public void saveRampItemResult(Boolean result) {
    if (result) {
      Info.display(messages.info(), messages.info_saveSuccessfully());
      if (nowRoadDivision != null) presenter.getRampVdConfigs(nowRoadDivision.getDivisionId());
    } else Info.display(messages.info(), messages.info_saveFail());
  }

  @UiHandler("refreshRampButton")
  public void refreshRampButton(SelectEvent event) {
    GWT.log("refreshRampButton start...");
    if (nowRoadDivision != null) {
      presenter.getRampVdConfigs(nowRoadDivision.getDivisionId());
      mask(messages.waiting());
    }
  }

  public void addRingItemResult(Boolean result) {
    if (result) {
      Info.display(messages.info(), messages.info_addSuccessfully());
      if (nowRoadDivision != null) presenter.getRingRoadConfigs(nowRoadDivision.getDivisionId());
    } else Info.display(messages.info(), messages.info_addFail());
  }

  public void removeRingItemResult(Boolean result) {
    if (result) {
      Info.display(messages.info(), messages.info_removeSuccessfully());
      emptyRingFields();
      if (nowRoadDivision != null) presenter.getRingRoadConfigs(nowRoadDivision.getDivisionId());
    } else Info.display(messages.info(), messages.info_removeFail());
  }

  public void saveRingItemResult(Boolean result) {
    if (result) {
      Info.display(messages.info(), messages.info_saveSuccessfully());
      if (nowRoadDivision != null) presenter.getRingRoadConfigs(nowRoadDivision.getDivisionId());
    } else Info.display(messages.info(), messages.info_saveFail());
  }

  @UiHandler("refreshRingButton")
  public void refreshRingButton(SelectEvent event) {
    GWT.log("refreshRingButton start...");
    if (nowRoadDivision != null) {
      presenter.getRingRoadConfigs(nowRoadDivision.getDivisionId());
      mask(messages.waiting());
    }
  }

  public void getRingRoadConfigsResult(List<RingRoadConfigDTO> ringRoadConfigs) {
    unmask();
    ringStore.clear();
    if (ringRoadConfigs != null) {
      ringStore.addAll(ringRoadConfigs);
    } else {
      GWT.log("ringRoadConfigs is null!");
    }
  }

  public void fillDeviceConfig(List<DeviceConfigDTO> result) {
    deviceConfigDTOList = result;
  }
}
