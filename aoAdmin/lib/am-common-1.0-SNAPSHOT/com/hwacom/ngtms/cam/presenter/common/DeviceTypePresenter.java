/*
 * © HwaCom Systems Inc. 2015
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.cam.presenter.common;

import com.google.gwt.core.client.GWT;
import com.hwacom.ngtms.c.shared.dto.DeviceTypeDTO;
import com.hwacom.ngtms.cam.client.HomeEP;
import com.hwacom.ngtms.cam.client.ui.component.DeviceTypeComboBox;
import java.util.List;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;

public class DeviceTypePresenter {

  private DeviceTypeComboBox viewer;

  public DeviceTypePresenter(DeviceTypeComboBox viewer) {
    this.viewer = viewer;
  }

  public void getDeviceTypeData(Boolean tcTypeOnly) {
    HomeEP.camService.getDeviceTypeList(
        tcTypeOnly,
        new MethodCallback<List<DeviceTypeDTO>>() {
          @Override
          public void onSuccess(Method method, List<DeviceTypeDTO> result) {
            if (result != null) {
              viewer.fillDeviceTypeComboBoxData(result);
            }
          }

          @Override
          public void onFailure(Method method, Throwable caught) {
            GWT.log("DeviceTypePresenter.getDeviceTypeData failed.", caught);
          }
        });
  }
}
