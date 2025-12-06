/*
 * © HwaCom Systems Inc. 2015
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.cam.presenter.road;

import com.google.gwt.core.client.GWT;
import com.hwacom.ngtms.c.shared.dto.RoadLineDTO;
import com.hwacom.ngtms.c.shared.dto.RoadParametersDTO;
import com.hwacom.ngtms.cam.client.RoadEP;
import com.hwacom.ngtms.cam.view.Messages;
import com.hwacom.ngtms.cam.view.road.RoadLineConfigViewer;
import com.sencha.gxt.widget.core.client.info.Info;
import java.util.List;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;

public class RoadLineConfigPresenter {

  private RoadLineConfigViewer viewer;

  private static final Messages messages = GWT.create(Messages.class);

  public RoadLineConfigPresenter(RoadLineConfigViewer viewer) {
    this.viewer = viewer;
    getRoadLines();
  }

  public void getRoadLines() {
    RoadEP.roadService.getRoadLines(
        new MethodCallback<List<RoadLineDTO>>() {
          @Override
          public void onSuccess(Method method, List<RoadLineDTO> result) {
            viewer.initGridData(result);
          }

          @Override
          public void onFailure(Method method, Throwable caught) {
            GWT.log("RoadConfigPresenter.getRoadLines failed.", caught);
          }
        });
  }

  public void addItem(RoadLineDTO dto) {
    RoadParametersDTO params = new RoadParametersDTO();
    params.setRoadLineDTO(dto);
    RoadEP.roadService.addRoadLine(
        params,
        new MethodCallback<Boolean>() {
          @Override
          public void onSuccess(Method method, Boolean result) {
            viewer.addItemResult(result);
          }

          @Override
          public void onFailure(Method method, Throwable caught) {
            GWT.log("RoadLineConfigPresenter.addRoadLine failed.", caught);
            Info.display(messages.info(), caught.getMessage());
            viewer.addItemResult(false);
          }
        });
  }

  public void removeItem(String key) {
    RoadEP.roadService.removeRoadLine(
        key,
        new MethodCallback<Boolean>() {
          @Override
          public void onSuccess(Method method, Boolean result) {
            viewer.removeItemResult(result);
          }

          @Override
          public void onFailure(Method method, Throwable caught) {
            GWT.log("RoadLineConfigPresenter.removeRoadLine failed.", caught);
            Info.display(messages.info(), caught.getMessage());
            viewer.removeItemResult(false);
          }
        });
  }

  public void saveItem(RoadLineDTO dto) {
    RoadParametersDTO params = new RoadParametersDTO();
    params.setRoadLineDTO(dto);
    RoadEP.roadService.saveRoadLine(
        params,
        new MethodCallback<Boolean>() {
          @Override
          public void onSuccess(Method method, Boolean result) {
            viewer.saveItemResult(result);
          }

          @Override
          public void onFailure(Method method, Throwable caught) {
            GWT.log("RoadLineConfigPresenter.saveRoadLine failed.", caught);
            Info.display(messages.info(), caught.getMessage());
            viewer.saveItemResult(false);
          }
        });
  }
}
