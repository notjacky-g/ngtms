/*
 * © HwaCom Systems Inc. 2015
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.room.am.presenter;

import com.google.gwt.core.shared.GWT;
import com.hwacom.ngtms.c.shared.dto.DeviceSvgPositionConfigDTO;
import com.hwacom.ngtms.room.am.RoomEP;
import com.hwacom.ngtms.room.am.view.RoomDevicePositionEditViewer;
import com.hwacom.ngtms.room.shared.dto.RoomBackgroundSvgConfigDTO;
import com.sencha.gxt.widget.core.client.info.Info;
import java.util.List;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;

public class RoomDevicePositionEditPresenter {

  private RoomDevicePositionEditViewer viewer;

  public RoomDevicePositionEditPresenter(RoomDevicePositionEditViewer viewer) {
    this.viewer = viewer;
    getBackgroundSvgConfig();
  }

  public void getBackgroundSvgConfig() {
    RoomEP.commonService.getBackgroundSvgConfig(
        new MethodCallback<List<RoomBackgroundSvgConfigDTO>>() {
          @Override
          public void onSuccess(Method method, List<RoomBackgroundSvgConfigDTO> response) {
            viewer.fillAllBackgroundSvg(response);
          }

          @Override
          public void onFailure(Method method, Throwable exception) {
            GWT.log("RoomDevicePositionEditPresenter.getBackgroundSvgConfig failed.", exception);
          }
        });
  }

  public void queryDevicePositionConfig(String imageName) {
    GWT.log("RoomDevicePositionEditPresenter.queryDevicePositionConfig");
    RoomEP.commonService.queryDevicePositionConfig(
        imageName,
        new MethodCallback<List<DeviceSvgPositionConfigDTO>>() {
          @Override
          public void onSuccess(Method method, List<DeviceSvgPositionConfigDTO> response) {
            viewer.fillDeviceSvg(response);
          }

          @Override
          public void onFailure(Method method, Throwable exception) {
            GWT.log("RoomDevicePositionEditPresenter.queryDevicePositionConfig failed.", exception);
          }
        });
  }

  public void saveRoomDevicePositionConfigs(List<DeviceSvgPositionConfigDTO> position) {
    RoomEP.commonService.saveRoomDevicePositionConfigs(
        position,
        new MethodCallback<Boolean>() {
          @Override
          public void onSuccess(Method method, Boolean result) {
            if (result) {
              Info.display(RoomEP.messages.info_save(), RoomEP.messages.info_successFully());
              viewer.sendToMonitor();
            } else {
              Info.display(RoomEP.messages.info_save(), RoomEP.messages.info_fail());
            }
          }

          @Override
          public void onFailure(Method method, Throwable exception) {
            GWT.log(
                "RoomDevicePositionEditPresenter.saveDdsDevicePositionConfigs failed.", exception);
          }
        });
  }
}
