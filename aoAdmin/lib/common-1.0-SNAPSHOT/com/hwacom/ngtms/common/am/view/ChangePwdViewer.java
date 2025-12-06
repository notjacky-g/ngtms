/*
 * © HwaCom Systems Inc. 2021
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
import com.hwacom.ngtms.common.am.AmEntryPoint;
import com.hwacom.ngtms.common.am.util.MessageDigestUtil;
import com.hwacom.ngtms.common.am.util.RestfulErrorResponseParser;
import com.hwacom.ngtms.common.am.util.Validator;
import com.hwacom.ngtms.common.shared.ErrorEnum;
import com.hwacom.ngtms.common.shared.RestfulErrorResponse;
import com.hwacom.ngtms.common.shared.dto.ChangePwdDTO;
import com.hwacom.ngtms.common.shared.dto.SingleHintMessageDTO;
import com.sencha.gxt.widget.core.client.Composite;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.form.PasswordField;
import com.sencha.gxt.widget.core.client.form.validator.RegExValidator;
import com.sencha.gxt.widget.core.client.info.Info;
import java.util.Objects;
import java.util.Optional;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;

public class ChangePwdViewer extends Composite {

  private static ResetPwdViewerUiBinder uiBinder = GWT.create(ResetPwdViewerUiBinder.class);

  interface ResetPwdViewerUiBinder extends UiBinder<Widget, ChangePwdViewer> {}

  private static final Messages messages = GWT.create(Messages.class);

  private Runnable requestSuccessRunner;

  @UiField Label message;

  @UiField PasswordField currentPwd;

  @UiField PasswordField pwd;

  @UiField PasswordField confirmPwd;

  public ChangePwdViewer(Runnable requestSuccessRunner) {
    this.requestSuccessRunner = requestSuccessRunner;
    initWidget(uiBinder.createAndBindUi(this));
    message.getElement().getStyle().setColor("red");
    RegExValidator regExValidator = Validator.pwdValidator();
    pwd.addValidator(regExValidator);
    confirmPwd.addValidator(regExValidator);
  }

  @UiHandler("save")
  public void onSave(SelectEvent event) {
    if (isFieldValid()) {
      if (!isPwdIdentical()) {
        message.setText(messages.message_confirmPasswordError());
      } else {
        ChangePwdDTO dto =
            new ChangePwdDTO(
                MessageDigestUtil.sha256(currentPwd.getValue()),
                MessageDigestUtil.sha256(pwd.getValue()));
        AmEntryPoint.accountService.changePwd(
            dto,
            new MethodCallback<SingleHintMessageDTO>() {
              @Override
              public void onSuccess(Method method, SingleHintMessageDTO response) {
                ChangePwdViewer.this.onSuccess(method, response.getString());
              }

              @Override
              public void onFailure(Method method, Throwable exception) {
                ChangePwdViewer.this.onFailure(method, exception);
              }
            });
      }
    }
  }

  protected boolean isFieldValid() {
    return currentPwd.isValid() & pwd.isValid() && confirmPwd.isValid();
  }

  protected boolean isPwdIdentical() {
    return Objects.equals(pwd.getValue(), confirmPwd.getValue());
  }

  protected void onSuccess(Method method, String response) {
    if (response == null) {
      requestSuccessRunner.run();
      Info.display(messages.message(), messages.user_reset_pwd_success());
    } else {
      message.setText(response);
    }
  }

  protected void onFailure(Method method, Throwable exception) {
    Optional<RestfulErrorResponse> optRestfulErrorResponse =
        RestfulErrorResponseParser.parse(method);
    String message = messages.pwd_change_failure_message();
    if (optRestfulErrorResponse.isPresent()) {
      RestfulErrorResponse response = optRestfulErrorResponse.get();
      Optional<ErrorEnum> optError = ErrorEnum.toErrorEnum(response.getCode());
      if (optError.isPresent()) {
        ErrorEnum error = optError.get();
        if (error == ErrorEnum.INCORRECT_PWD) {
          message = messages.pwd_change_failure_incorrect_pwd();
        }
      }
    }
    this.message.setText(message);
  }

  @UiHandler("clear")
  public void onClear(SelectEvent event) {
    currentPwd.clear();
    pwd.clear();
    confirmPwd.clear();
  }
}
