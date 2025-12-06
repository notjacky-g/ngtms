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
import com.hwacom.ngtms.common.am.MfaCodeRecoveryEP;
import com.hwacom.ngtms.common.am.event.MfaCodeRecoveryEvent;
import com.hwacom.ngtms.common.am.event.MfaCodeRecoveryEvent.Action;
import com.hwacom.ngtms.common.am.util.RestfulErrorResponseParser;
import com.hwacom.ngtms.common.shared.ErrorEnum;
import com.hwacom.ngtms.common.shared.RestfulErrorResponse;
import com.hwacom.ngtms.common.shared.ValidationRegExConstants;
import com.hwacom.ngtms.common.shared.dto.RecoverMfaCodeDTO;
import com.hwacom.ngtms.hcce.am.factory.ClientFactory;
import com.sencha.gxt.widget.core.client.Composite;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.form.validator.RegExValidator;
import java.util.Optional;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;

public class MfaCodeRecoveryViewer extends Composite {

  private static MfaCodeRecoveryViewerUiBinder uiBinder =
      GWT.create(MfaCodeRecoveryViewerUiBinder.class);

  interface MfaCodeRecoveryViewerUiBinder extends UiBinder<Widget, MfaCodeRecoveryViewer> {}

  private final ClientFactory clientFactory = GWT.create(ClientFactory.class);

  @UiField Messages messages;

  @UiField TextField login;

  @UiField TextField mail;

  @UiField Label errorMessage;

  public MfaCodeRecoveryViewer() {
    initWidget(uiBinder.createAndBindUi(this));
    mail.addValidator(new RegExValidator(ValidationRegExConstants.EMAIL));
  }

  @UiHandler("recoverMfaCode")
  public void recoverMfaCode(SelectEvent event) {
    if (!validate()) {
      return;
    }
    mask();
    MfaCodeRecoveryEP.accountService.recoverMfaCode(
        new RecoverMfaCodeDTO(login.getValue(), mail.getValue()),
        new MethodCallback<Void>() {
          @Override
          public void onSuccess(Method method, Void response) {
            unmask();
            clientFactory
                .getEventBus()
                .fireEventFromSource(new MfaCodeRecoveryEvent(Action.COMPLETE), mail.getValue());
          }

          @Override
          public void onFailure(Method method, Throwable exception) {
            unmask();
            String text = messages.mfa_code_recovery_failure();
            Optional<RestfulErrorResponse> optRestfulErrorResponse =
                RestfulErrorResponseParser.parse(method);
            if (optRestfulErrorResponse.isPresent()) {
              RestfulErrorResponse response = optRestfulErrorResponse.get();
              Optional<ErrorEnum> optError = ErrorEnum.toErrorEnum(response.getCode());
              errorMessage.getElement().getStyle().setColor("red");
              if (optError.isPresent()) {
                ErrorEnum error = optError.get();
                if (error == ErrorEnum.VERIFICATION_FAILED
                    || error == ErrorEnum.USER_NOT_FOUND
                    || error == ErrorEnum.USER_EMAIL_UNMATCH) {
                  text = messages.pwd_recovery_failure_verification();
                }
              }
            }
            errorMessage.setText(text);
          }
        });
  }

  private boolean validate() {
    boolean valid = true;
    if (!login.validate()) {
      valid = false;
    }
    if (!mail.validate()) {
      valid = false;
    }
    return valid;
  }
}
