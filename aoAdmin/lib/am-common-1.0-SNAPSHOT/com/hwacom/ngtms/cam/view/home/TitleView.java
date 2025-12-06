/*
 * © HwaCom Systems Inc. 2015
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.cam.view.home;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.hwacom.ngtms.cam.client.HomeEP;
import com.hwacom.ngtms.common.shared.dto.UserDTO;
import com.sencha.gxt.widget.core.client.Composite;
import java.util.Date;
import java.util.logging.Logger;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;

public class TitleView extends Composite {

  private static Logger logger = Logger.getLogger("Home TitleView");
  private static HeaderViewUiBinder uiBinder = GWT.create(HeaderViewUiBinder.class);
  private DateTimeFormat dateForamt = DateTimeFormat.getFormat("yyyy/MM/dd");
  private DateTimeFormat timeForamt = DateTimeFormat.getFormat("HH:mm");
  private Timer clockTimer;
  private Timer checkSessionTimer;

  interface HeaderViewUiBinder extends UiBinder<Widget, TitleView> {}

  interface TitleStyle extends CssResource {
    String titleLeft();

    String titleRight();

    String titleColor();

    String userText();

    String itemStyle();

    String redAlarm();

    String redAlarmStyle();

    String alarmText();

    String alarmText1Style();

    String alarmText2Style();

    String alarmText3Style();
  }

  interface TitleResources extends ClientBundle {
    TitleStyle title();

    ImageResource headerLeft();

    ImageResource headerCenter();

    ImageResource headerRight();

    ImageResource alarm();

    ImageResource alarmText();
  }

  private final TitleResources titleResources = GWT.create(TitleResources.class);

  @UiField Label dateLabel;
  @UiField Label timeLabel;
  @UiField Label userName;

  public TitleView() {
    initWidget(uiBinder.createAndBindUi(this));
    titleResources.title().ensureInjected();
    createClockTimer();
    showClock();
  }

  private void createClockTimer() {
    clockTimer =
        new Timer() {
          @Override
          public void run() {
            showClock();
          }
        };
    clockTimer.scheduleRepeating(60000);
  }

  private void showClock() {
    Date now = new Date();
    dateLabel.setText(dateForamt.format(now));
    timeLabel.setText(timeForamt.format(now));
  }

  @UiHandler("loginOutImage")
  public void loginOutImage(ClickEvent event) {
    GWT.log("Home TitleView loginOutImage click!");
    logger.info("GWT.getHostPageBaseURL() =>" + GWT.getHostPageBaseURL());
    //Window.Location.assign(GWT.getHostPageBaseURL() + ConstantUtil.SSO_LOGOUT_URI);
  }

  public void init() {
    HomeEP.camService.getUserDTO(
        new MethodCallback<UserDTO>() {
          @Override
          public void onSuccess(Method method, UserDTO result) {
            userName.setText(result.getName());
          }

          @Override
          public void onFailure(Method method, Throwable caught) {
            GWT.log("getUserDTO failed.", caught);
          }
        });
  }

  @Override
  protected void onUnload() {
    if (clockTimer != null) {
      clockTimer.cancel();
    }
    if (checkSessionTimer != null) {
      checkSessionTimer.cancel();
    }
    super.onUnload();
  }
}
