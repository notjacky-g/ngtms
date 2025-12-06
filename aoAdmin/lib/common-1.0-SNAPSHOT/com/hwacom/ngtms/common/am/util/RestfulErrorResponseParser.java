/*
 * © HwaCom Systems Inc. 2021
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.common.am.util;

import com.google.gwt.core.client.JsonUtils;
import com.google.gwt.json.client.JSONObject;
import com.hwacom.ngtms.common.shared.RestfulErrorResponse;
import java.util.Optional;
import org.fusesource.restygwt.client.Method;

public class RestfulErrorResponseParser {

  public static Optional<RestfulErrorResponse> parse(Method method) {
    return parse(method.getResponse().getText());
  }

  public static Optional<RestfulErrorResponse> parse(String json) {
    try {
      JSONObject jsonObject = new JSONObject(JsonUtils.safeEval(json));
      int code = (int) jsonObject.get("code").isNumber().doubleValue();
      RestfulErrorResponse response =
          new RestfulErrorResponse(code, jsonObject.get("message").isString().stringValue());
      return Optional.of(response);
    } catch (Exception e) {
      return Optional.empty();
    }
  }
}
