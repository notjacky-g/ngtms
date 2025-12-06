package com.hwacom.ngtms.common.am.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MessageDigestUtil {

  public static String sha256(String s) {
    try {
      MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
      byte[] bytes = messageDigest.digest(s.getBytes(StandardCharsets.UTF_8));
      StringBuffer result = new StringBuffer();
      for (byte each : bytes) {
        result.append(Integer.toString((each & 0xff) + 0x100, 16).substring(1));
      }
      return result.toString();
    } catch (NoSuchAlgorithmException e) {
      return s;
    }
  }
}
