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
import com.google.gwt.user.client.ui.Widget;
import com.hwacom.ngtms.common.am.util.MessageDigestUtil;
import com.hwacom.ngtms.common.am.util.Validator;
import com.hwacom.ngtms.common.shared.dto.UserBasicInfoDTO;
import com.sencha.gxt.widget.core.client.Composite;
import com.sencha.gxt.widget.core.client.form.PasswordField;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.form.validator.RegExValidator;
import com.sencha.gxt.widget.core.client.info.Info;
import java.util.Objects;

public class UserBasicInfo extends Composite {

  private static UserBasicInfoUiBinder uiBinder = GWT.create(UserBasicInfoUiBinder.class);

  interface UserBasicInfoUiBinder extends UiBinder<Widget, UserBasicInfo> {}

  private static final Messages messages = GWT.create(Messages.class);

  @UiField TextField login;

  @UiField TextField name;

  @UiField PasswordField password;

  @UiField PasswordField confirmPassword;

  @UiField TextField description;

  @UiField TextField mail;

  @UiField TextField mobile;

  public UserBasicInfo() {
    initWidget(uiBinder.createAndBindUi(this));
    RegExValidator regExValidator = Validator.pwdValidator();
    password.addValidator(regExValidator);
    confirmPassword.addValidator(regExValidator);
    mail.addValidator(
        new RegExValidator("^(\\w+)([-+.][\\w]+)*@(\\w[-\\w]*\\.){1,5}([A-Za-z]){2,4}$"));
    mobile.addValidator(new RegExValidator("^09[0-9]{8}$"));
  }

  @Override
  public void disable() {
    super.disable();
    login.disable();
    name.disable();
    password.disable();
    confirmPassword.disable();
    description.disable();
    mail.disable();
    mobile.disable();
  }

  public boolean validate() {
    boolean valid =
        login.isValid() && name.isValid() && password.isValid() && confirmPassword.isValid();
    if (!Objects.equals(password.getValue(), confirmPassword.getValue())) {
      Info.display(messages.message(), messages.message_confirmPasswordError());
      return false;
    } else {
      return valid;
    }
  }

  public UserBasicInfoDTO getDto() {
    UserBasicInfoDTO dto = new UserBasicInfoDTO();
    dto.setLogin(login.getValue());
    dto.setName(name.getValue());
    String originalPassword = password.getOriginalValue();
    dto.setPwd1(
        originalPassword != null && originalPassword.equals(password.getValue())
            ? password.getValue()
            : MessageDigestUtil.sha256(password.getValue()));
    dto.setDescription(description.getValue());
    dto.setMail(mail.getValue());
    dto.setMobile(mobile.getValue());
    return dto;
  }

  public void setDto(UserBasicInfoDTO dto) {
    login.setValue(dto.getLogin());
    name.setValue(dto.getName());
    password.setValue(dto.getPwd1());
    password.setOriginalValue(dto.getPwd1());
    confirmPassword.setValue(dto.getPwd1());
    description.setValue(dto.getDescription());
    mail.setValue(dto.getMail());
    mobile.setValue(dto.getMobile());
  }
}
