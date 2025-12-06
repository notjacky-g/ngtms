/*
 * © HwaCom Systems Inc. 2013
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.hcce.am.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.EditorError;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.CalendarUtil;
import com.sencha.gxt.widget.core.client.container.HorizontalLayoutContainer.HorizontalLayoutData;
import com.sencha.gxt.widget.core.client.form.DateField;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.form.TimeField;
import com.sencha.gxt.widget.core.client.form.Validator;
import com.sencha.gxt.widget.core.client.form.validator.EmptyValidator;
import com.sencha.gxt.widget.core.client.form.validator.EmptyValidator.EmptyMessages;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 提供包含 {@link DateField} 與 {@link TimeField} 的 {@link FieldLabel}。 {@link #getValue()} 會回傳結合 {@link
 * DateField} 與 {@link TimeField} 的 {@link Date}。
 *
 * <p>{@link DateField} 預設有掛上 {@link EmptyValidator}， 有額外需求可用 {@link #addDateValidator(Validator)} 與
 * {@link #addTimeValidator(Validator)}。
 *
 * @author monty.pan
 */
public class DateTimeField extends FieldLabel {
  private static DateTimeFieldUiBinder uiBinder = GWT.create(DateTimeFieldUiBinder.class);

  interface DateTimeFieldUiBinder extends UiBinder<Widget, DateTimeField> {}

  private static EmptyValidator<Date> emptyValidator = new EmptyValidator<>();

  static {
    emptyValidator.setMessages(
        new EmptyMessages() {
          @Override
          public String blankText() {
            return "日期不可空白"; //I18N
          }
        });
  }

  @UiField DateField date;
  @UiField TimeField time;
  @UiField HorizontalLayoutData dateLayoutData;
  @UiField HorizontalLayoutData timeLayoutData;

  public DateTimeField() {
    this.setWidget(uiBinder.createAndBindUi(this));
    date.addValidator(emptyValidator);
  }

  public void addDateValidator(Validator<Date> validator) {
    date.addValidator(validator);
  }

  public void addTimeValidator(Validator<Date> validator) {
    time.addValidator(validator);
  }

  public void setTimeIncrement(int increment) {
    time.setIncrement(increment);
    this.forceLayout();
  }

  /**
   * 設定 {@link DateField} 的 {@link HorizontalLayoutData} 的 width。
   *
   * @param width 比照 {@link HorizontalLayoutData#setWidth(double)}
   */
  public void setDateFieldWidth(double width) {
    dateLayoutData.setWidth(width);
    this.forceLayout();
  }

  /**
   * 設定 {@link TimeField} 的 {@link HorizontalLayoutData} 的 width。
   *
   * @param width 比照 {@link HorizontalLayoutData#setWidth(double)}
   */
  public void setTimeFieldWidth(double width) {
    timeLayoutData.setWidth(width);
    this.forceLayout();
  }

  public Date getValue() throws DateTimeValidException {
    if (date.validate() && time.validate()) {
      return DateTimeUtil.getValue(date, time);
    } else {
      ArrayList<String> allError = new ArrayList<>();
      for (EditorError error : date.getErrors()) {
        allError.add("日期錯誤：" + error.getMessage()); //I18N
      }
      for (EditorError error : time.getErrors()) {
        allError.add("時間錯誤：" + error.getMessage()); //I18N
      }
      throw new DateTimeValidException(getText(), allError);
    }
  }

  public void setValue(Date value) {
    date.setValue(value);
    time.setValue(CalendarUtil.copyDate(value));
    //另外複製一個 instance，避免 DateField 與 TimeField 共用 instance
    //像 DateField 裡頭就有清空時間的習慣，所以也沒辦法不用 DateTimeUtil.getValue()
  }

  /**
   * {@link DateTimeField#getValue()} 的專用 exception。 可提供 FiledLabel 的 title 值（{@link #getTitle()}） 與
   * validate 的結果（{@link #getErrors()}、{@link #getErrorString()}）。
   */
  @SuppressWarnings("serial")
  public static class DateTimeValidException extends Exception {
    private String title;
    private List<String> errors;

    public DateTimeValidException(String title, List<String> allError) {
      this.title = title;
      this.errors = allError;
    }

    /** @return {@link FieldLabel#getText()} */
    public String getTitle() {
      return title;
    }

    public List<String> getErrors() {
      return errors;
    }

    /** @return 將 errors 用 \n 的方式組合成一個字串。 */
    public String getErrorString() {
      StringBuffer result = new StringBuffer();
      for (String error : errors) {
        result.append(error + "\n");
      }
      return result.toString();
    }
  }

  public void setTimeEditable(boolean editable) {
    time.setEditable(editable);
  }
}
