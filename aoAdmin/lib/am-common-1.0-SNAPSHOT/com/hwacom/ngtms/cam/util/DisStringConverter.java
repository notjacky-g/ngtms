/*
 * © HwaCom Systems Inc. 2015
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.cam.util;

import com.google.gwt.core.client.GWT;
import com.hwacom.ngtms.c.dis.shared.BrightType;
import com.hwacom.ngtms.c.dis.shared.CRMode;
import com.hwacom.ngtms.c.dis.shared.PanelCategory;
import com.hwacom.ngtms.c.dis.shared.RGColorModel;
import com.hwacom.ngtms.c.dis.shared.dto.PanelCategoryDTO;
import com.hwacom.ngtms.cam.view.Messages;

public class DisStringConverter {
  public static final Messages messages = GWT.create(Messages.class);

  public static String getBrightTypeName(BrightType type) {
    if (type == null) {
      return "";
    }
    switch (type) {
      case Daytime:
        return messages.brightType_daytime();
      case Dusk:
        return messages.brightType_dusk();
      case LateAtNight:
        return messages.brightType_lateAtNight();
      case Night:
        return messages.brightType_night();
      case SensorControl:
        return messages.brightType_sensorControl();
      case SunshineTableControl:
        return messages.brightType_sunshineTableControl();
      case None:
        return messages.brightType_none();
      default:
        return "";
    }
  }

  public static String getRGColorModelName(RGColorModel color) {
    if (color == null) {
      return "";
    }
    switch (color) {
      case GREEN:
        return messages.rgColorModel_green();
      case RED:
        return messages.rgColorModel_red();
      case YELLOW:
        return messages.rgColorModel_yellow();
      case OFF:
        return messages.rgColorModel_off();
      default:
        return "";
    }
  }

  public static String getPanelCategoryName(PanelCategoryDTO category) {
    if (category == null) {
      return "";
    }
    switch (category) {
      case RG_1x4:
        return "1x4 " + messages.panelCategory_rgb();
      case RG_1x8:
        return "1x8 " + messages.panelCategory_rgb();
      case RG_2x2:
        return "2x2 " + messages.panelCategory_rgb();
      case RG_2x6:
        return "2x6 " + messages.panelCategory_rgb();
      case RG_2x8:
        return "2x8 " + messages.panelCategory_rgb();
      case RGB_2x8:
        return "2x8 " + messages.panelCategory_fullColor();
      case RG_3x6:
        return "3x6 " + messages.panelCategory_rgb();
      case RG_4x1:
        return "4x1 " + messages.panelCategory_rgb();
      case RGB_4x8:
        return "4x8 " + messages.panelCategory_fullColor();
      case RG_5x1:
        return "5x1 " + messages.panelCategory_rgb();
      case RG_5x2:
        return "5x2 " + messages.panelCategory_rgb();
      case RG_8x1:
        return "8x1 " + messages.panelCategory_rgb();
      case RGB_3x8:
        return "3x8 " + messages.panelCategory_fullColor();
      case RGB_6x2:
        return "6x2 " + messages.panelCategory_fullColor();
      case RGB_8x1:
        return "8x1 " + messages.panelCategory_fullColor();
      case RG_3x8:
        return "3x8 " + messages.panelCategory_rgb();
      case RG_6x2:
        return "6x2 " + messages.panelCategory_rgb();
      case RGB_1x12:
        return "1x12 " + messages.panelCategory_fullColor();
      case RGB_1x8:
        return "1x8 " + messages.panelCategory_fullColor();
      case RGB_2x6:
        return "2x6 " + messages.panelCategory_fullColor();
      case NONE:
        return messages.panelCategory_none();
      default:
        return "";
    }
  }

  public static String getPanelCategoryName(PanelCategory category) {
    switch (category) {
      case RG_1x4:
        return "1x4 " + messages.panelCategory_rgb();
      case RG_1x8:
        return "1x8 " + messages.panelCategory_rgb();
      case RG_2x2:
        return "2x2 " + messages.panelCategory_rgb();
      case RG_2x6:
        return "2x6 " + messages.panelCategory_rgb();
      case RG_2x8:
        return "2x8 " + messages.panelCategory_rgb();
      case RGB_2x8:
        return "2x8 " + messages.panelCategory_fullColor();
      case RG_3x6:
        return "3x6 " + messages.panelCategory_rgb();
      case RG_4x1:
        return "4x1 " + messages.panelCategory_rgb();
      case RGB_4x8:
        return "4x8 " + messages.panelCategory_fullColor();
      case RG_5x1:
        return "5x1 " + messages.panelCategory_rgb();
      case RG_5x2:
        return "5x2 " + messages.panelCategory_rgb();
      case RG_8x1:
        return "8x1 " + messages.panelCategory_rgb();
      case RGB_3x8:
        return "3x8 " + messages.panelCategory_fullColor();
      case RGB_6x2:
        return "6x2 " + messages.panelCategory_fullColor();
      case RGB_8x1:
        return "8x1 " + messages.panelCategory_fullColor();
      case RG_3x8:
        return "3x8 " + messages.panelCategory_rgb();
      case RG_6x2:
        return "6x2 " + messages.panelCategory_rgb();
      case RGB_1x12:
        return "1x12 " + messages.panelCategory_fullColor();
      case RGB_1x8:
        return "1x8 " + messages.panelCategory_fullColor();
      case RGB_2x6:
        return "2x6 " + messages.panelCategory_fullColor();
      case NONE:
        return messages.panelCategory_none();
      default:
        return "";
    }
  }

  public static String getCRModeName(CRMode type) {
    if (type == null) {
      return "";
    }
    switch (type) {
      case CR:
        return messages.CRMode_CR();
      case CRLF:
        return messages.CRMode_CRLF();
      case NONE:
        return messages.CRMode_NONE();
      default:
        return "";
    }
  }
}
