/*
 * © HwaCom Systems Inc. 2015
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.cam.view.road;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.core.client.GWT;
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
import com.hwacom.ngtms.c.shared.dto.RampVdConfigDTO;
import com.hwacom.ngtms.c.shared.dto.RampVdTypeDTO;
import com.hwacom.ngtms.c.shared.dto.RoadDivisionDTO;
import com.hwacom.ngtms.c.shared.dto.RoadLineDTO;
import com.hwacom.ngtms.cam.event.road.RoadConfigTreeFireSelectedEvent;
import com.hwacom.ngtms.cam.event.road.RoadConfigTreeFireSelectedEvent.RoadConfigTreeFireSelectedEventHandler;
import com.hwacom.ngtms.cam.event.road.RoadDivisionUpdatedEvent;
import com.hwacom.ngtms.cam.presenter.road.InterchangePresenter;
import com.hwacom.ngtms.cam.util.CommonStringConverter;
import com.hwacom.ngtms.cam.view.Messages;
import com.hwacom.ngtms.cam.vo.RampVdConfigProperties;
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

public class InterchangeViewer extends Composite {

  private static InterchangeViewerUiBinder uiBinder = GWT.create(InterchangeViewerUiBinder.class);

  interface InterchangeViewerUiBinder extends UiBinder<Widget, InterchangeViewer> {}

  private static final RoadDivisionProperties props = GWT.create(RoadDivisionProperties.class);
  private static final RoadLineProperties roadLineProps = GWT.create(RoadLineProperties.class);
  private static final RampVdConfigProperties rampVdConfigProps =
      GWT.create(RampVdConfigProperties.class);
  private final ClientFactory clientFactory = GWT.create(ClientFactory.class);
  private static final Messages messages = GWT.create(Messages.class);
  private InterchangePresenter presenter;
  private final HandlerRegistration roadConfigTreeFireSelectedEventRegistration;
  private static final DivisionType DIVISION_TYPE = DivisionType.I;

  private List<RoadLineDTO> roadLineList;
  private List<RoadDivisionDTO> roadDivisionList = new ArrayList<>();
  private KeyValue yesKeyValue = new KeyValue("true", messages.yes());
  private KeyValue noKeyValue = new KeyValue("false", messages.no());
  private KeyValue zeroKeyValue = new KeyValue("0", messages.no());
  private KeyValue oneKeyValue = new KeyValue("1", messages.yes());
  private RoadDivisionFilter roadDivisionFilter = new RoadDivisionFilter();
  private RoadDivisionDTO nowRoadDivision;
  private RampVdConfigDTO nowRampVdConfig;

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
              return item.name();
            }
          });

  @UiField(provided = true)
  SimpleComboBox<Direction> directionCombo =
      new SimpleComboBox<Direction>(
          new LabelProvider<Direction>() {
            @Override
            public String getLabel(Direction item) {
              return item.name();
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

  public InterchangeViewer() {
    store = new ListStore<RoadDivisionDTO>(props.key());
    rampStore = new ListStore<RampVdConfigDTO>(rampVdConfigProps.key());
    initColumnModel();
    initRampColumnModel();
    initWidget(uiBinder.createAndBindUi(this));
    presenter = new InterchangePresenter(this);
    store.addSortInfo(new StoreSortInfo<RoadDivisionDTO>(props.mileage(), SortDir.ASC));
    store.addFilter(roadDivisionFilter);
    roadDivisionFilter.setShowAll(true);
    store.setEnableFilters(true);
    rampStore.addSortInfo(new StoreSortInfo<RampVdConfigDTO>(rampVdConfigProps.id(), SortDir.ASC));
    initComboBox();
    roadConfigTreeFireSelectedEventRegistration =
        clientFactory
            .getEventBus()
            .addHandler(
                RoadConfigTreeFireSelectedEvent.TYPE,
                new RoadConfigTreeFireSelectedEventHandlerImpl());
  }

  private void initComboBox() {
    lineNameCombo
        .getStore()
        .addSortInfo(new StoreSortInfo<RoadLineDTO>(roadLineProps.lineId(), SortDir.ASC));
    if (roadLineList != null) {
      lineNameCombo.getStore().clear();
      lineNameCombo.getStore().addAll(roadLineList);
      if (lineNameCombo.getStore().size() > 0) {
        lineNameCombo.setValue(lineNameCombo.getStore().get(0));
      }
    }
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
  }

  public void initRoadLineData(List<RoadLineDTO> roadLineList) {
    this.roadLineList = roadLineList;
    lineNameCombo.getStore().clear();
    lineNameCombo.getStore().addAll(roadLineList);
    if (lineNameCombo.getStore().size() > 0) {
      lineNameCombo.setValue(lineNameCombo.getStore().get(0));
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
  }

  @UiHandler("grid")
  public void rowClick(RowClickEvent event) {
    RoadDivisionDTO dto = store.get(event.getRowIndex());
    if (dto != null) {
      GWT.log(dto.toString());
      fillFields(dto);
      mask(messages.waiting());
      presenter.getRampVdConfigs(dto.getDivisionId());
    }
  }

  private void filterGrid(RoadLineDTO roadLineDto) {
    GWT.log("InterchangeViewer filterGrid roadLineDto = " + roadLineDto);
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
    } else emptyFields();
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
      Info.display(messages.info(), messages.info_noSelectedOneInterchange());
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
      Info.display(messages.info(), messages.info_noSelectedOneInterchange());
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
              //RampVdConfigDTO storeDto = rampStore.findModelWithKey(""+nowRampVdConfig.getId());
              //if (storeDto == null) {
              RampVdConfigDTO dto = rampFieldsToRampDto();
              GWT.log("Add dto = " + dto);
              presenter.addRampItem(dto);
              //} else {
              //    Info.display(messages.message(),
              //            messages.message_itemExisted());
              //}
            }
          }
        });
    box.show();
  }

  @UiHandler("deleteRampButton")
  public void deleteRampButton(SelectEvent event) {
    GWT.log("deleteRampButton Click!");
    if (nowRampVdConfig == null) {
      Info.display(messages.info(), messages.info_noSelectedOneItem());
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

  class RoadConfigTreeFireSelectedEventHandlerImpl
      implements RoadConfigTreeFireSelectedEventHandler {

    @Override
    public void selectOneItem(RoadConfigTreeFireSelectedEvent event) {
      GWT.log(
          "InterchangeViewer RoadConfigTreeFireSelectedEventHandlerImpl selectOneItem start...");
      RoadLineDTO dto = (RoadLineDTO) event.getSource();
      filterGrid(dto);
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
}
