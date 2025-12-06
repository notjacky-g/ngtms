/*
 * © HwaCom Systems Inc. 2015
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.cam.util;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.hwacom.ngtms.c.fm.model.DeviceTcStatus;
import com.hwacom.ngtms.c.shared.DeviceStatusType;
import com.hwacom.ngtms.c.shared.dto.DeviceStatusDTO;
import com.hwacom.ngtms.c.shared.dto.EmsDeviceStatusDTO;
import com.hwacom.ngtms.c.shared.dto.RoadLineDTO;
import com.hwacom.ngtms.c.shared.dto.RoadSectionDTO;
import com.hwacom.ngtms.cam.view.Messages;
import com.hwacom.ngtms.cam.vo.DeviceStatusProperties;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 疑似不同 device 的硬體狀態共通部份（byte1），顯示字串會不同， 因此設計成 caller 可自己取 {@link #fixColumnConfig}、{@link
 * #locationColumnConfig}， 然後自己重新組 {@link #hwCommonColumnConfig} 的部份，而不限制只能使用 {@link
 * #getCommonColumnConfig()}
 */
public class DeviceStatusUtil {
  private static final Messages messages = GWT.create(Messages.class);
  private static final ArrayList<ColumnConfig<DeviceStatusDTO, ?>> fixColumnConfig =
      new ArrayList<>();
  private static final ArrayList<ColumnConfig<DeviceStatusDTO, ?>> locationColumnConfig =
      new ArrayList<>();
  private static final ArrayList<ColumnConfig<DeviceStatusDTO, ?>> hwCommonColumnConfig =
      new ArrayList<>();
  private static final ArrayList<ColumnConfig<DeviceStatusDTO, ?>> allColumnConfig =
      new ArrayList<>();
  // 用來顯示變色的cell
  private static AbstractCell<String> hwDisplayCell =
      new AbstractCell<String>() {
        @Override
        public void render(
            com.google.gwt.cell.client.Cell.Context context, String value, SafeHtmlBuilder sb) {
          sb.appendHtmlConstant(value);
        }
      };

  static {
    DeviceStatusProperties props = DeviceStatusProperties.INSTANCE;

    // ==== 固定不會隱藏的部份 ==== //
    fixColumnConfig.add(
        new ColumnConfig<>(props.displayName(), 200, messages.deviceStatus_deviceName()));
    ColumnConfig<DeviceStatusDTO, DeviceStatusType> statusColumn =
        new ColumnConfig<>(
            new ValueProvider<DeviceStatusDTO, DeviceStatusType>() {
              @Override
              public DeviceStatusType getValue(DeviceStatusDTO item) {
                if (item.getDeviceConfig() == null) {
                  return DeviceStatusType.SUSPEND;
                }
                if (!item.getDeviceConfig().getEnable()) {
                  return DeviceStatusType.SUSPEND;
                }
                if (item.getEmsDeviceStatus() == null) {
                  return DeviceStatusType.OFFLINE;
                }
                return item.getEmsDeviceStatus().isAlive()
                    ? DeviceStatusType.ONLINE
                    : DeviceStatusType.OFFLINE;
              }

              @Override
              public void setValue(DeviceStatusDTO object, DeviceStatusType value) {
                // TODO Auto-generated method stub

              }

              @Override
              public String getPath() {
                // TODO Auto-generated method stub
                return null;
              }
            },
            100,
            messages.deviceStatus_connectStatus());
    statusColumn.setCell(
        new AbstractCell<DeviceStatusType>() {
          @Override
          public void render(
              com.google.gwt.cell.client.Cell.Context context,
              DeviceStatusType value,
              SafeHtmlBuilder sb) {
            String color = null;
            String displayValue = null;
            switch (value) {
              case ONLINE:
                color = messages.statusColor_connect();
                displayValue = messages.grid_status_connect();
                break;
              case SUSPEND:
                color = messages.statusColor_stop();
                displayValue = messages.grid_status_stop();
                break;
              case OFFLINE:
              default:
                color = messages.statusColor_broken();
                displayValue = messages.grid_status_offline();
                break;
            }
            sb.appendHtmlConstant("<span style='color:" + color + "'>");
            sb.appendHtmlConstant(displayValue);
            sb.appendHtmlConstant("</span>");
          }
        });
    fixColumnConfig.add(statusColumn);
    // ======== //

    // ==== 設備位置的部份 ==== //
    locationColumnConfig.add(
        new ColumnConfig<>(
            new ValueProvider<DeviceStatusDTO, String>() {
              @Override
              public String getValue(DeviceStatusDTO object) {
                RoadLineDTO dto = DataCenter.getRoadLine(object.getDeviceConfig().getLineId());
                return dto == null ? "" : dto.getLineName();
              }

              @Override
              public void setValue(DeviceStatusDTO object, String value) {}

              @Override
              public String getPath() {
                return "lineName";
              }
            },
            100,
            messages.deviceStatus_roadLine()));
    locationColumnConfig.add(
        new ColumnConfig<>(
            new ValueProvider<DeviceStatusDTO, String>() {
              @Override
              public String getValue(DeviceStatusDTO object) {
                RoadSectionDTO dto =
                    DataCenter.getRoadSection(object.getDeviceConfig().getSectionId());
                return dto == null ? "" : dto.getSectionName();
              }

              @Override
              public void setValue(DeviceStatusDTO object, String value) {}

              @Override
              public String getPath() {
                return "sectionName";
              }
            },
            100,
            messages.deviceStatus_roadSection()));
    locationColumnConfig.add(
        new ColumnConfig<>(
            new ValueProvider<DeviceStatusDTO, String>() {
              @Override
              public String getValue(DeviceStatusDTO object) {
                return CommonStringConverter.getDirectionName(
                    object.getDeviceConfig().getDirection());
              }

              @Override
              public void setValue(DeviceStatusDTO object, String value) {}

              @Override
              public String getPath() {
                return "direction";
              }
            },
            100,
            messages.deviceStatus_direction()));
    locationColumnConfig.add(
        new ColumnConfig<>(
            new ValueProvider<DeviceStatusDTO, String>() {
              @Override
              public String getValue(DeviceStatusDTO object) {
                return CommonStringConverter.toMilepost(object.getDeviceConfig().getMilepost());
              }

              @Override
              public void setValue(DeviceStatusDTO object, String value) {}

              @Override
              public String getPath() {
                return "milepost";
              }
            },
            100,
            messages.deviceStatus_milepost()));
    locationColumnConfig.add(new ColumnConfig<>(props.ip(), 100, messages.deviceStatus_ip()));
    // ======== //

    // ==== 硬體狀態的部份 ==== //
    ColumnConfig<DeviceStatusDTO, String> bit0Column =
        new ColumnConfig<DeviceStatusDTO, String>(
            new ImprovedEmsDeviceStatusProvider(0) {
              @Override
              public String convert(boolean value) {
                return (!value) ? messages.grid_status_abnormal() : messages.grid_status_normal();
              }
            },
            100,
            messages.deviceStatus_deviceStatus());
    bit0Column.setCell(hwDisplayCell);
    hwCommonColumnConfig.add(bit0Column);
    ColumnConfig<DeviceStatusDTO, String> bit1Column =
        new ColumnConfig<DeviceStatusDTO, String>(
            new ImprovedEmsDeviceStatusProvider(1) {
              @Override
              public String convert(boolean value) {
                return (!value) ? messages.grid_status_open() : messages.grid_status_close();
              }
            },
            100,
            messages.deviceStatus_boxDoor());
    bit1Column.setCell(hwDisplayCell);
    hwCommonColumnConfig.add(bit1Column);
    ColumnConfig<DeviceStatusDTO, String> bit2Column =
        new ColumnConfig<DeviceStatusDTO, String>(
            new ImprovedEmsDeviceStatusProvider(2) {
              @Override
              public String convert(boolean value) {
                return (!value) ? messages.grid_status_yes() : messages.grid_status_no();
              }
            },
            100,
            messages.deviceStatus_handSet());
    bit2Column.setCell(hwDisplayCell);
    hwCommonColumnConfig.add(bit2Column);
    ColumnConfig<DeviceStatusDTO, String> bit3Column =
        new ColumnConfig<DeviceStatusDTO, String>(
            new ImprovedEmsDeviceStatusProvider(3) {
              @Override
              public String convert(boolean value) {
                return (!value) ? messages.grid_status_onSite() : messages.grid_status_remote();
              }
            },
            100,
            messages.deviceStatus_operationFlag());
    bit3Column.setCell(hwDisplayCell);
    hwCommonColumnConfig.add(bit3Column);
    ColumnConfig<DeviceStatusDTO, String> bit4Column =
        new ColumnConfig<DeviceStatusDTO, String>(
            new ImprovedEmsDeviceStatusProvider(4) {
              @Override
              public String convert(boolean value) {
                return (!value) ? messages.grid_status_active() : messages.grid_status_inactive();
              }
            },
            100,
            messages.deviceStatus_parameterRequest());
    bit4Column.setCell(hwDisplayCell);
    hwCommonColumnConfig.add(bit4Column);
    ColumnConfig<DeviceStatusDTO, String> bit5Column =
        new ColumnConfig<DeviceStatusDTO, String>(
            new ImprovedEmsDeviceStatusProvider(5) {
              @Override
              public String convert(boolean value) {
                return (!value) ? messages.grid_status_active() : messages.grid_status_inactive();
              }
            },
            100,
            messages.deviceStatus_autoReboot());
    bit5Column.setCell(hwDisplayCell);
    hwCommonColumnConfig.add(bit5Column);
    // bit 6：燈號熄滅，並非所有 device 均有
    ColumnConfig<DeviceStatusDTO, String> bit7Column =
        new ColumnConfig<DeviceStatusDTO, String>(
            new ImprovedEmsDeviceStatusProvider(7) {
              @Override
              public String convert(boolean value) {
                return (!value) ? messages.grid_status_broken() : messages.grid_status_normal();
              }
            },
            100,
            messages.deviceStatus_inputUnit());
    bit7Column.setCell(hwDisplayCell);
    hwCommonColumnConfig.add(bit7Column);
    // ======== //

    allColumnConfig.addAll(fixColumnConfig);
    allColumnConfig.addAll(locationColumnConfig);
    allColumnConfig.addAll(hwCommonColumnConfig);
  }

  /**
   * 以下列順序產生 {@link ColumnConfig} list：
   *
   * <ul>
   *   <li>{@link #fixColumnConfig}
   *   <li>{@link #locationColumnConfig} [設備位置]
   *   <li>{@link #hwCommonColumnConfig} [硬體狀態]（各 device 共同欄位，不含「燈號熄滅」）
   * </ul>
   *
   * 則可以直接使用此回傳值。
   *
   * @return 產生 {@link DeviceStatusDTO} 的通用 ColumnConfig
   */
  public static List<ColumnConfig<DeviceStatusDTO, ?>> getCommonColumnConfig() {
    return Collections.unmodifiableList(allColumnConfig);
  }

  public static List<ColumnConfig<DeviceStatusDTO, ?>> getFixcolumnconfig() {
    return Collections.unmodifiableList(fixColumnConfig);
  }

  public static List<ColumnConfig<DeviceStatusDTO, ?>> getLocationColumnConfig() {
    return Collections.unmodifiableList(locationColumnConfig);
  }

  public static List<ColumnConfig<DeviceStatusDTO, ?>> getHwCommonColumnConfig() {
    return Collections.unmodifiableList(hwCommonColumnConfig);
  }

  public static int getLocationColumnLength() {
    return locationColumnConfig.size();
  }

  public static int getFixColumnLength() {
    return fixColumnConfig.size();
  }

  public static int getHwCommonColumnLength() {
    return hwCommonColumnConfig.size();
  }

  /**
   * 針對 {@link DeviceStatusDTO} 的 {@link DeviceTcStatus} 提供特製化的 {@link ValueProvider}。
   *
   * <p>設計目標是讓 caller 不用再檢查 {@link DeviceStatusDTO#getEmsDeviceStatus()} 是否為 null（直接回傳空字串）， 若 {@link
   * EmsDeviceStatus#getBit(Integer)} 值為 null 也可以透過 override {@link #convertNull()} 處理。
   */
  public abstract static class EmsDeviceStatusProvider
      implements ValueProvider<DeviceStatusDTO, String> {
    private int bit;

    public EmsDeviceStatusProvider(int bit) {
      this.bit = bit;
    }

    /**
     * 將指定 bit 的值轉換成畫面要顯示的字串。 如果指定 bit 的值為 null，則是由 {@link #convertNull()} 處理。
     *
     * @param value {@link EmsDeviceStatus#getBit(Integer)} 是否等於 {@link EmsDeviceStatus#BIT_NORMAL}
     */
    public abstract String convert(boolean value);

    /** 當指定 bit 的值為 null 時的轉換成字串的邏輯。 預設行為是回傳空字串。 */
    public String convertNull() {
      return "";
    }

    @Override
    public String getValue(DeviceStatusDTO object) {
      if (object.getEmsDeviceStatus() == null) {
        return "";
      }
      if (object.getEmsDeviceStatus().getCommStatus() == EmsDeviceStatusDTO.COMM_STATUS_OFFLINE) {
        return "";
      }
      if (!object.getDeviceConfig().getEnable()) {
        return "";
      }
      if (object.getEmsDeviceStatus().getBit(bit) == null) {
        return convertNull();
      }
      return convert(object.getEmsDeviceStatus().getBit(bit) == EmsDeviceStatusDTO.BIT_NORMAL);
    }

    @Override
    public void setValue(DeviceStatusDTO object, String value) {}

    @Override
    public String getPath() {
      return "emsDeviceStatusProvider";
    }
  }

  public abstract static class ImprovedEmsDeviceStatusProvider
      implements ValueProvider<DeviceStatusDTO, String> {
    private int bit;

    public ImprovedEmsDeviceStatusProvider(int bit) {
      this.bit = bit;
    }

    public abstract String convert(boolean value);

    @Override
    public String getValue(DeviceStatusDTO object) {
      String color = "black";
      if (object == null) {
        return "";
      }
      if (object.getEmsDeviceStatus() == null) {
        return "";
      }
      if (object.getEmsDeviceStatus().getCommStatus() == EmsDeviceStatusDTO.COMM_STATUS_OFFLINE) {
        return "";
      }
      if (!object.getDeviceConfig().getEnable()) {
        return "";
      }
      if (object.getEmsDeviceStatus().getBit(bit) == null) {
        return "";
      }
      if (object != null
          && (object.getEmsDeviceStatus() != null
              && object.getEmsDeviceStatus().getCommStatus()
                  == EmsDeviceStatusDTO.COMM_STATUS_ONLINE)
          && object.getEmsDeviceStatus().getBit(bit) != EmsDeviceStatusDTO.BIT_NORMAL) {
        color = "red";
      }
      if (object.getEmsDeviceStatus().getBit(bit)) {
        color = "red";
      }
      return "<span style='color:"
          + color
          + "'>"
          + String.valueOf(
              convert(object.getEmsDeviceStatus().getBit(bit) == EmsDeviceStatusDTO.BIT_NORMAL))
          + "</span>";
    }

    @Override
    public void setValue(DeviceStatusDTO object, String value) {}

    @Override
    public String getPath() {
      return "improvedEmsDeviceStatusProvider";
    }
  }

  public enum ColumnGroup {
    DEVICE_LOCATION(messages.deviceStatus_deviceLocation()),
    HARDWARE_STATUS(messages.deviceStatus_hardwareStatus());

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

  public static void addColumnConfig(
      List<ColumnConfig<DeviceStatusDTO, ?>> configList,
      ColumnConfig<DeviceStatusDTO, String> config) {
    configList.add(config);
    config.setCell(hwDisplayCell);
  }
}
