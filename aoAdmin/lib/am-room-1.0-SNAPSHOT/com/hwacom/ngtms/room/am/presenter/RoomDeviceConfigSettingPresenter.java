/*
 * © HwaCom Systems Inc. 2015
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.room.am.presenter;

import com.google.gwt.core.shared.GWT;
import com.hwacom.ngtms.c.shared.dto.DeviceTypeDTO;
import com.hwacom.ngtms.room.am.RoomEP;
import com.hwacom.ngtms.room.am.view.RoomDeviceConfigSettingViewer;
import com.hwacom.ngtms.room.shared.dto.RoomDeviceConfigDTO;
import com.hwacom.ngtms.room.shared.dto.RoomDeviceSubLocationConfigDTO;
import java.util.List;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;

public class RoomDeviceConfigSettingPresenter {

  private RoomDeviceConfigSettingViewer viewer;

  public RoomDeviceConfigSettingPresenter(RoomDeviceConfigSettingViewer viewer) {
    this.viewer = viewer;
  }

  public void initGridData() {
    RoomEP.commonService.getRoomDeviceSubLocationConfigData(
        new MethodCallback<List<RoomDeviceSubLocationConfigDTO>>() {
          @Override
          public void onSuccess(Method method, List<RoomDeviceSubLocationConfigDTO> response) {
            if (response.size() > 0) {
              viewer.fillGridData(response);
            }
          }

          @Override
          public void onFailure(Method method, Throwable exception) {
            GWT.log(
                "RoomDeviceConfigSettingPresenter.getRoomDeviceSubLocationConfigData failed.",
                exception);
          }
        });
  }

  public void fiilDeviceGridData(String locName, String subLocation) {
    RoomEP.commonService.getRoomDeviceConfig(
        locName,
        subLocation,
        new MethodCallback<List<RoomDeviceConfigDTO>>() {
          @Override
          public void onSuccess(Method method, List<RoomDeviceConfigDTO> response) {
            viewer.fillDeviceGrid(response);
          }

          @Override
          public void onFailure(Method method, Throwable exception) {
            GWT.log("RoomDeviceConfigSettingPresenter.fiilDeviceGridData failed.", exception);
          }
        });
  }

  public void fiilTypeCB() {
    RoomEP.commonService.getRoomDeviceType(
        new MethodCallback<List<DeviceTypeDTO>>() {
          @Override
          public void onSuccess(Method method, List<DeviceTypeDTO> response) {
            if (response.size() > 0) {
              viewer.fillTypeCB(response);
            }
          }

          @Override
          public void onFailure(Method method, Throwable exception) {
            GWT.log("RoomDeviceConfigSettingPresenter.fiilTypeCB failed.", exception);
          }
        });
  }

  public void updateRoomDeviceConfig(RoomDeviceConfigDTO dto) {
    RoomEP.commonService.updateRoomDeviceConfig(
        dto,
        new MethodCallback<Boolean>() {
          @Override
          public void onSuccess(Method method, Boolean response) {
            viewer.updateConfig(response);
          }

          @Override
          public void onFailure(Method method, Throwable exception) {
            GWT.log("RoomDeviceConfigSettingPresenter.updateRoomDeviceConfig failed.", exception);
          }
        });
  }

  public void findSettedDevcieLocation(String deviceName) {
    RoomEP.commonService.querySelectedRoomSubLocation(
        deviceName,
        new MethodCallback<List<String>>() {
          @Override
          public void onSuccess(Method method, List<String> response) {
            viewer.setSettedDeviceLocation(response.get(0));
          }

          @Override
          public void onFailure(Method method, Throwable exception) {
            GWT.log("RoomDeviceConfigSettingPresenter.findSettedDevcieLocation failed.", exception);
          }
        });
  }
}
