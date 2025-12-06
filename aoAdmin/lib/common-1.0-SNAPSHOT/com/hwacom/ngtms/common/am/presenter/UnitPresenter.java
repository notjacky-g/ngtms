package com.hwacom.ngtms.common.am.presenter;

import com.google.gwt.core.client.GWT;
import com.hwacom.ngtms.common.am.AccountEP;
import com.hwacom.ngtms.common.am.view.UnitViewer;
import com.hwacom.ngtms.common.shared.dto.AccountParametersDTO;
import com.hwacom.ngtms.common.shared.dto.UnitDTO;
import com.sencha.gxt.widget.core.client.info.Info;
import java.util.List;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;

public class UnitPresenter {

  private UnitViewer viewer;

  public UnitPresenter(UnitViewer viewer) {
    this.viewer = viewer;
    init();
  }

  private void init() {
    AccountEP.accountService.getUnits(
        new MethodCallback<List<UnitDTO>>() {
          @Override
          public void onSuccess(Method method, List<UnitDTO> result) {
            if (result != null) {
              viewer.init(result);
            }
          }

          @Override
          public void onFailure(Method method, Throwable caught) {
            GWT.log("UnitPresenter.getUnits failed.", caught);
          }
        });
  }

  public void addItem(UnitDTO dto) {
    AccountParametersDTO params = new AccountParametersDTO();
    params.setUnitDTO(dto);
    AccountEP.accountService.addUnit(
        params,
        new MethodCallback<UnitDTO>() {
          @Override
          public void onSuccess(Method method, UnitDTO result) {
            if (result != null) {
              viewer.addStore(result);
            }
          }

          @Override
          public void onFailure(Method method, Throwable caught) {
            GWT.log("UnitPresenter.addUnit failed.", caught);
            Info.display(AccountEP.messages.message(), AccountEP.messages.message_addFail());
          }
        });
  }

  public void removeItem(final UnitDTO dto) {
    AccountParametersDTO params = new AccountParametersDTO();
    params.setUnitDTO(dto);
    AccountEP.accountService.deleteUnit(
        params,
        new MethodCallback<Boolean>() {
          @Override
          public void onSuccess(Method method, Boolean result) {
            if (result) {
              viewer.removeStore(dto);
            }
          }

          @Override
          public void onFailure(Method method, Throwable caught) {
            GWT.log("UnitPresenter.deleteUnit failed.", caught);
            Info.display(AccountEP.messages.message(), AccountEP.messages.message_deleteFail());
          }
        });
  }

  public void saveItem(UnitDTO dto) {
    AccountParametersDTO params = new AccountParametersDTO();
    params.setUnitDTO(dto);
    AccountEP.accountService.saveUnit(
        params,
        new MethodCallback<UnitDTO>() {
          @Override
          public void onSuccess(Method method, UnitDTO result) {
            if (result != null) {
              viewer.updateStore(result);
            }
          }

          @Override
          public void onFailure(Method method, Throwable caught) {
            GWT.log("UnitPresenter.saveUnit failed.", caught);
            Info.display(AccountEP.messages.message(), AccountEP.messages.message_saveFail());
          }
        });
  }
}
