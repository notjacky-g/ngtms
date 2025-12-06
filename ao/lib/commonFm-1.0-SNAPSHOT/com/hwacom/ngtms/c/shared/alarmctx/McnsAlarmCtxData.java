/*
 * © HwaCom Systems Inc. 2015
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.c.shared.alarmctx;

import com.hwacom.ngtms.c.shared.AlarmContextData;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class McnsAlarmCtxData implements AlarmContextData {
  private static final String FORMAT = "yyyy-MM-dd HH:mm:ss";
  /** 移動性施工編號 */
  private String constructionNo;
  /** 移動性施工通報者 */
  private String notifier;
  /** 移動性施工阻斷型態編號 */
  private String blockTypeId;
  /** 移動性施工 阻斷車道數量與位置 (用 8 bit 表示) */
  private String blockLaneMark;
  /** 是否啟動反應計畫 */
  private Boolean isCMS;
  /** 施工開始時間 */
  private Date startTime;
  /** 施工結束時間 */
  private Date endTime;
  /** 移動性施工備註 */
  private String memo;

  private SimpleDateFormat sdf = new SimpleDateFormat(FORMAT);

  public String getConstructionNo() {
    return constructionNo;
  }

  public void setConstructionNo(String constructionNo) {
    this.constructionNo = constructionNo;
  }

  public String getNotifier() {
    return notifier;
  }

  public void setNotifier(String notifier) {
    this.notifier = notifier;
  }

  public String getBlockTypeId() {
    return blockTypeId;
  }

  public void setBlockTypeId(String blockTypeId) {
    this.blockTypeId = blockTypeId;
  }

  public String getBlockLaneMark() {
    return blockLaneMark;
  }

  public void setBlockLaneMark(String blockLaneMark) {
    this.blockLaneMark = blockLaneMark;
  }

  @Override
  public String toString() {
    return "McnsAlarmCtxData [constructionNo="
        + constructionNo
        + ", notifier="
        + notifier
        + ", blockTypeId="
        + blockTypeId
        + ", blockLaneMark="
        + blockLaneMark
        + ", isCMS="
        + isCMS
        + ", startTime="
        + startTime
        + ", endTime="
        + endTime
        + "]";
  }

  @Override
  public void decode(String strData) {
    if (strData != null) {
      String[] parts = strData.split(";");
      if ((parts.length > 0) && (parts[0].length() > 0)) constructionNo = parts[0];
      if ((parts.length > 1) && (parts[1].length() > 0)) notifier = parts[1];
      if ((parts.length > 2) && (parts[2].length() > 0)) blockTypeId = parts[2];
      if ((parts.length > 3) && (parts[3].length() > 0)) blockLaneMark = parts[3];
      if ((parts.length > 4) && (parts[4].length() > 0))
        isCMS = ("Y".equalsIgnoreCase(parts[4])) ? true : false;
      if ((parts.length > 5) && (parts[5].length() > 0)) {
        try {
          startTime = sdf.parse(parts[5]);
        } catch (ParseException e) {

        }
      }
      if ((parts.length > 6) && (parts[6].length() > 0)) {
        try {
          endTime = sdf.parse(parts[6]);
        } catch (ParseException e) {

        }
      }
      if ((parts.length > 7) && (parts[7].length() > 0)) memo = parts[7];
    }
  }

  @Override
  public String encode() {
    StringBuilder sb = new StringBuilder();
    if (constructionNo != null) sb.append(constructionNo);
    sb.append(';');
    if (notifier != null) sb.append(notifier);
    sb.append(';');
    if (blockTypeId != null) sb.append(blockTypeId);
    sb.append(';');
    if (blockLaneMark != null) sb.append(blockLaneMark);
    sb.append(';');
    if (isCMS != null) sb.append(isCMS ? "Y" : "N");
    sb.append(';');
    if (startTime != null) sb.append(sdf.format(startTime));
    sb.append(';');
    if (endTime != null) sb.append(sdf.format(endTime));
    sb.append(';');
    if (memo != null) sb.append(memo);
    sb.append(';');
    return sb.toString();
  }

  public Boolean getIsCMS() {
    return isCMS;
  }

  public void setIsCMS(Boolean isCMS) {
    this.isCMS = isCMS;
  }

  public String getMemo() {
    return memo;
  }

  public void setMemo(String memo) {
    this.memo = memo;
  }

  public Date getStartTime() {
    return startTime;
  }

  public void setStartTime(Date startTime) {
    this.startTime = startTime;
  }

  public Date getEndTime() {
    return endTime;
  }

  public void setEndTime(Date endTime) {
    this.endTime = endTime;
  }
}
