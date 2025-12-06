/*
 * © HwaCom Systems Inc. 2013
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.hcce.am.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor.Path;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Widget;
import com.hwacom.ngtms.hcce.am.event.TopologyConfigEvent;
import com.hwacom.ngtms.hcce.am.event.TopologyConfigEvent.Action;
import com.hwacom.ngtms.hcce.am.event.TopologyConfigEvent.TopologyConfigEventHandler;
import com.hwacom.ngtms.hcce.am.factory.ClientFactory;
import com.hwacom.ngtms.hcce.am.presenter.TopologyNodeCfgPresenter;
import com.hwacom.ngtms.hcce.shared.dto.TopologyGroup;
import com.hwacom.ngtms.hcce.shared.dto.TopologyNodeCfgVO;
import com.sencha.gxt.core.client.Style.SelectionMode;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.LabelProvider;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;
import com.sencha.gxt.widget.core.client.Composite;
import com.sencha.gxt.widget.core.client.Dialog.PredefinedButton;
import com.sencha.gxt.widget.core.client.box.ConfirmMessageBox;
import com.sencha.gxt.widget.core.client.event.DialogHideEvent;
import com.sencha.gxt.widget.core.client.event.DialogHideEvent.DialogHideHandler;
import com.sencha.gxt.widget.core.client.event.RowClickEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.form.TextArea;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.grid.GridView;
import com.sencha.gxt.widget.core.client.info.Info;
import java.util.ArrayList;
import java.util.List;

public class TopologyNodeCfgViewer extends Composite {

  private static TopologyNodeCfgViewerUiBinder uiBinder =
      GWT.create(TopologyNodeCfgViewerUiBinder.class);

  interface TopologyNodeCfgViewerUiBinder extends UiBinder<Widget, TopologyNodeCfgViewer> {}

  private static final TopologyNodeConfigProperties props =
      GWT.create(TopologyNodeConfigProperties.class);
  private static final Messages messages = GWT.create(Messages.class);
  private TopologyNodeCfgPresenter presenter;
  private final ClientFactory clientFactory = GWT.create(ClientFactory.class);
  private final HandlerRegistration handlerRegistration;
  private TopologyGroup topologyGroup;

  @UiField(provided = true)
  ColumnModel<TopologyNodeCfgVO> cm;

  @UiField(provided = true)
  ListStore<TopologyNodeCfgVO> store;

  @UiField GridView<TopologyNodeCfgVO> view;
  @UiField Grid<TopologyNodeCfgVO> grid;
  @UiField TextField groupName;
  @UiField TextField nodeName;
  @UiField TextArea description;

  public TopologyNodeCfgViewer() {
    store = new ListStore<TopologyNodeCfgVO>(props.key());
    initColumnModel();
    initWidget(uiBinder.createAndBindUi(this));
    grid.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    handlerRegistration =
        clientFactory
            .getEventBus()
            .addHandler(TopologyConfigEvent.TYPE, new DefaultTopologyConfigEventHandler());
    presenter = new TopologyNodeCfgPresenter(this);
  }

  private void initColumnModel() {
    List<ColumnConfig<TopologyNodeCfgVO, ?>> columnConfigs =
        new ArrayList<ColumnConfig<TopologyNodeCfgVO, ?>>();

    // Configure column groupName
    ColumnConfig<TopologyNodeCfgVO, String> groupNameConfig =
        new ColumnConfig<TopologyNodeCfgVO, String>(props.groupName());
    groupNameConfig.setWidth(160);
    groupNameConfig.setHeader(messages.topologyConfigGrid_groupName());
    groupNameConfig.setHideable(false);
    groupNameConfig.setMenuDisabled(true);
    groupNameConfig.setSortable(false);
    columnConfigs.add(groupNameConfig);

    // Configure column nodeName
    ColumnConfig<TopologyNodeCfgVO, String> nodeNameConfig =
        new ColumnConfig<TopologyNodeCfgVO, String>(props.nodeName());
    nodeNameConfig.setWidth(160);
    nodeNameConfig.setHeader(messages.topologyConfigGrid_nodeName());
    nodeNameConfig.setHideable(false);
    nodeNameConfig.setMenuDisabled(true);
    nodeNameConfig.setSortable(false);
    columnConfigs.add(nodeNameConfig);

    // Configure column description
    ColumnConfig<TopologyNodeCfgVO, String> descriptionConfig =
        new ColumnConfig<TopologyNodeCfgVO, String>(props.description());
    descriptionConfig.setWidth(350);
    descriptionConfig.setHeader(messages.topologyConfigGrid_description());
    descriptionConfig.setHideable(false);
    descriptionConfig.setMenuDisabled(true);
    descriptionConfig.setSortable(false);
    columnConfigs.add(descriptionConfig);

    cm = new ColumnModel<TopologyNodeCfgVO>(columnConfigs);
  }

  @UiHandler("addButton")
  public void addButton(SelectEvent event) {
    GWT.log("addButton Click!");
    if (!groupName.validate()) return;
    if (!nodeName.validate()) return;
    TopologyNodeCfgVO storeVo = store.findModelWithKey(groupName.getValue() + nodeName.getValue());
    if (storeVo == null) {
      TopologyNodeCfgVO vo = new TopologyNodeCfgVO();
      vo.setGroupName(groupName.getValue());
      vo.setNodeName(nodeName.getValue());
      vo.setDescription(description.getValue());
      clientFactory
          .getEventBus()
          .fireEventFromSource(new TopologyConfigEvent(Action.ADD, topologyGroup), vo);
    } else {
      Info.display(messages.message(), messages.message_itemExisted());
    }
  }

  @UiHandler("deleteButton")
  public void deleteButton(SelectEvent event) {
    GWT.log("deleteButton Click!");
    if (!groupName.validate()) return;
    if (!nodeName.validate()) return;
    final ConfirmMessageBox box =
        new ConfirmMessageBox(messages.message(), messages.message_itemDeleteConfirm());
    box.addDialogHideHandler(
        new DialogHideHandler() {
          @Override
          public void onDialogHide(DialogHideEvent event) {
            if (event.getHideButton() == PredefinedButton.YES) {
              clientFactory
                  .getEventBus()
                  .fireEvent(new TopologyConfigEvent(Action.DELETE, topologyGroup));
            }
          }
        });
    box.show();
  }

  @UiHandler("saveButton")
  public void saveButton(SelectEvent event) {
    GWT.log("saveButton Click!");
    if (!groupName.validate()) return;
    if (!nodeName.validate()) return;
    TopologyNodeCfgVO storeVo = store.findModelWithKey(groupName.getValue() + nodeName.getValue());
    if (storeVo != null) {
      TopologyNodeCfgVO vo = new TopologyNodeCfgVO();
      vo.setGroupName(groupName.getValue());
      vo.setNodeName(nodeName.getValue());
      vo.setDescription(description.getValue());
      clientFactory
          .getEventBus()
          .fireEventFromSource(new TopologyConfigEvent(Action.SAVE, topologyGroup), vo);
    } else {
      Info.display(messages.message(), messages.message_itemNoExisted());
    }
  }

  @UiHandler("grid")
  public void rowClick(RowClickEvent event) {
    TopologyNodeCfgVO vo = store.get(event.getRowIndex());
    if (vo != null) {
      groupName.setValue(vo.getGroupName());
      nodeName.setValue(vo.getNodeName());
      description.setValue(vo.getDescription());
    }
  }

  interface TopologyNodeConfigProperties extends PropertyAccess<TopologyNodeCfgVO> {
    @Path("key")
    ModelKeyProvider<TopologyNodeCfgVO> key();

    @Path("nodeName")
    LabelProvider<TopologyNodeCfgVO> nameLabel();

    ValueProvider<TopologyNodeCfgVO, String> groupName();

    ValueProvider<TopologyNodeCfgVO, String> nodeName();

    ValueProvider<TopologyNodeCfgVO, String> description();
  }

  @Override
  protected void onUnload() {
    super.onUnload();
    handlerRegistration.removeHandler();
  }

  public void init(List<TopologyNodeCfgVO> TopologyConfigs) {
    store.clear();
    store.addAll(TopologyConfigs);
  }

  public void addStore(TopologyNodeCfgVO vo) {
    store.add(vo);
    Info.display(messages.message(), messages.message_addSuccessfully());
  }

  public void removeStore(String groupName, String nodeName) {
    TopologyNodeCfgVO vo = store.findModelWithKey(groupName + nodeName);
    if (vo != null) {
      store.remove(vo);
      Info.display(messages.message(), messages.message_deleteSuccessfully());
    }
  }

  public void updateSotre(TopologyNodeCfgVO vo) {
    GWT.log("updateSotre vo=" + vo);
    TopologyNodeCfgVO updateVo = store.findModelWithKey(vo.getGroupName() + vo.getNodeName());
    if (updateVo != null) {
      store.update(vo);
      Info.display(messages.message(), messages.message_saveSuccessfully());
    }
  }

  public void setPresenter(TopologyNodeCfgPresenter presenter) {
    this.presenter = presenter;
  }

  public void setTopologyGroup(TopologyGroup topologyGroup) {
    this.topologyGroup = topologyGroup;
    presenter.init(topologyGroup);
  }

  public TopologyGroup getTopologyGroup() {
    return this.topologyGroup;
  }

  class DefaultTopologyConfigEventHandler implements TopologyConfigEventHandler {

    @Override
    public void onSave(TopologyConfigEvent event) {
      GWT.log(
          "onSave...event.getTopologyGroup():"
              + event.getTopologyGroup()
              + "; me TopologyGroup :"
              + topologyGroup);
      if (topologyGroup == event.getTopologyGroup()) {
        TopologyNodeCfgVO vo = (TopologyNodeCfgVO) event.getSource();
        presenter.saveItem(vo);
      }
    }

    @Override
    public void onAdd(TopologyConfigEvent event) {
      GWT.log(
          "onAdd...event.getTopologyGroup():"
              + event.getTopologyGroup()
              + "; me TopologyGroup :"
              + topologyGroup);
      if (topologyGroup == event.getTopologyGroup()) {
        TopologyNodeCfgVO vo = (TopologyNodeCfgVO) event.getSource();
        presenter.addItem(vo);
      } else {
      }
    }

    @Override
    public void onDelete(TopologyConfigEvent event) {
      GWT.log(
          "onDelete...event.getTopologyGroup():"
              + event.getTopologyGroup()
              + "; me TopologyGroup :"
              + topologyGroup);
      if (topologyGroup == event.getTopologyGroup()) {
        presenter.removeItem(groupName.getValue(), nodeName.getValue());
      }
    }
  }
}
