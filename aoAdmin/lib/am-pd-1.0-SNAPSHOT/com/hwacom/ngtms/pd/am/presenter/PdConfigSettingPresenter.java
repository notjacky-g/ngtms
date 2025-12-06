package com.hwacom.ngtms.pd.am.presenter;

import com.google.gwt.core.client.GWT;
import com.hwacom.ngtms.c.shared.dto.RoadLineDTO;
import com.hwacom.ngtms.c.shared.dto.RoadSectionDTO;
import com.hwacom.ngtms.pd.am.PdEP;
import com.hwacom.ngtms.pd.am.view.PdConfigSettingViewer;
import com.hwacom.ngtms.pd.shared.dto.LocationDTO;
import com.hwacom.ngtms.pd.shared.dto.PdConfigDTO;
import com.hwacom.ngtms.pd.shared.dto.PdParametersDTO;
import java.util.List;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;

public class PdConfigSettingPresenter {

  private PdConfigSettingViewer viewer;

  public PdConfigSettingPresenter(PdConfigSettingViewer viewer) {
    this.viewer = viewer;
  }

  public void retrievePdConfigDTO() {
    PdEP.pdCommonService.retrievePdConfig(
        new MethodCallback<List<PdConfigDTO>>() {
          @Override
          public void onSuccess(Method method, List<PdConfigDTO> result) {
            viewer.addPdConfig(result);
          }

          @Override
          public void onFailure(Method method, Throwable caught) {
            GWT.log("PdConfigSettingPresenter.retrievePdConfig failed.", caught);
          }
        });
  }

  public void checkPdConfig(String deviceName, String action, PdConfigDTO dto) {
    PdParametersDTO params = new PdParametersDTO();
    params.setPdDeviceName(deviceName);
    PdEP.pdCommonService.checkPdConfig(
        params,
        new MethodCallback<Boolean>() {
          @Override
          public void onSuccess(Method method, Boolean response) {
            if (response == false) {
              savePdConfig(dto);
            } else {
              if (action.equals("save")) {
                viewer.showExistInfo();
                return;
              } else {
                updatePdConfig(dto);
              }
            }
          }

          @Override
          public void onFailure(Method method, Throwable caught) {
            GWT.log("PdConfigSettingPresenter.checkPdConfig failed.", caught);
          }
        });
  }

  public void checkRemovedPdConfig(String deviceName) {
    PdParametersDTO dto = new PdParametersDTO();
    dto.setPdDeviceName(deviceName);
    PdEP.pdCommonService.checkRemovedPdConfig(
        dto,
        new MethodCallback<Boolean>() {
          @Override
          public void onSuccess(Method method, Boolean response) {
            if (response == false) {
              deletePdConfig(deviceName);
              viewer.cleanField();
            } else {
              viewer.confirmDialog(deviceName);
            }
          }

          @Override
          public void onFailure(Method method, Throwable caught) {
            GWT.log("PdConfigSettingPresenter.checkRemovedPdConfig failed.", caught);
          }
        });
  }

  public void savePdConfig(PdConfigDTO dto) {
    PdEP.pdCommonService.savePdConfig(
        dto,
        new MethodCallback<Void>() {
          @Override
          public void onSuccess(Method method, Void response) {
            viewer.refillGrid();
            viewer.updatePdConfig();
          }

          @Override
          public void onFailure(Method method, Throwable caught) {
            GWT.log("PdConfigSettingPresenter.savePdConfig failed.", caught);
          }
        });
  }

  public void updatePdConfig(PdConfigDTO dto) {
    PdEP.pdCommonService.updatePdConfig(
        dto,
        new MethodCallback<Void>() {
          @Override
          public void onSuccess(Method method, Void response) {
            viewer.refillGrid();
            viewer.updatePdConfig();
          }

          @Override
          public void onFailure(Method method, Throwable caught) {
            GWT.log("PdConfigSettingPresenter.updatePdConfig failed.", caught);
          }
        });
  }

  public void deletePdConfig(String deviceName) {
    PdParametersDTO dto = new PdParametersDTO();
    dto.setPdDeviceName(deviceName);
    PdEP.pdCommonService.deletePdConfig(
        dto,
        new MethodCallback<Void>() {
          @Override
          public void onSuccess(Method method, Void response) {
            viewer.refillGrid();
            viewer.updatePdConfig();
          }

          @Override
          public void onFailure(Method method, Throwable caught) {
            GWT.log("PdConfigSettingPresenter.deletePdConfig failed.", caught);
          }
        });
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
            GWT.log("PdConfigSettingPresenter.retrieveRoadLine failed.", caught);
          }
        });
  }

  public void retrieveLocation() {
    PdEP.pdCommonService.retrieveLocation(
        new MethodCallback<List<LocationDTO>>() {
          @Override
          public void onSuccess(Method method, List<LocationDTO> result) {
            viewer.fillLocation(result);
          }

          @Override
          public void onFailure(Method method, Throwable caught) {
            GWT.log("PdConfigSettingPresenter.retrieveLocation failed.", caught);
          }
        });
  }

  public void retrieveSection() {
    PdEP.pdCommonService.retrieveSection(
        new MethodCallback<List<RoadSectionDTO>>() {
          @Override
          public void onSuccess(Method method, List<RoadSectionDTO> result) {
            viewer.fillSection(result);
          }

          @Override
          public void onFailure(Method method, Throwable caught) {
            GWT.log("PdConfigSettingPresenter.retrieveSection failed.", caught);
          }
        });
  }
}
