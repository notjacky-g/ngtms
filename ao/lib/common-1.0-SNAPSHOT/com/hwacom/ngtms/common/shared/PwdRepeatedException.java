/*
 * © HwaCom Systems Inc. 2020
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.common.shared;

public class PwdRepeatedException extends Exception {

  private static final long serialVersionUID = 1L;

  public PwdRepeatedException() {
    super();
  }

  public PwdRepeatedException(String message) {
    super(message);
  }

  public PwdRepeatedException(String message, Throwable cause) {
    super(message, cause);
  }

  public PwdRepeatedException(Throwable cause) {
    super(cause);
  }
}
