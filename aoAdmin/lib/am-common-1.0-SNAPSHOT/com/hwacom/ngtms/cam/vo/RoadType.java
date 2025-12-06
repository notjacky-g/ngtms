/*
 * © HwaCom Systems Inc. 2015
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.cam.vo;

import com.google.gwt.core.client.GWT;
import com.hwacom.ngtms.cam.view.Messages;

/** RoadType.L=路線; RoadType.C=系統交流道; RoadType.I=交流道; RoadType.S=服務區; RoadType.T=收費站 */
public enum RoadType {
  L,
  C,
  I,
  S;
  //T; 暫時不顯示,等確認需要顯示時在打開

  private static final Messages messages = GWT.create(Messages.class);

  public static String getName(RoadType roadType) {
    switch (roadType) {
      case C:
        return messages.RoadType_C();
      case I:
        return messages.RoadType_I();
      case L:
        return messages.RoadType_L();
      case S:
        return messages.RoadType_S();
        //case T:
        //    return messages.RoadType_T();
      default:
        return null;
    }
  }
}
