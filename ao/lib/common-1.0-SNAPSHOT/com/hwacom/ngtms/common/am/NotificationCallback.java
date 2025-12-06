package com.hwacom.ngtms.common.am;

import com.google.gwt.core.shared.GWT;
import com.hwacom.ngtms.common.am.view.Messages;
import com.sencha.gxt.widget.core.client.info.Info;
import java.util.function.Consumer;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;

public class NotificationCallback<T> implements MethodCallback<T> {

  private Messages messages = GWT.create(Messages.class);

  private Consumer<T> consumer;

  public NotificationCallback() {
    consumer = t -> {};
  }

  public NotificationCallback(Consumer<T> consumer) {
    this.consumer = consumer;
  }

  @Override
  public void onFailure(Method method, Throwable exception) {
    Info.display(messages.operation_result(), messages.operation_failure());
  }

  @Override
  public void onSuccess(Method method, T response) {
    consumer.accept(response);
    Info.display(messages.operation_result(), messages.operation_success());
  }
}
