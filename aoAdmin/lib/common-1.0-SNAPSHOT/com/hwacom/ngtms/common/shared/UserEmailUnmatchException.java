/*
 * © HwaCom Systems Inc. 2021
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.common.shared;

public class UserEmailUnmatchException extends Exception {

  private static final long serialVersionUID = -2143053344284476483L;

  public UserEmailUnmatchException() {
    super();
  }

  public UserEmailUnmatchException(String message) {
    super(message);
  }

  public UserEmailUnmatchException(String message, Throwable cause) {
    super(message, cause);
  }

  public UserEmailUnmatchException(Throwable cause) {
    super(cause);
  }
}
