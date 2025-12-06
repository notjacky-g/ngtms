/*
 * © HwaCom Systems Inc. 2015
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.cam.factory;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.hwacom.ngtms.cam.client.ui.HistoryViewer;
import com.hwacom.ngtms.cam.client.ui.LiveViewer;
import com.hwacom.ngtms.cam.client.ui.chart.TimeLineChart;
import com.sencha.gxt.chart.client.chart.axis.TimeAxis;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.Store.StoreSortInfo;
import com.sencha.gxt.widget.core.client.Component;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import java.util.Date;
import java.util.List;

/**
 * 子系統各自提供實做 class，並在 <code>gwt.xml</code> 用
 *
 * <pre>{@code
 * <replace-with class="PKG_NAME.實做class">
 *     <when-type-is class="com.hwacom.ngtms.common.am.factory.HistoryModelFactory"/>
 * </replace-with>
 * }</pre>
 *
 * @see HistoryViewer
 * @author monty.pan
 */
public interface HistoryModelFactory<T> {
  /** @return 提供設定選項的 widget，回傳 null 值表示不需要設定選項。 */
  Component optionWidget();

  /** @return grid 所需要的 ModelKeyProvider */
  ModelKeyProvider<T> getModelKeyProvider();

  /**
   * 注意：{@link LiveViewer} 預設會以第一個 {@link ColumnConfig} 作 grouping。
   *
   * @return grid 需要的 ColumnConfig list
   */
  List<ColumnConfig<T, ?>> getColumnConfigList();

  /** @return grid 需要的 SortInfo */
  StoreSortInfo<T> getSortInfo();

  void setView(HistoryViewer<T> view);

  /** @return {@link TimeLineChart} 的 {@link TimeAxis} 需要的 formater。 */
  DateTimeFormat getFormate();

  String getTitle();

  /** @return 每個 step 的間隔時間，單位是秒 */
  int getPeriod();

  /**
   * 使用者在 {@link HistoryViewer} 按下 submit 時，會檢查起訖時間是否大於此回傳值； 若是，則跳出 {@link #getPeriodError()} 的錯誤訊息。
   *
   * <p>這是為了避免查詢時間過久、以及 chart 圖呈現過多點（以預設解析度下，建議 150 點以內）。
   *
   * @return 起訖時間的最大間隔，單位為「秒」
   */
  int getMaxPeriod();

  /** @return 起訖時間超過 {@link #getMaxPeriod()} 的錯誤訊息 */
  String getPeriodError();

  /**
   * 以指定時間區間取得資料後，呼叫 {@link HistoryViewer#update(ArrayList)}
   *
   * @param startDate
   * @param endDate
   * @param chart
   */
  void fetch(Date startDate, Date endDate, TimeLineChart chart);
}
