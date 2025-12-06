/*
 * © HwaCom Systems Inc. 2015
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.room.am.util;

import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.i18n.shared.DateTimeFormat;
import com.hwacom.ngtms.room.shared.CardStatus;
import com.hwacom.ngtms.room.shared.CardType;
import com.hwacom.ngtms.room.shared.SignalType;
import java.util.Date;

/** @author huei.yang */
public class StringConverter {

  public static String getFormattedMileage(Integer mileage) {
    if (mileage == null) {
      return "";
    }
    return mileage / 1000 + "k+" + NumberFormat.getFormat("000").format(mileage % 1000);
  }

  public static String getCardType(CardType cardType) {
    switch (cardType) {
      case REGULAR:
        return "定期卡";
      case TEMPORARY:
        return "臨時卡";
      case UNLIMITE:
        return "無限卡";
      case VIRTUAL:
        return "虛擬卡";
      default:
        return "";
    }
  }

  public static String getYesOrNo(Boolean result) {
    if (result == null) {
      return "";
    }
    return result ? "是" : "否";
  }

  public static String getCardEnable(Boolean enable) {
    if (enable == null) {
      return "已歸還";
    }
    return enable ? "啟用" : "停用";
  }

  /** 將Date轉換成字串yyyy-MM-dd HH:mm:ss */
  public static String getTimeyyyyMMddHHmmss(Date date) {
    if (date != null) {
      DateTimeFormat format = DateTimeFormat.getFormat("yyyy-MM-dd HH:mm:ss");
      return format.format(date);
    }
    return "";
  }

  public static String getTimeyyyyMMdd(Date date) {
    if (date != null) {
      DateTimeFormat format = DateTimeFormat.getFormat("yyyy-MM-dd");
      return format.format(date);
    }
    return "";
  }

  public static Date getTime0000() {
    DateTimeFormat format = DateTimeFormat.getFormat("yyyy-MM-dd HH:mm");
    return format.parse("2019-10-29 00:00");
  }

  /** 將cardType string 轉為enum */
  public static CardType getCardTpyeFromStr(String cardTypeStr) {
    switch (cardTypeStr) {
      case "定期卡":
        return CardType.REGULAR;
      case "臨時卡":
        return CardType.TEMPORARY;
      case "無限卡":
        return CardType.UNLIMITE;
      case "虛擬卡":
        return CardType.VIRTUAL;
      default:
        return null;
    }
  }

  /** 專屬RoomCardIssurViewer的filter 是否發卡comboBox的value轉換 */
  public static Boolean getYesOrNoBoolean(String str) {
    switch (str) {
      case "是":
        return true;
      case "否":
        return false;
      default:
        return null;
    }
  }

  public static String getCardStatus(CardStatus cardStatus) {
    switch (cardStatus) {
      case ENABLE:
        return "啟用";
      case DISABLE:
        return "停用";
      case RETURN:
        return "已歸還";
      case LOST:
        return "遺失";
      case BLACKLIST:
        return "黑名單";
      case LOGOUT:
        return "註銷";
      default:
        return "";
    }
  }

  /** 取得DateField、TimeField的組合起來的Date */
  public static Date getTimeFromDateAndTime(Date date, Date time) {
    DateTimeFormat dayFormat = DateTimeFormat.getFormat("yyyy-MM-dd");
    DateTimeFormat timeFormat = DateTimeFormat.getFormat("HH:mm:ss");
    DateTimeFormat format = DateTimeFormat.getFormat("yyyy-MM-dd HH:mm:ss");
    String dateString = dayFormat.format(date) + " " + timeFormat.format(time);

    return format.parse(dateString);
  }

  /** 取得當下10年後的時間 */
  public static Date getAfter10yearTime() {
    Date result = new Date();
    int year = result.getYear();
    result.setYear(year + 10);

    return result;
  }

  public static String getFormattedSignalType(SignalType signalType) {
    if (signalType == null) {
      return "";
    }
    switch (signalType) {
      case ANALOG_IN:
        return "類比輸入";
      case DIGITAL_IN:
        return "數位輸入";
      case ANALOG_OUT:
        return "類比輸出";
      case DIGITAL_OUT:
        return "數位輸出";
      default:
        return "";
    }
  }
  //  "CARD_READER_3"  "交控中心卡機" -> none
  //  "CARD_READER_5"  "北口卡機"    -> CCTV-NG-OutDoor
  //  "CARD_READER_9"  "南口卡機"    -> CCTV-SG-Door
  //  "CARD_READER_4"  "會議室卡機"   -> none
  //  "CARD_READER_1"  "機房大門卡機"  -> CCTV-AS-Tc
  //  "CARD_READER_10" "草埔卡機"     -> CCTV-CP-Door
  //  "CARD_READER_11" "豎井卡機"     -> CCTV-SJ-InDoor
  //  "CARD_READER_13" "豎井大門卡機"  -> CCTV-SJ-OutDoor
  //  "CARD_READER_12" "豎井後門卡機"  -> CCTV-SJ-OutSide
  //  "CARD_READER_6"  "輔1卡機"     -> CCTV-S1-Door
  //  "CARD_READER_7"  "輔2卡機"     -> CCTV-S2-Door
  //  "CARD_READER_8"  "輔3卡機"     -> CCTV-S3-Door
  //  "CARD_READER_2"  "電視牆卡機"   -> CCTV-AS-Monitor
  public static String getMappingCctvDevice(String deviceName) {
    if (deviceName == null) {
      return null;
    }
    String resultName = null;
    if ("CARD_READER_1".equals(deviceName)) {
      resultName = "CCTV-AS-Tc";
    } else if ("CARD_READER_2".equals(deviceName)) {
      resultName = "CCTV-AS-Monitor";
    } else if ("CARD_READER_5".equals(deviceName)) {
      resultName = "CCTV-NG-OutDoor";
    } else if ("CARD_READER_6".equals(deviceName)) {
      resultName = "CCTV-S1-Door";
    } else if ("CARD_READER_7".equals(deviceName)) {
      resultName = "CCTV-S2-Door";
    } else if ("CARD_READER_8".equals(deviceName)) {
      resultName = "CCTV-S3-Door";
    } else if ("CARD_READER_9".equals(deviceName)) {
      resultName = "CCTV-SG-Door";
    } else if ("CARD_READER_10".equals(deviceName)) {
      resultName = "CCTV-CP-Door";
    } else if ("CARD_READER_11".equals(deviceName)) {
      resultName = "CCTV-SJ-InDoor";
    } else if ("CARD_READER_12".equals(deviceName)) {
      resultName = "CCTV-SJ-OutSide";
    } else if ("CARD_READER_13".equals(deviceName)) {
      resultName = "CCTV-SJ-OutDoor";
    }
    return resultName;
  }
}
