/*
 * © HwaCom Systems Inc. 2021
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.common.shared;

public class ValidationRegExConstants {

  public static final String EMAIL = "^(\\w+)([-+.][\\w]+)*@(\\w[-\\w]*\\.){1,5}([A-Za-z]){2,4}$";

  public static final String MOBILE_PHONE = "^09[0-9]{8}$";

  public static final String PWD = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.{8,})";
}
