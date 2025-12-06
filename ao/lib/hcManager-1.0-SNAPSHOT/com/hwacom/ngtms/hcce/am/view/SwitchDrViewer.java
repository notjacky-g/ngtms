/*
 * © HwaCom Systems Inc. 2013
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.hcce.am.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;
import com.hwacom.ngtms.hcce.am.event.MaskRootEvent;
import com.hwacom.ngtms.hcce.am.event.SwitchDrEvent;
import com.hwacom.ngtms.hcce.am.event.SwitchDrEvent.Action;
import com.hwacom.ngtms.hcce.am.event.SwitchDrEvent.SwitchDrEventHandler;
import com.hwacom.ngtms.hcce.am.factory.ClientFactory;
import com.hwacom.ngtms.hcce.am.images.AllImages;
import com.hwacom.ngtms.hcce.am.presenter.SwitchDrPresenter;
import com.hwacom.ngtms.hcce.am.presenter.SwitchDrPresenter.SystemDataStatus;
import com.hwacom.ngtms.hcce.shared.dto.ClusterModeDTO;
import com.hwacom.ngtms.hcce.shared.dto.DbStatusDTO;
import com.hwacom.ngtms.hcce.shared.dto.SystemEnvDTO;
import com.sencha.gxt.widget.core.client.Composite;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.Dialog.PredefinedButton;
import com.sencha.gxt.widget.core.client.box.ConfirmMessageBox;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.event.DialogHideEvent;
import com.sencha.gxt.widget.core.client.event.DialogHideEvent.DialogHideHandler;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.form.TextArea;
import com.sencha.gxt.widget.core.client.info.Info;
import java.util.logging.Logger;

public class SwitchDrViewer extends Composite {

  private static SwitchDrViewerUiBinder uiBinder = GWT.create(SwitchDrViewerUiBinder.class);

  interface SwitchDrViewerUiBinder extends UiBinder<Widget, SwitchDrViewer> {}

  private static Logger logger = Logger.getLogger("SwitchDrViewerImpl");

  private static final Messages messages = GWT.create(Messages.class);
  private static final AllImages allImages = GWT.create(AllImages.class);
  private final ClientFactory clientFactory = GWT.create(ClientFactory.class);
  private final HandlerRegistration handlerRegistration;
  private static final int SYSTEM_DATA_REPEAT_TIME = 10000; // 每10秒監控一次
  private boolean toKeepGetSystemData = true;
  private SwitchDrPresenter presenter;
  private Timer systemTimer;
  private Timer maskTimer;
  private Timer systemDrTimer;
  private Timer maskDrTimer;
  private SystemEnvDTO oldSystemEnvVo;
  private SystemEnvDTO oldSystemDrEnvVo;
  private SystemEnvDTO nowSystemEnvVo;
  private SystemEnvDTO nowSystemDrEnvVo;
  private boolean activeSystemHadClosed = false;
  private boolean standbySystemHadClosed = false;

  enum ActionType {
    PrimarySystem,
    BackupSytem,
    None;
  };

  private ActionType nowPrimaryActionType = ActionType.None;
  private ActionType nowDrActionType = ActionType.None;
  private String expectedType = "None";
  private String expectedDrType = "None";
  @UiField ContentPanel primaryPanel;
  @UiField ContentPanel slavePanel;
  @UiField ContentPanel primaryOnePanel;
  // 主系統 UI
  @UiField Image primarySystemImage;
  // @UiField
  // CheckBox primaryAllowActiveCheck;
  @UiField TextButton primaryPromoteDemoteButton;
  @UiField Image primaryNumberImage;
  @UiField Image dbOnlineImage;
  // 備援系統 UI
  @UiField Image backupSystemImage;
  // @UiField
  // CheckBox drAllowActiveCheck;
  @UiField TextButton backupPromoteDemoteButton;
  @UiField Image backupNumberImage;
  @UiField Image dbBackupImage;
  // @UiField
  // Label cmStatus;
  // @UiField
  // Label drCmStatus;
  @UiField TextArea dbOnlineDescrption;
  @UiField TextArea dbBackupDescrption;

  public SwitchDrViewer() {
    initWidget(uiBinder.createAndBindUi(this));
    presenter = new SwitchDrPresenter(this);
    // primaryPanel.setBodyStyle("background-color:yellow");
    primaryPanel.setHeading(messages.switchDrViewer_primarySystem());
    slavePanel.setHeading(messages.switchDrViewer_drSystem());

    // primaryOnePanel.setBodyStyle("background-color:green");
    handlerRegistration =
        clientFactory
            .getEventBus()
            .addHandler(SwitchDrEvent.TYPE, new DefaulSwitchDrEventHandler());
    createFeatchSystemEnvTimer();
    createFeatchSystemDrEnvTimer();
  }

  private void createFeatchSystemEnvTimer() {
    systemTimer =
        new Timer() {
          public void run() {
            if (toKeepGetSystemData) presenter.getPrimarySystemEnv(SystemDataStatus.UPDATE);
          }
        };
    systemTimer.scheduleRepeating(SYSTEM_DATA_REPEAT_TIME);
  }

  private void createFeatchSystemDrEnvTimer() {
    systemDrTimer =
        new Timer() {
          public void run() {
            if (toKeepGetSystemData) presenter.getBackupSystemEnv(SystemDataStatus.UPDATE);
          }
        };
    systemDrTimer.scheduleRepeating(SYSTEM_DATA_REPEAT_TIME);
  }

  private boolean checkSystemHasChange(SystemEnvDTO systemEnvVo) {
    boolean result = false;
    if (nowPrimaryActionType == ActionType.None) return false;
    if (systemEnvVo == null) return false;
    GWT.log(
        "checkSystemHasChange nowPrimaryActionType="
            + nowPrimaryActionType
            + ";expectedType="
            + expectedType);
    switch (nowPrimaryActionType) {
      case PrimarySystem:
        if (systemEnvVo.getClusterMode() != null
            && systemEnvVo.getClusterMode().toString().equals(expectedType)) {
          result = true;
        }
        break;
      case BackupSytem:
      default:
        break;
    }
    return result;
  }

  private void updatePrimaryData(SystemEnvDTO systemEnvVo) {
    if (systemEnvVo == null) {
      GWT.log("systemEnvVo = null");
      primarySystemImage.setResource(allImages.primarySystemGray());
      primaryPromoteDemoteButton.setText(null);
      primaryPromoteDemoteButton.hide();
      primaryNumberImage.setResource(allImages.numberWhite());
      return;
    }
    if (systemEnvVo != null) {
      GWT.log("initData :" + systemEnvVo.toString());
      oldSystemEnvVo = systemEnvVo;
      GWT.log("initData nowPrimaryActionType=" + nowPrimaryActionType);
      GWT.log("initData expectedType=" + expectedType);
      if (systemEnvVo.getClusterMode() != null) {
        switch (systemEnvVo.getClusterMode()) {
          case Active:
            primarySystemImage.setResource(allImages.primarySystemGreen());
            // primaryPromoteDemoteButton.setText(ClusterModeVO.Standby.toString());
            primaryPromoteDemoteButton.setText("切換至待命中");
            primaryPromoteDemoteButton.show();
            primaryNumberImage.setResource(allImages.numberOne());
            // primaryNumberImage.setResource(allImages.numberWhite());
            break;
          case Standby:
            primarySystemImage.setResource(allImages.primarySystemYellow());
            // primaryPromoteDemoteButton.setText(ClusterModeVO.Active.toString());
            primaryPromoteDemoteButton.setText("切換至運作中");
            primaryPromoteDemoteButton.show();
            primaryNumberImage.setResource(allImages.numberTwo());
            break;
          default:
            primarySystemImage.setResource(allImages.primarySystemGray());
            primaryPromoteDemoteButton.setText("");
            primaryPromoteDemoteButton.hide();
            primaryNumberImage.setResource(allImages.numberWhite());
            break;
        }
      } else {
        primarySystemImage.setResource(allImages.primarySystemGray());
        primaryPromoteDemoteButton.setText(null);
        primaryPromoteDemoteButton.hide();
        primaryNumberImage.setResource(allImages.numberWhite());
      }
    }
    // 暫時先設定空值，有需要時再拿掉
    // cmStatus.setText(" ");
    if (nowPrimaryActionType != ActionType.None && checkSystemHasChange(systemEnvVo)) {
      GWT.log("do unmask...., exptected comed!");
      nowPrimaryActionType = ActionType.None;
      expectedType = "None";
      SwitchDrViewer.this.unmask();
      if (ClusterModeDTO.Active.toString().equals(expectedType)) {
        setCheckBoxAsFalse();
      }
    }
  }

  private boolean checkDrSystemHasChange(SystemEnvDTO systemEnvVo) {
    boolean result = false;
    if (nowDrActionType == ActionType.None) return false;
    if (systemEnvVo == null) return false;
    GWT.log(
        "checkDrSystemHasChange nowDrActionType="
            + nowDrActionType
            + ";expectedDrType="
            + expectedDrType
            + ";systemEnvVo.getClusterMode()="
            + systemEnvVo.getClusterMode().toString());
    switch (nowDrActionType) {
      case BackupSytem:
        if (systemEnvVo.getClusterMode() != null
            && systemEnvVo.getClusterMode().toString().equals(expectedDrType)) {
          result = true;
        }
        break;
      case PrimarySystem:
        break;
      default:
        break;
    }
    return result;
  }

  private void updateDrData(SystemEnvDTO systemEnvVo) {
    oldSystemDrEnvVo = systemEnvVo;
    if (oldSystemDrEnvVo == null) {
      GWT.log("oldSystemDrEnvVo = null");
      backupSystemImage.setResource(allImages.backupSystemGray());
      backupPromoteDemoteButton.setText(null);
      backupPromoteDemoteButton.hide();
      backupNumberImage.setResource(allImages.numberWhite());
      return;
    }
    if (oldSystemDrEnvVo != null) {
      GWT.log("initDrData :" + oldSystemDrEnvVo.toString());
      GWT.log("initDrData nowDrActionType=" + nowDrActionType);
      GWT.log("initDrData expectedDrType=" + expectedDrType);

      if (oldSystemDrEnvVo.getClusterMode() != null) {
        switch (oldSystemDrEnvVo.getClusterMode()) {
          case Active:
            backupSystemImage.setResource(allImages.backupSystemGreen());
            // drPromoteDemoteButton.setText(ClusterModeVO.Standby.toString());
            backupPromoteDemoteButton.setText("切換至待命中");
            backupPromoteDemoteButton.show();
            backupNumberImage.setResource(allImages.numberOne());
            // drNumberImage.setResource(allImages.numberWhite());
            break;
          case Standby:
            backupSystemImage.setResource(allImages.backupSystemYellow());
            // drPromoteDemoteButton.setText(ClusterModeVO.Active.toString());
            backupPromoteDemoteButton.setText("切換至運作中");
            backupPromoteDemoteButton.show();
            backupNumberImage.setResource(allImages.numberTwo());
            break;
          default:
            backupSystemImage.setResource(allImages.backupSystemGray());
            backupPromoteDemoteButton.setText("");
            backupPromoteDemoteButton.hide();
            backupNumberImage.setResource(allImages.numberWhite());
            break;
        }
      } else {
        backupSystemImage.setResource(allImages.backupSystemGray());
        backupPromoteDemoteButton.setText(null);
        backupPromoteDemoteButton.hide();
        backupNumberImage.setResource(allImages.numberWhite());
      }
    }

    // 暫時先設定空值，有需要時再拿掉
    // drCmStatus.setText(" ");
    if (nowDrActionType != ActionType.None && checkDrSystemHasChange(oldSystemDrEnvVo)) {
      GWT.log("do unmask...., exptected comed!");
      nowDrActionType = ActionType.None;
      expectedDrType = "None";
      SwitchDrViewer.this.unmask();
      if (ClusterModeDTO.Active.toString().equals(expectedDrType)) {
        setCheckBoxAsFalse();
      }
    }
  }

  @UiHandler("dbOnlineImage")
  public void dbOnlineImage(MouseOverEvent event) {
    if (dbOnlineImage.getAltText() != null && !dbOnlineImage.getAltText().isEmpty()) {
      Info.display(messages.message(), dbOnlineImage.getAltText());
    }
  }

  @UiHandler("dbBackupImage")
  public void dbBackupImage(MouseOverEvent event) {
    if (dbBackupImage.getAltText() != null && !dbBackupImage.getAltText().isEmpty()) {
      Info.display(messages.message(), dbBackupImage.getAltText());
    }
  }

  @UiHandler("primaryPromoteDemoteButton")
  public void primaryPromoteDemoteButton(SelectEvent event) {
    GWT.log("primaryPromoteDemoteButton Click!");
    if (oldSystemEnvVo == null) return;

    if (oldSystemEnvVo.getClusterMode() == ClusterModeDTO.DisConnected) {
      return;
    }
    if (oldSystemDrEnvVo != null && oldSystemDrEnvVo.getClusterMode() == ClusterModeDTO.Active) {
      new MessageTimerMask(SwitchDrViewer.this, "備援系統現在為[運作中]，不可執行!", 5000);
      return;
    }
    primaryPromoteDemote();
  }

  private void primaryPromoteDemote() {
    String text = null;
    if (oldSystemEnvVo.getClusterMode() == ClusterModeDTO.Active) {
      text = "待命中";
    }
    if (oldSystemEnvVo.getClusterMode() == ClusterModeDTO.Standby) {
      text = "運作中";
    }
    final ConfirmMessageBox box =
        new ConfirmMessageBox(
            messages.message(), messages.switchDrViewer_areYouSure() + " " + text);
    box.addDialogHideHandler(
        new DialogHideHandler() {
          @Override
          public void onDialogHide(DialogHideEvent event) {
            if (event.getHideButton() == PredefinedButton.YES) {
              if (oldSystemEnvVo.getClusterMode() != null) {
                if (oldSystemEnvVo.getClusterMode() == ClusterModeDTO.Active) {
                  clientFactory
                      .getEventBus()
                      .fireEvent(new SwitchDrEvent(Action.PRIMARY_STOP_CLUSTER));
                } else {
                  clientFactory
                      .getEventBus()
                      .fireEvent(new SwitchDrEvent(Action.PRIMARY_START_CLUSTER));
                }

                SwitchDrViewer.this.mask("主系統正在切換中...");
                nowPrimaryActionType = ActionType.PrimarySystem;
                String expectedClusterModeString = null;
                if (oldSystemEnvVo.getClusterMode() == ClusterModeDTO.Active) {
                  expectedClusterModeString = ClusterModeDTO.Standby.toString();
                }
                if (oldSystemEnvVo.getClusterMode() == ClusterModeDTO.Standby) {
                  expectedClusterModeString = ClusterModeDTO.Active.toString();
                }
                expectedType = expectedClusterModeString;
                clientFactory
                    .getEventBus()
                    .fireEventFromSource(
                        new SwitchDrEvent(Action.PRIMARY_SYSTEM),
                        ClusterModeDTO.getMode(expectedClusterModeString));
              }
            }
          }
        });
    box.show();
  }

  @UiHandler("backupPromoteDemoteButton")
  public void backupPromoteDemoteButton(SelectEvent event) {
    GWT.log("backupPromoteDemoteButton Click!");
    if (oldSystemDrEnvVo == null) {
      new MessageTimerMask(SwitchDrViewer.this, "系統參數不存在,無法執行!", 5000);
      return;
    }
    if (oldSystemEnvVo != null && oldSystemEnvVo.getClusterMode() == ClusterModeDTO.Active) {
      new MessageTimerMask(SwitchDrViewer.this, "主系統現在為[運作中]，不可執行!", 5000);
      return;
    }
    drPromoteDemote();
  }

  private void drPromoteDemote() {
    String text = null;
    if (oldSystemDrEnvVo.getClusterMode() == ClusterModeDTO.Active) {
      text = "待命中";
    }
    if (oldSystemDrEnvVo.getClusterMode() == ClusterModeDTO.Standby) {
      text = "運作中";
    }
    final ConfirmMessageBox box =
        new ConfirmMessageBox(
            messages.message(), messages.switchDrViewer_areYouSure() + " " + text);
    box.addDialogHideHandler(
        new DialogHideHandler() {
          @Override
          public void onDialogHide(DialogHideEvent event) {
            if (event.getHideButton() == PredefinedButton.YES) {
              if (oldSystemDrEnvVo.getClusterMode() != null) {
                if (oldSystemDrEnvVo.getClusterMode() == ClusterModeDTO.Active) {
                  clientFactory.getEventBus().fireEvent(new SwitchDrEvent(Action.DR_STOP_CLUSTER));
                } else {
                  clientFactory.getEventBus().fireEvent(new SwitchDrEvent(Action.DR_START_CLUSTER));
                }

                SwitchDrViewer.this.mask("備援系統正在切換中...");
                nowDrActionType = ActionType.BackupSytem;
                String expectedClusterModeString = null;
                GWT.log(
                    "dbOnlinePromoteDemoteButton oldSystemDrEnvVo.getClusterMode()="
                        + oldSystemDrEnvVo.getClusterMode());
                if (oldSystemDrEnvVo.getClusterMode() == ClusterModeDTO.Active) {
                  expectedClusterModeString = ClusterModeDTO.Standby.toString();
                }
                if (oldSystemDrEnvVo.getClusterMode() == ClusterModeDTO.Standby) {
                  expectedClusterModeString = ClusterModeDTO.Active.toString();
                }
                expectedDrType = expectedClusterModeString;
                GWT.log("dbOnlinePromoteDemoteButton expectedDrType=" + expectedDrType);
                clientFactory
                    .getEventBus()
                    .fireEventFromSource(
                        new SwitchDrEvent(Action.DR_SYSTEM),
                        ClusterModeDTO.getMode(expectedClusterModeString));
              }
            }
          }
        });
    box.show();
  }

  private void checkSystemEnv() {
    if (nowSystemEnvVo == null && nowSystemDrEnvVo == null) {
      stopTimer();
      clientFactory
          .getEventBus()
          .fireEvent(
              new MaskRootEvent(com.hwacom.ngtms.hcce.am.event.MaskRootEvent.Action.TO_MASK));
    }
  }

  private void setCheckBoxAsFalse() {
    primaryNumberImage.setResource(AllImages.INSTANCE.numberWhite());
    backupNumberImage.setResource(AllImages.INSTANCE.numberWhite());
    primaryPanel.unmask();
    slavePanel.unmask();
  }

  private void stopTimer() {
    if (systemTimer != null) systemTimer.cancel();
    if (maskTimer != null) maskTimer.cancel();
    if (systemDrTimer != null) systemDrTimer.cancel();
    if (maskDrTimer != null) maskDrTimer.cancel();
  }

  @Override
  protected void onUnload() {
    stopTimer();
    handlerRegistration.removeHandler();
    super.onUnload();
  }

  public void setPresenter(SwitchDrPresenter presenter) {
    this.presenter = presenter;
  }

  public void updatePrimarySystem(SystemEnvDTO systemEnvVo) {
    nowSystemEnvVo = systemEnvVo;
    checkSystemEnv();
    updatePrimaryData(systemEnvVo);
  }

  public void updateOldb(DbStatusDTO dbEnvVo) {
    if (dbEnvVo == null) {
      GWT.log("oldDbEnvVo = null");
      dbOnlineImage.setResource(allImages.dbOnlineGray());
    } else {
      dbOnlineDescrption.setText(dbEnvVo.getDescriptoin());
      dbOnlineImage.setResource(
          dbEnvVo.isOnline() ? allImages.dbOnlineGreen() : allImages.dbOnlineYellow());
    }
  }

  public void updatedrdb(DbStatusDTO dbEnvVo) {
    if (dbEnvVo == null) {
      dbBackupImage.setResource(allImages.dbBackupGray());
    } else {
      dbBackupDescrption.setText(dbEnvVo.getDescriptoin());
      dbBackupImage.setResource(
          dbEnvVo.isOnline() ? allImages.dbBackupGreen() : allImages.dbBackupYellow());
    }
  }

  public void updateDrSystem(SystemEnvDTO systemEnvVo) {
    nowSystemDrEnvVo = systemEnvVo;
    checkSystemEnv();
    updateDrData(systemEnvVo);
  }

  class DefaulSwitchDrEventHandler implements SwitchDrEventHandler {

    @Override
    public void primarySystem(SwitchDrEvent event) {
      presenter.doPriamrySystemAction((ClusterModeDTO) event.getSource());
    }

    @Override
    public void drSystem(SwitchDrEvent event) {
      presenter.doBackupSystemAction((ClusterModeDTO) event.getSource());
    }

    @Override
    public void primaryStartCluster(SwitchDrEvent event) {
      presenter.startPrimaryCluster();
    }

    @Override
    public void primaryStopCluster(SwitchDrEvent event) {
      presenter.stopPrimaryCluster();
    }

    @Override
    public void drStartCluster(SwitchDrEvent event) {
      presenter.startBackupCluster();
    }

    @Override
    public void drStopCluster(SwitchDrEvent event) {
      presenter.stopBackupCluster();
    }
  };

  public void setActiveSystemHadClosed(boolean isClosed) {
    activeSystemHadClosed = isClosed;
    if (activeSystemHadClosed && standbySystemHadClosed) {
      stopTimer();
      clientFactory
          .getEventBus()
          .fireEvent(
              new MaskRootEvent(com.hwacom.ngtms.hcce.am.event.MaskRootEvent.Action.TO_MASK));
    }
  }

  public void setStandbySystemHadClosed(boolean isClosed) {
    standbySystemHadClosed = isClosed;
    if (activeSystemHadClosed && standbySystemHadClosed) {
      stopTimer();
      clientFactory
          .getEventBus()
          .fireEvent(
              new MaskRootEvent(com.hwacom.ngtms.hcce.am.event.MaskRootEvent.Action.TO_MASK));
    }
  }

  public void doPriamryDbOperationSuccessResult(Boolean result) {
    GWT.log("doPriamryDbOperationSuccessResult result =" + result);
    unmaskWidget();
  }

  public void doPriamryDbOperationFailResult(Throwable caught) {
    GWT.log("doPriamryDbOperationFailResult caught =" + caught);
    unmaskWidget();
  }

  public void doDrDbOperationSuccessResult(Boolean result) {
    GWT.log("doDrDbOperationSuccessResult result =" + result);
    unmaskWidget();
  }

  public void doDrDbOperationFailResult(Throwable caught) {
    GWT.log("doDrDbOperationFailResult caught =" + caught);
    unmaskWidget();
  }

  private void unmaskWidget() {
    Timer t =
        new Timer() {
          @Override
          public void run() {
            SwitchDrViewer.this.unmask();
          }
        };
    t.schedule(90000);
  };
}
