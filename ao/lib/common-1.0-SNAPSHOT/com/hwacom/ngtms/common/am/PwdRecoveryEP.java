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
import com.hwacom.ngtms.common.am.event.PwdRecoveryEvent;
import com.hwacom.ngtms.common.am.event.PwdRecoveryEvent.PwdRecoveryEventHandler;
import com.hwacom.ngtms.common.am.restygwt.AccountRestService;
import com.hwacom.ngtms.common.am.util.RestfulErrorResponseParser;
import com.hwacom.ngtms.common.am.view.Messages;
import com.hwacom.ngtms.common.am.view.PwdRecoveryViewer;
import com.hwacom.ngtms.common.am.view.ResetPwdWindow;
import com.hwacom.ngtms.common.shared.ErrorEnum;
import com.hwacom.ngtms.common.shared.RestfulErrorResponse;
import com.hwacom.ngtms.common.shared.dto.ResetPwdDTO;
import com.hwacom.ngtms.hcce.am.GwtEntryPoint;
import com.hwacom.ngtms.hcce.am.factory.ClientFactory;
import com.sencha.gxt.core.client.XTemplates;
import com.sencha.gxt.widget.core.client.Window;
import com.sencha.gxt.widget.core.client.container.CenterLayoutContainer;
import com.sencha.gxt.widget.core.client.info.Info;
import java.util.Optional;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;

public class PwdRecoveryEP extends GwtEntryPoint {

  public static final AccountRestService accountService = GWT.create(AccountRestService.class);

  public static final Messages messages = GWT.create(Messages.class);

  interface Template extends XTemplates {

    @XTemplate(source = "templates/pwdRecoverySuccess.html")
    SafeHtml pwdRecoverySuccess(String mail);
  }

  private Template template = GWT.create(Template.class);

  private final ClientFactory clientFactory = GWT.create(ClientFactory.class);

  private String pwdRecoveryText;

  private Window window;

  public PwdRecoveryEP(String windowTitle, Optional<String> optPwdRecoveryText) {
    super(windowTitle, accountService);
    this.pwdRecoveryText = optPwdRecoveryText.orElse(messages.pwd_recovery());
  }

  @Override
  public void onModuleLoad() {
    Anchor pwdRecovery = new Anchor(pwdRecoveryText);
    pwdRecovery.addClickHandler(
        new ClickHandler() {
          @Override
          public void onClick(ClickEvent event) {
            showRecoveryWindow();
          }
        });
    RootPanel.get("pwdRecovery").add(pwdRecovery);
    clientFactory
        .getEventBus()
        .addHandler(PwdRecoveryEvent.TYPE, new DefaultPwdRecoveryEventHandler());
  }

  private void showRecoveryWindow() {
    window = new Window();
    window.setHeading(pwdRecoveryText);
    window.setOnEsc(false);
    window.setWidget(new PwdRecoveryViewer());
    window.setModal(true);
    window.setWidth(300);
    window.setHeight(160);
    window.show();
  }

  @Override
  protected void allServicesReady() {
    checkTokenExists();
  }

  private void checkTokenExists() {
    String token = com.google.gwt.user.client.Window.Location.getParameter("token");
    if (token != null && !token.isEmpty()) {
      accountService.validateTokenExpiration(
          token,
          new MethodCallback<Boolean>() {
            @Override
            public void onSuccess(Method method, Boolean response) {
              if (Boolean.TRUE.equals(response)) {
                showResetPwdWindow(token);
              } else {
                showTokenInvalidMessage();
              }
            }

            @Override
            public void onFailure(Method method, Throwable exception) {
              showTokenInvalidMessage();
            }
          });
    }
  }

  private void showResetPwdWindow(String token) {
    ResetPwdWindow window =
        new ResetPwdWindow(
            (pwd, callback) -> {
              ResetPwdDTO dto = new ResetPwdDTO(token, pwd);
              accountService.resetPwdWithToken(dto, callback);
            },
            w -> w.hide(),
            this::resetPwdFailure);
    window.show(Optional.empty());
  }

  private void resetPwdFailure(Method method) {
    Optional<RestfulErrorResponse> optRestfulErrorResponse =
        RestfulErrorResponseParser.parse(method);
    String message = messages.pwd_recovery_reset_failure_message();
    if (optRestfulErrorResponse.isPresent()) {
      RestfulErrorResponse response = optRestfulErrorResponse.get();
      Optional<ErrorEnum> optError = ErrorEnum.toErrorEnum(response.getCode());
      if (optError.isPresent()) {
        ErrorEnum error = optError.get();
        if (error == ErrorEnum.INVALID_TOKEN) {
          message = messages.pwd_recovery_reset_failure_message_invalid_token();
        } else if (error == ErrorEnum.TOKEN_EXPIRED) {
          message = messages.pwd_recovery_reset_failure_message_token_expired();
        }
      }
    }
    Info.display(messages.pwd_recovery_reset_failure_title(), message);
  }

  private void showTokenInvalidMessage() {
    Info.display(
        messages.pwd_recovery_token_expired_title(), messages.pwd_recovery_token_expired_message());
  }

  @Override
  protected void initHostPage(String windowTitle) {}

  class DefaultPwdRecoveryEventHandler implements PwdRecoveryEventHandler {

    @Override
    public void onComplete(PwdRecoveryEvent event) {
      String mail = (String) event.getSource();
      CenterLayoutContainer center = new CenterLayoutContainer();
      center.setWidget(new HTML(template.pwdRecoverySuccess(mail)));
      window.setWidget(center);
      window.forceLayout();
    }
  }
}
