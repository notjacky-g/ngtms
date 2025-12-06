/*
 * © HwaCom Systems Inc. 2013
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.common.am.presenter;

import com.google.gwt.core.client.GWT;
import com.hwacom.ngtms.common.am.AccountEP;
import com.hwacom.ngtms.common.am.event.AccountDataEvent;
import com.hwacom.ngtms.common.am.view.RoleFunctionPermissionViewer;
import com.hwacom.ngtms.common.shared.dto.AccountParametersDTO;
import com.hwacom.ngtms.common.shared.dto.FunctionPermissionDTO;
import com.hwacom.ngtms.common.shared.dto.RoleDTO;
import com.hwacom.ngtms.hcce.am.factory.ClientFactory;
import com.sencha.gxt.widget.core.client.info.Info;
import java.util.List;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;

public class RoleFunctionPermissionPresenter {

  private final ClientFactory clientFactory = GWT.create(ClientFactory.class);

  private RoleFunctionPermissionViewer viewer;

  public RoleFunctionPermissionPresenter(RoleFunctionPermissionViewer viewer) {
    this.viewer = viewer;
    initRoles();
    init();
  }

  private void initRoles() {
    AccountEP.accountService.getRoles(
        new MethodCallback<List<RoleDTO>>() {
          @Override
          public void onSuccess(Method method, List<RoleDTO> result) {
            if (result != null) {
              viewer.initRoles(result);
              clientFactory
                  .getEventBus()
                  .fireEventFromSource(
                      new AccountDataEvent(AccountDataEvent.Action.ROLE_INIT_DATA), result);
            }
          }

          @Override
          public void onFailure(Method method, Throwable caught) {
            GWT.log("RoleFunctionPermissionPresenter.init failed.", caught);
          }
        });
  }

  private void init() {
    AccountEP.accountService.getFunctionPermissions(
        new MethodCallback<List<FunctionPermissionDTO>>() {
          @Override
          public void onSuccess(Method method, List<FunctionPermissionDTO> result) {
            if (result != null) {
              viewer.initFunctionPermission(result);
            }
          }

          @Override
          public void onFailure(Method method, Throwable caught) {
            GWT.log("RoleFunctionPermissionPresenter.getFunctionPermissions failed.", caught);
          }
        });
  }

  public void addItem(RoleDTO dto) {
    AccountParametersDTO params = new AccountParametersDTO();
    params.setRoleDTO(dto);
    AccountEP.accountService.addRole(
        params,
        new MethodCallback<RoleDTO>() {
          @Override
          public void onSuccess(Method method, RoleDTO result) {
            if (result != null) {
              viewer.addStore(result);
              initRoles();
            }
          }

          @Override
          public void onFailure(Method method, Throwable caught) {
            GWT.log("RoleFunctionPermissionPresenter.addRole failed.", caught);
            Info.display(AccountEP.messages.message(), AccountEP.messages.message_addFail());
          }
        });
  }

  public void removeItem(final RoleDTO dto) {
    AccountParametersDTO params = new AccountParametersDTO();
    params.setRoleDTO(dto);
    AccountEP.accountService.deleteRole(
        params,
        new MethodCallback<Boolean>() {
          @Override
          public void onSuccess(Method method, Boolean result) {
            if (result) {
              viewer.removeStore(dto);
              initRoles();
            }
          }

          @Override
          public void onFailure(Method method, Throwable caught) {
            GWT.log("RoleFunctionPermissionPresenter.deleteRole failed.", caught);
            Info.display(AccountEP.messages.message(), AccountEP.messages.message_deleteFail());
          }
        });
  }

  public void saveItem(RoleDTO dto) {
    AccountParametersDTO params = new AccountParametersDTO();
    params.setRoleDTO(dto);
    AccountEP.accountService.saveRole(
        params,
        new MethodCallback<RoleDTO>() {
          @Override
          public void onSuccess(Method method, RoleDTO result) {
            if (result != null) {
              viewer.updateSotre(result);
            }
          }

          @Override
          public void onFailure(Method method, Throwable caught) {
            GWT.log("RoleFunctionPermissionPresenter.saveRole failed.", caught);
            Info.display(AccountEP.messages.message(), AccountEP.messages.message_saveFail());
          }
        });
  }
}
