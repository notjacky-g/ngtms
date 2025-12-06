/*
 * © HwaCom Systems Inc. 2015
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.c.shared;

import com.hwacom.ngtms.base.i18n.shared.MessageType;
import org.joda.time.LocalDate;

public enum DateClassType implements MessageType {
  Type1110,
  Type1121, //
  Type1122, //
  Type1123,
  Type1130, //
  Type1140, //
  Type1200,
  Type1221, //
  Type1222, //
  Type1223, //
  Type1300, //

  Type2010,
  Type2021, //
  Type2022, //
  Type2023, //
  Type2030, //
  Type2221, //
  Type2222, //
  Type2223, //
  Type2230; //

  public static DateClassType getTypeByString(String typeStr) {
    try {
      if (typeStr.length() > 4) {
        return DateClassType.valueOf(typeStr);
      }
      return DateClassType.valueOf("Type" + typeStr);
    } catch (Exception e) {
      return null;
    }
  }

  /**
   * 取得今日的日期類型
   *
   * @return
   */
  public static DateClassType getDateClassTypeOfToday() {

    // 小禮拜為每月奇數禮拜，大禮拜為每月偶數禮拜
    // 昭毅說明: 計算大小禮拜, 星期天為起始日
    // ****但應該是星期一為開始****
    LocalDate today = LocalDate.now();
    LocalDate startOfMonth = today.withDayOfMonth(1);
    int firstDayWeekOfMonth = startOfMonth.getWeekOfWeekyear();
    int todayWeekOfYeay = today.getWeekOfWeekyear();
    int weekOfMonth = todayWeekOfYeay - firstDayWeekOfMonth + 1;
    boolean isEvenWeek = Boolean.FALSE;
    if (weekOfMonth % 2 == 0) {
      isEvenWeek = Boolean.TRUE;
    }

    int todayOfWeek = today.getDayOfWeek();

    switch (todayOfWeek) {
      case 1:
        if (isEvenWeek) {
          // 今明皆平日, 昨日為大禮拜週日
          return Type1122;
        } else {
          // 今明皆平日, 昨日為小禮拜週日
          return Type1121;
        }
      case 2:
      case 3:
      case 4:
        return Type1140;
      case 5:
        if (isEvenWeek) {
          // 今日平日, 明日為2日假日(大禮拜)
          return Type1221;
        } else {
          // 今日平日, 明日為2日假日(小禮拜)
          return Type1222;
        }
      case 6:
        if (isEvenWeek) {
          // 今日假日, 明日為2日假日(大禮拜), 大禮拜週六
          return Type2221;
        } else {
          // 今日假日, 明日為2日假日(小禮拜), 小禮拜週六
          return Type2222;
        }
      case 7:
        if (isEvenWeek) {
          // 今日為2日假日最後一日(大禮拜), 大禮拜週日
          return Type2021;
        } else {
          // 今日為2日假日最後一日(小禮拜), 小禮拜週日
          return Type2022;
        }
    }
    return null;
  }

  @Override
  public String getMessageKeyPrefix() {
    return "dgs.DateClassType";
  }
}
