package com.hwacom.ngtms.common.am.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor.Path;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Widget;
import com.hwacom.ngtms.common.am.event.UnitViewerEvent;
import com.hwacom.ngtms.common.am.event.UnitViewerEvent.Action;
import com.hwacom.ngtms.common.am.presenter.UnitPresenter;
import com.hwacom.ngtms.common.shared.dto.UnitDTO;
import com.hwacom.ngtms.hcce.am.factory.ClientFactory;
import com.sencha.gxt.core.client.Style.SelectionMode;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;
import com.sencha.gxt.widget.core.client.Composite;
import com.sencha.gxt.widget.core.client.Dialog.PredefinedButton;
import com.sencha.gxt.widget.core.client.box.ConfirmMessageBox;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.event.DialogHideEvent;
import com.sencha.gxt.widget.core.client.event.DialogHideEvent.DialogHideHandler;
import com.sencha.gxt.widget.core.client.event.RowClickEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.form.CheckBox;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.grid.GridView;
import com.sencha.gxt.widget.core.client.info.Info;
import java.util.ArrayList;
import java.util.List;

public class UnitViewer extends Composite {

  private static UnitViewerUiBinder uiBinder = GWT.create(UnitViewerUiBinder.class);

  interface UnitViewerUiBinder extends UiBinder<Widget, UnitViewer> {}

  private static final Messages messages = GWT.create(Messages.class);
  private final UnitPropertyAccess props = GWT.create(UnitPropertyAccess.class);
  private UnitPresenter presenter;

  private ClientFactory clientFactory = GWT.create(ClientFactory.class);

  @UiField Grid<UnitDTO> grid;

  @UiField(provided = true)
  ListStore<UnitDTO> listStore;

  @UiField(provided = true)
  ColumnModel<UnitDTO> columnModel;

  @UiField GridView<UnitDTO> gridView;

  @UiField TextField nameField;

  @UiField TextField fullNameField;

  @UiField TextField manInChargeField;

  @UiField TextField addressField;

  @UiField TextField emailField;

  @UiField TextField phoneField;

  @UiField TextField faxField;

  @UiField TextField taxIdField;

  @UiField TextField descriptionField;

  @UiField CheckBox approveCheckBox;

  @UiField TextButton saveButton;

  @UiField TextButton deleteButton;

  public UnitViewer() {
    listStore = new ListStore<UnitDTO>(props.id());
    initColumnModel();
    initWidget(uiBinder.createAndBindUi(this));
    grid.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    presenter = new UnitPresenter(this);
  }

  private void initColumnModel() {
    List<ColumnConfig<UnitDTO, ?>> columnConfigs = new ArrayList<ColumnConfig<UnitDTO, ?>>();

    ColumnConfig<UnitDTO, String> unit =
        new ColumnConfig<UnitDTO, String>(props.name(), 140, messages.unit_name());
    unit.setMenuDisabled(true);
    unit.setSortable(true);
    columnConfigs.add(unit);

    ColumnConfig<UnitDTO, String> fullName =
        new ColumnConfig<UnitDTO, String>(props.fullName(), 210, messages.unit_fullName());
    fullName.setMenuDisabled(true);
    fullName.setSortable(true);
    columnConfigs.add(fullName);

    ColumnConfig<UnitDTO, String> manInCharge =
        new ColumnConfig<UnitDTO, String>(props.manInCharge(), 50, messages.unit_manInCharge());
    manInCharge.setMenuDisabled(true);
    manInCharge.setSortable(true);
    columnConfigs.add(manInCharge);

    ColumnConfig<UnitDTO, String> address =
        new ColumnConfig<UnitDTO, String>(props.address(), 320, messages.unit_address());
    address.setMenuDisabled(true);
    address.setSortable(true);
    columnConfigs.add(address);

    ColumnConfig<UnitDTO, String> email =
        new ColumnConfig<UnitDTO, String>(props.email(), 220, messages.unit_email());
    email.setMenuDisabled(true);
    email.setSortable(true);
    columnConfigs.add(email);

    ColumnConfig<UnitDTO, String> phone =
        new ColumnConfig<UnitDTO, String>(props.phone(), 120, messages.unit_phone());
    phone.setMenuDisabled(true);
    phone.setSortable(true);
    columnConfigs.add(phone);

    ColumnConfig<UnitDTO, String> fax =
        new ColumnConfig<UnitDTO, String>(props.fax(), 120, messages.unit_fax());
    fax.setMenuDisabled(true);
    fax.setSortable(true);
    columnConfigs.add(fax);

    ColumnConfig<UnitDTO, String> taxId =
        new ColumnConfig<UnitDTO, String>(props.taxId(), 70, messages.unit_taxId());
    taxId.setMenuDisabled(true);
    taxId.setSortable(true);
    columnConfigs.add(taxId);

    ColumnConfig<UnitDTO, String> description =
        new ColumnConfig<UnitDTO, String>(props.description(), 140, messages.unit_description());
    description.setMenuDisabled(true);
    description.setSortable(true);
    columnConfigs.add(description);

    ColumnConfig<UnitDTO, String> haveAccountApprove =
        new ColumnConfig<UnitDTO, String>(accountApproveProvider, 85, messages.unit_approve());
    haveAccountApprove.setMenuDisabled(true);
    haveAccountApprove.setSortable(true);
    columnConfigs.add(haveAccountApprove);

    columnModel = new ColumnModel<UnitDTO>(columnConfigs);
  }

  public void init(List<UnitDTO> units) {
    listStore.clear();
    listStore.addAll(units);
  }

  public void addStore(UnitDTO dto) {
    listStore.add(dto);
    clientFactory.getEventBus().fireEventFromSource(new UnitViewerEvent(Action.UNIT_ADDED), dto);
    Info.display(messages.message(), messages.message_addSuccessfully());
  }

  public void removeStore(UnitDTO dto) {
    UnitDTO storeDto = listStore.findModelWithKey(dto.getName());
    if (storeDto != null) {
      listStore.remove(storeDto);
      Info.display(messages.message(), messages.message_deleteSuccessfully());
    }
    clientFactory.getEventBus().fireEventFromSource(new UnitViewerEvent(Action.UNIT_REMOVED), dto);
  }

  public void updateStore(UnitDTO dto) {
    UnitDTO updateDto = listStore.findModelWithKey(dto.getName());
    if (updateDto != null) {
      listStore.update(dto);
      Info.display(messages.message(), messages.message_saveSuccessfully());
    }
    clientFactory.getEventBus().fireEventFromSource(new UnitViewerEvent(Action.UNIT_UPDATED), dto);
  }

  public void setPresenter(UnitPresenter presenter) {
    this.presenter = presenter;
  }

  @UiHandler("grid")
  public void onRowClick(RowClickEvent event) {
    UnitDTO dto = listStore.get(event.getRowIndex());
    if (dto != null) {
      nameField.setValue(dto.getName());
      fullNameField.setValue(dto.getFullName());
      manInChargeField.setValue(dto.getManInCharge());
      addressField.setValue(dto.getAddress());
      emailField.setValue(dto.getEmail());
      phoneField.setValue(dto.getPhone());
      faxField.setValue(dto.getFax());
      taxIdField.setValue(dto.getTaxId());
      descriptionField.setValue(dto.getDescription());
      approveCheckBox.setValue(dto.getHaveAccountApprove());
    }
  }

  @UiHandler("addButton")
  public void onAddButton(SelectEvent event) {
    if (!nameField.validate() || !fullNameField.validate()) return;
    ConfirmMessageBox box =
        new ConfirmMessageBox(messages.message(), messages.message_itemAddConfirm());
    box.addDialogHideHandler(
        new DialogHideHandler() {
          @Override
          public void onDialogHide(DialogHideEvent event) {
            if (event.getHideButton() == PredefinedButton.YES) {
              UnitDTO unit = listStore.findModelWithKey(nameField.getCurrentValue());
              if (unit == null) {
                UnitDTO dto = getUnitDTOFromUI();
                presenter.addItem(dto);
              } else {
                Info.display(messages.message(), messages.message_itemExisted());
              }
            }
          }
        });
    box.show();
  }

  @UiHandler("saveButton")
  public void onSaveButton(SelectEvent event) {
    if (!nameField.validate() || !fullNameField.validate()) return;
    ConfirmMessageBox box =
        new ConfirmMessageBox(messages.message(), messages.message_itemSaveConfirm());
    box.addDialogHideHandler(
        new DialogHideHandler() {
          @Override
          public void onDialogHide(DialogHideEvent event) {
            if (event.getHideButton() == PredefinedButton.YES) {
              UnitDTO unit = listStore.findModelWithKey(nameField.getCurrentValue());
              if (unit != null) {
                UnitDTO dto = getUnitDTOFromUI();
                presenter.saveItem(dto);
              } else {
                Info.display(messages.message(), messages.message_itemNoExisted());
              }
            }
          }
        });
    box.show();
  }

  @UiHandler("deleteButton")
  public void onDeleteButton(SelectEvent event) {
    ConfirmMessageBox box =
        new ConfirmMessageBox(messages.message(), messages.message_itemDeleteConfirm());
    box.addDialogHideHandler(
        new DialogHideHandler() {
          @Override
          public void onDialogHide(DialogHideEvent event) {
            if (event.getHideButton() == PredefinedButton.YES) {
              UnitDTO unit = listStore.findModelWithKey(nameField.getCurrentValue());
              if (unit != null) {
                UnitDTO dto = getUnitDTOFromUI();
                presenter.removeItem(dto);
              } else {
                Info.display(messages.message(), messages.message_itemNoExisted());
              }
            }
          }
        });
    box.show();
  }

  private UnitDTO getUnitDTOFromUI() {
    UnitDTO dto = new UnitDTO();
    dto.setName(nameField.getCurrentValue());
    dto.setFullName(fullNameField.getCurrentValue());
    dto.setManInCharge(manInChargeField.getCurrentValue());
    dto.setAddress(addressField.getCurrentValue());
    dto.setEmail(emailField.getCurrentValue());
    dto.setPhone(phoneField.getCurrentValue());
    dto.setFax(faxField.getCurrentValue());
    dto.setTaxId(taxIdField.getCurrentValue());
    dto.setDescription(descriptionField.getCurrentValue());
    dto.setHaveAccountApprove(approveCheckBox.getValue());
    return dto;
  }

  interface UnitPropertyAccess extends PropertyAccess<UnitDTO> {
    @Path("name")
    ModelKeyProvider<UnitDTO> id();

    ValueProvider<UnitDTO, String> name();

    ValueProvider<UnitDTO, String> fullName();

    ValueProvider<UnitDTO, String> manInCharge();

    ValueProvider<UnitDTO, String> address();

    ValueProvider<UnitDTO, String> email();

    ValueProvider<UnitDTO, String> phone();

    ValueProvider<UnitDTO, String> fax();

    ValueProvider<UnitDTO, String> taxId();

    ValueProvider<UnitDTO, String> description();
  }

  private final ValueProvider<UnitDTO, String> accountApproveProvider =
      new ValueProvider<UnitDTO, String>() {
        @Override
        public String getValue(UnitDTO object) {
          if (object.getHaveAccountApprove()) {
            return messages.yes();
          } else {
            return messages.no();
          }
        }

        @Override
        public void setValue(UnitDTO object, String value) {}

        @Override
        public String getPath() {
          return "haveAccountApprove";
        }
      };
}
