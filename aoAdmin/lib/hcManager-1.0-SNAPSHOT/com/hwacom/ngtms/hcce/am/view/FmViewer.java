/*
 * © HwaCom Systems Inc. 2013
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.hcce.am.view;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor.Path;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.safecss.shared.SafeStyles;
import com.google.gwt.safecss.shared.SafeStylesUtils;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.Widget;
import com.hwacom.ngtms.hcce.am.event.FmEvent;
import com.hwacom.ngtms.hcce.am.event.FmEvent.Action;
import com.hwacom.ngtms.hcce.am.event.FmEvent.FmEventHandler;
import com.hwacom.ngtms.hcce.am.event.MaskRootEvent;
import com.hwacom.ngtms.hcce.am.event.MaskRootEvent.MaskRootEventHandler;
import com.hwacom.ngtms.hcce.am.factory.ClientFactory;
import com.hwacom.ngtms.hcce.am.presenter.FmPresenter;
import com.hwacom.ngtms.hcce.shared.dto.DynamicConfigDTO;
import com.hwacom.ngtms.hcce.shared.dto.FmeDefinitionDTO;
import com.hwacom.ngtms.hcce.shared.dto.NodeDTO;
import com.hwacom.ngtms.hcce.shared.dto.TopologyGroup;
import com.sencha.gxt.cell.core.client.TextButtonCell;
import com.sencha.gxt.core.client.Style.SelectionMode;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.core.client.resources.CommonStyles;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.core.client.util.ToggleGroup;
import com.sencha.gxt.data.shared.LabelProvider;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;
import com.sencha.gxt.data.shared.SortDir;
import com.sencha.gxt.data.shared.Store.StoreSortInfo;
import com.sencha.gxt.dnd.core.client.DND.Feedback;
import com.sencha.gxt.dnd.core.client.DND.Operation;
import com.sencha.gxt.dnd.core.client.DropTarget;
import com.sencha.gxt.dnd.core.client.ListViewDragSource;
import com.sencha.gxt.dnd.core.client.ListViewDropTarget;
import com.sencha.gxt.widget.core.client.Composite;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.Dialog.PredefinedButton;
import com.sencha.gxt.widget.core.client.FramedPanel;
import com.sencha.gxt.widget.core.client.ListView;
import com.sencha.gxt.widget.core.client.box.ConfirmMessageBox;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.HorizontalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.HorizontalLayoutContainer.HorizontalLayoutData;
import com.sencha.gxt.widget.core.client.event.DialogHideEvent;
import com.sencha.gxt.widget.core.client.event.DialogHideEvent.DialogHideHandler;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.form.Radio;
import com.sencha.gxt.widget.core.client.form.TextArea;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.grid.GridView;
import com.sencha.gxt.widget.core.client.info.Info;
import java.util.ArrayList;
import java.util.List;

public class FmViewer extends Composite {

  private static FmViewerUiBinder uiBinder = GWT.create(FmViewerUiBinder.class);
  private final HandlerRegistration eventHandlerRegistration;

  interface FmViewerUiBinder extends UiBinder<Widget, FmViewer> {}

  private static final FmeDefinitionProperties props = GWT.create(FmeDefinitionProperties.class);
  private static final NodeProperties nodeProps = GWT.create(NodeProperties.class);
  private static final DynamicConfigProperties dynamicConfigProps =
      GWT.create(DynamicConfigProperties.class);
  private static final Messages messages = GWT.create(Messages.class);
  private String listViewGroup1 = "nodeListViewGroup1";
  private String listViewGroup2 = "nodeListViewGroup2";
  private final ClientFactory clientFactory = GWT.create(ClientFactory.class);
  private final HandlerRegistration handlerRegistration;
  private Timer timer;
  private Timer resetFmeTimer;
  private TopologyGroup topologyGroup;
  private boolean fmEnable = true;
  private FmPresenter presenter;

  private ToggleGroup toggle;

  private String userId;

  ListStore<NodeDTO> allNodeStore;
  ListStore<NodeDTO> selectedNodeStore;
  ListView<NodeDTO, String> allListView;
  ListView<NodeDTO, String> selectedListView;

  @UiField FramedPanel fmeDefinitionPanel;

  @UiField(provided = true)
  ColumnModel<FmeDefinitionDTO> cm;

  @UiField(provided = true)
  ListStore<FmeDefinitionDTO> store;

  @UiField GridView<FmeDefinitionDTO> view;
  @UiField Grid<FmeDefinitionDTO> grid;
  @UiField HorizontalLayoutContainer listContainer;
  @UiField TextField groupName;
  @UiField TextField fmeName;
  @UiField TextField className;
  @UiField TextArea description;
  @UiField Radio enableRadio;
  @UiField Radio disableRadio;
  @UiField TextButton deleteButton;
  @UiField TextButton cancel;
  @UiField TextField dynamicConfigName;
  @UiField TextField dynamicConfigValue;

  @UiField(provided = true)
  ColumnModel<DynamicConfigDTO> dynamicConfigCm;

  @UiField(provided = true)
  ListStore<DynamicConfigDTO> dynamicConfigStore;

  @UiField GridView<DynamicConfigDTO> dynamicConfigView;
  @UiField Grid<DynamicConfigDTO> dynamicConfigGrid;

  public FmViewer() {
    store = new ListStore<FmeDefinitionDTO>(props.key());
    dynamicConfigStore = new ListStore<DynamicConfigDTO>(dynamicConfigProps.key());
    initColumnModel();
    initDynamicConfigColumnModel();
    initWidget(uiBinder.createAndBindUi(this));
    grid.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    dynamicConfigGrid.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    handlerRegistration =
        clientFactory.getEventBus().addHandler(FmEvent.TYPE, new DefaultFmEventHandler());
    dynamicConfigStore.addSortInfo(
        new StoreSortInfo<DynamicConfigDTO>(dynamicConfigProps.name(), SortDir.ASC));
    createFeatchDataTimer();
    presenter = new FmPresenter(this);
    // set name on radios or use toggle group
    toggle = new ToggleGroup();
    toggle.add(enableRadio);
    toggle.add(disableRadio);
    toggle.addValueChangeHandler(
        new ValueChangeHandler<HasValue<Boolean>>() {
          @Override
          public void onValueChange(ValueChangeEvent<HasValue<Boolean>> event) {
            ToggleGroup group = (ToggleGroup) event.getSource();
            Radio radio = (Radio) group.getValue();
            if (radio.getBoxLabel().asString().equals(messages.fm_enable())) {
              GWT.log("enable radio...");
              fmEnable = true;
            } else if (radio.getBoxLabel().asString().equals(messages.fm_disable())) {
              GWT.log("disable radio...");
              fmEnable = false;
            }
            // Info.display("Color Changed",
            // "You selected " + radio.getBoxLabel());
          }
        });
    eventHandlerRegistration =
        clientFactory
            .getEventBus()
            .addHandler(
                MaskRootEvent.TYPE,
                new MaskRootEventHandler() {

                  @Override
                  public void onMask(MaskRootEvent event) {
                    GWT.log("FmViewerImpl Get the MaskRootEvent TO_MASK action");
                    if (timer != null) timer.cancel();
                  }
                });
    addEventHandlers();
  }

  private void addEventHandlers() {
    grid.getSelectionModel()
        .addSelectionChangedHandler(
            event -> {
              boolean enabled = grid.getSelectionModel().getSelectedItem() != null;
              cancel.setEnabled(enabled);
              //              deleteButton.setEnabled(enabled);
              if (!enabled) {
                fmeName.clear();
                className.clear();
                toggle.setValue(enableRadio);
                description.clear();
                selectedNodeStore.clear();
              }
            });
    grid.getSelectionModel()
        .addSelectionHandler(
            new SelectionHandler<FmeDefinitionDTO>() {
              @Override
              public void onSelection(SelectionEvent<FmeDefinitionDTO> event) {
                selectedNodeStore.clear();
                emptyDynamicConfigfields();
                FmeDefinitionDTO vo = event.getSelectedItem();
                if (vo != null) {
                  presenter.getFmeDyNamicConfig(
                      vo.getGroupName(), vo.getFmeName(), vo.getClassName(), topologyGroup);
                  groupName.setValue(vo.getGroupName());
                  fmeName.setValue(vo.getFmeName());
                  className.setValue(vo.getClassName());
                  description.setValue(vo.getDescription());
                  toggle.setValue(vo.isEnable() ? enableRadio : disableRadio, true);
                  if (vo.getNodePriority() != null && vo.getNodePriority().length() > 0) {
                    String[] nodes = vo.getNodePriority().split(",");
                    if (nodes != null && nodes.length > 0) {
                      for (int i = 0; i < nodes.length; i++) {
                        NodeDTO node = new NodeDTO();
                        node.setKey(vo.getGroupName() + nodes[i]);
                        node.setName(nodes[i]);
                        selectedNodeStore.add(node);
                      }
                    }
                  }
                }
              }
            });

    dynamicConfigGrid
        .getSelectionModel()
        .addSelectionChangedHandler(
            event -> {
              DynamicConfigDTO vo = dynamicConfigGrid.getSelectionModel().getSelectedItem();
              if (vo != null) {
                dynamicConfigName.setValue(vo.getName());
                dynamicConfigValue.setValue(vo.getValue());
              }
            });
  }

  private void initDynamicConfigColumnModel() {
    List<ColumnConfig<DynamicConfigDTO, ?>> columnConfigs =
        new ArrayList<ColumnConfig<DynamicConfigDTO, ?>>();
    ColumnConfig<DynamicConfigDTO, String> name =
        new ColumnConfig<DynamicConfigDTO, String>(dynamicConfigProps.name());
    name.setWidth(130);
    name.setHeader(messages.dynamicConfig_name());
    name.setHideable(false);
    name.setMenuDisabled(true);
    name.setSortable(false);
    columnConfigs.add(name);
    ColumnConfig<DynamicConfigDTO, String> value =
        new ColumnConfig<DynamicConfigDTO, String>(dynamicConfigProps.value());
    value.setWidth(310);
    value.setHeader(messages.dynamicConfig_value());
    value.setHideable(false);
    value.setMenuDisabled(true);
    value.setSortable(false);
    columnConfigs.add(value);

    dynamicConfigCm = new ColumnModel<DynamicConfigDTO>(columnConfigs);
  }

  private void initColumnModel() {
    List<ColumnConfig<FmeDefinitionDTO, ?>> columnConfigs =
        new ArrayList<ColumnConfig<FmeDefinitionDTO, ?>>();

    SafeStyles btnPaddingStyle = SafeStylesUtils.fromTrustedString("padding: 3px 3px 0;");
    ColumnConfig<FmeDefinitionDTO, String> operationColumn =
        new ColumnConfig<FmeDefinitionDTO, String>(props.operation(), 100, messages.fm_operation());
    operationColumn.setColumnTextClassName(CommonStyles.get().inlineBlock());
    operationColumn.setColumnTextStyle(btnPaddingStyle);

    TextButtonCell button = new TextButtonCell();
    button.addSelectHandler(
        new SelectHandler() {
          @Override
          public void onSelect(SelectEvent event) {
            int row = event.getContext().getIndex();
            FmeDefinitionDTO vo = store.get(row);
            if (vo.isEnable()) {
              clientFactory
                  .getEventBus()
                  .fireEventFromSource(new FmEvent(Action.FME_RESET, topologyGroup), vo);
            } else {
              new MessageTimerMask(FmViewer.this, messages.fm_isDisable(), 3000);
            }
          }
        });
    operationColumn.setCell(button);
    columnConfigs.add(operationColumn);

    ColumnConfig<FmeDefinitionDTO, String> groupNameConfig =
        new ColumnConfig<FmeDefinitionDTO, String>(props.groupName());
    groupNameConfig.setWidth(130);
    groupNameConfig.setHeader(messages.fm_groupName());
    groupNameConfig.setHideable(false);
    groupNameConfig.setMenuDisabled(true);
    groupNameConfig.setSortable(false);
    columnConfigs.add(groupNameConfig);

    ColumnConfig<FmeDefinitionDTO, String> fmeNameConfig =
        new ColumnConfig<FmeDefinitionDTO, String>(props.fmeName());
    fmeNameConfig.setWidth(130);
    fmeNameConfig.setHeader(messages.fm_fmeName());
    fmeNameConfig.setHideable(false);
    fmeNameConfig.setMenuDisabled(true);
    fmeNameConfig.setSortable(false);
    columnConfigs.add(fmeNameConfig);

    ColumnConfig<FmeDefinitionDTO, String> fmeStatusConfig =
        new ColumnConfig<FmeDefinitionDTO, String>(props.fmeStatus());
    fmeStatusConfig.setWidth(100);
    fmeStatusConfig.setHeader(messages.fm_status());
    fmeStatusConfig.setHideable(false);
    fmeStatusConfig.setMenuDisabled(true);
    fmeStatusConfig.setSortable(false);
    // fmeStatusConfig.setCell(new AbstractCell<String>() {
    // @Override
    // public void render(com.google.gwt.cell.client.Cell.Context context,
    // String value, SafeHtmlBuilder sb) {
    // GWT.log("value="+value);
    // String style = "";
    // String message = "";
    // if (value.equals("NoNode") || value.equals("Starting")
    // || value.equals("Running") || value.equals("Stop")) {
    // style = "style='color: green";
    // if (value.equals("NoNode"))
    // message = messages.fm_state_NoNode();
    // else if (value.equals("Starting"))
    // message = messages.fm_state_Starting();
    // else if (value.equals("Running"))
    // message = messages.fm_state_Running();
    // else if (value.equals("Stop"))
    // message = messages.fm_state_Stop();
    // } else {
    // style = "style='color: red";
    // if (value.equals("RuntimeException"))
    // message = messages.fm_state_RuntimeException();
    // }
    //
    // sb.appendHtmlConstant("<span " + style + ">" + message
    // + "</span>");
    // }
    // });

    columnConfigs.add(fmeStatusConfig);

    ColumnConfig<FmeDefinitionDTO, String> nowNodeConfig =
        new ColumnConfig<FmeDefinitionDTO, String>(props.nowNode());
    nowNodeConfig.setWidth(150);
    nowNodeConfig.setHeader(messages.fm_nowNode());
    nowNodeConfig.setHideable(false);
    nowNodeConfig.setMenuDisabled(true);
    nowNodeConfig.setSortable(false);
    columnConfigs.add(nowNodeConfig);

    ColumnConfig<FmeDefinitionDTO, Boolean> enableConfig =
        new ColumnConfig<FmeDefinitionDTO, Boolean>(props.enable());
    enableConfig.setWidth(300);
    enableConfig.setHeader(messages.fm_enableDisable());
    enableConfig.setHideable(false);
    enableConfig.setMenuDisabled(true);
    enableConfig.setSortable(false);
    enableConfig.setCell(
        new AbstractCell<Boolean>() {
          @Override
          public void render(
              com.google.gwt.cell.client.Cell.Context context, Boolean value, SafeHtmlBuilder sb) {
            String style = "style='color: " + (value ? "green" : "red") + "'";
            sb.appendHtmlConstant(
                "<span "
                    + style
                    + ">"
                    + (value ? messages.fm_enable() : messages.fm_disable())
                    + "</span>");
          }
        });
    columnConfigs.add(enableConfig);

    ColumnConfig<FmeDefinitionDTO, String> nodePriorityConfig =
        new ColumnConfig<FmeDefinitionDTO, String>(props.nodePriority());
    nodePriorityConfig.setWidth(250);
    nodePriorityConfig.setHeader(messages.fm_nodePriority());
    nodePriorityConfig.setHideable(false);
    nodePriorityConfig.setMenuDisabled(true);
    nodePriorityConfig.setSortable(false);
    columnConfigs.add(nodePriorityConfig);

    ColumnConfig<FmeDefinitionDTO, String> classNameConfig =
        new ColumnConfig<FmeDefinitionDTO, String>(props.className());
    classNameConfig.setWidth(300);
    classNameConfig.setHeader(messages.fm_className());
    classNameConfig.setHideable(false);
    classNameConfig.setMenuDisabled(true);
    classNameConfig.setSortable(false);
    columnConfigs.add(classNameConfig);

    // Configure column description
    ColumnConfig<FmeDefinitionDTO, String> descriptionConfig =
        new ColumnConfig<FmeDefinitionDTO, String>(props.description());
    descriptionConfig.setWidth(350);
    descriptionConfig.setHeader(messages.fm_description());
    descriptionConfig.setHideable(false);
    descriptionConfig.setMenuDisabled(true);
    descriptionConfig.setSortable(false);
    columnConfigs.add(descriptionConfig);

    ColumnConfig<FmeDefinitionDTO, String> paramsConfig =
        new ColumnConfig<FmeDefinitionDTO, String>(props.params());
    paramsConfig.setWidth(150);
    paramsConfig.setHeader(messages.fm_params());
    paramsConfig.setHideable(false);
    paramsConfig.setMenuDisabled(true);
    paramsConfig.setSortable(false);
    columnConfigs.add(paramsConfig);

    cm = new ColumnModel<FmeDefinitionDTO>(columnConfigs);
  }

  private void initListView() {
    allNodeStore = new ListStore<NodeDTO>(nodeProps.key());
    selectedNodeStore = new ListStore<NodeDTO>(nodeProps.key());
    allListView = new ListView<NodeDTO, String>(allNodeStore, nodeProps.name());
    selectedListView = new ListView<NodeDTO, String>(selectedNodeStore, nodeProps.name());

    allNodeStore = new ListStore<NodeDTO>(nodeProps.key());
    selectedNodeStore = new ListStore<NodeDTO>(nodeProps.key());
    allListView = new ListView<NodeDTO, String>(allNodeStore, nodeProps.name());
    selectedListView = new ListView<NodeDTO, String>(selectedNodeStore, nodeProps.name());

    ListViewDragSource<NodeDTO> allListViewDragSource =
        new ListViewDragSource<NodeDTO>(allListView);
    allListViewDragSource.setGroup(listViewGroup1);
    // allListViewDragSource.getDraggable().setUpdateZIndex(true);

    ListViewDropTarget<NodeDTO> selectedListViewDropSource =
        new ListViewDropTarget<NodeDTO>(selectedListView);
    selectedListViewDropSource.setGroup(listViewGroup1);
    selectedListViewDropSource.setOperation(Operation.COPY);

    ListViewDragSource<NodeDTO> selectedListViewDragSource =
        new ListViewDragSource<NodeDTO>(selectedListView);
    selectedListViewDragSource.setGroup(listViewGroup2);

    DropTarget allListViewDropSource = new DropTarget(allListView);
    allListViewDropSource.setOperation(Operation.MOVE);
    allListViewDropSource.setGroup(listViewGroup2);

    ListViewDropTarget<NodeDTO> selectedListViewDragSource2 =
        new ListViewDropTarget<NodeDTO>(selectedListView);
    selectedListViewDragSource2.setGroup(listViewGroup2);
    selectedListViewDragSource2.setAllowSelfAsSource(true);
    selectedListViewDragSource2.setFeedback(Feedback.INSERT);

    ContentPanel allListCp = new ContentPanel();
    allListCp.setHeading(messages.fm_allListViewTitle());
    allListCp.setWidget(allListView);
    ContentPanel selectedListCp = new ContentPanel();
    selectedListCp.setHeading(messages.fm_selectedListViewTitle());
    selectedListCp.setWidget(selectedListView);
    listContainer.add(allListCp, new HorizontalLayoutData(.5, 1, new Margins(5)));
    listContainer.add(selectedListCp, new HorizontalLayoutData(.5, 1, new Margins(5, 5, 5, 0)));
  }

  private void emptyDynamicConfigfields() {
    dynamicConfigName.setValue(null);
    dynamicConfigValue.setValue(null);
  }

  public void forceLayout() {
    fmeDefinitionPanel.forceLayout();
  }

  @UiHandler("saveDynamicButton")
  public void saveDynamicButton(SelectEvent event) {
    GWT.log("saveDynamicButton Click!");
    FmeDefinitionDTO storeVo = store.findModelWithKey(groupName.getValue() + fmeName.getValue());
    if (storeVo != null) {
      DynamicConfigDTO vo =
          dynamicConfigStore.findModelWithKey(
              groupName.getValue() + fmeName.getValue() + dynamicConfigName.getValue());
      if (vo != null) {
        final ConfirmMessageBox box =
            new ConfirmMessageBox(messages.message(), messages.message_itemSaveConfirm());
        box.addDialogHideHandler(
            new DialogHideHandler() {
              @Override
              public void onDialogHide(DialogHideEvent event) {
                if (event.getHideButton() == PredefinedButton.YES) {
                  DynamicConfigDTO newVo =
                      new DynamicConfigDTO(
                          groupName.getValue(),
                          fmeName.getValue(),
                          dynamicConfigName.getValue(),
                          dynamicConfigValue.getValue());
                  presenter.updateDynamicConfig(newVo);
                }
              }
            });
        box.show();

      } else {
        Info.display(messages.message(), messages.message_itemNoExisted());
      }
    } else {
      Info.display(messages.message(), messages.message_itemNoExisted());
    }
  }

  @UiHandler("deleteButton")
  public void deleteButton(SelectEvent event) {
    GWT.log("deleteButton Click!");
    if (!fmeName.validate()) return;
    if (!className.validate()) return;
    final ConfirmMessageBox box =
        new ConfirmMessageBox(messages.message(), messages.message_itemDeleteConfirm());
    box.addDialogHideHandler(
        new DialogHideHandler() {
          @Override
          public void onDialogHide(DialogHideEvent event) {
            if (event.getHideButton() == PredefinedButton.YES) {
              FmeDefinitionDTO storeVo = grid.getSelectionModel().getSelectedItem();
              presenter.removeItem(storeVo);
            }
          }
        });
    box.show();
  }

  @UiHandler("saveButton")
  public void saveButton(SelectEvent event) {
    GWT.log("saveButton Click!");
    if (!fmeName.validate()) return;
    if (!className.validate()) return;
    FmeDefinitionDTO dto = new FmeDefinitionDTO();
    dto.setGroupName(groupName.getValue());
    dto.setFmeName(fmeName.getValue());
    dto.setClassName(className.getValue());
    dto.setDescription(description.getValue());
    dto.setEnable(fmEnable);
    StringBuilder nodes = new StringBuilder();
    for (int i = 0; i < selectedNodeStore.getAll().size(); i++) {
      NodeDTO nodeVo = selectedNodeStore.getAll().get(i);
      nodes.append(nodeVo.getName());
      if (i != (selectedNodeStore.getAll().size() - 1)) {
        nodes.append(",");
      }
    }
    dto.setNodePriority(nodes.toString());
    GWT.log("storeVo.toString()=" + dto.toString());
    presenter.saveItem(dto);
  }

  @UiHandler("cancel")
  public void onCancel(SelectEvent event) {
    grid.getSelectionModel().deselectAll();
  }

  interface FmeDefinitionProperties extends PropertyAccess<FmeDefinitionDTO> {
    @Path("key")
    ModelKeyProvider<FmeDefinitionDTO> key();

    @Path("fmeName")
    LabelProvider<FmeDefinitionDTO> nameLabel();

    ValueProvider<FmeDefinitionDTO, String> groupName();

    ValueProvider<FmeDefinitionDTO, String> fmeName();

    ValueProvider<FmeDefinitionDTO, String> className();

    ValueProvider<FmeDefinitionDTO, String> nodePriority();

    ValueProvider<FmeDefinitionDTO, Boolean> enable();

    ValueProvider<FmeDefinitionDTO, String> fmeStatus();

    ValueProvider<FmeDefinitionDTO, String> nowNode();

    ValueProvider<FmeDefinitionDTO, String> description();

    ValueProvider<FmeDefinitionDTO, String> params();

    ValueProvider<FmeDefinitionDTO, String> operation();
  }

  interface NodeProperties extends PropertyAccess<NodeDTO> {
    ModelKeyProvider<NodeDTO> key();

    ValueProvider<NodeDTO, String> name();
  }

  interface DynamicConfigProperties extends PropertyAccess<DynamicConfigDTO> {
    @Path("key")
    ModelKeyProvider<DynamicConfigDTO> key();

    ValueProvider<DynamicConfigDTO, String> groupName();

    ValueProvider<DynamicConfigDTO, String> fmeName();

    ValueProvider<DynamicConfigDTO, String> name();

    ValueProvider<DynamicConfigDTO, String> value();
  }

  @Override
  protected void onUnload() {
    if (timer != null) timer.cancel();
    if (resetFmeTimer != null) resetFmeTimer.cancel();
    if (handlerRegistration != null) handlerRegistration.removeHandler();
    if (eventHandlerRegistration != null) eventHandlerRegistration.removeHandler();
    super.onUnload();
  }

  public void init(List<FmeDefinitionDTO> fmeDefinitionList) {
    // GWT.log("FmViewer:init:fmeDefinitionList =>"+  fmeDefinitionList.toString());
    store.clear();
    store.addAll(fmeDefinitionList);
  }

  public void updateFme(List<FmeDefinitionDTO> fmeDefinitionList) {
    for (FmeDefinitionDTO vo : fmeDefinitionList) {
      updateSotre(vo);
    }
  }

  public void initNodeList(List<NodeDTO> nodeList) {
    GWT.log("nodeList =>" + nodeList.toString());
    allNodeStore.clear();
    allNodeStore.addAll(nodeList);
  }

  public void removeStore(FmeDefinitionDTO dto) {
    GWT.log("removeStore...");
    store.remove(dto);
  }

  public void updateSotre(FmeDefinitionDTO vo) {
    GWT.log("updateSotre...");
    FmeDefinitionDTO updateVo = store.findModelWithKey(props.key().getKey(vo));
    if (updateVo != null) {
      store.update(vo);
    } else {
      store.add(vo);
    }
  }

  class DefaultFmEventHandler implements FmEventHandler {

    @Override
    public void onResetFme(FmEvent event) {
      GWT.log(
          "onFmeReset...event.getTopologyGroup():"
              + event.getTopologyGroup()
              + "; me TopologyGroup :"
              + topologyGroup);
      if (topologyGroup == event.getTopologyGroup()) {
        new MessageTimerMask(FmViewer.this, messages.message_waiting(), 10000);
        FmViewer.this.mask();
        createResetFmeTimer();
        FmeDefinitionDTO vo = (FmeDefinitionDTO) event.getSource();
        vo.setUserId(userId);
        presenter.resetFme(vo);
      }
    }
  }

  public void setTopologyGroup(TopologyGroup topologyGroup) {
    this.topologyGroup = topologyGroup;
    groupName.setValue(topologyGroup.toString());
    presenter.init(topologyGroup);
    presenter.initNodeList(topologyGroup);
    listViewGroup1 = listViewGroup1 + topologyGroup;
    listViewGroup2 = listViewGroup2 + topologyGroup;
    timer.scheduleRepeating(3000);
    initListView();
  }

  private void createFeatchDataTimer() {
    timer =
        new Timer() {
          public void run() {
            presenter.updateFme(topologyGroup);
          }
        };
  }

  private void createResetFmeTimer() {
    resetFmeTimer =
        new Timer() {
          public void run() {
            FmViewer.this.unmask();
          }
        };
    resetFmeTimer.scheduleRepeating(10000);
  }

  public void updateDynamicConfigStore(List<DynamicConfigDTO> result) {
    GWT.log("result :" + result);
    dynamicConfigStore.clear();
    if (result != null) {
      dynamicConfigStore.addAll(result);
    }
  }

  public void updateDynamicConfigResult(DynamicConfigDTO result) {
    if (result != null) {
      dynamicConfigStore.update(result);
    }
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }
}
