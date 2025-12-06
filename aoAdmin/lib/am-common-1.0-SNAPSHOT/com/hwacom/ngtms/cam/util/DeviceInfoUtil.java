/*
 * © HwaCom Systems Inc. 2015
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.cam.util;

import com.google.gwt.core.client.GWT;
import com.hwacom.ngtms.c.shared.dto.DeviceConfigDTO;
import com.hwacom.ngtms.c.shared.dto.DeviceInfoDTO;
import com.hwacom.ngtms.c.shared.dto.MfccConfigDTO;
import com.hwacom.ngtms.cam.view.Messages;
import com.hwacom.ngtms.cam.vo.DeviceInfoProperties;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import java.util.ArrayList;
import java.util.List;

public class DeviceInfoUtil {
  private static final Messages messages = GWT.create(Messages.class);
  /**
   * 產生共用（{@link DeviceConfigDTO}、{@link MfccConfigDTO}）的 ColumnConfig。 順序是先 DeviceConfig、後
   * MfccConfig。
   *
   * @param props 子系統自行實做的 property access
   * @return
   */
  public static <SC> List<ColumnConfig<DeviceInfoDTO<SC>, ?>> genCommonColumnConfig(
      DeviceInfoProperties<SC> props) {
    ArrayList<ColumnConfig<DeviceInfoDTO<SC>, ?>> result = new ArrayList<>();

    // ==== DeviceConfig 部份 ==== //
    //注意，修改請一併修改 getDeviceConfigColumnLength();
    result.add(
        new ColumnConfig<>(props.displayName(), 180, messages.deviceInfo_grid_displayName()));
    //R22 不是每個都有設備種類
    result.add(
        new ColumnConfig<>(
            new ValueProvider<DeviceInfoDTO<SC>, String>() {
              @Override
              public String getValue(DeviceInfoDTO<SC> object) {
                return object.getDeviceConfig().getEnable()
                    ? (object.isConnect() != null && object.isConnect()
                        ? messages.grid_status_connect()
                        : "斷線")
                    : messages.grid_status_stop();
              }

              @Override
              public void setValue(DeviceInfoDTO<SC> object, String value) {}

              @Override
              public String getPath() {
                return "status";
              }
            },
            120,
            messages.deviceInfo_grid_enable()));
    result.add(
        new ColumnConfig<>(
            new ValueProvider<DeviceInfoDTO<SC>, String>() {
              @Override
              public String getValue(DeviceInfoDTO<SC> object) {
                return DataCenter.getRoadLine(object.getDeviceConfig().getLineId()).getLineName();
              }

              @Override
              public void setValue(DeviceInfoDTO<SC> object, String value) {}

              @Override
              public String getPath() {
                return "roadLine";
              }
            },
            100,
            messages.deviceInfo_grid_roadLine()));
    result.add(
        new ColumnConfig<>(
            new ValueProvider<DeviceInfoDTO<SC>, String>() {
              @Override
              public String getValue(DeviceInfoDTO<SC> object) {
                return DataCenter.getRoadSection(object.getDeviceConfig().getSectionId())
                    .getSectionName();
              }

              @Override
              public void setValue(DeviceInfoDTO<SC> object, String value) {}

              @Override
              public String getPath() {
                return "roadSection";
              }
            },
            100,
            messages.deviceInfo_grid_roadSection()));
    result.add(
        new ColumnConfig<>(
            new ValueProvider<DeviceInfoDTO<SC>, String>() {
              @Override
              public String getValue(DeviceInfoDTO<SC> object) {
                return CommonStringConverter.getDirectionName(
                    object.getDeviceConfig().getDirection());
              }

              @Override
              public void setValue(DeviceInfoDTO<SC> object, String value) {}

              @Override
              public String getPath() {
                return "direction";
              }
            },
            100,
            messages.deviceInfo_grid_direction()));
    result.add(
        new ColumnConfig<>(
            new ValueProvider<DeviceInfoDTO<SC>, String>() {
              @Override
              public String getValue(DeviceInfoDTO<SC> object) {
                return CommonStringConverter.toMilepost(object.getDeviceConfig().getMilepost());
              }

              @Override
              public void setValue(DeviceInfoDTO<SC> object, String value) {}

              @Override
              public String getPath() {
                return "milepost";
              }
            },
            100,
            messages.deviceInfo_grid_milepost()));
    result.add(new ColumnConfig<>(props.ip(), 100, messages.deviceInfo_grid_ip()));
    result.add(new ColumnConfig<>(props.port(), 100, messages.deviceInfo_grid_port()));
    // ======== //

    // ==== MfccConfig 部份 ==== //
    //注意，修改請一併修改 getMfccConfigColumnLength();
    result.add(
        new ColumnConfig<>(
            new ValueProvider<DeviceInfoDTO<SC>, String>() {
              @Override
              public String getValue(DeviceInfoDTO<SC> object) {
                return object.getMfccConfig() == null ? "" : object.getMfccConfig().getMfccName();
              }

              @Override
              public void setValue(DeviceInfoDTO<SC> object, String value) {}

              @Override
              public String getPath() {
                return "mfccName";
              }
            },
            100,
            messages.deviceInfo_grid_mfccName()));
    // ======== //

    return result;
  }

  public static int getDeviceConfigColumnLength() {
    return 8;
  }

  public static int getMfccConfigColumnLength() {
    return 1;
  }

  public enum ColumnGroup {
    BASIC_INFO(messages.deviceInfo_basicInfo()),
    NETWORK_STATUS(messages.deviceInfo_networkStatus()),
    DEVICE_STATUS(messages.deviceInfo_deviceStatus());

    private String name;

    ColumnGroup(String name) {
      this.name = name;
    }

    public String getName() {
      return name;
    }

    public static ColumnGroup parse(String name) {
      for (ColumnGroup each : ColumnGroup.values()) {
        if (each.getName().equals(name)) {
          return each;
        }
      }
      throw new IllegalArgumentException("can't find ColumnGroup by name: " + name);
    }
  }
}
