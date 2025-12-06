/*
 * © HwaCom Systems Inc. 2020
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.common.am.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.hwacom.ngtms.common.am.util.MessageDigestUtil;
import com.hwacom.ngtms.common.am.util.Validator;
import com.hwacom.ngtms.common.shared.dto.SingleHintMessageDTO;
import com.sencha.gxt.widget.core.client.Composite;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.form.PasswordField;
import com.sencha.gxt.widget.core.client.form.validator.RegExValidator;
import com.sencha.gxt.widget.core.client.info.Info;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;

public class ResetPwdViewer extends Composite {

  private static ResetPwdViewerUiBinder uiBinder = GWT.create(ResetPwdViewerUiBinder.class);

  interface ResetPwdViewerUiBinder extends UiBinder<Widget, ResetPwdViewer> {}

  private static final Messages messages = GWT.create(Messages.class);

  private Runnable requestSuccessRunner;

  private BiConsumer<String, MethodCallback<SingleHintMessageDTO>> requestSender;

  private Consumer<Method> requestFailureHandler;

  @UiField Label message;

  @UiField PasswordField pwd;

  @UiField PasswordField confirmPwd;

  public ResetPwdViewer(
      BiConsumer<String, MethodCallback<SingleHintMessageDTO>> requestSender,
      Runnable requestSuccessRunner,
      Consumer<Method> requestFailureHandler) {
    this.requestSender = requestSender;
    this.requestSuccessRunner = requestSuccessRunner;
    this.requestFailureHandler = requestFailureHandler;
    initWidget(uiBinder.createAndBindUi(this));
    RegExValidator regExValidator = Validator.pwdValidator();
    pwd.addValidator(regExValidator);
    confirmPwd.addValidator(regExValidator);
  }

  public ResetPwdViewer(
      String messageHint,
      BiConsumer<String, MethodCallback<SingleHintMessageDTO>> requestSender,
      Runnable requestSuccessRunner,
      Consumer<Method> requestFailureHandler) {
    this(requestSender, requestSuccessRunner, requestFailureHandler);
    message.setText(messageHint);
  }

  @UiHandler("save")
  public void onSave(SelectEvent event) {
    if (isFieldValid()) {
      if (!isPwdIdentical()) {
        message.getElement().getStyle().setColor("red");
        message.setText(messages.message_confirmPasswordError());
      } else {
        requestSender.accept(
            MessageDigestUtil.sha256(pwd.getValue()),
            new MethodCallback<SingleHintMessageDTO>() {
              @Override
              public void onSuccess(Method method, SingleHintMessageDTO response) {
                ResetPwdViewer.this.onSuccess(method, response.getString());
              }

              @Override
              public void onFailure(Method method, Throwable exception) {
                ResetPwdViewer.this.onFailure(method, exception);
              }
            });
      }
    }
  }

  protected boolean isFieldValid() {
    return pwd.isValid() && confirmPwd.isValid();
  }

  protected boolean isPwdIdentical() {
    return Objects.equals(pwd.getValue(), confirmPwd.getValue());
  }

  protected void onSuccess(Method method, String response) {
    if (response == null) {
      requestSuccessRunner.run();
      Info.display(messages.message(), messages.user_reset_pwd_success());
    } else {
      message.getElement().getStyle().setColor("red");
      message.setText(response);
    }
  }

  protected void onFailure(Method method, Throwable exception) {
    requestFailureHandler.accept(method);
  }

  @UiHandler("clear")
  public void onClear(SelectEvent event) {
    pwd.clear();
    confirmPwd.clear();
  }
}
