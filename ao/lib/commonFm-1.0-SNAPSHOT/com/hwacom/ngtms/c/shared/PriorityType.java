/*
 * © HwaCom Systems Inc. 2015
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.c.shared;

/**
 * 顯示優先權的設計： 強制手動 > 反應計畫 > 一般手動 > 排程 > 自動演算 forced to manually> response plan> General Manual>
 * Schedule> automatic calculation
 */
public enum PriorityType {

  /** 強制手動 */
  ManualOverride(1000),

  /** 反應計畫 */
  ResponsePlan(2000),

  /** 一般手動 */
  Manual(3000),

  /** 排程 */
  Schedule(4000),

  /** 自動演算 (旅行時間) */
  AutomaticCalculation(5000);

  private int priority;

  PriorityType(int priority) {
    this.priority = priority;
  }

  public int getPriority() {
    return priority;
  }
}
