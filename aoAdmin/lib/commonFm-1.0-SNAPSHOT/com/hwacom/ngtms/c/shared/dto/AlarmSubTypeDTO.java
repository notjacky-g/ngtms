/*
 * © HwaCom Systems Inc. 2015
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.c.shared.dto;

import com.google.gwt.user.client.rpc.IsSerializable;
import com.hwacom.ngtms.c.shared.EventExeMode;
import java.io.Serializable;

public class AlarmSubTypeDTO implements Serializable, IsSerializable {

  private static final long serialVersionUID = 8084835694724294469L;

  private Integer id;

  private String name;

  private String alarmTypeId;

  private EventExeMode exeMode;

  public AlarmSubTypeDTO() {}

  public AlarmSubTypeDTO(Integer id, String name) {
    super();
    this.id = id;
    this.name = name;
  }

  public AlarmSubTypeDTO(Integer id, String name, String alarmTypeId) {
    super();
    this.id = id;
    this.name = name;
    this.alarmTypeId = alarmTypeId;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getAlarmTypeId() {
    return alarmTypeId;
  }

  public void setAlarmTypeId(String alarmTypeId) {
    this.alarmTypeId = alarmTypeId;
  }

  public EventExeMode getExeMode() {
    return exeMode;
  }

  public void setExeMode(EventExeMode exeMode) {
    this.exeMode = exeMode;
  }

  @Override
  public int hashCode() {
    int hash = 0;
    hash += (id != null ? id.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    if (!(object instanceof AlarmSubTypeDTO)) {
      return false;
    }
    AlarmSubTypeDTO other = (AlarmSubTypeDTO) object;
    if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "AlarmSubTypeDTO [id=" + id + "]";
  }
}
