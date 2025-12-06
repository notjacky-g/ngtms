/*
 * © HwaCom Systems Inc. 2015
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.cam.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Widget;
import com.hwacom.ngtms.c.shared.dto.DeviceConfigDTO;
import com.hwacom.ngtms.cam.images.AmImages;
import com.hwacom.ngtms.cam.view.Messages;
import com.hwacom.ngtms.cam.vo.RareWordVO;
import com.sencha.gxt.widget.core.client.TabPanel;
import java.util.ArrayList;
import java.util.List;

public class RareWordManagerViewer extends AmTab {

  private static RareWordManagerViewerUiBinder uiBinder =
      GWT.create(RareWordManagerViewerUiBinder.class);

  interface RareWordManagerViewerUiBinder extends UiBinder<Widget, RareWordManagerViewer> {}

  private Messages messages = GWT.create(Messages.class);

  @UiField public TabPanel tabPanel;

  @UiField(provided = true)
  RareWordViewer rareWordViewer;

  @UiField(provided = true)
  RareWordQueryViewer rareWordQueryViewer;

  public RareWordManagerViewer() {
    rareWordViewer = new RareWordViewer();
    rareWordQueryViewer = new RareWordQueryViewer();
    initWidget(uiBinder.createAndBindUi(this));
    setTabTitle(messages.rareWord());
    setButtonIcon(AmImages.INSTANCE.rareWord());
    setButtonOverIcon(AmImages.INSTANCE.overRareWord());
  }

  @UiHandler("tabPanel")
  public void onTabSelection(SelectionEvent<Widget> event) {
    Widget selectedItem = event.getSelectedItem();
    unactiveRareWordViewer();
    if (selectedItem == rareWordViewer) {
      activeRareWordViewer();
    } else if (selectedItem == rareWordQueryViewer) {
      rareWordQueryViewer.forceLayout();
    }
  }

  public RareWordViewer getRareWordViewer() {
    return rareWordViewer;
  }

  public RareWordQueryViewer getRareWordQueryViewer() {
    return rareWordQueryViewer;
  }

  public boolean isRareWordViewerActive() {
    return tabPanel.getActiveWidget() == rareWordViewer;
  }

  public void maskRareWordViewer() {
    rareWordViewer.mask();
  }

  public void activeRareWordViewer() {
    rareWordViewer.setActive(true);
  }

  public void unactiveRareWordViewer() {
    rareWordViewer.setActive(false);
    // 為了避免頁籤被關閉後再開啟時 ActiveX 的元件未顯示的問題
    maskRareWordViewer();
  }

  public void addDevices(List<DeviceConfigDTO> data) {
    rareWordViewer.addDevices(extractDeviceNames(data));

    List<RareWordVO> list = new ArrayList<>();
    for (DeviceConfigDTO each : data) {
      RareWordVO dto = new RareWordVO(each.getDeviceName(), null);
      dto.setDisplayName(each.getDisplayName());
      list.add(dto);
    }
    rareWordQueryViewer.addDevices(list);
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
    rareWordViewer.removeDevices(deviceNames);
    rareWordQueryViewer.removeDevices(deviceNames);
  }
}
