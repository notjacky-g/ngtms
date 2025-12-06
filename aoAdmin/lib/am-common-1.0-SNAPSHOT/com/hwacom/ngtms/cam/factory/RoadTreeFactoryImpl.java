/*
 * © HwaCom Systems Inc. 2015
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.cam.factory;

import com.hwacom.ngtms.c.shared.dto.DeviceConfigDTO;
import com.hwacom.ngtms.cam.vo.IdNameNode;
import java.util.List;

/**
 * 子系統各自提供實做 class，並在 <code>gwt.xml</code> 用
 *
 * <pre>{@code
 * <replace-with class="PKG_NAME.實做class">
 * 	<when-type-is class="com.hwacom.ngtms.common.am.factory.RoadTreeFactory"/>
 * </replace-with>
 * }</pre>
 *
 * 來替換成各自的實做內容（deferred binding）。
 */
public class RoadTreeFactoryImpl implements RoadTreeFactory {

  @Override
  public boolean isFactoryReady() {
    return true;
  }

  @Override
  public String getSpecialSignal(String deviceName) {
    return null;
  }

  @Override
  public List<IdNameNode> getTreeSource() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public boolean isDragDeviceConfig() {
    return true;
  }

  @Override
  public String getDisplayName(DeviceConfigDTO deviceConfig) {
    return deviceConfig.getDisplayName();
  }
}
