package com.hwacom.ngtms.room.shared.dto;

import com.google.gwt.user.client.rpc.IsSerializable;
import java.io.Serializable;

public class RoomCardGroupConfigDTO implements Serializable, IsSerializable {

  private static final long serialVersionUID = 1L;

  private Long id;

  //群組名稱,不可重複
  private String name;

  //備註
  private String memo;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getMemo() {
    return memo;
  }

  public void setMemo(String memo) {
    this.memo = memo;
  }

  @Override
  public String toString() {
    return "RoomCardGroupConfigDTO [id=" + id + ", name=" + name + ", memo=" + memo + "]";
  }
}
