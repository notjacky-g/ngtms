/*
 * © HwaCom Systems Inc. 2021
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.common.shared;

public class TokenNotExpiredException extends RuntimeException {

  private static final long serialVersionUID = -7194190542606191022L;

  public TokenNotExpiredException() {
    super();
  }

  public TokenNotExpiredException(String message) {
    super(message);
  }

  public TokenNotExpiredException(String message, Throwable cause) {
    super(message, cause);
  }

  public TokenNotExpiredException(Throwable cause) {
    super(cause);
  }
}
