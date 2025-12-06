/*
 * © HwaCom Systems Inc. 2013
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.hcce.am.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiConstructor;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.hwacom.ngtms.hcce.am.event.SystemEnvEvent;
import com.hwacom.ngtms.hcce.am.event.SystemEnvEvent.SystemEnvEventHandler;
import com.hwacom.ngtms.hcce.am.factory.ClientFactory;
import com.hwacom.ngtms.hcce.am.presenter.SystemPresenter;
import com.hwacom.ngtms.hcce.shared.dto.ClusterModeDTO;
import com.hwacom.ngtms.hcce.shared.dto.SystemEnvDTO;
import com.hwacom.ngtms.hcce.shared.dto.TopologyGroup;
import com.sencha.gxt.widget.core.client.Composite;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.CardLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.event.SelectEvent;

/** @author johnson.lan */
public class SystemViewer extends Composite {

  private static SystemViewerUiBinder uiBinder = GWT.create(SystemViewerUiBinder.class);
  private final ClientFactory clientFactory = GWT.create(ClientFactory.class);
  private static final Messages messages = GWT.create(Messages.class);
  private final HandlerRegistration handlerRegistration;

  interface SystemViewerUiBinder extends UiBinder<Widget, SystemViewer> {}

  private SystemPresenter presenter;
  private TopologyGroup topologyGroup = TopologyGroup.HC_PRIMARY_GROUP;

  @UiField VerticalLayoutContainer leftContainer;

  @UiField CardLayoutContainer cardLayout;
  @UiField TopologyNodeCfgViewer topologyNodeCfgViewer;
  @UiField TopologyNodeStatusViewer topologyNodeStatusViewer;
  @UiField FmViewer fmViewer;
  @UiField Label systemStatus;
  @UiField TextButton buttonTopologyConfig;
  @UiField TextButton buttonTopologyStatus;
  @UiField TextButton buttonFmConfig;

  public @UiConstructor SystemViewer(TopologyGroup topologyGroup) {
    this.topologyGroup = topologyGroup;
    initWidget(uiBinder.createAndBindUi(this));

    topologyNodeCfgViewer.setTopologyGroup(topologyGroup);
    topologyNodeStatusViewer.setTopologyGroup(topologyGroup);
    fmViewer.setTopologyGroup(topologyGroup);
    handlerRegistration =
        clientFactory
            .getEventBus()
            .addHandler(SystemEnvEvent.TYPE, new DefaulHcceEnvEventHandler());
    buttonTopologyStatus.hide();
    buttonTopologyConfig.hide();
    buttonFmConfig.hide();

    presenter = new SystemPresenter(this, topologyGroup);
  }

  @UiHandler("buttonTopologyConfig")
  public void buttonTopologyConfig(SelectEvent event) {
    cardLayout.setActiveWidget(cardLayout.getWidget(1));
  }

  @UiHandler("buttonTopologyStatus")
  public void buttonTopologyStatus(SelectEvent event) {
    cardLayout.setActiveWidget(cardLayout.getWidget(0));
  }

  @UiHandler("buttonFmConfig")
  public void buttonFmConfig(SelectEvent event) {
    cardLayout.setActiveWidget(cardLayout.getWidget(2));
    fmViewer.forceLayout();
  }

  public void setTopologyGroup(TopologyGroup topologyGroup) {
    this.topologyGroup = topologyGroup;
  }

  @Override
  protected void onUnload() {
    handlerRegistration.removeHandler();
    super.onUnload();
  }

  public void setPresenter(SystemPresenter presenter) {
    this.presenter = presenter;
  }

  public void setUserId(String userId) {
    fmViewer.setUserId(userId);
  }

  class DefaulHcceEnvEventHandler implements SystemEnvEventHandler {

    @Override
    public void onInitEnvData(SystemEnvEvent event) {
      GWT.log(
          "SystemViewerImpl onInitEnvData event.getTopologyGroup() =" + event.getTopologyGroup());
      SystemEnvDTO systemEnvVo = (SystemEnvDTO) event.getSource();
      if (systemEnvVo != null) {
        GWT.log("SystemViewerImpl onInitEnvData systemEnvVo =" + systemEnvVo.toString());
        if (topologyGroup == null) {
          GWT.log("SystemViewerImpl onInitEnvData topologyGroup == null!");
          return;
        }
        GWT.log("SystemViewerImpl onInitEnvData topologyGroup =" + topologyGroup);
        if (event.getTopologyGroup() == topologyGroup) {
          switch (topologyGroup) {
            case HC_BACKUP_GROUP:
              GWT.log(
                  "SystemViewerImpl onInitEnvData HC_BACKUP_GROUP systemEnvVo.getPrimaryClusterMode() == "
                      + systemEnvVo.getClusterMode());
              if (systemEnvVo.getClusterMode() == ClusterModeDTO.Active) {
                systemStatus.setText(messages.drSystem() + " : " + messages.active());
              } else if (systemEnvVo.getClusterMode() == ClusterModeDTO.Standby) {
                systemStatus.setText(messages.drSystem() + " : " + messages.standby());
              } else {
                systemStatus.setText(messages.primarySystem() + " : " + messages.disconnected());
              }
              if ("hc_primary_group".equals(systemEnvVo.getHcceEnv().getCurrentGroupName())) {
                buttonTopologyStatus.show();
                buttonTopologyConfig.hide();
                buttonFmConfig.hide();
              } else {
                buttonTopologyStatus.show();
                buttonTopologyConfig.show();
                buttonFmConfig.show();
              }
              break;
            case HC_PRIMARY_GROUP:
              GWT.log(
                  "SystemViewerImpl onInitEnvData HC_PRIMARY_GROUP systemEnvVo.getPrimaryClusterMode() == "
                      + systemEnvVo.getClusterMode());
              if (systemEnvVo.getClusterMode() == ClusterModeDTO.Active) {
                systemStatus.setText(messages.primarySystem() + " : " + messages.active());
              } else if (systemEnvVo.getClusterMode() == ClusterModeDTO.Standby) {
                systemStatus.setText(messages.primarySystem() + " : " + messages.standby());
              } else {
                systemStatus.setText(messages.primarySystem() + " : " + messages.disconnected());
              }
              if ("hc_primary_group".equals(systemEnvVo.getHcceEnv().getCurrentGroupName())) {
                buttonTopologyStatus.show();
                buttonTopologyConfig.show();
                buttonFmConfig.show();
              } else {
                buttonTopologyStatus.show();
                buttonTopologyConfig.hide();
                buttonFmConfig.hide();
              }
              break;
            default:
              break;
          }
        }

      } else {
        GWT.log("systemEnvVo = null");
        buttonTopologyStatus.hide();
        buttonTopologyConfig.hide();
        buttonFmConfig.hide();
        systemStatus.setText("");
      }
      leftContainer.forceLayout();
    }

    @Override
    public void onUpdateEnvData(SystemEnvEvent event) {
      SystemEnvDTO systemEnvVo = (SystemEnvDTO) event.getSource();
      GWT.log(
          "SystemViewerImpl onUpdateEnvData event.getTopologyGroup() =" + event.getTopologyGroup());
      if (systemEnvVo != null) {
        GWT.log("SystemViewerImpl onUpdateEnvData systemEnvVo =" + systemEnvVo.toString());
        if (topologyGroup == null) {
          GWT.log("SystemViewerImpl topologyGroup == null!");
          return;
        }
        GWT.log("SystemViewerImpl onUpdateEnvData topologyGroup =" + topologyGroup);
        if (event.getTopologyGroup() == topologyGroup) {
          switch (topologyGroup) {
            case HC_BACKUP_GROUP:
              // if ("hc_backup_group".equals(systemEnvVo.getHcceEnv().getCurrentGroupName())) {
              GWT.log(
                  "onUpdateEnvData HC_BACKUP_GROUP SystemViewerImpl systemEnvVo.getPrimaryClusterMode() == "
                      + systemEnvVo.getClusterMode());
              if (systemEnvVo.getClusterMode() == ClusterModeDTO.Active) {
                systemStatus.setText(messages.drSystem() + " : " + messages.active());
              } else if (systemEnvVo.getClusterMode() == ClusterModeDTO.Standby) {
                systemStatus.setText(messages.drSystem() + " : " + messages.standby());
              } else {
                systemStatus.setText(messages.primarySystem() + " : " + messages.disconnected());
              }
              // }
              break;
            case HC_PRIMARY_GROUP:
              // if ("hc_primary_group".equals(systemEnvVo.getHcceEnv().getCurrentGroupName())) {
              GWT.log(
                  "onUpdateEnvData HC_PRIMARY_GROUP SystemViewerImpl systemEnvVo.getPrimaryClusterMode() == "
                      + systemEnvVo.getClusterMode());
              if (systemEnvVo.getClusterMode() == ClusterModeDTO.Active) {
                systemStatus.setText(messages.primarySystem() + " : " + messages.active());
              } else if (systemEnvVo.getClusterMode() == ClusterModeDTO.Standby) {
                systemStatus.setText(messages.primarySystem() + " : " + messages.standby());
              } else {
                systemStatus.setText(messages.primarySystem() + " : " + messages.disconnected());
              }
              // }
              break;
            default:
              break;
          }
        }

      } else {
        GWT.log("systemEnvVo = null");
        systemStatus.setText("");
      }
    }
  }
}
