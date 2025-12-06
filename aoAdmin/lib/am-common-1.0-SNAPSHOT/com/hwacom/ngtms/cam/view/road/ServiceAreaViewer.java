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
import com.hwacom.ngtms.c.shared.DivisionType;
import com.hwacom.ngtms.c.shared.dto.RoadDivisionDTO;
import com.hwacom.ngtms.c.shared.dto.RoadLineDTO;
import com.hwacom.ngtms.cam.event.road.RoadConfigTreeFireSelectedEvent;
import com.hwacom.ngtms.cam.event.road.RoadConfigTreeFireSelectedEvent.RoadConfigTreeFireSelectedEventHandler;
import com.hwacom.ngtms.cam.event.road.RoadDivisionUpdatedEvent;
import com.hwacom.ngtms.cam.presenter.road.ServiceAreaPresenter;
import com.hwacom.ngtms.cam.view.Messages;
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
import java.util.List;

public class ServiceAreaViewer extends Composite {

  private static ServiceAreaViewerUiBinder uiBinder = GWT.create(ServiceAreaViewerUiBinder.class);

  interface ServiceAreaViewerUiBinder extends UiBinder<Widget, ServiceAreaViewer> {}

  private static final RoadDivisionProperties props = GWT.create(RoadDivisionProperties.class);
  private static final RoadLineProperties roadLineProps = GWT.create(RoadLineProperties.class);
  private final ClientFactory clientFactory = GWT.create(ClientFactory.class);
  private static final Messages messages = GWT.create(Messages.class);
  private ServiceAreaPresenter presenter;
  private final HandlerRegistration roadConfigTreeFireSelectedEventRegistration;
  private static final DivisionType DIVISION_TYPE = DivisionType.S;

  private List<RoadLineDTO> roadLineList;
  private List<RoadDivisionDTO> roadDivisionList = new ArrayList<>();
  private KeyValue yesKeyValue = new KeyValue("true", messages.yes());
  private KeyValue noKeyValue = new KeyValue("false", messages.no());
  private RoadDivisionFilter roadDivisionFilter = new RoadDivisionFilter();
  private RoadDivisionDTO nowRoadDivision;

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

  public ServiceAreaViewer() {
    store = new ListStore<RoadDivisionDTO>(props.key());
    initColumnModel();
    initWidget(uiBinder.createAndBindUi(this));
    presenter = new ServiceAreaPresenter(this);
    store.addSortInfo(new StoreSortInfo<RoadDivisionDTO>(props.divisionId(), SortDir.ASC));
    store.addFilter(roadDivisionFilter);
    roadDivisionFilter.setShowAll(true);
    store.setEnableFilters(true);
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
    }
  }

  private void filterGrid(RoadLineDTO roadLineDto) {
    GWT.log("ServiceAreaViewer filterGrid roadLineDto = " + roadLineDto);
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

  class RoadConfigTreeFireSelectedEventHandlerImpl
      implements RoadConfigTreeFireSelectedEventHandler {

    @Override
    public void selectOneItem(RoadConfigTreeFireSelectedEvent event) {
      GWT.log(
          "ServiceAreaViewer RoadConfigTreeFireSelectedEventHandlerImpl selectOneItem start...");
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
}
