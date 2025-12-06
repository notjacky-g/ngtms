/*
 * © HwaCom Systems Inc. 2021
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.common.shared;

public class RestfulException extends Exception {

  private static final long serialVersionUID = -4019658095706413247L;

  public RestfulException() {
    super();
  }

  public RestfulException(String message) {
    super(message);
  }

  public RestfulException(String message, Throwable cause) {
    super(message, cause);
  }

  public RestfulException(Throwable cause) {
    super(cause);
  }
}
