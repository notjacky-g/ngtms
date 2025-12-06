package com.hwacom.ngtms.ao.shared.dto;

import com.hwacom.ngtms.room.shared.EventCode;
import java.io.Serializable;

public class EventCodeDTO implements Serializable {

  private static final long serialVersionUID = 9046246995371719389L;

  private String id;

  private EventCode eventCode;

  private String description;

  private boolean all;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public EventCode getEventCode() {
    return eventCode;
  }

  public void setEventCode(EventCode eventCode) {
    this.eventCode = eventCode;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public boolean isAll() {
    return all;
  }

  public void setAll(boolean all) {
    this.all = all;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (getClass() != obj.getClass()) return false;
    EventCodeDTO other = (EventCodeDTO) obj;
    if (id == null) {
      if (other.id != null) return false;
    } else if (!id.equals(other.id)) return false;
    return true;
  }

  @Override
  public String toString() {
    return "EventCodeDTO [id="
        + id
        + ", eventCode="
        + eventCode
        + ", description="
        + description
        + ", all="
        + all
        + "]";
  }
}
