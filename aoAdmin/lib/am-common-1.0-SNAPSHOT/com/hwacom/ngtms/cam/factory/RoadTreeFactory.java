/*
 * © HwaCom Systems Inc. 2015
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.cam.factory;

import com.hwacom.ngtms.c.shared.dto.DeviceConfigDTO;
import com.hwacom.ngtms.cam.client.ui.RoadTreeViewer;
import com.hwacom.ngtms.cam.vo.IdNameNode;
import java.util.List;

/**
 * 子系統各自提供實做 class，並在 <code>gwt.xml</code> 用
 *
 * <pre>{@code
 * <replace-with class="PKG_NAME.實做class">
 * 	<when-type-is class="com.hwacom.ngtms.common.am.factory.RoadTreeFactoryImpl"/>
 * </replace-with>
 * }</pre>
 *
 * 來替換成各自的實做內容（deferred binding）。
 */
public interface RoadTreeFactory {

  /**
   * 相關需要的 data 有沒有準備完成
   *
   * @return true if data is ready
   */
  boolean isFactoryReady();

  String getSpecialSignal(String deviceName);

  /**
   * 取得樹狀結構的建構資料, 配合 {@link RoadTreeViewer#setAutoGeneration(boolean)} 使用 可透過{@link
   * IdNameNode#setChildren(List)}來設定子層結構
   *
   * @return
   */
  List<IdNameNode> getTreeSource();

  /**
   * DnD 的 AmDragItem 是不是 DeviceConfigDTO (AddType.DeviceConfig)
   *
   * @return true if DeviceConfigDTO (AddType.DeviceConfig), false otherwise (AddType.Other)
   */
  boolean isDragDeviceConfig();

  /**
   * 在 isDragDeviceConfig 是 true 的情況下，要使用的顯示名稱
   *
   * @param deviceConfig 設備的 DeviceConfigDTO
   * @return 顯示名稱
   */
  String getDisplayName(DeviceConfigDTO deviceConfig);
}
