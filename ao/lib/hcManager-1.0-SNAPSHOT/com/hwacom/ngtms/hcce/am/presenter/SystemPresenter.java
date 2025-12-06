/*
 * © HwaCom Systems Inc. 2013
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.hcce.am.presenter;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Timer;
import com.hwacom.ngtms.hcce.am.GwtEntryPoint;
import com.hwacom.ngtms.hcce.am.event.SystemEnvEvent;
import com.hwacom.ngtms.hcce.am.factory.ClientFactory;
import com.hwacom.ngtms.hcce.am.restygwt.HcceRestService;
import com.hwacom.ngtms.hcce.am.view.Messages;
import com.hwacom.ngtms.hcce.am.view.SystemViewer;
import com.hwacom.ngtms.hcce.shared.dto.SystemEnvDTO;
import com.hwacom.ngtms.hcce.shared.dto.TopologyGroup;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;

public class SystemPresenter {

  private SystemViewer viewer;

  @SuppressWarnings("unused")
  private static final Messages messages = GWT.create(Messages.class);

  private final ClientFactory clientFactory = GWT.create(ClientFactory.class);

  public enum SystemDataStatus {
    INIT,
    UPDATE
  }

  public SystemPresenter(SystemViewer viewer, TopologyGroup topologyGroup) {
    this.viewer = viewer;
    Timer initPrimarySystemEnvTimer =
        new Timer() {
          public void run() {
            getMainSystemEnv(SystemDataStatus.INIT, topologyGroup);
          }
        };
    initPrimarySystemEnvTimer.schedule(1000);
  }

  private void toFireInitDataEvent(TopologyGroup topologyGroup, SystemEnvDTO systemEnv) {
    clientFactory
        .getEventBus()
        .fireEventFromSource(
            new SystemEnvEvent(
                com.hwacom.ngtms.hcce.am.event.SystemEnvEvent.Action.INIT_DATA, topologyGroup),
            systemEnv);
  }

  private void toFireUpdateDataEvent(TopologyGroup topologyGroup, SystemEnvDTO systemEnv) {
    clientFactory
        .getEventBus()
        .fireEventFromSource(
            new SystemEnvEvent(
                com.hwacom.ngtms.hcce.am.event.SystemEnvEvent.Action.UPDATE_DATA, topologyGroup),
            systemEnv);
  }

  public void getMainSystemEnv(
      final SystemDataStatus systemDataStatus, TopologyGroup topologyGroup) {
    HcceRestService service;
    if (topologyGroup == TopologyGroup.HC_PRIMARY_GROUP) {
      service = GwtEntryPoint.hcceService;
    } else {
      service = GwtEntryPoint.backupSystemHcceService;
    }

    service.getSystemEnv(
        new MethodCallback<SystemEnvDTO>() {
          @Override
          public void onSuccess(Method method, SystemEnvDTO result) {
            if (result != null) {
              if (result.getHcceEnv() != null) {
                if (systemDataStatus == SystemDataStatus.INIT) {
                  toFireInitDataEvent(topologyGroup, result);
                } else {
                  toFireUpdateDataEvent(topologyGroup, result);
                }
              }
            }
          }

          @Override
          public void onFailure(Method method, Throwable caught) {
            GWT.log("SystemPresenter getMainSystemEnv failed.", caught);
          }
        });
  }
}
