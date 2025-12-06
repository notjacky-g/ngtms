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
import com.hwacom.ngtms.hcce.am.view.TopologyNodeStatusViewer;
import com.hwacom.ngtms.hcce.shared.dto.TopologyGroup;
import com.hwacom.ngtms.hcce.shared.dto.TopologyNodeStatusDTO;
import com.sencha.gxt.widget.core.client.info.Info;
import java.util.List;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;

public class TopologyNodeStatusPresenter {

  private TopologyNodeStatusViewer viewer;

  private HcceRestService service;

  private String group;

  private static final Messages messages = GWT.create(Messages.class);

  public TopologyNodeStatusPresenter(TopologyNodeStatusViewer viewer) {
    this.viewer = viewer;
  }

  public void init(TopologyGroup topologyGroup) {
    if (topologyGroup == TopologyGroup.HC_PRIMARY_GROUP) {
      service = GwtEntryPoint.hcceService;
    } else {
      service = GwtEntryPoint.backupSystemHcceService;
    }

    group = topologyGroup.toString().toLowerCase();
    refreshTopologyNode();
  }

  public void refreshTopologyNode() {
    service.fetchTopologyNodeStatusData(
        group,
        new MethodCallback<List<TopologyNodeStatusDTO>>() {
          @Override
          public void onSuccess(Method method, List<TopologyNodeStatusDTO> result) {
            if (result != null) {
              viewer.init(result);
            }
          }

          @Override
          public void onFailure(Method method, Throwable caught) {
            GWT.log("TopologyConfigPresenter fetchMainTopologyNodeStatusData failed.", caught);
          }
        });
  }

  public void startHcNode(TopologyGroup topologyGroup, String nodeName) {
    GWT.log("startHcNode..topologyGroup:" + topologyGroup);
    GWT.log("startHcNode..nodeName:" + nodeName);

    service.startNode(
        topologyGroup.toString(),
        nodeName,
        new MethodCallback<Boolean>() {
          @Override
          public void onSuccess(Method method, Boolean result) {
            if (result != null) {
              Info.display(messages.message(), messages.message_startHcNode());
            }
          }

          @Override
          public void onFailure(Method method, Throwable caught) {
            GWT.log("TopologyConfigPresenter.service.startHcNode.startHcNode failed.", caught);
            Info.display(messages.message(), caught.getMessage());
          }
        });
  }

  public void stopHcNode(TopologyGroup topologyGroup, String nodeName) {
    GWT.log("stopHcNode..topologyGroup:" + topologyGroup);
    GWT.log("stopHcNode..nodeName:" + nodeName);

    service.stopNode(
        topologyGroup.toString(),
        nodeName,
        new MethodCallback<Boolean>() {
          @Override
          public void onSuccess(Method method, Boolean result) {
            if (result != null) {
              Info.display(messages.message(), messages.message_stopHcNode());
            }
          }

          @Override
          public void onFailure(Method method, Throwable caught) {
            GWT.log("TopologyConfigPresenter.service.stopHcNode.stopHcNode failed.", caught);
            Info.display(messages.message(), caught.getMessage());
          }
        });
  }

  public void removeHcNode(TopologyGroup topologyGroup, String nodeName) {

    service.removeNode(
        topologyGroup.toString(),
        nodeName,
        new MethodCallback<Boolean>() {
          @Override
          public void onSuccess(Method method, Boolean result) {
            Info.display(
                messages.message(),
                result ? messages.message_removeSuccessfully() : messages.message_removeFail());
            refreshTopologyNode();
          }

          @Override
          public void onFailure(Method method, Throwable caught) {
            GWT.log("TopologyConfigPresenter.service.removeHcNode failed.", caught);
            Info.display(messages.message(), messages.message_removeFail());
          }
        });
  }
}
