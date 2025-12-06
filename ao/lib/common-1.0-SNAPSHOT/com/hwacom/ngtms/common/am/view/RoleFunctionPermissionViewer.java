/*
 * © HwaCom Systems Inc. 2013
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.common.am.view;

import com.google.gwt.cell.client.DateCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor.Path;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Widget;
import com.hwacom.ngtms.common.am.event.RoleEvent;
import com.hwacom.ngtms.common.am.event.RoleEvent.RoleEventHandler;
import com.hwacom.ngtms.common.am.event.RoleFunctionPermissionEvent;
import com.hwacom.ngtms.common.am.event.RoleFunctionPermissionEvent.Action;
import com.hwacom.ngtms.common.am.event.RoleFunctionPermissionEvent.RoleFunctionPermissionEventHandler;
import com.hwacom.ngtms.common.am.presenter.RoleFunctionPermissionPresenter;
import com.hwacom.ngtms.common.shared.dto.FunctionPermissionDTO;
import com.hwacom.ngtms.common.shared.dto.RoleDTO;
import com.hwacom.ngtms.hcce.am.factory.ClientFactory;
import com.sencha.gxt.core.client.Style.SelectionMode;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;
import com.sencha.gxt.data.shared.TreeStore;
import com.sencha.gxt.widget.core.client.Composite;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.Dialog.PredefinedButton;
import com.sencha.gxt.widget.core.client.box.ConfirmMessageBox;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.button.ToolButton;
import com.sencha.gxt.widget.core.client.event.DialogHideEvent;
import com.sencha.gxt.widget.core.client.event.DialogHideEvent.DialogHideHandler;
import com.sencha.gxt.widget.core.client.event.RowClickEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.grid.GridView;
import com.sencha.gxt.widget.core.client.info.Info;
import com.sencha.gxt.widget.core.client.tree.Tree;
import com.sencha.gxt.widget.core.client.tree.Tree.CheckCascade;
import com.sencha.gxt.widget.core.client.tree.Tree.CheckState;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class RoleFunctionPermissionViewer extends Composite {

  private static RoleFunctionPermissionViewerUiBinder uiBinder =
      GWT.create(RoleFunctionPermissionViewerUiBinder.class);

  interface RoleFunctionPermissionViewerUiBinder
      extends UiBinder<Widget, RoleFunctionPermissionViewer> {}

  private static final RoleProperties props = GWT.create(RoleProperties.class);

  private FunctionPermissionPropertyAccess propertyAccess =
      GWT.create(FunctionPermissionPropertyAccess.class);

  private static final Messages messages = GWT.create(Messages.class);
  private RoleFunctionPermissionPresenter presenter;
  private final ClientFactory clientFactory = GWT.create(ClientFactory.class);
  private final HandlerRegistration roleHandlerRegistration;
  private final HandlerRegistration handlerRegistration;

  private Runnable roleSaver;

  @UiField Grid<RoleDTO> grid;

  @UiField(provided = true)
  ListStore<RoleDTO> store;

  @UiField(provided = true)
  ColumnModel<RoleDTO> cm;

  @UiField GridView<RoleDTO> view;

  @UiField ToolButton addRole;

  @UiField ContentPanel rolePanel;

  @UiField TextField name;

  @UiField TextField description;

  @UiField TextButton deleteRole;

  @UiField ContentPanel functionPermissionListPanel;

  @UiField Tree<FunctionPermissionDTO, String> tree;

  @UiField(provided = true)
  TreeStore<FunctionPermissionDTO> treeStore;

  @UiField(provided = true)
  ValueProvider<FunctionPermissionDTO, String> treeValueProvider;

  public RoleFunctionPermissionViewer() {
    store = new ListStore<RoleDTO>(props.key());
    initColumnModel();
    treeStore = new TreeStore<FunctionPermissionDTO>(propertyAccess.id());
    treeValueProvider = propertyAccess.name();
    initWidget(uiBinder.createAndBindUi(this));
    grid.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    view.setAutoExpandColumn(cm.findColumnConfig(props.description().getPath()));
    addRole.fireEvent(new SelectEvent());
    DefaultRoleFunctionPermissionEventHandler eventHandler =
        new DefaultRoleFunctionPermissionEventHandler();
    roleHandlerRegistration = clientFactory.getEventBus().addHandler(RoleEvent.TYPE, eventHandler);
    handlerRegistration =
        clientFactory.getEventBus().addHandler(RoleFunctionPermissionEvent.TYPE, eventHandler);
    presenter = new RoleFunctionPermissionPresenter(this);
    tree.setCheckable(true);
    tree.setCheckStyle(CheckCascade.TRI);
    tree.setAutoLoad(true);
    tree.expandAll();
  }

  private void initColumnModel() {
    List<ColumnConfig<RoleDTO, ?>> columnConfigs = new ArrayList<ColumnConfig<RoleDTO, ?>>();

    ColumnConfig<RoleDTO, String> name =
        new ColumnConfig<RoleDTO, String>(props.name(), 140, messages.role_name());
    name.setSortable(true);
    columnConfigs.add(name);

    ColumnConfig<RoleDTO, String> descriptionConfig =
        new ColumnConfig<RoleDTO, String>(props.description());
    descriptionConfig.setHeader(messages.role_description());
    descriptionConfig.setSortable(true);
    columnConfigs.add(descriptionConfig);

    ColumnConfig<RoleDTO, Date> updateTime =
        new ColumnConfig<RoleDTO, Date>(props.updateTime(), 120, messages.role_updateTime());
    updateTime.setFixed(true);
    updateTime.setCell(new DateCell(DateTimeFormat.getFormat("yyyy/MM/dd HH:mm:ss")));
    columnConfigs.add(updateTime);

    cm = new ColumnModel<RoleDTO>(columnConfigs);
  }

  @UiHandler("grid")
  public void rowClick(RowClickEvent event) {
    uncheckTreeAll();
    RoleDTO dto = store.get(event.getRowIndex());
    if (dto != null) {
      fillTreeStoreSelected(dto);
      name.clearInvalid();
      name.setValue(dto.getName());
      description.clearInvalid();
      description.setValue(dto.getDescription());
    }
    name.disable();
    deleteRole.show();
    rolePanel.syncSize();
    functionPermissionListPanel.unmask();
    roleSaver =
        () -> {
          if (!name.validate()) return;
          final ConfirmMessageBox box =
              new ConfirmMessageBox(messages.message(), messages.message_itemSaveConfirm());
          box.addDialogHideHandler(
              new DialogHideHandler() {
                @Override
                public void onDialogHide(DialogHideEvent event) {
                  if (event.getHideButton() == PredefinedButton.YES) {
                    RoleDTO dto = store.findModelWithKey(name.getValue().trim());
                    if (dto != null) {
                      dto.setDescription(description.getValue());
                      dto.setUpdateTime(new Date());
                      clientFactory
                          .getEventBus()
                          .fireEventFromSource(
                              new RoleEvent(com.hwacom.ngtms.common.am.event.RoleEvent.Action.SAVE),
                              dto);
                    } else {
                      Info.display(messages.message(), messages.message_itemNoExisted());
                    }
                  }
                }
              });
          box.show();
        };
  }

  @UiHandler("addRole")
  public void addButton(SelectEvent event) {
    name.clear();
    description.clear();
    name.enable();
    deleteRole.hide();
    grid.getSelectionModel().deselectAll();
    uncheckTreeAll();
    functionPermissionListPanel.mask();
    roleSaver =
        () -> {
          if (!name.isValid() || !description.isValid()) {
            return;
          }
          final ConfirmMessageBox box =
              new ConfirmMessageBox(messages.message(), messages.message_itemAddConfirm());
          box.addDialogHideHandler(
              new DialogHideHandler() {
                @Override
                public void onDialogHide(DialogHideEvent event) {
                  if (event.getHideButton() == PredefinedButton.YES) {
                    RoleDTO storeDto = store.findModelWithKey(name.getValue().trim());
                    if (storeDto == null) {
                      RoleDTO dto = new RoleDTO();
                      dto.setName(name.getValue());
                      dto.setDescription(description.getValue());
                      dto.setUpdateTime(new Date());
                      clientFactory
                          .getEventBus()
                          .fireEventFromSource(
                              new RoleEvent(com.hwacom.ngtms.common.am.event.RoleEvent.Action.ADD),
                              dto);
                    } else {
                      Info.display(messages.message(), messages.message_itemExisted());
                    }
                  }
                }
              });
          box.show();
        };
  }

  @UiHandler("deleteRole")
  public void deleteButton(SelectEvent event) {
    GWT.log("deleteButton Click!");
    if (!name.validate()) return;
    final ConfirmMessageBox box =
        new ConfirmMessageBox(messages.message(), messages.message_itemDeleteConfirm());
    box.addDialogHideHandler(
        new DialogHideHandler() {
          @Override
          public void onDialogHide(DialogHideEvent event) {
            if (event.getHideButton() == PredefinedButton.YES) {
              RoleDTO storeDto = store.findModelWithKey(name.getValue().trim());
              if (storeDto != null) {
                RoleDTO dto = new RoleDTO();
                dto.setName(name.getValue());
                dto.setDescription(description.getValue());
                dto.setUpdateTime(new Date());
                clientFactory
                    .getEventBus()
                    .fireEventFromSource(
                        new RoleEvent(com.hwacom.ngtms.common.am.event.RoleEvent.Action.DELETE),
                        dto);
              } else {
                Info.display(messages.message(), messages.message_itemNoExisted());
              }
            }
          }
        });
    box.show();
  }

  @UiHandler("saveRole")
  public void saveRole(SelectEvent event) {
    GWT.log("saveButton Click!");
    roleSaver.run();
  }

  @UiHandler("saveButton")
  public void saveButton(SelectEvent event) {
    GWT.log("saveButton Click!");
    RoleDTO selectedRole = grid.getSelectionModel().getSelectedItem();
    if (selectedRole == null) {
      Info.display(messages.message(), messages.message_notSelectedItem());
      return;
    }
    final ConfirmMessageBox box =
        new ConfirmMessageBox(messages.message(), messages.message_itemSaveConfirm());
    box.addDialogHideHandler(
        new DialogHideHandler() {
          @Override
          public void onDialogHide(DialogHideEvent event) {
            if (event.getHideButton() == PredefinedButton.YES) {
              if (selectedRole != null) {
                selectedRole.setFunctionPermissions(new HashSet<FunctionPermissionDTO>());
                for (FunctionPermissionDTO fpDto : treeStore.getAll()) {
                  CheckState checkState = tree.getChecked(fpDto);
                  if (CheckState.CHECKED.equals(checkState)
                      || CheckState.PARTIAL.equals(checkState)) {
                    selectedRole.addFunctionPermission(fpDto);
                  }
                }
                GWT.log("selectedRole=" + selectedRole.toString());
                clientFactory
                    .getEventBus()
                    .fireEventFromSource(
                        new RoleFunctionPermissionEvent(Action.SAVE), selectedRole);
              }
            }
          }
        });
    box.show();
  }

  private void fillTreeStoreSelected(RoleDTO roleDto) {
    Set<FunctionPermissionDTO> fpDtoList = roleDto.getFunctionPermissions();

    if (fpDtoList != null) {
      // 從 leaf 開始勾
      fpDtoList =
          fpDtoList
              .stream()
              .sorted(Comparator.comparing(FunctionPermissionDTO::getLevel).reversed())
              .collect(Collectors.toSet());
      Set<String> fpDtoIdList =
          fpDtoList.stream().map(FunctionPermissionDTO::getId).collect(Collectors.toSet());
      for (FunctionPermissionDTO fpDto : fpDtoList) {
        FunctionPermissionDTO storeFpDto = treeStore.findModelWithKey(fpDto.getId());
        if (storeFpDto != null) {
          if (tree.getCheckStyle() == CheckCascade.TRI) {
            tri(storeFpDto, fpDtoIdList, storeFpDto);
          } else if (tree.getCheckStyle() == CheckCascade.PARENTS) {
            tree.setChecked(storeFpDto, CheckState.CHECKED);
          }
        }
      }
    }
  }

  private void tri(
      FunctionPermissionDTO storeFpDto, Set<String> fpDtoIdList, FunctionPermissionDTO fpDto) {
    List<FunctionPermissionDTO> children = treeStore.getAllChildren(storeFpDto);
    if (children.size() > 0) {
      boolean allChecked = true;
      boolean allUnChecked = true;
      for (FunctionPermissionDTO child : children) {
        // check if any children not select
        boolean contain = fpDtoIdList.contains(child.getId());
        allChecked = allChecked && contain;
        allUnChecked = allUnChecked && !contain;
      }
      CheckState checkState;
      if (allChecked) {
        checkState = CheckState.CHECKED;
      } else if (allUnChecked) {
        checkState = CheckState.UNCHECKED;
      } else {
        checkState = CheckState.PARTIAL;
      }
      tree.setChecked(fpDto, checkState);
    } else {
      GWT.log("set checked=" + fpDto.getId());
      tree.setChecked(storeFpDto, CheckState.CHECKED);
    }
  }

  private void uncheckTreeAll() {
    List<FunctionPermissionDTO> list = treeStore.getAll();
    for (FunctionPermissionDTO item : list) {
      tree.setChecked(item, CheckState.UNCHECKED);
    }
  }

  @UiHandler("expandAll")
  public void expandAll(SelectEvent event) {
    tree.expandAll();
  }

  @UiHandler("collapseAll")
  public void collapseAll(SelectEvent event) {
    tree.collapseAll();
  }

  @Override
  protected void onUnload() {
    roleHandlerRegistration.removeHandler();
    handlerRegistration.removeHandler();
    super.onUnload();
  }

  public void removeStore(RoleDTO dto) {
    RoleDTO storeDto = store.findModelWithKey(dto.getName());
    if (storeDto != null) {
      store.remove(storeDto);
      Info.display(messages.message(), messages.message_deleteSuccessfully());
      addRole.fireEvent(new SelectEvent());
    }
  }

  public void addStore(RoleDTO dto) {
    store.add(dto);
    Info.display(messages.message(), messages.message_addSuccessfully());
  }

  public void updateSotre(RoleDTO dto) {
    GWT.log("updateSotre dto=" + dto);
    RoleDTO updateDto = store.findModelWithKey(dto.getName());
    if (updateDto != null) {
      store.update(dto);
      Info.display(messages.message(), messages.message_saveSuccessfully());
    }
  }

  public void initRoles(List<RoleDTO> roles) {
    store.clear();
    store.addAll(roles);
  }

  public void initFunctionPermission(List<FunctionPermissionDTO> functionPermissions) {
    treeStore.clear();
    functionPermissions.sort(
        Comparator.comparing(FunctionPermissionDTO::getLevel)
            .thenComparing(FunctionPermissionDTO::getSequence));
    GWT.log(functionPermissions.toString());
    for (FunctionPermissionDTO functionPermission : functionPermissions) {
      FunctionPermissionDTO root = treeStore.findModelWithKey(functionPermission.getParentId());
      if (root != null) {
        treeStore.add(root, functionPermission);
      } else {
        treeStore.add(functionPermission);
      }
    }
  }

  public void hideRolePanel() {
    rolePanel.hide();
  }

  public void setCheckStyle(CheckCascade checkCascade) {
    tree.setCheckStyle(checkCascade);
  }

  public interface RoleProperties extends PropertyAccess<RoleDTO> {
    @Path("name")
    ModelKeyProvider<RoleDTO> key();

    ValueProvider<RoleDTO, String> name();

    ValueProvider<RoleDTO, String> description();

    ValueProvider<RoleDTO, Date> updateTime();
  }

  interface FunctionPermissionPropertyAccess extends PropertyAccess<FunctionPermissionDTO> {
    ModelKeyProvider<FunctionPermissionDTO> id();

    ValueProvider<FunctionPermissionDTO, String> name();
  }

  class DefaultRoleFunctionPermissionEventHandler
      implements RoleFunctionPermissionEventHandler, RoleEventHandler {

    @Override
    public void onSave(RoleFunctionPermissionEvent event) {
      GWT.log("onSave...");
      RoleDTO dto = (RoleDTO) event.getSource();
      presenter.saveItem(dto);
    }

    @Override
    public void onSave(RoleEvent event) {
      GWT.log("onSave...");
      RoleDTO dto = (RoleDTO) event.getSource();
      presenter.saveItem(dto);
    }

    @Override
    public void onAdd(RoleEvent event) {
      GWT.log("onAdd...");
      RoleDTO dto = (RoleDTO) event.getSource();
      presenter.addItem(dto);
    }

    @Override
    public void onDelete(RoleEvent event) {
      GWT.log("onDelete...");
      RoleDTO dto = (RoleDTO) event.getSource();
      presenter.removeItem(dto);
    }
  }
}
