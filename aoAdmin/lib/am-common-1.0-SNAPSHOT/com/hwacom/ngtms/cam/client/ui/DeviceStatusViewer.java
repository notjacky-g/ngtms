/*
 * © HwaCom Systems Inc. 2015
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.cam.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.RepeatingCommand;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;
import com.hwacom.ngtms.c.shared.Direction;
import com.hwacom.ngtms.c.shared.dto.DeviceConfigDTO;
import com.hwacom.ngtms.c.shared.dto.DeviceStatusDTO;
import com.hwacom.ngtms.c.shared.dto.EmsDeviceStatusDTO;
import com.hwacom.ngtms.c.shared.dto.RoadLineDTO;
import com.hwacom.ngtms.cam.client.HomeEP;
import com.hwacom.ngtms.cam.factory.DeviceStatusFactory;
import com.hwacom.ngtms.cam.util.DataCenter;
import com.hwacom.ngtms.cam.util.DeviceStatusUtil;
import com.hwacom.ngtms.cam.util.DeviceStatusUtil.ColumnGroup;
import com.hwacom.ngtms.cam.view.Messages;
import com.hwacom.ngtms.cam.vo.DeviceStatusProperties;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.core.client.XTemplates;
import com.sencha.gxt.core.client.util.DelayedTask;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.SortDir;
import com.sencha.gxt.data.shared.Store.StoreSortInfo;
import com.sencha.gxt.data.shared.TreeStore;
import com.sencha.gxt.widget.core.client.event.CheckChangedEvent;
import com.sencha.gxt.widget.core.client.event.CheckChangedEvent.CheckChangedHandler;
import com.sencha.gxt.widget.core.client.event.HideEvent;
import com.sencha.gxt.widget.core.client.event.HideEvent.HideHandler;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.ShowEvent;
import com.sencha.gxt.widget.core.client.event.ShowEvent.ShowHandler;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.grid.GridView;
import com.sencha.gxt.widget.core.client.tree.Tree;
import com.sencha.gxt.widget.core.client.tree.Tree.CheckState;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;

/**
 * 提供共用的「設備狀態」widget。 各 device 無法共用的部份，透過 {@link DeviceStatusFactory} 實做 deferred binding 來達成。
 *
 * <p>各類 device 共用 {@link EmsDeviceStatusDTO}，差別在於 {@link EmsDeviceStatusDTO#getBit(Integer)}
 * 對應的邏輯意義不同。 各硬體狀態對應的 bit 定義參見通訊協定：指令編碼 01H。
 *
 * @see DeviceStatusFactory
 * @author monty.pan
 */
public class DeviceStatusViewer extends AmTab {
  private static DeviceStatusViewUiBinder uiBinder = GWT.create(DeviceStatusViewUiBinder.class);

  interface DeviceStatusViewUiBinder extends UiBinder<Widget, DeviceStatusViewer> {}

  private static DataRenderer renderer = GWT.create(DataRenderer.class);
  private static final int UPDATE_INTERVAL = 120 * 1000;
  private static final int STATIC_COLUMN_LENGTH = 1;
  List<String> displayTypeDevice = new ArrayList<>();
  private Messages messages = GWT.create(Messages.class);

  @UiField GridView<DeviceStatusDTO> view;

  @UiField(provided = true)
  Grid<DeviceStatusDTO> grid;

  @UiField HTML statistics;
  @UiField Tree<TreeData, String> tree;

  @UiField(provided = true)
  TreeStore<TreeData> treeStore;

  @UiField(provided = true)
  ValueProvider<TreeData, String> valueProvider;

  private ListStore<DeviceStatusDTO> store;
  private ColumnModel<DeviceStatusDTO> cm;
  private Timer timer =
      new Timer() {
        @Override
        public void run() {
          updateEmsDeviceStatus();
        }
      };

  private DeviceStatusFactory factory = GWT.create(DeviceStatusFactory.class);

  public DeviceStatusViewer() {
    this.setTabTitle(messages.deviceStatusView_name());

    store = new ListStore<>(DeviceStatusProperties.INSTANCE.id());
    cm = new ColumnModel<>(factory.getColumnConfigList());
    grid = new Grid<>(store, cm);

    treeStore =
        new TreeStore<TreeData>(
            new ModelKeyProvider<TreeData>() {
              @Override
              public String getKey(TreeData item) {
                return item.getId();
              }
            });
    valueProvider =
        new ValueProvider<TreeData, String>() {
          @Override
          public String getValue(TreeData object) {
            return object.getName();
          }

          @Override
          public void setValue(TreeData object, String value) {}

          @Override
          public String getPath() {
            return "deviceStatusTree";
          }
        };

    initWidget(uiBinder.createAndBindUi(this));
    initTreeData();
    initStoreSortInfo();

    this.addShowHandler(
        new ShowHandler() {
          @Override
          public void onShow(ShowEvent event) {
            start();
          }
        });
    this.addHideHandler(
        new HideHandler() {
          @Override
          public void onHide(HideEvent event) {
            timer.cancel();
          }
        });

    final DelayedTask task =
        new DelayedTask() {
          @Override
          public void onExecute() {
            for (TreeData eachParent : treeStore.getRootItems()) {
              for (TreeData each : treeStore.getChildren(eachParent)) {
                cm.setHidden(Integer.valueOf(each.getId()), !tree.isChecked(each));
              }
            }
          }
        };

    tree.addCheckChangedHandler(
        new CheckChangedHandler<TreeData>() {
          @Override
          public void onCheckChanged(CheckChangedEvent<TreeData> event) {
            task.delay(100);
          }
        });
    checkAll();
    displayTypeDevice.add("LCS");
    displayTypeDevice.add("CSLS");
    displayTypeDevice.add("CMS");
    displayTypeDevice.add("CMSRST");
    displayTypeDevice.add("CMSRST");
  }

  private void start() {
    grid.mask(messages.message_loading());
    Scheduler.get()
        .scheduleFixedDelay(
            new RepeatingCommand() {
              @Override
              public boolean execute() {
                if (store.size() == 0) {
                  if (DataCenter.getDeviceConfigList() == null) {
                    return true;
                  }

                  for (DeviceConfigDTO dc : DataCenter.getDeviceConfigList()) {
                    if (dc.getEnable()) {
                      DeviceStatusDTO ds = new DeviceStatusDTO();
                      ds.setDeviceConfig(dc);
                      store.add(ds);
                    }
                  }
                }
                updateEmsDeviceStatus();
                return false;
              }
            },
            200); // 0.2 秒檢查一次
  }

  //FIXME TIMER 不會 cancel 的問題
  private void updateEmsDeviceStatus() {
    grid.mask(messages.message_updating());

    List<String> typeList = new ArrayList<>();
    for (String dto : factory.getDeviceTypeDTOList()) {
      typeList.add(dto);
    }
    HomeEP.camService.getDeviceStatus(
        typeList,
        new MethodCallback<List<EmsDeviceStatusDTO>>() {
          @Override
          public void onSuccess(Method method, List<EmsDeviceStatusDTO> result) {
            for (EmsDeviceStatusDTO eds : result) {
              DeviceStatusDTO ds = store.findModelWithKey(eds.getDeviceName());
              if (ds == null) {
                continue;
              }
              ds.setEmsDeviceStatus(eds);
            }
            view.refresh(false);
            // 重新計算下方訊息列資訊
            updateMessage(genStatData());
            grid.unmask();

            timer.schedule(UPDATE_INTERVAL);
          }

          @Override
          public void onFailure(Method method, Throwable caught) {
            GWT.log("DeviceStatusView.getDeviceStatus failed.", caught);
          }
        });
  }

  private void updateMessage(Statistics statData) {
    statistics.setHTML(renderer.render(messages, statData));
  }

  // 依照store內容至整理下方訊息列
  private Statistics genStatData() {
    Statistics result = new Statistics();
    int total = 0;
    int connect = 0;
    int lowException = 0;
    for (DeviceStatusDTO ds : store.getAll()) {
      total++;
      if (ds.getEmsDeviceStatus() != null) {
        if (ds.getEmsDeviceStatus().isAlive()) {
          connect++;
        }
        if (ds.getEmsDeviceStatus().getBit(7)) {
          lowException++;
        }
      }
    }
    result.setTotal(total);
    result.setConnect(connect);
    result.setNotSync("");
    result.setLowException(String.valueOf(lowException));
    // 處理比對不符及下層異常
    // 目前只處理資收類, 資顯類可額外添加
    boolean isDisplay = false;
    for (String type : displayTypeDevice) {
      if (factory.getDeviceTypeDTOList().contains(type)) {
        isDisplay = true;
      }
    }
    if (!isDisplay) {
      result.setLowException(messages.deviceStatus_noMatchData());
      result.setNotSync(messages.deviceStatus_noMatchData());
    }
    return result;
  }

  //建立樹狀資料
  //TODO 避免影響其他已建立系統,先以此方式產生, 後面考慮在DeviceStatusUtil內建立
  private void initTreeData() {
    int count = DeviceStatusUtil.getFixColumnLength() + DeviceStatusUtil.getLocationColumnLength();
    for (ColumnGroup cg : ColumnGroup.values()) {
      if (cg == ColumnGroup.DEVICE_LOCATION) {
        String basicIndex = "0_" + count;
        TreeData td = new TreeData(basicIndex, cg.getName());
        treeStore.add(td);
        for (int i = STATIC_COLUMN_LENGTH; i < count; i++) {
          treeStore.add(
              td, new TreeData(String.valueOf(i), cm.getColumn(i).getHeader().asString()));
        }
      } else if (cg == ColumnGroup.HARDWARE_STATUS) {
        String basicIndex = count + "_" + cm.getColumns().size();
        TreeData td = new TreeData(basicIndex, cg.getName());
        treeStore.add(td);
        for (int i = count; i < cm.getColumns().size(); i++) {
          treeStore.add(
              td, new TreeData(String.valueOf(i), cm.getColumn(i).getHeader().asString()));
        }
      }
    }
  }

  /** 依照{@link RoadLineDTO}、{@link Direction}、{@link DeviceConfigDTO#getMilepost()} 進行排序 */
  private void initStoreSortInfo() {
    store.addSortInfo(
        new StoreSortInfo<>(
            new Comparator<DeviceStatusDTO>() {
              @Override
              public int compare(DeviceStatusDTO o1, DeviceStatusDTO o2) {
                return o1.getDeviceConfig().getLineId().compareTo(o2.getDeviceConfig().getLineId());
              }
            },
            SortDir.ASC));
    store.addSortInfo(
        new StoreSortInfo<>(
            new Comparator<DeviceStatusDTO>() {
              @Override
              public int compare(DeviceStatusDTO o1, DeviceStatusDTO o2) {
                return o1.getDeviceConfig()
                    .getDirection()
                    .compareTo(o2.getDeviceConfig().getDirection());
              }
            },
            SortDir.ASC));
    store.addSortInfo(
        new StoreSortInfo<>(
            new Comparator<DeviceStatusDTO>() {
              @Override
              public int compare(DeviceStatusDTO o1, DeviceStatusDTO o2) {
                return o1.getDeviceConfig()
                    .getMilepost()
                    .compareTo(o2.getDeviceConfig().getMilepost());
              }
            },
            SortDir.ASC));
  }

  @UiHandler("export")
  public void onExport(SelectEvent event) {
    ExportCsvFile.export(grid, "DeviceStatus.csv");
  }

  @UiHandler("expandAll")
  public void onExpandAll(SelectEvent event) {
    tree.expandAll();
  }

  @UiHandler("collapseAll")
  public void onCollapseAll(SelectEvent event) {
    tree.collapseAll();
  }

  private void checkAll() {
    for (TreeData each : treeStore.getRootItems()) {
      tree.setChecked(each, CheckState.CHECKED);
    }
  }

  public interface DataRenderer extends XTemplates {
    @XTemplate(source = "deviceStatusStats.template")
    public SafeHtml render(Messages message, Statistics data);
  }

  static class Statistics {
    private int total;
    private int connect;
    private String notSync;
    private String lowException;

    public int getTotal() {
      return total;
    }

    public void setTotal(int total) {
      this.total = total;
    }

    public int getConnect() {
      return connect;
    }

    public void setConnect(int connect) {
      this.connect = connect;
    }

    public int getDisconnect() {
      return total - connect;
    }

    public String getGoodRatio() {
      if (total == 0) {
        return "0";
      }
      return (int) (100.0 * connect / total) + "%";
    }

    public String getNotSync() {
      return "" + notSync;
    }

    public void setNotSync(String notSync) {
      this.notSync = notSync;
    }

    public String getLowException() {
      return "" + lowException;
    }

    public void setLowException(String lowException) {
      this.lowException = lowException;
    }
  }

  private class TreeData {
    private String id;
    private String name;

    public TreeData(String id, String name) {
      this.id = id;
      this.name = name;
    }

    public String getId() {
      return id;
    }

    public String getName() {
      return name;
    }
  }
}
