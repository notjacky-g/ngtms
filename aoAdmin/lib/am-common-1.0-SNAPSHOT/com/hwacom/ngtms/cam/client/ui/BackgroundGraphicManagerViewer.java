/*
 * © HwaCom Systems Inc. 2015
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.cam.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Widget;
import com.hwacom.ngtms.c.shared.dto.DeviceConfigDTO;
import com.hwacom.ngtms.cam.images.AmImages;
import com.hwacom.ngtms.cam.view.Messages;
import com.hwacom.ngtms.cam.vo.BackgroundGraphicVO;
import java.util.ArrayList;
import java.util.List;

public class BackgroundGraphicManagerViewer extends AmTab {

  private static BackgroundGraphicManagerViewerUiBinder uiBinder =
      GWT.create(BackgroundGraphicManagerViewerUiBinder.class);

  interface BackgroundGraphicManagerViewerUiBinder
      extends UiBinder<Widget, BackgroundGraphicManagerViewer> {}

  private Messages messages = GWT.create(Messages.class);

  @UiField(provided = true)
  BackgroundGraphicEditViewer editViewer;

  @UiField(provided = true)
  BackgroundGraphicQueryViewer queryViewer;

  public BackgroundGraphicManagerViewer() {
    editViewer = new BackgroundGraphicEditViewer();
    queryViewer = new BackgroundGraphicQueryViewer();
    initWidget(uiBinder.createAndBindUi(this));
    setTabTitle(messages.backgroundGraphic());
    setButtonIcon(AmImages.INSTANCE.backgroundGraphic());
    setButtonOverIcon(AmImages.INSTANCE.overBackgroundGraphic());
  }

  public void addDevices(List<DeviceConfigDTO> data) {
    editViewer.addDevices(extractDeviceNames(data));

    List<BackgroundGraphicVO> list = new ArrayList<>();
    for (DeviceConfigDTO each : data) {
      list.add(new BackgroundGraphicVO(each.getDeviceName(), each.getDisplayName()));
    }
    queryViewer.addDevices(list);
  }

  private List<String> extractDeviceNames(List<DeviceConfigDTO> data) {
    List<String> deviceNames = new ArrayList<String>();
    for (DeviceConfigDTO eachDevice : data) {
      String deviceName = eachDevice.getDeviceName();
      deviceNames.add(deviceName);
    }
    return deviceNames;
  }

  public void removeDevices(List<DeviceConfigDTO> data) {
    List<String> deviceNames = extractDeviceNames(data);
    editViewer.removeDevices(deviceNames);
    queryViewer.removeDevices(deviceNames);
  }

  public BackgroundGraphicEditViewer getBackgroundGraphicEditViewer() {
    return editViewer;
  }

  public BackgroundGraphicQueryViewer getBackgroundGraphicQueryViewer() {
    return queryViewer;
  }
}
