/*
 * © HwaCom Systems Inc. 2013
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.hcce.am.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Widget;
import com.hwacom.ngtms.hcce.am.event.MaskRootEvent;
import com.hwacom.ngtms.hcce.am.event.MaskRootEvent.MaskRootEventHandler;
import com.hwacom.ngtms.hcce.am.factory.ClientFactory;
import com.sencha.gxt.widget.core.client.Composite;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer;

/** @author johnson.lan */
public class HcceViewer extends Composite {

  private final ClientFactory clientFactory = GWT.create(ClientFactory.class);
  private static HcceViewerUiBinder uiBinder = GWT.create(HcceViewerUiBinder.class);
  private final HandlerRegistration eventHandlerRegistration;

  interface HcceViewerUiBinder extends UiBinder<Widget, HcceViewer> {}

  @UiField BorderLayoutContainer rootContainer;

  public HcceViewer() {
    initWidget(uiBinder.createAndBindUi(this));
    eventHandlerRegistration =
        clientFactory
            .getEventBus()
            .addHandler(
                MaskRootEvent.TYPE,
                new MaskRootEventHandler() {

                  @Override
                  public void onMask(MaskRootEvent event) {
                    GWT.log("Get the MaskRootEvent TO_MASK action");
                    rootContainer.mask("主系統與備援系統皆已斷線，請嘗試重新整理瀏覽器!");
                  }
                });
  }

  @Override
  protected void onUnload() {
    eventHandlerRegistration.removeHandler();
    super.onUnload();
  }
}
