/*
 * © HwaCom Systems Inc. 2015
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.cam.presenter.home;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Timer;
import com.hwacom.ngtms.cam.client.HomeEP;
import com.hwacom.ngtms.cam.view.home.HomeViewer;
import com.hwacom.ngtms.common.shared.dto.FunctionPermissionDTO;
import com.hwacom.ngtms.common.shared.dto.UserDTO;
import java.util.List;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;

public class HomePresenter {

  private HomeViewer viewer;

  public HomePresenter(HomeViewer viewer) {
    this.viewer = viewer;
  }

  public void getFunctionPermissions() {
    HomeEP.accountService.getFunctionPermissions(
        new MethodCallback<List<FunctionPermissionDTO>>() {
          @Override
          public void onSuccess(Method method, List<FunctionPermissionDTO> result) {
            if (result != null) {
              GWT.log(
                  "HomePresenter getFunctionPermissions viewer.initFunctionPermission ... start ");
              viewer.initFunctionPermission(result);
              Timer t =
                  new Timer() {
                    @Override
                    public void run() {
                      GWT.log(
                          "HomePresenter getFunctionPermissions onSuccess. next run getUser ... ");
                      getUser();
                    }
                  };
              t.schedule(500);
            }
          }

          @Override
          public void onFailure(Method method, Throwable caught) {
            GWT.log("HomePresenter.getFunctionPermissions failed.", caught);
          }
        });
  }

  public void getUser() {
    HomeEP.camService.getUserDTO(
        new MethodCallback<UserDTO>() {
          @Override
          public void onSuccess(Method method, UserDTO result) {
            if (result != null) {
              viewer.initUser(result);
            }
          }

          @Override
          public void onFailure(Method method, Throwable caught) {
            GWT.log("HomePresenter.getUserDTO failed.", caught);
          }
        });
  }
}
