/*
 * © HwaCom Systems Inc. 2013
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.hcce.am.view;

import com.google.common.base.Preconditions;
import com.google.gwt.user.datepicker.client.CalendarUtil;
import com.sencha.gxt.core.client.util.DateWrapper;
import com.sencha.gxt.widget.core.client.form.DateField;
import com.sencha.gxt.widget.core.client.form.TimeField;
import java.util.Date;

public class DateTimeUtil {

  @SuppressWarnings("deprecation")
  public static Date getValue(DateField dateField, TimeField timeField) {
    Date result = Preconditions.checkNotNull(dateField.getValue());
    CalendarUtil.resetTime(result);

    Date time = timeField.getValue();
    if (time != null) {
      result.setHours(time.getHours());
      result.setMinutes(time.getMinutes());
      result.setSeconds(time.getSeconds());
    }
    return result;
  }

  /**
   * @param date 指定日期
   * @return 指定日期當天 00:00:00 的 {@link Date} instance
   */
  public static Date dayStart(Date date) {
    Date result = CalendarUtil.copyDate(date);
    CalendarUtil.resetTime(result);
    return result;
  }

  /**
   * @param date 指定日期
   * @return 指定日期當天 23:59:59 的 {@link Date} instance
   */
  public static Date dayEnd(Date date) {
    return new DateWrapper(dayStart(date)).addDays(1).addSeconds(-1).asDate();
  }
}
