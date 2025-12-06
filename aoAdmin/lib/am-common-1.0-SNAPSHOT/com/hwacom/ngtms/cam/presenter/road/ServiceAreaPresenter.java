/*
 * © HwaCom Systems Inc. 2015
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.cam.presenter.road;

import com.google.gwt.core.client.GWT;
import com.hwacom.ngtms.c.shared.dto.RoadDivisionDTO;
import com.hwacom.ngtms.c.shared.dto.RoadParametersDTO;
import com.hwacom.ngtms.cam.client.RoadEP;
import com.hwacom.ngtms.cam.view.road.ServiceAreaViewer;
import com.sencha.gxt.widget.core.client.info.Info;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;

public class ServiceAreaPresenter {

  private ServiceAreaViewer viewer;

  public ServiceAreaPresenter(ServiceAreaViewer viewer) {
    this.viewer = viewer;
  }

  public void addItem(RoadDivisionDTO dto) {
    RoadParametersDTO params = new RoadParametersDTO();
    params.setRoadDivisionDTO(dto);
    RoadEP.roadService.addRoadDivision(
        params,
        new MethodCallback<Boolean>() {
          @Override
          public void onSuccess(Method method, Boolean result) {
            viewer.addItemResult(result);
          }

          @Override
          public void onFailure(Method method, Throwable caught) {
            GWT.log("ServiceAreaPresenter.addRoadDivision failed.", caught);
            Info.display(RoadEP.messages.info(), caught.getMessage());
            viewer.addItemResult(false);
          }
        });
  }

  public void removeItem(String key) {
    RoadEP.roadService.removeRoadDivision(
        key,
        new MethodCallback<Boolean>() {
          @Override
          public void onSuccess(Method method, Boolean result) {
            viewer.removeItemResult(result);
          }

          @Override
          public void onFailure(Method method, Throwable caught) {
            GWT.log("ServiceAreaPresenter.removeRoadDivision failed.", caught);
            Info.display(RoadEP.messages.info(), caught.getMessage());
            viewer.removeItemResult(false);
          }
        });
  }

  public void saveItem(RoadDivisionDTO dto) {
    RoadParametersDTO params = new RoadParametersDTO();
    params.setRoadDivisionDTO(dto);
    RoadEP.roadService.saveRoadDivision(
        params,
        new MethodCallback<Boolean>() {
          @Override
          public void onSuccess(Method method, Boolean result) {
            viewer.saveItemResult(result);
          }

          @Override
          public void onFailure(Method method, Throwable caught) {
            GWT.log("ServiceAreaPresenter.saveRoadDivision failed.", caught);
            Info.display(RoadEP.messages.info(), caught.getMessage());
            viewer.saveItemResult(false);
          }
        });
  }
}
