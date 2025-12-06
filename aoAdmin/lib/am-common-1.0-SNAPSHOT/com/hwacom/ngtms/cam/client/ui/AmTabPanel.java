/*
 * © HwaCom Systems Inc. 2015
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.cam.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiChild;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.hwacom.ngtms.c.shared.DragItem;
import com.hwacom.ngtms.c.shared.dto.DeviceConfigDTO;
import com.hwacom.ngtms.cam.client.event.AmEventCenter;
import com.hwacom.ngtms.cam.client.event.DeviceConfigSelectEvent;
import com.hwacom.ngtms.cam.client.event.DragItemSelectEvent;
import com.hwacom.ngtms.cam.client.ui.component.ImgTxtButton;
import com.hwacom.ngtms.cam.client.ui.component.ScrollFloatContainer;
import com.hwacom.ngtms.cam.client.ui.dnd.AddType;
import com.hwacom.ngtms.cam.images.AmImages;
import com.hwacom.ngtms.cam.view.Messages;
import com.hwacom.ngtms.common.shared.dto.FunctionPermissionDTO;
import com.hwacom.ngtms.common.shared.dto.RoleDTO;
import com.hwacom.ngtms.common.shared.dto.UserDTO;
import com.sencha.gxt.dnd.core.client.DND.Operation;
import com.sencha.gxt.dnd.core.client.DndDropEvent;
import com.sencha.gxt.dnd.core.client.DndDropEvent.DndDropHandler;
import com.sencha.gxt.dnd.core.client.DropTarget;
import com.sencha.gxt.widget.core.client.TabItemConfig;
import com.sencha.gxt.widget.core.client.TabPanel;
import com.sencha.gxt.widget.core.client.container.MarginData;
import com.sencha.gxt.widget.core.client.event.CloseEvent;
import com.sencha.gxt.widget.core.client.event.CloseEvent.CloseHandler;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.event.ShowEvent;
import com.sencha.gxt.widget.core.client.event.ShowEvent.ShowHandler;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

/**
 * 其實就是 {@link TabPanel}，因為實際需求需要 overview tab 與處理 DnD，因此擴充成 AmTabPanel。
 *
 * <p>如果使用（Swing-way）{@link #addItem(AmTab)}、（UiBinder）
 *
 * <pre>
 * &lt;am:AmTabPanel ui:field="mainLayout" dndType="deviceConfig"&gt;
 *   &lt;am:item&gt;
 *     &lt;am:FooView ui:field="fooView" /&gt;
 *   &lt;/am:item&gt;
 * &lt;/am:AmTabPanel&gt;</pre>
 *
 * 就會自動建立 overview 的內容。注意若使用 {@link TabPanel#add(IsWidget, TabItemConfig)}， 畫面上是不會出問題，但是 overview
 * 會無法出現該 tab。
 *
 * <p>處理 DnD 的部份主要在於 {@link #setDndType(AddType)}， 如果 DnD 的來源是來自 {@link RoadTreeView}， 因此 {@link
 * #setDndType(AddType)} 的傳入值必須是 {@link AddType#DeviceConfig}，否則會無法順利 DnD。 會開放設定 {@link
 * #setDndType(AddType)} 是因為考量到其他子系統可能無法共用 {@link RoadTreeView}， 但卻依然可以共用 AmTabPanel。
 *
 * <p>新增依使用者角色篩選 tab 功能，未設定預設為顯示全部， 若啟用該功能，將比對 tab 的 className 與 FunctionPermission 中的 urlMapping， 在
 * FunctionPermission 中有定義的才會顯示。
 *
 * @see AmTab
 * @author monty.pan
 */
public class AmTabPanel extends TabPanel {
  private final Messages messages = GWT.create(Messages.class);
  private static final int BTN_WIDTH = 150;
  private static final int BTN_HEIGHT = 150;
  private static final MarginData MARGIN_DATA = new MarginData(5);
  private static final int IMAGE_HEIGHT = 40;
  private static final int IMAGE_WIDTH = 40;

  private boolean showRpt;

  private DropTarget dropTarget = new DropTarget(this);
  private ScrollFloatContainer overview = new ScrollFloatContainer();
  private HashMap<AmTab, Boolean> tabMap = new HashMap<>();

  /** class name 及對應的 AmTab, ImgTxtButton, 用於角色篩選功能 */
  private HashMap<String, Entry<AmTab, ImgTxtButton>> classMap = new HashMap<>();

  /** 是否啟用角色篩選頁籤功能 */
  private boolean enableRoleFilter = false;

  public AmTabPanel() {
    this(true);
  }

  public AmTabPanel(boolean showRpt) {
    this.showRpt = showRpt;
    this.setTabScroll(true);
    this.setAnimScroll(true);

    TabItemConfig overviewConfig = new TabItemConfig(messages.overview());
    super.add(overview, overviewConfig);

    if (showRpt) {
      GWT.log("show RPT");
      AmTab rpt = new AmTab();
      rpt.setTabTitle(messages.overview_rpt());
      rpt.setButtonIcon(AmImages.INSTANCE.HDA());
      rpt.setButtonOverIcon(AmImages.INSTANCE.HDAOn());
      addItem(rpt);
    }
    //XXX 不是一開始就 show 的 tab 就會產生 layout 問題，下面這個是目前找到的 workaround
    overview.addShowHandler(
        new ShowHandler() {
          @Override
          public void onShow(ShowEvent event) {
            overview.forceLayout();
          }
        });

    this.addCloseHandler(
        new CloseHandler<Widget>() {
          @Override
          public void onClose(CloseEvent<Widget> event) {
            AmTab tab = (AmTab) event.getItem();
            tabMap.put(tab, false);

            /* 有一些頁籤（例如需要定時更新）會倚賴 addShowHandler()、addHideHandler()
             * 實驗發現，在 close tab 時並不會觸發 HideEvent，所以只好強制呼叫 hide() 以確保會觸發 HideEvent
             * （反而是 TabPanel 裡頭的 CardLayoutContainer.remove() 莫名其妙會觸發 ShowEvent... 難以理解 Orz）
             */
            tab.hide();
          }
        });
  }

  public void setEnableRoleFilter(boolean enable) {
    this.enableRoleFilter = enable;
  }

  public void setDndType(AddType dndGroup) {
    dropTarget.setOperation(Operation.COPY);
    dropTarget.setGroup(dndGroup.toString());

    switch (dndGroup) {
      case DeviceConfig:
        dropTarget.addDropHandler(
            new DndDropHandler() {
              @SuppressWarnings("unchecked")
              @Override
              public void onDrop(DndDropEvent event) {
                AmEventCenter.fireEvent(
                    new DeviceConfigSelectEvent((ArrayList<DeviceConfigDTO>) event.getData()));
              }
            });
        break;
      case Other:
        dropTarget.addDropHandler(
            new DndDropHandler() {
              @SuppressWarnings("unchecked")
              @Override
              public void onDrop(DndDropEvent event) {
                AmEventCenter.fireEvent(
                    new DragItemSelectEvent((ArrayList<DragItem>) event.getData()));
              }
            });
        break;
    }
  }

  /**
   * layout 面的邏輯跟 {@link TabPanel#add(IsWidget, TabItemConfig)} 一致。 透過這個 method 加入的 widget，會自動加入到
   * overview tab 的內容中。
   */
  @UiChild
  public void addTab(final AmTab tab, final TabItemConfig config) {
    //建 Overview
    ImgTxtButton button = new ImgTxtButton();
    button.setPixelSize(BTN_WIDTH, BTN_HEIGHT);
    button.setLayoutData(MARGIN_DATA);
    if (tab.getButtonIcon() == null) {
      //AmTab 沒有指定圖片就自己給一個
      button.setImage(AmImages.INSTANCE.add());
    } else {
      //有指定圖片就直接用，然後壓成適當的大小
      button.setImage(tab.getButtonIcon());
      button.setOverImage(tab.getButtonOverIcon());
      button.setImageWidth(IMAGE_WIDTH);
      button.setImageHeight(IMAGE_HEIGHT);
    }
    button.setText(config.getText());

    button.addSelectHandler(
        new SelectHandler() {
          @Override
          public void onSelect(SelectEvent event) {
            openTab(tab, config);
          }
        });

    if (tab.isDefaultShow()) {
      super.add(tab, config);
    }
    tabMap.put(tab, tab.isDefaultShow());

    Entry<AmTab, ImgTxtButton> entry = new SimpleEntry<>(tab, button);
    if (showRpt) {
      if (overview.getWidgetCount() == 0) {
        // insert rpt
        overview.add(button);
        classMap.put(messages.overview_rpt(), entry);
      } else {
        overview.insertBeforeLastOne(button);
        classMap.put(tab.getClass().getName(), entry);
      }
    } else {
      overview.add(button);
      classMap.put(tab.getClass().getName(), entry);
    }
  }

  /**
   * layout 面的邏輯跟 {@link TabPanel#add(IsWidget, TabItemConfig)} 一致， 只不過 {@link TabItemConfig} 是自動產生。
   * 透過這個 method 加入的 widget，會自動加入到 overview tab 的內容中。
   */
  @UiChild
  public void addItem(AmTab tab) {
    TabItemConfig config = new TabItemConfig(tab.getTabTitle(), tab.isClosable());
    addTab(tab, config);
  }

  /**
   * 等同於 {@link TabPanel#setActiveWidget(com.google.gwt.user.client.ui.Widget)}， 增加這個 method 是為了
   * ui.xml 當中可以直接設定 active widget，而不會產生 ambiguous error。
   *
   * @param tab
   */
  public void setActive(AmTab tab) {
    super.setActiveWidget(tab);
  }

  /**
   * 開啟頁籤
   *
   * @param tab
   * @param config
   */
  public void openTab(final AmTab tab, final TabItemConfig config) {
    if (messages.overview_rpt().equals(config.getText())) {
      Window.open("rpt.html", null, null);
    } else {
      if (!tabMap.get(tab)) {
        AmTabPanel.super.add(tab, config);
        tabMap.put(tab, true);
      }
      setActive(tab);
    }
  }

  /** 依使用者登入資訊調整可點選的 tab */
  public void refreshTab(UserDTO userDTO) {
    if (!enableRoleFilter) {
      // 不啟用角色篩選功能,顯示全部頁籤
      return;
    }
    List<Widget> removeButtonList = new ArrayList<>();
    List<String> classNameList = new ArrayList<>();
    boolean rptFunction = false;
    if (userDTO != null && userDTO.getRoles() != null) {
      for (RoleDTO role : userDTO.getRoles()) {
        if (role.getFunctionPermissions() != null) {
          for (FunctionPermissionDTO function : role.getFunctionPermissions()) {
            String className = function.getUrlMapping();
            if ("rpt.html".equals(className) || "RPT".equals(function.getId())) {
              rptFunction = true;
            }
            if (className != null && className.startsWith("com.hwacom")) {
              classNameList.add(className);
            }
          }
        }
      }
    }
    for (String className : classMap.keySet()) {
      if (!classNameList.contains(className)) {
        Entry<AmTab, ImgTxtButton> entry = classMap.get(className);
        AmTab tab = entry.getKey();
        if (messages.overview_rpt().equals(tab.getTabTitle())) {
          continue;
        }
        ImgTxtButton button = entry.getValue();
        if (tabMap.get(tab) != null) {
          tabMap.remove(tab);
        }
        remove(tab);
        removeButtonList.add(button);
      }
    }
    if (showRpt && !rptFunction) {
      Entry<AmTab, ImgTxtButton> entry = classMap.get(messages.overview_rpt());
      AmTab rpt = entry.getKey();
      if (tabMap.get(rpt) != null) {
        tabMap.remove(rpt);
      }
      remove(rpt);
      removeButtonList.add(entry.getValue());
    }
    overview.removeWidget(removeButtonList);
  }
}
