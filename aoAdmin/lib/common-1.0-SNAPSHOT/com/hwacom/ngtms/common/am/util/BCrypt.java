/*
 * © HwaCom Systems Inc. 2021
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.common.am.util;

public class BCrypt {

  public static String hash(String pwd) {
    return hash(pwd, 10);
  }

  /**
   * @param rounds 4 ~ 31
   * @return
   */
  public static native String hash(String pwd, int rounds) /*-{
    var bcrypt = dcodeIO.bcrypt;
    return bcrypt.hashSync(pwd, rounds);
}-*/;
}
