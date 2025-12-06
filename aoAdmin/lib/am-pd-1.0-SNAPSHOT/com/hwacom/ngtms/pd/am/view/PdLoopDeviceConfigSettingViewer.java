package com.hwacom.ngtms.pd.am.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Widget;
import com.hwacom.ngtms.c.shared.dto.DeviceConfigDTO;
import com.hwacom.ngtms.cam.client.event.AmEventCenter;
import com.hwacom.ngtms.cam.client.event.DeviceConfigDeselectEvent;
import com.hwacom.ngtms.cam.client.event.DeviceConfigDeselectHandler;
import com.hwacom.ngtms.cam.client.event.DeviceConfigSelectEvent;
import com.hwacom.ngtms.cam.client.event.DeviceConfigSelectHandler;
import com.hwacom.ngtms.cam.client.ui.AmTab;
import com.hwacom.ngtms.cam.client.ui.RoadTreeViewer;
import com.hwacom.ngtms.hcce.am.factory.ClientFactory;
import com.hwacom.ngtms.pd.am.event.PdConfigSettingViewerEvent;
import com.hwacom.ngtms.pd.am.event.PdConfigSettingViewerEvent.PdConfigSettingViewerEventHandler;
import com.hwacom.ngtms.pd.am.presenter.PdLoopDeviceConfigSettingPresenter;
import com.hwacom.ngtms.pd.shared.dto.LoopDeviceConfigDTO;
import com.sencha.gxt.data.shared.TreeStore.TreeNode;
import com.sencha.gxt.dnd.core.client.DND.Feedback;
import com.sencha.gxt.dnd.core.client.DndDragMoveEvent;
import com.sencha.gxt.dnd.core.client.DndDragStartEvent;
import com.sencha.gxt.dnd.core.client.DndDragStartEvent.DndDragStartHandler;
import com.sencha.gxt.dnd.core.client.DndDropEvent;
import com.sencha.gxt.dnd.core.client.DndDropEvent.DndDropHandler;
import com.sencha.gxt.dnd.core.client.Insert;
import com.sencha.gxt.dnd.core.client.TreeGridDragSource;
import com.sencha.gxt.dnd.core.client.TreeGridDropTarget;
import com.sencha.gxt.widget.core.client.Dialog;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.button.ToolButton;
import com.sencha.gxt.widget.core.client.container.HorizontalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.form.IntegerField;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.info.Info;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

public class PdLoopDeviceConfigSettingViewer extends AmTab {

  private static PdLoopDeviceConfigSettingViewerUiBinder uiBinder =
      GWT.create(PdLoopDeviceConfigSettingViewerUiBinder.class);

  interface PdLoopDeviceConfigSettingViewerUiBinder
      extends UiBinder<Widget, PdLoopDeviceConfigSettingViewer> {}

  private static Messages messages = GWT.create(Messages.class);

  private final ClientFactory clientFactory = GWT.create(ClientFactory.class);

  private PdLoopDeviceConfigSettingPresenter presenter =
      new PdLoopDeviceConfigSettingPresenter(this);

  @UiField PdLoopDeviceConfigGrid loopDeviceConfigGrid;

  @UiField PdUnsetLoopDeviceConfigGrid unsetLoopDeviceConfigGrid;

  @UiField ToolButton expandAll;

  @UiField ToolButton collapseAll;

  @UiField ToolButton setting;

  @UiField ToolButton expandAllunset;

  @UiField ToolButton collapseAllunset;

  @UiField RoadTreeViewer roadTree;

  private List<String> deviceNames = new ArrayList<>();

  private LoopDeviceConfigDTO dndSourceParent;

  private static Dialog settingDialog;

  private Set<DeviceConfigDTO> deviceList = new HashSet<>();

  public PdLoopDeviceConfigSettingViewer() {
    initWidget(uiBinder.createAndBindUi(this));
    initDragAndDrop();
    presenter.retrieveUnsetLoopDeviceConfig();

    AmEventCenter.addDeviceConfigSelectHandler(
        new DeviceConfigSelectHandler() {
          public void onDeviceConfigSelect(DeviceConfigSelectEvent event) {
            deviceList.addAll(event.getData());
            List<DeviceConfigDTO> devices = new ArrayList<DeviceConfigDTO>(deviceList);
            addDevices(devices);
          }
        });

    AmEventCenter.addDeviceConfigDeselectHandler(
        new DeviceConfigDeselectHandler() {
          public void onDeviceConfigDeselect(DeviceConfigDeselectEvent event) {
            deviceList.removeAll(event.getData());
            removeDevices(event.getData());
          }
        });

    clientFactory
        .getEventBus()
        .addHandler(
            PdConfigSettingViewerEvent.TYPE, new DefaultPdConfigSettingViewerEventHandler());
  }

  @Override
  protected void onAttach() {
    super.onAttach();
    loopDeviceConfigGrid.setAutoExpand(true);
  }

  @Override
  protected void onDetach() {
    loopDeviceConfigGrid.setAutoExpand(false);
    super.onDetach();
  }

  @UiHandler("expandAll")
  void onExpandAll(SelectEvent e) {
    loopDeviceConfigGrid.expandAll();
  }

  @UiHandler("collapseAll")
  void onCollapseAll(SelectEvent e) {
    loopDeviceConfigGrid.collapseAll();
  }

  @UiHandler("setting")
  void onSetting(SelectEvent e) {
    if (loopDeviceConfigGrid.getSelectionModel().getSelectedItem() == null
        || loopDeviceConfigGrid.getSelectionModel().getSelectedItem().getCheck() == false) {
      Info.display("失敗", "請選擇設備");
      return;
    }
    showSettingDialog();
  }

  @UiHandler("expandAllunset")
  void onExpandAllunset(SelectEvent e) {
    unsetLoopDeviceConfigGrid.expandAll();
  }

  @UiHandler("collapseAllunset")
  void onCollapseAllunset(SelectEvent e) {
    unsetLoopDeviceConfigGrid.collapseAll();
  }

  // TODO treeNodeMover
  private void initDragAndDrop() {
    TreeGridDragSource<LoopDeviceConfigDTO> unsetTreeGridDragSource =
        new TreeGridDragSource<LoopDeviceConfigDTO>(unsetLoopDeviceConfigGrid);
    unsetTreeGridDragSource.setGroup("unset");

    TreeGridDropTarget<LoopDeviceConfigDTO> treeGridToUnsetGridDropTarget =
        new TreeGridDropTarget<LoopDeviceConfigDTO>(unsetLoopDeviceConfigGrid);
    treeGridToUnsetGridDropTarget.setFeedback(Feedback.BOTH);
    treeGridToUnsetGridDropTarget.setGroup("self");
    treeGridToUnsetGridDropTarget.setAllowDropOnLeaf(false);
    treeGridToUnsetGridDropTarget.addDropHandler(
        new DndDropHandler() {
          @Override
          public void onDrop(DndDropEvent event) {
            List<LoopDeviceConfigDTO> newDataList = new ArrayList<>();
            for (TreeNode<LoopDeviceConfigDTO> each :
                (List<TreeNode<LoopDeviceConfigDTO>>) event.getData()) {
              LoopDeviceConfigDTO dto = each.getData();
              dto.setLoopNo(null);
              newDataList.add(each.getData());
            }
            presenter.updateLoopDeviceConfig("", newDataList);
          }
        });

    TreeGridDragSource<LoopDeviceConfigDTO> treeGridDragSource =
        new TreeGridDragSource<LoopDeviceConfigDTO>(loopDeviceConfigGrid);
    treeGridDragSource.setGroup("self");

    treeGridDragSource.addDragStartHandler(
        new DndDragStartHandler() {
          @Override
          public void onDragStart(DndDragStartEvent event) {
            GWT.log("treeGridDragSource onDragStart!=" + event.getData());
            LoopDeviceConfigDTO selectedItem =
                loopDeviceConfigGrid.getSelectionModel().getSelectedItem();
            dndSourceParent = loopDeviceConfigGrid.getTreeStore().getParent(selectedItem);
          }
        });

    TreeGridDropTarget<LoopDeviceConfigDTO> treeGridDropTarget =
        new TreeGridDropTarget<LoopDeviceConfigDTO>(loopDeviceConfigGrid) {
          @Override
          protected void showFeedback(DndDragMoveEvent event) {
            Element target = getElementFromEvent(event.getDragMoveEvent().getNativeEvent());

            LoopDeviceConfigDTO targetParent =
                Optional.ofNullable(loopDeviceConfigGrid.findNode(Element.as(target)))
                    .map(treeNode -> treeNode.getModel())
                    .orElse(null);
            LoopDeviceConfigDTO selectedItem =
                loopDeviceConfigGrid.getSelectionModel().getSelectedItem();
            boolean check = selectedItem.getCheck();
            boolean sameParent = Objects.equals(dndSourceParent, targetParent);
            if (!check
                || sameParent
                || targetParent == null
                || targetParent.getLoopNo() == null
                || targetParent.getCheck()) {

              Insert.get().hide();
              event.getStatusProxy().setStatus(false);
              return;
            }
            setFeedback(Feedback.APPEND);
            super.showFeedback(event);
          }
        };
    treeGridDropTarget.setFeedback(Feedback.APPEND);
    treeGridDropTarget.setGroup("self");
    treeGridDropTarget.setAllowDropOnLeaf(true);
    treeGridDropTarget.setAllowSelfAsSource(true);
    treeGridDropTarget.addDropHandler(
        new DndDropHandler() {
          @Override
          public void onDrop(DndDropEvent event) {
            loopDeviceConfigGrid.expandAll();
            List<LoopDeviceConfigDTO> newDataList = new ArrayList<>();
            LoopDeviceConfigDTO newLoopNode = null;
            for (TreeNode<LoopDeviceConfigDTO> each :
                (List<TreeNode<LoopDeviceConfigDTO>>) event.getData()) {
              LoopDeviceConfigDTO dto = each.getData();
              if (newLoopNode == null) {
                newLoopNode = loopDeviceConfigGrid.getTreeStore().getParent(dto);
              }
              GWT.log("self onDrop dto: " + dto);
              dto.setLoopNo(newLoopNode.getLoopNo());
              newDataList.add(dto);
            }
            String newPdDevice =
                loopDeviceConfigGrid.getTreeStore().getParent(newLoopNode).getDeviceName();
            presenter.updateLoopDeviceConfig(newPdDevice, newDataList);
          }
        });

    TreeGridDropTarget<LoopDeviceConfigDTO> unsetGridToTreeGridDropTarget =
        new TreeGridDropTarget<LoopDeviceConfigDTO>(loopDeviceConfigGrid) {
          @Override
          protected void showFeedback(DndDragMoveEvent event) {
            Element target = getElementFromEvent(event.getDragMoveEvent().getNativeEvent());

            LoopDeviceConfigDTO targetParent =
                Optional.ofNullable(loopDeviceConfigGrid.findNode(Element.as(target)))
                    .map(treeNode -> treeNode.getModel())
                    .orElse(null);
            LoopDeviceConfigDTO selectedItem =
                unsetLoopDeviceConfigGrid.getSelectionModel().getSelectedItem();
            boolean check = selectedItem.getCheck();
            if (!check
                || targetParent == null
                || targetParent.getLoopNo() == null
                || targetParent.getCheck()) {
              Insert.get().hide();
              event.getStatusProxy().setStatus(false);
              return;
            }
            setFeedback(Feedback.APPEND);
            super.showFeedback(event);
          }
        };
    unsetGridToTreeGridDropTarget.setFeedback(Feedback.APPEND);
    unsetGridToTreeGridDropTarget.setGroup("unset");
    unsetGridToTreeGridDropTarget.setAllowDropOnLeaf(true);
    unsetGridToTreeGridDropTarget.addDropHandler(
        new DndDropHandler() {
          @Override
          public void onDrop(DndDropEvent event) {
            List<LoopDeviceConfigDTO> newDataList = new ArrayList<>();
            LoopDeviceConfigDTO newLoopNode = null;
            for (TreeNode<LoopDeviceConfigDTO> each :
                (List<TreeNode<LoopDeviceConfigDTO>>) event.getData()) {
              LoopDeviceConfigDTO dto = each.getData();
              if (newLoopNode == null) {
                newLoopNode = loopDeviceConfigGrid.getTreeStore().getParent(dto);
              }
              GWT.log("unset onDrop dto: " + dto);
              dto.setLoopNo(newLoopNode.getLoopNo());
              newDataList.add(each.getData());
            }
            String newPdDevice =
                loopDeviceConfigGrid.getTreeStore().getParent(newLoopNode).getDeviceName();
            presenter.updateLoopDeviceConfig(newPdDevice, newDataList);
          }
        });
  }

  private void initSettingDialog() {
    VerticalLayoutContainer container = new VerticalLayoutContainer();
    HorizontalLayoutContainer diameterContainer = new HorizontalLayoutContainer();
    HorizontalLayoutContainer memoContainer = new HorizontalLayoutContainer();
    diameterContainer.setHeight(30);
    memoContainer.setHeight(30);
    settingDialog = new Dialog();
    settingDialog.setHeading(messages.pdLoopDeviceConfigSettingViewer_heading_update());
    settingDialog.setModal(true);
    settingDialog.setWidth(180);
    settingDialog.setHeight(130);
    FieldLabel diameterLabel = new FieldLabel();
    diameterLabel.setText(messages.pdLoopDeviceConfigSettingViewer_button_diameter());
    diameterLabel.setWidth(50);
    FieldLabel memoLabel = new FieldLabel();
    memoLabel.setText(messages.pdLoopDeviceConfigSettingViewer_button_memo());
    memoLabel.setWidth(50);
    IntegerField diameter = new IntegerField();
    diameter.setWidth(100);
    if (loopDeviceConfigGrid.getSelectionModel().getSelectedItem().getDiameter() != null) {
      diameter.setValue(loopDeviceConfigGrid.getSelectionModel().getSelectedItem().getDiameter());
    }
    diameter.setAllowBlank(false);
    TextField memo = new TextField();
    memo.setWidth(100);
    if (loopDeviceConfigGrid.getSelectionModel().getSelectedItem().getMemo() != null) {
      memo.setValue(loopDeviceConfigGrid.getSelectionModel().getSelectedItem().getMemo());
    }
    memo.setAllowBlank(false);

    TextButton updateButton = new TextButton();
    updateButton.setText(messages.pdLoopDeviceConfigSettingViewer_update());
    updateButton.addSelectHandler(
        new SelectHandler() {
          @Override
          public void onSelect(SelectEvent event) {
            LoopDeviceConfigDTO dto = loopDeviceConfigGrid.getSelectionModel().getSelectedItem();
            dto.setDiameter(diameter.getCurrentValue());
            dto.setMemo(memo.getCurrentValue());
            presenter.updateLoopDeviceData(dto);
          }
        });
    TextButton cancelButton = new TextButton();
    cancelButton.setText(messages.pdLoopDeviceConfigSettingViewer_cancel());
    cancelButton.addSelectHandler(
        new SelectHandler() {
          @Override
          public void onSelect(SelectEvent event) {
            settingDialog.hide();
          }
        });
    diameterContainer.add(diameterLabel);
    diameterContainer.add(diameter);
    memoContainer.add(memoLabel);
    memoContainer.add(memo);
    container.add(diameterContainer);
    container.add(memoContainer);
    settingDialog.add(container);
    settingDialog.getButtonBar().clear();
    settingDialog.getButtonBar().add(updateButton);
    settingDialog.getButtonBar().add(cancelButton);
  }

  public void showSettingDialog() {
    if (settingDialog == null) {
      initSettingDialog();
    }
    settingDialog.show();
  }

  public void fillLoopDeviceGrid(List<LoopDeviceConfigDTO> list) {
    loopDeviceConfigGrid.setGridStore(list);
  }

  public void fillUnsetLoopDeviceGrid(List<LoopDeviceConfigDTO> list) {
    unsetLoopDeviceConfigGrid.setGridStore(list);
  }

  public void addDevices(List<DeviceConfigDTO> list) {
    for (DeviceConfigDTO dto : list) {
      if (!deviceNames.contains(dto.getDeviceName())) {
        deviceNames.add(dto.getDeviceName());
      }
    }
    presenter.retrieveLoopDeviceConfig(deviceNames);
  }

  public void removeDevices(List<DeviceConfigDTO> list) {
    List<String> removeDeviceNames = new ArrayList<>();
    for (DeviceConfigDTO dto : list) {
      removeDeviceNames.add(dto.getDeviceName());
    }
    deviceNames.removeAll(removeDeviceNames);
    if (deviceNames.size() != 0) {
      presenter.retrieveLoopDeviceConfig(deviceNames);
    } else {
      loopDeviceConfigGrid.getTreeStore().clear();
    }
  }

  public void refreshLoopDeviceGrid(List<LoopDeviceConfigDTO> list) {
    loopDeviceConfigGrid.setGridStore(list);
    // loopDeviceConfigGrid.expandAll();
  }

  public void refreshUnsetLoopDeviceGrid(List<LoopDeviceConfigDTO> list) {
    unsetLoopDeviceConfigGrid.setGridStore(list);
    //  unsetLoopDeviceConfigGrid.expandAll();
  }

  public void mask() {
    mask(messages.pdLoopDeviceConfigSettingViewer_loading());
  }

  public void updateTreeGrid() {
    presenter.refreshLoopDeviceConfig(deviceNames);
  }

  public void updateUnsetGrid() {
    presenter.refreshUnsetLoopDeviceConfig();
  }

  public void hideDialog() {
    settingDialog.hide();
    loopDeviceConfigGrid.expandAll();
  }

  class DefaultPdConfigSettingViewerEventHandler implements PdConfigSettingViewerEventHandler {

    @Override
    public void onUpdate(PdConfigSettingViewerEvent event) {
      roadTree.resetDeviceType("PD");
      roadTree.unmask();
    }
  }
}
