/*

* © HwaCom Systems Inc. 2013
* All Rights Reserved
* No part of this software or any of its contents may be reproduced, copied, modified or adapted,
* without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
*/
package com.hwacom.ngtms.common.am.view;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.DateCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor.Path;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.Widget;
import com.hwacom.ngtms.common.am.event.AccountDataEvent;
import com.hwacom.ngtms.common.am.event.AccountDataEvent.AccountDataEventHandler;
import com.hwacom.ngtms.common.am.event.UnitViewerEvent;
import com.hwacom.ngtms.common.am.event.UnitViewerEvent.UnitViewerEventHandler;
import com.hwacom.ngtms.common.am.event.UserEvent;
import com.hwacom.ngtms.common.am.event.UserEvent.Action;
import com.hwacom.ngtms.common.am.event.UserEvent.UserEventHandler;
import com.hwacom.ngtms.common.am.event.UserViewerEvent;
import com.hwacom.ngtms.common.am.presenter.UserPresenter;
import com.hwacom.ngtms.common.am.util.DateTimeUtil;
import com.hwacom.ngtms.common.am.view.RoleFunctionPermissionViewer.RoleProperties;
import com.hwacom.ngtms.common.shared.dto.RoleDTO;
import com.hwacom.ngtms.common.shared.dto.UnitDTO;
import com.hwacom.ngtms.common.shared.dto.UserDTO;
import com.hwacom.ngtms.hcce.am.factory.ClientFactory;
import com.sencha.gxt.core.client.Style.SelectionMode;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.core.client.util.ToggleGroup;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;
import com.sencha.gxt.data.shared.SortDir;
import com.sencha.gxt.data.shared.Store.StoreSortInfo;
import com.sencha.gxt.dnd.core.client.DND.Operation;
import com.sencha.gxt.dnd.core.client.DropTarget;
import com.sencha.gxt.dnd.core.client.ListViewDragSource;
import com.sencha.gxt.dnd.core.client.ListViewDropTarget;
import com.sencha.gxt.widget.core.client.Composite;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.Dialog.PredefinedButton;
import com.sencha.gxt.widget.core.client.ListView;
import com.sencha.gxt.widget.core.client.box.ConfirmMessageBox;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.HorizontalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.HorizontalLayoutContainer.HorizontalLayoutData;
import com.sencha.gxt.widget.core.client.event.DialogHideEvent;
import com.sencha.gxt.widget.core.client.event.DialogHideEvent.DialogHideHandler;
import com.sencha.gxt.widget.core.client.event.RowClickEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.form.DateField;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.form.Radio;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.grid.GridView;
import com.sencha.gxt.widget.core.client.info.Info;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class UserViewer extends Composite {

  private static UserViewerUiBinder uiBinder = GWT.create(UserViewerUiBinder.class);

  interface UserViewerUiBinder extends UiBinder<Widget, UserViewer> {}

  private static final String DATE_FORMAT_PATTERN = "yyyy/MM/dd";

  private static final String DATE_TIME_FORMAT_PATTERN = "yyyy/MM/dd HH:mm:ss";

  private static final Messages messages = GWT.create(Messages.class);

  private static final UserProperties props = GWT.create(UserProperties.class);
  private static final RoleProperties roleProps = GWT.create(RoleProperties.class);
  private static final UnitProperties unitProps = GWT.create(UnitProperties.class);

  private UserPresenter presenter;
  private final ClientFactory clientFactory = GWT.create(ClientFactory.class);
  private final HandlerRegistration handlerRegistration;
  private final HandlerRegistration accountDataHandlerRegistration;
  private final HandlerRegistration unitViewerEventHandlerRegistration;

  private String listViewGroup1 = "nodeListViewGroup1";
  private String listViewGroup2 = "nodeListViewGroup2";
  private ListStore<RoleDTO> allNodeStore;
  private ListStore<RoleDTO> selectedNodeStore;
  private ListView<RoleDTO, String> allListView;
  private ListView<RoleDTO, String> selectedListView;

  private String unitListViewGroup1 = "unitListViewGroup1";
  private String unitListViewGroup2 = "unitListViewGroup2";
  private ListStore<UnitDTO> allUnitStore;
  private ListStore<UnitDTO> selectedUnitStore;
  private ListView<UnitDTO, String> allUnitListView;
  private ListView<UnitDTO, String> selectedUnitListView;

  @UiField ContentPanel gridContentPanel;

  @UiField(provided = true)
  ColumnModel<UserDTO> cm;

  @UiField(provided = true)
  ListStore<UserDTO> store;

  @UiField GridView<UserDTO> view;
  @UiField Grid<UserDTO> grid;

  @UiField UserBasicInfo basicInfo;

  @UiField FieldLabel enableFieldLabel;

  @UiField Radio enableYesRadio;
  @UiField Radio enableNoRadio;
  Boolean enable = false;

  @UiField FieldLabel checkExpiredFieldLabel;

  @UiField Radio checkExpiredYesRadio;
  @UiField Radio checkExpiredNoRadio;
  Boolean checkExpired = false;

  @UiField FieldLabel startTimeFieldLabel;

  @UiField DateField startTime;

  @UiField FieldLabel endTimeFieldLabel;

  @UiField DateField endTime;

  @UiField FieldLabel rolesFieldLabel;

  @UiField HorizontalLayoutContainer listContainer;
  @UiField HorizontalLayoutContainer unitListContainer;

  @UiField TextButton addButton;

  @UiField TextButton deleteButton;

  @UiField TextButton saveButton;

  public UserViewer() {
    store = new ListStore<UserDTO>(props.key());
    initColumnModel();
    initWidget(uiBinder.createAndBindUi(this));
    store.addSortInfo(new StoreSortInfo<UserDTO>(props.name(), SortDir.ASC));
    grid.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    handlerRegistration =
        clientFactory.getEventBus().addHandler(UserEvent.TYPE, new DefaultUserEventHandler());
    accountDataHandlerRegistration =
        clientFactory
            .getEventBus()
            .addHandler(AccountDataEvent.TYPE, new DefaultAccountDataEventHandler());
    unitViewerEventHandlerRegistration =
        clientFactory
            .getEventBus()
            .addHandler(UnitViewerEvent.TYPE, new DefaultUnitViewerEventHandler());
    presenter = new UserPresenter(this);
    initStartTime();
    initEndTime();
    ToggleGroup enableToggle = new ToggleGroup();
    enableToggle.add(enableYesRadio);
    enableToggle.add(enableNoRadio);
    enableToggle.addValueChangeHandler(
        new ValueChangeHandler<HasValue<Boolean>>() {
          @Override
          public void onValueChange(ValueChangeEvent<HasValue<Boolean>> event) {
            ToggleGroup group = (ToggleGroup) event.getSource();
            Radio radio = (Radio) group.getValue();
            if (radio.getBoxLabel().equals(enableYesRadio.getBoxLabel())) {
              enable = true;
            } else if (radio.getBoxLabel().equals(enableNoRadio.getBoxLabel())) {
              enable = false;
            }
          }
        });

    ToggleGroup checkExpiredToggle = new ToggleGroup();
    checkExpiredToggle.add(checkExpiredYesRadio);
    checkExpiredToggle.add(checkExpiredNoRadio);
    checkExpiredToggle.addValueChangeHandler(
        new ValueChangeHandler<HasValue<Boolean>>() {
          @Override
          public void onValueChange(ValueChangeEvent<HasValue<Boolean>> event) {
            ToggleGroup group = (ToggleGroup) event.getSource();
            Radio radio = (Radio) group.getValue();
            if (radio.getBoxLabel().equals(checkExpiredYesRadio.getBoxLabel())) {
              checkExpired = true;
            } else if (radio.getBoxLabel().equals(checkExpiredNoRadio.getBoxLabel())) {
              checkExpired = false;
            }
          }
        });
    initListView();
    initUnitListView();
  }

  private void initColumnModel() {
    List<ColumnConfig<UserDTO, ?>> columnConfigs = new ArrayList<ColumnConfig<UserDTO, ?>>();

    ColumnConfig<UserDTO, String> login = new ColumnConfig<UserDTO, String>(props.login());
    login.setWidth(160);
    login.setHeader(messages.user_login());
    login.setHideable(true);
    login.setMenuDisabled(false);
    login.setSortable(true);
    columnConfigs.add(login);

    ColumnConfig<UserDTO, String> name = new ColumnConfig<UserDTO, String>(props.name());
    name.setWidth(160);
    name.setHeader(messages.user_name());
    name.setHideable(true);
    name.setMenuDisabled(false);
    name.setSortable(true);
    columnConfigs.add(name);

    ColumnConfig<UserDTO, String> accountType =
        new ColumnConfig<UserDTO, String>(UserProperties.accountType);
    accountType.setWidth(70);
    accountType.setHeader(messages.user_accountType());
    accountType.setHideable(true);
    accountType.setMenuDisabled(false);
    accountType.setSortable(true);
    columnConfigs.add(accountType);

    ColumnConfig<UserDTO, Date> lockTime = new ColumnConfig<>(props.lockTime());
    lockTime.setWidth(120);
    lockTime.setHeader(messages.user_lockTime());
    lockTime.setCell(new DateCell(DateTimeFormat.getFormat(DATE_TIME_FORMAT_PATTERN)));
    lockTime.setHideable(true);
    lockTime.setMenuDisabled(false);
    lockTime.setSortable(true);
    columnConfigs.add(lockTime);

    ColumnConfig<UserDTO, Integer> loginFailureCount =
        new ColumnConfig<>(props.loginFailureCount());
    loginFailureCount.setWidth(90);
    loginFailureCount.setHeader(messages.user_loginFailureCount());
    loginFailureCount.setHideable(true);
    loginFailureCount.setMenuDisabled(false);
    loginFailureCount.setSortable(true);
    columnConfigs.add(loginFailureCount);

    ColumnConfig<UserDTO, Date> lastPwdChangeTime = new ColumnConfig<>(props.lastPwdChangeTime());
    lastPwdChangeTime.setWidth(120);
    lastPwdChangeTime.setHeader(messages.user_lastPwdChangeTime());
    lastPwdChangeTime.setCell(new DateCell(DateTimeFormat.getFormat(DATE_TIME_FORMAT_PATTERN)));
    lastPwdChangeTime.setHideable(true);
    lastPwdChangeTime.setMenuDisabled(false);
    lastPwdChangeTime.setSortable(true);
    columnConfigs.add(lastPwdChangeTime);

    ColumnConfig<UserDTO, Boolean> enable = new ColumnConfig<UserDTO, Boolean>(props.enable());
    enable.setWidth(70);
    enable.setHeader(messages.user_enable());
    enable.setHideable(true);
    enable.setMenuDisabled(false);
    enable.setSortable(true);
    enable.setCell(
        new AbstractCell<Boolean>() {
          @Override
          public void render(
              com.google.gwt.cell.client.Cell.Context context, Boolean value, SafeHtmlBuilder sb) {
            String style = "style='color: " + (value ? "green" : "red") + "'";
            sb.appendHtmlConstant(
                "<span "
                    + style
                    + ">"
                    + (value ? messages.message_yes() : messages.message_no())
                    + "</span>");
          }
        });
    columnConfigs.add(enable);

    ColumnConfig<UserDTO, String> roleStr = new ColumnConfig<UserDTO, String>(props.roleStr());
    roleStr.setWidth(200);
    roleStr.setHeader(messages.user_selectedRoles());
    roleStr.setHideable(true);
    roleStr.setMenuDisabled(false);
    roleStr.setSortable(true);
    columnConfigs.add(roleStr);

    ColumnConfig<UserDTO, String> unitStr = new ColumnConfig<UserDTO, String>(props.unitStr());
    unitStr.setWidth(150);
    unitStr.setHeader(messages.user_selectedUnits());
    unitStr.setHideable(true);
    unitStr.setMenuDisabled(false);
    unitStr.setSortable(true);
    columnConfigs.add(unitStr);

    ColumnConfig<UserDTO, Date> updateTime = new ColumnConfig<UserDTO, Date>(props.updateTime());
    updateTime.setWidth(120);
    updateTime.setHeader(messages.user_updateTime());
    updateTime.setCell(new DateCell(DateTimeFormat.getFormat(DATE_TIME_FORMAT_PATTERN)));
    columnConfigs.add(updateTime);

    ColumnConfig<UserDTO, String> mobile = new ColumnConfig<UserDTO, String>(props.mobile());
    mobile.setWidth(100);
    mobile.setHeader(messages.user_mobile());
    mobile.setHideable(true);
    mobile.setMenuDisabled(false);
    mobile.setSortable(true);
    columnConfigs.add(mobile);

    ColumnConfig<UserDTO, String> mail = new ColumnConfig<UserDTO, String>(props.mail());
    mail.setWidth(150);
    mail.setHeader(messages.user_mail());
    mail.setHideable(true);
    mail.setMenuDisabled(false);
    mail.setSortable(true);
    columnConfigs.add(mail);

    ColumnConfig<UserDTO, String> descriptionConfig =
        new ColumnConfig<UserDTO, String>(props.description());
    descriptionConfig.setWidth(250);
    descriptionConfig.setHeader(messages.user_description());
    descriptionConfig.setHideable(true);
    descriptionConfig.setMenuDisabled(false);
    descriptionConfig.setSortable(true);
    columnConfigs.add(descriptionConfig);

    ColumnConfig<UserDTO, Boolean> checkExpired =
        new ColumnConfig<UserDTO, Boolean>(props.checkExpired());
    checkExpired.setWidth(100);
    checkExpired.setHeader(messages.user_checkExpired());
    checkExpired.setHideable(true);
    checkExpired.setMenuDisabled(false);
    checkExpired.setSortable(true);
    checkExpired.setCell(
        new AbstractCell<Boolean>() {
          @Override
          public void render(
              com.google.gwt.cell.client.Cell.Context context, Boolean value, SafeHtmlBuilder sb) {
            String style = "style='color: " + (value ? "green" : "red") + "'";
            sb.appendHtmlConstant(
                "<span "
                    + style
                    + ">"
                    + (value ? messages.message_yes() : messages.message_no())
                    + "</span>");
          }
        });
    columnConfigs.add(checkExpired);

    ColumnConfig<UserDTO, Date> startTime = new ColumnConfig<UserDTO, Date>(props.startTime());
    startTime.setWidth(80);
    startTime.setHeader(messages.user_startTime());
    startTime.setCell(new DateCell(DateTimeFormat.getFormat(DATE_FORMAT_PATTERN)));
    columnConfigs.add(startTime);

    ColumnConfig<UserDTO, Date> endTime = new ColumnConfig<UserDTO, Date>(props.endTime());
    endTime.setWidth(80);
    endTime.setHeader(messages.user_endTime());
    endTime.setCell(new DateCell(DateTimeFormat.getFormat(DATE_FORMAT_PATTERN)));
    columnConfigs.add(endTime);

    cm = new ColumnModel<UserDTO>(columnConfigs);
  }

  private void initStartTime() {
    startTime.setValue(DateTimeUtil.dayStart(new Date()));
  }

  private void initEndTime() {
    endTime.setValue(DateTimeUtil.dayEnd(new Date()));
  }

  private void initListView() {
    allNodeStore = new ListStore<RoleDTO>(roleProps.key());
    selectedNodeStore = new ListStore<RoleDTO>(roleProps.key());
    allListView = new ListView<RoleDTO, String>(allNodeStore, roleProps.name());
    selectedListView = new ListView<RoleDTO, String>(selectedNodeStore, roleProps.name());
    ListViewDragSource<RoleDTO> allListViewDragSource =
        new ListViewDragSource<RoleDTO>(allListView);
    allListViewDragSource.setGroup(listViewGroup1);

    ListViewDropTarget<RoleDTO> selectedListViewDropSource =
        new ListViewDropTarget<RoleDTO>(selectedListView);
    selectedListViewDropSource.setGroup(listViewGroup1);
    selectedListViewDropSource.setOperation(Operation.COPY);

    ListViewDragSource<RoleDTO> selectedListViewDragSource =
        new ListViewDragSource<RoleDTO>(selectedListView);
    selectedListViewDragSource.setGroup(listViewGroup2);

    ContentPanel allListCp = new ContentPanel();
    allListCp.setHeading(messages.user_allRoles());
    allListCp.setWidget(allListView);
    DropTarget dropTarget = new DropTarget(allListCp);
    dropTarget.setGroup(listViewGroup2);

    ContentPanel selectedListCp = new ContentPanel();
    selectedListCp.setHeading(messages.user_selectedRoles());
    selectedListCp.setWidget(selectedListView);
    listContainer.add(allListCp, new HorizontalLayoutData(.5, 1, new Margins(4)));
    listContainer.add(selectedListCp, new HorizontalLayoutData(.5, 1, new Margins(4, 4, 4, 0)));
  }

  private void initUnitListView() {
    allUnitStore = new ListStore<UnitDTO>(unitProps.id());
    selectedUnitStore = new ListStore<UnitDTO>(unitProps.id());
    allUnitListView = new ListView<UnitDTO, String>(allUnitStore, unitProps.name());
    selectedUnitListView = new ListView<UnitDTO, String>(selectedUnitStore, unitProps.name());
    ListViewDragSource<UnitDTO> allListViewDragSource =
        new ListViewDragSource<UnitDTO>(allUnitListView);
    allListViewDragSource.setGroup(unitListViewGroup1);

    ListViewDropTarget<UnitDTO> selectedListViewDropSource =
        new ListViewDropTarget<UnitDTO>(selectedUnitListView);
    selectedListViewDropSource.setGroup(unitListViewGroup1);
    selectedListViewDropSource.setOperation(Operation.COPY);

    ListViewDragSource<UnitDTO> selectedListViewDragSource =
        new ListViewDragSource<UnitDTO>(selectedUnitListView);
    selectedListViewDragSource.setGroup(unitListViewGroup2);

    ContentPanel allListCp = new ContentPanel();
    allListCp.setHeading(messages.user_allUnits());
    allListCp.setWidget(allUnitListView);
    DropTarget dropTarget = new DropTarget(allListCp);
    dropTarget.setGroup(unitListViewGroup2);

    ContentPanel selectedListCp = new ContentPanel();
    selectedListCp.setHeading(messages.user_selectedUnits());
    selectedListCp.setWidget(selectedUnitListView);
    unitListContainer.add(allListCp, new HorizontalLayoutData(.5, 1, new Margins(4)));
    unitListContainer.add(selectedListCp, new HorizontalLayoutData(.5, 1, new Margins(4, 4, 4, 0)));
  }

  @UiHandler("addButton")
  public void addButton(SelectEvent event) {
    GWT.log("addButton Click!");
    if (!validate()) return;
    final ConfirmMessageBox box =
        new ConfirmMessageBox(messages.message(), messages.message_itemAddConfirm());
    box.addDialogHideHandler(
        new DialogHideHandler() {
          @Override
          public void onDialogHide(DialogHideEvent event) {
            if (event.getHideButton() == PredefinedButton.YES) {
              UserDTO storeDto = store.findModelWithKey(basicInfo.login.getValue());
              if (storeDto == null) {
                UserDTO dto = convertUser();
                clientFactory.getEventBus().fireEventFromSource(new UserEvent(Action.ADD), dto);
              } else {
                Info.display(messages.message(), messages.message_itemExisted());
              }
            }
          }
        });
    box.show();
  }

  private boolean validate() {
    boolean valid = basicInfo.validate();
    if (startTime.getValue().getTime() > endTime.getValue().getTime()) {
      Info.display(messages.message(), messages.message_startTimeGreaterThanEndTimeError());
      valid = false;
    }
    return valid;
  }

  @UiHandler("deleteButton")
  public void deleteButton(SelectEvent event) {
    GWT.log("deleteButton Click!");
    if (!basicInfo.login.validate()) return;
    final ConfirmMessageBox box =
        new ConfirmMessageBox(messages.message(), messages.message_itemDeleteConfirm());
    box.addDialogHideHandler(
        new DialogHideHandler() {
          @Override
          public void onDialogHide(DialogHideEvent event) {
            if (event.getHideButton() == PredefinedButton.YES) {
              UserDTO storeDto = store.findModelWithKey(basicInfo.login.getValue());
              if (storeDto != null) {
                UserDTO dto = convertUser();
                clientFactory.getEventBus().fireEventFromSource(new UserEvent(Action.DELETE), dto);
              } else {
                Info.display(messages.message(), messages.message_itemNoExisted());
              }
            }
          }
        });
    box.show();
  }

  @UiHandler("saveButton")
  public void saveButton(SelectEvent event) {
    GWT.log("saveButton Click!");
    if (!validate()) return;
    final ConfirmMessageBox box =
        new ConfirmMessageBox(messages.message(), messages.message_itemSaveConfirm());
    box.addDialogHideHandler(
        new DialogHideHandler() {
          @Override
          public void onDialogHide(DialogHideEvent event) {
            if (event.getHideButton() == PredefinedButton.YES) {
              UserDTO storeDto = store.findModelWithKey(basicInfo.login.getValue());
              if (storeDto != null) {
                UserDTO dto = convertUser();
                clientFactory.getEventBus().fireEventFromSource(new UserEvent(Action.SAVE), dto);
              } else {
                Info.display(messages.message(), messages.message_itemNoExisted());
              }
            }
          }
        });
    box.show();
  }

  private UserDTO convertUser() {
    UserDTO dto = new UserDTO();
    dto.setBasicInfo(basicInfo.getDto());
    dto.setCheckExpired(checkExpired);
    dto.setStartTime(startTime.getValue());
    dto.setEndTime(endTime.getValue());
    dto.setEnable(enable);
    dto.setUpdateTime(new Date());
    for (RoleDTO role : selectedNodeStore.getAll()) {
      RoleDTO role2 = new RoleDTO();
      role2.setName(role.getName());
      role2.setDescription(role.getDescription());
      role2.setUpdateTime(role.getUpdateTime());
      dto.addRole(role2);
    }
    for (UnitDTO unit : selectedUnitStore.getAll()) {
      dto.addUnit(unit);
    }
    return dto;
  }

  public void showUserData(int index) {
    selectedNodeStore.clear();
    selectedUnitStore.clear();
    UserDTO dto = store.get(index);
    if (dto != null) {
      basicInfo.setDto(dto.getBasicInfo());
      if (dto.getStartTime() == null) {
        initStartTime();
      } else {
        startTime.setValue(dto.getStartTime());
      }
      if (dto.getEndTime() == null) {
        initEndTime();
      } else {
        endTime.setValue(dto.getEndTime());
      }
      if (dto.getCheckExpired()) {
        checkExpiredYesRadio.setValue(true);
        checkExpiredNoRadio.setValue(false);
        checkExpired = true;
      } else {
        checkExpiredYesRadio.setValue(false);
        checkExpiredNoRadio.setValue(true);
        checkExpired = false;
      }
      if (dto.getEnable()) {
        enableYesRadio.setValue(true);
        enableNoRadio.setValue(false);
        enable = true;
      } else {
        enableYesRadio.setValue(false);
        enableNoRadio.setValue(true);
        enable = false;
      }
      if (dto.getRoles() != null) {
        for (RoleDTO role : dto.getRoles()) {
          selectedNodeStore.add(role);
        }
      }
      if (dto.getUnits() != null) {
        for (UnitDTO unit : dto.getUnits()) {
          selectedUnitStore.add(unit);
        }
      }
    }
  }

  @UiHandler("grid")
  public void rowClick(RowClickEvent event) {
    showUserData(event.getRowIndex());
  }

  public interface UserProperties extends PropertyAccess<UserDTO> {
    @Path("login")
    ModelKeyProvider<UserDTO> key();

    ValueProvider<UserDTO, String> login();

    ValueProvider<UserDTO, String> pwd1();

    ValueProvider<UserDTO, String> name();

    ValueProvider<UserDTO, String> description();

    ValueProvider<UserDTO, Date> startTime();

    ValueProvider<UserDTO, Date> endTime();

    ValueProvider<UserDTO, Date> updateTime();

    ValueProvider<UserDTO, Boolean> checkExpired();

    ValueProvider<UserDTO, Boolean> enable();

    ValueProvider<UserDTO, String> roleStr();

    ValueProvider<UserDTO, String> mobile();

    ValueProvider<UserDTO, String> mail();

    ValueProvider<UserDTO, String> unitStr();

    ValueProvider<UserDTO, String> accountType =
        new ValueProvider<UserDTO, String>() {
          @Override
          public void setValue(UserDTO object, String value) {}

          @Override
          public String getValue(UserDTO object) {
            return Objects.equals(Boolean.TRUE, object.getCheckExpired())
                ? messages.user_accountType_temporary()
                : messages.user_accountType_forever();
          }

          @Override
          public String getPath() {
            return "accountType";
          }
        };

    ValueProvider<UserDTO, Date> lockTime();

    ValueProvider<UserDTO, Integer> loginFailureCount();

    ValueProvider<UserDTO, Date> lastPwdChangeTime();
  }

  interface UnitProperties extends PropertyAccess<UnitDTO> {
    @Path("name")
    ModelKeyProvider<UnitDTO> id();

    ValueProvider<UnitDTO, String> name();
  }

  @Override
  protected void onUnload() {
    handlerRegistration.removeHandler();
    accountDataHandlerRegistration.removeHandler();
    unitViewerEventHandlerRegistration.removeHandler();
    super.onUnload();
  }

  public void unitOnlyMode() {
    basicInfo.disable();
    enableFieldLabel.disable();
    enableYesRadio.disable();
    enableNoRadio.disable();
    checkExpiredFieldLabel.disable();
    checkExpiredYesRadio.disable();
    checkExpiredNoRadio.disable();
    startTimeFieldLabel.disable();
    endTimeFieldLabel.disable();
    rolesFieldLabel.disable();

    addButton.disable();
    deleteButton.disable();
  }

  public void init(List<UserDTO> users) {
    store.clear();
    store.addAll(users);
    clientFactory
        .getEventBus()
        .fireEventFromSource(new UserViewerEvent(UserViewerEvent.Action.GRID_READY), this);
  }

  public void selectFirstRow() {
    int index = 0;
    grid.getSelectionModel().select(index, false);
    showUserData(index);
  }

  public void addStore(UserDTO dto) {
    store.add(dto);
    grid.getSelectionModel().select(dto, false);
    Info.display(messages.message(), messages.message_addSuccessfully());
  }

  public void removeStore(UserDTO dto) {
    UserDTO storeDto = store.findModelWithKey(dto.getBasicInfo().getLogin());
    if (storeDto != null) {
      store.remove(storeDto);
      Info.display(messages.message(), messages.message_deleteSuccessfully());
    }
  }

  public void updateStore(UserDTO dto) {
    GWT.log("updateSotre dto=" + dto);
    UserDTO updateDto = store.findModelWithKey(dto.getBasicInfo().getLogin());
    if (updateDto != null) {
      GWT.log(updateDto.toString());
      store.update(dto);
      Info.display(messages.message(), messages.message_saveSuccessfully());
    }
  }

  public void setPresenter(UserPresenter presenter) {
    this.presenter = presenter;
  }

  public void initRoles(List<RoleDTO> roleList) {
    if (roleList != null) {
      allNodeStore.clear();
      allNodeStore.addAll(roleList);
    }
  }

  public void initUnits(List<UnitDTO> unitList) {
    if (unitList != null) {
      allUnitStore.clear();
      allUnitStore.addAll(unitList);
    }
  }

  public void addButtonInGridContentPanel(TextButton button) {
    gridContentPanel.addButton(button);
  }

  class DefaultUserEventHandler implements UserEventHandler {

    @Override
    public void onSave(UserEvent event) {
      GWT.log("onSave...");
      UserDTO dto = (UserDTO) event.getSource();
      presenter.saveItem(dto);
    }

    @Override
    public void onAdd(UserEvent event) {
      GWT.log("onAdd...");
      UserDTO dto = (UserDTO) event.getSource();
      presenter.addItem(dto);
    }

    @Override
    public void onDelete(UserEvent event) {
      GWT.log("onDelete...");
      UserDTO dto = (UserDTO) event.getSource();
      presenter.removeItem(dto);
    }
  }

  class DefaultAccountDataEventHandler implements AccountDataEventHandler {

    @Override
    public void onRoleInitData(AccountDataEvent event) {
      @SuppressWarnings("unchecked")
      List<RoleDTO> roleList = (List<RoleDTO>) event.getSource();
      GWT.log("onRoleInitData roleList =" + roleList.toString());
      if (roleList != null) {
        allNodeStore.clear();
        allNodeStore.addAll(roleList);
      }
    }
  }

  class DefaultUnitViewerEventHandler implements UnitViewerEventHandler {
    @Override
    public void onUnitAdded(UnitViewerEvent event) {
      allUnitStore.add((UnitDTO) event.getSource());
    }

    @Override
    public void onUnitRemoved(UnitViewerEvent event) {
      UnitDTO unitDTO = (UnitDTO) event.getSource();
      allUnitStore.remove(unitDTO);
      selectedUnitStore.remove(unitDTO);
    }

    @Override
    public void onUnitUpdated(UnitViewerEvent event) {
      allUnitStore.update((UnitDTO) event.getSource());
    }
  }
}
