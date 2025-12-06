/*
 * © HwaCom Systems Inc. 2015
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.ao.shared;

import com.google.gwt.user.client.rpc.IsSerializable;
import java.io.Serializable;

public class CardReaderConfig implements Serializable, IsSerializable {

  private static final long serialVersionUID = 6108551981169789094L;

  //補充欄位用
  private String phoneName;

  public String getPhoneName() {
    return phoneName;
  }

  public void setPhoneName(String phoneName) {
    this.phoneName = phoneName;
  }
}
