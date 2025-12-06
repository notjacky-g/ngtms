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
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Widget;
import com.hwacom.ngtms.c.shared.dto.DeviceConfigDTO;
import com.hwacom.ngtms.cam.client.event.AmEventCenter;
import com.hwacom.ngtms.cam.client.event.DeviceConfigDeselectEvent;
import com.hwacom.ngtms.cam.client.event.DeviceConfigDeselectHandler;
import com.hwacom.ngtms.cam.client.ui.chart.TimeLineChart;
import com.hwacom.ngtms.cam.client.ui.chart.TimeLineChart.TimeLineData;
import com.hwacom.ngtms.cam.factory.HistoryModelFactory;
import com.hwacom.ngtms.cam.view.Messages;
import com.hwacom.ngtms.common.am.view.DateRangePicker;
import com.sencha.gxt.core.client.util.DateWrapper;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.ShowEvent;
import com.sencha.gxt.widget.core.client.event.ShowEvent.ShowHandler;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.grid.GroupingView;
import com.sencha.gxt.widget.core.client.info.Info;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 提供共用的「歷史趨勢圖」widget。 各子系統無法共用的部份，透過 {@link HistoryModelFactory} 實作 deferred binding 來達成。 為了避免
 * chart 圖過於龐雜，{@link #getDeviceList()} 將會只回傳前 {@value #MAX_DEVICE_AMOUNT} 筆的 device 資料。
 *
 * <p>左方「已經選取設備」預設使用 {@link DeviceNameView}，如果要改為自訂的 widget， 則使用 {@link
 * #setDeviceGrid(HasDeviceList)}，ui.xml 的寫法為
 *
 * <pre>{@code
 * <am:HistoryView>
 *     <am:deviceGrid>
 *         <!-- custom widget -->
 *     </am:deviceGrid>
 * </am:HistoryView>
 * }</pre>
 *
 * 目前自訂 widget 必須是 {@link ContentPanel} 且 implements {@link HasDeviceList}。 另外，自訂 widget 必須能處理 DnD
 * 行為並觸發 {@link DeviceConfigDeselectEvent}， 在這裡並不會對傳入 widget 作相關設定。
 *
 * @see HistoryModelFactory
 * @author monty.pan
 */
//基本上是參考 LiveView，所以有些 LiveView 有寫的註解這邊可能就會拿掉
public class HistoryViewer<T> extends AmTab {
  /** 畫面上會顯示的 device 數量的最大值 */
  private static final int MAX_DEVICE_AMOUNT = 10;

  private static HistoryViewUiBinder uiBinder = GWT.create(HistoryViewUiBinder.class);

  interface HistoryViewUiBinder extends UiBinder<Widget, HistoryViewer<?>> {}

  private final Messages messages = GWT.create(Messages.class);

  @UiField BorderLayoutContainer root;
  @UiField ContentPanel mainPanel;
  @UiField VerticalLayoutContainer content;
  @UiField DateRangePicker dateRangePicker;

  @UiField(provided = true)
  TimeLineChart chart;

  @UiField(provided = true)
  Grid<T> grid;

  @UiField GroupingView<T> gridView;

  private HistoryModelFactory<T> model = GWT.create(HistoryModelFactory.class);
  private ArrayList<DeviceConfigDTO> deviceList = new ArrayList<>();

  public HistoryViewer() {
    this.setTabTitle(messages.historyView_name());

    model.setView(this);
    grid =
        new Grid<>(
            new ListStore<>(model.getModelKeyProvider()),
            new ColumnModel<>(model.getColumnConfigList()));
    grid.getStore().addSortInfo(model.getSortInfo());

    chart = new TimeLineChart(model.getFormate());

    initWidget(uiBinder.createAndBindUi(this));
    gridView.groupBy(grid.getColumnModel().getColumn(0));

    mainPanel.setHeading(model.getTitle());

    if (model.optionWidget() != null) {
      //第 0 個是固定的「起訖時間」panel
      content.insert(model.optionWidget(), 1);
    }

    //設定起訖時間的預設值，當天現在時間-6hr～現在時間
    Date endDate = new Date();
    dateRangePicker.setStartDate(new Date(endDate.getTime() - 6 * 60 * 60 * 1000));
    dateRangePicker.setEndDate(endDate);

    //變成顯示的時候要檢查已選取設備數量
    this.addShowHandler(
        new ShowHandler() {
          @Override
          public void onShow(ShowEvent event) {
            checkDeviceAmount();
            if (deviceList.size() > 0) {
              queryData();
            }
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

  public void update(List<T> gridData, List<TimeLineData> lineData) {
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

    grid.getStore().clear();
    grid.getStore().addAll(gridData);

    chart.getStore().clear();
    chart.getStore().addAll(lineData);
    chart.unmask();
    chart.redrawChart();
  }

  @UiHandler("submitBtn")
  void clickSubmit(SelectEvent se) {
    List<DeviceConfigDTO> deviceList = getDeviceList();

    if (deviceList.size() == 0) {
      Info.display(messages.message_inputError(), messages.message_noDevice());
      return;
    }

    queryData();
  }

  private void queryData() {
    Date startDate;
    Date endDate;
    startDate = dateRangePicker.getStartDate();
    endDate = dateRangePicker.getEndDate();
    if (endDate.before(startDate)) {
      Info.display(messages.message_inputError(), messages.message_timeError());
      return;
    }
    if ((endDate.getTime() - startDate.getTime()) > (model.getMaxPeriod() * 1000L)) {
      Info.display(messages.message_inputError(), model.getPeriodError());
      return;
    }

    chart.setStartDate(new Date(startDate.getTime() - (model.getPeriod() * 1000)));
    chart.setEndDate(
        new Date(endDate.getTime() + (model.getPeriod() * 1000))); // TODO maybe not need to do this
    model.fetch(startDate, endDate, chart);
  }

  private void removeDevice(List<DeviceConfigDTO> data) {
    deviceList.removeAll(data);
    afterAdjustDevice();
  }

  private void afterAdjustDevice() {
    grid.getStore().clear();
    chart.clearSurface();

    if (this.isVisible() && deviceList.size() > 0) {
      queryData();
    }
  }

  private void checkDeviceAmount() {
    if (deviceList.size() > MAX_DEVICE_AMOUNT) {
      Info.display(messages.message_notice(), messages.message_over10Device());
    }
  }
}
