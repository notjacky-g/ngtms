/*
 * © HwaCom Systems Inc. 2015
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.cam.vo;

import com.google.gwt.editor.client.Editor.Path;
import com.hwacom.ngtms.c.shared.AreaType;
import com.hwacom.ngtms.c.shared.DivisionType;
import com.hwacom.ngtms.c.shared.dto.RoadDivisionDTO;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;

public interface RoadDivisionProperties extends PropertyAccess<RoadDivisionDTO> {
  @Path("divisionId")
  ModelKeyProvider<RoadDivisionDTO> key();

  ValueProvider<RoadDivisionDTO, String> divisionId();

  ValueProvider<RoadDivisionDTO, String> divisionName();

  ValueProvider<RoadDivisionDTO, DivisionType> divisionType();

  ValueProvider<RoadDivisionDTO, Integer> mileage();

  ValueProvider<RoadDivisionDTO, String> lineId();

  ValueProvider<RoadDivisionDTO, String> lineName();

  ValueProvider<RoadDivisionDTO, AreaType> areaType();

  ValueProvider<RoadDivisionDTO, Boolean> boundary();

  ValueProvider<RoadDivisionDTO, Boolean> travelTimeVisible();
}
