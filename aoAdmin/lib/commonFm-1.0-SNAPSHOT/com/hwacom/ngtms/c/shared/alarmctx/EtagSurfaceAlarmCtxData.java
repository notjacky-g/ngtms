/*
 * © HwaCom Systems Inc. 2015
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.c.shared.alarmctx;

import com.hwacom.ngtms.c.shared.AlarmContextData;
import com.hwacom.ngtms.c.shared.Direction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** @author chao.wu */
public class EtagSurfaceAlarmCtxData implements AlarmContextData {

  private static final Logger logger = LoggerFactory.getLogger(EtagSurfaceAlarmCtxData.class);

  /** CMS Device Name */
  private String cmsDeviceName;

  /** 方向 */
  private Direction direction;

  /** etag 路段 id */
  private String etagSectionId;

  public String getCmsDeviceName() {
    return cmsDeviceName;
  }

  public void setCmsDeviceName(String cmsDeviceName) {
    this.cmsDeviceName = cmsDeviceName;
  }

  public Direction getDirection() {
    return direction;
  }

  public void setDirection(Direction direction) {
    this.direction = direction;
  }

  public String getEtagSectionId() {
    return etagSectionId;
  }

  public void setEtagSectionId(String etagSectionId) {
    this.etagSectionId = etagSectionId;
  }

  @Override
  public String toString() {
    return "EtagSurfaceAlarmCtxData{"
        + "cmsDeviceName="
        + cmsDeviceName
        + ", direction="
        + direction
        + ", etagSectionId="
        + etagSectionId
        + '}';
  }

  @Override
  public void decode(String strData) {
    if (strData != null) {
      String[] parts = strData.split(";");
      if ((parts.length > 0) && (parts[0].length() > 0)) cmsDeviceName = parts[0];
      if ((parts.length > 1) && (parts[1].length() > 0)) {
        try {
          direction = Direction.valueOf(parts[1]);
        } catch (IllegalArgumentException ex) {
          logger.warn("EtagSurfaceAlarmCtxData decode failed!", ex);
        }
      }
      if ((parts.length > 2) && (parts[2].length() > 0)) etagSectionId = parts[2];
    }
  }

  @Override
  public String encode() {
    StringBuilder sb = new StringBuilder();
    if (cmsDeviceName != null) sb.append(cmsDeviceName);
    sb.append(';');
    if (direction != null) sb.append(direction);
    sb.append(';');
    if (etagSectionId != null) sb.append(etagSectionId);
    sb.append(';');
    return sb.toString();
  }
}
