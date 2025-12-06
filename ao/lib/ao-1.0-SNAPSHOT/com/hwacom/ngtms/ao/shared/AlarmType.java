package com.hwacom.ngtms.ao.shared;

import java.util.Optional;

/** 機房告警類別 */
public enum AlarmType {
  ALL("全部", Optional.empty()),
  UPPER_LIMIT("超過上限值", Optional.empty()),
  LOWER_LIMIT("低於下限值", Optional.empty()),
  ABNORMAL("訊號異常", Optional.empty()),
  ROOM_OPEN_FAIL("機房開啟失敗", Optional.empty()),
  IDENTIFY_FAIL("人臉辨識失敗", Optional.empty()),
  DOOR_OPEN("門位開啟", Optional.empty()),
  SOUND_LIGHT_START("偵測非法入侵", Optional.empty()),
  SECURITY_RELEASE("保全解除", Optional.empty()),
  NCU_OFFLINE("NCU主機斷線", Optional.empty()),
  RTU_OFFLINE("RTU設備斷線", Optional.empty()),
  PD_OFFLINE("PD設備斷線", Optional.empty()),
  PD_BOX_OPEN("PD箱門開啟", Optional.empty()),
  PRIMARY_ABNORMAL_R("一次側(R)異常", Optional.empty()),
  PRIMARY_ABNORMAL_S("一次側(S)異常", Optional.empty()),
  PRIMARY_ABNORMAL_T("一次側(T)異常", Optional.empty()),
  SECONDARY_ABNORMAL_R("二次側(R)異常", Optional.empty()),
  SECONDARY_ABNORMAL_S("二次側(S)異常", Optional.empty()),
  SECONDARY_ABNORMAL_T("二次側(T)異常", Optional.empty()),
  BRANCH_CIRCUIT_ABNORMAL_1("分迴路(1)異常", Optional.empty()),
  BRANCH_CIRCUIT_ABNORMAL_2("分迴路(2)異常", Optional.empty()),
  BRANCH_CIRCUIT_ABNORMAL_3("分迴路(3)異常", Optional.empty()),
  BRANCH_CIRCUIT_ABNORMAL_4("分迴路(4)異常", Optional.empty()),
  BRANCH_CIRCUIT_ABNORMAL_5("分迴路(5)異常", Optional.empty());

  private String name;

  private Optional<String> optAlarmThresholdUnit;

  AlarmType(String name, Optional<String> optAlarmThresholdUnit) {
    this.name = name;
    this.optAlarmThresholdUnit = optAlarmThresholdUnit;
  }

  public String getName() {
    return name;
  }

  public Optional<String> getAlarmThresholdUnit() {
    return optAlarmThresholdUnit;
  }

  public boolean isAlarmThresholdAvailable() {
    return optAlarmThresholdUnit.isPresent();
  }
}
