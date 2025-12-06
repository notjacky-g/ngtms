/*
 * © HwaCom Systems Inc. 2013
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.node.performance.am.presenter;

import com.google.gwt.core.client.Callback;
import com.google.gwt.core.client.GWT;
import com.hwacom.ngtms.hcce.am.GwtEntryPoint;
import com.hwacom.ngtms.hcce.shared.dto.NodeDTO;
import com.hwacom.ngtms.hcce.shared.dto.TopologyGroup;
import com.hwacom.ngtms.node.performance.am.view.HistoryDataViewer;
import com.hwacom.ngtms.node.performance.shared.dto.GetNodePerformanceLogsRequestDTO;
import com.hwacom.ngtms.node.performance.shared.dto.NodePerformanceLogDTO;
import com.sencha.gxt.data.shared.loader.DataProxy;
import com.sencha.gxt.data.shared.loader.PagingLoadConfig;
import com.sencha.gxt.data.shared.loader.PagingLoadResult;
import java.util.List;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;

public class HistoryDataPresenter {

  private DataProxy<PagingLoadConfig, PagingLoadResult<NodePerformanceLogDTO>> proxy;

  private HistoryDataViewer viewer;

  public HistoryDataPresenter(final HistoryDataViewer viewer) {
    this.viewer = viewer;
    proxy =
        new DataProxy<PagingLoadConfig, PagingLoadResult<NodePerformanceLogDTO>>() {

          @Override
          public void load(
              PagingLoadConfig loadConfig,
              Callback<PagingLoadResult<NodePerformanceLogDTO>, Throwable> callback) {
            GetNodePerformanceLogsRequestDTO dto = new GetNodePerformanceLogsRequestDTO();
            dto.setStartTime(viewer.getStartDate());
            dto.setEndTime(viewer.getEndDate());
            dto.setGroupName(viewer.getGroupName());
            dto.setNodeName(viewer.getNodeName());
            dto.setOffset(loadConfig.getOffset());
            dto.setLimit(loadConfig.getLimit());

            // TODO create by nodePerformance, use NodePerformanceRestService
            //            GwtEntryPoint.hcceService.getNodePerformanceLogs(
            //                dto,
            //                new MethodCallback<GetNodePerformanceLogsResponseDTO>() {
            //                  @Override
            //                  public void onSuccess(Method method, GetNodePerformanceLogsResponseDTO response) {
            //                    callback.onSuccess(
            //                        new PagingLoadResultBean<NodePerformanceLogDTO>(
            //                            response.getNodePerformanceLogs(),
            //                            response.getTotalLength(),
            //                            loadConfig.getOffset()));
            //                  }
            //
            //                  @Override
            //                  public void onFailure(Method method, Throwable exception) {
            //                    callback.onFailure(exception);
            //                  }
            //                });
          }
        };
  }

  public void fetchNodeData(TopologyGroup group) {
    GwtEntryPoint.hcceService.fetchNodeData(
        group.toString(),
        new MethodCallback<List<NodeDTO>>() {
          @Override
          public void onSuccess(Method method, List<NodeDTO> result) {
            viewer.updateNodeNames(result);
          }

          @Override
          public void onFailure(Method method, Throwable caught) {
            GWT.log("HistoryDataPresenter fetchNodeData failed.", caught);
          }
        });
  }

  /** @return the proxy */
  public DataProxy<PagingLoadConfig, PagingLoadResult<NodePerformanceLogDTO>> getProxy() {
    return proxy;
  }
}
