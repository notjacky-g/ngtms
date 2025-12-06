package com.hwacom.ngtms.common.am.view;

import com.google.gwt.core.client.GWT;
import com.sencha.gxt.widget.core.client.Window;

public class UserBasicInfoWindow extends Window {

  private static final Messages messages = GWT.create(Messages.class);

  public UserBasicInfoWindow() {
    setHeading(messages.user_basicInfo());
    setWidth(370);
    setModal(true);
    setWidget(new UserBasicInfoViewer());
  }
}
