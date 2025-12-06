/*
 * © HwaCom Systems Inc. 2018
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.common.am.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsDate;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiConstructor;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.core.client.XTemplates;
import com.sencha.gxt.widget.core.client.Composite;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.BiConsumer;

public class DateRangePicker extends Composite {

  private static DateRangePickerUiBinder uiBinder = GWT.create(DateRangePickerUiBinder.class);

  interface DateRangePickerUiBinder extends UiBinder<Widget, DateRangePicker> {}

  interface Template extends XTemplates {
    @XTemplate("<input type=\"text\" id=\"{id}\" size=\"{size}\" />")
    SafeHtml inputText(String id, int size);
  }

  private Template template = GWT.create(Template.class);

  private String id;

  private String selector;

  private List<BiConsumer<Date, Date>> callbacks;

  private int limitDays;

  private boolean timePicker = false;

  private int timePickerIncrement = 10;

  private boolean singleDatePicker = false;

  private String format = "YYYY-MM-DD HH:mm";

  private Date startDate = null;

  private Date endDate = null;

  @UiField HTML html;

  /**
   * @param timePicker 啟用/停用 時間選擇
   * @param singleDatePicker 單一日期選擇表
   */
  @UiConstructor
  public DateRangePicker(boolean timePicker, boolean singleDatePicker) {
    this.timePicker = timePicker;
    this.format = timePicker ? "YYYY-MM-DD HH:mm" : "YYYY-MM-DD";
    this.singleDatePicker = singleDatePicker;
    this.id = DOM.createUniqueId();
    this.selector = "#" + id;
    initWidget(uiBinder.createAndBindUi(this));
    html.setHTML(template.inputText(id, getSize()));
  }

  private int getSize() {
    if (timePicker) {
      return singleDatePicker ? 11 : 28;
    } else {
      return singleDatePicker ? 8 : 18;
    }
  }

  /**
   * @param limitDays limitDays 起訖日期不能超過指定的天數，小於等於 1 代表不限制，例如 limitDays 為 7 表示結束時間會被限制在開始時間的 7 天內
   */
  public void setLimitDays(int limitDays) {
    this.limitDays = limitDays;
  }

  /** @param timePickerIncrement timePickerIncrement 時間選擇區間 */
  public void setTimePickerIncrement(int timePickerIncrement) {
    this.timePickerIncrement = timePickerIncrement;
  }

  public void addValueChangeCallback(BiConsumer<Date, Date> callback) {
    if (callbacks == null) {
      callbacks = new ArrayList<>();
    }
    callbacks.add(callback);
  }

  public Date getStartDate() {
    JsDate date = getJsStartDate(selector);
    return toDate(date);
  }

  private Date toDate(JsDate date) {
    return new Date((long) date.getTime());
  }

  private JsDate toJsDate(Date date) {
    return JsDate.create(date.getTime());
  }

  private native JsDate getJsStartDate(String selector) /*-{
    return $wnd.jQuery(selector).data('daterangepicker').startDate.toDate();
  }-*/;

  public void setStartDate(Date date) {
    startDate = date;
    if (this.isAttached()) {
      setJsStartDate(toJsDate(date), selector);
    }
  }

  private native void setJsStartDate(JsDate date, String selector) /*-{
  	$wnd.jQuery(selector).data('daterangepicker').setStartDate(date);
  }-*/;

  public Date getEndDate() {
    JsDate date = getJsEndDate(selector);
    return toDate(date);
  }

  private native JsDate getJsEndDate(String selector) /*-{
    return $wnd.jQuery(selector).data('daterangepicker').endDate.toDate();
  }-*/;

  public void setEndDate(Date date) {
    endDate = date;
    if (this.isAttached()) {
      setJsEndDate(toJsDate(date), selector);
    }
  }

  private native void setJsEndDate(JsDate date, String selector) /*-{
    $wnd.jQuery(selector).data('daterangepicker').setEndDate(date);
  }-*/;

  @Override
  protected void onAttach() {
    super.onAttach();
    init(selector);
    if (startDate != null) {
      setJsStartDate(toJsDate(startDate), selector);
    }
    if (endDate != null) {
      setJsEndDate(toJsDate(endDate), selector);
    }
  }

  private native void init(String selector) /*-{
    var self = this;
    $wnd.jQuery(selector).daterangepicker({
        timePicker: self.@com.hwacom.ngtms.common.am.view.DateRangePicker::timePicker,
        timePickerIncrement: self.@com.hwacom.ngtms.common.am.view.DateRangePicker::timePickerIncrement,
        timePicker24Hour: true,
        singleDatePicker: self.@com.hwacom.ngtms.common.am.view.DateRangePicker::singleDatePicker,
        locale: {format: self.@com.hwacom.ngtms.common.am.view.DateRangePicker::format},
        isInvalidDate: function(date) {
          if (self.@com.hwacom.ngtms.common.am.view.DateRangePicker::limitDays <= 1) {
            return false;
          } else {
            var startDate = $wnd.jQuery(selector).data('daterangepicker').startDate;
            return date.diff(startDate, 'days') >= self.@com.hwacom.ngtms.common.am.view.DateRangePicker::limitDays;
          }
        }
    }, function(start, end, label) {
        self.@com.hwacom.ngtms.common.am.view.DateRangePicker::callback(Lcom/google/gwt/core/client/JsDate;Lcom/google/gwt/core/client/JsDate;)(start.toDate(), end.toDate());
    });
  }-*/;

  private void callback(JsDate start, JsDate end) {
    if (callbacks == null) {
      return;
    }
    for (BiConsumer<Date, Date> callback : callbacks) {
      callback.accept(toDate(start), toDate(end));
    }
  }
}
