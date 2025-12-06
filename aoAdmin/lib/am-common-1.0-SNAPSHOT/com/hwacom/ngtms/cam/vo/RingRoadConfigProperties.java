/*
 * © HwaCom Systems Inc. 2015
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.cam.vo;

import com.google.gwt.editor.client.Editor.Path;
import com.hwacom.ngtms.c.shared.dto.RingRoadConfigDTO;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;

public interface RingRoadConfigProperties extends PropertyAccess<RingRoadConfigDTO> {
  @Path("id")
  ModelKeyProvider<RingRoadConfigDTO> key();

  ValueProvider<RingRoadConfigDTO, String> id();

  ValueProvider<RingRoadConfigDTO, String> startDivisionId();

  ValueProvider<RingRoadConfigDTO, String> startDivisionName();

  ValueProvider<RingRoadConfigDTO, String> endDivisionId();

  ValueProvider<RingRoadConfigDTO, String> endDivisionName();

  ValueProvider<RingRoadConfigDTO, String> startLineId();

  ValueProvider<RingRoadConfigDTO, String> startLineName();

  ValueProvider<RingRoadConfigDTO, String> startDirectionStr();

  ValueProvider<RingRoadConfigDTO, Integer> startMileage();

  ValueProvider<RingRoadConfigDTO, String> startDescription();

  ValueProvider<RingRoadConfigDTO, String> endLineId();

  ValueProvider<RingRoadConfigDTO, String> endLineName();

  ValueProvider<RingRoadConfigDTO, String> endDirectionStr();

  ValueProvider<RingRoadConfigDTO, Integer> endMileage();

  ValueProvider<RingRoadConfigDTO, String> endDescription();

  ValueProvider<RingRoadConfigDTO, String> exitVd();

  ValueProvider<RingRoadConfigDTO, String> exitVdName();

  ValueProvider<RingRoadConfigDTO, Integer> length();

  ValueProvider<RingRoadConfigDTO, Integer> speedLimit();

  ValueProvider<RingRoadConfigDTO, Integer> freeTravelTime();

  ValueProvider<RingRoadConfigDTO, String> etagSectionId();

  ValueProvider<RingRoadConfigDTO, String> ringRoadVdStr();
}
