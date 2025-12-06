/*
 * © HwaCom Systems Inc. 2015
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.cam.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.google.web.bindery.autobean.shared.AutoBean;
import com.google.web.bindery.autobean.shared.AutoBeanFactory;
import com.hwacom.ngtms.c.dis.shared.QueryRareWordListMessage;
import com.hwacom.ngtms.c.dis.shared.QueryRareWordMessage;
import com.hwacom.ngtms.c.dis.shared.QueryRareWordResultMessage;
import com.hwacom.ngtms.cam.client.event.RareWordQueryViewerEvent;
import com.hwacom.ngtms.cam.client.event.RareWordQueryViewerEvent.Action;
import com.hwacom.ngtms.cam.client.ui.dnd.RemoveDeviceUtil;
import com.hwacom.ngtms.cam.view.Messages;
import com.hwacom.ngtms.cam.vo.RareWordVO;
import com.hwacom.ngtms.hcce.am.factory.ClientFactory;
import com.sencha.gxt.chart.client.draw.DrawComponent;
import com.sencha.gxt.chart.client.draw.RGB;
import com.sencha.gxt.chart.client.draw.sprite.RectangleSprite;
import com.sencha.gxt.core.client.Style.SelectionMode;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;
import com.sencha.gxt.data.shared.SortDir;
import com.sencha.gxt.data.shared.Store.StoreSortInfo;
import com.sencha.gxt.data.shared.loader.JsonReader;
import com.sencha.gxt.data.shared.writer.JsonWriter;
import com.sencha.gxt.widget.core.client.Composite;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.HorizontalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.grid.GroupingView;
import com.sencha.gxt.widget.core.client.selection.SelectionChangedEvent;
import com.sencha.gxt.widget.core.client.selection.SelectionChangedEvent.SelectionChangedHandler;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RareWordQueryViewer extends Composite {

  private static RareWordQueryViewerUiBinder uiBinder =
      GWT.create(RareWordQueryViewerUiBinder.class);

  interface RareWordQueryViewerUiBinder extends UiBinder<Widget, RareWordQueryViewer> {}

  private Messages messages = GWT.create(Messages.class);

  private RareWordDTOPropertyAccess propertyAccess = GWT.create(RareWordDTOPropertyAccess.class);

  private ClientFactory clientFactory = GWT.create(ClientFactory.class);

  private final MessageAutoBeanFactory factory = GWT.create(MessageAutoBeanFactory.class);

  @UiField HorizontalLayoutContainer horizontalLayoutContainer;

  @UiField Grid<RareWordVO> grid;

  @UiField(provided = true)
  ListStore<RareWordVO> listStore;

  @UiField(provided = true)
  ColumnModel<RareWordVO> columnModel;

  ColumnConfig<RareWordVO, String> displayNameColumn;

  @UiField GroupingView<RareWordVO> groupingView;

  @UiField VerticalLayoutContainer verticalLayoutContainer;

  private DrawComponent drawComponent;

  @UiField Label displayName;

  @UiField Label code;

  @UiField TextButton queryList;

  @UiField TextButton query;

  public RareWordQueryViewer() {
    listStore = new ListStore<>(propertyAccess.id());
    listStore.addSortInfo(new StoreSortInfo<>(propertyAccess.codeId(), SortDir.ASC));
    initColumnModel();
    initWidget(uiBinder.createAndBindUi(this));
    RemoveDeviceUtil.buildHasDeviceGrid(grid);
    grid.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    groupingView.groupBy(displayNameColumn);
    addEventHandlers();
  }

  private void initColumnModel() {
    List<ColumnConfig<RareWordVO, ?>> columnConfigs = new ArrayList<>();
    displayNameColumn = new ColumnConfig<>(propertyAccess.displayName());
    columnConfigs.add(displayNameColumn);

    ColumnConfig<RareWordVO, String> code = new ColumnConfig<>(propertyAccess.codeId());
    code.setHeader(messages.rareWord_query_code());
    columnConfigs.add(code);

    columnModel = new ColumnModel<>(columnConfigs);
  }

  private void addEventHandlers() {
    grid.getSelectionModel()
        .addSelectionHandler(
            new SelectionHandler<RareWordVO>() {
              @Override
              public void onSelection(SelectionEvent<RareWordVO> event) {
                RareWordVO selectedItem = event.getSelectedItem();
                if (selectedItem.getCodeId() == null) {
                  clearRareWordInfo();
                } else {
                  displayName.setText(selectedItem.getDisplayName());
                  code.setText(selectedItem.getCodeId());
                  query.enable();
                }
              }
            });

    grid.getSelectionModel()
        .addSelectionChangedHandler(
            new SelectionChangedHandler<RareWordVO>() {
              @Override
              public void onSelectionChanged(SelectionChangedEvent<RareWordVO> event) {
                if (event.getSelection().isEmpty()) {
                  clearRareWordInfo();
                }
              }
            });
  }

  private void clearRareWordInfo() {
    displayName.setText(null);
    code.setText(null);
    if (drawComponent != null) {
      verticalLayoutContainer.remove(drawComponent);
    }
    query.disable();
  }

  public void updateList(String json) {
    JSONValue jsonValue = JSONParser.parseStrict(json);
    JSONArray jsonArray = jsonValue.isArray();
    List<RareWordVO> list = new ArrayList<>();
    for (int i = 0; i < jsonArray.size(); i++) {
      JSONValue value = jsonArray.get(i);
      JSONObject object = value.isObject();
      JSONValue deviceName = object.get("deviceName");
      JSONValue codeId = object.get("codeId");
      RareWordVO dto =
          new RareWordVO(deviceName.isString().stringValue(), codeId.isString().stringValue());
      list.add(dto);
    }

    Map<String, String> displayNameMap = new HashMap<>();
    for (RareWordVO each : listStore.getAll()) {
      displayNameMap.put(each.getDeviceName(), each.getDisplayName());
    }

    for (RareWordVO each : list) {
      RareWordVO dumb = listStore.findModelWithKey(each.getDeviceName());
      if (dumb != null) {
        listStore.remove(dumb);
      }
      RareWordVO dto = listStore.findModel(each);
      if (dto == null) {
        each.setDisplayName(displayNameMap.get(each.getDeviceName()));
        listStore.add(each);
      }
    }
  }

  public void update(String json) {
    JsonReader<QueryRareWordResultMessage, QueryRareWordResultMessage> jsonReader =
        new JsonReader<>(factory, QueryRareWordResultMessage.class);
    QueryRareWordResultMessage result = jsonReader.read(null, json);
    draw(result);
  }

  private void draw(QueryRareWordResultMessage message) {
    if (drawComponent != null) {
      verticalLayoutContainer.remove(drawComponent);
    }

    int rectangleSize = 5;
    drawComponent =
        new DrawComponent(message.getWidth() * rectangleSize, message.getHeight() * rectangleSize);
    int index = 0;
    for (int heightIndex = 0; heightIndex < message.getHeight(); heightIndex++) {
      for (int widthIndex = 0; widthIndex < message.getWidth(); widthIndex++) {
        RectangleSprite rectangleSprite = new RectangleSprite(rectangleSize, rectangleSize);
        rectangleSprite.setX(widthIndex * rectangleSize);
        rectangleSprite.setY(heightIndex * rectangleSize);
        if (message.getTrueIndices().contains(index)) {
          rectangleSprite.setFill(RGB.RED);
        } else {
          rectangleSprite.setFill(RGB.BLACK);
        }
        drawComponent.addSprite(rectangleSprite);
        index++;
      }
    }
    verticalLayoutContainer.add(drawComponent);
    verticalLayoutContainer.forceLayout();
  }

  public void queryFinished() {
    unmask();
  }

  @UiHandler("queryList")
  public void onQueryList(SelectEvent event) {
    mask(messages.inProgress());
    final List<String> deviceNames = new ArrayList<String>();
    for (RareWordVO each : listStore.getAll()) {
      if (!deviceNames.contains(each.getDeviceName())) {
        deviceNames.add(each.getDeviceName());
      }
    }
    AutoBean<QueryRareWordListMessage> autoBean = factory.queryListMessage();
    QueryRareWordListMessage message = autoBean.as();
    message.setType(QueryRareWordListMessage.TYPE);
    message.setDeviceNames(deviceNames);
    JsonWriter<QueryRareWordListMessage> jsonWriter =
        new JsonWriter<>(factory, QueryRareWordListMessage.class);
    String json = jsonWriter.write(message);
    clientFactory
        .getEventBus()
        .fireEventFromSource(new RareWordQueryViewerEvent(Action.QUERY_LIST), json);
  }

  @UiHandler("query")
  public void onQuery(SelectEvent event) {
    mask(messages.inProgress());
    RareWordVO dto = grid.getSelectionModel().getSelectedItem();
    AutoBean<QueryRareWordMessage> autoBean = factory.queryMessage();
    QueryRareWordMessage message = autoBean.as();
    message.setType(QueryRareWordMessage.TYPE);
    message.setDeviceName(dto.getDeviceName());
    message.setCodeId(dto.getCodeId());
    JsonWriter<QueryRareWordMessage> jsonWriter =
        new JsonWriter<>(factory, QueryRareWordMessage.class);
    String json = jsonWriter.write(message);
    clientFactory
        .getEventBus()
        .fireEventFromSource(new RareWordQueryViewerEvent(Action.QUERY), json);
  }

  public void addDevices(List<RareWordVO> list) {
    List<RareWordVO> allDevices = listStore.getAll();
    for (RareWordVO each : list) {
      if (!allDevices.contains(each)) {
        listStore.add(each);
      }
    }
    changeQueryListButtonState();
  }

  private void changeQueryListButtonState() {
    if (listStore.getAll().isEmpty()) {
      queryList.disable();
    } else {
      queryList.enable();
    }
  }

  public void removeDevices(List<String> deviceNames) {
    for (RareWordVO each : new ArrayList<>(listStore.getAll())) {
      if (deviceNames.contains(each.getDeviceName())) {
        listStore.remove(each);
      }
    }
    changeQueryListButtonState();
    groupingView.refresh(false);
  }

  public void forceLayout() {
    horizontalLayoutContainer.forceLayout();
  }

  interface RareWordDTOPropertyAccess extends PropertyAccess<RareWordVO> {
    ModelKeyProvider<RareWordVO> id();

    ValueProvider<RareWordVO, String> displayName();

    ValueProvider<RareWordVO, String> codeId();
  }

  interface MessageAutoBeanFactory extends AutoBeanFactory {
    AutoBean<QueryRareWordListMessage> queryListMessage();

    AutoBean<QueryRareWordMessage> queryMessage();

    AutoBean<QueryRareWordResultMessage> queryResultMessage();
  }
}
