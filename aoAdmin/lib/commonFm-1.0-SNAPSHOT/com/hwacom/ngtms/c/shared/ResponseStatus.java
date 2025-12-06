/*
 * © HwaCom Systems Inc. 2013
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.c.shared;

public enum ResponseStatus {
  /**
   * Request 未成功執行<br>
   * <br>
   * 如果是與 TC 溝通的狀況，則來自 NccHelper 放置 request 到 Hazelcast queue 失敗<br>
   * 或是在放置的過程中出現 exception
   */
  NOT_SENT,

  /**
   * 尚未收到 response<br>
   * <br>
   * 如果是與 TC 溝通的狀況，則來自 NccHelper 放置 request 到 Hazelcast queue 成功<br>
   * 等待 device 回傳訊息
   */
  PENDING,

  /**
   * Request 執行成功<br>
   * <br>
   * 如果是與 TC 溝通的狀況，則來自 device 回傳訊息成功
   */
  ACK,

  /**
   * Request 執行異常<br>
   * <br>
   * 如果是與 TC 溝通的狀況，則來自 device 回傳訊息異常
   */
  NAK,

  /**
   * Request 執行逾時<br>
   * <br>
   * 如果是與 TC 溝通的狀況，則來自 NccHelper 等到 timeout 尚未收到 device 回傳訊息
   */
  TIMEOUT;
}
