/*
 * © HwaCom Systems Inc. 2015
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.c.shared;

import static com.google.common.base.Preconditions.checkArgument;

import com.google.common.collect.ImmutableMap.Builder;
import com.hwacom.ngtms.base.i18n.shared.MessageType;
import java.util.Map;

/**
 * 儀控模式
 *
 * @author JtsayLin
 */
public enum RmsControlMode implements MessageType {
  /** 0.固定時制 */
  FixedMetering(0),

  /** 1.區域交通反應 */
  LocalTrafficReact(1),

  /** 2.預設時制 */
  Default(2),

  /** 3.匝道關閉 */
  RampClose(3),

  /** 4.整合式交通反應 */
  IntegratedTrafficReact(4),

  /** 5.手動操作 */
  Manual(5),

  /** 6.儀控終止 */
  StopMetering(6),

  /** 7.中心區域交通反應 */
  CentralLocalTrafficReact(7),

  /** 99. 取消手動 */
  CancelManual(99),
  ;

  private static final Map<Integer, com.hwacom.ngtms.c.shared.RmsControlMode> PROTOCOL_CODE_MAPPING;

  static {
    Builder<Integer, com.hwacom.ngtms.c.shared.RmsControlMode> builder = new Builder<>();
    for (com.hwacom.ngtms.c.shared.RmsControlMode mode :
        com.hwacom.ngtms.c.shared.RmsControlMode.values()) {
      builder.put(mode.getProtocolCode(), mode);
    }

    PROTOCOL_CODE_MAPPING = builder.build();
  }

  public static com.hwacom.ngtms.c.shared.RmsControlMode fromProtocolCode(int protocolCode) {
    com.hwacom.ngtms.c.shared.RmsControlMode mode = PROTOCOL_CODE_MAPPING.get(protocolCode);
    checkArgument(mode != null, "Undefined control mode protocol code [%s]", protocolCode);

    return mode;
  }

  private Integer protocolCode;

  RmsControlMode(Integer protocolCode) {
    this.protocolCode = protocolCode;
  }

  public Integer getProtocolCode() {
    return protocolCode;
  }

  @Override
  public String getMessageKeyPrefix() {
    return "rmsFm.ControlMode";
  }
}
