/*
 * © HwaCom Systems Inc. 2013
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.common.am;

import com.google.gwt.core.client.GWT;
import com.hwacom.ngtms.common.am.restygwt.AccountRestService;
import com.hwacom.ngtms.common.am.view.Messages;
import com.hwacom.ngtms.common.am.view.ResetPwdWindow;
import com.hwacom.ngtms.hcce.am.GwtEntryPoint;
import com.hwacom.ngtms.hcce.am.factory.ClientFactory;
import com.sencha.gxt.widget.core.client.Composite;
import com.sencha.gxt.widget.core.client.info.Info;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;
import org.fusesource.restygwt.client.RestService;
import org.fusesource.restygwt.client.TextCallback;

public class AmEntryPoint extends GwtEntryPoint {

  public static final AccountRestService accountService = GWT.create(AccountRestService.class);

  private Messages messages = GWT.create(Messages.class);

  private static String userLogin;

  private static String userName;

  private ClientFactory clientFactory = GWT.create(ClientFactory.class);

  public static String getUserLogin() {
    return userLogin;
  }

  public static void setUserLogin(String userLogin) {
    AmEntryPoint.userLogin = userLogin;
  }

  public static String getUserName() {
    return userName;
  }

  public AmEntryPoint(String windowTitle, RestService... services) {
    this(windowTitle, null, services);
  }

  public AmEntryPoint(
      String windowTitle, List<SimpleEntry<String, RestService>> dedicatedServices) {
    this(windowTitle, dedicatedServices, new RestService[0]);
  }

  public AmEntryPoint(
      String windowTitle,
      List<SimpleEntry<String, RestService>> dedicatedServices,
      RestService... services) {
    super(windowTitle, dedicatedServices, services);
  }

  @Override
  protected List<RestService> getAdditionalRestServices() {
    List<RestService> list = new ArrayList<>(super.getAdditionalRestServices());
    list.add(accountService);
    return list;
  }

  @Override
  protected void initService(
      List<SimpleEntry<String, RestService>> dedicatedServices, RestService... services) {
    fmeService.retrieveEncryptedUserLogin(
        new TextCallback() {
          @Override
          public void onSuccess(Method method, String response) {
            AmEntryPoint.userLogin = response;
            AmEntryPoint.super.initService(dedicatedServices, services);
          }

          @Override
          public void onFailure(Method method, Throwable exception) {
            Info.display(
                messages.warning_retrieveEncryptedUserLogin_title(),
                messages.warning_retrieveEncryptedUserLogin_message());
            AmEntryPoint.super.initService(dedicatedServices, services);
          }
        });
  }

  @Override
  protected void initHostPage(String windowTitle) {
    super.initHostPage(windowTitle);
    injectCommon();
  }

  @Override
  protected void init(Composite mainWidget) {
    super.init(mainWidget);
  }

  @Override
  protected void serviceReady(RestService service, String fmeAddress, int port) {
    if (service == accountService) {
      validatePwdExpiration();
      accountService.getUserName(
          userLogin,
          new TextCallback() {
            @Override
            public void onSuccess(Method method, String response) {
              AmEntryPoint.userName = response;
              AmEntryPoint.super.serviceReady(service, fmeAddress, port);
            }

            @Override
            public void onFailure(Method method, Throwable exception) {
              AmEntryPoint.super.serviceReady(service, fmeAddress, port);
            }
          });
    } else {
      super.serviceReady(service, fmeAddress, port);
    }
  }

  private void validatePwdExpiration() {
    getViewport().mask(messages.user_pwd_expiration_validation());
    accountService.validatePwdExpiration(
        new MethodCallback<Boolean>() {
          @Override
          public void onSuccess(Method method, Boolean valid) {
            if (valid) {
              getViewport().unmask();
            } else {
              showResetPwdWindow();
            }
          }

          @Override
          public void onFailure(Method method, Throwable exception) {
            getViewport().unmask();
          }
        });
  }

  private void showResetPwdWindow() {
    ResetPwdWindow window =
        new ResetPwdWindow(
            (pwd, callback) -> AmEntryPoint.accountService.resetPwd(pwd, callback),
            w -> {
              w.hide();
              getViewport().unmask();
            },
            method -> Info.display(messages.message(), messages.user_reset_pwd_failure()));
    window.show(Optional.of(messages.user_reset_pwd_message()));
  }

  private native void injectCommon() /*-{
    if (!$wnd.convertDate) {
      $wnd.convertDate = function(str) {
        return @java.util.Date::new(Ljava/lang/String;)(str);
      }
    }
  }-*/;
}
