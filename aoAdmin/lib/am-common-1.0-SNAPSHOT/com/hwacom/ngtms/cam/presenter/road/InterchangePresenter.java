/*
 * © HwaCom Systems Inc. 2015
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.cam.presenter.road;

import com.google.gwt.core.client.GWT;
import com.hwacom.ngtms.c.shared.dto.RampVdConfigDTO;
import com.hwacom.ngtms.c.shared.dto.RoadDivisionDTO;
import com.hwacom.ngtms.c.shared.dto.RoadParametersDTO;
import com.hwacom.ngtms.cam.client.RoadEP;
import com.hwacom.ngtms.cam.view.road.InterchangeViewer;
import com.sencha.gxt.widget.core.client.info.Info;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;

public class InterchangePresenter {

  private InterchangeViewer viewer;

  public InterchangePresenter(InterchangeViewer viewer) {
    this.viewer = viewer;
    getRampVdTypes();
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
            GWT.log("InterchangePresenter.addRoadDivision failed.", caught);
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
            GWT.log("InterchangePresenter.removeRoadDivision failed.", caught);
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
            GWT.log("InterchangePresenter.saveRoadDivision failed.", caught);
            Info.display(RoadEP.messages.info(), caught.getMessage());
            viewer.saveItemResult(false);
          }
        });
  }

  public void getRampVdConfigs(String divisionId) {
    // TODO ramp config already moved to commonDgsFm
    //		RoadParametersDTO params = new RoadParametersDTO();
    //		params.setDivisionId(divisionId);
    //		RoadEntry.service.getRampVdConfigs(params, new MethodCallback<List<RampVdConfigDTO>>() {
    //			@Override
    //			public void onSuccess(Method method, List<RampVdConfigDTO> rampVdConfigs) {
    //				viewer.getRampVdConfigsResult(rampVdConfigs);
    //			}
    //			@Override
    //			public void onFailure(Method method, Throwable caught) {
    //				GWT.log("InterchangePresenter.getRampVdConfigs failed.", caught);
    //				viewer.getRampVdConfigsResult(null);
    //			}
    //		});
  }

  public void getRampVdTypes() {
    // TODO ramp config already moved to commonDgsFm
    //		RoadEntry.service.getRampVdTypes(new MethodCallback<List<RampVdTypeDTO>>() {
    //			@Override
    //			public void onSuccess(Method method, List<RampVdTypeDTO> rampVdTypes) {
    //				viewer.getRampVdTypesResult(rampVdTypes);
    //			}
    //			@Override
    //			public void onFailure(Method method, Throwable caught) {
    //				GWT.log("InterchangePresenter.getRampVdTypes failed.", caught);
    //				viewer.getRampVdConfigsResult(null);
    //			}
    //		});
  }

  public void addRampItem(RampVdConfigDTO dto) {
    // TODO ramp config already moved to commonDgsFm
    //		RoadParametersDTO params = new RoadParametersDTO();
    //		params.setRampVdConfigDTO(dto);
    //		RoadEntry.service.addRampVdConfig(params, new MethodCallback<Boolean>() {
    //			@Override
    //			public void onSuccess(Method method, Boolean result) {
    //				viewer.addRampItemResult(result);
    //			}
    //			@Override
    //			public void onFailure(Method method, Throwable caught) {
    //				GWT.log("InterchangePresenter.addRampVdConfig failed.", caught);
    //				Info.display(RoadEntry.messages.info(), caught.getMessage());
    //				viewer.addRampItemResult(false);
    //			}
    //		});
  }

  public void removeRampItem(Integer key) {
    // TODO ramp config already moved to commonDgsFm
    //		RoadParametersDTO params = new RoadParametersDTO();
    //		params.setKeyInInteger(key);
    //		RoadEntry.service.removeRampVdConfig(params, new MethodCallback<Boolean>() {
    //			@Override
    //			public void onSuccess(Method method, Boolean result) {
    //				viewer.removeRampItemResult(result);
    //			}
    //			@Override
    //			public void onFailure(Method method, Throwable caught) {
    //				GWT.log("InterchangePresenter.removeRampVdConfig failed.", caught);
    //				Info.display(RoadEntry.messages.info(), caught.getMessage());
    //				viewer.removeRampItemResult(false);
    //			}
    //		});
  }

  public void saveRampItem(RampVdConfigDTO dto) {
    // TODO ramp config already moved to commonDgsFm
    //		RoadParametersDTO params = new RoadParametersDTO();
    //		params.setRampVdConfigDTO(dto);
    //		RoadEntry.service.saveRampVdConfig(params, new MethodCallback<Boolean>() {
    //			@Override
    //			public void onSuccess(Method method, Boolean result) {
    //				viewer.saveRampItemResult(result);
    //			}
    //			@Override
    //			public void onFailure(Method method, Throwable caught) {
    //				GWT.log("InterchangePresenter.saveRampVdConfig failed.", caught);
    //				Info.display(RoadEntry.messages.info(), caught.getMessage());
    //				viewer.saveRampItemResult(false);
    //			}
    //		});
  }
}
