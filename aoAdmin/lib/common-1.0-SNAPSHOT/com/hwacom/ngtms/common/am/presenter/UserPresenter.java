/*
 * © HwaCom Systems Inc. 2013
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.common.am.presenter;

import com.google.gwt.core.client.GWT;
import com.hwacom.ngtms.common.am.AccountEP;
import com.hwacom.ngtms.common.am.view.UserViewer;
import com.hwacom.ngtms.common.shared.dto.AccountParametersDTO;
import com.hwacom.ngtms.common.shared.dto.UnitDTO;
import com.hwacom.ngtms.common.shared.dto.UserDTO;
import com.sencha.gxt.widget.core.client.info.Info;
import java.util.List;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;

public class UserPresenter {

  private UserViewer viewer;

  public UserPresenter(UserViewer viewer) {
    this.viewer = viewer;
    init();
  }

  private void init() {
    AccountEP.accountService.getUsers(
        new MethodCallback<List<UserDTO>>() {
          @Override
          public void onSuccess(Method method, List<UserDTO> result) {
            if (result != null) {
              viewer.init(result);
            }
          }

          @Override
          public void onFailure(Method method, Throwable caught) {
            GWT.log("UserPresenter.getUsers failed.", caught);
          }
        });
    AccountEP.accountService.getUnits(
        new MethodCallback<List<UnitDTO>>() {
          @Override
          public void onSuccess(Method method, List<UnitDTO> response) {
            if (response != null) {
              viewer.initUnits(response);
            }
          }

          @Override
          public void onFailure(Method method, Throwable exception) {
            GWT.log("UserPresenter.getUnits failed.", exception);
          }
        });
  }

  public void addItem(UserDTO dto) {
    AccountParametersDTO params = new AccountParametersDTO();
    params.setUserDTO(dto);
    AccountEP.accountService.addUser(
        params,
        new MethodCallback<UserDTO>() {
          @Override
          public void onSuccess(Method method, UserDTO result) {
            if (result != null) {
              viewer.addStore(result);
            }
          }

          @Override
          public void onFailure(Method method, Throwable caught) {
            GWT.log("UserPresenter.addUser failed.", caught);
            Info.display(AccountEP.messages.message(), AccountEP.messages.message_addFail());
          }
        });
  }

  public void removeItem(final UserDTO dto) {
    AccountParametersDTO params = new AccountParametersDTO();
    params.setUserDTO(dto);
    AccountEP.accountService.deleteUser(
        params,
        new MethodCallback<Boolean>() {
          @Override
          public void onSuccess(Method method, Boolean result) {
            if (result) {
              viewer.removeStore(dto);
            } else {
              Info.display(AccountEP.messages.message(), AccountEP.messages.message_deleteFail());
            }
          }

          @Override
          public void onFailure(Method method, Throwable caught) {
            GWT.log("UserPresenter.deleteUser failed.", caught);
            Info.display(AccountEP.messages.message(), AccountEP.messages.message_deleteFail());
          }
        });
  }

  public void saveItem(UserDTO dto) {
    AccountParametersDTO params = new AccountParametersDTO();
    params.setUserDTO(dto);
    AccountEP.accountService.saveUser(
        params,
        new MethodCallback<UserDTO>() {
          @Override
          public void onSuccess(Method method, UserDTO result) {
            if (result != null) {
              viewer.updateStore(result);
            }
          }

          @Override
          public void onFailure(Method method, Throwable caught) {
            GWT.log("UserPresenter.saveUser failed.", caught);
            Info.display(AccountEP.messages.message(), AccountEP.messages.message_saveFail());
          }
        });
  }
}
