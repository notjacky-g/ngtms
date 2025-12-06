/*
 * © HwaCom Systems Inc. 2015
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.cam.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor.Path;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Widget;
import com.hwacom.ngtms.c.shared.DragItem;
import com.hwacom.ngtms.c.shared.dto.DeviceConfigDTO;
import com.hwacom.ngtms.cam.client.event.AmEventCenter;
import com.hwacom.ngtms.cam.client.event.DeviceConfigDeselectEvent;
import com.hwacom.ngtms.cam.client.event.DeviceConfigDeselectHandler;
import com.hwacom.ngtms.cam.client.event.DeviceConfigSelectEvent;
import com.hwacom.ngtms.cam.client.event.DeviceConfigSelectHandler;
import com.hwacom.ngtms.cam.client.event.DragItemDeselectEvent;
import com.hwacom.ngtms.cam.client.event.DragItemDeselectHandler;
import com.hwacom.ngtms.cam.client.event.DragItemSelectEvent;
import com.hwacom.ngtms.cam.client.event.DragItemSelectHandler;
import com.hwacom.ngtms.cam.client.event.RoadTreeViewerEvent;
import com.hwacom.ngtms.cam.client.event.RoadTreeViewerEvent.Action;
import com.hwacom.ngtms.cam.client.event.RoadTreeViewerMaskEvent;
import com.hwacom.ngtms.cam.client.event.RoadTreeViewerMaskEventHandler;
import com.hwacom.ngtms.cam.client.ui.dnd.AddType;
import com.hwacom.ngtms.cam.client.ui.dnd.RemoveDeviceUtil;
import com.hwacom.ngtms.cam.util.DataCenter;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;
import com.sencha.gxt.dnd.core.client.DND.Operation;
import com.sencha.gxt.dnd.core.client.DndDropEvent;
import com.sencha.gxt.dnd.core.client.DndDropEvent.DndDropHandler;
import com.sencha.gxt.dnd.core.client.DropTarget;
import com.sencha.gxt.dnd.core.client.GridDragSource;
import com.sencha.gxt.widget.core.client.Composite;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;
import com.sencha.gxt.widget.core.client.grid.Grid;
import java.util.ArrayList;
import java.util.List;

/** {@link RoadTreeViewer}下方的已選取設備區 */
public class RoadTreeSelectedViewer extends Composite {
  private static RoadTreeSelectedViewUiBinder uiBinder =
      GWT.create(RoadTreeSelectedViewUiBinder.class);

  interface RoadTreeSelectedViewUiBinder extends UiBinder<Widget, RoadTreeSelectedViewer> {}

  private static final GridProperties props = GWT.create(GridProperties.class);

  @UiField Grid<DragItem> grid;

  @UiField(provided = true)
  ListStore<DragItem> store;

  @UiField(provided = true)
  ColumnModel<DragItem> cm;

  private DropTarget dropTarget = new DropTarget(this);
  private boolean selectionMode = true;

  public RoadTreeSelectedViewer() {
    ArrayList<ColumnConfig<DragItem, ?>> columnConfigList = new ArrayList<>();
    columnConfigList.add(
        new ColumnConfig<>(
            new ValueProvider<DragItem, String>() {
              @Override
              public String getValue(DragItem item) {
                if (null == item.getSelectedDisplayName()) {
                  return DataCenter.getDisplayName(item.getUuid());
                }
                return item.getSelectedDisplayName();
              }

              @Override
              public void setValue(DragItem object, String value) {}

              @Override
              public String getPath() {
                return "roadTreeSelectedProvider";
              }
            }));
    cm = new ColumnModel<>(columnConfigList);
    store = new ListStore<>(props.id());
    initWidget(uiBinder.createAndBindUi(this));

    // 處理外部送來的命令，並將已選取設備mask/unmask
    AmEventCenter.addRoadTreeViewerMaskEventHandler(
        new RoadTreeViewerMaskEventHandler() {
          @Override
          public void onMaskDevices(RoadTreeViewerMaskEvent event) {
            if (event.getSource() == null) {
              grid.mask();
            } else {
              grid.mask((String) event.getSource());
            }
          }

          @Override
          public void onUnmaskDevices(RoadTreeViewerMaskEvent event) {
            grid.unmask();
          }
        });

    // 當已選取設備區內的設備被點選時,觸發事件並傳遞被選取的DeviceConfigDTO
    grid.getSelectionModel()
        .addSelectionHandler(
            new SelectionHandler<DragItem>() {
              @Override
              public void onSelection(SelectionEvent<DragItem> event) {
                AmEventCenter.fireEventFromSource(
                    new RoadTreeViewerEvent(Action.SELECT_SELECTEDDEVICE), event.getSelectedItem());
              }
            });
  }

  /**
   * 設定 DnD Item 的類別。
   *
   * <p>如果類別是 DeviceConfig，則收到 DndDropEvent 時發送 DeviceConfigSelectEvent， 附帶資料為 DeviceConfigDTO 的
   * ArrayList。
   *
   * <p>若類別是 Other，則收到 DndDropEvent 時發送 DragItemSelectEvent， 附帶資料為 AmDragItem 的 ArrayList。
   *
   * @param dndGroup
   */
  public void setDndType(AddType dndGroup) {
    // drag source 的處理
    GridDragSource<DragItem> gds = new GridDragSource<>(grid);
    gds.setGroup(RemoveDeviceUtil.GROUP);

    dropTarget.setOperation(Operation.COPY);
    dropTarget.setGroup(dndGroup.toString());

    switch (dndGroup) {
      case DeviceConfig:
        // 因Drop必須拖曳到其他Widget才會觸發事件, 所以使用DeviceConfigSelectHandler來處理
        AmEventCenter.addDeviceConfigSelectHandler(
            new DeviceConfigSelectHandler() {
              @Override
              public void onDeviceConfigSelect(DeviceConfigSelectEvent event) {
                if (store.getAll().isEmpty()) {
                  addItem(event.getData().get(0));
                  AmEventCenter.fireEventFromSource(
                      new RoadTreeViewerEvent(Action.SELECT_SELECTEDDEVICE),
                      event.getData().get(0));
                }
                if (selectionMode) {
                  for (DragItem item : event.getData()) {
                    addItem(item);
                  }
                } else {
                  store.clear();
                  addItem(event.getData().get(0));
                }
              }
            });

        // 處理DeviceConfigDTO移除事件
        AmEventCenter.addDeviceConfigDeselectHandler(
            new DeviceConfigDeselectHandler() {
              @Override
              public void onDeviceConfigDeselect(DeviceConfigDeselectEvent event) {
                for (DeviceConfigDTO dc : event.getData()) {
                  store.remove(store.findModelWithKey(dc.getDeviceName()));
                }
                if (store.getAll().isEmpty()) {
                  AmEventCenter.fireEvent(new RoadTreeViewerEvent(Action.REMOTE_ALLDEVICE));
                }
              }
            });

        dropTarget.addDropHandler(
            new DndDropHandler() {
              @Override
              public void onDrop(DndDropEvent event) {
                @SuppressWarnings("unchecked")
                ArrayList<DeviceConfigDTO> data = (ArrayList<DeviceConfigDTO>) event.getData();
                AmEventCenter.fireEvent(new DeviceConfigSelectEvent(data));
              }
            });
        break;
      case Other:
        AmEventCenter.addDragItemSelectHandler(
            new DragItemSelectHandler() {
              @Override
              public void onDragItemSelect(DragItemSelectEvent event) {
                if (store.getAll().isEmpty()) {
                  addItem(event.getData().get(0));
                  AmEventCenter.fireEventFromSource(
                      new RoadTreeViewerEvent(Action.SELECT_SELECTEDDEVICE),
                      event.getData().get(0));
                }
                if (selectionMode) {
                  for (DragItem item : event.getData()) {
                    addItem(item);
                  }
                } else {
                  store.clear();
                  addItem(event.getData().get(0));
                }
              }
            });

        AmEventCenter.addDragItemDeselectHandler(
            new DragItemDeselectHandler() {
              @Override
              public void onDragItemDeselect(DragItemDeselectEvent event) {
                for (DragItem item : event.getData()) {
                  store.remove(store.findModelWithKey(item.getUuid()));
                }
                if (store.getAll().isEmpty()) {
                  AmEventCenter.fireEvent(new RoadTreeViewerEvent(Action.REMOTE_ALLDEVICE));
                }
              }
            });

        dropTarget.addDropHandler(
            new DndDropHandler() {
              @Override
              public void onDrop(DndDropEvent event) {
                @SuppressWarnings("unchecked")
                ArrayList<DragItem> data = (ArrayList<DragItem>) event.getData();
                AmEventCenter.fireEvent(new DragItemSelectEvent(data));
              }
            });
        break;
    }
  }

  /**
   * 設定已選取設備區目前的已選取設備
   *
   * @param index 欲設定為已選取設備的設備在 grid store 中的 index
   */
  public void setGridSelection(int index) {
    if (index < store.size()) {
      List<DragItem> selection = new ArrayList<>();
      selection.add(store.get(index));
      grid.getSelectionModel().setSelection(selection);
    }
  }

  /**
   * 設定已選取設備區的 Grid 的 selectionMode
   *
   * @param selectionMode true 多選，false 單選
   */
  public void setSelectionMode(boolean selectionMode) {
    this.selectionMode = selectionMode;
  }

  /**
   * 直接取得已選取設備區的 Grid
   *
   * @return the grid
   */
  public Grid<DragItem> getGrid() {
    return grid;
  }

  private void addItem(DragItem dragItem) {
    if (!store.getAll().contains(dragItem)) {
      if (store.findModelWithKey(dragItem.getUuid()) == null) {
        store.add(dragItem);
      }
    }
  }

  public void removeAllDevices() {
    store.clear();
  }

  interface GridProperties extends PropertyAccess<DragItem> {
    @Path("uuid")
    ModelKeyProvider<DragItem> id();
  }
}
