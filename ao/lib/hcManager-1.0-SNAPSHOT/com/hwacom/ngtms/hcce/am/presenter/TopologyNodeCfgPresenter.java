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
import com.hwacom.ngtms.hcce.am.view.Messages;
import com.hwacom.ngtms.hcce.am.view.TopologyNodeCfgViewer;
import com.hwacom.ngtms.hcce.shared.dto.TopologyGroup;
import com.hwacom.ngtms.hcce.shared.dto.TopologyNodeCfgVO;
import com.sencha.gxt.widget.core.client.info.Info;
import java.util.List;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;

public class TopologyNodeCfgPresenter {

  private TopologyNodeCfgViewer viewer;

  private HcceRestService service;

  private static final Messages messages = GWT.create(Messages.class);

  public TopologyNodeCfgPresenter(TopologyNodeCfgViewer viewer) {
    this.viewer = viewer;
    //init(viewer.getTopologyGroup());
  }

  public void init(TopologyGroup topologyGroup) {
    GWT.log("TopologyConfigPresenter init..:" + topologyGroup);
    if (topologyGroup == TopologyGroup.HC_PRIMARY_GROUP) {
      service = GwtEntryPoint.hcceService;
    } else {
      service = GwtEntryPoint.backupSystemHcceService;
    }

    service.fetchTopologyNodeCfgData(
        topologyGroup.toString(),
        new MethodCallback<List<TopologyNodeCfgVO>>() {
          @Override
          public void onSuccess(Method method, List<TopologyNodeCfgVO> result) {
            if (result != null && result.size() > 0) {
              viewer.init(result);
            }
          }

          @Override
          public void onFailure(Method method, Throwable caught) {
            GWT.log(
                "TopologyConfigPresenter.service.init.fetchTopologyNodeCfgData failed.", caught);
            Info.display(messages.message(), messages.message_dataFetchError());
          }
        });
  }

  public void addItem(TopologyNodeCfgVO vo) {
    GWT.log("addItem...");
    service.addTopologyNodeCfg(
        vo,
        new MethodCallback<TopologyNodeCfgVO>() {
          @Override
          public void onSuccess(Method method, TopologyNodeCfgVO result) {
            if (result != null) {
              viewer.addStore(result);
            }
          }

          @Override
          public void onFailure(Method method, Throwable caught) {
            GWT.log("TopologyConfigPresenter.service.addItem.addTopologyNodeCfg failed.", caught);
            Info.display(messages.message(), messages.message_addFail());
          }
        });
  }

  public void removeItem(final String groupName, final String nodeName) {
    GWT.log("removeItem...");
    service.deleteTopologyNodeCfg(
        groupName,
        nodeName,
        new MethodCallback<Boolean>() {
          @Override
          public void onSuccess(Method method, Boolean result) {
            if (result) {
              viewer.removeStore(groupName, nodeName);
            }
          }

          @Override
          public void onFailure(Method method, Throwable caught) {
            GWT.log(
                "TopologyConfigPresenter.service.removeItem.deleteTopologyNodeCfg failed.", caught);
            Info.display(messages.message(), messages.message_deleteFail());
          }
        });
  }

  public void saveItem(TopologyNodeCfgVO vo) {
    GWT.log("saveItem...");
    service.saveTopologyNodeCfg(
        vo,
        new MethodCallback<TopologyNodeCfgVO>() {
          @Override
          public void onSuccess(Method method, TopologyNodeCfgVO result) {
            if (result != null) {
              viewer.updateSotre(result);
            }
          }

          @Override
          public void onFailure(Method method, Throwable caught) {
            GWT.log("TopologyConfigPresenter.service.saveItem.saveTopologyNodeCfg failed.", caught);
            Info.display(messages.message(), messages.message_saveFail());
          }
        });
  }
}
