/*
 * © HwaCom Systems Inc. 2015
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.cam.util;

import com.google.gwt.core.client.GWT;
import com.hwacom.ngtms.cam.view.Messages;
import com.sencha.gxt.widget.core.client.Dialog;
import com.sencha.gxt.widget.core.client.Dialog.DialogMessages;
import com.sencha.gxt.widget.core.client.Dialog.PredefinedButton;

public class DialogUtil {
  public static final Messages messages = GWT.create(Messages.class);
  public static final DialogMessages dialogMessages =
      new DialogMessages() {
        @Override
        public String yes() {
          return messages.dialog_yes();
        }

        @Override
        public String ok() {
          return messages.dialog_ok();
        }

        @Override
        public String no() {
          return messages.dialog_no();
        }

        @Override
        public String close() {
          return messages.dialog_close();
        }

        @Override
        public String cancel() {
          return messages.dialog_cancel();
        }
      };

  public static void buildButton(Dialog dialog, PredefinedButton... buttons) {
    dialog.setDialogMessages(dialogMessages);
    dialog.setPredefinedButtons(buttons);
  }
}
