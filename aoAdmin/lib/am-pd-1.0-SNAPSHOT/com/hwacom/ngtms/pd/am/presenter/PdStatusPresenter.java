package com.hwacom.ngtms.pd.am.presenter;

import com.google.gwt.core.client.GWT;
import com.hwacom.ngtms.c.shared.dto.RoadLineDTO;
import com.hwacom.ngtms.pd.am.PdEP;
import com.hwacom.ngtms.pd.am.view.PdStatusViewer;
import com.hwacom.ngtms.pd.shared.dto.LoopDeviceConfigDTO;
import com.hwacom.ngtms.pd.shared.dto.PdStatusDTO;
import java.util.List;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;

public class PdStatusPresenter {

  private PdStatusViewer viewer;

  public PdStatusPresenter(PdStatusViewer viewer) {
    this.viewer = viewer;
  }

  public void retrieveRoadLine() {
    PdEP.pdCommonService.retrieveRoadLine(
        new MethodCallback<List<RoadLineDTO>>() {
          @Override
          public void onSuccess(Method method, List<RoadLineDTO> result) {
            viewer.fillRoadLine(result);
          }

          @Override
          public void onFailure(Method method, Throwable caught) {
            GWT.log("PdStatusPresenter.retrieveRoadLine failed.", caught);
          }
        });
  }

  public void retrievePdStatus() {
    PdEP.pdCommonService.retrievePdStatus(
        new MethodCallback<List<PdStatusDTO>>() {
          @Override
          public void onSuccess(Method method, List<PdStatusDTO> result) {
            viewer.fillPdStatus(result);
          }

          @Override
          public void onFailure(Method method, Throwable caught) {
            GWT.log("PdStatusPresenter.retrievePdStatus failed.", caught);
          }
        });
  }

  public void retrieveLoopDeviceConfig(List<String> deviceNames, PdStatusDTO dto) {
    PdEP.pdCommonService.retrieveLoopDeviceConfig(
        deviceNames,
        new MethodCallback<List<LoopDeviceConfigDTO>>() {
          @Override
          public void onSuccess(Method method, List<LoopDeviceConfigDTO> result) {
            viewer.fillLoopDevice(result, dto);
          }

          @Override
          public void onFailure(Method method, Throwable caught) {
            GWT.log("PdStatusPresenter.retrieveLoopDeviceConfig failed.", caught);
          }
        });
  }
}
