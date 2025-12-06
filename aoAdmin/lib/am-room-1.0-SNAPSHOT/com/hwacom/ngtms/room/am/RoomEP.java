/*
 * © HwaCom Systems Inc. 2015
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.room.am;

import com.google.gwt.core.client.GWT;
import com.hwacom.ngtms.cam.client.HomeEP;
import com.hwacom.ngtms.common.am.AmEntryPoint;
import com.hwacom.ngtms.room.am.restygwt.RoomCommonRestService;
import com.hwacom.ngtms.room.am.view.Messages;
import com.hwacom.ngtms.room.am.view.RoomViewer;
import java.util.AbstractMap.SimpleEntry;
import java.util.Arrays;
import org.fusesource.restygwt.client.RestService;

public class RoomEP extends AmEntryPoint {

  public static Messages messages = GWT.create(Messages.class);

  public static RoomCommonRestService commonService = GWT.create(RoomCommonRestService.class);

  public RoomEP() {
    super(
        messages.room(),
        Arrays.asList(new SimpleEntry<>("Room", commonService)),
        HomeEP.camService);
  }

  @Override
  protected void serviceReady(RestService service, String fmeAddress, int port) {
    if (RoomEP.commonService == service) {
      webSocketIp = fmeAddress;
      webSocketPort = port;
    }
    super.serviceReady(service, fmeAddress, port);
  }

  @Override
  protected void allServicesReady() {
    init(new RoomViewer());
  }
}
