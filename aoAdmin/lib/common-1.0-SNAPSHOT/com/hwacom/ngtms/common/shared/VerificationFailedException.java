/*
 * © HwaCom Systems Inc. 2021
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.common.shared;

public class VerificationFailedException extends Exception {

  private static final long serialVersionUID = 1L;

  public VerificationFailedException() {
    super();
  }

  public VerificationFailedException(String message) {
    super(message);
  }

  public VerificationFailedException(String message, Throwable cause) {
    super(message, cause);
  }

  public VerificationFailedException(Throwable cause) {
    super(cause);
  }
}
