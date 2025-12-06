/*
 * © HwaCom Systems Inc. 2015
 * All Rights Reserved
 * No part of this software or any of its contents may be reproduced, copied, modified or adapted,
 * without the prior written consent of HwaCom Systems Inc., unless otherwise indicated for stand-alone materials.
 */
package com.hwacom.ngtms.cam.restygwt;

import com.hwacom.ngtms.c.fm.model.DeviceGroup;
import com.hwacom.ngtms.c.fm.model.DeviceTcStatus;
import com.hwacom.ngtms.c.fm.model.RoadLine;
import com.hwacom.ngtms.c.fm.model.RoadSection;
import com.hwacom.ngtms.c.shared.dto.AmParametersDTO;
import com.hwacom.ngtms.c.shared.dto.DeviceConfigDTO;
import com.hwacom.ngtms.c.shared.dto.DeviceGroupDTO;
import com.hwacom.ngtms.c.shared.dto.DeviceTypeDTO;
import com.hwacom.ngtms.c.shared.dto.EmsDeviceStatusDTO;
import com.hwacom.ngtms.c.shared.dto.MfccConfigDTO;
import com.hwacom.ngtms.c.shared.dto.RoadDivisionDTO;
import com.hwacom.ngtms.c.shared.dto.RoadLineDTO;
import com.hwacom.ngtms.c.shared.dto.RoadSectionDTO;
import com.hwacom.ngtms.common.am.restygwt.AuthDispatcher;
import com.hwacom.ngtms.common.shared.dto.UserDTO;
import java.util.List;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import org.fusesource.restygwt.client.MethodCallback;
import org.fusesource.restygwt.client.Options;
import org.fusesource.restygwt.client.RestService;
import org.fusesource.restygwt.client.TextCallback;

@Path("/api/cam")
public interface CommonRestService extends RestService {

  @GET
  @Path("/userDTO")
  @Options(dispatcher = AuthDispatcher.class)
  public void getUserDTO(MethodCallback<UserDTO> callback);

  /** @return 系統內所有的 {@link RoadLine} */
  @GET
  @Path("/roadLineDTO")
  public void getRoadLineDTO(MethodCallback<List<RoadLineDTO>> callback);

  /** @return 系統內所有的 {@link RoadSection} */
  @GET
  @Path("/roadSectionDTO")
  public void getRoadSectionDTO(MethodCallback<List<RoadSectionDTO>> callback);

  /** @return 系統內所有的 {@link MfccConfigDTO} */
  @GET
  @Path("/mfccConfigDTO")
  public void getMfccConfigDTO(MethodCallback<List<MfccConfigDTO>> callback);

  /**
   * @param DeviceTypeDTO 指定的 device type 清單
   * @return 系統內符合指定 device type 的 {@link DeviceTcConfig}
   */
  @GET
  @Path("/deviceConfigDTO/{deviceType}")
  public void getDeviceConfigDTO(
      @PathParam("deviceType") String deviceType, MethodCallback<List<DeviceConfigDTO>> callback);

  /**
   * @param deviceTypeList 指定的 device type 清單
   * @return 系統內符合指定 device type 的 {@link DeviceTcConfig}
   */
  @GET
  @Path("/deviceConfig")
  public void getDeviceConfig(
      @QueryParam("deviceType") List<String> deviceTypeList,
      MethodCallback<List<DeviceConfigDTO>> callback);

  /**
   * @param deviceTypeList 指定的 device type 清單
   * @return 系統內符合指定 device type 的 {@link DeviceGroup}
   */
  @GET
  @Path("/deviceGroup/deviceTypeList")
  public void getDeviceGroup(
      @QueryParam("deviceTypeList") List<String> deviceTypeList,
      MethodCallback<List<DeviceGroupDTO>> callback);

  /** @param groupId */
  @DELETE
  @Path("/deviceGroup/{groupId}")
  public void removeDeviceGroup(
      @PathParam("groupId") String groupId, MethodCallback<Boolean> callback);

  /** @param DeviceGroupDTO */
  @POST
  @Path("/deviceGroup")
  public void createDeviceGroup(AmParametersDTO params, MethodCallback<Boolean> callback);

  /** @param DeviceGroupDTO */
  @PUT
  @Path("/deviceGroup")
  public void updateDeviceGroup(AmParametersDTO params, MethodCallback<Boolean> callback);

  /**
   * 取得指定 {@link DeviceTypeDTO} 的 {@link DeviceTcStatus}。
   *
   * @param deviceTypeList
   */
  @GET
  @Path("/deviceStatus/deviceTypeList")
  public void getDeviceStatus(
      @QueryParam("deviceTypeList") List<String> deviceTypeList,
      MethodCallback<List<EmsDeviceStatusDTO>> callback);

  /**
   * 取得指定 device name 的 {@link DeviceTcStatus}
   *
   * @param deviceNameList
   */
  @POST
  @Path("/deviceStatusList/deviceNameList")
  public void getDeviceStatusList(
      @QueryParam("deviceNameList") List<String> deviceNameList,
      MethodCallback<List<EmsDeviceStatusDTO>> callback);

  @GET
  @Path("/roadDivisionList")
  public void getRoadDivisionList(MethodCallback<List<RoadDivisionDTO>> callback);

  /**
   * 匯出Excel檔案
   *
   * @param csvData
   * @param csvHeaderWidths
   * @return
   */
  @POST
  @Path("/exportOrderedListDataFromRpt")
  public void exportOrderedListDataFromRpt(AmParametersDTO params, TextCallback callback);

  /** @return 系統內所有的 {@link deviceTypeDTO} */
  @GET
  @Path("/deviceTypeList/{tcTypeOnly}")
  public void getDeviceTypeList(
      @PathParam("tcTypeOnly") Boolean tcTypeOnly, MethodCallback<List<DeviceTypeDTO>> callback);
}
