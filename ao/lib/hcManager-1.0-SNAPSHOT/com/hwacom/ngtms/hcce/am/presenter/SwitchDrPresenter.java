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
import com.hwacom.ngtms.hcce.am.view.Messages;
import com.hwacom.ngtms.hcce.am.view.SwitchDrViewer;
import com.hwacom.ngtms.hcce.shared.dto.ClusterModeDTO;
import com.hwacom.ngtms.hcce.shared.dto.DbStatusDTO;
import com.hwacom.ngtms.hcce.shared.dto.SystemEnvDTO;
import com.hwacom.ngtms.hcce.shared.dto.TopologyGroup;
import java.util.Map;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;

public class SwitchDrPresenter {

  private SwitchDrViewer viewer;

  @SuppressWarnings("unused")
  private static final Messages messages = GWT.create(Messages.class);

  private final ClientFactory clientFactory = GWT.create(ClientFactory.class);

  public enum SystemDataStatus {
    INIT,
    UPDATE
  }

  public SwitchDrPresenter(SwitchDrViewer viewer) {
    this.viewer = viewer;
    Timer primarySystemTimer =
        new Timer() {
          public void run() {
            getPrimarySystemEnv(SystemDataStatus.INIT);
          }
        };
    primarySystemTimer.schedule(1000);

    Timer backupSystemTimer =
        new Timer() {
          public void run() {
            getBackupSystemEnv(SystemDataStatus.INIT);
          }
        };
    backupSystemTimer.schedule(1000);
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

  public void getPrimarySystemEnv(final SystemDataStatus systemDataStatus) {
    // GWT.log("getPrimarySystemEnv init ...");
    GwtEntryPoint.hcceService.getSystemEnv(
        new MethodCallback<SystemEnvDTO>() {
          @Override
          public void onSuccess(Method method, SystemEnvDTO result) {
            if (result != null) {
              if (result.getHcceEnv() != null) {
                if (systemDataStatus == SystemDataStatus.INIT) {
                  toFireInitDataEvent(TopologyGroup.HC_PRIMARY_GROUP, result);
                } else {
                  toFireUpdateDataEvent(TopologyGroup.HC_PRIMARY_GROUP, result);
                }
                viewer.updatePrimarySystem(result);
                viewer.setActiveSystemHadClosed(false);

                // 運行中的 HC決定所有 DB的狀態
                if (result.getClusterMode() == ClusterModeDTO.Active) {

                  Map<String, DbStatusDTO> map = result.getDbStatusMap();

                  if (map != null && map.size() != 0) {
                    if (map.get("oldb") != null) {
                      viewer.updateOldb(map.get("oldb"));
                    } else {
                      viewer.updateOldb(null);
                    }

                    if (map.get("bkdb") != null) {
                      viewer.updatedrdb(map.get("bkdb"));
                    } else {
                      viewer.updatedrdb(null);
                    }
                  }
                }
              }
            }
          }

          @Override
          public void onFailure(Method method, Throwable caught) {
            GWT.log("SwitchDrPresenter getPrimarySystemEnv failed.", caught);
            viewer.setActiveSystemHadClosed(true);
          }
        });
  }

  public void getBackupSystemEnv(final SystemDataStatus systemDataStatus) {
    // GWT.log("getBackupSystemEnv init ...");
    GwtEntryPoint.backupSystemHcceService.getSystemEnv(
        new MethodCallback<SystemEnvDTO>() {
          @Override
          public void onSuccess(Method method, SystemEnvDTO result) {
            if (result != null) {
              if (result.getHcceEnv() != null) {
                if (systemDataStatus == SystemDataStatus.INIT) {
                  toFireInitDataEvent(TopologyGroup.HC_BACKUP_GROUP, result);
                } else {
                  toFireUpdateDataEvent(TopologyGroup.HC_BACKUP_GROUP, result);
                }
                viewer.updateDrSystem(result);
                viewer.setStandbySystemHadClosed(false);

                // 運行中的 HC決定所有 DB的狀態
                if (result.getClusterMode() == ClusterModeDTO.Active) {
                  Map<String, DbStatusDTO> map = result.getDbStatusMap();

                  if (map != null && map.size() != 0) {
                    if (map.get("oldb") != null) {
                      viewer.updatedrdb(map.get("oldb"));
                    } else {
                      viewer.updatedrdb(null);
                    }

                    if (map.get("bkdb") != null) {
                      viewer.updateOldb(map.get("bkdb"));
                    } else {
                      viewer.updateOldb(null);
                    }
                  }
                }
              }
            }
          }

          @Override
          public void onFailure(Method method, Throwable caught) {
            GWT.log("SwitchDrPresenter getBackupSystemEnv failed.", caught);
            viewer.setStandbySystemHadClosed(true);
          }
        });
  }

  public void doPriamrySystemAction(ClusterModeDTO clusterMode) {
    GWT.log("doPriamrySystemAction =>" + clusterMode);
    GwtEntryPoint.hcceService.doSystemAction(
        clusterMode,
        new MethodCallback<Boolean>() {
          @Override
          public void onSuccess(Method method, Boolean result) {
            GWT.log("doPriamrySystemAction result=" + result);
          }

          @Override
          public void onFailure(Method method, Throwable caught) {
            GWT.log("SwitchDrPresenter.service.init.doPriamrySystemAction failed.", caught);
          }
        });
  }

  public void doBackupSystemAction(ClusterModeDTO clusterMode) {
    GWT.log("doBackupSystemAction =>" + clusterMode);
    GwtEntryPoint.backupSystemHcceService.doSystemAction(
        clusterMode,
        new MethodCallback<Boolean>() {
          @Override
          public void onSuccess(Method method, Boolean result) {
            GWT.log("doBackupSystemAction result=" + result);
          }

          @Override
          public void onFailure(Method method, Throwable caught) {
            GWT.log("SwitchDrPresenter.service.init.doBackupSystemAction failed.", caught);
          }
        });
  }

  public void startPrimaryCluster() {
    GWT.log("startPrimaryCluster...");
    GwtEntryPoint.hcceService.startCluster(
        new MethodCallback<Boolean>() {
          @Override
          public void onSuccess(Method method, Boolean result) {
            GWT.log("startPrimaryCluster result=" + result);
          }

          @Override
          public void onFailure(Method method, Throwable caught) {
            GWT.log("SwitchDrPresenter.service.init.startPrimaryCluster failed.", caught);
          }
        });
  }

  public void stopPrimaryCluster() {
    GWT.log("stopPrimaryCluster...");
    GwtEntryPoint.hcceService.stopCluster(
        new MethodCallback<Boolean>() {
          @Override
          public void onSuccess(Method method, Boolean result) {
            GWT.log("stopPrimaryCluster result=" + result);
          }

          @Override
          public void onFailure(Method method, Throwable caught) {
            GWT.log("SwitchDrPresenter.service.init.stopPrimaryCluster failed.", caught);
          }
        });
  }

  public void startBackupCluster() {
    GWT.log("startBackupCluster...");
    GwtEntryPoint.backupSystemHcceService.startCluster(
        new MethodCallback<Boolean>() {
          @Override
          public void onSuccess(Method method, Boolean result) {
            GWT.log("startBackupCluster result=" + result);
          }

          @Override
          public void onFailure(Method method, Throwable caught) {
            GWT.log("SwitchDrPresenter.service.init.startBackupCluster failed.", caught);
          }
        });
  }

  public void stopBackupCluster() {
    GWT.log("stopBackupCluster...");
    GwtEntryPoint.backupSystemHcceService.stopCluster(
        new MethodCallback<Boolean>() {
          @Override
          public void onSuccess(Method method, Boolean result) {
            GWT.log("stopBackupCluster result=" + result);
          }

          @Override
          public void onFailure(Method method, Throwable caught) {
            GWT.log("SwitchDrPresenter.service.init.stopBackupCluster failed.", caught);
          }
        });
  }
}
