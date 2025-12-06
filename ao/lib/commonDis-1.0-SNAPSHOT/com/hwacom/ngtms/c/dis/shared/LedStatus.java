/*
 * © HwaCom Systems Inc. 2015
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.c.dis.shared;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import java.io.Serializable;
import java.util.Date;

/**
 * 供AM顯示LED測試結果<br>
 * LED長寬換算方式:<br>
 * 2X8 cms面板 , ledModule 64<br>
 * 燈泡數為128 X 512 ((2*64) X (8*64))<br>
 * 換成LED板長寬=8X32 ((128/16) X (512/16))<br>
 * TODO 目前不確定 各TC回傳結果座標是從(0,0)開始還是(0,1)開始<br>
 * 可使用LedHelper 換算面板寬度含幾個LED版單位<br>
 *
 * @see com.hwacom.ngtms.c.dis.shared.LedHelper
 */
public class LedStatus implements Serializable {

  /** 一單位LED版所含燈泡數 預設為16*16 */
  public static final int LED_BITSET_COUNT = 16;

  /** 面板一個字所需要燈泡數 預設為64*64 */
  public static final int DEFAULT_LED_MODULE = 64;

  private static final long serialVersionUID = -8610964258034844035L;

  /** 設備名稱 */
  private String deviceName;

  /** 記錄時間 */
  private Date dataTime;

  /** 有顯示點故障之模組數 */
  private Integer badLedNo = 0;

  /** LED寬度(實際bitLed/設備一單位LED版所含燈泡數) 實際bitLed數目=面板高*一個字所需要燈泡數 */
  private int ledWidth;

  /**
   * LED高度(實際bitLed/設備一單位LED版所含燈泡數)
   *
   * <p>實際bitLed數目=面板寬*一個字所需要燈泡數
   */
  private int ledHeight;

  /** LED 損壞座標矩陣 Key : 故障模組之橫軸標，整數 value :故障模組之縱軸標，整數 */
  private Multimap<Integer, Integer> badLedMultimap = HashMultimap.create();

  public String getDeviceName() {
    return deviceName;
  }

  public void setDeviceName(String deviceName) {
    this.deviceName = deviceName;
  }

  /** LED 損壞座標矩陣 Key : 故障模組之橫軸標，整數 value :故障模組之縱軸標，整數 */
  public Multimap<Integer, Integer> getBadLedMultimap() {
    return badLedMultimap;
  }

  public void setBadLedMultimap(Multimap<Integer, Integer> badLedMultimap) {
    this.badLedMultimap = badLedMultimap;
  }

  public Date getDataTime() {
    return (Date) dataTime.clone();
  }

  public void setDataTime(Date dataTime) {
    this.dataTime = (Date) dataTime.clone();
  }

  public Integer getBadLedNo() {
    return badLedNo;
  }

  public void setBadLedNo(Integer badLedNo) {
    this.badLedNo = badLedNo;
  }

  public int getLedWidth() {
    return ledWidth;
  }

  public void setLedWidth(int ledWidth) {
    this.ledWidth = ledWidth;
  }

  public int getLedHeight() {
    return ledHeight;
  }

  public void setLedHeight(int ledHeight) {
    this.ledHeight = ledHeight;
  }
}
