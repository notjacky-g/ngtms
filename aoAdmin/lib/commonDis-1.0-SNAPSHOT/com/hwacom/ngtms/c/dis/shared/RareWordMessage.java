/*
 * © HwaCom Systems Inc. 2015
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.c.dis.shared;

import com.hwacom.ngtms.c.shared.DeviceListMessage;

public interface RareWordMessage extends DeviceListMessage {

  String TYPE = RareWordMessage.class.getName();

  String SEPARATOR = ",";

  String getCode();

  void setCode(String code);

  String getWord32();

  void setWord32(String word32);

  String getWord48();

  void setWord48(String word48);

  String getWord64();

  void setWord64(String word64);

  public class RareWordMessageImpl extends DeviceListMessageImpl implements RareWordMessage {

    private String code;

    private String word32;

    private String word48;

    private String word64;

    @Override
    public String getCode() {
      return code;
    }

    @Override
    public void setCode(String code) {
      this.code = code;
    }

    @Override
    public String getWord32() {
      return word32;
    }

    @Override
    public void setWord32(String word32) {
      this.word32 = word32;
    }

    @Override
    public String getWord48() {
      return word48;
    }

    @Override
    public void setWord48(String word48) {
      this.word48 = word48;
    }

    @Override
    public String getWord64() {
      return word64;
    }

    @Override
    public void setWord64(String word64) {
      this.word64 = word64;
    }
  }
}
