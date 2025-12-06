/*

* © HwaCom Systems Inc. 2020
* All Rights Reserved
* No part of this software or any of its contents may be reproduced, copied, modified or adapted,
* without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
*/
package com.hwacom.ngtms.common.am.util;

import com.google.gwt.core.client.GWT;
import com.hwacom.ngtms.common.am.view.Messages;
import com.sencha.gxt.widget.core.client.form.validator.RegExValidator;

public class Validator {

  private static final Messages messages = GWT.create(Messages.class);

  public static RegExValidator pwdValidator() {
    return new RegExValidator(
        "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.{8,})", messages.user_password_validation());
  }
}
