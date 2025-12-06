/*
 * © HwaCom Systems Inc. 2015
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.cam.client.ui.chart;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.hwacom.ngtms.cam.client.event.AmEventCenter;
import com.hwacom.ngtms.cam.client.event.ChartItemSelectionEvent;
import com.hwacom.ngtms.cam.client.ui.chart.TimeLineChart.TimeLineData;
import com.sencha.gxt.chart.client.chart.Chart;
import com.sencha.gxt.chart.client.chart.Legend;
import com.sencha.gxt.chart.client.chart.axis.NumericAxis;
import com.sencha.gxt.chart.client.chart.axis.TimeAxis;
import com.sencha.gxt.chart.client.chart.event.SeriesSelectionEvent;
import com.sencha.gxt.chart.client.chart.event.SeriesSelectionEvent.SeriesSelectionHandler;
import com.sencha.gxt.chart.client.chart.series.LineSeries;
import com.sencha.gxt.chart.client.chart.series.SeriesLabelProvider;
import com.sencha.gxt.chart.client.chart.series.SeriesToolTipConfig;
import com.sencha.gxt.chart.client.draw.Color;
import com.sencha.gxt.chart.client.draw.path.PathSprite;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.core.client.util.DateWrapper;
import com.sencha.gxt.data.shared.LabelProvider;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * 提供一個只能顯示 {@link TimeLineData} 的折線圖 {@link Chart}， X 軸限定為時間， 且可透過 {@link #setStartDate(Date)} 跟
 * {@link #setEndDate(Date)} 來顯示指定時間區間內的資料。
 *
 * <p>TimeLineData 可以允許不定數量的資料筆數（對應到線條數量）。 透過 {@link #setSeriesName(List)} 設定各條線的名稱，
 * 其餘如線條顏色、端點形狀都會自動產生（by {@link SeriesGenerator}）。 caller 不需要準備 {@link ValueProvider}， 但 caller
 * 必須自己控制好 series name list 與 {@link TimeLineData} 的 index 對應關係。
 *
 * <p>直接繼承 {@link Chart}，是考量到如果 caller 需要進行特製化功能時， 有辦法可以透過原本 API 操作。
 *
 * <p>其他預設行為：
 *
 * <ul>
 *   <li>setAnimated(true)
 *   <li>Y 軸為斑馬 style（#ddd / #bbb 相間）
 * </ul>
 *
 * @author monty.pan
 */
// XXX 再多一個相似的 chart 就要抽 parent class
public class TimeLineChart extends Chart<TimeLineData> {

  private NumericAxis<TimeLineData> axis = new VdsNumericAxis();

  private TimeAxis<TimeLineData> timeAxis = new TimeAxis<>();

  public TimeLineChart(final DateTimeFormat formater) {
    ListStore<TimeLineData> store =
        new ListStore<>(
            new ModelKeyProvider<TimeLineData>() {
              @Override
              public String getKey(TimeLineData item) {
                return formater.format(item.getDate());
              }
            });
    this.setStore(store);
    this.setAnimated(true);

    // X 軸顯示設定，以傳入的 formater 來轉換 Date 數值
    timeAxis.setField(
        new ValueProvider<TimeLineData, Date>() {
          @Override
          public Date getValue(TimeLineData object) {
            return object.getDate();
          }

          @Override
          public void setValue(TimeLineData object, Date value) {}

          @Override
          public String getPath() {
            return "timeAxisProvider";
          }
        });
    timeAxis.setLabelProvider(
        new LabelProvider<Date>() {
          @Override
          public String getLabel(Date item) {
            return formater.format(item);
          }
        });
    timeAxis.setLabelOverlapHiding(true);
    timeAxis.setPosition(Position.BOTTOM);
    this.addAxis(timeAxis);

    axis.setPosition(Position.LEFT);
    // 設定 Y 軸的斑馬 style
    PathSprite odd = new PathSprite();
    odd.setOpacity(1);
    odd.setFill(new Color("#ddd"));
    odd.setStroke(new Color("#bbb"));
    odd.setStrokeWidth(0.5);
    axis.setGridOddConfig(odd);
    axis.setDisplayGrid(true);
    // //
    this.addAxis(axis);

    Legend<TimeLineData> legend = new Legend<TimeLineData>();
    legend.setItemHighlighting(true);
    legend.setItemHiding(true);
    this.setLegend(legend);
  }

  public TimeLineChart(final DateTimeFormat formater, final int interval) {
    ListStore<TimeLineData> store =
        new ListStore<>(
            new ModelKeyProvider<TimeLineData>() {
              @Override
              public String getKey(TimeLineData item) {
                return formater.format(item.getDate());
              }
            });
    this.setStore(store);
    this.setAnimated(true);

    // X 軸顯示設定，以傳入的 formater 來轉換 Date 數值
    timeAxis.setField(
        new ValueProvider<TimeLineData, Date>() {
          @Override
          public Date getValue(TimeLineData object) {
            return object.getDate();
          }

          @Override
          public void setValue(TimeLineData object, Date value) {}

          @Override
          public String getPath() {
            return "timeAxisProvider";
          }
        });
    timeAxis.setLabelProvider(
        new LabelProvider<Date>() {
          @Override
          public String getLabel(Date item) {
            return formater.format(item);
          }
        });
    timeAxis.setLabelOverlapHiding(true);
    timeAxis.setPosition(Position.BOTTOM);
    this.addAxis(timeAxis);

    axis.setPosition(Position.LEFT);
    // 設定 Y 軸的斑馬 style
    PathSprite odd = new PathSprite();
    odd.setOpacity(1);
    odd.setFill(new Color("#ddd"));
    odd.setStroke(new Color("#bbb"));
    odd.setStrokeWidth(0.5);
    axis.setGridOddConfig(odd);
    axis.setDisplayGrid(true);
    axis.setMaximum(2000);
    axis.setMinimum(0);
    axis.setMinorTickSteps(interval);
    // //
    this.addAxis(axis);

    Legend<TimeLineData> legend = new Legend<TimeLineData>();
    legend.setItemHighlighting(true);
    legend.setItemHiding(true);
    this.setLegend(legend);
  }

  public void resetSeries(List<TimeLineData> list, List<String> deviceNames4SeriesOrder) {
    getStore().clear();
    getLegend().clear();
    axis.getFields().clear();
    axis.clear();
    timeAxis.clear();
    for (int i = this.getSeries().size(); i > 0; i--) {
      // 沒辦法直接 this.getSeries().clear()，只能一個一個移。
      this.removeSeries(0);
    }

    Set<String> deviceNames = new HashSet<>();
    Map<String, LineSeries<TimeLineData>> serieses = new HashMap<>();
    for (TimeLineData each : list) {
      for (Entry<String, Pair> entry : each.getMap().entrySet()) {
        String deviceName = entry.getKey();
        if (deviceNames.contains(deviceName)) {
          continue;
        }
        deviceNames.add(deviceName);
        ValueProvider<TimeLineData, Number> valueProvider =
            createSeriesValueProvider(deviceName, entry.getValue().getDisplayName());
        axis.addField(valueProvider);
        serieses.put(deviceName, createSeries(deviceNames.size(), valueProvider));
      }
    }
    for (String each : deviceNames4SeriesOrder) {
      if (serieses.get(each) == null) {
        continue;
      }
      addSeries(serieses.get(each));
    }

    timeAxis.drawAxis(false);
  }

  private ValueProvider<TimeLineData, Number> createSeriesValueProvider(
      final String deviceName, final String displayName) {
    return new ValueProvider<TimeLineChart.TimeLineData, Number>() {
      @Override
      public void setValue(TimeLineData object, Number value) {}

      @Override
      public Number getValue(TimeLineData object) {
        Pair pair = object.getMap().get(deviceName);
        if (pair == null || pair.getValue() == null) {
          return Double.NaN;
        }
        return pair.getValue();
      }

      @Override
      public String getPath() {
        return displayName;
      }
    };
  }

  private LineSeries<TimeLineData> createSeries(
      int anyNumberForColor, ValueProvider<TimeLineData, Number> valueProvider) {
    LineSeries<TimeLineData> series = SeriesGenerator.gen(anyNumberForColor);
    series.setYAxisPosition(Position.LEFT);
    series.setYField(valueProvider);
    series.setGapless(false);
    series.setToolTipConfig(createSeriesToolTip());
    series.addSeriesSelectionHandler(createSeriesSelectionHandler());
    return series;
  }

  private SeriesToolTipConfig<TimeLineData> createSeriesToolTip() {
    SeriesToolTipConfig<TimeLineData> toolTip = new SeriesToolTipConfig<>();
    toolTip.setTrackMouse(true);
    toolTip.setHideDelay(200);
    toolTip.setLabelProvider(
        new SeriesLabelProvider<TimeLineData>() {
          @Override
          public String getLabel(
              TimeLineData item,
              ValueProvider<? super TimeLineData, ? extends Number> valueProvider) {
            return String.valueOf(valueProvider.getValue(item));
          }
        });
    return toolTip;
  }

  private SeriesSelectionHandler<TimeLineData> createSeriesSelectionHandler() {
    return new SeriesSelectionHandler<TimeLineData>() {
      @Override
      public void onSeriesSelection(SeriesSelectionEvent<TimeLineData> event) {
        AmEventCenter.fireEvent(
            new ChartItemSelectionEvent(
                getSeries().indexOf(event.getSource()), event.getItem().getDate()));
      }
    };
  }

  /**
   * @param nameList
   * @deprecated use {@link #resetSeries} instead
   */
  @Deprecated
  public void setSeriesName(List<String> nameList) {
    // ==== 先重設相關的 attribute ==== //
    this.getStore().clear();
    this.getLegend().clear();
    /*
     * 純看邏輯上好像可以不做 Legend.clear()，反正一開始 setLegend() 之後也沒在操作， 都是 Chart 自己從
     * series 去設定對應的顯示。實際上如果沒作 legend.clear()， 在之後作 chart.redrawChart()
     * 的時候會炸錯誤，錯誤來源是： legend.updatePosition() 中的 border.redraw()， 實際是
     * SVG.renderSprite() 要對 border 決定 zIndex 時 出現
     * ArrayIndexOutOfBoundsException（至於為什麼就... 饒了我吧 Orz） 反過來說，要求
     * Legend.clear() 好像也很合理。
     */
    axis.getFields().clear(); // 清除 Y 軸的 ValueProvider
    axis.clear(); // 清除 Y 軸的 sprite 們
    timeAxis.clear(); // 清除 X 軸的 sprite 們。
    // field 不用清是因為 timeAxis 自己會依照資料作處理

    for (int i = this.getSeries().size(); i > 0; i--) {
      // 沒辦法直接 this.getSeries().clear()，只能一個一個移。
      this.removeSeries(0);
    }
    // ======== //

    // 每個點位會顯示該數值的tooTip
    for (int i = 0; i < nameList.size(); i++) {
      LineSeries<TimeLineData> series = SeriesGenerator.gen(i);
      ValueProvider<TimeLineData, Number> vp = new LineDataValueProvider(i, nameList.get(i));
      axis.addField(vp);
      series.setYAxisPosition(Position.LEFT);
      series.setYField(vp);
      series.setGapless(false);
      SeriesToolTipConfig<TimeLineData> toolTip = new SeriesToolTipConfig<TimeLineData>();
      toolTip.setTrackMouse(true);
      toolTip.setHideDelay(200);
      toolTip.setLabelProvider(
          new SeriesLabelProvider<TimeLineData>() {
            @Override
            public String getLabel(
                TimeLineData item,
                ValueProvider<? super TimeLineData, ? extends Number> valueProvider) {
              return String.valueOf(valueProvider.getValue(item));
            }
          });
      series.setToolTipConfig(toolTip);

      series.addSeriesSelectionHandler(
          new SeriesSelectionHandler<TimeLineData>() {
            @Override
            public void onSeriesSelection(SeriesSelectionEvent<TimeLineData> event) {
              AmEventCenter.fireEvent(
                  new ChartItemSelectionEvent(
                      getSeries().indexOf(event.getSource()), event.getItem().getDate()));
            }
          });

      this.addSeries(series);
    }

    // 透過 drawAxis() 裡頭的 applyData() 強制讓 timeAxis 重新設定 chart.setSubstore()
    // 否則會抓到不正確的 store
    timeAxis.drawAxis(false);
  }

  public void setStartDate(Date date) {
    timeAxis.setStartDate(date);
  }

  public Date getStartDate() {
    return timeAxis.getStartDate();
  }

  public void setEndDate(Date date) {
    timeAxis.setEndDate(date);
  }

  public Date getEndDate() {
    return timeAxis.getEndDate();
  }

  public void setAxisInterval(int interval) {
    axis.setInterval(Double.valueOf(interval));
  }

  /**
   * 讓時間區間往後移動指定的秒數。
   *
   * @param second 移動量，單位為秒。
   */
  public void nextTimeInterval(int second) {
    setStartDate(new DateWrapper(getStartDate()).addSeconds(second).asDate());
    setEndDate(new DateWrapper(getEndDate()).addSeconds(second).asDate());
  }

  // 修改時間軸的間距
  public void setAxisLabelStep(int step) {
    timeAxis.setLabelStepRatio(step);
  }

  /**
   * 提供 TimeLineChart 使用的專屬資料型態。 因為 chart 的 X 軸為時間軸，因此將 date 視為 id。
   *
   * <p>{@link #dataList} 中是每個 X 軸各折線的數值，順序需與 {@link LineChart#setSeriesName(List)} 的傳入值相互對應。
   */
  public static class TimeLineData {

    private Date date;

    /** @deprecated use {@link #map} instead. */
    @Deprecated private ArrayList<Number> dataList = new ArrayList<>();

    private Map<String, Pair> map = new HashMap<>();

    public TimeLineData(Date date) {
      this.date = date;
    }

    public Date getDate() {
      return date;
    }

    public void setDate(Date date) {
      this.date = date;
    }

    /** @deprecated use {@link #getMap} instead. */
    @Deprecated
    public ArrayList<Number> getDataList() {
      return dataList;
    }

    public Map<String, Pair> getMap() {
      return map;
    }

    public void setMap(Map<String, Pair> map) {
      this.map = map;
    }

    /**
     * @param date
     * @param size
     * @return 產生一個有 <code>size</code> 條線、內容值均為 {@link Double#NaN} 的 node。
     */
    @Deprecated
    public static TimeLineData genEmpty(Date date, int size) {
      TimeLineData result = new TimeLineData(date);
      for (int i = 0; i < size; i++) {
        result.getDataList().add(Double.NaN);
      }
      return result;
    }
  }

  public static class Pair {
    private String displayName;
    private Number value;

    public Pair(String displayName, Number value) {
      this.displayName = displayName;
      this.value = value;
    }

    public String getDisplayName() {
      return displayName;
    }

    public void setDisplayName(String displayName) {
      this.displayName = displayName;
    }

    public Number getValue() {
      return value;
    }

    public void setValue(Number value) {
      this.value = value;
    }
  }

  private class LineDataValueProvider implements ValueProvider<TimeLineData, Number> {
    private int index;
    private String name;

    public LineDataValueProvider(int index, String name) {
      this.index = index;
      this.name = name;
    }

    @Override
    public Number getValue(TimeLineData object) {
      return object.getDataList().get(index);
    }

    @Override
    public void setValue(TimeLineData object, Number value) {
      object.getDataList().set(index, value);
    }

    @Override
    public String getPath() {
      return name;
    }
  }

  private class VdsNumericAxis extends NumericAxis<TimeLineData> {

    @Override
    protected void applyData() {
      super.applyData();
      if (!(to == 0 && from == 0) && to - from < steps) {
        setMaximum(to);
        setMinimum(from);
        setSteps((int) (to - from));
        super.applyData();

        setMaximum(Double.NaN);
        setMinimum(Double.NaN);
        setSteps(-1);
      }
    }
  }
}
