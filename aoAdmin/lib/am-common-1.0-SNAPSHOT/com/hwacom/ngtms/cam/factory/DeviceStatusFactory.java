/*
 * © HwaCom Systems Inc. 2015
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.cam.factory;

import com.hwacom.ngtms.c.shared.dto.DeviceStatusDTO;
import com.hwacom.ngtms.cam.client.ui.DeviceStatusViewer;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import java.util.ArrayList;
import java.util.List;

/**
 * 子系統各自提供實做 class，並在 <code>gwt.xml</code> 用
 *
 * <pre>{@code
 * <replace-with class="PKG_NAME.實做class">
 * 	<when-type-is class="com.hwacom.ngtms.common.am.factory.DeviceStatusFactory"/>
 * </replace-with>
 * }</pre>
 *
 * 來替換成各自的實做內容（deferred binding）。
 *
 * @see DeviceStatusViewer
 * @author monty.pan
 */
public abstract class DeviceStatusFactory {
  protected ArrayList<String> deviceType = new ArrayList<>();

  protected DeviceStatusFactory(String... typeList) {
    for (String dt : typeList) {
      deviceType.add(dt);
    }
  }

  public ArrayList<String> getDeviceTypeDTOList() {
    return deviceType;
  }

  public abstract List<ColumnConfig<DeviceStatusDTO, ?>> getColumnConfigList();

  /**
   * 子系統特有 column 的數量，這是右方「群組欄位快選」的所需設定參數。
   *
   * <p>目前是預留 API，實際上並沒有用到這個值。
   *
   * @return 各子系統特有的 column 數量
   */
  public abstract int customColumnLength();
}
