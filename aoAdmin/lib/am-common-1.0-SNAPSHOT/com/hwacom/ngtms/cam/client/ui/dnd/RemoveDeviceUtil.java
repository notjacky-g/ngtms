/*
 * © HwaCom Systems Inc. 2015
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.cam.client.ui.dnd;

import com.hwacom.ngtms.c.shared.HasDeviceConfig;
import com.hwacom.ngtms.c.shared.dto.DeviceConfigDTO;
import com.hwacom.ngtms.cam.client.ui.RoadTreeViewer;
import com.hwacom.ngtms.cam.util.DataCenter;
import com.sencha.gxt.dnd.core.client.DndDragStartEvent;
import com.sencha.gxt.dnd.core.client.DndDragStartEvent.DndDragStartHandler;
import com.sencha.gxt.dnd.core.client.GridDragSource;
import com.sencha.gxt.widget.core.client.grid.Grid;
import java.util.ArrayList;
import java.util.List;

/**
 * @see RoadTreeViewer
 * @author monty.pan
 */
public class RemoveDeviceUtil {
  public static final String GROUP = "RemoveDevice";

  /** 讓 grid 掛上 {@link GridDragSource}，並設定 DnD 的 group 參數。 */
  public static void buildDeviceGrid(Grid<DeviceConfigDTO> grid) {
    GridDragSource<DeviceConfigDTO> gds = new GridDragSource<DeviceConfigDTO>(grid);
    gds.setGroup(GROUP);
  }

  /**
   * 讓 grid 掛上 {@link GridDragSource}，並設定 DnD 的 group 參數。 此外，會在 {@link
   * DndDragStartHandler#onDragStart(DndDragStartEvent)} 時， 會執行 {@link
   * DndDragStartEvent#setData(Object)}，將內容值轉成 {@link DeviceConfigDTO} 的 {@link ArrayList}。
   */
  public static <T extends HasDeviceConfig> void buildHasDeviceGrid(Grid<T> grid) {
    GridDragSource<T> dragSource = new GridDragSource<>(grid);
    dragSource.setGroup(RemoveDeviceUtil.GROUP);
    dragSource.addDragStartHandler(
        new DndDragStartHandler() {
          @SuppressWarnings("unchecked")
          @Override
          public void onDragStart(DndDragStartEvent event) {
            ArrayList<DeviceConfigDTO> result = new ArrayList<>();
            for (T data : (List<T>) event.getData()) {
              result.add(data.getDeviceConfig());
            }
            event.setData(result);
          }
        });
  }

  /**
   * 目標功能和buildHasDeviceGrid一致, 但是你的Grid的的model只有可以對應到deviceName, 卻沒有DeviceConfigDTO 可以使用這個
   *
   * @param grid
   * @param deviceNameAccess 從T中的取出deviceName
   * @param <T>
   */
  public static <T> void buildHasDeviceGrid(
      Grid<T> grid, final DeviceNameAccess<T> deviceNameAccess) {
    GridDragSource<T> dragSource = new GridDragSource<>(grid);
    dragSource.setGroup(RemoveDeviceUtil.GROUP);
    dragSource.addDragStartHandler(
        new DndDragStartEvent.DndDragStartHandler() {
          @SuppressWarnings("unchecked")
          @Override
          public void onDragStart(DndDragStartEvent event) {
            ArrayList<DeviceConfigDTO> result = new ArrayList<>();
            for (T data : (List<T>) event.getData()) {
              result.add(DataCenter.getDeviceConfig(deviceNameAccess.getDeviceName(data)));
            }
            event.setData(result);
          }
        });
  }

  public interface DeviceNameAccess<T> {
    String getDeviceName(T t);
  }
}
