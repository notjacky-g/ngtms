/*
 * © HwaCom Systems Inc. 2015
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.cam.presenter.road;

import com.google.gwt.core.client.GWT;
import com.hwacom.ngtms.c.shared.dto.RoadLineDTO;
import com.hwacom.ngtms.cam.client.RoadEP;
import com.hwacom.ngtms.cam.view.road.RoadConfigViewer;
import java.util.List;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;

public class RoadConfigPresenter {

  private RoadConfigViewer viewer;

  public RoadConfigPresenter(RoadConfigViewer viewer) {
    this.viewer = viewer;
    getRoadLines();
  }

  public void getRoadLines() {
    RoadEP.roadService.getRoadLines(
        new MethodCallback<List<RoadLineDTO>>() {
          @Override
          public void onSuccess(Method method, List<RoadLineDTO> result) {
            viewer.fillRoadLines(result);
          }

          @Override
          public void onFailure(Method method, Throwable caught) {
            GWT.log("RoadConfigPresenter.getRoadLines failed.", caught);
          }
        });
  }
}
