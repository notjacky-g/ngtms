/*
 * © HwaCom Systems Inc. 2015
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.c.shared.dto;

import com.google.gwt.user.client.rpc.IsSerializable;
import java.io.Serializable;

public class RampVdTypeDTO implements Serializable, IsSerializable {

  /** */
  private static final long serialVersionUID = -2358199097750823641L;

  private Integer id;

  private String name;

  /** 該類別之說明 */
  private String memo;

  /** 總匝道口有無 VD */
  private Boolean vdFather;

  /** 匝道口1有無 VD */
  private Boolean vdChild1;

  /** 匝道口2有無 VD */
  private Boolean vdChild2;

  /** @return the id */
  public Integer getId() {
    return id;
  }

  /** @param id the id to set */
  public void setId(Integer id) {
    this.id = id;
  }

  /** @return the name */
  public String getName() {
    return name;
  }

  /** @param name the name to set */
  public void setName(String name) {
    this.name = name;
  }

  /** @return the memo */
  public String getMemo() {
    return memo;
  }

  /** @param memo the memo to set */
  public void setMemo(String memo) {
    this.memo = memo;
  }

  /** @return the vdFather */
  public Boolean getVdFather() {
    return vdFather;
  }

  /** @param vdFather the vdFather to set */
  public void setVdFather(Boolean vdFather) {
    this.vdFather = vdFather;
  }

  /** @return the vdChild1 */
  public Boolean getVdChild1() {
    return vdChild1;
  }

  /** @param vdChild1 the vdChild1 to set */
  public void setVdChild1(Boolean vdChild1) {
    this.vdChild1 = vdChild1;
  }

  /** @return the vdChild2 */
  public Boolean getVdChild2() {
    return vdChild2;
  }

  /** @param vdChild2 the vdChild2 to set */
  public void setVdChild2(Boolean vdChild2) {
    this.vdChild2 = vdChild2;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    //此 toString 是給 rampVdTypeCombo 用的,不可更動
    return "" + this.id;
  }

  public String toStringInfo() {
    return "RampVdTypeDTO [id="
        + id
        + ", name="
        + name
        + ", memo="
        + memo
        + ", vdFather="
        + vdFather
        + ", vdChild1="
        + vdChild1
        + ", vdChild2="
        + vdChild2
        + "]";
  }

  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    return result;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (getClass() != obj.getClass()) return false;
    RampVdTypeDTO other = (RampVdTypeDTO) obj;
    if (id == null) {
      if (other.id != null) return false;
    } else if (!id.equals(other.id)) return false;
    return true;
  }
}
