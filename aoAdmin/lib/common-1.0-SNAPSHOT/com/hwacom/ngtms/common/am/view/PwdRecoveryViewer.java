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
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.hwacom.ngtms.common.am.PwdRecoveryEP;
import com.hwacom.ngtms.common.am.event.PwdRecoveryEvent;
import com.hwacom.ngtms.common.am.event.PwdRecoveryEvent.Action;
import com.hwacom.ngtms.common.am.util.RestfulErrorResponseParser;
import com.hwacom.ngtms.common.shared.ErrorEnum;
import com.hwacom.ngtms.common.shared.RestfulErrorResponse;
import com.hwacom.ngtms.common.shared.ValidationRegExConstants;
import com.hwacom.ngtms.common.shared.dto.RecoverPwdDTO;
import com.hwacom.ngtms.hcce.am.factory.ClientFactory;
import com.sencha.gxt.widget.core.client.Composite;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.form.validator.RegExValidator;
import java.util.Optional;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;

public class PwdRecoveryViewer extends Composite {

  private static PwdRecoveryViewerUiBinder uiBinder = GWT.create(PwdRecoveryViewerUiBinder.class);

  interface PwdRecoveryViewerUiBinder extends UiBinder<Widget, PwdRecoveryViewer> {}

  private final ClientFactory clientFactory = GWT.create(ClientFactory.class);

  @UiField Messages messages;

  @UiField ContentPanel contentPanel;

  @UiField VerticalLayoutContainer container;

  @UiField TextField login;

  @UiField TextField mail;

  @UiField Label errorMessage;

  public PwdRecoveryViewer() {
    initWidget(uiBinder.createAndBindUi(this));
    mail.addValidator(new RegExValidator(ValidationRegExConstants.EMAIL));
  }

  @UiHandler("recoverPwd")
  public void recoverPwd(SelectEvent event) {
    if (!validate()) {
      return;
    }
    mask();
    PwdRecoveryEP.accountService.recoverPwd(
        new RecoverPwdDTO(login.getValue(), mail.getValue(), Window.Location.getHref()),
        new MethodCallback<Void>() {
          @Override
          public void onSuccess(Method method, Void response) {
            unmask();
            clientFactory
                .getEventBus()
                .fireEventFromSource(new PwdRecoveryEvent(Action.COMPLETE), mail.getValue());
          }

          @Override
          public void onFailure(Method method, Throwable exception) {
            unmask();
            String text = messages.pwd_recovery_failure();
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
                } else if (error == ErrorEnum.TOKEN_NOT_EXPIRED) {
                  text = messages.pwd_recovery_failure_token_not_expired();
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
