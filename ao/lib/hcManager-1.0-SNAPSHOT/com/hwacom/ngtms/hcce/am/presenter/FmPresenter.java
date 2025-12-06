/*
 * © HwaCom Systems Inc. 2013
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.hcce.am.presenter;

import com.google.gwt.core.client.GWT;
import com.hwacom.ngtms.hcce.am.GwtEntryPoint;
import com.hwacom.ngtms.hcce.am.restygwt.HcceRestService;
import com.hwacom.ngtms.hcce.am.view.FmViewer;
import com.hwacom.ngtms.hcce.am.view.Messages;
import com.hwacom.ngtms.hcce.shared.dto.DynamicConfigDTO;
import com.hwacom.ngtms.hcce.shared.dto.FmeDefinitionDTO;
import com.hwacom.ngtms.hcce.shared.dto.NodeDTO;
import com.hwacom.ngtms.hcce.shared.dto.TopologyGroup;
import com.sencha.gxt.widget.core.client.info.Info;
import java.util.List;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;

public class FmPresenter {

  private FmViewer viewer;

  private HcceRestService service;

  private static final Messages messages = GWT.create(Messages.class);

  private enum ActionType {
    INIT,
    UPDATE
  }

  public FmPresenter(FmViewer viewer) {
    this.viewer = viewer;
  }

  public void init(TopologyGroup topologyGroup) {
    if (topologyGroup == TopologyGroup.HC_PRIMARY_GROUP) {
      service = GwtEntryPoint.hcceService;
    } else {
      service = GwtEntryPoint.backupSystemHcceService;
    }
    fetchFmeDefinitionData(topologyGroup, ActionType.INIT);
  }

  public void updateFme(TopologyGroup topologyGroup) {
    fetchFmeDefinitionData(topologyGroup, ActionType.UPDATE);
  }

  private void fetchFmeDefinitionData(
      final TopologyGroup topologyGroup, final ActionType actionType) {
    service.fetchFmeDefinitionData(
        topologyGroup.toString(),
        new MethodCallback<List<FmeDefinitionDTO>>() {
          @Override
          public void onSuccess(Method method, List<FmeDefinitionDTO> result) {
            if (result != null && result.size() > 0) {
              if (actionType == ActionType.INIT) {
                viewer.init(result);
              }
              if (actionType == ActionType.UPDATE) {
                viewer.updateFme(result);
              }
            }
          }

          @Override
          public void onFailure(Method method, Throwable caught) {
            GWT.log("TopologyConfigPresenter fetchMainFmeDefinitionData failed.", caught);
          }
        });
  }

  public void initNodeList(TopologyGroup topologyGroup) {
    service.fetchNodeData(
        topologyGroup.toString(),
        new MethodCallback<List<NodeDTO>>() {
          @Override
          public void onSuccess(Method method, List<NodeDTO> result) {
            if (result != null && result.size() > 0) {
              viewer.initNodeList(result);
            }
          }

          @Override
          public void onFailure(Method method, Throwable caught) {
            GWT.log("TopologyConfigPresenter.service.init.fetchNodeData failed.", caught);
            // Info.display(messages.message(), topologyGroup
            // +" "+messages.message_dataFetchError());
          }
        });
  }

  public void updateDynamicConfig(DynamicConfigDTO dynamicConfig) {
    GWT.log("updateDynamicConfig...");
    service.updateDynamicConfig(
        dynamicConfig,
        new MethodCallback<DynamicConfigDTO>() {
          @Override
          public void onSuccess(Method method, DynamicConfigDTO result) {
            if (result != null) {
              viewer.updateDynamicConfigResult(result);
              Info.display(messages.message(), messages.message_saveSuccessfully());
            } else {
              Info.display(messages.message(), messages.message_saveFail());
            }
          }

          @Override
          public void onFailure(Method method, Throwable caught) {
            GWT.log("FmPresenter.updateDynamicConfig failed.", caught);
            Info.display(messages.message(), messages.message_saveFail());
          }
        });
  }

  public void removeItem(FmeDefinitionDTO dto) {
    GWT.log("removeItem...");
    service.deleteFme(
        dto.getGroupName(),
        dto.getFmeName(),
        new MethodCallback<Void>() {
          @Override
          public void onSuccess(Method method, Void result) {
            viewer.removeStore(dto);
            Info.display(messages.message(), messages.message_deleteSuccessfully());
          }

          @Override
          public void onFailure(Method method, Throwable caught) {
            GWT.log("FmPresenter.deleteFme failed.", caught);
            Info.display(messages.message(), messages.message_deleteFail());
          }
        });
  }

  public void getFmeDyNamicConfig(
      String groupName, String fmeName, String className, TopologyGroup topologyGroup) {
    service.getFmeDyNamicConfig(
        groupName,
        fmeName,
        className,
        topologyGroup.toString(),
        new MethodCallback<List<DynamicConfigDTO>>() {
          @Override
          public void onSuccess(Method method, List<DynamicConfigDTO> result) {
            viewer.updateDynamicConfigStore(result);
          }

          @Override
          public void onFailure(Method method, Throwable caught) {
            GWT.log("FmPresenter.deleteFme failed.", caught);
            Info.display(messages.message(), messages.message_deleteFail());
          }
        });
  }

  public void saveItem(FmeDefinitionDTO dto) {
    GWT.log("saveItem...");
    service.saveFme(
        dto,
        new MethodCallback<Void>() {
          @Override
          public void onSuccess(Method method, Void result) {
            viewer.updateSotre(dto);
            Info.display(messages.message(), messages.message_saveSuccessfully());
          }

          @Override
          public void onFailure(Method method, Throwable caught) {
            GWT.log("FmPresenter.saveFme failed.", caught);
            Info.display(messages.message(), messages.message_saveFail());
          }
        });
  }

  public void resetFme(FmeDefinitionDTO vo) {
    GWT.log("resetFme...");
    service.resetFme(
        vo,
        new MethodCallback<Boolean>() {
          @Override
          public void onSuccess(Method method, Boolean result) {
            if (result) {
              Info.display(messages.message(), messages.fm_resetting() + "...");
            }
          }

          @Override
          public void onFailure(Method method, Throwable caught) {
            GWT.log("FmPresenter.resetFme failed.", caught);
            Info.display(messages.message(), caught.getMessage());
          }
        });
  }
}
