/*
 * © HwaCom Systems Inc. 2015
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.cam.util;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.hwacom.ngtms.c.fm.model.MfccConfig;
import com.hwacom.ngtms.c.shared.DivisionType;
import com.hwacom.ngtms.c.shared.dto.DeviceConfigDTO;
import com.hwacom.ngtms.c.shared.dto.DeviceGroupDTO;
import com.hwacom.ngtms.c.shared.dto.DeviceTypeDTO;
import com.hwacom.ngtms.c.shared.dto.MfccConfigDTO;
import com.hwacom.ngtms.c.shared.dto.RoadDivisionDTO;
import com.hwacom.ngtms.c.shared.dto.RoadLineDTO;
import com.hwacom.ngtms.c.shared.dto.RoadSectionDTO;
import com.hwacom.ngtms.cam.client.HomeEP;
import com.hwacom.ngtms.cam.client.event.AmEventCenter;
import com.hwacom.ngtms.cam.client.event.DataFetchEvent;
import com.hwacom.ngtms.cam.client.event.DataModifyEvent;
import com.hwacom.ngtms.cam.client.event.DataModifyHandler;
import com.hwacom.ngtms.cam.vo.DeviceDataType;
import java.util.ArrayList;
import java.util.List;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;

/**
 * 共用性高、資料內容不常變動（非必要條件）的 DTO，由 DataCenter 實做 RPC、並維護一份 static instance。 目前處理下列 class：
 *
 * <ul>
 *   <li> {@link RoadLineDTO}
 *   <li> {@link RoadSectionDTO}
 *   <li> {@link RoadDivisionDTO}
 *   <li> {@link MfccConfig}
 *   <li> {@link DeviceConfigDTO}
 *   <li> {@link DeviceGroupDTO}
 * </ul>
 *
 * 在呼叫 getter 取值前，必須先確保系統曾經呼叫過 <code>fetchFoo()</code>。 <code>fetchFoo()</code>可視為要求 DataCenter
 * 更新資料， 在 <code>fetchFoo</code> 中會先將 <code>foo</code> 值設定為 null 然後呼叫 RPC。 <b>也就是說：在 RPC 正常結束之前， 該
 * instance 的值會是 null</b>， caller 必須自行解決等待 RPC 完成前的時間差（例如：使用 {@link Scheduler}）。
 *
 * <p>在 static block 中會呼叫：
 *
 * <ul>
 *   <li> {@link #fetchRoadLine()}
 *   <li> {@link #fetchRoadSection()}
 *   <li> {@link #fetchRoadDivision()}
 *   <li> {@link #fetchMfccConfig()}
 * </ul>
 *
 * <p>{@link DataCenter} 目前（又再次改回）指定 {@link DeviceTypeDTO} 的設計， 也就是說，同一時間 {@link DataCenter} 只會有指定
 * {@link DeviceTypeDTO} 的 {@link DeviceConfigDTO} 與 {@link DeviceGroupDTO}。 在 {@link
 * #setDeviceType(ArrayList)} 中會呼叫：
 *
 * <ul>
 *   <li> {@link #fetchDeviceConfig()}
 *   <li> {@link #fetchDeviceGroup()}
 * </ul>
 *
 * @author monty.pan
 */
public class DataCenter {
  static {
    initHandler();
    fetchRoadLine();
    fetchRoadSection();
    fetchRoadDivision();
    fetchMfccConfig();
  }

  private static void initHandler() {
    AmEventCenter.addDataModifyHandler(
        new DataModifyHandler() {
          @Override
          public void onDataModifyEvent(DataModifyEvent event) {
            switch (event.getType()) {
              case DeviceGroup:
                fetchDeviceGroup();
                break;
              default:
                break;
            }
          }
        });
  }

  //==== RoadLine 區 ====//
  private static List<RoadLineDTO> roadLine;

  public static void fetchRoadLine() {
    roadLine = null;
    HomeEP.camService.getRoadLineDTO(
        new MethodCallback<List<RoadLineDTO>>() {
          @Override
          public void onSuccess(Method method, List<RoadLineDTO> result) {
            roadLine = result;
          }

          @Override
          public void onFailure(Method method, Throwable caught) {
            GWT.log("AmDataCenter.getMfccConfigDTO failed.", caught);
          }
        });
  }

  public static List<RoadLineDTO> getRoadLineList() {
    return roadLine;
  }

  /**
   * @param lineId
   * @return {@link #roadLine} 中 lineId 為傳入值的 instance，找不到會回傳 null。
   */
  public static RoadLineDTO getRoadLine(String lineId) {
    if (roadLine != null) {
      for (RoadLineDTO line : roadLine) {
        if (line.getLineId().equals(lineId)) {
          return line;
        }
      }
    }
    return null;
  }

  //==== RoadSection 區 ==== //
  private static List<RoadSectionDTO> roadSection;

  public static void fetchRoadSection() {
    roadSection = null;
    HomeEP.camService.getRoadSectionDTO(
        new MethodCallback<List<RoadSectionDTO>>() {
          @Override
          public void onSuccess(Method method, List<RoadSectionDTO> result) {
            roadSection = result;
          }

          @Override
          public void onFailure(Method method, Throwable caught) {
            GWT.log("AmDataCenter.getMfccConfigDTO failed.", caught);
          }
        });
  }

  public static List<RoadSectionDTO> getRoadSectionList() {
    return roadSection;
  }

  /**
   * @param sectionId
   * @return {@link #roadSection} 中 sectionId 為傳入值的 instance，找不到會回傳 null。
   */
  public static RoadSectionDTO getRoadSection(String sectionId) {
    if (roadSection != null) {
      for (RoadSectionDTO section : roadSection) {
        if (section.getSectionId().equals(sectionId)) {
          return section;
        }
      }
    }
    return null;
  }

  //==== MfccConfig 區 ==== //
  private static List<MfccConfigDTO> mfccConfig;

  public static void fetchMfccConfig() {
    mfccConfig = null;
    HomeEP.camService.getMfccConfigDTO(
        new MethodCallback<List<MfccConfigDTO>>() {
          @Override
          public void onSuccess(Method method, List<MfccConfigDTO> result) {
            mfccConfig = result;
          }

          @Override
          public void onFailure(Method method, Throwable caught) {
            GWT.log("AmDataCenter.getMfccConfigDTO failed.", caught);
          }
        });
  }

  public static List<MfccConfigDTO> getMfccConfigList() {
    return mfccConfig;
  }

  /**
   * @param mfccId
   * @return {@link #mfccConfig} 中 mfccId 為傳入值的 instance，找不到會回傳 null。
   */
  public static MfccConfigDTO getMfccConfig(String mfccId) {
    if (mfccConfig != null) {
      for (MfccConfigDTO mc : mfccConfig) {
        if (mc.getMfccId().equals(mfccId)) {
          return mc;
        }
      }
    }
    return null;
  }

  private static List<String> deviceTypes;

  /**
   * 設定 {@link DataCenter} 的 {@link DeviceTypeDTO}， 同時會重作 {@link #fetchDeviceConfig()} 跟 {@link
   * #fetchDeviceGroup()}。
   */
  public static void setDeviceType(List<String> types) {
    deviceTypes = types;
    fetchDeviceConfig();
    fetchDeviceGroup();
  }

  public static List<String> getDeviceType() {
    return deviceTypes;
  }

  //==== DeviceConfig 區 ====//
  private static List<DeviceConfigDTO> deviceConfig;

  public static void fetchDeviceConfig() {
    deviceConfig = null;
    if (deviceTypes == null) {
      return;
    }
    HomeEP.camService.getDeviceConfig(
        deviceTypes,
        new MethodCallback<List<DeviceConfigDTO>>() {
          @Override
          public void onSuccess(Method method, List<DeviceConfigDTO> result) {
            deviceConfig = result;
          }

          @Override
          public void onFailure(Method method, Throwable caught) {
            GWT.log("AmDataCenter.getDeviceConfig failed.", caught);
          }
        });
  }

  public static List<DeviceConfigDTO> getDeviceConfigList() {
    return deviceConfig;
  }

  /**
   * @param deviceName
   * @return {@link #deviceConfig} 中 deviceName 為傳入值的 instance，找不到會回傳 null。
   */
  public static DeviceConfigDTO getDeviceConfig(String deviceName) {
    if (deviceConfig != null) {
      for (DeviceConfigDTO device : deviceConfig) {
        if (device.getDeviceName().equals(deviceName)) {
          return device;
        }
      }
    }
    return null;
  }

  //==== DeviceGroup 區 ====//
  private static List<DeviceGroupDTO> deviceGroup;

  public static void fetchDeviceGroup() {
    deviceGroup = null;
    if (deviceTypes == null) {
      return;
    }
    HomeEP.camService.getDeviceGroup(
        deviceTypes,
        new MethodCallback<List<DeviceGroupDTO>>() {
          @Override
          public void onSuccess(Method method, List<DeviceGroupDTO> result) {
            deviceGroup = result;
            AmEventCenter.fireEvent(new DataFetchEvent(DeviceDataType.DeviceGroup));
          }

          @Override
          public void onFailure(Method method, Throwable caught) {
            GWT.log("AmDataCenter.getDeviceGroup failed.", caught);
          }
        });
  }

  public static List<DeviceGroupDTO> getDeviceGroupList() {
    return deviceGroup;
  }

  //==== RoadDivision 區 ====//
  private static List<RoadDivisionDTO> roadDivisionList;

  public static void fetchRoadDivision() {
    roadDivisionList = null;
    HomeEP.camService.getRoadDivisionList(
        new MethodCallback<List<RoadDivisionDTO>>() {
          @Override
          public void onSuccess(Method method, List<RoadDivisionDTO> result) {
            if (result != null) {
              roadDivisionList = result;
            }
          }

          @Override
          public void onFailure(Method method, Throwable caught) {
            GWT.log("fetchRoadDivision failed ! Error Message :" + caught.getMessage());
          }
        });
  }

  public static List<RoadDivisionDTO> getRoadDivisionList() {
    return roadDivisionList;
  }

  public static List<RoadDivisionDTO> getRoadDivisionList(DivisionType divisionType) {
    if (roadDivisionList == null) {
      return new ArrayList<>();
    }

    ArrayList<RoadDivisionDTO> result = new ArrayList<RoadDivisionDTO>();
    for (RoadDivisionDTO dto : roadDivisionList) {
      if (dto.getDivisionType() == divisionType) {
        result.add(dto);
      }
    }
    return result;
  }

  public static RoadDivisionDTO getRoadDivision(String divisionId) {
    if (roadDivisionList != null) {
      for (RoadDivisionDTO dto : roadDivisionList) {
        if (dto.getDivisionId().equals(divisionId)) {
          return dto;
        }
      }
    }
    return null;
  }

  public static String getDisplayName(String deviceName) {
    if (deviceConfig != null) {
      for (DeviceConfigDTO dc : deviceConfig) {
        if (dc.getDeviceName().equals(deviceName)) {
          return dc.getDisplayName();
        }
      }
    }
    return deviceName;
  }
}
