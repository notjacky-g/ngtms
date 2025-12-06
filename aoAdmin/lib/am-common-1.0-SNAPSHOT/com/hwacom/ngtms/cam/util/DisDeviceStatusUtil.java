/*
 * © HwaCom Systems Inc. 2015
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.cam.util;

import com.hwacom.ngtms.c.fm.model.DeviceTcStatus;
import com.hwacom.ngtms.c.shared.dto.DeviceStatusDTO;
import com.hwacom.ngtms.c.shared.dto.EmsDeviceStatusDTO;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;

public class DisDeviceStatusUtil {
  /**
   *
   *
   * <ul>
   *   <li>指令編碼：01H
   *   <li>參數位址：byte 1 bit 6
   *   <li>參數定義：燈號熄減
   *   <li>適用設備：共通
   * </ul>
   */
  public static ColumnConfig<DeviceStatusDTO, ?> getLightStatusColumnConfig() {
    return new ColumnConfig<>(
        new EmsDeviceStatusProvider(6) {
          @Override
          public String convert(boolean value) {
            return CommonStringConverter.toRegularOrNot(value);
          }
        },
        100,
        CommonStringConverter.messages.deviceStatusHeader_lightStatus());
  }

  /**
   *
   *
   * <ul>
   *   <li>指令編碼：01H
   *   <li>參數位址：byte 2 bit 0
   *   <li>參數定義：顯示設備故障
   *   <li>適用設備：CMS/CGS/SLS/LCS/RMS/FS/FLA/FWL
   * </ul>
   */
  public static ColumnConfig<DeviceStatusDTO, ?> getDisplayDeviceStatus() {
    return new ColumnConfig<>(
        new EmsDeviceStatusProvider(8) {
          @Override
          public String convert(boolean value) {
            return CommonStringConverter.toRegularOrNot(value);
          }
        },
        100,
        CommonStringConverter.messages.deviceStatusHeader_displayDeviceStatus());
  }

  /**
   *
   *
   * <ul>
   *   <li>指令編碼：01H
   *   <li>參數位址：byte 2 bit 1
   *   <li>參數定義：終端控制器與上層連線異常
   *   <li>適用設備：CMS/CGS/SLS/LCS/RMS/FS/FLA/FWL
   * </ul>
   */
  public static ColumnConfig<DeviceStatusDTO, ?> getTcUplink() {
    return new ColumnConfig<>(
        new EmsDeviceStatusProvider(9) {
          @Override
          public String convert(boolean value) {
            return CommonStringConverter.toRegularOrNot(value);
          }
        },
        100,
        CommonStringConverter.messages.deviceStatusHeader_tcUplink());
  }

  /**
   *
   *
   * <ul>
   *   <li>指令編碼：01H
   *   <li>參數位址：byte 2 bit 2
   *   <li>參數定義：終端控制器與下層連線異常
   *   <li>適用設備：CMS/CGS/SLS/LCS/RMS/FS/FLA/FWL
   * </ul>
   */
  public static ColumnConfig<DeviceStatusDTO, ?> getTcDownlink() {
    return new ColumnConfig<>(
        new EmsDeviceStatusProvider(10) {
          @Override
          public String convert(boolean value) {
            return CommonStringConverter.toRegularOrNot(value);
          }
        },
        100,
        CommonStringConverter.messages.deviceStatusHeader_tcDownlink());
  }

  /**
   *
   *
   * <ul>
   *   <li>指令編碼：01H
   *   <li>參數位址：byte 2 bit 3
   *   <li>參數定義：LED 或燈泡模組故障
   *   <li>適用設備：SLS/LCS/RMS/FS/FLA/FWL
   * </ul>
   */
  public static ColumnConfig<DeviceStatusDTO, ?> getLedStatus() {
    return new ColumnConfig<>(
        new EmsDeviceStatusProvider(11) {
          @Override
          public String convert(boolean value) {
            return CommonStringConverter.toRegularOrNot(value);
          }
        },
        100,
        CommonStringConverter.messages.deviceStatusHeader_ledStatus());
  }

  /**
   *
   *
   * <ul>
   *   <li>指令編碼：01H
   *   <li>參數位址：byte 2 bit 5
   *   <li>參數定義：CGS 照明燈異常
   *   <li>適用設備：CMS/CGS
   * </ul>
   */
  public static ColumnConfig<DeviceStatusDTO, ?> getCgsStatus() {
    return new ColumnConfig<>(
        new EmsDeviceStatusProvider(13) {
          @Override
          public String convert(boolean value) {
            return CommonStringConverter.toRegularOrNot(value);
          }
        },
        100,
        CommonStringConverter.messages.deviceStatusHeader_cgsStatus());
  }

  /**
   *
   *
   * <ul>
   *   <li>指令編碼：01H
   *   <li>參數位址：byte 2 bit 7
   *   <li>參數定義：RTU 連動通訊異常
   *   <li>適用設備：CMS/CGS/SLS/LCS/RMS/FS/FLA/FWL
   * </ul>
   */
  public static ColumnConfig<DeviceStatusDTO, ?> getRtuCommunication() {
    return new ColumnConfig<>(
        new EmsDeviceStatusProvider(15) {
          @Override
          public String convert(boolean value) {
            return CommonStringConverter.toRegularOrNot(value);
          }
        },
        100,
        CommonStringConverter.messages.deviceStatusHeader_rtuCommunication());
  }

  /**
   *
   *
   * <ul>
   *   <li>指令編碼：0BH
   *   <li>指令名稱：回報設備運作狀態
   * </ul>
   */
  public static ColumnConfig<DeviceStatusDTO, ?> getOperationStatus() {
    return new ColumnConfig<>(
        new ValueProvider<DeviceStatusDTO, String>() {
          @Override
          public String getValue(DeviceStatusDTO object) {
            if (object.getEmsDeviceStatus() == null
                || object.getEmsDeviceStatus().getOpStatusHiNibble() == null
                || object.getEmsDeviceStatus().getOpStatusLowNibble() == null) {
              return "";
            }

            String result =
                CommonStringConverter.getOpStatusHiNibbleTypeName(
                        object.getEmsDeviceStatus().getOpStatusHiNibble())
                    + ","
                    + CommonStringConverter.getOpStatusLowNibbleTypeName(
                        object.getEmsDeviceStatus().getOpStatusLowNibble());
            return result.equals(",") ? "" : result;
          }

          @Override
          public void setValue(DeviceStatusDTO object, String value) {}

          @Override
          public String getPath() {
            return "opStatus";
          }
        },
        100,
        CommonStringConverter.messages.deviceStatusHeader_operationStatus());
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
}
