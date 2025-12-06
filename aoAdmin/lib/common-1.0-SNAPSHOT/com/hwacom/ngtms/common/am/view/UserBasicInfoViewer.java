/*

* © HwaCom Systems Inc. 2019
* All Rights Reserved
* No part of this software or any of its contents may be reproduced, copied, modified or adapted,
* without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
*/
package com.hwacom.ngtms.common.am.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Widget;
import com.hwacom.ngtms.common.am.presenter.UserBasicInfoPresenter;
import com.hwacom.ngtms.common.shared.dto.UserBasicInfoDTO;
import com.sencha.gxt.widget.core.client.Composite;
import com.sencha.gxt.widget.core.client.event.SelectEvent;

public class UserBasicInfoViewer extends Composite {

  private static UserBasicInfoViewerUiBinder uiBinder =
      GWT.create(UserBasicInfoViewerUiBinder.class);

  interface UserBasicInfoViewerUiBinder extends UiBinder<Widget, UserBasicInfoViewer> {}

  private UserBasicInfoPresenter presenter;

  @UiField UserBasicInfo basicInfo;

  public UserBasicInfoViewer() {
    initWidget(uiBinder.createAndBindUi(this));
    basicInfo.login.disable();
    presenter = new UserBasicInfoPresenter(this);
  }

  public void init(UserBasicInfoDTO dto) {
    basicInfo.setDto(dto);
  }

  @UiHandler("save")
  public void onSave(SelectEvent event) {
    if (basicInfo.validate()) {
      presenter.save(basicInfo.getDto());
    }
  }
}
