/*
 * © HwaCom Systems Inc. 2015
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.cam.util;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.NumberFormat;
import com.hwacom.ngtms.c.shared.Direction;
import com.hwacom.ngtms.c.shared.HardWareCycle;
import com.hwacom.ngtms.c.shared.LocationType;
import com.hwacom.ngtms.c.shared.OpStatusHiNibbleType;
import com.hwacom.ngtms.c.shared.OpStatusLowNibbleType;
import com.hwacom.ngtms.c.shared.PriorityType;
import com.hwacom.ngtms.c.shared.RampType;
import com.hwacom.ngtms.c.shared.ResponseStatus;
import com.hwacom.ngtms.c.shared.TcProtocolType;
import com.hwacom.ngtms.c.shared.TransmissionMode;
import com.hwacom.ngtms.cam.view.Messages;

public class CommonStringConverter {
  public static final Messages messages = GWT.create(Messages.class);

  public static String getDirectionName(Direction direction) {
    if (direction == null) {
      return "";
    }
    switch (direction) {
      case N:
        return messages.direction_N();
      case E:
        return messages.direction_E();
      case S:
        return messages.direction_S();
      case W:
        return messages.direction_W();
      case EN:
        return messages.direction_EN();
      case ES:
        return messages.direction_ES();
      case EW:
        return messages.direction_EW();
      case NS:
        return messages.direction_NS();
      case WN:
        return messages.direction_WN();
      case WS:
        return messages.direction_WS();
      default:
        return "";
    }
  }

  public static String getDirectionNameWithBiDirection(Direction direction) {
    switch (direction) {
      case N:
        return messages.direction_N();
      case E:
        return messages.direction_E();
      case S:
        return messages.direction_S();
      case W:
        return messages.direction_W();
      case EN:
        return messages.direction_EN();
      case ES:
        return messages.direction_ES();
      case WN:
        return messages.direction_WN();
      case WS:
        return messages.direction_WS();
      case EW:
      case NS:
        return messages.direction_BI();
      default:
        return "";
    }
  }

  public static String getPriorityTypeName(PriorityType type) {
    if (type == null) {
      return "";
    }
    switch (type) {
      case ManualOverride:
        return messages.priorityType_manualOverride();
      case ResponsePlan:
        return messages.priorityType_responsePlan();
      case Manual:
        return messages.priorityType_manual();
      case Schedule:
        return messages.priorityType_schedule();
      case AutomaticCalculation:
        return messages.priorityType_automaticCalculation();
      default:
        return "";
    }
  }

  /** @return 將 1～6 轉換成為「星期一」～「星期六」，7 轉換成為「星期天」 其他值則回傳空字串。 */
  public static String getWeekDayName(int i) {
    switch (i) {
      case 1:
        return messages.monday();
      case 2:
        return messages.tuesday();
      case 3:
        return messages.wednesday();
      case 4:
        return messages.thursday();
      case 5:
        return messages.friday();
      case 6:
        return messages.saturday();
      case 7:
        return messages.sunday();
      default:
        return "";
    }
  }

  public static String getOpStatusHiNibbleTypeName(OpStatusHiNibbleType type) {
    if (null == type) {
      return "";
    }
    switch (type) {
      case ABNORMAL:
        return messages.reportStatus_abnormal();
      case DAWN_BRIGHTNESS:
        return messages.reportStatus_onDusk();
      case DAY_BRIGHTNESS:
        return messages.reportStatus_onDayTime();
      case MALFUNCTIONAL:
        return messages.reportStatus_error();
      case NIGHT_BRIGHTNESS:
        return messages.reportStatus_onNight();
      case OFF:
        return messages.reportStatus_off();
      default:
        return "";
    }
  }

  public static String getOpStatusLowNibbleTypeName(OpStatusLowNibbleType type) {
    if (null == type) {
      return "";
    }
    switch (type) {
      case ABNORMAL:
        return messages.reportStatus_abnormal();
      case BRIGHTNESS_TABLE_CONTROL:
        return messages.reportStatus_sensorControl();
      case ON_SITE_DETECT:
        return messages.reportStatus_fieldDetect();
      case REMOTE_DAY_BRIGHTNESS:
        return messages.reportStatus_remoteDayTime();
      case REMOTE_NIGHT_BRIGHTNESS:
        return messages.reportStatus_remoteNight();
      case SUNSHINE_TABLE_CONTROL:
        return messages.reportStatus_sunshineTableControl();
      default:
        return "";
    }
  }

  public static String toYesOrNo(boolean value) {
    return value ? messages.common_yes() : messages.common_no();
  }

  public static String toRegularOrNot(boolean value) {
    return value ? messages.common_normal() : messages.common_abnormal();
  }

  public static String toAbnormalOrNormal(boolean value) {
    return value ? messages.common_abnormal() : messages.common_normal();
  }

  public static String toEnableOrNot(boolean value) {
    return value ? messages.common_enable() : messages.common_disable();
  }

  /**
   * 將里程（單位公尺）轉換成「55K+066」的形式，沒有套多國語系
   *
   * @param milepost　里程（單位公尺）
   * @return 「55k+066」形式的字串
   */
  public static String toMilepost(int milepost) {
    int kilometer = milepost / 1000;
    int meter = milepost % 1000;
    return kilometer + "k+" + NumberFormat.getFormat("000").format(meter);
  }

  public static String getResponseStatusName(ResponseStatus status) {
    if (status == null) {
      return "";
    }
    switch (status) {
      case ACK:
        return messages.operation_responseStatus_ack();
      case NAK:
        return messages.operation_responseStatus_nak();
      case NOT_SENT:
        return messages.operation_responseStatus_notSent();
      case PENDING:
        return messages.operation_responseStatus_pending();
      case TIMEOUT:
        return messages.operation_responseStatus_timeout();
      default:
        return "";
    }
  }

  public static String getHardWareCycleName(HardWareCycle hwCycle) {
    if (hwCycle == null) {
      return "";
    }
    switch (hwCycle) {
      case FIVEMIN:
        return messages.hardWareCycle_fiveMin();
      case FIVESECONDS:
        return messages.hardWareCycle_fiveSeconds();
      case ONEMIN:
        return messages.hardWareCycle_oneMin();
      case STOP:
        return messages.hardWareCycle_stop();
      case TENSECONDS:
        return messages.hardWareCycle_tenSeconds();
      case TWENTYSECONDS:
        return messages.hardWareCycle_twentySeconds();
      default:
        return "";
    }
  }

  public static String getTransmissionModeName(TransmissionMode transMode) {
    if (transMode == null) {
      return "";
    }
    switch (transMode) {
      case ACTIVE:
        return messages.transmissionMode_active();
      case PASSIVE:
        return messages.transmissionMode_passive();
      default:
        return "";
    }
  }

  public static String getRampTypeName(RampType type) {
    if (type == null) {
      return "";
    }
    switch (type) {
      case I:
        return messages.rampType_I();
      case O:
        return messages.rampType_O();
      case U:
        return messages.rampType_U();
      default:
        return "";
    }
  }

  public static String getLocationType(String locationType) {
    if (locationType == null) {
      return "";
    }
    try {
      LocationType type = LocationType.valueOf(locationType);
      switch (type) {
        case F:
          return messages.LocationType_F();
        case H:
          return messages.LocationType_H();
        case I:
          return messages.LocationType_I();
        case L:
          return messages.LocationType_L();
        case P:
          return messages.LocationType_P();
        case R:
          return messages.LocationType_R();
        case S:
          return messages.LocationType_S();
        case T:
          return messages.LocationType_T();
        case CT:
          return messages.LocationType_CT();
        case D:
          return messages.LocationType_D();
        case TE:
          return messages.LocationType_TE();
        default:
          return locationType;
      }
    } catch (IllegalArgumentException e) {
      // 非 LocationType 中的類型，直接回傳
      return locationType;
    }
  }

  public static String getEventType(String eventType) {
    if (eventType == null) {
      return "";
    }
    switch (eventType) {
      case "NONE":
        return "不使用";
      case "STOP":
        return "停等";
      case "PEDESTRIAN":
        return "行人偵測";
      case "ITEM":
        return "散落物";
      case "REVERSE_DIRECTION":
        return "逆行車輛";
      case "SMOKE":
        return "煙霧";
      case "CONGESTION":
        return "交通壅塞";
      default:
        return eventType;
    }
  }

  public static String getTcProtocolTypeName(TcProtocolType type) {
    if (type == null) {
      return "";
    }
    switch (type) {
      case CT3:
        return messages.tcProtocolType_CT3();
      case FW2:
        return messages.tcProtocolType_FW2();
      default:
        return "";
    }
  }

  public static String getCT3OperationMode(Integer mode) {
    if (mode == null) {
      return "";
    }
    switch (mode) {
      case 0:
        return "全部解除鎖定，允許現場操作";
      case 1:
        return "全部鎖定，不允許現場操作";
      case 2:
        return "鎖定DB更新，允許現場查看資料";
      default:
        return "";
    }
  }
}
