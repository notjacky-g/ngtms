/*
 * © HwaCom Systems Inc. 2015
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.cam.vo;

import com.hwacom.ngtms.c.shared.DeviceStatusType;
import com.hwacom.ngtms.c.shared.Direction;
import java.util.List;

//// ==== 下面都是為了建 tree 而弄出來的 class，基本上不用理會也不該理會 XD ====//

// 得是 default、不能 private，不然 BeforeExpandItemEvent 無法用 UiHandler 直接處理 Orz
// 新增milepost及direction做排序處理
public class IdNameNode {
  private String id;
  private String name;
  private Integer milepost;
  private Direction direction;
  private DeviceStatusType status = DeviceStatusType.NONE;
  private String specialSignal;
  private String deviceName;
  private List<IdNameNode> children;

  public IdNameNode(String id, String name) {
    this(id, name, null, null);
  }

  public IdNameNode(String id, String name, Integer milepost, Direction direction) {
    this.id = id;
    this.name = name;
    this.milepost = milepost;
    this.direction = direction;
  }

  public String getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public Integer getMilePost() {
    return milepost;
  }

  public Direction getDirection() {
    return direction;
  }

  public void setStatus(DeviceStatusType status) {
    this.status = status;
  }

  public DeviceStatusType getStatus() {
    return status;
  }

  public void setSpecialSignal(String specialSignal) {
    this.specialSignal = specialSignal;
  }

  public String getSpecialSignal() {
    return specialSignal;
  }

  public void setDeviceName(String deviceName) {
    this.deviceName = deviceName;
  }

  public String getDeviceName() {
    return deviceName;
  }

  public void setChildren(List<IdNameNode> children) {
    this.children = children;
  }

  public List<IdNameNode> getChildren() {
    return children;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return "IdNameNode [id="
        + id
        + ", name="
        + name
        + ", milepost="
        + milepost
        + ", direction="
        + direction
        + ", status="
        + status
        + ", specialSignal="
        + specialSignal
        + ", deviceName="
        + deviceName
        + "]";
  }
}
