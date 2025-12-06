/*
 * © HwaCom Systems Inc. 2015
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.cam.client.ui.component;

import com.hwacom.ngtms.c.shared.dto.DeviceTypeDTO;
import com.hwacom.ngtms.cam.presenter.common.DeviceTypePresenter;
import com.sencha.gxt.cell.core.client.form.ComboBoxCell.TriggerAction;
import com.sencha.gxt.data.shared.LabelProvider;
import com.sencha.gxt.widget.core.client.form.SimpleComboBox;
import java.util.List;

public class DeviceTypeComboBox extends SimpleComboBox<DeviceTypeDTO> {

  private DeviceTypePresenter presenter = new DeviceTypePresenter(this);

  private Boolean tcTypeOnly = true;

  public DeviceTypeComboBox() {
    super(
        new LabelProvider<DeviceTypeDTO>() {

          @Override
          public String getLabel(DeviceTypeDTO item) {
            return item.getDescription();
          }
        });
    //Get deviceType data
    presenter.getDeviceTypeData(tcTypeOnly);

    // 初始化 deviceType
    this.setEditable(false);
    this.setTriggerAction(TriggerAction.ALL);
  }

  public void removeData(DeviceTypeDTO DeviceType) {
    this.getStore().remove(DeviceType);
  }

  public void fillDeviceTypeComboBoxData(List<DeviceTypeDTO> result) {
    this.clear();
    this.add(result);
    if (result.size() > 0) {
      this.setValue(result.get(0));
    }
  }

  public void setTcTypeOnly(Boolean tcTypeOnly) {
    this.tcTypeOnly = tcTypeOnly;
  }
}
