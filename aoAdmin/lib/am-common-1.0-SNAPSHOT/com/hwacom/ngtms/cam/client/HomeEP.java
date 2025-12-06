/*
 * © HwaCom Systems Inc. 2015
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.cam.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.hwacom.ngtms.cam.restygwt.CommonRestService;
import com.hwacom.ngtms.cam.view.Messages;
import com.hwacom.ngtms.cam.view.home.HomeViewer;
import com.hwacom.ngtms.common.am.AmEntryPoint;
import com.sencha.gxt.widget.core.client.container.Viewport;
import org.fusesource.restygwt.client.RestService;

public class HomeEP extends AmEntryPoint {
  public static final CommonRestService camService = GWT.create(CommonRestService.class);
  public static final Messages messages = GWT.create(Messages.class);
  public static String fmeAddress;

  public HomeEP() {
    super(messages.title_homeName(), camService);
  }

  private HomeViewer homeViewer;
  private Viewport viewport;

  @Override
  protected void serviceReady(RestService service, String fmeAddress, int port) {
    HomeEP.fmeAddress = fmeAddress;
    homeViewer = new HomeViewer();
    viewport = new Viewport();
    Label emptyView = new Label();
    viewport.add(emptyView);
    RootPanel.get().add(viewport);
    goHome();
  }

  public void goHome() {
    GWT.log(messages.title_homeName());
    Window.setTitle(messages.title_homeName());
    viewport.remove(0);
    viewport.add(homeViewer);
    viewport.forceLayout();
    homeViewer.init();
  }
}
