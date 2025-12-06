/*
 * © HwaCom Systems Inc. 2021
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.common.am.view;

import com.google.gwt.core.client.GWT;
import com.hwacom.ngtms.common.shared.dto.SingleHintMessageDTO;
import com.sencha.gxt.widget.core.client.Window;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;

public class ResetPwdWindow {

  private Messages messages = GWT.create(Messages.class);

  private Window window;

  private BiConsumer<String, MethodCallback<SingleHintMessageDTO>> requestSender;

  private Consumer<ResetPwdWindow> requestSuccessHandler;

  private Consumer<Method> requestFailureHandler;

  public ResetPwdWindow(
      BiConsumer<String, MethodCallback<SingleHintMessageDTO>> requestSender,
      Consumer<ResetPwdWindow> requestSuccessHandler,
      Consumer<Method> requestFailureHandler) {
    this.window = new Window();
    this.requestSender = requestSender;
    this.requestSuccessHandler = requestSuccessHandler;
    this.requestFailureHandler = requestFailureHandler;
  }

  public void show(Optional<String> optMessageHint) {
    window.setClosable(false);
    window.setHeading(messages.user_reset_pwd_title());
    window.setWidget(
        new ResetPwdViewer(
            optMessageHint.orElse(null),
            requestSender,
            () -> requestSuccessHandler.accept(this),
            requestFailureHandler));
    window.setModal(true);
    window.setOnEsc(false);
    window.setResizable(false);
    window.setWidth(360);
    window.setHeight(170);
    window.show();
  }

  public void hide() {
    window.hide();
  }
}
