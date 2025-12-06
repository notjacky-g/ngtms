package com.hwacom.ngtms.room.shared;

/** 門禁卡卡片狀態 */
public enum CardStatus {
  /** 啟用 */
  ENABLE,
  /** 停用 */
  DISABLE,
  /** 已歸還 */
  RETURN,
  /** 遺失 */
  LOST,
  /** 黑名單 */
  BLACKLIST,
  /** 註銷，無法再做修改 */
  LOGOUT;
}
