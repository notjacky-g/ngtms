/*
 * © HwaCom Systems Inc. 2015
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.cam.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsonUtils;
import com.google.gwt.dom.client.SpanElement;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.google.web.bindery.autobean.shared.AutoBean;
import com.google.web.bindery.autobean.shared.AutoBeanCodex;
import com.google.web.bindery.autobean.shared.AutoBeanFactory;
import com.hwacom.ngtms.cam.client.HomeEP;
import com.hwacom.ngtms.cam.client.event.TitleViewEvent;
import com.hwacom.ngtms.cam.client.event.TitleViewEvent.Action;
import com.hwacom.ngtms.cam.images.AmImages;
import com.hwacom.ngtms.cam.vo.BrowserAlarmIF;
import com.hwacom.ngtms.cam.vo.BrowserAlarmVO;
import com.hwacom.ngtms.cam.vo.WebSocketCloseReason;
import com.hwacom.ngtms.cam.websocket.BrowserAlarmWebSocket;
import com.hwacom.ngtms.cam.websocket.BrowserAlarmWebSocket.WebSocketHandler;
import com.hwacom.ngtms.common.shared.dto.UserDTO;
import com.hwacom.ngtms.hcce.am.factory.ClientFactory;
import com.sencha.gxt.widget.core.client.Composite;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;

public class TitleViewer extends Composite {

  private static Logger logger = Logger.getLogger("TitleView");
  private static TitleViewUiBinder uiBinder = GWT.create(TitleViewUiBinder.class);
  private ClientFactory clientFactory = GWT.create(ClientFactory.class);
  private DateTimeFormat dateForamt = DateTimeFormat.getFormat("yyyy/MM/dd");
  private DateTimeFormat timeForamt = DateTimeFormat.getFormat("HH:mm");
  private List<String> alarmTextList = new ArrayList<>();
  private static final int ALARM_TEXT_LIST_MAX_SIZE = 20;
  private static final int SHOW_TEXT_TIME = 60000;
  private boolean isSsoLogin = false;
  private Timer clockTimer;
  private Timer showTextTimer;
  private BrowserAlarmWebSocket webSocket;
  BrowserAlarmAutoBeanFactory browserAlarmAutoBeanFactory =
      GWT.create(BrowserAlarmAutoBeanFactory.class);

  interface BrowserAlarmAutoBeanFactory extends AutoBeanFactory {
    AutoBean<BrowserAlarmIF> createBean();
  }

  interface TitleViewUiBinder extends UiBinder<Widget, TitleViewer> {}

  interface TitleStyle extends CssResource {
    String titleLeft();

    String titleRight();

    String titleColor();

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

  @UiField Image logoImage;
  @UiField Image itemImage;
  @UiField Label itemLabel;
  @UiField Image homeImage;
  @UiField Label dateLabel;
  @UiField Label timeLabel;
  // @UiField
  // Label redAlarmNumber;
  // @UiField
  // SimpleContainer alarmPanel;
  @UiField SpanElement alarmText1;
  @UiField SpanElement alarmText2;
  @UiField Label userName;
  // @UiField
  // Image alarmImage;

  public TitleViewer() {
    initWidget(uiBinder.createAndBindUi(this));
    titleResources.title().ensureInjected();
    createClockTimer();
    createShowTextTimer();
    showClock();
    // alarmPanel.addDomHandler(new ClickHandler() {
    //
    // @Override
    // public void onClick(ClickEvent event) {
    // if (!"警報管理系統".equals(itemLabel.getText())) {
    // Window.open("alarm.html", "_blank", "");
    // } else {
    // GWT.log("This is Alarm System, so do nothing!");
    // }
    // }
    // }, ClickEvent.getType());
    showAlarmText();
    init();
    webSocket = new BrowserAlarmWebSocket(new DefaultWebSocketHandler());
    webSocket.open();

    Timer timer2 =
        new Timer() {

          @Override
          public void run() {
            GWT.log("setLogoImage ... ");
            setLogoImage();
          }
        };
    timer2.schedule(5500);
  }

  private void setLogoImage() {
    GWT.log("setLogoImage ... run");
    logoImage.setResource(AmImages.INSTANCE.logo_main());
  }
  /**
   * 以 {@link ImageResource} 的方式設定 Item 圖
   *
   * @param imageResource
   */
  public void setItemImage(ImageResource imageResource) {
    itemImage.setResource(imageResource);
  }

  /**
   * 以 {@link String} 的方式設定 Item Label
   *
   * @param itemName
   */
  public void setItemName(String itemName) {
    itemLabel.setText(itemName);
  }

  private void showAlarmText() {
    GWT.log("alarmTextList size =>" + alarmTextList.size());
    GWT.log("alarmTextList  =>" + alarmTextList.toString());
    GWT.log("alarmTextList size =>" + alarmTextList.size());

    if (alarmTextList != null) {
      if (alarmTextList.size() > 2) {
        alarmText1.setInnerText(alarmTextList.get(0));
        alarmText2.setInnerText(alarmTextList.get(1));
      } else if (alarmTextList.size() == 2) {
        alarmText1.setInnerText(alarmTextList.get(0));
        alarmText2.setInnerText(alarmTextList.get(1));
      } else if (alarmTextList.size() == 1) {
        alarmText1.setInnerText(" ");
        alarmText2.setInnerText(alarmTextList.get(0));
      } else if (alarmTextList.size() == 0) {
        alarmText1.setInnerText(" ");
        alarmText2.setInnerText(" ");
      }
    }
    if (alarmTextList.size() > 0) {
      alarmTextList.remove(0);
    }
  }

  public void processAddAlarmText(String content) {
    GWT.log("processAddAlarmText content:" + content);
    if (alarmTextList.contains(content)) {
      GWT.log("content 內容相同,不新增!");
      return;
    }
    //GWT.log("開始處理新增 ...");
    if (alarmTextList.size() > (ALARM_TEXT_LIST_MAX_SIZE - 1)) {
      alarmTextList.remove(alarmTextList.size() - 1);
    }
    //GWT.log("開始處理新增 ...02");
    alarmTextList.add(content);
    if (alarmTextList.size() == 0) {
      //GWT.log("開始處理新增 ...02...1");
      alarmText1.setInnerText(alarmTextList.get(0));
    }
    //GWT.log("開始處理新增 ...03");
    if (alarmTextList.size() == 1) {
      //GWT.log("開始處理新增 ...03...1");
      alarmText2.setInnerText(alarmTextList.get(0));
    }
    //GWT.log("開始處理新增 ...04");
    if (alarmTextList.size() == 2) {
      //GWT.log("開始處理新增 ...04...1");
      alarmText1.setInnerText(alarmTextList.get(0));
      alarmText2.setInnerText(alarmTextList.get(1));
    }
    //GWT.log("開始處理新增 ...05");
    GWT.log("processAddAlarmText alarmTextList.size : " + alarmTextList.size());
  }

  private void createClockTimer() {
    if (clockTimer != null && clockTimer.isRunning()) {
      clockTimer.cancel();
      clockTimer = null;
    }
    clockTimer =
        new Timer() {
          @Override
          public void run() {
            showClock();
          }
        };
    clockTimer.scheduleRepeating(60000);
  }

  private void stopClockTimer() {
    if (clockTimer != null && showTextTimer.isRunning()) {
      clockTimer.cancel();
      clockTimer = null;
    }
  }

  private void createShowTextTimer() {
    GWT.log("createShowTextTimer ...");
    if (showTextTimer != null && showTextTimer.isRunning()) {
      GWT.log("do ShowTextTimer cancel ...2");
      showTextTimer.cancel();
      showTextTimer = null;
    }
    showTextTimer =
        new Timer() {
          @Override
          public void run() {
            showAlarmText();
          }
        };
    showTextTimer.scheduleRepeating(SHOW_TEXT_TIME);
  }

  private void stopShowTextTimer() {
    //GWT.log("stopShowTextTimer ...");
    if (showTextTimer != null) {
      //GWT.log("do showTextTimer cancel ...1");
      //GWT.log("showTextTimer showTextTimer.isRunning :" + showTextTimer.isRunning());
      showTextTimer.cancel();
      //GWT.log("showTextTimer showTextTimer.isRunning :" + showTextTimer.isRunning());
      showTextTimer = null;
    }
  }

  private void showClock() {
    Date now = new Date();
    dateLabel.setText(dateForamt.format(now));
    timeLabel.setText(timeForamt.format(now));
  }

  @UiHandler("loginOutImage")
  public void loginOutImage(ClickEvent event) {
    GWT.log("loginOutImage click!");
    GWT.log("isSsoLogin =>" + isSsoLogin);
    GWT.log("GWT.getHostPageBaseURL() =>" + GWT.getHostPageBaseURL());
    //Window.Location.assign(GWT.getHostPageBaseURL() + ConstantUtil.SSO_LOGOUT_URI);
  }

  @UiHandler("homeImage")
  public void homeImage(ClickEvent event) {
    GWT.log("homeImage click!");
    //Window.Location.assign(ConstantUtil.LOGIN_PAGE);
  }

  private void init() {
    logger.info("TitleView init ... ");
    HomeEP.camService.getUserDTO(
        new MethodCallback<UserDTO>() {

          @Override
          public void onSuccess(Method method, UserDTO result) {
            logger.info("TitleView getUserDTO onSuccess result = ");
            if (result != null && result.getName() != null) {
              logger.info("UserDTO name =>" + result.getName());
              userName.setText(result.getName());
              // title view 取得 user 資訊後發出 event
              clientFactory
                  .getEventBus()
                  .fireEventFromSource(new TitleViewEvent(Action.REFRESH_ROLE), result);
            } else {
              logger.info("TitleView getUserDTO userName is null! ");
              userName.setText("");
            }
          }

          @Override
          public void onFailure(Method method, Throwable caught) {
            logger.info("TitleView loginService.getUserDTO failed." + caught);
          }
        });
  }

  @Override
  protected void onUnload() {
    stopClockTimer();
    stopShowTextTimer();
    super.onUnload();
  }

  class DefaultWebSocketHandler implements WebSocketHandler {

    @Override
    public void onOpen() {
      GWT.log("OperationLogView DefaultWebSocketHandler onOpen...");
    }

    @Override
    public void onMessage(String message) {
      GWT.log("TitleView DefaultWebSocketHandler onMessage...");
      GWT.log("TitleView get message =>" + message);
      String jsonStr = JsonUtils.escapeJsonForEval(message);
      // GWT.log("jsonStr =>" + jsonStr);
      BrowserAlarmIF browserAlarmIf = deserializeFromJson(jsonStr);
      BrowserAlarmVO browserAlarm = new BrowserAlarmVO();
      browserAlarm.setId(browserAlarmIf.getId());
      browserAlarm.setContent(browserAlarmIf.getContent());
      GWT.log("TitleView browserAlarm =>" + browserAlarm);
      processAddAlarmText(browserAlarm.getContent());
    }

    @Override
    public void onClose(int code, String reason) {
      GWT.log("TitleView DefaultWebSocketHandlerHandler onClose...");
      GWT.log("code=" + code + ";reason=" + reason);
      if (WebSocketCloseReason.FINISHED.getCode() == code
          || WebSocketCloseReason.FAILURE.getCode() == code) {
        // unmask();
      }
    }
  }

  public BrowserAlarmIF deserializeFromJson(String json) {
    AutoBean<BrowserAlarmIF> bean =
        AutoBeanCodex.decode(browserAlarmAutoBeanFactory, BrowserAlarmIF.class, json);
    return bean.as();
  }
}
