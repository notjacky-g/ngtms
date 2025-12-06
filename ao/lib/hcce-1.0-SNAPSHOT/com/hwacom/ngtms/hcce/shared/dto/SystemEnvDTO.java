/*
 * © HwaCom Systems Inc. 2013
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.hcce.shared.dto;

import com.google.gwt.user.client.rpc.IsSerializable;
import java.io.Serializable;
import java.util.Date;
import java.util.Map;

public class SystemEnvDTO implements Serializable, IsSerializable {

  private static final long serialVersionUID = -7494589924843157526L;

  private HcceEnvDTO hcceEnv;
  private ClusterModeDTO clusterMode;
  private Map<String, DbStatusDTO> dbStatusMap;
  private Date updateTime;

  public SystemEnvDTO() {
    clusterMode = ClusterModeDTO.DisConnected;
  }

  /** @return the clusterMode */
  public ClusterModeDTO getClusterMode() {
    return clusterMode;
  }
  /** @param clusterMode the clusterMode to set */
  public void setClusterMode(ClusterModeDTO clusterMode) {
    this.clusterMode = clusterMode;
  }
  /** @return the hcceEnv */
  public HcceEnvDTO getHcceEnv() {
    return hcceEnv;
  }
  /** @param hcceEnv the hcceEnv to set */
  public void setHcceEnv(HcceEnvDTO hcceEnv) {
    this.hcceEnv = hcceEnv;
  }

  public Date getUpdateTime() {
    return updateTime;
  }

  public void setUpdateTime(Date updateTime) {
    this.updateTime = updateTime;
  }

  public Map<String, DbStatusDTO> getDbStatusMap() {
    return dbStatusMap;
  }

  public void setDbStatusMap(Map<String, DbStatusDTO> dbStatusMap) {
    this.dbStatusMap = dbStatusMap;
  }

  @Override
  public String toString() {
    return "SystemEnvDTO [hcceEnv="
        + hcceEnv
        + ", clusterMode="
        + clusterMode
        + ", dbStatus="
        + dbStatusMap
        + ", updateTime="
        + updateTime
        + "]";
  }
}
