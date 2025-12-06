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
import com.hwacom.ngtms.room.am.view.RoomDeviceMonitorViewer;
import com.hwacom.ngtms.room.shared.dto.RoomBackgroundSvgConfigDTO;
import com.hwacom.ngtms.room.shared.dto.RoomCardReaderLogDTO;
import com.hwacom.ngtms.room.shared.dto.RoomCctvUrlDTO;
import com.hwacom.ngtms.room.shared.dto.RoomDeviceStatusDTO;
import com.hwacom.ngtms.room.shared.dto.RoomDeviceSubLocationConfigDTO;
import com.hwacom.ngtms.room.shared.dto.RoomDoDTO;
import java.util.List;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;

public class RoomDeviceMonitorPresenter {

  private RoomDeviceMonitorViewer viewer;

  public RoomDeviceMonitorPresenter(RoomDeviceMonitorViewer viewer) {
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
                "RoomDeviceMonitorPresenter.getRoomDeviceSubLocationConfigData failed.", exception);
          }
        });
  }

  public void getRoomLocationMapConfig(Integer hostId, String subLocationName) {
    RoomEP.commonService.getRoomLocationMapConfig(
        hostId,
        subLocationName,
        new MethodCallback<RoomBackgroundSvgConfigDTO>() {
          @Override
          public void onSuccess(Method method, RoomBackgroundSvgConfigDTO response) {
            if (response.getNameId() != null) {
              viewer.fillRoomSvg(response.getNameId());
            }
          }

          @Override
          public void onFailure(Method method, Throwable exception) {
            GWT.log("RoomDeviceMonitorPresenter.getRoomLocationMapConfig failed.", exception);
          }
        });
  }

  public void queryDevicePositionConfig(String svgName) {
    GWT.log("RoomDeviceMonitorPresenter.queryDevicePositionConfig");
    RoomEP.commonService.queryDevicePositionConfig(
        svgName,
        new MethodCallback<List<DeviceSvgPositionConfigDTO>>() {
          @Override
          public void onSuccess(Method method, List<DeviceSvgPositionConfigDTO> response) {
            viewer.fillDeviceSvg(svgName, response);
          }

          @Override
          public void onFailure(Method method, Throwable exception) {
            GWT.log("RoomDeviceMonitorPresenter.queryDevicePositionConfig failed.", exception);
          }
        });
  }

  public void refreshRoomDeviceStatus(List<String> deviceNameList) {
    RoomEP.commonService.refreshRoomDeviceStatus(
        deviceNameList,
        new MethodCallback<List<RoomDeviceStatusDTO>>() {
          @Override
          public void onSuccess(Method method, List<RoomDeviceStatusDTO> response) {
            viewer.refreshStatus(response);
          }

          @Override
          public void onFailure(Method method, Throwable exception) {
            GWT.log("RoomDeviceMonitorPresenter.refreshRoomDeviceStatus failed.", exception);
          }
        });
  }

  public void getRoomCctvUrl() {
    RoomEP.commonService.getRoomCctvUrl(
        new MethodCallback<String>() {
          @Override
          public void onSuccess(Method method, String response) {
            viewer.getCctvUrl(response);
          }

          @Override
          public void onFailure(Method method, Throwable exception) {
            GWT.log("RoomDeviceMonitorPresenter.getRoomCctvUrl failed.", exception);
          }
        });
  }

  public void getCardReaderData(String locName, String subLocationName) {
    RoomEP.commonService.getCardReaderData(
        locName,
        subLocationName,
        new MethodCallback<List<RoomCardReaderLogDTO>>() {
          @Override
          public void onSuccess(Method method, List<RoomCardReaderLogDTO> response) {
            viewer.generateDoorSvg(response);
          }

          @Override
          public void onFailure(Method method, Throwable exception) {
            GWT.log("RoomDeviceMonitorPresenter.getCardReaderData failed.", exception);
          }
        });
  }

  public void getRoomCctvUrls(String locName, String subLocationName) {
    RoomEP.commonService.getRoomCctvUrls(
        locName,
        subLocationName,
        new MethodCallback<List<RoomCctvUrlDTO>>() {
          @Override
          public void onSuccess(Method method, List<RoomCctvUrlDTO> response) {
            viewer.fillCctvUrl(response);
          }

          @Override
          public void onFailure(Method method, Throwable exception) {
            GWT.log("RoomDeviceMonitorPresenter.getRoomCctvUrls failed.", exception);
          }
        });
  }

  public void changeDoorStatus(String deviceName) {
    RoomEP.commonService.changeDoorOpen(
        deviceName,
        new MethodCallback<Boolean>() {
          @Override
          public void onSuccess(Method method, Boolean response) {
            viewer.showDoorOpenInfo(response);
          }

          @Override
          public void onFailure(Method method, Throwable exception) {
            GWT.log("RoomDeviceMonitorPresenter.changeDoorStatus failed.", exception);
          }
        });
  }

  public void updateDoDevice(RoomDoDTO dto) {
    RoomEP.commonService.updateDoDevice(
        dto,
        new MethodCallback<Boolean>() {
          @Override
          public void onSuccess(Method method, Boolean response) {
            viewer.openPower(response);
          }

          @Override
          public void onFailure(Method method, Throwable exception) {
            GWT.log("RoomDeviceMonitorPresenter.updateDoDevice failed.", exception);
          }
        });
  }
}
