/*
 * © HwaCom Systems Inc. 2015
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.cam.client.event;

import com.google.gwt.event.shared.GwtEvent;
import com.hwacom.ngtms.c.shared.dto.DeviceGroupDTO;

public class GroupOperationEvent extends GwtEvent<GroupOperationHandler> {
  public static final Type<GroupOperationHandler> TYPE = new Type<GroupOperationHandler>();

  public enum GroupOperationType {
    /** 刪除 */
    DELETE,
    /** 更新 */
    UPDATE
  }

  private GroupOperationType type;
  private DeviceGroupDTO deviceGroup;

  public GroupOperationEvent(GroupOperationType type, DeviceGroupDTO deviceGroup) {
    this.type = type;
    this.deviceGroup = deviceGroup;
  }

  public GroupOperationType getType() {
    return type;
  }

  public DeviceGroupDTO getDeviceGroup() {
    return deviceGroup;
  }

  @Override
  public Type<GroupOperationHandler> getAssociatedType() {
    return TYPE;
  }

  @Override
  protected void dispatch(GroupOperationHandler handler) {
    handler.onGroupOperation(this);
  }
}
