/*
 * © HwaCom Systems Inc. 2015
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.room.am.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Widget;
import com.hwacom.ngtms.cam.client.ui.AmTabPanel;
import com.hwacom.ngtms.hcce.am.factory.ClientFactory;
import com.hwacom.ngtms.room.am.event.RoomDeviceConfigEvent;
import com.hwacom.ngtms.room.am.event.RoomDeviceConfigEvent.RoomDeviceConfigEventHandler;
import com.hwacom.ngtms.room.am.images.roomMap.RoomMapImages;
import com.hwacom.ngtms.room.am.presenter.RoomDeviceConfigSettingPresenter;
import com.sencha.gxt.widget.core.client.Composite;
import com.sencha.gxt.widget.core.client.TabItemConfig;
import java.util.HashMap;
import java.util.Map;
import org.vectomatic.dom.svg.ui.SVGResource;

/** @author brian.cheng */
public class RoomViewer extends Composite {

  private static RoomViewImplUiBinder uiBinder = GWT.create(RoomViewImplUiBinder.class);

  interface RoomViewImplUiBinder extends UiBinder<Widget, RoomViewer> {}

  @UiField RoomDevicePositionEditViewer roomDevicePositionEditViewer;

  @UiField RoomDeviceMonitorViewer roomDeviceMonitorViewer;

  @UiField AmTabPanel amTabPanel;

  RoomDeviceConfigSettingViewer roomDeviceConfigSettingViewer = new RoomDeviceConfigSettingViewer();

  private final ClientFactory clientFactory = GWT.create(ClientFactory.class);

  public RoomViewer() {
    initWidget(uiBinder.createAndBindUi(this));
    // Map<Key = svgName, Value = SVGResource>
    Map<String, SVGResource> svgResourceMap = new HashMap<String, SVGResource>();
    svgResourceMap.put("northHoleRoom1F", RoomMapImages.INSTANCE.northHoleRoom1F());
    svgResourceMap.put("southHoleRoom1F", RoomMapImages.INSTANCE.southHoleRoom1F());
    roomDevicePositionEditViewer.setRoomBackground(svgResourceMap);
    roomDeviceMonitorViewer.setRoomBackground(svgResourceMap);

    RoomDeviceConfigSettingPresenter roomDeviceConfigSettingPresenter =
        new RoomDeviceConfigSettingPresenter(roomDeviceConfigSettingViewer);
    roomDeviceConfigSettingViewer.setPresenter(roomDeviceConfigSettingPresenter);
    roomDeviceConfigSettingViewer.setId("roomDeviceConfigSettingViewer");
    roomDeviceConfigSettingViewer.setTabTitle("監控點設定");

    clientFactory
        .getEventBus()
        .addHandler(RoomDeviceConfigEvent.TYPE, new DefaultRoomDeviceConfigEventHandler());
  }

  protected void onUnload() {
    super.onUnload();
  }

  class DefaultRoomDeviceConfigEventHandler implements RoomDeviceConfigEventHandler {

    @Override
    public void onClick(RoomDeviceConfigEvent event) {
      RoomDeviceConfigSettingViewer viewer =
          (RoomDeviceConfigSettingViewer)
              amTabPanel.findItem("roomDeviceConfigSettingViewer", true);
      if (viewer == null) {
        viewer = roomDeviceConfigSettingViewer;
        amTabPanel.add(viewer, new TabItemConfig(viewer.getTabTitle(), viewer.isClosable()));
      }
      amTabPanel.setActive(viewer);
      roomDeviceConfigSettingViewer.receiveSettingEvent((String) event.getSource());
    }

    @Override
    public void onAnalogRecord(RoomDeviceConfigEvent event) {
      // TODO Auto-generated method stub
    }
  }
}
