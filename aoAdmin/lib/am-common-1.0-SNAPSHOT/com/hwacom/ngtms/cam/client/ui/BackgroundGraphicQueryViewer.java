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
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.google.web.bindery.autobean.shared.AutoBean;
import com.google.web.bindery.autobean.shared.AutoBeanFactory;
import com.hwacom.ngtms.c.dis.shared.QueryBackgroundGraphicListMessage;
import com.hwacom.ngtms.c.dis.shared.QueryBackgroundGraphicMessage;
import com.hwacom.ngtms.cam.client.event.BackgroundGraphicQueryViewerEvent;
import com.hwacom.ngtms.cam.client.event.BackgroundGraphicQueryViewerEvent.Action;
import com.hwacom.ngtms.cam.client.ui.dnd.RemoveDeviceUtil;
import com.hwacom.ngtms.cam.view.Messages;
import com.hwacom.ngtms.cam.vo.BackgroundGraphicVO;
import com.hwacom.ngtms.hcce.am.factory.ClientFactory;
import com.sencha.gxt.core.client.Style.SelectionMode;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;
import com.sencha.gxt.data.shared.SortDir;
import com.sencha.gxt.data.shared.Store.StoreSortInfo;
import com.sencha.gxt.data.shared.writer.JsonWriter;
import com.sencha.gxt.widget.core.client.Composite;
import com.sencha.gxt.widget.core.client.button.TextButton;
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

public class BackgroundGraphicQueryViewer extends Composite {

  private static BackgroundGraphicQueryViewerUiBinder uiBinder =
      GWT.create(BackgroundGraphicQueryViewerUiBinder.class);

  interface BackgroundGraphicQueryViewerUiBinder
      extends UiBinder<Widget, BackgroundGraphicQueryViewer> {}

  private BackgroundGraphicPropertyAccess propertyAccess =
      GWT.create(BackgroundGraphicPropertyAccess.class);

  private Messages messages = GWT.create(Messages.class);

  private ClientFactory clientFactory = GWT.create(ClientFactory.class);

  private final BackgroundAutoBeanFactory factory = GWT.create(BackgroundAutoBeanFactory.class);

  @UiField Grid<BackgroundGraphicVO> grid;

  @UiField(provided = true)
  ListStore<BackgroundGraphicVO> listStore;

  @UiField(provided = true)
  ColumnModel<BackgroundGraphicVO> columnModel;

  ColumnConfig<BackgroundGraphicVO, String> displayNameColumn;

  @UiField GroupingView<BackgroundGraphicVO> groupingView;

  @UiField Label displayName;

  @UiField Label codeId;

  @UiField Label description;

  @UiField Image graphic;

  @UiField TextButton queryList;

  @UiField TextButton query;

  public BackgroundGraphicQueryViewer() {
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
    List<ColumnConfig<BackgroundGraphicVO, ?>> configs = new ArrayList<>();
    displayNameColumn = new ColumnConfig<>(propertyAccess.displayName());
    configs.add(displayNameColumn);

    ColumnConfig<BackgroundGraphicVO, Integer> codeId = new ColumnConfig<>(propertyAccess.codeId());
    codeId.setHeader(messages.backgroundGraphic_query_codeId());
    configs.add(codeId);

    columnModel = new ColumnModel<>(configs);
  }

  private void addEventHandlers() {
    grid.getSelectionModel()
        .addSelectionHandler(
            new SelectionHandler<BackgroundGraphicVO>() {
              @Override
              public void onSelection(SelectionEvent<BackgroundGraphicVO> event) {
                BackgroundGraphicVO selectedItem = event.getSelectedItem();
                if (selectedItem.getCodeId() == null) {
                  clearGraphicInfo();
                } else {
                  displayName.setText(selectedItem.getDisplayName());
                  codeId.setText(Integer.toString(selectedItem.getCodeId()));
                  description.setText(selectedItem.getDescription());
                  query.enable();
                }
              }
            });

    grid.getSelectionModel()
        .addSelectionChangedHandler(
            new SelectionChangedHandler<BackgroundGraphicVO>() {
              @Override
              public void onSelectionChanged(SelectionChangedEvent<BackgroundGraphicVO> event) {
                if (event.getSelection().isEmpty()) {
                  clearGraphicInfo();
                }
              }
            });
  }

  protected void clearGraphicInfo() {
    displayName.setText(null);
    codeId.setText(null);
    description.setText(null);
    graphic.setUrl("");
    query.disable();
  }

  public void updateGraphicList(String json) {
    JSONValue jsonValue = JSONParser.parseStrict(json);
    JSONArray jsonArray = jsonValue.isArray();
    List<BackgroundGraphicVO> graphicList = new ArrayList<>();
    for (int i = 0; i < jsonArray.size(); i++) {
      JSONValue value = jsonArray.get(i);
      JSONObject object = value.isObject();
      JSONValue deviceName = object.get("deviceName");
      JSONValue codeId = object.get("codeId");
      BackgroundGraphicVO dto =
          new BackgroundGraphicVO(
              deviceName.isString().stringValue(), (int) codeId.isNumber().doubleValue());
      graphicList.add(dto);
    }

    Map<String, String> displayNameMap = new HashMap<>();
    for (BackgroundGraphicVO each : listStore.getAll()) {
      displayNameMap.put(each.getDeviceName(), each.getDisplayName());
    }
    for (BackgroundGraphicVO each : graphicList) {
      BackgroundGraphicVO dumb = listStore.findModelWithKey(each.getDeviceName());
      if (dumb != null) {
        listStore.remove(dumb);
      }
      BackgroundGraphicVO dto = listStore.findModel(each);
      if (dto == null) {
        each.setDisplayName(displayNameMap.get(each.getDeviceName()));
        listStore.add(each);
      } else {
        dto.setDescription(each.getDescription());
        listStore.update(dto);
      }
    }
  }

  public void updateGraphic(String base64) {
    graphic.setUrl("data:image/png;base64," + base64);
  }

  public void queryFinished() {
    unmask();
  }

  public void addDevices(List<BackgroundGraphicVO> list) {
    List<BackgroundGraphicVO> allDevices = listStore.getAll();
    for (BackgroundGraphicVO each : list) {
      if (!allDevices.contains(each)) {
        listStore.add(each);
      }
    }
    actForDeviceListChanged();
  }

  private void actForDeviceListChanged() {
    if (listStore.getAll().isEmpty()) {
      queryList.disable();
    } else {
      queryList.enable();
    }
  }

  public void removeDevices(List<String> deviceNames) {
    for (BackgroundGraphicVO each : new ArrayList<>(listStore.getAll())) {
      if (deviceNames.contains(each.getDeviceName())) {
        listStore.remove(each);
      }
    }
    actForDeviceListChanged();
  }

  @UiHandler("queryList")
  public void onQueryList(SelectEvent event) {
    mask(messages.inProgress());

    final List<String> deviceNames = new ArrayList<String>();
    for (BackgroundGraphicVO each : listStore.getAll()) {
      if (!deviceNames.contains(each.getDeviceName())) {
        deviceNames.add(each.getDeviceName());
      }
    }
    AutoBean<QueryBackgroundGraphicListMessage> autoBean = factory.queryListMessage();
    QueryBackgroundGraphicListMessage message = autoBean.as();
    message.setType(QueryBackgroundGraphicListMessage.TYPE);
    message.setDeviceNames(deviceNames);
    JsonWriter<QueryBackgroundGraphicListMessage> jsonWriter =
        new JsonWriter<>(factory, QueryBackgroundGraphicListMessage.class);
    String json = jsonWriter.write(message);
    clientFactory
        .getEventBus()
        .fireEventFromSource(new BackgroundGraphicQueryViewerEvent(Action.QUERY_LIST), json);
  }

  @UiHandler("query")
  public void onQuery(SelectEvent event) {
    mask(messages.inProgress());
    AutoBean<QueryBackgroundGraphicMessage> autoBean = factory.queryMessage();
    QueryBackgroundGraphicMessage message = autoBean.as();
    message.setType(QueryBackgroundGraphicMessage.TYPE);
    BackgroundGraphicVO dto = grid.getSelectionModel().getSelectedItem();
    message.setDeviceName(dto.getDeviceName());
    message.setCodeId(dto.getCodeId());
    JsonWriter<QueryBackgroundGraphicMessage> jsonWriter =
        new JsonWriter<>(factory, QueryBackgroundGraphicMessage.class);
    String json = jsonWriter.write(message);
    clientFactory
        .getEventBus()
        .fireEventFromSource(new BackgroundGraphicQueryViewerEvent(Action.QUERY_GRAPHIC), json);
  }

  interface BackgroundGraphicPropertyAccess extends PropertyAccess<BackgroundGraphicVO> {
    ModelKeyProvider<BackgroundGraphicVO> id();

    ValueProvider<BackgroundGraphicVO, String> displayName();

    ValueProvider<BackgroundGraphicVO, Integer> codeId();

    ValueProvider<BackgroundGraphicVO, String> description();
  }

  interface BackgroundAutoBeanFactory extends AutoBeanFactory {
    AutoBean<QueryBackgroundGraphicListMessage> queryListMessage();

    AutoBean<QueryBackgroundGraphicMessage> queryMessage();
  }
}
