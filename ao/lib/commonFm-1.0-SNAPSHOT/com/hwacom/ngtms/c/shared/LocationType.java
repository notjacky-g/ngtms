package com.hwacom.ngtms.c.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

public enum LocationType implements IsSerializable {
  /** 高速公路 */
  F,
  /** 快速道路 */
  H,
  /** 隧道 */
  T,
  /** 匝道 */
  R,
  /** 保留 */
  P,
  /** 服務區 */
  S,
  /** 地方道路 */
  L,
  /** 未知區域 */
  I,
  /** 人車行聯絡道 */
  CT,
  /** 改道點 */
  D,
  /** 隧道口 */
  TE;
}
