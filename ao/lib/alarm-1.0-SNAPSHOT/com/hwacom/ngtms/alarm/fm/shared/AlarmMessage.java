/*
 * © HwaCom Systems Inc. 2015
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.alarm.fm.shared;

import com.hwacom.ngtms.alarm.fm.model.AlarmLog;
import java.io.Serializable;
import java.util.List;

/**
 * 告警訊息,用於發送 mail,手機簡訊,Line
 *
 * @author BrianCheng
 */
public class AlarmMessage implements Serializable {

  private static final long serialVersionUID = -1301730357124444036L;

  /** mail帳號 */
  private List<String> mails;

  /** 手機號碼 */
  private List<String> mobiles;

  /** Line Token */
  private List<String> lineTokens;

  /** 主旨 */
  private String title;

  /** 告警內容 */
  private AlarmLog alarmLog;

  public List<String> getMails() {
    return mails;
  }

  public void setMails(List<String> mails) {
    this.mails = mails;
  }

  public List<String> getMobiles() {
    return mobiles;
  }

  public void setMobiles(List<String> mobiles) {
    this.mobiles = mobiles;
  }

  public List<String> getLineTokens() {
    return lineTokens;
  }

  public void setLineTokens(List<String> lineTokens) {
    this.lineTokens = lineTokens;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public AlarmLog getAlarmLog() {
    return alarmLog;
  }

  public void setAlarmLog(AlarmLog alarmLog) {
    this.alarmLog = alarmLog;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((alarmLog == null) ? 0 : alarmLog.hashCode());
    result = prime * result + ((lineTokens == null) ? 0 : lineTokens.hashCode());
    result = prime * result + ((mails == null) ? 0 : mails.hashCode());
    result = prime * result + ((mobiles == null) ? 0 : mobiles.hashCode());
    result = prime * result + ((title == null) ? 0 : title.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (getClass() != obj.getClass()) return false;
    AlarmMessage other = (AlarmMessage) obj;
    if (alarmLog == null) {
      if (other.alarmLog != null) return false;
    } else if (!alarmLog.equals(other.alarmLog)) return false;
    if (lineTokens == null) {
      if (other.lineTokens != null) return false;
    } else if (!lineTokens.equals(other.lineTokens)) return false;
    if (mails == null) {
      if (other.mails != null) return false;
    } else if (!mails.equals(other.mails)) return false;
    if (mobiles == null) {
      if (other.mobiles != null) return false;
    } else if (!mobiles.equals(other.mobiles)) return false;
    if (title == null) {
      if (other.title != null) return false;
    } else if (!title.equals(other.title)) return false;
    return true;
  }

  @Override
  public String toString() {
    return "AlarmMessage [mails="
        + mails
        + ", mobiles="
        + mobiles
        + ", lineTokens="
        + lineTokens
        + ", title="
        + title
        + ", alarmLog="
        + alarmLog
        + "]";
  }
}
