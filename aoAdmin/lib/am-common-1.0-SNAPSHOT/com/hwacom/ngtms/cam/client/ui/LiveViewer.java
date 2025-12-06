/*
 * © HwaCom Systems Inc. 2015
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.cam.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Widget;
import com.hwacom.ngtms.c.shared.dto.DeviceConfigDTO;
import com.hwacom.ngtms.cam.client.event.AmEventCenter;
import com.hwacom.ngtms.cam.client.event.DeviceConfigDeselectEvent;
import com.hwacom.ngtms.cam.client.event.DeviceConfigDeselectHandler;
import com.hwacom.ngtms.cam.client.ui.chart.TimeLineChart;
import com.hwacom.ngtms.cam.client.ui.chart.TimeLineChart.TimeLineData;
import com.hwacom.ngtms.cam.factory.LiveModelFactory;
import com.hwacom.ngtms.cam.view.Messages;
import com.sencha.gxt.core.client.Style.LayoutRegion;
import com.sencha.gxt.core.client.util.DateWrapper;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.SplitBar;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.event.HideEvent;
import com.sencha.gxt.widget.core.client.event.HideEvent.HideHandler;
import com.sencha.gxt.widget.core.client.event.ShowEvent;
import com.sencha.gxt.widget.core.client.event.ShowEvent.ShowHandler;
import com.sencha.gxt.widget.core.client.event.SplitBarDragEvent;
import com.sencha.gxt.widget.core.client.event.SplitBarDragEvent.SplitBarDragHandler;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.grid.GroupingView;
import com.sencha.gxt.widget.core.client.info.Info;
import com.sencha.gxt.widget.core.client.tips.ToolTipConfig;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 提供共用的「即時資料顯示」widget。 各子系統無法共用的部份，透過 {@link LiveModelFactory} 實做 deferred binding 來達成。
 *
 * <p>每次新增 / 移除 device，會 reset 成為「初始狀態」（呼叫 {@link #start()}）。 在初始狀態，chart / grid 會清空，接著會間隔 {@link
 * LiveModelFactory#getPeriod()} 呼叫 {@link LiveModelFactory#trigger(java.util.Date)}， {@link
 * LiveModelFactory#trigger(java.util.Date)} 必須呼叫 {@link #update(List, List)} 以此更新 chart / grid。
 * 另外，為了避免 chart 圖過於龐雜，{@link #getDeviceList()} 將會只回傳前 {@value #MAX_DEVICE_AMOUNT} 筆的 device 資料。
 *
 * <p>此 widget 有註冊 {@link #addShowHandler(ShowHandler)}（觸發時變成「初始狀態」）、 {@link
 * #addHideHandler(HideHandler)}（觸發時呼叫 {@link #stop()}）。
 *
 * <p>則使用 {@link #setDeviceGrid(HasDeviceList)}，ui.xml 的寫法為
 *
 * <pre>{@code
 * <am:LiveView>
 *     <am:deviceGrid>
 *         <!-- custom widget -->
 *     </am:deviceGrid>
 * </am:LiveView>
 * }</pre>
 *
 * 目前自訂 widget 必須是 {@link ContentPanel} 且 implements {@link HasDeviceList}。 另外，自訂 widget 必須能處理 DnD
 * 行為並觸發 {@link DeviceConfigDeselectEvent}， 在這裡並不會對傳入 widget 作相關設定。
 *
 * @param <T> 要呈現的資料型態
 * @see TimeLineChart
 * @see LiveModelFactory
 * @author monty.pan
 */
//XXX Y 軸是否用直接指定區間的方式
public class LiveViewer<T> extends AmTab {
  /** {@link #grid} 高度的最大值 */
  private static final int GRID_MAX_HEIGHT = 500;
  /** {@link #grid} 高度的最小值 */
  private static final int GRID_MIN_HEIGHT = 50;
  /** 畫面上會顯示的 device 數量的最大值 */
  private static final int MAX_DEVICE_AMOUNT = 10;
  //圖表更新時間
  private static final int UPDATE_SECONDS = 60;

  private static LiveViewUiBinder uiBinder = GWT.create(LiveViewUiBinder.class);

  interface LiveViewUiBinder extends UiBinder<Widget, LiveViewer<?>> {}

  private final Messages messages = GWT.create(Messages.class);

  @UiField BorderLayoutContainer root;

  @UiField(provided = true)
  TimeLineChart chart;

  @UiField(provided = true)
  Grid<T> grid;

  @UiField VerticalLayoutContainer content;
  @UiField VerticalLayoutData gridLayoutData;
  @UiField GroupingView<T> gridView;

  private LiveModelFactory<T> model = GWT.create(LiveModelFactory.class);
  /**
   * 本來是可以不需要這個變數，但是考慮到無法保證 {@link DeviceConfigDeselectHandler} 觸發先後順序， 如果 {@link #deviceNameView}
   * 裡頭的 handler 後觸發，則畫面顯示就會不正確， 所以只能自己 keep 一份 instance 而不是用 {@link #deviceNameView} 的資料。
   */
  private ArrayList<DeviceConfigDTO> deviceList = new ArrayList<>();

  private Timer timer =
      new Timer() {
        @Override
        public void run() {
          chart.nextTimeInterval(UPDATE_SECONDS);
          model.trigger(genStartDate(), chart);
        }
      };

  public LiveViewer() {
    this.setTabTitle(messages.liveView_name());

    model.setView(this);

    grid =
        new Grid<>(
            new ListStore<>(model.getModelKeyProvider()),
            new ColumnModel<>(model.getColumnConfigList()));
    grid.getStore().addSortInfo(model.getSortInfo());

    /* 為了讓 grid 跟 chart 可能讓使用者動態調整大小，所以用了 SplitBar。
     * 無法讓整個 LiveView 用 BorderLayout 的原因是
     * BorderLayout 的 south 會佔滿整個寬度，與需求的畫面不合。
     */
    final SplitBar bar = new SplitBar(LayoutRegion.NORTH, grid);
    bar.setMinSize(GRID_MIN_HEIGHT);
    bar.setMaxSize(GRID_MAX_HEIGHT);
    bar.addSplitBarDragHandler(
        new SplitBarDragHandler() {
          @Override
          public void onDragEvent(SplitBarDragEvent event) {
            if (!event.isStart()) {
              gridLayoutData.setHeight(event.getSize());
              content.forceLayout();
            }
          }
        });

    chart = new TimeLineChart(model.getFormate(), 200);

    initWidget(uiBinder.createAndBindUi(this));

    //預設以第一個 column config 作 grouping
    gridView.groupBy(grid.getColumnModel().getColumn(0));

    if (model.optionWidget() != null) {
      //看起來 insert() 沒有擋 null 的樣子，所以保險起見自己擋
      //找不到簡單用 ui.xml 作 layout 的方法，改用 insert 的方式卡在最上頭
      content.insert(model.optionWidget(), 0);
    }

    //看得到的時候作 start()、看不到的時候就 stop()
    this.addShowHandler(
        new ShowHandler() {
          @Override
          public void onShow(ShowEvent event) {
            checkDeviceAmount();
            start();
          }
        });
    this.addHideHandler(
        new HideHandler() {
          @Override
          public void onHide(HideEvent event) {
            stop();
          }
        });

    AmEventCenter.addDeviceConfigDeselectHandler(
        new DeviceConfigDeselectHandler() {
          @Override
          public void onDeviceConfigDeselect(DeviceConfigDeselectEvent event) {
            removeDevice(event.getData());
          }
        });
  }

  /**
   * 增加 device 的時候清空 grid / chart，如果在 visible 的狀態就重新啟動定時機制。
   *
   * @param data 新增的 device list
   */
  public void addDevices(List<DeviceConfigDTO> data) {
    for (DeviceConfigDTO dc : data) {
      if (!deviceList.contains(dc)) {
        deviceList.add(dc);
      }
    }
    afterAdjustDevice();

    if (this.isVisible()) {
      checkDeviceAmount();
    }
  }

  /**
   * 取得目前選取的 DeviceConfig 清單。如果資料筆數超過 {@value #MAX_DEVICE_AMOUNT}，將會只回傳前 {@value #MAX_DEVICE_AMOUNT}
   * 筆資料
   *
   * @return
   */
  public List<DeviceConfigDTO> getDeviceList() {
    return deviceList.size() > MAX_DEVICE_AMOUNT
        ? deviceList.subList(0, MAX_DEVICE_AMOUNT)
        : deviceList;
  }

  /**
   * 清除 grid / chart 的既有資料，以傳入的資料重新繪製 grid / chart。 最後呼叫 {@link
   * TimeLineChart#nextTimeInterval(int)}。
   *
   * @param gridData grid 的資料
   * @param lineData chart 的資料
   */
  /* XXX 不是很重要的效能 issue：
   * 目前 {@link #update(List, List)} 的作法是清空 chart / grid 的資料重塞，
   * 這主要是要預防 client side 被堆積的資料塞到爆炸，但實際上每一次抓的資料有很大一部分都是重複的。
   * 理想狀況是後續只抓取所需的小時間區間... [下略]
   */
  public void update(List<T> gridData, List<TimeLineData> lineData) {
    grid.getStore().clear();
    grid.getStore().addAll(gridData);
    redrawChart(lineData);
  }

  /**
   * 單純以傳入資料更新 chart，不會影響 chart 的時間區間、也不會影響 grid 顯示。
   *
   * @param lineData
   */
  public void resetChart(List<TimeLineData> lineData) {
    chart.getStore().clear();
    this.redrawChart(lineData);
  }

  //修改時間軸的間距
  public void setTimeAxisLabelStep(int step) {
    chart.setAxisLabelStep(step);
  }

  //修改chart的tooltip
  public void setToolTip(ToolTipConfig tooltip) {
    chart.setToolTipConfig(tooltip);
  }

  /** @return 現在時間往回推 12 個 {@link LiveModelFactory#getPeriod()} 的時間。 */
  private Date genStartDate() {
    return new Date(new Date().getTime() - (12 * 60 * 1000));
  }

  private void start() {
    //沒有 device 就啥都不做
    if (deviceList.size() == 0) {
      return;
    }

    //設定 chart 的開始 / 結束時間
    chart.setStartDate(genStartDate());
    chart.setEndDate(new Date(new Date().getTime() + model.getPeriod() * 1000 * 3));
    model.trigger(chart.getStartDate(), chart);
    timer.scheduleRepeating(model.getPeriod() * 1000);
  }

  /** 清空 grid / chart 的 store 資料、timer 運作。 */
  private void stop() {
    grid.getStore().clear();
    chart.getStore().clear();
    timer.cancel();
  }

  private void redrawChart(List<TimeLineData> lineData) {
    if (lineData == null || lineData.size() < 2) {
      chart.mask(messages.message_noData());
      return;
    }

    //為了美觀，在尾巴補一個空點
    Date max = new Date(0);
    for (TimeLineData data : lineData) {
      if (max.before(data.getDate())) {
        max = data.getDate();
      }
    }
    lineData.add(new TimeLineData(new DateWrapper(max).addSeconds(model.getPeriod()).asDate()));

    chart.getStore().clear();
    chart.getStore().addAll(lineData);
    chart.unmask();
    chart.redrawChart();
  }

  private void removeDevice(List<DeviceConfigDTO> data) {
    deviceList.removeAll(data);
    //deviceGrid 自己會處理 remove，所以不用重複 remove
    afterAdjustDevice();
  }

  private void afterAdjustDevice() {
    grid.getStore().clear();
    chart.clearSurface();

    //看得到才重新啟動
    if (this.isVisible()) {
      timer.cancel(); //只有 timer.cancel() 沒作過，就不呼叫 stop() 了
      start();
    }
  }

  //由外部添加grid的資料
  public void addGridItem(T item) {
    if (deviceList.size() == 1) {
      if (!grid.getStore().getAll().contains(item)) {
        grid.getStore().add(item);
      }
    }
  }

  public void refreshGridView() {
    grid.getView().refresh(false);
  }

  //選取grid上指定的物件
  @SuppressWarnings("unchecked")
  public void setGridSelection(T data) {
    grid.getSelectionModel().select(false, data);
  }

  private void checkDeviceAmount() {
    if (deviceList.size() > MAX_DEVICE_AMOUNT) {
      Info.display(messages.message_notice(), messages.message_over10Device());
    }
  }
}
