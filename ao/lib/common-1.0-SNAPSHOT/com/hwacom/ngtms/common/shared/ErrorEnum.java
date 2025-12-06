/*
 * © HwaCom Systems Inc. 2021
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.common.shared;

import java.util.Optional;

public enum ErrorEnum {
  USER_NOT_FOUND(1010),
  USER_EMAIL_UNMATCH(1020),
  INVALID_TOKEN(1030),
  TOKEN_EXPIRED(1040),
  TOKEN_NOT_EXPIRED(1050),
  INCORRECT_PWD(1060),
  VERIFICATION_FAILED(1800),
  RESTFUL_EXCEPTION(1900);

  private int code;

  ErrorEnum(int code) {
    this.code = code;
  }

  public int getCode() {
    return code;
  }

  public static Optional<ErrorEnum> toErrorEnum(int code) {
    for (ErrorEnum each : ErrorEnum.values()) {
      if (each.getCode() == code) {
        return Optional.of(each);
      }
    }
    return Optional.empty();
  }
}
