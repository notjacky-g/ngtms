package com.hwacom.ngtms.common.am.restygwt;

import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestException;
import org.fusesource.restygwt.client.Method;

public class GwtUploadSessionDispatcher extends AuthDispatcher {

  public static final GwtUploadSessionDispatcher INSTANCE = new GwtUploadSessionDispatcher();

  @Override
  public Request send(Method method, RequestBuilder builder) throws RequestException {
    builder.setIncludeCredentials(true);
    return super.send(method, builder);
  }
}
