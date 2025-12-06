/*
 * © HwaCom Systems Inc. 2021
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.common.am;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;
import com.hwacom.ngtms.common.am.event.MfaCodeRecoveryEvent;
import com.hwacom.ngtms.common.am.event.MfaCodeRecoveryEvent.MfaCodeRecoveryEventHandler;
import com.hwacom.ngtms.common.am.restygwt.AccountRestService;
import com.hwacom.ngtms.common.am.view.Messages;
import com.hwacom.ngtms.common.am.view.MfaCodeRecoveryViewer;
import com.hwacom.ngtms.hcce.am.GwtEntryPoint;
import com.hwacom.ngtms.hcce.am.factory.ClientFactory;
import com.sencha.gxt.core.client.XTemplates;
import com.sencha.gxt.widget.core.client.Window;
import com.sencha.gxt.widget.core.client.container.CenterLayoutContainer;

public class MfaCodeRecoveryEP extends GwtEntryPoint {

  public static final AccountRestService accountService = GWT.create(AccountRestService.class);

  public static final Messages messages = GWT.create(Messages.class);

  interface Template extends XTemplates {

    @XTemplate(source = "templates/mfaCodeRecoverySuccess.html")
    SafeHtml mfaCodeRecoverySuccess(String mail);
  }

  private Template template = GWT.create(Template.class);

  private final ClientFactory clientFactory = GWT.create(ClientFactory.class);

  private Window window;

  public MfaCodeRecoveryEP() {
    super(null, accountService);
  }

  @Override
  public void onModuleLoad() {
    Anchor mfaCodeRecovery = new Anchor(messages.mfa_code_recovery());
    mfaCodeRecovery.addClickHandler(
        new ClickHandler() {
          @Override
          public void onClick(ClickEvent event) {
            showRecoveryWindow();
          }
        });
    RootPanel.get("mfaCodeRecovery").add(mfaCodeRecovery);
    clientFactory
        .getEventBus()
        .addHandler(MfaCodeRecoveryEvent.TYPE, new DefaultMfaCodeRecoveryEventHandler());
  }

  private void showRecoveryWindow() {
    window = new Window();
    window.setHeading(messages.mfa_code_recovery());
    window.setOnEsc(false);
    window.setWidget(new MfaCodeRecoveryViewer());
    window.setModal(true);
    window.setWidth(300);
    window.setHeight(160);
    window.show();
  }

  @Override
  protected void initHostPage(String windowTitle) {}

  class DefaultMfaCodeRecoveryEventHandler implements MfaCodeRecoveryEventHandler {

    @Override
    public void onComplete(MfaCodeRecoveryEvent event) {
      String mail = (String) event.getSource();
      CenterLayoutContainer center = new CenterLayoutContainer();
      center.setWidget(new HTML(template.mfaCodeRecoverySuccess(mail)));
      window.setWidget(center);
      window.forceLayout();
    }
  }
}
