/*
 * © HwaCom Systems Inc. 2015
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.c.shared.dto;

import com.google.gwt.user.client.rpc.IsSerializable;
import com.hwacom.ngtms.c.shared.DivisionType;
import java.io.Serializable;

/**
 * Rest 傳送相關參數
 *
 * @author brian.cheng
 */
public class RoadParametersDTO implements Serializable, IsSerializable {
  /** */
  private static final long serialVersionUID = -5360722802733182592L;

  private RoadLineDTO roadLineDTO;
  private String key;
  private DivisionType divisionType;
  private RoadDivisionDTO roadDivisionDTO;
  private String divisionId;
  private RampVdConfigDTO rampVdConfigDTO;
  private Integer keyInInteger;
  private RingRoadConfigDTO ringRoadConfigDTO;

  public RoadLineDTO getRoadLineDTO() {
    return roadLineDTO;
  }

  public void setRoadLineDTO(RoadLineDTO roadLineDTO) {
    this.roadLineDTO = roadLineDTO;
  }

  public String getKey() {
    return key;
  }

  public void setKey(String key) {
    this.key = key;
  }

  public DivisionType getDivisionType() {
    return divisionType;
  }

  public void setDivisionType(DivisionType divisionType) {
    this.divisionType = divisionType;
  }

  public RoadDivisionDTO getRoadDivisionDTO() {
    return roadDivisionDTO;
  }

  public void setRoadDivisionDTO(RoadDivisionDTO roadDivisionDTO) {
    this.roadDivisionDTO = roadDivisionDTO;
  }

  public String getDivisionId() {
    return divisionId;
  }

  public void setDivisionId(String divisionId) {
    this.divisionId = divisionId;
  }

  public RampVdConfigDTO getRampVdConfigDTO() {
    return rampVdConfigDTO;
  }

  public void setRampVdConfigDTO(RampVdConfigDTO rampVdConfigDTO) {
    this.rampVdConfigDTO = rampVdConfigDTO;
  }

  public Integer getKeyInInteger() {
    return keyInInteger;
  }

  public void setKeyInInteger(Integer keyInInteger) {
    this.keyInInteger = keyInInteger;
  }

  public RingRoadConfigDTO getRingRoadConfigDTO() {
    return ringRoadConfigDTO;
  }

  public void setRingRoadConfigDTO(RingRoadConfigDTO ringRoadConfigDTO) {
    this.ringRoadConfigDTO = ringRoadConfigDTO;
  }
}
