/*
 * © HwaCom Systems Inc. 2019
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.common.am.presenter;

import com.google.gwt.core.client.GWT;
import com.hwacom.ngtms.common.am.AmEntryPoint;
import com.hwacom.ngtms.common.am.view.Messages;
import com.hwacom.ngtms.common.am.view.UserBasicInfoViewer;
import com.hwacom.ngtms.common.shared.dto.UserBasicInfoDTO;
import com.sencha.gxt.widget.core.client.info.Info;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;

public class UserBasicInfoPresenter {

  private static final Messages messages = GWT.create(Messages.class);

  private UserBasicInfoViewer viewer;

  public UserBasicInfoPresenter(UserBasicInfoViewer viewer) {
    this.viewer = viewer;
    retrieveUserBasicInfo();
  }

  private void retrieveUserBasicInfo() {
    AmEntryPoint.accountService.retrieveUserBasicInfo(
        AmEntryPoint.getUserLogin(),
        new MethodCallback<UserBasicInfoDTO>() {
          @Override
          public void onSuccess(Method method, UserBasicInfoDTO dto) {
            viewer.init(dto);
          }

          @Override
          public void onFailure(Method method, Throwable exception) {
            GWT.log("UserBasicInfoPresenter.retrieveUserBasicInfo failed.", exception);
          }
        });
  }

  public void save(UserBasicInfoDTO dto) {
    AmEntryPoint.accountService.saveUserBasicInfo(
        dto,
        new MethodCallback<Void>() {
          @Override
          public void onSuccess(Method method, Void response) {
            Info.display(messages.user_basicInfo(), messages.user_basicInfo_save_success());
          }

          @Override
          public void onFailure(Method method, Throwable exception) {
            Info.display(messages.user_basicInfo(), messages.user_basicInfo_save_failure());
          }
        });
  }
}
