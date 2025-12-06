/*
 * © HwaCom Systems Inc. 2015
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.room.am.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseEvent;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Widget;
import com.hwacom.ngtms.c.shared.dto.DeviceSvgPositionConfigDTO;
import com.hwacom.ngtms.cam.client.ui.AmTab;
import com.hwacom.ngtms.hcce.am.factory.ClientFactory;
import com.hwacom.ngtms.room.am.RoomEP;
import com.hwacom.ngtms.room.am.event.RoomLoadMapEvent;
import com.hwacom.ngtms.room.am.event.RoomLoadMapEvent.Action;
import com.hwacom.ngtms.room.am.images.roomDevice.RoomDeviceImages;
import com.hwacom.ngtms.room.am.presenter.RoomDevicePositionEditPresenter;
import com.hwacom.ngtms.room.shared.dto.RoomBackgroundSvgConfigDTO;
import com.sencha.gxt.data.shared.LabelProvider;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.form.ComboBox;
import com.sencha.gxt.widget.core.client.info.Info;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.vectomatic.dom.svg.OMElement;
import org.vectomatic.dom.svg.OMSVGDocument;
import org.vectomatic.dom.svg.OMSVGMatrix;
import org.vectomatic.dom.svg.OMSVGPoint;
import org.vectomatic.dom.svg.OMSVGSVGElement;
import org.vectomatic.dom.svg.ui.SVGResource;
import org.vectomatic.dom.svg.utils.DOMHelper;
import org.vectomatic.dom.svg.utils.OMSVGParser;

public class RoomDevicePositionEditViewer extends AmTab {

  private static RoomDevicePositionEditViewerUiBinder uiBinder =
      GWT.create(RoomDevicePositionEditViewerUiBinder.class);

  interface RoomDevicePositionEditViewerUiBinder
      extends UiBinder<Widget, RoomDevicePositionEditViewer> {}

  private RoomDevicePositionEditPresenter presenter = new RoomDevicePositionEditPresenter(this);

  private final ClientFactory clientFactory = GWT.create(ClientFactory.class);

  @UiField(provided = true)
  ListStore<RoomBackgroundSvgConfigDTO> layerListStore;

  @UiField(provided = true)
  LabelProvider<RoomBackgroundSvgConfigDTO> layerLabelProvider;

  @UiField ComboBox<RoomBackgroundSvgConfigDTO> layerComboBox;

  @UiField ContentPanel svgContainer;

  @UiField ContentPanel aboveSvgContainer;

  //新增Room背景圖層，需要將新的背景圖放入此Map中
  private Map<String, SVGResource> roomMap = new HashMap<String, SVGResource>();

  /** 建立 svg 元件 */
  private OMSVGDocument doc = OMSVGParser.currentDocument();

  private OMSVGSVGElement svg = doc.createSVGSVGElement();
  private Element div = null;
  /** 拖拉設備圖示時記錄原座標 */
  private OMSVGPoint point;

  private boolean dragging;

  /** SimpleEntry 用來配對設備的座標 <設備位置DTO,SVG圖元件> */
  private List<SimpleEntry<DeviceSvgPositionConfigDTO, OMSVGSVGElement>> deviceSvgList =
      new ArrayList<>();

  public RoomDevicePositionEditViewer() {
    layerListStore =
        new ListStore<>(
            new ModelKeyProvider<RoomBackgroundSvgConfigDTO>() {
              @Override
              public String getKey(RoomBackgroundSvgConfigDTO item) {
                return item.getNameId();
              }
            });
    layerLabelProvider =
        new LabelProvider<RoomBackgroundSvgConfigDTO>() {
          @Override
          public String getLabel(RoomBackgroundSvgConfigDTO item) {
            return item.getDescription();
          }
        };
    initWidget(uiBinder.createAndBindUi(this));
    div = svgContainer.getElement();

    layerComboBox.addSelectionHandler(
        new SelectionHandler<RoomBackgroundSvgConfigDTO>() {
          @Override
          public void onSelection(SelectionEvent<RoomBackgroundSvgConfigDTO> event) {
            fillBackgroundSvg(event.getSelectedItem().getNameId());
            aboveSvgContainer.setHeading(event.getSelectedItem().getDescription());
          }
        });
  }

  @UiHandler("saveButton")
  public void onSelectSaveButton(SelectEvent event) {
    List<DeviceSvgPositionConfigDTO> position = new ArrayList<>();
    for (SimpleEntry<DeviceSvgPositionConfigDTO, OMSVGSVGElement> entry : deviceSvgList) {
      DeviceSvgPositionConfigDTO dto = entry.getKey();
      dto.setPositionX(entry.getValue().getX().getBaseVal().getValue());
      dto.setPositionY(entry.getValue().getY().getBaseVal().getValue());
      position.add(dto);
    }
    presenter.saveRoomDevicePositionConfigs(position);
  }

  public void fillAllBackgroundSvg(List<RoomBackgroundSvgConfigDTO> configList) {
    layerListStore.clear();
    layerListStore.addAll(configList);
    if (configList.size() > 0) {
      layerComboBox.setValue(configList.get(0), true);
      fillBackgroundSvg(configList.get(0).getNameId());
    } else {
      Info.display(RoomEP.messages.info_data_error(), RoomEP.messages.info_cannot_findRoomDevice());
    }
  }

  public void fillBackgroundSvg(String svgName) {
    // 先清空所有原件
    clearSVGComponent();

    SVGResource resource = getBackgroundSvgResource(svgName);
    if (resource == null) {
      GWT.log("Fill background svg failed, can not get resource, svgName=" + svgName);
      return;
    }
    OMSVGSVGElement svgElement = resource.getSvg();
    // 將 viewbox 設為 svg 底圖大小
    svg.setViewBox(
        0f,
        0f,
        svgElement.getWidth().getBaseVal().getValue(),
        svgElement.getHeight().getBaseVal().getValue());
    svg.appendChild(svgElement);
    div.appendChild(svg.getElement());
    presenter.queryDevicePositionConfig(svgName);
  }

  /**
   * 依設備位置設定在 svg 圖上畫出設備
   *
   * @param positionConfigs 設備位置設定列表
   */
  public void fillDeviceSvg(List<DeviceSvgPositionConfigDTO> positionConfigs) {
    for (DeviceSvgPositionConfigDTO config : positionConfigs) {
      SVGResource resource = getDeviceSvgResource(config.getDeviceType());
      if (resource == null) {
        GWT.log("Can not find svg image, deviceType=" + config.getDeviceType());
        continue;
      }
      OMSVGSVGElement svgElement = resource.getSvg();
      if (svgElement.getElementById("deviceName") != null) {
        OMElement tooltip = svgElement.getElementById("deviceName");
        tooltip.getElement().setInnerText(config.getDeviceName());
      }
      //設備顯示名稱+狀態內容(預設格式)
      if (svgElement.getElementById("displayName") != null) {
        OMElement displayNameElement = svgElement.getElementById("displayName");
        displayNameElement.getElement().setInnerText(config.getDisplayName() + " " + "[狀態]");
      }
      svgElement.getX().getBaseVal().setValue(config.getPositionX());
      svgElement.getY().getBaseVal().setValue(config.getPositionY());
      // 設定拖拉事件
      svgElement.addMouseDownHandler(
          new MouseDownHandler() {
            @Override
            public void onMouseDown(MouseDownEvent event) {
              dragging = true;
              point = getLocalCoordinates(event, svgElement);
              DOMHelper.setCaptureElement(svgElement, null);
              svgElement.getStyle().setOpacity(0.5f);
              event.stopPropagation();
              event.preventDefault();
            }
          });
      svgElement.addMouseUpHandler(
          new MouseUpHandler() {
            @Override
            public void onMouseUp(MouseUpEvent event) {
              dragging = false;
              DOMHelper.releaseCaptureElement();
              svgElement.getStyle().setOpacity(1.0f);
              event.stopPropagation();
              event.preventDefault();
            }
          });
      svgElement.addMouseMoveHandler(
          new MouseMoveHandler() {
            @Override
            public void onMouseMove(MouseMoveEvent event) {
              if (dragging) {
                OMSVGPoint p = getLocalCoordinates(event, svgElement);
                float dx = p.getX() - point.getX();
                float dy = p.getY() - point.getY();
                float x = svgElement.getX().getBaseVal().getValue();
                float y = svgElement.getY().getBaseVal().getValue();
                svgElement.getX().getBaseVal().setValue(x + dx);
                svgElement.getY().getBaseVal().setValue(y + dy);
                point = p;
              }
              event.stopPropagation();
              event.preventDefault();
            }
          });
      svg.appendChild(svgElement);
      deviceSvgList.add(new SimpleEntry<>(config, svgElement));
    }
  }

  public void sendToMonitor() {
    clientFactory.getEventBus().fireEvent(new RoomLoadMapEvent(Action.LOAD));
  }

  public void setRoomBackground(Map<String, SVGResource> svgResourceMap) {
    roomMap.putAll(svgResourceMap);
  }

  /**
   * 取得 svg 底圖
   *
   * @param svgName 底圖名稱
   */
  private SVGResource getBackgroundSvgResource(String svgName) {
    if (svgName == null) {
      return null;
    }
    return roomMap.get(svgName);
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

  /** 將滑鼠取得的點位轉換為 svg 的點位 */
  private OMSVGPoint getLocalCoordinates(
      MouseEvent<? extends EventHandler> e, OMSVGSVGElement svgElement) {
    OMSVGPoint p = svg.createSVGPoint(e.getClientX(), e.getClientY());
    OMSVGMatrix m = svg.getScreenCTM().inverse();
    return p.matrixTransform(m);
  }

  /** 清空 SVG 相關物件 */
  private void clearSVGComponent() {
    div.removeAllChildren();
    svg = doc.createSVGSVGElement();
    deviceSvgList = new ArrayList<>();
  }
}
