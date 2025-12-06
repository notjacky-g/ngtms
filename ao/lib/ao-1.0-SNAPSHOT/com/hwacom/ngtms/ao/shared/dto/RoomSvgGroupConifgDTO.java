/*
 * © HwaCom Systems Inc. 2015
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.ao.shared.dto;

import com.google.gwt.user.client.rpc.IsSerializable;
import com.sencha.gxt.data.shared.TreeStore.TreeNode;
import java.util.ArrayList;
import java.util.List;

public class RoomSvgGroupConifgDTO implements IsSerializable, TreeNode<RoomSvgGroupConifgDTO> {

  /** 節點Key */
  private String nodeKey;
  /** 節點名稱 */
  private String nodeName;
  /** 識別名稱 拖拉時對應機房名稱 */
  private String roomName;
  /** 群組名稱 儲存時判斷的點位 */
  private String groupName;
  /** 是否為設備那一層 */
  private Boolean check = false;

  private List<RoomSvgGroupConifgDTO> children = new ArrayList<RoomSvgGroupConifgDTO>();

  public String getNodeKey() {
    return nodeKey;
  }

  public void setNodeKey(String nodeKey) {
    this.nodeKey = nodeKey;
  }

  public String getNodeName() {
    return nodeName;
  }

  public void setNodeName(String nodeName) {
    this.nodeName = nodeName;
  }

  public String getRoomName() {
    return roomName;
  }

  public void setRoomName(String roomName) {
    this.roomName = roomName;
  }

  public String getGroupName() {
    return groupName;
  }

  public void setGroupName(String groupName) {
    this.groupName = groupName;
  }

  public Boolean getCheck() {
    return check;
  }

  public void setCheck(Boolean check) {
    this.check = check;
  }

  public List<RoomSvgGroupConifgDTO> getChildren() {
    return children;
  }

  public void setChildren(List<RoomSvgGroupConifgDTO> children) {
    this.children = children;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((nodeKey == null) ? 0 : nodeKey.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (getClass() != obj.getClass()) return false;
    RoomSvgGroupConifgDTO other = (RoomSvgGroupConifgDTO) obj;
    if (nodeKey == null) {
      if (other.nodeKey != null) return false;
    } else if (!nodeKey.equals(other.nodeKey)) return false;
    return true;
  }

  @Override
  public RoomSvgGroupConifgDTO getData() {
    return null;
  }
}
