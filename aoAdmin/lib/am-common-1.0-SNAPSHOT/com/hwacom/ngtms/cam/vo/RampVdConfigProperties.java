/*
 * © HwaCom Systems Inc. 2015
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.cam.vo;

import com.google.gwt.editor.client.Editor.Path;
import com.hwacom.ngtms.c.shared.dto.RampVdConfigDTO;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;

public interface RampVdConfigProperties extends PropertyAccess<RampVdConfigDTO> {
  @Path("id")
  ModelKeyProvider<RampVdConfigDTO> key();

  ValueProvider<RampVdConfigDTO, Integer> id();

  ValueProvider<RampVdConfigDTO, String> divisionId();

  ValueProvider<RampVdConfigDTO, String> divisionName();

  ValueProvider<RampVdConfigDTO, String> directionStr();

  ValueProvider<RampVdConfigDTO, String> rampTypeStr();

  ValueProvider<RampVdConfigDTO, String> rampTypeDesc();

  ValueProvider<RampVdConfigDTO, Integer> rampVdType();

  ValueProvider<RampVdConfigDTO, Integer> exception();

  ValueProvider<RampVdConfigDTO, String> vd1();

  ValueProvider<RampVdConfigDTO, String> vd2();

  ValueProvider<RampVdConfigDTO, String> vd3();

  ValueProvider<RampVdConfigDTO, String> degreeVd1();

  ValueProvider<RampVdConfigDTO, String> degreeVd2();

  ValueProvider<RampVdConfigDTO, String> degreeVd3();

  ValueProvider<RampVdConfigDTO, String> degreeVd4();

  ValueProvider<RampVdConfigDTO, String> degreeVd5();

  ValueProvider<RampVdConfigDTO, String> degreeVd6();
}
