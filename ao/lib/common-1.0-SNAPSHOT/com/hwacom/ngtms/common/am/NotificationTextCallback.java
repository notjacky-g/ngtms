/*
 * © HwaCom Systems Inc. 2018
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.common.am;

import java.util.function.Consumer;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.TextCallback;

public class NotificationTextCallback implements TextCallback {

  private NotificationCallback<String> callback;

  public NotificationTextCallback() {
    callback = new NotificationCallback<>();
  }

  public NotificationTextCallback(Consumer<String> consumer) {
    callback = new NotificationCallback<>(consumer);
  }

  @Override
  public void onFailure(Method method, Throwable exception) {
    callback.onFailure(method, exception);
  }

  @Override
  public void onSuccess(Method method, String response) {
    callback.onSuccess(method, response);
  }
}
