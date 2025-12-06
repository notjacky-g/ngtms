/*
 * © HwaCom Systems Inc. 2021
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.common.am.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.hwacom.ngtms.common.am.AmEntryPoint;
import com.sencha.gxt.widget.core.client.Window;
import com.sencha.gxt.widget.core.client.container.CenterLayoutContainer;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;

public class ChangePwdWindow {

  private Messages messages = GWT.create(Messages.class);

  private Runnable requestSuccessHandler;

  private Window window;

  public ChangePwdWindow(Runnable requestSuccessHandler) {
    this.requestSuccessHandler = requestSuccessHandler;
    this.window = new Window();
  }

  public void show() {
    AmEntryPoint.accountService.isLocalAccount(
        new MethodCallback<Boolean>() {
          @Override
          public void onSuccess(Method method, Boolean response) {
            if (Boolean.TRUE.equals(response)) {
              showChangePwdViewer(
                  new ChangePwdViewer(
                      () -> {
                        hide();
                        requestSuccessHandler.run();
                      }));
            } else {
              CenterLayoutContainer container = new CenterLayoutContainer();
              Label label = new Label(messages.pwd_change_not_local_account());
              label.getElement().getStyle().setFontSize(24, Unit.PX);
              container.setWidget(label);
              showChangePwdViewer(container);
            }
          }

          @Override
          public void onFailure(Method method, Throwable exception) {}
        });
  }

  private void showChangePwdViewer(Widget widget) {
    window.setHeading(messages.pwd_change());
    window.setWidget(widget);
    window.setModal(true);
    window.setResizable(false);
    window.setWidth(360);
    window.setHeight(210);
    window.show();
  }

  public void hide() {
    window.hide();
  }
}
