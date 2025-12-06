/*
 * © HwaCom Systems Inc. 2015
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.cam.view.road;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Widget;
import com.hwacom.ngtms.c.shared.Direction;
import com.hwacom.ngtms.c.shared.dto.RoadLineDTO;
import com.hwacom.ngtms.cam.client.ui.AmTab;
import com.hwacom.ngtms.cam.event.road.RoadConfigTreeFireSelectedEvent;
import com.hwacom.ngtms.cam.event.road.RoadConfigTreeFireSelectedEvent.RoadConfigTreeFireSelectedEventHandler;
import com.hwacom.ngtms.cam.event.road.RoadConfigTreeSelectEvent;
import com.hwacom.ngtms.cam.event.road.RoadLineUpdatedEvent;
import com.hwacom.ngtms.cam.presenter.road.RoadLineConfigPresenter;
import com.hwacom.ngtms.cam.util.CommonStringConverter;
import com.hwacom.ngtms.cam.view.Messages;
import com.hwacom.ngtms.cam.vo.RoadLineProperties;
import com.hwacom.ngtms.hcce.am.factory.ClientFactory;
import com.sencha.gxt.data.shared.LabelProvider;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.SortDir;
import com.sencha.gxt.data.shared.Store.StoreSortInfo;
import com.sencha.gxt.widget.core.client.Dialog.PredefinedButton;
import com.sencha.gxt.widget.core.client.box.ConfirmMessageBox;
import com.sencha.gxt.widget.core.client.event.DialogHideEvent;
import com.sencha.gxt.widget.core.client.event.DialogHideEvent.DialogHideHandler;
import com.sencha.gxt.widget.core.client.event.RowClickEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
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

public class RoadLineConfigViewer extends AmTab {

  private static RoadLineConfigViewerUiBinder uiBinder =
      GWT.create(RoadLineConfigViewerUiBinder.class);

  interface RoadLineConfigViewerUiBinder extends UiBinder<Widget, RoadLineConfigViewer> {}

  private static final RoadLineProperties props = GWT.create(RoadLineProperties.class);
  private final ClientFactory clientFactory = GWT.create(ClientFactory.class);

  private static final Messages messages = GWT.create(Messages.class);
  private RoadLineConfigPresenter presenter;
  private final HandlerRegistration roadConfigTreeFireSelectedEventRegistration;
  boolean enable = true;

  @UiField(provided = true)
  ColumnModel<RoadLineDTO> cm;

  @UiField(provided = true)
  ListStore<RoadLineDTO> store;

  @UiField GridView<RoadLineDTO> view;
  @UiField Grid<RoadLineDTO> grid;
  @UiField TextField lineId;
  @UiField TextField lineName;
  @UiField IntegerField startMileage;
  @UiField IntegerField endMileage;
  @UiField IntegerField gCodeId;
  @UiField TextField memo;

  @UiField FieldLabel gCodeIdLabel;

  @UiField(provided = true)
  SimpleComboBox<Direction> directionCombo =
      new SimpleComboBox<Direction>(
          new LabelProvider<Direction>() {
            @Override
            public String getLabel(Direction item) {
              return CommonStringConverter.getDirectionName(item);
            }
          });

  public RoadLineConfigViewer() {
    store = new ListStore<RoadLineDTO>(props.key());
    initColumnModel();
    initWidget(uiBinder.createAndBindUi(this));
    presenter = new RoadLineConfigPresenter(this);
    store.addSortInfo(new StoreSortInfo<RoadLineDTO>(props.lineId(), SortDir.ASC));
    initComboBox();
    roadConfigTreeFireSelectedEventRegistration =
        clientFactory
            .getEventBus()
            .addHandler(
                RoadConfigTreeFireSelectedEvent.TYPE,
                new RoadConfigTreeFireSelectedEventHandlerImpl());
  }

  private void initComboBox() {
    for (Direction direction : Direction.values()) {
      directionCombo.add(direction);
    }
    directionCombo.setValue(Direction.N);
  }

  public void initGridData(List<RoadLineDTO> roadLineList) {
    store.clear();
    store.addAll(roadLineList);
    if (store.size() == 0) return;
    String lineId = this.lineId.getValue();
    if (lineId != null && !lineId.isEmpty()) {
      RoadLineDTO storeDto = store.findModelWithKey(lineId);
      grid.getSelectionModel().select(storeDto, false);
      clientFactory
          .getEventBus()
          .fireEventFromSource(
              new RoadConfigTreeSelectEvent(RoadConfigTreeSelectEvent.Action.SELECT_ONE_ITEM),
              storeDto);

    } else {
      grid.getSelectionModel().select(0, false);
      clientFactory
          .getEventBus()
          .fireEventFromSource(
              new RoadConfigTreeSelectEvent(RoadConfigTreeSelectEvent.Action.SELECT_ONE_ITEM),
              store.get(0));
    }
  }

  @UiHandler("grid")
  public void rowClick(RowClickEvent event) {
    RoadLineDTO dto = store.get(event.getRowIndex());
    if (dto != null) {
      GWT.log(dto.toString());
      fillFields(dto);
      clientFactory
          .getEventBus()
          .fireEventFromSource(
              new RoadConfigTreeSelectEvent(RoadConfigTreeSelectEvent.Action.SELECT_ONE_ITEM), dto);
    }
  }

  private void selectGridItem(RoadLineDTO dto) {
    if (dto != null) {
      if ("root".equals(dto.getLineId())) {
        if (store.size() > 0) grid.getSelectionModel().select(store.get(0), false);
        return;
      }
      GWT.log(dto.toString());
      fillFields(dto);
      grid.getSelectionModel().select(dto, false);
    }
  }

  @UiHandler("saveButton")
  public void saveButton(SelectEvent event) {
    GWT.log("saveButton Click!");
    if (!lineId.validate()) {
      Info.display(messages.info(), messages.roadLine_lineId() + " " + messages.info_notEmpty());
      return;
    }
    if (!lineName.validate()) {
      Info.display(messages.info(), messages.roadLine_lineName() + " " + messages.info_notEmpty());
      return;
    }
    final ConfirmMessageBox box =
        new ConfirmMessageBox(messages.message(), messages.message_itemSaveConfirm());
    box.addDialogHideHandler(
        new DialogHideHandler() {
          @Override
          public void onDialogHide(DialogHideEvent event) {
            if (event.getHideButton() == PredefinedButton.YES) {
              RoadLineDTO storeDto = store.findModelWithKey(lineId.getValue().trim());
              if (storeDto != null) {
                RoadLineDTO dto = fieldsToRoadLine();
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
    if (!lineId.validate()) {
      Info.display(messages.info(), messages.roadLine_lineId() + " " + messages.info_notEmpty());
      return;
    }
    if (!lineName.validate()) {
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
              RoadLineDTO storeDto = store.findModelWithKey(lineId.getValue().trim());
              if (storeDto == null) {
                RoadLineDTO dto = fieldsToRoadLine();
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
    if (!lineId.validate()) {
      Info.display(messages.info(), messages.roadLine_lineId() + " " + messages.info_notEmpty());
      return;
    }
    if (!lineName.validate()) {
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
              RoadLineDTO storeDto = store.findModelWithKey(lineId.getValue().trim());
              if (storeDto != null) {
                RoadLineDTO dto = fieldsToRoadLine();
                GWT.log("Delete dto = " + dto);
                presenter.removeItem(storeDto.getLineId());
              } else {
                Info.display(messages.message(), messages.message_itemNoExisted());
              }
            }
          }
        });
    box.show();
  }

  private void emptyFields() {
    lineId.setValue(null);
    lineName.setValue(null);
    startMileage.setValue(null);
    endMileage.setValue(null);
    gCodeId.setValue(null);
    memo.setValue(null);
    directionCombo.setValue(Direction.N);
    enable = true;
  }

  private void fillFields(RoadLineDTO dto) {
    lineId.setValue(dto.getLineId());
    lineName.setValue(dto.getLineName());
    startMileage.setValue(dto.getStartMileage());
    endMileage.setValue(dto.getEndMileage());
    gCodeId.setValue(dto.getGgCodeId());
    memo.setValue(dto.getMemo());
    directionCombo.setValue(dto.getDirection());
    enable = dto.isEnable();
  }

  private RoadLineDTO fieldsToRoadLine() {
    RoadLineDTO dto = new RoadLineDTO();
    dto.setLineId(lineId.getValue());
    dto.setLineName(lineName.getValue());
    dto.setDirection(directionCombo.getValue());
    dto.setStartMileage(startMileage.getValue());
    dto.setEndMileage(endMileage.getValue());
    dto.setEnable(true);
    dto.setMemo(memo.getValue());
    dto.setGgCodeId(gCodeId.getValue());
    dto.setEnable(enable);
    return dto;
  }

  private void initColumnModel() {
    List<ColumnConfig<RoadLineDTO, ?>> columnConfigs =
        new ArrayList<ColumnConfig<RoadLineDTO, ?>>();

    ColumnConfig<RoadLineDTO, String> lineId =
        new ColumnConfig<RoadLineDTO, String>(props.lineId());
    lineId.setWidth(80);
    lineId.setHeader(messages.roadLine_lineId());
    lineId.setHideable(true);
    lineId.setMenuDisabled(false);
    lineId.setSortable(true);
    columnConfigs.add(lineId);

    ColumnConfig<RoadLineDTO, String> lineName =
        new ColumnConfig<RoadLineDTO, String>(props.lineName());
    lineName.setWidth(160);
    lineName.setHeader(messages.roadLine_lineName());
    lineName.setHideable(true);
    lineName.setMenuDisabled(false);
    lineName.setSortable(true);
    columnConfigs.add(lineName);

    ColumnConfig<RoadLineDTO, String> direction =
        new ColumnConfig<RoadLineDTO, String>(props.direction);
    direction.setWidth(80);
    direction.setHeader(messages.roadLine_direction());
    direction.setHideable(true);
    direction.setMenuDisabled(false);
    direction.setSortable(true);
    columnConfigs.add(direction);

    ColumnConfig<RoadLineDTO, Integer> startMileage =
        new ColumnConfig<RoadLineDTO, Integer>(props.startMileage());
    startMileage.setWidth(80);
    startMileage.setHeader(messages.roadLine_startMileage());
    startMileage.setHideable(true);
    startMileage.setMenuDisabled(false);
    startMileage.setSortable(true);
    columnConfigs.add(startMileage);

    ColumnConfig<RoadLineDTO, Integer> endMileage =
        new ColumnConfig<RoadLineDTO, Integer>(props.endMileage());
    endMileage.setWidth(80);
    endMileage.setHeader(messages.roadLine_endMileage());
    endMileage.setHideable(true);
    endMileage.setMenuDisabled(false);
    endMileage.setSortable(true);
    columnConfigs.add(endMileage);

    ColumnConfig<RoadLineDTO, Integer> gCodeId =
        new ColumnConfig<RoadLineDTO, Integer>(props.ggCodeId());
    gCodeId.setWidth(80);
    gCodeId.setHeader(messages.roadLine_gCodeId());
    gCodeId.setHideable(true);
    gCodeId.setMenuDisabled(false);
    gCodeId.setSortable(true);
    columnConfigs.add(gCodeId);

    ColumnConfig<RoadLineDTO, String> memo = new ColumnConfig<RoadLineDTO, String>(props.memo());
    memo.setWidth(160);
    memo.setHeader(messages.roadLine_memo());
    memo.setHideable(true);
    memo.setMenuDisabled(false);
    memo.setSortable(true);
    columnConfigs.add(memo);

    cm = new ColumnModel<RoadLineDTO>(columnConfigs);
  }

  class RoadConfigTreeFireSelectedEventHandlerImpl
      implements RoadConfigTreeFireSelectedEventHandler {

    @Override
    public void selectOneItem(RoadConfigTreeFireSelectedEvent event) {
      GWT.log(
          "DeviceConfigViewer RoadConfigTreeFireSelectedEventHandlerImpl selectOneItem start...");
      RoadLineDTO dto = (RoadLineDTO) event.getSource();
      selectGridItem(dto);
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
          .fireEvent(new RoadLineUpdatedEvent(RoadLineUpdatedEvent.Action.ROADLINE_UPDATED));
    } else Info.display(messages.info(), messages.info_addFail());

    presenter.getRoadLines();
  }

  public void removeItemResult(Boolean result) {
    if (result) {
      Info.display(messages.info(), messages.info_removeSuccessfully());
      emptyFields();
      clientFactory
          .getEventBus()
          .fireEvent(new RoadLineUpdatedEvent(RoadLineUpdatedEvent.Action.ROADLINE_UPDATED));
    } else Info.display(messages.info(), messages.info_removeFail());

    presenter.getRoadLines();
  }

  public void saveItemResult(Boolean result) {
    if (result) {
      Info.display(messages.info(), messages.info_saveSuccessfully());
      clientFactory
          .getEventBus()
          .fireEvent(new RoadLineUpdatedEvent(RoadLineUpdatedEvent.Action.ROADLINE_UPDATED));
    } else Info.display(messages.info(), messages.info_saveFail());

    presenter.getRoadLines();
  }

  public void setGCodeDisplay(boolean check) {
    if (check == false) {
      gCodeId.hide();
      gCodeIdLabel.hide();
      cm.getColumn(5).setHidden(true);
    }
  }

  @UiHandler("refreshButton")
  public void refreshButton(SelectEvent event) {
    GWT.log("refreshButton start...");
    clientFactory
        .getEventBus()
        .fireEvent(new RoadLineUpdatedEvent(RoadLineUpdatedEvent.Action.ROADLINE_UPDATED));
  }
}
