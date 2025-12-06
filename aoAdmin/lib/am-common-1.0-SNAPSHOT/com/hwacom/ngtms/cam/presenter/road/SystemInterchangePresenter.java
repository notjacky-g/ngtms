/*
 * © HwaCom Systems Inc. 2015
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.cam.presenter.road;

import com.google.gwt.core.client.GWT;
import com.hwacom.ngtms.c.shared.dto.DeviceConfigDTO;
import com.hwacom.ngtms.c.shared.dto.DeviceTypeDTO;
import com.hwacom.ngtms.c.shared.dto.RampVdConfigDTO;
import com.hwacom.ngtms.c.shared.dto.RingRoadConfigDTO;
import com.hwacom.ngtms.c.shared.dto.RoadDivisionDTO;
import com.hwacom.ngtms.c.shared.dto.RoadParametersDTO;
import com.hwacom.ngtms.cam.client.RoadEP;
import com.hwacom.ngtms.cam.view.road.SystemInterchangeViewer;
import com.sencha.gxt.widget.core.client.info.Info;
import java.util.ArrayList;
import java.util.List;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;

public class SystemInterchangePresenter {

  private SystemInterchangeViewer viewer;

  public SystemInterchangePresenter(SystemInterchangeViewer viewer) {
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
            GWT.log("SystemInterchangePresenter.addRoadDivision failed.", caught);
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
            GWT.log("SystemInterchangePresenter.removeRoadDivision failed.", caught);
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
            GWT.log("SystemInterchangePresenter.saveRoadDivision failed.", caught);
            Info.display(RoadEP.messages.info(), caught.getMessage());
            viewer.saveItemResult(false);
          }
        });
  }

  public void getRampVdConfigs(String divisionId) {
    // TODO RampVdConfig already moved to commonDgsFm
    //		RoadParametersDTO params = new RoadParametersDTO();
    //		params.setDivisionId(divisionId);
    //		RoadEntry.service.getRampVdConfigs(params, new MethodCallback<List<RampVdConfigDTO>>() {
    //			@Override
    //			public void onSuccess(Method method, List<RampVdConfigDTO> rampVdConfigs) {
    //				viewer.getRampVdConfigsResult(rampVdConfigs);
    //			}
    //			@Override
    //			public void onFailure(Method method, Throwable caught) {
    //				GWT.log("SystemInterchangePresenter.getRampVdConfigs failed.", caught);
    //				viewer.getRampVdConfigsResult(null);
    //			}
    //		});
  }

  public void getRampVdTypes() {
    // TODO RampVdConfig already moved to commonDgsFm
    //		RoadEntry.service.getRampVdTypes(new MethodCallback<List<RampVdTypeDTO>>() {
    //			@Override
    //			public void onSuccess(Method method, List<RampVdTypeDTO> rampVdTypes) {
    //				viewer.getRampVdTypesResult(rampVdTypes);
    //			}
    //			@Override
    //			public void onFailure(Method method, Throwable caught) {
    //				GWT.log("SystemInterchangePresenter.getRampVdTypes failed.", caught);
    //				viewer.getRampVdConfigsResult(null);
    //			}
    //		});
  }

  public void addRampItem(RampVdConfigDTO dto) {
    // TODO RampVdConfig already moved to commonDgsFm
    //		RoadParametersDTO params = new RoadParametersDTO();
    //		params.setRampVdConfigDTO(dto);
    //		RoadEntry.service.addRampVdConfig(params, new MethodCallback<Boolean>() {
    //			@Override
    //			public void onSuccess(Method method, Boolean result) {
    //				viewer.addRampItemResult(result);
    //			}
    //			@Override
    //			public void onFailure(Method method, Throwable caught) {
    //				GWT.log("SystemInterchangePresenter.addRampVdConfig failed.", caught);
    //				Info.display(RoadEntry.messages.info(), caught.getMessage());
    //				viewer.addRampItemResult(false);
    //			}
    //		});
  }

  public void removeRampItem(Integer key) {
    // TODO RampVdConfig already moved to commonDgsFm
    //		RoadParametersDTO params = new RoadParametersDTO();
    //		params.setKeyInInteger(key);
    //		RoadEntry.service.removeRampVdConfig(params, new MethodCallback<Boolean>() {
    //			@Override
    //			public void onSuccess(Method method, Boolean result) {
    //				viewer.removeRampItemResult(result);
    //			}
    //			@Override
    //			public void onFailure(Method method, Throwable caught) {
    //				GWT.log("SystemInterchangePresenter.removeRampVdConfig failed.", caught);
    //				Info.display(RoadEntry.messages.info(), caught.getMessage());
    //				viewer.removeRampItemResult(false);
    //			}
    //		});
  }

  public void saveRampItem(RampVdConfigDTO dto) {
    // TODO RampVdConfig already moved to commonDgsFm
    //		RoadParametersDTO params = new RoadParametersDTO();
    //		params.setRampVdConfigDTO(dto);
    //		RoadEntry.service.saveRampVdConfig(params, new MethodCallback<Boolean>() {
    //			@Override
    //			public void onSuccess(Method method, Boolean result) {
    //				viewer.saveRampItemResult(result);
    //			}
    //			@Override
    //			public void onFailure(Method method, Throwable caught) {
    //				GWT.log("SystemInterchangePresenter.saveRampVdConfig failed.", caught);
    //				Info.display(RoadEntry.messages.info(), caught.getMessage());
    //				viewer.saveRampItemResult(false);
    //			}
    //		});
  }

  public void getRingRoadConfigs(String divisionId) {
    // TODO RingRoadConfig already moved to commonDgsFm
    //		RoadParametersDTO params = new RoadParametersDTO();
    //		params.setDivisionId(divisionId);
    //		RoadEntry.service.getRingRoadConfigs(params, new MethodCallback<List<RingRoadConfigDTO>>() {
    //			@Override
    //			public void onSuccess(Method method, List<RingRoadConfigDTO> ringRoadConfigs) {
    //				viewer.getRingRoadConfigsResult(ringRoadConfigs);
    //			}
    //			@Override
    //			public void onFailure(Method method, Throwable caught) {
    //				GWT.log("SystemInterchangePresenter.getRingRoadConfigs failed.", caught);
    //				viewer.getRingRoadConfigsResult(null);
    //			}
    //		});
  }

  public void addRingItem(RingRoadConfigDTO dto) {
    // TODO RingRoadConfig already moved to commonDgsFm
    //		RoadParametersDTO params = new RoadParametersDTO();
    //		params.setRingRoadConfigDTO(dto);
    //		RoadEntry.service.addRingRoadConfig(params, new MethodCallback<Boolean>() {
    //			@Override
    //			public void onSuccess(Method method, Boolean result) {
    //				viewer.addRingItemResult(result);
    //			}
    //			@Override
    //			public void onFailure(Method method, Throwable caught) {
    //				GWT.log("SystemInterchangePresenter.addRingRoadConfig failed.", caught);
    //				Info.display(RoadEntry.messages.info(), caught.getMessage());
    //				viewer.addRingItemResult(false);
    //			}
    //		});
  }

  public void removeRingItem(String key) {
    // TODO RingRoadConfig already moved to commonDgsFm
    //		RoadParametersDTO params = new RoadParametersDTO();
    //		params.setKey(key);
    //		RoadEntry.service.removeRingRoadConfig(params, new MethodCallback<Boolean>() {
    //			@Override
    //			public void onSuccess(Method method, Boolean result) {
    //				viewer.removeRingItemResult(result);
    //			}
    //			@Override
    //			public void onFailure(Method method, Throwable caught) {
    //				GWT.log("SystemInterchangePresenter.removeRingRoadConfig failed.", caught);
    //				Info.display(RoadEntry.messages.info(), caught.getMessage());
    //				viewer.removeRingItemResult(false);
    //			}
    //		});
  }

  public void saveRingItem(RingRoadConfigDTO dto) {
    // TODO RingRoadConfig already moved to commonDgsFm
    //		RoadParametersDTO params = new RoadParametersDTO();
    //		params.setRingRoadConfigDTO(dto);
    //		RoadEntry.service.saveRingRoadConfig(params, new MethodCallback<Boolean>() {
    //			@Override
    //			public void onSuccess(Method method, Boolean result) {
    //				viewer.saveRingItemResult(result);
    //			}
    //			@Override
    //			public void onFailure(Method method, Throwable caught) {
    //				GWT.log("SystemInterchangePresenter.saveRingRoadConfig failed.", caught);
    //				Info.display(RoadEntry.messages.info(), caught.getMessage());
    //				viewer.saveRingItemResult(false);
    //			}
    //		});
  }

  public void getDeviceConfig(List<DeviceTypeDTO> deviceTypes) {
    List<String> typeListNames = new ArrayList<>();
    for (DeviceTypeDTO dto : deviceTypes) {
      typeListNames.add(dto.getId());
    }
    RoadEP.camService.getDeviceConfig(
        typeListNames,
        new MethodCallback<List<DeviceConfigDTO>>() {
          @Override
          public void onSuccess(Method method, List<DeviceConfigDTO> result) {
            viewer.fillDeviceConfig(result);
          }

          @Override
          public void onFailure(Method method, Throwable caught) {
            GWT.log("SystemInterchangePresenter.getDeviceConfig failed.", caught);
          }
        });
  }
}
