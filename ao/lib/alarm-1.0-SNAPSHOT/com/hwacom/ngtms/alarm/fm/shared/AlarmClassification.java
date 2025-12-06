/*
 * © HwaCom Systems Inc. 2017
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.alarm.fm.shared;

import com.hwacom.ngtms.base.i18n.shared.MessageType;

public enum AlarmClassification implements MessageType {
  /** 事件類型 */
  EVENT("事件類型"),
  /** 警告類型 */
  WARNING("警告類型"),
  /** 恢復正常 */
  NORMAL("恢復正常");

  private String desc;

  private AlarmClassification(String desc) {
    this.desc = desc;
  }

  public String getDesc() {
    return desc;
  }

  @Override
  public String getMessageKeyPrefix() {
    return "alarm.AlarmClassification";
  }
}
