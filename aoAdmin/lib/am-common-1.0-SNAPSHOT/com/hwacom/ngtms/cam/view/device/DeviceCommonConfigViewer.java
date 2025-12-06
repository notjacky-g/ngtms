/*
 * © HwaCom Systems Inc. 2015
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.cam.view.device;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Widget;
import com.hwacom.ngtms.c.shared.Direction;
import com.hwacom.ngtms.c.shared.LocationType;
import com.hwacom.ngtms.c.shared.RampType;
import com.hwacom.ngtms.c.shared.dto.DeviceConfigDTO;
import com.hwacom.ngtms.c.shared.dto.RoadDivisionDTO;
import com.hwacom.ngtms.c.shared.dto.RoadLineDTO;
import com.hwacom.ngtms.c.shared.dto.RoadSectionDTO;
import com.hwacom.ngtms.cam.client.event.AmEventCenter;
import com.hwacom.ngtms.cam.client.event.RoadTreeSelectEvent;
import com.hwacom.ngtms.cam.client.event.RoadTreeSelectHandler;
import com.hwacom.ngtms.cam.client.ui.RoadTreeViewer;
import com.hwacom.ngtms.cam.client.ui.component.DeviceTypeComboBox;
import com.hwacom.ngtms.cam.util.CommonStringConverter;
import com.hwacom.ngtms.cam.util.DataCenter;
import com.hwacom.ngtms.cam.view.Messages;
import com.hwacom.ngtms.cam.vo.IdNameNode;
import com.hwacom.ngtms.cam.vo.KeyValue;
import com.hwacom.ngtms.cam.vo.RoadLineProperties;
import com.hwacom.ngtms.cam.vo.RoadSectionProperties;
import com.hwacom.ngtms.hcce.am.factory.ClientFactory;
import com.sencha.gxt.data.shared.LabelProvider;
import com.sencha.gxt.data.shared.SortDir;
import com.sencha.gxt.data.shared.Store;
import com.sencha.gxt.data.shared.Store.StoreFilter;
import com.sencha.gxt.data.shared.Store.StoreSortInfo;
import com.sencha.gxt.widget.core.client.Composite;
import com.sencha.gxt.widget.core.client.form.DoubleField;
import com.sencha.gxt.widget.core.client.form.IntegerField;
import com.sencha.gxt.widget.core.client.form.SimpleComboBox;
import com.sencha.gxt.widget.core.client.form.TextField;
import java.util.Arrays;
import java.util.List;

public class DeviceCommonConfigViewer extends Composite {

  private static DeviceCommonConfigViewerUiBinder uiBinder =
      GWT.create(DeviceCommonConfigViewerUiBinder.class);

  interface DeviceCommonConfigViewerUiBinder extends UiBinder<Widget, DeviceCommonConfigViewer> {}

  private static final int REFRESH_ROAD_TREE_VIEW_TIME = 1000;
  private static final Messages messages = GWT.create(Messages.class);
  private static final RoadLineProperties roadLineProps = GWT.create(RoadLineProperties.class);
  private static final RoadSectionProperties roadSectionProps =
      GWT.create(RoadSectionProperties.class);
  private KeyValue yesKeyValue = new KeyValue("true", messages.yes());
  private KeyValue noKeyValue = new KeyValue("false", messages.no());
  private DeviceConfigDTO nowDeviceConfig;

  @SuppressWarnings("unused")
  private final ClientFactory clientFactory = GWT.create(ClientFactory.class);

  @SuppressWarnings("unused")
  private List<RoadLineDTO> roadLines;

  @SuppressWarnings("unused")
  private List<RoadSectionDTO> roadSections;

  @SuppressWarnings("unused")
  private List<RoadDivisionDTO> roadDivisions;

  private DirectionFilter directionfilter = new DirectionFilter();

  @SuppressWarnings("unused")
  private List<RoadSectionDTO> roadSectionList;

  private RoadSectionFilter roadSectionFilter = new RoadSectionFilter();
  private IdNameNode oldParentNode;
  private RoadTreeViewer roadTreeView;
  private DeviceTypeComboBox deviceTypeCombo;
  private String deviceType;

  @UiField(provided = true)
  SimpleComboBox<RoadLineDTO> lineNameCombo =
      new SimpleComboBox<RoadLineDTO>(
          new LabelProvider<RoadLineDTO>() {

            @Override
            public String getLabel(RoadLineDTO item) {
              return item.getLineName();
            }
          });

  @UiField(provided = true)
  SimpleComboBox<Direction> directionCombo =
      new SimpleComboBox<Direction>(
          new LabelProvider<Direction>() {
            @Override
            public String getLabel(Direction item) {
              return CommonStringConverter.getDirectionName(item);
            }
          });

  @UiField(provided = true)
  SimpleComboBox<RoadSectionDTO> roadSectionCombo =
      new SimpleComboBox<RoadSectionDTO>(
          new LabelProvider<RoadSectionDTO>() {

            @Override
            public String getLabel(RoadSectionDTO item) {
              return item.getSectionName();
            }
          });

  @UiField(provided = true)
  SimpleComboBox<KeyValue> enableCombo =
      new SimpleComboBox<KeyValue>(
          new LabelProvider<KeyValue>() {

            @Override
            public String getLabel(KeyValue item) {
              return item.getValue();
            }
          });

  @UiField(provided = true)
  SimpleComboBox<LocationType> locationTypeCombo =
      new SimpleComboBox<LocationType>(
          new LabelProvider<LocationType>() {

            @Override
            public String getLabel(LocationType item) {
              return CommonStringConverter.getLocationType(item.name());
            }
          });

  @UiField(provided = true)
  SimpleComboBox<RampType> rampTypeCombo =
      new SimpleComboBox<RampType>(
          new LabelProvider<RampType>() {

            @Override
            public String getLabel(RampType item) {
              return CommonStringConverter.getRampTypeName(item);
            }
          });

  @UiField TextField deviceName;
  @UiField TextField displayName;
  @UiField IntegerField milepost;
  @UiField TextField ip;
  @UiField TextField port;
  @UiField TextField mfccId;
  @UiField TextField defaultMfccId;
  @UiField TextField project;
  @UiField DoubleField longitude;
  @UiField DoubleField latitude;
  @UiField TextField memo;

  public DeviceCommonConfigViewer() {
    initWidget(uiBinder.createAndBindUi(this));
    initCommonComboBox();
    initEventHandler();
    directionCombo.addSelectionHandler(
        new SelectionHandler<Direction>() {
          @Override
          public void onSelection(SelectionEvent<Direction> event) {
            GWT.log("directionCombo onSelection event.getSource():" + event.getSelectedItem());
            resetRoadSectionComboFilter(
                lineNameCombo.getValue().getLineId(), event.getSelectedItem().toString());
            if (roadSectionCombo.getStore().size() > 0) {
              roadSectionCombo.setValue(roadSectionCombo.getStore().get(0));
            }
          }
        });
  }

  private void initEventHandler() {
    AmEventCenter.addRoadTreeSelectHandler(
        new RoadTreeSelectHandler() {

          @Override
          public void onRoadTreeSelect(RoadTreeSelectEvent event) {
            String nodeId = event.getId();
            GWT.log("nodeId=" + nodeId);
            String[] strs = nodeId.split("::::");
            if (strs != null && strs.length == 2) {
              DeviceConfigDTO deviceConfig = DataCenter.getDeviceConfig(strs[1]);
              //GWT.log("deviceConfig=" + deviceConfig);
              nowDeviceConfig = deviceConfig;
              if (deviceConfig != null) {
                if (deviceType != null) {
                  if (deviceType.toString().equals(deviceConfig.getDeviceType().toString())) {
                    GWT.log(
                        "DeviceCommonConfigViewer => "
                            + deviceConfig.getDeviceType().toString()
                            + " : "
                            + deviceType.toString()
                            + " 相符!");
                    fillFields(deviceConfig);
                    GWT.log(
                        "DeviceCommonConfigViewer => deviceType:"
                            + deviceType.toString()
                            + " : deviceConfig:"
                            + deviceConfig);
                    oldParentNode = roadTreeView.getParentNode(nowDeviceConfig.getDeviceName());
                    GWT.log(
                        "DeviceCommonConfigViewer => deviceType:"
                            + deviceType.toString()
                            + ";oldParentNode:"
                            + oldParentNode);
                  } else {
                    GWT.log(
                        deviceConfig.getDeviceType().toString()
                            + " : "
                            + deviceType.toString()
                            + " 不相符,所以不處理!");
                  }
                } else {
                  GWT.log("DeviceCommonConfigViewer => deviceType is null!");
                  fillFields(deviceConfig);
                  oldParentNode = roadTreeView.getParentNode(nowDeviceConfig.getDeviceName());
                  GWT.log("DeviceCommonConfigViewer => deviceConfig=" + deviceConfig);
                  GWT.log("DeviceCommonConfigViewer => oldParentNode=" + oldParentNode);
                }
              }
            } else {
              GWT.log("格式不符,無法 parsing,不處理!(" + deviceType.toString() + ")");
            }
          }
        });
  }

  private void initCommonComboBox() {
    lineNameCombo
        .getStore()
        .addSortInfo(new StoreSortInfo<RoadLineDTO>(roadLineProps.lineId(), SortDir.ASC));
    lineNameCombo.addSelectionHandler(
        new SelectionHandler<RoadLineDTO>() {
          @Override
          public void onSelection(SelectionEvent<RoadLineDTO> event) {
            resetDirectionComboFilter(event.getSelectedItem().getDirection().name());
            if (directionCombo.getStore().size() > 0) {
              directionCombo.setValue(directionCombo.getStore().get(0));
            }
            resetRoadSectionComboFilter(
                event.getSelectedItem().getLineId(), event.getSelectedItem().getDirection().name());
            if (roadSectionCombo.getStore().size() > 0) {
              roadSectionCombo.setValue(roadSectionCombo.getStore().get(0));
            }
          }
        });

    enableCombo.getStore().add(yesKeyValue);
    enableCombo.getStore().add(noKeyValue);
    enableCombo.setValue(yesKeyValue);

    directionCombo.add(Arrays.asList(Direction.values()));
    directionCombo.setValue(Direction.N);
    directionCombo.getStore().addFilter(directionfilter);
    directionCombo.getStore().setEnableFilters(true);
    resetDirectionComboFilter(Direction.N.toString());

    rampTypeCombo.add(Arrays.asList(RampType.values()));
    rampTypeCombo.setValue(RampType.U);

    locationTypeCombo.add(Arrays.asList(LocationType.values()));
    locationTypeCombo.setValue(LocationType.F);

    roadSectionCombo
        .getStore()
        .addSortInfo(
            new StoreSortInfo<RoadSectionDTO>(roadSectionProps.startDivisionId(), SortDir.ASC));
    roadSectionCombo.getStore().addFilter(roadSectionFilter);
    roadSectionCombo.getStore().setEnableFilters(true);
  }

  public void emptyFields() {
    deviceName.setValue(null);
    displayName.setValue(null);
    milepost.setValue(null);
    ip.setValue(null);
    port.setValue(null);
    mfccId.setValue(null);
    defaultMfccId.setValue(null);
    project.setValue(null);
    longitude.setValue(null);
    latitude.setValue(null);
    memo.setValue(null);
    if (lineNameCombo.getStore().size() > 0) {
      lineNameCombo.setValue(lineNameCombo.getStore().get(0));
    } else {
      lineNameCombo.setValue(null);
    }
    directionCombo.setValue(Direction.N);
    if (roadSectionCombo.getStore().size() > 0) {
      roadSectionCombo.setValue(roadSectionCombo.getStore().get(0));
    } else {
      roadSectionCombo.setValue(null);
    }
    enableCombo.setValue(yesKeyValue);
    locationTypeCombo.setValue(LocationType.F);
    rampTypeCombo.setValue(RampType.I);
  }

  public void fillFields(DeviceConfigDTO dto) {
    deviceName.setValue(dto.getDeviceName());
    displayName.setValue(dto.getDisplayName());
    milepost.setValue(dto.getMilepost());
    ip.setValue(dto.getIp());
    port.setValue(dto.getPort());
    mfccId.setValue(dto.getMfccId());
    defaultMfccId.setValue(dto.getDefaultMfccId());
    project.setValue(dto.getProject());
    longitude.setValue(dto.getLongitude());
    latitude.setValue(dto.getLatitude());
    memo.setValue(dto.getMemo());

    RoadLineDTO roadLine = lineNameCombo.getStore().findModelWithKey(dto.getLineId().toString());
    if (roadLine != null) {
      lineNameCombo.setValue(null);
      lineNameCombo.setValue(roadLine);
      resetDirectionComboFilter(roadLine.getDirection().name());
    } else {
      lineNameCombo.setValue(null);
    }
    GWT.log("dto.getDirection().toString()=" + dto.getDirection().toString());
    directionCombo.setValue(null);
    directionCombo.setValue(Direction.valueOf(dto.getDirection().toString()));
    if (roadLine != null) {
      resetRoadSectionComboFilter(roadLine.getLineId(), directionCombo.getValue().toString());
    }
    RoadSectionDTO roadSection =
        roadSectionCombo.getStore().findModelWithKey(dto.getSectionId().toString());
    GWT.log("roadSection=" + roadSection);
    if (roadSection != null) roadSectionCombo.setValue(roadSection);
    else roadSectionCombo.setValue(null);
    if (dto.getEnable()) enableCombo.setValue(yesKeyValue);
    else enableCombo.setValue(noKeyValue);
    locationTypeCombo.setValue(LocationType.valueOf(dto.getLocation()));
    if (dto.getLocationR() == null) {
      rampTypeCombo.setValue(RampType.U);
    } else {
      rampTypeCombo.setValue(RampType.valueOf(dto.getLocationR().toString()));
    }
  }

  public DeviceConfigDTO fieldsToDto() {
    DeviceConfigDTO dto = new DeviceConfigDTO();
    dto.setDeviceName(deviceName.getValue());
    dto.setDisplayName(displayName.getValue());
    dto.setMilepost(milepost.getValue());
    dto.setIp(ip.getValue());
    dto.setPort(port.getValue());
    dto.setMfccId(mfccId.getValue());
    dto.setDefaultMfccId(defaultMfccId.getValue());
    dto.setProject(project.getValue());
    dto.setLongitude(longitude.getValue());
    dto.setLatitude(latitude.getValue());
    dto.setMemo(memo.getValue());
    dto.setLineId(lineNameCombo.getValue() != null ? lineNameCombo.getValue().getLineId() : null);
    dto.setDirection(
        directionCombo.getValue() != null
            ? Direction.valueOf(directionCombo.getValue().toString())
            : null);
    dto.setEnable(
        enableCombo.getValue() != null ? Boolean.valueOf(enableCombo.getValue().getKey()) : null);
    dto.setLocation(
        locationTypeCombo.getValue() != null ? locationTypeCombo.getValue().toString() : null);
    dto.setLocationR(
        rampTypeCombo.getValue() != null
            ? RampType.valueOf(rampTypeCombo.getValue().toString())
            : null);
    dto.setDeviceType(
        deviceTypeCombo.getValue() != null ? deviceTypeCombo.getValue().getId() : null);
    dto.setSectionId(
        roadSectionCombo.getValue() != null ? roadSectionCombo.getValue().getSectionId() : null);
    return dto;
  }

  @Override
  protected void onUnload() {
    super.onUnload();
  }

  public void initData(RoadTreeViewer roadTreeView) {
    this.roadTreeView = roadTreeView;
  }

  public void initData(RoadTreeViewer roadTreeView, DeviceTypeComboBox deviceTypeCombo) {
    this.roadTreeView = roadTreeView;
    this.deviceTypeCombo = deviceTypeCombo;
  }

  private void resetDirectionComboFilter(String direction) {
    //directionCombo.getStore().getFilters().clear();
    directionfilter.setCondition(direction);
    //directionCombo.getStore().addFilter(directionfilter);
    directionCombo.getStore().setEnableFilters(false);
    directionCombo.getStore().setEnableFilters(true);
  }

  private void resetRoadSectionComboFilter(String roadLineId, String direction) {
    GWT.log("resetRoadSectionComboFilter roadLineId = " + roadLineId + ";direction = " + direction);
    //if(!roadSectionCombo.getStore().getFilters().isEmpty())roadSectionCombo.getStore().getFilters().clear();
    roadSectionFilter.setCondition(roadLineId, direction);
    //roadSectionCombo.getStore().addFilter(roadSectionFilter);
    roadSectionCombo.getStore().setEnableFilters(false);
    roadSectionCombo.getStore().setEnableFilters(true);
  }

  private void resetDeviceTypeCombo(String deviceTypeStr) {
    GWT.log("resetDeviceTypeCombo deviceTypeStr=" + deviceTypeStr);
    roadTreeView.resetDeviceType(deviceTypeStr);
  }

  public void refreshRoadTreeViewData(final boolean toClearData) {
    roadTreeView.resetDeviceType(null);
    //        Timer t1 = new Timer() {
    //            @Override
    //            public void run() {
    //                resetDeviceTypeCombo(deviceTypeCombo.getValue().toString());
    //                expandedTreeAndSelected(toClearData);
    //            }
    //        };
    //        t1.schedule(5000);
    resetDeviceTypeCombo(deviceTypeCombo.getValue().toString());
    expandedTreeAndSelected(toClearData);
  }

  private void expandedTreeAndSelected(final boolean toClearData) {
    Timer t =
        new Timer() {
          @Override
          public void run() {
            if (nowDeviceConfig != null) {
              GWT.log(
                  "DeviceCommonConfigViewer refreshRoadTreeViewData => oldParentNode="
                      + oldParentNode);
              IdNameNode newParentNode = roadTreeView.findModelWithKey(oldParentNode.getId());
              GWT.log(
                  "DeviceCommonConfigViewer refreshRoadTreeViewData => newParentNode="
                      + newParentNode);
              if (newParentNode != null) {
                roadTreeView.expandedTreeAndSelected(
                    newParentNode, nowDeviceConfig.getDeviceName());
              }
            } else {
              GWT.log(
                  "DeviceCommonConfigViewer refreshRoadTreeViewData => nowDeviceConfig = null, So can not expend parent fordler!");
            }
            if (toClearData) {
              nowDeviceConfig = null;
              oldParentNode = null;
            }
          }
        };
    t.schedule(REFRESH_ROAD_TREE_VIEW_TIME);
  }

  public void initRoadDivisions(List<RoadDivisionDTO> roadDivisions) {
    if (roadDivisions == null) return;
    this.roadDivisions = roadDivisions;
  }

  public void initRoadLines(List<RoadLineDTO> roadLines) {
    GWT.log("DeviceCommonConfig : roadLines.size=" + roadLines.size());
    this.roadLines = roadLines;
    if (roadLines != null) {
      lineNameCombo.getStore().clear();
      lineNameCombo.getStore().addAll(roadLines);
      if (lineNameCombo.getStore().size() > 0) {
        lineNameCombo.setValue(lineNameCombo.getStore().get(0));
        resetDirectionComboFilter(lineNameCombo.getStore().get(0).getDirection().name());
        GWT.log(
            "initRoadLines = "
                + lineNameCombo.getStore().get(0).getLineId()
                + ":"
                + lineNameCombo.getStore().get(0).getDirection().name());
        resetRoadSectionComboFilter(
            lineNameCombo.getStore().get(0).getLineId(),
            lineNameCombo.getStore().get(0).getDirection().name());
      }
    }
  }

  public void initRoadSections(List<RoadSectionDTO> roadSections) {
    roadSectionCombo.getStore().clear();
    if (roadSections != null) {
      GWT.log("DeviceCommonConfig : roadSections.size=" + roadSections.size());
      this.roadSectionList = roadSections;
      roadSectionCombo.getStore().addAll(roadSections);
      GWT.log(
          "DeviceCommonConfig : roadSectionCombo.getStore().size="
              + roadSectionCombo.getStore().size());
    }
  }

  public void setDeviceType(String deviceType) {
    this.deviceType = deviceType;
  }

  /** @return the nowDeviceConfig */
  public DeviceConfigDTO getNowDeviceConfig() {
    return nowDeviceConfig;
  }

  /** @param nowDeviceConfig the nowDeviceConfig to set */
  public void setNowDeviceConfig(DeviceConfigDTO nowDeviceConfig) {
    this.nowDeviceConfig = nowDeviceConfig;
  }

  class DirectionFilter implements StoreFilter<Direction> {
    private String roadLineDirection = null;

    public void setCondition(String roadLineDirection) {
      this.roadLineDirection = roadLineDirection;
    }

    @Override
    public boolean select(Store<Direction> store, Direction parent, Direction item) {
      if ("N".equals(roadLineDirection)) {
        return item.equals(Direction.N) || item.equals(Direction.S);
      }
      if ("E".equals(roadLineDirection)) {
        return item.equals(Direction.E) || item.equals(Direction.W);
      }
      return false;
    }
  }

  public SimpleComboBox<RoadLineDTO> getLineNameCombo() {
    return lineNameCombo;
  }

  public void setLineNameCombo(SimpleComboBox<RoadLineDTO> lineNameCombo) {
    this.lineNameCombo = lineNameCombo;
  }

  public SimpleComboBox<Direction> getDirectionCombo() {
    return directionCombo;
  }

  public void setDirectionCombo(SimpleComboBox<Direction> directionCombo) {
    this.directionCombo = directionCombo;
  }

  public SimpleComboBox<RoadSectionDTO> getRoadSectionCombo() {
    return roadSectionCombo;
  }

  public void setRoadSectionCombo(SimpleComboBox<RoadSectionDTO> roadSectionCombo) {
    this.roadSectionCombo = roadSectionCombo;
  }

  public SimpleComboBox<KeyValue> getEnableCombo() {
    return enableCombo;
  }

  public void setEnableCombo(SimpleComboBox<KeyValue> enableCombo) {
    this.enableCombo = enableCombo;
  }

  public SimpleComboBox<LocationType> getLocationTypeCombo() {
    return locationTypeCombo;
  }

  public void setLocationTypeCombo(SimpleComboBox<LocationType> locationTypeCombo) {
    this.locationTypeCombo = locationTypeCombo;
  }

  public SimpleComboBox<RampType> getRampTypeCombo() {
    return rampTypeCombo;
  }

  public void setRampTypeCombo(SimpleComboBox<RampType> rampTypeCombo) {
    this.rampTypeCombo = rampTypeCombo;
  }

  public TextField getDeviceName() {
    return deviceName;
  }

  public void setDeviceName(TextField deviceName) {
    this.deviceName = deviceName;
  }

  public TextField getDisplayName() {
    return displayName;
  }

  public void setDisplayName(TextField displayName) {
    this.displayName = displayName;
  }

  public IntegerField getMilepost() {
    return milepost;
  }

  public void setMilepost(IntegerField milepost) {
    this.milepost = milepost;
  }

  public TextField getIp() {
    return ip;
  }

  public void setIp(TextField ip) {
    this.ip = ip;
  }

  public TextField getPort() {
    return port;
  }

  public void setPort(TextField port) {
    this.port = port;
  }

  public TextField getMfccId() {
    return mfccId;
  }

  public void setMfccId(TextField mfccId) {
    this.mfccId = mfccId;
  }

  public TextField getDefaultMfccId() {
    return defaultMfccId;
  }

  public void setDefaultMfccId(TextField defaultMfccId) {
    this.defaultMfccId = defaultMfccId;
  }

  public TextField getProject() {
    return project;
  }

  public void setProject(TextField project) {
    this.project = project;
  }

  public DoubleField getLongitude() {
    return longitude;
  }

  public void setLongitude(DoubleField longitude) {
    this.longitude = longitude;
  }

  public DoubleField getLatitude() {
    return latitude;
  }

  public void setLatitude(DoubleField latitude) {
    this.latitude = latitude;
  }

  public TextField getMemo() {
    return memo;
  }

  public void setMemo(TextField memo) {
    this.memo = memo;
  }

  class RoadSectionFilter implements StoreFilter<RoadSectionDTO> {
    private String roadLineId = null;
    private String direction = null;

    public void setCondition(String roadLineId, String direction) {
      this.roadLineId = roadLineId;
      this.direction = direction;
    }

    @Override
    public boolean select(Store<RoadSectionDTO> store, RoadSectionDTO parent, RoadSectionDTO item) {
      if (roadLineId != null && direction != null) {
        if (roadLineId.equals(item.getLineid()) && direction.equals(item.getDirection()))
          return true;
        else return false;
      }
      return false;
    }
  }
}
