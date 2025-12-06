/*
 * © HwaCom Systems Inc. 2015
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.c.shared;

import java.io.Serializable;

/** @author bryan.lin */
public class EventAlarmContextData implements Serializable {

  private static final long serialVersionUID = 1L;

  private String eventId;

  private String alarmContextData;

  @Override
  public int hashCode() {
    int hash = 0;
    hash += (eventId != null ? eventId.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    if (!(object instanceof EventAlarmContextData)) {
      return false;
    }
    EventAlarmContextData other = (EventAlarmContextData) object;
    if ((this.eventId == null && other.eventId != null)
        || (this.eventId != null && !this.eventId.equals(other.eventId))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "EventAlarmContextData [eventId="
        + eventId
        + ", alarmContextData="
        + alarmContextData
        + "]";
  }

  /** @return the eventId */
  public String getEventId() {
    return eventId;
  }

  /** @param eventId the eventId to set */
  public void setEventId(String eventId) {
    this.eventId = eventId;
  }

  /** @return the alarmContextData */
  public String getAlarmContextData() {
    return alarmContextData;
  }

  /** @param alarmContextData the alarmContextData to set */
  public void setAlarmContextData(String alarmContextData) {
    this.alarmContextData = alarmContextData;
  }
}
