/*
 * © HwaCom Systems Inc. 2015
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.room.shared;

import com.hwacom.ngtms.base.i18n.shared.MessageType;

/** 門禁管制卡種類 */
public enum CardType implements MessageType {
  /** 定期卡 */
  REGULAR,
  /** 臨時卡 */
  TEMPORARY,
  /** 無限卡，可進入任何機房 */
  UNLIMITE,
  /** 虛擬卡，緊急狀況時間內給通行碼 */
  VIRTUAL;

  @Override
  public String getMessageKeyPrefix() {
    return "roomFm.cardType";
  }
}
