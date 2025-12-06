package com.hwacom.ngtms.c.shared.dto;

import com.google.gwt.user.client.rpc.IsSerializable;
import java.io.Serializable;

public class DeviceTypeDTO implements Serializable, IsSerializable {

  private static final long serialVersionUID = 5228600450689798507L;

  /** 設備類型名稱 */
  private String id;

  /** 設備類型中文描述 */
  private String description;

  /** 設備分類 (資收 = DGS, 資顯 = DIS, 交管 = TC 等) */
  private String category;

  private String categoryDescription;

  private boolean all;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getCategory() {
    return category;
  }

  public void setCategory(String category) {
    this.category = category;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getCategoryDescription() {
    return categoryDescription;
  }

  public void setCategoryDescription(String categoryDescription) {
    this.categoryDescription = categoryDescription;
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
    DeviceTypeDTO other = (DeviceTypeDTO) obj;
    if (id == null) {
      if (other.id != null) return false;
    } else if (!id.equals(other.id)) return false;
    return true;
  }

  @Override
  public String toString() {
    return "DeviceType ["
        + "id="
        + id
        + ", description="
        + description
        + ", category="
        + category
        + "]";
  }
}
