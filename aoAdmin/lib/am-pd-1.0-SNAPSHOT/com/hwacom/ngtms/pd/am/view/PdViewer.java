/*
 * © HwaCom Systems Inc. 2015
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.pd.am.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Widget;
import com.hwacom.ngtms.cam.client.event.TitleViewEvent;
import com.hwacom.ngtms.cam.client.event.TitleViewEvent.TitleViewEventHandler;
import com.hwacom.ngtms.cam.client.ui.AmTabPanel;
import com.hwacom.ngtms.common.shared.dto.UserDTO;
import com.hwacom.ngtms.hcce.am.factory.ClientFactory;
import com.hwacom.ngtms.pd.am.presenter.PdPresenter;
import com.sencha.gxt.widget.core.client.Composite;

/** @author brian.cheng */
public class PdViewer extends Composite {

  private static PdViewImplUiBinder uiBinder = GWT.create(PdViewImplUiBinder.class);

  private final ClientFactory clientFactory = GWT.create(ClientFactory.class);

  interface PdViewImplUiBinder extends UiBinder<Widget, PdViewer> {}

  @UiField AmTabPanel amTabPanel;
  @UiField PdConfigSettingViewer pdConfigSetting;
  @UiField PdLoopDeviceConfigSettingViewer pdLoopDeviceConfigSetting;

  private PdPresenter presenter = new PdPresenter(this);

  public PdViewer() {
    initWidget(uiBinder.createAndBindUi(this));

    clientFactory.getEventBus().addHandler(TitleViewEvent.TYPE, new DefaultTitleViewEventHandler());
  }

  public void setPresenter(PdPresenter presenter) {
    this.presenter = presenter;
  }

  class DefaultTitleViewEventHandler implements TitleViewEventHandler {

    @Override
    public void onRefreshRole(TitleViewEvent event) {
      UserDTO user = (UserDTO) event.getSource();
      if (user != null) {
        amTabPanel.refreshTab(user);
      }
    }
  }
}
