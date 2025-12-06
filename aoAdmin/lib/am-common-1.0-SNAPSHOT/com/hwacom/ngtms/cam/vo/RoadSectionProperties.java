/*
 * © HwaCom Systems Inc. 2015
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.cam.vo;

import com.google.gwt.editor.client.Editor.Path;
import com.hwacom.ngtms.c.shared.dto.RoadSectionDTO;
import com.hwacom.ngtms.cam.util.CommonStringConverter;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;

public interface RoadSectionProperties extends PropertyAccess<RoadSectionDTO> {
  @Path("sectionId")
  ModelKeyProvider<RoadSectionDTO> key();

  ValueProvider<RoadSectionDTO, String> sectionId();

  ValueProvider<RoadSectionDTO, String> sectionName();

  ValueProvider<RoadSectionDTO, String> lineid();

  ValueProvider<RoadSectionDTO, String> direction =
      new ValueProvider<RoadSectionDTO, String>() {
        @Override
        public void setValue(RoadSectionDTO object, String value) {}

        @Override
        public String getValue(RoadSectionDTO object) {
          return CommonStringConverter.getDirectionName(object.getDirection());
        }

        @Override
        public String getPath() {
          return "direction";
        }
      };

  ValueProvider<RoadSectionDTO, String> startDivisionId();

  ValueProvider<RoadSectionDTO, String> endDivisionId();

  ValueProvider<RoadSectionDTO, Short> laneCount();

  ValueProvider<RoadSectionDTO, Integer> maxSpeed();

  ValueProvider<RoadSectionDTO, Integer> minSpeed();

  ValueProvider<RoadSectionDTO, String> areaType();

  ValueProvider<RoadSectionDTO, String> standardVd();

  ValueProvider<RoadSectionDTO, String> subStandardVd1();

  ValueProvider<RoadSectionDTO, String> subStandardVd2();

  ValueProvider<RoadSectionDTO, Boolean> manualEnabled();

  ValueProvider<RoadSectionDTO, String> eventLogMode();

  ValueProvider<RoadSectionDTO, String> eventExecMode();

  ValueProvider<RoadSectionDTO, Integer> sectionFlow();

  ValueProvider<RoadSectionDTO, Integer> freeSpeed();

  ValueProvider<RoadSectionDTO, Boolean> ddsRoadSectionEnabled();

  ValueProvider<RoadSectionDTO, Boolean> ddsRoadSectionGroup1();

  ValueProvider<RoadSectionDTO, Boolean> ddsRoadSectionGroup2();

  ValueProvider<RoadSectionDTO, Boolean> ddsRoadSectionGroup3();

  ValueProvider<RoadSectionDTO, Boolean> ddsRoadSectionGroup4();
}
