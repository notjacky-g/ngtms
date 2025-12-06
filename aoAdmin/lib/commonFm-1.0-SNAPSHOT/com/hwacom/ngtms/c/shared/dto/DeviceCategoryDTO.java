package com.hwacom.ngtms.c.shared.dto;

import java.io.Serializable;

/** 設備分類 (資收 = DGS, 資顯 = DIS, 交管 = TC 等) */
public class DeviceCategoryDTO implements Serializable {

  private static final long serialVersionUID = 871115560612150888L;

  /** 設備分類名稱 (資收 = DGS, 資顯 = DIS, 交管 = TC 等) */
  private String id;

  /** 設備分類中文描述 */
  private String description;

  private String parentId;

  private boolean all;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getParentId() {
    return parentId;
  }

  public void setParentId(String parentId) {
    this.parentId = parentId;
  }

  public boolean getAll() {
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
    DeviceCategoryDTO other = (DeviceCategoryDTO) obj;
    if (id == null) {
      if (other.id != null) return false;
    } else if (!id.equals(other.id)) return false;
    return true;
  }

  @Override
  public String toString() {
    return "DeviceCategoryDTO [" + "id=" + id + ", description=" + description + "]";
  }
}
