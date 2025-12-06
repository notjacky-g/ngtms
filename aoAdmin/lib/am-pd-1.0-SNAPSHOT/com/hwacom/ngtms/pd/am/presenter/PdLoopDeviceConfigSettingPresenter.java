package com.hwacom.ngtms.pd.am.presenter;

import com.google.gwt.core.client.GWT;
import com.hwacom.ngtms.pd.am.PdEP;
import com.hwacom.ngtms.pd.am.view.PdLoopDeviceConfigSettingViewer;
import com.hwacom.ngtms.pd.shared.dto.LoopDeviceConfigDTO;
import com.hwacom.ngtms.pd.shared.dto.PdParametersDTO;
import java.util.List;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;

public class PdLoopDeviceConfigSettingPresenter {

  private PdLoopDeviceConfigSettingViewer viewer;

  public PdLoopDeviceConfigSettingPresenter(PdLoopDeviceConfigSettingViewer viewer) {
    this.viewer = viewer;
  }

  public void retrieveLoopDeviceConfig(List<String> deviceNames) {
    PdEP.pdCommonService.retrieveLoopDeviceConfig(
        deviceNames,
        new MethodCallback<List<LoopDeviceConfigDTO>>() {
          @Override
          public void onSuccess(Method method, List<LoopDeviceConfigDTO> result) {
            viewer.fillLoopDeviceGrid(result);
          }

          @Override
          public void onFailure(Method method, Throwable caught) {
            GWT.log("pdLoopDeviceConfigSettingPresenter.retrieveLoopDeviceConfig failed.", caught);
          }
        });
  }

  public void refreshLoopDeviceConfig(List<String> deviceNames) {
    PdEP.pdCommonService.retrieveLoopDeviceConfig(
        deviceNames,
        new MethodCallback<List<LoopDeviceConfigDTO>>() {
          @Override
          public void onSuccess(Method method, List<LoopDeviceConfigDTO> result) {
            viewer.refreshLoopDeviceGrid(result);
          }

          @Override
          public void onFailure(Method method, Throwable caught) {
            GWT.log("pdLoopDeviceConfigSettingPresenter.refreshLoopDeviceConfig failed.", caught);
          }
        });
  }

  public void retrieveUnsetLoopDeviceConfig() {
    PdEP.pdCommonService.retrieveUnsetLoopDeviceConfig(
        new MethodCallback<List<LoopDeviceConfigDTO>>() {
          @Override
          public void onSuccess(Method method, List<LoopDeviceConfigDTO> result) {
            viewer.fillUnsetLoopDeviceGrid(result);
          }

          @Override
          public void onFailure(Method method, Throwable caught) {
            GWT.log(
                "pdLoopDeviceConfigSettingPresenter.retrieveUnsetLoopDeviceConfig failed.", caught);
          }
        });
  }

  public void refreshUnsetLoopDeviceConfig() {
    PdEP.pdCommonService.retrieveUnsetLoopDeviceConfig(
        new MethodCallback<List<LoopDeviceConfigDTO>>() {
          @Override
          public void onSuccess(Method method, List<LoopDeviceConfigDTO> result) {
            viewer.refreshUnsetLoopDeviceGrid(result);
          }

          @Override
          public void onFailure(Method method, Throwable caught) {
            GWT.log(
                "pdLoopDeviceConfigSettingPresenter.refreshUnsetLoopDeviceConfig failed.", caught);
          }
        });
  }

  public void updateLoopDeviceConfig(String pdDeviceName, List<LoopDeviceConfigDTO> dtos) {
    PdParametersDTO params = new PdParametersDTO();
    params.setPdDeviceName(pdDeviceName);
    params.setLoopDeviceConfigDTOs(dtos);
    PdEP.pdCommonService.updateLoopDeviceConfig(
        params,
        new MethodCallback<Void>() {
          @Override
          public void onSuccess(Method method, Void response) {
            viewer.updateTreeGrid();
            viewer.updateUnsetGrid();
          }

          @Override
          public void onFailure(Method method, Throwable caught) {
            GWT.log("pdLoopDeviceConfigSettingPresenter.updateLoopDeviceConfig failed.", caught);
          }
        });
  }

  public void updateLoopDeviceData(LoopDeviceConfigDTO dto) {
    PdEP.pdCommonService.updateLoopDeviceData(
        dto,
        new MethodCallback<Void>() {
          @Override
          public void onSuccess(Method method, Void result) {
            viewer.updateTreeGrid();
            viewer.updateUnsetGrid();
            viewer.hideDialog();
          }

          @Override
          public void onFailure(Method method, Throwable caught) {
            GWT.log("pdLoopDeviceConfigSettingPresenter.updateLoopDeviceData failed.", caught);
          }
        });
  }
}
