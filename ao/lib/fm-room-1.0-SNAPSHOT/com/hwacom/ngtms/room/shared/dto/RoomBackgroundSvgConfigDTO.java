/*
 * © HwaCom Systems Inc. 2015
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.room.shared.dto;

import java.io.Serializable;

public class RoomBackgroundSvgConfigDTO implements Serializable {

  private static final long serialVersionUID = 246665475545612377L;

  /** 底圖名稱,各機房底圖名稱不會有重複的 */
  private String nameId;

  /** 底圖中文描述名稱 */
  private String description;

  public String getNameId() {
    return nameId;
  }

  public void setNameId(String nameId) {
    this.nameId = nameId;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((nameId == null) ? 0 : nameId.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (getClass() != obj.getClass()) return false;
    RoomBackgroundSvgConfigDTO other = (RoomBackgroundSvgConfigDTO) obj;
    if (nameId == null) {
      if (other.nameId != null) return false;
    } else if (!nameId.equals(other.nameId)) return false;
    return true;
  }

  @Override
  public String toString() {
    return "RoomBackgroundSvgConfigDTO [nameId=" + nameId + ", description=" + description + "]";
  }
}
