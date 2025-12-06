/*
 * © HwaCom Systems Inc. 2015
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.cam.vo;

import com.hwacom.ngtms.cam.client.event.DataFetchEvent;
import com.hwacom.ngtms.cam.client.event.DataModifyEvent;

/**
 * 資料更新相關流程所需的 enum， client side 參見 {@link DataFetchEvent}、{@link DataModifyEvent}。
 *
 * <p>建議直接以 value object 的 class name 作為 enum 的名稱。
 */
public enum DeviceDataType {
  DeviceGroup,

  // Weather
  RdConfig,

  ViConfig,

  WdConfig,

  // RMS
  RmsConfig,

  RmsMeteringPlan,

  RmsMeteringPlanTemplate,

  RmsSchedule,

  RmsScheduleTemplate,

  RmsDdOccupancy,

  RmsDdOccupancyTemplate,

  RmsDownstreamCapacity,

  RmsWarnMessage,

  RmsWarnMessageTemplate,

  // VDS
  VdConfig,

  VdMacroGroup,

  // TEM
  TemTunnel,

  TemAirDetectPlace,

  TemCardReader,

  TemFireDivision,

  TemLightingDivision,

  TemLocationDefinition,

  TemPlaceDefinition,
  ;
}
