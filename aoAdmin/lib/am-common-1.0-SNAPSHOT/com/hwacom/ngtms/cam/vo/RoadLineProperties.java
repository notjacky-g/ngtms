/*
 * © HwaCom Systems Inc. 2015
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.cam.vo;

import com.google.gwt.editor.client.Editor.Path;
import com.hwacom.ngtms.c.shared.dto.RoadLineDTO;
import com.hwacom.ngtms.cam.util.CommonStringConverter;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;

public interface RoadLineProperties extends PropertyAccess<RoadLineDTO> {
  @Path("lineId")
  ModelKeyProvider<RoadLineDTO> key();

  ValueProvider<RoadLineDTO, String> lineId();

  ValueProvider<RoadLineDTO, String> lineName();

  ValueProvider<RoadLineDTO, String> direction =
      new ValueProvider<RoadLineDTO, String>() {
        @Override
        public void setValue(RoadLineDTO object, String value) {}

        @Override
        public String getValue(RoadLineDTO object) {
          return CommonStringConverter.getDirectionName(object.getDirection());
        }

        @Override
        public String getPath() {
          return "direction";
        }
      };

  ValueProvider<RoadLineDTO, Integer> startMileage();

  ValueProvider<RoadLineDTO, Integer> endMileage();

  ValueProvider<RoadLineDTO, String> memo();

  ValueProvider<RoadLineDTO, Boolean> enable();

  ValueProvider<RoadLineDTO, Integer> ggCodeId();
}
