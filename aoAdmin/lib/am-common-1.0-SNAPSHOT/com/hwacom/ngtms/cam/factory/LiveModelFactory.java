/*
 * © HwaCom Systems Inc. 2015
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.cam.factory;

import com.google.gwt.i18n.client.DateTimeFormat;
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
 *     <when-type-is class="com.hwacom.ngtms.common.am.factory.LiveModelFactory"/>
 * </replace-with>
 * }</pre>
 *
 * @param <T> 要呈現的資料型態
 * @see LiveViewer
 * @author monty.pan
 */
public interface LiveModelFactory<T> {
  /** 圖表時間點位數量 */
  public static final int DATE_STEP = 12;

  public static final String SEPARATOR = "_";
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

  /**
   * {@link LiveViewer} 會將自身 instance 透過這個 method 傳給實做 class， 如此實做 class 才有辦法控制 {@link
   * LiveViewer}（例如： {@link LiveViewer#update(List, List)}）。
   *
   * @param view
   */
  void setView(LiveViewer<T> view);

  /** @return {@link TimeLineChart} 的 {@link TimeAxis} 需要的 formater。 */
  DateTimeFormat getFormate();

  /** @return 每個 step 的間隔時間，單位是秒 */
  int getPeriod();

  /**
   * 取得更新資料後，請呼叫 {@link LiveViewer#update(List, List)}。
   *
   * @param start
   * @param chart
   */
  void trigger(Date start, TimeLineChart chart);
}
