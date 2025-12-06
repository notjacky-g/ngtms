/*
 * © HwaCom Systems Inc. 2015
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.room.am.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.ContextMenuEvent;
import com.google.gwt.event.dom.client.ContextMenuHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Frame;
import com.google.gwt.user.client.ui.Widget;
import com.hwacom.ngtms.c.shared.dto.DeviceSvgPositionConfigDTO;
import com.hwacom.ngtms.cam.client.ui.AmTab;
import com.hwacom.ngtms.hcce.am.factory.ClientFactory;
import com.hwacom.ngtms.room.am.event.RoomDeviceConfigEvent;
import com.hwacom.ngtms.room.am.event.RoomLoadMapEvent;
import com.hwacom.ngtms.room.am.event.RoomLoadMapEvent.RoomLoadMapEventHandler;
import com.hwacom.ngtms.room.am.images.roomDevice.RoomDeviceImages;
import com.hwacom.ngtms.room.am.images.roomStatus.RoomStatusImages;
import com.hwacom.ngtms.room.am.presenter.RoomDeviceMonitorPresenter;
import com.hwacom.ngtms.room.am.util.StringConverter;
import com.hwacom.ngtms.room.shared.EventCode;
import com.hwacom.ngtms.room.shared.dto.RoomCardReaderLogDTO;
import com.hwacom.ngtms.room.shared.dto.RoomCctvUrlDTO;
import com.hwacom.ngtms.room.shared.dto.RoomDeviceStatusDTO;
import com.hwacom.ngtms.room.shared.dto.RoomDeviceSubLocationConfigDTO;
import com.hwacom.ngtms.room.shared.dto.RoomDoDTO;
import com.sencha.gxt.core.client.Style.SelectionMode;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.LabelProvider;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;
import com.sencha.gxt.widget.core.client.ComponentHelper;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.Dialog;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.event.HideEvent;
import com.sencha.gxt.widget.core.client.event.HideEvent.HideHandler;
import com.sencha.gxt.widget.core.client.event.RowClickEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.event.ShowContextMenuEvent;
import com.sencha.gxt.widget.core.client.form.ComboBox;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.grid.GridView;
import com.sencha.gxt.widget.core.client.info.Info;
import com.sencha.gxt.widget.core.client.menu.Item;
import com.sencha.gxt.widget.core.client.menu.Menu;
import com.sencha.gxt.widget.core.client.menu.MenuItem;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.vectomatic.dom.svg.OMElement;
import org.vectomatic.dom.svg.OMSVGDocument;
import org.vectomatic.dom.svg.OMSVGSVGElement;
import org.vectomatic.dom.svg.ui.SVGResource;
import org.vectomatic.dom.svg.utils.OMSVGParser;

public class RoomDeviceMonitorViewer extends AmTab {

  private static RoomDeviceMonitorViewerUiBinder uiBinder =
      GWT.create(RoomDeviceMonitorViewerUiBinder.class);

  interface RoomDeviceMonitorViewerUiBinder extends UiBinder<Widget, RoomDeviceMonitorViewer> {}

  private static GridProperties props = GWT.create(GridProperties.class);

  private RoomDeviceMonitorPresenter presenter = new RoomDeviceMonitorPresenter(this);

  private final ClientFactory clientFactory = GWT.create(ClientFactory.class);
  //新增Room背景圖層，需要將新的背景圖放入此Map中
  private Map<String, SVGResource> roomMap = new HashMap<String, SVGResource>();
  //儲存該底圖的設備
  private Set<String> svgDeviceNameList = new HashSet<>();
  //比對狀態判斷
  private boolean statusChangeFlag = false;
  private boolean findSame = false;
  //暫存設備上一次的狀態
  private Map<String, RoomDeviceStatusDTO> tempStatusMap = new HashMap<>();

  /** 建立 svg 元件 */
  private OMSVGDocument doc = OMSVGParser.currentDocument();

  private OMSVGSVGElement svg = doc.createSVGSVGElement();

  private Element div = null;

  //  private Element statusDiv = null;
  /** 異常 - 紅 */
  private String colorRed = "#ca553a";
  /** 正常 - 綠 */
  private String colorGreen = "#2bb92b";
  /** 門禁開啟 - 黃 */
  private String colorYellow = "#d2d20e";

  private Timer timer = null;

  private Timer roomStatusTimer = null;

  private Timer cardReaderStatusTimer = null;

  /** 更新設備頻率 */
  private final int SECOND = 10000;

  private final String STYLE = "style";

  @UiField Frame frame;

  @UiField ContentPanel svgContainer;

  @UiField ContentPanel aboveSvgContainer;

  @UiField(provided = true)
  ListStore<RoomDeviceSubLocationConfigDTO> store;

  @UiField(provided = true)
  ColumnModel<RoomDeviceSubLocationConfigDTO> cm;

  @UiField GridView<RoomDeviceSubLocationConfigDTO> view;

  @UiField Grid<RoomDeviceSubLocationConfigDTO> grid;

  @UiField VerticalLayoutContainer roomSvgContainer;

  private String selectedSubLocationId;

  private Boolean selectingCheckFlag = false;

  private Menu openMenu = new Menu();

  private Menu settingDIMenu = new Menu();

  private Menu settingDOMenu = new Menu();

  private MenuItem open = new MenuItem();

  private MenuItem setting = new MenuItem();

  private MenuItem settingDO = new MenuItem();

  private Dialog dialog;

  private Dialog doDialog;

  private String openedDevice;

  private String settedDevice;

  private RoomDeviceSubLocationConfigDTO lastLocationConfigDTO =
      new RoomDeviceSubLocationConfigDTO();

  @UiField(provided = true)
  ListStore<RoomCctvUrlDTO> deviceStore;

  @UiField(provided = true)
  LabelProvider<RoomCctvUrlDTO> deviceProvider;

  @UiField ComboBox<RoomCctvUrlDTO> deviceCB;

  @UiField ContentPanel framePanel;

  public RoomDeviceMonitorViewer() {

    deviceStore =
        new ListStore<>(
            new ModelKeyProvider<RoomCctvUrlDTO>() {
              @Override
              public String getKey(RoomCctvUrlDTO item) {
                return item.getDeviceName();
              }
            });

    deviceProvider =
        new LabelProvider<RoomCctvUrlDTO>() {
          @Override
          public String getLabel(RoomCctvUrlDTO item) {
            return item.getDisplayName();
          }
        };

    store = new ListStore<>(props.id());
    cm = genColumnModel();
    initWidget(uiBinder.createAndBindUi(this));
    div = svgContainer.getElement();
    grid.setHideHeaders(true);
    grid.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    clientFactory
        .getEventBus()
        .addHandler(RoomLoadMapEvent.TYPE, new DefaultRoomLoadMapEventHandler());
    startCardReaderStatusTimer();

    setting.setText("屬性設定");
    setting.addSelectionHandler(
        new SelectionHandler<Item>() {

          @Override
          public void onSelection(SelectionEvent<Item> event) {
            clientFactory
                .getEventBus()
                .fireEventFromSource(
                    new RoomDeviceConfigEvent(RoomDeviceConfigEvent.Action.CLICK), settedDevice);
          }
        });
    settingDIMenu.add(setting);

    settingDO.setText("設備開關");
    settingDO.addSelectionHandler(
        new SelectionHandler<Item>() {

          @Override
          public void onSelection(SelectionEvent<Item> event) {
            showDODialog();
          }
        });
    settingDOMenu.add(setting);
    settingDOMenu.add(settingDO);

    open.setText("遠端開門");
    open.addSelectionHandler(
        new SelectionHandler<Item>() {

          @Override
          public void onSelection(SelectionEvent<Item> event) {
            showDialog();
          }
        });
    openMenu.add(open);

    svgContainer.addDomHandler(
        new ContextMenuHandler() {

          @Override
          public void onContextMenu(ContextMenuEvent event) {
            event.preventDefault();
          }
        },
        ContextMenuEvent.getType());

    roomSvgContainer.addDomHandler(
        new ContextMenuHandler() {

          @Override
          public void onContextMenu(ContextMenuEvent event) {
            event.preventDefault();
          }
        },
        ContextMenuEvent.getType());
  }

  private ColumnModel<RoomDeviceSubLocationConfigDTO> genColumnModel() {
    List<ColumnConfig<RoomDeviceSubLocationConfigDTO, ?>> columnConfigList = new ArrayList<>();

    ColumnConfig<RoomDeviceSubLocationConfigDTO, String> locationName =
        new ColumnConfig<>(
            new ValueProvider<RoomDeviceSubLocationConfigDTO, String>() {
              @Override
              public String getValue(RoomDeviceSubLocationConfigDTO dto) {
                return dto.getLocationName() + "-" + dto.getSubLocation();
              }

              @Override
              public void setValue(RoomDeviceSubLocationConfigDTO object, String value) {}

              @Override
              public String getPath() {
                return "locationName";
              }
            },
            100,
            "");
    locationName.setMenuDisabled(true);
    columnConfigList.add(locationName);

    return new ColumnModel<>(columnConfigList);
  }

  @UiHandler("grid")
  public void rowClick(RowClickEvent event) {
    setRoomSvg();
    presenter.getRoomCctvUrls(
        grid.getSelectionModel().getSelectedItem().getLocationName(),
        grid.getSelectionModel().getSelectedItem().getSubLocation());
  }

  @UiHandler("deviceCB")
  void onDeviceCBSelected(SelectionEvent<RoomCctvUrlDTO> event) {
    if (event.getSelectedItem().getUrl() != null) {
      frame.setUrl(event.getSelectedItem().getUrl());
    }
  }

  public void setRoomSvg() {
    roomSvgContainer.clear();
    selectingCheckFlag = true;
    RoomDeviceSubLocationConfigDTO selectedItem = grid.getSelectionModel().getSelectedItem();
    aboveSvgContainer.setHeading(
        selectedItem.getLocationName() + "-" + selectedItem.getSubLocation());
    selectedSubLocationId = selectedItem.getId();
    fillRoomSvg(selectedItem.getBackgroundSvgId());
    presenter.getCardReaderData(selectedItem.getLocationName(), selectedItem.getSubLocation());
  }

  public void showDialog() {
    if (dialog == null) {
      initDialog();
    }
    dialog.show();
  }

  private void initDialog() {
    dialog = new Dialog();
    dialog.setHeading("是否確定開門");
    dialog.setModal(true);
    dialog.setWidth(260);
    dialog.setHeight(100);
    TextButton confirmButton = new TextButton();
    confirmButton.setText("是");
    confirmButton.addSelectHandler(
        new SelectHandler() {
          @Override
          public void onSelect(SelectEvent event) {
            presenter.changeDoorStatus(openedDevice);
          }
        });
    TextButton cancelButton = new TextButton();
    cancelButton.setText("取消");
    cancelButton.addSelectHandler(
        new SelectHandler() {
          @Override
          public void onSelect(SelectEvent event) {
            dialog.hide();
          }
        });
    dialog.getButtonBar().clear();
    dialog.getButtonBar().add(confirmButton);
    dialog.getButtonBar().add(cancelButton);
  }

  public void showDODialog() {
    if (doDialog == null) {
      initDoDialog();
    }
    doDialog.show();
  }

  private void initDoDialog() {
    doDialog = new Dialog();
    doDialog.setHeading("設備開關");
    doDialog.setModal(true);
    doDialog.setWidth(260);
    doDialog.setHeight(100);
    TextButton confirmButton = new TextButton();
    confirmButton.setText("開啟");
    confirmButton.addSelectHandler(
        new SelectHandler() {
          @Override
          public void onSelect(SelectEvent event) {
            RoomDoDTO dto = new RoomDoDTO();
            dto.setDeviceName(settedDevice);
            dto.setValue(true);
            presenter.updateDoDevice(dto);
          }
        });
    TextButton rejectButton = new TextButton();
    rejectButton.setText("關閉");
    rejectButton.addSelectHandler(
        new SelectHandler() {
          @Override
          public void onSelect(SelectEvent event) {
            RoomDoDTO dto = new RoomDoDTO();
            dto.setDeviceName(settedDevice);
            dto.setValue(false);
            presenter.updateDoDevice(dto);
          }
        });
    TextButton cancelButton = new TextButton();
    cancelButton.setText("取消");
    cancelButton.addSelectHandler(
        new SelectHandler() {
          @Override
          public void onSelect(SelectEvent event) {
            doDialog.hide();
          }
        });
    doDialog.getButtonBar().clear();
    doDialog.getButtonBar().add(confirmButton);
    doDialog.getButtonBar().add(rejectButton);
    doDialog.getButtonBar().add(cancelButton);
  }

  public void fillCctvUrl(List<RoomCctvUrlDTO> response) {
    deviceStore.clear();
    deviceCB.clear();
    deviceStore.addAll(response);
    lastLocationConfigDTO = grid.getSelectionModel().getSelectedItem();

    if (deviceStore == null || deviceStore.size() == 0) {
      frame.setUrl("");
      framePanel.mask("該機房無攝影機設備");
    } else {
      framePanel.unmask();
      deviceCB.setValue(deviceStore.get(0));
      frame.setUrl(deviceCB.getCurrentValue().getUrl());
    }
  }

  public void generateDoorSvg(List<RoomCardReaderLogDTO> response) {
    if (response == null) {
      // TODO info
      return;
    }
    for (RoomCardReaderLogDTO dto : response) {
      OMSVGDocument doorDoc = OMSVGParser.currentDocument();
      OMSVGSVGElement doorSvg = doorDoc.createSVGSVGElement();
      Element doorDiv = null;
      VerticalLayoutContainer statusSvgContainer = new VerticalLayoutContainer();
      doorDiv = statusSvgContainer.getElement();
      SVGResource resource = RoomStatusImages.INSTANCE.roomDoorStatus();
      if (resource == null) {
        GWT.log("Fill svg failed, can not get resource.");
        return;
      }
      OMSVGSVGElement svgElement = resource.getSvg();
      // 將 viewbox 設為 svg 底圖大小
      doorSvg.setViewBox(
          0f,
          0f,
          svgElement.getWidth().getBaseVal().getValue(),
          svgElement.getHeight().getBaseVal().getValue());

      OMElement deviceNameElement = svgElement.getElementById("deviceName");
      deviceNameElement.getElement().setInnerText(dto.getDescription());

      OMElement doorRectElement = svgElement.getElementById("doorRect");
      OMElement doorStatusElement = svgElement.getElementById("doorStatus");
      doorStatusElement.getElement().setId(dto.getDeviceName());

      OMElement doorStatusRectElement = svgElement.getElementById("doorStatusRect");
      OMElement statusElement = svgElement.getElementById("status");
      OMElement statusRectElement = svgElement.getElementById("statusRect");
      if (dto.getStatus() != 1) {
        if (dto.getEventCode() == EventCode.DOOR_OPEN) {
          doorStatusElement.getElement().setInnerText("門禁被開啟");
          doorStatusRectElement.getElement().getStyle().setProperty("fill", colorYellow);
          //要先做卡機 要對應相對的 攝影機  排除交控中心和會議室
          if ("CARD_READER_3".equals(dto.getDeviceName())
              || "CARD_READER_4".equals(dto.getDeviceName())) {
            //無對應的攝影機
          } else {
            String cctvDeviceName = StringConverter.getMappingCctvDevice(dto.getDeviceName());
            if (cctvDeviceName != null) {
              RoomCctvUrlDTO cctvDto = deviceStore.findModelWithKey(cctvDeviceName);
              deviceCB.select(cctvDto);
            }
          }
        } else if (dto.getEventCode() == EventCode.DOOR_CLOSE) {
          doorStatusElement.getElement().setInnerText("遙控門開啟");
          openedDevice = doorStatusElement.getElement().getId();
          doorStatusElement.addDomHandler(
              new ContextMenuHandler() {

                @Override
                public void onContextMenu(ContextMenuEvent event) {
                  final int x = event.getNativeEvent().getClientX();
                  final int y = event.getNativeEvent().getClientY();
                  event.preventDefault();
                  onShowContextMenu(openMenu, x, y);
                }
              },
              ContextMenuEvent.getType());

          doorStatusRectElement.addDomHandler(
              new ContextMenuHandler() {

                @Override
                public void onContextMenu(ContextMenuEvent event) {
                  final int x = event.getNativeEvent().getClientX();
                  final int y = event.getNativeEvent().getClientY();
                  event.preventDefault();
                  onShowContextMenu(openMenu, x, y);
                }
              },
              ContextMenuEvent.getType());
          openedDevice = dto.getDeviceName();
          doorStatusRectElement.getElement().getStyle().setProperty("fill", colorGreen);
        }
        statusElement.getElement().setInnerText("保全啟動");
        statusRectElement.getElement().getStyle().setProperty("fill", colorGreen);
      } else {
        doorRectElement.getElement().getStyle().setProperty("fill", "#662020");
        doorStatusRectElement.getElement().getStyle().setProperty("fill", "#ededed");
        statusRectElement.getElement().getStyle().setProperty("fill", "#ededed");
      }
      OMElement codeRectElement = svgElement.getElementById("codeRect");
      codeRectElement.getElement().getStyle().setProperty("fill", "#ededed");

      doorSvg.appendChild(svgElement);
      doorDiv.appendChild(doorSvg.getElement());
      roomSvgContainer.add(statusSvgContainer);
    }
  }

  protected void onShowContextMenu(Menu menu, int clientX, int clientY) {
    menu.showAt(clientX, clientY);
    if (menu.isVisible()) {
      fireEvent(new ShowContextMenuEvent(menu));
      openMenu.addHideHandler(
          new HideHandler() {

            @Override
            public void onHide(HideEvent event) {
              ComponentHelper.removeHandler(menu, HideEvent.getType(), this);
            }
          });
    }
  }

  public void fillGridData(List<RoomDeviceSubLocationConfigDTO> response) {
    Collections.sort(
        response,
        new Comparator<RoomDeviceSubLocationConfigDTO>() {
          @Override
          public int compare(RoomDeviceSubLocationConfigDTO o1, RoomDeviceSubLocationConfigDTO o2) {
            if (o1.getStatus() == 0) {
              if (o2.getStatus() == 0) {
                return 0;
              } else {
                return 1;
              }
            } else if (o1.getStatus() == 1) {
              if (o2.getStatus() == 1) {
                return 0;
              } else {
                return -1;
              }
            } else {
              if (o2.getStatus() == 2) {
                return 0;
              } else if (o2.getStatus() == 1) {
                return 1;
              } else {
                return -1;
              }
            }
          }
        });
    store.clear();
    store.addAll(response);
    changeStoreColor();
    if (selectedSubLocationId != null) {
      grid.getSelectionModel().select(true, store.findModelWithKey(selectedSubLocationId));
      if (!lastLocationConfigDTO
              .getLocationName()
              .equals(grid.getSelectionModel().getSelectedItem().getLocationName())
          && !lastLocationConfigDTO
              .getSubLocation()
              .equals(grid.getSelectionModel().getSelectedItem().getSubLocation())) {
        presenter.getRoomCctvUrls(
            grid.getSelectionModel().getSelectedItem().getLocationName(),
            grid.getSelectionModel().getSelectedItem().getSubLocation());
      }
    }
  }

  public void changeStoreColor() {
    for (RoomDeviceSubLocationConfigDTO dto : store.getAll()) {
      int row = store.indexOf(dto);
      for (int i = 0; i < cm.getColumnCount(); i++) {
        if (dto.getStatus() == 0) {
          grid.getView().getCell(row, i).getStyle().setBackgroundColor(colorGreen);
        } else if (dto.getStatus() == 1) {
          grid.getView().getCell(row, i).getStyle().setBackgroundColor(colorRed);
        } else {
          grid.getView().getCell(row, i).getStyle().setBackgroundColor(colorYellow);
        }
      }
    }
  }

  private void startCardReaderStatusTimer() {
    if (cardReaderStatusTimer == null) {
      cardReaderStatusTimer =
          new Timer() {
            @Override
            public void run() {
              if (selectingCheckFlag == true && selectedSubLocationId != null) {
                GWT.log("refresh card reader status");
                roomSvgContainer.clear();
                RoomDeviceSubLocationConfigDTO selectedItem =
                    grid.getSelectionModel().getSelectedItem();
                presenter.getCardReaderData(
                    selectedItem.getLocationName(), selectedItem.getSubLocation());
              }
            }
          };
    }
    cardReaderStatusTimer.scheduleRepeating(SECOND);
    cardReaderStatusTimer.run();
  }

  private void startRoomStatusTimer() {
    if (roomStatusTimer == null) {
      roomStatusTimer =
          new Timer() {
            @Override
            public void run() {
              presenter.initGridData();
            }
          };
    }
    roomStatusTimer.scheduleRepeating(SECOND);
    roomStatusTimer.run();
  }

  //點到再更新
  private void startTimer() {
    if (timer == null) {
      timer =
          new Timer() {
            @Override
            public void run() {
              presenter.refreshRoomDeviceStatus(new ArrayList<>(svgDeviceNameList));
            }
          };
      timer.scheduleRepeating(SECOND);
      timer.run();
    }
  }

  public void setRoomBackground(Map<String, SVGResource> svgResourceMap) {
    roomMap.putAll(svgResourceMap);
  }

  public void getCctvUrl(String response) {
    //	    frame.setUrl();
  }

  public void fillRoomSvg(String svgName) {
    if (svgName == null) {
      GWT.log("svgName is null.");
      return;
    }
    // 先清空所有原件
    clearSVGComponent();

    SVGResource resource = roomMap.get(svgName);
    if (resource == null) {
      GWT.log("Fill background svg failed, can not get resource, svgName=" + svgName);
      return;
    }
    OMSVGSVGElement svgElement = resource.getSvg();
    // 將 viewbox 設為 svg 底圖大小 TODO 調整大小
    svg.setViewBox(
        0f,
        0f,
        svgElement.getWidth().getBaseVal().getValue(),
        svgElement.getHeight().getBaseVal().getValue() + 170);

    svg.appendChild(svgElement);
    div.appendChild(svg.getElement());

    presenter.queryDevicePositionConfig(svgName);
  }

  public void fillDeviceSvg(String svgName, List<DeviceSvgPositionConfigDTO> response) {
    for (DeviceSvgPositionConfigDTO config : response) {
      SVGResource resource = getDeviceSvgResource(config.getDeviceType());
      if (resource == null) {
        GWT.log("Can not find svg image, deviceType=" + config.getDeviceType());
        continue;
      }
      //該地圖的設備數量
      svgDeviceNameList.add(config.getDeviceName());
      OMSVGSVGElement svgElement = resource.getSvg();
      if (svgElement.getElementById("deviceName") != null) {
        OMElement tooltip = svgElement.getElementById("deviceName");
        tooltip.getElement().setAttribute("device", config.getDeviceName());
      }
      //設備顯示名稱 附加獨立的Id
      if (svgElement.getElementById("displayName") != null) {
        OMElement displayNameElement = svgElement.getElementById("displayName");
        displayNameElement.getElement().setInnerText(config.getDisplayName() + " ");
        displayNameElement.getElement().setAttribute("id", config.getDeviceName() + "_displayName");
      }
      //設備狀態 附加獨立的Id(deviceName_status)
      if (svgElement.getElementById("status") != null) {
        OMElement statusElement = svgElement.getElementById("status");
        statusElement.getElement().setAttribute("id", config.getDeviceName() + "_status");
      }
      //機房設備位置
      svgElement.getX().getBaseVal().setValue(config.getPositionX());
      svgElement.getY().getBaseVal().setValue(config.getPositionY());
      svgElement.setId(config.getDeviceName());
      svg.appendChild(svgElement);
      if (!config.getDeviceType().equals("cardReader") && !config.getDeviceType().equals("CCTV")) {
        //在分類DO和(AI與DI)
        if (config.getDeviceType().equals("flashlight")
            || config.getDeviceType().equals("powerSwitch")) {
          showSettingDOContextMenu(svgElement, config.getDeviceName());
        } else {
          showSettingDIContextMenu(svgElement, config.getDeviceName());
        }
      }
    }
    if (svgDeviceNameList != null && !svgDeviceNameList.isEmpty()) {
      startTimer();
    }
  }

  public void refreshStatus(List<RoomDeviceStatusDTO> response) {
    //第一次進入一定會做一次
    statusChangeFlag = checkLastStatus(response);
    //比對狀態 如果狀態沒有更新 就不作svg圖更新
    if (statusChangeFlag) {
      for (RoomDeviceStatusDTO dto : response) {
        if (tempStatusMap.get(dto.getDeviceName()) == null) {
          tempStatusMap.put(dto.getDeviceName(), dto);
        }
        String deviceName = dto.getDeviceName();
        OMElement svgNode = svg.getElementById(dto.getDeviceName());
        Element svgDevice = svgNode.getElement();
        SVGResource resource = getDeviceSvgResource(dto.getDeviceType());
        OMSVGSVGElement svgElement = resource.getSvg();
        //比對DeviceName
        if (svgDevice
            .getElementsByTagName("title")
            .getItem(0)
            .getAttribute("device")
            .equals(deviceName)) {
          if (svg.getElementById(deviceName + "_status") != null) {
            OMElement statusChange = svg.getElementById(deviceName + "_status");

            if (dto.getStatus() == 1) {
              statusChange.getElement().setAttribute(STYLE, "fill:" + colorRed);
            } else if (dto.getStatus() == 0) {
              statusChange.getElement().setAttribute(STYLE, "fill:" + colorGreen);
              if (dto.getDeviceType().equals("cardReader")) {
                statusChange.addDomHandler(
                    new ContextMenuHandler() {

                      @Override
                      public void onContextMenu(ContextMenuEvent event) {
                        final int x = event.getNativeEvent().getClientX();
                        final int y = event.getNativeEvent().getClientY();
                        event.preventDefault();
                        onShowContextMenu(openMenu, x, y);
                        openedDevice = deviceName;
                      }
                    },
                    ContextMenuEvent.getType());

                OMElement displayNameElement = svg.getElementById(deviceName + "_displayName");
                displayNameElement.addDomHandler(
                    new ContextMenuHandler() {

                      @Override
                      public void onContextMenu(ContextMenuEvent event) {
                        final int x = event.getNativeEvent().getClientX();
                        final int y = event.getNativeEvent().getClientY();
                        event.preventDefault();
                        onShowContextMenu(openMenu, x, y);
                        openedDevice = deviceName;
                      }
                    },
                    ContextMenuEvent.getType());
              }
            } else {
              statusChange.getElement().setAttribute(STYLE, "fill:" + colorYellow);
            }
            if (!dto.getDeviceType().equals("cardReader") && !dto.getDeviceType().equals("CCTV")) {
              //在分類DO和(AI與DI)
              if (dto.getDeviceType().equals("flashlight")
                  || dto.getDeviceType().equals("powerSwitch")) {
                showSettingDOContextMenu(svgElement, dto.getDeviceName());
              } else {
                showSettingDIContextMenu(svgElement, dto.getDeviceName());
              }
            }
          }
          if (dto.getStatusContent() != null) {
            if (svg.getElementById(deviceName + "_displayName") != null) {
              OMElement displayNameElement = svg.getElementById(deviceName + "_displayName");
              String displayName[] = null;
              displayName = displayNameElement.getElement().getInnerText().split(" ");
              displayNameElement
                  .getElement()
                  .setInnerText(displayName[0] + " " + dto.getStatusContent());
            }
          }
        } else {
          GWT.log("Can not find deviceName, deviceName=" + deviceName);
          continue;
        }
      }
    } else {
      return;
    }
  }

  public void showSettingDIContextMenu(OMElement element, String deviceName) {
    element.addDomHandler(
        new ContextMenuHandler() {

          @Override
          public void onContextMenu(ContextMenuEvent event) {
            final int x = event.getNativeEvent().getClientX();
            final int y = event.getNativeEvent().getClientY();
            event.preventDefault();
            onShowContextMenu(settingDIMenu, x, y);
            settedDevice = deviceName;
          }
        },
        ContextMenuEvent.getType());
  }

  public void showSettingDOContextMenu(OMElement element, String deviceName) {
    element.addDomHandler(
        new ContextMenuHandler() {

          @Override
          public void onContextMenu(ContextMenuEvent event) {
            final int x = event.getNativeEvent().getClientX();
            final int y = event.getNativeEvent().getClientY();
            event.preventDefault();
            onShowContextMenu(settingDOMenu, x, y);
            settedDevice = deviceName;
          }
        },
        ContextMenuEvent.getType());
  }

  public void showDoorOpenInfo(Boolean result) {
    if (result == true) {
      Info.display("開門完成", "並更新卡機狀態");
    } else {
      Info.display("開門失敗", "更新卡機狀態");
    }
    setRoomSvg();
    dialog.hide();
  }

  public void openPower(Boolean result) {
    if (result == true) {
      Info.display("完成", "");
    } else {
      Info.display("失敗", "");
    }
    RoomDeviceSubLocationConfigDTO selectedItem = grid.getSelectionModel().getSelectedItem();
    fillRoomSvg(selectedItem.getBackgroundSvgId());
    doDialog.hide();
  }

  public String getSettedDevice() {
    return settedDevice;
  }

  private boolean checkLastStatus(List<RoomDeviceStatusDTO> list) {
    if (tempStatusMap != null && !tempStatusMap.isEmpty()) {
      findSame = false;
      for (RoomDeviceStatusDTO dto : list) {
        if (tempStatusMap.get(dto.getDeviceName()) != null) {
          if (dto.getStatus() == tempStatusMap.get(dto.getDeviceName()).getStatus()) {
            findSame = true;
          } else {
            findSame = false;
            break;
          }
        } else {
          findSame = false;
          break;
        }
      }
      //比對結果 成功 回傳 不需要更新
      if (findSame) {
        return false;
      } else {
        return true;
      }
    } else {
      return true;
    }
  }

  /** 清空 SVG 相關物件 */
  private void clearSVGComponent() {
    div.removeAllChildren();
    svg = doc.createSVGSVGElement();
    svgDeviceNameList = new HashSet<>();
    tempStatusMap.clear();
  }

  /**
   * 依設備類型及面板種類取得 svg 圖
   *
   * @param deviceType 設備類型
   */
  private SVGResource getDeviceSvgResource(String deviceType) {
    if (deviceType.equals("CCTV")) {
      return RoomDeviceImages.INSTANCE.gt();
    }
    return RoomDeviceImages.INSTANCE.gc();
  }

  @Override
  protected void onShow() {
    super.onShow();
    if (timer != null) {
      timer.cancel();
    }
    startRoomStatusTimer();
  }

  @Override
  protected void onHide() {
    super.onHide();
    selectingCheckFlag = false;
    if (timer != null) {
      timer.cancel();
    }
    if (roomStatusTimer != null) {
      roomStatusTimer.cancel();
    }
  }

  interface GridProperties extends PropertyAccess<RoomDeviceSubLocationConfigDTO> {
    ModelKeyProvider<RoomDeviceSubLocationConfigDTO> id();

    ValueProvider<RoomDeviceSubLocationConfigDTO, String> locationName();
  }

  class DefaultRoomLoadMapEventHandler implements RoomLoadMapEventHandler {

    @Override
    public void onLoad(RoomLoadMapEvent event) {
      if (grid.getSelectionModel().getSelectedItem() != null) {
        setRoomSvg();
      }
    }
  }
}
