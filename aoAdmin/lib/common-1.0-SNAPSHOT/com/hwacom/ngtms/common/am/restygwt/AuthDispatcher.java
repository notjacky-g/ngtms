package com.hwacom.ngtms.common.am.restygwt;

import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestException;
import com.hwacom.ngtms.common.am.AmEntryPoint;
import org.fusesource.restygwt.client.Dispatcher;
import org.fusesource.restygwt.client.Method;

public class AuthDispatcher implements Dispatcher {

  public static final AuthDispatcher INSTANCE = new AuthDispatcher();

  @Override
  public Request send(Method method, RequestBuilder builder) throws RequestException {
    builder.setHeader("encryptedUserLogin", AmEntryPoint.getUserLogin());
    return builder.send();
  }
}
