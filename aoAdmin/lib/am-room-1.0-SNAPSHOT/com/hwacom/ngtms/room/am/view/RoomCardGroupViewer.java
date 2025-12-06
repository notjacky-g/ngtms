package com.hwacom.ngtms.room.am.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Widget;
import com.hwacom.ngtms.cam.client.ui.AmTab;
import com.hwacom.ngtms.room.am.presenter.RoomCardGroupPresenter;
import com.hwacom.ngtms.room.shared.dto.RoomCardGroupConfigDTO;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.info.Info;
import java.util.ArrayList;
import java.util.List;

public class RoomCardGroupViewer extends AmTab {

  private static RoomCardGroupViewerUiBinder uiBinder =
      GWT.create(RoomCardGroupViewerUiBinder.class);

  interface RoomCardGroupViewerUiBinder extends UiBinder<Widget, RoomCardGroupViewer> {}

  private RoomCardGroupPropertyAcess roomCardGroupPropertyAccess =
      GWT.create(RoomCardGroupPropertyAcess.class);

  private RoomCardGroupPresenter presenter = new RoomCardGroupPresenter(this);

  private final Messages messages = GWT.create(Messages.class);

  @UiField Grid<RoomCardGroupConfigDTO> grid;

  @UiField(provided = true)
  ListStore<RoomCardGroupConfigDTO> listStore;

  @UiField(provided = true)
  ColumnModel<RoomCardGroupConfigDTO> columnModel;

  @UiField TextField groupName;

  @UiField TextField memo;

  public RoomCardGroupViewer() {
    listStore = new ListStore<>(roomCardGroupPropertyAccess.id());

    initColumnModel();
    initWidget(uiBinder.createAndBindUi(this));
    addEventHandlers();
  }

  @UiHandler("create")
  public void onCreate(SelectEvent event) {
    if (validation()) {
      RoomCardGroupConfigDTO cardGroup = new RoomCardGroupConfigDTO();
      cardGroup.setName(groupName.getCurrentValue());
      cardGroup.setMemo(memo.getCurrentValue());
      presenter.createRoomCardGroupConfig(cardGroup);
    }
  }

  @UiHandler("modify")
  public void onModify(SelectEvent event) {
    if (grid.getSelectionModel().getSelectedItem() != null) {
      RoomCardGroupConfigDTO cardGroup = grid.getSelectionModel().getSelectedItem();
      cardGroup.setName(groupName.getCurrentValue());
      cardGroup.setMemo(memo.getCurrentValue());
      presenter.updateRoomCardGroupConfig(cardGroup);
    } else {
      Info.display("修改定期卡群組", "修改失敗，請選一筆資料");
    }
  }

  @UiHandler("delete")
  public void onDelete(SelectEvent event) {
    if (grid.getSelectionModel().getSelectedItem() != null) {
      RoomCardGroupConfigDTO cardGroup = grid.getSelectionModel().getSelectedItem();
      Long id = cardGroup.getId();
      presenter.deleteRoomCardGroupConfig(id);
    } else {
      Info.display("刪除定期卡群組失敗", "請選擇一筆資料");
    }
  }

  /**
   * 驗證資料
   *
   * @return
   */
  private boolean validation() {
    if (groupName.getCurrentValue() == null) {
      Info.display("設定定期卡群組失敗", "請填入名稱");
      return false;
    }
    for (RoomCardGroupConfigDTO dto : listStore.getAll()) {
      if (dto.getName().equals(groupName.getCurrentValue())) {
        Info.display("設定定期卡群組失敗", "已有重複的名稱");
        return false;
      }
    }
    return true;
  }

  private void initColumnModel() {
    List<ColumnConfig<RoomCardGroupConfigDTO, ?>> columnConfigs = new ArrayList<>();

    ColumnConfig<RoomCardGroupConfigDTO, String> groupNameConfig =
        new ColumnConfig<>(roomCardGroupPropertyAccess.name(), 50, "群組名稱");
    columnConfigs.add(groupNameConfig);
    ColumnConfig<RoomCardGroupConfigDTO, String> memoConfig =
        new ColumnConfig<>(roomCardGroupPropertyAccess.memo(), 150, "備註");
    columnConfigs.add(memoConfig);

    columnModel = new ColumnModel<>(columnConfigs);
  }

  private void addEventHandlers() {
    grid.getSelectionModel()
        .addSelectionHandler(
            new SelectionHandler<RoomCardGroupConfigDTO>() {
              @Override
              public void onSelection(SelectionEvent<RoomCardGroupConfigDTO> event) {
                GWT.log("grid onSelection.");
                RoomCardGroupConfigDTO dto = grid.getSelectionModel().getSelectedItem();
                groupName.setText(dto.getName());
                memo.setText(dto.getMemo());
              }
            });
  }

  public void fillData(List<RoomCardGroupConfigDTO> result) {
    listStore.clear();
    listStore.addAll(result);
  }

  public void setPresenter(RoomCardGroupPresenter presenter) {
    this.presenter = presenter;
  }

  interface RoomCardGroupPropertyAcess extends PropertyAccess<RoomCardGroupConfigDTO> {
    ModelKeyProvider<RoomCardGroupConfigDTO> id();

    ValueProvider<RoomCardGroupConfigDTO, String> name();

    ValueProvider<RoomCardGroupConfigDTO, String> memo();
  }
}
