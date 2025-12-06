/*
 * © HwaCom Systems Inc. 2015
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.c.shared;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 供 RMS 設備相關資料存放使用
 *
 * <ul>
 *   <li>定時查詢 87H RMS 設備監視
 *   <li>定時查詢 04HA0H 匝道管制警告標誌之顯示控制
 *   <li>主動回報 A7H 匝道管制號誌顯示狀態
 *   <li>主動回報 AEH 顯示訊息改變
 *   <li>重下機制的 counter
 * </ul>
 */
public class RmsDeviceStatusDTO implements Serializable {
  private static final long serialVersionUID = 172702341407142506L;

  /** 設備名稱 */
  private String deviceName;

  /** 儀控模式 */
  private RmsControlMode controlMode;

  /** 時制編號 */
  private Integer meteringPlanNo;

  /** 儀控率 */
  private Integer meteringRate;

  /** 號誌燈綠燈(橫排)狀態，0 表正常，1 表故障 */
  private List<Integer> greenLampErr;

  /** 號誌燈黃燈(橫排)狀態，0 表正常，1 表故障 */
  private List<Integer> yellowLampErr;

  /** 號誌燈紅燈(橫排)狀態，0 表正常，1 表故障 */
  private List<Integer> redLampErr;

  /** 號誌燈警勤燈狀態，0 表正常，1 表故障 */
  private List<Integer> policeLampErr;

  /** 號誌燈綠燈(直排)狀態，0 表正常，1 表故障 */
  private List<Integer> green2LampErr;

  /** 號誌燈黃燈(直排)狀態，0 表正常，1 表故障 */
  private List<Integer> yellow2LampErr;

  /** 號誌燈紅燈(直排)狀態，0 表正常，1 表故障 */
  private List<Integer> red2LampErr;

  /** 號誌燈倒數燈號狀態，0 表正常，1 表故障 */
  private List<Integer> countDownLampErr;
  /** BOS1 設備數目 */
  private Integer bos1Count;
  /** BOS2 設備數目 */
  private Integer bos2Count;
  /** BOS1-1 字窗驅動單元，0 表正常，1 表故障 */
  private List<Integer> bos1DriveErr;
  /** BOS1-2 字窗驅動單元，0 表正常，1 表故障 */
  private List<Integer> bos2DriveErr;
  /** BOS1-3 字窗驅動單元，0 表正常，1 表故障 */
  private List<Integer> bos3DriveErr;
  /** BOS1-4 字窗驅動單元，0 表正常，1 表故障 */
  private List<Integer> bos4DriveErr;
  /** BOS2-1 字窗驅動單元，0 表正常，1 表故障 */
  private List<Integer> bos5DriveErr;
  /** BOS2-2 字窗驅動單元，0 表正常，1 表故障 */
  private List<Integer> bos6DriveErr;
  /** BOS2-3 字窗驅動單元，0 表正常，1 表故障 */
  private List<Integer> bos7DriveErr;
  /** BOS2-4 字窗驅動單元，0 表正常，1 表故障 */
  private List<Integer> bos8DriveErr;
  /** BOS1-1 號誌亮滅 0:熄滅 1:恆亮 2:閃爍 */
  private Integer bos1Status;
  /** BOS1-1 訊息編號 */
  private Integer bos1MessageId;
  /** BOS1-1 訊息 */
  private String bos1MessageText;
  /** BOS1-1 顯示符號 */
  private BosDisplaySign bos1MessageSign;
  /** BOS1-2 號誌亮滅 0:熄滅 1:恆亮 2:閃爍 */
  private Integer bos2Status;
  /** BOS1-2 訊息編號 */
  private Integer bos2MessageId;
  /** BOS1-2 訊息 */
  private String bos2MessageText;
  /** BOS1-2 顯示符號 */
  private BosDisplaySign bos2MessageSign;
  /** BOS1-3 號誌亮滅 0:熄滅 1:恆亮 2:閃爍 */
  private Integer bos3Status;
  /** BOS1-3 訊息編號 */
  private Integer bos3MessageId;
  /** BOS1-3 訊息 */
  private String bos3MessageText;
  /** BOS1-3 顯示符號 */
  private BosDisplaySign bos3MessageSign;
  /** BOS1-4 號誌亮滅 0:熄滅 1:恆亮 2:閃爍 */
  private Integer bos4Status;
  /** BOS1-4 訊息編號 */
  private Integer bos4MessageId;
  /** BOS1-4 訊息 */
  private String bos4MessageText;
  /** BOS1-4 顯示符號 */
  private BosDisplaySign bos4MessageSign;
  /** BOS2-1 號誌亮滅 0:熄滅 1:恆亮 2:閃爍 */
  private Integer bos5Status;
  /** BOS2-1 訊息編號 */
  private Integer bos5MessageId;
  /** BOS2-1 訊息 */
  private String bos5MessageText;
  /** BOS2-1 顯示符號 */
  private BosDisplaySign bos5MessageSign;
  /** BOS2-2 號誌亮滅 0:熄滅 1:恆亮 2:閃爍 */
  private Integer bos6Status;
  /** BOS2-2 訊息編號 */
  private Integer bos6MessageId;
  /** BOS2-2 訊息 */
  private String bos6MessageText;
  /** BOS2-2 顯示符號 */
  private BosDisplaySign bos6MessageSign;
  /** BOS2-3 號誌亮滅 0:熄滅 1:恆亮 2:閃爍 */
  private Integer bos7Status;
  /** BOS2-3 訊息編號 */
  private Integer bos7MessageId;
  /** BOS2-3 訊息 */
  private String bos7MessageText;
  /** BOS2-3 顯示符號 */
  private BosDisplaySign bos7MessageSign;
  /** BOS2-4 號誌亮滅 0:熄滅 1:恆亮 2:閃爍 */
  private Integer bos8Status;
  /** BOS2-4 訊息編號 */
  private Integer bos8MessageId;
  /** BOS2-4 訊息 */
  private String bos8MessageText;
  /** BOS2-4 顯示符號 */
  private BosDisplaySign bos8MessageSign;

  /** 每次更新 上一次硬體狀態讀取時間 */
  private Date lastUpdateTime;

  /** 下載指令後，要過三分鐘才檢查 */
  private Integer skipCheckCount;

  /** 比對不符五次後不再下載 */
  private Integer mismatchCount;

  public String getDeviceName() {
    return deviceName;
  }

  public void setDeviceName(String deviceName) {
    this.deviceName = deviceName;
  }

  public RmsControlMode getControlMode() {
    return controlMode;
  }

  public void setControlMode(RmsControlMode controlMode) {
    this.controlMode = controlMode;
  }

  public Integer getMeteringPlanNo() {
    return meteringPlanNo;
  }

  public void setMeteringPlanNo(Integer meteringPlanNo) {
    this.meteringPlanNo = meteringPlanNo;
  }

  public Integer getMeteringRate() {
    return meteringRate;
  }

  public void setMeteringRate(Integer meteringRate) {
    this.meteringRate = meteringRate;
  }

  public List<Integer> getGreenLampErr() {
    return greenLampErr;
  }

  public void setGreenLampErr(List<Integer> greenLampErr) {
    this.greenLampErr = greenLampErr;
  }

  public List<Integer> getYellowLampErr() {
    return yellowLampErr;
  }

  public void setYellowLampErr(List<Integer> yellowLampErr) {
    this.yellowLampErr = yellowLampErr;
  }

  public List<Integer> getRedLampErr() {
    return redLampErr;
  }

  public void setRedLampErr(List<Integer> redLampErr) {
    this.redLampErr = redLampErr;
  }

  public List<Integer> getPoliceLampErr() {
    return policeLampErr;
  }

  public void setPoliceLampErr(List<Integer> policeLampErr) {
    this.policeLampErr = policeLampErr;
  }

  public List<Integer> getGreen2LampErr() {
    return green2LampErr;
  }

  public void setGreen2LampErr(List<Integer> green2LampErr) {
    this.green2LampErr = green2LampErr;
  }

  public List<Integer> getYellow2LampErr() {
    return yellow2LampErr;
  }

  public void setYellow2LampErr(List<Integer> yellow2LampErr) {
    this.yellow2LampErr = yellow2LampErr;
  }

  public List<Integer> getRed2LampErr() {
    return red2LampErr;
  }

  public void setRed2LampErr(List<Integer> red2LampErr) {
    this.red2LampErr = red2LampErr;
  }

  public List<Integer> getCountDownLampErr() {
    return countDownLampErr;
  }

  public void setCountDownLampErr(List<Integer> countDownLampErr) {
    this.countDownLampErr = countDownLampErr;
  }

  public Integer getBos1Count() {
    return bos1Count;
  }

  public void setBos1Count(Integer bos1Count) {
    this.bos1Count = bos1Count;
  }

  public Integer getBos2Count() {
    return bos2Count;
  }

  public void setBos2Count(Integer bos2Count) {
    this.bos2Count = bos2Count;
  }

  public List<Integer> getBos1DriveErr() {
    return bos1DriveErr;
  }

  public void setBos1DriveErr(List<Integer> bos1DriveErr) {
    this.bos1DriveErr = bos1DriveErr;
  }

  public List<Integer> getBos2DriveErr() {
    return bos2DriveErr;
  }

  public void setBos2DriveErr(List<Integer> bos2DriveErr) {
    this.bos2DriveErr = bos2DriveErr;
  }

  public List<Integer> getBos3DriveErr() {
    return bos3DriveErr;
  }

  public void setBos3DriveErr(List<Integer> bos3DriveErr) {
    this.bos3DriveErr = bos3DriveErr;
  }

  public List<Integer> getBos4DriveErr() {
    return bos4DriveErr;
  }

  public void setBos4DriveErr(List<Integer> bos4DriveErr) {
    this.bos4DriveErr = bos4DriveErr;
  }

  public List<Integer> getBos5DriveErr() {
    return bos5DriveErr;
  }

  public void setBos5DriveErr(List<Integer> bos5DriveErr) {
    this.bos5DriveErr = bos5DriveErr;
  }

  public List<Integer> getBos6DriveErr() {
    return bos6DriveErr;
  }

  public void setBos6DriveErr(List<Integer> bos6DriveErr) {
    this.bos6DriveErr = bos6DriveErr;
  }

  public List<Integer> getBos7DriveErr() {
    return bos7DriveErr;
  }

  public void setBos7DriveErr(List<Integer> bos7DriveErr) {
    this.bos7DriveErr = bos7DriveErr;
  }

  public List<Integer> getBos8DriveErr() {
    return bos8DriveErr;
  }

  public void setBos8DriveErr(List<Integer> bos8DriveErr) {
    this.bos8DriveErr = bos8DriveErr;
  }

  public Integer getBos1Status() {
    return bos1Status;
  }

  public void setBos1Status(Integer bos1Status) {
    this.bos1Status = bos1Status;
  }

  public Integer getBos1MessageId() {
    return bos1MessageId;
  }

  public void setBos1MessageId(Integer bos1MessageId) {
    this.bos1MessageId = bos1MessageId;
  }

  public Integer getBos2Status() {
    return bos2Status;
  }

  public void setBos2Status(Integer bos2Status) {
    this.bos2Status = bos2Status;
  }

  public Integer getBos2MessageId() {
    return bos2MessageId;
  }

  public void setBos2MessageId(Integer bos2MessageId) {
    this.bos2MessageId = bos2MessageId;
  }

  public Integer getBos3Status() {
    return bos3Status;
  }

  public void setBos3Status(Integer bos3Status) {
    this.bos3Status = bos3Status;
  }

  public Integer getBos3MessageId() {
    return bos3MessageId;
  }

  public void setBos3MessageId(Integer bos3MessageId) {
    this.bos3MessageId = bos3MessageId;
  }

  public Integer getBos4Status() {
    return bos4Status;
  }

  public void setBos4Status(Integer bos4Status) {
    this.bos4Status = bos4Status;
  }

  public Integer getBos4MessageId() {
    return bos4MessageId;
  }

  public void setBos4MessageId(Integer bos4MessageId) {
    this.bos4MessageId = bos4MessageId;
  }

  public Integer getBos5Status() {
    return bos5Status;
  }

  public void setBos5Status(Integer bos5Status) {
    this.bos5Status = bos5Status;
  }

  public Integer getBos5MessageId() {
    return bos5MessageId;
  }

  public void setBos5MessageId(Integer bos5MessageId) {
    this.bos5MessageId = bos5MessageId;
  }

  public Integer getBos6Status() {
    return bos6Status;
  }

  public void setBos6Status(Integer bos6Status) {
    this.bos6Status = bos6Status;
  }

  public Integer getBos6MessageId() {
    return bos6MessageId;
  }

  public void setBos6MessageId(Integer bos6MessageId) {
    this.bos6MessageId = bos6MessageId;
  }

  public Integer getBos7Status() {
    return bos7Status;
  }

  public void setBos7Status(Integer bos7Status) {
    this.bos7Status = bos7Status;
  }

  public Integer getBos7MessageId() {
    return bos7MessageId;
  }

  public void setBos7MessageId(Integer bos7MessageId) {
    this.bos7MessageId = bos7MessageId;
  }

  public Integer getBos8Status() {
    return bos8Status;
  }

  public void setBos8Status(Integer bos8Status) {
    this.bos8Status = bos8Status;
  }

  public Integer getBos8MessageId() {
    return bos8MessageId;
  }

  public void setBos8MessageId(Integer bos8MessageId) {
    this.bos8MessageId = bos8MessageId;
  }

  public Date getLastUpdateTime() {
    return lastUpdateTime;
  }

  public void setLastUpdateTime(Date lastUpdateTime) {
    this.lastUpdateTime = lastUpdateTime;
  }

  public Integer getSkipCheckCount() {
    return skipCheckCount;
  }

  public void setSkipCheckCount(Integer skipCheckCount) {
    this.skipCheckCount = skipCheckCount;
  }

  public Integer getMismatchCount() {
    return mismatchCount;
  }

  public void setMismatchCount(Integer mismatchCount) {
    this.mismatchCount = mismatchCount;
  }

  public String getBos1MessageText() {
    return bos1MessageText;
  }

  public void setBos1MessageText(String bos1MessageText) {
    this.bos1MessageText = bos1MessageText;
  }

  public BosDisplaySign getBos1MessageSign() {
    return bos1MessageSign;
  }

  public void setBos1MessageSign(BosDisplaySign bos1MessageSign) {
    this.bos1MessageSign = bos1MessageSign;
  }

  public String getBos2MessageText() {
    return bos2MessageText;
  }

  public void setBos2MessageText(String bos2MessageText) {
    this.bos2MessageText = bos2MessageText;
  }

  public BosDisplaySign getBos2MessageSign() {
    return bos2MessageSign;
  }

  public void setBos2MessageSign(BosDisplaySign bos2MessageSign) {
    this.bos2MessageSign = bos2MessageSign;
  }

  public String getBos3MessageText() {
    return bos3MessageText;
  }

  public void setBos3MessageText(String bos3MessageText) {
    this.bos3MessageText = bos3MessageText;
  }

  public BosDisplaySign getBos3MessageSign() {
    return bos3MessageSign;
  }

  public void setBos3MessageSign(BosDisplaySign bos3MessageSign) {
    this.bos3MessageSign = bos3MessageSign;
  }

  public String getBos4MessageText() {
    return bos4MessageText;
  }

  public void setBos4MessageText(String bos4MessageText) {
    this.bos4MessageText = bos4MessageText;
  }

  public BosDisplaySign getBos4MessageSign() {
    return bos4MessageSign;
  }

  public void setBos4MessageSign(BosDisplaySign bos4MessageSign) {
    this.bos4MessageSign = bos4MessageSign;
  }

  public String getBos5MessageText() {
    return bos5MessageText;
  }

  public void setBos5MessageText(String bos5MessageText) {
    this.bos5MessageText = bos5MessageText;
  }

  public BosDisplaySign getBos5MessageSign() {
    return bos5MessageSign;
  }

  public void setBos5MessageSign(BosDisplaySign bos5MessageSign) {
    this.bos5MessageSign = bos5MessageSign;
  }

  public String getBos6MessageText() {
    return bos6MessageText;
  }

  public void setBos6MessageText(String bos6MessageText) {
    this.bos6MessageText = bos6MessageText;
  }

  public BosDisplaySign getBos6MessageSign() {
    return bos6MessageSign;
  }

  public void setBos6MessageSign(BosDisplaySign bos6MessageSign) {
    this.bos6MessageSign = bos6MessageSign;
  }

  public String getBos7MessageText() {
    return bos7MessageText;
  }

  public void setBos7MessageText(String bos7MessageText) {
    this.bos7MessageText = bos7MessageText;
  }

  public BosDisplaySign getBos7MessageSign() {
    return bos7MessageSign;
  }

  public void setBos7MessageSign(BosDisplaySign bos7MessageSign) {
    this.bos7MessageSign = bos7MessageSign;
  }

  public String getBos8MessageText() {
    return bos8MessageText;
  }

  public void setBos8MessageText(String bos8MessageText) {
    this.bos8MessageText = bos8MessageText;
  }

  public BosDisplaySign getBos8MessageSign() {
    return bos8MessageSign;
  }

  public void setBos8MessageSign(BosDisplaySign bos8MessageSign) {
    this.bos8MessageSign = bos8MessageSign;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    RmsDeviceStatusDTO that = (RmsDeviceStatusDTO) o;
    return deviceName != null
        ? deviceName.equals(that.getDeviceName())
        : that.getDeviceName() == null;
  }

  @Override
  public int hashCode() {
    return deviceName != null ? deviceName.hashCode() : 0;
  }
}
